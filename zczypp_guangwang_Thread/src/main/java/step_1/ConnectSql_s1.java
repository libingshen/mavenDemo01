package step_1;

import util.ConfigManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectSql_s1 {
	
	private Connection conn;
	
	public ConnectSql_s1(Connection conn){
		this.conn=conn;
	}
	
	/**
	 * 获取站址标识的规则
	 * @return
	 */
	public List<Map<String,String>> getLocationCondition(){
		Statement stmt = null;
		ResultSet rs = null;
		List<Map<String,String>> list= new ArrayList<Map<String,String>>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(ConfigManager.getItemValue("/configuration/getLocationWhere"));
			while(rs.next()){
				Map<String,String> m = new HashMap<String, String>();
				if(rs.getString("zhanzhi_name") !="" && rs.getString("zhanzhi_name") != null){
					m.put("zhanzhi_name", rs.getString("zhanzhi_name") );
					m.put("ltext", rs.getString("ltext"));
					m.put("anlhtxt", rs.getString("anlhtxt"));
					m.put("zc_zz_key", rs.getString("zc_zz_key"));
					m.put("zc_id", "光网OLT");
					m.put("zy_id", "光网OLT");
					list.add(m);
				}
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
	
	/**
	 * 第二步，给站址  打上索引标识
	 * 获取一个区间内的站址名称
	 * @param begin_count
	 * @param end_count
	 * @return
	 */
	public List<Map<String,String>> getLocationCondition_index(int begin_count,int end_count){
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String,String>> list= new ArrayList<Map<String,String>>();
		try {
			ps = conn.prepareStatement(ConfigManager.getItemValue("/configuration/getLocationWhere_index"));
			ps.setInt(1, begin_count);
			ps.setInt(2, end_count);
			rs = ps.executeQuery();
			while(rs.next()){
				Map<String,String> m = new HashMap<String, String>();
				if(rs.getString("zhanzhi_name") !="" && rs.getString("zhanzhi_name") != null){
					m.put("zhanzhi_name", rs.getString("zhanzhi_name") );
					m.put("ltext", rs.getString("ltext"));
					m.put("anlhtxt", rs.getString("anlhtxt"));
					m.put("zc_zz_key", rs.getString("zc_zz_key"));
					m.put("zc_id", "光网OLT");
					m.put("zy_id", "光网OLT");
					list.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
				try {
					ps.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return list;
	}
	
	/**
	 * 第三步 资产与资源的匹配
	 * 获取站址标识的 规则
	 * @return
	 */
	public List<Map<String,String>> getLocationCondition_zczypp(int count_begin,int count_end){
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String,String>> list= new ArrayList<Map<String,String>>();
		try {
			ps = conn.prepareStatement(ConfigManager.getItemValue("/configuration/getLocationWhere_zczypp"));
			ps.setInt(1, count_begin);
			ps.setInt(2, count_end);
			rs = ps.executeQuery();
			while(rs.next()){
				Map<String,String> m = new HashMap<String, String>();
				if(rs.getString("zhanzhi_name") !="" && rs.getString("zhanzhi_name") != null){
					m.put("zhanzhi_name", rs.getString("zhanzhi_name") );
					m.put("ltext", rs.getString("ltext"));
					m.put("anlhtxt", rs.getString("anlhtxt"));
					m.put("zc_zz_key", rs.getString("zc_zz_key"));
					m.put("zc_id", "光网OLT");
					m.put("zy_id", "光网OLT");
					list.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
				try {
					ps.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return list;
	}
	/**
	 * 获取站址总数
	 * @return
	 */
	public int getLocationCondition_count(){
		Statement stmt = null;
		ResultSet rs = null;
		int c = 0;
		try {
			stmt = conn.createStatement();
			String sql = ConfigManager.getItemValue("/configuration/getLocationWhere_count");
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				c = rs.getInt("c");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				conn.close();
				stmt.close();
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return c;
	}
	/**
	 * 第一步：资产给站址打标识
	 * 拼站址打标识的sql
	 * @return
	 */
	public List<Map<String,String>> zhanzhi_name_pp(){
		List<Map<String,String>> l = new ArrayList<Map<String,String>>(); 
		//ConnectSql_s1 cs = new ConnectSql_s1();
		List<Map<String,String>> list = getLocationCondition();
		for(Map<String,String> m : list){
			Map<String,String> sqlMap = new HashMap<String, String>();
			String sql = "update (select * from zc_copy_test01 t ";
			sql =sql +" where t.ltext like '%"+m.get("ltext")+"%' and t.ANLHTXT like '%"+m.get("anlhtxt")+"%'";
			sql =sql +" and "+m.get("zc_zz_key");
			sql =sql +" and t.AKTIV< 20160101";
			sql+= ") k set k.zhanzhi_name = '"+m.get("zhanzhi_name")+"',k.zc_id='光网OLT'"; 
			//System.out.println("----------------------------------"+m.get("zhanzhi_name"));
			//System.out.println(sql);
			sqlMap.put("zhanzhi_name",m.get("zhanzhi_name"));
			sqlMap.put("sql",sql);
			l.add(sqlMap);
		}
		return l;
	}
}
