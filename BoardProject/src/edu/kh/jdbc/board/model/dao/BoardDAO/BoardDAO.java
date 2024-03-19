package edu.kh.jdbc.board.model.dao.BoardDAO;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.dto.Board.Board;
import edu.kh.jdbc.common.JDBCTemplate;

public class BoardDAO {
     private ResultSet rs = null;
     private PreparedStatement pstmt = null;
     private Statement stmt = null;
     
     
     
     //xml 에 작성된 SQL 에 을 읽어와 저장할 객체를 참조하는 변수
     
     private Properties prop = null;
     
     public BoardDAO() {
    	 
    	 try {
    		 prop = new Properties();
    		 
    		 prop.loadFromXML(new FileInputStream("board-sql.xml"));
    	 }catch(Exception e) {
    		 e.printStackTrace();
    	 }
    	 
    	 
     }

	public List<Board> selectAllBoard(Connection conn) throws Exception{
		
		List<Board> boardList = new ArrayList<Board>();		
		
		try {
			
			String sql = prop.getProperty("selectAllBoard");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int no = rs.getInt("BOARD_NO");
				String title = rs.getString("BOARD_TITLE");
				String name = rs.getString("MEMBER_NM");
				String createDt = rs.getString("CREATE_DT");
				int readCount = rs.getInt("READ_COUNT");
				int commentCount = rs.getInt("COMMENT_COUNT");
				
				Board board = new Board();
				
				board.setBoardNo(no);
				board.setBoardTitle(title);
				board.setMemberName(name);
				board.setCreateDate(createDt);
				board.setReadCount(readCount);
				board.setCommentCount(commentCount);
				
				boardList.add(board);
						
				
				
			}
			
		}finally {
			
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
			
		}
		
		return boardList;
	}

	/** 게시글 상세 조회 SQL 수행 DAO
	 * @param conn
	 * @param input
	 * @return
	 */
	public Board selectBoard(Connection conn, int input) throws Exception{
		Board board = null;
		
		
		
		try {
			
			String sql = prop.getProperty("selectBoard");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				int no = rs.getInt("BOARD_NO");
				String title = rs.getString("BOARD_TITLE");
				String content = rs.getString("BOARD_CONTENT");
				int memberNo = rs.getInt("MEMBER_NO");
				String memberName = rs.getString("MEMBER_NM");
				int readCount = rs.getInt("READ_COUNT");
				String createDt = rs.getString("CREATE_DT");
				
				board = new Board();
				
				board.setBoardNo(no);
				board.setBoardTitle(title);
				board.setBoardContent(content);
				board.setMemberNo(memberNo);
				board.setMemberName(memberName);
				board.setReadCount(readCount);
				board.setCreateDate(createDt);
				
			}
		}finally {
		   JDBCTemplate.close(rs);	
		   JDBCTemplate.close(pstmt);
		}
		
		return board;
	}

	/**조회수 증가하게 할 문
	 * @param conn
	 * @param input
	 * @return
	 */
	public int updateReadCount(Connection conn, int input) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateReadCount");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			
			result = pstmt.executeUpdate();
			
			
			
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
}
