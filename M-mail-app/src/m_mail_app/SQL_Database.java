package m_mail_app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JCheckBox;

import net.proteanit.sql.DbUtils;

public class SQL_Database extends JFrame {

	private static final long serialVersionUID = 3942998910297755712L;
	private JPanel contentPane;
	
	public JTextField search;
	
	/**
	 * @Fields used to get user and password details
	 */
	
	private static JPasswordField password_Field = new JPasswordField();
	public JTextField userName = new JTextField();
	
	/**
	 * @Fields used in Create and Update form
	 */
	
	public JTextField user_Id = new JTextField();
	public JTextField user_Name = new JTextField();
	public JTextField user_Surname = new JTextField();
	public JTextField user_Phone = new JTextField();
	public JTextField user_Email = new JTextField();
	public JTextField user_Address = new JTextField();
	public JTextField user_Nic = new JTextField();
	public JTextField user_Password = new JTextField();
	
	/**
	 * @Global variables
	 */
	
	public String userName1 = null;
	public String password1 = null;
	
	//@Variable used to update contact on DB
		public String add1 = "";
		public String add2 = "";
		public String add3 = "";
		public String add4 = "";
		public String add5 = "";
		public String add6 = "";
		public String add7 = "";
		public String add8 = "";
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
//@Login Form
	public void showLoginForm(){
		
		//@multiple fields
			Object[] fields = {"User", userName,
					           "Password", password_Field
					           };		
			JFrame login = new JFrame("JOptionPane showMessageDialog example");
			JOptionPane.showConfirmDialog(login, fields, "Login Form", JOptionPane.OK_CANCEL_OPTION);
		}
	
//@Form for update and create
     public void showForm(String message){
		
		//@multiple fields
    	 
			Object[] fields = {"ID", user_Id,
					           "Name", user_Name,
					           "Surname", user_Surname,
					           "Phone", user_Phone,
					           "Address", user_Address,
					           "User", user_Nic,
					           "Password", user_Password,
					           "Email", user_Email,					           
					           };	
			
			JFrame frame = new JFrame("JOptionPane showMessageDialog example");
			JOptionPane.showConfirmDialog(frame, fields, message, JOptionPane.OK_CANCEL_OPTION);
		}
	
 //@Show Message Window
	public static void showMessageDialog(String message){		
		//@show message	
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");
		JOptionPane.showMessageDialog(frame, message);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SQL_Database frame = new SQL_Database();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SQL_Database() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 414, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("About");
		menuBar.add(mnNewMenu);
		
		final TextArea textArea = new TextArea();
		textArea.setPreferredSize(new Dimension(300, 200));
		textArea.setFont(new Font("DejaVu Sans Condensed", Font.BOLD | Font.ITALIC, 14));
		textArea.setColumns(20);
		textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textArea.setBounds(20, 22, 372, 222);
		contentPane.add(textArea);
		
		JLabel lblNewLabel = new JLabel("Search");
		lblNewLabel.setBounds(20, 250, 70, 15);
		contentPane.add(lblNewLabel);
		
		search = new JTextField();
		search.setBounds(20, 266, 114, 19);
		contentPane.add(search);
		search.setColumns(10);
		
		final JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String search1 = search.getText().trim();					
				String result = "";
				
			//@Perform check on validation on userName1
			
		if(FirstExample.check_connection == false){		
			//@Display Login Form
			    showLoginForm();
				userName1 = userName.getText();
				password1 = password_Field.getSelectedText();
		}
			
		  //@Perform check 
		FirstExample.dbConnector(userName1, password1);
		
		if(FirstExample.check_connection == true){
			
			//@Connect to DB and search
				FirstExample.SQLConnector(userName1, password1);
				FirstExample.search(search1);
				
				BufferedReader br = null;
				
			    try {
					br = new BufferedReader(new FileReader("search.ser"));
					String line;
					try {
						while((line = br.readLine()) != null){
							result += line + "\n";
						}
						
						br.close();
						
					//@Print Output to textArea
					    textArea.setText(result); 
					    
					//@Connect lo Sqlite DB if contact not found on server DB
						if(result.length() <= 1){
							JOptionPane.showMessageDialog(null, "Contact not Found in On Server Data Base.");
							
							Sqlite_Database.dbConnector();
							Sqlite_Database.search(search1);
							
							File file = new File("sqlite_search.ser");
							
							if(file.length() > 1){
								BufferedReader br2 = new BufferedReader(new FileReader(file));
								String line2;
								while((line2 = br2.readLine()) != null){
                                   result += line2 + "\n";
								}
								br2.close();
								textArea.setText(result);								
							}
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}//end (userName1 == null)
		}
			
		});
		btnSearch.setBounds(17, 297, 117, 25);
		contentPane.add(btnSearch);
		
		/**
		 * @Create new Contact in DB on server and local DB
		 */
		
		final JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		
		//@Perform check on connection to DB on server side
		  // FirstExample.dbConnector(userName1, password1);
		   
		if(FirstExample.check_connection == false){				
			 showLoginForm();
			 userName1 = userName.getText().trim();
			 password1 = password_Field.getSelectedText();
		}
			 //@Change state of boolean to true in FirstExample.dbConnector()
			 
			 FirstExample.dbConnector(userName1, password1);
			 
	  if(FirstExample.check_connection == true){
				
				showForm("Create User Form");
			//@Variables
				String userId = user_Id.getText().replaceAll("\\W", "").trim();
				String user = user_Name.getText().replaceAll("\\W", "").trim();
				String surname = user_Surname.getText().replaceAll("\\W", "").trim();
				String phone = user_Phone.getText().replaceAll("\\W", "").trim();						
				String address = user_Address.getText().replaceAll("\\W", "").trim();
				String nic = user_Nic.getText().replaceAll("\\W", "").trim();
				String password = user_Password.getText();
				String email = user_Email.getText().trim();
				
				//@Remove unwanted 	characters from Strings
				List<String> list = new ArrayList<String>();
				List<String> sorted = new ArrayList<String>();
				list.add(userId);
				list.add(user);
				list.add(surname);
				list.add(phone);
				list.add(address);
				list.add(nic);
				list.add(password);
				list.add(email);
				
				//@Remove elements	
				 for(int i = 0; i < list.size();i++){
					 
					 StringTokenizer tokenizer = new StringTokenizer(list.get(i), "// //,//�//<//>//://;//!//()//?//)//#//=//{//}//(//)//[//]//|//\\//+//-//_//*//&//%//$//^//“//„//'//~//'//");								
					 
					 while(tokenizer.hasMoreElements()){								   								   
						        String st = tokenizer.nextToken();
						        st = Normalizer.normalize(st, Normalizer.Form.NFD);
						        String result = st.replaceAll("[^\\x00-\\x7F]", "").replaceAll("[^\\p{ASCII}]", "");
						        result.replaceAll("[^a-zA-Z0-9\\s]", "");
						        sorted.add(result);
					   }
				 }
				 
				    userId = sorted.get(0);
					user = sorted.get(1);
					surname = sorted.get(2);
					phone = sorted.get(3);
					address = sorted.get(4);
					nic = sorted.get(5);
					password = sorted.get(6);
				
				
				int id = Integer.parseInt(userId);
				int phone_number = Integer.parseInt(phone);
				
				Pattern pattern = Pattern.compile(EMAIL_PATTERN);
				Matcher matcher = pattern.matcher(email);
				
					if(matcher.matches() == true){				
						//@Create new contact in DB on server side
						    FirstExample.SQLConnector(userName1, password1);
							FirstExample.insert(id, user, surname, phone_number, address, nic, password, email);
					//@Create new contact in Sqlite DB 												
							Sqlite_Database.dbConnector();
							Sqlite_Database.insert(id, user, surname, phone_number, address, nic, password, email);
					   }else{
							showMessageDialog("Wrong Email Address! Try Again ...");	
							
						}//end if(matcher.matches() == true)
				}//end if(FirstExample.check_connection == true)
			}
		});
		btnCreate.setBounds(146, 297, 117, 25);
		contentPane.add(btnCreate);
		
		
		
		final JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
		if(FirstExample.check_connection == false){				
			showLoginForm();
			userName1 = userName.getText().trim();
			password1 = password_Field.getSelectedText();			
		 }
				
		//@Perform check on connection status in DB on server side if success check_connection change state to  = true
			FirstExample.dbConnector(userName1, password1);
			
			if(FirstExample.check_connection == true){
				
				Object[] fields = {"Name", user_Name};												
				JFrame frame = new JFrame("JOptionPane showMessageDialog example");
				JOptionPane.showConfirmDialog(frame, fields, "Enter User Name", JOptionPane.OK_CANCEL_OPTION);
				
			//@Search contact
				String search = user_Name.getText().trim();
				
			//@Delete user in MySQL 
				FirstExample.SQLConnector(userName1, password1);
				FirstExample.delete(search);
				
			//@Delete user in Sqlite
				Sqlite_Database.dbConnector();
				Sqlite_Database.delete(search);
			//@Clear	
				search = null;
				user_Name.setText("");
			}				
		}
		});
		btnDelete.setBounds(285, 297, 117, 25);
		contentPane.add(btnDelete);
		
		
		final JCheckBox chckbxUpdate = new JCheckBox("Update");
		chckbxUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				
				if(chckbxUpdate.isSelected()){
					
					//@Perform check if username not null
					
					if(FirstExample.check_connection == false){
							showLoginForm();
							userName1 = userName.getText();
							password1 = password_Field.getSelectedText();
					}
					
					Connection connection = null;
					
					 try {
						 
						connection = DriverManager.getConnection(FirstExample.DB_URL,userName1,password1);
						
						if(connection != null){
							
							//@Set New JFrame
		    		    	JFrame frame = new JFrame();
		  	    		    final JTable table = new JTable();
	
		  	    		    JScrollPane scrollPane = new JScrollPane(table);
		  	    		    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		  	    		    frame.setSize(400, 250);
		  	    		    frame.setVisible(true);
		  	    		    
	    		    	  //@Prepare statement
		    			     String query = "SELECT Name, Email FROM contacts";
		    			     PreparedStatement pst =  connection.prepareStatement(query);
		    			 
		    			   //@Execute query
		    			     ResultSet rs = pst.executeQuery();
		    			 
		    			   //@Pass Results into JTable
		    			     table.setModel(DbUtils.resultSetToTableModel(rs));
		    			     
		    			    //@Close connection to DB and change state of check box
		    			      connection.close();	
		    			     
                           table.addMouseListener(new MouseAdapter() {
		    			    	 
		    			         public void mouseClicked(MouseEvent e) {
		    			        	 
		    			             if (e.getButton() == MouseEvent.NOBUTTON) {
		    			            	 
		    			               textArea.setText("No button clicked...");
		    			               
		    			             } else if (e.getButton() == MouseEvent.BUTTON1) {
		    			               
		    			            	 Connection connection2 = null;
		    			            	 
		    			               try{		
		    			            	//@Get user name from table		    			            	   
		    			            	   int row = table.getSelectedRow();
		    			            	   
		    			            	 //@Get name from Table which will be updated
		    			            	   String table_click = (table.getModel().getValueAt(row, 0).toString());
		    			            	   System.out.println(table_click);
		    			            	   
		    			            	   connection2 = DriverManager.getConnection(FirstExample.DB_URL,userName1,password1);
		    			            	   String sql2 = "select * from contacts where Name = '" + table_click + "'";
		    			            	   System.out.println(sql2);
		    			            	   
		    			            	   PreparedStatement pst2 = connection2.prepareStatement(sql2);
		    			            	   ResultSet rs = pst2.executeQuery();
		    			            	   
		    			             //@Assign values retrieved from DB to Strings 
		    			            if(rs.next()){	    			            	
		    			            	add1 = rs.getString("ID");
		    			            	add2 = rs.getString("Name");
		    			            	add3 = rs.getString("Surname");
		    			            	add4 = rs.getString("Phone");
		    			            	add5 = rs.getString("Address");		   
		    			            	add6 = rs.getString("User");
		    			            	add7 = rs.getString("Password");
		    			            	add8 = rs.getString("Email");
		    			            	//System.out.println(add1 + add2 + add3 + add4 + add5);		    			            	
		    			               }
		    			        //@Assign values to fields   
		    			            user_Id.setText(add1);
		    			            user_Name.setText(add2);
		    			            user_Surname.setText(add3);
		    			            user_Phone.setText(add4);
		    			            user_Address.setText(add5);
		    			            user_Nic.setText(add6);
		    			            user_Password.setText(add7);
		    			            user_Email.setText(add8);
		    			            
		    			        //@Create fields with assigned values
		    			            
		    			            Object[] fields = {
		    			            		   "ID",   user_Id,
		    			            		   "Name", user_Name,
		    						           "Surname", user_Surname,
		    						           "Phone", user_Phone,
		    						           "Address", user_Address,
		    						           "User Nic", user_Nic,
		    						           "User Password", user_Password,
		    						           "Email", user_Email,
		    						           };	
		    			//@Show Update Form
		    				JFrame frame = new JFrame("JOptionPane showMessageDialog example");
		    				JOptionPane.showConfirmDialog(frame, fields, "Update Form", JOptionPane.OK_CANCEL_OPTION);
		    				
		    				
		    			int userId = Integer.parseInt(user_Id.getText());
		    			int userPhone = Integer.parseInt(user_Phone.getText());
		    				
		    			//@Execute SQL update method	
		    			    FirstExample.SQLConnector(userName1, password1);
		    				FirstExample.update(userId, user_Name.getText(), user_Surname.getText(), userPhone, user_Address.getText(), user_Nic.getText(), user_Password.getText(), user_Email.getText(), table_click);
		    			
		    				
		    		 //@Execute Sqlite update method only if SQL Database on server can be updated
		    				
		    			FirstExample.dbConnector(userName1, password1);
		    			
		    			if(FirstExample.check_connection == true){
		    				Sqlite_Database.dbConnector();
		    				Sqlite_Database.update(userId, user_Name.getText(), user_Surname.getText(), userPhone, user_Address.getText(), user_Nic.getText(), user_Password.getText(), user_Email.getText(), table_click);
		    			}
		    			
		    			
		    		  //@Change state of chckbxUpdate
		    				chckbxUpdate.setSelected(false);
		    			            	   
		    			               }catch(Exception exc){
		    			            	   JOptionPane.showMessageDialog(null, exc);
		    			               }finally{
		    			            	   if(connection2 != null){
		    			            		   try {
												connection2.close();
											} catch (SQLException e1) {
												JOptionPane.showMessageDialog(null, e1);
											}
		    			            	 }
		    			               }//end finally
		    			              		  	               
		    			             } 
		    			            
		    			           }
		    			         });
							
						}//end if(connection != null)
						
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, "Connection to server can not be established try again later");
					}
				}
			  
			}
		});
		chckbxUpdate.setBounds(160, 264, 91, 23);
		contentPane.add(chckbxUpdate);
	}
}
