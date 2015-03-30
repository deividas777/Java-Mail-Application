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
		 insert("root", "",2, "Johnas", "Jamies", 86023232, "Waterford Cork Rd 300", "john888", "password","jamie@gmail.com");
		
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
				JOptionPane.showMessageDialog(null, "Connection Successfull ... Connected To Local DataBase  ");
				return connection;
				
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e);
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
	 
	 public static void update(String DB_USER, String DB_PASS,int userID, String userName, String userSurname, int userPhone, String userAddress, String userNic, String userPassword, String userEmail, String userId){
		   
		 //Connection connection = null;
		 PreparedStatement statement = null;
		 
		 try{
			 
			//STEP 2: Register JDBC driver
		      Class.forName(JDBC_DRIVER);
		      
			   //@Get a Connection To Database
			     connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
			
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
		   } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		 finally{
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
    * @param user
    * @param pass
    * @param delete_user
    * 
    * @Methid Deletes user from database
    */
	 
   public static void delete(String user, String pass, String delete_user){
	   
	   Connection connection = null;
	   PreparedStatement statement = null;
	   try {
		 //STEP 2: Register JDBC driver
		      Class.forName(JDBC_DRIVER);
		      
		        connection = DriverManager.getConnection(DB_URL,user,pass);
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    * @param user
    * @param pass
    * @param userID
    * @param userName
    * @param userSurname
    * @param userPhone
    * @param userAddress
    * @param userNic
    * @param userPassword
    * @param userEmail
    * 
    * @Method creates new record in DB
    */

   public static void insert(String user, String pass, long userID, String userName, String userSurname, long userPhone, String userAddress, String userNic, String userPassword,  String userEmail){
	  
	   Connection connection = null;
	   Statement statement = null;
	   
	   try{
		    //STEP 2: Register JDBC driver
		      Class.forName(JDBC_DRIVER);
		   //Get a Connection To Database
		     connection = DriverManager.getConnection(DB_URL,user,pass);
		   
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
		   se.getNextException();
	   } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
    * @param url
    * @param user
    * @param pass
    * @param search
    * 
    * @Method searches for record in DB 
    */
   public static void search(String user, String pass, String search) {
   
	   Connection conn = null;
       Statement stmt = null;
   
   try{
      //STEP 2: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 3: Open a connection
      
      System.out.println("Connecting to database...");
      
     // conn = DriverManager.getConnection(DB_URL,USER,PASS);
      
      conn = DriverManager.getConnection(DB_URL,user,pass);
     
      //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      final String sql = "SELECT Name, Surname, Phone, Address, Email FROM contacts";
		      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set and write into a file
		   
		      
		      File file = new File("search.ser");
	            if(!file.exists()){
	            	file.createNewFile();
	            }	            
	           
	         BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	         
	       //@Contain two copies of contacts in json format for future functionality   
	         
	         FileWriter fw = new FileWriter("contacts.json");
	         FileWriter fw1 = new FileWriter("tmp.json");
	         
	     //@Compare Files if different update ==> (contacts.json)    
	       boolean hash = HashFunction.compareFileHashes(new File("contacts.json"), new File("tmp.json"));
	         
	       //@JSON 
	         JSONObject obj = new JSONObject();
	            
      while(rs.next()){
         //Retrieve by column name
	         name  = rs.getString("Name");
	         surname = rs.getString("Surname");
	         number = rs.getInt("Phone");
	         address = rs.getString("Address");
	         email = rs.getString("Email");
	         
    	if(hash == false){
    		
	    	 obj.put("Name", name);
	         obj.put("Surname", surname);
	         obj.put("Phone", number);
	         obj.put("Address", address);
	         obj.put("Email", email);
	         
	         StringWriter out = new StringWriter();
	         obj.writeJSONString(out);
	         String jsonText = out.toString();
	         System.out.println(jsonText);
	         fw.write(obj.toJSONString());
	         System.out.println("Writing to: contacts.json");
	          	
    	}else{
    		 obj.put("Name", name);
	         obj.put("Surname", surname);
	         obj.put("Phone", number);
	         obj.put("Address", address);
	         obj.put("Email", email);
	         
	         StringWriter out = new StringWriter();
	         obj.writeJSONString(out);
	         String jsonText = out.toString();
	         System.out.println(jsonText);
	         fw1.write(obj.toJSONString());
	         System.out.println("Writing to: tmp.json");
    	}
	    
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
	 //@Close File writers     
	      fw.flush();
	      fw.close();
	      
	      fw1.flush();
	      fw1.close();
	      
             
      //STEP 6: Clean-up environment
	      rs.close();
	      stmt.close();
	      
	   }catch(SQLException se){
	      //Handle errors for JDBC
	       //se.printStackTrace();
		   showMessageDialog("Access denied for user 'root'@'localhost'");
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
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Goodbye!");
   }//end search(String url,String user, String pass, String search) 
}//end FirstExample