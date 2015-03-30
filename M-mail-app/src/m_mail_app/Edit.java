package m_mail_app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Collections;
import javax.swing.JLabel;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import javax.swing.JRadioButton;
import java.awt.SystemColor;
//import com.jgoodies.forms.factories.DefaultComponentFactory;

public class Edit extends JFrame {

	private JPanel contentPane;
	private static JTextArea textAbout;
	private JTextField textSearch;
	private JButton btnRemove;
	private JButton btnAdd;
	private JTextField textField;
	private JButton btnExit;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblNewLabel;
	private static String message = "Use populate list function to import\nNames, Emails, Messages or Passwords" +
			"\ninto your collections!\nUse remove duplicates function to remove duplicates\nin your collections!";
	private JTextField textField_3;
	private JRadioButton rdbtnEmails;
	private JRadioButton rdbtnMessages;
	private JLabel lblRemoveDuplicates;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit frame = new Edit();
					frame.setVisible(true);
					textAbout.setText(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Edit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 342);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
	
	
	textAbout = new JTextArea(11, 5);
	textAbout.setEditable(false);
	JScrollPane scrollPane = new JScrollPane(textAbout);
	scrollPane.setBounds(240, 11, 290, 197);
	contentPane.add(scrollPane);
	
	/*Remove from list */
	
	btnRemove = new JButton("Remove");
	btnRemove.setToolTipText("Enter name of collection and name of the object to remove from the collection");
	btnRemove.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			Utility utl = new Utility();
			  		   
			    String str = textField_1.getText();
			  int result = -1;
			  
			  if(textField.getText().length() > 0 && textField.getText().equalsIgnoreCase("messages")){
				  Utility.loadMessages();
				  textAbout.setText(utl.search(Utility.messages, str));
				   result = Collections.binarySearch(Utility.messages, str);
				  utl.removeMessage(result);			  
				  Utility.saveMessages();
			   }
			  
			  else if(textField.getText().length() > 0 && textField.getText().equalsIgnoreCase("names")){
				  utl.loadNames();
				   textAbout.setText(utl.search(Utility.names, str));
				   result = Collections.binarySearch(Utility.names, str);
				  utl.removeName(result);

			   utl.saveNames();
			   }
			  
			  else if(textField.getText().length() > 0 && textField.getText().equalsIgnoreCase("emails")){
				  Utility.loadEmails();
				  textAbout.setText(utl.search(Utility.emails, str));
				   result = Collections.binarySearch(Utility.emails, str);
				  utl.removeEmail(result);			  
				  Utility.saveEmails();
			   }
			  
			  else if(textField.getText().length() > 0 && textField.getText().equalsIgnoreCase("passwords")){
				  Utility.loadPasswords();
				  textAbout.setText(utl.search(Utility.passwords, str));
				   result = Collections.binarySearch(Utility.passwords, str);
				  utl.removePassword(result);			  
				  Utility.savePasswords();
			   }
			  
			  
			  
			  else{
				   textAbout.setText("Not Found:" + str);
			   }
			
		}
	});
	btnRemove.setBounds(173, 267, 117, 25);
	contentPane.add(btnRemove);
	
	/*Add records to existing collections
	 * */
	
	btnAdd = new JButton("Populate");
	btnAdd.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			Utility utl = new Utility();
	         String coll = textField.getText();
	          String name = textField_1.getText();
	           String path = textField_2.getText();
	        
	        if(coll.length() >  0 && coll.equalsIgnoreCase("names")){
	        	try {
					utl.populateList(utl.names,"names", name, path);
				} catch (IOException e) {
					
					e.getMessage();
				}
	        	
	        }
	        
	        else if(coll.length() >  0 && coll.equalsIgnoreCase("passwords")){
	        	try {
					utl.populateList(utl.passwords,"passwords", name, path);
				} catch (IOException e) {
					
					e.getMessage();
				}
	        	
	        }
	        
	        else  if(coll.length() >  0 && coll.equalsIgnoreCase("emails")){
	        	try {
					utl.populateList(utl.emails,"emails", name, path);
				} catch (IOException e) {
					
					e.getMessage();
				}
	        	
	        }
	        
	        else if(coll.length() >  0 && coll.equalsIgnoreCase("messages")){
	        	try {
					utl.populateList(utl.messages,"messages", name, path);
				} catch (IOException e) {
					e.getMessage();
				}
	        	textAbout.setText("Success");
	        } 
	        else{
	        	textAbout.setText("Enter collection name(names, passwords, emails. Name of the file/n" +
	        			"(name.txt) and path to the file (/root/Desktop/).");
	        }
			
		}
	});
	
	btnAdd.setBounds(302, 267, 117, 25);
	contentPane.add(btnAdd);
	
	textField = new JTextField();
	textField.setBounds(27, 18, 119, 19);
	contentPane.add(textField);
	textField.setColumns(10);
	
	btnExit = new JButton("Back");
	btnExit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			MainFrame frame = new MainFrame();
			frame.setVisible(true);
		}
	});
	btnExit.setBounds(431, 267, 117, 25);
	contentPane.add(btnExit);
	
	textField_1 = new JTextField();
	textField_1.setBounds(27, 65, 119, 19);
	contentPane.add(textField_1);
	textField_1.setColumns(10);
	
	JLabel lblListName = new JLabel("Collection:");
	lblListName.setToolTipText("Names, Passwords or Emails");
	lblListName.setBounds(32, 0, 81, 15);
	contentPane.add(lblListName);
	
	JLabel lblName = new JLabel("Name:");
	lblName.setBounds(32, 49, 70, 15);
	contentPane.add(lblName);
	
	textField_2 = new JTextField();
	textField_2.setBounds(27, 119, 119, 19);
	contentPane.add(textField_2);
	textField_2.setColumns(10);
	
	lblNewLabel = new JLabel("Path to File:");
	lblNewLabel.setBounds(32, 96, 111, 15);
	contentPane.add(lblNewLabel);
	
	JLabel lblSearch = new JLabel("Search:");
	lblSearch.setBounds(32, 150, 60, 15);
	contentPane.add(lblSearch);
	
	textField_3 = new JTextField();
	textField_3.setBounds(29, 177, 122, 19);
	contentPane.add(textField_3);
	textField_3.setColumns(10);
	
	JButton btnSearch = new JButton("Search");
	btnSearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			if(textField_3.getText().length() > 0){
				Utility util = new Utility();
				String str = "Names List:\n";
				str += util.search(Utility.names, textField_3.getText());
				str += "\nPasswords List:\n";
				str += util.search(Utility.passwords, textField_3.getText());
				str += "\nEmails List:\n";
				str += util.search(Utility.emails, textField_3.getText());
				str += "\nMessages List:\n";
				str += util.search(Utility.messages, textField_3.getText());			
				textAbout.setText(str);
				
				}
		}
	});
	btnSearch.setBounds(29, 267, 117, 25);
	contentPane.add(btnSearch);
	
	final JRadioButton rdbtnNames = new JRadioButton("Names");
	rdbtnNames.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(rdbtnNames.isSelected()){
				Utility utl = new Utility();
				utl.loadNames();
				Set<String> set = new HashSet<String>(Utility.names);
				Utility.names = null;
				 Utility.names = new ArrayList<String>(set);
				  Collections.sort(Utility.names);
				textAbout.setText(utl.listNames() + "\nNumber of Names: "+ Utility.names.size());
				utl.saveNames();
			}else{
				textAbout.setText("");
			}
		}
	});
	rdbtnNames.setBounds(287, 237, 81, 18);
	contentPane.add(rdbtnNames);
	
	final JRadioButton rdbtnPasswords = new JRadioButton("Passwords");
	rdbtnPasswords.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(rdbtnPasswords.isSelected()){
				/*Load Passwords from file*/
					
					
					Utility.loadPasswords();
				
				/*Remove Duplicates by moving passwords into hash collection, resetting  
				 * collection and adding back to collection*/
					Set<String> set = new HashSet<String>(Utility.passwords);
					Utility.passwords = null;
					Utility.passwords = new ArrayList<String>(set);
				 
				 /*Sort collections */
					  Collections.sort(Utility.passwords);
					  textAbout.setText(Utility.listPasswords() + "\nNumber of Passwords: " + Utility.passwords.size());	
					
				/*Save sorted Passwords back to the  file*/
					  Utility.savePasswords();

				
			}else{
				textAbout.setText("");
			}
			
		}
	});
	rdbtnPasswords.setBounds(144, 237, 111, 18);
	contentPane.add(rdbtnPasswords);
	
	final JRadioButton rdbtnEmails = new JRadioButton("Emails");
	rdbtnEmails.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(rdbtnEmails.isSelected()){
				/*Load Passwords from file*/
				
						Utility utl = new Utility();			
						Utility.loadEmails();
			    /*Remove Duplicates by moving passwords into hash collection, resetting  
				 * collection and adding back to collection*/
					
						 Set<String> set = new HashSet<String>(utl.emails);
						 Utility.emails = null;
						 Utility.emails = new ArrayList<String>(set);
			   /*Sort collections */
					   Collections.sort(Utility.emails);		 				  
				       textAbout.setText(utl.listEmails() + "\nNumber of Emails: " + Utility.emails.size());
					   
			   /*Save sorted Passwords back to the  file*/
				       Utility.saveEmails();
					   utl = null;
			}else{
				textAbout.setText("");
			}
		}
	});
	rdbtnEmails.setBounds(51, 237, 81, 18);
	contentPane.add(rdbtnEmails);
	
	final JRadioButton rdbtnMessages = new JRadioButton("Messages");
	rdbtnMessages.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(rdbtnMessages.isSelected()){
				Utility utl = new Utility();
				 Utility.loadMessages();
				Set<String> set = new HashSet<String>(Utility.messages);
				Utility.messages = null;
				 Utility.messages = new ArrayList<String>(set);
				  Collections.sort(Utility.messages);
				textAbout.setText(utl.listMessages() + "\nNumber of Messages: " + Utility.messages.size());
				Utility.saveMessages();
			}else{
				textAbout.setText("");
			}
		}
	});
	rdbtnMessages.setBounds(409, 237, 101, 18);
	contentPane.add(rdbtnMessages);
	
	lblRemoveDuplicates = new JLabel("Remove Duplicates");
	lblRemoveDuplicates.setBounds(51, 210, 147, 15);
	contentPane.add(lblRemoveDuplicates);
}
}