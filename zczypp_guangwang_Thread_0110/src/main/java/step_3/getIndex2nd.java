package step_3;

import util.ConfigManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getIndex2nd {
	
	public List<Map<String,String>> getIndexList(Connection conn,String zc_id,String zhanzhi_name){
		PreparedStatement stmt =null;
		ResultSet rs = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		try{
			stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/selectIndex2nd"));
			stmt.setString(1, zc_id);
			stmt.setString(2, zhanzhi_name);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Map<String,String> m = new HashMap<String, String>();
				m.put("zc_index", rs.getString("zc_index"));
				m.put("zc_index_2nd", rs.getString("zc_index_2nd"));
				list.add(m);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
}
