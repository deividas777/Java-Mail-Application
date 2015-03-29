package m_mail_app;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendFromYahoo{
	
	public static void main(String [] args){
		
		    // Sender's email ID needs to be mentioned
		     String from = "olivialesbian@yahoo.com";
		     String pass ="menuliukas";
		    // Recipient's email ID needs to be mentioned.
		     String to = "deividas777@gmail.com";
		     String host = "smtp.mail.yahoo.com";
		
		   // Get system properties
		   Properties properties = System.getProperties();
		   
		 //Set Proxies => socks Tor service
		       properties.setProperty("proxySet","true");
		       properties.setProperty("socksProxyHost","localhost");
		       properties.setProperty("socksProxyPort","9050");
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
		      message.setSubject("This is the Subject Line!");
		
		      // Now set the actual message
		      message.setText("This is actual message");
		
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
}
