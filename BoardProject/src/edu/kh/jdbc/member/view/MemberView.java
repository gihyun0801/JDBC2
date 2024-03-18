package edu.kh.jdbc.member.view;

import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.member.model.dto.Member;
import edu.kh.jdbc.member.model.service.MemberService;

/**
 * 회원 전용 화면 
 */
public class MemberView {
   
	
	private Scanner sc = new Scanner(System.in);
	
	private MemberService service = new MemberService();
	
	
	public void memberMenu() {
		
		int input = 0;
		
		do {
		
			try {
				
				
				System.out.println("\n=== 회원 기능===\n");
				System.out.println("1. 내 정보 조회");
				System.out.println("2. 회원 목록 조회(아이디, 이름, 성별)");
				System.out.println("3. 내 정보 수정(이름, 성별, (현재 로그인한 회원번호))");
				System.out.println("4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)");
				System.out.println("5. 회원 탈퇴(보안코드, 비밀번호, UPDATE)");
				
				System.out.println("9. 메인 메뉴로 돌아가기");
				System.out.println("0. 프로그램 종료");
				
				
				System.out.print("\n메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: selectMyInfo();break;
				case 2: selectMemberList(); break;
				case 3: updateMember(); break; //현재 로그인한 회원번호를 넘겨줌
				case 4: //updatePassword(); break;
				case 5: // if( unRegisterMenu() ) return; break;
				case 9: System.out.println("\n========메인 메뉴로 돌아갑니다=======\n"); break;
				case 0: System.out.println("\n=============프로그램 종료=============\n"); 
				        // JVM 강제 종료 구문
				// 매개변수는 기본 0, 다른 숫자는 오류를 의미
				System.exit(0);
				
				break;
				
				default : System.out.println("\n***메뉴 번호만 입력하세요***\n");
				}
				
			}catch(Exception e) {
				System.out.println("\n*** 입력 형식이 올바르지 않습니다***\n");
				input = -1;
				sc.nextLine();
				
				e.printStackTrace();
			}
			
			
		}while(input != 9);
		
	}


	private void updateMember() throws Exception{
		
		System.out.println("\n===회원 정보 수정===\n");
		
		System.out.println("수정할 이름 : ");
		String memberName = sc.next();
		
		String memberGender = null;
		
		while(true) {
			System.out.println("수정할 성별 : ");
			memberGender = sc.next().toUpperCase();
			
			if(memberGender.equals("M") || memberGender.equals("F")) {
				break;
			}
			
			System.out.println("** M 또는 F를 입력해주세요 **");
		}
		
		
		try {
			// 회원 정보 수정 서비스 호출 및 결과 반환받기
			
			int result = service.updateMember(memberName, memberGender, Session.loginMember.getMemberNO());
			
			if(result > 0) {
				System.out.println("\n수정 되었습니다\n");
				
				// service 를 호출해서 db만 수정햇다 자바프로그램에선 수정이 안됀상태다 
				// 로그인한 얘는 지금 수정이 안됐따 자바프로그램 내에 선....
				
				Session.loginMember.setMemberName(memberName);
				Session.loginMember.setMemberGender(memberGender);
			}else {
				System.out.println("\n수정 실패\n");
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * 회원 목록 조회
	 */
	private void selectMemberList() throws Exception{
		
		System.out.println("\n========회원 목록 조회(아이디,이름,성별)========\n");
		
		
		
		List<Member> list = service.selectMemberList();
		
		if(list.size() == 0) {
			System.out.println("조회된 결과 없음");
		}
		
		for(Member li : list) {
			System.out.printf("회원 아이디 : %s || 회원 이름 : %s || 회원 성별 : %s\n", li.getMemberId(), li.getMemberName(), li.getMemberGender());
		}
      	
		
	}


	/**
	 * 내 정보 조회
	 */
	private void selectMyInfo() {
		
		System.out.println("\n====내 정보 조회=====\n");
		
		//회원 번호, 아이디, 이름, 성별(남/여), 가입일
		//Session.loginMember(이용)
		
		System.out.println("회원번호 : " + Session.loginMember.getMemberNO());
		System.out.println("아이디 : " + Session.loginMember.getMemberId());
		System.out.println("이름 : " + Session.loginMember.getMemberName());
		
		if(Session.loginMember.getMemberGender().equals("M")) {
			System.out.println("성별 : 남");
		}else {
			System.out.println("성별 : 여");
		}
		
		System.out.println("가입일 : " + Session.loginMember.getEnrollDate());
		
		
	}
	
}
