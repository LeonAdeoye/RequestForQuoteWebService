package com.leon.ws.rfq.chat;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "com.leon.ws.rfq.chat")
public class ChatMessageListImpl
{
	@XmlElementWrapper(name = "ChatMessageListImpl")
	@XmlElement(name = "ChatMessageImpl")
	  
	private ArrayList<ChatMessageImpl> chatMessageArrayList;
	
	public ChatMessageListImpl(ArrayList<ChatMessageImpl> chatMessages)
	{
		this.chatMessageArrayList = chatMessages;
	}
	
	public ChatMessageListImpl() {}
	  
	public void setChatMessageList(ArrayList<ChatMessageImpl> chatMessages) 
	{
		this.chatMessageArrayList = chatMessages;
	}
	
	public ArrayList<ChatMessageImpl> getChatMessageList() 
	{
		return this.chatMessageArrayList;
	}	  
}