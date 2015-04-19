package m_mail_app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESFileDecryption {
	//@Test
	public static void main(String[] args) throws Exception {

		fileDecryption("/root/Attachment/TMP/rules.sh_encrypted.des", "/root/Attachment/rules.sh","ｍｍﾀｕｕｐｇｋｆｈｷｷｼｹ［ｬｂｅｮﾁｾｖｏﾂﾂｲｂｅｷｶｌｏｋｫｯ＼＾ｮｿｓ","/root/Attachment/TMP/salt.enc", "/root/Attachment/TMP/iv.enc");
			
	}
 
	public static void fileDecryption(String fileDecrypt, String fileOutput, String password, String saltSafe, String ivInput) throws Exception{
		

		// reading the salt
		// user should have secure mechanism to transfer the
		// salt, iv and password to the recipient
		FileInputStream saltFis = new FileInputStream(saltSafe);
		byte[] salt = new byte[8];
		saltFis.read(salt);
		saltFis.close();

		// reading the iv
		FileInputStream ivFis = new FileInputStream(ivInput);
		byte[] iv = new byte[16];
		ivFis.read(iv);
		ivFis.close();

		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
				256);
		SecretKey tmp = factory.generateSecret(keySpec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

		// file decryption
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
		FileInputStream fis = new FileInputStream(fileDecrypt);
		FileOutputStream fos = new FileOutputStream(fileOutput);
		byte[] in = new byte[64];
		int read;
		while ((read = fis.read(in)) != -1) {
			byte[] output = cipher.update(in, 0, read);
			if (output != null)
				fos.write(output);
		}

		byte[] output = cipher.doFinal();
		if (output != null)
			fos.write(output);
		fis.close();
		fos.flush();
		fos.close();
		System.out.println("File Decrypted.");
	}

}
