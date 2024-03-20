package edu.kh.jdbc.board.view.BoardView;

import java.security.Provider.Service;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.dto.Board.Board;
import edu.kh.jdbc.board.model.dto.Comment.Comment;
import edu.kh.jdbc.board.model.service.BoardService.BoardService;
import edu.kh.jdbc.board.view.CommentView.CommentView;
import edu.kh.jdbc.common.Session;

public class BoardView {

	private Scanner sc = new Scanner(System.in);
	
	private BoardService boardService = new BoardService();
	private CommentView commentView = new CommentView();
     public void boardMenu() {
		
		int input = -1;
		
		do {
			try {
				System.out.println("\n===== 게시판 기능 =====\n");
				System.out.println("1. 게시글 목록 조회");
				System.out.println("2. 게시글 상세 조회(+ 댓글 기능)");
				System.out.println("3. 게시글 작성");
				// 제목, 내용(StringBuffer 이용) 입력
				// -> 게시글 삽입 서비스(제목, 내용, 로그인 회원 번호) 호출
				
				System.out.println("9. 메인 메뉴로 돌아가기");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("\n메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				System.out.println();
				
				switch(input) {
				case 1: selectAllBoard();  break; // 게시글 목록 조회
				case 2: selectBoard(); break; // 게시글 상세 조회 + 댓글 목록 조회
				case 3: insertBoard(); break; // 게시글 등록(삽입)
				
				case 9:
					System.out.println("\n===== 메인 메뉴로 돌아갑니다 =====\n");
					break;
				
				case 0:
					System.out.println("\n=== 프로그램 종료 ===\n");
					System.exit(0);
				default: System.out.println("\n*** 메뉴 번호만 입력 해주세요 ***\n"); 
				}
				
				System.out.println();
				
			}catch (InputMismatchException e) {
				System.out.println("\n*** 입력 형식이 올바르지 않습니다***\n");
				sc.nextLine(); // 입력버퍼에 잘못된 문자열 제거
				input = -1; // while문 종료 방지
			}
			
		}while(input != 9);
		
	}

	private void insertBoard() {
		
		System.out.println("\n===== 게시글 등록 ======\n");
		
		// 제목 입력
		System.out.print("제목 입력 : ");
		String boardTitle = sc.nextLine();
		
		StringBuffer sb = new StringBuffer();
		
		System.out.println("<!wq 입력 시 종료>");
		
		while(true) {
			String str = sc.nextLine();
			
			if(str.equals("!wq")) {
				break;
			}
			
			sb.append(str);
			sb.append("\n");
		}
		
		try {
			int result = boardService.insertBoard(boardTitle, sb, Session.loginMember.getMemberNO());
			
			if(result > 0) { //성공
				
				System.out.println("\n=========게시글 등록 완료============\n");
				
				//등록된 게시글 상세조회 서비스 호출
				// -> 게시글 번호, 로그인 회원 번호
				Board board = boardService.selectBoard(result, Session.loginMember.getMemberNO());
				
				System.out.println("--------------------------------------------------------");
				System.out.printf("글번호 : %d \n제목 : %s\n", board.getBoardNo(), board.getBoardTitle());
				System.out.printf("작성자 : %s | 작성일 : %s  \n조회수 : %d\n",
						board.getMemberName(), board.getCreateDate(), board.getReadCount());
				System.out.println("--------------------------------------------------------\n");
				System.out.println(board.getBoardContent());
				System.out.println("\n--------------------------------------------------------");
				
				
				
			}else {
				
				
				System.out.println("\n--------------------------------등록 실패---------------------------------\n");
				
				
				
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 게시글 상세 조회 + 댓글 목록 조회
	 */
	private void selectBoard() {
		
		System.out.println("\n=== 게시글 상세 조회 ===\n");
		
		// 게시글 번호 입력
		// 어떤 게시글을 상세 조회 할 것인지
		// 1) 번호가 일치하는 게시글이 DB 상에 있으면 조회
		//  -> 조회수 증가(단, 자신이 작성한 게시글일 경우 조회수 증가 X)
		//  -> 자신이 작성한 게시글일 경우
		//        수정/삭제 기능 노출
		// + 댓글 목록/ 댓글 기능 추가 예정
		
		// 2) 번호가 일치하는 게시글 없으면
		//  -> 해당 게시글이 존재하지 않습니다.
		
		System.out.print("게시글 번호 입력");
		int input = sc.nextInt();
		sc.nextLine();
		
		// 게시글 상세 조회 서비스 호출
		try {
			Board board = boardService.selectBoard(input,Session.loginMember.getMemberNO());
			                            //입력받은 게시글 번호, 로그인한 회원번호(조회수 증가 사용)
			if(board == null) {
				System.out.println("\n***게시글이 존재하지 않습니다***\n");
				return;
			}
			
			System.out.println("--------------------------------------------------------");
			System.out.printf("글번호 : %d \n제목 : %s\n", board.getBoardNo(), board.getBoardTitle());
			System.out.printf("작성자 : %s | 작성일 : %s  \n조회수 : %d\n",
					board.getMemberName(), board.getCreateDate(), board.getReadCount());
			System.out.println("--------------------------------------------------------\n");
			System.out.println(board.getBoardContent());
			System.out.println("\n--------------------------------------------------------");
			
			//******************************************************************************
			
			/*해당 게시글의 댓글 목록 조회*/
			if(!board.getCommentList().isEmpty()) {
				
				for(Comment c : board.getCommentList()) {
					System.out.printf("[댓글 번호 : %d 작성자 : %s   작성일   %s\n%s\n" ,      
							            c.getCommentNo(), c.getMemberName(), c.getCreateDate(), c.getCommentContent());
				}
				
				System.out.println("-----------------------------------------------------------------------------------");
				
			}
			
			/*댓글 메뉴 출력*/
			//1) 댓글 등록 - 누가 몇번 게시글에 작성하는가?
			//2) 댓글 수정 - 누가 몇번 게시글에있는 몇번 댓글을 수정할 것인가?
			//3) 댓글 삭제 - 누가 몇번 게시글에 있는 몇번 댓글을 삭제할 것인가?
			
			//게시글 번호
			commentView.commentMenu(input);
			
			//****************************************************************************
			
			//로그인한 회원이 작성한 게시글일 경우
			//게시글 수정/삭제 기능 노출
			if(Session.loginMember.getMemberNO() == board.getMemberNo()) {
				while(true) {
					System.out.println("(1) 수정");
					System.out.println("(2) 삭제");
					System.out.println("(0) 게시판 메뉴로 돌아가기");
					
					System.out.print("선택 >> ");
					input = sc.nextInt();
					sc.nextLine();
					
					//수정/삭제 기능 수행 후 게시판 메뉴로 돌아가기
					switch(input) {
					case 1: //updateBoard(board.getBoardNo()); return;   break;
						   //게시글 번호를 매개변수로 전달 - 해당 번호의 게시글을 수정
					case 2: //deleteBoard(board.getBoardNo()); return; break;
						 //게시글 번호를 매개변수로 전달 - 해당 번호의 게시글을 삭제
						
						
					case 0: return;
					default : System.out.println("\n*** 잘못 입력 하셨습니다 ***\n");
					
					}
				}
			}
			
		}catch(Exception e) {
			System.out.println("\n***게시글 상세 조회 중 예외 발생***\n");
			e.printStackTrace();
		}
		
		
		
		
	}

	private void deleteBoard(int boardNo) {
		
		
		
		
	}

	/** 게시글 수정
	 * @param boardNo
	 */
	private void updateBoard(int boardNo) {

		System.out.println("\n=== 게시글 수정===\n");
		
		System.out.print("수정할 제목 : ");
		String boardTitle = sc.nextLine();
		
		StringBuffer sb = new StringBuffer();
		
		System.out.println("<!wq 입력 시 종료>");
		
		while(true) {
			String str = sc.nextLine();
			
			if(str.equals("!wq")) {
				break;
			}
			
			sb.append(str);
			sb.append("\n");
		}
		
		try {
			// 게시글 수정 서비스 호출
			int result = BoardService.updateBoard(boardTitle,sb.toString(),boardNo);
			
			if(result > 0) {
				System.out.println("\n 게시글이 수정 되었음 \n");
			}else {
				System.out.println("\n 게시글 수정 실패 \n");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("\n 게시글 작성 중 예외 발생 \n");
		}
	}

	/**
	 * 게시글 목록 조회
	 */
	private void selectAllBoard() {
		
		System.out.println("\n=====게시글 목록 조회=======\n");
		
		// 3 | 샘플 제목[2] | 이름 | 작성일 | 조회수
		
		
		try {
			List<Board> boardList = boardService.selectAllBoard();
			
			if(boardList.size() == 0) {
				System.out.println("\n===게시글이 존재하지 않습니다===\n");
				return;
			}
			
			for(Board l : boardList) {
				System.out.printf("%d | %s[%d] | %s | %s | %d\n", l.getBoardNo(), l.getBoardTitle(),    
						                    l.getCommentCount(), l.getMemberName(), l.getCreateDate(), l.getReadCount());
			}
			
			
		}catch(Exception e) {
			System.out.println("***게시글 목록 조회 중 오류 발생***");
			e.printStackTrace();
		}
		
		
	}
	




}
