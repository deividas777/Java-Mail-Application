package m_mail_app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.io.Serializable;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

import java.util.*;

import javax.naming.directory.BasicAttributes;




public class Utility implements Serializable{
	
	public static List<String> names =  new ArrayList<String>();
	private List<Integer> numbers = new ArrayList<Integer>();
	private List<Character> characters = new ArrayList<Character>();
	private List<Double> doubles = new ArrayList<Double>();
	public static List<String> emails = new ArrayList<String>();
	public static List<String> passwords = new ArrayList<String>();
	public static List<String> messages = new ArrayList<String>();
	private Random randomGenerator = new Random();
	public static List<String> strings = new ArrayList<String>();
	static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static final int BUFFER_SIZE = 65536;
	
	public static void main(String[] args) throws IOException{
	
	/*	
		File file1 = new File("/root/Desktop/keys2/randomBytes");
		File file2 = new File("/root/Desktop/keys2/decrypted_RSA_RandomBytes");
		isFileBinaryEqual(file1, file2);
		
		populateList( passwords, "passwords", "rec_passwords.txt", "/root/Desktop/List/");
		System.out.println(passwords.size());
		
	*/	
		
		readFile("/root/Desktop/mp3/geltona_zalia_raudona.mp3");
	}
	
	
	/*
	 * Read file
	 */
	
	public static void readFile(String fileName){
		
		        File file = new File(fileName);
		        FileInputStream fin = null;
		        
		      
			        try {
			            // create FileInputStream object
			            fin = new FileInputStream(file);
			 
			            byte fileContent[] = new byte[(int)file.length()];
			             
			            // Reads up to certain bytes of data from this input stream into an array of bytes.
			            fin.read(fileContent);
			            //create string from byte array
			            String s = new String(fileContent).substring(200,330);//.replace("\\W", "");//.replaceAll("([^ \t\r\n])[ \t]+$","");
			            
			            
			            System.out.println("File content: " + s);
			        }
			        catch (FileNotFoundException e) {
			            System.out.println("File not found" + e);
			        }
			        catch (IOException ioe) {
			            System.out.println("Exception while reading file " + ioe);
			        }
			        finally {
			            // close the streams using close method
			            try {
			                if (fin != null) {
			                    fin.close();
			                }
			            }
			            catch (IOException ioe) {
			                System.out.println("Error while closing stream: " + ioe);
			            }
			        }
		
	}
	
	/*
	 * Delete Directory with files inside
	 */
	static public boolean deleteDirectory(File path) {
	    if( path.exists() ) {
	      File[] files = path.listFiles();
	      for(int i=0; i<files.length; i++) {
	         if(files[i].isDirectory()) {
	           deleteDirectory(files[i]);
	         }
	         else {
	           files[i].delete();
	         }
	      }
	    }
	    return( path.delete() );
	  }
	
	
	//Method that compares two binary files for equality
	
	public static boolean isFileBinaryEqual(File first,File second ) throws IOException{
		      
		      boolean retval = false;
		      
		      if ((first.exists()) && (second.exists()) 
		         && (first.isFile()) && (second.isFile()))
		      {
		         if (first.getCanonicalPath().equals(second.getCanonicalPath()))
		         {
		            retval = true;
		         }
		         else
		         {
		            FileInputStream firstInput = null;
		            FileInputStream secondInput = null;
		            BufferedInputStream bufFirstInput = null;
		            BufferedInputStream bufSecondInput = null;

		            try
		            {            
		               firstInput = new FileInputStream(first); 
		               secondInput = new FileInputStream(second);
		               bufFirstInput = new BufferedInputStream(firstInput, BUFFER_SIZE); 
		               bufSecondInput = new BufferedInputStream(secondInput, BUFFER_SIZE);
		   
		               int firstByte;
		               int secondByte;
		               
		               while (true)
		               {
		                  firstByte = bufFirstInput.read();
		                  secondByte = bufSecondInput.read();
		                  if (firstByte != secondByte)
		                  {
		                     break;
		                  }
		                  if ((firstByte < 0) && (secondByte < 0))
		                  {
		                     retval = true;
		                     break;
		                  }
		               }
		            }
		            finally
		            {
		               try
		               {
		                  if (bufFirstInput != null)
		                  {
		                     bufFirstInput.close();
		                  }
		               }
		               finally
		               {
		                  if (bufSecondInput != null)
		                  {
		                     bufSecondInput.close();
		                  }
		               }
		            }
		         }
		      }
		      return retval;
		   }

    
	public static void removeDuplicates(List<String> list){
		
		
		Set<String> set = new HashSet<String>(list);
		list = null;
		list = new ArrayList<String>(set);
		Collections.sort(list);
	}
	
	
	public String randomString(int len) 
    {
       StringBuilder sb = new StringBuilder( len );
       for( int i = 0; i < len; i++ ) 
          sb.append( AB.charAt( randomGenerator.nextInt(AB.length()) ) );
       return sb.toString();
    }
    
    public static void oneSec() {
        try {
          Thread.currentThread().sleep(20000);
          }
        catch (InterruptedException e) {
          e.printStackTrace();
          }
        }  
    
    /*
     * Populate List, email's, user names, messages
     * */
    
    
    
    public static  void populateList(List<String> list, String file, String file2, String patch)throws IOException{
    	
    	loadList(list, file);
    	BufferedReader reader = null;
    	try{
    		reader = new BufferedReader(new FileReader(patch + file2));
    		String line = null;
    	while((line = reader.readLine()) != null){
    		list.add(line);
    	  }
    	} finally{
    		reader.close();
    	}
    	Collections.sort(list);
    	saveList(list, file);
    }
    
    /*
     * Load existing files that contains email's, messages, user names, passwords
     * */
		
	public static void loadList(List<String> list, String file){
		try{
			String patch = "/root/Desktop/List/";
			 FileInputStream fout = new FileInputStream(patch + file + ".dat");
			  ObjectInputStream in = new ObjectInputStream(fout);
			   list = (List) in.readObject();
			    in.close();		
		} catch(Exception e){
			e.getMessage();
		}
	}
	
	public static void loadEmails(){
		
		try{
			 
			FileInputStream fout = new FileInputStream("/root/Desktop/List/emails.dat");
			ObjectInputStream in = new ObjectInputStream(fout);   
			emails = (List) in.readObject();
			in.close();				
			
		   }catch(Exception ex){
			   ex.printStackTrace();
		   }
		}
	
	public static void loadMessages(){
		
		try{
			 
			FileInputStream fout = new FileInputStream("/root/Desktop/List/messages.dat");
			ObjectInputStream in = new ObjectInputStream(fout);   
			messages = (List) in.readObject();
			in.close();				
			
		   }catch(Exception ex){
			   ex.printStackTrace();
		   }
		}
	
	public void loadNames(){
		
	try{
		 
		FileInputStream fout = new FileInputStream("/root/Desktop/List/names.dat");
		ObjectInputStream in = new ObjectInputStream(fout);   
		names = (List) in.readObject();
		in.close();				
		
	   }catch(Exception ex){
		   ex.printStackTrace();
	   }
	}
	
	public static void loadPasswords(){
		
		try{
			 
			FileInputStream fout = new FileInputStream("/root/Desktop/List/passwords.dat");
			ObjectInputStream in = new ObjectInputStream(fout);   
			passwords = (List) in.readObject();
			in.close();				
			
		   }catch(Exception ex){
			   ex.printStackTrace();
		   }
		}
	
	public static void saveList(List<String> list, String file){
		try{
			String patch = "/root/Desktop/List/";
			 FileOutputStream output = new FileOutputStream(patch + file + ".dat");
			  ObjectOutputStream stream = new ObjectOutputStream(output);
			   stream.writeObject(list);
			    stream.close();
			     stream.flush();
		} catch(IOException e){
			e.getMessage();
		}
	}
	
	public static void saveEmails(){
		
		try{
			String patch = "/root/Desktop/List/";
		     FileOutputStream output = new FileOutputStream(patch + "emails.dat");
		      ObjectOutputStream stream = new ObjectOutputStream(output);   
		       stream.writeObject(emails);
		        stream.close();
		        }
		catch(IOException e){
			                         e.getMessage();
		}
		
		}
	
	
	
   public static void saveMessages(){
		
		try{
			String patch = "/root/Desktop/List/";
		     FileOutputStream output = new FileOutputStream(patch + "messages.dat");
		      ObjectOutputStream stream = new ObjectOutputStream(output);   
		       stream.writeObject(messages);
		        stream.close();
		        }
		catch(IOException e){
			                         e.getMessage();
		}
		
		}
	
	public void saveNames(){
	
	try{
		String patch = "/root/Desktop/List/";
	     FileOutputStream output = new FileOutputStream(patch + "names.dat");
	      ObjectOutputStream stream = new ObjectOutputStream(output);   
	       stream.writeObject(names);
	        stream.close();
	        }
	catch(IOException e){
		                         e.getMessage();
	}
	
	}

	public static void savePasswords(){
		
		try{
			String patch = "/root/Desktop/List/";
		     FileOutputStream output = new FileOutputStream(patch + "passwords.dat");
		      ObjectOutputStream stream = new ObjectOutputStream(output);   
		       stream.writeObject(passwords);
		        stream.close();
		        }
		catch(IOException e){
			   e.getMessage();
		}
		
		}
	
	
	public static void createFile(String fileName) throws IOException{
		
		String patch = "/root/Desktop/List/";
		File file = new File(patch + fileName +".dat");
		if(!file.exists()){
			file.createNewFile();
		}
	}
	
	public void createDirectory(){
		
		File file = new File("/root/Desktop/List");
		if(!file.exists()){
			file.mkdir();
		}	
		
	}
	
	public String search(List<String> list, String key){
		
		int result = -1;
		result = Collections.binarySearch(list, key);
		System.out.println(result);
		
		if( result >= 0){
			return "Found at index " + result;
		}
		else{
			return "Doesn't exist: " + key;
		}
	}
	
		
	public void removeEmail(int index){
		emails.remove(index);
	}
	
	public void removeMessage(int index){
		messages.remove(index);
	}
	
	public void removeDouble(int index){
		doubles.remove(index);
		Collections.sort(doubles);
	}
	
	public void removeCharacter(int index){
		characters.remove(index);
		Collections.sort(characters);
	}
	
	public void removeNumber(int index){
		numbers.remove(index);
		Collections.sort(numbers);
	}
	
	public void removeName(int index){
		names.remove(index);
		Collections.sort(names);
	}
	
	public void removePassword(int index){
		passwords.remove(index);
		Collections.sort(passwords);
	}
	
	
	public void addNames(String name){
		names.add(name);
		Collections.sort(names);
		}
	
	public void addStrings(String str){
		strings.add(str);
		
	}
	
	public void addNumbers(int number){
		numbers.add(number);
		Collections.sort(numbers);
	}
	
	public void addCharacters(char character ){
		characters.add(character);
	}
	
	public void addDoubles(double number){
		doubles.add(number);
		Collections.sort(doubles);
	}
	
	public void addEmails(String email){
		emails.add(email);
		Collections.sort(emails);
		}
	
	public void addPasswords(String password){
		passwords.add(password);
		Collections.sort(passwords);
		}
	
	public static void addMessages(String message){
		messages.add(message);
		Collections.sort(messages);
	}
	
	public static void addToCollection(List<String> list, String word){
	   list.add(word);	
	   Collections.sort(list);
	}
	
	
	
	public String listEmails(){
		String str = "";
		for(String s: emails){
			str += s + "\n";
		}
		return str;
	}
	
	public void listDoubles(){
		for(Double s: doubles){
			System.out.printf("%s\n", s);
		}
	}
	
	public void listCharacters(){
		for(Character s: characters){
			System.out.printf("%s\n", s);
		}
	}
	
	public String listNames(){
		String str ="";
		for(String s: names){
			str += s + "\n";
		}
		return str;
	}
	
	public void listNumbers(){
		for(Integer s: numbers){
			System.out.printf("%s\n", s);
		}
	}
	
	public void listStrings(){
		for(String s: strings){
			System.out.println(s);
		}
	}
	
	public static String listPasswords(){
		String str = "";
		for(String s: passwords){
			str += s + "\n";
		}
		return str;
	}
	
	public String listMessages(){
		String str = "";
		for(String s: messages){
			str += s + "\n";
		}
		return str;
	}
	
	
	
	
    public String pickEmails(){
		int index = randomGenerator.nextInt(emails.size());
		return emails.get(index);
	}
	
	public char pickCharacter(){
		int index = randomGenerator.nextInt(characters.size());
		return characters.get(index);
	}
	
	public double pickDouble(){
		int index = randomGenerator.nextInt(doubles.size());
		return doubles.get(index);
	}
	
	public int pickNumbers(){
		int index = randomGenerator.nextInt(numbers.size());
		return numbers.get(index);
	}
	
	public String pickNames(){
		int index = randomGenerator.nextInt(names.size());
	    return names.get(index);	
	}
	
	public String pickPasswords(){
		int index = randomGenerator.nextInt(passwords.size());
		return passwords.get(index);
	}
	
	public String pickMessages(){
		int index = randomGenerator.nextInt(messages.size());
		return messages.get(index);
	}
	
	
}