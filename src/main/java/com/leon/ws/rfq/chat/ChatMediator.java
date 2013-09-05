package com.leon.ws.rfq.chat;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="ChatMediator")
public interface ChatMediator
{
	@WebMethod
	void sendMessage(@WebParam(name="requestForQuoteId") int requestForQuoteId, 
			@WebParam(name="sender") String sender, @WebParam(name="message") String message);
	
	@WebMethod
	ChatMessageListImpl registerParticipant(@WebParam(name="requestForQuoteId") int requestForQuoteId, 
			@WebParam(name="newParticipantName")  String newParticipantName);	
	
	@WebMethod
	boolean isParticipantRegistered(@WebParam(name="requestForQuoteId")  int requestForQuoteId, 
			@WebParam(name="participantName") String participantName);
	
	@WebMethod
	ChatMessageListImpl getChatMessages(@WebParam(name="requestForQuoteId") int requestForQuote,
			@WebParam(name="fromThisSequenceId") int fromThisSequenceId);

	@WebMethod
	void unregisterParticipant(@WebParam(name="requestForQuoteId") int requestForQuote, 
			@WebParam(name="existingParticipantName") String existingParticipantName);
}