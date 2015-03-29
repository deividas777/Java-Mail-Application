package m_mail_app;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;

public class Settings {

	/**
	 * @param args
	 */
	public static Map<String, String> map2;
	
	//public static void main(String[] args) throws FileNotFoundException, IOException {
		//retrieveSettings("http://localhost/myfolder/waterfordblackbeltacademy.com/data.ser","data.ser");
		//HashMap_POP();
		//HashMap_SMTP();
		//HashMap_PROXY();
		//secureDelete();
		//random_acces_file();
	//}
	
	static final String FILEPATH = "shirt2.gif";

	public static void main(String[] args) {

		try {

			System.out.println(new String(readFromFile(FILEPATH, 150, 23)));
			writeToFile(FILEPATH, "JavaCodeGeeks Rocks!", 200);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}//end
	
     public static void Image_to_binary(String inputFile, String outputFile){
		
		File input = new File(inputFile);
		File output = new File(outputFile);
		
		try{
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(input));
			BufferedWriter bw = new BufferedWriter(new FileWriter(output));
			int read;
			while((read = bis.read()) != -1){
				String text = Integer.toString(read, 2);
				while(text.length() < 8){
					text = "0" + text;
				}
				bis.close();
				bw.write(text);				
				
			 }			
			bw.close();
			}catch(IOException ioe){
				
				System.err.println(ioe);
			}
		
	}
	
   public static void blackAndWhite(String inputFile, String outputFile) throws IOException{
		
		File file = new File(inputFile);
	    BufferedImage orginalImage = ImageIO.read(file);

	    BufferedImage blackAndWhiteImg = new BufferedImage(orginalImage.getWidth(), orginalImage.getHeight(),
	                                                                          BufferedImage.TYPE_BYTE_BINARY);
	    
	    Graphics2D graphics = blackAndWhiteImg.createGraphics();
	    graphics.drawImage(orginalImage, 0, 0, null);

	    ImageIO.write(blackAndWhiteImg, "JPG", new File(outputFile)); 
		
	}//end
	
	public static void ConverToGrayScale(String file, String outputName, String extension) {
		
		BufferedImage image;
		
	      try {
	         File input = new File(file);
	         image = ImageIO.read(input);
	         int width = image.getWidth();
	         int height = image.getHeight();
	         
	         for(int i=0; i < height; i++){
	         
	            for(int j=0; j < width; j++){
	            
	               Color c = new Color(image.getRGB(j, i));
	               int red = (int)(c.getRed() * 0.299);
	               int green = (int)(c.getGreen() * 0.587);
	               int blue = (int)(c.getBlue() *0.114);
	               Color newColor = new Color(red+green+blue,red+green+blue,red+green+blue);
	               
	               image.setRGB(j,i,newColor.getRGB());
	            }
	         }
	         
	         File output = new File(outputName);
	         ImageIO.write(image, extension, output);
	         
	      } catch (Exception e) {}
	   }

	
	public static byte[] readFromFile(String filePath, int position, int size)
			throws IOException {

		RandomAccessFile file = new RandomAccessFile(filePath, "r");
		file.seek(position);
		byte[] bytes = new byte[size];
		file.read(bytes);
		file.close();
		return bytes;

	}

	public static void writeToFile(String filePath, String data, int position)
			throws IOException {

		RandomAccessFile file = new RandomAccessFile(filePath, "rw");
		file.seek(position);
		file.write(data.getBytes());
		file.close();

	}
	
	
	
	
	public static void secureDelete(File file) throws IOException{
		
		if (file.exists()) {
			long length = file.length();
			SecureRandom random = new SecureRandom();
			RandomAccessFile raf = new RandomAccessFile(file, "rws");
			raf.seek(0);
			raf.getFilePointer();
			byte[] data = new byte[64];
			int pos = 0;
			while (pos < length) {
				random.nextBytes(data);
				raf.write(data);
				pos += data.length;
			}
			raf.close();
			file.delete();
		}else{
			System.out.println("File can not be found!");
		}
	}
	
	/*
	 * Method Builds HashMap From a file
	 */
	
	public static void loadHashMap(Map<String,String> map) throws FileNotFoundException, IOException{
		
	        map = new HashMap<String, String>();
			Properties properties = new Properties();
			properties.load(new FileInputStream("data.ser"));

			for (String key : properties.stringPropertyNames()) {
			   map.put(key, properties.get(key).toString());
			}
			
			// Display content using Iterator
		      Set set = map.entrySet();
		      Iterator iterator = set.iterator();
		      while(iterator.hasNext()) {
		         Map.Entry mentry = (Map.Entry)iterator.next();
		         System.out.print("key: "+ mentry.getKey() + " & Value: ");
		         System.out.println(mentry.getValue());
		      }
}
	
	/*
	 * @Params Method retrieves file from server
	 * and saves into a file
	 */
	public static void retrieveSettings(String host, String dataFile){

		Socket socket = null;
		boolean reachable = false;
		try{
			socket = new Socket("127.0.0.1",80);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {						
			e.printStackTrace();
		}catch(NumberFormatException nuf ){
			nuf.printStackTrace();
		}					
		finally{
			
		if(socket != null)
			
			try {
				socket.close();
				reachable = true;
				System.out.println("You have access to Internet: " + reachable);
				
				File file = new File(dataFile);
				if(!file.exists()){
					file.createNewFile();
				}
				
				URL url = new URL(host);
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));			      
		        BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));  
		        
		        String line;		     
			      while ((line = reader.readLine()) != null) {			         
			          writer.write(line);
			           writer.newLine();			           	            
			      }	
			      
			      writer.flush();
			      reader.close();
			      writer.close();
			      
			}catch(IOException ioe){
				ioe.getMessage();
			}
		}
	}
	
   /*
    * @Param Method Builds HasMap and stores servers settings to 
    * send mail into a file	
    */
   public static void HashMap_SMTP() throws FileNotFoundException, IOException{
		
		Map<String, String> servers = new HashMap<String, String>();
		Properties properties = new Properties();
		
		servers.put(".*gmail.*", "smtp.gmail.com");
		servers.put(".*hotmail.*", "smtp.live.com");
		servers.put(".*yahoo.*", "smtp.mail.yahoo.com");
		servers.put(".*zoho.*","smtp.zoho.com");
		servers.put(".*outlook.*","smtp-mail.outlook.com");
		
		for (Map.Entry<String,String> entry : servers.entrySet()) {
		    properties.put(entry.getKey(), entry.getValue());
		}

		properties.store(new FileOutputStream("data_smtp.ser"), null);
	}
   
   /*
    * @Param Method Builds HasMap and stores servers settings to 
    * send mail into a file	
    */
   public static void HashMap_PROXY() throws FileNotFoundException, IOException{
		
		HashMap<String, String> servers = new HashMap<String, String>();
		Properties properties = new Properties();
		
		servers.put("115.29.251.179", "1080");
		servers.put("188.165.224.104", "60088");
		servers.put("79.127.124.115", "1080");
		servers.put("121.40.150.14","1080");
		servers.put("121.40.103.137","1080");
		
		for (Map.Entry<String,String> entry : servers.entrySet()) {
		    properties.put(entry.getKey(), entry.getValue());
		}

		properties.store(new FileOutputStream("PROXY.ser"), null);
	}
   
   /*
    * @Param Method Builds HashMap and stores servers settings to
    * retrieve mail into a file
    */
   public static void HashMap_POP() throws FileNotFoundException, IOException{
		
		Map<String, String> servers = new HashMap<String, String>();
		Properties properties = new Properties();
		
		servers.put(".*gmail.*", "pop.gmail.com");
		servers.put(".*hotmail.*", "pop3.live.com");
		servers.put(".*yahoo.*", "pop.mail.yahoo.com");
		servers.put(".*zoho.*","pop.zoho.com");
		servers.put(".*outlook.*","pop-mail.outlook.com");
		
		for (Map.Entry<String,String> entry : servers.entrySet()) {
		    properties.put(entry.getKey(), entry.getValue());
		}

		properties.store(new FileOutputStream("data_pop.ser"), null);
	}

}
