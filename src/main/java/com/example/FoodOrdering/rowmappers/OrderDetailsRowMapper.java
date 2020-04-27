package com.example.FoodOrdering.rowmappers;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.FoodOrdering.data.OrderDetails;
public class OrderDetailsRowMapper implements RowMapper<OrderDetails>{

	@Override
	public OrderDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderDetails orderDetails=new OrderDetails();
		
		orderDetails.setOrderDetailsId(rs.getInt("ODId"));
		orderDetails.setFoodId(rs.getInt("FId"));
		orderDetails.setOrderId(rs.getInt("OId"));
		orderDetails.setFoodQuantity(rs.getInt("FQuantity"));
		
		return orderDetails;
	}

}
