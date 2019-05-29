package com.pizzastore.server.sql;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pizzastore.data.ItemList;
import com.pizzastore.data.Message;

public class ItemListSql implements Serializable{
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private Message message;
	private Connection connection;
	private ItemList itemList;
	
	private final String SUCCESS = "S";
	private final String FAILURE = "F";

	private final int VIEW_ITEM_LIST_OP = 400;
	private final int INSERT_ITEM_LIST_OP = 410;
	private final int UPDATE_ITEM_LIST_OP = 420;
	private final int DELETE_ITEM_LIST_OP = 430;

	public ItemListSql(Connection connection) {
		this.connection = connection;
	}

	public void setParams(Message message) {
		this.message = message;
		this.itemList = message.getItemList();
	}
	
	public void insertItemList() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from iantopdb.itemlist where item_list_id = " + message.getItemListId() + ";");
		if (!resultSet.next()) {
			preparedStatement = connection.prepareStatement("INSERT INTO `iantopdb`.`itemlist` (`item_list_id`, `ItemDetail_item_id`, `Order_order_id` ) values (?, ?, ?)");
			preparedStatement.setInt(1, message.getItemListId());
			preparedStatement.setInt(2, message.getItemId());
			preparedStatement.setInt(3, message.getOrderId());
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		} else {

			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID already exists in the database");
		}
		message.setOpType(INSERT_ITEM_LIST_OP);
	}

	public void viewItemList() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from ItemList where item_list_id = " + itemList.getItemListId() + ";");

		if (resultSet.next()) {
			itemList.setItemListId(resultSet.getInt(1));
			itemList.setItemDetailItemId(resultSet.getInt(2));
			itemList.setOrderOrderId(resultSet.getInt(3));
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(VIEW_ITEM_LIST_OP);
	}

	public void updateItemList() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();

			preparedStatement = connection.prepareStatement(
					"update itemlist SET Order_order_id = "+message.getOrderId()+" WHERE Order_order_id = 0");
			
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);

		message.setOpType(UPDATE_ITEM_LIST_OP);
	}

	public void deleteItemList() throws SQLException {

		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from ItemList Where item_list_id = " + itemList.getItemListId() + ";");

		if (resultSet.next()) {
			preparedStatement = connection.prepareStatement("delete from ItemList WHERE item_list_id = ?");
			preparedStatement.setInt(1, itemList.getItemListId());
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(DELETE_ITEM_LIST_OP);
	}
	public void viewItemListItems() throws SQLException {
		String idList = "";
		String sendIt = "";
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("SELECT itemdetail.name FROM itemlist INNER JOIN itemdetail ON itemlist.itemDetail_item_id = itemdetail.item_id WHERE (((itemlist.Order_order_id)= "+message.getOrderId()+")) ;");

		while(resultSet.next()){
			 idList = resultSet.getString("name");	
				sendIt = sendIt + idList+"\n"; 
			}
	message.setOrderItems(sendIt);

		message.setOpType(VIEW_ITEM_LIST_OP);
	}
}
