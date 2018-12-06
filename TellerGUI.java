import java.awt.event.*;  
import javax.swing.*;  
import java.awt.*;
import java.awt.font.*;
import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.sql.*;
// import org.jdesktop.swingx.*;
public class TellerGUI {  
	static int WIDTH=1600;
	static int HEIGHT=600;
	static JFrame f=null;
	static JPanel r=null;
	static LocalDate epoch = LocalDate.ofEpochDay(0);
	static LocalDate now = LocalDate.now();
	static JLabel dat;
public static void clearJPanel(){
	r.removeAll();
	r.revalidate();
	r.repaint();
}
public static void func() throws Exception{
	return;
}

public static void addButtons(){
	JButton b=new JButton("Enter Check");  
    b.setBounds(30,115,150,25);  
    b.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("Enter check");
	    	clearJPanel();
	    	JPanel r2=new JPanel();
	    	GridLayout rl = new GridLayout(0,2);
	    	r2.setLayout(rl);
	    	JLabel accountLabel=new JLabel("Account ID");
	    	JTextField jt=new JTextField(20);
	    	JLabel al=new JLabel("Amount");
	    	JTextField jt2=new JTextField(20);
	    	Button button=new Button("Submit");
	    	button.addActionListener(new ActionListener(){
	    		public void actionPerformed(ActionEvent e){
	    			boolean good=false;
	    			try{
	    				int accountID=Integer.valueOf(jt.getText());
	    				double amount=Double.valueOf(jt2.getText());
	    				BankTransactionSender.enter_check(accountID,amount,ChronoUnit.DAYS.between(epoch, now));
	    				good=true;
	    			}catch(Exception ee){
	    				ee.printStackTrace();
	    			}
	    			JLabel status=new JLabel("Transaction Successful");
			    	if(!good)
			    		status.setText("Transaction Failure");
			    	// r.setLayout(new FlowLayout());
			    	clearJPanel();
			    	r.add(status);
			    	r.repaint();
	    		}
	    	});
	    	r2.add(accountLabel);
	    	r2.add(jt);
	    	r2.add(al);
	    	r2.add(jt2);
	    	r2.add(button);
	    	r.add(r2);
	    	r.repaint();
	    } 
    });  
	f.add(b);
	JButton b9=new JButton("Monthly Statement");  
    b9.setBounds(30,150,150,25);  
    b9.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("Monthly Statement");
	    	clearJPanel();
	    	JPanel r2=new JPanel();
	    	GridLayout rl = new GridLayout(0,2);
	    	r2.setLayout(rl);
	    	JLabel jl=new JLabel("User PIN Number");
	    	JTextField jt=new JTextField(20);
	    	Button button=new Button("Submit");
	    	button.addActionListener(new ActionListener(){
	    		public void actionPerformed(ActionEvent e){
	    			boolean good=false;
	    			MonthlyStatementResultSet msrs=null;
	    			String pin="";
	    			try{
	    				pin=jt.getText();
	    				 msrs=BankTransactionSender.monthly_statement(pin,ChronoUnit.DAYS.between(epoch, now));

	    				good=true;
	    			}catch(Exception ee){
	    				ee.printStackTrace();
	    			}
	    			clearJPanel();
	    			JLabel failed=new JLabel("Transaction Failure");
			    	if(!good){
			    		r.add(failed);
			    	}else{
			    		JPanel r3=new JPanel();
			    		r3.setLayout(new BoxLayout(r3, BoxLayout.Y_AXIS));
			    		try{
				    		JLabel jl=new JLabel("Monthly Statement for user: "+pin);
				    		r3.add(jl);
				    		double balanceSum=0.0;
				    		for(int i=0;i<msrs.balances.size();i++){
				    			balanceSum+=msrs.balances.get(i);
				    		}
				    		if(balanceSum>100000){
				    			JLabel jll=new JLabel("Sum of balances exceeds 100,000, limit of insurance reached");
				    			r3.add(jll);
				    		}
				    		boolean notTransactionsDone=msrs.transactions.next();
				    		int currentAid;
				    		Iterator it=msrs.owners.iterator();
				    		Iterator bit=msrs.balances.iterator();
				    		boolean notDone=it.hasNext();
				    		while(notDone){
				    			OwnerTuple ot=(OwnerTuple) it.next();
				    			currentAid=Integer.valueOf(ot.aid);
				    			JLabel jla=new JLabel("Account "+currentAid+":");
				    			// JLabel jlo=new JLabel("Owners:");
				    			r3.add(jla);
				    			// r3.add(jlo);
				    			JLabel nameTitle=new JLabel("Name");
				    			JLabel addrTitle=new JLabel("Address");
				    			JPanel r4=new JPanel();
				    			r4.setLayout(new GridLayout(0,2));
				    			r4.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
				    			nameTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    			r4.add(nameTitle);
				    			addrTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    			r4.add(addrTitle);
				    			while(currentAid==Integer.valueOf(ot.aid)){
				    				JLabel j1=new JLabel(ot.name);
				    				j1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    				r4.add(j1);
				    				JLabel j2=new JLabel(ot.address);
				    				j2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    				r4.add(j2);
				    				if(it.hasNext())ot=(OwnerTuple) it.next();
				    				boolean notDone2=it.hasNext();
				    				if(!notDone2){
				    					notDone=notDone2;
				    					break;
				    				}
				    			}
				    			JPanel r5=new JPanel();
				    			r5.setLayout(new GridLayout(0,7));
				    			r5.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
				    			JLabel tid=new JLabel("Transaction ID");
				    			tid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    			JLabel ttype=new JLabel("Transaction Type");
				    			ttype.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    			JLabel tacc=new JLabel("Account");
				    			tacc.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    			JLabel tacc2=new JLabel("Account 2");
				    			tacc2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    			JLabel tam=new JLabel("Amount");
				    			tam.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    			JLabel tch=new JLabel("Check ID");
				    			tch.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    			JLabel dt=new JLabel("Date");
				    			dt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    			r5.add(tid);
				    			r5.add(ttype);
				    			r5.add(tacc);
				    			r5.add(tacc2);
				    			r5.add(tam);
				    			r5.add(tch);
				    			r5.add(dt);
				    			double fb=(double)bit.next();
				    			double ib=fb;
				    			if(notTransactionsDone){
				    				System.out.println(currentAid);
				    				System.out.println(msrs.transactions.getInt("aid"));
				    				int aid=0;
				    				while(currentAid==(aid=msrs.transactions.getInt("aid"))){
				    					// System.out.println("HIIIIIIIII");
				    					JLabel j1=new JLabel(msrs.transactions.getString("tid"));
				    					j1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    					String type=msrs.transactions.getString("type");
				    					JLabel j2=new JLabel(type);
				    					j2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    					String aid1=msrs.transactions.getString("aid1");
				    					JLabel j3=new JLabel(aid1);
				    					j3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    					JLabel j4=new JLabel(msrs.transactions.getString("aid2"));
				    					j4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    					String amount=msrs.transactions.getString("amount");
				    					JLabel j5=new JLabel(amount);
				    					j5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    					JLabel j6=new JLabel(msrs.transactions.getString("checkID"));
				    					j6.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    					LocalDate cp=epoch.plusDays(Integer.valueOf(msrs.transactions.getString("daysSince1970")));
				    					JLabel j7=new JLabel(cp.toString());
				    					j7.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				    					ib+=TransactionSender.update_balance(type,Double.valueOf(amount),Integer.valueOf(aid1),aid);
				    					r5.add(j1);
				    					r5.add(j2);
				    					r5.add(j3);
				    					r5.add(j4);
				    					r5.add(j5);
				    					r5.add(j6);
				    					r5.add(j7);
				    					notTransactionsDone=msrs.transactions.next();
				    					if(!notTransactionsDone)break;
				    				}
				    			}
				    			JLabel bal=new JLabel("Initial Balance: "+ib);
				    			JLabel fbal=new JLabel("Final Balance: "+fb);
				    			r3.add(bal);
				    			r3.add(fbal);
				    			r3.add(r4);
				    			r3.add(r5);


				    		}
				    	}catch(Exception eee){
				    		eee.printStackTrace();
				    		r3.add(new JLabel("Result Set Key Failure"));
				    	}
			    		r.add(r3);
			    	}
			    	// r.setLayout(new FlowLayout());
			    	
			    	r.repaint();
	    		}
	    	});
	    	r2.add(jl);
	    	r2.add(jt);
	    	r2.add(button);
	    	r.add(r2);
	    	r.repaint();
	    } 
    });  
	f.add(b9);
	JButton b2=new JButton("List Closed Accounts");  
    b2.setBounds(30,185,150,25);  
    b2.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	boolean good=false;
			ResultSet rs=null;
			try{
				rs=BankTransactionSender.list_closed_accounts(ChronoUnit.DAYS.between(epoch, now));
				good=true;
			}catch(Exception ee){
				ee.printStackTrace();
			}
	    	clearJPanel();
	    	if(!good){
				JLabel failed=new JLabel("Transaction Failed");
	    		r.add(failed);
	    	}else{
	    		JPanel r3=new JPanel();
	    		r3.setLayout(new GridLayout(0,1));
	    		try{
		    		JLabel jl=new JLabel("Closed Account aids: ");
		    		r3.add(jl);
		    		while(rs.next()){
		    			JLabel jl2=new JLabel(rs.getString("aid1"));
		    			
		    			r3.add(jl2);
		    		}
		    	}catch(Exception eee){
		    		eee.printStackTrace();
		    		r3.add(new JLabel("ResultSet Key Failure"));
		    	}
	    		r.add(r3);
	    	}
	    	// r.setLayout(new FlowLayout());
	    	r.repaint(); 
	    } 
    });  
	f.add(b2);
	JButton b3=new JButton("Large Transactions Report");  
    b3.setBounds(30,220,150,25);  
    b3.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	boolean good=false;
			ResultSet rs=null;
			try{
				rs=BankTransactionSender.large_transactions_report();
				good=true;
			}catch(Exception ee){
				ee.printStackTrace();
			}
	    	clearJPanel();
	    	if(!good){
				JLabel failed=new JLabel("Transaction Failed");
	    		r.add(failed);
	    	}else{
	    		JPanel r3=new JPanel();
	    		r3.setLayout(new GridLayout(0,2));
	    		try{
		    		JLabel jl=new JLabel("PIN: ");
		    		r3.add(jl);
		    		JLabel jl2=new JLabel("Name: ");
		    		r3.add(jl2);
		    		while(rs.next()){
		    			r3.add(new JLabel(rs.getString("pin")));
		    			r3.add(new JLabel(String.valueOf(rs.getString("name"))));

		    		}
		    	}catch(Exception eee){
		    		r3.add(new JLabel("ResultSet Key Failure"));
		    	}
	    		r.add(r3);
	    	}
	    	// r.setLayout(new FlowLayout());
	    	r.repaint(); 
	    } 
    });  
	f.add(b3);
	JButton b4=new JButton("Customer Report");  
    b4.setBounds(30,255,150,25);  
    b4.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	clearJPanel();
	    	JPanel r2=new JPanel();
	    	GridLayout rl = new GridLayout(0,2);
	    	r2.setLayout(rl);
	    	JLabel jl=new JLabel("User PIN Number");
	    	JTextField jt=new JTextField(20);
	    	Button button=new Button("Submit");
	    	button.addActionListener(new ActionListener(){
	    		public void actionPerformed(ActionEvent e){
	    			boolean good=false;
	    			ResultSet rs=null;
	    			String pin="";
	    			try{
	    				pin=jt.getText();
	    				rs=BankTransactionSender.customer_report(pin);

	    				good=true;
	    			}catch(Exception ee){
	    				ee.printStackTrace();
	    			}
	    			clearJPanel();
	    			JLabel failed=new JLabel("Transaction Failure");
			    	if(!good){
			    		r.add(failed);
			    	}else{
			    		JPanel r3=new JPanel();
			    		r3.setLayout(new GridLayout(0,2));
			    		try{
				    		JLabel jl=new JLabel("Account: ");
				    		r3.add(jl);
				    		JLabel jl2=new JLabel("Closed: ");
				    		r3.add(jl2);
				    		while(rs.next()){
				    			r3.add(new JLabel(rs.getString("aid")));
				    			r3.add(new JLabel(String.valueOf(rs.getBoolean("closed"))));

				    		}
				    	}catch(Exception eee){
				    		r3.add(new JLabel("Result Set Key Failure"));
				    	}
			    		r.add(r3);
			    	}
			    	r.repaint();
	    		}
	    	});
	    	r2.add(jl);
	    	r2.add(jt);
	    	r2.add(button);
	    	r.add(r2);
	    	r.repaint();
	    } 
    });  
	f.add(b4);
	JButton b5=new JButton("Add Interest");  
    b5.setBounds(30,290,150,25);  
    b5.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	clearJPanel();
	    	JPanel r2=new JPanel();
	    	GridLayout rl = new GridLayout(0,2);
	    	r2.setLayout(rl);
	    	JLabel jl=new JLabel("Account ID");
	    	JTextField jt=new JTextField(20);
	    	Button button=new Button("Submit");
	    	button.addActionListener(new ActionListener(){
	    		public void actionPerformed(ActionEvent e){
	    			boolean good=false;
	    			try{
	    				BankTransactionSender.add_interest(Integer.valueOf(jt.getText()),ChronoUnit.DAYS.between(epoch, now));
	    				good=true;
	    			}catch(Exception ee){
	    				ee.printStackTrace();
	    			}
	    			clearJPanel();
	    			JLabel status=new JLabel("Transaction Successful");
			    	if(!good)
			    		status.setText("Transaction Failed");
			    	r.add(status);
			    	r.repaint();
	    		}
	    	});
	    	r2.add(jl);
	    	r2.add(jt);
	    	r2.add(button);
	    	r.add(r2);
	    	r.repaint();
	    } 
    });  
	f.add(b5);
	JButton b6=new JButton("Create Account");  
    b6.setBounds(30,325,150,25);  
    b6.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	clearJPanel();
	    	JPanel r2=new JPanel();
	    	GridLayout rl = new GridLayout(0,2);
	    	r2.setLayout(rl);
	    	JLabel jl=new JLabel("New Account ID");
	    	JTextField jt=new JTextField(9);
	    	JLabel jl5=new JLabel("Linked Account ID (if a pocket account)");
	    	JTextField jt5=new JTextField(9);
	    	JLabel jl2=new JLabel("Type of Account");
	    	JTextField jt2=new JTextField(20);
	    	JLabel jl3=new JLabel("Initial Balance");
	    	JTextField jt3=new JTextField(20);
	    	JLabel jl4=new JLabel("Owner PINs (Primary first, separate by comma)");
	    	JTextField jt4=new JTextField(60);
	    	Button button=new Button("Submit");
	    	button.addActionListener(new ActionListener(){
	    		public void actionPerformed(ActionEvent e){
	    			boolean good=false;
	    			String pin="";
	    			try{
	    				pin=jt.getText();
	    				ArrayList<String> list = new ArrayList<String>(Arrays.asList(jt4.getText().replaceAll("\\s","").split(",")));
	    				int i5=(jt5.getText().equals(""))?0:Integer.valueOf(jt5.getText());
	    				BankTransactionSender.create_account(Integer.valueOf(jt.getText()),i5,jt2.getText(),Double.valueOf(jt3.getText()),list);
	    				good=true;
	    			}catch(Exception ee){
	    				ee.printStackTrace();
	    			}
	    			clearJPanel();
	    			JLabel status=new JLabel("Transaction Successful");
			    	if(!good)
			    		status.setText("Transaction Failed");
			    	r.add(status);
			    	r.repaint();
	    		}
	    	});
	    	r2.add(jl);
	    	r2.add(jt);
	    	r2.add(jl5);
	    	r2.add(jt5);
	    	r2.add(jl2);
	    	r2.add(jt2);
	    	r2.add(jl3);
	    	r2.add(jt3);
	    	r2.add(jl4);
	    	r2.add(jt4);
	    	r2.add(button);
	    	r.add(r2);
	    	r.repaint(); 
	    } 
    });  
	f.add(b6);
	JButton b7=new JButton("Delete Closed Accounts and Customers");  
    b7.setBounds(30,360,150,25);  
    b7.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	boolean good=false;
			try{
				BankTransactionSender.delete_closed_accounts_and_customers();
				good=true;
			}catch(Exception ee){
				ee.printStackTrace();
			}
			JLabel status=new JLabel("Transaction Deleted");
	    	if(!good)
	    		status.setText("Failed to delete transactions");
	    	// r.setLayout(new FlowLayout());
	    	clearJPanel();
	    	r.add(status);
	    	r.repaint();  
	    } 
    });  
	f.add(b7);
	JButton b8=new JButton("Delete Transactions");  
    b8.setBounds(30,395,150,25);  
    b8.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	boolean good=false;
			try{
				BankTransactionSender.delete_transactions();
				good=true;
			}catch(Exception ee){
				ee.printStackTrace();
			}
			JLabel status=new JLabel("Transaction Deleted");
	    	if(!good)
	    		status.setText("Failed to delete transactions");
	    	// r.setLayout(new FlowLayout());
	    	clearJPanel();
	    	r.add(status);
	    	r.repaint(); 
	    } 
    });  
	f.add(b8);
	// JButton b12=new JButton("Change Date");
	// b12.setForeground(Color.BLUE);  
 //    b12.setBounds(30,430,150,25);  
 //    b12.addActionListener(new ActionListener(){  
 //    	public void actionPerformed(ActionEvent e){  
	//     	System.out.println("dfs");  
	//     } 
 //    });  
	// f.add(b12);
	// JButton b13=new JButton("Change Interest Rate");
	// b13.setForeground(Color.BLUE);  
 //    b13.setBounds(30,465,150,25);  
 //    b13.addActionListener(new ActionListener(){  
 //    	public void actionPerformed(ActionEvent e){  
	//     	System.out.println("dfs");  
	//     } 
 //    });  
	// f.add(b13);
	JButton b14=new JButton("Set Date");
	b14.setForeground(Color.BLUE);  
    b14.setBounds(30,500,150,25);  
    b14.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){
    		JPanel r2=new JPanel();
	    	GridLayout rl = new GridLayout(0,4);
	    	JLabel dt=new JLabel("Date MM DD YYYY");
	    	JTextField m=new JTextField(2);
	    	JTextField d=new JTextField(2);
	    	JTextField y=new JTextField(4);
	    	Button b=new Button("Submit");
	    	b.addActionListener(new ActionListener(){  
    			public void actionPerformed(ActionEvent e){
    				JLabel status=new JLabel("Date change failed");
    				try{
    					now=LocalDate.of(Integer.valueOf(y.getText()), Integer.valueOf(m.getText()), Integer.valueOf(d.getText()));
    					System.out.println(ChronoUnit.DAYS.between(epoch, now));
    					dat.setText(now.toString());
    					status.setText("Date change successful");
    				}catch(Exception ee){
    					ee.printStackTrace();
    				}
    				clearJPanel();
    				r.add(status);
    				f.repaint();
    			}
    		});
    		
    		r2.add(dt);
    		r2.add(m);
    		r2.add(d);
    		r2.add(y);
    		r2.add(b);
    		clearJPanel();
    		r.add(r2);
    		r.repaint();
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b14);
	JButton b15=new JButton("Set Interest Rate");
	b15.setForeground(Color.BLUE);  
    b15.setBounds(30,535,150,25);  
    b15.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	clearJPanel();
	    	JPanel r2=new JPanel();
	    	GridLayout rl = new GridLayout(0,2);
	    	r2.setLayout(rl);
	    	JLabel jl=new JLabel("Type of Account");
	    	JTextField jt=new JTextField(20);
	    	JLabel jl2=new JLabel("New Interest Rate");
	    	JTextField jt2=new JTextField(20);
	    	Button button=new Button("Submit");
	    	button.addActionListener(new ActionListener(){
	    		public void actionPerformed(ActionEvent e){
	    			boolean good=false;
	    			try{
	    				BankTransactionSender.set_interest_rate(jt.getText(),Double.valueOf(jt2.getText().replaceAll("\\s","")));
	    				good=true;
	    			}catch(Exception ee){
	    				ee.printStackTrace();
	    			}
	    			JLabel status=new JLabel("Transaction Successful");
			    	if(!good)
			    		status.setText("Transaction Failure");
			    	// r.setLayout(new FlowLayout());
			    	clearJPanel();
			    	r.add(status);
			    	r.repaint();
	    		}
	    	});
	    	r2.add(jl);
	    	r2.add(jt);
	    	r2.add(jl2);
	    	r2.add(jt2);
	    	r2.add(button);
	    	r.add(r2);
	    	r.repaint();  
	    } 
    });  
	f.add(b15);
	JButton b10=new JButton("Reset and Init DB");
	b10.setForeground(Color.RED);  
    b10.setBounds(200,535,150,25);  
    b10.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
    		boolean good=false;
			try{
				DatabaseInitializer.wipeDatabase();
				DatabaseInitializer.wipeDatabase();
		    	DatabaseInitializer.initializeDatabase();
		    	DatabaseInitializer.addDefaultData();
	    		good=true;
			}catch(Exception ee){
				ee.printStackTrace();
			}
			JLabel status=new JLabel("Initialization Complete");
	    	if(!good)
	    		status.setText("Initialization Failure");
	    	// r.setLayout(new FlowLayout());
	    	System.out.println("Init complete");
	    	clearJPanel();
	    	r.add(status);
	    	System.out.println("Init complete2");
	    	r.repaint();  
	    	
	    } 
    });  
	f.add(b10);
	JButton b11=new JButton("Wipe Datebase");
	b11.setForeground(Color.RED);  
    b11.setBounds(370,535,150,25);  
    b11.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){
    		boolean good=false;
			try{
				DatabaseInitializer.wipeDatabase();
				DatabaseInitializer.wipeDatabase();
	    		good=true;
			}catch(Exception ee){
				ee.printStackTrace();
			}
			JLabel status=new JLabel("Datebase Wipe Complete");
	    	if(!good)
	    		status.setText("Initialization Failure");
	    	// r.setLayout(new FlowLayout());
	    	clearJPanel();
	    	r.add(status);
	    	r.repaint();  
	    	
	    }  
    });  
	f.add(b11);
}
public static void main(String[] args) {
	DBConnection.getInstance();  
	f=new JFrame();//creating instance of JFrame  
	f.addWindowListener(new java.awt.event.WindowAdapter() {
	    @Override
	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	        DBConnection.getInstance().close();
	        System.exit(0);
	    }
	});
	Font tff = new Font("SansSerif", Font.BOLD, 24);
	JLabel tf=new JLabel("Welcome to the Bank Teller Interface.");
	tf.setFont(tff);
	tf.setBounds(WIDTH/2-260,20,600,100);
	f.add(tf);
	addButtons();
	
	r=new JPanel();

	// r.setLayout(null);
	JScrollPane jsp = new JScrollPane(r);
	jsp.setBorder(BorderFactory.createLineBorder(Color.black));
	jsp.setBounds(200,120,1000,400);
	f.add(jsp);
	System.out.println(now.toString());
	dat=new JLabel(now.toString());
	// dat.setText(now.toString());
	dat.setBounds(550,535,200,25);
	f.add(dat);
	f.setSize(WIDTH,HEIGHT);//400 width and 500 height  
	f.setLayout(null);//using no layout managers  
	f.setVisible(true);//making the frame visible  
	}  
}  