package sqlUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import util.ConfigManager;
import util.DbUtil;
/**
 * 第二步：生成  打上索引 的sql
 * @author LJP
 *
 */
public class ConnectSql {
	
	private Connection conn;
	
	public ConnectSql(Connection conn){
		this.conn=conn;
	}
	
	//获取资产规则表zc_guize信息
	public List<Map<String,String>> getZcCondition(){
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Map<String,String>> list= new ArrayList<Map<String,String>>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(ConfigManager.getItemValue("/configuration/getZcWhere"));
			while(rs.next()){
				Map<String,String> m = new HashMap<String, String>();
				if(rs.getString("zc_index") !="" && rs.getString("zc_index") != null){
					m.put("zc_index", rs.getString("zc_index") );
					m.put("zc_guize", rs.getString("zc_guize"));
					m.put("zc_zz_key", rs.getString("zc_zz_key"));
					list.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(conn != null){
				try {
					stmt.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
	public List<Map<String,String>> zc_index(String zhanzhi_name){
		List<Map<String,String>> l = new ArrayList<Map<String,String>>(); 
		List<Map<String,String>> list = getZcCondition();
		for(Map<String,String> m : list){
			Map<String,String> sqlMap = new HashMap<String, String>();
			String sql = "update (select * from zc_copy_test01 t ";
			sql =sql +"where"+m.get("zc_guize");
			sql =sql+" and "+m.get("zc_zz_key");
			sql=sql+" and t.zhanzhi_name = '"+zhanzhi_name+"'";
			sql+= ") k set k.zc_index = '"+m.get("zc_index")+"'"; 
			//System.out.println("----------------------------------"+m.get("zc_index"));
			//System.out.println(sql);
			sqlMap.put("zc_index",m.get("zc_index"));
			sqlMap.put("sql",sql);
			l.add(sqlMap);
		}
		return l;
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
					m.put("zy_index", rs.getString("zy_index") );
					m.put("zy_guize", rs.getString("zy_guize"));
					list.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(conn != null){
				try {
					stmt.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
	public List<Map<String,String>> zy_index(String zhanzhi_name){
		List<Map<String,String>> l = new ArrayList<Map<String,String>>(); 
		List<Map<String,String>> list = getZyCondition();
		long st = System.currentTimeMillis();
		for(Map<String,String> m : list){
			Map<String,String> sqlMap = new HashMap<String, String>();
			String sql = "update (select * from zy_copy_test01 t ";
			sql =sql +"where"+m.get("zy_guize");
			sql=sql+" and t.zhanzhi_name = '"+zhanzhi_name+"'";
			sql+= ") k set k.zy_index = '"+m.get("zy_index")+"'"; 
			//System.out.println("----------------------------------"+m.get("zy_index"));
			//System.out.println(sql);
			sqlMap.put("zy_index",m.get("zc_index"));
			sqlMap.put("sql",sql);
			l.add(sqlMap);
		}
		long et = System.currentTimeMillis();
		//System.out.println("测试时间，总耗时："+(et-st)+"ms");
		return l;
	}
	
	public List<Map<String,String>> zy_index_1(String zhanzhi_name){
		List<Map<String,String>> l = new ArrayList<Map<String,String>>(); 
		List<Map<String,String>> list = getZyCondition();
		long st = System.currentTimeMillis();
		StringBuffer sql = new StringBuffer();
		for(Map<String,String> m : list){
			Map<String,String> sqlMap = new HashMap<String, String>();
			sql.append("update (select * from zy_copy_test01 t ");
			sql.append("where"+m.get("zy_guize"));
			sql.append(" and t.zhanzhi_name = '"+zhanzhi_name+"'");
			sql.append(") k set k.zy_index = '"+m.get("zy_index")+"'"); 
			//System.out.println("----------------------------------"+m.get("zy_index"));
			//System.out.println(sql.toString());
			sqlMap.put("zy_index",m.get("zc_index"));
			sqlMap.put("sql",sql.toString());
			l.add(sqlMap);
		}
		long et = System.currentTimeMillis();
		//System.out.println("测试时间，总耗时："+(et-st)+"ms");
		return l;
	}
	public List<Map<String,String>> zy_index_2(String zhanzhi_name){
		List<Map<String,String>> l = new ArrayList<Map<String,String>>(); 
		List<Map<String,String>> list = getZyCondition();
		long st = System.currentTimeMillis();
		String sql = "update (select * from zy_copy_test01 t "
				+	"where"+" {zy_guize}"
				+	" and t.zhanzhi_name = "+" '{zhanzhi_name}' "
				+	") k set k.zy_index = "+ " '{zy_index}' ";
		//System.out.println(sql+"~~~~~~");
		for(Map<String,String> m : list){
			Map<String,String> sqlMap = new HashMap<String, String>();
			String temp = sql;
			temp= temp.replace("{zy_guize}", m.get("zy_guize"));
			temp= temp.replace("{zhanzhi_name}", zhanzhi_name);
			temp= temp.replace("{zy_index}", m.get("zy_index"));
			//System.out.println("----------------------------------"+m.get("zy_index"));
			//System.out.println(temp);
			sqlMap.put("zy_index",m.get("zc_index"));
			sqlMap.put("sql",temp);
			l.add(sqlMap);
		}
		long et = System.currentTimeMillis();
		//System.out.println("测试时间，总耗时："+(et-st)+"ms");
		return l;
	}
	public static void main(String[] args) throws Exception {
		ConnectSql cs = new ConnectSql(DbUtil.getConn());
		cs.zc_index("海口天诚小区");
	}
}
