package m_mail_app;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JScrollPane;

import java.awt.Font;
import javax.swing.JSeparator;


public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3311578534357379033L;
	private JPanel contentPane;
	private JTextArea textAbout;
	private JTextField textField1 = new JTextField();
	
	
	/**
	 * @Fields used to get user and password details
	 */
	
	private JPasswordField password_Field = new JPasswordField();
	private JTextField userName = new JTextField();
	
	/**
	 * @Mehods
	 */
	
    public void showUserEmailForm(){
		
		Object[] field = {"Email", textField1};		
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");
		JOptionPane.showConfirmDialog(frame, field, "Email Address", JOptionPane.OK_CANCEL_OPTION);
	}
    
   public void showSearchForm(){
		
		//@multiple fields
			Object[] fields = {"User", userName,
					           "Password", password_Field,
					           "Search", textField1
					           };		
			JFrame login = new JFrame("JOptionPane showMessageDialog example");
			JOptionPane.showConfirmDialog(login, fields, "Login Form", JOptionPane.OK_CANCEL_OPTION);
		}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 282);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		mnNewMenu.add(mntmExit);
		
		JMenu mnNewMenu_1 = new JMenu("Properties");
		menuBar.add(mnNewMenu_1);
		
		JMenu mnNewMenu_2 = new JMenu("About");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("About the Program");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "Version 2 Updated on 17/11/2013 This program is\n designed to send emails to the users." +
						     "\nYou can choose to send emails from legitamate\nGoogle accounts or fake emails using local host.\n";
				str += "In localhost:\n" +
					   "f = Random source emails\n";
				str += "r = Source and Destination random emails\n";
				str += "a = same as r with random messages\n";
				str += "To find local host server use nslookup tool and \nset q=mx and your ISP providers address \neg. eircom.net, vodafone.ie\n";
				str += "And then enter the mail exchange server\n";
				str += "Added Encryption Option, RSA, Bitshifter and SHA-1 generation function!";
				JOptionPane.showMessageDialog(null, str);
				
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textAbout = new JTextArea(11, 5);
		textAbout.setFont(new Font("Courier New", Font.BOLD, 14));
		textAbout.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textAbout);
		scrollPane.setBounds(208, 11, 325, 197);
		contentPane.add(scrollPane);
				
		JButton btnGoogle = new JButton("Send Mail");
		btnGoogle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
                Google google = new Google();
                google.setVisible(true);
			}
		});
		
		JButton btnShowMessages = new JButton("Read Mail");
		btnShowMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String userName = "";
				
			//@Email Form	
				showUserEmailForm();
				userName = textField1.getText().trim();

			//@Perform validation on email pattern
				Pattern pattern = Pattern.compile(Mail_Retrieval.EMAIL_PATTERN);
				Matcher matcher = pattern.matcher(userName);
				
				
				if(matcher.matches() == true){
					
					File file = new File(userName + "_MESSAGES.ser");
					
				if(!file.exists()){
					//@Show Message
						JOptionPane.showMessageDialog(null,"Can not display messages, first retrieve messages!");
						textField1.setText("");
						return;
						
					}else{	
						
							BufferedReader bfr = null;
							try {
								
								bfr = new BufferedReader(new FileReader(userName + "_MESSAGES.ser"));
								
							} catch (FileNotFoundException e2) {									
								JOptionPane.showMessageDialog(null,"No Messages Found!");
							}
							String line;
							String messages = "";
							
							try {
								while((line = bfr.readLine()) != null){
									messages += line + "\n";
								}
								bfr.close();
								
								//@Display output to textArea
								textAbout.setText(messages);
								textField1.setText("");
								
							} catch (IOException e1) {
								
								e1.printStackTrace();
							}						
					}//end else
					
				}else{
				//@Show message if email adress not found or file do not exist
					JOptionPane.showMessageDialog(null,"Check Email address!");
					textField1.setText("");
				}
			}				
				
		});
		btnShowMessages.setBounds(30, 12, 153, 23);
		contentPane.add(btnShowMessages);
		btnGoogle.setBounds(30, 47, 153, 25);
		contentPane.add(btnGoogle);
		
		JButton btnSearch = new JButton("Search Contact");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
			//@Variable to store search results			
				String result = "";
				
			//@Delete file with old search result
				File file = new File("search.ser");
				if(file.exists()){
					file.delete();
				}
			
			//@Display Search Form
			    showSearchForm();
				final String user= userName.getText();
				final String password = password_Field.getSelectedText();
				String search = textField1.getText().replaceAll("\\W", "").trim();
			
		//@Validate search 
				
			if(search.length() > 1 && search.length() < 30){
						
			//@Call Search Method in FirstExample class ==> search result will be written into a file (search.ser)	
			
				FirstExample.SQLConnector(user,password);
				FirstExample.search(search);
				BufferedReader br = null;
				
			    try {
					 br = new BufferedReader(new FileReader("search.ser"));
					 String line;
					try {
						while((line = br.readLine()) != null){
							result += line + "\n";
						}
						
					//@Print OUtput to textArea
					    textAbout.setText(result);
					    
					//@Close buffered reader
						br.close();
						
					//@Perform check on result 	
						if(result.length() <= 1){
						
						//@Display Message
							JOptionPane.showMessageDialog(null, "Contact not found on SERVER DB connecting to local DB .... ");
							
						//@Create new BufferedReader	
							BufferedReader br2 = null;
						
						//@Connect to Sqlite DB
							Sqlite_Database.dbConnector();
							Sqlite_Database.search(search);
							
							File file2 = new File("sqlite_search.ser");
						
						//@Perform check on file size
							if(file2.length() > 1){
								
								br2 = new BufferedReader(new FileReader(file2));
								String line2;
								while((line2 = br2.readLine()) != null){
	                               result += line2 + "\n";
								}
								br2.close();
								textAbout.setText(result);								
							}//end if
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(null, "Check search field");
			}
			    
			}		
		});
		
		
		
		final JButton btnRetrieveMail = new JButton("Retrieve Mail");
		btnRetrieveMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				dispose();
                Mail_Retrieval mail = new Mail_Retrieval();
                mail.setVisible(true);
			}
		});
		btnRetrieveMail.setBounds(30, 84, 153, 25);
		contentPane.add(btnRetrieveMail);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(30, 121, 153, 2);
		contentPane.add(separator);
		
		btnSearch.setBounds(30, 135, 153, 23);
		contentPane.add(btnSearch);
		
		final JButton btnNewButton = new JButton("Contacts");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				dispose();
				SQL_Database sql = new SQL_Database();
				sql.setVisible(true);				
			}
		});
		btnNewButton.setBounds(30, 170, 153, 25);
		contentPane.add(btnNewButton);
	}
}