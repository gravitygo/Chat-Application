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
import dbBackEnd.DBFunc;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import interfaces.LogIn;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Group extends JFrame{
//declaration of variables
	public static String[] values;
	public static String uID;
	static Group frame = new Group();
	static ArrayList<String> getValues;
	private JPanel contentPane;
	DBFunc db= new DBFunc();
	LogIn LogIn= new LogIn();
	JTextField groupName = new JTextField();
	JLabel lblGroupName = new JLabel("Group Name:");
	JLabel lblAvailable = new JLabel("Available:");
	JLabel lblSelected = new JLabel("Selected:");
	JButton create = new JButton("Create");
	JButton cancel = new JButton("Cancel");
	ArrayList<String> arry= new ArrayList<String>();
	ArrayList<String> arry2= new ArrayList<String>();
	ArrayList<String> arry3= new ArrayList<String>();
	JScrollPane scrollPane_Slctd = new JScrollPane();
	JScrollPane scrollPane_Avail = new JScrollPane();
	@SuppressWarnings("rawtypes")
	JList available;
	@SuppressWarnings("rawtypes")
	JList selected;
	@SuppressWarnings("rawtypes")
	DefaultListModel selectionListModel= new DefaultListModel();
	@SuppressWarnings("rawtypes")
	DefaultListModel availableListModel= new DefaultListModel();
//get the usernames of the friends
	public void dbase() {
		DBFunc db= new DBFunc();
		db.piConnection="jdbc:oracle:thin:@localhost:1521:orcl";
		db.piUserName="chat as sysdba";
		db.piPassword="chatapp";
		getValues=db.doRecordSet("select a.USER_NAME FROM TUSER a, "
								+ "TUSER_FRIENDS b WHERE b.user_id='"+uID+"' "
								+ "and b.friend_id = a.user_id order by a.user_name asc");
			for (int i=0; i<= db.l_str.size()-1;i++) {
				values=getValues.get(i).split("`");
			}	
	}
//create group funciton
	public void insertion() {
		String nameQuery="INSERT INTO TGROUP_NAME(GROUP_NAME) VALUES (?)";
		String getGID="Select GROUP_ID FROM TGROUP_NAME WHERE GROUP_NAME='"+groupName.getText()+"'";
		String getUID="SELECT USER_ID FROM TUSER WHERE ";
		
		DBFunc db= new DBFunc();
		db.piConnection="jdbc:oracle:thin:@localhost:1521:orcl";
		db.piUserName="chat as sysdba";
		db.piPassword="chatapp";
		
		arry3.add(groupName.getText());
		db.insertion(nameQuery, arry3, 0);
		
		arry2=db.doRecordSet(getGID);
		String[] concat=arry2.get(0).split("`");
		
		String query="INSERT INTO TGROUP_USER(GROUP_ID,USERID_INGROUP) VALUES ('"+ concat[0] + "',?)";
		for (int idx=1;idx<=selected.getModel().getSize();idx++) {
				getUID=getUID+"USER_NAME='"+(String) selected.getModel().getElementAt(idx-1)+"' or ";
		}	
		getUID=getUID+"USER_ID='"+uID+"'";
		arry=db.doRecordSet(getUID);
		String[] concat2=null;
		for (int idx=0;idx<=arry.size()-1;idx++) {
			concat2=arry.get(idx).split("`");
		}
		arry.clear();
		
		arry.addAll(Arrays.asList(concat2));
		while(arry.size()-1>=0) {
			db.insertion(query, arry,0);
			System.out.print(arry.toString());
			arry.remove(arry.get(0));
		}
	}
//gui setter
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Group() {
//load usernames
		dbase();
		setTitle("Group");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 258, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
// go back button		
		groupName.setBounds(75, 11, 129, 20);
		contentPane.add(groupName);
		groupName.setColumns(10);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Chat chat=new Chat();
				chat.setVisible(true);
			}
		});
		cancel.setBounds(20, 270, 89, 23);
		contentPane.add(cancel);
		
//create group button
		create.setBounds(135, 270, 89, 23);
		contentPane.add(create);
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertion();
			}
		});
		
		lblGroupName.setBounds(10, 14, 72, 14);
		contentPane.add(lblGroupName);
		
		lblAvailable.setBounds(20, 42, 72, 14);
		contentPane.add(lblAvailable);
		
		lblSelected.setBounds(114, 42, 72, 14);
		contentPane.add(lblSelected);

//selection jlist initialization
		scrollPane_Avail.setBounds(10, 63, 91, 187);
		contentPane.add(scrollPane_Avail);
//getvalues for available jlist
		if(getValues.isEmpty()) {
			String[] val= new String[]{"No Friends"};
			available = new JList(val);
			available.setEnabled(false);
		}else {
			available = new JList(values);
		}
		availableFunc();
		scrollPane_Avail.setViewportView(available);
//selection jlist initialization
		scrollPane_Slctd.setBounds(107, 63, 136, 187);
		contentPane.add(scrollPane_Slctd);
		selected = new JList(selectionListModel);
		selectionFunc();
		scrollPane_Slctd.setViewportView(selected);
	}
//removes selected names
	public void selectionFunc() {
		selected.addMouseListener(new MouseAdapter()
		
	    {
	        public void mouseReleased(MouseEvent e){}
	        
	        public void mousePressed(MouseEvent e)
	        {
	        	selectionListModel.remove(available.getSelectedIndex());
	        }
	    });

	}
//add selected names
	public void availableFunc() {
		
		available.addMouseListener(new MouseAdapter()
	    {
	        public void mouseReleased(MouseEvent e){}
	        
	        @SuppressWarnings("unchecked")
			public void mousePressed(MouseEvent e)
	        {
	        	if (!isDuplicatedName(available.getSelectedValue())) {
	        		selectionListModel.addElement(available.getSelectedValue());
	        	}
	        }
	    });
		
	}
//check if name is in selected
	public Boolean isDuplicatedName(Object object) {
		for(int idx=0;idx<=selected.getLastVisibleIndex();idx++) {
			if(object==selected.getModel().getElementAt(idx)) {
				return true;
			}
		}
		return false;
	}

}
