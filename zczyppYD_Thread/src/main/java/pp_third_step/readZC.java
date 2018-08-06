package pp_third_step;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConfigManager;
import util.DbUtil;

public class readZC {
	
	private PreparedStatement stmt ;
	private ResultSet rs ;
	
	/**
	 * 获取某站址 的一个索引的  资产编码
	 * @param index
	 * @param zhanzhi_name
	 * @return
	 */
	public List<String> getData(Connection conn,String index,String zhanzhi_name){
		List<String> list = new ArrayList<String>();
		try {
//			 conn = DbUtil.getConn();
			 stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/getSqlZCOne"));
			 stmt.setString(1,index);
			 stmt.setString(2,zhanzhi_name);
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
//				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		
//		readZC rzc = new readZC();
//		List<String> list = rzc.getData("DSM","海口文岭村站");
//		int count=0;
//		for(String s : list){
//			System.out.println(s);
//			count++;
//		}
//		System.out.println("总共"+count+"条");
	}
}
