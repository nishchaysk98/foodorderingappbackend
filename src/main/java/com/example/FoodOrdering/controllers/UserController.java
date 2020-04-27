package com.example.FoodOrdering.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.FoodOrdering.data.Restaurant;
import com.example.FoodOrdering.data.*;
import com.example.FoodOrdering.queries.RestaurantQueries;
import com.example.FoodOrdering.queries.UserQueries;
import com.example.FoodOrdering.rowmappers.RestaurantRowMapper;
import com.example.FoodOrdering.rowmappers.UserRowMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@RestController
public class UserController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	NamedParameterJdbcTemplate np;

	@PostMapping("/user/login")
	public User requestLogin(@RequestBody User user) {
		System.out.println(user);
		
				try {
					user=jdbcTemplate.query(UserQueries.VALIDATE_USER, new Object[] { user.getUsername(), user.getPassword() },new UserRowMapper()).get(0);
//					String token = FirebaseAuth.getInstance().createCustomTokenAsync(String.valueOf(user.getId())).get();
//					System.out.println(token);
	
					return user;
//					if(jdbcTemplate.update(UserQueries.ADD_USER_TOKEN,new Object[]{user.getId(),token})==1)
//					return token;
//					else
//					return "";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					return e.toString();
					return null;
				}
	}

	@PostMapping("/user/signup")
	public int requestSignup(@RequestBody User user) {
		System.out.println(user);
		try {
			return jdbcTemplate.update(UserQueries.ADD_USER, new Object[] { user.getName(), user.getUsername(),
					user.getPassword(), user.getEmail(), user.getPicture(), user.getUsername() });
		} catch (DataIntegrityViolationException e) {
			System.out.println(e);
			return -1;
		}
	}

	@PutMapping("/user/updatelocation")
	public void updateUserLocation(@RequestBody User user) {
		try {
			String sql = UserQueries.UPDATE_USER_LOCATION;
			sql = org.apache.commons.lang3.StringUtils.replace(sql, "[LAT]", "" + user.getGeoLocationX());
			sql = org.apache.commons.lang3.StringUtils.replace(sql, "[LONG]", "" + user.getGeoLocationY());
			System.out.println(jdbcTemplate.update(sql, new Object[] { user.getId() }));
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping("/user/orderhistory/{uId}")
	public void getOrderHistory(@PathVariable("uId") int uId) {

	}

	@GetMapping("/user/restaurants/{uId}")
	public List<Restaurant> getRestaurant(@PathVariable("uId") int uId) {
		// Do some logic for getting nearest restaurants
		System.out.println("Hi der");
		List<Restaurant> list = jdbcTemplate.query(RestaurantQueries.GET_RESTAURANTS, new RestaurantRowMapper());
		list.forEach((item)->{
			List<String> tokens=jdbcTemplate.queryForList(RestaurantQueries.GET_TOKEN_BY_RID, new Object[]{item.getId()},String.class);
			item.setTokens(tokens);
		});
		System.out.println(list);
		return list;
	}

// 	@GetMapping("/tokens")
// 	public String dummy() {
// 		try {

// 			String token = FirebaseAuth.getInstance().createCustomTokenAsync("101").get();
// 			System.out.println(token);

// 			return token;
// 		} catch (Exception e) {
// 			// TODO Auto-generated catch block
// 			e.printStackTrace();
// 			return e.toString();
// 		}
// 	}


	
}
