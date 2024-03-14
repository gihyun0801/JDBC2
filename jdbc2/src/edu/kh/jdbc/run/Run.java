package edu.kh.jdbc.run;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run {
   public static void main(String[] args) {
 	      TestService service = new TestService();
 	      
 	      // TB_TEST 테이블에 INSERT 수행
 	      // TB_TEST 테이블에 insert 를 수행하는 서비스 메서드를 호출 후
 	      // 결과를 반환받기
 	      
 	      TestVO vo1 = new TestVO(1, "제목1","내용1");
 	      
 	      try {
 	    	 int result = service.insert(vo1); // 1 / 0
 	    	 
 	    	 if(result > 0) {
 	    		 
 	    		 System.out.println("insert 성공");
 	    		 
 	    	 }else {
 	    		 System.out.println("insert 실패");
 	    	 }
 	    	 
 	      }catch(Exception e) {
 	    	  e.printStackTrace();
 	      }
 	      
 	      
 	      
 	      
 	      
 	      
 	      
 	      
 	      
 	      
   	}
}
