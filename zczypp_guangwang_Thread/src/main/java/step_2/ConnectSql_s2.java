package step_2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ConfigManager;
/**
 * 第二步：生成  打上索引 的sql
 * @author LJP
 *
 */
public class ConnectSql_s2 {
	private Connection conn;
	
	public ConnectSql_s2(Connection conn){
		this.conn = conn;
	}
	
	//获取资产规则表zc_guize信息
	public List<Map<String,String>> getZcCondition(){
		Statement stmt=null ;
	    ResultSet rs =null;
		
		List<Map<String,String>> list= new ArrayList<Map<String,String>>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(ConfigManager.getItemValue("/configuration/getZcWhere"));
			while(rs.next()){
				Map<String,String> m = new HashMap<String, String>();
				if(rs.getString("index_level") !="" && rs.getString("index_level") != null){
					m.put("index_level", rs.getString("index_level") );
					m.put("zc_guize", rs.getString("zc_guize"));
					m.put("zc_zz_key", rs.getString("zc_zz_key"));
					m.put("zc_index", rs.getString("zc_index"));
					m.put("zc_id", rs.getString("zc_id"));
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
	 * 生成打上资产索引 的sql
	 * @param zhanzhi_name
	 * @return
	 */
	//拼资产索引sql
	public Map<String,List<Map<String,String>>> zc_index(String zhanzhi_name){
		List<Map<String,String>> list1 = new ArrayList<Map<String,String>>(); 
		List<Map<String,String>> list2 = new ArrayList<Map<String,String>>();
		Map<String,List<Map<String,String>>> resultMap = new HashMap<String, List<Map<String,String>>>();
		List<Map<String,String>> list = getZcCondition();
		for(Map<String,String> m : list){
			if(m.get("index_level").equals("1")){
				Map<String,String> sqlMap = new HashMap<String, String>();
				String sql = "update (select * from zc_copy_test01 t ";
				sql =sql +"where"+m.get("zc_guize");
				sql =sql+" and "+m.get("zc_zz_key");
				sql =sql+" and t.zhanzhi_name = '"+zhanzhi_name+"'";
				sql =sql+" and t.anln1 not like '5%'";
				sql+= ") k set k.zc_index = '"+m.get("zc_index")+"',k.zc_id='"+m.get("zc_id")+"'"; 
				//System.out.println("----------------------------------"+m.get("zc_index"));
				//System.out.println(sql);
				sqlMap.put("zc_index",m.get("zc_index"));
				sqlMap.put("sql",sql);
				list1.add(sqlMap);
			}else{
				Map<String,String> sqlMap = new HashMap<String, String>();
				String sql = "update (select * from zc_copy_test01 t ";
				sql =sql +" where "+m.get("zc_guize");
				sql =sql +" and t.zc_index is not null ";
				sql+= ") k  set k.zc_index_2nd ='"+m.get("zc_index")+"'"; 
				//System.out.println("----------------------------------"+m.get("zc_index"));
				//System.out.println(sql);
				sqlMap.put("zc_index",m.get("zc_index"));
				sqlMap.put("sql",sql);
				list2.add(sqlMap);
			}
		}
		resultMap.put("1", list1);
		resultMap.put("2", list2);
		return resultMap;
	}
	
	//获取资源规则表
	public List<Map<String,String>> getZyCondition(){
		Statement stmt = null;
		ResultSet rs = null;
		List<Map<String,String>> list= new ArrayList<Map<String,String>>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(ConfigManager.getItemValue("/configuration/getZyWhere"));
			while(rs.next()){
				Map<String,String> m = new HashMap<String, String>();
				if(rs.getString("zy_index")!="" &&  rs.getString("zy_index")!=null ){
					m.put("index_level", rs.getString("index_level") );
					m.put("zy_index", rs.getString("zy_index"));
					m.put("zy_guize", rs.getString("zy_guize"));
					m.put("zy_id", rs.getString("zy_id"));
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
	 * 生成打上资源索引的sql
	 * @param zhanzhi_name
	 * @return
	 */
	//拼资产索引sql
	public Map<String,List<Map<String,String>>> zy_index(String zhanzhi_name){
		List<Map<String,String>> list1 = new ArrayList<Map<String,String>>(); 
		List<Map<String,String>> list2 = new ArrayList<Map<String,String>>(); 
		Map<String,List<Map<String,String>>> resultMap = new HashMap<String, List<Map<String,String>>>();
		List<Map<String,String>> list = getZyCondition();
		for(Map<String,String> m : list){
			if(m.get("index_level").equals("1")){
				Map<String,String> sqlMap = new HashMap<String, String>();
				String sql = "update (select * from zy_copy_test01 t ";
				sql =sql +"where"+m.get("zy_guize");
				sql=sql+" and t.zhanzhi_name = '"+zhanzhi_name+"'";
				sql+= ") k set k.zy_index = '"+m.get("zy_index")+"',k.zy_id='"+m.get("zy_id")+"'"; 
				//System.out.println("----------------------------------"+m.get("zy_index"));
				//System.out.println(sql);
				sqlMap.put("zy_index",m.get("zc_index"));
				sqlMap.put("sql",sql);
				list1.add(sqlMap);
			}else{
				Map<String,String> sqlMap = new HashMap<String, String>();
				String sql = "update (select * from zy_copy_test01 t ";
				sql =sql +" where"+m.get("zy_guize");
				sql =sql +" and zy_index is not null";
				sql+= ") k set k.zy_index_2nd = '"+m.get("zy_index")+"',k.zy_id='"+m.get("zy_id")+"'"; 
				//System.out.println("----------------------------------"+m.get("zy_index"));
				//System.out.println(sql);
				sqlMap.put("zy_index",m.get("zc_index"));
				sqlMap.put("sql",sql);
				list1.add(sqlMap);
			}
			
		}
		resultMap.put("1", list1);
		resultMap.put("2", list2);
		return resultMap;
	}

}
