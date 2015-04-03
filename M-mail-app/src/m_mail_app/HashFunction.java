package m_mail_app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;

public class HashFunction {
	
	public static void main(String[] args) throws Exception{
		HashFunction function = new HashFunction();
		function.generateHash("Hello MY Friend");
		generateFileHash(new File("/root/Attachments2/4TMP.zip"));
	}
	
	public static boolean compareFileHashes(File file, File file2) throws NoSuchAlgorithmException, IOException{
		
		String hash1 = generateFileHash(file);
		String hash2 = generateFileHash(file2);
		
		if(hash1.equals(hash2)){
			return true;
		}else{
			return false;
		}
	}
	
	public static String generateFileHash(File file) throws NoSuchAlgorithmException, IOException{
		
		FileInputStream fis = new FileInputStream(file);
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] byteBuffer = new byte[1024];
		int bytesRead = -1;
		
		while((bytesRead = fis.read(byteBuffer)) != -1){
			md.update(byteBuffer, 0, bytesRead);
		}
		
		//@Close
		fis.close();
		
		byte[] hashedBytes = md.digest();
		return convertByteArrayToHexString(hashedBytes);
	}
	
	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		
        StringBuffer stringBuffer = new StringBuffer();
        
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        System.out.println("File: " + stringBuffer.toString());
        return stringBuffer.toString();
    }
	

	public String generateHash (String text) throws Exception{
		
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(text.getBytes());
		byte[] byteData = md.digest();
		
		//convert the byte to hex format method
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println("Text: " + sb.toString());
        return sb.toString();      
	}
}
