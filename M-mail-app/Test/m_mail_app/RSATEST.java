package m_mail_app;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class RSATEST {
	
	RSA_TEST secure = new RSA_TEST();

	@Test
	public void test() throws IOException, InterruptedException, GeneralSecurityException {
		
		

		/*
		 * To encrypt file make AES KEY and save into the file
		 */
			secure.makeKey("");
		
			/*
			 * Get instances of AES and RSA encryption Chippers
			 */
			secure.FileEncryption();
			
			/*
			 * Encrypt actual file with AES Key
			 */
			secure.encrypt(new File("/root/Desktop/mp3/geltona_zalia_raudona.mp3"), new File("/root/Desktop/mp3/geltona_zalia_raudona_ENCRYPTED"));		
			/*
			 * Encrypt AES Key with RSA Public Key
			 */
			secure.saveKey(new File("/root/Desktop/mp3/AES_ENCRYPTED_RSA"), new File("public.der"));
		
	}
	
	@Test
	public void decrypt_test() throws GeneralSecurityException, IOException, InterruptedException{
		
		/*
		 * Get instances of AES and RSA encryption Chippers
		 */
		secure.FileEncryption();
		
		/*
		 * Decrypt AES Key with RSA Private Key
		 */
	    secure.loadKey(new File("/root/Desktop/mp3/AES_ENCRYPTED_RSA"), new File("private.der"));
	    
	    /*
	     * Decrypt File with Extracted AES Key file
	     */
		secure.decrypt(new File("/root/Desktop/mp3/geltona_zalia_raudona_ENCRYPTED"),new File("/root/Desktop/mp3/geltona_zalia_raudona_DEcrypted.mp3"));
	}

}
