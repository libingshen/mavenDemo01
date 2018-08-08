package pp_third_step;

import util.ConfigManager;
import util.DbUtil;
import yidongwang_pp.GetLocation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class Step_3rd implements Runnable{
	
//	private int count_begin;
//	private int count_end;
//	private CountDownLatch countDownLatch;
	
	private Connection conn ;
	private PreparedStatement ps ;
	private Statement stmt;
	
	public Step_3rd(Connection conn){
//		this.count_begin=count_begin;
//		this.count_end=count_end;
//		this.countDownLatch=countDownLatch;
		this.conn = conn;
	}
	
	
	/**
	 * 资产资源匹配第三步：   资产编码和资源id有条件匹配
	 */
	public void run (){
//		String logFileName = count_begin+"至"+count_end+"站址匹配";
//		String logPath = "F:"+File.separator+"YDlog"+File.separator+"移动网匹配"+File.separator+logFileName+".txt";
//		PrintWriter logPrint;
//		try {
//			logPrint = new PrintWriter(new FileWriter(logPath, true), true);
//		} catch (IOException e1) {
//			System.out.println(logFileName+",异常");
//			countDownLatch.countDown();
//			return;
//		}
//		logPrint.println("开始匹配");
		List<Map<String,String>> list = new GetLocation(conn).getZcLocation();
		
		int zhanzhi_count=1;
		int count = 0;
		long st =System.currentTimeMillis();
		long et =System.currentTimeMillis();
		try{
			ps = conn.prepareStatement(ConfigManager.getItemValue("/configuration/insertSqlStatistic"));
			stmt = conn.createStatement();
			//循环基站
			for(Map<String,String> m: list){
				List<String> listIndex = new getIndex().getIndexList(conn,m.get("zhanzhi_name"));
				System.out.println(m.get("zhanzhi_name"));
				//循环某基站的所有索引类型
				for(String s : listIndex){
					st = System.currentTimeMillis();
					List<String> zc_index = new readZC().getData(conn,s,m.get("zhanzhi_name"));
					List<String> zy_index = new readZY().getData(conn,s,m.get("zhanzhi_name"));
//					logPrint.println("~~~~~~~zc_indx:"+s+"~~~~~~~~~~~~~~~~~~~~count:zc:"+zc_index.size()+"~~;  zy:"+zy_index.size()+"~~~~~~~~");
					//匹配当前索引
//					for(int i = 0; i<zc_index.size() && i<zy_index.size() ;i++){
//						//System.out.println("zc_id:"+zc_index.get(i)+"~~~~~~~zy_id:"+zy_index.get(i) );
//						ps.setString(1,zc_index.get(i));
//				    	ps.setString(2,zy_index.get(i));
//				    	ps.addBatch();
//						count++;
//					}
					int zc_num = zc_index.size();
					int zy_num = zy_index.size();
					ps.setString(1, m.get("zhanzhi_name"));
					ps.setString(2, s);
					ps.setString(3, zc_num+"");
					ps.setString(4, zy_num+"");
					ps.setString(5, (zc_num<zy_num)?zc_num+"":zy_num+"");
					ps.addBatch();
					count++;
					if(count%3000==0){
						ps.executeBatch();
					}
					zc_index=null;
					zy_index=null;
				}
				ps.executeBatch();
				et = System.currentTimeMillis();
//				logPrint.println("基站："+zhanzhi_name+"；总共匹配"+count+"条;耗时："+(et-st)/1000+"s;"+zhanzhi_count);
				zhanzhi_count++;
				count=0;
			}
//			countDownLatch.countDown();
		}catch (Exception e) {
			e.printStackTrace();
//			countDownLatch.countDown();
		}finally{
			try {
				ps.close();
				conn.close();
//				logPrint.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws Exception {
		new Thread(new Step_3rd(DbUtil.getConn())).start();
	}
}
