package step_3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConfigManager;
import util.DbUtil;

public class readZC_2nd {
	private Connection conn ;
	private PreparedStatement stmt;
	private ResultSet rs ;
	
	public readZC_2nd(Connection conn){
		this.conn=conn;
	}
	
	public List<String> getData(String index,String index_2nd,String zc_id,String zhanzhi_name){
		List<String> list = new ArrayList<String>();
		try {
			 stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/selectSqlZC2nd"));
			 stmt.setString(1,index);
			 stmt.setString(2,index_2nd);
			 stmt.setString(3,zc_id);
			 stmt.setString(4,zhanzhi_name);
			 rs = stmt.executeQuery();
	         while(rs.next()){
	         	String str = rs.getString("ANLN1");
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
