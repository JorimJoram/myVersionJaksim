DROP TABLE RESERVATION;

CREATE TABLE RESERVATION(
    R_IDX INT(8) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    USER_ID VARCHAR(30) NOT NULL,
    T_IDX INT(8) NOT NULL,
    P_IDX INT(8) NOT NULL,
    FOREIGN KEY (T_IDX) REFERENCES TIMETABLE(T_IDX) ON DELETE CASCADE,
    FOREIGN KEY (USER_ID) REFERENCES USER_INFO(USER_ID) ON DELETE CASCADE,
    FOREIGN KEY (P_IDX) REFERENCES PAYMENT(P_IDX) ON DELETE CASCADE
);

COMMIT;



