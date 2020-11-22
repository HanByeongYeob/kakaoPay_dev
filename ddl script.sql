CREATE SEQUENCE seq_dutch START 1;
CREATE SEQUENCE seq_receive START 1;

-- 사용자 정보 테이블 생성
CREATE TABLE public.TBL_USER_INFO (
 X_USER_ID INT NOT NULL,
 X_USER_NAME VARCHAR(10) NULL,
 X_USER_BALANCE INT NULL,
PRIMARY KEY (X_USER_ID)
);

COMMENT ON TABLE PUBLIC.TBL_USER_INFO IS '사용자 정보';
COMMENT ON COLUMN PUBLIC.TBL_USER_INFO.X_USER_ID IS '사용자 ID';
COMMENT ON COLUMN PUBLIC.TBL_USER_INFO.X_USER_NAME IS '사용자 이름';
COMMENT ON COLUMN PUBLIC.TBL_USER_INFO.X_USER_BALANCE IS '사용자 잔액';

-- 대화방 정보 테이블 생성
CREATE TABLE public.TBL_ROOM_INFO (
 X_ROOM_ID VARCHAR(100) NOT NULL,
 X_USER_ID INT NOT NULL,
 X_ROOM_NAME VARCHAR(100) NOT NULL,
PRIMARY KEY (X_ROOM_ID, X_USER_ID),
FOREIGN KEY (X_USER_ID) REFERENCES public.TBL_USER_INFO(X_USER_ID) ON UPDATE CASCADE
);

COMMENT ON TABLE PUBLIC.TBL_ROOM_INFO IS '대화방 정보';
COMMENT ON COLUMN PUBLIC.TBL_ROOM_INFO.X_ROOM_ID IS '대화방 ID';
COMMENT ON COLUMN PUBLIC.TBL_ROOM_INFO.X_USER_ID IS '사용자 ID';
COMMENT ON COLUMN PUBLIC.TBL_ROOM_INFO.X_ROOM_NAME IS '대화방 이름';

-- 뿌리기 정보 테이블 생성
CREATE TABLE public.TBL_DUTCH_INFO (
 D_DUTCH_ID INT NOT NULL DEFAULT nextval('seq_dutch'),
 D_TOKEN_VALUE VARCHAR(3) NOT NULL,
 D_ROOM_ID VARCHAR(100) NOT NULL,
 D_USER_ID INT NOT NULL,
 D_REGISTER_DT TIMESTAMP NOT NULL,
 D_AMOUNT INT NOT NULL,
 D_NUM_PEOPLE INT NOT NULL,
 D_COMPLET_YN VARCHAR(1) NULL DEFAULT 'N',
PRIMARY KEY (D_DUTCH_ID),
FOREIGN KEY (D_ROOM_ID, D_USER_ID) REFERENCES public.TBL_ROOM_INFO(X_ROOM_ID, X_USER_ID) ON UPDATE CASCADE
);

COMMENT ON TABLE PUBLIC.TBL_DUTCH_INFO IS '뿌리기 정보';
COMMENT ON COLUMN PUBLIC.TBL_DUTCH_INFO.D_DUTCH_ID IS '뿌리기 아이디';
COMMENT ON COLUMN PUBLIC.TBL_DUTCH_INFO.D_TOKEN_VALUE IS '뿌리기 토큰 값';
COMMENT ON COLUMN PUBLIC.TBL_DUTCH_INFO.D_ROOM_ID IS '뿌리기 대화방 ID';
COMMENT ON COLUMN PUBLIC.TBL_DUTCH_INFO.D_USER_ID IS '뿌리기 등록자 ID';
COMMENT ON COLUMN PUBLIC.TBL_DUTCH_INFO.D_REGISTER_DT IS '뿌리기 등록자 일시';
COMMENT ON COLUMN PUBLIC.TBL_DUTCH_INFO.D_AMOUNT IS '뿌리기 금액';
COMMENT ON COLUMN PUBLIC.TBL_DUTCH_INFO.D_NUM_PEOPLE IS '사용자 ID';
COMMENT ON COLUMN PUBLIC.TBL_DUTCH_INFO.D_COMPLET_YN IS '뿌리기 완료 여부';

-- 받기 정보 테이블 생성
CREATE TABLE public.TBL_RECEIVE_INFO (
 R_RECEIVE_ID INT NOT NULL DEFAULT nextval('seq_receive'),
 D_DUTCH_ID INT NOT NULL,
 R_USER_ID INT NOT NULL,
 R_REGISTER_DT TIMESTAMP NOT NULL,
 R_AMOUNT INT NOT NULL,
 R_COMPLET_YN VARCHAR(1) NULL DEFAULT 'N',
PRIMARY KEY (R_RECEIVE_ID),
FOREIGN KEY (D_DUTCH_ID) REFERENCES public.TBL_DUTCH_INFO(D_DUTCH_ID) ON UPDATE CASCADE
);

COMMENT ON TABLE PUBLIC.TBL_RECEIVE_INFO IS '받기 정보';
COMMENT ON COLUMN PUBLIC.TBL_RECEIVE_INFO.R_RECEIVE_ID IS '받기 ID';
COMMENT ON COLUMN PUBLIC.TBL_RECEIVE_INFO.D_DUTCH_ID IS '뿌리기 ID';
COMMENT ON COLUMN PUBLIC.TBL_RECEIVE_INFO.R_USER_ID IS '받기 등록자 ID';
COMMENT ON COLUMN PUBLIC.TBL_RECEIVE_INFO.R_REGISTER_DT IS '받기 등록 일시';
COMMENT ON COLUMN PUBLIC.TBL_RECEIVE_INFO.R_AMOUNT IS '받기 금액';
COMMENT ON COLUMN PUBLIC.TBL_RECEIVE_INFO.R_COMPLET_YN IS '받기 완료 여부';