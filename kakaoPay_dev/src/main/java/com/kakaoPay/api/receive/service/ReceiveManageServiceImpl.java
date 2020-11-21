package com.kakaoPay.api.receive.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kakaoPay.api.dutch.service.DutchManageService;
import com.kakaoPay.api.dutch.service.DutchManageVO;
import com.kakaoPay.api.receive.dao.ReceiveManageDao;

@Service("ReceiveManageService")
public class ReceiveManageServiceImpl implements ReceiveManageService{
	
	/** DutchManageDao */
	@Resource(name = "ReceiveManageDao")
	ReceiveManageDao receiveManageDao;
	
	/** DutchManageService */
	@Resource(name = "DutchManageService")
	DutchManageService dutchManageService;
	
	final static Logger logger = LoggerFactory.getLogger(ReceiveManageServiceImpl.class);
	
	/**
	 * �ޱ� ���� �Է�
	 * @param ReceiveManageVO
	 */
	@Override
	public void	insertReceiveInfo(ReceiveManageVO receiveManageVO){
		receiveManageDao.insertReceiveInfo(receiveManageVO);
	}
	
	/**
	 * �ޱ� ���� ��ȸ
	 * @param ReceiveManageVO
	 * @return List<ReceiveInfoVO>
	 */
	@Override
	public List<ReceiveInfoVO> selectReceiveInfo(ReceiveManageVO receiveManageVO) {
		return receiveManageDao.selectReceiveInfo(receiveManageVO);
	}
	
	/**
	 * �ݾ� �ޱ�
	 * @param DutchManageVO
	 * @return DutchManageVO
	 */
	@Override
	public int receiveRAmount(ReceiveInfoVO receiveInfoVO) {
		logger.info("-----------�ޱ� �Ϸ� ó�� ����-----------");
		//�ޱ� �Ϸ�ó��
		receiveManageDao.updateReceiveInfo(receiveInfoVO);
		logger.info("-----------�ޱ� �Ϸ� ó�� ��-----------");
		
		logger.info("-----------�ܾ� ���� ó�� ����-----------");
		int rUserId = receiveInfoVO.getrUserId(); //������� ���̵�
		int rAmount = receiveInfoVO.getrAmount(); //������� �ݾ�
		
		DutchManageVO dutchManageVO = new DutchManageVO();
		dutchManageVO.setxUserId(rUserId);
		dutchManageVO.setdAmount(rAmount);
		//���� ��� �ܾ� ����
		dutchManageService.updateUserBalance(dutchManageVO);
		
		logger.info("-----------�ܾ� ���� ó�� ��-----------");
		
		logger.info("-----------�Ѹ��� �Ϸ� Ȯ�� ����-----------");
		ReceiveManageVO receiveManageVO = new ReceiveManageVO();
		receiveManageVO.setdDutchId(receiveInfoVO.getdDutchId());
		//�ޱ� ���� ��ȸ
		List<ReceiveInfoVO> receivedUserList = receiveManageDao.selectReceiveInfo(receiveManageVO);
		
		//flag=1: �Ѹ��� �Ϸ� x / flag=0: �Ѹ��� �Ϸ�
		int flag=1;
		for(ReceiveInfoVO user : receivedUserList) {
			if("N".equals(user.getrCompletYn())){
				flag=0;
				break;
			}
		}
		
		if(flag == 1) {
			logger.info("-----------�Ѹ��� �Ϸ�-----------");
			//�Ѹ��� �Ϸ�ó��
			dutchManageService.updateDutchInfo(receiveInfoVO.getdDutchId());
		}
		
		logger.info("-----------�Ѹ��� �Ϸ� Ȯ�� ��-----------");
		return rAmount;
	}
}
