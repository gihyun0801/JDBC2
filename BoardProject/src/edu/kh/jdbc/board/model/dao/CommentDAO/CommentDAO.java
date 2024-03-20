package edu.kh.jdbc.board.model.dao.CommentDAO;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.dto.Comment.Comment;
import edu.kh.jdbc.common.JDBCTemplate;

public class CommentDAO {

	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;
	public CommentDAO() {
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("comment-sql.xml"));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/** 댓글 목록 조회 SQL 수행 DAO
	 * @return
	 */
	public List<Comment> selectCommentList(Connection conn, int input) {
		List<Comment> commentList = new ArrayList<Comment>();
		
		try {
			
			String sql = prop.getProperty("selectCommentList");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int commentNo = rs.getInt("COMMENT_NO");
				String commentContent = rs.getString("COMMENT_CONTENT");
				int memberNo = rs.getInt("MEMBER_NO");
				String name = rs.getString("MEMBER_NM");
				
				Comment comm = new Comment();
				
				comm.setCommentNo(commentNo);
				comm.setCommentContent(commentContent);
				comm.setMemberNo(memberNo);
				comm.setMemberName(name);
				
				commentList.add(comm);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		
		
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		
		return commentList;
	}

	public int insertComment(Connection conn, int boardNo, String commentContent, int memberNo) {
		int result = 0;
		try {
			
			String sql = prop.getProperty("insertComment");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, commentContent);
			pstmt.setInt(2, memberNo);
			pstmt.setInt(3, boardNo);
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		
		return result;
	}

	public int checkCommentNo(int commentNo, int boardNo, int memberNO, Connection conn) {
		int result = 0;
		try {
			
			String sql = prop.getProperty("checkCommentNo");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentNo);
			pstmt.setInt(2, memberNO);
			pstmt.setInt(3, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
		result = rs.getInt(1);
				
		
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
		}
		
		return result;
	}

	public int updateComment(Connection conn, int commentNo, String string) {
		int result2 = 0;
		try {
			
			String sql = prop.getProperty("updateComment");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, string);
			pstmt.setInt(2, commentNo);
			
			result2 = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			JDBCTemplate.close(conn);
			
		}
		
		return result2;
	}

	public int deleteCommentNo(Connection conn, int commentNo, int boardNo, int memberNo) {
		
		int result = 0;
		try {
			
			String sql = prop.getProperty("deleteCommentNo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, commentNo);
			pstmt.setInt(2, memberNo);
			pstmt.setInt(3, boardNo);
			
			
			
			result = pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int deleteComment(Connection conn, int commentNo) {
		int result = 0;
		try {
			String sql = prop.getProperty("deleteComment");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, commentNo);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

}
