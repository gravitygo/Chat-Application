package dbBackEnd;

import java.sql.*;
import java.util.ArrayList;

public class DBFunc {
	// can make query a variable relying from a variable depends on the system
		int var;
		public String user_id,user,pass;
		public ArrayList<String> l_str = new ArrayList<String>();
		String query;
		public String piConnection, piUserName, piPassword;
		//connection
		public Connection connection() {		
			String url=piConnection;
			Connection conn=null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver"); 
				conn=DriverManager.getConnection(url,  piUserName, piPassword);
			}catch(Exception e) {
				System.out.print(e.getMessage());
			}
			
			return conn;
		}
		//insertion
		public void insertion(String query, ArrayList<String> arry, int num) {
			try (Connection conn = this.connection()){
				//prepare query given to be processed
			      PreparedStatement preparedStmt = conn.prepareStatement(query);
			      //loop all values from arraylist and insert to query
			      for (int i=0; i<= num;i++) {
			    	  preparedStmt.setString (i+1, arry.get(i));
					}	
			      //execute query
			      preparedStmt.execute();
			      //close connection
			      conn.close();
			}catch(SQLException e) {
				System.out.print(e.getMessage());
			}
		}
		//selection
		public ArrayList<String> doRecordSet(String query) {
			String l_tmp="";
			//connection
			try (Connection conn = this.connection()){
				Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = st.executeQuery(query);	
				while(rs.next()) {
					//go to next column
					for(int i=1; i<=rs.getMetaData().getColumnCount();i++) {
					l_tmp=l_tmp+ rs.getString(i)+"`";
					}
					l_str.add(l_tmp);
				}
				//close connection
				st.close();
			}catch(SQLException e) {
				System.out.print(e.getMessage());
			}
			return l_str;
		}
}
