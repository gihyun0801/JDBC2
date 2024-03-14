package edu.kh.jdbc.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.vo.TestVO;

public class TestDao {
 
	 //DAO ( DATE Access Object) : 데이터가 저장된 DB에 접근하는 객체
	//                     -> SQL 수행 , 결과 반환 받는 기능을 수행
	
	 private Statement stmt = null;
	 private PreparedStatement psmt = null;
	 
	 // xml 로 SQL 을 다룰것이다 -> Properties 객체 사용
	 private Properties prop = null;
	 
	 
	 
	 public TestDao() {
		 //TestDao 객체 생성시
		 // test-query.xml 파일의 내용을 읽어와
		 // properties 객체에 저장
		 
		 
		 prop = new Properties();
		 
		 
		 try {
			
			 prop.loadFromXML(new FileInputStream("test-query.xml"));
			 
			 
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 
		 
		 
	 }
	 
	 public int insert(Connection conn, TestVO vo1) throws Exception{
		 
		 // 1. 결과 저장용 변수 선언 
		 int result = 0;
		 
		 try {
			 // 2.SQL 작성(test-query.xml에 작성된 SQL 얻어오기)
			 
			 String sql = prop.getProperty("insert");
			 
			 // 버스를 만들라면 connection 이 필요하기 떄문이다 
			 psmt = conn.prepareStatement(sql);
			 
			 
			 // 4. 위치 홀더 (?)에 알맞은값 세팅
			 psmt.setInt(1,vo1.getTestNo());
			 psmt.setString(2, vo1.getTestTitle());
			 psmt.setString(3, vo1.getTestContent());
			 
			 //5. SQL(INSERT) 수행 후 결과 반환받기
			 
			 
			 result = psmt.executeUpdate(); // 
		 }finally {
			 
			 //자원 반환
			 //6. 사용한 JDBC 자원 반환
			 
			 JDBCTemplate.close(psmt);
			 
			 
		 }
		 
		return result;
	 }
	 
	 
	 //번호가 일치하는 행의 제목, 내용을 수정 DAO
	 
	 
	 public int update(Connection conn, TestVO vo1) throws Exception{
		 
		 
		 int result = 0;//결과 저장용 변수
		 
		 try {
			 
			 
			 String sql = prop.getProperty("update");
			 
			 psmt = conn.prepareStatement(sql);
			 
			 psmt.setString(1, vo1.getTestTitle());
			 psmt.setString(2, vo1.getTestContent());
			 psmt.setInt(3, vo1.getTestNo());
			 
			 result = psmt.executeUpdate();
			 
			 JDBCTemplate.close(psmt);
			 
			 
		 }finally {
			 
		 }
		 
		 
		 
		 return result;
		 
		 
	 }

	public int delete(int num, Connection conn) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("delete");
			
			psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, num);
			
			result = psmt.executeUpdate();
			
			JDBCTemplate.close(psmt);
			
			
		}finally {
			
		}
			
		
		
		
		return result;
	}
	 
	  
	 
}
