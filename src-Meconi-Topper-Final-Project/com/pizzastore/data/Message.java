package com.pizzastore.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {
	private User user;
	private Order order;
	private Payment payment;
	private ItemList itemList;
	private ItemDetail itemDetail;
	private int itemId;
	private List<Order> orderList;
	private List<ItemDetail> itemDetailList;
	private List<ItemList> itemListList;
	private String ccNumber;
	private String ccName;
	private String ccExp;
	private int paymentId;
	int opType;
	private String opStatus;
	private String errorMsg;
	private int orderId;
	private int quantity;
	private double price;
	private String status;
	private int userId;
	private String pizzaItems;
	private String drinksItems;
	private String sidesItems;
	private Double grandTotal;
	private Double taxes;
	private String orderItems;
	private String orderStatus;
	private int itemListId;
	private String item;
	
	public Message(String item, int opType) {
		super();
		this.opType = opType;
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public Message(int opType, int orderId, int itemId, int itemListId ) {
		super();
		this.itemId = itemId;
		this.opType = opType;
		this.orderId = orderId;
		this.itemListId = itemListId;
	}

	public int getItemListId() {
		return itemListId;
	}

	public void setItemListId(int itemListId) {
		this.itemListId = itemListId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItem(int itemId) {
		this.itemId = itemId;
	}

	public Message(int opType, int paymentId, Payment payment) {
		super();
		this.payment = payment;
		this.paymentId = paymentId;
		this.opType = opType;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	public Double getTaxes() {
		return taxes;
	}
	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}
	public String getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(String orderItems) {
		this.orderItems = orderItems;
	}
	public String getDrinksItems() {
		return drinksItems;
	}
	public void setDrinksItems(String drinksItems) {
		this.drinksItems = drinksItems;
	}
	public String getSidesItems() {
		return sidesItems;
	}
	public void setSidesItems(String sidesItems) {
		this.sidesItems = sidesItems;
	}
	public void setPizzaItems(String pizzaItems) {
		this.pizzaItems = pizzaItems;
	}
	public String getPizzaItems() {
		return pizzaItems;
	}
	public void setAllItems(String pizzaItems) {
		this.pizzaItems = pizzaItems;
	}
	public Message(int orderId,int opType ) {
		super();
		this.orderId = orderId;
		this.opType = opType;
		
	}
	public Message(int opType, String opStatus, int orderId) {
		super();
		this.opType = opType;
		this.opStatus = opStatus;
		this.orderId = orderId;
		
	}
	
	public Message(int opType, String opStatus, Order order) {
		super();
		this.opType = opType;
		this.opStatus = opStatus;
		this.order = order;
		
	}
	
	public Message(int opType, String allItems) {
		super();
		this.opType = opType;
		this.pizzaItems = allItems;
		
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public Message() {
		
	}
	
	public Message(int opType, User user) {
		this.opType = opType;
		this.user = user;
	}
	
	public Message(int opType, ItemDetail itemDetail) {
		this.opType = opType;
		this.itemDetail = itemDetail;
	}
	
	public Message(int opType) {
		this.opType = opType;
		
	}
	
	public Message(int opType, Order order) {
		this.opType = opType;
		this.order = order;
	}
	public Message(String status, int opType, int orderId) {
		super();
		this.opType = opType;
		this.status = orderStatus;
		this.orderId = orderId;

	}
	public Message(int opType, String ccNumber, String ccName, String ccExp, int paymentId, int orderId) {
		super();
		this.opType = opType;
		this.ccNumber = ccNumber;
		this.ccName = ccName;
		this.ccExp = ccExp;
		this.paymentId = paymentId;
		this.orderId = orderId;
	}

	public String getCcNumber() {
		return ccNumber;
	}
	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}
	public String getCcName() {
		return ccName;
	}
	public void setCcName(String ccName) {
		this.ccName = ccName;
	}
	public String getCcExp() {
		return ccExp;
	}
	public void setCcExp(String ccExp) {
		this.ccExp = ccExp;
	}
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public ItemList getItemList() {
		return itemList;
	}

	public void setItemList(ItemList itemList) {
		this.itemList = itemList;
	}

	public ItemDetail getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(ItemDetail itemDetail) {
		this.itemDetail = itemDetail;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public List<ItemDetail> getItemDetailList() {
		return itemDetailList;
	}

	public void setItemDetailList(List<ItemDetail> itemDetailList) {
		this.itemDetailList = itemDetailList;
	}

	public List<ItemList> getItemListList() {
		return itemListList;
	}

	public void setItemListList(List<ItemList> itemListList) {
		this.itemListList = itemListList;
	}

	public int getOpType() {
		return opType;
	}

	public void setOpType(int opType) {
		this.opType = opType;
	}

	public String getOpStatus() {
		return opStatus;
	}

	public void setOpStatus(String opStatus) {
		this.opStatus = opStatus;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}

