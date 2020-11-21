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
	 * 사용자 정보 조회
	 * @param xuserId
	 * @return DutchManageVO
	 */
	@Override
	public DutchManageVO selectUserInfo(int xUserId) {
		return dutchManageDao.selectUserInfo(xUserId);
	}
	
	/**
	 * 뿌리기 금액 분배하기
	 * @param DutchManageVO
	 */
	@Override
	public String devideUserDamount(DutchManageVO dutchManageVO) {
		int xUserBalance = dutchManageVO.getxUserBalance(); //잔액
		int amount = dutchManageVO.getdAmount(); //뿌리기 금액
		int numPeople = dutchManageVO.getdNumPeople(); //뿌리기 인원
		String xRoomId = dutchManageVO.getxRoomId(); //단톡방 아이디
		int rAmountSum = 0; //받은 금액 합계
		logger.info("---------단톡방 멤버 조회-----------");
		
		//단톡방 멤버 조회
		List<DutchManageVO> roomMemberList = dutchManageDao.selectRoomMemberList(dutchManageVO);
		
		//뿌리기 인원 유효성 체크
		if(numPeople>roomMemberList.size()) {
			return "check";
		}
		
		logger.info("---------토큰값 발급 시작-----------");
		//Token 발급
		String tokenValue = makeToken();
	    
	    //토큰값 3자리 확인
	    if(tokenValue.length() != 3) {
	    	return "token";
	    }
	    
	    DutchManageVO tempVO = new DutchManageVO();
	    tempVO.setxRoomId(xRoomId);
	    while(true) {
			tempVO.setdTokenValue(tokenValue);
			int cnt = dutchManageDao.selectOverlappingTokenValue(tempVO);
			
			if(cnt == 0) { //토큰 중복값이 없으면 다음단계 진행
				break;
			}else { //토큰 중복값이 있다면 다시 생성
				tokenValue=makeToken();
				//토큰값 3자리 확인
			    if(tokenValue.length() != 3) {
			    	return "token";
			    }
			}
	    }
    	//뿌리기 정보 입력
    	dutchManageVO.setdTokenValue(tokenValue);
    	dutchManageDao.insertDutchInfo(dutchManageVO);

	    logger.info("---------토큰값 발급 끝-----------");

	    Random random = new Random();
        //받을 돈 새팅 및 받기 정보 입력
		for(int i = 0; i <numPeople; i++) {
			logger.info("---------받기 정보 세팅 시작 "+(i+1)+"번째-----------");
			ReceiveManageVO receiveManageVO = new ReceiveManageVO();
			
			//받을 금액 새팅
			int rAmount = 0;
			if(i == numPeople-1) { //마직막 사람
				rAmount = amount - rAmountSum;
			}else { //마지막 아닌사람
				rAmount = (int) (Math.random()*amount);
				rAmountSum = rAmountSum + rAmount;
			}
			receiveManageVO.setrAmount(rAmount);
			
			//받을 사람 세팅
			int roomMemberCnt = roomMemberList.size();
			int r = random.nextInt(roomMemberCnt);
			receiveManageVO.setrUserId(roomMemberList.get(r).getxUserId());
			roomMemberList.remove(r);
			
			receiveManageVO.setdDutchId(dutchManageVO.getdDutchId());
			logger.info("---------받기 정보 세팅 시작 "+(i+1)+"번째-----------");
			logger.info("---------받기 정보 입력 "+(i+1)+"번째-----------");
			//받기 정보 입력
			receiveManageService.insertReceiveInfo(receiveManageVO);
		}
		
		//잔액 - 뿌리기 금액 = 새로운 잔액
		int newUserBalance= xUserBalance - amount;
		dutchManageVO.setxUserBalance(newUserBalance);
		
		logger.info("---------뿌리기 하고 남은 잔액 변경-----------");
		//잔액 변경하기
		dutchManageDao.updateUserBalance(dutchManageVO);
		
		return tokenValue;
	}
	
	/**
	 * 뿌리기 정보 조회
	 * @param DutchManageVO
	 * @return DutchManageVO
	 */
	@Override
	public DutchInfoVO selectDutchInfo(DutchManageVO dutchManageVO) {
		return dutchManageDao.selectDutchInfo(dutchManageVO);
	}
	
	/**
	 * 사용자 잔액 변경하기
	 * @param DutchManageVO
	 */
	@Override
	public void updateUserBalance(DutchManageVO dutchManageVO) {
		//사용자 잔액조회
		DutchManageVO userInfo = dutchManageDao.selectUserInfo(dutchManageVO.getxUserId());
		//받기 금액 조회
		int rAmount = dutchManageVO.getdAmount();
		//새로운 잔액 = 기존 잔액 + 받기 금액
		int total = rAmount + userInfo.getxUserBalance();
		dutchManageVO.setdAmount(total);
		dutchManageDao.updateUserBalance(dutchManageVO);
	}
	
	/**
	 * 뿌리기 정보 업데이트
	 * @param String
	 */
	@Override
	public void updateDutchInfo(int dDutchId) {
		dutchManageDao.updateDutchInfo(dDutchId);
	}
	
	/**
	 * 토큰값 만들기(a~z + A~Z + 0~9)
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
