package com.kakaoPay.api.receive.service;

public class ReceiveManageVO {

	//받기 등록 ID
	private String rReceiveId;
	
	//뿌리기 ID
	private int dDutchId;
	
	//받기 등록자 ID
	private int rUserId;
	
	//받기 등록 시간
	private String rRegisterDt;
	
	//받기 금액
	private int rAmount;
	
	//받기 완료 여부
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
