package com.example.FoodOrdering.queries;

public class FoodQueries {
	public static final String GET_FOOD_BY_RID="Select * from Food where RId = ?";
	
	public static final String GET_ADD_ONS_QSNS_OF_FOOD="select * from add_ons_qsns where FId=?";
	
	public static final String ADD_ORDER_DETAILS="INSERT INTO order_details(OId, FId, FQuantity) VALUES ("
			+ " ?, ?,?)";	
	
	public static final String ADD_ORDER="INSERT INTO user_food_order(OPurchased, UId, OAccepted, RId) VALUES (false,?,false,?)";
	
	public static final String ADD_ADD_ONS="INSERT INTO add_ons_ans(ODId, AQId) VALUES (?,?)";
	
	public static final String GET_FOOD_BY_FID="SELECT * FROM food where FId=?";
	
	public static final String GET_ADD_ONS_QSNS_OF_FOOD_FROM_AQID="select * from add_ons_qsns where AQId in (select AQId from add_ons_ans where ODId = ?) ";
}
