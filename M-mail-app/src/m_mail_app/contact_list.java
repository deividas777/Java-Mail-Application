package m_mail_app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.io.*;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;



public class contact_list extends JFrame {

	protected static final AbstractButton chckbxNames = null;
	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblString;
	private JTextField textField_1;
	Utility utl = new Utility();
	private JLabel lblPassword;
	private JTextField textField_2;
	private JTextArea textAbout;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					contact_list frame = new contact_list();
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
	public contact_list() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 479, 363);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(28, 32, 114, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			  utl.createDirectory();   		  
				   try {
					     utl.createFile("names");
					      utl.createFile("passwords");
					       utl.createFile("emails");
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}	
				   
				   utl.loadNames();	
					 utl.loadEmails();
					  utl.loadPasswords();
					        				     
					        String name = textField.getText();
						      String email = textField_1.getText(); 
						       String password = textField_2.getText();
				     				    
				     if(name.length() > 0){			      
				 
				          utl.addNames(name);
				           utl.saveNames();    
				            textField.setText("");
				         			         
				     }
				     
				     if(email.length() > 0){
				    	 
				    	 utl.addEmails(email);
				    	  utl.saveEmails();
				    	   textField_1.setText("");
				     }
				     
				     if(password.length() > 0){
				    	 utl.addPasswords(password);
				    	  utl.savePasswords();
				    	   textField_2.setText("");
				    	 }
				     
				     else{
				    	 textAbout.setText("Name and Email must contain at least 1 letter");
				     } 
				   textAbout.setText("Name: " + name + " Email:" + email
						   + "Passwords: " + password);
				      
		   } 		
		});
		
		
		create.setBounds(38, 236, 92, 25);
		contentPane.add(create);
		
		JButton Back = new JButton("Back");
		Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
			}
		});
		Back.setBounds(368, 299, 87, 25);
		contentPane.add(Back);
		
		lblString = new JLabel("Name:");
		lblString.setBounds(28, 0, 70, 19);
		contentPane.add(lblString);
		
		textField_1 = new JTextField();
		textField_1.setBounds(28, 95, 114, 30);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblDigit = new JLabel("Email:");
		lblDigit.setBounds(28, 69, 70, 15);
		contentPane.add(lblDigit);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(28, 137, 70, 15);
		contentPane.add(lblPassword);
		
		textField_2 = new JTextField();
		textField_2.setBounds(28, 164, 114, 30);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textAbout = new JTextArea(11, 5);
		textAbout.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textAbout);
		scrollPane.setBounds(161, 11, 263, 197);
		contentPane.add(scrollPane);
	}
}
