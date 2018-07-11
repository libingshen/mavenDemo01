package step_3;

import util.ConfigManager;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class readZC_2nd {
	
	public List<String> getData(String index,String index_2nd,String zc_id,String zhanzhi_name){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			 conn = DbUtil.getConn();
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
		readZC_2nd rzc = new readZC_2nd();
		List<String> list = rzc.getData("ETGO","OLT05","光网OLT","海口白坡");
		int count=0;
		for(String s : list){
			System.out.println(s);
			count++;
		}
		System.out.println("总共"+count+"条");
	}
}
