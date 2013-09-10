package com.leon.ws.rfq.chat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

 
public class ChatMessageDaoJdbcImpl extends SimpleJdbcDaoSupport implements ChatMessageDao
{
	private class ChatMessageParameterizedRowMapper implements ParameterizedRowMapper<ChatMessageImpl>
	{
		public ChatMessageImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
			ChatMessageImpl chatMessage = new ChatMessageImpl();
			chatMessage.setOwner(rs.getString("owner"));
			chatMessage.setContent(rs.getString("content"));
			chatMessage.setRequestForQuoteId(rs.getInt("requestForQuoteId"));
			chatMessage.setSequenceId(rs.getInt("sequenceId"));
			chatMessage.setTimeStamp(rs.getString("createTime"));
			return chatMessage;
		}		
	}	
	
	private static final String SAVE = "CALL chatMessages_SAVE (?, ?, ?)";
	private static final String GET = "CALL chatMessages_GET (?, ?)";
	private static Logger logger = LoggerFactory.getLogger(ChatMessageDaoJdbcImpl.class);
	
	public ChatMessage save(int requestForQuoteId, String owner, String content)
	{
		try
		{
			if(logger.isDebugEnabled())
				logger.debug(String.format("Executing stored procedure [%s] with parameters requesteForQuoteId [%d], owner [%s], and content [%s]", SAVE, requestForQuoteId, owner, content));
					
			ChatMessageImpl chatMessage = getSimpleJdbcTemplate().queryForObject(
					SAVE,  new ChatMessageParameterizedRowMapper(), requestForQuoteId, owner, content);
		 
			return chatMessage;				
		}
		
		catch(Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
			return null;			
		}
	}

	public List<ChatMessageImpl> get(int requestForQuoteId, int fromThisSequenceId)
	{
		try
		{
			if(logger.isDebugEnabled())
				logger.debug(String.format("Executing stored procedure [%s] with parameters requesteForQuoteId [%d] and sequenceId [%d]", GET, requestForQuoteId, fromThisSequenceId));
					
			List<ChatMessageImpl> chatMessages = getSimpleJdbcTemplate().query(GET, 
			ParameterizedBeanPropertyRowMapper.newInstance(ChatMessageImpl.class), requestForQuoteId, fromThisSequenceId);
			
			return chatMessages;
		}
		
		catch(Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
			return new LinkedList<ChatMessageImpl>();			
		}	
	}
}
