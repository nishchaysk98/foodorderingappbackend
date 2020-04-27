package com.example.FoodOrdering.data;

import java.util.List;

public class OrderHistory {
	private User user;
	private List<Food> foods;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Food> getFoods() {
		return foods;
	}
	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}
	
	
}
