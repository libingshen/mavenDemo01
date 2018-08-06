package pp_third_step;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.ConfigManager;
import util.DbUtil;

public class readZY {
	
	private PreparedStatement stmt ;
	private ResultSet rs ;
	/**
	 * 获取某站址的一个资源索引的  资源id
	 * @param index
	 * @param zhanzhi_name
	 * @return
	 */
	public List<String> getData(Connection conn,String index,String zhanzhi_name){
		List<String> list = new ArrayList<String>();
		try {
//			 conn = DbUtil.getConn();
			 stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/getSqlZYOne"));
			 stmt.setString(1, index);
			 stmt.setString(2, zhanzhi_name);
			 rs = stmt.executeQuery();
	         while(rs.next()){
	         	String str = rs.getString("id");
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
//		long startTime = System.currentTimeMillis();
//		readZY readzy = new readZY();
//		List<String> list = readzy.getData("DSM","海口安置小区");
//		for(int i =0 ; i<list.size();i++){
//			System.out.println(list.get(i));
//		}
//		long endTime = System.currentTimeMillis();
//		System.out.println("程序运行时间："+(endTime-startTime)/1000/60+"m");
	}
}
