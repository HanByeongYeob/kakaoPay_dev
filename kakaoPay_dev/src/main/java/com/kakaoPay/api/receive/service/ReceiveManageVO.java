package com.kakaoPay.api.receive.service;

public class ReceiveManageVO {

	//�ޱ� ��� ID
	private String rReceiveId;
	
	//�Ѹ��� ID
	private int dDutchId;
	
	//�ޱ� ����� ID
	private int rUserId;
	
	//�ޱ� ��� �ð�
	private String rRegisterDt;
	
	//�ޱ� �ݾ�
	private int rAmount;
	
	//�ޱ� �Ϸ� ����
	private String rCompletYn;

	public String getrReceiveId() {
		return rReceiveId;
	}

	public void setrReceiveId(String rReceiveId) {
		this.rReceiveId = rReceiveId;
	}

	public int getdDutchId() {
		return dDutchId;
	}

	public void setdDutchId(int dDutchId) {
		this.dDutchId = dDutchId;
	}

	public int getrUserId() {
		return rUserId;
	}

	public void setrUserId(int rUserId) {
		this.rUserId = rUserId;
	}

	public String getrRegisterDt() {
		return rRegisterDt;
	}

	public void setrRegisterDt(String rRegisterDt) {
		this.rRegisterDt = rRegisterDt;
	}

	public int getrAmount() {
		return rAmount;
	}

	public void setrAmount(int rAmount) {
		this.rAmount = rAmount;
	}

	public String getrCompletYn() {
		return rCompletYn;
	}

	public void setrCompletYn(String rCompletYn) {
		this.rCompletYn = rCompletYn;
	}

}
