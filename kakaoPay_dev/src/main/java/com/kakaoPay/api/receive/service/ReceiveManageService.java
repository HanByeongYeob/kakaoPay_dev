package com.kakaoPay.api.receive.service;

import java.util.List;

public interface ReceiveManageService {

	//받기 정보 입력
	public void	insertReceiveInfo(ReceiveManageVO receiveManageVO);
	
	//받기 정보 조회
	public List<ReceiveInfoVO> selectReceiveInfo(ReceiveManageVO receiveManageVO);
	
	// 금액 받기
	public int receiveRAmount(ReceiveInfoVO receiveInfoVO);
}
