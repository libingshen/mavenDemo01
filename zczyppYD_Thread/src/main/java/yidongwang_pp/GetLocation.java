package yidongwang_pp;

import util.ConfigManager;
import util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetLocation {
	/**
	 * 获取所有站址名称
	 * 		资源站址匹配
	 * @return
	 */
	public List<String> getLocation(Connection conn){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/getLocation"));
			rs = stmt.executeQuery();
			
			while(rs.next()){
				if(rs.getString("zhanzhi_name")!="" && rs.getString("zhanzhi_name") !=null){
					list.add(rs.getString("zhanzhi_name"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				stmt.close();
				rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 给100个站址打上索引
	 * @return
	 */
	public List<String> getLocation_index(Connection conn,int count_begin,int count_end){
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			ps = conn.prepareStatement(ConfigManager.getItemValue("/configuration/getLocation_index"));
			ps.setInt(1, count_begin);
			ps.setInt(2, count_end);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString("zhanzhi_name")!="" && rs.getString("zhanzhi_name") !=null){
					list.add(rs.getString("zhanzhi_name"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				ps.close();
				rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 站址匹配
	 * 		获取站址名称，以及该站址名称的限制条件
	 * @return
	 */
	public List<Map<String,String>> getLocation_zcpp(Connection conn){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/getLocation"));
			rs = stmt.executeQuery();
			
			while(rs.next()){
				if(rs.getString("zhanzhi_name")!="" && rs.getString("zhanzhi_name") !=null){
					Map<String,String> m = new HashMap<String, String>();
					m.put("zhanzhi_name", rs.getString("zhanzhi_name"));
					m.put("zhanzhi_suoyin", rs.getString("zhanzhi_suoyin"));
					m.put("city", rs.getString("city"));
					list.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				stmt.close();
				rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	public int getZhanzhi_count(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count =0;
		try {
			conn = DbUtil.getConn();
			stmt = conn.createStatement();
			String sql = "select count(1) from ZCZYPP_JZMC";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	

}

	
