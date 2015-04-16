package m_mail_app;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;

public class AESFileEncryption_Test {

	@Test
	public void test() throws Exception {
		
		String filePath = "/root/git/Java-Mail-Application/M-mail-app/";
		String fileName = "ssl_keys_commands";
		HashFunction hash = new HashFunction();
		String password = fileName;
		
		int iter = (password.length() ^ 3) % 3;
		if(iter == 0){
			iter = iter + 121;
		}
		
		//@Generate Hash
		for(int i = 0; i < iter;i++){
			password = hash.generateHash(password);
			System.out.println(i + " ==> " + password);
		}
		
		/*
		 * Encrypt password that was used to encrypt file
		 */
		int [] chain = Bitshifter.buildChain((password.length()/2));
		password = Bitshifter.encrypt(password, chain);
		
		String FileOutputName = fileName + "_encrypted.des";
		AESFileEncryption.fileEncryption(filePath,fileName, FileOutputName, password,"salt.enc","iv.enc");
	}

}
