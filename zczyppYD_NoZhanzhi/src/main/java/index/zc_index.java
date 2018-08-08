package index;

import sqlUtil.ConnectSql;
import util.DbUtil;
import yidongwang_pp.GetLocation;

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
	
	private CountDownLatch countDownLatch;
	
	private Connection conn ;
	private Statement stmt ;
	
	public zc_index(Connection conn,CountDownLatch countDownLatch){
		super();
		this.countDownLatch=countDownLatch;
		this.conn=conn;
	}
	
	public void run(){
//		String logFileName = count_begin+"至"+count_end+"个站址打资产索引";
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
			List<Map<String,String>> location = new GetLocation(conn).getZcLocation();
			long startTime=System.currentTimeMillis();
			int count =1;
			for(Map<String,String> m: location){
				List<Map<String,String>> list =cs.zc_index(m.get("zhanzhi_name"));
				System.out.println("~~~~~~~~~~~~~~~~~~~~资产~~~~~~~~~~~~~~~"+m.get("zhanzhi_name"));
//				logPrint.println("-----开始为~~"+s+"~~站址打上索引；第"+count+"个站");
				long st=System.currentTimeMillis();
				for(Map<String,String> m1 : list){
					//System.out.println(m.get("sql"));
					if(m1.get("sql")=="" || m1.get("sql") ==null){
//						logPrint.println(s+"站址的"+m.get("zc_index")+" 索引，获取sql时出错");
					}
//					System.out.println(m1.get("sql"));
					stmt.execute(m1.get("sql"));
				}
				long et = System.currentTimeMillis();
//				logPrint.println("耗时："+(et-st)/1000+"s");
				System.out.println("耗时："+(et-st)/1000+"s");
				count++;
			}
			long endTime = System.currentTimeMillis();
//			logPrint.println("总耗时："+(endTime-startTime)/1000+"s");
			countDownLatch.countDown();
		} catch (Exception e) {
//			logPrint.println("资产打索引标识、异常");
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
		CountDownLatch countDownLatch = new CountDownLatch(1);
		new Thread(new zc_index(DbUtil.getConn(),countDownLatch)).start();
	}
}
