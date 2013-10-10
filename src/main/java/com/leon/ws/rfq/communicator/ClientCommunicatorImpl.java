package com.leon.ws.rfq.communicator;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.leon.ws.rfq.events.JsonSerializableEvent;

public final class ClientCommunicatorImpl extends Thread implements ClientCommunicator, ApplicationListener
{
	private static final Logger logger = LoggerFactory.getLogger(ClientCommunicatorImpl.class);
	private AsynchronousChannelGroup channelGroup;
	private AsynchronousServerSocketChannel serverSocket;
	private final Lock lockObject = new ReentrantLock(true); // with fair ordering policy
	private final Map<String, AsynchronousSocketChannel> mapOfClients =  new ConcurrentSkipListMap<>();
	private final int portNumber;
	private final String ipAddress;

	/**
	 * Constructor.
	 *
	 * @param  ipAddress					the IP address that will be used for socket communication.
	 * @param portNumber					the port number that will be used for socket communication.
	 * @throws IllegalArgumentException		if the IP address string is empty or the port number <= 0.
	 */
	public ClientCommunicatorImpl(String ipAddress, int portNumber)
	{
		if(ipAddress.isEmpty())
			throw new IllegalArgumentException("ipAddress");

		if(portNumber <= 0)
			throw new IllegalArgumentException("portNumber");

		this.portNumber = portNumber;
		this.ipAddress = ipAddress;
	}

	/**
	 * Opens up the socket channel and starts the client communicator on its own thread.
	 *
	 */
	@Override
	public boolean initialize()
	{
		try
		{
			this.channelGroup = AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), 10);
			this.serverSocket = AsynchronousServerSocketChannel.open(this.channelGroup);

			if(this.serverSocket.isOpen())
			{
				this.serverSocket.bind(new InetSocketAddress(this.ipAddress, this.portNumber));

				if(logger.isInfoEnabled())
					logger.info(String.format("server socket has been opened and is bound to %s using port %d", this.ipAddress, this.portNumber));

				// Sleep to allow the binding to complete
				sleep(2000);
				this.start();
				return true;
			}
			else
			{
				if(logger.isErrorEnabled())
					logger.error("server socket could not be opened!");
			}

		}
		catch(Exception ex)
		{
			if(logger.isErrorEnabled())
				logger.error(String.format("Exception raised:%s", ex));
		}
		return false;
	}

	/**
	 * Continuously waits for new client connections.
	 *
	 */
	@Override
	public void run()
	{
		if(logger.isInfoEnabled())
			logger.info("Waiting for new clients...");

		while(true)
		{
			try
			{
				Future<AsynchronousSocketChannel> future = this.serverSocket.accept();
				saveClientSocketChannel(future.get());
			}
			catch(java.util.concurrent.ExecutionException ex)
			{
				if(logger.isErrorEnabled())
					logger.error(String.format("ExecutionException raised:%s. Exiting run loop!", ex));

				break;
			}
			catch(Exception ex)
			{
				if(logger.isErrorEnabled())
					logger.error(String.format("Exception raised:%s", ex));
			}
		}
	}

	/**
	 * Saves the details of any new clients that wish to listen to JSOn messages.
	 *
	 * @param  newClient				the new client that will listen to messages.
	 * @throws NullPointerException		if newClient == null
	 */
	private void saveClientSocketChannel(AsynchronousSocketChannel newClient)
	{
		if(newClient == null)
			throw new NullPointerException("newClient");

		try
		{
			String clientAddress = newClient.getRemoteAddress().toString();

			if(logger.isInfoEnabled())
				logger.info(String.format("New client connected from remote address %s", clientAddress));

			this.lockObject.lock();
			if(!this.mapOfClients.containsKey(clientAddress))
				this.mapOfClients.put(clientAddress, newClient);
			this.lockObject.unlock();
		}
		catch(Exception ex)
		{
			if(logger.isErrorEnabled())
				logger.error(String.format("Exception raised:%s", ex));
		}
	}

	/**
	 * Sends a JSON message to all listening clients.
	 *
	 * @param  message						the message to be sent in JSON.
	 * @throws IllegalArgumentException		if message length == 0 || message length > 5120.
	 */
	@Override
	public void sendToClients(String message)
	{
		if((message.length() == 0) || (message.length() > 5120))
			throw new IllegalArgumentException("Message length is either zero or exceeds 5120. Message length: " + message.length());

		this.lockObject.lock();
		for(String address : this.mapOfClients.keySet())
		{
			try
			{
				AsynchronousSocketChannel client = this.mapOfClients.get(address);
				if(!client.isOpen())
				{
					if(logger.isErrorEnabled())
						logger.error(String.format("Client at remote address %s is no longer open!", address));

					this.mapOfClients.remove(address);
				}
				else
				{
					try
					{
						if(logger.isInfoEnabled())
							logger.info(String.format("Sending message [%s] to client at this addres [%s] on thread with name [%s] and id [%d]", message, address, Thread.currentThread().getName(),  Thread.currentThread().getId()));

						client.write(ByteBuffer.wrap(String.format("%04d%s", message.length(), message).getBytes())).get();
					}
					catch(Exception e)
					{
						if(logger.isErrorEnabled())
							logger.error(String.format("Client at remote address %s is no longer open!", address));

						this.mapOfClients.remove(address);
					}
				}
			}
			catch(Exception ex)
			{
				if(logger.isErrorEnabled())
					logger.error(String.format("Exception raised:%s", ex));
			}
		}
		this.lockObject.unlock();
	}

	/**
	 * Shuts down and terminates the socket channel group and unregisters all listening clients.
	 *
	 */
	@Override
	public void dispose()
	{
		this.mapOfClients.clear();
		try
		{
			if(!this.channelGroup.isShutdown())
				this.channelGroup.shutdown();
			if(!this.channelGroup.isTerminated())
				this.channelGroup.shutdownNow();
			this.channelGroup.awaitTermination(10, TimeUnit.SECONDS);
			this.serverSocket.close();
		}
		catch(Exception ex)
		{
			if(logger.isErrorEnabled())
				logger.error(String.format("Exception raised:%s", ex));
		}
	}

	/**
	 * Converts application events of the right type to JSON and sends them to the client via the socket pipe.
	 *
	 * @param  event			the application event that will be converted to JSON.
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent event)
	{
		if(!(event instanceof JsonSerializableEvent))
			return;

		JsonSerializableEvent jsonSerializableEvent = (JsonSerializableEvent) event;

		sendToClients(jsonSerializableEvent.getJson());
	}
}