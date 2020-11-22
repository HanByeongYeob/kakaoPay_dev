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
* �Ѹ��� API�� ó���ϴ� Ŭ����
* @author �Ѻ���
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
	 * �Ѹ��� API.
	 * @param X-USER-ID
	 * @param X-ROOM-ID
	 * @param D-AMOUNT
	 * @param D-NUM-PEOPLE
	 * @return D-TOKEN-VALUE
	 */
	@RequestMapping(value = "/executeDutchAPI")
	public LinkedHashMap<String, Object> executeDutchAPI(@ModelAttribute("DutchManageVO")DutchManageVO dutchManageVO, HttpServletRequest request){
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		logger.info("-----------�Ѹ��� API ����-----------");
		logger.info("-----------��û�� ���� üũ ����-----------");
		//��û�� ����� ���� �ޱ�
		String xUserIdStr = (String) request.getParameter("X-USER-ID");
		//���� ����� ���� �ޱ�
		String xRoomId = (String) request.getParameter("X-ROOM-ID");

		//����� ���� üũ
		if(xUserIdStr == null || "".equals(xUserIdStr)) {
			result.put("code", "1");
			result.put("msg", "Error Unable to check user's information.");
			return result;
		}
		//���� ����� ���� üũ
		if(xRoomId == null || "".equals(xRoomId)) {
			result.put("code", "1");
			result.put("msg", "Error Unable to check room's information.");
			return result;
		}
		//�Ѹ� �ݾ� üũ
		if(dutchManageVO.getdAmount() == 0) {
			result.put("code", "1");
			result.put("msg", "Error Unable to check dutch's amount.");
			return result;
		}
		//�Ѹ� �ο� üũ
		if(dutchManageVO.getdNumPeople() == 0) {
			result.put("code", "1");
			result.put("msg", "Error Unable to check dutch's num of people.");
			return result;
		}
		
		//����� ���� ��ȸ
		int xUserId = Integer.parseInt(xUserIdStr);
		DutchManageVO requestUserInfo = dutchManageService.selectUserInfo(xUserId);

		//����� ���� üũ
		if(requestUserInfo == null) {
			result.put("code", "2");
			result.put("msg", "Error Unregistered user.");
			return result;
		}
		logger.info("-----------��û�� ���� üũ ��-----------");
		
		//Vo ����
		requestUserInfo.setxRoomId(xRoomId);
		requestUserInfo.setdAmount(dutchManageVO.getdAmount());
		requestUserInfo.setdNumPeople(dutchManageVO.getdNumPeople());

		logger.info("----------�Ѹ��� �ݾ� �й� ����-----------");
		//�Ѹ��� �ݾ� �й��ϱ�
		String tokenValue = dutchManageService.devideUserDamount(requestUserInfo);
		logger.info("----------�Ѹ��� �ݾ� �й� ��-----------");
		if("token".equals(tokenValue)) {
			result.put("code", "0");
			result.put("msg", "Error creating token.");
		}else if("check".equals(tokenValue)) {
			result.put("code", "99");
			result.put("msg", "Error exceeded the number of members in the group that can be dutched.");
		}else {
			result.put("tokenValue", tokenValue);
		}
		logger.info("-----------�Ѹ��� API ��-----------");
		
		return result;
	}
	
	@RequestMapping(value = "/selectDutchAPI")
	public LinkedHashMap<String, Object> selectDutchAPI(@ModelAttribute("DutchManageVO")DutchManageVO dutchManageVO, HttpServletRequest request) throws JsonProcessingException{
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		logger.info("-----------�Ѹ��� ��ȸ API ����-----------");
		logger.info("-----------��û�� ���� üũ ����-----------");
		//��û�� ����� ���� �ޱ�
		String xUserIdStr = (String) request.getParameter("X-USER-ID");
		//���� ����� ���� �ޱ�
		String xRoomId = (String) request.getParameter("X-ROOM-ID");
		
		//���� ����� ���� üũ
		if(xRoomId == null || "".equals(xRoomId)) {
			result.put("code", "1");
			result.put("msg", "Error Unable to check room's information.");
			return result;
		}
		
		//��û�� ����� ���� üũ
		if(xUserIdStr == null || "".equals(xUserIdStr)) {
			result.put("code", "1");
			result.put("msg", "Error Unable to check user's information.");
			return result;
		}
		
		//��ū�� üũ
	    if(dutchManageVO.getdTokenValue() == null || "".equals(dutchManageVO.getdTokenValue())) {
	    	result.put("code", "1");
	    	result.put("msg", "Error Unable to check token value.");
	    	return result;
	    }
	    logger.info("-----------��û�� ���� üũ ��-----------");
	    
	    //Vo ����
		int xUserId = Integer.parseInt(xUserIdStr);
		dutchManageVO.setxRoomId(xRoomId);
		
		logger.info("-----------�Ѹ��� ���� ��ȸ ����-----------");
		//�Ѹ��� ���� ��ȸ
		DutchInfoVO duichAPIInfo = dutchManageService.selectDutchInfo(dutchManageVO);

        //�Ѹ��� ���� üũ
        if(duichAPIInfo == null) {
        	result.put("code", "2");
	    	result.put("msg", "Error No dutch's information exists for that token value.");
        	return result;
        }
        
        //�Ѹ��� ���� Ȯ��
        logger.info("-----------�Ѹ��� ���� Ȯ��-----------");
        if(duichAPIInfo.getdUserId() != xUserId){
        	result.put("code", "99");
	    	result.put("msg", "Error You're not user to have been dutch.");
	    	return result;
        }
        
        //Vo ����
        dutchManageVO.setxUserId(xUserId);
        
        logger.info("-----------�Ѹ��� ����Ⱓ Ȯ��-----------");
        //���� �ð� ����
      	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String current = dateFormat.format(new Date());
        Long currentInt = Long.parseLong(current);
        
        //���� �Ⱓ Ȯ��
        Long dExpiryDt = Long.parseLong(duichAPIInfo.getdExpiryDt());
        if(currentInt > dExpiryDt) {
        	result.put("code", "3");
	    	result.put("msg", "The dutch period has expired.");
	    	return result;
        }
        
        logger.info("-----------�ޱ� ���� Ȯ��-----------");
        ReceiveManageVO receiveManageVO = new ReceiveManageVO();
        receiveManageVO.setdDutchId(duichAPIInfo.getdDutchId());
        receiveManageVO.setrCompletYn("Y");
        
        //�ޱ� �Ϸ�� �ޱ����� ��ȸ
        List<ReceiveInfoVO> receiveInfoList = receiveManageService.selectReceiveInfo(receiveManageVO);

        //response ������ ����
        result.put("The-Dutch-Time", duichAPIInfo.getdRegisterDt());
        result.put("The-Dutch-Amount", duichAPIInfo.getdAount());
        int receiveTotalAmount = 0;
        result.put("The-Finished-Receive-Amount", receiveTotalAmount);
        
        //�ޱ� ������ ���� Ȯ��
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
        
        logger.info("-----------�Ѹ��� ���� ��ȸ ��-----------");
        logger.info("-----------�Ѹ��� ��ȸ API ��-----------");
        return result;
	}
}
