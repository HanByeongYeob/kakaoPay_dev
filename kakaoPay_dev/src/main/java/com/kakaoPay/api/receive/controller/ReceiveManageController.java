package com.kakaoPay.api.receive.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/receive")
public class ReceiveManageController {
	
	/** DutchManageService */
	@Resource(name = "DutchManageService")
	DutchManageService dutchManageService;
	
	/** ReceiveManageService */
	@Resource(name = "ReceiveManageService")
	ReceiveManageService receiveManageService;
	
	final static Logger logger = LoggerFactory.getLogger(ReceiveManageController.class);
	
	/**
	 * 받기 API.
	 * @param X-USER-ID
	 * @param X-ROOM-ID
	 * @param D-AMOUNT
	 * @param D-NUM-PEOPLE
	 * @return D-TOKEN-VALUE
	 */
	@RequestMapping(value = "/executeReceiveAPI")
	public LinkedHashMap<String, Object> executeDutchAPI(@ModelAttribute("DutchManageVO")DutchManageVO dutchManageVO, HttpServletRequest request){
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		logger.info("-----------받기 API 시작-----------");
		logger.info("-----------요청자 정보 체크 시작-----------");
		int rAmount = 0;
		//요청한 사용자 정보 받기
		String xUserIdStr = (String) request.getParameter("X-USER-ID");
		
		//사용자 정보 세팅
	    if(xUserIdStr == null || "".equals(xUserIdStr)) {
	    	result.put("code", "1");
			result.put("msg", "Error Unable to check user's information.");
	    	return result;
	    }
	    
	    //속한 단톡방 정보 받기
	    String xRoomId = (String) request.getParameter("X-ROOM-ID");
	    //속한 단톡방 정보 세팅
	    if(xRoomId == null || "".equals(xRoomId)) {
	    	result.put("code", "1");
			result.put("msg", "Error Unable to check room's information.");
	    	return result;
	    }
	    dutchManageVO.setxRoomId(xRoomId);
	    
	    //토큰값 확인
	    if(dutchManageVO.getdTokenValue() == null || "".equals(dutchManageVO.getdTokenValue())) {
	    	result.put("code", "1");
			result.put("msg", "Error Unable to check token value.");
	    	return result;
	    }
	    logger.info("-----------요청자 정보 체크 끝-----------");
	    
	    logger.info("-----------뿌리기 조회 유효성 체크 시작-----------");
	    //뿌리기 정보 조회
		DutchInfoVO DutchInfoVO = dutchManageService.selectDutchInfo(dutchManageVO);
		
		//뿌리기 정보 확인
		if(DutchInfoVO == null) {
			result.put("code", "2");
			result.put("msg", "Error No dutch's information exists for that token value.");
			return result;
		}
		
		//뿌리기 완료 여부 확인
		if("Y".equals(DutchInfoVO.getdCompletYn())){
			result.put("code", "5");
			result.put("msg", "It is finished ducth");
			return result;
		}
		

		logger.info("-----------받기 유효기간 확인-----------");
		//현재날짜 세팅
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	    String current = dateFormat.format(new Date());	        
	    Long currentLong = Long.parseLong(current);
	    //받기 유효기간 세팅
	    Long dExpiryDt = Long.parseLong(DutchInfoVO.getrExpiryDt());
	    
		//받기 유효기간 확인		
	    if(dExpiryDt<currentLong) {
	    	result.put("code", "3");
			result.put("msg", "The receiveing period has expired.");
	    	return result;
	    }
	    
	    //받을 사람과 뿌린 사람 동일 인물 확인
	    int xUserId = Integer.parseInt(xUserIdStr);
	    if(xUserId == DutchInfoVO.getdUserId()){
	    	result.put("code", "99");
			result.put("msg", "Error You're user to have been dutch. So you can't receive the money.");
	    	return result;
	    }
	    logger.info("-----------뿌리기 조회 유효성 체크 끝-----------");
	    
		ReceiveManageVO receiveManageVO = new ReceiveManageVO();
		ReceiveInfoVO receiveUserInfo = new ReceiveInfoVO();
		List<ReceiveInfoVO> receiveInfoList = new ArrayList<ReceiveInfoVO>();

	    receiveManageVO.setdDutchId(DutchInfoVO.getdDutchId());
	    receiveManageVO.setrUserId(xUserId);
	    
	    //받을 사용자 정보 조회
	    receiveInfoList = receiveManageService.selectReceiveInfo(receiveManageVO);
	    
	    //받기 분배에 할당된 사용자인지 확인
	    if(receiveInfoList.size() < 1) {
	    	result.put("code", "4");
			result.put("msg", "You failed to receive the money");
	    	return result;
	    }
	    
	    receiveUserInfo = receiveInfoList.get(0);
	    
	    //1번이상 받기를 한 사용자인지 확인
	    if("Y".equals(receiveUserInfo.getrCompletYn())){
	    	result.put("code", "5");
			result.put("msg", "Error you already received the money.");
	    	return result;
	    }
	    
		logger.info("-----------받기 시작-----------");
		rAmount = receiveManageService.receiveRAmount(receiveUserInfo);           
		logger.info("-----------받기 끝-----------");
		result.put("rAmount", rAmount);
		logger.info("-----------받기 API 끝-----------");
		return result;
	}
}
