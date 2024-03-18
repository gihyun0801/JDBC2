package edu.kh.jdbc.member.model.dto;

public class Member {
	private int memberNO;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberGender;
	private String enrollDate; //가입일
    private String unregisterFlag; //탈퇴여부
    
    public Member() {
    	
    	
    	
    	
    }

	public int getMemberNO() {
		return memberNO;
	}

	public void setMemberNO(int memberNO) {
		this.memberNO = memberNO;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public String getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getUnregisterFlag() {
		return unregisterFlag;
	}

	public void setUnregisterFlag(String unregisterFlag) {
		this.unregisterFlag = unregisterFlag;
	}
    
  
}
