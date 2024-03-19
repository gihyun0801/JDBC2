package edu.kh.jdbc.board.model.service.BoardService;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO.BoardDAO;
import edu.kh.jdbc.board.model.dto.Board.Board;
import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.common.Session;

public class BoardService {
    private BoardDAO dao = new BoardDAO();

	public List<Board> selectAllBoard() throws Exception{
		
		
		Connection conn = JDBCTemplate.getConnection();
		
		// DAO 메서드 호출
		
		List<Board> boardList = dao.selectAllBoard(conn);
		
		JDBCTemplate.close(conn);
		
		return boardList;
	}

	/** 게시글 상세 조회 서비스
	 * @param input
	 * @param memberNO
	 * @return
	 */
	public Board selectBoard(int input, int memberNO) throws Exception{
		
		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2.게시글 상세 조회 DAO 메서드 호출
		Board board = dao.selectBoard(conn, input);
		
		//3. 게시글이 존재되서 조회된 경우
		
		if(board != null) {
			//4. 조회수 증가
		    //단, 게시글 작성자와 로그인한 회원이 같으면 조회수 증가 안해줄거다 == 다를경우에만 증가
			if(board.getMemberNo() != memberNO) {
				//조회한 게시글 작성한 회원번호 != 로그인한 회원번호
				
				//5. 조회 수 증가 DAO 메서드 호출 (UPDATE)
				
				int result = dao.updateReadCount(conn, input);
				                       // input 을 작성하는 이유는 어떤 게시물의 조회수를 증가시키려고 
				                     // 그걸 구분하려고 input값을 전달함
				
				//6. 트랜잭션 제어 처리 + 데이터 동기화 처리
				if(result > 0) {
					JDBCTemplate.commit(conn);
					// 현재 조회된 board에 조회수 0
					// DB에 있는 조회수는 1이 된 상태 근데 자바는 현재 0이므로 수동적으로 올려줘야된다
					board.setReadCount(board.getReadCount() + 1);
				}else {
					JDBCTemplate.rollback(conn);
				}
				
			}
		
		}
		
		
		// 7. 커넥션 반환
		JDBCTemplate.close(conn);
		
		// 8. 결과 반환
		return board;
	}
    
    
}
