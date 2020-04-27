package com.example.FoodOrdering.controllers;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.FoodOrdering.data.AddOnQsns;
import com.example.FoodOrdering.data.Food;
import com.example.FoodOrdering.data.Restaurant;
import com.example.FoodOrdering.data.User;
import com.example.FoodOrdering.data.UserFoodOrder;
import com.example.FoodOrdering.data.OrderDetails;
import com.example.FoodOrdering.data.OrderHistory;
import com.example.FoodOrdering.queries.FoodQueries;
import com.example.FoodOrdering.queries.RestaurantQueries;
import com.example.FoodOrdering.queries.UserQueries;
import com.example.FoodOrdering.rowmappers.FoodRowMapper;
import com.example.FoodOrdering.rowmappers.OrderDetailsRowMapper;
import com.example.FoodOrdering.rowmappers.RestaurantRowMapper;
import com.example.FoodOrdering.rowmappers.UserFoodOrderRowMapper;
import com.example.FoodOrdering.rowmappers.UserRowMapper;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

@RestController
public class RestaurantController {
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	@GetMapping("/restaurant/orderhistory/{rId}")
	public Object getRestaurantOrderHistory(@PathVariable("rId") Integer rId) {
		Map<String,Object> response=new HashMap<String, Object>();
		List<UserFoodOrder> orders=jdbcTemplate.query(RestaurantQueries.GET_OID_UID_FROM_RID,new Object[] {
				rId
		}, new UserFoodOrderRowMapper());
	//	response.put("UserFoodOrder", orders);
		
		
		List<OrderHistory> histories=new ArrayList<OrderHistory>();
		orders.forEach((order)->{
			
			List<OrderDetails> orderDetails=jdbcTemplate.query(RestaurantQueries.GET_ORDER_DETAILS_FROM_OID,new Object[] {
					order.getId()
			},new OrderDetailsRowMapper());
			OrderHistory orderHistory=new OrderHistory();
			User user=jdbcTemplate.query(UserQueries.GET_USER_FROM_UID,new Object[] {
					order.getUserId()
			},new UserRowMapper()).get(0);
			List<Food> foods=new ArrayList<Food>();
			orderDetails.forEach((details)->{
				Food food=jdbcTemplate.queryForObject(FoodQueries.GET_FOOD_BY_FID, new Object[] {details.getFoodId()},new FoodRowMapper());
				food.setQuantity(details.getFoodQuantity());
				HashMap<Boolean, List<AddOnQsns>> addOnQsnsDetails=new HashMap<Boolean,List<AddOnQsns>>();
				System.out.println("");
				jdbcTemplate.query(FoodQueries.GET_ADD_ONS_QSNS_OF_FOOD_FROM_AQID,new Object[] {
					details.getOrderDetailsId()
					
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
				foods.add(food);
				System.out.println("Food="+food);
				
				
			});
			
			orderHistory.setUser(user);
			orderHistory.setFoods(foods);
			histories.add(orderHistory);
		});
		
		response.put("Order History",histories);
		return response;
	}
	
	
	@GetMapping("/restaurant/find/{query}")
	public List<Restaurant> getSearchResults(@PathVariable("query") String query){
		
		List<Restaurant> list= jdbcTemplate.query(RestaurantQueries.SEARCH_QUERY_IN_RESTAURANTS, new Object[] {
				"%"+query+"%","%"+query+"%"
		}, new RestaurantRowMapper());
		//Do some logic for finding nearest restaurants for user
		return list;
	}
	
	@PostMapping("/restaurant/signup")
	public int requestSignup(@RequestBody Restaurant restaurant) {
		System.out.println(restaurant);
		try {
			return jdbcTemplate.update(RestaurantQueries.ADD_RESTAURANT,new Object[] {
					restaurant.getName(), restaurant.getUsername(),restaurant.getPassword(),restaurant.getEmail(),
					restaurant.getLocation(), restaurant.getPicture(), restaurant.getUsername()
			});
		}catch(DataIntegrityViolationException e) {
			System.out.println(e);
			return -1;
		}
	}
	@PostMapping("/restaurant/login")
	public Restaurant requestLogin(@RequestBody Restaurant restaurant) {
		try {
			restaurant=jdbcTemplate.query(RestaurantQueries.VALIDATE_RESTAURANT, new Object[] {
				restaurant.getUsername(), restaurant.getPassword()
		}, new RestaurantRowMapper()).get(0);
			
			return restaurant;
//			String token = FirebaseAuth.getInstance().createCustomTokenAsync(String.valueOf(restaurant.getId())).get();
//			System.out.println(token);

//			if(jdbcTemplate.update(RestaurantQueries.ADD_RESTAURANT_TOKEN,new Object[]{restaurant.getId(),token})==1)
//			return token;
//			else
//			return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			return e.toString();
			return null; 
		}
		// return jdbcTemplate.query(RestaurantQueries.VALIDATE_RESTAURANT, new Object[] {
		// 		restaurant.getUsername(), restaurant.getPassword()
		// }, new RestaurantRowMapper());		
	}
	
	
	@PutMapping("/restaurant/addfood")
	public int addFood(@RequestBody Food food) {
		try {
			KeyHolder holder=new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps=con.prepareStatement(RestaurantQueries.ADD_FOOD, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, food.getRid());
					ps.setString(2,food.getName());
					ps.setDouble(3, food.getPrice());
					ps.setString(4, food.getDescription());
					ps.setString(5, food.getPicture());
					
					return ps;
				}
			},holder );
			int fId=holder.getKey().intValue();
			food.setId(fId);
			System.out.println(fId);
			food.getAddOnQsnsDetails().forEach((isExclusive,list)->{
				list.forEach((detail)->{
					jdbcTemplate.update(RestaurantQueries.ADD_ADDON_QSN_DETAILS, 
							food.getId(),detail.getQuestion(), detail.getAddOnPrice(),isExclusive);
				});
			});
			
			return 1;
		}catch(Exception e) {
			return 0;
			/*
			 * {
        "rid": 1,
        "name": "Kharabath",
        "description": "Sooji dish",
        "addOnQsnsDetails": {},
        "picture": "Kharabath",
        "price": 30.0
	
}
			 */
		}
	}
	
}
