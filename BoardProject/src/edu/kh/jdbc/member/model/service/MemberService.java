package edu.kh.jdbc.member.model.service;

import java.sql.Connection;
import java.util.List;
import java.util.Random;

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



	public int updatePassword(String newPw1, String current, int memberPw) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.updatePassword(newPw1, current, memberPw,conn);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}



	/** 숫자 6자리 보안코드 생성 서비스
	 * @return code
	 */
	public String createSecurityCode() {
		
		StringBuffer code = new StringBuffer();
		
		Random random = new Random();
		
		
		
		for(int i = 0; i < 6; i++) {
			int x = random.nextInt(10);
			
			code.append(x);
		}
		
		return code.toString();
	}



	public int unRegisterMember(String memberPw, int memberNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.unRegisterMember(conn, memberPw, memberNo);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}
}
