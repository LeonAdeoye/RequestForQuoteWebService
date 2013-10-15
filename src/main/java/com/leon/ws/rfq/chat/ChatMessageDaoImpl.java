package com.leon.ws.rfq.chat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;

public final class ChatMessageDaoImpl implements ChatMessageDao
{
	private class ChatMessageParameterizedRowMapper implements ParameterizedRowMapper<ChatMessageImpl>
	{
		@Override
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
	private GenericDatabaseCommandExecutor<ChatMessageImpl> databaseExecutor;

	ChatMessageDaoImpl()
	{

	}

	ChatMessageDaoImpl(GenericDatabaseCommandExecutor<ChatMessageImpl> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor<ChatMessageImpl> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	@Override
	public ChatMessage save(int requestForQuoteId, String owner, String content)
	{
		return this.databaseExecutor.getSingleResult(SAVE, new ChatMessageParameterizedRowMapper(), requestForQuoteId, owner, content);
	}

	@Override
	public List<ChatMessageImpl> get(int requestForQuoteId, int fromThisSequenceId)
	{
		return this.databaseExecutor.getResultSet(GET, new ChatMessageParameterizedRowMapper(), requestForQuoteId, fromThisSequenceId);
	}
}
