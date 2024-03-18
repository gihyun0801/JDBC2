package edu.kh.jdbc.member.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.member.model.dao.MemberDAO;
import edu.kh.jdbc.member.model.dto.Member;

public class MemberService {
	private MemberDAO dao = new MemberDAO();

	public List<Member> selectMemberList() throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<Member> m = dao.selectMemberList(conn);
		
		JDBCTemplate.close(conn);
		
		
		return m;
	}

	

	public int updateMember(String memberName, String memberGender, int memberNO) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.updateMember(conn, memberName, memberGender, memberNO);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		return 0;
	}
}
