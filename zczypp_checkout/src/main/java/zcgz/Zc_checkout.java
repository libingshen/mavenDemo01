package zcgz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DbUtil;

public class Zc_checkout {
	
	private Connection conn = null;
	private	Statement stmt = null;
	private	ResultSet rs = null;
	private PreparedStatement ps =null;
	
	
	public void do_delete_repeat(){
		try {
			conn = DbUtil.getConn();
			stmt = conn.createStatement();
			String sql ="delete from zczypp_checkout a " 
					+	"  where (a.zc_id,a.zc_index) in (select zc_id,zc_index from zczypp_checkout group by zc_id,zc_index having count(*) > 1)" 
					+	"  and rowid not in (select min(rowid) from zczypp_checkout group by zc_id,zc_index having count(*)>1) ";
			int d=stmt.executeUpdate(sql);
			if(d>0){
				System.out.println("去重复值完毕");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取资产索引list -> zc_index   
	 * @return
	 */
	public List<String> do_checkout(){
		List<String> l = new ArrayList<String>();
		try {
			conn = DbUtil.getConn();
			stmt = conn.createStatement();
			String sql = "select t.zc_id,count(t.zc_id) c from zczypp_checkout t group by t.zc_id";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				if(Integer.valueOf(rs.getString("c")) > 1  ){
					l.add(rs.getString("zc_id"));
				}
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
		return l;
	}
	
	
	public void checkout_result(){
		try {
			do_delete_repeat();
			List<String> list = do_checkout();
			if(!(list.size()>0)){
				System.out.println("没有重复值");
				return;
			};
			String sql = "select t.zc_id,t.zc_index,t.TXA50,t.TXT50,t.anlhtxt from zczypp_checkout t where t.zc_id = ?";
			conn = DbUtil.getConn();
			ps = conn.prepareStatement(sql);
			for(String s : list ){
				ps.setString(1, s);
				rs = ps.executeQuery();
				while(rs.next()){
					System.out.println("有重复值：zc_id="+rs.getString("zc_id")+";zc_index= "+rs.getString("zc_index")
							+";txa50= "+rs.getString("txa50")+";txt50= "+rs.getString("txt50")+";anlhtxt= "+rs.getString("anlhtxt"));
				}
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
	}
	public static void main(String[] args) {
		Zc_checkout zc = new Zc_checkout();
		zc.do_delete_repeat();
		zc.checkout_result();
	}
}
