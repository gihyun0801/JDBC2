package edu.kh.gihyun.model.vo;

public class Employee {
   private String empName;
   private String date;
   private char gender;
public String getEmpName() {
	return empName;
}
public void setEmpName(String empName) {
	this.empName = empName;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public char getGender() {
	return gender;
}
public void setGender(char gender) {
	this.gender = gender;
}
public Employee(String empName, String date, char gender) {
	super();
	this.empName = empName;
	this.date = date;
	this.gender = gender;
}
@Override
public String toString() {
	return String.format("이름 : %s / 입사일 : %s / 성별 : %c", empName, date, gender);
}
   
   
}
