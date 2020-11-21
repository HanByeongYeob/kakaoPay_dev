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
	 * 사용자 정보 조회
	 * @param xuserId
	 * @return DutchManageVO
	 */
    public DutchManageVO selectUserInfo(int xUserId){
        return (DutchManageVO) sqlSession.selectOne(NAMESPACE + ".selectUserInfo", xUserId);
    }
    
    /**
	 * 단톡방 멤버 정보 조회
	 * @param xuserId
	 * @return DutchManageVO
	 */
    public List<DutchManageVO> selectRoomMemberList(DutchManageVO dutchManageVO){
    	return sqlSession.selectList(NAMESPACE + ".selectRoomMemberList", dutchManageVO);
    }
   
    
    /**
   	 * 토큰 중복값 정보 조회
   	 * @param xuserId
   	 * @return DutchManageVO
   	 */
	public int selectOverlappingTokenValue(DutchManageVO dutchManageVO){
		return sqlSession.selectOne(NAMESPACE + ".selectOverlappingTokenValue", dutchManageVO);
	}
       
    /**
   	 * 뿌리기 정보 입력
   	 * @param xuserId
   	 * @return DutchManageVO
   	 */
	public void insertDutchInfo(DutchManageVO dutchManageVO){
		sqlSession.insert(NAMESPACE + ".insertDutchInfo", dutchManageVO);
	}
    
    /**
	 * 사용자 계좌 정보 업데이트
	 * @param ReceiveManageVO
	 * @return DutchManageVO
	 */
	public void updateUserBalance(DutchManageVO dutchManageVO) {
    	sqlSession.update(NAMESPACE + ".updateUserBalance", dutchManageVO);
    }
    
    /**
	 * 뿌리기 정보 조회
	 * @param DutchManageVO
	 * @return DutchManageVO
	 */
	public DutchInfoVO selectDutchInfo(DutchManageVO dutchManageVO) {
		return (DutchInfoVO) sqlSession.selectOne(NAMESPACE + ".selectDutchInfo", dutchManageVO);
	}
	
	/**
	 * 뿌리기 정보 업데이트
	 * @param String
	 */
	public void updateDutchInfo(int dDutchId) {
		sqlSession.update(NAMESPACE + ".updateDutchInfo", dDutchId);
	}
}
