package com.pizzastore.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Order implements Serializable{
	
	private int orderId;
	private int quantity;
	private LocalDate orderDate;
	private double price;
	private double taxes;
	private String orderStatus;
	private User user;
	private List<ItemList> itemList;
	private Payment payment;
	private Date date;
	private String status;
	private int userId;
	private double grandTotal;
	private String orderItems;
	
	
	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(String orderItems) {
		this.orderItems = orderItems;
	}

	public Order(int orderId, double price, String orderStatus, int userId, double taxes, double grandTotal, String orderItems) {
		super();
		this.orderId = orderId;
		this.price = price;
		this.orderStatus = orderStatus;
		this.userId = userId;
		this.taxes = taxes;
		this.grandTotal = grandTotal;
		this.orderItems = orderItems;
	}
	
	public Order() {
		// TODO Auto-generated constructor stub
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate localDate) {
		this.orderDate = localDate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTaxes() {
		return taxes;
	}
	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<ItemList> getItemList() {
		return itemList;
	}
	public void setItemList(List<ItemList> itemList) {
		this.itemList = itemList;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	@Override
	public String toString() {
		return "\t"+"\t"+"\t"+"Order: "+"\n"+"\t"+"\t"+"\t"+"[Order Id: " + orderId + ", Sub Total: " + price + 
				", Taxes: " + taxes + ", User Id: " + userId + ", Grand Total: " + grandTotal + ", Order Items: " + orderItems.trim() +"]";
	}
	
}
