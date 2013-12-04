import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.leon.ws.rfq.book.BookDetailImpl;
import com.leon.ws.rfq.book.BookManagerDao;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class BookDaoTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private BookManagerDao bookDao;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	private TransactionStatus status;
	
	public BookDaoTest() {}

	@Test
	public void test_save_AddValidTestBook_TotalCountOfBooksIncrementedByOne()
	{
		// Arrange
		List<BookDetailImpl> beforeSave = this.bookDao.getAll();
				
		// Act
		this.bookDao.save("TEST", "TEST", "testuser");
		List<BookDetailImpl> afterSave = this.bookDao.getAll();
		// Assert
		assertEquals("Test book not saved with bookcode TEST", beforeSave.size() + 1, afterSave.size());
	}
	
	@Test
	public void test_getAll_AddValidTestBook_ReturnsAllBooksIncludingRecentlyAddedTestBook()
	{
		// Arrange
		List<BookDetailImpl> before = this.bookDao.getAll();
		this.bookDao.save("TEST", "TEST", "testuser");
		
		// Act
		List<BookDetailImpl> after = this.bookDao.getAll();
		
		// Assert
		assertEquals("getAll does not return saved books", before.size() + 1, after.size());
	}
	
	@Test
	public void test_delete_AddAndDeleteValidTestBook_DeletesNewlyAddedBook()
	{
		// Arrange
		List<BookDetailImpl> beforeSave = this.bookDao.getAll();
		this.bookDao.save("TEST", "TEST", "testuser");
		
		// Act
		this.bookDao.delete("TEST");
		List<BookDetailImpl> afterDelete = this.bookDao.getAll();
		
		// Assert
		assertEquals("delete method does not remove newly saved book", beforeSave.size(), afterDelete.size());
	}
	
	@Test
	public void test_get_AddAndRetrieveValidTestBook_RetrievesNewlyAddedBook()
	{
		// Arrange
		this.bookDao.save("TEST", "TEST", "testuser");
		
		// Act
		BookDetailImpl newBook = this.bookDao.get("TEST");
		
		// Assert
		assertNotNull("get method does not retrieve newly saved book", newBook);
	}
	
	@Test
	public void test_updateValidity_AddThenUpdateValidityAndRetrieveValidTestBook_UpdatesNewlyAddedBook()
	{
		// Arrange
		this.bookDao.save("TEST", "TEST", "testuser");
		BookDetailImpl bookAfterSave = this.bookDao.get("TEST");
		
		// Act
		this.bookDao.updateValidity("TEST", false);
		BookDetailImpl bookAfterUpdate = this.bookDao.get("TEST");
		
		// Assert
		assertNotEquals("updateValidity method does not update validity of book",
				bookAfterSave.getIsValid(), bookAfterUpdate.getIsValid());
	}
	
	@Before
	public void setUp()
	{
		assertNotNull("autowired book dao should not be null", this.bookDao);
		assertNotNull("autowired transaction manager should not be null", this.transactionManager);
		
		TransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
		this.status=this.transactionManager.getTransaction(paramTransactionDefinition);
	}
	
	@After
	public void tearDown()
	{
		this.transactionManager.rollback(this.status);
	}
}
