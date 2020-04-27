package com.example.FoodOrdering.controllers;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.FoodOrdering.data.Food;
import com.example.FoodOrdering.data.OrderDetails;
import com.example.FoodOrdering.data.User;
import com.example.FoodOrdering.data.AddOnQsns;
import com.example.FoodOrdering.queries.FoodQueries;
import com.example.FoodOrdering.queries.RestaurantQueries;
import com.example.FoodOrdering.queries.UserQueries;
import com.example.FoodOrdering.rowmappers.FoodRowMapper;
import com.example.FoodOrdering.rowmappers.UserRowMapper;

@RestController
public class FoodController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@GetMapping("/food/listfoods/{rId}")
	public List<Food> getFoodsOfRestaurant(@PathVariable("rId") Integer rId){
		List<Food> restaurantFoodData=jdbcTemplate.query(FoodQueries.GET_FOOD_BY_RID, new Object[] {rId},new FoodRowMapper());
		restaurantFoodData.forEach(food->{
			HashMap<Boolean, List<AddOnQsns>> addOnQsnsDetails=new HashMap<Boolean,List<AddOnQsns>>();
			addOnQsnsDetails.put(true, new ArrayList<AddOnQsns>());
			addOnQsnsDetails.put(false, new ArrayList<AddOnQsns>());
			jdbcTemplate.query(FoodQueries.GET_ADD_ONS_QSNS_OF_FOOD,new Object[] {
				food.getId()
				},
				new RowMapper<AddOnQsns>() {
					@Override
					public AddOnQsns mapRow(ResultSet rs, int rowNum) throws SQLException {
						AddOnQsns addOnQsns=new AddOnQsns();
						addOnQsns.setId(rs.getInt("AQId"));
						addOnQsns.setQuestion(rs.getString("AddOnQuestion"));
						addOnQsns.setExclusive(rs.getBoolean("isExclusive"));
						addOnQsns.setAddOnPrice(rs.getFloat("AddOnPrice"));
						if(addOnQsnsDetails.containsKey(addOnQsns.isExclusive())) {
							addOnQsnsDetails.get(addOnQsns.isExclusive()).add(addOnQsns);
						}else {
							List<AddOnQsns> list=new ArrayList<AddOnQsns>();
							list.add(addOnQsns);
							addOnQsnsDetails.put(addOnQsns.isExclusive(), list);
						}
						return null;
					}
				}
			);
			
			food.setAddOnQsnsDetails(addOnQsnsDetails);
		});
		System.out.println(restaurantFoodData);
		return restaurantFoodData;
	}

	@PostMapping("/food/placeorder/{uId}/{rId}")
	public boolean requestToPlaceOrder(@RequestBody List<OrderDetails> orderDetails, @PathVariable("uId") int uId,@PathVariable("rId") int rId) {
		orderDetails.forEach((od)->{
			System.out.println(od.toString());
		});
		System.out.println("User Id="+uId);
		KeyHolder holder=new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps=con.prepareStatement(FoodQueries.ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, uId);
				ps.setInt(2,rId);
				return ps;
			}
		},holder );
		int OId=holder.getKey().intValue();
		
		orderDetails.forEach((od)->{
			od.setOrderId(OId);
			KeyHolder ODIdholder=new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps=con.prepareStatement(FoodQueries.ADD_ORDER_DETAILS, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1,OId);
					ps.setInt(2,od.getFoodId());					
					ps.setInt(3,od.getFoodQuantity());
					
					return ps;
				}
			},ODIdholder);
			
			int ODId=ODIdholder.getKey().intValue();
			od.setOrderDetailsId(ODId);
			od.getAddOnQId().forEach((AQId)->{
				jdbcTemplate.update(FoodQueries.ADD_ADD_ONS,new Object[] {
					ODId,AQId
				});
			});
		});
		
		/*
		 * 
		 * Do cloud messaging for sending notification to restaurant owner
		 * 
		 */
		//return prepareOrderPayloadForRestaurant(orderDetails,OId, uId);
		return true;
		
	}
	public Object prepareOrderPayloadForRestaurant(List<OrderDetails> orderDetails, int OId, int uId) {
		System.out.println(orderDetails);
		List<Food> foodData=new ArrayList<Food>();
		orderDetails.forEach((od)->{
			Food food=jdbcTemplate.queryForObject(FoodQueries.GET_FOOD_BY_FID, new Object[] {od.getFoodId()},new FoodRowMapper());
			food.setQuantity(od.getFoodQuantity());

			HashMap<Boolean, List<AddOnQsns>> addOnQsnsDetails=new HashMap<Boolean,List<AddOnQsns>>();
			System.out.println("");
			jdbcTemplate.query(FoodQueries.GET_ADD_ONS_QSNS_OF_FOOD_FROM_AQID,new Object[] {
				od.getOrderDetailsId()
				
				},
				new RowMapper<AddOnQsns>() {
					@Override
					public AddOnQsns mapRow(ResultSet rs, int rowNum) throws SQLException {
						AddOnQsns addOnQsns=new AddOnQsns();
						addOnQsns.setId(rs.getInt("AQId"));
						addOnQsns.setQuestion(rs.getString("AddOnQuestion"));
						addOnQsns.setExclusive(rs.getBoolean("isExclusive"));
						addOnQsns.setAddOnPrice(rs.getFloat("AddOnPrice"));
						if(addOnQsnsDetails.containsKey(addOnQsns.isExclusive())) {
							addOnQsnsDetails.get(addOnQsns.isExclusive()).add(addOnQsns);
						}else {
							List<AddOnQsns> list=new ArrayList<AddOnQsns>();
							list.add(addOnQsns);
							addOnQsnsDetails.put(addOnQsns.isExclusive(), list);
						}
						return null;
					}
				}
			);
			System.out.println(addOnQsnsDetails);
			
			food.setAddOnQsnsDetails(addOnQsnsDetails);
		
			
			foodData.add(food);
			
			
		});
		User user=jdbcTemplate.queryForObject(UserQueries.GET_USER_FROM_UID,new Object[] {uId},new UserRowMapper() );
		
		HashMap<String,Object> orderPayload=new HashMap<String, Object>();
		orderPayload.put("orderId",OId);
		orderPayload.put("orderData",foodData);
		orderPayload.put("userInfo", user);
		return orderPayload;
	}
}
