package edu.kh.emp.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.kh.emp.common.JDBCTemplate;
import edu.kh.emp.model.vo.Employee;

public class EmployeeDAO {
  
	 
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private Properties prop = null;
	
	public EmployeeDAO() {
		
		try {
			
			prop = new Properties();
			
			prop.loadFromXML(new FileInputStream("query.xml"));
			
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public List<Employee> selectAll(Connection conn) {
		
		List<Employee> list = new ArrayList<Employee>();
	
		try {
			
	        stmt = conn.createStatement();
			
			String sql = prop.getProperty("all");
			
			rs = stmt.executeQuery(sql);
			
			
			
			
			while(rs.next()) {
				
				
				
				int empId = rs.getInt("EMP_ID");
				// EMP_ID 컬럼은 문자열 컬럼이지만
				// 저장된 값들은 모두 숫자형태
				// -> DB 에서 자동 형변환해서 보내줌
				String empName = rs.getString("EMP_NAME"); 
				String empNo = rs.getString("EMP_NO"); 
				String empEmail = rs.getString("EMAIL"); 
				String empPhone = rs.getString("PHONE"); 
				String empDeptTitle = rs.getString("DEPT_TITLE"); 
				String empJobName = rs.getString("JOB_NAME"); 
				int empSalary = rs.getInt("SALARY"); 
				
			
				
				Employee emp = new Employee(empId, empName, empNo, empEmail, empPhone, empDeptTitle, empJobName,   
						empSalary);
				
				list.add(emp);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		JDBCTemplate.close(stmt);
		
		return list;
	}

	public int insert(Connection conn, Employee emp) {
		
		int result = 0 ;
		try {
			
            String sql = prop.getProperty("insert");  
			
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setInt(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getEmail());
			pstmt.setString(5, emp.getPhone());
			pstmt.setString(6, emp.getDeptCode());
			pstmt.setString(7, emp.getJobCode());
			pstmt.setString(8, emp.getSalLevel());
			pstmt.setInt(9, emp.getSalary());
			pstmt.setDouble(10, emp.getBonus());
			pstmt.setInt(11, emp.getManagerId());
			
			
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		JDBCTemplate.close(pstmt);
		
		return result;
	}

	public Employee search(int num, Connection conn) {
		Employee emp = null;
		try {
			
			String sql = prop.getProperty("view");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) { // ResultSet에 결과가 있다면
	            // ResultSet에서 각 열의 데이터를 가져와서 Employee 객체 생성
	            emp = new Employee(
	                rs.getInt("EMP_ID"),
	                rs.getString("EMP_NAME"),
	                rs.getString("EMP_NO"),
	                rs.getString("EMAIL"),
	                rs.getString("PHONE"),
	                rs.getString("DEPT_TITLE"),
	                rs.getString("JOB_NAME"),
	                rs.getInt("SALARY")
	                
	               
	              
	               
	            );
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return emp;
	}

	public int update(Connection conn, Employee emp, int num) {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("update");
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, emp.getEmpName());
			pstmt.setString(2, emp.getEmpNo());
			pstmt.setString(3, emp.getEmail());
			pstmt.setString(4, emp.getPhone());
			pstmt.setString(5, emp.getDeptCode());
			pstmt.setString(6, emp.getJobCode());
			pstmt.setString(7, emp.getSalLevel());
			pstmt.setInt(8, emp.getSalary());
			pstmt.setDouble(9, emp.getBonus());
			pstmt.setInt(10, emp.getManagerId());
			pstmt.setInt(11, num);
			
			result = pstmt.executeUpdate();
			
			JDBCTemplate.close(pstmt);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}

	public int delete(Connection conn, int num) {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("delete");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
			
			JDBCTemplate.close(pstmt);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}

	public List<Employee> selectDeptEmp(Connection conn, String dept) {
		List<Employee> list = new ArrayList<Employee>();
		try {
			
			String sql = prop.getProperty("selectDeptEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dept);
			
			rs = pstmt.executeQuery();
			
			
			
			
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String deptCode = rs.getString("DEPT_CODE");
				String jobCode = rs.getString("JOB_CODE");
				String salLevel = rs.getString("SAL_LEVEL");
				int salary = rs.getInt("SALARY");
				double bonus = rs.getDouble("BONUS");
				int managerId = rs.getInt("MANAGER_ID");
				
				
				Employee emp = new Employee(empId, empName, empNo, email, phone, salary, 
						    deptCode,jobCode, salLevel, bonus, managerId);
				
				list.add(emp);
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public List<Employee> selectSalaryEmp(Connection conn, int salary) {
		
		List<Employee> list = new ArrayList<Employee>();
		try {
			
			String sql = prop.getProperty("selectSalaryEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, salary);
			
			rs = pstmt.executeQuery();
			
			
	while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String deptCode = rs.getString("DEPT_CODE");
				String jobCode = rs.getString("JOB_CODE");
				String salLevel = rs.getString("SAL_LEVEL");
				int salary2 = rs.getInt("SALARY");
				double bonus = rs.getDouble("BONUS");
				int managerId = rs.getInt("MANAGER_ID");
				
				
				Employee emp = new Employee(empId, empName, empNo, email, phone, salary2, 
						    deptCode,jobCode, salLevel, bonus, managerId);
				
				list.add(emp);
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public Map<String, Integer> selectDeptTotalSalary(Connection conn) {
		
		Map<String, Integer> list = new HashMap<String, Integer>();
		
		try {
			
			String sql = prop.getProperty("selectDeptTotalSalary");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String str1 = rs.getString("DEPT_CODE");
				int num1 = rs.getInt("급여합");
				
				list.put(str1, num1);
				
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		return list;
	}

	public List<Employee> selectEmpNo(Connection conn, String no) {
		
		List<Employee> list = new ArrayList<Employee>();
		
		try {
			
			
			String sql = prop.getProperty("selectDeptTotalSalary");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String deptCode = rs.getString("DEPT_CODE");
				String jobCode = rs.getString("JOB_CODE");
				String salLevel = rs.getString("SAL_LEVEL");
				int salary2 = rs.getInt("SALARY");
				double bonus = rs.getDouble("BONUS");
				int managerId = rs.getInt("MANAGER_ID");
				
				
				Employee emp = new Employee(empId, empName, empNo, email, phone, salary2, 
						    deptCode,jobCode, salLevel, bonus, managerId);
				
				list.add(emp);
				
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		return list;
	}

	public Map<String, Integer> selectJobAvgSalary(Connection conn) {
		
		Map<String, Integer> list = new HashMap<String, Integer>();
		
		try {
			String sql = prop.getProperty("selectJobAvgSalary");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String str1 = rs.getString("JOB_NAME");
				int num1 = rs.getInt("급여평균");
				
				list.put(str1, num1);
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		return list;
	}
	 
	
}
