package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	
	   /* DB 연결 (Connection 생성) + 자동 커밋 off, JDBC 객체 자원 반환(close)
	    * 트랜잭션 제어
	    * 
	    * 이러한 JDBC에서 반복 사용되는 코드를 모아둔 클래스
	    * 
	    * * 모든 필드, 메서드가 static * 
	    *  -> 어디서든지 클래스명.변수명 / 클래스명.변수명 호출 가능 
	    *  -> 별도 객체 생성 x
	    * 
	    * */
	   
	   private static Connection conn = null;
      
	    
	   // DB연결 정보를 담고있는 CONNECTION 객체 생성 및 반환 메서드
	    
	   
	   public static Connection getConnection() {
		   
		   try {
			   
			   // 현재 커넥션 객체가 없을 경우 -> 새 커넥션 객체 생성
			   if(conn == null || conn.isClosed()) {
				    //conn.isClosed() 커넥션 통로가 닫혀있는 여부를 확인 true/false반환
				   
				   Properties prop = new Properties();
				   //Map 의 자식객체 key,value 전부 String , xml 입출력 특화
				   
				   // driver.xml 파일 읽어오기
				   
				   prop.loadFromXML(new FileInputStream("driver.xml"));
				   
				   String driver = prop.getProperty("driver");
		            String url =prop.getProperty("url");
		           String user = prop.getProperty("user");
		            String password = prop.getProperty("password");
		            Class.forName(driver);
		          
		            
		            conn = DriverManager.getConnection(url, user, password);
		            
		            
		            
		            // + 자동 커밋 비활성화
		            conn.setAutoCommit(false);
				   
			   }
			   
			   
		   }catch(Exception e) {
			   System.out.println("[connection 생성 중 예외 발생]");
			   e.printStackTrace();
		   }
		   
		   return conn;
	   };
	   
	   public static void close(Connection conn) {
		   
		   try {
			   
			   //전달받은 conn이
			   //참조하는 Connection 객체가 있고
			   //그 Connection 객체가 close 상태가 아니라면
			   //자원 반환 해라
			   
              if(conn != null&& !conn.isClosed()) conn.close();
            	  
              
			   
			   
		   }catch(Exception e) {
			   e.printStackTrace();
		   }
		   
	   }
 
	    
	   //------------------------------------------------------------------------
	   // Statement(부모) , PreparedStatement(자식) 객체 자원 반환 메서드
	   public static void close(Statement stmt) {
		   
		   
		   try {
			   
			   if(stmt != null && !stmt.isClosed()) stmt.close();
			   
		   }catch(Exception e) {
			   e.printStackTrace();
		   }
		   
	   }
	   
	   
	   //ResultSet 객체 자원 반환 메서드
	   public static void close(ResultSet rs) {
		   
		   try {
			   if(rs != null && !rs.isClosed()) rs.close();
			   
			   
		   }catch(Exception e) {
			   e.printStackTrace();
		   }
		   
	   }
	   
	   //트랜잭션 commit 메서드
	   public static void commit(Connection conn) {
		   
		   try {
			   
			   if(conn != null & !conn.isClosed()) conn.commit();
			   
		   }catch(Exception e) {
			   e.printStackTrace();
		   }
		   
	   }
	   
	   //트랜잭션 rollback 메서드
	   public static void rollback(Connection conn) {
try {
			   
			   if(conn != null & !conn.isClosed()) conn.rollback();;
			   
		   }catch(Exception e) {
			   e.printStackTrace();
		   }
	   }
}
