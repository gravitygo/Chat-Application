/**========================================================================================================================================================
 *  ______  __    __       ___   .___________.    ___      .______   .______    __       __    ______     ___   .___________. __    ______   .__   __. 
 * /      ||  |  |  |     /   \  |           |   /   \     |   _  \  |   _  \  |  |     |  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  | 
 *|  ,----'|  |__|  |    /  ^  \ `---|  |----`  /  ^  \    |  |_)  | |  |_)  | |  |     |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  | 
 *|  |     |   __   |   /  /_\  \    |  |      /  /_\  \   |   ___/  |   ___/  |  |     |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  | 
 *|  `----.|  |  |  |  /  _____  \   |  |     /  _____  \  |  |      |  |      |  `----.|  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   | 
 * \______||__|  |__| /__/     \__\  |__|    /__/     \__\ | _|      | _|      |_______||__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__| 
                                                                                                                                                     
 * Made by: Chyle Andrei Lee &
 * 			Clyde Adrian Ramos
 * ========================================================================================================================================================
 */
package interfaces;
//import packages
import java.awt.EventQueue;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import dbBackEnd.DBFunc;

@SuppressWarnings("serial")
public class LogIn extends JFrame {
	//declaration of variables
	private JPanel contentPane;
	static JTextField user_Name;
	static JPasswordField password;
	static LogIn frame = new LogIn();
	public String uID;
	String[] concat;
	ArrayList<String> getVal;
//gui setter
	public LogIn() {
		//jpanel initialization
		setTitle("Log-in");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 277, 216);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWelcome = new JLabel("Welcome User!");
		lblWelcome.setBounds(99, 11, 72, 14);
		contentPane.add(lblWelcome);
		
		user_Name= new JTextField();
		user_Name.setBounds(99, 51, 152, 20);
		contentPane.add(user_Name);
		user_Name.setColumns(10);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(17, 54, 72, 14);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(17, 85, 72, 14);
		contentPane.add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(99, 82, 152, 20);
		contentPane.add(password);
		
		JButton logIn = new JButton("Log-In");
		logIn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				//select if the user name and password is in the database
				String query="SELECT USER_NAME,USER_PASSWORD,USER_ID "
						   + "FROM TUSER where "
						   + "USER_NAME='"+ user_Name.getText()+"' "
						   + "and USER_PASSWORD='"+password.getText()+"'";
				//connect to database
				DBFunc db= new DBFunc();
				db.piConnection="jdbc:oracle:thin:@localhost:1521:orcl";
				db.piUserName="chat as sysdba";
				db.piPassword="chatapp";
				//run script
				getVal=db.doRecordSet(query);
				//check if unavailable
				if (getVal.isEmpty()) {JOptionPane.showMessageDialog(null, "Error! No Such username and/or password");}
				else {
					//get uid and open chat window
					for (int i=0; i<= db.l_str.size()-1;i++) {
						concat=getVal.get(i).split("`");
					}
					Chat.uID=concat[2];
					Chat.uname=user_Name.getText();
					frame.setVisible(false);					
					Chat chat= new Chat();
					chat.setVisible(true);
				}
			}
		});
		logIn.setBounds(87, 110, 89, 23);
		contentPane.add(logIn);
		
		JButton signUp = new JButton("SignUp");
		signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//go to signup page
				frame.setVisible(false);
				SignUp signup= new SignUp();
				signup.setVisible(true);
			}
		});
		signUp.setBounds(87, 147, 89, 23);
		contentPane.add(signUp);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
