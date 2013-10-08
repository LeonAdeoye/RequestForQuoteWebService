package com.leon.ws.rfq.chat;

import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leon.ws.rfq.communicator.ClientCommunicator;

@WebService(serviceName="ChatMediator", endpointInterface="com.leon.ws.rfq.chat.ChatMediator")
public class ChatMediatorImpl implements ChatMediator
{
	private static final Logger logger = LoggerFactory.getLogger(ChatMediatorImpl.class);	
	private Map<Integer, Set<String>> chatRooms = new HashMap<>();
	private static final String NEW_CHAT_MESSAGE = "NewChatMessage";
	private final ClientCommunicator communicator;	
	private ChatMessageDao dao;
	
	public ChatMediatorImpl(ClientCommunicator communicator, ChatMessageDao dao)
	{
		this.communicator = communicator;
		this.dao = dao;
	}	
	
	@WebMethod
	@Oneway	
	public void sendMessage(int requestForQuoteId, String owner, String content)
	{
		if(isParticipantRegistered(requestForQuoteId, owner))
		{
			ChatMessage newlySavedMessage = dao.save(requestForQuoteId, owner, content);
			if(newlySavedMessage != null)
				communicator.sendToClients(String.format("%s=%s", NEW_CHAT_MESSAGE, newlySavedMessage));			
		}			
		else
			logger.error("Participant: {} in chatroom: {} is NOT registered. Message undelivered.", requestForQuoteId, owner);
	}

	@WebMethod
	public ChatMessageListImpl registerParticipant(int requestForQuoteId, String newParticipantName)
	{
		if(chatRooms.containsKey(requestForQuoteId))
		{
			if(logger.isInfoEnabled())
				logger.info("New participant: {} added to chatroom with requestForQuote Id: {}.", newParticipantName, requestForQuoteId);
			
			Set<String> participants = chatRooms.get(requestForQuoteId);
			if(!participants.contains(newParticipantName))
				participants.add(newParticipantName);		
		}
		else
		{
			if(logger.isInfoEnabled())
				logger.info("Chatroom opened for requestForQuote Id: {} and participant: {}.", requestForQuoteId, newParticipantName);
			
			Set<String> participants = new HashSet<String>();
			participants.add(newParticipantName);
			chatRooms.put(requestForQuoteId, participants);	
		}
		
		int fromStartingSequenceId = 0;
		return getChatMessages(requestForQuoteId, fromStartingSequenceId);			
	}
	
	@WebMethod
	public boolean isParticipantRegistered(int requestForQuoteId, String participantName)
	{
		if(chatRooms.containsKey(requestForQuoteId))
		{
			Set<String> participants = chatRooms.get(requestForQuoteId);
			return participants.contains(participantName);
		}
		return false;
	}
	
	@WebMethod
	public ChatMessageListImpl getChatMessages(int requestForQuoteId, int fromThisSequenceId)
	{	
		ChatMessageListImpl result = new ChatMessageListImpl();
		ArrayList<ChatMessageImpl> chatMessageArrayList = new ArrayList<ChatMessageImpl>();
		for(ChatMessageImpl chatMessage : dao.get(requestForQuoteId, fromThisSequenceId))
			chatMessageArrayList.add(chatMessage);	
		result.setChatMessageList(chatMessageArrayList);
		return result;
	}
	
	@WebMethod	
	public void unregisterParticipant(int requestForQuoteId, String existingParticipantName)
	{
		if(chatRooms.containsKey(requestForQuoteId))
		{
			Set<String> participants = chatRooms.get(requestForQuoteId);
			if(participants.contains(existingParticipantName))
				participants.remove(existingParticipantName);
			else
				logger.error("Participant: {} is NOT registered with the chatroom with requestForQuote Id: {}.", existingParticipantName, requestForQuoteId);							
		}
		else
			logger.error("Chatroom with requestForQuote Id: {} does NOT exist.", requestForQuoteId);			
	}
	
	public int countOfChatRooms()
	{
		return chatRooms.size();
	}
	
	public int countOfParticipantsInChatRoom(int requestForQuoteId)
	{
		if(chatRooms.containsKey(requestForQuoteId))
		{
			Set<String> participants = chatRooms.get(requestForQuoteId);
			return participants.size();
		}
		else
			logger.error("Chatroom with requestForQuote Id: {} does NOT exist.", requestForQuoteId);
		
		return -1;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		for(Map.Entry<Integer, Set<String>> chatroom : chatRooms.entrySet())
		{
			sb.append("\n[ Chatroom for requestforQuoteId=");
			sb.append(chatroom.getKey());			
			for(String participant : chatroom.getValue())
			{
				sb.append("\nParticipant=");
				sb.append(participant);
			}			
			sb.append(" ]");			
		}
		return sb.toString();
	}
}
