package clear;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import util.ConfigManager;
import util.DbUtil;

public class ClearYD_Statistic {

	public void doClear() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DbUtil.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(ConfigManager.getItemValue("/configuration/clearYD_Statistic"));
			System.out.println("清除统计表数据完毕");
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
		ClearYD_Statistic czz = new ClearYD_Statistic();
		czz.doClear();
	}

}
