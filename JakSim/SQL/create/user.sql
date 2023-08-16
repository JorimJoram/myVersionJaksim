DROP TABLE USER_INFO;

CREATE TABLE USER_INFO(
                          USER_ID VARCHAR(30),
                          USER_PW VARCHAR(100) NOT NULL,
                          USER_NAME VARCHAR(50) NOT NULL,
                          USER_GENDER INT(1) NOT NULL,
                          USER_TEL VARCHAR(15) UNIQUE NOT NULL,
                          USER_EMAIL VARCHAR(100) UNIQUE NOT NULL,
                          USER_BIRTH DATE NOT NULL,
                          USER_C_DT DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          USER_M_DT DATETIME,
                          USER_ROLE INT(1) NOT NULL,
                          PRIMARY KEY (USER_ID)
);

COMMIT;

INSERT INTO USER_INFO
VALUES('wkdgyfla97', '1234', '장효림', '0', '01085945142', 'wkdgyfla@naver.com', '1997-03-30', '2023-06-09 12:36:34', NULL, 1);
INSERT INTO USER_INFO
VALUES('hye8997', '1234', '정혜화', '1', '01012345678', 'hye8997@naver.com', '1989-03-30', current_timestamp, NULL, 1);
INSERT INTO USER_INFO
VALUES('ujeong', '1004', '남유정', '1', '01012345999', 'ujeong@naver.com', '2000-05-28', current_timestamp, NULL, 1);
INSERT INTO USER_INFO
VALUES('humble', '6789', '김겸손', '0', '01091029629', 'humble@naver.com', '1996-02-09', current_timestamp, NULL, 1);
INSERT INTO USER_INFO
VALUES('hyerin', '1234', '전혜린', '1', '01040853763', 'hyerin@naver.com', '1961-09-27', current_timestamp, NULL, 1);

ALTER TABLE USER_INFO ADD USER_M_DT DATETIME;

alter table payment add TID VARCHAR(255) NOT NULL;
