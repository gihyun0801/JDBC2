package edu.kh.emp.model.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import edu.kh.emp.common.JDBCTemplate;
import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;
import oracle.jdbc.driver.T2CConnection;

public class EmployeeService {
	
	
  private EmployeeDAO dao = new EmployeeDAO();

public List<Employee> selectAll() {
	
	Connection conn = JDBCTemplate.getConnection();
	
	List<Employee> list = dao.selectAll(conn);
	
	
	
	
	JDBCTemplate.close(conn);
	
	
	
	return list;
}

public int insert(Employee emp) {
	
	Connection conn = JDBCTemplate.getConnection();
	
	int result = dao.insert(conn, emp);
	
	if(result > 0) {
		JDBCTemplate.commit(conn);
	}else {
		JDBCTemplate.rollback(conn);
	}
	
	JDBCTemplate.close(conn);
	
	
	return result;
}

public Employee alert(int num) {
	
	Connection conn = JDBCTemplate.getConnection();
	
	Employee em = dao.search(num, conn);
	
	
	return em;
}

public int update(Employee emp, int num) {
	
	Connection conn = JDBCTemplate.getConnection();
	
	int result = dao.update(conn, emp, num);
	
	if(result > 0) {
		JDBCTemplate.commit(conn);
	}else {
		JDBCTemplate.rollback(conn);
	}
	
	JDBCTemplate.commit(conn);
	
	return result;
}

public int delete(int num) {
	
	Connection conn = JDBCTemplate.getConnection();
	
	int result = dao.delete(conn,  num);
	
	if(result > 0) {
		JDBCTemplate.commit(conn);
	}else {
		JDBCTemplate.rollback(conn);
	}
	
	JDBCTemplate.close(conn);
	
	return result;
}





public List<Employee> selectDeptEmp(String dept) {
	
	Connection conn = JDBCTemplate.getConnection();
	
	List<Employee> list = dao.selectDeptEmp(conn, dept);
	
	return list;
	
	
}

public List<Employee> selectSalaryEmp(int salary) {
	
	Connection conn = JDBCTemplate.getConnection();
	
	List<Employee> list = dao.selectSalaryEmp(conn, salary);
	
	return list;
}
























public Map<String, Integer> selectDeptTotalSalary() {
	
	Connection conn = JDBCTemplate.getConnection();
	
	
	Map<String, Integer> list = dao.selectDeptTotalSalary(conn);
	
	
	return list;
}

public List<Employee> selectEmpNo(String no) {
	
	Connection conn = JDBCTemplate.getConnection();
	
	List<Employee> list = dao.selectEmpNo(conn, no);
	
	
	return list;
}

public Map<String, Integer> selectJobAvgSalary() {
	
Connection conn = JDBCTemplate.getConnection();
	
Map<String, Integer> list = dao.selectJobAvgSalary(conn);
	
	
	return list;
}
  
  
  
  
}
