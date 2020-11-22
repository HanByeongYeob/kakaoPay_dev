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
* �Ѹ��� API�� ó���ϴ� Ŭ����
* @author �Ѻ���
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
	 * �ޱ� API.
	 * @param X-USER-ID
	 * @param X-ROOM-ID
	 * @param D-AMOUNT
	 * @param D-NUM-PEOPLE
	 * @return D-TOKEN-VALUE
	 */
	@RequestMapping(value = "/executeReceiveAPI")
	public LinkedHashMap<String, Object> executeDutchAPI(@ModelAttribute("DutchManageVO")DutchManageVO dutchManageVO, HttpServletRequest request){
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		logger.info("-----------�ޱ� API ����-----------");
		logger.info("-----------��û�� ���� üũ ����-----------");
		int rAmount = 0;
		//��û�� ����� ���� �ޱ�
		String xUserIdStr = (String) request.getParameter("X-USER-ID");
		
		//����� ���� ����
	    if(xUserIdStr == null || "".equals(xUserIdStr)) {
	    	result.put("code", "1");
			result.put("msg", "Error Unable to check user's information.");
	    	return result;
	    }
	    
	    //���� ����� ���� �ޱ�
	    String xRoomId = (String) request.getParameter("X-ROOM-ID");
	    //���� ����� ���� ����
	    if(xRoomId == null || "".equals(xRoomId)) {
	    	result.put("code", "1");
			result.put("msg", "Error Unable to check room's information.");
	    	return result;
	    }
	    dutchManageVO.setxRoomId(xRoomId);
	    
	    //��ū�� Ȯ��
	    if(dutchManageVO.getdTokenValue() == null || "".equals(dutchManageVO.getdTokenValue())) {
	    	result.put("code", "1");
			result.put("msg", "Error Unable to check token value.");
	    	return result;
	    }
	    logger.info("-----------��û�� ���� üũ ��-----------");
	    
	    logger.info("-----------�Ѹ��� ��ȸ ��ȿ�� üũ ����-----------");
	    //�Ѹ��� ���� ��ȸ
		DutchInfoVO DutchInfoVO = dutchManageService.selectDutchInfo(dutchManageVO);
		
		//�Ѹ��� ���� Ȯ��
		if(DutchInfoVO == null) {
			result.put("code", "2");
			result.put("msg", "Error No dutch's information exists for that token value.");
			return result;
		}
		
		//�Ѹ��� �Ϸ� ���� Ȯ��
		if("Y".equals(DutchInfoVO.getdCompletYn())){
			result.put("code", "5");
			result.put("msg", "It is finished ducth");
			return result;
		}
		

		logger.info("-----------�ޱ� ��ȿ�Ⱓ Ȯ��-----------");
		//���糯¥ ����
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	    String current = dateFormat.format(new Date());	        
	    Long currentLong = Long.parseLong(current);
	    //�ޱ� ��ȿ�Ⱓ ����
	    Long dExpiryDt = Long.parseLong(DutchInfoVO.getrExpiryDt());
	    
		//�ޱ� ��ȿ�Ⱓ Ȯ��		
	    if(dExpiryDt<currentLong) {
	    	result.put("code", "3");
			result.put("msg", "The receiveing period has expired.");
	    	return result;
	    }
	    
	    //���� ����� �Ѹ� ��� ���� �ι� Ȯ��
	    int xUserId = Integer.parseInt(xUserIdStr);
	    if(xUserId == DutchInfoVO.getdUserId()){
	    	result.put("code", "99");
			result.put("msg", "Error You're user to have been dutch. So you can't receive the money.");
	    	return result;
	    }
	    logger.info("-----------�Ѹ��� ��ȸ ��ȿ�� üũ ��-----------");
	    
		ReceiveManageVO receiveManageVO = new ReceiveManageVO();
		ReceiveInfoVO receiveUserInfo = new ReceiveInfoVO();
		List<ReceiveInfoVO> receiveInfoList = new ArrayList<ReceiveInfoVO>();

	    receiveManageVO.setdDutchId(DutchInfoVO.getdDutchId());
	    receiveManageVO.setrUserId(xUserId);
	    
	    //���� ����� ���� ��ȸ
	    receiveInfoList = receiveManageService.selectReceiveInfo(receiveManageVO);
	    
	    //�ޱ� �й迡 �Ҵ�� ��������� Ȯ��
	    if(receiveInfoList.size() < 1) {
	    	result.put("code", "4");
			result.put("msg", "You failed to receive the money");
	    	return result;
	    }
	    
	    receiveUserInfo = receiveInfoList.get(0);
	    
	    //1���̻� �ޱ⸦ �� ��������� Ȯ��
	    if("Y".equals(receiveUserInfo.getrCompletYn())){
	    	result.put("code", "5");
			result.put("msg", "Error you already received the money.");
	    	return result;
	    }
	    
		logger.info("-----------�ޱ� ����-----------");
		rAmount = receiveManageService.receiveRAmount(receiveUserInfo);           
		logger.info("-----------�ޱ� ��-----------");
		result.put("rAmount", rAmount);
		logger.info("-----------�ޱ� API ��-----------");
		return result;
	}
}
