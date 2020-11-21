package com.kakaoPay.api.dutch.service;

public class DutchManageVO {

	//뿌리기 ID
	private int dDutchId;
	
	//사용자 아이디
	private int xUserId;
	
	//사용자 이름
	private String xUserName;
	
	//사용자 잔액
	private int xUserBalance;
	
	//대화방 아이디
	private String xRoomId;
	
	//대화방 이름
	private String xRoomName; 
	
	//뿌리기 금액
	private int dAmount;

	//뿌리기 인원
	private int dNumPeople;
	
	//토큰 값
	private String dTokenValue;
	
	public int getdDutchId() {
		return dDutchId;
	}

	public void setdDutchId(int dDutchId) {
		this.dDutchId = dDutchId;
	}
	
	public int getxUserId() {
		return xUserId;
	}

	public void setxUserId(int xUserId) {
		this.xUserId = xUserId;
	}

	public String getxUserName() {
		return xUserName;
	}

	public void setxUserName(String xUserName) {
		this.xUserName = xUserName;
	}

	public int getxUserBalance() {
		return xUserBalance;
	}

	public void setxUserBalance(int xUserBalance) {
		this.xUserBalance = xUserBalance;
	}

	public String getxRoomId() {
		return xRoomId;
	}

	public void setxRoomId(String xRoomId) {
		this.xRoomId = xRoomId;
	}

	public int getdAmount() {
		return dAmount;
	}

	public void setdAmount(int dAmount) {
		this.dAmount = dAmount;
	}

	public int getdNumPeople() {
		return dNumPeople;
	}

	public void setdNumPeople(int dNumPeople) {
		this.dNumPeople = dNumPeople;
	}

	public String getdTokenValue() {
		return dTokenValue;
	}

	public void setdTokenValue(String dTokenValue) {
		this.dTokenValue = dTokenValue;
	}

	public String getxRoomName() {
		return xRoomName;
	}

	public void setxRoomName(String xRoomName) {
		this.xRoomName = xRoomName;
	}
	
}
