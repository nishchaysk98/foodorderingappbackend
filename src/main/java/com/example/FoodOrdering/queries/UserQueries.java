package com.example.FoodOrdering.queries;

public class UserQueries {
	//Login query for User
	public static final String VALIDATE_USER="SELECT UId, UName, UUserName, UPassword, UEmail, UPicture,ST_X(UGeoLocation) as ULat, ST_Y(UGeoLocation) as ULong"
			+ " FROM User where UUsername=? and UPassword=?";
	
	//Signup query for User
	public static final String ADD_USER="INSERT INTO User(UName, UUserName, UPassword, UEmail, UPicture) "
			+ "SELECT * from ( SELECT  ? as UName, ? as UUserName, ? as UPassword, ? as UEmail, ? as UPicture) "
			+ "as temp where not exists (SELECT UUserName  FROM User WHERE UUserName=?) LIMIT 1;";
	
	//Update query for updating location
	public static final String UPDATE_USER_LOCATION="UPDATE User SET UGeoLocation=GeomFromText('POINT([LAT] [LONG])') WHERE UId=?";
	
	public static final String GET_USER_FROM_UID="SELECT UId, UName, UUserName, UEmail, UPicture,ST_X(UGeoLocation) as ULat, ST_Y(UGeoLocation) as ULong"
			+ " FROM User where UId=?";

	public static final String ADD_USER_TOKEN="INSERT INTO user_tokens(UId, token) VALUES (?,?)";
	
}
