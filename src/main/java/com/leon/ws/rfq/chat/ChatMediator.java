package com.leon.ws.rfq.chat;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="ChatMediator")
public interface ChatMediator
{
	@WebMethod
	public void sendMessage(@WebParam(name="requestForQuoteId") int requestForQuoteId, 
			@WebParam(name="sender") String sender, @WebParam(name="message") String message);
	
	@WebMethod
	public ChatMessageListImpl registerParticipant(@WebParam(name="requestForQuoteId") int requestForQuoteId, 
			@WebParam(name="newParticipantName")  String newParticipantName);	
	
	@WebMethod
	public boolean isParticipantRegistered(@WebParam(name="requestForQuoteId")  int requestForQuoteId, 
			@WebParam(name="participantName") String participantName);
	
	@WebMethod
	public ChatMessageListImpl getChatMessages(@WebParam(name="requestForQuoteId") int requestForQuote,
			@WebParam(name="fromThisSequenceId") int fromThisSequenceId);

	@WebMethod
	public void unregisterParticipant(@WebParam(name="requestForQuoteId") int requestForQuote, 
			@WebParam(name="existingParticipantName") String existingParticipantName);
}