package com.kakaoPay.api.receive.service;

import java.util.List;

public interface ReceiveManageService {

	//�ޱ� ���� �Է�
	public void	insertReceiveInfo(ReceiveManageVO receiveManageVO);
	
	//�ޱ� ���� ��ȸ
	public List<ReceiveInfoVO> selectReceiveInfo(ReceiveManageVO receiveManageVO);
	
	// �ݾ� �ޱ�
	public int receiveRAmount(ReceiveInfoVO receiveInfoVO);
}
