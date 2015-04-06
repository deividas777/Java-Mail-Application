package m_mail_app;

import static org.junit.Assert.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.Test;

public class SendMailt_TLS_Test {
	
	
	String host = "smtp.live.com";
	String from = "deividas777@hotmail.com";
	String password = "dominykas98";
	String to = "deividas777@gmail.com";
	String subject = "Test JUnit";
	String text = "Test TEST TEST";
	String filename = "data_smtp.ser";
	
	@Test
	public void test_sendThrough_ISP() throws AddressException, MessagingException{
		
		 SendMail_TLS.sendMail_ISP2("mail.vodafone.ie", from, to, subject, text);

	}

	@Test
	public void test() throws MessagingException {
		
	//set proxy TOR	
		//SendMail_TLS.proxy();
	//send mail
		SendMail_TLS.sendMail(host, from, password, to, subject, text, filename);
		
	}
	@Test
	public void test_sendMail_N0File() throws MessagingException{
		
		SendMail_TLS.sendMail_NoAttachement(host, from, password, to, subject, text);
		
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
