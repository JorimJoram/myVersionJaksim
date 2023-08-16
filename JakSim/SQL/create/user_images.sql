DROP TABLE USER_IMAGE;

CREATE TABLE USER_IMAGE(
    UI_ID INT(8) AUTO_INCREMENT PRIMARY KEY,
    USER_ID VARCHAR(30) NOT NULL,
    UI_PATH VARCHAR(300) NOT NULL,
    UI_DT DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(USER_ID) REFERENCES USER_INFO(USER_ID)
    ON DELETE CASCADE
);

COMMIT;