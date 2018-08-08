package step_3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import step_1.ConnectSql_s1;
import util.ConfigManager;

public class OneToOne implements Runnable{
	private Connection conn ;
	private PreparedStatement stmt;
	private int count_begin;
	private int count_end;
	private CountDownLatch countDownLatch;
	
	public OneToOne(Connection conn,int count_begin,int count_end, CountDownLatch countDownLatch){
		this.conn= conn;
		this.count_begin=count_begin;
		this.count_end=count_end;
		this.countDownLatch=countDownLatch;
	}
	
	public void run() {
		String logFileName = count_begin+"至"+count_end+"站址，一级索引匹配";
		String logPath = "F:"+File.separator+"GWlog"+File.separator+"光网匹配"+File.separator+logFileName+".txt";
		PrintWriter logPrint;
		try {
			logPrint = new PrintWriter(new FileWriter(logPath, true), true);
		} catch (IOException e1) {
			System.out.println(logFileName+",异常");
			countDownLatch.countDown();
			return;
		}
		logPrint.println("开始匹配");
		
		readZC_1st zc = new readZC_1st(conn);
		readZY_1st zy = new readZY_1st(conn);
		long st = System.currentTimeMillis();
		int location_count=0;
		int count = 0;
		int co =0 ;
		try {
			stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/insertSqlPP"));
			ConnectSql_s1 cs1 = new ConnectSql_s1(conn);
			List<Map<String,String>> location = cs1.getLocationCondition_zczypp(count_begin,count_end);
			for(Map<String,String> m : location){
				location_count++;
				co=0;
				//获取一级索引
				List<String> list = new getIndex1st().getIndexList(m.get("zc_id"),m.get("zhanzhi_name"));
				//System.out.println("开始匹配：基站"+m.get("zhanzhi_name"));
				for(String s : list){
					List<String> zc_index = zc.getData(s,m.get("zc_id"),m.get("zhanzhi_name"));
					List<String> zy_index = zy.getData(s,m.get("zc_id"),m.get("zhanzhi_name"));
					//System.out.println("~~~~~~~zc_indx:"+s+"~~~~~~~~~~~~~~~~~~~~count:zc:"+zc_index.size()+"~~;  zy:"+zy_index.size()+"~~~~~~~~");
					logPrint.println("~~~~~~~zc_indx:"+s+"~~~~~~~~~~~~~~~~~~~~count:zc:"+zc_index.size()+"~~;  zy:"+zy_index.size()+"~~~~~~~~");
					for(int i = 0; i<zc_index.size() && i<zy_index.size() ;i++){
						//System.out.println("zc_id:"+zc_index.get(i)+"~~~~~~~zy_id:"+zy_index.get(i) );
						stmt.setString(1,zc_index.get(i));
				    	stmt.setString(2,zy_index.get(i));
				    	stmt.addBatch();
						count++;
						co++;
					}
					zc_index=null;
					zy_index=null;
				}
				//System.out.println(m.get("zhanzhi_name")+"。该站址总共匹配："+co+"条");
				logPrint.println("第"+location_count+"个站址"+m.get("zhanzhi_name")+"。该站址总共匹配："+co+"条");
			}
			stmt.executeBatch();
			long ft = System.currentTimeMillis();
			//System.out.println(location_count+"个站址总共匹配："+count+"条;总耗时"+(ft-st)/1000+"s");
			logPrint.println(location_count+"个站址总共匹配："+count+"条;总耗时"+(ft-st)/1000+"s");
			countDownLatch.countDown();
		} catch (Exception e) {
			e.printStackTrace();
			countDownLatch.countDown();
		}finally{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}


