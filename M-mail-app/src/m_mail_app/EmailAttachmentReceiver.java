package m_mail_app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;


import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.swing.JOptionPane;
 
/**
 * This program demonstrates how to download e-mail messages and save
 * attachments into files on disk.
 *
 * @author www.codejava.net ==> updated by Deividas Onaitis 
 * Extra added get multiple files and rename them if they contains same names
 * Extra proxy settings introduced also TOR server configuration included
 * Extra Clear properties. extra write messages into a file
 *
 */
public class EmailAttachmentReceiver {
	
	/**
	 * Global Variables
	 */
    public static String saveDirectory;
    public static Address[] fromAddress;
    
    public static String from;
    public static String subject;
    public static String sentDate;

    public static String contentType;
    public static String messageContent;
   // public static String attachFiles;
    /*Set Properties*/
    static Properties properties = System.getProperties();
    
    //@Write retrieved message content into a file
    static BufferedWriter bfr; 
    
    /**
     * @ Set properties to use TOR
     */
    public static void proxy(){
		properties.setProperty("proxySet","true");
	    properties.setProperty("socksProxyHost","localhost");
	    properties.setProperty("socksProxyPort","9050");
	   //@Perform check
	    System.out.println("TOR is selected!");
	}
    
   /**
    * 
    * @param host
    * @param port
    * 
    * Manual sock's proxies, http proxies not working must use socks 4 or 5
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
     * Sets the directory where attached files will be stored.
     * dir absolute path of the directory
     * @param dir
     */
    public static void setSaveDirectory(String dir) {
        saveDirectory = dir;
    }
 
    /**
     * Downloads new messages and saves attachments to disk if any.
     * @param host
     * @param port
     * @param userName
     * @param password
     */
    
    public static void downloadEmailAttachments(String host, final String userName, final String password) {
        
        //@Server setting
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "995");
      
        //@SSL setting
        properties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.pop3.socketFactory.fallback", "false");
        properties.setProperty("mail.pop3.socketFactory.port",
                String.valueOf("995"));
 
        
        //@New lines after (properties, Authentication 
        Session session = Session.getDefaultInstance(properties,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(userName, password);
            }
         });
 
        try {
            // connects to the message store
            Store store = session.getStore("pop3");
            store.connect(userName, password);
 
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
 
            // fetches new messages from server
            Message[] arrayMessages = folderInbox.getMessages();
            
          //@Write message content into a file ==> (userName + MESSAGES.ser)
	           
             File file_messages = new File(userName + "_MESSAGES.ser");
	           
             if(!file_messages.exists()){
	            	file_messages.createNewFile();
	            	System.out.println("New File created: " + userName + "_MESSAGES.ser");
	            }
	        //@Create new buffered writer  
             bfr = new BufferedWriter(new FileWriter(file_messages));
 
            
            for (int i = 0; i < arrayMessages.length; i++) {
                
               Message message = arrayMessages[i];
               fromAddress = message.getFrom();
               
               from = fromAddress[0].toString();
               subject = message.getSubject();
               sentDate = message.getSentDate().toString();
 
               contentType = message.getContentType();
               messageContent = "";
 
                // store attachment file name, separated by comma
                String attachFiles = "";
 
                if (contentType.contains("multipart")) {
                    
                	// content may contain attachments
                	
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    	
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            
                        	// this part is attachment
                            String fileName = part.getFileName();
                            
                            //(st) variable used to rename files if files with a same names exists
                            String st = "";
                            
                            //Validation of file name
                            if(fileName.contains("TMP.zip")){
                              st = fileName.substring(fileName.length() - 7, fileName.length());                         
                            }else{
                            	st = fileName;
                            }                            
                            //Rename file  if file exist with a same name
                            File file = new File(saveDirectory + st);
                            if(file.exists()){
                            	st = i + st;
                            }                           
                            //save files in chosen directory
                            attachFiles += fileName + ", ";
                            part.saveFile(saveDirectory + File.separator + st);                            
                        } else {
                            // this part may be the message content
                            messageContent = part.getContent().toString();
                        }
                    }//end for (int partCount = 0; partCount < numberOfParts; partCount++)
                    if (attachFiles.length() > 1) {
                        attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                    }
                } else if (contentType.contains("text/plain"))
                        //|| contentType.contains("text/html")) 
                		{
                    Object content = message.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                }
               
              //@print out details of each message
                System.out.println("Message #" + (i + 1) + ":");
                System.out.println("\t From: " + from);
                System.out.println("\t Subject: " + subject);
                System.out.println("\t Sent Date: " + sentDate);
                
                System.out.println("\t Message: " + messageContent.replaceAll("\\W", " ").substring(0,(messageContent.length()/5)));
                System.out.println("\t Attachments: " + attachFiles);
                
            //@Write into file  ==> userName + MESSAGES.ser
                
                bfr.write("\tMessage #" + (i + 1) + ":");
                bfr.newLine();
                bfr.write("\t From: " + from);
                bfr.newLine();
                bfr.write("\t Subject: " + subject);
                bfr.newLine();
                bfr.write("\t Sent Date: " + sentDate);
                bfr.newLine();
                bfr.write("\t Message: " + messageContent.replaceAll("\\W", " "));
                bfr.newLine();
                bfr.write("\t Attachments: " + attachFiles);
                bfr.newLine();
               
            }//end for (int i = 0; i < arrayMessages.length; i++)
           
           //@Close buffer writer
            bfr.flush();
            bfr.close();
            
           //@disconnect
            folderInbox.close(false);
            store.close();
            
            
        } catch (NoSuchProviderException ex) {
        	JOptionPane.showMessageDialog(null, "No provider for pop3");
            ex.printStackTrace();
        } catch (MessagingException ex) {
        	JOptionPane.showMessageDialog(null, "Could not connect to the message store");
            ex.printStackTrace();
        } catch (IOException ex) {
        	JOptionPane.showMessageDialog(null, "Can not connect to " + userName);
            ex.printStackTrace();
        }
    }
 
    /**
     * Runs this program with ZOHO POP server
     */
    public static void main(String[] args) {
       
        String host = "pop.zoho.com";
        String userName = "@zoho.com";
        String password = "";
 
        //create directory if does not exists
        File file = new File("/root/Attachment");
        if(!file.exists()){
        	file.mkdir();
        }
        //give value to save directory
        String saveDirectory = "/root/Attachment/";
        
        EmailAttachmentReceiver.setSaveDirectory(saveDirectory);
        
        //System.out.println("PROXY:  115.29.251.179 PORT: 1080");
        //EmailAttachmentReceiver.m_proxy("115.29.251.179", "1080");    
        
        EmailAttachmentReceiver.downloadEmailAttachments(host,  userName, password);
        //System.out.println("Clear Properties!");
        
        //mailAttachmentReceiver.clearProperties();
        //System.out.println("Recieve Files NO PROXY: " + properties);
        //EmailAttachmentReceiver.downloadEmailAttachments(host,  userName, password);
 
    }
}
