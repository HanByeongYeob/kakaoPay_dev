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
	 * 받기 정보 입력
	 * @param ReceiveManageVO
	 */
	@Override
	public void	insertReceiveInfo(ReceiveManageVO receiveManageVO){
		receiveManageDao.insertReceiveInfo(receiveManageVO);
	}
	
	/**
	 * 받기 정보 조회
	 * @param ReceiveManageVO
	 * @return List<ReceiveInfoVO>
	 */
	@Override
	public List<ReceiveInfoVO> selectReceiveInfo(ReceiveManageVO receiveManageVO) {
		return receiveManageDao.selectReceiveInfo(receiveManageVO);
	}
	
	/**
	 * 금액 받기
	 * @param DutchManageVO
	 * @return DutchManageVO
	 */
	@Override
	public int receiveRAmount(ReceiveInfoVO receiveInfoVO) {
		logger.info("-----------받기 완료 처리 시작-----------");
		//받기 완료처리
		receiveManageDao.updateReceiveInfo(receiveInfoVO);
		logger.info("-----------받기 완료 처리 끝-----------");
		
		logger.info("-----------잔액 변경 처리 시작-----------");
		int rUserId = receiveInfoVO.getrUserId(); //받을사람 아이디
		int rAmount = receiveInfoVO.getrAmount(); //받을사람 금액
		
		DutchManageVO dutchManageVO = new DutchManageVO();
		dutchManageVO.setxUserId(rUserId);
		dutchManageVO.setdAmount(rAmount);
		//받을 사람 잔액 변경
		dutchManageService.updateUserBalance(dutchManageVO);
		
		logger.info("-----------잔액 변경 처리 끝-----------");
		
		logger.info("-----------뿌리기 완료 확인 시작-----------");
		ReceiveManageVO receiveManageVO = new ReceiveManageVO();
		receiveManageVO.setdDutchId(receiveInfoVO.getdDutchId());
		//받기 정보 조회
		List<ReceiveInfoVO> receivedUserList = receiveManageDao.selectReceiveInfo(receiveManageVO);
		
		//flag=1: 뿌리기 완료 x / flag=0: 뿌리기 완료
		int flag=1;
		for(ReceiveInfoVO user : receivedUserList) {
			if("N".equals(user.getrCompletYn())){
				flag=0;
				break;
			}
		}
		
		if(flag == 1) {
			logger.info("-----------뿌리기 완료-----------");
			//뿌리기 완료처리
			dutchManageService.updateDutchInfo(receiveInfoVO.getdDutchId());
		}
		
		logger.info("-----------뿌리기 완료 확인 끝-----------");
		return rAmount;
	}
}
