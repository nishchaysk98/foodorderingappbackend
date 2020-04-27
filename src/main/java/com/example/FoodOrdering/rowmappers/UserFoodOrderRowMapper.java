package com.example.FoodOrdering.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.FoodOrdering.data.UserFoodOrder;

public class UserFoodOrderRowMapper implements RowMapper<UserFoodOrder> {

	@Override
	public UserFoodOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserFoodOrder userFoodOrder=new UserFoodOrder();
		
		userFoodOrder.setId(rs.getInt("OId"));
		userFoodOrder.setPurchased(rs.getBoolean("OPurchased"));
		userFoodOrder.setUserId(rs.getInt("UId"));
		userFoodOrder.setAccepted(rs.getBoolean("OAccepted"));
		userFoodOrder.setRestaurantId(rs.getInt("RId"));
		
		
		return userFoodOrder;
	}
	

}
