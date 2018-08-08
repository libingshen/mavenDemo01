package zcgz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DbUtil;
/**
 * 生成各个索引 查询sql
 * @author LJP
 *
 */
public class Zc_guize_1 {

	public List<Map<String,String>> zc_exec(){
		List<Map<String,String>> list = new	ArrayList<Map<String,String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DbUtil.getConn();
			stmt = conn.createStatement();
			String sql = "select t.zc_index,t.zc_guize,t.zc_zz_key from zc_guize t";
			rs = stmt.executeQuery(sql);
			String sql2 = "";
			while(rs.next()){
				count++;
				Map<String,String> m = new HashMap<String, String>();
				sql2 = "select t.anln1,t.txt50,t.txa50,t.anlhtxt from zc_copy_test01 t where ";
				sql2 +=  rs.getString("zc_zz_key");
				sql2 += " and " + rs.getString("zc_guize");
				sql2 += " and t.aktiv < '20160101' ";
				sql2 += " and  (t.anlhtxt like '海口' or t.ltext = '省公司本部') ";
				sql2 += " and  t.zc_id='移动网' ";
				//System.out.println(sql2);
				m.put("sql", sql2);
				m.put("zc_index", rs.getString("zc_index"));
				list.add(m);
				sql2="";
			}
			System.out.println("总共生成"+count +"条sql，即规则条数");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public static void main(String[] args) {
		Zc_guize_1 zg = new Zc_guize_1();
		zg.zc_exec();
	}
}
