package pp_third_step;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConfigManager;
import util.DbUtil;

public class getIndex {
	
	private PreparedStatement stmt =null;
	private ResultSet rs = null;
	
	/**
	 * 获取某站址 的所有  索引类型
	 * @param zhanzhi_name
	 * @return
	 */
	public List<String> getIndexList(Connection conn,String zhanzhi_name){
		
		List<String> list = new ArrayList<String>();
		
		try{
//			conn = DbUtil.getConn();
			stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/getIndex"));
			stmt.setString(1, zhanzhi_name);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				if(rs.getString("zc_index")!="" && rs.getString("zc_index")!=null){
					list.add(rs.getString("zc_index"));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
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
//		getIndex index = new getIndex();
//		List<String> list = index.getIndexList("海口安置小区");
//		for(String s : list){
//			System.out.println(s);
//		}
		
	}


}
