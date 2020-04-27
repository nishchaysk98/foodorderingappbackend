package com.example.FoodOrdering.data;

public class DeliveryBoy {
	private int Id;
	private String Name;
	private String Username;
	private String Password;
	private String Email;
	private double GeoLocationX;
	private double GeoLocationY;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public double getGeoLocationX() {
		return GeoLocationX;
	}
	public void setGeoLocationX(double geoLocationX) {
		GeoLocationX = geoLocationX;
	}
	public double getGeoLocationY() {
		return GeoLocationY;
	}
	public void setGeoLocationY(double geoLocationY) {
		GeoLocationY = geoLocationY;
	}
	@Override
	public String toString() {
		return "DeliveryBoy [Id=" + Id + ", Name=" + Name + ", Username=" + Username + ", Password=" + Password
				+ ", Email=" + Email + ", GeoLocationX=" + GeoLocationX + ", GeoLocationY=" + GeoLocationY + "]";
	}
	
}
