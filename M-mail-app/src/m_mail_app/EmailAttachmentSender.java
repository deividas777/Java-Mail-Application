package m_mail_app;




import java.io.IOException;
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
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
 
public class EmailAttachmentSender {
 
    public static void sendEmailWithAttachments(String host,  String toAddress, String userName,
            String subject, String message, String[] attachFiles)
            throws AddressException, MessagingException {
        
    	// sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", 25);
        //properties.put("mail.smtp.auth", "true");
       // properties.put("mail.smtp.starttls.enable", "true");
        //properties.put("mail.user", userName);
        //properties.put("mail.password", password);
 
        // creates a new session with an authenticator
       // Authenticator auth = new Authenticator() {
            //public PasswordAuthentication getPasswordAuthentication() {
               // return new PasswordAuthentication(userName, password);
            //}
        //};
        Session session = Session.getInstance(properties);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
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
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        Transport.send(msg);
 
    }
 
    /**
     * Test sending e-mail with attachments
     */
    public static void main(String[] args) {
        // SMTP info
        String host = "danu.vodafone.ie";//"smtp.gmail.com";
        String mailFrom = "javatest56789@gmail.com";
        //String password = "gabrieleotilija7401";
 
        // message info
        String mailTo = "deividas777@gmail.com";
        String subject = "New email with attachments";
        String message = "I have some attachments for you.";
 
        // attachments
        String[] attachFiles = new String[2];
        attachFiles[0] = "/root/Desktop/keys2/public.key";
        attachFiles[1] = "/root/Desktop/keys2/randomBytes";
       // attachFiles[2] = "/root/Desktop/keys2/public.key";
 
        try {
            sendEmailWithAttachments(host, mailFrom, mailTo,
                subject, message, attachFiles);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }
}



