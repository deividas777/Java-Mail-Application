package m_mail_app;

import static org.junit.Assert.*;

import org.junit.Test;

public class AESFileDecryption_Test {

	@Test
	public void test() throws Exception {
		
		String filePath = "/root/git/Java-Mail-Application/M-mail-app/TMP/";
		String fileName = "ssl_keys_commands_encrypted.des";
		HashFunction hash = new HashFunction();
		String password = fileName.substring(0, fileName.length() -14);
		
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
		
		AESFileDecryption.fileDecryption(filePath + "ssl_keys_commands_encrypted.des", filePath + "ssl_keys_commands", password, filePath +  "salt.enc", filePath +  "iv.enc");
		
	}

}
