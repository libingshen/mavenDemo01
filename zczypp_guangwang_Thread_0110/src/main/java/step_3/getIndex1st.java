package step_3;

import util.ConfigManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class getIndex1st {
	
	public List<String> getIndexList(Connection conn,String zc_id,String zhanzhi_name){
		PreparedStatement stmt =null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		
		try{
			stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/selectIndex1st"));
			stmt.setString(1, zc_id);
			stmt.setString(2, zhanzhi_name);
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}


}
