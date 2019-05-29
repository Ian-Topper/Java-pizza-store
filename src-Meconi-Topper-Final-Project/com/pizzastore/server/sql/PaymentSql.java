package com.pizzastore.server.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pizzastore.data.Message;
import com.pizzastore.data.Order;
import com.pizzastore.data.Payment;

public class PaymentSql {
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private Message message;
	private Connection connection;
	private Order order;
	private Payment payment;
	private Date paymentDate;
	private Date cceDate;
	
	private final String SUCCESS = "S";
	private final String FAILURE = "F";

	private final int VIEW_PAYMENT_OP = 300;
	private final int INSERT_PAYMENT_OP = 310;
	private final int UPDATE_PAYMENT_OP = 320;
	private final int DELETE_PAYMENT_OP = 330;

	public PaymentSql(Connection connection) {
		this.connection = connection;
	}

	public void setParams(Message message) {
		this.message = message;
		this.payment = message.getPayment();
		
	}
	
	public void insertPayment() throws SQLException {
		// statements allow to issue SQL queries to the database
		resultSet = null;
		int paymentId = payment.getOrderId();
		
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from `payment` where Order_order_id = "+paymentId+";");
		
		if (!resultSet.next()) {
			preparedStatement = connection.prepareStatement("INSERT INTO  `iantopdb`.`payment` (`payment_id`, `credit_card_number`, `credit_card_exp_date`, `Order_order_id`, `name_on_card`) values (?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, paymentId);
			preparedStatement.setString(2, payment.getCcNumber());
			preparedStatement.setString(3, payment.getCcExp());
			preparedStatement.setDouble(4, payment.getOrderId());
			preparedStatement.setString(5, payment.getCcName());

		
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		}

		message.setOpType(INSERT_PAYMENT_OP);
	}



	public void deletePayment() throws SQLException {

		// statements allow to issue SQL queries to the database
		statement = connection.createStatement();
		// resultSet gets the result of the SQL query
		resultSet = statement.executeQuery("select * from Payment Where payment_id = " + payment.getPaymentId() + ";");

		if (resultSet.next()) {
			preparedStatement = connection.prepareStatement("delete from Payment WHERE payment_id = ?");
			preparedStatement.setInt(1, payment.getPaymentId());
			preparedStatement.executeUpdate();
			message.setOpStatus(SUCCESS);
		} else {
			message.setOpStatus(FAILURE);
			message.setErrorMsg("ID value does not exists in the database");
		}
		message.setOpType(DELETE_PAYMENT_OP);
	}

}
