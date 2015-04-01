package m_mail_app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JCheckBoxMenuItem;


public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextArea textAbout;
	private JTextField textSearch;

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
		setBounds(100, 100, 447, 329);
		
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
		
		
		
		
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Edit List");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				Edit edit = new Edit();
				edit.setVisible(true);
			}
		});
		
		
		
		
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
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
				textAbout.setText(str);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
			
		
		
		JButton btnGoogle = new JButton("Google");
		btnGoogle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
                Google google = new Google();
                google.setVisible(true);
			}
		});
		btnGoogle.setBounds(30, 147, 117, 25);
		contentPane.add(btnGoogle);
		
		textAbout = new JTextArea(11, 5);
		textAbout.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textAbout);
		scrollPane.setBounds(161, 11, 263, 197);
		contentPane.add(scrollPane);
		
		JButton btnShowNames = new JButton("Show Names");
		btnShowNames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Utility util = new Utility();
				util.loadNames();
				textAbout.setText(util.listNames());
				util = null;
			}
		});
		btnShowNames.setBounds(30, 13, 117, 23);
		contentPane.add(btnShowNames);
		
		JButton btnShowEmails = new JButton("Show Emails");
		btnShowEmails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utility util = new Utility();
				util.loadEmails();
				textAbout.setText(util.listEmails());
				util = null;
			}
		});
		btnShowEmails.setBounds(30, 47, 117, 23);
		contentPane.add(btnShowEmails);
		
		JButton btnShowPasswords = new JButton("Show Passwords");
		btnShowPasswords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utility util = new Utility();
				util.loadPasswords();
				textAbout.setText(util.listPasswords());
				util = null;
			}
		});
		btnShowPasswords.setBounds(30, 79, 117, 23);
		contentPane.add(btnShowPasswords);
		
		JButton btnShowMessages = new JButton("Show Messages");
		btnShowMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utility util = new Utility();
				util.loadMessages();
				textAbout.setText(util.listMessages());
				util = null;
			}
		});
		btnShowMessages.setBounds(30, 113, 117, 23);
		contentPane.add(btnShowMessages);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textSearch.getText().length() > 0){
				Utility util = new Utility();
				String str = "Names List:\n";
				str += util.search(util.names, textSearch.getText());
				str += "\nPasswords List:\n";
				str += util.search(util.passwords, textSearch.getText());
				str += "\nEmails List:\n";
				str += util.search(util.emails, textSearch.getText());
				str += "\nMessages List:\n";
				str += util.search(util.messages, textSearch.getText());			
				textAbout.setText(str);
				
				}
			}
		});
		btnSearch.setBounds(30, 237, 117, 23);
		contentPane.add(btnSearch);
		
		textSearch = new JTextField();
		textSearch.setBounds(161, 238, 117, 20);
		contentPane.add(textSearch);
		textSearch.setColumns(10);
	}
}