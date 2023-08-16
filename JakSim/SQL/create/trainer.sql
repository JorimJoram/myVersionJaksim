DROP TABLE TRAINER_DETAILS;
DROP TABLE TRAINER_IMAGE;
DROP TABLE TRAINER_CERT;
DROP TABLE TRAINER_CAREER;
DROP TABLE PRODUCT;


CREATE TABLE TRAINER_DETAILS (
    UT_IDX INT(8) NOT NULL AUTO_INCREMENT,
    UT_INTRO VARCHAR(600) NOT NULL,
    UT_INSTA VARCHAR(100) NULL,
    UT_GYM VARCHAR(300) NOT NULL,
    USER_ID VARCHAR(30) NOT NULL,
    UT_EXPERT_1 INT(1) NOT NULL CHECK (UT_EXPERT_1 BETWEEN 0 AND 5),
    UT_EXPERT_2 INT(1) NOT NULL CHECK (UT_EXPERT_2 BETWEEN 0 AND 5),
    UT_ADDRESS VARCHAR(300) NOT NULL,
    UT_PROFILE_IMG VARCHAR(1000) NOT NULL,
    PRIMARY KEY (UT_IDX),
    FOREIGN KEY (USER_ID) REFERENCES USER_INFO (USER_ID)
    ON DELETE CASCADE
);


CREATE TABLE TRAINER_IMAGE(
    TI_IDX INT(8) AUTO_INCREMENT,
    UT_IDX INT(8) NOT NULL,
    TI_PATH VARCHAR(1000) NOT NULL,
    PRIMARY KEY (TI_IDX),
    FOREIGN KEY (UT_IDX) REFERENCES TRAINER_DETAILS(UT_IDX)
    ON DELETE CASCADE
);

CREATE TABLE TRAINER_CERT(
    TC_IDX INT(8) AUTO_INCREMENT,
    UT_IDX INT(8) NOT NULL,
    TC_NAME VARCHAR(300) NOT NULL,
    PRIMARY KEY (TC_IDX),
    FOREIGN KEY (UT_IDX) REFERENCES TRAINER_DETAILS(UT_IDX)
    ON DELETE CASCADE
);


CREATE TABLE TRAINER_CAREER(
    TCAR_IDX INT(8) AUTO_INCREMENT,
    UT_IDX INT(8) NOT NULL,
    TCAR_CONTENT VARCHAR(300) NOT NULL,
    PRIMARY KEY (TCAR_IDX),
    FOREIGN KEY (UT_IDX) REFERENCES TRAINER_DETAILS(UT_IDX)
    ON DELETE CASCADE
);

CREATE TABLE PRODUCT(
    TP_IDX INT(8) AUTO_INCREMENT,
    UT_IDX INT(8) NOT NULL,
    TP_TIMES INT(3) NOT NULL,
    TP_PRICE INT(7) NOT NULL,
    TP_TYPE INT(1) NOT NULL,
    TP_TITLE VARCHAR(300) NOT NULL,
    TP_PERIOD INT(3) NOT NULL,
    PRIMARY KEY (TP_IDX),
    FOREIGN KEY (UT_IDX) REFERENCES TRAINER_DETAILS(UT_IDX)
    ON DELETE CASCADE
);

COMMIT;

INSERT INTO TRAINER_CAREER
VALUES(NULL, 'wkdgyfla97', '전국체전 입선');
INSERT INTO TRAINER_CAREER
VALUES(NULL, 'hye8997', '줘패기대회 입선');
INSERT INTO TRAINER_CAREER
VALUES(NULL, 'humble', '입털기 대회 우승');
INSERT INTO TRAINER_CAREER
VALUES(NULL, 'ujeong', '귀여움 대회 아차상');
INSERT INTO TRAINER_CAREER
VALUES(NULL, 'test96', '가리봉 싸움짱');


INSERT INTO PRODUCT
VALUES(NULL, 'wkdgyfla97', 24, 600000, 0, '바디프로필 3개월 패키지', 6);
INSERT INTO PRODUCT
VALUES(NULL, 'hye8997', 10, 200000, 0, '생활근력 만들기', 5);
INSERT INTO PRODUCT
VALUES(NULL, 'humble', 20, 400000, 1, '입근육을 만들자', 6);
INSERT INTO PRODUCT
VALUES(NULL, 'ujeong', 1, 10000, 0, '귀여워지고싶나용', 6);
INSERT INTO PRODUCT
VALUES(NULL, 'test2', 1, 20000, 0, '뒤지게패자', 6);


COMMIT;


SELECT DISTINCT td.user_id, td.UT_IDX, ti.TI_PATH, td.UT_GYM, ui.user_name, td.UT_EXPERT_1, td.UT_EXPERT_2, td.UT_ADDRESS, tc.TC_NAME, ROUND(AVG(r.R_STAR), 1) AS AVG_R_STAR
FROM trainer_details td
JOIN product p ON td.user_id = p.user_id
JOIN trainer_career tca ON td.user_id = tca.user_id
JOIN trainer_cert tc ON td.user_id = tc.user_id
JOIN trainer_image ti ON td.user_id = ti.user_id
JOIN user_info ui ON td.user_id = ui.user_id
LEFT JOIN review r ON td.UT_IDX = r.UT_IDX
where ut_expert_1 = 2 or ut_expert_2 = 5
GROUP BY td.user_id
ORDER BY AVG_R_STAR DESC, td.UT_IDX DESC
LIMIT 0, 10;

ALTER TABLE PRODUCT
ADD FOREIGN KEY(UT_IDX)
REFERENCES TRAINER_DETAILS(UT_IDX)
ON DELETE CASCADE;

-- 트레이너 단위 별점 평균값
SELECT
    PRODUCT.TP_IDX, PRODUCT.TP_TITLE, PRODUCT.UT_IDX, COUNT(PAYMENT.TP_IDX), ROUND(AVG(REVIEW.R_STAR), 1)
FROM PRODUCT, PAYMENT, REVIEW
WHERE
    PRODUCT.TP_IDX = PAYMENT.TP_IDX
    AND
    PAYMENT.TID = REVIEW.TID
    AND
    UT_IDX = 11
GROUP BY PAYMENT.TP_IDX
ORDER BY PAYMENT.TP_IDX DESC;

INSERT INTO REVIEW(USER_ID, TID, R_CONTENT, R_STAR, R_C_DT)
VALUES('west5', 'T4d69e2b76b90283a2d4', 'testReview5', 5, CURRENT_TIMESTAMP);

SELECT DISTINCT td.user_id, td.UT_IDX, td.UT_PROFILE_IMG, ti.TI_PATH, td.UT_GYM, ui.user_name, td.UT_EXPERT_1, td.UT_EXPERT_2, td.UT_ADDRESS, tc.TC_NAME,
ROUND(AVG(R.R_STAR), 1) as R_AVG_STAR
FROM TRAINER_DETAILS TD
JOIN PRODUCT P ON TD.UT_IDX = P.UT_IDX
JOIN TRAINER_CAREER TCA ON TD.UT_IDX = TCA.UT_IDX
JOIN TRAINER_CERT TC ON TD.UT_IDX = TC.UT_IDX
JOIN TRAINER_IMAGE TI ON TD.UT_IDX = TI.UT_IDX
JOIN USER_INFO UI ON TD.USER_ID = UI.USER_ID
JOIN PAYMENT PA ON P.TP_IDX = PA.TP_IDX
JOIN REVIEW R ON PA.TID = R.TID
GROUP BY TD.USER_ID
ORDER BY R_AVG_STAR DESC, TD.UT_IDX DESC
LIMIT 5;

SELECT DISTINCT td.user_id, td.UT_IDX, td.UT_PROFILE_IMG, ti.TI_PATH, td.UT_GYM, ui.user_name, td.UT_EXPERT_1, td.UT_EXPERT_2, td.UT_ADDRESS, tc.TC_NAME,
COALESCE(ROUND(AVG(R.R_STAR), 1), 0) AS R_AVG_STAR
FROM TRAINER_DETAILS TD
JOIN PRODUCT P ON TD.UT_IDX = P.UT_IDX
JOIN TRAINER_CAREER TCA ON TD.UT_IDX = TCA.UT_IDX
JOIN TRAINER_CERT TC ON TD.UT_IDX = TC.UT_IDX
JOIN TRAINER_IMAGE TI ON TD.UT_IDX = TI.UT_IDX
JOIN USER_INFO UI ON TD.USER_ID = UI.USER_ID
LEFT JOIN PAYMENT PA ON P.TP_IDX = PA.TP_IDX
LEFT JOIN REVIEW R ON PA.TID = R.TID
GROUP BY TD.USER_ID
ORDER BY R_AVG_STAR DESC, TD.UT_IDX DESC
LIMIT 5;

SELECT T.UT_IDX, PR.TP_IDX, COALESCE(ROUND(AVG(R.R_STAR), 1),0) AS R_AVG_STAR
FROM TRAINER_DETAILS T, PRODUCT PR, PAYMENT P, REVIEW R
WHERE
T.UT_IDX = PR.UT_IDX
AND
PR.TP_IDX = P.TP_IDX
AND
P.TID = R.TID
AND
PR.TP_IDX = 10
GROUP BY PR.TP_IDX;


--T.UT_IDX = 12
