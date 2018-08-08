package step_2;

import step_1.ConnectSql_s1;
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
/**
 * 资产资源匹配第二步：为资产打上索引
 * @author LJP
 *
 */
public class zc_index implements Runnable{
	private int begin_count ;
	private int end_count;
	
	private Connection conn ;
	private Statement stmt ;
	
	private CountDownLatch countDownLatch;
	
	public zc_index(Connection conn,int begin_count,int end_count,CountDownLatch countDownLatch){
		this.conn = conn;
		this.begin_count=begin_count;
		this.end_count = end_count;
		this.countDownLatch=countDownLatch;
	}
	
	public void run(){
		String logFileName = begin_count+"至"+end_count+"个站址打资产索引";
		String logPath = "C:"+File.separator+"GWlog"+File.separator+"光网索引"+File.separator+logFileName+".txt";
		PrintWriter logPrint;
		try {
			logPrint = new PrintWriter(new FileWriter(logPath, true), true);
		} catch (IOException e1) {
			System.out.println(logFileName+",异常");
			countDownLatch.countDown();
			return;
		}
		logPrint.println("开始为第"+logFileName);
		try {
			stmt = conn.createStatement();
			ConnectSql_s1 cs1 = new ConnectSql_s1(conn);
			//取站址
			List<Map<String,String>> location = cs1.getLocationCondition_index(begin_count,end_count);
			ConnectSql_s2 cs2 = new ConnectSql_s2(conn);
			long st=System.currentTimeMillis();
			int count=0;
			for(Map<String,String> m : location){
				//获得打索引的SQL
				Map<String,List<Map<String,String>>> level_map = cs2.zc_index(m.get("zhanzhi_name"));
				//System.out.println("----第"+count+"个站址；----------------开始为~~"+m.get("zhanzhi_name")+"~~站址打上索引");
				logPrint.println("----第"+count+"个站址；----------------开始为~~"+m.get("zhanzhi_name")+"~~站址打上索引");
				long startTime = System.currentTimeMillis();
				
				for(Map<String,String> m1 : level_map.get("1")){
					//System.out.println(m1.get("sql"));
					stmt.execute(m1.get("sql"));
				}
				/*for(Map<String,String> m2 : level_map.get("2")){
					//System.out.println(m2.get("sql"));
					stmt.execute(m2.get("sql"));
				}*/
				long endTime = System.currentTimeMillis();
				//System.out.println("耗时："+(endTime-startTime)/1000+"s");
				logPrint.println("耗时："+(endTime-startTime)/1000+"s");
				count++;
			}
			long ft = System.currentTimeMillis();
			//System.out.println("资产索引匹配"+count+"个基站，总耗时："+(ft-st)/1000+"s；第"+begin_count+"至"+end_count+"基站");
			logPrint.println("资产索引匹配"+count+"个基站，总耗时："+(ft-st)/1000+"s；第"+begin_count+"至"+end_count+"基站");
			countDownLatch.countDown();
		} catch (Exception e) {
			e.printStackTrace();
			countDownLatch.countDown();
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
	public static void main(String[] args) throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		new Thread(new zc_index(DbUtil.getConn(),1,2,countDownLatch)).start();
	}
}
