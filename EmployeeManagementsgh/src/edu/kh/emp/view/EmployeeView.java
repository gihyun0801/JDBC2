package edu.kh.emp.view;

import java.security.KeyStore.Entry;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import edu.kh.emp.common.JDBCTemplate;
import edu.kh.emp.model.service.EmployeeService;
import edu.kh.emp.model.vo.Employee;

public class EmployeeView {

	private Scanner sc = new Scanner(System.in);
	
	EmployeeService service = new EmployeeService();
	
	public EmployeeView() {}
	
	
	
	public void displaymenu() {
		
		
		int input = 0;
		
		do {
			
			try {
				
				System.out.println("\n============================================\n");
				System.out.println("----------사원 관리 프로그램----------");
				System.out.println("1. 전체 사원 정보 조회 ");
				System.out.println("2. 새로운 사원 추가");
				System.out.println("3. 사번이 일치 하는 사원 정보 조회");
				System.out.println("4. 사번이 일치하는 사원 정보 수정");
				System.out.println("5. 사번이 일치하는 사원 정보 삭제");
				System.out.println("6. 입력 받은 부서와 일치하는 모든 사원 정보 조회");
				System.out.println("7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회");
				System.out.println("8. 부서별 급여 합 전체 조회");
				System.out.println("9. 주민등록번호가 일치하는 사원 정보 조회");
				System.out.println("10. 직급별 급여 평균 조회");
				System.out.println("0. 프로그램종료");
				
				System.out.print("메뉴 선택 : ");
				int choice = sc.nextInt();
				
				sc.nextLine();
				
				switch(choice) {
				
				case 1 : selectAll(); break;
				case 2 : insertEmployee(); break;
				case 3 : selectEmpId(); break;
				case 4 : upadateEmployee(); break;
				case 5 : deleteEmployee(); break;
				case 6 : selectDeptEmp(); break;
				case 7 : selectSalaryEmp(); break;
				case 8 : selectDeptTotalSalary(); break;
				case 9 : selectEmpNo(); break;
				case 10 : selectJobAvgSalary(); break;
				case 0 : System.out.println("프로그램을 종료합니다");; break;
				
				default : System.out.println("잘못 입력"); break;
				
				
				}
				
				
				
				
			}catch(InputMismatchException e) {
				System.out.println("숫자만 입력하세요");
				input = -1;
				sc.nextLine(); //잘못 입력된 문자열 제거하기
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			
		}while(input != 0);
		
		
	}



	



	private void selectJobAvgSalary() {
		
System.out.println("\n============직급별 급여 평균 조회================\n");
		
		
		
Map<String, Double> list = service.selectJobAvgSalary();
		
		Set<Map.Entry<String, Double>> yaya = list.entrySet();
		
		for(Map.Entry<String, Double> DS : yaya) {
			
			System.out.printf("%s || %s\n", DS.getKey(), DS.getValue());
			
		}
		
		
	}



	private void selectEmpNo() {
		
System.out.println("\n============주민등록번호가 일치하는 사원 정보 조회================\n");
		
		System.out.print("주민등록번호 입력 (-포함) : ");
		String No = sc.nextLine();
		
		List<Employee> list = service.selectEmpNo(No);
		
		for(Employee li : list) {
			System.out.println(li);
		}
		
	}



	private void selectDeptTotalSalary() {
		
		System.out.println("\n====================부서 별 급여 합 전체 조회====================\n");
		
		Map<String, Integer> list = service.selectDeptTotalSalary();
		
		Set<Map.Entry<String, Integer>> ya = list.entrySet();
		
		
		for(Map.Entry<String, Integer> li : ya) {
			
			System.out.printf("부서 : %s || 급여합 : %d\n", li.getKey(), li.getValue());
			
		}
		
	}



	private void selectSalaryEmp() {
		
		
		System.out.println("\n============급여 이상 받는 사원 조회================\n");
		
		System.out.print("급여 입력 : ");
		int salary = sc.nextInt();
		
		List<Employee> list = service.selectSalaryEmp(salary);
		

		for(Employee li : list) {
			System.out.println(li);
		}
		
		
	}



	private void selectDeptEmp() {
		
		
		System.out.println("\n===========부서와 일치하는 모든 사원 정보 조회================\n");
		System.out.print("부서명 입력 : ");
		String dept = sc.nextLine();
		
		List<Employee> list = service.selectDeptEmp(dept);
		
		for(Employee li : list) {
			System.out.println(li);
		}
		
	}



	private void deleteEmployee() {
		
		int num = inputEmpId();
		
		
		int result = service.delete(num);
		
		if(result > 0) {
			System.out.println("삭제 성공");
		}else {
			System.out.println("삭제 실패");
		}
		
		
	}



	private void upadateEmployee() {
		
		System.out.println("\n===============================사원 정보 바꾸기=========================================\n");
		
		
	
		int num = inputEmpId();
		
		System.out.print("이름  : ");
		String name = sc.nextLine();
		
		System.out.print("주민등록번호 : ");
		String no = sc.nextLine();
		
		System.out.print("이메일 : ");
		String email = sc.nextLine();
		
		System.out.print("전화번호 : ");
		String phone = sc.nextLine();
		
		System.out.print("급여 : ");
		int salary = sc.nextInt();
		sc.nextLine();
		
		System.out.print("부서코드 : ");
		String dept = sc.nextLine();
		
		System.out.print("직급코드 : ");
		String job = sc.nextLine();
		
		System.out.print("셀 레벨 : ");
		String salLevel = sc.nextLine();
		
		System.out.print("보너스 : ");
		double bonus = sc.nextDouble();
		
		System.out.print("사수 번호 : ");
		int managerId = sc.nextInt();
		
		Employee emp = new Employee(num, name, no, email, phone,   
				salary, dept, job, salLevel, bonus, managerId);
		
		
		
		int result = service.update(emp, num);
		
		if(result > 0) {
			System.out.println("성공");
		}else {
			System.out.println("실패");
		}
		
	}



	private void insertEmployee() {
		
		
		System.out.println("\n====================================사원 추가====================================\n");
		
		
		int empId = inputEmpId();
		
		System.out.print("이름 입력 : ");
		String name = sc.nextLine();
		
		System.out.print("주민번호 입력 : ");
		String no = sc.nextLine();
		
		System.out.print("이메일 입력 : ");
		String email = sc.nextLine();
		
		System.out.print("폰번호 : ");
		String phone = sc.nextLine();
		
		System.out.print("부서코드(D1~D9) : ");
		String deptCode = sc.nextLine();
		
		System.out.print("직급코드(J1~J7) : ");
		String jobCode = sc.nextLine();
		
		System.out.print("급여등급(S1~S6): ");
		String salLevel = sc.nextLine();
		
		System.out.print("급여: ");
		int salary = sc.nextInt();
		
		System.out.print("보너스: ");
		double bonus = sc.nextDouble();
		
		System.out.print("사수번호: ");
		int managerId= sc.nextInt();
		
		
		Employee emp = new Employee(empId, name, no, email, phone,salary, deptCode, jobCode, salLevel,bonus,managerId);
		
		
		
		
		int result = service.insert(emp);
		
		if(result > 0) {
			System.out.println("추가 성공");
		}else {
			System.out.println("추가 실패");
		}
		
		
		
	}
	
	
	/**
	 * @사번을 입력받아 반환하는 메서드
	 */
	public int inputEmpId() {
		
		System.out.print("사번 입력 : ");
		int empId = sc.nextInt();
		sc.nextLine();
		
		
		return empId;
		
	}



	public void selectAll() {
		
		//전체 사원 정보 조회
		
		
		System.out.println("\n====================================전체 사원 정보 조회====================================\n");
		
		
		
		List<Employee> list = service.selectAll();
		
		
		
		
		for(Employee em : list) {
			
			printOne(em);
			
		}
	    
		
		
	}
   
	
	/** 사원 1명 정보 출력
	 * @param emp
	 */
	public void printOne(Employee emp) {
		if(emp == null) {
			System.out.println("조회된 사원 정보가 없습니다.");
			
		} else {
			System.out.println("사번 |   이름  | 주민 등록 번호 |        이메일        |   전화 번호   | 부서 | 직책 | 급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			
			System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n",
					emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(),
					emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
		}
	}

	public void selectEmpId() {
		
		System.out.println("\n====================사번 일치하는 회원 조회====================\n");
		
		int num = inputEmpId();
		
		
		Employee re = service.alert(num);
		
		printOne(re);
		
		
	}
	
	
	
}
