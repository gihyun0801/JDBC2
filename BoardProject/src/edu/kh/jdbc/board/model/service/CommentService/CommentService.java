package edu.kh.jdbc.board.model.service.CommentService;

import java.sql.Connection;

import edu.kh.jdbc.board.model.dao.CommentDAO.CommentDAO;
import edu.kh.jdbc.common.JDBCTemplate;

public class CommentService{
       private CommentDAO dao = new CommentDAO();

	public int insertComment(int boardNo, String commentContent, int memberNo)  throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.insertComment(conn,boardNo, commentContent, memberNo);
		
		if(result >0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		
		return result;
	}

	public int checkCommentNo(int commentNo, int boardNo, int memberNO) {
		
		
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.checkCommentNo(commentNo, boardNo, memberNO, conn);
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int updateComment(int commentNo, String string) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result2 = dao.updateComment(conn, commentNo, string);
		
		if(result2 > 0) JDBCTemplate.commit(conn);
		else  JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result2;
	}

	public int deleteCommentNo(int commentNo, int boardNo, int memberNO) {
Connection conn = JDBCTemplate.getConnection();
		
		int result2 = dao.deleteCommentNo(conn, commentNo, boardNo, memberNO);
		
		if(result2 > 0) JDBCTemplate.commit(conn);
		else  JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result2;
		
	}

	public int deleteComment(int commentNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.deleteComment(conn, commentNo);
		
		if(result > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
	}
}
