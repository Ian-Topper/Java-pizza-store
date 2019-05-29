package com.pizzastore.server.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.pizzastore.data.Message;
import com.pizzastore.data.User;

public class UserSql {

	private Statement statement;
	private ResultSet resultSet;
	private ResultSet rs2;
	private PreparedStatement preparedStatement;
	private Message message;
	private Connection connection;
	private User user;

	private final String SUCCESS = "S";
	private final String FAILURE = "F";

	private final int VIEW_USR_OP = 100;
	private final int INSERT_USR_OP = 110;
	private final int UPDATE_USR_OP = 120;
	private final int DELETE_USR_OP = 130;
	private final int VALIDATE_USR_OP = 140;

	public UserSql(Connection connection) {
		this.connection = connection;
	}

	public void setParams(Message message) {
		this.message = message;
		this.user = message.getUser();
	}
	
	public void validateLoginCredentials() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from User where user_name = " + "'" + user.getUserName() + "'" +  " and password = " + "'" + user.getPassword() + "'" + ";");  
		
		if (resultSet.next()) {
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("Invalid username and/or password");
		}
		message.setOpType(VALIDATE_USR_OP);
		
		rs2 = statement.executeQuery("select user_id from user where user_name =" + "'" + user.getUserName() + "'" +  ";"); 
		if (rs2.next()) {
			int x = rs2.getInt(1);
		message.setUserId(x);
		}
	}
	
	public void insertUser() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		rs2 = statement.executeQuery("SELECT MAX(user_id) from user;");
		if (rs2.next()) {
			int x = rs2.getInt(1);
			x+=1;
			user.setUserId(x);
		}
		
		resultSet = statement.executeQuery("select * from User where user_id = " + user.getUserId() +  ";");
		
		
		if (!resultSet.next()) {
			preparedStatement = connection.prepareStatement("insert into  user values (?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, user.getUserId());
			preparedStatement.setString(2, user.getRole());
			preparedStatement.setString(3, user.getUserName());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getName());
			preparedStatement.setString(6, user.getMailAddress());
			preparedStatement.setString(7, user.getEmailAddress());
			preparedStatement.setString(8, user.getPhoneNumber());
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID already exists in the database");
		}
		message.setOpType(INSERT_USR_OP);
	}

	public void viewUser() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from User where user_id = " + user.getUserId() + ";");

		if (resultSet.next()) {
			user.setUserId(resultSet.getInt(1));
			user.setRole(resultSet.getString(2));
			user.setUserName(resultSet.getString(3));
			user.setPassword(resultSet.getString(4));
			user.setName(resultSet.getString(5));
			user.setMailAddress(resultSet.getString(6));
			user.setEmailAddress(resultSet.getString(7));
			user.setPhoneNumber(resultSet.getString(8));
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(VIEW_USR_OP);
	}

	public void updateUser() throws SQLException {
		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from User Where user_id= " + user.getUserId() + ";");

		if (resultSet.next()) {
			preparedStatement = connection.prepareStatement(
					"update staff SET role = ?, user_name = ?, password = ?, name = ?, mail_address = ?, email_address = ?, phone_number =? WHERE user_id = ?");
			preparedStatement.setString(1, user.getRole());
			preparedStatement.setString(2, user.getUserName());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getName());
			preparedStatement.setString(5, user.getMailAddress());
			preparedStatement.setString(6, user.getEmailAddress());
			preparedStatement.setString(7, user.getPhoneNumber());
			preparedStatement.setInt(8, user.getUserId());
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(UPDATE_USR_OP);
	}

	public void deleteUser() throws SQLException {

		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from User Where user_id = " + user.getUserId() + ";");

		if (resultSet.next()) {
			preparedStatement = connection.prepareStatement("delete from User WHERE user_id = ?");
			preparedStatement.setLong(1, user.getUserId());
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(DELETE_USR_OP);
	}
}
