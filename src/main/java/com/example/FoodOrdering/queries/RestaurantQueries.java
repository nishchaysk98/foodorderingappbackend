package com.example.FoodOrdering.queries;

public class RestaurantQueries {
	public static final String GET_RESTAURANTS="SELECT RId, RName, REmail, RLocation, ST_X(RGeoLocation) as RLat, ST_Y(RGeoLocation) as RLong,RPicture, RIsOpen, RRating"
			+ " FROM restaurant ";
	
	public static final String ADD_RESTAURANT = "INSERT INTO restaurant( RName, RUserName, RPassword, REmail, RLocation, RPicture) "
			+ "SELECT * from ( SELECT  ? as RName, ? as RUserName, ? as RPassword, ? as REmail, ? as RLocation, ? as RPicture) "
			+ "as temp where not exists (SELECT RUserName  FROM restaurant WHERE RUserName=?) LIMIT 1;";
	
	public static final String VALIDATE_RESTAURANT = "SELECT RId, RName, REmail, RLocation, ST_X(RGeoLocation) as RLat, ST_Y(RGeoLocation) as RLong,RPicture, RIsOpen, RRating"
			+ " FROM restaurant where RUserName = ? and RPassword = ? ";
	
	public static final String ADD_FOOD = "INSERT INTO food( RId, FName, FPrice, FDescription, FPicture)"
			+ " VALUES (?,?,?,?,?)";
	
	public static final String ADD_ADDON_QSN_DETAILS="INSERT INTO add_ons_qsns(FId, AddOnQuestion, AddOnPrice, isExclusive) "
			+ "VALUES (?,?,?,?)";
	
	public static final String SEARCH_QUERY_IN_RESTAURANTS="SELECT RId, RName, REmail, RLocation, ST_X(RGeoLocation) as RLat, ST_Y(RGeoLocation) as RLong,RPicture, RIsOpen, RRating"
			+ " FROM restaurant where RName like ? or RLocation like ?";
	
	public static final String GET_OID_UID_FROM_RID="SELECT * from user_food_order where RId=? and OAccepted=1";
	
	public static final String GET_ORDER_DETAILS_FROM_OID="SELECT * from order_details where OID=?";
	
	public static final String GET_ADD_ON_QSN_ID_FROM_ODID="SELECT AQId from add_ons_ans where ODId=?";
	
	public static final String GET_ADD_ONS_FROM_AQID="SELECT * from add_ons_qsns where AQId=?";

	public static final String ADD_RESTAURANT_TOKEN="INSERT INTO restaurant_token(RId, token) VALUES (?,?)";

	public static final String GET_TOKEN_BY_RID="select token from restaurant_token where RId=? ";
}
