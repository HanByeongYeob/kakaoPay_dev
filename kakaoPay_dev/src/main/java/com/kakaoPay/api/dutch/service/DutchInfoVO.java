package com.kakaoPay.api.dutch.service;

public class DutchInfoVO {

	private int dDutchId;
	//�Ѹ��� ��ū ��
	private String dTokenValue;
	
	//�Ѹ��� ����� ���̵�
	private String dRoomId;
	
	//�Ѹ��� ����� ���̵�
	private int dUserId;
	
	//�Ѹ��� ��� �Ͻ�
	private String dRegisterDt;
	
	// �Ѹ��� �ݾ�
	private int dAount;
	
	//�Ѹ��� �ο�
	private int dNumPeople;
	
	//�Ѹ��� ��ȿ�Ⱓ
	private String dExpiryDt;
	
	//�Ѹ��� ��ȿ�Ⱓ
	private String rExpiryDt;
	
	//�Ѹ��� �Ϸ� ����
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
