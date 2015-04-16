package m_mail_app;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class Mail_Retrieval extends JFrame {
	
	private static final long serialVersionUID = 1L;

	//@ New Object of EmailAttachmentReceiver
    public static EmailAttachmentReceiver ear = new EmailAttachmentReceiver();

	private JPanel contentPane;
	JTextField textField1 = new JTextField();
	JPasswordField passwordField2 = new JPasswordField();
	
	/*
	 * Global Variables
	 */
	public String fileName;
	public String filePath;
	public String ivFile;
	public String ivPath;
	public String saltFile;
	public String saltPath;
	public String publicKey;
	public String publicKeyPath;
	public String rsaPrivateKeyPath; 
	public String rsaPrivateKey; 
	
	public String messages;
	/*
	 * IP Address pattern 
	 */
	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	public static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	/*
	 * Login Details
	 */
			public String userName;
			public String password;
	
	/**
	 * Hold's PROXY ADDRESSES and PORT NUMBERS
	 */	    	
	    	public String PROXY;
	    	public HashMap<String,String> PROXY_LIST = new HashMap<String,String>();
	    	public HashMap<String,String> VALID_PROXY_LIST = new HashMap<String,String>();
	    	public List<String> proxy_list = new ArrayList<String>();
	    	public String proxy_address;
	    	public String proxy_port;
		
	/*
	 * Server settings
	 */
	private HashMap<String,String> servers;
	
	/**
	 * List of JCheckBox
	 */
	final JCheckBox chckbxShowProxy = new JCheckBox("Proxy");
	final JCheckBox chckbxDecryptFile = new JCheckBox("Decrypt File");
	final JCheckBox chckbxFileSignature = new JCheckBox("File Signature");
	final JCheckBox chckbxTor = new JCheckBox("TOR");
	final JCheckBox chckbxReadMessages = new JCheckBox("Read Emails");
	
	final JComboBox comboBox = new JComboBox();
	private final JCheckBox chckbxDeleteMessages = new JCheckBox("Delete Emails");
	private final JButton btnBack = new JButton("Back");
	
	/**
	 * 
	 * @param List of Methods used in this class
	 */
	
	public boolean TEST_PROXY_LIST(HashMap<String,String> map){
		boolean valid = map.isEmpty();
		return valid;
	}
	
	public void showMessageDialog(String message){		
		//@show message	
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");
		JOptionPane.showMessageDialog(frame, message);
	}
	
	public void showLoginForm(){
		
	//@multiple fields
		Object[] fields = {"Email", textField1,
				           "Password", passwordField2
				           };		
		JFrame login = new JFrame("JOptionPane showMessageDialog example");
		JOptionPane.showConfirmDialog(login, fields, "Login Form", JOptionPane.OK_CANCEL_OPTION);
	}
	
	public void showUserEmailForm(){
		
		Object[] field = {"Email", textField1};		
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");
		JOptionPane.showConfirmDialog(frame, field, "Email Address", JOptionPane.OK_CANCEL_OPTION);
	}
	
	public  void LoadProxy(){
		//@Perform Check
		   System.out.println("METHOD ==> LoadProxy()");
		/**
		 * @add to comboBox Random RROXY Address from ==> proxy_list
		 */
			Random random = new Random();
			int index = random.nextInt(proxy_list.size());
		//@Attach value to comboBox
			comboBox.removeAllItems();
			comboBox.addItem(proxy_list.get(index));							
		/**
		 * @Grab PROXY value from comboBox (PROXY) and set proxy in ==> ear.m_proxy(host, port);	
		 */
			for (Entry<String, String> entry : VALID_PROXY_LIST.entrySet()) {
	            if (entry.getKey().equals(PROXY)){
	            	//@Perform Check
	                System.out.println("KEY: " + entry.getKey() + " VALUE: " + entry.getValue());
	                ear.m_proxy(entry.getKey(), entry.getValue());
	        }
	    }
	}//end LoadProxy()
	
	/**
	 * @Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mail_Retrieval frame = new Mail_Retrieval();
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
	public Mail_Retrieval() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 590, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, null, null, null));
		scrollPane.setBounds(24, 12, 543, 196);
		contentPane.add(scrollPane);
		
		final JTextArea textArea = new JTextArea();
		textArea.setRows(13);
		scrollPane.setViewportView(textArea);
		
		final JButton btnConnect = new JButton("Get Files");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			//@Execute LoginForm() Method
				showLoginForm();
				
				//@Get Values from JTextFields
					userName = textField1.getText().trim();
					password = passwordField2.getText().trim();
					
					//@check email address pattern
					Pattern pattern = Pattern.compile(EMAIL_PATTERN);
					Matcher matcher = pattern.matcher(userName);
					
					if(matcher.matches() == true && password.length() >= 6 && password.length() <= 50){
											
						//@Choose directory where to save Email Attachments
						JFileChooser chooser = new JFileChooser();
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int returnVal = chooser.showOpenDialog(getParent());
						if(returnVal == JFileChooser.APPROVE_OPTION) {					
							
							/**
							 * Attach retrieved value to fileName and filePath
							 */
						      
							String directory = chooser.getSelectedFile().getAbsolutePath();
							
                          //@save attachments into selected directory add ==> ("/") to directory path
							ear.setSaveDirectory(directory + "/");
							
							/**
							 * @Retrieve Servers configuration file from server	==> (data.ser) file and write into a file
							 * Perform check on file ==> (data_pop.ser) if doesn't exits connect to server and retrieve file
							 */				
							File file_pop = new File("data_pop.ser");
								if(!file_pop.exists()){
								    Settings.retrieveSettings("http://localhost/myfolder/waterfordblackbeltacademy.com/data_pop.ser","data_pop.ser");
								}
								
							/**
							 *@Build HashMap from file that contains information of servers associated with extensions to their names
							 */							    					    
							    servers = new HashMap<String, String>();
								Properties properties2 = new Properties();
								try {
									
									properties2.load(new FileInputStream("data_pop.ser"));
									
								} catch (FileNotFoundException e) {									
									e.printStackTrace();
								} catch (IOException e) {									
									e.printStackTrace();
								}
								for (String key : properties2.stringPropertyNames()) {
								   servers.put(key, properties2.get(key).toString());
								}							
														
							    /*
							     * Iterate trough collection and match email address extension
							     * with servers settings if match found proceed further
							     */
									
							for (Map.Entry<String, String> entry : servers.entrySet()) {
								
									String input = entry.getKey().toString();
									Pattern pattern1 = Pattern.compile(input);
							//@userName ==> user email address 
									Matcher matcher1 = pattern1.matcher(userName);
									boolean matches_server = matcher1.matches();
								/*
								 * If match found servers collection call sendMail() function from send SendMail_TLS class
								 */
									//@perform check
									System.out.println("Iterating through HasMap:" + input);
									
									
									if(matches_server == true){
									  //@attache value to host variable
										String host = entry.getValue();	
										System.out.println("Host: " + host);								
										//@Perform Check on PROXY if SET UP	
											System.out.println("PROXY: " + PROXY);
										
										//@Retrieve Files
											EmailAttachmentReceiver.downloadEmailAttachments(host, userName, password);	
											
										//@Retrieve messages from file	
											try {
												BufferedReader bfr = new BufferedReader(new FileReader(userName + "_MESSAGES.ser"));
												String line;
												try {
													while((line = bfr.readLine()) != null){
														messages += line + "\n";														
													}
													bfr.close();
												} catch (IOException e) {
													
													e.printStackTrace();
												}
											} catch (FileNotFoundException e) {
												
												e.printStackTrace();
											}
						/**
						 * @Future functionality print into text area message context      
						 */
									textArea.setText(messages);
										   break;
										   
									}//end if(matches_server == true)
							}//end for (Map.Entry<String, String> entry : servers.entrySet())						
					}//end if(returnVal == JFileChooser.APPROVE_OPTION)
				}else{
					//@ Display Message if Email pattern will not match
					showMessageDialog("Check Email address format or password length!");
	
			  }//end if(matcher.matches() == true)
			}//end
		});
		btnConnect.setBounds(24, 283, 117, 25);
		contentPane.add(btnConnect);
		
		
		chckbxDecryptFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				HashFunction hash = new HashFunction();
				Bitshifter bit = new Bitshifter();
				AESFileDecryption fileDec = new AESFileDecryption();
				
				if(chckbxDecryptFile.isSelected()){
					
					try{
					
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					int returnVal = chooser.showOpenDialog(getParent());
					if(returnVal == JFileChooser.APPROVE_OPTION) {					
						/*
						 * Attach retrieved value to fileName and filePath
						 */
											      
					      fileName = chooser.getSelectedFile().getName();
					      filePath = chooser.getSelectedFile().getAbsolutePath();
					      int x = filePath.length();
					      int y = fileName.length();
					      String st = filePath.substring(0, ( x - y));
					      
					     /*
					      * Asset new value to filePath, and Unzip zipped folder 
					      * to the same directory as Zipped folder exists
					      */
					      filePath = st;	
					    
					      //@Perform check  
					      System.out.println(" File Chooser ==>  File Name: "+ fileName + "  File Path: " + filePath);
					     
					      if(fileName.contains(".zip")){
					      //@Unzip zipped folder, to the same directory as Zipped file exists
					        Zip.unZipIt(filePath + fileName, st);		
					      }
					      //Check for directory, if exists after unzipping
					      File file = new File(st + "TMP");
					      
					      if(file.isDirectory()){
					    	  //@Perform check
					    	  System.out.println("Directory Found ==> proceeding further !!!");
					    	  File file2 = new File(st + "TMP");
					    	 
					    	  //@add files found in directory to collection
					    	  File[] list = file2.listFiles();
					    	  
					 //@Iterate through collection, check for encrypted file					    	  					    	 
					    for(int i = 0; i < list.length;i++){
					    		  
					    	//perform check, list files in folder
					    	    System.out.println(list[i]);
					    	    
					    	    String tmp = list[i].toString();
					    	    
					    	    /**
					    	     * Main method checks for file extensions ==> do not change  !!!!
					    	     * @Check for file extension ==> (des) and (rsa) two main encryption file extensions 
					    	     * after encrypting file ....
					    	     */
					    if(tmp.contains(".des") || tmp.contains(".aes")){	
					    	    	
					    	    	String encryptedFile = tmp;
					    	    //@Perform Check
					    	    	System.out.println("Encrypted File name: " + encryptedFile);
					    	    	
					    	    //@check for AES Encryption File file extension (.des)
					    	    	if(encryptedFile.contains(".des")){
					    	    	
					    	    		//@show message	
					    	    		showMessageDialog("Pick a IV File to decrypt " + encryptedFile);					    	    		
					    	    	
					    	    	//@chose iv file			    	    	    
					    	    	    JFileChooser chooser2 = new JFileChooser();
										chooser2.setFileSelectionMode(JFileChooser.FILES_ONLY);
										int returnVal2 = chooser2.showOpenDialog(getParent());
										if(returnVal2 == JFileChooser.APPROVE_OPTION) {
											
											/*
											 * Attach retrieved value to IV Name and IV Path
											 */		
										      ivFile = chooser2.getSelectedFile().getName();
										      ivPath = chooser2.getSelectedFile().getAbsolutePath();
										      int t = ivPath.length();
										      int s = ivFile.length();
										      String st1 = ivPath.substring(0, ( t - s));
										      System.out.println("IV File name: " + ivFile + " IV File Path: " + st1);
										      
									//@show message	
										      showMessageDialog("Pick a Salt File to decrypt " + encryptedFile);
										      
							    	     //JFrame frame2 = new JFrame("JOptionPane showMessageDialog example");
							    	    //JOptionPane.showMessageDialog(frame2, "Pick a Salt File to decrypt " + encryptedFile);
							    	    
							    	    //@chose salt file
							    	    JFileChooser chooser3 = new JFileChooser();
							    	    chooser3.setFileSelectionMode(JFileChooser.FILES_ONLY);
							    	    int returnVal3 = chooser3.showOpenDialog(getParent());
							    	    
							    	if(returnVal3 == JFileChooser.APPROVE_OPTION){
							    	    	/*
							    	    	 * Attach retrieved value to Salt Name and Salt Path
							    	    	 */
								    	    	saltFile = chooser3.getSelectedFile().getName();
								    	    	saltPath = chooser3.getSelectedFile().getAbsolutePath();
								    	    	int u = saltPath.length();
								    	    	int p = saltFile.length();
								    	    	String st2 = saltPath.substring(0,( u - p));
								    	    	//perform check
								    	    	System.out.println("Salt File Name: " + saltFile + " Salt File Path: " + st2);
							    	    	
							    	   /*
							    	    * Perform selected files check procedure, if everything ok 
							    	    * rebuild password and decrypt file
							    	    */
							    	    	if(ivFile.contains("iv.enc") && saltFile.contains("salt.enc")){
							    	    		/*
							    	    		 * Decrypt File, Restore Password that were used to encrypt file, 
							    	    		 * filePath.length() + 4 ==> this is location of (TMP/) directory
							    	    		 */
							    	    		String password = encryptedFile.substring(filePath.length() + 4,(encryptedFile.length() - 14));
							    	    		
							    	    		//@perform check on password
							    	    		System.out.println("Password: ==> " + password);
							    	    		
							    	    		/**
												 * Call hash function to generate password,
												 * password to encrypt file is a hash value of the filename,
												 * create temporary value ==> (iter) to iterate through hash function
												 */
							    	    		
												int iter = (password.length() ^ 3) % 3;
												if(iter == 0){
													iter = iter + 121;
												}
																	
												for(int m = 0; m < iter; m++){
													//@generate hash of password
														password = hash.generateHash(password);
														//perform check
														System.out.println(m + " ==>:  " + password);										
												}
												
												/*
												 * Encrypt password that was used to encrypt file
												 */
												int [] chain = bit.buildChain((password.length()/2));
												password = bit.encrypt(password, chain);
												
											//@perform check on password
												System.out.println("Password after Encrypting: " + password);											
												
											//File output Location and decrypted file name
							    	    		String fileOutput = filePath + encryptedFile.substring(filePath.length() + 4,(encryptedFile.length() - 14));
							    	    		
							    	    	//@perform check on files locations before decrypting file
							    	    		System.out.println("\nEncrypted File: " + encryptedFile +"\nFileOutput: " + fileOutput +
							    	    				"\nPassword: " + password +  "\nSalt: " + saltPath + "\nivFile: " + ivPath);
							    	    		/**
							    	    		 * Decrypt Encrypted file and move that file one directory above as it was located 
							    	    		 */
							    	    		AESFileDecryption.fileDecryption(encryptedFile, fileOutput, password, saltPath, ivPath);
							    	    		
							    	    		//@perform check
							    	    		//System.out.println("File Decrypted");
							    	    		
							    	    		//@show message	
							    	    		showMessageDialog("File Decrypted and can be found in " + fileOutput + " directory.");

							    	    		/*
							    	    		 * Print decrypted file content, into text area, if file extension 
							    	    		 * format matches ==> most common text files extensions, that are saved in file ==> (file_Extensions.txt)
							    	    		 */
							    	    		//@ ask for user if he wishes to display decrypted file content
							    	    		JFrame question = new JFrame();
							    	    		//@display message
							    	    		String message = "If you wish to display Decrypted File, press YES Button otherwise press NO Button!";
							    	    		int answer = JOptionPane.showConfirmDialog(question, message);
							    	  /**
							    	   * @If Answer NO	
							    	   */
							    	    	if(answer == JOptionPane.NO_OPTION){
							    	    		//@change state of check box unselect if answer ==> NO	
							    	    			chckbxDecryptFile.setSelected(false);							    	    			
							    	    		}//if(answer == JOptionPane.NO_OPTION)
							    	  /**
							    	   * @If Answer YES  		
							    	   */
							    	  else if(answer == JOptionPane.YES_OPTION){

							    	    		List<String> fileExtensions = new ArrayList<String>();
							    	    		BufferedReader br = new BufferedReader(new FileReader("file_Extensions.txt"));			    	    		
							    	    		String line2;
							    	    		
							    	    		//@build collection
							    	    		while((line2 = br.readLine()) != null){
							    	    			fileExtensions.add(line2);		    	    			
							    	    		}//end while((line2 = br.readLine()) != null)
							    	    		
							    	    		//@Perform check
							    	    		System.out.println(fileExtensions);
							    	    									    	    		
							    	    for(int b = 0; b < fileExtensions.size();b++){
							    	    			    	    			
							    	    			//@perform check on file extensions
							    	    			 // System.out.println("File extensions: " + fileExtensions.get(b));
							    	    			//@Temporary variable that holds value of available file extensions
							    	    			  String ext = fileExtensions.get(b);
							    	    			  System.out.println("String ext: " + ext);
							    	    									    	    		
							    	    	if(fileOutput.contains(ext)){
														    	    			
							    	    		//@read decrypted file content ==> fileOutput
							    	    		
							    	    			BufferedReader bfr = new BufferedReader(new FileReader(fileOutput));
							    	    			String line;
							    	    			
							    	    			//@Temporary variable used to display output of decrypted file
							    	    			String out = "";
							    	    			
							    	    			while((line = bfr.readLine()) != null){
							    	    				out += line + "\n";							    	    								    	    				
							    	    			}//end while((line = bfr.readLine()) != null)
							    	    			
								    	    		//@display output in new window ==> showMessageDialog() method
								    	    			showMessageDialog(out);
								    	    		
								    	    		//@deselect check box
								    	    			chckbxDecryptFile.setSelected(false);
								    	    		//@reset fileName and Path
								    	    			fileName = null;
								    	    			filePath = null;
								    	    			
								    	    		//@perform check on fileName and filePath
								    	    			System.out.println("File name: " + fileName + " File Path: " + filePath);
								    	    			
								    	    			 /**
											        	  * Call Delete Directory method from Utility class
											        	  * it will deletes ==> (TMP) directory, that was located inside location
											        	  * of the attached file
											        	  */
								    	    			System.out.println("Directory will be deleted: " + st + "TMP");
							    	    				    File file_st = new File(st+"TMP");
							    	    				    if(file.exists()){
											        	       Settings.deleteDirectory(file_st);
											        	       System.out.println("Deleting Directory: " + file_st.toString());
							    	    				    }  	    	
							    	    				//@Exit Loop    
							    	    				  break;		
							    	    				  
							    	    	//@Perform action if file extension not found
							    	    		}else if(b == (fileExtensions.size() - 1)){
							    	    			
							    	    			//@show message	
							    	    			showMessageDialog("File Format not supported!");
								    	    		
							    	    			File file_st = new File(st+"TMP");
					    	    				    if(file.exists()){
									        	       Settings.deleteDirectory(file_st);
					    	    				    }  	    	
							    	    		}//end else if(b == (fileExtensions.size() - 1))
							    	    }//end for(int b = 0; b < fileExtensions.size();b++)
							    	    		
							    	    	}else{
							    	    	//@show message	
							    	    		showMessageDialog("Wrong files Selected, please chose IV and Salt files to decrypt file!");
							    	    		chckbxDecryptFile.setSelected(false);
							    	    		
							    	    	}//end if(ivFile.contains("iv.enc") && saltFile.contains("salt.enc"))							    	    	
							          }//end if(returnVal3 == JFileChooser.APPROVE_OPTION)									      
								    }//end if(returnVal2 == JFileChooser.APPROVE_OPTION)
					    	      }//end if(encryptedFile.contains(".des"))
					    	    }//end if(tmp.contains(".des"))
					    	    
							/**
							 * @Check if file exist and encrypted using RSA Encryption 
							 */

					    	else if(tmp.contains(".aes")){
					    	    	
					    	    //@perform check
					    	    	System.out.println("File Containing aes extension found ==> Proceding Further!\nEncrypted file name: " + tmp);
					    	   
					    	    //@Attach tmp value to new String rsaEncrypted	
					    	    	String rsaEncryptedFile = tmp;
					    	    	
					    	    //@perform check
					    	    	System.out.println("Encrypted File name after renaming: " + rsaEncryptedFile);
					    	    	
					    	    //@show message	
					    	    	showMessageDialog("Pick AES_ENCRYPTED_RSA File to decrypt " + rsaEncryptedFile);
				    	    		//JFrame frame = new JFrame("JOptionPane showMessageDialog example");
				    	    		//JOptionPane.showMessageDialog(frame, "Pick AES_ENCRYPTED_RSA File to decrypt " + rsaEncryptedFile);
				    	    	
				    	    	//@chose AES_ENCRYPTED_RSA file			    	    	    
				    	    	    JFileChooser chooser7 = new JFileChooser();
									chooser7.setFileSelectionMode(JFileChooser.FILES_ONLY);
									int returnVal7 = chooser7.showOpenDialog(getParent());
									if(returnVal7 == JFileChooser.APPROVE_OPTION) {
										
										/*
										 * Attach retrieved value to publicKey and publicKeyPath
										 */		
									      publicKey= chooser7.getSelectedFile().getName();
									      publicKeyPath = chooser7.getSelectedFile().getAbsolutePath();
									      int tt = publicKeyPath.length();
									      int ss = publicKey.length();
									      String st2 = publicKeyPath.substring(0, ( tt - ss));
									      
									    //@perform check  
									      System.out.println("Public Key: " + publicKey + "\nPublic Key Path: " + st2);
									      
									      if(publicKey.equalsIgnoreCase("AES_ENCRYPTED_RSA")){
									        //@perform check
									    	    System.out.println("publicKey valid: " + publicKey);
									    	  
									    	//@show message	
									    	    showMessageDialog("Enter RSA Private Key (private.der) to decrypt: " + rsaEncryptedFile);						    	  
									    	  
									    	//@chose RSA Private Key to decrypt AES Key ==> which is encrypted in AES_ENCRYPTED_RSA									    	  
									    	  JFileChooser chooser8 = new JFileChooser();
									    	  chooser8.setFileSelectionMode(JFileChooser.FILES_ONLY);
									    	  int returnVal8 = chooser8.showOpenDialog(getParent());
									    	  if(returnVal8 == JFileChooser.APPROVE_OPTION){
									    		  
									    		 rsaPrivateKeyPath = chooser8.getSelectedFile().getAbsolutePath();
									    		 rsaPrivateKey = chooser8.getSelectedFile().getName();
									    		
									    	//@perform check rsaPrivateKeyPath, rsaPrivateKey									    		  
									    		  if(rsaPrivateKey.contains("private.der")){
									    			  System.out.println("RSA Private Key: " + rsaPrivateKey + "\nRSA Private Key Path: " + rsaPrivateKeyPath);
									    			 
									    			   RSA_TEST secure = new RSA_TEST();
									    			   
									    			    /*
									    				 * Get instances of AES and RSA encryption Chippers
									    				 */
									    				    secure.FileEncryption();
									    			
									    			//@decrypt AES_ENCRYPTED_RSA file, restore AES Key that was used to encrypt file
									    			  secure.loadKey(new File(publicKeyPath), new File(rsaPrivateKeyPath));
									    			  
									    			  String outpuFile = filePath;
									    			  System.out.println("Key Loaded Successfully!\n\n");
									    			  System.out.println("File name: " + fileName + " File Path: " + filePath);
									    			  
									    			//@show message
									    			  showMessageDialog("Chose Location where you wish to save File!");
									    			 // JFrame location = new JFrame("JOptionPane showMessageDialog example");
									    			  //JOptionPane.showMessageDialog(location, "Chose Location where you wish to save File!");
									    			   
									    			//@chose RSA Private Key to decrypt AES Key ==> which is encrypted in AES_ENCRYPTED_RSA									    	  
											    	  JFileChooser chooser9 = new JFileChooser();
											    	  chooser9.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
											    	  int returnVal9 = chooser9.showOpenDialog(getParent());
											    	  if(returnVal9 == JFileChooser.APPROVE_OPTION){
											    		
											    		//@Location of file will be saved  
											    		 String saveLocation = chooser9.getSelectedFile().getCanonicalPath();
											    		 
											       //@ask for file name to save and extension
											    		 JFrame frameAsk = new JFrame();
											    		 String file_name = JOptionPane.showInputDialog(frameAsk, "Enter New File Name To Save Decrypted File!");
											    		 System.out.println("This is Location of File where it going to be saved: " + saveLocation);
											    	
											    	//@decrypt encrypted file and save to chosen location
										    			secure.decrypt(new File(rsaEncryptedFile),new File(saveLocation + "/" + file_name));
												    	System.out.println("File Decrypted and saved to: " + saveLocation + "/" + file_name);
												    	
												   //@ask user if he wishes to view decrypted file
									    	    		JFrame question2 = new JFrame();
									    	       
									    	    	//@display message
									    	    		String message2 = "If you wish to display Decrypted File, press YES Button otherwise press NO Button!";
									    	    		int answer2 = JOptionPane.showConfirmDialog(question2, message2);
									    	    		
									    	    		if(answer2 == JOptionPane.NO_OPTION){									    	    		
									    	    			//@deselect check box
									    	    			chckbxDecryptFile.setSelected(false);
									    	    		}
									    	    		
									    	    	//@perform action if user press YES Button
									    	    		else if(answer2 == JOptionPane.YES_OPTION){
									    	    		
									    	    	//@load file extension checker
									    	    			BufferedReader bfr = new BufferedReader(new FileReader("file_Extensions.txt"));
									    	    			List<String> fileExt = new ArrayList<String>();
									    	    			
									    	    	//@add to collection	
									    	    			String line3;
									    	    			while((line3 = bfr.readLine()) != null){
									    	    				fileExt.add(line3);				    	    				
									    	    			}//end while
									    	    			
									    	    			//@ new value to perform check on file extension
									    	    			String newFileName = saveLocation + "/" + file_name;
									    	    			
									    	    			//@perform check on newFileName
									    	    			System.out.println("After renaming new saved file name: " + newFileName);
									    	    			
									    	    			for(int g = 0; g < fileExt.size();g++){ 	    				
									    	    				String check = fileExt.get(g);
									    	    				//@perform check on collection
									    	    				System.out.println(check);
									    	    				
									    	    		//@perform check on file extensions
									    	    			if(newFileName.contains(check)){
									    	    					
									    	    				//@read file into buffer it will be displayed back to the user
											    	    			BufferedReader bf = new BufferedReader(new FileReader(newFileName));
											    	    			String line4;
											    	    			String output = "";
											    	    			
											    	    			while((line4 = bf.readLine()) != null){
											    	    				output += line4 + "\n";
											    	    			}//end while
											    	    			
											    	    		//@show message
											    	    			showMessageDialog(output);							    			  
													    			  
													    			//@deselect check box
												    	    			chckbxDecryptFile.setSelected(false);		
												    	    			
												    	    			/*
															        	  * Call Delete Directory method from Utility class
															        	  * it will deletes ==> (TMP) directory, that was located inside location
															        	  * of the attached file
															        	  */
											    	    				    File file_st = new File(st+"TMP");
											    	    				    if(file.exists()){
															        	       Settings.deleteDirectory(file_st);
											    	    				    }
											    	    				  //@ out of loop
											    	    				    break;
    	    			
									    	    				}else if(g == (fileExt.size() - 1)){			    	    			
											    	    			//show message	
									    	    					showMessageDialog("File Format not supported!" );
	                                                               
									    	    				//@Delete Directory
											    	    			File file_st = new File(st+"TMP");
									    	    				    if(file.exists()){
													        	       Settings.deleteDirectory(file_st);
									    	    				    }  	    	
											    	    		  }//end else if(g == (fileExt.size() - 1))
										    	    			}//end if(newFileName.contains(check))
									    	    			}//end for(int g = 0; g < fileExt.size();g++)									    	    			
									    	    		}//end else if(answer2 == JOptionPane.YES_OPTION)	
											    	 }//end if(returnVal9 == JFileChooser.APPROVE_OPTION)								    			  
									    		  }//end  if(rsaPrivateKey.contains("private.der"))
									    	  }//end  if(publicKey.equalsIgnoreCase("AES_ENCRYPTED_RSA"))
									      }//end  if(publicKey.equalsIgnoreCase("AES_ENCRYPTED_RSA"))				      
									}//end if(returnVal7 == JFileChooser.APPROVE_OPTION)
					    	    }//end else if(tmp.contains(".aes") || tmp.contains(".des"))
					    	  }//end for(int i = 0; i < list.length;i++)
					      }else{
					    	  //@Show Message
					    	  showMessageDialog("Directory can not be found nothing to decrypt!");
					    	  textArea.setText("Directory can not be found nothing to decrypt!");
					    	  chckbxDecryptFile.setSelected(false);
					      }//if(file.isDirectory())
					      
					}//end if(returnVal == JFileChooser.APPROVE_OPTION)
					}catch(Exception e1){
						e1.getMessage();
					}
				}//end if(chckbxDecryptFile.isSelected())
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainFrame main = new MainFrame();
				main.setVisible(true);
			}
		});
		btnBack.setBounds(24, 320, 117, 25);
		
		contentPane.add(btnBack);
		
		
		chckbxDecryptFile.setBounds(24, 233, 124, 23);
		contentPane.add(chckbxDecryptFile);
		
		
		chckbxFileSignature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(chckbxFileSignature.isSelected()){
					//@show message
					showMessageDialog("Choose File To Check Signature!");
    				  				
    			//@Choose directory where to save Email Attachments
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int returnVal = chooser.showOpenDialog(getParent());
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						//@Location of file 
			    		 try {
							String selected_file = chooser.getSelectedFile().getCanonicalPath();
							String selected_file_name = chooser.getSelectedFile().getName();
							File file = new File(selected_file);
							try {
							//@Generate Hash of file
								String fileHash = HashFunction.generateFileHash(file);
							//@Display Hash of file @show message
								showMessageDialog("File Signature:\n" + selected_file_name + ":\n " + fileHash);
								textArea.setText("File Signature:\n" + selected_file_name + ":\n " + fileHash);
							//@Unselect Check box
								chckbxFileSignature.setSelected(false);
							} catch (NoSuchAlgorithmException e) {
								e.printStackTrace();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}//end if(returnVal == JFileChooser.APPROVE_OPTION)		
				}//end if(chckbxFileSignature.isSelected())
			}
		});
		chckbxFileSignature.setBounds(152, 233, 129, 23);
		contentPane.add(chckbxFileSignature);
		/*
		 * @ TOR Proxy perform check 
		 */
		
		chckbxTor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(chckbxTor.isSelected()){
					try {
						String host = "127.0.0.1";
						int port = 9050;
						
						boolean reachable = "" != null;
						boolean tr = SendMail_TLS.checkConnection(host, port, reachable);
						
						if(tr == true){
							System.out.println("Working Proxy TOR : " + reachable);
							ear.proxy();	
							
							chckbxShowProxy.setSelected(false);
													
						}else{
							showMessageDialog("");
						}
						
						
					} catch (UnknownHostException e) {					
						e.printStackTrace();
						showMessageDialog("UnknownHostException");
					} catch (IOException e) {						
						e.getMessage();
						//@Show message
						showMessageDialog("TOR not Running or not installed on this machine! Please ensure that TOR runs on port 9050!");
						chckbxTor.setSelected(false);
					} 						
				}
			}
		});
		chckbxTor.setBounds(313, 233, 62, 23);
		contentPane.add(chckbxTor);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(317, 264, 250, 3);
		contentPane.add(separator);
		
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				PROXY = (String) comboBox.getSelectedItem();			
			}
		});
				
		comboBox.setBounds(313, 284, 162, 23);
		contentPane.add(comboBox);
		
		
		chckbxShowProxy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
			if(chckbxShowProxy.isSelected()){
				
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
						    System.out.println("Proxy Setting retrieve.");
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
				}//end if(chckbxShowProxy.isSelected())
			        					
			else{
				/**
				 * @Run LoadProxy Method if user reselects check box
				 */
				    LoadProxy();
			 }
	       }//end 	
		
		});
		chckbxShowProxy.setBounds(395, 233, 88, 23);
		contentPane.add(chckbxShowProxy);
		
		/**
		 * @User can view messages retrieved from other accounts
		 */
		chckbxReadMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(chckbxReadMessages.isSelected()){
					showUserEmailForm();
					userName = textField1.getText().trim();
					
					Pattern pattern = Pattern.compile(EMAIL_PATTERN);
					Matcher matcher = pattern.matcher(userName);
					
					if(matcher.matches() == true){
						
						File file = new File(userName + "_MESSAGES.ser");
						
					if(!file.exists()){
						//@Show Message
							showMessageDialog("Can not display messages, first retrieve messages!");
							chckbxReadMessages.setSelected(false);
							textField1.setText("");
							return;
						}else{							
								BufferedReader bfr = null;
								try {
									bfr = new BufferedReader(new FileReader(userName + "_MESSAGES.ser"));
								} catch (FileNotFoundException e) {									
									showMessageDialog("No Messages Found!");
								}
								String line;
								try {
									while((line = bfr.readLine()) != null){
										messages += line + "\n";
									}
									bfr.close();
								} catch (IOException e) {
									
									e.printStackTrace();
								}
								//@Display output to textArea
								textArea.setText(messages);
								chckbxReadMessages.setSelected(false);
								textField1.setText("");
							
						}
						
					}else{
					//@Show message if email adress not found or file do not exist
						showMessageDialog("Check Email address!");
						chckbxReadMessages.setSelected(false);
						textField1.setText("");
					}
				}
				
			}
		});
		chckbxReadMessages.setBounds(152, 260, 137, 23);
		contentPane.add(chckbxReadMessages);
		chckbxDeleteMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(chckbxDeleteMessages.isSelected()){
					
					showUserEmailForm();
					userName = textField1.getText().trim();
				
				//@Perform check on email address format
					Pattern pattern = Pattern.compile(EMAIL_PATTERN);
					Matcher matcher = pattern.matcher(userName);
					
					if(matcher.matches() == true){
						
						File file = new File(userName + "_MESSAGES.ser");
						if(!file.exists()){
							//@Show Message
							showMessageDialog("Nothing to delete, messages not found!");
							textField1.setText("");
							chckbxDeleteMessages.setSelected(false);
							
						}else{
							try {
								
							//@Secure Delete File
								Settings.secureDelete(file);
							} catch (IOException e) {
								
								e.printStackTrace();
							}
							//@Show Message
							showMessageDialog("Messages deleted!");
							textField1.setText("");
							chckbxDeleteMessages.setSelected(false);
						}
					}
					
				}
			}
		});
		chckbxDeleteMessages.setBounds(152, 284, 153, 23);		
		contentPane.add(chckbxDeleteMessages);
		
		final JLabel mail_icon = new JLabel();
		mail_icon.setIcon(new ImageIcon("img/hotmail.png"));
		mail_icon.setBounds(559, 357, 69, 56);
	    contentPane.add(mail_icon);
	}	
}
