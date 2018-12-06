import java.sql.*;
import javax.swing.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.awt.*;
import java.awt.event.*;


public class UserGUI {
	//database connection object
	DBConnection dbc;
	
	//frame, panel, and layout
	JFrame frame;
	JPanel panel;
	GridLayout layout;
	
	//test/debug/demo operation fields/labels/buttons
	JTextField mmField;
	JTextField ddField;
	JTextField yyyyField;
	JLabel dateLabel;
	JButton dateButton;
	
	JTextField rateField;
	JLabel rateLabel;
	JButton interestCheckingRateButton;
	JButton studentCheckingRateButton;
	JButton savingsRateButton;
	JButton pocketRateButton;
	
	//login field + label + button
	JTextField pinField;
	JLabel pinLabel;
	JButton loginButton;
	
	//transaction buttons
	JButton DepositButton;
	JButton TopUpButton;
	JButton WithdrawButton;
	JButton PurchaseButton;
	JButton TransferButton;
	JButton CollectButton;
	JButton WireButton;
	JButton PayFriendButton;
	
	//parameter fields + labels
	JTextField aid1Field;
	JTextField aid2Field;
	JTextField amountField;
	JLabel aid1Label;
	JLabel aid2Label;
	JLabel amountLabel;
	
	//output text area
	JTextArea outputArea;
	
	//transaction parameter values, get from user fields
	int aid1;							//source account
	int aid2;							//destination account
	double amount;
	
	//user login session information
	String pin;
	User user;
	boolean loggedIn = false;
	
	long date;	//to pass to transaction functions
	
	public UserGUI(){
		//initialize dbc
		dbc = DBConnection.getInstance();
		
		//initialize date
		LocalDate now = LocalDate.now();
		LocalDate epoch = LocalDate.ofEpochDay(0);
		date = ChronoUnit.DAYS.between(epoch, now);
		
		//set up frame and panel
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("ATM Interface");
		frame.setSize(800, 600);
		frame.setResizable(false);
		JFrame.setDefaultLookAndFeelDecorated(true);

		//ensure that database connection is terminated when GUI window is closed
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent){
				dbc.close();
			}
		});
		
		layout = new GridLayout(18, 4, 10, 10);
		panel = new JPanel(layout);
	
		//create and add widgets to panel
		addWidgets();
	
		//add panel to window
		frame.getContentPane().add(panel);

		//finally, display GUI
		frame.pack();
		frame.setVisible(true);
	}
	
	private boolean updateParameters(){
		//first verify that user is logged in, else can't perform transaction
		if(!loggedIn){
			outputArea.selectAll();
			outputArea.replaceSelection("You must first login before attempting any transaction.");
			return false;
		}
		
		int newAid1 = Integer.parseInt(aid1Field.getText());
		int newAid2 = Integer.parseInt(aid2Field.getText());
		double newAmount = Double.parseDouble(amountField.getText());
		
		return true;
	}
	
	private void transactionSuccessful(){
		outputArea.selectAll();
		outputArea.replaceSelection("Action successfully completed!");
	}
	
	private void addWidgets() {
		
		//test/debug/demo operation fields/labels/buttons
		mmField = new JTextField(2);
		ddField = new JTextField(2);
		yyyyField = new JTextField(4);
		dateLabel = new JLabel("New date");
		dateButton = new JButton("Update Date");
	
		rateField = new JTextField(10);
		rateLabel = new JLabel("New interest rate");
		interestCheckingRateButton = new JButton("Update Interest-Checking");
		studentCheckingRateButton = new JButton("Update Student-Checking");
		savingsRateButton = new JButton("Update Savings");
		pocketRateButton = new JButton("Update Pocket");
		
		//create login label + field + button
		pinField = new JTextField(10);
		pinLabel = new JLabel("Enter PIN");
		loginButton = new JButton("Login");
		
		//create transaction buttons
		DepositButton = new JButton("Deposit");
		TopUpButton = new JButton("Top-Up");
		WithdrawButton = new JButton("Withdraw");
		PurchaseButton = new JButton("Purchase");
		TransferButton = new JButton("Transfer");
		CollectButton = new JButton("Collect");
		WireButton = new JButton("Wire");
		PayFriendButton = new JButton("Pay-Friend");
		
		//create parameter labels + fields
		aid1Field = new JTextField(10);
		aid2Field = new JTextField(10);
		amountField = new JTextField(10);
		aid1Label = new JLabel("Source Account ID");
		aid2Label = new JLabel("Destination Account ID");
		amountLabel = new JLabel("Amount");
		
		//create output textArea
		outputArea = new JTextArea(5, 20);
		
		//add actionlisteners to log in button
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String userPin = pinField.getText();
				try{
					user = TransactionSender.getUser(pin);
					pin = userPin;
					loggedIn = true;
				}catch(Exception ex) {
					outputArea.selectAll();
					outputArea.replaceSelection("User PIN verification failed. Try again.");
				}
				transactionSuccessful();
			}
		});
		
		//add actionlisteners to test/debug/demo operation buttons
		dateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String yyyy = yyyyField.getText();
				String mm = mmField.getText();
				String dd = ddField.getText();
				
				String dateInput = yyyy + "-" + mm + "-" + dd;
				try{
					LocalDate newDate = LocalDate.parse(dateInput);
					LocalDate epoch = LocalDate.ofEpochDay(0);
					date = ChronoUnit.DAYS.between(epoch, newDate);					
				} catch(Exception ex){
					outputArea.selectAll();
					outputArea.replaceSelection("Error parsing date. Please enter YYYY, MM, and DD as integers.");
				}

			}
		});
		
		interestCheckingRateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				double newRate = Double.parseDouble(rateField.getText());
				try{
					ResultSet rs = dbc.sendQuery("UPDATE Rate " + 
											 "SET rate = " + newRate + " " + 
											 "WHERE type = 'Interest-Checking'");
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
		studentCheckingRateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				double newRate = Double.parseDouble(rateField.getText());
				try{
				ResultSet rs = dbc.sendQuery("UPDATE Rate " + 
											 "SET rate = " + newRate + " " + 
											 "WHERE type = 'Student-Checking'");	
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});	
		
		savingsRateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				double newRate = Double.parseDouble(rateField.getText());
				try{
				ResultSet rs = dbc.sendQuery("UPDATE Rate " + 
											 "SET rate = " + newRate + " " + 
											 "WHERE type = 'Savings'");	
				}catch(Exception ex){
					ex.printStackTrace();
				}				
			}
		});
		
		pocketRateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				double newRate = Double.parseDouble(rateField.getText());
				try{
				ResultSet rs = dbc.sendQuery("UPDATE Rate " + 
											 "SET rate = " + newRate + " " + 
											 "WHERE type = 'Pocket'");	
				}catch(Exception ex){
					ex.printStackTrace();
				}						
			}
		});
		
		//add actionlisteners to transaction buttons
		DepositButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!updateParameters()) return;
				try{
					TransactionSender.deposit(TransactionSender.getAccount(aid2), amount, date);
				}catch(Exception ex) {
					String errorMessage = ex.getMessage();
					outputArea.selectAll();
					outputArea.replaceSelection(errorMessage);
				}
				transactionSuccessful();
			}
		});
		
		TopUpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!updateParameters()) return;
				try{
					TransactionSender.top_up(TransactionSender.getAccount(aid2), TransactionSender.getAccount(aid1), amount, date);
				}catch(Exception ex) {
					String errorMessage = ex.getMessage();
					outputArea.selectAll();
					outputArea.replaceSelection(errorMessage);
				}
				transactionSuccessful();
			}
		});
		
		WithdrawButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!updateParameters()) return;
				try{
					TransactionSender.withdraw(TransactionSender.getAccount(aid1), amount, date);
				}catch(Exception ex) {
					String errorMessage = ex.getMessage();
					outputArea.selectAll();
					outputArea.replaceSelection(errorMessage);
				}
				transactionSuccessful();
			}
		});
		
		PurchaseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!updateParameters()) return;
				try{
					TransactionSender.purchase(TransactionSender.getAccount(aid1), amount, date);
				}catch(Exception ex) {
					String errorMessage = ex.getMessage();
					outputArea.selectAll();
					outputArea.replaceSelection(errorMessage);
				}
				transactionSuccessful();
			}
		});
		
		TransferButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!updateParameters()) return;

				//verify that user owns both accounts
				boolean verifiedSource = false;				
				try{
					ResultSet rs1 = dbc.sendQuery("SELECT O.pin " + 
											  "FROM Owner O " + 
											  "WHERE O.aid = " + aid1);
					while(rs1.next()){
						String ownerPin = rs1.getString("pin");
						if(ownerPin.equals(pin)) verifiedSource = true;
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				boolean verifiedDest = false;
				try{
					ResultSet rs2 = dbc.sendQuery("SELECT O.pin " + 
											  "FROM Owner O " + 
											  "WHERE O.aid = " + aid2);
					while(rs2.next()){
						String ownerPin = rs2.getString("pin");
						if(ownerPin.equals(pin)) verifiedDest = true;
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				if(!verifiedSource || !verifiedDest){
					outputArea.selectAll();
					outputArea.replaceSelection("You do not own both the source and destination accounts. Try again.");
					return;
				}
				
				try{
					TransactionSender.transfer(TransactionSender.getAccount(aid1), TransactionSender.getAccount(aid2), amount, date);
				}catch(Exception ex) {
					String errorMessage = ex.getMessage();
					outputArea.selectAll();
					outputArea.replaceSelection(errorMessage);
				}
				transactionSuccessful();
			}
		});
		
		CollectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!updateParameters()) return;
				try{
					TransactionSender.collect(TransactionSender.getAccount(aid1), TransactionSender.getAccount(aid2), amount, date);
				}catch(Exception ex) {
					String errorMessage = ex.getMessage();
					outputArea.selectAll();
					outputArea.replaceSelection(errorMessage);
				}
				transactionSuccessful();
			}
		});
		
		WireButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!updateParameters()) return;
				
				//verify that user owns source account
				boolean verified = false;
				try{
					ResultSet rs = dbc.sendQuery("SELECT O.pin " + 
											 "FROM Owner O " + 
											 "WHERE O.aid = " + aid1);
					while(rs.next()){
						String ownerPin = rs.getString("pin");
						if(ownerPin.equals(pin)) verified = true;
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}

				if(!verified){
					outputArea.selectAll();
					outputArea.replaceSelection("You do not own the source account. Try again.");
					return;
				}
				
				try{
					TransactionSender.wire(TransactionSender.getAccount(aid1), TransactionSender.getAccount(aid2), amount, date);
				}catch(Exception ex) {
					String errorMessage = ex.getMessage();
					outputArea.selectAll();
					outputArea.replaceSelection(errorMessage);
				}
				transactionSuccessful();
			}
		});
		
		PayFriendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!updateParameters()) return;
				try{
					TransactionSender.pay_friend(TransactionSender.getAccount(aid1), TransactionSender.getAccount(aid2), amount, date);
				}catch(Exception ex) {
					String errorMessage = ex.getMessage();
					outputArea.selectAll();
					outputArea.replaceSelection(errorMessage);
				}
				transactionSuccessful();
			}
		});
		
		//add widgets to panel
		panel.add(dateLabel, 0, 0);
		panel.add(mmField, 0, 1);
		panel.add(ddField, 1, 1);
		panel.add(yyyyField, 2, 1);
		panel.add(dateButton, 0, 3);
		
		panel.add(rateLabel, 3, 0);
		panel.add(rateField, 3, 1);
		panel.add(interestCheckingRateButton, 3, 3);
		panel.add(studentCheckingRateButton, 4, 3);
		panel.add(savingsRateButton, 5, 3);
		panel.add(pocketRateButton, 6, 3);
		
		panel.add(pinLabel, 8, 0);
		panel.add(pinField, 8, 1);
		panel.add(loginButton, 8, 3);
		
		panel.add(aid1Label, 10, 0);
		panel.add(aid1Field, 10, 1);
		panel.add(aid2Label, 11, 0);
		panel.add(aid2Field, 11, 1);
		panel.add(amountLabel, 12, 0);
		panel.add(amountField, 12, 1);
		
		panel.add(DepositButton, 10, 3);
		panel.add(TopUpButton, 11, 3);
		panel.add(WithdrawButton, 12, 3);
		panel.add(PurchaseButton, 13, 3);
		panel.add(TransferButton, 14, 3);
		panel.add(CollectButton, 15, 3);
		panel.add(WireButton, 16, 3);
		panel.add(PayFriendButton, 17, 3);
	}
}

