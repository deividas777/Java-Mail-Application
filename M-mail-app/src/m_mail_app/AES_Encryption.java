package m_mail_app;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.io.*;

import javax.crypto.*;
import javax.crypto.spec.*;

public class AES_Encryption {
	
	public static void main(String[] args){
		AES_Encryption aes = new AES_Encryption();
		try {
			aes.fileEncryption("test_java");
		} catch (InvalidKeyException e) {
			
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			
			e.printStackTrace();
		} catch (BadPaddingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	public String fileEncryption(String file) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		
		//File containing secret AES key
				FileInputStream keyFIS = new FileInputStream("/root/Desktop/keys2/AESKeyFile");
				ObjectInputStream keyOIS = new ObjectInputStream(keyFIS);
				
				//Read in the AES key
				SecretKey aesKey = (SecretKey) keyOIS.readObject();
				keyOIS.close();
				keyFIS.close();
				
				// Set IV (required for CBC)
				byte[] iv = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
				IvParameterSpec ips = new IvParameterSpec(iv);
				
				// Create AES cipher instance
				Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				
				// Initialize the cipher for Encryption
				aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, ips);
				
				//File for writing output
				FileOutputStream fos = new FileOutputStream("/root/Desktop/file_locked");
				
		        // This is the message to be encrypted
		        byte plaintext[] = file.getBytes();
		        
		        //Encrypt the plainText
		        byte[] cipherText = aesCipher.doFinal(plaintext);
		        
		        //Write cipherText to file
		        fos.write(cipherText);
		        fos.close();
		        
		     // Also display it in Hex on stdout
		        StringBuffer hexString = new StringBuffer();
		        for (int i = 0; i < cipherText.length; i++)
		        {
		            hexString.append(Integer.toHexString(0xF & cipherText[i]>>4));
		            hexString.append(Integer.toHexString(0xF & cipherText[i]));
		            hexString.append("");
		        }
		        System.out.println("Plaintext: " + file);
		        System.out.println("Ciphertext: " + hexString.toString());
		        return new String(hexString);
		        
		
	}
	
		
	public String aesDecrypt()throws Exception{
		
        // File containing secret AES key
        FileInputStream keyFIS = new FileInputStream("/root/Desktop/keys2/decrypted_RSA_AESKeyFile");
        ObjectInputStream keyOIS = new ObjectInputStream(keyFIS);

        // Read in the AES key
        SecretKey aesKey = (SecretKey) keyOIS.readObject();
        keyOIS.close();
        keyFIS.close();
        
        // set IV (required for CBC)
        byte[] iv = new byte[] {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        IvParameterSpec ips = new IvParameterSpec(iv);

       // Create AES cipher instance
        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
        // Initialize the cipher for decryption
        aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ips);
        
        // Read ciphertext from file and decrypt it
        FileInputStream fis = new FileInputStream("/root/Desktop/keys2/scrambled");
        BufferedInputStream bis = new BufferedInputStream(fis);
        CipherInputStream cis = new CipherInputStream(bis, aesCipher);
        
        StringBuffer plaintext = new StringBuffer();
        int c;
        while ((c = cis.read()) != -1)
            plaintext.append((char) c);
        cis.close();
        bis.close();
        fis.close();
        
        System.out.println("Plaintext: " + plaintext.toString());
        return plaintext.toString();       
	}
	
	
	public String aesEncrypt(String text)throws Exception{
	
		//File containing secret AES key
		FileInputStream keyFIS = new FileInputStream("/root/Desktop/keys2/AESKeyFile");
		ObjectInputStream keyOIS = new ObjectInputStream(keyFIS);
		
		//Read in the AES key
		SecretKey aesKey = (SecretKey) keyOIS.readObject();
		keyOIS.close();
		keyFIS.close();
		
		// Set IV (required for CBC)
		byte[] iv = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		IvParameterSpec ips = new IvParameterSpec(iv);
		
		// Create AES cipher instance
		Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		// Initialize the cipher for Encryption
		aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, ips);
		
		//File for writing output
		FileOutputStream fos = new FileOutputStream("/root/Desktop/keys2/scrambled");
		
        // This is the message to be encrypted
        byte plaintext[] = text.getBytes();
        
        //Encrypt the plainText
        byte[] cipherText = aesCipher.doFinal(plaintext);
        
        //Write cipherText to file
        fos.write(cipherText);
        fos.close();
        
     // Also display it in Hex on stdout
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < cipherText.length; i++)
        {
            hexString.append(Integer.toHexString(0xF & cipherText[i]>>4));
            hexString.append(Integer.toHexString(0xF & cipherText[i]));
            hexString.append("");
        }
        System.out.println("Plaintext: " + text);
        System.out.println("Ciphertext: " + hexString.toString());
        return new String(hexString);
        
    }
     
	/*
	 * Method that generates AES Key and writes into the file
	 */
			
	
	public void aesKeyGen()throws Exception{
		
		//File for writing output
		FileOutputStream fos = new FileOutputStream("/root/Desktop/keys2/AESKeyFile");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		//Generate random AES key using random bits generated by generateRandom method
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");	
		
		//Key initialization using generateRandom Method
		keyGen.init(generateRandom("SHA1PRNG",128));	
		
		SecretKey aesKey = keyGen.generateKey();
		oos.writeObject(aesKey);
		
		System.out.println("AES key generated and written to file: AESKeyFile");
		oos.close();
		fos.close();
	}
	
	/*
	 * Method generates random bytes and outputs into the file, user can choose size and algorithm
	 */
	
	public SecureRandom generateRandom(String algorithm,int number)throws Exception{
		
	   SecureRandom sRandom = SecureRandom.getInstance(algorithm);
	   byte[] randomBytes = new byte[number];
	   sRandom.nextBytes(randomBytes);
	   
	   //File for writing output
	    FileOutputStream fos = new FileOutputStream("/root/Desktop/keys2/randomBytes");
	 			   
	   StringBuffer hexString = new StringBuffer();
	   for(int i = 0; i < randomBytes.length; i++){
		   hexString.append(Integer.toHexString(0xF & randomBytes[i] >> 4));
		   hexString.append(Integer.toHexString(0xF & randomBytes[i]));
		   hexString.append("");
	   }
	   System.out.println ("Generate Random:" + hexString.toString());
	 
	   fos.write(randomBytes);
	   fos.close();
	   
	return sRandom;
	}
	
}
