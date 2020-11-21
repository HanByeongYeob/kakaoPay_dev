package com.kakaoPay.api.dutch.service;

public class DutchInfoVO {

	private int dDutchId;
	//뿌리기 토큰 값
	private String dTokenValue;
	
	//뿌리기 단톡방 아이디
	private String dRoomId;
	
	//뿌리기 등록자 아이디
	private int dUserId;
	
	//뿌리기 등록 일시
	private String dRegisterDt;
	
	// 뿌리기 금액
	private int dAount;
	
	//뿌리기 인원
	private int dNumPeople;
	
	//뿌리기 유효기간
	private String dExpiryDt;
	
	//뿌리기 유효기간
	private String rExpiryDt;
	
	//뿌리기 완료 여부
	private String dCompletYn;
	
	public int getdDutchId() {
		return dDutchId;
	}

	public void setdDutchId(int dDutchId) {
		this.dDutchId = dDutchId;
	}
	
	public String getdTokenValue() {
		return dTokenValue;
	}

	public void setdTokenValue(String dTokenValue) {
		this.dTokenValue = dTokenValue;
	}

	public String getdRoomId() {
		return dRoomId;
	}

	public void setdRoomId(String dRoomId) {
		this.dRoomId = dRoomId;
	}

	public int getdUserId() {
		return dUserId;
	}

	public void setdUserId(int dUserId) {
		this.dUserId = dUserId;
	}

	public String getdRegisterDt() {
		return dRegisterDt;
	}

	public void setdRegisterDt(String dRegisterDt) {
		this.dRegisterDt = dRegisterDt;
	}

	public int getdAount() {
		return dAount;
	}

	public void setdAount(int dAount) {
		this.dAount = dAount;
	}

	public int getdNumPeople() {
		return dNumPeople;
	}

	public void setdNumPeople(int dNumPeople) {
		this.dNumPeople = dNumPeople;
	}

	public String getdExpiryDt() {
		return dExpiryDt;
	}

	public void setdExpiryDt(String dExpiryDt) {
		this.dExpiryDt = dExpiryDt;
	}

	public String getdCompletYn() {
		return dCompletYn;
	}

	public void setdCompletYn(String dCompletYn) {
		this.dCompletYn = dCompletYn;
	}

	public String getrExpiryDt() {
		return rExpiryDt;
	}

	public void setrExpiryDt(String rExpiryDt) {
		this.rExpiryDt = rExpiryDt;
	}

}
