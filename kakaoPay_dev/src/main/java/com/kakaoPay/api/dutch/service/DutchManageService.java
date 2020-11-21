package com.kakaoPay.api.dutch.service;

public interface DutchManageService {
	
	//사용자정보 조회
	public DutchManageVO selectUserInfo(int xUserId);
	
	//뿌리기 금액 분배하기
	public String devideUserDamount(DutchManageVO dutchManageVO);
	
	//뿌리기 정보 조회
	public DutchInfoVO selectDutchInfo(DutchManageVO dutchManageVO);
	
	//사용자 잔액 변경하기
	public void updateUserBalance(DutchManageVO dutchManageVO);
	
	//뿌리기 정보 업데이트
	public void updateDutchInfo(int dutchId);
}
