package m_mail_app;

import java.sql.*;

import javax.swing.*;

public class Sqlite_Database {
	
	static Connection connection  = null;
	final public static String DB_URL = "jdbc:sqlite:Contact_Data.sqlite";
	final public static String JDBC = "org.sqlite.JDBC";
	
	
	public static void main(String[] args){
		
		dbConnector();
		//update("Gabriele", "Onaityte", "232334", "gabi@gmail.com", "Tramore", "Otilija");
		insert(4, "ddd", "fff", 343, "dddd", "wdddd", "ddd", "der@sdeer.com");
		
	}
	
	public static Connection dbConnector(){
				
		try{
			
			Class.forName(JDBC);
			connection = DriverManager.getConnection(DB_URL);
			JOptionPane.showMessageDialog(null, "Connection Successfull ... Connected To Local DataBase  ");
			return connection;
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param delete_user
	 */
	
	public static void delete(String delete_user){
		   
		   
		   PreparedStatement statement = null;
		   try {
			 
			        final String sql = "DELETE FROM ContactInfo WHERE Name=?";
			        
			        statement = connection.prepareStatement(sql);
			        statement.setString(1, delete_user);
			        int rowsDeleted = statement.executeUpdate();
			      
			     if(rowsDeleted > 0){
			        	JOptionPane.showMessageDialog(null, "User was deleted succssfully!");
			        }
			        
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Access denied!");
			
			}finally{
				//finally block used to close resources
			      try{
			         if(statement!=null){
			            statement.close();
			         }
			      }catch(SQLException se2){
			    	  JOptionPane.showMessageDialog(null, se2);
			      }
			      try{
			         if(connection!=null){
			            connection.close();
			         }
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			}
	   }
	
	
	/**
	 * 
	 * @param userID
	 * @param userName
	 * @param userSurname
	 * @param userPhone
	 * @param userAddress
	 * @param userNic
	 * @param userPassword
	 * @param userEmail
	 */
	
	public static void insert(int userID, String userName, String userSurname, int userPhone, String userAddress, String userNic, String userPassword, String userEmail){
		  
		   Statement statement = null;
		   
		   try{
			    
			   
			   //Create a Statement		   
			     statement = connection.createStatement();
			   
			   //Execute SQL Query		   
			     final String sql = "insert into ContactInfo"
					        +" (ID, Name, Surname, Phone, Address, User, Password, Email)"
					        +" values ('" + userID + "','" + userName + "','" + userSurname + "','" + userPhone + "','" + userAddress + "','" + userNic + "','" + userPassword + "','" + userEmail + "')";
				   statement.executeUpdate(sql);
				   //@Display Message
				   JOptionPane.showMessageDialog(null, "New Contact Created!");
				   
			   
		   }catch(SQLException se){
			   se.getNextException();
		   } 
		   
		   finally{
			   //finally block used to close resources
			      try{
			         if(statement!=null){
			            statement.close();
			         }
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(connection!=null){
			            connection.close();
			         }
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
		   }
	   }
	
	/**
	 * 
	 * @param userID
	 * @param userName
	 * @param userSurname
	 * @param userPhone
	 * @param userAddress
	 * @param userNic
	 * @param userPassword
	 * @param userEmail
	 * @param userId
	 */
	
	public static void update(int userID, String userName, String userSurname, int userPhone, String userAddress, String userNic, String userPassword, String userEmail, String userId){
		   		 
		 PreparedStatement statement = null;
		 
		 try{		    
		      			   
			//@Execute SQL Query		   
		     final String sql = "UPDATE ContactInfo SET ID=?, Name=?, Surname=?, Phone=?, Address=?, User=?, Password=?, Email=? WHERE Name=?";
		     
		     statement = connection.prepareStatement(sql);
		     
		     statement.setLong(1, userID);
		     statement.setString(2, userName);
		     statement.setString(3, userSurname);
		     statement.setLong(4, userPhone);
		     statement.setString(5, userAddress);
		     statement.setString(6, userNic);
		     statement.setString(7, userPassword);
		     statement.setString(8, userEmail);			     
		     statement.setString(9, userId);
			     
			     int rowsUpdated = statement.executeUpdate();
			     
			     if (rowsUpdated > 0) {
			    	 JOptionPane.showMessageDialog(null,"An existing user was updated successfully!" );
			     }		
	
			   
		   }catch(SQLException se){
			   se.getNextException();
		   } 
		   
		 finally{
			   //finally block used to close resources
			      try{
			         if(statement!=null){
			            statement.close();
			         }
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(connection != null){
			            connection.close();
			         }
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
		   }
		 
	   }//end update()
}
