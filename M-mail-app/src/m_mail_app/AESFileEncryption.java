package m_mail_app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESFileEncryption {
	
	public static String filePath;
	
	//@Test
	public static void main(String[] args) throws Exception {

		String password = "geltona_zalia_raudona.mp3";
		HashFunction hashF = new HashFunction();
		//Bitshifter bit = new Bitshifter();
		//password = hashF.generateHash(password);
		
		/*
		 * Number of Iterations depends on file name length
		 */
		BufferedReader bf = null;
		bf = new BufferedReader(new FileReader("/root/Attachment/TMP/iv.enc"));
		String line;
		int tmp = 0;
		while((line = bf.readLine()) != null){
			byte[] test = new byte[line.length()];
			test = line.getBytes();
			
			for(int u = 0; u < test.length;u++){
				int gt = test[u];			
				tmp = tmp + gt;
			System.out.println("BYTE: " + test[u] + " Value: " + tmp);
			}			
		}
		//@Close BufferedReader
		bf.close();
		
		if(tmp <= 0){
			tmp = ((tmp) * (-2));
		}		
		System.out.println("TMP Value: " + tmp);
		
		for(int i = 0; i < (password.length() ^ 2)/ 12 ;i++){
			
			password = hashF.generateHash(password);
			System.out.println(i + " ==> " + password);
			
		}
		
		System.out.println( "Password Length ==> " + (password.length() ^ 2)/ 12);
		
		int[] chain = Bitshifter.buildChain(password.length()/2);
		password = Bitshifter.encrypt(password, chain).toString();
		System.out.println("New Password: " + password);
		
				
		fileEncryption("/root/Desktop/mp3/","geltona_zalia_raudona.mp3","geltona_zalia_raudona.mp3_encrypted.des",password, "salt.enc", "iv.enc");
		Zip.zipFolder("/root/Desktop/mp3/TMP", "/root/Desktop/mp3/TMP.zip");
		//Thread.sleep(3000);
		//System.out.println("Password: " + password);
		
		/*
	          byte[] str = new byte[password.length()];
	          str = password.getBytes();
	          FileOutputStream fos = new FileOutputStream("/root/Desktop/mp3/TMP/password");
	          fos.write(str);
	          fos.close();
	          
	          
		
		RSA_TEST rsaTest = new RSA_TEST();
		rsaTest.saveKey(new File("/root/Desktop/mp3/TMP/password"), new File("public.der"));
		*/
		
       String filePath = "/root/Desktop/mp3/";
       String fileName = "geltona_zalia_raudona.mp3";
       password = filePath + fileName;
       int i = password.length();
       password = password.substring(password.length() - fileName.length(), i);
       System.out.println("Updated Password: " + password);
       
       /*
		 * Number of Iterations depends on file name length
		 */
        //password = hashF.generateHash(password);
		for(int y = 0; y < (password.length() ^ 2)/ 12  ;y++){
			
			password = hashF.generateHash(password);
			System.out.println(y + " ==> " + password);
			
		}
		
		
		
		int[] chain2 = Bitshifter.buildChain(password.length()/2);
		password = Bitshifter.encrypt(password, chain2).toString();
		System.out.println("Decryption Password: " + password);
       
       
		
		//AESFileDecryption filedec = new AESFileDecryption();
		AESFileDecryption.fileDecryption("/root/Desktop/mp3/TMP/geltona_zalia_raudona.mp3_encrypted.des", "/root/Desktop/mp3/decrypted.mp", password, "/root/Desktop/mp3/TMP/salt.enc", "/root/Desktop/mp3/TMP/iv.enc");
		

		/*
		// to encrypt a file RSA_TEST secure = new RSA_TEST();
		secure.makeKey();
		secure.FileEncryption();
		secure.encrypt(new File("/root/Desktop/mp3/geltona_zalia_raudona.mp3"), new File("/root/Desktop/mp3/geltona_zalia_raudona_ENCRYPTED"));		
		secure.saveKey(new File("/root/Desktop/mp3/AES_ENCRYPTED_RSA"), new File("public.der"));
         
		Thread.sleep(5000);

		// to decrypt it again
	    secure.loadKey(new File("/root/Desktop/mp3/AES_ENCRYPTED_RSA"), new File("private.der"));
		secure.decrypt(new File("/root/Desktop/mp3/geltona_zalia_raudona_ENCRYPTED"),new File("/root/Desktop/mp3/geltona_zalia_raudona_DEcrypted.mp3"));
		//Utility.deleteDirectory(new File("/root/Desktop/mp3/TMP"));
		/*
		File file  = new File("/root/Desktop/mp3/TMP.zip");
		if(file.exists()){
			file.delete();
		}
		*/
	}
	
	
	
	public static void fileEncryption(String file_Path, String fileEncrypt, String fileOutput, String password, String saltSafe, String ivOutput) throws Exception{		
		
		/*
		 * Create TMP folder to store String fileOutput,  String saltSafe, String ivOutput
		 */
				
		File file = new File(file_Path+"TMP");
		  if(!file.exists()){
			  if(file.mkdir()){
                 System.out.println("Directory Created!" + file_Path + "TMP");
                 
		
		        // input file to be encrypted
				FileInputStream inFile = new FileInputStream(file_Path+fileEncrypt);

				// output encrypted file 
				FileOutputStream outFile = new FileOutputStream(file_Path+"TMP/"+fileOutput);

				// password, iv and salt should be transferred to the other end
				// in a secure manner

				// salt is used for encoding
				// writing it to a file
				// salt should be transferred to the recipient securely
				// for decryption
				
				byte[] salt = new byte[8];
				SecureRandom secureRandom = new SecureRandom();
				secureRandom.nextBytes(salt);
				FileOutputStream saltOutFile = new FileOutputStream(file_Path+"TMP/"+saltSafe);
				saltOutFile.write(salt);
				saltOutFile.close();

				SecretKeyFactory factory = SecretKeyFactory
						.getInstance("PBKDF2WithHmacSHA1");
				KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
				SecretKey secretKey = factory.generateSecret(keySpec);
				SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, secret);
				AlgorithmParameters params = cipher.getParameters();

				//@IV adds randomness to the text and just makes the mechanism more
				//@Secure
				//@Used while initializing the cipher
				//@File to store the iv
				
				FileOutputStream ivOutFile = new FileOutputStream(file_Path+"TMP/"+ivOutput);
				byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
				ivOutFile.write(iv);
				ivOutFile.close();

				//@File encryption
				
				byte[] input = new byte[64];
				int bytesRead;

				while ((bytesRead = inFile.read(input)) != -1) {
					byte[] output = cipher.update(input, 0, bytesRead);
					if (output != null)
						outFile.write(output);
				}

				byte[] output = cipher.doFinal();
				if (output != null)
					outFile.write(output);

				inFile.close();
				outFile.flush();
				outFile.close();

				System.out.println("File Encrypted.");
	         }
		  }else{
			  System.out.println("Directory exits!");
		  }
	  }
}
