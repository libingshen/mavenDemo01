package index;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.dom4j.DocumentException;

import sqlUtil.ConnectSql;
import util.ConfigManager;
import util.DbUtil;
import yidongwang_pp.GetLocation;
/**
 * 资产资源第二步：为资源打上索引
 * @author LJP
 *
 */
public class zy_index implements Runnable{
	
	private CountDownLatch countDownLatch;
	
	private Connection conn ;
	private Statement stmt ;
	
	
	public zy_index(Connection conn,CountDownLatch countDownLatch){
		this.countDownLatch =countDownLatch;
		this.conn=conn;
	}
	
	public void run(){
//		String logFileName = count_begin+"至"+count_end+"个站址打资源索引";
//		String logPath = "F:"+File.separator+"YDlog"+File.separator+"移动网索引"+File.separator+logFileName+".txt";
//		PrintWriter logPrint;
//		try {
//			logPrint = new PrintWriter(new FileWriter(logPath, true), true);
//		} catch (IOException e1) {
//			System.out.println(logFileName+",异常");
//			countDownLatch.countDown();
//			return;
//		}
//		logPrint.println("开始为第"+logFileName);
		try {
			stmt = conn.createStatement();
			ConnectSql cs = new ConnectSql(conn);
			List<Map<String,String>> location = new GetLocation(conn).getZyLocation();
			long st=System.currentTimeMillis();
			int count =1;
			for(Map<String,String> m : location){
				List<Map<String,String>> list = cs.zy_index(m.get("zhanzhi_name"));
				System.out.println("~~~~~~~~~~~~~~~~~~资源~~~~~~~~~~~~~~"+m.get("zhanzhi_name"));
//				logPrint.println("------------------------------开始为~~"+s+"~~站址打上索引；"+count);
				long startTime = System.currentTimeMillis();
				for(Map<String,String> m1 : list){
					if(m.get("sql")=="" || m.get("sql") ==null){
//						logPrint.println(s+"站址的"+m.get("zy_index")+" 索引，获取sql时出错");
					}
//					System.out.println(m1.get("sql"));
					stmt.execute(m1.get("sql"));
				}
				long endTime = System.currentTimeMillis();
//				logPrint.println("耗时："+(endTime-startTime)/1000+"s");
				System.out.println("耗时："+(endTime-startTime)/1000+"s");
				count++;
			}
			long ft = System.currentTimeMillis();
//			logPrint.println("总耗时："+(ft-st)/1000+"s");
			countDownLatch.countDown();
		} catch (Exception e) {
//			logPrint.println("资源打索引标识、错误");
			countDownLatch.countDown();
		} finally{
			if(conn != null){
				try {
					conn.close();
					stmt.close();
//					logPrint.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(2);
		new Thread(new zy_index(DbUtil.getConn(),countDownLatch)).start();
		new Thread(new zc_index(DbUtil.getConn(),countDownLatch)).start();
		countDownLatch.await();
		System.out.println("打索引完成");
	}
	
}
