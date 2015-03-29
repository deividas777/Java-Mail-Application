package m_mail_app;

import java.io.*;
import java.security.*;
import javax.crypto.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public class RsaEncryption {
	
	public static final String ALGORITHM = "RSA";
	public static final String PRIVATE_KEY_FILE = "RSAPrivateKeyFile";
	public static final String PUBLIC_KEY_FILE = "RSAPublicKeyFile";

	//Method that decrypts encrypted file using RSA private key
	// decrypted file will be used to Rebuild AES Key
	
	
		 
	
     public static void decryptSessionKey() throws Exception {
		
	    Cipher cipher = Cipher.getInstance("RSA");	
	    ObjectInputStream  inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
		   final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
		     cipher.init(Cipher.DECRYPT_MODE, privateKey);	
		      		
		String cleartextFile = "decrypted_RSA";
		String ciphertextFile = "scrambled";
			 
		FileInputStream fis = new FileInputStream(ciphertextFile);
		FileOutputStream fos = new FileOutputStream(cleartextFile);
		CipherOutputStream cos = new CipherOutputStream(fos, cipher);
		
		byte[] block = new byte[1024];
		int i;
		while ((i = fis.read(block)) != -1) {
		cos.write(block, 0, i);
		}
	   cos.close();
	   inputStream.close();
	   fis.close();	  
     }
     
     
			
    //This method encrypts File using RSA public key
    //File containing Random Bits will be encrypted
     
	public static void encryptSessionKey() throws Exception {
		
	    Cipher cipher = Cipher.getInstance("RSA");	
	    ObjectInputStream  inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
		   final PublicKey publicKey = (PublicKey) inputStream.readObject();
		     cipher.init(Cipher.ENCRYPT_MODE, publicKey);	
		      		
		String cleartextFile = "AESKeyFile";
		String ciphertextFile = "AESKeyFile_RSA";
			 
		FileInputStream fis = new FileInputStream(cleartextFile);
		FileOutputStream fos = new FileOutputStream(ciphertextFile);
		CipherOutputStream cos = new CipherOutputStream(fos, cipher);
		
		byte[] block = new byte[1024];
		int i;
		while ((i = fis.read(block)) != -1) {
		cos.write(block, 0, i);
		}
	   cos.close();
	   inputStream.close();
	   fis.close();	  
 }
	
	public static String decrypt(byte[] text, PrivateKey key){
		
		byte[] decryptedText = null;
		try{
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			decryptedText = cipher.doFinal(text);
		}catch(Exception e){
			e.getMessage();
		} 
		return new String(decryptedText);
	}
	
	
	
	public static byte[] encrypt(String text, PublicKey key){
		
		byte[] cipherText = null;
		try{
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text.getBytes());
		}catch(Exception e){
			e.getMessage();
		}
		return cipherText;
	}
	
   //This Method will be used to encrypt String using Users Private Key
   //This method was created to encrypt Hash value of the input
	
   public static byte[] encrypt_Sign(String text, PrivateKey key){
		
		byte[] cipherText = null;
		try{
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text.getBytes());
		}catch(Exception e){
			e.getMessage();
		}
		return cipherText;
	}
   
   public void  decrypt_Sign()throws Exception{
		
	   Cipher cipher = Cipher.getInstance("RSA");	
	    ObjectInputStream  inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
		   final PublicKey publicKey = (PublicKey) inputStream.readObject();
		     cipher.init(Cipher.DECRYPT_MODE, publicKey);	
		      		
		String cleartextFile = "/root/Desktop/keys2/decrypted_Signature";
		String ciphertextFile = "/root/Desktop/keys2/encrypted_Signature";
			 
		FileInputStream fis = new FileInputStream(ciphertextFile);
		FileOutputStream fos = new FileOutputStream(cleartextFile);
		CipherOutputStream cos = new CipherOutputStream(fos, cipher);
		
		byte[] block = new byte[32];
		int i;
		while ((i = fis.read(block)) != -1) {
		cos.write(block, 0, i);
		}
	   cos.close();
	   inputStream.close();
	   fis.close();	  
    }
  
   
   
   	
   //This method checks for the RSA Keys 
	public static boolean areKeysPresent(){
		
		File privateKey = new File(PRIVATE_KEY_FILE);
		File publicKey = new File(PUBLIC_KEY_FILE);
		
		if(privateKey.exists() && publicKey.exists()){			
			return true;
		}
		return false;
	}
	
	//Method that generates RSA Key Pair, the user can choose the Key size
	//and outputs both keys into specified folder
	
	public static void generateKey(int size){
		try{
			
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
			keyGen.initialize(size);
			final KeyPair key = keyGen.generateKeyPair();
			
			File privateKeyFile = new File(PRIVATE_KEY_FILE);
			File publicKeyFile = new File(PUBLIC_KEY_FILE);
			
			if(privateKeyFile.getParentFile() != null){				
				privateKeyFile.getParentFile().mkdirs();
			}
			    privateKeyFile.createNewFile();
			    
			if(publicKeyFile.getParentFile() != null){
				publicKeyFile.getParentFile().mkdirs();
			}
			    publicKeyFile.createNewFile();
			    
			ObjectOutputStream publicKeyOS = new ObjectOutputStream (new FileOutputStream(publicKeyFile));
			publicKeyOS.writeObject(key.getPublic());
			publicKeyOS.close();
			
			ObjectOutputStream privateKeyOS = new ObjectOutputStream (new FileOutputStream(privateKeyFile));
			privateKeyOS.writeObject(key.getPrivate());
			privateKeyOS.close();
			
		}catch(Exception e){
			e.getMessage();
		}
	}
}
