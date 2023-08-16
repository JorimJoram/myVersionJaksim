package com.twinkle.JakSim.model.dao.account;

import com.twinkle.JakSim.model.dto.account.UserImage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserImageMapper implements RowMapper<UserImage> {
    @Override
    public UserImage mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserImage image = new UserImage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        image.setPath(rs.getString("UI_PATH"));
        image.setUser_id(rs.getString("USER_ID"));
        image.setDt(LocalDateTime.parse(rs.getString("UI_DT"), formatter));
        image.setId(rs.getInt("UI_ID"));

        return image;
    }
}
