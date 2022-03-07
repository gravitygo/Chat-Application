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

import dbBackEnd.DBFunc;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.Timer;
import java.awt.event.ActionEvent;
import javax.swing.*;
import interfaces.LogIn;

import javax.swing.table.DefaultTableModel;
@SuppressWarnings("serial")
public class Chat extends JFrame{
	//declaration of variables
	public static String[] userVal,groupVal,chatVal;
	public static String uID,uname;
	private static JTextField chatArea;
	private JPanel profileLook;
	static ArrayList<String> getUser,getGroup,getChat,getChatfrom,getChatfromto,getChatinfo,getChatday;
	static DefaultTableModel tableModel ;
	static JLabel chatName;
	static JTable chatTable;
	static String queryDec="";
	static String objs[][];
	@SuppressWarnings("rawtypes")
	DefaultListModel groupListModel= new DefaultListModel();
	@SuppressWarnings("rawtypes")
	DefaultListModel userListModel= new DefaultListModel();
	@SuppressWarnings("rawtypes")
	DefaultListModel chatListModel= new DefaultListModel();
	ArrayList<String> message= new ArrayList<String>();
	@SuppressWarnings("rawtypes")
	JList users,groups;
	
//gui loader
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Chat() {
	//user and group listbox load to arrays
		dbaseuser();
		dbasegroup();
	//panel initialization
		setResizable(false);
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 405);
		profileLook = new JPanel();
		profileLook.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(profileLook);
		profileLook.setLayout(null);
	//chatarea initialization
		chatArea = new JTextField();
		chatArea.setBounds(173, 328, 188, 30);
		profileLook.add(chatArea);
		chatArea.setColumns(10);
	//sending of chat(also refer to dbInsert)
		JButton send = new JButton("Send");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dbInsert();
				chatArea.setText("");
			}
		});
		send.setBounds(367, 332, 57, 23);
		profileLook.add(send);
		
		JLabel lblUsers = new JLabel("Users:");
		lblUsers.setBounds(10, 31, 48, 14);
		profileLook.add(lblUsers);
	//combobox action to take
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Chat frame = new Chat();
				if(comboBox.getSelectedItem().equals("Find Friends")) {
					Search.uID=uID;
					Search.uname=uname;
					Search search=new Search();
					search.setVisible(true);
					frame.setVisible(false);
				}
				if(comboBox.getSelectedItem().equals("Group")) {					
					Group.uID=uID;
					Group group=new Group();
					group.setVisible(true);
					frame.setVisible(false);
				}
				if(comboBox.getSelectedItem().equals("Log Out")) {
					frame.dispose();
					LogIn login=new LogIn();
					login.setVisible(true);
				}
				
			}
		});
	//combobox initialization and loading
		comboBox.setModel(new DefaultComboBoxModel(new String[] {uname+"'s Profile","Find Friends","Group","Log Out"}));
		comboBox.setBounds(10, 11, 136, 20);
		profileLook.add(comboBox);
	
		chatName = new JLabel("");
		chatName.setBounds(174, 34, 90, 14);
		profileLook.add(chatName);
		
		JScrollPane scrollPane_Users = new JScrollPane();
		scrollPane_Users.setBounds(10, 56, 136, 139);
		profileLook.add(scrollPane_Users);
	//load users upon open
		if(getUser.isEmpty()) {
			String[] val= new String[]{"No Friends"};
			users = new JList(val);
			users.setEnabled(false);
		}else {
			users = new JList(userVal);
		}
		scrollPane_Users.setViewportView(users);
		JScrollPane scrollPane_Chat = new JScrollPane();
		scrollPane_Chat.setBounds(150, 59, 274, 258);
		profileLook.add(scrollPane_Chat);
		
	//table initialization and loading
		String col[] = {"Name","Time","Message"};
		tableModel = new DefaultTableModel(col, 0);
		chatTable = new JTable(tableModel);
		chatTable.setBounds(10, 11, 414, 239);
		scrollPane_Chat.setViewportView(chatTable);
		
		JLabel lblGroups = new JLabel("Groups:");
		lblGroups.setBounds(10, 202, 48, 14);
		profileLook.add(lblGroups);
		
		JScrollPane scrollPane_Groups = new JScrollPane();
		scrollPane_Groups.setBounds(10, 227, 136, 138);
		profileLook.add(scrollPane_Groups);
	//load groups upon open
		if(getGroup.isEmpty()) {
			String[] val= new String[]{"No Group"};
			groups = new JList(val);
			groups.setEnabled(false);
		}else {
			groups = new JList(groupVal);
		}	
	//load mouse listeners
		userFunc();
		groupFunc();
		scrollPane_Groups.setViewportView(groups);
	}
//Load user list
	public void dbaseuser() {
		DBFunc db= new DBFunc();
		db.piConnection="jdbc:oracle:thin:@localhost:1521:orcl";
		db.piUserName="chat as sysdba";
		db.piPassword="chatapp";
		getUser=db.doRecordSet("select a.USER_NAME FROM TUSER a, "
								+ "TUSER_FRIENDS b WHERE b.user_id='"+uID+"' "
								+ "and b.friend_id = a.user_id order by a.user_name asc");
			for (int i=0; i<= db.l_str.size()-1;i++) {
				userVal=getUser.get(i).split("`");
			}	
	}
//Insert message to database
	public void dbInsert() {
		DBFunc db= new DBFunc();
		ArrayList<String> a= new ArrayList<String>();
		db.piConnection="jdbc:oracle:thin:@localhost:1521:orcl";
		db.piUserName="chat as sysdba";
		db.piPassword="chatapp";	
		a.add("");
		String query="Insert into tmessage(message_information,USER_ID,USER_TO) "
				+ "values('"+chatArea.getText()+"','"+uname+"','"+chatName.getText()+"')";
		db.insertion(query, a, -1);
	}
//Load group list
	public void dbasegroup() {
		DBFunc db= new DBFunc();
		db.piConnection="jdbc:oracle:thin:@localhost:1521:orcl";
		db.piUserName="chat as sysdba";
		db.piPassword="chatapp";
		getGroup=db.doRecordSet("select Group_name FROM Tgroup_name where group_id in "
				+ "(select group_id from tgroup_user where userid_ingroup='"+uID+"') "
				+ "order by group_name asc");
			for (int i=0; i<= db.l_str.size()-1;i++) {
				groupVal=getGroup.get(i).split("`");
			}
	}
//for chat update
	public static void updateChat() {
//declaration of variables
		String queryinf="";
		String querytime="";
		String queryid="";
		String[] concat = null;
		String[] concat1 = null;
		String[] concat2 = null;
		DBFunc db= new DBFunc();
//connection
		db.piConnection="jdbc:oracle:thin:@localhost:1521:orcl";
		db.piUserName="chat as sysdba";
		db.piPassword="chatapp";
		db.connection();
//check if the used list is user or group
		if(queryDec.equals("user")) {
			queryinf="Select message_information from tmessage where "
					+ "(user_id= '"+uname+"' and user_to='"+chatName.getText()+"') or "
					+ "(user_id='"+chatName.getText()+"' and user_to='"+uname+"')";
			querytime="Select message_time from tmessage where "
					+ "(user_id= '"+uname+"' and user_to='"+chatName.getText()+"') or "
					+ "(user_id='"+chatName.getText()+"' and user_to='"+uname+"')";
			queryid ="Select user_id from tmessage where "
					+ "(user_id= '"+uname+"' and user_to='"+chatName.getText()+"') or "
					+ "(user_id='"+chatName.getText()+"' and user_to='"+uname+"')";
		}
		if(queryDec.equals("group")) {
			queryinf="Select message_information from tmessage "
					+ "where user_to='"+chatName.getText()+"'";
			querytime="Select message_time from tmessage "
					+ "where user_to='"+chatName.getText()+"'";
			queryid="Select user_id from tmessage "
					+ "where user_to='"+chatName.getText()+"'";
		}
//get friends name
		getChatfromto=db.doRecordSet(queryid);
//add the selected to table
		if(getChatfromto.isEmpty()) {
			tableModel.setRowCount(0);
		}else {
			for (int i=0; i<= db.l_str.size()-1;i++) {
				concat = getChatfromto.get(i).split("`");
			}
			getChatfromto.clear();
			getChatday=db.doRecordSet(querytime);
			for (int i=0; i<= db.l_str.size()-1;i++) {
				concat1 = getChatday.get(i).split("`");
			}
			getChatday.clear();
			getChatinfo=db.doRecordSet(queryinf);
			for (int i=0; i<= db.l_str.size()-1;i++) {
				concat2 = getChatinfo.get(i).split("`");
			}
			getChatinfo.clear();
			for (int i=0; i<=concat.length-1;i++) {
				getChatfromto.add(concat[i]);
				getChatfromto.add(concat1[i]);
				getChatfromto.add(concat2[i]);
			}
			tableModel.setRowCount(0);
			int a=0;
			objs=new String[getChatfromto.size()/3][3];
			for (int j=0; j<=(getChatfromto.size()/3)-1;j++) {
				for (int k=0; k<=2;k++) {
					objs[j][k]=getChatfromto.get(a);
					a++;
				}
			}
			for(int idx=0; idx <= objs.length-1;idx++) {
				tableModel.addRow(objs[idx]);
			}
		}
		
			
	}
//method for listening key press in mouse(users)	
	public void userFunc() {
		users.addMouseListener(new MouseAdapter()
	    {
	        public void mouseReleased(MouseEvent e){}
	        
	        public void mousePressed(MouseEvent e)
	        {
	        	chatName.setText((String) users.getSelectedValue());
	        	queryDec="user";
	        	updateChat();
	        	users.clearSelection();      	
	        	Timer timer = new Timer(); 
				TimerTask task = new Time(); 
				timer.schedule(task, 1000, 3000); 
	        }
	    });
	}
//method for listening key press in mouse(groups)	
	public void groupFunc() {
		groups.addMouseListener(new MouseAdapter()
	    {
	        public void mouseReleased(MouseEvent e){}
	        public void mousePressed(MouseEvent e)
	        {
	        	chatName.setText((String) groups.getSelectedValue());
	        	queryDec="group";
	        	updateChat();
	        	groups.clearSelection();
	        	Timer timer = new Timer(); 
				TimerTask task = new Time(); 
				timer.schedule(task, 1000, 3000); 
	        }
	    });

	}

}

