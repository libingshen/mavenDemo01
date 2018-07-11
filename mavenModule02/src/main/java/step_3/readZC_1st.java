package step_3;

import util.ConfigManager;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class readZC_1st {
	
	public List<String> getData(String index,String zc_id,String zhanzhi_name){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			 conn = DbUtil.getConn();
			 stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/selectSqlZC1st"));
			 stmt.setString(1,index);
			 stmt.setString(2,zc_id);
			 stmt.setString(3,zhanzhi_name);
			 rs = stmt.executeQuery();
	         while(rs.next()){
	         	String str = rs.getString("anln1");
	        	list.add(str);
	         }
	         stmt.close();
	         conn.close();
		} catch (Exception e) {
			e.printStackTrace();  
		} finally{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		
		readZC_1st rzc = new readZC_1st();
		List<String> list = rzc.getData("C300","光网OLT","海口白坡");
		int count=0;
		for(String s : list){
			System.out.println(s);
			count++;
		}
		System.out.println("总共"+count+"条");
	}
}
