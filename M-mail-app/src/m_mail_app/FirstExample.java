package m_mail_app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.simple.JSONObject;



public class FirstExample {
	
	/*
	 * @Global Variables
	 */
	public static boolean check_connection = false;
	
   // JDBC driver name and database URL
   static Connection connection  = null;
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/phplogin";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "";
   
   
   //@Database variables
	 public  static String name ;
	 public  static String surname;
	 public  static int number;
	 public  static String address; 
	 public  static String email;
   
	 public static void showMessageDialog(String message){		
 		//@show message	
 		JFrame frame = new JFrame("JOptionPane showMessageDialog example");
 		JOptionPane.showMessageDialog(frame, message);
 	}
	 
	 public static void main(String[] args){
		   //connect("jdbc:mysql://localhost/phplogin","root","","Deividas");
		   
		 //update("root", "", "Sexy Beast","SEXY BITCH", "232334", "sexy@gmail.com","Kaunas Rokakiemio 10","trish");
		 //insert("root", "", "Johnas", "Jamies", "9283723", "Waterford Cork Rd 300", "jamie@gmail.com");
		 //insert("root", "",2, "Johnas", "Jamies", 86023232, "Waterford Cork Rd 300", "john888", "password","jamie@gmail.com");
		
	   }
	 
	 /**
	  * 
	  * @param DB_USER
	  * @param DB_PASS
	  * @return
	  * 
	  * @Method performs connection validation function
	  */
	 
	 public static Connection dbConnector(String DB_USER, String DB_PASS){
			
			try{
				
				Class.forName(JDBC_DRIVER);
				connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
				return connection;
				
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Can not establish connection to database!");
				return null;
			}finally{
				try{
					if(connection != null){
						connection.close();
						check_connection = true;
						}
					}catch(SQLException ex){
						JOptionPane.showMessageDialog(null, ex);
					}
				}
			}
	 
	 /**
	  * 
	  * @param DB_USER
	  * @param DB_PASS
	  * @return
	  */
	 
	 final static Connection SQLConnector(String DB_USER, String DB_PASS){
		 
		 try{			 
			    Class.forName(JDBC_DRIVER);
				connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
				return connection;
				
		 }catch(Exception e){
			 e.getMessage();
			 return null;
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
		   
		 //Connection connection = null;
		 PreparedStatement statement = null;
		 
		 try{
			 			
			   //@Execute SQL Query		   
			     final String sql = "UPDATE contacts SET ID=?, Name=?, Surname=?, Phone=?, Address=?, User=?, Password=?, Email=? WHERE Name=?";
			     
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
			    	 showMessageDialog("An existing user was updated successfully!");
			        // System.out.println("An existing user was updated successfully!");
			     }		
	
			   
		   }catch(SQLException se){
			   se.getNextException();
		   } finally{
			   //finally block used to close resources
			      try{
			         if(statement!=null){
			            statement.close();
			         }
			      }catch(SQLException se2){
			      }
			      try{
			         if(connection != null){
			            connection.close();
			         }
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
		   }
		 
	   }
	   
   /**
    * 
    * @param delete_user
    */
	 
   public static void delete(String delete_user){
	   
	   PreparedStatement statement = null;
	   try {
	
		        final String sql = "DELETE FROM contacts WHERE Name=?";
		        
		        statement = connection.prepareStatement(sql);
		        statement.setString(1, delete_user);
		        int rowsDeleted = statement.executeUpdate();
		      
		     if(rowsDeleted > 0){
		        	showMessageDialog("User was deleted succssfully!");
		        }
		        
		} catch (SQLException e) {
			showMessageDialog("Access denied for user 'root'@'localhost'");
			        e.printStackTrace();
		} finally{
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

   public static void insert(int userID, String userName, String userSurname, int userPhone, String userAddress, String userNic, String userPassword,  String userEmail){
	  
	   Statement statement = null;
	   
	   try{
		   
		   //Create a Statement		   
		     statement = connection.createStatement();
		   
		   //Execute SQL Query		   
		     final String sql = "insert into contacts"
				        +" (ID, Name, Surname, Phone, Address, User, Password, Email)"
				        +" values ('" + userID + "','" + userName + "','" + userSurname + "','" + userPhone + "','" + userAddress + "','" + userNic + "','" + userPassword + "','" + userEmail + "')";
			   statement.executeUpdate(sql);
			   //@Display Message
			   showMessageDialog("New Contact Created!");
			   //connection.close();
		   
	   }catch(SQLException se){
		   JOptionPane.showMessageDialog(null, "Can not establish connection to database!");
	   }finally{
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
		    	  JOptionPane.showMessageDialog(null, "Can not establish connection to database!");
		      }//end finally try
	   }
   }
   
   /**
    * 
    * @param search
    */
   public static void search(String search) {
   
       Statement stmt = null;
   
   try{
   
      //@Execute a query
		      System.out.println("Creating statement...");
		      stmt = connection.createStatement();
		      final String sql = "SELECT Name, Surname, Phone, Address, Email FROM contacts";
		      ResultSet rs = stmt.executeQuery(sql);

      //@Extract data from result set and write into a file
		   		      
		      File file = new File("search.ser");
	            if(!file.exists()){
	            	file.createNewFile();
	            }	            
	           
	         BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	            
      while(rs.next()){
         //@Retrieve by column name
	         name  = rs.getString("Name");
	         surname = rs.getString("Surname");
	         number = rs.getInt("Phone");
	         address = rs.getString("Address");
	         email = rs.getString("Email");
	         	    
         //@Convert integer to String
            String phone_number = Integer.toString(number); 
            
         //Display values and write into a file                  
	        if (name.equals(search) || surname.equals(search) || email.equals(search) || phone_number.equals(search)){	 	        	
		         bw.write("\n\nName: " + name + "\nSurname: " + surname + "\nNumber: " + phone_number + "\nAddress: " + address + "\nEmail: " + email);		       
              }//end if 	   
        }//end while loop
        
     //@Close BufferedReader 
	      bw.flush();
	      bw.close();
	 
	                   
      //@Clean-up environment
	      rs.close();
	      stmt.close();
	    	      
	   }catch(SQLException se){
	       //se.printStackTrace();
		   showMessageDialog("Access denied");
		   
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	      showMessageDialog("User name not right!");
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(connection!=null)
	            connection.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Goodbye!");
   }//end search(String url,String user, String pass, String search) 
}//end FirstExample