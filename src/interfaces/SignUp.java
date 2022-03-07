/**========================================================================================================================================================
 *______  __    __       ___   .___________.    ___      .______   .______    __       __    ______     ___   .___________. __    ______   .__   __. 
 /      ||  |  |  |     /   \  |           |   /   \     |   _  \  |   _  \  |  |     |  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  | 
|  ,----'|  |__|  |    /  ^  \ `---|  |----`  /  ^  \    |  |_)  | |  |_)  | |  |     |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  | 
|  |     |   __   |   /  /_\  \    |  |      /  /_\  \   |   ___/  |   ___/  |  |     |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  | 
|  `----.|  |  |  |  /  _____  \   |  |     /  _____  \  |  |      |  |      |  `----.|  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   | 
 \______||__|  |__| /__/     \__\  |__|    /__/     \__\ | _|      | _|      |_______||__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__| 
                                                                                                                                                     
 * Made by: Chyle Andrei Lee &
 * 			Clyde Adrian Ramos
 * ========================================================================================================================================================
 */
package interfaces;
//import packages
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

import dbBackEnd.DBFunc;

@SuppressWarnings("serial")
public class SignUp extends javax.swing.JFrame {
	//declaration of variables
    DefaultTableModel model;
	private JPanel contentPane;
	private JTextField userName;
	private JPasswordField password;
	private JPasswordField r_password;
	private JLabel lblCreateYourAccount;
	private JButton confirm;
	private JButton here;
	private JLabel lblAlreadyHaveAn;
	private JLabel msgeer;
	ArrayList<String> getVal;

//gui setter
	public SignUp() {
		setTitle("Sign-Up");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 273, 230);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userName = new JTextField();
		userName.setBounds(108, 45, 137, 20);
		contentPane.add(userName);
		userName.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(108, 73, 137, 20);
		contentPane.add(password);
		
		r_password = new JPasswordField();
		r_password.setBounds(108, 98, 137, 20);
		contentPane.add(r_password);

//create account
		confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				//declaration of variables
				String query = "INSERT INTO TUSER(USER_NAME, USER_PASSWORD) VALUES (?, ?)";
				DBFunc db= new DBFunc();
				ArrayList<String> arry= new ArrayList<String>();
				//connection
				db.piConnection="jdbc:oracle:thin:@localhost:1521:orcl";
				db.piUserName="chat as sysdba";
				db.piPassword="chatapp";
				//check if all fields have values
				if (userName.getText().equals("") || password.getText().equals("") || r_password.getText().equals("")){
		            JOptionPane.showMessageDialog(msgeer, "Error! Fill all data");
		        } else {
		        	//check if password and repeat password are the same
		        	if(password.getText().equals(r_password.getText())) {
		        		//check if username or password is existing
		        		String queryCheck="SELECT USER_NAME,USER_PASSWORD "
								   + "FROM TUSER where "
								   + "USER_NAME='"+ userName.getText()+"' "
								   + "or USER_PASSWORD='"+password.getText()+"'";
		        		getVal=db.doRecordSet(queryCheck);
		        		//insert if no exisiting
		        		if (getVal.isEmpty()) {
		        			arry.add(userName.getText());
			        		arry.add(password.getText());
			        		System.out.print(arry.get(0));
				            db.insertion(query, arry, 1); 
				        	LogIn frame = new LogIn();
				        	frame.setVisible(true);
				        	SignUp.this.setVisible(false);   
		        		}else {
		        			JOptionPane.showMessageDialog(msgeer, "Error! Please try a different password/ username");
		        		}
		        			
		        	} else {
		            JOptionPane.showMessageDialog(msgeer, "Error! Passwords do not match.");

		        
		        }}
		    }                                  

		});
		confirm.setBounds(87, 126, 89, 23);
		contentPane.add(confirm);
		
		here = new JButton("Here");
		here.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//go to login frame
				LogIn frame = new LogIn();
				frame.setVisible(true);
				SignUp.this.setVisible(false);
		}
		});
		here.setBounds(160, 178, 85, 23);
		contentPane.add(here);
		
		lblAlreadyHaveAn = new JLabel("Already have an account? Click");
		lblAlreadyHaveAn.setForeground(Color.BLUE);
		lblAlreadyHaveAn.setBounds(10, 182, 194, 14);
		contentPane.add(lblAlreadyHaveAn);
		
		JLabel lblRetypePassword = new JLabel("Re-type Password ");
		lblRetypePassword.setBounds(10, 101, 91, 14);
		contentPane.add(lblRetypePassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 76, 73, 14);
		contentPane.add(lblPassword);
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setBounds(10, 48, 73, 14);
		contentPane.add(lblUsername);
		
		lblCreateYourAccount = new JLabel("Create your Account!");
		lblCreateYourAccount.setBounds(76, 11, 121, 14);
		contentPane.add(lblCreateYourAccount);
		
		JLabel msgeer = new JLabel();
		msgeer.setFont(new Font("Tahoma", Font.PLAIN, 26));
		msgeer.setForeground(Color.RED);
		msgeer.setBounds(10, 157, 247, 14);
		contentPane.add(msgeer);
		
		
	}
}