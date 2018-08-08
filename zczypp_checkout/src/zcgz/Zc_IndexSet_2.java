package zcgz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import util.DbUtil;
/**
 * 根据规则sql查找打上索引的资产id
 * 插入zczypp_checkout表数据->资产id,资产索引
 * @author LJP
 *
 */
public class Zc_IndexSet_2 {
	
	public void getIndexSet(List<Map<String,String>> list){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement ps =null;
		int count = 0;
		try {
			conn = DbUtil.getConn();
			stmt = conn.createStatement();
			String sql = "insert into zczypp_checkout values(?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			for(Map<String,String> m : list){
				//System.out.println(m.get("sql"));
				rs = stmt.executeQuery(m.get("sql"));
				while(rs.next()){
					ps.setString(1, rs.getString("anln1"));
					ps.setString(2, m.get("zc_index"));
					ps.setString(3, rs.getString("txt50"));
					ps.setString(4, rs.getString("txa50"));
					ps.setString(5, rs.getString("anlhtxt"));
					ps.addBatch();
					count++;
					System.out.println("插入第"+count+"条；zc_id:"+rs.getString("anln1")+";zc_index:"+m.get("zc_index")
							+";txt50:"+rs.getString("txt50")+";txa50:"+rs.getString("txa50")+";anlhtxt:"+rs.getString("anlhtxt"));
				}
				ps.executeBatch();
			}
			System.out.println("总共插入："+count+"条数据");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
				stmt.close();
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		Zc_guize_1 zg = new Zc_guize_1();
		Zc_IndexSet_2 zi = new Zc_IndexSet_2();
		zi.getIndexSet(zg.zc_exec());
		
	}
}
