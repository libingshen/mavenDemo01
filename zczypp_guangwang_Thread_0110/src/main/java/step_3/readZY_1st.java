package step_3;

import util.ConfigManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class readZY_1st {
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	public readZY_1st(Connection conn){
		this.conn=conn;
	}
	
	public List<String> getData(String index,String zc_id,String zhanzhi_name){
		List<String> list = new ArrayList<String>();
		try {
			 stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/selectSqlZY1st"));
			 stmt.setString(1, index);
			 stmt.setString(2, zc_id);
			 stmt.setString(3, zhanzhi_name);
			 rs = stmt.executeQuery();
			 String queryZy = "id";
	         while(rs.next()){
	         	String str = rs.getString(queryZy);
	        	list.add(str);
	         }
		} catch (Exception e) {
			e.printStackTrace();  
		} finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
