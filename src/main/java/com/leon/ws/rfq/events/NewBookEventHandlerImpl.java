package com.leon.ws.rfq.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.leon.ws.rfq.communicator.ClientCommunicator;

public final class NewBookEventHandlerImpl implements ApplicationListener
{
	private static Logger logger = LoggerFactory.getLogger(NewBookEventHandlerImpl.class);
	private ClientCommunicator communicator = null;
	
	public NewBookEventHandlerImpl(ClientCommunicator communicator)
	{
		this.communicator = communicator;
	}
	
	@Override
	public void onApplicationEvent(ApplicationEvent event)
	{
		if(!(event instanceof NewBookEvent))
			return;
		
		NewBookEvent newBookEvent = (NewBookEvent) event;
		
		if(logger.isDebugEnabled())
			logger.debug("New book event recieved: " + newBookEvent.getNewBook().toString());
				
		communicator.sendToClients(newBookEvent.getNewBook().toJson());
	}

}
