package com.kakaoPay.api.dutch.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kakaoPay.api.dutch.service.DutchInfoVO;
import com.kakaoPay.api.dutch.service.DutchManageVO;

@Repository("DutchManageDao")
public class DutchManageDao {
	
	@Resource(name = "sqlSession")
    private SqlSession sqlSession;
 
    private static final String NAMESPACE = "dutchManageDao";

    /**
	 * ����� ���� ��ȸ
	 * @param xuserId
	 * @return DutchManageVO
	 */
    public DutchManageVO selectUserInfo(int xUserId){
        return (DutchManageVO) sqlSession.selectOne(NAMESPACE + ".selectUserInfo", xUserId);
    }
    
    /**
	 * ����� ��� ���� ��ȸ
	 * @param xuserId
	 * @return DutchManageVO
	 */
    public List<DutchManageVO> selectRoomMemberList(DutchManageVO dutchManageVO){
    	return sqlSession.selectList(NAMESPACE + ".selectRoomMemberList", dutchManageVO);
    }
   
    
    /**
   	 * ��ū �ߺ��� ���� ��ȸ
   	 * @param xuserId
   	 * @return DutchManageVO
   	 */
	public int selectOverlappingTokenValue(DutchManageVO dutchManageVO){
		return sqlSession.selectOne(NAMESPACE + ".selectOverlappingTokenValue", dutchManageVO);
	}
       
    /**
   	 * �Ѹ��� ���� �Է�
   	 * @param xuserId
   	 * @return DutchManageVO
   	 */
	public void insertDutchInfo(DutchManageVO dutchManageVO){
		sqlSession.insert(NAMESPACE + ".insertDutchInfo", dutchManageVO);
	}
    
    /**
	 * ����� ���� ���� ������Ʈ
	 * @param ReceiveManageVO
	 * @return DutchManageVO
	 */
	public void updateUserBalance(DutchManageVO dutchManageVO) {
    	sqlSession.update(NAMESPACE + ".updateUserBalance", dutchManageVO);
    }
    
    /**
	 * �Ѹ��� ���� ��ȸ
	 * @param DutchManageVO
	 * @return DutchManageVO
	 */
	public DutchInfoVO selectDutchInfo(DutchManageVO dutchManageVO) {
		return (DutchInfoVO) sqlSession.selectOne(NAMESPACE + ".selectDutchInfo", dutchManageVO);
	}
	
	/**
	 * �Ѹ��� ���� ������Ʈ
	 * @param String
	 */
	public void updateDutchInfo(int dDutchId) {
		sqlSession.update(NAMESPACE + ".updateDutchInfo", dDutchId);
	}
}
