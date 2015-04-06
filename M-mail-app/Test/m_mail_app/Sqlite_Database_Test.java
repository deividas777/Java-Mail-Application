package m_mail_app;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.mysql.jdbc.Connection;

public class Sqlite_Database_Test {

	@Test 
	public void test() {
		Connection connection = null;
		Sqlite_Database.dbConnector();
		assertNotSame(connection, Sqlite_Database.connection);
	}
	
	@Test
	public void test_search(){
		String name = "fff";
		Sqlite_Database.search(name);
		
		File file = new File("sqlite_search.ser");
		assertTrue(file.length() > 0);
		boolean exist = file.exists();
		assertTrue(exist);
		
		name = "scddcdcec";
		Sqlite_Database.dbConnector();
		Sqlite_Database.search(name);
		assertFalse(file.length() > 1);
		
	}
		
	@Test 	
	public void test_create(){
		
		//@create contact
		Sqlite_Database.dbConnector();
		Sqlite_Database.insert(102, "Tomass", "Kupryss", 3536577, "Kaunas", "tom", "pasword", "toms@gmail.com");
		//@search contact
		Sqlite_Database.dbConnector();
	    Sqlite_Database.search("Tomas");
	   //@check search file size
	    File file = new File("sqlite_search.ser");
	    assertTrue(file.length() > 0);
	}
	
	@Test
	public void test_update(){
		//@connect
		Sqlite_Database.dbConnector();
		//@update
		Sqlite_Database.update(102, "Aliosa", "Kuprys", 345321, "Kaunas", "aliosa", "password", "aliosa@gmail.com", "Tomass");
	}
	
	@Test
	public void test_delete(){
		
		//@connect
				Sqlite_Database.dbConnector();
	   //@delete
				Sqlite_Database.delete("Aliosa");
	}

}
