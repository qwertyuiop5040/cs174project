import java.awt.event.*;  
import javax.swing.*;  
import java.awt.*;
import java.awt.font.*;
import java.io.*;
public class TellerGUI {  
	static int WIDTH=800;
	static int HEIGHT=600;
	static JFrame f=null;
	static JPanel r=null;
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
    b.setBounds(30,120,150,30);  
    b.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("enter check");
	    	clearJPanel();
	    	boolean good=false;
	    	try{
	    		func();
	    		good=true;
	    	}catch(Exception ee){
	    		ee.printStackTrace();
	    	}
	    	JLabel status=new JLabel("Transaction Successful");
	    	if(!good)
	    		status.setText("Transaction Failure");
	    	r.add(status);
	    	r.repaint();
	    } 
    });  
	f.add(b);
	JButton b9=new JButton("Monthly Statement");  
    b9.setBounds(30,170,150,30);  
    b9.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b9);
	JButton b2=new JButton("List Closed Accounts");  
    b2.setBounds(30,220,150,30);  
    b2.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b2);
	JButton b3=new JButton("Large Transactions Report");  
    b3.setBounds(30,270,150,30);  
    b3.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b3);
	JButton b4=new JButton("Customer Report");  
    b4.setBounds(30,320,150,30);  
    b4.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b4);
	JButton b5=new JButton("Add Interest");  
    b5.setBounds(30,370,150,30);  
    b5.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b5);
	JButton b6=new JButton("Create Account");  
    b6.setBounds(30,420,150,30);  
    b6.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b6);
	JButton b7=new JButton("Delete Closed Accounts and Customers");  
    b7.setBounds(30,470,150,30);  
    b7.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b7);
	JButton b8=new JButton("Delete Transactions");  
    b8.setBounds(30,520,150,30);  
    b8.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
	    	System.out.println("dfs");  
	    } 
    });  
	f.add(b8);
}
public static void main(String[] args) {  
	f=new JFrame();//creating instance of JFrame  

	Font tff = new Font("SansSerif", Font.BOLD, 24);
	JLabel tf=new JLabel("Welcome to the Bank Teller Interface.");
	tf.setFont(tff);
	tf.setBounds(WIDTH/2-260,20,600,100);
	f.add(tf);
	addButtons();
	
	r=new JPanel();
	r.setBounds(200,120,500,430);
	r.setBorder(BorderFactory.createLineBorder(Color.black));
	f.add(r);

	
	f.setSize(WIDTH,HEIGHT);//400 width and 500 height  
	f.setLayout(null);//using no layout managers  
	f.setVisible(true);//making the frame visible  
	}  
}  