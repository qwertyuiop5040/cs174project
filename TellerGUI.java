import java.awt.event.*;  
import javax.swing.*;  
import java.awt.*;
import java.awt.font.*;
import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
// import org.jdesktop.swingx.*;
public class TellerGUI {  
	static int WIDTH=800;
	static int HEIGHT=600;
	static JFrame f=null;
	static JPanel r=null;
	static LocalDate epoch = LocalDate.ofEpochDay(0);
	static LocalDate now = LocalDate.now();

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
	    	System.out.println("enter check");
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
	    	// jt.setBounds(50,200,200,200);
	    	// JTextArea  = new JTextArea();  
			// PromptSupport.setPrompt("Account id", jt);
	    	// boolean good=false;
	    	// try{
	    	// 	func();
	    	// 	good=true;
	    	// }catch(Exception ee){
	    	// 	ee.printStackTrace();
	    	// }
	    	// JLabel status=new JLabel("Transaction Successful");
	    	// if(!good)
	    	// 	status.setText("Transaction Failure");
	    	// r.add(status);
	    	r2.add(accountLabel);
	    	r2.add(jt);
	    	r2.add(al);
	    	r2.add(jt2);
	    	r2.add(button);
	    	// r2.setBounds(50,30,r.getWidth()-100,150);
	    	r.add(r2);
	    	r.repaint();
	    } 
    });  
	f.add(b);
	JButton b9=new JButton("Monthly Statement");  
    b9.setBounds(30,150,150,25);  
    b9.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b9);
	JButton b2=new JButton("List Closed Accounts");  
    b2.setBounds(30,185,150,25);  
    b2.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b2);
	JButton b3=new JButton("Large Transactions Report");  
    b3.setBounds(30,220,150,25);  
    b3.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b3);
	JButton b4=new JButton("Customer Report");  
    b4.setBounds(30,255,150,25);  
    b4.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b4);
	JButton b5=new JButton("Add Interest");  
    b5.setBounds(30,290,150,25);  
    b5.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b5);
	JButton b6=new JButton("Create Account");  
    b6.setBounds(30,325,150,25);  
    b6.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b6);
	JButton b7=new JButton("Delete Closed Accounts and Customers");  
    b7.setBounds(30,360,150,25);  
    b7.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b7);
	JButton b8=new JButton("Delete Transactions");  
    b8.setBounds(30,395,150,25);  
    b8.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b8);
	JButton b12=new JButton("Change Date");
	b12.setForeground(Color.BLUE);  
    b12.setBounds(30,430,150,25);  
    b12.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b12);
	JButton b13=new JButton("Change Interest Rate");
	b13.setForeground(Color.BLUE);  
    b13.setBounds(30,465,150,25);  
    b13.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b13);
	JButton b10=new JButton("Reset and Init DB");
	b10.setForeground(Color.RED);  
    b10.setBounds(30,500,150,25);  
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
    b11.setBounds(30,535,150,25);  
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
	r.setBounds(200,120,500,400);
	// r.setLayout(null);
	r.setBorder(BorderFactory.createLineBorder(Color.black));
	f.add(r);

	
	f.setSize(WIDTH,HEIGHT);//400 width and 500 height  
	f.setLayout(null);//using no layout managers  
	f.setVisible(true);//making the frame visible  
	}  
}  