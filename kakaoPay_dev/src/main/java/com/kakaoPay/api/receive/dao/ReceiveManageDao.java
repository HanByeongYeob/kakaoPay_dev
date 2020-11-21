package com.kakaoPay.api.receive.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kakaoPay.api.receive.service.ReceiveInfoVO;
import com.kakaoPay.api.receive.service.ReceiveManageVO;

@Repository("ReceiveManageDao")
public class ReceiveManageDao {
	
	@Resource(name = "sqlSession")
    private SqlSession sqlSession;
 
    private static final String NAMESPACE = "receiveManageDao";

    /**
	 * 받기 정보 입력
	 * @param xuserId
	 * @return DutchManageVO
	 */
    public void	insertReceiveInfo(ReceiveManageVO receiveManageVO){
    	sqlSession.insert(NAMESPACE + ".insertReceiveInfo", receiveManageVO);
    }
    
    /**
	 * 받기 정보 조회
	 * @param DutchManageVO
	 * @return DutchManageVO
	 */
	public List<ReceiveInfoVO> selectReceiveInfo(ReceiveManageVO receiveManageVO) {
		return sqlSession.selectList(NAMESPACE + ".selectReceiveInfo", receiveManageVO);
	}
	
	 /**
	  * 받기 정보 조회
	  * @param DutchManageVO
	  * @return DutchManageVO
	  */
	public void updateReceiveInfo(ReceiveInfoVO receiveInfoVO) {
		sqlSession.selectList(NAMESPACE + ".updateReceiveInfo", receiveInfoVO);
	}
	
	
}
