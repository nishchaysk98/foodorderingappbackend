package com.example.FoodOrdering.queries;

public class DeliveryBoyQueries {
	public static final String VALIDATE_DELIVERY_BOY="SELECT DId, DName, DUserName, DPassword, DEmail, ST_X(DGeoLocation) as DLat, ST_Y(DGeoLocation) as DLong"
			+ "FROM delivery_boy WHERE DUserName=? and DPassword=?;";
	
	public static final String ADD_DELIVERY_BOY="INSERT INTO delivery_boy( DName, DUserName, DPassword, DEmail)"
			+ "SELECT * from ( SELECT  ? as DName, ? as DUserName, ? as DPassword, ? as DEmail) "
			+ "as temp where not exists (SELECT DUserName  FROM delivery_boy WHERE DUserName=?) LIMIT 1;";
	
	public static final String UPDATE_DELIVERY_BOY_LOCATION="UPDATE delivery_boy "
			+ "SET DGeoLocation=GeomFromText('POINT([LAT] [LONG])') WHERE DId=?";
	
	public static final String GET_DELIVERY_BOY_FROM_DID="SELECT DId, DName, DUserName, DEmail, ST_X(DGeoLocation) as DLat, ST_Y(DGeoLocation) as DLong" 
			+ "FROM delivery_boy WHERE DId=?;";
	
	public static final String ADD_DELIVERY_BOY_TOKEN="UPDATE delivery_boy SET token=? WHERE DId=?";
	
	
}
