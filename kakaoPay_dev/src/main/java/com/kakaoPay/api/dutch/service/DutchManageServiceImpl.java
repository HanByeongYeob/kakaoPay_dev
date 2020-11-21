package com.kakaoPay.api.dutch.service;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kakaoPay.api.dutch.dao.DutchManageDao;
import com.kakaoPay.api.receive.service.ReceiveManageService;
import com.kakaoPay.api.receive.service.ReceiveManageVO;

@Service("DutchManageService")
public class DutchManageServiceImpl implements DutchManageService{
	
	/** DutchManageDao */
	@Resource(name = "DutchManageDao")
	DutchManageDao dutchManageDao;
	
	/** ReceiveManageService */
	@Resource(name = "ReceiveManageService")
	ReceiveManageService receiveManageService;
	
	final static Logger logger = LoggerFactory.getLogger(DutchManageServiceImpl.class);
	/**
	 * ����� ���� ��ȸ
	 * @param xuserId
	 * @return DutchManageVO
	 */
	@Override
	public DutchManageVO selectUserInfo(int xUserId) {
		return dutchManageDao.selectUserInfo(xUserId);
	}
	
	/**
	 * �Ѹ��� �ݾ� �й��ϱ�
	 * @param DutchManageVO
	 */
	@Override
	public String devideUserDamount(DutchManageVO dutchManageVO) {
		int xUserBalance = dutchManageVO.getxUserBalance(); //�ܾ�
		int amount = dutchManageVO.getdAmount(); //�Ѹ��� �ݾ�
		int numPeople = dutchManageVO.getdNumPeople(); //�Ѹ��� �ο�
		String xRoomId = dutchManageVO.getxRoomId(); //����� ���̵�
		int rAmountSum = 0; //���� �ݾ� �հ�
		logger.info("---------����� ��� ��ȸ-----------");
		
		//����� ��� ��ȸ
		List<DutchManageVO> roomMemberList = dutchManageDao.selectRoomMemberList(dutchManageVO);
		
		//�Ѹ��� �ο� ��ȿ�� üũ
		if(numPeople>roomMemberList.size()) {
			return "check";
		}
		
		logger.info("---------��ū�� �߱� ����-----------");
		//Token �߱�
		String tokenValue = makeToken();
	    
	    //��ū�� 3�ڸ� Ȯ��
	    if(tokenValue.length() != 3) {
	    	return "token";
	    }
	    
	    DutchManageVO tempVO = new DutchManageVO();
	    tempVO.setxRoomId(xRoomId);
	    while(true) {
			tempVO.setdTokenValue(tokenValue);
			int cnt = dutchManageDao.selectOverlappingTokenValue(tempVO);
			
			if(cnt == 0) { //��ū �ߺ����� ������ �����ܰ� ����
				break;
			}else { //��ū �ߺ����� �ִٸ� �ٽ� ����
				tokenValue=makeToken();
				//��ū�� 3�ڸ� Ȯ��
			    if(tokenValue.length() != 3) {
			    	return "token";
			    }
			}
	    }
    	//�Ѹ��� ���� �Է�
    	dutchManageVO.setdTokenValue(tokenValue);
    	dutchManageDao.insertDutchInfo(dutchManageVO);

	    logger.info("---------��ū�� �߱� ��-----------");

	    Random random = new Random();
        //���� �� ���� �� �ޱ� ���� �Է�
		for(int i = 0; i <numPeople; i++) {
			logger.info("---------�ޱ� ���� ���� ���� "+(i+1)+"��°-----------");
			ReceiveManageVO receiveManageVO = new ReceiveManageVO();
			
			//���� �ݾ� ����
			int rAmount = 0;
			if(i == numPeople-1) { //������ ���
				rAmount = amount - rAmountSum;
			}else { //������ �ƴѻ��
				rAmount = (int) (Math.random()*amount);
				rAmountSum = rAmountSum + rAmount;
			}
			receiveManageVO.setrAmount(rAmount);
			
			//���� ��� ����
			int roomMemberCnt = roomMemberList.size();
			int r = random.nextInt(roomMemberCnt);
			receiveManageVO.setrUserId(roomMemberList.get(r).getxUserId());
			roomMemberList.remove(r);
			
			receiveManageVO.setdDutchId(dutchManageVO.getdDutchId());
			logger.info("---------�ޱ� ���� ���� ���� "+(i+1)+"��°-----------");
			logger.info("---------�ޱ� ���� �Է� "+(i+1)+"��°-----------");
			//�ޱ� ���� �Է�
			receiveManageService.insertReceiveInfo(receiveManageVO);
		}
		
		//�ܾ� - �Ѹ��� �ݾ� = ���ο� �ܾ�
		int newUserBalance= xUserBalance - amount;
		dutchManageVO.setxUserBalance(newUserBalance);
		
		logger.info("---------�Ѹ��� �ϰ� ���� �ܾ� ����-----------");
		//�ܾ� �����ϱ�
		dutchManageDao.updateUserBalance(dutchManageVO);
		
		return tokenValue;
	}
	
	/**
	 * �Ѹ��� ���� ��ȸ
	 * @param DutchManageVO
	 * @return DutchManageVO
	 */
	@Override
	public DutchInfoVO selectDutchInfo(DutchManageVO dutchManageVO) {
		return dutchManageDao.selectDutchInfo(dutchManageVO);
	}
	
	/**
	 * ����� �ܾ� �����ϱ�
	 * @param DutchManageVO
	 */
	@Override
	public void updateUserBalance(DutchManageVO dutchManageVO) {
		//����� �ܾ���ȸ
		DutchManageVO userInfo = dutchManageDao.selectUserInfo(dutchManageVO.getxUserId());
		//�ޱ� �ݾ� ��ȸ
		int rAmount = dutchManageVO.getdAmount();
		//���ο� �ܾ� = ���� �ܾ� + �ޱ� �ݾ�
		int total = rAmount + userInfo.getxUserBalance();
		dutchManageVO.setdAmount(total);
		dutchManageDao.updateUserBalance(dutchManageVO);
	}
	
	/**
	 * �Ѹ��� ���� ������Ʈ
	 * @param String
	 */
	@Override
	public void updateDutchInfo(int dDutchId) {
		dutchManageDao.updateDutchInfo(dDutchId);
	}
	
	/**
	 * ��ū�� �����(a~z + A~Z + 0~9)
	 * @return String
	 */
	public String makeToken() {
		String tokenValue = "";
		for (int i = 0; i < 3; i++) {
	    	int a = (int) ((Math.random() * 74) + 48);
	    	if((a>58&&a<64) || (a>91&&a<96)) {
	    		i--;
	    	}else {
	    		char ch = (char) a;
	    		tokenValue += Character.toString(ch);
	    	}
	    }
		return tokenValue;
	}
}
