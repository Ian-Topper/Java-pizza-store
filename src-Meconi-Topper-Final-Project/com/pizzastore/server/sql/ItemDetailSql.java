package com.pizzastore.server.sql;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.pizzastore.data.ItemDetail;
import com.pizzastore.data.ItemList;
import com.pizzastore.data.Message;
import com.pizzastore.data.User;

public class ItemDetailSql implements Serializable{
	private Statement statement;
	private ResultSet resultSet;
	private ResultSet rs2;
	private ResultSet rs3;
	private ResultSet rs4;
	
	private PreparedStatement preparedStatement;
	private Message message;
	private Connection connection;
	private ItemDetail itemDetail;
	
	
	private final String SUCCESS = "S";
	private final String FAILURE = "F";
	private final int VIEW_ITEM_DETAIL_ID_OP = 505;
	private final int VIEW_ITEM_DETAIL_OP = 500;
	private final int INSERT_ITEM_DETAIL_OP = 510;
	private final int UPDATE_ITEM_DETAIL_OP = 520;
	private final int DELETE_ITEM_DETAIL_OP = 530;
	private final int UPDATE_LISTS_OP = 540;
	public ItemDetailSql(Connection connection) {
		this.connection = connection;
	}

	public void setParams(Message message) {
		this.message = message;
		this.itemDetail = message.getItemDetail();
	}
	
	public void insertItemDetail() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from ItemDetail where item_id = " + itemDetail.getItemId() + ";");

		if (!resultSet.next()) {
			preparedStatement = connection.prepareStatement("insert into  ItemDetail values (?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, itemDetail.getItemId());
			preparedStatement.setString(2, itemDetail.getName());
			preparedStatement.setString(3, itemDetail.getType());
			preparedStatement.setString(4, itemDetail.getSize());
			preparedStatement.setDouble(5, itemDetail.getUnitPrice());
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID already exists in the database");
		}
		message.setOpType(INSERT_ITEM_DETAIL_OP);
	}
	
	public void upDateLists() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		String pizzaAllInfo = "";
		String sidesAllInfo = "";
		String drinksAllInfo = "";
		rs2 = statement.executeQuery("select * from ItemDetail where type = \"Pizza\" ;"); 
		while (rs2.next()) {
		   String pat = rs2.getString("name") ;
		   String pat1 = rs2.getString("size") ; 
		   String pat2 = rs2.getString("unit_price");
		   pizzaAllInfo = pizzaAllInfo + pat + "," + pat1 + "," + pat2 + ",";
           message.setPizzaItems(pizzaAllInfo);
		}
		
        rs3 = statement.executeQuery("select * from ItemDetail where type = \"Drinks\" ;"); 
   		while (rs3.next()) {
   		   String xpat = rs3.getString("name") ;
   		   String xpat1 = rs3.getString("size") ; 
   		   String xpat2 = rs3.getString("unit_price");
   		   drinksAllInfo = drinksAllInfo + xpat + "," + xpat1 + "," + xpat2 + ",";
              message.setDrinksItems(drinksAllInfo);
        }
   		
   		rs4 = statement.executeQuery("select * from ItemDetail where type = \"Sides\" ;"); 
   		while (rs4.next()) {
   		   String zpat = rs4.getString("name") ;
   		   String zpat1 = rs4.getString("size") ; 
   		   String zpat2 = rs4.getString("unit_price");
   		   sidesAllInfo = sidesAllInfo + zpat + "," + zpat1 + "," + zpat2 + ",";
              message.setSidesItems(sidesAllInfo);
        }
		//JOptionPane.showMessageDialog(null, allInfo);
	}
	
	public void viewItemDetail() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from ItemDetail where item_id = " + "'" + itemDetail.getItemId()+ "'" +";");  
		if (resultSet.next()) {
			itemDetail.setItemId(resultSet.getInt(1));
			itemDetail.setName(resultSet.getString(2));
			itemDetail.setType(resultSet.getString(3));
			itemDetail.setSize(resultSet.getString(4));
			itemDetail.setUnitPrice(resultSet.getDouble(5));
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(VIEW_ITEM_DETAIL_OP);
	}

	public void updateItemDetail() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from ItemDetail Where item_id= " + itemDetail.getItemId() + ";");

		if (resultSet.next()) {
			preparedStatement = connection.prepareStatement(
					"update ItemDetail SET name = ?, type = ?, size = ?, unit_price = ? where item_id= " + "'" + itemDetail.getItemId() +"'" + ";" );

			preparedStatement.setString(1, itemDetail.getName());
			preparedStatement.setString(2, itemDetail.getType());
			preparedStatement.setString(3, itemDetail.getSize());
			preparedStatement.setDouble(4, itemDetail.getUnitPrice());
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(UPDATE_ITEM_DETAIL_OP);
	}

	public void deleteItemDetail() throws SQLException {

		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from ItemDetail Where item_id = " + itemDetail.getItemId() + ";");

		if (resultSet.next()) {
			preparedStatement = connection.prepareStatement("delete from ItemDetail WHERE item_id = ?");
			preparedStatement.setLong(1, itemDetail.getItemId());
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(DELETE_ITEM_DETAIL_OP);
	}
	public void viewItemDetailId() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query/////////may need quotes on item
		resultSet = statement.executeQuery("select item_id from iantopdb.itemdetail where name = '"+message.getItem()+"';");  
		if (resultSet.next()) {
			String id = resultSet.getString("item_id");
			message.setItemId(Integer.parseInt(id));
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(VIEW_ITEM_DETAIL_OP);
	}
}
