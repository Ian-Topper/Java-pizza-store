package com.pizzastore.data;

import java.io.Serializable;

public class ItemDetail implements Serializable{
	private int itemId;
	private String name;
	private String type;
	private String size;
	private double unitPrice;
	
	public ItemDetail(int itemId, String name, String type, String size, double unitPrice) {
		this.itemId = itemId;
		this.name = name;
		this.type = type;
		this.size = size;
		this.unitPrice = unitPrice;
	}
	
	public ItemDetail(String name, String size, double unitPrice) {
		this.name = name;
		this.size = size;
		this.unitPrice = unitPrice;
	}

	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}
