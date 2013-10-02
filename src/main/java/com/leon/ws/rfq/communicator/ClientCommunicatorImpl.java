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

public class ClientCommunicatorImpl extends Thread implements ClientCommunicator
{
	private static Logger logger = LoggerFactory.getLogger(ClientCommunicatorImpl.class);
	private AsynchronousChannelGroup channelGroup;
	private AsynchronousServerSocketChannel serverSocket;
	private Lock lockObject = new ReentrantLock(true); // with fair ordering policy
	private Map<String, AsynchronousSocketChannel> mapOfClients =  new ConcurrentSkipListMap<>();
	private int portNumber;
	private String ipAddress;
	
	public ClientCommunicatorImpl(String ipAddress, int portNumber)
	{
		this.portNumber = portNumber;
		this.ipAddress = ipAddress;
	}
	
	public boolean initialize()
	{
		try
		{
			channelGroup = AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), 10);
			serverSocket = AsynchronousServerSocketChannel.open(channelGroup);
			
			if(serverSocket.isOpen())
			{
				serverSocket.bind(new InetSocketAddress(ipAddress, portNumber));
				
				if(logger.isInfoEnabled())
					logger.info(String.format("server socket has been opened and is bound to %s using port %d", ipAddress, portNumber));
				
				// Sleep to allow the binding to complete
				sleep(2000);				
				this.start();				
				return true;				
			}
			else
				logger.error("server socket could not be opened!");			
		}
		catch(Exception ex)
		{
			logger.error(String.format("Exception raised:%s", ex));	
		}
		return false;
	}
	
	@Override
	public void run()
	{
		if(logger.isInfoEnabled())
			logger.info(String.format("Waiting for new clients on thread with name [%s] and id [%d]", 
					Thread.currentThread().getName(), Thread.currentThread().getId()));
		
		while(true)
		{
			try
			{
				Future<AsynchronousSocketChannel> future = serverSocket.accept();		                           
				saveClientSocketChannel(future.get());
			}
			catch(java.util.concurrent.ExecutionException ex)
			{
				logger.error(String.format("ExecutionException raised:%s. Exiting run loop!", ex));
				break;
			}
			catch(Exception ex)
			{
				logger.error(String.format("Exception raised:%s", ex));
			}			
		}
	}
	
	private void saveClientSocketChannel(AsynchronousSocketChannel newClient)
	{
		try
		{
			String clientAddress = newClient.getRemoteAddress().toString();
			
			if(logger.isInfoEnabled())
				logger.info(String.format("New client connected from remote address %s", clientAddress));
			
			lockObject.lock();
			if(!mapOfClients.containsKey(clientAddress))
				mapOfClients.put(clientAddress, newClient);
			lockObject.unlock();
		}
		catch(Exception ex)
		{
			logger.error(String.format("Exception raised:%s", ex));
		}			
	}
	
	public void sendToClients(String message)
	{
		
		lockObject.lock();
		for(String address : mapOfClients.keySet())
		{
			try
			{
				AsynchronousSocketChannel client = mapOfClients.get(address);
				if(!client.isOpen())
				{
					logger.error(String.format("Client at remote address %s is no longer open!", address));
					mapOfClients.remove(address);
				}
				else
				{
					try
					{
						if(logger.isInfoEnabled())
							logger.info(String.format("Sending message [%s] to client at this addres [%s] on thread with name [%s] and id [%d]", message, address, Thread.currentThread().getName(),  Thread.currentThread().getId()));
						
						client.write(ByteBuffer.wrap(String.format("%03d%s", message.length(), message).getBytes())).get();
					}
					catch(Exception e)
					{
						logger.error(String.format("Client at remote address %s is no longer open!", address));
						mapOfClients.remove(address);						
					}
				}
			}
			catch(Exception ex)
			{
				logger.error(String.format("Exception raised:%s", ex));
			}
		}
		lockObject.unlock();
	}
	
	public void dispose()
	{
		mapOfClients.clear();
		try
		{
			if(!channelGroup.isShutdown())
				channelGroup.shutdown();
			if(!channelGroup.isTerminated())
				channelGroup.shutdownNow();			
			channelGroup.awaitTermination(10, TimeUnit.SECONDS);		
			serverSocket.close();
		}
		catch(Exception ex)
		{
			logger.error(String.format("Exception raised:%s", ex));				
		}
	}	
}