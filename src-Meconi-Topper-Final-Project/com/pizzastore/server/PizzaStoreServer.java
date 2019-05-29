package com.pizzastore.server;

import java.awt.*;

import javax.swing.*;

import com.pizzastore.data.Message;
import com.pizzastore.server.sql.ItemDetailSql;
import com.pizzastore.server.sql.ItemListSql;
import com.pizzastore.server.sql.OrderSql;
import com.pizzastore.server.sql.PaymentSql;
import com.pizzastore.server.sql.UserSql;

import java.io.*;
import java.net.*;
import java.sql.*;

public class PizzaStoreServer {

	private PizzaStoreServerGui gui;
	private int port;
	private Message message;
	private Socket socket;
	private ObjectInputStream serverInputStream;
	private ObjectOutputStream serverOutputStream; 
	private Connection connection;
	
	private UserSql userSql;
	private OrderSql orderSql;
	private PaymentSql paymentSql;
	private ItemListSql itemListSql;
	private ItemDetailSql itemDetailSql;
	
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://BUSCISMYSQL01:3306/iantopdb";
	static final String USER = "iantop";
	static final String PASS = "c618a!75221";
	
	private final int VIEW_USR_OP = 100;
	private final int INSERT_USR_OP = 110;
	private final int UPDATE_USR_OP = 120;
	private final int DELETE_USR_OP = 130;
	private final int VALIDATE_USR_OP = 140;
	
	private final int VIEW_ORDER_OP = 200;
	private final int INSERT_ORDER_OP = 210;
	private final int UPDATE_ORDER_OP = 220;
	private final int DELETE_ORDER_OP = 230;
	private final int GET_UPDATE_ORDER_OP = 217;
	private final int VIEW_ORDER_ID_CLICK_OP = 205;
	private final int GET_MAX_ID_OP = 215;

	private final int VIEW_PAYMENT_OP = 300;
	private final int INSERT_PAYMENT_OP = 310;
	private final int UPDATE_PAYMENT_OP = 320;
	private final int DELETE_PAYMENT_OP = 330;

	private final int VIEW_ITEM_LIST_ITEMS_OP = 415;
	private final int VIEW_ITEM_LIST_OP = 400;
	private final int INSERT_ITEM_LIST_OP = 410;
	private final int UPDATE_ITEM_LIST_OP = 420;
	private final int DELETE_ITEM_LIST_OP = 430;

	
	private final int VIEW_ITEM_DETAIL_OP = 500;
	private final int VIEW_ITEM_DETAIL_ID_OP = 505;
	private final int INSERT_ITEM_DETAIL_OP = 510;
	private final int UPDATE_ITEM_DETAIL_OP = 520;
	private final int DELETE_ITEM_DETAIL_OP = 530;
	private final int UPDATE_LISTS_OP = 540;
	
	private final String SUCCESS = "S";
	private final String FAILURE = "F";
	
	public PizzaStoreServer(int port) {
		this.port = port;
		viewGui();
	}

	private void initializeDB() throws IOException, SQLException, ClassNotFoundException {

		// create the server
		ServerSocket serverSocket = new ServerSocket(port);
		gui.txtDisplayResults.append("Server Is Listening ON Port:  " + port + "\n");
		// Connect to your database using your credentials
		// Load the JDBC driver
		Class.forName(JDBC_DRIVER);
		// Establish a connection
		connection = DriverManager.getConnection(DB_URL, USER, PASS);
		gui.txtDisplayResults.append("MySQL database connection established" + "\n");
		// loops for ever waiting for the client connection requests
		// create a thread for each client connection request using Runnable
		// class HandleAClient
		
		userSql = new UserSql(connection);
		orderSql = new OrderSql(connection);
		paymentSql = new PaymentSql(connection);
		itemListSql = new ItemListSql(connection);
		itemDetailSql = new ItemDetailSql(connection);
		
		while (true) {
			// Listen for a new connection request
			socket = serverSocket.accept();
			gui.txtDisplayResults.append("Connection Request ....... Connection Accepted" + "\n");

			// Create a new thread for the connection
			HandleAClient task = new HandleAClient(socket);

			// Start the new thread
			new Thread(task).start();

		}

	}

	// inner Runnable class handle a client connection
	class HandleAClient implements Runnable {
		private Socket socket; // A connected socket

		/** Construct a thread */
		public HandleAClient(Socket socket) {
			this.socket = socket;
		}

		/** Run a thread */
		public void run() {
			try {
				serverInputStream = new ObjectInputStream(socket.getInputStream());
				serverOutputStream = new ObjectOutputStream(socket.getOutputStream());
				// Continuously serve the client
				while (true) {
					Message message = null;	
					message = (Message)serverInputStream.readObject();
					 if ((message.getOpType() >= 100) && (message.getOpType() <= 140))  {
						 userSql.setParams(message);
					 }
					 else if ((message.getOpType() >= 200) && (message.getOpType() <= 230))  {
						 orderSql.setParams(message);	 
					 }
					 else if ((message.getOpType() >= 500) && (message.getOpType() <= 540))  {
						 itemDetailSql.setParams(message);	 
					 }
					 else if ((message.getOpType() >= 300) && (message.getOpType() <= 350))  {
						 paymentSql.setParams(message);	 
					 }
					 else if ((message.getOpType() >= 400) && (message.getOpType() <= 450))  {
						 itemListSql.setParams(message);	 
					 }
					
					//paymentSql.setParams(message);
					//itemListSql.setParams(message);
					
					switch (message.getOpType()) {
					case VIEW_USR_OP:
						userSql.viewUser();;
						break;
					case INSERT_USR_OP:
						userSql.insertUser();;
						break;
					case UPDATE_USR_OP:
						userSql.updateUser();;
						break;
					case DELETE_USR_OP:
						userSql.deleteUser();
						break;
					case VALIDATE_USR_OP:
						userSql.validateLoginCredentials();
						break;
					case VIEW_ORDER_OP:
						orderSql.viewOrder();
						break;
					case VIEW_ORDER_ID_CLICK_OP:
						orderSql.orderIdUpdate();
						break;
					case INSERT_ORDER_OP:
						orderSql.insertOrder();;
						break;
					case GET_UPDATE_ORDER_OP:
						orderSql.getUpdateOrder();
						break;
					case UPDATE_ORDER_OP:
						orderSql.updateOrder();;
						break;
					case DELETE_ORDER_OP:
						orderSql.deleteOrder();
						break;
					case INSERT_PAYMENT_OP:
						paymentSql.insertPayment();;
						break;
					case DELETE_PAYMENT_OP:
						paymentSql.deletePayment();
						break;
					case VIEW_ITEM_LIST_ITEMS_OP:
						itemListSql.viewItemListItems();
						break;
					case VIEW_ITEM_LIST_OP:
						itemListSql.viewItemList();
						break;
					case INSERT_ITEM_LIST_OP:
						itemListSql.insertItemList();;
						break;
					case UPDATE_ITEM_LIST_OP:
						itemListSql.updateItemList();;
						break;
					case DELETE_ITEM_LIST_OP:
						itemListSql.deleteItemList();
						break;
					case VIEW_ITEM_DETAIL_OP:
						itemDetailSql.viewItemDetail();
						break;
					case VIEW_ITEM_DETAIL_ID_OP:
						itemDetailSql.viewItemDetailId();
						break;
					case INSERT_ITEM_DETAIL_OP:
						itemDetailSql.insertItemDetail();;
						break;
					case UPDATE_ITEM_DETAIL_OP:
						itemDetailSql.updateItemDetail();;
						break;
					case DELETE_ITEM_DETAIL_OP:
						itemDetailSql.deleteItemDetail();
						break;
					case UPDATE_LISTS_OP:
						itemDetailSql.upDateLists();
					case GET_MAX_ID_OP:
						orderSql.getMaxOrder();
						break;
					}
					
					serverOutputStream.writeObject(message);
				}

			} catch (Exception ex) {
				//String message = "The system will exit. ERROR" + ex;
				//JOptionPane.showMessageDialog(null, message);
				//ex.printStackTrace();
				//System.out.println(ex);
				//System.exit(0);
			}

		}
	}// end of class Runnable

	void viewGui() {

		JFrame frame = new JFrame();
		frame.setTitle("Pizza Server");
		Container contentPane = frame.getContentPane();
		gui = new PizzaStoreServerGui(this);
		contentPane.add(gui);
		frame.pack();
		frame.setVisible(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (frame.getWidth() / 2), 
				middle.y - (frame.getHeight() / 2));
		frame.setLocation(newLocation);
	}
	
	public static void main(String[] args) {
		PizzaStoreServer server = new PizzaStoreServer(8031);
		try {
			server.initializeDB();
		} catch(Exception e) {
			String message = "An error occurred in the Pizza Server application - the system will exit.";
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
			System.exit(0);
		}

	}
}
