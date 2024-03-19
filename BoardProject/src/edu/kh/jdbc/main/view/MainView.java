package edu.kh.jdbc.main.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.jdbc.board.view.BoardView.BoardView;
import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.main.model.service.MainService;
import edu.kh.jdbc.member.model.dto.Member;
import edu.kh.jdbc.member.view.MemberView;

public class MainView {
 
	 Scanner sc = new Scanner(System.in);
	 
	 private MainService service = new MainService();
	 
	 private MemberView memberView = new MemberView();
	 private BoardView boardView = new BoardView();
      
	 
	 
	 /**
	 * 메인 메뉴 출력 View
	 */
	public void mainMenu() {
		 
		int input = 0; //메뉴 선택용 변수
		
		do {
			
			try {
				
				
				if(Session.loginMember == null) { //로그인 x
					
					System.out.println("\n===회원제 게시판 프로그램=====\n");
					System.out.println("1. 로그인");
					System.out.println("2. 회원가입");
					System.out.println("0. 프로그램 종료");
					
					
					System.out.print("\n메뉴 선택 : ");
					input = sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1 : login(); break;
					case 2 : signUp(); break;
					case 0 : System.out.println("프로그램 종료");
						default :System.out.println("메뉴에 선택된 번호만 입력해주세요"); 
							
					}
				}else {
					//로그인 o
					
					System.out.println("\n====로그인 메뉴===\n");
					System.out.println("1. 회원 기능");
					System.out.println("2. 게시판 기능");
					System.out.println("3. 로그아웃");
					System.out.println("0. 프로그램 종료");
					
					System.out.print("\n 메뉴 선택 : ");
					input = sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1:  memberView.memberMenu();;         break;
					case 2:  boardView.boardMenu(); break;
					case 3 : System.out.println("\n===로그아웃 되엇습니다 ===\n");
					      Session.loginMember = null;
					      //참고 하고 있던 로그인 회원 객체을 없앰
					}
				}
				
				
				
				
				
			}catch(InputMismatchException e) {
				sc.nextLine();
				input = -1;
				System.out.println("\n**** 입력 형식이 올바르지 않습니다***\n");
			}
			
			
			
		}while(input != 0);
		 
		 
		 
	 }
	 
	
	/**
	 * 회원가입
	 */
	private void signUp() {
		
		System.out.println("\n============[회원가입]==============\n");
		
		//아이디, 비밀번호, 비밀번호 확인, 이름, 성별을 저장할 변수 선언
	    String memberId = null;
	    String memberPw = null;
	    String pwConfirm = null; //비밀번호 확인용 변수
	    String memberName = null;
	    String memberGender = null;
	    
	    try {
	    	
	    	//아이디 입력
	    	//-DB 에 탈퇴하지 않은 회원 중
	    	// 입력한 아이디와 같은 아이다가 존재하면 중복으로 판정
	    	// -> 중복이 입력되지 않을 때 까지 무한 반복
	    	
	    	while(true) {
	    		System.out.print("아이디 입력 : ");
	    		memberId = sc.nextLine();
	    		
	    		//아이디 중복 확인, 서비스 호출
	    		// -> 중복인 경우 1, 아닌 경우 0 반환
	    		
	    		int result = service.idDuplicationCheck(memberId);
	    		//중복 검사 결과에 따라 반복 제어
	    		
	    		if(result == 0) {
	    			System.out.println("\n\n 사용가능한 아이디 입니다\n\n");
	    			break;
	    		}else {
	    			System.out.println("\n\n 사용 불가능한 아이디 입니다(중복됨)\n\n");
	    		}
	    	}
	    	
	    	//비밀번호, 비밀번호 확인 입력을 받아서 같을 때 까지 무한 반복
	    	while(true) {
	    		
	    		System.out.print("비밀번호 입력 : ");
	    		memberPw = sc.next();
	    		
	    		System.out.print("비밀번호 확인 : ");
	    		pwConfirm = sc.next();
	    		
	    		if(memberPw.equals(pwConfirm)) {
	    			System.out.println("\n==비밀번호 일치==\n");
	    			break;
	    		}else {
	    			System.out.println("\n==비밀번호 불일치==\n");
	    		}
	    	}
	    	
	    	//이름 입력
	    	System.out.print("이름 : ");
	    	memberName = sc.next();
	    	
	    	//성별 입력
	    	//M 또는 F가 입력될 때 까지 무한 반복
	    	
	    	
	    	while(true) {
	    		System.out.print("성별(M/F) : ");
	    		memberGender = sc.next().toUpperCase();
	    		
	    		
	    		if(memberGender.equals("M") || memberGender.equals("F")) {
	    			break;
	    		}else {
	    			System.out.println("\n*** M 또는 F만 입력해주세요 *** \n");
	    		}
	    	}
	    	
	    	
	    	//Member 객체를 생성하여 입력 받은 값 세팅해서 보냄
	    	
	    	Member member = new Member();
	    	
	    	member.setMemberId(memberId);
	    	member.setMemberPw(memberPw);
	    	member.setMemberName(memberName);
	    	member.setMemberGender(memberGender);
	    	
	    	int result = service.signUp(member);
	    	
	    	if(result > 0) {
	    		System.out.println("\n===회원 가입 성공===\n");
	    	}else {
	    		System.out.println("\n==회원 가입 실패==\n");
	    	}
	    	
	    }catch(Exception e) {
	    	System.out.println("\n 회원 가입 중 예외 발생\n");
	    	e.printStackTrace();
	    }
	    
	    
	    
		
		
		
	}


	/**
	 * 로그인
	 */
	public void login() {
		
		System.out.println("\n[로그인]\n");
		
		System.out.print("아이디 : ");
		String memberId = sc.next();
		
		System.out.print("비밀번호 : ");
		String memberPw = sc.next();
		
		try {
			
			//로그인 서비스 호출 후 결과 반환 받기
			//반환 받은 결과 =  Session.loginMember 에 저장
			Session.loginMember = service.login(memberId, memberPw);
			
			if(Session.loginMember == null) {
				System.out.println("\n*** 아이디/비밀번호가 일치하지 않습니다 ***\n");
			}else {
				System.out.printf("\n============%s 님 환영합니다============\n",Session.loginMember.getMemberName());
				
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
}
