package com.kakaoPay.api.dutch.service;

public class DutchManageVO {

	//�Ѹ��� ID
	private int dDutchId;
	
	//����� ���̵�
	private int xUserId;
	
	//����� �̸�
	private String xUserName;
	
	//����� �ܾ�
	private int xUserBalance;
	
	//��ȭ�� ���̵�
	private String xRoomId;
	
	//��ȭ�� �̸�
	private String xRoomName; 
	
	//�Ѹ��� �ݾ�
	private int dAmount;

	//�Ѹ��� �ο�
	private int dNumPeople;
	
	//��ū ��
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
