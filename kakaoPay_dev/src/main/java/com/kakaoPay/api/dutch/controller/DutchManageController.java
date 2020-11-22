package com.kakaoPay.api.dutch.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kakaoPay.api.dutch.service.DutchInfoVO;
import com.kakaoPay.api.dutch.service.DutchManageService;
import com.kakaoPay.api.dutch.service.DutchManageVO;
import com.kakaoPay.api.receive.service.ReceiveInfoVO;
import com.kakaoPay.api.receive.service.ReceiveManageService;
import com.kakaoPay.api.receive.service.ReceiveManageVO;

/**
*
* 뿌리기 API를 처리하는 클래스
* @author 한병엽
* @since 2020.11.18
* @version 1.0
* @see
*
*/

@RestController
@RequestMapping("/dutch")
public class DutchManageController {

	/** DutchManageService */
	@Resource(name = "DutchManageService")
	DutchManageService dutchManageService;
	
	/** ReceiveManageService */
	@Resource(name = "ReceiveManageService")
	ReceiveManageService receiveManageService;
	
	final static Logger logger = LoggerFactory.getLogger(DutchManageController.class);
	
	/**
	 * 뿌리기 API.
	 * @param X-USER-ID
	 * @param X-ROOM-ID
	 * @param D-AMOUNT
	 * @param D-NUM-PEOPLE
	 * @return D-TOKEN-VALUE
	 */
	@RequestMapping(value = "/executeDutchAPI")
	public LinkedHashMap<String, Object> executeDutchAPI(@ModelAttribute("DutchManageVO")DutchManageVO dutchManageVO, HttpServletRequest request){
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		logger.info("-----------뿌리기 API 시작-----------");
		logger.info("-----------요청자 정보 체크 시작-----------");
		//요청한 사용자 정보 받기
		String xUserIdStr = (String) request.getParameter("X-USER-ID");
		//속한 단톡방 정보 받기
		String xRoomId = (String) request.getParameter("X-ROOM-ID");

		//사용자 정보 체크
		if(xUserIdStr == null || "".equals(xUserIdStr)) {
			result.put("code", "1");
			result.put("msg", "Error Unable to check user's information.");
			return result;
		}
		//속한 단톡방 정보 체크
		if(xRoomId == null || "".equals(xRoomId)) {
			result.put("code", "1");
			result.put("msg", "Error Unable to check room's information.");
			return result;
		}
		//뿌릴 금액 체크
		if(dutchManageVO.getdAmount() == 0) {
			result.put("code", "1");
			result.put("msg", "Error Unable to check dutch's amount.");
			return result;
		}
		//뿌릴 인원 체크
		if(dutchManageVO.getdNumPeople() == 0) {
			result.put("code", "1");
			result.put("msg", "Error Unable to check dutch's num of people.");
			return result;
		}
		
		//사용자 정보 조회
		int xUserId = Integer.parseInt(xUserIdStr);
		DutchManageVO requestUserInfo = dutchManageService.selectUserInfo(xUserId);

		//사용자 정보 체크
		if(requestUserInfo == null) {
			result.put("code", "2");
			result.put("msg", "Error Unregistered user.");
			return result;
		}
		logger.info("-----------요청자 정보 체크 끝-----------");
		
		//Vo 세팅
		requestUserInfo.setxRoomId(xRoomId);
		requestUserInfo.setdAmount(dutchManageVO.getdAmount());
		requestUserInfo.setdNumPeople(dutchManageVO.getdNumPeople());

		logger.info("----------뿌리기 금액 분배 시작-----------");
		//뿌리기 금액 분배하기
		String tokenValue = dutchManageService.devideUserDamount(requestUserInfo);
		logger.info("----------뿌리기 금액 분배 끝-----------");
		if("token".equals(tokenValue)) {
			result.put("code", "0");
			result.put("msg", "Error creating token.");
		}else if("check".equals(tokenValue)) {
			result.put("code", "99");
			result.put("msg", "Error exceeded the number of members in the group that can be dutched.");
		}else {
			result.put("tokenValue", tokenValue);
		}
		logger.info("-----------뿌리기 API 끝-----------");
		
		return result;
	}
	
	@RequestMapping(value = "/selectDutchAPI")
	public LinkedHashMap<String, Object> selectDutchAPI(@ModelAttribute("DutchManageVO")DutchManageVO dutchManageVO, HttpServletRequest request) throws JsonProcessingException{
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		logger.info("-----------뿌리기 조회 API 시작-----------");
		logger.info("-----------요청자 정보 체크 시작-----------");
		//요청한 사용자 정보 받기
		String xUserIdStr = (String) request.getParameter("X-USER-ID");
		//속한 단톡방 정보 받기
		String xRoomId = (String) request.getParameter("X-ROOM-ID");
		
		//속한 단톡방 정보 체크
		if(xRoomId == null || "".equals(xRoomId)) {
			result.put("code", "1");
			result.put("msg", "Error Unable to check room's information.");
			return result;
		}
		
		//요청한 사용자 정보 체크
		if(xUserIdStr == null || "".equals(xUserIdStr)) {
			result.put("code", "1");
			result.put("msg", "Error Unable to check user's information.");
			return result;
		}
		
		//토큰값 체크
	    if(dutchManageVO.getdTokenValue() == null || "".equals(dutchManageVO.getdTokenValue())) {
	    	result.put("code", "1");
	    	result.put("msg", "Error Unable to check token value.");
	    	return result;
	    }
	    logger.info("-----------요청자 정보 체크 끝-----------");
	    
	    //Vo 세팅
		int xUserId = Integer.parseInt(xUserIdStr);
		dutchManageVO.setxRoomId(xRoomId);
		
		logger.info("-----------뿌리기 정보 조회 시작-----------");
		//뿌리기 정보 조회
		DutchInfoVO duichAPIInfo = dutchManageService.selectDutchInfo(dutchManageVO);

        //뿌리기 정보 체크
        if(duichAPIInfo == null) {
        	result.put("code", "2");
	    	result.put("msg", "Error No dutch's information exists for that token value.");
        	return result;
        }
        
        //뿌리기 본인 확인
        logger.info("-----------뿌리기 본인 확인-----------");
        if(duichAPIInfo.getdUserId() != xUserId){
        	result.put("code", "99");
	    	result.put("msg", "Error You're not user to have been dutch.");
	    	return result;
        }
        
        //Vo 세팅
        dutchManageVO.setxUserId(xUserId);
        
        logger.info("-----------뿌리기 만료기간 확인-----------");
        //지금 시간 세팅
      	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String current = dateFormat.format(new Date());
        Long currentInt = Long.parseLong(current);
        
        //만료 기간 확인
        Long dExpiryDt = Long.parseLong(duichAPIInfo.getdExpiryDt());
        if(currentInt > dExpiryDt) {
        	result.put("code", "3");
	    	result.put("msg", "The dutch period has expired.");
	    	return result;
        }
        
        logger.info("-----------받기 정보 확인-----------");
        ReceiveManageVO receiveManageVO = new ReceiveManageVO();
        receiveManageVO.setdDutchId(duichAPIInfo.getdDutchId());
        receiveManageVO.setrCompletYn("Y");
        
        //받기 완료된 받기정보 조회
        List<ReceiveInfoVO> receiveInfoList = receiveManageService.selectReceiveInfo(receiveManageVO);

        //response 데이터 생성
        result.put("The-Dutch-Time", duichAPIInfo.getdRegisterDt());
        result.put("The-Dutch-Amount", duichAPIInfo.getdAount());
        int receiveTotalAmount = 0;
        result.put("The-Finished-Receive-Amount", receiveTotalAmount);
        
        //받기 정보가 존재 확인
        if(receiveInfoList.size()>0) {
        	int receiveInfoCnt = receiveInfoList.size();
        	for(int i =0; i<receiveInfoCnt; i++) {
        		result.put("The-Receive-Id"+(i+1), receiveInfoList.get(i).getrUserId());
        		int rAmount = receiveInfoList.get(i).getrAmount();
        		result.put("The-Receive-Amount"+(i+1), rAmount);
        		receiveTotalAmount += rAmount;
        	}
        }
        result.put("The-Finished-Receive-Amount", receiveTotalAmount);
        
        logger.info("-----------뿌리기 정보 조회 끝-----------");
        logger.info("-----------뿌리기 조회 API 끝-----------");
        return result;
	}
}
