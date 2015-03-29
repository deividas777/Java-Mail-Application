package m_mail_app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class E_Mail_Gen extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	Utility utl = new Utility();
	MainFrame frame = new MainFrame();
	SendGoogle gen = new SendGoogle();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					E_Mail_Gen frame = new E_Mail_Gen();
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
	public E_Mail_Gen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(46, 34, 114, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(192, 36, 201, 151);
		contentPane.add(textArea);
		
		JButton btnCrate = new JButton("Create");
		btnCrate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
	            
			  utl.loadEmails();
				int len = Integer.parseInt(textField.getText());
				 String postfix = textField_1.getText();
				  int number = Integer.parseInt(textField_2.getText());
								
				for(int i = 0; i < number;i++){	
					utl.addEmails(((Utility) utl).randomString(len).trim() + "@" + postfix.trim());
					 utl.saveEmails();
				 
					}		
				textArea.setText("You have: "  + utl.emails.size() + " email addresses in you list.");
				
				        
				         
				}catch(Exception e){
					e.getMessage();
				}
				
			}
		});
		btnCrate.setBounds(145, 225, 117, 25);
		contentPane.add(btnCrate);
		
		textField_1 = new JTextField();
		textField_1.setBounds(46, 88, 114, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblLength = new JLabel("Length:");
		lblLength.setBounds(46, 12, 70, 15);
		contentPane.add(lblLength);
		
		JLabel lblPrefix = new JLabel("Postfix:");
		lblPrefix.setToolTipText("Do not enter \"@\"");
		lblPrefix.setBounds(46, 65, 70, 15);
		contentPane.add(lblPrefix);
		
		textField_2 = new JTextField();
		textField_2.setBounds(46, 141, 114, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNoOfEmails = new JLabel("No of Emails:");
		lblNoOfEmails.setBounds(46, 114, 114, 15);
		contentPane.add(lblNoOfEmails);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
			}
		});
		btnBack.setBounds(274, 225, 117, 25);
		contentPane.add(btnBack);
	}
}
