package edu.kh.jdbc.main.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.member.model.dto.Member;

public class MainDao {
	
	
	//JDBC 객체 참조 변수
	
	//기본 생성자 DAO 객체가 생성될 떄 XML파일 읽어와 properties 객체 저장
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;
	public MainDao() {
		
		try{
			
			prop = new Properties();
			
			prop.loadFromXML(new FileInputStream("main-sql.xml"));
			// -> Properties 객체에
			// key : value 형식으로 xml 내용이 저장됨
			
	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/** 로그인 DAO(아이디, 비밀번호가 일치하는 회원이있는지 조회)
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public Member login(Connection conn, String memberId, String memberPw) throws Exception{
		Member member = null;
		
		try {
			
			//1. 결과 저장용 변수 생성
			
			
			// 비밀번호가 까지 가져오진않는다 보안상의 이유때문에 자바문에 절대 pw를 저장하면안됀다
			
			String sql = prop.getProperty("login");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int No = rs.getInt("MEMBER_NO");
				String memberName = rs.getString("MEMBER_NM");
				String memberid = rs.getString("MEMBER_ID");
				String memberGender = rs.getString("MEMBER_GENDER");
				String enrollDate = rs.getString("ENROLL_DT");
				
				member = new Member();
				
				member.setMemberNO(No);
				member.setMemberId(memberid);
				member.setMemberName(memberName);
				member.setMemberGender(memberGender);
				member.setEnrollDate(enrollDate);
				
				
				
			}
			
			
			
			
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
		}
		
		
		
		return member;
	}

	public int idDuplicationCheck(Connection conn, String memberId) {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("idDuplicationCheck");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				result = rs.getInt(1);  //첫번째 컬럼이란 뜻
				
			
				
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
			
		}
		
		
		return result;
	}

	public int signUp(Connection conn, Member member) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("signUp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberGender());
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			JDBCTemplate.close(pstmt);
			
			
		}
		
		return result;
	}
	

}
