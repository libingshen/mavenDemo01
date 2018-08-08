package step_1;

import util.DbUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


public class Zhanzhi_pp_zc implements Runnable{
	
	private Connection conn;
	private Statement stmt ;
	private CountDownLatch countDownLatch;
	
	public Zhanzhi_pp_zc(Connection conn,CountDownLatch countDownLatch){
		this.conn = conn;
		this.countDownLatch=countDownLatch;
	}
	
	
	public void run(){
		String logFileName = "资产打上站址";
		String logPath = "F:"+File.separator+"GWlog"+File.separator+"光网站址"+File.separator+logFileName+".txt";
		PrintWriter logPrint;
		try {
			logPrint = new PrintWriter(new FileWriter(logPath, true), true);
		} catch (IOException e1) {
			System.out.println(logFileName+",文件创建异常");
			countDownLatch.countDown();
			return;
		}
		try {
			stmt = conn.createStatement();
			ConnectSql_s1 cs = new ConnectSql_s1(conn);
			List<Map<String,String>> location = cs.zhanzhi_name_pp();
			long st=System.currentTimeMillis();
			int count =1;
			for(Map<String,String> m : location){
				//System.out.println("------------------------------开始为~~"+m.get("zhanzhi_name")+"~~站址打上zhanzhi_name");
				logPrint.println("------------------------------开始为~~"+m.get("zhanzhi_name")+"~~站址打上zhanzhi_name。 "+count);
				long startTime = System.currentTimeMillis();
				//System.out.println(m.get("sql"));
				stmt.executeUpdate(m.get("sql"));
				long endTime = System.currentTimeMillis();
				//System.out.println("匹配一个站址耗时："+(endTime-startTime)/1000+"s");
				logPrint.println("匹配一个站址耗时："+(endTime-startTime)+"ms");
				count++;
			}
			long et = System.currentTimeMillis();
			logPrint.println("总耗时"+(et-st)+"ms"+",总共打上"+count+"个站址ַ");
			countDownLatch.countDown();
		} catch (Exception e) {
			System.out.println("资产站址打标识异常");
			countDownLatch.countDown();
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
	public static void main(String[] args) throws Exception{
		
		ConnectSql_s1 css = new ConnectSql_s1(DbUtil.getConn());
		List<Map<String,String>> l = css.zhanzhi_name_pp();
		for(Map<String,String> m : l){
			System.out.println(m.get("sql"));
		}
	}
}
