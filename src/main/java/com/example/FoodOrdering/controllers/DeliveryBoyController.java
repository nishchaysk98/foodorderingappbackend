package com.example.FoodOrdering.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.FoodOrdering.data.DeliveryBoy;
import com.example.FoodOrdering.data.User;
import com.example.FoodOrdering.queries.DeliveryBoyQueries;
import com.example.FoodOrdering.queries.UserQueries;
import com.example.FoodOrdering.rowmappers.DeliverBoyRowMapper;
import com.example.FoodOrdering.rowmappers.UserRowMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.TopicManagementResponse;

@RestController
public class DeliveryBoyController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@PostMapping("/deliveryboy/login")
	public String requestLogin(@RequestBody DeliveryBoy deliveryBoy) {
		
		try {
			deliveryBoy=jdbcTemplate.query(DeliveryBoyQueries.VALIDATE_DELIVERY_BOY, 
					new Object[] { 
							deliveryBoy.getUsername(), 
							deliveryBoy.getPassword() 
					},
					new DeliverBoyRowMapper()).get(0);
			String token = FirebaseAuth.getInstance().createCustomTokenAsync(String.valueOf(deliveryBoy.getId())).get();
			System.out.println(token);

			if(jdbcTemplate.update(DeliveryBoyQueries.ADD_DELIVERY_BOY_TOKEN,new Object[]{token,deliveryBoy.getId()})==1)
			return token;
			else
			return "";
		}catch(Exception e) {
			return e.toString();
		}
	}
	
	@PostMapping("/deliveryboy/signup")
	public int requestSignup(@RequestBody DeliveryBoy deliveryBoy) {
		System.out.println(deliveryBoy);
		try {
			return jdbcTemplate.update(UserQueries.ADD_USER, new Object[] { deliveryBoy.getName(), deliveryBoy.getUsername(),
					deliveryBoy.getPassword(), deliveryBoy.getEmail(), deliveryBoy.getUsername() });
		} catch (DataIntegrityViolationException e) {
			System.out.println(e);
			return -1;
		}
	}
	
	@PutMapping("/deliveryboy/updatelocation")
	public void updateUserLocation(@RequestBody DeliveryBoy deliveryBoy) {
		try {

			String sql = DeliveryBoyQueries.UPDATE_DELIVERY_BOY_LOCATION;
			sql = org.apache.commons.lang3.StringUtils.replace(sql, "[LAT]", "" + deliveryBoy.getGeoLocationX());
			sql = org.apache.commons.lang3.StringUtils.replace(sql, "[LONG]", "" + deliveryBoy.getGeoLocationY());
			System.out.println(jdbcTemplate.update(sql, new Object[] { deliveryBoy.getId() }));
		} catch (Exception e) {
			throw e;
		}
	}

}
