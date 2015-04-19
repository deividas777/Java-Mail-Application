package m_mail_app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zip {

	//@Test
	public static void main( String[] args ){
    	
		//ZipFile("/root/Desktop/104NIKON/DSCN0783.JPG", "DSCN0783.JPG.zip", "DSCN0783.JPG");
		//unZipIt("/root/Attachments/TMP.zip", "/root/Attachments/");
    }
	
	/**
	 * 
	 * @param srcFolder
	 * @param destZipFile
	 * @throws Exception
	 */
	static public void zipFolder(String srcFolder, String destZipFile) throws Exception {
	    ZipOutputStream zip = null;
	    FileOutputStream fileWriter = null;

	    fileWriter = new FileOutputStream(destZipFile);
	    zip = new ZipOutputStream(fileWriter);

	    addFolderToZip("", srcFolder, zip);
	    zip.flush();
	    zip.close();
	    
	    
	  }
	
	/**
	 * 
	 * @param path
	 * @param srcFolder
	 * @param zip
	 * @throws Exception
	 */
	static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)throws Exception {
		    
		  File folder = new File(srcFolder);

		    for (String fileName : folder.list()) {
		      if (path.equals("")) {
		        addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
		      } else {
		        addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
		      }
		    }
		  }
	
	/**
	 * 
	 * @param path
	 * @param srcFile
	 * @param zip
	 * @throws Exception
	 */
	
	static private void addFileToZip(String path, String srcFile, ZipOutputStream zip)throws Exception {

		    File folder = new File(srcFile);
		    if (folder.isDirectory()) {
		      addFolderToZip(path, srcFile, zip);
		    } else {
		      byte[] buf = new byte[1024];
		      int len;
		      FileInputStream in = new FileInputStream(srcFile);
		      zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
		      while ((len = in.read(buf)) > 0) {
		        zip.write(buf, 0, len);
		      }
		      in.close();
		    }
		  }
	/**
	 * 
	 * @param fileToZip
	 * @param file_output
	 * @param zip_entry
	 */
	
    public static void ZipFile(String fileToZip, String file_output, String zip_entry){
    	
		byte[] buffer = new byte[2048];
 
    	try{
 
    		FileOutputStream fos = new FileOutputStream(file_output);
    		ZipOutputStream zos = new ZipOutputStream(fos);
    		ZipEntry ze= new ZipEntry(zip_entry);
    		zos.putNextEntry(ze);
    		FileInputStream in = new FileInputStream(fileToZip);
 
    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}
 
    		in.close();
    		zos.closeEntry();
 
    		//remember close it
    		zos.close();
 
    		System.out.println("Done");
 
    	}catch(IOException ex){
    	   ex.printStackTrace();
    	}
    	
  }
        
    /**
     * Unzip it
     * @param zipFile input zip file
     * @param output zip file output folder
     */
    public static void unZipIt(String zipFile, String outputFolder){
 
     byte[] buffer = new byte[2048];
 
     try{
 
    	//create output directory is not exists
    	File folder = new File(outputFolder);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
 
    	//get the zip file content
    	ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
 
    	while(ze!=null){
 
    	   String fileName = ze.getName();
           File newFile = new File(outputFolder + File.separator + fileName);
 
           System.out.println("file unzip : "+ newFile.getAbsoluteFile());
 
            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();
 
            FileOutputStream fos = new FileOutputStream(newFile);             
 
            int len;
            while ((len = zis.read(buffer)) > 0) {
       		fos.write(buffer, 0, len);
            }
 
            fos.close();   
            ze = zis.getNextEntry();
    	}
 
        zis.closeEntry();
    	zis.close();
 
    	System.out.println("Done");
 
    }catch(IOException ex){
       ex.printStackTrace(); 
    }
   }  
}

