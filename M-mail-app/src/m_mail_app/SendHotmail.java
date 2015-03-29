package m_mail_app;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendHotmail {
	
	Properties properties = System.getProperties();
	
	public void proxy(){
		properties.setProperty("proxySet","true");
	    properties.setProperty("socksProxyHost","localhost");
	    properties.setProperty("socksProxyPort","9050");
	}
	
	public  void sendMail(String host,String from, String password, String to, String subject, String text) throws MessagingException{
		
		/*
		 * Get System Properties, Setup mail server
		 */
			
		    proxy();
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.user", from);
			properties.put("mail.smtp.password", password);
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
	
		/*
		 * Get the default Session object.
		 */
			Session session = Session.getDefaultInstance(properties);
			session.setDebug(true);
		/*
		 * Create a default MimeMessage object.	
		 */
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(text);
		/*
		 * Send message
		 */
			Transport transport = session.getTransport("smtp");
			transport.connect(from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
	}
	
	public static void main(String[] args)throws MessagingException {
	 SendHotmail send = new SendHotmail();
	 //send.sendMailHotmail(587,"smtp.live.com","deividas777@hotmail.com","dominykas98","deividas777@gmail.com","Test","Testing TOR Proxy!!!");
	 //send.sendMailHotmail(587,"smtp.mail.yahoo.com","olivialesbian@yahoo.com","menuliukas123","deividas777@gmail.com","Test","Testing TOR Proxy!!!");
	 send.sendMail("smtp.gmail.com","deividas777@gmail.com","Menuliukas#@","deividas777@gmail.com","Test","Testing TOR Proxy!!!");

	 //send.send2("deividas777@hotmail.com","dominykas98","deividas777@gmail.com","Test","Test Hotmail");	
	}
	
	public static void send2(String from, String pass, String to,String subject, String message_text){
		/*
	    // Sender's email ID needs to be mentioned
	     String from = "deividas777@hotmail.com";
	     String pass ="dominykas98";
	    // Recipient's email ID needs to be mentioned.
	     String to = "deividas777@gmail.com";
	     String host = "smtp.live.com";
	   */
		String host = "smtp.live.com";
	   // Get system properties
	   Properties properties = System.getProperties();
	   
	 //Set Proxies => socks Tor service
	        //properties.setProperty("proxySet","true");
	        //properties.setProperty("socksProxyHost","localhost");
	        //properties.setProperty("socksProxyPort","9050");
	   // Setup mail server
		   properties.put("mail.smtp.starttls.enable", "true");
		   properties.put("mail.smtp.host", host);
		   properties.put("mail.smtp.user", from);
		   properties.put("mail.smtp.password", pass);
		   properties.put("mail.smtp.port", "587");
		   properties.put("mail.smtp.auth", "true");

	   // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);
	      session.setDebug(true);

   try{
	      // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);
	
	      // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));
	
	      // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                               new InternetAddress(to));
	
	      // Set Subject: header field
	         message.setSubject(subject);
	
	      // Now set the actual message
	         message.setText(message_text);
	
	      // Send message
		      Transport transport = session.getTransport("smtp");
		      transport.connect(host, from, pass);
		      transport.sendMessage(message, message.getAllRecipients());
		      transport.close();
		      System.out.println("Sent message successfully....");
   }catch (MessagingException mex) {
              mex.printStackTrace();
   }
}
    /*
    public static void main(String[] args) {
		try {
			SendHotmail.sendEmail("ID 1000");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}