package m_mail_app;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.util.Random;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class GUI extends JFrame {
	
	protected static final Component emptyLabel = null;
	private String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private Random rnd = new Random();
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JLabel lblTo;
	private JLabel lblSubject;
	private JLabel lblServer;
	private JLabel lblMessage;
	private JTextField textField_5;
	
	Utility utl = new Utility();
	private JTextField textField_6;
	private JTextField textField_8;
	private JCheckBox chckbxSingEncrypt;
	private JLabel lblAttachfile;
	private JTextField textField_9;
	private JLabel lblNumberOfFiles;
	private JTextField textField_10;
	private JCheckBox chckbxSymmetricEncryption;
	private JTextField textField_11;
	private JCheckBox chckbxAesencryption;
	private JEditorPane editorPane;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		setForeground(new Color(224, 255, 255));
		setBackground(SystemColor.desktop);
		setFont(UIManager.getFont("Button.font"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 417);
		contentPane = new JPanel();
		contentPane.setToolTipText("Enter Y if you wanna generate random emails");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(87, 23, 341, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					localHost local = new localHost();
					 String from = textField.getText();
					  String to   = textField_1.getText();
					   String mailServer = textField_2.getText();
					    String subject = textField_3.getText();
					     String messageBody = textField_4.getText();
					      String str = textField_6.getText();	
					      int number =Integer.parseInt(textField_5.getText());				       
					       
					      //int count = Integer.parseInt(textField_10.getText());	
									     
					        String []attachments = new String[3];
					        attachments[0] = "/root/Desktop/keys2/scrambled";
					        attachments[1] = "/root/Desktop/keys2/encrypted_RSA_RandomBytes";	
					        attachments[2] = "/root/Desktop/keys2/encrypted_Signature";
						 
						// FileInputStream fos = new FileInputStream("/root/Desktop/keys2/signed_decrypted");
						// BufferedInputStream bis = new BufferedInputStream(fos);
						 
						// ObjectInputStream inputStream = null;
						//  inputStream = new ObjectInputStream(new FileInputStream(RsaEncryption.PUBLIC_KEY_FILE));
						//   final PublicKey publicKey = (PublicKey) inputStream.readObject();
						    //final String cipherText = RsaEncryption.decrypt(bis, publicKey);
						 //    inputStream.close();
						     
						 //   StringBuffer sb = new StringBuffer();
					     //    for (int i = 0; i < cipherText.length; i++) {
					      //    sb.append(Integer.toString((cipherText[i] & 0xff) + 0x100, 16).substring(1));
					      //   }
						    
						  //   textField_4.setText(null);
						  //    textField_4.setText("Encoded with RSA:/n" + sb.toString());
						       
						  //    if(textField_8.getText().length() > 0){
						    	  
						   //      local.sendMail(mailServer, from, to, subject, new String(cipherText) + "//n" + textField_8.getText(), attachments);
					       //   }
						      				
					
					if(str.equalsIgnoreCase("f")){
						
						utl.loadEmails();
				      
						  for( int i = 0; i < number; i++ ) {	 
							  if(textField_8.getText().length() > 0){
					             local.sendMail(mailServer, utl.pickEmails(), to, subject, messageBody + textField_8.getText(), attachments);
					       }
							  else {local.sendMail(mailServer, utl.pickEmails(), to, subject, messageBody, attachments);}
					    }
			        }
					
					if(str.equalsIgnoreCase("r")){
						
						utl.loadEmails();
						
						 for(int i = 0; i < number; i++){
							 if(textField_8.getText().length() > 0){
					             local.sendMail(mailServer, utl.pickEmails(), to, subject, messageBody + textField_8.getText(), attachments);
					       }
							 else{ local.sendMail(mailServer, utl.pickEmails(), utl.pickEmails(), subject, messageBody, attachments);}
					      }
				        }
					
					if(str.equalsIgnoreCase("a")){
						
						utl.loadEmails();
						utl.loadMessages();
						
						for(int i = 0; i < number; i++){
							if(textField_8.getText().length() > 0){
					             local.sendMail(mailServer, utl.pickEmails(), to, subject, messageBody + textField_8.getText(), attachments);
					       }
							else{local.sendMail(mailServer, utl.pickEmails(), utl.pickEmails(), subject, utl.pickMessages(), attachments);}
						}
					}
					
				else {
					 
					for(int i = 0; i < number; i++){
					 				 
					            local.sendMail(mailServer, from, to, subject, messageBody, attachments);
					         }
				     }
					
				}catch(Exception e){
					e.getMessage();
				}			
				
			}
		});
		btnNewButton.setBounds(306, 339, 117, 25);
		contentPane.add(btnNewButton);
		
		textField_1 = new JTextField();
		textField_1.setBounds(87, 54, 341, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(306, 85, 122, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(87, 85, 150, 19);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(87, 169, 481, 85);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("From:");
		lblNewLabel.setBounds(12, 25, 70, 15);
		contentPane.add(lblNewLabel);
		
		lblTo = new JLabel("To:");
		lblTo.setBounds(12, 56, 67, 15);
		contentPane.add(lblTo);
		
		lblSubject = new JLabel("Subject:");
		lblSubject.setBounds(12, 87, 70, 15);
		contentPane.add(lblSubject);
		
		lblServer = new JLabel("Server");
		lblServer.setBounds(256, 87, 60, 15);
		contentPane.add(lblServer);
		
		lblMessage = new JLabel("Message:");
		lblMessage.setBounds(12, 193, 70, 15);
		contentPane.add(lblMessage);
		
		textField_5 = new JTextField();
		textField_5.setBounds(102, 125, 40, 19);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNoOfMails = new JLabel("No of mails:");
		lblNoOfMails.setBounds(12, 127, 78, 15);
		contentPane.add(lblNoOfMails);
		
		JButton btnExit = new JButton("Back");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
			}
		});
		btnExit.setBounds(451, 339, 117, 25);
		contentPane.add(btnExit);
		
		textField_6 = new JTextField();
		textField_6.setBounds(224, 125, 40, 19);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblRandom = new JLabel("Random:");
		lblRandom.setToolTipText("Enter 'f' 'r' 'a'");
		lblRandom.setBounds(160, 127, 60, 15);
		contentPane.add(lblRandom);
		
		textField_8 = new JTextField();
		textField_8.setBounds(160, 290, 316, 27);
		contentPane.add(textField_8);
		textField_8.setColumns(10);
		
		final JCheckBox chckbxNewCheckBox = new JCheckBox("Hash");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String sha1;
				
				if(chckbxNewCheckBox.isSelected() && textField_4.getText().length() > 0){
				
					textField_8.setText(null);
					HashFunction function = new HashFunction();
					try{
						
					sha1 = function.generateHash(textField_4.getText());
					textField_8.setText(sha1);	

					}catch(Exception e){
						e.getMessage();
					}
				
				}else{
					textField_8.setText(null);
					sha1 = null;
				}
			}
		});
		chckbxNewCheckBox.setBounds(27, 293, 115, 21);
		contentPane.add(chckbxNewCheckBox);
		
	 
		final JCheckBox chckbxSingEncrypt = new JCheckBox("Sing & Encrypt");
		chckbxSingEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(chckbxSingEncrypt.isSelected() && textField_4.getText().length() > 0){
					
					textField_8.setText(null);
					HashFunction function = new HashFunction();
					
					try{
						
					String sha1 = function.generateHash(textField_4.getText());
					textField_8.setText(sha1);
				    FileOutputStream fos = new FileOutputStream("/root/Desktop/keys2/encrypted_Signature");
					
				    ObjectInputStream inputStream = null;
					  inputStream = new ObjectInputStream(new FileInputStream(RsaEncryption.PRIVATE_KEY_FILE));
					   final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
					    final byte[] cipherText = RsaEncryption.encrypt_Sign(textField_8.getText(), privateKey);
					    fos.write(cipherText);
					     inputStream.close();
					      fos.close();
					    
					    StringBuffer sb = new StringBuffer();
				        for (int i = 0; i < cipherText.length; i++) {
				         sb.append(Integer.toString((cipherText[i] & 0xff) + 0x100, 16).substring(1));
				        }
					    
					      textField_8.setText(null);
					      textField_8.setText(new String(sb));
					
					
					}catch(Exception e){
						e.getMessage();
					}
				
				}else{
					textField_8.setText(null);
				}
					
				}		
		});
		chckbxSingEncrypt.setBounds(27, 260, 129, 18);
		contentPane.add(chckbxSingEncrypt);
		
		lblAttachfile = new JLabel("Attach_File:");
		lblAttachfile.setBounds(463, 86, 92, 17);
		contentPane.add(lblAttachfile);
		
		textField_9 = new JTextField();
		textField_9.setBounds(451, 125, 117, 19);
		contentPane.add(textField_9);
		textField_9.setColumns(10);
		
		lblNumberOfFiles = new JLabel("Number of Files:");
		lblNumberOfFiles.setBounds(448, 24, 120, 17);
		contentPane.add(lblNumberOfFiles);
		
		textField_10 = new JTextField();
		textField_10.setBounds(463, 54, 52, 19);
		contentPane.add(textField_10);
		textField_10.setColumns(10);
		
	    final JCheckBox chckbxSymmetricEncryption = new JCheckBox("Bit Shifter =>");
		chckbxSymmetricEncryption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Bitshifter shifter = new Bitshifter();
				int date = (int) (new Date().getTime()/1000);
				
				String encrypted = "";
				int number = Integer.parseInt(textField_11.getText());
				
				System.out.println(date/number);
				
				if(textField_11.getText().isEmpty() || number == 0 || number > 9999){
					textField_4.setText("Key Length cannot be zero or longer than 9999!");
					return;					
				}
				
				final int[] ck = new int[number];
				
				//Build array of integers
				for(int i = 0; i < ck.length; i++){
					ck[i] = (i + 2)^(date/number) * 543 % 233;
				}
				
				for(int i = 0; i < ck.length; i++){
				System.out.println(i + ": " +ck[i]);
				}
							
				if(chckbxSymmetricEncryption.isSelected() && textField_4.getText().length() > 0){
					/*
					 * Call shifter function from Bitshifter class
					 **/
					encrypted = shifter.encrypt(textField_4.getText(), ck);
					textField_4.setText(encrypted);
					
				}else{ //if checkbox unselected call decryption function
					
					encrypted = textField_4.getText();
					String decrypt = shifter.decrypt(encrypted, ck);
					textField_4.setText(decrypt);
				}//end if statement 
			  
			}
		});
		chckbxSymmetricEncryption.setBounds(160, 258, 130, 22);
		contentPane.add(chckbxSymmetricEncryption);
		
		textField_11 = new JTextField();
		textField_11.setBounds(393, 260, 52, 18);
		contentPane.add(textField_11);
		textField_11.setColumns(10);
		
		JLabel lblbuildKey = new JLabel("Key Length:");
		lblbuildKey.setBounds(302, 261, 98, 17);
		contentPane.add(lblbuildKey);
		
		final JCheckBox chckbxAesencryption = new JCheckBox("AES_Encryption");
		chckbxAesencryption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String enc;
				String dec;
				
				AES_Encryption encryption = new AES_Encryption();
				RsaEncryption rsa = new RsaEncryption();
				
				
				if(chckbxAesencryption.isSelected() && textField_4.getText().length() > 0){
					if(!rsa.areKeysPresent()){
					 rsa.generateKey(2048);
				 }
					try{					
						encryption.aesKeyGen();
						enc = encryption.aesEncrypt(textField_4.getText());					
						textField_4.setText(enc);
						rsa.encryptSessionKey();										
					}
					catch(Exception e2){
						e2.getMessage();
					}
					
				}else {
					try{
						encryption.aesDecrypt();
						dec = encryption.aesDecrypt();
						textField_4.setText(dec);
						
					}catch(Exception e3){
						e3.getMessage();
					}
				}
			}
		});
		chckbxAesencryption.setBounds(291, 123, 137, 22);
		contentPane.add(chckbxAesencryption);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Decryptor_GUI dec = new Decryptor_GUI();
				dec.setVisible(true);
				
			}
		});
		btnDecrypt.setBounds(27, 339, 115, 25);
		contentPane.add(btnDecrypt);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane, textField, btnNewButton, textField_1, textField_2, textField_3, textField_4, lblNewLabel, lblTo, lblSubject, lblServer, lblMessage, textField_5, lblNoOfMails, btnExit, textField_6, lblRandom, textField_8, chckbxNewCheckBox, chckbxSingEncrypt}));
	}
}