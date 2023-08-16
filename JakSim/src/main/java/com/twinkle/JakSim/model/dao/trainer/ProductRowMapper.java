package com.twinkle.JakSim.model.dao.trainer;

import com.twinkle.JakSim.model.dto.trainer.ProductDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<ProductDto>{

    @Override
    public ProductDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductDto productDto = new ProductDto();

        productDto.setPtId(rs.getInt("TP_IDX"));
        productDto.setTrainerId(rs.getInt("UT_IDX"));
        productDto.setPtTimes(rs.getInt("TP_TIMES"));
        productDto.setPtPrice(rs.getInt("TP_PRICE"));
        productDto.setPtType(rs.getInt("TP_TYPE"));
        productDto.setPtTitle(rs.getString("TP_TITLE"));
        productDto.setPtPeriod(rs.getInt("TP_PERIOD"));

        return productDto;
    }
}
