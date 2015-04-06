package m_mail_app;

import java.io.*;
import java.security.*;
import java.security.spec.*;

import javax.crypto.*;
import javax.crypto.spec.*;

public class RSA_TEST {

	public static final int AES_Key_Size = 256;
	
	Cipher pkCipher, aesCipher;
	byte[] aesKey;
	SecretKeySpec aeskeySpec;
	
	public static void main(String[] args) throws IOException, GeneralSecurityException, InterruptedException{
		RSA_TEST secure = new RSA_TEST();

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
         
		Thread.sleep(5000);

		/*
		 * Decrypt AES Key with RSA Private Key
		 */
	    secure.loadKey(new File("/root/Desktop/mp3/AES_ENCRYPTED_RSA"), new File("private.der"));
	    
	    /*
	     * Decrypt File with Extracted AES Key file
	     */
		secure.decrypt(new File("/root/Desktop/mp3/geltona_zalia_raudona_ENCRYPTED"),new File("/root/Desktop/mp3/geltona_zalia_raudona_DEcrypted.mp3"));
	}
	
	
	
	/**
	 * Constructor: creates ciphers
	 * @return 
	 */
	public void FileEncryption() throws GeneralSecurityException {
		// create RSA public key cipher
		pkCipher = Cipher.getInstance("RSA");
	    // create AES shared key cipher
	    aesCipher = Cipher.getInstance("AES");
	}
	
	/**
	 * Creates a new AES key
	 * @throws IOException 
	 * @throws InterruptedException 
	 * move key into directory
	 */
	public void makeKey(String directory) throws NoSuchAlgorithmException, IOException, InterruptedException {
		
		// File for writing output
		File file = new File("AESKeyFile");
		if(file.exists()){
			file.delete();
		}
		Thread.sleep(6000);
		
        FileOutputStream keyFOS = new FileOutputStream(directory + "AESKeyFile");
        ObjectOutputStream keyOOS = new ObjectOutputStream(keyFOS);		
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
	    kgen.init(AES_Key_Size);
	    SecretKey key = kgen.generateKey();
	    aesKey = key.getEncoded();
	    aeskeySpec = new SecretKeySpec(aesKey, "AES");
	    keyOOS.writeObject(aeskeySpec);          
        System.out.println("AES key generated and written to file: AESKeyFile");        
        keyOOS.close();
        keyFOS.close();	    
	}

	/**
	 * Decrypts an AES key from a file using an RSA private key
	 * @throws InterruptedException 
	 */
	public void loadKey(File in, File privateKeyFile) throws GeneralSecurityException, IOException, InterruptedException {
		
		File file = new File("AES_Decrypted_RSA");
		if(file.exists()){
			file.delete();
		}
		
		Thread.sleep(5000);
		
		// read private key to be used to decrypt the AES key
		byte[] encodedKey = new byte[(int)privateKeyFile.length()];
		new FileInputStream(privateKeyFile).read(encodedKey);
		
		// create private key
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pk = kf.generatePrivate(privateKeySpec);
		
		// read AES key
		pkCipher.init(Cipher.DECRYPT_MODE, pk);
		aesKey = new byte[AES_Key_Size/8];
		CipherInputStream is = new CipherInputStream(new FileInputStream(in), pkCipher);
		is.read(aesKey);
		aeskeySpec = new SecretKeySpec(aesKey, "AES");
		
		/*
		 * Write to new file decrypted AES key
		 
		FileOutputStream fos = new FileOutputStream("AES_Decrypted_RSA");
		ObjectOutputStream keyOOS = new ObjectOutputStream(fos);
		keyOOS.writeObject(aeskeySpec);
		*/
	}
	
	/**
	 * Encrypts the AES key to a file using an RSA public key
	 * @param out
	 * @param publicKeyFile
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public void saveKey(File out, File publicKeyFile) throws IOException, GeneralSecurityException {
		
		// read public key to be used to encrypt the AES key
		byte[] encodedKey = new byte[(int)publicKeyFile.length()];
		new FileInputStream(publicKeyFile).read(encodedKey);
		
		// create public key
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pk = kf.generatePublic(publicKeySpec);
		
		// write AES key
		pkCipher.init(Cipher.ENCRYPT_MODE, pk);
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(out), pkCipher);
		os.write(aesKey);
		os.close();
	}
	
	/**
	 * Encrypts and then copies the contents of a given file.
	 */
	public void encrypt(File in, File out) throws IOException, InvalidKeyException {
		aesCipher.init(Cipher.ENCRYPT_MODE, aeskeySpec);
		
		FileInputStream is = new FileInputStream(in);
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(out), aesCipher);
		
		copy(is, os);
		
		os.close();
	}
	
	/**
	 * Decrypts and then copies the contents of a given file.
	 */
	public void decrypt(File in, File out) throws IOException, InvalidKeyException {
		aesCipher.init(Cipher.DECRYPT_MODE, aeskeySpec);
		
		CipherInputStream is = new CipherInputStream(new FileInputStream(in), aesCipher);
		FileOutputStream os = new FileOutputStream(out);
		
		copy(is, os);
		
		is.close();
		os.close();
	}
	
	/**
	 * Copies a stream.
	 */
	private void copy(InputStream is, OutputStream os) throws IOException {
		int i;
		byte[] b = new byte[1024];
		while((i=is.read(b))!=-1) {
			os.write(b, 0, i);
		}
	}
	
}	




