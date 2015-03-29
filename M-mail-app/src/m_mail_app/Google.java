package m_mail_app;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;

import net.proteanit.sql.DbUtils;

public class Google extends JFrame{

	private JPanel contentPane;
	private static JTextField textField;
	private static JTextField textField_1;
	private static JTextField textField_2;
	private static JTextField textField_5;
	private static JTextField textField_7;
	private static JTextField user_Name = new JTextField();
	private static JPasswordField password_Field = new JPasswordField();
	
	final JComboBox comboBox = new JComboBox();
	final JComboBox comboBox_1 = new JComboBox();
	final JCheckBox chckbxHideMessage = new JCheckBox("Hide Message");
	final JCheckBox chckbxSelect = new JCheckBox("Select Contact");
	
			/**
			 * Hold's PROXY ADDRESSES and PORT NUMBERS
			 */	    	
			    	public String PROXY;
			    	public HashMap<String,String> PROXY_LIST = new HashMap<String,String>();
			    	public HashMap<String,String> VALID_PROXY_LIST = new HashMap<String,String>();
			    	public List<String> proxy_list = new ArrayList<String>();
			    	public String proxy_address;
			    	public String proxy_port;
			 
			/**
			 * @Perform check on connection to DB
			 */
			    	public boolean db_connection = false;
	    	    	
		    	/**
		    	 * 
		    	 * @param List of Methods used in this class
		    	 */
			    	
			    	public void showLoginForm(){
			    		
			    		//@multiple fields
			    			Object[] fields = {"User", user_Name,
			    					           "Password", password_Field
			    					           };		
			    			JFrame login = new JFrame("JOptionPane showMessageDialog example");
			    			JOptionPane.showConfirmDialog(login, fields, "Login Form", JOptionPane.OK_CANCEL_OPTION);
			    		}
			    	
			    	public void ISP_ComboBOX(){
			    		/*
			    		 * @Retrieve Servers configuration file from server	==> (data.ser) file and write into a file
			    		 */
			    		    Settings.retrieveSettings("http://localhost/myfolder/waterfordblackbeltacademy.com/ISP.ser","ISP.ser");
			    		
			    		/*
			    		 *@Build ISP List from file
			    		 */
			    		    					    
			    		   ISP_LIST = new ArrayList<String>(); 
			    		   try {
			    			BufferedReader isp_bfr = new BufferedReader(new FileReader("ISP.ser"));
			    			String line;
			    			while((line = isp_bfr.readLine()) != null){
			    				ISP_LIST.add(line);				
			    			}
			    			//@Close buffered reader
			    			   isp_bfr.close();
			    			   
			    		} catch (FileNotFoundException e1) {
			    			e1.printStackTrace();
			    		} catch (IOException e1) {
			    			e1.printStackTrace();
			    		}
			    		
			    		   //@add to comboBox selected IPS settings
			    		   for(int i = 0; i < ISP_LIST.size();i++){
			    		          comboBox.addItem(ISP_LIST.get(i));
			    				}
			    		}//end ISP_ComboBOX()
			    	
	    	
		    	public static void showMessageDialog(String message){		
		    		//@show message	
		    		JFrame frame4 = new JFrame("JOptionPane showMessageDialog example");
		    		JOptionPane.showMessageDialog(frame4, message);
		    	}
		    	
		    	public boolean TEST_PROXY_LIST(HashMap<String,String> map){
		    		boolean valid = map.isEmpty();
		    		return valid;
		    	}
	    	
    	
		    	public void LoadProxy(){
		    		//@Perform Check
		    		   System.out.println("METHOD ==> LoadProxy()");
		    		/**
		    		 * @add to comboBox_1 Random RROXY Address from ==> proxy_list
		    		 */
		    			Random random = new Random();
		    			int index = random.nextInt(proxy_list.size());
		    		//@Attach value to comboBox_1
		    			comboBox_1.removeAllItems();
		    			comboBox_1.addItem(proxy_list.get(index));							
		    		/**
		    		 * @Grab PROXY value from comboBox (PROXY) and set proxy in ==> SendMail_TLS..m_proxy(host, port);	
		    		 */
		    			for (Entry<String, String> entry : VALID_PROXY_LIST.entrySet()) {
		    	            if (entry.getKey().equals(PROXY)){
		    	            	//@Perform Check
		    	                System.out.println("KEY: " + entry.getKey() + " VALUE: " + entry.getValue());
		    	                SendMail_TLS.m_proxy(entry.getKey(), entry.getValue());
		    	        }
		    	    }
		    	}//end LoadProxy()
	
	
	/*
	 * Regex email and IP address matchers 
	 */
	
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	/*
	 * Holds Value's of attachment file
	 */
    	public String fileName;
    	public String filePath;
    	
   /*
    * Hold's value of RSA private and public key
    */
    	public String rsa_pubkey;
    	public String rsa_prikey;
    	
   /*
    * Hold's ISP mail exchange server settings
    */
    	
    	public String ISP;
    	public List<String> ISP_LIST;
    	 	
   /*
    * HashMap Holds Servers settings
    */
    	
    private Map<String, String> servers;
      	  		
	/*
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Google frame = new Google();					
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
	public Google() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 388);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(130, 12, 221, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(130, 39, 221, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(130, 70, 414, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnExit = new JButton("Back");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
			}
		});
		btnExit.setBounds(485, 318, 117, 25);
		contentPane.add(btnExit);
		
				
		JLabel lblFrom = new JLabel("From:");
		lblFrom.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		lblFrom.setBounds(26, 14, 70, 15);
		contentPane.add(lblFrom);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		lblTo.setBounds(26, 41, 70, 15);
		contentPane.add(lblTo);
		
		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		lblSubject.setBounds(26, 72, 70, 15);
		contentPane.add(lblSubject);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		lblMessage.setBounds(177, 147, 92, 23);
		contentPane.add(lblMessage);
		
		textField_5 = new JTextField();
		textField_5.setBounds(130, 97, 122, 19);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		lblPassword.setBounds(26, 99, 92, 15);
		contentPane.add(lblPassword);
		
		final JTextArea textArea = new JTextArea();
		textArea.setRows(2);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(3, 3, 326, 111);
		textArea.setEditable(true);
		contentPane.add(textArea);
		
				
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(169, 182, 452, 117);
		contentPane.add(scrollPane);
		
		/*
		 *Due too http proxies problem to work with javamail
		 *sock 4 or 5 must be used as proxy servers to send emails through  
		 */
		
		final JCheckBox proxy = new JCheckBox("Proxy");
		proxy.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		proxy.setToolTipText("Enter IP Address and Port Number First");
		proxy.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				if(proxy.isSelected()){
										
					/**
					 * @Check  VALID_PROXY_LIST IF IS NOT EMPTY EXECUTE METHOD LoadProxy(),
					 * if not proceed, saves time if user wishes to use another PROXY ADDRESS
					 * 
					 */
					
					boolean valid = TEST_PROXY_LIST(VALID_PROXY_LIST);
						
					if(valid == false){
						
							LoadProxy();
							System.out.println("Loading LoadProxy() method!");
							
						}else {
							System.out.println("Skipping LoadProxy() method!");
																
						//@Show Message	
							showMessageDialog("Validation of PROXY could take some time, please be patient!");
							
							/**
							 * @Retrieve Servers configuration file from server	==> (PROXY.ser) file and write into a file
							 * Perform check on file ==> (PROXY.ser) if doesn't exits connect to server and retrieve file
							 */				
							File file_pop = new File("PROXY.ser");
								if(!file_pop.exists()){
								    Settings.retrieveSettings("http://localhost/myfolder/waterfordblackbeltacademy.com/PROXY.ser","PROXY.ser");
								}
							/**
							 *@Build HashMap from file that contains information of servers associated with extensions to their names
							 */			
								Properties properties2 = new Properties();
								
								try {
									properties2.load(new FileInputStream("PROXY.ser"));
								} catch (FileNotFoundException e) {									
									e.printStackTrace();
								} catch (IOException e) {									
									e.printStackTrace();
								}
								
								for (String key : properties2.stringPropertyNames()) {
								   
								   PROXY_LIST.put(key, properties2.get(key).toString());
								  
								}//end for (String key : properties2.stringPropertyNames())	
																		
								/**
							     * @Iterate trough collection and match PATTERN of proxy address 
							     */
								   					   							
							for (Map.Entry<String, String> entry : PROXY_LIST.entrySet()) {			
								
								//@attache value to proxy_address variable
								    proxy_address = entry.getKey().toString();
								  					     
								//@Perform Check on proxy_address variable Validation of IP format, in a case mistake in PROXY.ser file													
									Pattern pattern1 = Pattern.compile(IPADDRESS_PATTERN);
									
								//@PROXY Value from comboBox ==> pattern1.matcher(PROXY);
									Matcher matcher1 = pattern1.matcher(proxy_address);
									boolean matches_server = matcher1.matches();
								/*
								 * If match found PROXY_LIST collection call m_proxy(String host, String port) function from EmailAttachmentReceiver class
								 */
									//@perform check
									System.out.println("METHOD ==> for (Map.Entry<String, String> entry : PROXY_LIST.entrySet()) ==> PROXY ADDRESS: " + proxy_address);
								
							//@Perform check 
								if(matches_server == true){
								
								//@attache value to proxy_port variable
									proxy_port = entry.getValue();	
																									
										try {
											String host = proxy_address;
											String port = proxy_port;
											int port_number = Integer.parseInt(port);
																			
											boolean reachable = "" != null;
											/**
											 * @Check PROXY
											 */
										
										   
											boolean tr = SendMail_TLS.checkConnection(host, port_number, reachable);
											
										//proxy_thread.interrupt();
											
										/**
										 * @Perform check on VALID PROXY, if connection established add Proxy  to ==> VALID_PROXY_LIST
										 * AND BUILD ==> proxy_list that contains valid IP Addresses in String format
										 * proxy_list will be used to get Random address from collection and insert into comboBox ==> LoadProxy() Method
										 */
										if(tr == true){
											//@Perform check
												System.out.println("if(tr == true) ==> Working Proxy  : " + reachable + " PROXY: " + host);
											//@Add to VALID_PROXY_LIST and proxy_list
												VALID_PROXY_LIST.put(host, port);
												proxy_list.add(host);
											}									
											
										} catch (UnknownHostException e) {									
											e.printStackTrace();
										} catch (IOException e) {									
											e.printStackTrace();
										}
																	
								   //@Perform Check
										System.out.println("PROXY SELECTED ==> Proxy Address: " + proxy_address + " Proxy Port: " + proxy_port);
										
								}//end if(matches_server == true)
								
							//@Perform check on size VALID_PROXY_LIST size
								System.out.println("\n\nVALID_PROXY_LIST SIZE: " + VALID_PROXY_LIST.size());
			
						}//end for (Map.Entry<String, String> entry : PROXY_LIST.entrySet()) 
						
						/**
						 * @Run LoadProxy Method
						 */
							LoadProxy();
							
						  }	//end if(valid == false)
						
					
				}else{
					SendMail_TLS.clearProperties();
				}
			
			
			}
		});
		
		proxy.setBounds(94, 149, 77, 18);
		contentPane.add(proxy);
				
		/*
		 * Select Proxy checkbox, if selected run ==> proxy()
		 * else reset properties ==> clearProperties()
		 * Mail will sent through TOR proxy server
		 */
		
		final JCheckBox chckbxProxy = new JCheckBox("TOR");
		chckbxProxy.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		chckbxProxy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxProxy.isSelected()){
					
					try {
						String host = "127.0.0.1";
						int port = 9050;
						
						boolean reachable = "" != null;
						boolean tr = SendMail_TLS.checkConnection(host, port, reachable);
						
						if(tr == true){
							System.out.println("Working Proxy TOR : " + reachable);
							SendMail_TLS.proxy();																					
						}
											
					} catch (UnknownHostException e) {					
						e.printStackTrace();
						showMessageDialog("UnknownHostException");
					} catch (IOException e) {						
						e.getMessage();
						//@Show message
						showMessageDialog("TOR not Running or not installed on this machine! Please ensure that TOR runs on port 9050!");
						chckbxProxy.setSelected(false);
					} 						
				}else{
					SendMail_TLS.clearProperties();
				}
			}
		});
		chckbxProxy.setBounds(26, 149, 64, 18);
		contentPane.add(chckbxProxy);
		
		/*
		 * If checkbox is selected open file chooser select file name and path
		 */	
		
		final JCheckBox chckbxAttacheFile = new JCheckBox("Attach File");
		chckbxAttacheFile.setToolTipText("Select File To Attach to Email");
		chckbxAttacheFile.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		chckbxAttacheFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
						
				
				if(chckbxAttacheFile.isSelected()){					
					
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int returnVal = chooser.showOpenDialog(getParent());
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						
						/*
						 * Attach retrieved value to fileName and filePath, this file be attached to email
						 */
											      
					      fileName = chooser.getSelectedFile().getName();
					      filePath = chooser.getSelectedFile().getAbsolutePath();
					      int x = filePath.length();
					      int y = fileName.length();
					      String st = filePath.substring(0, ( x - y));
					      
					     /*
					      * Asset new value to attach file
					      */
					      filePath = st;	
					      
					      System.out.println(" File Chooser ==>  File Name: "+ fileName + "  File Path: " + filePath);
					}
												
				}else{
					/*
					 * Reset fileName to null
					 */
					     fileName = (String) null;				
					}				
			}
		});
		chckbxAttacheFile.setBounds(363, 12, 122, 18);
		contentPane.add(chckbxAttacheFile);	
		
		/**
		 * @Send Mail Function
		 */		
		
		final JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try{
					/*
					 * Inputs from textFields
					 */
					    String from = textField.getText().trim();
					    String to   = textField_1.getText().trim();
						String subject = textField_2.getText().trim();
						String message = textArea.getText().trim();
						String password = textField_5.getText().trim();
											
				/*
				 * Validation of email input values, they must be formed as into proper form 	
				 * perfom check if user wishes send email using his ISP mail exchange settings
				 */
						
				if(from.length() <= 255 || from.length() > 0 || to.length() <= 255 || to.length() > 0 ){
					
					//@Check if ISP is selected and length is > 1 and perform action if conditions right	
						int ISP_Length = ISP.length();
					if(ISP_Length > 1){
							System.out.println(ISP + " is selected!");
							SendMail_TLS.sendMail_ISP2(ISP, from, to, subject, message);
							//@show message							
							showMessageDialog("Email Sent!");	
							return;						
						}
						
					    pattern = Pattern.compile(EMAIL_PATTERN);
						matcher = pattern.matcher(from);
						boolean matches = matcher.matches();
						
						Pattern pattern_to = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher_to = pattern_to.matcher(to);
						boolean matches_to = matcher_to.matches();
					/*
					 * If email is in correct form ==> proceed
					 */
					
					if(matches == true && matches_to == true){												
						
						/*
						 * @Retrieve Servers configuration file from server	==> (data.ser) file and write into a file
						 * perform check if File exist if not connect to server and get file
						 */
						
						File file_smtp = new File("data_smtp.ser");
						if(!file_smtp.exists()){					
						    Settings.retrieveSettings("http://localhost/myfolder/waterfordblackbeltacademy.com/data_smtp.ser","data_smtp.ser");
						  }
						/*
						 *@Build HashMap from file that contains information of servers associated with extensions to their names
						 */
						    					    
						    servers = new HashMap<String, String>();
							Properties properties2 = new Properties();
							properties2.load(new FileInputStream("data_smtp.ser"));

							for (String key : properties2.stringPropertyNames()) {
							   servers.put(key, properties2.get(key).toString());
							}
	
							/*
							 * This an old method ==> use this method if a method above will not work
							 * Holds collection of available servers, Gmail, Yahoo, Hotmail, Zoho, Outlook
							 * Can be added more if required 
							 
								servers.put(".*gmail.*", "smtp.gmail.com");
								servers.put(".*hotmail.*", "smtp.live.com");
								servers.put(".*yahoo.*", "smtp.mail.yahoo.com");
								servers.put(".*zoho.*","smtp.zoho.com");
								servers.put(".*outlook.*","smtp-mail.outlook.com");
								//servers.put(".*mail.*","smtp.mail.com");												
						    /*
						     * Iterate trough collection and match  email address extension
						     * with servers settings 
						     */
								
						for (Map.Entry<String, String> entry : servers.entrySet()) {
							
								String input = entry.getKey().toString();
								Pattern pattern = Pattern.compile(input);
								Matcher matcher = pattern.matcher(from);
								boolean matches_server = matcher.matches();
								
								//@Perform check
								System.out.println("Matches server settings: " + matches_server + " ==> " + input);
							/*
							 * If match found servers collection call sendMail() function from send SendMail_TLS class
							 */
								
							if(matches_server == true){
								
								/*
								 * Check Validation of the file extension, if attached file do not has  ==> (.zip)
								 * ending use ==> ( filePath + fileName) in SendMail_TLS.sendMail method,
								 * else use only ==> (fileName) in SendMail_TLS.sendMail method
								 */
								
								//@Perform check
								System.out.println("Matches server settings: " + matches_server + " ==> " + input);
								System.out.println("File Name: " + fileName);
																
								//@perform check on file name, depends how email will be sent to reciever								
							if(fileName != null){
									
									System.out.println("Ask Qustion!");
								//@Show message, ask if user wishes to attache signature of the file  
				    	    		JFrame question_a = new JFrame();
				    	    		//@display message
				    	    		String ask_message = "Are you wish to sign a file!";
				    	    		int answer = JOptionPane.showConfirmDialog(question_a, ask_message);
				    	    		
				    	    		String file_signature = "";
				    	    		 
				    	    		if(answer == JOptionPane.NO_OPTION){
				    	    		//@change state of check box unselect if answer ==> NO	
				    	    									    	    			
				    	    		}//if(answer == JOptionPane.NO_OPTION)
				    	    		
				    	    		else if(answer == JOptionPane.YES_OPTION){				    	    			
				    	    			String file_hash = HashFunction.generateFileHash(new File(fileName));
				    	    			file_signature = "\n\nFile Signature: " + file_hash;
				    	    		}
									
									if(!fileName.contains(".zip")){
										
										String host = entry.getValue();	
										//@Check
									    System.out.println("Send ==> File Name: " + fileName + "  File Path: " + filePath);				    
									    SendMail_TLS.sendMail(host,from, password, to, subject, message + file_signature, filePath + fileName);
									  //@show message							
										showMessageDialog("Email Sent Sent ... File attached ==> " + filePath+ fileName);	
							            textArea.setText("Message Sent ... File attached ==> " + filePath+ fileName);	
							            chckbxAttacheFile.setSelected(false);
							           
							            
									
									}else if(fileName.contains(".zip")){
										
										String host = entry.getValue();	
										//@Check
									    System.out.println("Send ==> File Name: " + fileName + "  File Path: " + filePath);							    
									    SendMail_TLS.sendMail(host,from, password, to, subject, message + file_signature, fileName);
									    showMessageDialog("Email Sent Sent ... File attached: " + fileName);
							            textArea.setText("Message Sent ... ! File attached: " + fileName);	
							            chckbxAttacheFile.setSelected(false);
							            
									}
								}
								
								if(fileName == null){
									
									String host = entry.getValue();	
									//@Check
								    System.out.println("Send ==> File Name: " + fileName + "  File Path: " + filePath);							    
								    SendMail_TLS.sendMail_NoAttachement(host,from, password, to, subject, message);
								    showMessageDialog("Email Sent without attachement!");
						            textArea.setText("Message Sent without attachement!");	
						            
								}
										            
						         /**
						          * @Delete Encrypted File or Zip, if File exist must
						          * contains extension ==> (.zip) 
						          */
						            
						         if(fileName.contains(".zip")){
						        	 
						        	 File file = new File(fileName);
						        	 /*
						        	  * Call Delete Directory method from Utility class
						        	  * it will deletes ==> (TMP) directory, that was located inside location
						        	  * of the attached file
						        	  */
						        	 Utility.deleteDirectory(new File(filePath+"TMP"));
						        	
						        	 Thread.sleep(6000);
						        	 
						        	 if(file.delete()){
						        		 
						     			System.out.println(file.getName() + " is deleted! ");
						     			
						     		}else{
						     			
						     			System.out.println("Delete operation is failed.");
						     		}
						        	 
						         }
						         
						         /*
						          * Reset File name and Path
						          */
							         fileName = null;
							         filePath = null;
						         
						         //@perform check
							         System.out.println("File name and Path after reseting:" + fileName + " " + filePath);
							         
						    }					    
						}//end Map.Entry<String, String> entry : servers.entrySet()
						
						 }else{
							 
							textArea.setText("Check Email Address !");
				   }//end if matches email 							
				}//end input validation
																	
				}catch(Exception ex){
					ex.getStackTrace();
				}
			}
		});
		btnSend.setBounds(356, 318, 117, 25);
		contentPane.add(btnSend);
		
		final JCheckBox checkBoxSHA1 = new JCheckBox("Sign Message");
		checkBoxSHA1.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		checkBoxSHA1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String sha1;
				String message = textArea.getText().trim();
				String tmp = "";
				
				if(checkBoxSHA1.isSelected() && message.length() > 0){
					
					HashFunction hash = new HashFunction();
					
					try {
						
						sha1 = hash.generateHash(message);
						textArea.setText(message + "\n\n"+ sha1);
						tmp = message;
						//message = null;
						
					} catch (Exception e) {					
						e.getMessage();
					}
				}else{
					
					textArea.setText(tmp);					
				}
			}
		});
		checkBoxSHA1.setBounds(459, 149, 139, 19);
		contentPane.add(checkBoxSHA1);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.DARK_GRAY);
		separator.setBounds(28, 214, 120, 10);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.DARK_GRAY);
		separator_1.setBounds(26, 145, 120, 2);
		contentPane.add(separator_1);
		
		JLabel lblProxt = new JLabel("Proxy");
		lblProxt.setForeground(Color.BLACK);
		lblProxt.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		lblProxt.setBounds(26, 128, 60, 15);
		contentPane.add(lblProxt);
		
		final JCheckBox symmetric = new JCheckBox("Encrypt");
		symmetric.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		symmetric.setToolTipText("Symmetric Encryption, Enter Key Length");
		symmetric.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
							
				Bitshifter shifter = new Bitshifter();				
				String encrypted;
				int key_length = Integer.parseInt(textField_7.getText());
				/*
				 * Server Synchronization Method Goes here
				 * must create function that gets server time form web
				 * to enable encryption time shifting key
				 */
			
				/*
				 *Pass length of the chain ==> new int[number] 
				 */
				
				int[] chain = new int[key_length];
	
				/*
				 * Call buildChain method ==> Bitshifter.class
				 * pass argument length of the chain into it ==> number
				 */
				    chain = shifter.buildChain(key_length);
						
				/*
				 * Validation of key length, get text from text Area, if matches parameters execute method	
				 */
				if(symmetric.isSelected() && !textArea.getText().isEmpty() && key_length > 0 && key_length < 9999){					
					/*
					 * Call shifter method from Bitshifter class
					 */ 
					encrypted = shifter.encrypt(textArea.getText(), chain);
					textArea.setText(encrypted);	
					
				}else{ 
					/*
					 * if checkbox unselected call decryption function
					 */					
						encrypted = textArea.getText();
						String decrypt = shifter.decrypt(encrypted, chain);
						textArea.setText(decrypt);
				}//end if statement 			
			}
		
		});
		symmetric.setBounds(260, 147, 102, 23);
		contentPane.add(symmetric);
		
		/*
		 * Choose RSA key public key to encrypt, AES key
		 */
		
		final JCheckBox chckbxRSA = new JCheckBox("RSA");
		chckbxRSA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(chckbxRSA.isSelected()){
					
					if(fileName != null && !fileName.contains(".des") && !fileName.contains(".aes")){
					
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int returnVal = chooser.showOpenDialog(getParent());
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						
						/*
						 * Attach retrieved value to rsa_pubkey and filePath, key will be used to 
						 * encrypt AES key
						 */
											      
					      rsa_pubkey = chooser.getSelectedFile().getName();
					      
					      
					      /*
					       * Validation of the public key extension
					       */
					      String st = "";
					      
					      if(rsa_pubkey.contains(".der") || rsa_pubkey.contains(".pem")){
					    	  
					    	  String rsa_pubkey_path = chooser.getSelectedFile().getAbsolutePath();
						      int x = rsa_pubkey_path.length();
						      int y = rsa_pubkey.length();
						      st = rsa_pubkey_path.substring(0, ( x - y));
					    	  System.out.println(" File Chooser ==>  File Name: "+ rsa_pubkey + "  RSA Path: " + st);
					    	  
					    	  /*
					    	   * Create directory to save AES Key File if
					    	   * directory doesn't exist
					    	   */
					    	  File file = new File(filePath + "/TMP");
					    	  if(!file.exists()){
					    		  file.mkdir();
					    	  }
					    	  
					    	  /*
					    	   * Call RSA_TEST class
					    	   */
					    	     RSA_TEST rsa_test = new RSA_TEST();				    	    
					    	     
					    	  try {
					    	/*
					    	 * Create AES Key and move that key in selected directory
					    	 */
								rsa_test.makeKey(filePath + "/TMP/");
								
								/*
								 * Get instances of AES and RSA encryption Chippers
								 */
								try {
									rsa_test.FileEncryption();
								} catch (GeneralSecurityException e) {
									
									e.printStackTrace();
								}							
								/*
								 * Encrypt actual file with AES Key
								 */
								try {
									
									rsa_test.encrypt(new File(filePath + fileName), new File(filePath + "/TMP/" + fileName + ".aes"));
								 /*
								  * Assign new Value to fileName
								  */
									fileName = fileName + ".aes";
								    System.out.println("Encrypting File: " + fileName);
								    
								} catch (InvalidKeyException e) {
									
									e.printStackTrace();
								}	
								
								
								/*
								 * Encrypt AES Key with RSA Public Key, and save into (TMP) directory
								 */
								try {
									
									rsa_test.saveKey(new File(filePath + "/TMP/" + "AES_ENCRYPTED_RSA"), new File(rsa_pubkey_path));
									System.out.println("Encrypting AES Key with public RSA key and moving into: " + filePath + "/TMP/  directory!");
								    
								    /*
								     * Delete plain AESKeyFile
								     */
									File file_aes = new File(filePath + "/TMP/AESKeyFile");
								    if(file_aes.exists()){
								    	
								    	file_aes.delete();
								    	System.out.println("Deleting plain AES Key File: " + file_aes);
								    }else{
								    	System.out.println("AESKeyFile not Found!");
								    }
								    
								} catch (GeneralSecurityException e) {
									
									e.printStackTrace();
								}
								
								/*
								 * Convert multiple files into a single file
								 * using compression technique ==> Zip folder
								 */
								try {
									
									Zip.zipFolder(filePath + "/TMP/", filePath + "TMP.zip");
									fileName = filePath + "TMP.zip";
									System.out.println("Archiving Folder: " + fileName);
									
								} catch (Exception e) {
									
									e.printStackTrace();
								}								
							} catch (NoSuchAlgorithmException e) {								
								e.printStackTrace();
							} catch (IOException e) {								
								e.printStackTrace();
							} catch (InterruptedException e) {								
								e.printStackTrace();
							}					    	  
					      }else{
					    	  System.out.println("You must select RSA key!");
					      }					      
					}												
				}//end if(fileName != null)
					else{
						//@show message							
						showMessageDialog("Attach File to Encrypt First!");	
			    	    chckbxRSA.setSelected(false);
					}//end else	
				}//end if(chckbxRSA.isSelected())
			  }			
		});
		
		chckbxRSA.setToolTipText("Choose only one encryption method, AES or RSA!");
		chckbxRSA.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		chckbxRSA.setBounds(94, 261, 70, 18);
		contentPane.add(chckbxRSA);
		
		textField_7 = new JTextField();
		textField_7.setToolTipText("Enter Key Length");
		textField_7.setBounds(374, 149, 40, 19);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
		
		/*
		 * Encrypt Selected File, AES Encryption, Required Password, File Output location,
		 * Salt output location, IV output location
		 */
		
		final JCheckBox chckbxNewCheckBoxAES = new JCheckBox("AES");
		chckbxNewCheckBoxAES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				if(chckbxNewCheckBoxAES.isSelected()){
									               
			try{ 			
				
				if(fileName != null){
					
					HashFunction hash = new HashFunction();
					Bitshifter bitsh = new Bitshifter();
					
					/*
					 * Create password to encrypt file, iterate through hash function 
					 * iteration length depends on file name length,
					 * encrypt password before writing into the file
					 */
					
					String password = fileName;					
					
					System.out.println("Password before generating hash value: " + password);
									
					/*
					 * Call hash function to generate password,
					 * password to encrypt file is a hash value of the filename,
					 * create temporary value ==> (iter) to iterate through hash function
					 */
					int iter = (password.length() ^ 3) % 3;
					if(iter == 0){
						iter = iter + 121;
					}//end if(iter == 0)
					
					for(int i = 0; i < iter;i++){
						password = hash.generateHash(password);
						System.out.println(i + " ==> " + password);
					}//end for(int i = 0; i < iter;i++)
					
					/*
					 * Encrypt password that was used to encrypt file
					 */
					int [] chain = bitsh.buildChain((password.length()/2));
					password = bitsh.encrypt(password, chain);
					System.out.println("Password after Encrypting: " + password);
					
				  //Check filename and filePath
				   System.out.println("AES Encryption: ==> File Path: " + filePath + "   File Name: " + fileName );
				  
				   /*
				    *@ Holds new value of the encrypted file
				    */
				   String FileOutputName = fileName + "_encrypted.des";
				   				   
				/*
				 * Encrypt File AESFileEncryption
				 */
				   AESFileEncryption.fileEncryption(filePath,fileName, FileOutputName, password,"salt.enc","iv.enc");
				   
				/*
				 * Rename fileName to temporary, purpose attach file in send method 
				 * holds value of ==> fileName, do not have to change value of attachment, also
				 * same value will be used to delete encrypted file on users computer 
				 */
				    fileName = FileOutputName;
				    System.out.println("AES encryption ==> File name after encryption: " + fileName);
				   
				    /*
				     * Write encrypted password into the file ==> encrypted_password
				     */
				       //FileOutputStream fos = new FileOutputStream(filePath + "/TMP/encrypted_password");
					  // byte[] pass = new byte[password.length()];
					  // pass = password.getBytes();
					  // fos.write(pass);
					  //fos.close();
					   
				  //  System.out.println("Password written into the file!");
				    
				    /*
					 * Call ZipFolder Method from Zip class, directory will be zipped
					 * and attache as a single file to email, encrypted file, salt, ivs will 
					 * saved in ==> (TMP) directory if file encrypted
					 */
						
						try {
							
							Zip.zipFolder(filePath+"TMP", filePath+"TMP.zip");
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					/*
					 * Rename fileName, add zip ending
					 */
						
						fileName = filePath + "TMP.zip";
						System.out.println("New File Name after Zipping: " + fileName);
				}else{
					//@show message	
					showMessageDialog("Choose File to Encrypt First!");
		    	    chckbxNewCheckBoxAES.setSelected(false);
				}//if(fileName != null)
				    				  
				}catch(Exception e){
					e.getMessage();
				  }
			
				}
			}
		});
		chckbxNewCheckBoxAES.setToolTipText("First Attach File");
		chckbxNewCheckBoxAES.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		chckbxNewCheckBoxAES.setBounds(21, 261, 77, 18);
		contentPane.add(chckbxNewCheckBoxAES);
		
		/*
		 * Archive attached file --> Zip extension
		 */
		final JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Zip File");
		chckbxNewCheckBox_1.setToolTipText("Do not Zip Encrypted File");
		chckbxNewCheckBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(chckbxNewCheckBox_1.isSelected()){
				
				/*
				 * Call ZipFile Method from Zip class	
				 */
					
					try {
						/*
						 * Validation of the fileName, fileName must not be null
						 * or have extension (.zip)
						 */
						
						if(fileName != null){
							
							if(!fileName.contains(".zip")){
							
								//@disable aes check box

								chckbxNewCheckBoxAES.setEnabled(false);
								chckbxRSA.setEnabled(false);
								
								System.out.println("Zip File method: File Name: " + fileName + " File Path: " + filePath);
								/*
								 * After Zipping the file the file name will change ==> fileName . (zip)
								 */
								String tmp = filePath + fileName.substring(0,(fileName.length() - 4)) + ".zip";
								
								Zip.ZipFile(filePath + fileName, tmp , fileName);
								//@Show Message 
								showMessageDialog("Zipped file can be found at: " + filePath);
								/*
								 * Holds new value of the attachment file
								 */
								fileName = tmp;
								System.out.println("New File Name after Zipping: " + fileName);
								
							}//end if(!fileName.contains(".zip"))
							else{
								//@Show Message
								showMessageDialog("File Already Encrypted and Zipped, please choose only one function Encrypt " +
								                                                      "or Zip for the file!");
								textArea.setText("File Already Encrypted and Zipped, please choose only one function Encrypt " +
										         "or Zip for the file!");
							}//end else
							
						}//end if(fileName != null)
						else {
							//@Show Message
							showMessageDialog("Please Choose file To Zip first!");							
						}//end if(fileName != null)
												
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}//end if(chckbxNewCheckBox_1.isSelected())
				else{
					
				//@Show Message
					showMessageDialog("File will be Deleted!");
					
				//@Enable AES and RSA check box	
					chckbxNewCheckBoxAES.setEnabled(true);
					chckbxRSA.setEnabled(true);
					
				//@Delete File if Box an selected
					File file = new File(fileName);
					if(file.exists()){
						file.delete();
					}
				}//end else
			}
		});
		chckbxNewCheckBox_1.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		chckbxNewCheckBox_1.setBounds(487, 12, 102, 18);
		contentPane.add(chckbxNewCheckBox_1);		
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBackground(Color.DARK_GRAY);
		separator_2.setBounds(365, 35, 180, 5);
		contentPane.add(separator_2);
		
		
		comboBox.setToolTipText("ISP MAIL EXCHANGE SERVER");
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				//@Load Method ==> ISP_ComboBOX()
				ISP_ComboBOX();
				
				ISP = (String) comboBox.getSelectedItem();
			//@perform Check
				System.out.println(ISP);
			}
		});
		comboBox.setBounds(308, 97, 165, 19);
		contentPane.add(comboBox);
		comboBox.setEditable(true);
		comboBox.addItem("");
				   
		JLabel lblIsp = new JLabel("ISP");
		lblIsp.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		lblIsp.setBounds(273, 99, 40, 15);
		contentPane.add(lblIsp);	
		
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				PROXY = (String) comboBox_1.getSelectedItem();
			}
		});
				
		comboBox_1.setBounds(26, 183, 131, 19);
		contentPane.add(comboBox_1);
		chckbxHideMessage.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		
		
		chckbxHideMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String text = textArea.getText().trim();
				
				if(chckbxHideMessage.isSelected()){	
										
					if(fileName == null && text == null){
						
					//@show Message
						showMessageDialog("File not Found!");

					}else if(fileName.contains(".JPG")){
						
						String newFileName = filePath + "blackWhite.JPG";
						System.out.println("New File Name: " + newFileName);	
						try {
							Settings.blackAndWhite(filePath + fileName, newFileName);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						String outputFile = newFileName + "_binary";
						Settings.Image_to_binary(newFileName, outputFile);
						//Settings.ConverToGrayScale(filePath + fileName, newFileName, "JPG");
						
						File file = new File(outputFile);
						
						long size = file.length();
						int s = new BigDecimal(size).intValueExact();
						
						SecureRandom sr = new SecureRandom();
						long index = sr.nextInt(s);
						int t = new BigDecimal(index).intValueExact();
					
					//@Perform check
						System.out.println("Possition: " + t);
						
						int[] chain = Bitshifter.buildChain(text.length());
						String encrypt = Bitshifter.encrypt(text, chain);
						
					//@Perform Check
						System.out.println(encrypt);
						
						
					byte[] bytes = encrypt.getBytes();
					StringBuilder binary = new StringBuilder();
					
					for(byte b: bytes){
						int val = b;
						for(int i = 0; i < 24; i++){
							binary.append((val & 128) == 0 ? 0 : 1);
							val <<= 1;
						}
						binary.append(' ');
					}
				
					System.out.println("\nTo binaFry: " + binary);
						
					try {
						Settings.writeToFile(outputFile, binary.toString(), t);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
					}
				}
			}
		});
		chckbxHideMessage.setBounds(459, 129, 139, 18);
		contentPane.add(chckbxHideMessage);
	/**
	 * @Select from MySQL or Sqlite databases method
	 */
	    chckbxSelect.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		
	    		String user = user_Name.getText();
    			String password = password_Field.getSelectedText();
    			System.out.println("Server_db_connection: " + db_connection);
	    		
	    		if(chckbxSelect.isSelected()){
	    		
		    		if(db_connection == false){
		    			showLoginForm();
		    			user = user_Name.getText();
		    			password = password_Field.getSelectedText();	
	     			}
	    				    		    
	    		    Connection conn = null;    		    
	    		    
	    		    try{
	    		    	 try {
	    		    		 
							Class.forName(FirstExample.JDBC_DRIVER);
							
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	    		    	
    		         //@Connect TO server DB
	    		         conn = DriverManager.getConnection(FirstExample.DB_URL,user,password);
	    		         
	    		   //@Check Connection Status on Server if connection can be established proceed further 
	    		      if(conn != null){    	
	    		    	  
	    		    	  //@Set New JFrame
		    		    	JFrame frame = new JFrame();
		  	    		    final JTable table = new JTable();
	
		  	    		    JScrollPane scrollPane = new JScrollPane(table);
		  	    		    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		  	    		    frame.setSize(400, 250);
		  	    		    frame.setVisible(true);
		  	    		    
	    		    	  //@Prepare statement
		    			     String query = "SELECT Name, Email FROM contacts";
		    			     PreparedStatement  pst =  conn.prepareStatement(query);
		    			 
		    			   //@Execute query
		    			     ResultSet rs = pst.executeQuery();
		    			 
		    			   //@Pass Results into JTable
		    			     table.setModel(DbUtils.resultSetToTableModel(rs));
		    			     
		    			    //@change state of check box
		    			    
		    			     chckbxSelect.setSelected(false);
		    			     
		    			   //@Attach new value to db_connection after successful login to DB
		    			     db_connection = true;
		    			
                           /**
                            * @Mouse click listener attached to table
                            */
		    			     
		    			     table.addMouseListener(new MouseAdapter() {
		    			    	 
		    			         public void mouseClicked(MouseEvent e) {
		    			        	 
		    			             if (e.getButton() == MouseEvent.NOBUTTON) {
		    			            	 
		    			               textArea.setText("No button clicked...");
		    			               
		    			             } else if (e.getButton() == MouseEvent.BUTTON1) {
		    			               		    			               
		    			               try{		
		    			            	//@Get email address from table
		    			            	   int row = table.getSelectedRow();
		    			            	   String table_click = (table.getModel().getValueAt(row, 1).toString());
		    			               //@Set selected value to field ==> TO
		    			            	   textField_1.setText(table_click);
		    			            	   
		    			               }catch(Exception exc){
		    			            	   JOptionPane.showMessageDialog(null, exc);
		    			               
		    			               }	    			              		    			               
		    			             } 
		    			            
		    			           }
		    			         });	    	
		    			      }
	    		      
	    		    }catch(SQLException se){
	    			      //@Handle errors for JDBC
	    				   showMessageDialog("Access denied or server not running press OK to connect to Local Data Base");
	    			   }finally{
	    				   try{
	    					   //@Close connection
	    					   if(conn != null){
	    						   conn.close();
	    					   }
	    				   }catch(Exception e1){
	    					   JOptionPane.showMessageDialog(null, e1);
	    				   }
	    			   }
	    		  //@If connection is refused on server side connect to local DB  
	    		    
	    		    if(conn == null){
	    		    	
	    		     try{
	    		    	 
	    		    //@Connect to Sqlite DB on Local Machine
	    		    	Sqlite_Database.dbConnector();
	    		    	
	    		    //@Set New JFrame
	    		    	JFrame frame = new JFrame();
	  	    		    final JTable table = new JTable();

	  	    		    JScrollPane scrollPane = new JScrollPane(table);
	  	    		    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	  	    		    frame.setSize(400, 250);
	  	    		    frame.setVisible(true);
	  	    		    
	  	    		//@Prepare statement
	    			     String query = "SELECT Name, Email FROM ContactInfo";
	    			     PreparedStatement  pst =  Sqlite_Database.connection.prepareStatement(query);
	    			     
	    			   //@Execute query
	    			     ResultSet rs = pst.executeQuery();
	    			 
	    			   //@Pass Results into JTable
	    			     table.setModel(DbUtils.resultSetToTableModel(rs));
	    			     
	    			    //@change state of check box
	    			     
	    			     chckbxSelect.setSelected(false);
	    			     
	    			   //@Close Statements
	    			     pst.close();
	    			     rs.close();
	    			     
	    			     /**
                          * @Mouse click listener attached to table
                          */
		    			     
		    			     table.addMouseListener(new MouseAdapter() {
		    			    	 
		    			         public void mouseClicked(MouseEvent e) {
		    			        	 
		    			             if (e.getButton() == MouseEvent.NOBUTTON) {
		    			            	 
		    			               textArea.setText("No button clicked...");
		    			               
		    			             } else if (e.getButton() == MouseEvent.BUTTON1) {
		    			               		    			               
		    			               try{		
		    			            	//@Get email address from table
		    			            	   int row = table.getSelectedRow();
		    			            	   String table_click = (table.getModel().getValueAt(row, 1).toString());
		    			               //@Set selected value to field ==> TO
		    			            	   textField_1.setText(table_click);
		    			            	   
		    			               }catch(Exception exc){
		    			            	   JOptionPane.showMessageDialog(null, exc);
		    			               }
		    			              		    			               
		    			             } 
		    			           }
		    			         });	    
		    			     
		    			   //@Set value to db_connection true
		    			     db_connection = true;

	    			    		 
	    		     }catch(Exception e1){
	    		    	 JOptionPane.showMessageDialog(null, e1);
	    		     }finally{
	    		    	 try{
	    		    		 
	    		    //@Close Connection Sqlite_Database.connection
	    		    		 
	    		    	 if(Sqlite_Database.connection != null){
	    		    		 Sqlite_Database.connection.close();
	    		    	 }
	    		    	}catch(Exception e2){
	    		    		JOptionPane.showMessageDialog(null, e2);
	    		    	}
	    		     }
	    		    }
	    		}
	    	}
	    });
		
		
	    chckbxSelect.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
	    chckbxSelect.setBounds(363, 39, 154, 18);
	    contentPane.add(chckbxSelect);
		
	/**
	 * @Images 	
	 */
		final JLabel mail_icon = new JLabel();
		mail_icon.setIcon(new ImageIcon("img/zoho.png"));
		mail_icon.setBounds(551, 39, 70, 56);
	    contentPane.add(mail_icon);
	    
	    final JLabel aes_icon = new JLabel();
	    aes_icon.setIcon(new ImageIcon("img/aes.png"));
	    aes_icon.setBounds(51, 291, 64, 56);
	    contentPane.add(aes_icon);
	    
	    JLabel lblFileEncryption = new JLabel("File Encryption:");
	    lblFileEncryption.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
	    lblFileEncryption.setBounds(21, 225, 136, 20);
	    contentPane.add(lblFileEncryption);
	    	    
	}	
}	


