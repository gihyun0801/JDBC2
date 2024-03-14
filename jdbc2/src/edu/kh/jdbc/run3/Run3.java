package edu.kh.jdbc.run3;

import java.util.Scanner;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run3 {
   // 번호 , 제목, 내용을 입력받아
	// 번호가 일치하는 행의 제목, 내용 수정
	
	public static void main(String[] args) {
		
	
		
		
		try {
			
			
			Scanner sc = new Scanner(System.in);
			
			TestService service = new TestService();
			
			System.out.print("번호 입력 : ");
			int testNo = sc.nextInt();
			
			sc.nextLine();
			
			System.out.print("제목 입력 : ");
			String testTitle = sc.nextLine();
			
			System.out.print("내용 입력 : ");
			String testContent = sc.nextLine();
	  
			TestVO vo = new TestVO(testNo, testTitle, testContent);
			
			int result = service.update(vo);
			
			if(result > 0) {
				System.out.println("update 성공");
			}else {
				System.out.println("update 실패");
				
			}
			
			
		}catch(Exception e) {
			System.out.println("수정 중 예외가 발생했습니다");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
}
