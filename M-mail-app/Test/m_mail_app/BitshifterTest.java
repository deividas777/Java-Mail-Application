package m_mail_app;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class BitshifterTest {

	@Test
	public void test_string_encrypt_decrypt() {
	
	//@build chain
		int[] chain = Bitshifter.buildChain(12);
	//@string to encrypt
		String word = "How can I convert the data in a (Access) database ";
		System.out.println("Text before encryption:" + word);
	//@execute encrypt
		String enc = Bitshifter.encrypt(word, chain);	
   //@expected result
		System.out.println("Text after encryption:" + enc);
		String result = "ĭœŚĂńŁŝĎĶČŎŔŒřŇœŔďŢŕőċŉŅŗŃāŉŝĎŎČēĦŇņŇŔœĘĎőōşņņńŕņĀ";
	//@assert
		assertEquals(enc, result);
	//@string to decrypt	
		String dec = Bitshifter.decrypt(enc, chain);
		System.out.println("Text after decryption:" + word);
    //@assert
		assertEquals(dec, word);		
	}
	
	//@Test
	public void test_file_encrypt_decrypt() throws IOException{
		
		//@file to encrypt
		String file = "plainfile_decrypted.txt";
		Bitshifter.fileEncrypt(file);
		
		String enc = "";
		int[] ck = Bitshifter.buildChain(file.length());
		enc = Bitshifter.encrypt(Bitshifter.encrypt, ck);
		System.out.println(enc + " " + enc.length());
		
		String dec = "";
		dec = Bitshifter.decrypt(enc, ck);
		System.out.println(dec + " "+ dec.length());
		
		//assert
		assertEquals(enc.length(), dec.length());
		
	}
	
	//@Test
	public void test_RemoveUnicode() throws UnsupportedEncodingException{
		
		String unicode = "ĭŉŏŎŐĀņŝşŘŏĆ";
		byte[] bs = unicode.getBytes("UTF-16");
		//String removed = new String (bs.toString());
		System.out.println(new String(bs, "UTF-16"));
	}

}
