package edu.kh.gihyun;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.gihyun.model.vo.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Gihyun {
   public static void main(String[] args) {
	
	   
	   Connection conn = null;
	   
	   Statement stmt = null;
	   
	   ResultSet rs = null;
	   
	   Scanner sc = new Scanner(System.in);
	   
	   try {
		   
		     System.out.print("날짜 입력(YYYY-MM-DD) : ");
		     String date = sc.next();
		   
		   //입사일이 2000-01-01 이후에 입사한 사람들 조회
		   // 남자면 M 여자면 F
		   		Class.forName("oracle.jdbc.driver.OracleDriver");
		   		
		   		String local = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		   		String id = "kh_sgh";
		   		String pw = "kh1234";
		   		
		   		conn = DriverManager.getConnection(local, id, pw);
		   		
		   		stmt = conn.createStatement();
		   		
		   		
		   		String sql = "SELECT EMP_NAME, TO_CHAR(HIRE_DATE, 'YYYY\"년\"MM\"월\"DD\"일\"') AS 입사일, DECODE(SUBSTR(EMP_NO, 8, 1), 1, 'M' , 2, 'F'   ) AS 성별 FROM EMPLOYEE WHERE HIRE_DATE >" + "'" + date +  "'"; 
		   		
		   		
		   		rs = stmt.executeQuery(sql);
		    
		   		List<Employee> list = new ArrayList<Employee>(); 
		   		
		   		 while(rs.next()) {
		   			 
		   			 String empName = rs.getString("EMP_NAME");
		   			 String empDate = rs.getString("입사일");
		   			 char ch = rs.getString("성별").charAt(0);
		   			 
		   			 list.add(new Employee(empName, empDate, ch));
		   			 
		   			 
		   		 }
		   		 
		   		 if(list.isEmpty()) {
		   			 System.out.println("조회 결과 없음");
		   		 }else {
		   			 
		   			 for(int i = 0; i < list.size(); i++) {
		   				 
		   				 System.out.printf("%2d) %s / %s / %c\n", i+1, list.get(i).getEmpName(), list.get(i).getDate(), list.get(i).getGender());
		   				 
		   			 }
		   			 
		   		 }
		   
		   
		   
	   }catch(Exception e) {
		   e.printStackTrace();
	   }finally {
		   
		   try {
			   
			   
			   if(rs != null)rs.close();
			   if(stmt != null)stmt.close();
			   if(rs != null)rs.close();
			   
		   }catch(Exception e) {
			   e.printStackTrace();
		   }
		   
	   }
	   
	   
}
}
