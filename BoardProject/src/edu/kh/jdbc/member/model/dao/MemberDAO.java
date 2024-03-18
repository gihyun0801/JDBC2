package edu.kh.jdbc.member.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.spi.DirStateFactory.Result;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.member.model.dto.Member;

public class MemberDAO {
        
	//JDBC 객체 참조 변수
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public MemberDAO() {
		
			try {
				
				prop = new Properties();
				
				prop.loadFromXML(new FileInputStream("member-sql.xml."));
				
				
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		
	}



	public List<Member> selectMemberList(Connection conn2) throws Exception{
		List<Member> list = new ArrayList<Member>();
		
		
			try {
			
			String sql = prop.getProperty("selectMemberList");
			
			stmt = conn2.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String Id = rs.getString("MEMBER_ID");
				String nM = rs.getString("MEMBER_NM");
				String gender = rs.getString("MEMBER_GENDER");
				
				Member mem = new Member();
				
				mem.setMemberId(Id);
				mem.setMemberName(nM);
				mem.setMemberGender(gender);
				
				list.add(mem);
				
				
			}
			
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
		}
		
		
		return list;
	}



	public List<Member> memberAllSearch(Connection conn, String memberId) throws Exception{
		List<Member> list = new ArrayList<Member>();
		
		try {
			
			String sql = prop.getProperty("memberAllSearch");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				Member m = new Member();
				
				String memId = rs.getString("MEMBER_ID");
				
				m.setMemberId(memId);
				
				list.add(m);
				
				
			}
			
			
		}finally {
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
			
			
		}
		
		return list;
	}



	public int updateMember(Connection conn, String memberName, String memberGender, int memberNO) {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("updateMember");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberGender);
			pstmt.setString(2, memberName);
			pstmt.setInt(3, memberNO);
			
			result = pstmt.executeUpdate();
			
			JDBCTemplate.close(pstmt);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		return result;
	}
	
}
