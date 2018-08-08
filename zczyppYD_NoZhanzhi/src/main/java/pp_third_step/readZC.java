package pp_third_step;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

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
			 stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/getSqlZCOne"));
			 stmt.setString(1,index);
			 stmt.setString(2,zhanzhi_name);
			 rs = stmt.executeQuery();
	         while(rs.next()){
	        	//System.out.println(rs.getString("ANLN1"));
	         	String str = rs.getString("ANLN1");
	        	list.add(str);
	         }
		} catch (Exception e) {
			e.printStackTrace();  
		} finally{
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static void main(String[] args) throws Exception {
		
		readZC rzc = new readZC();
		List<String> list = rzc.getData(DbUtil.getConn(),"GPS","海南省");
		int count=0;
		for(String s : list){
			System.out.println(s);
			count++;
		}
		System.out.println("总共"+count+"条");
	}
}
