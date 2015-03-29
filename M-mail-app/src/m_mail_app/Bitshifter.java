package m_mail_app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Bitshifter {
	
	public static String encrypt;
	public static String decrypt;
	
	private final int[] chain = {
		1234, 2314, 4354, 333, 2109, 90876, 765, 564, 8970, 9987, 3245, 1, 3, 2, 4, 7, 12, 43, 78, 98
	};
	
	public static void main(String[] args) throws IOException, InterruptedException{
		
		//Bitshifter shifter = new Bitshifter();
		
		BufferedReader br;
		br = new BufferedReader(new FileReader("plainfile_decrypted.txt"));
		String line;
		String st = "";
		
		while((line = br.readLine()) != null){
			StringTokenizer tokenizer = new StringTokenizer(line, " ");
			while(tokenizer.hasMoreElements()){
				 st = tokenizer.nextToken();
				 encrypt += st.trim();				
			}
			//System.out.println(encrypt);
		}
		line = null;
		st = null;
		br.close();
				
		int[] ck = Bitshifter.buildChain(5);
		String enc = Bitshifter.encrypt(encrypt,ck);
		
		System.out.println("Encrypted: " + NCR2UnicodeString(enc) + "\n");
		System.out.println("Importing File: ecncrypt_bitshifter\n");
		
	//@Write into a file
		BufferedWriter bw = new BufferedWriter(new FileWriter("encrypted_bitshifter"));		
		bw.write(enc);
		bw.newLine();
		
		bw.flush();
		bw.close();
		
		ck = null;
		
		br = new BufferedReader(new FileReader("encrypted_bitshifter"));
		while((line = br.readLine()) != null){
			decrypt += line;
		}
		
	//System.out.println(decrypt);
		br.close();
		ck = Bitshifter.buildChain(5);
					
		String dec = Bitshifter.decrypt(decrypt,ck);
		System.out.println("Decrypted:" + NCR2UnicodeString(dec));
	
	}//end
	
	
	
	
	public static String NCR2UnicodeString(String str){
		
		StringBuffer ostr = new StringBuffer();
		int i1=0;
		int i2=0;
		
			while(i2<str.length())
			{
				i1 = str.indexOf("&#",i2);
				if (i1 == -1 ) {
				ostr.append(str.substring(i2, str.length()));
				break ;
			}
				ostr.append(str.substring(i2, i1));
				i2 = str.indexOf(";", i1);
				if (i2 == -1 ) {
				ostr.append(str.substring(i1, str.length()));
				break ;
			}
		
				String tok = str.substring(i1+2, i2);
				try {
				int radix = 10 ;
				if (tok.trim().charAt(0) == 'x') {
				radix = 16 ;
				tok = tok.substring(1,tok.length());
			}
				ostr.append((char) Integer.parseInt(tok, radix));
			} catch (NumberFormatException exp) {
				ostr.append('?') ;
			}
				i2++ ;
			}
			return new String(ostr) ;
	}

	
	
	
	public static int[] buildChain(int length){
		
		int[] chain = new int[length];
		
		for(int i = 0; i < chain.length ; i++){
			chain[i] = (i + 2)^3 *543 % 233 % 321;
			System.out.println(i + ": " + chain[i]);
		}
		return chain; 	
	}
	
	public static String encrypt(String key, int[] chain){
		
		String result = "";
		char ch;
		int ck = 0;
		 
		 for(int i = 0; i < key.length(); i++){
			 if(ck >= chain.length - 1) ck = 0;
			  ch = key.charAt(i);
			   ch += chain[ck];
			    result += ch;
			     ck++;
			   //System.out.println(ch);
		 }
		 return result;
	}
	
	public static String decrypt(String key, int[] chain){
		
		String result = "";
		char ch = 0;
		int ck = 0;
		
		for(int i = 0; i < key.length(); i++){
			if(ck >= chain.length -1) ck = 0;
			 ch = key.charAt(i);
			  ch -= chain[ck];
			   result += ch;	
			    ck++;
			   // System.out.println(ch);
		}
		return result;
	}
	
	public static String fileEncrypt(String file) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		String st;
		while((line = br.readLine()) != null){
			StringTokenizer tokenizer = new StringTokenizer(line, "\\{\\}");
			while(tokenizer.hasMoreElements()){
				 st = tokenizer.nextToken();
				encrypt += st;
				
			}
			System.out.println("fileEncrypt(String file) ==> " + encrypt);
		}
		br.close();
		return encrypt;
		
	}
	

}
