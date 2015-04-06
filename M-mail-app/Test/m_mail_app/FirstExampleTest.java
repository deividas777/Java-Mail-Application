package m_mail_app;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class FirstExampleTest {
	
	//Setup
			public String user = "root";
			public String password = "";	

	@Test
	public void test() {
	
	//@Setup
		
	//@Execute
		FirstExample.dbConnector(user, password);
	//@Assert
		boolean valid = true;
		assertEquals(valid, FirstExample.check_connection);
		valid = false;
		assertNotSame(valid, FirstExample.check_connection);
		
	}
	
	@Test	 
	public void connectDB() throws SQLException{					
		//Execute 
		FirstExample.SQLConnector(user, password);		
		//Assert
		Connection connection = FirstExample.connection;		
		assertTrue(connection != null);		
		//Close connection
		connection.close();
		assertFalse(connection == null);
	}
	
	@Test
	public void insert(){
		
		int id = 100;
		String name = "Saule";
		String surname = "Kaliause";
		int phone = 233434;
		String address = "Tramore";
		String user_name = "saule";
		String user_password = "password";
		String email = "saule@gmail.com";
		
		FirstExample.SQLConnector(user, password);	
		FirstExample.insert(id, name, surname, phone, address, user_name, user_password, email);
		
	}
	
	
	@Test
	public void search() throws IOException{
		
		FirstExample.SQLConnector(user, password);
		String name = "dse";
	//Search in DB
		FirstExample.search(name);
		BufferedReader  br = new BufferedReader(new FileReader("search.ser"));
		
		String line;
		String result = "";
		while((line = br.readLine()) != null){
			result += line;
			System.out.println(result);
		}
		br.close();
		
		
		//Check result
		 assertNotSame(name, result);
		 assertNotNull(result);
		 result = null;
		 assertNull(result);
		
		 //Check file size
		 File file = new File("search.ser");
		 int size = 0;
		 assertEquals(size, file.length());
		 
		 //Check Connection
		 Connection connection = null;
		 assertNotSame(connection, FirstExample.check_connection);
		
		
	}
	

}
