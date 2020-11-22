# 카카오페이 뿌리기 기능 간소화

## 1.개요
	카카오페이 뿌리기 기능 구현하기 (UI 및 메세지 영역은 제외한 간소화된 REST API를 구현)

------------

## 2.개발환경
	* IDE(Eclipse 2019-12(4.14))
	* Openjdk-15.0.1
	* Java-version 1.8
	* Apache-Tomcat-8.5.59
	* Spring 4.3.6.RELEASE
	* Maven 3.6.3
	* DB(Postgresql-11.2-1)
	* Mybatis 3.4.6

------------

## 3. 데이터 생성
	* 테스트 전 테이블을 생성하기 위해서 DDL Script.sql 파일의 스크립트를 실행하여 주세요.
	* 테스트 전 데이터를 생성하기 위해서 DML Script.sql 파일의 스크립트를 실행하여 주세요.

------------

## 4. 핵심 문제해결 전략
	4.1. 뿌리기 API
		* 뿌리기 금액 분배 전 파라미터 체크 및 사용자 정보 확인
		* 뿌리기 인원이 (단톡방 멤버 인원-1) 보다 큰 지 확인
		* 뿌리기 분배 시 하나의 트랜잭션으로 처리
		* 같은 단톡방에서 같은 토큰 값을 가지면 안되므로 토큰 값 확인
		* 뿌릴 금액과 뿌릴 인원은 동적으로 변하므로 ramdom 함수를 이용하여 무작위 분배 로직 구현
		* 마지막 멤버의 받을 금액은 뿌릴 금액 - (현재 받을 금액정보 합)으로 로직 구현
		* 뿌리기 끝나고 뿌릴 금액 만큼 현 잔액에서 감소
		
	4.2. 받기 API
		* 받기 전 파라미터 체크
		* 뿌리기 정보 존재 여부 확인
		* 뿌리기 및 받기 완료 여부 확인
		* 받기 유효기간 확인
		* 받기를 시도한 사람과 뿌리기 한 사람 동일 여부 확인
		* 받기가 할당된 사람인지 확인
		* 받기 로직 시 하나의 트랜잭션으로 처리
		* 받기 완료 처리
		* 받기 정보에 할당된 금액 만큼 현잔액에서 증가
		* 받기가 할당된 모든 사람이 받기 완료 시 뿌리기 완료 처리
		
	4.3. 조회 API
		* 뿌리기 조회 전 파라미터 체크 및 사용자 정보 체크
		* 뿌리기 정보 존재 여부 확인
		* 뿌리기 한 본인 확인 및 만료기간 확인
		* 받기 완료한 정보 확인
		
------------
	
## 5. API 기술문서
	5.1. 뿌리기 API
		* URL : /dutch/executeDutchAPI
		* 파라미터
			X-USER-ID : 사용자 아이디 (숫자형태)
			X-ROOM-ID : 단톡방 아이디 (문자형태) 
			dAmount : 뿌릴 금액 (숫자형태)
			dNumPeople : 뿌릴 인원 (숫자형태)
			
		* 요청/응답 메시지 예제
			- 요청메시지
				/dutch/executeDutchAPI?X-USER-ID=123&X-ROOM-ID=kakao&dAmount=100&dNumPeople=2
			- 응답메시지
				{"tokenValue":"vmp"}
			
	5.2. 받기 API
		* URL : /receive/executeReceiveAPI
		* 파라미터
			X-USER-ID : 사용자 아이디 (숫자형태)
			X-ROOM-ID : 단톡방 아이디 (문자형태)
			TokenValue : 토큰 값 (3자리 문자열)
			
		* 요청/응답 메시지 예제
			- 요청메시지
				/receive/executeReceiveAPI?X-USER-ID=456&X-ROOM-ID=kakao&dTokenValue=토큰값
			- 응답메시지
				{"rAmount":90}
			
	5.3. 조회 API
		* URL : /receive/executeReceiveAPI
		* 파라미터
			X-USER-ID : 사용자 아이디 (숫자형태)
			X-ROOM-ID : 단톡방 아이디 (문자형태)
			TokenValue : 토큰 값 (3자리 문자열)
			
		* 요청/응답 메시지 예제
			- 요청메시지
				/dutch/selectDutchAPI?X-USER-ID=123&X-ROOM-ID=kakao&dTokenValue=토큰값
			- 응답메시지
				{"The-Dutch-Time":"2020-11-22 15:11:09","The-Dutch-Amount":100,"The-Finished-Receive-Amount":90,"The-Receive-Id1":456,"The-Receive-Amount1":90}

	5.4. API 에러 코드 정리
		* 0 : 토큰 생성 오류
			- Error creating token.
			
		* 1 : 파라미터 누락 오류
			- Error Unable to check user's information.
			- Error Unable to check room's information.
			- Error Unable to check token value.
			- Error Unable to check dutch's amount.
			- Error Unable to check dutch's num of people.
			
		* 2 : 정보 없음 오류
			- Error No dutch's information exists for that token value.
			- Error Unregistered user.
			
		* 3 : 유효기간 만료 오류
			- The receiveing period has expired.
			- The dutch period has expired.
			
		* 4 : 할당되지 않은 사용자
			- You failed to receive the money.
			
		* 5 : 완료된 기능
			- It is finished ducth
			- Error you already received the money.
			
		* 99 : 기타 오류	
			- Error You're user to have been dutch. So you can't receive the money.
			- Error exceeded the number of members in the group that can be dutched.
			- Error You're not user to have been dutch.
		
