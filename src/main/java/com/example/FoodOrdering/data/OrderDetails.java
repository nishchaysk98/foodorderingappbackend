package com.example.FoodOrdering.data;

import java.util.List;

public class OrderDetails {
	private int foodId;
	private int orderId;
	private int orderDetailsId;
	private List<Integer> addOnQId;
	private int foodQuantity;

	
	
	
	public int getFoodId() {
		return foodId;
	}
	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getOrderDetailsId() {
		return orderDetailsId;
	}
	public void setOrderDetailsId(int orderDetailsId) {
		this.orderDetailsId = orderDetailsId;
	}
	
	public List<Integer> getAddOnQId() {
		return addOnQId;
	}
	public void setAddOnQId(List<Integer> addOnQId) {
		this.addOnQId = addOnQId;
	}
	public int getFoodQuantity() {
		return foodQuantity;
	}
	public void setFoodQuantity(int foodQuantity) {
		this.foodQuantity = foodQuantity;
	}
	@Override
	public String toString() {
		return "OrderDetails [foodId=" + foodId + ", orderId=" + orderId + ", orderDetailsId=" + orderDetailsId
				+ ", addOnQId=" + addOnQId + ", foodQuantity=" + foodQuantity + "]";
	}
	
}
