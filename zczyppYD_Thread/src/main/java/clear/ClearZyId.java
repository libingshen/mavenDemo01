package clear;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import util.ConfigManager;
import util.DbUtil;

public class ClearZyId {

	public void doClear() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DbUtil.getConn();
			stmt = conn.createStatement();
			int d = stmt.executeUpdate(ConfigManager.getItemValue("/configuration/clearZyId"));
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
		ClearZyId czz = new ClearZyId();
		czz.doClear();
	}
}
