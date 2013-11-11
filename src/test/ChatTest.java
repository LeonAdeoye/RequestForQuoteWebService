import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.leon.ws.rfq.chat.ChatMediator;

public class ChatTest extends TestCase
{
	private static final Logger logger = LoggerFactory.getLogger(ChatTest.class);
	private ChatMediator chatMediator;

	public ChatTest(String name)
	{
		super(name);
		initializeBean();
	}

	private void initializeBean()
	{
		try
		{
			ApplicationContext context = new FileSystemXmlApplicationContext(".\\src\\main\\webapp\\WEB-INF\\cxf-servlet.xml");
			this.chatMediator = (ChatMediator) context.getBean("chatMediator");

			if(logger.isDebugEnabled())
				logger.debug("Successfully wired bean chat mediator from file system application context.");
		}
		catch(BeansException be)
		{
			logger.error("Failed to load application context for chat mediator!", be);
		}
	}

	@Override
	@Before
	public void setUp()
	{
	}

	@Test
	public void test_sendMessage_RegisteredAndwithValidOwnerAndContent_messageSavedToDatabase()
	{
		int previousCount = this.chatMediator.registerParticipant(99999, "TESTER").getChatMessageList().size();
		this.chatMediator.sendMessage(99999, "TESTER", "Test message");
		int latestCount = this.chatMediator.getChatMessages(99999, 0).getChatMessageList().size();
		assertEquals("Test chat message not saved for requestForQuoueId = 99999", previousCount+1, latestCount);
	}

	@Test
	public void test_sendMessage_UnregisteredAndwithValidOwnerAndContent_messageSavedToDatabase()
	{
		int previousCount = this.chatMediator.getChatMessages(99999, 0).getChatMessageList().size();
		this.chatMediator.sendMessage(99999, "TESTER", "Test message");
		int latestCount = this.chatMediator.getChatMessages(99999, 0).getChatMessageList().size();
		assertEquals("Test chat message not saved for requestForQuoueId = 99999", previousCount, latestCount);
	}

	@Override
	@After
	public void tearDown()
	{

	}
}