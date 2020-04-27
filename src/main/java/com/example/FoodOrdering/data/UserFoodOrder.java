package com.example.FoodOrdering.data;

public class UserFoodOrder {
	private int Id;
	private boolean Purchased;
	private int UserId;
	private boolean accepted;
	private int RestaurantId;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public boolean isPurchased() {
		return Purchased;
	}
	public void setPurchased(boolean purchased) {
		Purchased = purchased;
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public boolean isAccepted() {
		return accepted;
	}
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	public int getRestaurantId() {
		return RestaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		RestaurantId = restaurantId;
	}
	@Override
	public String toString() {
		return "UserFoodOrder [Id=" + Id + ", Purchased=" + Purchased + ", UserId=" + UserId + ", accepted=" + accepted
				+ ", RestaurantId=" + RestaurantId + "]";
	}
	
	
}
