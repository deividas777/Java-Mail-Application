package m_mail_app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;

import javax.crypto.*;


import static m_mail_app.RsaEncryption.PUBLIC_KEY_FILE;
import static m_mail_app.RsaEncryption.encrypt;
import javax.swing.JTextArea;

public class EncryptMessages extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	localHost local = new localHost();
	Bitshifter shifter = new Bitshifter();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EncryptMessages frame = new EncryptMessages();
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
	public EncryptMessages() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 527, 334);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(91, 174, 389, 84);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(91, 12, 234, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(91, 53, 234, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(91, 96, 234, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(312, 143, 114, 19);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(91, 143, 125, 19);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("From:");
		lblNewLabel.setBounds(12, 14, 70, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("To:");
		lblNewLabel_1.setBounds(12, 55, 70, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Subject:");
		lblNewLabel_2.setBounds(12, 98, 70, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(12, 145, 96, 15);
		contentPane.add(lblPassword);
		
		JLabel lblServer = new JLabel("Server:");
		lblServer.setBounds(238, 145, 70, 15);
		contentPane.add(lblServer);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(12, 192, 70, 15);
		contentPane.add(lblMessage);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				String from = textField.getText();
				 String to = textField_1.getText();
				  String subject = textField_2.getText();
				   String password = textField_3.getText();
				    String server = textField_4.getText();
				     String messageBody = textArea.getText();

			    			    			 
				    		   //if(!RsaEncryption.areKeysPresent())
				    		   //{ RsaEncryption.generateKey(1024);}
							 
				    		try{
				    			
				    		   ObjectInputStream inputStream = null;
								 inputStream = new ObjectInputStream(new FileInputStream(RsaEncryption.PUBLIC_KEY_FILE));								   
								  final PublicKey publicKey = (PublicKey) inputStream.readObject();
								   final byte[] cipherText = RsaEncryption.encrypt(messageBody, publicKey);
								    inputStream.close();
								   							    
								    
								      System.out.println("Original Message: " + messageBody);
								      System.out.println("Ciphertext: " + cipherText.toString());
								      textArea.setText("");
								      textArea.append(new String(cipherText));
								      
								   
								    //String encryptedText = shifter.encrypt(messageBody = new String(cipherText));						     
							          //local.sendMail(server, from, to, subject, textField_5.getText(), "");	
								       //local.sendMail(server, from, to, subject, textField_5.getText(), "");
								      
						    ObjectInputStream inputStream2= null;
							 inputStream2 = new ObjectInputStream(new FileInputStream(RsaEncryption.PRIVATE_KEY_FILE));
						      final PrivateKey privateKey = (PrivateKey) inputStream2.readObject();	
						       String text = textArea.getText();
						        byte[] text2 = text.getBytes();
						        
						       //System.out.println("Decrypted text:" + text2);
						         
						        final String plainText = RsaEncryption.decrypt(text2, privateKey);
						         inputStream2.close();
						         System.out.println("Decrypted: " + plainText);
						        
						         
						       
								    
						} catch(Exception y){
			    			y.getMessage();
			    		}			
			}	
		});
		btnSend.setBounds(238, 270, 117, 25);
		contentPane.add(btnSend);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
			}
		});
		btnBack.setBounds(386, 270, 117, 25);
		contentPane.add(btnBack);
		
		
	}
}
