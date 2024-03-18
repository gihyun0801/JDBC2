package edu.kh.jdbc.main.model.service;

import java.sql.Connection;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.main.model.dao.MainDao;
import edu.kh.jdbc.member.model.dto.Member;

public class MainService {
  
	 private MainDao dao = new MainDao();

	public Member login(String memberId, String memberPw) throws Exception{
		
		
		Connection conn = JDBCTemplate.getConnection();
		
		//2 .DAO 호출
		Member member = dao.login(conn, memberId, memberPw);
		
		JDBCTemplate.close(conn);
		
		return member;
	}

	public int idDuplicationCheck(String memberId) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.idDuplicationCheck(conn, memberId);
		
		JDBCTemplate.close(conn);
		return result;
	}

	public int signUp(Member member) throws Exception{
		
		
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.signUp(conn, member);
		
		if(result == 1) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		return result;
	}
	 
	 
	 
	 
	 
	 
}
