package pp_third_step;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import util.ConfigManager;
import util.DbUtil;
import yidongwang_pp.GetLocation;

public class Step_3rd implements Runnable{
	
	private int count_begin;
	private int count_end;
	private CountDownLatch countDownLatch;
	
	private Connection conn ;
	private PreparedStatement ps ;
	private Statement stmt;
	
	public Step_3rd(Connection conn,int count_begin,int count_end,CountDownLatch countDownLatch){
		this.count_begin=count_begin;
		this.count_end=count_end;
		this.countDownLatch=countDownLatch;
		this.conn = conn;
	}
	
	
	/**
	 * 资产资源匹配第三步：   资产编码和资源id有条件匹配
	 */
	public void run (){
		String logFileName = count_begin+"至"+count_end+"站址匹配";
		String logPath = "F:"+File.separator+"YDlog"+File.separator+"移动网匹配"+File.separator+logFileName+".txt";
		PrintWriter logPrint;
		try {
			logPrint = new PrintWriter(new FileWriter(logPath, true), true);
		} catch (IOException e1) {
			System.out.println(logFileName+",异常");
			countDownLatch.countDown();
			return;
		}
		logPrint.println("开始匹配");
		GetLocation gl = new GetLocation();
		getIndex gi = new getIndex();
		readZC rzc = new readZC();
		readZY rzy = new readZY();
		List<String> list = gl.getLocation_index(conn,count_begin,count_end);
		
		
		int zhanzhi_count=1;
		int count = 0;
		long st =System.currentTimeMillis();
		long et =System.currentTimeMillis();
		try{
			ps = conn.prepareStatement(ConfigManager.getItemValue("/configuration/insertSqlPP"));
			stmt = conn.createStatement();
			//循环基站
			for(String zhanzhi_name : list){
				List<String> listIndex = gi.getIndexList(conn,zhanzhi_name);
				//循环某基站的所有索引类型
				for(String s : listIndex){
					st = System.currentTimeMillis();
					List<String> zc_index = rzc.getData(conn,s,zhanzhi_name);
					List<String> zy_index = rzy.getData(conn,s,zhanzhi_name);
					logPrint.println("~~~~~~~zc_indx:"+s+"~~~~~~~~~~~~~~~~~~~~count:zc:"+zc_index.size()+"~~;  zy:"+zy_index.size()+"~~~~~~~~");
					do_statistic(zhanzhi_name,s,zc_index.size(),zy_index.size());
					//匹配当前索引
					for(int i = 0; i<zc_index.size() && i<zy_index.size() ;i++){
						//System.out.println("zc_id:"+zc_index.get(i)+"~~~~~~~zy_id:"+zy_index.get(i) );
						ps.setString(1,zc_index.get(i));
				    	ps.setString(2,zy_index.get(i));
				    	ps.addBatch();
						count++;
					}
					zc_index=null;
					zy_index=null;
				}
				ps.executeBatch();
				et = System.currentTimeMillis();
				logPrint.println("基站："+zhanzhi_name+"；总共匹配"+count+"条;耗时："+(et-st)/1000+"s;"+zhanzhi_count);
				zhanzhi_count++;
				count=0;
			}
			countDownLatch.countDown();
		}catch (Exception e) {
			e.printStackTrace();
			countDownLatch.countDown();
		}finally{
			try {
				ps.close();
				conn.close();
				logPrint.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 插入 统计表YD_STATISTIC 数据
	 * @param zhanzhi
	 * @param zc_index
	 * @param zc_num
	 * @param zy_num
	 * @throws SQLException
	 */
	public void do_statistic(String zhanzhi,String zc_index,int zc_num,int zy_num) throws SQLException{
		int zczy_minus = zc_num-zy_num;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into YD_STATISTIC (zhanzhi,zc_id,zc_index,zc_num,zy_num,zczy_minus) values(");
		sb.append("'"+zhanzhi+"','"+"移动网"+"','"+zc_index+"','"+zc_num+"','"+zy_num+"','"+zczy_minus+"')");
		stmt.execute(sb.toString());
	}
}
