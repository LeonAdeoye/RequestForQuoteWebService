package com.leon.ws.rfq.communicator;

public interface ClientCommunicator
{
	public void sendToClients(String message);
	public boolean initialize();
	public void dispose();
}
