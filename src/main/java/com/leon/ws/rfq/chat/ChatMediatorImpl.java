package com.leon.ws.rfq.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.leon.ws.rfq.events.NewChatMessageEvent;

@WebService(serviceName="ChatMediator", endpointInterface="com.leon.ws.rfq.chat.ChatMediator")
public final class ChatMediatorImpl implements ChatMediator, ApplicationEventPublisherAware
{
	private static final Logger logger = LoggerFactory.getLogger(ChatMediatorImpl.class);
	private final Map<Integer, Set<String>> chatRooms = new HashMap<>();
	private ApplicationEventPublisher applicationEventPublisher;
	private final ChatMessageDao dao;

	public ChatMediatorImpl(ChatMessageDao dao)
	{
		if(dao == null)
			throw new NullPointerException("dao");

		this.dao = dao;
	}

	@Override
	@WebMethod
	@Oneway
	public void sendMessage(int requestForQuoteId, String owner, String content)
	{
		if(owner.isEmpty())
			throw new IllegalArgumentException("owner");

		if(content.isEmpty())
			throw new IllegalArgumentException("content");

		if(isParticipantRegistered(requestForQuoteId, owner))
		{
			ChatMessage newlySavedMessage = this.dao.save(requestForQuoteId, owner, content);

			if(newlySavedMessage != null)
				this.applicationEventPublisher.publishEvent(new NewChatMessageEvent(this, newlySavedMessage));
		}
		else
		{
			if(logger.isErrorEnabled())
				logger.error("Participant: {} in chatroom: {} is NOT registered. Message undelivered.", requestForQuoteId, owner);
		}

	}

	@Override
	@WebMethod
	public ChatMessageListImpl registerParticipant(int requestForQuoteId, String newParticipantName)
	{
		if(newParticipantName.isEmpty())
			throw new IllegalArgumentException("newParticipantName");

		if(this.chatRooms.containsKey(requestForQuoteId))
		{
			Set<String> participants = this.chatRooms.get(requestForQuoteId);
			if(!participants.contains(newParticipantName))
				participants.add(newParticipantName);

			if(logger.isInfoEnabled())
				logger.info("New participant: {} added to chatroom with requestForQuote Id: {}.", newParticipantName, requestForQuoteId);
		}
		else
		{
			Set<String> participants = new HashSet<String>();
			participants.add(newParticipantName);
			this.chatRooms.put(requestForQuoteId, participants);

			if(logger.isInfoEnabled())
				logger.info("Chatroom opened for requestForQuote Id: {} and participant: {}.", requestForQuoteId, newParticipantName);
		}

		int fromStartingSequenceId = 0;
		return getChatMessages(requestForQuoteId, fromStartingSequenceId);
	}

	@Override
	@WebMethod
	public boolean isParticipantRegistered(int requestForQuoteId, String participantName)
	{
		if(participantName.isEmpty())
			throw new IllegalArgumentException("participantName");

		if(this.chatRooms.containsKey(requestForQuoteId))
		{
			Set<String> participants = this.chatRooms.get(requestForQuoteId);
			return participants.contains(participantName);
		}
		return false;
	}

	@Override
	@WebMethod
	public ChatMessageListImpl getChatMessages(int requestForQuoteId, int fromThisSequenceId)
	{
		ChatMessageListImpl result = new ChatMessageListImpl();
		ArrayList<ChatMessageImpl> chatMessageArrayList = new ArrayList<ChatMessageImpl>();
		for(ChatMessageImpl chatMessage : this.dao.get(requestForQuoteId, fromThisSequenceId))
			chatMessageArrayList.add(chatMessage);
		result.setChatMessageList(chatMessageArrayList);
		return result;
	}

	@Override
	@WebMethod
	public void unregisterParticipant(int requestForQuoteId, String existingParticipantName)
	{
		if(existingParticipantName.isEmpty())
			throw new IllegalArgumentException("existingParticipantName");

		if(this.chatRooms.containsKey(requestForQuoteId))
		{
			Set<String> participants = this.chatRooms.get(requestForQuoteId);
			if(participants.contains(existingParticipantName))
				participants.remove(existingParticipantName);
			else
			{
				if(logger.isErrorEnabled())
					logger.error("Participant: {} is NOT registered with the chatroom with requestForQuote Id: {}.", existingParticipantName, requestForQuoteId);
			}

		}
		else
		{
			if(logger.isErrorEnabled())
				logger.error("Chatroom with requestForQuote Id: {} does NOT exist.", requestForQuoteId);
		}
	}

	public int countOfChatRooms()
	{
		return this.chatRooms.size();
	}

	public int countOfParticipantsInChatRoom(int requestForQuoteId)
	{
		if(this.chatRooms.containsKey(requestForQuoteId))
		{
			Set<String> participants = this.chatRooms.get(requestForQuoteId);
			return participants.size();
		}
		else
		{
			if(logger.isErrorEnabled())
				logger.error("Chatroom with requestForQuote Id: {} does NOT exist.", requestForQuoteId);
		}


		return -1;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<Integer, Set<String>> chatroom : this.chatRooms.entrySet())
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

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		if(applicationEventPublisher == null)
			throw new NullPointerException("applicationEventPublisher");

		this.applicationEventPublisher = applicationEventPublisher;
	}
}
