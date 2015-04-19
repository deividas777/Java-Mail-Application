package m_mail_app;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.Test;

public class SendMailt_TLS_Test {
	
	
	String host = "smtp.live.com";
	String from = "***@hotmail.com";
	String password = "****";
	String to = "***@zoho.com";
	String subject = "Test JUnit";
	String text = "Test TEST TEST";
	String filename = "ISP.ser";
	
	//@Test	
	public void test_sendThrough_ISP() throws AddressException, MessagingException{
		
		 SendMail_TLS.sendMail_ISP2("mail.vodafone.ie", from, to, subject, text);
		 //SendMail_TLS.properties.

	}
	
	//@Test
	public void test_multiple_emailsAccounts() throws MessagingException{
		
		List<String> host = new ArrayList<String>();
		List<String> from = new ArrayList<String>();
		List<String> password = new ArrayList<String>();
		List<String> to = new ArrayList<String>();
		List<String> subject = new ArrayList<String>();
		List<String> text = new ArrayList<String>();
		List<String> filename = new ArrayList<String>();
		
		
		host.add("smtp.live.com");
		host.add("smtp.mail.yahoo.com");
		host.add("smtp.zoho.com");
		host.add("smtp.gmail.com");
		
		from.add("*****@hotmail.com");
		from.add("****@yahoo.com");
		from.add("****@zoho.com");
		from.add("****@gmail.com");
		
		password.add("****");
		password.add("****");
		password.add("****");
		password.add("****");
		
		to.add("****@yahoh.com");
		to.add("****@zoho.com");
		to.add("****@gmail.com");
		to.add("****@hotmail.com");
		
		subject.add("This is a Test Message!");
		text.add("This is a Test Message");
		filename.add("data_smtp.ser");
		
	    for(int i = 0; i < host.size(); i++ ){
	    	
	    	String hostR = host.get(i).toString();
	    	String fromR = from.get(i);
	    	String toR = to.get(i);
	    	String passwordR = password.get(i);
	    	String subjectR = subject.get(0);
	    	String textR = text.get(0);
	    	String fileR = filename.get(0);
	    	
	    	SendMail_TLS.sendMail(hostR, fromR, passwordR, toR, subjectR, textR, fileR);
	    }
	}
 
	@Test
	public void test() throws MessagingException {
		
	//send mail
		int[] ck = Bitshifter.buildChain(text.length());
		text = Bitshifter.encrypt(text, ck);
		SendMail_TLS.sendMail(host, from, password, to, subject, text, filename);
		
	}
	//@Test
	public void test_sendMail_N0File() throws MessagingException{
		//int[] ck = Bitshifter.buildChain(text.length());
		//text = Bitshifter.encrypt(text, ck);
		SendMail_TLS.sendMail_NoAttachement(host, from, password, to, subject, text);
		
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
