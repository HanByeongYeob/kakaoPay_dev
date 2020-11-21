package com.kakaoPay.api.dutch.service;

public interface DutchManageService {
	
	//��������� ��ȸ
	public DutchManageVO selectUserInfo(int xUserId);
	
	//�Ѹ��� �ݾ� �й��ϱ�
	public String devideUserDamount(DutchManageVO dutchManageVO);
	
	//�Ѹ��� ���� ��ȸ
	public DutchInfoVO selectDutchInfo(DutchManageVO dutchManageVO);
	
	//����� �ܾ� �����ϱ�
	public void updateUserBalance(DutchManageVO dutchManageVO);
	
	//�Ѹ��� ���� ������Ʈ
	public void updateDutchInfo(int dutchId);
}
