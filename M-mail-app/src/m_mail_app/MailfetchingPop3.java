package m_mail_app;

import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.POP3SSLStore;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;


public class MailfetchingPop3
{
    private Session session;
    private POP3SSLStore store;
    private String username;
    private String password;
    private POP3Folder folder;
    public static String numberOfFiles = null;
    public static int toCheck = 0;
    public static Writer output = null;
    URLName url;
    public static String receiving_attachments="C:\\download";

    public MailfetchingPop3()
    {
        session = null;
        store = null;
    }

    public void setUserPass(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public void connect()
    throws Exception
    {
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties pop3Props = new Properties();
        pop3Props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
        pop3Props.setProperty("mail.pop3.port", "995");
        pop3Props.setProperty("mail.pop3.socketFactory.port", "995");
        url = new URLName("pop3", "pop.gmail.com", 995, "", username, password);
        session = Session.getInstance(pop3Props, null);
        store = new POP3SSLStore(session, url);
        store.connect();
    }

    public void openFolder(String folderName)
    throws Exception
    {
        folder = (POP3Folder)store.getFolder(folderName);
        System.out.println((new StringBuilder("For test----")).append
(folder.getParent().getFullName()).toString());
        if(folder == null)
            throw new Exception("Invalid folder");
        try
        {
            folder.open(2);
            System.out.println((new StringBuilder("Folder name----")).append
(folder.getFullName()).toString());
        }
        catch(Exception ex)
        {
            System.out.println((new StringBuilder("Folder Opening Exception..")).append(ex).toString());
        }
    }

    public void closeFolder()
    throws Exception
    {
        folder.close(false);
    }

    public int getMessageCount()
    throws Exception
    {
        return folder.getMessageCount();
    }

    public int getNewMessageCount()
    throws Exception
    {
        return folder.getNewMessageCount();
    }

    public void disconnect()
    throws Exception
    {
        store.close();
    }

    public void printAllMessages()
    throws Exception
    {
        Message msgs[] = folder.getMessages();
        FetchProfile fp = new FetchProfile();
        folder.fetch(msgs, fp);
        
        for(int i = 0; i < msgs.length; i++){
            Message message = msgs[i];
            dumpEnvelope(msgs[i]);
        System.out.println("==============================");
    System.out.println("Email #" + (i + 1));
    System.out.println("Subject: " + message.getSubject());
    System.out.println("From: " + message.getFrom()[0]);
    System.out.println("Text: " + message.getContent().toString());
        }
    }



    public static int saveFile(File saveFile, Part part) throws Exception {

        BufferedOutputStream bos = new BufferedOutputStream( new
FileOutputStream(saveFile) );

        byte[] buff = new byte[8192];
        InputStream is = part.getInputStream();
        int ret = 0, count = 0;
        while( (ret = is.read(buff)) > 0 ){
            bos.write(buff, 0, ret);
            count += ret;
        }
        bos.close();
        is.close();
        return count;
    }

    private static void dumpEnvelope(Message m) throws Exception
    {
        String body="";
        String path="";
        int size=0;
        Object content = m.getContent();
        if(content instanceof String){
            body = (String)content;
        }
        else if(content instanceof Multipart)
        {
            Multipart mp = (Multipart)content;
            for (int j=0; j < mp.getCount(); j++)
            {
                Part part = mp.getBodyPart(j);
                String disposition = part.getDisposition();
                //System.out.println("test disposition---->>"+disposition);
                if (disposition == null) {
                    // Check if plain
                    MimeBodyPart mbp = (MimeBodyPart)part;
                    if (mbp.isMimeType("text/plain")) {
                        body += mbp.getContent().toString();
                    }
                    else if (mbp.isMimeType("TEXT/HTML")) {
                        body += mbp.getContent().toString();
                    }
                    else {
                        //unknown
                    }
                } else if ((disposition != null) &&
                        (disposition.equals(Part.ATTACHMENT) || disposition.equals
(Part.INLINE) || disposition.equals("ATTACHMENT") || disposition.equals
("INLINE")) )
                {
                    // Check if plain
                    MimeBodyPart mbp = (MimeBodyPart)part;
                    if (mbp.isMimeType("text/plain")) {
                        body += (String)mbp.getContent();
                    }
                    else if (mbp.isMimeType("TEXT/HTML")) {
                        body += mbp.getContent().toString();
                    }
                    else {
                        File savedir = new File(receiving_attachments);
                        savedir.mkdirs();
                        File savefile = new File(savedir+"\\"+part.getFileName());
                        path = savefile.getAbsolutePath();
                        size = saveFile( savefile, part);

                    }
                }
            }
        }

    }
    public static void main(String args[])
    {
        try
        {
            MailfetchingPop3 gmail = new MailfetchingPop3();
            gmail.setUserPass("deividas777@gmail.com", "");
            gmail.connect();
            gmail.openFolder("INBOX");
            gmail.printAllMessages();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}