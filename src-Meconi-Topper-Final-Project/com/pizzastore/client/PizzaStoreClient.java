package com.pizzastore.client;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;

import com.pizzastore.data.ItemDetail;
import com.pizzastore.data.Message;
import com.pizzastore.data.Order;
import com.pizzastore.data.Payment;
import com.pizzastore.data.User;

import javax.swing.*;

public class PizzaStoreClient extends JFrame implements ActionListener ,Serializable  {

	private String hostname;
	private int port;
	private Message message;
	private Message  messageBack;
	private Socket conn;
	// IO streams
	private ObjectOutputStream clientOutputStream;
	private ObjectInputStream clientInputStream;
	private Order order;
	private Payment payment;
	// declare UI component objects
	
	//Login components
	private int guiFlow ;
	private int userID = 1;
	private int loggedID;
	private JLabel jlExisting;
	private JLabel jluserName;
	private JLabel jlpassword;
	private JLabel jlNewCust;
	private JLabel jlSpace;
	private JLabel jlPizzaImage;
	private JLabel jlaccountRole;
	
	private JTextField jfuserName;
	private JTextField jfpassword;
	
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
	private JButton jbtClose;
	private JButton jbtLogin;
	private JButton jbtCreateNew;
	
	private JComboBox rolesCombList;
	
	//Create Customer components
	
	private JLabel jlWelcome;
	private JLabel jlfName;
	private JLabel jlmailAddress;
	private JLabel jlemail_address;
	private JLabel jlphone_number;
	private JLabel jlDrinkImage;
	private JLabel jlBreadImage;
	private JLabel jlGarlicImage;
	private JLabel jlWingsImage;
	private JLabel jlroleSelect;
	
	private JTextField jffName;
	private JTextField jfmailAddress;
	private JTextField jfemail_address;
	private JTextField jfphone_number;
	
	private JPanel CreateNewMainPanel;
	private JPanel topPanelCreate; 
	private JPanel centerPanelCreate; 
	private JPanel bottomPanelCreate; 
	
	private JButton jbtCreateUser;
	private JButton jbtClear;
	private JButton jbtUpdate;
	
	//Create orderGUI components
	private int numItemsInOrder= 0;
	private double total = 0;
	private String orderItems = "";
	
	private JPanel CreateOrderPanel;
	private JPanel topOrderPanel;
	private JPanel middleOrderPanel;
	private JPanel bottomOrderPanel;
	
	private JLabel jlPizza;
	private JLabel jlSides;
	private JLabel jlDrinks;
	
	private JComboBox PizzaCombList;
	private JComboBox SidesCombList;
	private JComboBox DrinksCombList;
	
	private JTextArea orderText;
	private JScrollPane jp;
	private JScrollPane jpPay;
	
	private JButton jbtAddToOrder;
	private JButton jbtRemoveFromOrder;
	private JButton jbtPlaceOrder;
	
	private String[] PizzaList;
	private String[] PizzaList2;
	private String[] DrinksList;
	private String[] DrinksList2;
	private String[] SidesList;
	private String[] SidesList2;
	private String[] PriceList;
	private String[] PriceList2;
	
	//Create payment GUI components
	//paymentPanel
	private JPanel jbtnPane;
	private JPanel paymentPanel;
	private JPanel creditCardTop;
	private JPanel creditCardMid;
	private JPanel creditCardBot;
	private JPanel orderStatusPanel;
	private JPanel trackStatusPanel;
	
	private JLabel jlcreditCard;
	private JLabel jlcreditCardName;
	private JLabel jlcreditCardExp;
	private JLabel jltrackOrder;
	private JLabel jlPizza2Image;
	
	private JTextField jfcreditCard;
	private JTextField jfcreditCardName;
	private JTextField jfcreditCardExp;
	private JTextArea orderStatusText;
	private JScrollPane jporderStatus;
	
	private JButton jbtsubmitPayment;
	private JButton jbtnCancel;
	private JButton jbtnModify;
	private JButton jbtnUpdateStat;
	private JButton btnPayClose;
	
	//create Sysadmin GUI components
	private JPanel SysAdminTopPanel;
	private JPanel SysAdminMidPanel;
	private JPanel SysAdminBotPanel;
	private JPanel SysAdminLowerPanel;
	private JPanel SysAdminType;
	
	private JLabel jlSysAdmin;
	private JLabel jlupdateMenuValue;
	private JLabel jlupdatePriceValue;
	private JLabel jlupdateMenuElement;
	private JLabel jlupdatePriceElement;
	private JLabel jltype;
	
	private JTextField jfSysAdminMenuName;
	private JTextField jfSysAdminMenuPrice;
	private JTextField jfSysAdminMenuItemID;
	private JTextField jfSysAdminMenuSize;
	
	private JComboBox types;
	private JButton jbtviewMenu;
	private JButton jbtupdateMenu;
	
	private JTextArea SysAdminText;
	private JScrollPane spSys;

	//create Cook GUI components
	private JPanel CookTopPanel;
	private JPanel CookMidPanel;
	private JPanel CookBotPanel;
	private JLabel jlChef, jlOrderId, jlOrderDetails;
	private JTextArea jtaChef;
	private JScrollPane jspChef;
	private JButton btnViewOrder;
	private JTextField jfChefId;
	private JLabel lblPizzaFire;
	private JLabel lblPizzaFire2;
	private JRadioButton radComplete;
	private JRadioButton radPlaced;
	private JRadioButton radRecieved;
	private JRadioButton radInProcess;
	private JButton btnChangeStat;
	private static JComboBox<String> cmbOrderId;
	private static int orderIdMaster;
	public PizzaStoreClient(String hostname, int port) throws IOException {

		this.port = port;
		this.hostname = hostname;

		// Create a connection with the StaffServer server on port number 8000
		conn = new Socket(this.hostname, this.port);
		clientOutputStream = new ObjectOutputStream(conn.getOutputStream());
		clientInputStream = new ObjectInputStream(conn.getInputStream());
		// call these two methods to create user GUI
		initComponenet();
		doTheLayout();

	}

	private void initComponenet() {
		// Initialize the GUI components for main login
		jlExisting = new JLabel("<HTML><FONT COLOR=RED>Already have a account? Login here:</FONT>");
		jlSpace = new JLabel("");
		jlNewCust= new JLabel("<HTML><FONT COLOR=RED>Click here to create a New Account:</FONT>");
		
		jluserName = new JLabel("User Name:");
		jfuserName = new JTextField(20);
		jlpassword = new JLabel("Password:");
		jfpassword = new JTextField(20);
		
		jlaccountRole = new JLabel("<HTML><FONT COLOR=RED>Select Role:</FONT>");
		
		jbtLogin = new JButton("Login");
		jbtLogin.setMnemonic(KeyEvent.VK_O);
		jbtClose = new JButton("Close");
		jbtClose.setMnemonic(KeyEvent.VK_C);
		jbtCreateNew = new JButton("Create New Account");
		jbtCreateNew.setMnemonic(KeyEvent.VK_N);
		jbtUpdate = new JButton("Update");
		jbtUpdate.setMnemonic(KeyEvent.VK_P);
		
		String[] rolesList = {"Customer", "System Admin", "Cook"};
		rolesCombList = new JComboBox(rolesList);
		
		jlPizzaImage = new JLabel(new ImageIcon("U:\\Workspace\\PIZZA-STORE\\lib\\pizza_adven_zestypepperoni.png"));
		
		// Initialize the GUI components for CreateNew 
	
		jlWelcome = new JLabel("<HTML><FONT SIZE=6>Welcome! Please Create Your User Account:</FONT>");
		jlfName = new JLabel("Full Name:");
		jffName = new JTextField(20);
		jlmailAddress = new JLabel("Address:");
		jfmailAddress = new JTextField(20);
		jlemail_address = new JLabel("E-Mail Address:");
		jfemail_address = new JTextField(20);
		jlphone_number = new JLabel("Phone Number:");
		jfphone_number = new JTextField(20);
		jlroleSelect = new JLabel("Select Account Type");
		
		jbtCreateUser = new JButton("Create User Account");
		jbtCreateUser.setMnemonic(KeyEvent.VK_A);
		jbtClear = new JButton("Clear Fields");
		jbtClear.setMnemonic(KeyEvent.VK_E);
		//Initialize the GUI components for createOrderGUI
		
		jbtPlaceOrder = new JButton("Place Order:");
		jbtPlaceOrder.setMnemonic(KeyEvent.VK_R);
		String[] PizzaList = {"Single Slice -Cheese ", "Whole Pizza -Cheese", "Single Slice -Pepperoni" , "Whole Pizza -Pepperoni"   };
		PizzaCombList = new JComboBox(PizzaList);
		PizzaCombList.setSelectedIndex(-1);
		
		String[] SidesList = {"Bread Sticks", "Garic Knots", "Hot Wings", "Garlic Bread", "Side Salad" };
		SidesCombList = new JComboBox(SidesList);
		SidesCombList.setSelectedIndex(-1);
		
		String[] DrinkList = {"Pepsi", "Mtn. Dew", "Diet Pepsi", "Dr.Pepper", "Sprite"};
		DrinksCombList = new JComboBox(DrinkList);
		DrinksCombList.setSelectedIndex(-1);
		
		jlPizza = new JLabel("<HTML><FONT COLOR=RED>Pizza:</FONT>");
		jlSides = new JLabel("<HTML><FONT COLOR=RED>Sides:</FONT>");
		jlDrinks = new JLabel("<HTML><FONT COLOR=RED>Select Drinks:</FONT>");
		
		orderText = new JTextArea(60, 80);
		orderText.setEditable(false);
		orderText.setLineWrap(true);
		orderText.setWrapStyleWord(true);
		
		jp = new JScrollPane(orderText);
		jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		jbtAddToOrder = new JButton("Add Item To Order");
		jbtAddToOrder.setMnemonic(KeyEvent.VK_D);
		jbtRemoveFromOrder = new JButton("Remove Items From Order");
		jbtRemoveFromOrder.setMnemonic(KeyEvent.VK_I);
		jlDrinkImage = new JLabel(new ImageIcon("U:\\Workspace\\PIZZA-STORE\\lib\\Drinks.jpg"));
		jlBreadImage = new JLabel(new ImageIcon("U:\\Workspace\\PIZZA-STORE\\lib\\breadsticks.jpg"));
		jlGarlicImage = new JLabel(new ImageIcon("U:\\Workspace\\PIZZA-STORE\\lib\\Garlic.jpg"));
		jlWingsImage = new JLabel(new ImageIcon("U:\\Workspace\\PIZZA-STORE\\lib\\Hotwings.jpg"));
		
		//Initialize Payment GUI components for Payment GUI
		jlcreditCard = new JLabel("Enter Your Credit Card Number:      ");
		jlcreditCardName = new JLabel("Enter the Name on the Credit Card:");
		jlcreditCardExp = new JLabel("Enter the Expiration Date: MM/YYYY");
		jltrackOrder = new JLabel("<HTML><FONT COLOR=WHITE SIZE=8>Current Order Status</FONT>");
		
		jfcreditCard = new JTextField(20);
		jfcreditCardName = new JTextField(20);
		jfcreditCardExp = new JTextField(20);
		
		orderStatusText = new JTextArea(150, 125);
		orderStatusText.setEditable(false);
		orderStatusText.setLineWrap(true);
		orderStatusText.setWrapStyleWord(true);
		jpPay = new JScrollPane(orderStatusText);
		jpPay.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jpPay.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jbtsubmitPayment = new JButton("Submit Payment And Finalize Order");
		jbtsubmitPayment.setMnemonic(KeyEvent.VK_S);
		jbtnCancel = new JButton("Cancel Order");
		jbtnCancel.setMnemonic(KeyEvent.VK_L);
		jbtnModify = new JButton("Modify Current Order");
		jbtnModify.setMnemonic(KeyEvent.VK_M);
		jbtnUpdateStat = new JButton("Get Current Order Status");
		jbtnUpdateStat.setMnemonic(KeyEvent.VK_T);
		btnPayClose = new JButton("Exit (Unpaid Order Details Will Be Lost)");
		btnPayClose.setMnemonic(KeyEvent.VK_X);
		jlPizza2Image = new JLabel(new ImageIcon("U:\\Workspace\\PIZZA-STORE\\lib\\Pizza2.jpg"));
		
		//Initialize Sysadmin GUI components
		jlSysAdmin = new JLabel("<HTML><FONT COLOR=RED SIZE=7>System Admin Options:</FONT>");
		
		jbtviewMenu = new JButton("View Menu Items");
		jbtviewMenu.setMnemonic(KeyEvent.VK_W);
		jbtupdateMenu = new JButton("Update or Create Menu Items");
		
		
		jlupdateMenuElement = new JLabel("<HTML><FONT SIZE=5>Item Id(Int)(Ex: 1):</FONT>");
		jlupdateMenuValue = new JLabel("<HTML><FONT SIZE=5>Item Name (String):</FONT>");
		jlupdatePriceValue = new JLabel("<HTML><FONT SIZE=5>Item Price (Double)(Ex: 1.50) :</FONT>");
		jlupdatePriceElement = new JLabel("<HTML><FONT SIZE=5>Enter Item Size (String):</FONT>");
		jltype = new JLabel("<HTML><FONT SIZE=5>Select the item Type:</FONT>");
		
		jfSysAdminMenuName = new JTextField(20);
		jfSysAdminMenuItemID = new JTextField(20);
		jfSysAdminMenuPrice = new JTextField(20);
		jfSysAdminMenuSize = new JTextField(20);
		
		SysAdminText = new JTextArea(100, 75);
		SysAdminText.setEditable(false);
		SysAdminText.setLineWrap(true);
		SysAdminText.setWrapStyleWord(true);
		spSys = new JScrollPane(SysAdminText);
		String[] typeList = {"Pizza", "Drinks", "Sides" };
		types = new JComboBox(typeList);
		types.setSelectedIndex(-1);
	////Initialize Chef GUI
			jlChef = new  JLabel("Chef View");
			jlChef.setForeground(Color.WHITE.brighter());
			jlOrderId = new JLabel("Please Enter Order ID:");
			jlOrderDetails= new JLabel ("Order Details:");
			jlOrderDetails.setForeground(Color.WHITE.brighter());
			jtaChef = new JTextArea(30,30);
			jtaChef.setEditable(false);
			jtaChef.setLineWrap(true);
			jtaChef.setWrapStyleWord(true);
			jtaChef.setText(" Welcome chef please enter and submit the order to be viewed.");
			jspChef = new JScrollPane(jtaChef);
			jspChef.setSize(1000,20);
			
			jspChef.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jspChef.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			btnViewOrder= new JButton("View Order");
			btnViewOrder.setMnemonic(KeyEvent.VK_V);
			jfChefId = new JTextField(4);
			lblPizzaFire = new JLabel(new ImageIcon("U:\\Workspace\\PIZZA-STORE\\lib\\pizzapizza.png"));
			lblPizzaFire2 = new JLabel(new ImageIcon("U:\\Workspace\\PIZZA-STORE\\lib\\pizzapizza.png"));
			radPlaced = new JRadioButton("Order Placed");
			radComplete = new JRadioButton("Order Complete");
			radRecieved = new JRadioButton("Order Recieved");
			radInProcess = new JRadioButton("Order in Process");
			ButtonGroup btg = new ButtonGroup();
			btg.add(radPlaced);
			btg.add(radComplete);
			btg.add(radRecieved);
			btg.add(radInProcess);
			radPlaced.setSelected(true);;
			btnChangeStat = new JButton("Update Status");
			btnChangeStat.setMnemonic(KeyEvent.VK_U);
			String [] clickIt = {"Double Click Pictures For ID's"};
			cmbOrderId = new JComboBox<String>(clickIt);
		
	}

	private void doTheLayout() {
		// Arrange the UI components into GUI window
		
		mainPanel = new JPanel();
		
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(jlExisting);
		topPanel.add(jlSpace);
		topPanel.add(jluserName);
		topPanel.add(jfuserName);
		topPanel.add(jlpassword);
		topPanel.add(jfpassword);
		topPanel.add(jbtLogin);
		topPanel.add(jbtClose);		
		
		centerPanel = new JPanel();
		centerPanel.add(jlaccountRole);
		centerPanel.add(rolesCombList);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(jlNewCust);
		bottomPanel.add(jbtCreateNew);
		bottomPanel.add(jlPizzaImage);
		
		mainPanel.setLayout( new BorderLayout());
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
		
		jbtClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					closeButtonClicked();
				} catch (Exception ex) {
					
				}
			}
		});
		
		jbtCreateNew.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					CreateNewClicked();
				} catch (Exception ex) {
					
				}
			}
		});
		
		jbtLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					LoginButtonClicked();
				} catch (Exception ex) {
					
				}
			}
		});

	}
		
		private void doCreateNewGUI() {
			/////create user gui
			topPanel.setVisible(false);
			centerPanel.setVisible(false);
			bottomPanel.setVisible(false);
			mainPanel.setVisible(false);
			
			CreateNewMainPanel = new JPanel();
			topPanelCreate = new JPanel();
			
			topPanelCreate.setLayout(new GridLayout(4,2));
			topPanelCreate.setBackground(Color.lightGray);
			topPanelCreate.add(jlfName);
			topPanelCreate.add(jffName);
			topPanelCreate.add(jlmailAddress);
			topPanelCreate.add(jfmailAddress);
			topPanelCreate.add(jlemail_address);
			topPanelCreate.add(jfemail_address);
			topPanelCreate.add(jlphone_number);
			topPanelCreate.add(jfphone_number);
			topPanelCreate.add(jluserName);
			topPanelCreate.add(jfuserName);
			topPanelCreate.add(jlpassword);
			topPanelCreate.add(jfpassword);
			topPanelCreate.add(jlSpace);
			topPanelCreate.add(jlSpace);
			topPanelCreate.add(jlSpace);
			
			CreateNewMainPanel.setLayout(new FlowLayout());
			CreateNewMainPanel.setBackground(Color.lightGray);
			
			CreateNewMainPanel.add(jlWelcome);
			CreateNewMainPanel.add(jlroleSelect);
			CreateNewMainPanel.add(rolesCombList);
			
			centerPanelCreate = new JPanel();
			centerPanelCreate.setLayout(new FlowLayout());
			centerPanelCreate.add(jbtCreateUser);
			centerPanelCreate.add(jbtClose);
			
			add(jlDrinkImage);
			
			setLayout(new GridLayout(6,1));
			add(CreateNewMainPanel);
			add(topPanelCreate);
			add(centerPanelCreate);
			
			jbtCreateUser.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						
						CreateUserClicked();
					} catch (Exception ex) {
						
					}
				}
			});
				
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	//////order GUI 
	private void doCreateOrderGUI() {
		if(guiFlow == 1) {
			mainPanel.setVisible(false);
		}
		if(guiFlow == 4) {
			CreateNewMainPanel.setVisible(false);
			topPanelCreate.setVisible(false);
			centerPanelCreate.setVisible(false);
			jlGarlicImage.setVisible(false);
			getContentPane().removeAll();
			getContentPane().remove(jlGarlicImage);
			getContentPane().remove(jlWingsImage);
			getContentPane().remove(jlDrinkImage);
			getContentPane().remove(topPanelCreate);
			getContentPane().remove(CreateNewMainPanel);
			getContentPane().remove(centerPanelCreate);
		}
	
		CreateOrderPanel = new JPanel();
		topOrderPanel = new JPanel();
		middleOrderPanel = new JPanel();
		bottomOrderPanel = new JPanel();
		
		topOrderPanel.add(jbtAddToOrder);
		topOrderPanel.add(jbtRemoveFromOrder);

		
		
		middleOrderPanel.setLayout(new GridLayout(1,1));
		middleOrderPanel.add(jp);

		bottomOrderPanel.add(jbtPlaceOrder);		
		
		CreateOrderPanel.setLayout(new FlowLayout());
		CreateOrderPanel.add(jlPizza);
		CreateOrderPanel.add(PizzaCombList);
		CreateOrderPanel.add(jlSides);
		CreateOrderPanel.add(SidesCombList);
		CreateOrderPanel.add(jlDrinks);
		CreateOrderPanel.add(DrinksCombList);
		
		setLayout(new GridLayout(5,1));
		add(CreateOrderPanel);
		add(topOrderPanel);
		add(middleOrderPanel);
		add(bottomOrderPanel);
		
		jbtPlaceOrder.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					PlaceOrderButtonClicked();
				} catch (Exception ex) {
					
				}
			}
		});
		
		jbtAddToOrder.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					AddToOrderButtonClicked();
				} catch (Exception ex) {
					
				}
			}
		});
		
		jbtRemoveFromOrder.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					RemoveFromOrderButtonClicked();
				} catch (Exception ex) {
					
				}
			}
		});
		
		
	}
	/////Payment GUI
	private void doCreatePaymentGUI() {
		CreateOrderPanel.setVisible(false);
		topOrderPanel.setVisible(false);
		middleOrderPanel.setVisible(false);
		bottomOrderPanel.setVisible(false);
		getContentPane().removeAll();
		
		creditCardTop = new JPanel();
		creditCardTop.setLayout(new FlowLayout());
		creditCardTop.add(jlcreditCard);
		creditCardTop.add(jfcreditCard);
		
		creditCardMid = new JPanel();
		creditCardMid.setLayout(new FlowLayout());
		creditCardMid.add(jlcreditCardName);
		creditCardMid.add(jfcreditCardName);
		
		creditCardBot = new JPanel();
		creditCardBot.setLayout(new FlowLayout());
		creditCardBot.add(jlcreditCardExp);
		creditCardBot.add(jfcreditCardExp);
		
		jbtnPane = new JPanel();
		jbtnPane.setLayout(new FlowLayout());
		jbtnPane.setBackground(Color.red.darker().darker().darker());
		jbtnPane.add(jbtsubmitPayment);
		jbtnPane.add(jbtnCancel);
		jbtnPane.add(jbtnModify);
		jbtnPane.add(jbtnUpdateStat);
		jbtnPane.add(btnPayClose);
		
		paymentPanel = new JPanel();
		paymentPanel.setLayout(new GridLayout(7,2));
		paymentPanel.setBackground(Color.red.darker().darker().darker());
		paymentPanel.add(creditCardTop);
		paymentPanel.add(creditCardMid);
		paymentPanel.add(creditCardBot);
		paymentPanel.add(jbtnPane);
		paymentPanel.add(jltrackOrder);
		
		trackStatusPanel = new JPanel();
		trackStatusPanel.setLayout(new GridLayout(1,1));	
		trackStatusPanel.add(jpPay);
		
		orderStatusPanel = new JPanel();
		orderStatusPanel.setBackground(Color.red.darker().darker().darker());
		orderStatusPanel.add(jlPizza2Image);
		
		setLayout(new GridLayout(3,2));
		add(paymentPanel);
		add(trackStatusPanel);
		add(orderStatusPanel);
		
		
		
		btnPayClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					clickBtnPayClose();
				} catch (Exception ex) {
					
				}
			}
		});
		jbtsubmitPayment.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					jbtsubmitPaymentButtonClicked();
				} catch (Exception ex) {
					
				}
			}
		});
		jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					jbtnCancelClicked();
				} catch (Exception ex) {
					
				}
			}
		});
		jbtnModify.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					jbtnModifyClicked();
				} catch (Exception ex) {
					
				}
			}
		});
		jbtnUpdateStat.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					jbtnUpdateStatClicked();
				} catch (Exception ex) {
					
				}
			}
		});
	}
	//////Sys Admin GUI
	private void doCreateSysadminGUI() {
		mainPanel.setVisible(false);
		
		SysAdminTopPanel = new JPanel();
		SysAdminTopPanel.setLayout(new GridLayout(5,1));
		SysAdminTopPanel.add(jlupdateMenuElement);
		SysAdminTopPanel.add(jfSysAdminMenuItemID);
		SysAdminTopPanel.add(jlupdateMenuValue);
		SysAdminTopPanel.add(jfSysAdminMenuName);
		SysAdminTopPanel.add(jlupdatePriceValue);
		SysAdminTopPanel.add(jfSysAdminMenuPrice);
		SysAdminTopPanel.add(jlupdatePriceElement);
		SysAdminTopPanel.add(jfSysAdminMenuSize);
		SysAdminTopPanel.add(types);
		
		SysAdminType = new JPanel();
		SysAdminType.setLayout(new FlowLayout());
		SysAdminType.add(jltype);
		SysAdminType.add(types);
		
		SysAdminMidPanel = new JPanel();
		SysAdminMidPanel.setLayout(new FlowLayout());
		SysAdminMidPanel.add(jbtviewMenu);
		SysAdminMidPanel.add(jbtupdateMenu);
		SysAdminMidPanel.add(jbtClose);
		
		SysAdminLowerPanel = new JPanel();
		SysAdminLowerPanel.add(jlSysAdmin);
		
		
		SysAdminBotPanel = new JPanel();
		SysAdminBotPanel.setLayout(new GridLayout(1,1));
		SysAdminBotPanel.add(spSys);
		
		
		setLayout(new GridLayout(6,2));
		add(SysAdminLowerPanel);
		add(SysAdminTopPanel);
		add(SysAdminType);
		add(SysAdminMidPanel);
		add(SysAdminBotPanel);
		
		
		jbtviewMenu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					jbtviewMenuButtonClicked();
				} catch (Exception ex) {
					
				}
			}
		});
		
		jbtupdateMenu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					jbtupdateMenuButtonClicked();
				} catch (Exception ex) {
					
				}
			}
		});
		
		
	}
	//////Cook GUI
	private void doCreateCookGUI() throws ClassNotFoundException, IOException {
		mainPanel.setVisible(false);
		
		CookTopPanel = new JPanel();
		CookTopPanel.setPreferredSize(new Dimension(10,50));
		CookTopPanel.add(jlChef);;
		CookTopPanel.setBackground(Color.red.darker().darker());
		
		CookMidPanel = new JPanel();
		CookMidPanel.setPreferredSize(new Dimension(50,100));
		CookMidPanel.setLayout(new GridLayout(10,2));
		CookMidPanel.add(jlOrderId);
		CookMidPanel.add(cmbOrderId);
		CookMidPanel.add(btnViewOrder);
		CookMidPanel.add(radPlaced);
		CookMidPanel.add(radRecieved);
		CookMidPanel.add(radInProcess);
		CookMidPanel.add(radComplete);
		CookMidPanel.add(btnChangeStat);
		CookMidPanel.add(jbtClose);
		jlOrderDetails.setForeground(Color.red.darker().darker().darker());
		CookMidPanel.add(jlOrderDetails);
		
		CookBotPanel = new JPanel();
		CookBotPanel.setPreferredSize(new Dimension(30,400));
		CookBotPanel.setLayout(new GridLayout(2,1));
		
			
		CookBotPanel.add(jspChef);
		CookBotPanel.setBackground(Color.red.darker().darker());
		
		
		JPanel cookWPane = new JPanel();
		cookWPane.setPreferredSize(new Dimension(300,1000));
		cookWPane.add(lblPizzaFire);

		JPanel cookEPane = new JPanel();
		cookEPane.setPreferredSize(new Dimension(300,1000));
		cookEPane.add(lblPizzaFire2);
		
		setLayout(new BorderLayout(10,10));
		add(CookTopPanel,"North");
		add(CookMidPanel,"Center");
		add(CookBotPanel,"South");
		add(cookWPane,"West");
		add(cookEPane,"East");

		btnChangeStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					btnChangeStatClicked();
				} catch (Exception ex) {
					
				}
			}
		});
		btnViewOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					btnViewOrderClicked();
				} catch (Exception ex) {
					
				}
			}
		});
		addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent e){
		        if(e.getClickCount()==2){
		           try {
					cmbOrderIdClick();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
		        }
		    }
		});
		
		}
	//Methods for main Login Screen Buttons
	
	///close btn method
	void closeButtonClicked() {
		try {
			conn.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		System.exit(0);
		
	}
	/////Login method
	void LoginButtonClicked() throws IOException, Exception {
		populateMenu();
		
		String role = rolesCombList.getSelectedItem().toString();
		if(role == "System Admin"){
			doCreateSysadminGUI();
			
		}
		else if(role == "Cook"){
			doCreateCookGUI();
			
		}else {
		
			loggedID = 0;
			String userName = jfuserName.getText();
			String password = jfpassword.getText();
			com.pizzastore.data.User user = new User(loggedID, userName, password);
			message = new Message(140, user);
			clientOutputStream.writeObject(message);
			messageBack = (Message)clientInputStream.readObject();
				if(messageBack.getOpStatus().contentEquals("S")) {
					loggedID =  messageBack.getUserId();
					if(role == "Customer"){
						guiFlow = 1;
						doCreateOrderGUI();
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "User and/or Password Incorrect, Please Try Again!");
				}
		}			
	}
	////Menu set order GUI transfer
	void CreateNewClicked() throws Exception, Exception {
		populateMenu();
		doCreateNewGUI();
	}
	
	//Methods for create Customer Screen Buttons
	
	void CreateUserClicked() throws ClassNotFoundException, Exception {
		//toDo add validations and create user
		try {
			String Name = "";
			String phone = "";
			String email = "";
			String userName = "";
			String password = "";
			String address = "";
			
			if((jffName.getText().toString().matches("[a-zA-Z]+")) && (jffName.getText() != "")){
				Name = jffName.getText();
			} else {
				JOptionPane.showMessageDialog(null, "Invalid Entry in Name Field");
				jffName.requestFocus();
				return;
			}
				
			String role = rolesCombList.getSelectedItem().toString();
			
			if(! jfemail_address.getText().isEmpty()){
				email = jfemail_address.getText();
			
			} else {
				JOptionPane.showMessageDialog(null, "Invalid Entry in Email address");
				jfemail_address.requestFocus();
				return;
			}
			
			if(! jfmailAddress.getText().isEmpty()){
				address = jfmailAddress.getText();
			} else {
				JOptionPane.showMessageDialog(null, "Invalid Entry in Mail address");
				jfmailAddress.requestFocus();
				return;
			}
			
			if((jfphone_number.getText().toString().matches("[0-9-]+")) && (jffName.getText() != "")){
				phone = jfphone_number.getText();
			} else {
				JOptionPane.showMessageDialog(null, "Invalid Entry in Phone Number Field");
				jfphone_number.requestFocus();
				return;
			}
			
			if(! jfuserName.getText().isEmpty()){
				userName = jfuserName.getText();
			} else {
				JOptionPane.showMessageDialog(null, "Invalid Entry in User Name Field");
				jfuserName.requestFocus();
				return;
			}
				
			if(! jfpassword.getText().isEmpty()){
				password = jfpassword.getText();
			} else {
				JOptionPane.showMessageDialog(null, "Invalid Entry in User Password Field");
				jfpassword.requestFocus();
				return;
			}
			
			
			com.pizzastore.data.User user = new User(userID, role, userName, password, Name, address, email, phone);
			message = new Message(110, user);
			clientOutputStream.writeObject(message);
			messageBack = (Message)clientInputStream.readObject();
			if(messageBack.getOpStatus().contentEquals("S")) {
				userID++;
				JOptionPane.showMessageDialog(null, "Account Created Sucessfully, Please Place your Order!");
				role = "Customer";
				loggedID = 0;
				userName = jfuserName.getText();
				password = jfpassword.getText();
				
				message = new Message(140, user);
				clientOutputStream.writeObject(message);
				messageBack = (Message)clientInputStream.readObject();
					if(messageBack.getOpStatus().contentEquals("S")) {
						loggedID =  messageBack.getUserId();
						if(role == "Customer"){
							guiFlow = 4;
							doCreateOrderGUI();
						}
				
					}
				
			}	else {
				JOptionPane.showMessageDialog(null, "Failed to Create new User");
			}
		}
		catch (Exception lne) {
			JOptionPane.showMessageDialog(null, "Failed to Create new User");
		}
	}
	
	//methods for order menu
	void PlaceOrderButtonClicked() throws IOException, Exception {
		int orderId = 11;
	
			clientOutputStream.writeObject(message);
			messageBack = (Message)clientInputStream.readObject();
			String timeStamp = new SimpleDateFormat("ddHHmmss").format(new Date());
			orderId = Integer.parseInt(timeStamp);
		orderIdMaster = orderId; 
		double taxes = (total * .075);
		double grandTotal = (total + taxes);
	    DecimalFormat df = new DecimalFormat("#.00");
	    String grandTotalFormated = df.format(grandTotal);
		grandTotal = Double.parseDouble(grandTotalFormated);
		String taxesFormated = df.format(taxes);
		taxes = Double.parseDouble(taxesFormated);
		com.pizzastore.data.Order order = new Order(orderId, total, "Payment Needed", loggedID, taxes, grandTotal, orderItems);
		Message message1 = new Message(210, "", order);
		try {
			clientOutputStream.writeObject(message1);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			messageBack = (Message)clientInputStream.readObject();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		updateItemListOrderId(orderId);
		String items = getItemsOrder(orderId);
		jbtnCancel.setEnabled(true);
		jbtnModify.setEnabled(true);
		
		
		orderStatusText.setText("Order: "+"\n"+"[Order Id: " + orderId + ", Sub Total: " + total + 
				", Taxes: " + taxes + ", User Id: " + loggedID + ", Grand Total: " + grandTotal + "]"+"\n"+"Order Items: "+"\n"+items);
		doCreatePaymentGUI();
		jbtnUpdateStatClicked();
	}
	


String jbtnUpdateStatClicked()   {
		
		int opType =217;
		
		message = new Message(orderIdMaster,opType);
		try {
			clientOutputStream.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			messageBack = (Message)clientInputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		String stat = messageBack.getStatus();
		jltrackOrder.setText("<HTML><FONT COLOR=WHITE SIZE=8>Current Order Status: "+stat+"</FONT>");
		return stat;
	}
	
	void jbtnCancelClicked() throws ClassNotFoundException, IOException {
		

		String stat = "";
		int status = 0;
		 
		stat = jbtnUpdateStatClicked();
		
		if (stat.equals("Complete")){
			 status = 1; 
		}
		else if(stat.equals("Processing")){
			status = 1;
		}
		else if(stat.equals("Payment Needed")){
			status = 2;
		}
		else if(stat.equals("Recieved")){
			status = 2;
		}
		else if(stat.equals("Placed")){
			status = 2;
		}
		if (status ==(2)){
			JOptionPane.showMessageDialog(null,"Order has been cancelled");
			orderStatusText.setText("");
			message.setOrderId(orderIdMaster);
			deleteOrder(orderIdMaster);	
			System.exit(0);
			}
		else {
		JOptionPane.showMessageDialog(null,"Cannot cancel order with "
				+ "the status of processing or complete."+"\n"+"Sorry for "
						+ "any inconvienience");
	}
}
	void deleteOrder(int orderId) throws IOException, ClassNotFoundException {
		////deletes order and payment from database
		int opType = 230;
		
		message = new Message(orderId,opType);
		clientOutputStream.writeObject(message);
		messageBack = (Message)clientInputStream.readObject();
		String opStatus = messageBack.getOpStatus();
		
		if (opStatus.contentEquals("S")){
			JOptionPane.showMessageDialog(null,"No charges Applied"); 
		}
		else JOptionPane.showMessageDialog(null, "Order could not be deleted or was not finalized"); 
			
	}
	
	void jbtnModifyClicked() throws IOException, ClassNotFoundException, BadLocationException {
		//////deletes order and payment and allows new order to be made
		orderItems="";
		RemoveFromOrderButtonClicked();
		String stat = "";
		int status = 0;
		 stat = jbtnUpdateStatClicked();
	
		if (stat.equals("Complete")){
			 status = 1; 
		}
		else if(stat.equals("Processing")){
			status = 1;
		}
		else if(stat.equals("Payment Needed")){
			status = 3;
		}
		else if(stat.equals("Recieved")){
			status = 2;
		}
		else if(stat.equals("Placed")){
			status = 2;
		}
		if (status ==(2)){
			JOptionPane.showMessageDialog(null, "Payment Refunded");
			orderStatusText.setText("");
			message.setOrderId(orderIdMaster);
			jbtsubmitPayment.setText("Submit Payment And Finalize Order");
			jbtsubmitPayment.setEnabled(true);
			deleteOrder(orderIdMaster);
			
			paymentPanel.setVisible(false);
			trackStatusPanel.setVisible(false);
			orderStatusPanel.setVisible(false);
			getContentPane().removeAll();
			doCreateOrderGUI();
	}
		if (status ==(3)){
			
			orderStatusText.setText("");
			message.setOrderId(orderIdMaster);
			jbtsubmitPayment.setText("Submit Payment And Finalize Order");
			jbtsubmitPayment.setEnabled(true);
			deleteOrder(orderIdMaster);
			
			paymentPanel.setVisible(false);
			trackStatusPanel.setVisible(false);
			orderStatusPanel.setVisible(false);
			getContentPane().removeAll();
			doCreateOrderGUI();
			}
		
		else if (status ==(1)){
			JOptionPane.showMessageDialog(null,"Cannot modify order with "
					+ "the status of processing or complete."+"\n"+"Sorry for "
							+ "any inconvienience");
		}
		
	}
	void AddToOrderButtonClicked() throws ClassNotFoundException, IOException {
		numItemsInOrder++;
		
		if (DrinksCombList.getSelectedIndex() != (-1)){

			String drinks = DrinksCombList.getSelectedItem().toString();
			orderText.append(drinks + "\n");

			String price = DrinksCombList.getSelectedItem().toString();
			PriceList = price.split("\\$");
			total = total + Double.parseDouble(PriceList[1]);
			orderItems = orderItems.trim() + PriceList[0] + "\n";
			DrinksCombList.setSelectedIndex(-1);
			String itemer = drinks.replaceAll("\\s.*","                           ");
			itemMany(itemer);
		}
		
		if (PizzaCombList.getSelectedIndex() != (-1)){
			
			String pizza = PizzaCombList.getSelectedItem().toString();
			orderText.append(pizza + "\n");
		
			String price = PizzaCombList.getSelectedItem().toString();
			PriceList = price.split("\\$");
			total = total + Double.parseDouble(PriceList[1]);
			orderItems = orderItems + PriceList[0] + "\n";

			PizzaCombList.setSelectedIndex(-1);
			String itemer = pizza.replaceAll("\\s.*","                        ");
			itemMany(itemer);
		}
		
		if (SidesCombList.getSelectedIndex() != (-1)){
			
			String sides = SidesCombList.getSelectedItem().toString();
			orderText.append(sides + "\n");

			
			String price = SidesCombList.getSelectedItem().toString();
			PriceList = price.split("\\$");
			total = total + Double.parseDouble(PriceList[1]);
			orderItems = orderItems + PriceList[0] + "\n";

			SidesCombList.setSelectedIndex(-1);
			String itemer = sides.replaceAll("\\s.*","                          ");
			itemMany(itemer);
		}
		
		
	}
	private String getItemsOrder(int orderId) throws IOException, ClassNotFoundException {
		
		message = new Message(orderId, 415);
		clientOutputStream.writeObject(message);
		messageBack = (Message)clientInputStream.readObject();
		String items = messageBack.getOrderItems();
		
		
		return items;
	}
	void updateItemListOrderId(int orderId) throws IOException, ClassNotFoundException {
		message = new Message(orderId, 420);
		clientOutputStream.writeObject(message);
		messageBack = (Message)clientInputStream.readObject();
		String opStatus = messageBack.getOpStatus();
	}
	int itemId(String item) throws IOException, ClassNotFoundException{
		message = new Message(item, 505);
		clientOutputStream.writeObject(message);
		messageBack = (Message)clientInputStream.readObject();
		String opStatus = messageBack.getOpStatus();
		int id = messageBack.getItemId();
		
		return id;
	}
	void itemMany(String item) throws IOException, ClassNotFoundException{
		int itemId = itemId(item);
		String timeStamp = new SimpleDateFormat("ddHHmmss").format(new Date());
		int itemListId = Integer.parseInt(timeStamp);
		message = new Message(410, 0,itemId, itemListId);
		clientOutputStream.writeObject(message);
		messageBack = (Message)clientInputStream.readObject();
		String opStatus = messageBack.getOpStatus();
		
	
		
	}
	void RemoveFromOrderButtonClicked() throws BadLocationException {
		
		orderStatusText.setText("");
		int lines = orderText.getLineCount() -1; 
		int end = orderText.getLineEndOffset(lines);
		 orderText.replaceRange("", 0, end);
		 total = 0;
			 
	}
	
	//Payment Process methods
	void clickBtnPayClose() throws ClassNotFoundException, IOException {
		orderStatusText.setText("");
		String stat = "";
		int status = 0;
		 stat = jbtnUpdateStatClicked();
	
		if (stat.equals("Complete")){
			 status = 1; 
		}
		else if(stat.equals("Processing")){
			status = 1;
		}
		else if(stat.equals("Recieved")){
			status = 1;
		}
		else if(stat.equals("Placed")){
			status = 1;
		}
		else if(stat.equals("Payment Needed")){
			status = 2;
		}
		if (status ==(2)){
			JOptionPane.showMessageDialog(null,"Order Incomplete");
			deleteOrder(orderIdMaster);
			System.exit(0);		
	}
		else {
		JOptionPane.showMessageDialog(null,"Thank you for ordering from the "
				+ "Pizza Store"+"\n"+"Your order will be ready shortly");
		System.exit(0);
			}
	}
	void jbtsubmitPaymentButtonClicked() throws ClassNotFoundException, IOException  {
		String ccNumber = "";
		String ccName = "";
		String ccExp = "";
		int opType = 310;
		int paymentId;
		
		if((! jfcreditCard.getText().isEmpty()) && (jfcreditCard.getText().toString().matches("\\b(?:3[47]\\d{2}([\\s-]?)\\d{6}\\1\\d|(?:(?:4\\d|5[1-5]|65)\\d{2}|6011)([\\s-]?)\\d{4}\\2\\d{4}\\2)\\d{4}\\b"))){
			ccNumber = jfcreditCard.getText();
		} else {
			JOptionPane.showMessageDialog(null, "Invalid Credit Card Number Entered");
			jfcreditCard.requestFocus();
			return;
		}
		
		if((! jfcreditCardName.getText().isEmpty()) && (jfcreditCardName.getText().toString().matches("^[\\p{L} .'-]+$"))){
			ccName = jfcreditCardName.getText();
		} else {
			JOptionPane.showMessageDialog(null, "Invalid Credit Card Name Entered");
			jfcreditCardName.requestFocus();
			return;
		}
		
		if((! jfcreditCardExp.getText().isEmpty()) && (jfcreditCardExp.getText().matches("([0-9]{2}/[0-9]{4})"))) {
			ccExp = jfcreditCardExp.getText();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
			simpleDateFormat.setLenient(false);
			Date expiry = null ;
			try {
				expiry = simpleDateFormat.parse(ccExp);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			boolean expired = expiry.before(new Date());
			if (expired) {
				JOptionPane.showMessageDialog(null, "Invalid Credit Card Expiration Date");
				jfcreditCardExp.requestFocus();
				return;
				
			}
		} 
		
		
		else {
			JOptionPane.showMessageDialog(null, "Invalid Credit Card Expiration Date");
			jfcreditCardExp.requestFocus();
			return;
		}
		String timeStamp = new SimpleDateFormat("ddHHmmss").format(new Date());
		paymentId = Integer.parseInt(timeStamp);
		
		com.pizzastore.data.Payment paid = new Payment(paymentId, ccName, ccNumber,ccExp,orderIdMaster );
		
		message.setPaymentId(paymentId);
		message.setCcName(ccName);
		message.setCcExp(ccExp);
		message.setCcNumber(ccNumber);
		message.setOrderId(orderIdMaster);
		 message = new Message(opType,paymentId,paid);
			try {
				clientOutputStream.writeObject(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		try {
			messageBack = (Message)clientInputStream.readObject();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		jbtnCancel.setEnabled(true);
		jbtnModify.setEnabled(true);
		paymentStatChange();
		
	}
	void paymentStatChange() throws IOException, ClassNotFoundException {
		int orderId;
		String status = "Placed";
		int opType = 220;
		message.setOrderStatus(status);
		message.setOrderId(orderIdMaster);
		message = new Message(opType, status, orderIdMaster);
		clientOutputStream.writeObject(message);
		messageBack = (Message)clientInputStream.readObject();
		String stat = messageBack.getOpStatus();
		
		jltrackOrder.setText("<HTML><FONT COLOR=WHITE SIZE=8>Current Order Status: "+"Payment Recieved Order Placed"+"</FONT>");
		jbtsubmitPayment.setText("Payment Complete, Order "+status+"\n");
		jbtsubmitPayment.setEnabled(false);
		if(messageBack.getOpStatus().contentEquals("S")) {	
	
		 stat = messageBack.getStatus();
	
		}	else {
			JOptionPane.showMessageDialog(null, "  Failed Find Order");
		}	
	
	}
	void cmbOrderIdClick() throws IOException, ClassNotFoundException{
		int opType = 205;
		 message = new Message(opType);
		clientOutputStream.writeObject(message);
		messageBack = (Message)clientInputStream.readObject();
		String results = messageBack.getStatus();
		cmbOrderId.removeAllItems();
		jtaChef.setText("");
		String[] fillIt = results.split(",");
		jtaChef.append("    Order ID's Updated:"+"\n");
		for (String s: fillIt){
			cmbOrderId.addItem(s);
			jtaChef.append("    "+ s +"\n");
		}
	
	if(messageBack.getOpStatus().contentEquals("S")) {
	
	
	}	else {
		JOptionPane.showMessageDialog(null, "  Failed Find Order");
	}
	}
	
	//Chef view methods
			
	void btnChangeStatClicked() throws IOException, ClassNotFoundException {

		int orderId;
		String status = "";
		int opType = 220;
		
		String chefOrId = (String) cmbOrderId.getSelectedItem();
		try{
			orderId = Integer.parseInt(chefOrId);
		}
		catch (Exception al){
			jfChefId.setText("");
			jtaChef.requestFocus();
			jtaChef.setText("  Please enter a valid integer ID number");
			return;
		}

		if (radComplete.isSelected()){
			status = "Complete";
		}
		else if(radInProcess.isSelected()){
			status = "Processing";
		}
		else if(radRecieved.isSelected()){
			status = "Recieved";
		}
		else if(radPlaced.isSelected()){
			status = "Placed";
		}
		message.setOrderStatus(status);
		message.setOrderId(orderId);
		message = new Message(opType, status, orderId);
		clientOutputStream.writeObject(message);
		messageBack = (Message)clientInputStream.readObject();
		String stat = messageBack.getOpStatus();
		
		jtaChef.setText("");
		jtaChef.append("   Order Status Changed To: "+status+"\n");
		if(messageBack.getOpStatus().contentEquals("S")) {	
	
		 stat = messageBack.getStatus();
	
		}	else {
			JOptionPane.showMessageDialog(null, "  Failed Find Order");
		}
		
	}//end status change methood

	void btnViewOrderClicked() throws IOException, ClassNotFoundException {
		int orderId;
		int opType = 200;
		String chefOrId = (String) cmbOrderId.getSelectedItem();
		try{
			orderId = Integer.parseInt(chefOrId);
		}
		catch (Exception al){
			
			jtaChef.requestFocus();
			jtaChef.setText("  Please enter a valid integer ID number");
			return;
		}
		message = new Message(orderId,opType);
			clientOutputStream.writeObject(message);
			messageBack = (Message)clientInputStream.readObject();

		if(messageBack.getOpStatus().contentEquals("S")) {
	
			
			String oId = Integer.toString(messageBack.getOrderId());
			String tot = Double.toString(messageBack.getGrandTotal());
			String pri = Double.toString(messageBack.getPrice());
			String stat = messageBack.getStatus();
			String uId = Integer.toString(messageBack.getUserId());
			String tax = Double.toString(messageBack.getTaxes());
			String detail = messageBack.getOrderItems();
			String items =getItemsOrder(orderId);
			jtaChef.setText("Order ID: "+ oId+", Grand Total: "+ tot + ", Tax: "+tax+ ", Price: "+pri+", Order Status: " + stat + ", Customer ID: "+ uId+"\n" + "Order Details: "+"\n"+items);
			
		}	else {
			JOptionPane.showMessageDialog(null, "  Failed Find Order");
		}

			}//end chef view method

	//Methods for SysAdmin GUI
	void jbtviewMenuButtonClicked () throws IOException, Exception {
		int itemId = Integer.parseInt(jfSysAdminMenuItemID.getText());
		jfSysAdminMenuName.setText("");
		jfSysAdminMenuSize.setText("");
		jfSysAdminMenuPrice.setText("");
		types.setSelectedIndex(-1);
		
		String name = "";
		String type =  "";
		String size= "";
		double unitPrice = 0.0;
	
		com.pizzastore.data.ItemDetail itemDetail = new ItemDetail(itemId, name, type,size, unitPrice);
		message = new Message(500, itemDetail);
		clientOutputStream.writeObject(message);
		messageBack = (Message)clientInputStream.readObject();
		if(messageBack.getOpStatus().contentEquals("S")) {
			SysAdminText.append("Item Id: " + messageBack.getItemDetail().getItemId() + "\n");
			SysAdminText.append("Name: " + messageBack.getItemDetail().getName() + "\n");
			SysAdminText.append("Item List Type: " + messageBack.getItemDetail().getType() + "\n");
			SysAdminText.append("Size: " + messageBack.getItemDetail().getSize() + "\n");
			SysAdminText.append("Unit Price: " + messageBack.getItemDetail().getUnitPrice() + "\n");
		}
		else {
			SysAdminText.append("That ID does Not Exist in the DB" + "\n");
		}
		
	}
	
	void jbtupdateMenuButtonClicked() throws IOException, Exception {
		int itemId = Integer.parseInt(jfSysAdminMenuItemID.getText());
		String name = jfSysAdminMenuName.getText();
		String ourName =  name.replaceAll("\\s", "_");
		String type = types.getSelectedItem().toString();
		String size = jfSysAdminMenuSize.getText();
		double unitPrice = Double.parseDouble(jfSysAdminMenuPrice.getText());
		com.pizzastore.data.ItemDetail itemDetail = new ItemDetail(itemId, ourName, type, size, unitPrice);
		message = new Message(510, itemDetail);
		clientOutputStream.writeObject(message);
		messageBack = (Message)clientInputStream.readObject();
		if(messageBack.getOpStatus().contentEquals("S")) {
			SysAdminText.append("Item Added to DB");
		}
		else if(messageBack.getOpStatus().contentEquals("F")) {
			message = new Message(520, itemDetail);
			clientOutputStream.writeObject(message);
			if(messageBack.getOpStatus().contentEquals("S")) {
				SysAdminText.append("Item Updated");
			}
		}
	}
	
	public void populateMenu() throws IOException, Exception {
		String x = "";
		String y = "";
		String z = "";
		message = new Message(540, x);
		clientOutputStream.writeObject(message);
		messageBack = (Message)clientInputStream.readObject();
		x = messageBack.getPizzaItems();
		PizzaList = x.split(",");
		PizzaList2 = new String[(PizzaList.length / 3)];
		
		for(int i=0 ,j = 0 ; i<PizzaList.length; i += 3, j++) {
			PizzaList2[j] = PizzaList[i] + " " + PizzaList[i+1] + " : $" + PizzaList[i+2];
				
		}
		
		y = messageBack.getDrinksItems();
		DrinksList = y.split(",");
		DrinksList2 = new String[(DrinksList.length / 3)];
		
		for(int i=0 ,j = 0 ; i<DrinksList.length; i += 3, j++) {
			DrinksList2[j] = DrinksList[i] + " " + DrinksList[i+1] + " : $" + DrinksList[i+2];
				
		}
		
		z = messageBack.getSidesItems();
		SidesList = z.split(",");
		SidesList2 = new String[(SidesList.length / 3)];
		
		for(int i=0 ,j = 0 ; i<SidesList.length; i += 3, j++) {
			SidesList2[j] = SidesList[i] + " " + SidesList[i+1] + " : $" + SidesList[i+2];
				
		}
		
		PizzaCombList = new JComboBox(PizzaList2);
		PizzaCombList.setSelectedIndex(-1);
		DrinksCombList = new JComboBox(DrinksList2);
		DrinksCombList.setSelectedIndex(-1);
		SidesCombList = new JComboBox(SidesList2);
		SidesCombList.setSelectedIndex(-1);
		
	}
	
	public void sendMessage() throws IOException, ClassNotFoundException {
		
		message = new Message();
		clientOutputStream.writeObject(message);
		receivingMeesage();
	}
	
	private void receivingMeesage() throws IOException, ClassNotFoundException {
		message = (Message) clientInputStream.readObject();

	}
	
	/** Main method */
	public static void main(String[] args) {
		
		
		try {
			// create the user GUI
			PizzaStoreClient frame = new PizzaStoreClient("localhost", 8031);
			frame.pack();
			frame.setTitle("Welcome to the Pizza Store!");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			frame.setSize(1000, 800);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
			Point newLocation = new Point(middle.x - (frame.getWidth() / 2), 
					middle.y - (frame.getHeight() / 2));
			frame.setLocation(newLocation);
		} catch (Exception ex) {
			String message = "An error occurred in the StaffClient application - the system will exit.";
			JOptionPane.showMessageDialog(null, message);
			ex.printStackTrace();
			System.exit(0);
		}
	}	
}
