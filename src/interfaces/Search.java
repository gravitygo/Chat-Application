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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dbBackEnd.DBFunc;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Search extends JFrame {
	//declaration of variables
	public static String uID,uname;
	private JPanel contentPane;
	private static JTextField searchField;
	@SuppressWarnings("rawtypes")
	DefaultListModel ListModel= new DefaultListModel();
	@SuppressWarnings("rawtypes")
	JList found;
	String[] concat;
	ArrayList<String> founds= new ArrayList<String>();
//finding users at database
	@SuppressWarnings("unchecked")
	public void findFriends() {
		String query="Select user_name from tuser "
				   + "where user_name like '%"+searchField.getText()+"%'";
		DBFunc db= new DBFunc();
		db.piConnection="jdbc:oracle:thin:@localhost:1521:orcl";
		db.piUserName="chat as sysdba";
		db.piPassword="chatapp";
		founds=db.doRecordSet(query);
		for (int i=0; i<= db.l_str.size()-1;i++) {
			concat=founds.get(i).split("`");
		}
		for (int i=0; i<concat.length;i++) {
			if(concat[i].equals(uname)) {}
			else {
				ListModel.addElement(concat[i]);
			}
		}
		found.setModel(ListModel);
	}
//mouse listener for jlist of available friends
	public void userFunc() {
		found.addMouseListener(new MouseAdapter()
	    {
	        public void mouseReleased(MouseEvent e){}
	        
	        public void mousePressed(MouseEvent e)
	        {
	        	//declaration of variables
	        	int a;
	        	//show upon click
	        	a=JOptionPane.showConfirmDialog(null, "Do you want to add him/her as your friend?","Add Dialouge",JOptionPane.OK_CANCEL_OPTION);
	        	// do if ok
	        	if(a==JOptionPane.OK_OPTION) {
	        		//declaration
	        		ArrayList<String> friendid= new ArrayList<String>();
	        		String query_getid="Select user_id from tuser where user_name='"+found.getSelectedValue()+"'";
	        		String ifnull="Select * from tuser_friends "
	        				    + "where user_id='"+uID+"' and friend_id='"+friendid+"'";
	        		String query1="Insert into TUSER_FRIENDS(USER_ID,FRIEND_ID) "
	        				    + "VALUES('"+uID+"',?)";
	        		String query2="Insert into TUSER_FRIENDS(FRIEND_ID,USER_ID) "
	        				    + "VALUES('"+uID+"',?)";
	        		//connection
	        		DBFunc db= new DBFunc();
	        		db.piConnection="jdbc:oracle:thin:@localhost:1521:orcl";
	        		db.piUserName="chat as sysdba";
	        		db.piPassword="chatapp";
	        		//run query
	        		friendid=db.doRecordSet(query_getid);
	        		String[] concat = null;
	        		
	        		for (int i=0; i<= db.l_str.size()-1;i++) {
	    				concat=friendid.get(i).split("`");
	    			}
	        		friendid.clear();
	        		for (int i=0; i<=concat.length-1;i++) {
	        			friendid.add(concat[i]);
	        		}	        	
	        		db.doRecordSet(ifnull);
	        		if(db.l_str.isEmpty()) {
	        			JOptionPane.showMessageDialog(null, "Already a friend");
	        		}else {
	        			db.insertion(query1, friendid, 0);
		        		db.insertion(query2, friendid, 0);
	        		}
	        		
	        	}
	        }
	    });
	}
//gui setter
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Search() {
		setTitle("Search Friends");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 308, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		searchField = new JTextField();
		searchField.setBounds(52, 11, 134, 20);
		contentPane.add(searchField);
		searchField.setColumns(10);
		//find friend upon press
		JButton search = new JButton("Search");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findFriends();
			}
		});
		search.setBounds(196, 10, 89, 23);
		contentPane.add(search);
		//go back to chat frame
		JButton goBack = new JButton("Go Back");
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Search frame = new Search();
				frame.dispose();
				Chat chat = new Chat();
				chat.setVisible(true);
			}
		});
		goBack.setBounds(106, 236, 89, 23);
		contentPane.add(goBack);
		
		JLabel lblSearch = new JLabel("Search:");
		lblSearch.setBounds(10, 14, 46, 14);
		contentPane.add(lblSearch);
		
		JScrollPane scrollPane_Found = new JScrollPane();
		scrollPane_Found.setBounds(10, 57, 286, 168);
		contentPane.add(scrollPane_Found);
		found = new JList(ListModel);
		userFunc();
		scrollPane_Found.setViewportView(found);
		
	}
}
