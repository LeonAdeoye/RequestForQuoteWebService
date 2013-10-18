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
		public ChatMessageImpl mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			ChatMessageImpl chatMessage = new ChatMessageImpl(rs.getString("owner"), rs.getString("content"),
					rs.getString("createTime"), rs.getInt("requestForQuoteId"),	rs.getInt("sequenceId"));

			return chatMessage;
		}
	}

	private static final String SAVE = "CALL chatMessages_SAVE (?, ?, ?)";
	private static final String GET = "CALL chatMessages_GET (?, ?)";
	private GenericDatabaseCommandExecutor databaseExecutor;

	ChatMessageDaoImpl() {}

	ChatMessageDaoImpl(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	@Override
	public ChatMessage save(int requestForQuoteId, String owner, String content)
	{
		return this.databaseExecutor.<ChatMessageImpl>getSingleResult(SAVE, new ChatMessageParameterizedRowMapper(), requestForQuoteId, owner, content);
	}

	@Override
	public List<ChatMessageImpl> get(int requestForQuoteId, int fromThisSequenceId)
	{
		return this.databaseExecutor.<ChatMessageImpl>getResultSet(GET, new ChatMessageParameterizedRowMapper(), requestForQuoteId, fromThisSequenceId);
	}
}
