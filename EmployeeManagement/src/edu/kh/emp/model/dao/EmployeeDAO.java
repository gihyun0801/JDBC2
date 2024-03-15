package edu.kh.emp.model.dao;

import java.io.FileInputStream;
import java.util.Properties;

public class EmployeeDAO {
 
	 
	public EmployeeDAO() {
		
		
		
		try {
			Properties proo = new Properties();
			
			
			proo.loadFromXML(new FileInputStream("driver.xml"));
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	 
}
