package com.example.FoodOrdering.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.FoodOrdering.data.DeliveryBoy;

public class DeliverBoyRowMapper implements RowMapper<DeliveryBoy>{

	@Override
	public DeliveryBoy mapRow(ResultSet rs, int rowNum) throws SQLException {
		DeliveryBoy deliveryBoy=new DeliveryBoy();
		
		deliveryBoy.setId(rs.getInt("DId"));
		deliveryBoy.setName(rs.getString("DName"));
		deliveryBoy.setUsername(rs.getString("DUserName"));
		deliveryBoy.setPassword(rs.getString("DPassword")==null?"":rs.getString("DPassword"));
		deliveryBoy.setEmail(rs.getString("DEmail"));
		deliveryBoy.setGeoLocationX(rs.getDouble("DGeoLocation"));
		deliveryBoy.setGeoLocationY(rs.getDouble("DGeoLocation"));
		return deliveryBoy;
	}

}
