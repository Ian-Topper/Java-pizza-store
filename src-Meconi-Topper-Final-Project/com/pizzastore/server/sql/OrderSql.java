package com.pizzastore.server.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.pizzastore.data.Message;
import com.pizzastore.data.Order;
import com.pizzastore.data.User;

public class OrderSql {
	private Statement statement;
	private ResultSet resultSet;
	private ResultSet rs2;
	private PreparedStatement preparedStatement;
	private Message message;
	private Connection connection;
	private Order order;
	private User user;
	private Date orderDate;

	private final String SUCCESS = "S";
	private final String FAILURE = "F";
	private static List<String> orderIdList;
	private final int VIEW_ORDER_OP = 200;
	private final int INSERT_ORDER_OP = 210;
	private final int UPDATE_ORDER_OP = 220;
	private final int DELETE_ORDER_OP = 230;
	private final int VIEW_ORDER_ID_CLICK_OP = 205;
	private final int GET_UPDATE_ORDER_OP = 217;
	private final int GET_MAX_ID_OP = 215;
	
	public OrderSql(Connection connection) {
		this.connection = connection;
	}

	public void setParams(Message message) {
		this.message = message;
		this.order = message.getOrder();
	}
	public void insertOrder() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		int id = order.getOrderId();
		resultSet = statement.executeQuery("select * from `order` where order_id = " + id + ";");
		
	
		if (!resultSet.next()) {
			preparedStatement = connection.prepareStatement("INSERT INTO `iantopdb`.`order` (`order_id`, `price`, `order_status`, `User_user_id`, `taxes`, `grand_total`, `order_items`) values (?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, order.getOrderId());
			preparedStatement.setDouble(2,  order.getPrice());
			preparedStatement.setString(3, order.getOrderStatus());
			preparedStatement.setInt(4, order.getUserId());
			preparedStatement.setDouble(5, order.getTaxes());
			preparedStatement.setDouble(6, order.getGrandTotal());
			preparedStatement.setString(7, order.getOrderItems());
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID already exists in the database");
		}
		message.setOpType(INSERT_ORDER_OP);
	}
	
	public void viewOrder() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("SELECT * FROM iantopdb.order WHERE order_id = " + message.getOrderId() + ";");
		if (resultSet.next()) {
			String id = resultSet.getString("order_id");
			message.setOrderId(Integer.parseInt(id));
			String tot = resultSet.getString("grand_total");
			message.setGrandTotal(Double.parseDouble(tot));
			String pri = resultSet.getString("price");
			message.setPrice(Double.parseDouble(pri));
			message.setStatus(resultSet.getString("order_status"));
			String us = resultSet.getString("User_user_id");
			message.setUserId(Integer.parseInt(us));
			String tax = resultSet.getString("taxes");
			message.setTaxes(Double.parseDouble(tax));
			message.setOrderItems(resultSet.getString("order_items"));
			message.setOpStatus(SUCCESS);
			
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(VIEW_ORDER_OP);
	}

	public void updateOrder() throws SQLException {
		statement = connection.createStatement();
		resultSet = statement.executeQuery("SELECT 'order_id' FROM iantopdb.order WHERE order_id = "+ message.getOrderId());
		if (resultSet.next()) {
			preparedStatement = connection.prepareStatement("UPDATE `order` SET `order_status`='"+ message.getOpStatus() +"' WHERE `order_id` ='"+ message.getOrderId()+"';");	
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(UPDATE_ORDER_OP);
	}
	public void getMaxOrder()throws SQLException{
		
		statement = connection.createStatement();
		
		rs2 = statement.executeQuery("SELECT MAX(order_id) from iantopdb.order;");
		if (rs2.next()) {
			int orderId = rs2.getInt("MAX(order_id)");
			}
			else{
		}
	}
	public void orderIdUpdate() throws SQLException{
	
		String sendIt="";
		String idList;
		statement = connection.createStatement();
		resultSet = statement.executeQuery("SELECT order_id FROM iantopdb.order;");
		while(resultSet.next()){
		 idList = resultSet.getString("order_id");	
			sendIt = sendIt + idList+","; 
		}
		message.setStatus(sendIt);
		message.setOpType(VIEW_ORDER_ID_CLICK_OP);
		message.setOpStatus(SUCCESS);
	}
	public void getUpdateOrder()throws SQLException{

		statement = connection.createStatement();
		resultSet = statement.executeQuery("SELECT order_status FROM iantopdb.order WHERE order_id = "+message.getOrderId()+";");
		
		if (resultSet.next()) {
			
			String stat =resultSet.getString("order_status");
			message.setStatus(stat);
			message.setOpType(GET_UPDATE_ORDER_OP);
			message.setOpStatus(SUCCESS);
			}
			else{
				message.setOpStatus(FAILURE);
		}
		
		
	}
	public void deleteOrder() throws SQLException {

		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("SELECT * FROM iantopdb.order WHERE order_id = "+message.getOrderId()+";");
		
		if (resultSet.next()) {
			preparedStatement = connection.prepareStatement("DELETE FROM iantopdb.order WHERE order_id = "+message.getOrderId()+";");
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(DELETE_ORDER_OP);

	}
}
