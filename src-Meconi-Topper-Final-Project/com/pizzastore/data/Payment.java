package com.pizzastore.data;

import java.io.Serializable;
import java.time.LocalDate;

public class Payment implements Serializable{
	
	private int paymentId;
	private String ccName;
	private String ccNumber;
	private String ccExp;	
	private Order order;
	private int orderId;
	public Payment(int paymentId, String ccName, String ccNumber, String ccExp, int orderId) {
		super();
		this.paymentId = paymentId;
		this.ccName = ccName;
		this.ccNumber = ccNumber;
		this.ccExp = ccExp;
		this.orderId = orderId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getCcName() {
		return ccName;
	}

	public void setCcName(String ccName) {
		this.ccName = ccName;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public String getCcExp() {
		return ccExp;
	}

	public void setCcExp(String ccExpDate) {
		this.ccExp = ccExpDate;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	



}
	
	