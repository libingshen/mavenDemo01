package index;

import sqlUtil.ConnectSql;
import yidongwang_pp.GetLocation;

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
	
	private int count_begin;
	private int count_end;
	private CountDownLatch countDownLatch;
	
	private Connection conn =null;
	private Statement stmt = null;
	
	public zc_index(Connection conn,int count_begin,int count_end,CountDownLatch countDownLatch){
		super();
		this.count_begin = count_begin;
		this.count_end = count_end;
		this.countDownLatch=countDownLatch;
		this.conn=conn;
	}
	
	public void run(){
		String logFileName = count_begin+"至"+count_end+"个站址打资产索引";
		String logPath = "F:"+File.separator+"YDlog"+File.separator+"移动网索引"+File.separator+logFileName+".txt";
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
			GetLocation gl = new GetLocation();
			ConnectSql cs = new ConnectSql(conn);
			List<String> location = gl.getLocation_index(conn,count_begin,count_end);
			long startTime=System.currentTimeMillis();
			int count =1;
			for(String s : location){
				List<Map<String,String>> list =cs.zc_index(s);
				logPrint.println("-----开始为~~"+s+"~~站址打上索引；第"+count+"个站");
				long st=System.currentTimeMillis();
				for(Map<String,String> m : list){
					//System.out.println(m.get("sql"));
					if(m.get("sql")=="" || m.get("sql") ==null){
						logPrint.println(s+"站址的"+m.get("zc_index")+" 索引，获取sql时出错");
					}
					stmt.execute(m.get("sql"));
				}
				long et = System.currentTimeMillis();
				logPrint.println("耗时："+(et-st)/1000+"s");
				count++;
			}
			long endTime = System.currentTimeMillis();
			logPrint.println("总耗时："+(endTime-startTime)/1000+"s");
			countDownLatch.countDown();
		} catch (Exception e) {
			logPrint.println("资产打索引标识、异常");
			countDownLatch.countDown();
		} finally{
			if(conn != null){
				try {
					conn.close();
					stmt.close();
					logPrint.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
