package clear;

import util.ConfigManager;
import util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ClearZyIndex {

	public void doClear() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DbUtil.getConn();
			stmt = conn.createStatement();
			int d = stmt.executeUpdate(ConfigManager.getItemValue("/configuration/clearZyIndex"));
			if(d>0){
				System.out.println("去索引完毕："+d);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(conn != null){
				try {
					conn.close();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		ClearZyIndex czz = new ClearZyIndex();
		czz.doClear();
	}
}
