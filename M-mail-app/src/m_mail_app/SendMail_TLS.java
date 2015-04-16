package m_mail_app;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail_TLS{
		
	/*Set Properties*/
	   static Properties properties = new Properties();
	/*
	 * Add proxy properties, if proxy ==> checkbox is selected 
	 * on Google class
	 */
	    
	public static void proxy(){
		properties.setProperty("proxySet","true");
	    properties.setProperty("socksProxyHost","localhost");
	    properties.setProperty("socksProxyPort","9050");
	}
	
	/**
	 * Manual sock's proxies, http proxies not working must use socks 4 or 5
	 * @param host
	 * @param port
	 */
	public static void m_proxy(String host, String port){		
		properties.setProperty("proxySet","true");
	    properties.setProperty("socksProxyHost",host);
	    properties.setProperty("socksProxyPort",port);
	}
	
	/*
	 * Clear Properties after proxy checkbox on ==> (Google.java) unselected
	 */
	public static void clearProperties(){
		properties.clear();	
		
	}

	/**
	 * ISP mail exchange settings and port 25
	 * @param mailServer
	 * @param from
	 * @param to
	 * @param subject
	 * @param messageBody
	 * @throws MessagingException
	 * @throws AddressException
	 */
	
	public static void sendMail_ISP2(String mailServer, String from, String to,String subject, String messageBody) throws MessagingException, AddressException {

		// Setup mail server
			Properties props = new Properties();
			props.put("mail.smtp.host", mailServer);
			props.setProperty("mail.smtp.port", "25");
		
		
		// Get a mail session
			Session session = Session.getDefaultInstance(props, null); 
		    session.setDebug(true);
		
		// Define a new mail message
			Message message = new MimeMessage(session); 
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			
		// Create a message part to represent the body text
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(messageBody);
		
		//use a MimeMultipart as we need to handle the file attachments
			Multipart multipart = new MimeMultipart();
		
		//add the message body to the mime message
			multipart.addBodyPart(messageBodyPart);
		
			// Put all message parts in the message
			message.setContent(multipart);
			
			// Send the message
			Transport.send(message);		
		}
	
	  /**
	   * Send Mail Without Attachment
	   * 
	   * @param mailServer
	   * @param from
	   * @param to
	   * @param subject
	   * @param messageBody
	   * @param attachFiles
	   * @throws MessagingException
	   * @throws AddressException
	   */
	
	public static void sendMail_ISP(String mailServer, String from, String to,String subject, String messageBody,String attachFiles[]) throws MessagingException, AddressException {

		// Setup mail server
			Properties props = new Properties();
			props.put("mail.smtp.host", mailServer);
			props.setProperty("mail.smtp.port", "25");
		
		
		// Get a mail session
			Session session = Session.getDefaultInstance(props, null); 
		
		// Define a new mail message
			Message message = new MimeMessage(session); 
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			
		// Create a message part to represent the body text
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(messageBody);
		
		//use a MimeMultipart as we need to handle the file attachments
			Multipart multipart = new MimeMultipart();
		
		//add the message body to the mime message
			multipart.addBodyPart(messageBodyPart);
		
		// add any file attachments to the message
		//addAtachments(attachments, multipart);
		
		if (attachFiles != null && attachFiles.length > 0) {
			
			for (String filePath : attachFiles) {
			MimeBodyPart attachPart = new MimeBodyPart();
			
			try {
			    attachPart.attachFile(filePath);
			} catch (IOException ex) {
			    ex.printStackTrace();
			}
			
			multipart.addBodyPart(attachPart);
			}
			}
			
			// Put all message parts in the message
			message.setContent(multipart);
			
			// Send the message
			Transport.send(message);
					
		}
		
	/**
	 * 
	 * @param attachments
	 * @param multipart
	 * @throws MessagingException
	 * @throws AddressException
	 */
		protected static void addAtachments(String[] attachments, Multipart multipart) throws MessagingException, AddressException {
			
			for(int i = 0; i<= attachments.length -1; i++){
			
			String filename = attachments[i];
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			
			//use a JAF FileDataSource as it does MIME type detection
			DataSource source = new FileDataSource(filename);
			attachmentBodyPart.setDataHandler(new DataHandler(source));
			
			//assume that the filename you want to send is the same as the
			//actual file name - could alter this to remove the file path
			attachmentBodyPart.setFileName(filename);
			
			//add the attachment
			multipart.addBodyPart(attachmentBodyPart);
		}
	}

	/**
	 * 
	 * @param host
	 * @param from
	 * @param password
	 * @param to
	 * @param subject
	 * @param text
	 * @throws MessagingException
	 */
       public static void sendMail_NoAttachement(String host,final String from, final String password, String to, String subject, String text) throws MessagingException{
		
		/*
		 * Get System Properties, Setup mail server
		 */
			    
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.user", from);
			properties.put("mail.smtp.password", password);
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
	
		/*
		 * Get the default Session object.
		 */
			Session session = Session.getDefaultInstance(properties,
			         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(from, password);
	            }
	         });
			session.setDebug(true);
		/*
		 * Create a default MimeMessage object.	
		 */
			MimeMessage message = new MimeMessage(session);
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setContent(text, "text/html; charset=utf-16");
		/*
		 * Send message
		 */
			Transport transport = session.getTransport("smtp");
			transport.connect(from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
	}
	
	
	/**
	 * 
	 * @param host
	 * @param from
	 * @param password
	 * @param to
	 * @param subject
	 * @param text
	 * @param filename
	 * @throws MessagingException
	 */
		
    public static void sendMail(String host,final String from, final String password, String to, String subject, String text, String filename) throws MessagingException{
		
		/*
		 * Get System Properties, Setup mail server
		 */					    
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.user", from);
			properties.put("mail.smtp.password", password);
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");	
		/*
		 * Get the default Session object, Authenticate user 
		 */
			Session session = Session.getDefaultInstance(properties,
			         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(from, password);
	            }
	         });
			session.setDebug(true);
		/*
		 * Create a default MimeMessage object.	
		 */
			  MimeMessage message = new MimeMessage(session);
			  message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			  
			// Set From: header field of the header.
			  message.setFrom(new InternetAddress(from));
			  
			// Set To: header field of the header.
			  message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			  
			// Set Subject: header field
			  message.setSubject(subject);
			  
			// Create the message part
		       BodyPart messageBodyPart = new MimeBodyPart();
		       
		   // Now set the actual message
		       messageBodyPart.setContent( text, "text/html; charset=utf-16" );
		       //messageBodyPart.setText(text);
		       
		    // Create a multi part message
		       Multipart multipart = new MimeMultipart();
		       
		    // Set text message part
		       multipart.addBodyPart(messageBodyPart);
		      
		      /*
		       * Validation of File name to send as attachment, if File name = null send 
		       * email without attachment
		       */
			    if(filename != null){ 			    	  
			         
			         messageBodyPart = new MimeBodyPart();
			         DataSource source = new FileDataSource(filename);
			         messageBodyPart.setDataHandler(new DataHandler(source));
			         messageBodyPart.setFileName(filename);
			         multipart.addBodyPart(messageBodyPart);
			      }//end if(filename != null)
		    
		      // Send the complete message parts
		         message.setContent(multipart);
		/*
		 * Send message
		 */
			Transport transport = session.getTransport("smtp");
			transport.connect(from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
	
    }//end sendMail
    
    
    
    /**
     * Check Connection of Proxy server, must enter ip address,
     * port number and boolean value, cab be used to check Internet connection on the LAN
     * @param ip
     * @param port
     * @param reachable
     * @return
     * @throws UnknownHostException
     * @throws IOException
     * @throws ConnectException
     */
    
    public static boolean checkConnection(String ip, int port, boolean reachable) throws UnknownHostException, IOException, ConnectException{
		
		Socket socket = null;
		
		try{
			socket = new Socket(ip,port);
			
		}finally{
			if(socket != null) try {
				socket.close();
				reachable = true;
				return true;
				
			}catch(IOException ioe){
				ioe.printStackTrace();
				reachable = false;
				return false;
				
			}
		}
		System.out.println(reachable);
		return reachable;
		
	}
       
    /**
     * 
     * @param args
     * @throws MessagingException
     * @throws UnknownHostException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[]args) throws MessagingException, UnknownHostException, IOException, InterruptedException{
        	    	
    	//SendMail_TLS mail = new SendMail_TLS();
    	//boolean reachable = false;
		//SendMail_TLS.checkConnection("202.112.31.203",1080,reachable);
    	//m_proxy("115.29.251.179", "1080");
    	//mail.proxy();
    	int [] ch = Bitshifter.buildChain(10);
    	String message = Bitshifter.encrypt("Hello My Friend How Are you today!", ch);
    	//message = Bitshifter.NCR2UnicodeString(message);
    	
    	//sendMail("smtp.zoho.com", "deividas777@zoho.com", "menuliukas", "deividas777@zoho.com", "Test File Output", message, "PROXY.ser");
    	sendMail_NoAttachement("smtp.zoho.com", "deividas777@zoho.com", "menuliukas", "deividas777@zoho.com", "Test UTF-8", message);
    	//clearProperties();
    	//sendMail("smtp.zoho.com", "deividas777@zoho.com", "", "deividas777@zoho.com", "Test MESSAGE OUT PUT FILE", "Test MESSAGE OUTPUT FILE","deividas777@zoho.com_MESSAGES.ser");
    }
}
