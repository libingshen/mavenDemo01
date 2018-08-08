package thread_start;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.dom4j.DocumentException;

import util.ConfigManager;
import util.DbUtil;

public class Make_YDStatistic {

	public void make_statistic() throws ClassNotFoundException, SQLException, DocumentException{
		Connection conn = DbUtil.getConn();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(ConfigManager.getItemValue("/configuration/statistic_exist1"));
		stmt.executeUpdate(ConfigManager.getItemValue("/configuration/statistic_exist0"));
	}
	public static void main(String[] args) throws Exception {
		new Make_YDStatistic().make_statistic();
	}
}
