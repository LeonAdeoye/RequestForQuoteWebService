package com.leon.ws.rfq.communicator;

public interface ClientCommunicator
{
	void sendToClients(String message);
	boolean initialize();
	void dispose();
}
