package step_1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import util.ConfigManager;
import util.DbUtil;

public class Zhanzhi_pp_zy implements Runnable{
	
	private Connection conn;
	private PreparedStatement stmt ;
	private CountDownLatch countDownLatch;
	
	public Zhanzhi_pp_zy(Connection conn,CountDownLatch countDownLatch){
		this.conn = conn;
		this.countDownLatch=countDownLatch;
	}
	
	public void run() {
		String logFileName = "资源打上站址";
		String logPath = "F:"+File.separator+"GWlog"+File.separator+"光网站址"+File.separator+logFileName+".txt";
		PrintWriter logPrint;
		try {
			logPrint = new PrintWriter(new FileWriter(logPath, true), true);
		} catch (IOException e1) {
			System.out.println(logFileName+",文件创建异常");
			countDownLatch.countDown();
			return;
		}
		
	    int count = 0;
		try {
			conn = DbUtil.getConn();
			ConnectSql_s1 css = new ConnectSql_s1(conn);
			stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/zhanzhi_pp_zy"));
			List<Map<String,String>> location = css.getLocationCondition();
			for(Map<String,String> m : location){
				stmt.setString(1, m.get("zhanzhi_name"));
				stmt.setString(2, "%"+m.get("zhanzhi_name")+"%");
				stmt.execute();
				count++;
				//System.out.println(count+"插入："+m.get("zhanzhi_name"));
				logPrint.println(count+"插入："+m.get("zhanzhi_name"));
			}
			//System.out.println("资源总共匹配"+count+"个基站");
			logPrint.println("资源总共匹配"+count+"个基站");
			countDownLatch.countDown();
		} catch (Exception e) {
			System.out.println("资源站址打标识异常");
			e.printStackTrace();
			countDownLatch.countDown();
		} finally{
			try {
				conn.close();
				stmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
