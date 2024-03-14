package edu.kh.emp.common;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class CreateXMLFile {
          // driver.xml
	      // query.xml
	 
	      public static void main(String[] args) {
			
	    	  
	    	  try {
	    		  Properties proo = new Properties();
	    		  FileOutputStream fos = new FileOutputStream("driver.xml");
	    		  
	    		  FileOutputStream fos2 = new FileOutputStream("query.xml");
	    		  
	    		  proo.storeToXML(fos, "아아");
	    		  proo.storeToXML(fos2, "이이");
	    		  
	    	  }catch(Exception e) {
	    		  e.printStackTrace();
	    	  }
	    	 
	    	  
	    	 
	    	  
	    	  
		}
	 
	
}
