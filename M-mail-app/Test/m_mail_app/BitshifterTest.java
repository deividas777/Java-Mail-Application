package m_mail_app;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class BitshifterTest {

	@Test
	public void test_string_encrypt_decrypt() {
	
	//@build chain
		int[] chain = Bitshifter.buildChain(12);
	//@string to encrypt
		String word = "Hello World!";
	//@execute encrypt
		String enc = Bitshifter.encrypt(word, chain);	
   //@expected result
		String result = "ĭŉŏŎŐĀņŝşŘŏĆ";
	//@assert
		assertEquals(enc, result);
	//@string to decrypt	
		String dec = Bitshifter.decrypt(enc, chain);
    //@assert
		assertEquals(dec, word);		
	}
	
	@Test
	public void test_file_encrypt_decrypt() throws IOException{
		
		//@file to encrypt
		String file = "plainfile_decrypted.txt";
		Bitshifter.fileEncrypt(file);
		
		String enc = "";
		int[] ck = Bitshifter.buildChain(23);
		enc = Bitshifter.encrypt(Bitshifter.encrypt, ck);
		System.out.println(enc + " " + enc.length());
		
		String dec = "";
		dec = Bitshifter.decrypt(enc, ck);
		System.out.println(dec + " "+ dec.length());
		
		//assert
		assertEquals(enc.length(), dec.length());
		
	}
	
	@Test
	public void test_RemoveUnicode(){
		
		String unicode = "Hello";
		String removed = Bitshifter.NCR2UnicodeString(unicode);
		System.out.println(removed);
	}

}
