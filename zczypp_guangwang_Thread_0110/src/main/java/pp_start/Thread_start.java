package pp_start;


import step_1.ConnectSql_s1;
import step_1.Zhanzhi_pp_zc;
import step_1.Zhanzhi_pp_zy;
import step_2.zc_index;
import step_2.zy_index;
import step_3.OneToOne;
import step_3.Two2Two;
import util.DbUtil;
import util.DeleteDirectory;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * 总的资产资源匹配
 */
public class Thread_start {
	
	private int times;
	private int zhanzhi_count;
	private static int interval = 30;
	
	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
	public int getZhanzhi_count() {
		return zhanzhi_count;
	}

	public void setZhanzhi_count(int zhanzhi_count) {
		this.zhanzhi_count = zhanzhi_count;
	}

	public void star_step_1(CountDownLatch countDownLatch){
		try {
			new Thread(new Zhanzhi_pp_zc(DbUtil.getConn(),countDownLatch)).start();
			new Thread(new Zhanzhi_pp_zy(DbUtil.getConn(),countDownLatch)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void star_step_2(CountDownLatch countDownLatch){
		//启动线程，给资产打上索引
		try {
			int count_begin = 0;
			int count_end = 0;
			for (int i=0; i < times; i++) {
	    		count_begin = i*interval+1;
	    		count_end = count_begin+interval-1;
	    		//资产匹配索引
	    		new Thread(new zc_index(DbUtil.getConn(),count_begin,count_end,countDownLatch)).start();
	    		new Thread(new zy_index(DbUtil.getConn(),count_begin,count_end,countDownLatch)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void star_step_3(){
		//启动线程，给资产打上索引
		try {
			int count_begin = 0;
			int count_end = 0;
			CountDownLatch countDownLatch_1 = new CountDownLatch(times);
			for (int i=0; i < times; i++) {
	    		count_begin = i*interval+1;
	    		count_end = count_begin+interval-1;
	    		//资产与资源一级匹配
	    		new Thread(new Two2Two(DbUtil.getConn(),count_begin,count_end,countDownLatch_1)).start();
			}
			countDownLatch_1.await();
			CountDownLatch countDownLatch_2 = new CountDownLatch(times);
			for (int i=0; i < times; i++){
				count_begin = i*interval+1;
	    		count_end = count_begin+interval-1;
	    		//资产与资源二级匹配
	    		new Thread(new OneToOne(DbUtil.getConn(),count_begin,count_end,countDownLatch_2)).start();
			}
			countDownLatch_2.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//创建 文件夹 F:/GWlog
		String logDirPath = "C:"+File.separator+"GWlog";
		File logDir = new File(logDirPath);
    	if(logDir.exists()){
    		DeleteDirectory.deleteDir(logDir);
    	}
    	logDir.mkdirs();
		//创建文件夹 F:/GWlog/光网站址
		String logZhanzhiStr = "C:"+File.separator+"GWlog"+File.separator+"光网站址";
		File logZhanzhi = new File(logZhanzhiStr);
    	if(logZhanzhi.exists()){
    		DeleteDirectory.deleteDir(logZhanzhi);
    	}
    	logZhanzhi.mkdirs();
    	//创建文件夹 F:/GWlog/光网索引
		String logIndexStr = "C:"+File.separator+"GWlog"+File.separator+"光网索引";
		File logIndex = new File(logIndexStr);
    	if(logIndex.exists()){
    		DeleteDirectory.deleteDir(logIndex);
    	}
    	logIndex.mkdirs();
    	//创建文件夹 F:/GWlog/光网匹配
		String logPPStr = "C:"+File.separator+"GWlog"+File.separator+"光网匹配";
		File logPP = new File(logPPStr);
    	if(logPP.exists()){
    		DeleteDirectory.deleteDir(logPP);
    	}
    	logPP.mkdirs();
    	
		try {
			Thread_start ts = new Thread_start();
			ts.setZhanzhi_count( new ConnectSql_s1(DbUtil.getConn()).getLocationCondition_count());
			int i1 = ts.getZhanzhi_count()/interval;
			int i2 = ts.getZhanzhi_count()%interval;
			if(i2 > 0){
				ts.setTimes(i1+1) ;						//次数，线程个数
			}else{
				ts.setTimes(i1) ;
			}
			//第一步
//			System.out.println("第一步开始执行");
//			long st = System.currentTimeMillis();
//			CountDownLatch countDownLatch_1 = new CountDownLatch(2);
//			ts.star_step_1(countDownLatch_1);
//			countDownLatch_1.await();
//			long et = System.currentTimeMillis();
//			System.out.println("第一步结束，总用时： "+(et-st)/1000+" s");
//			//第二步
//			System.out.println("第二步开始执行");
//			long st = System.currentTimeMillis();
//			CountDownLatch countDownLatch_2 = new CountDownLatch(ts.getTimes()*2);
//			ts.star_step_2(countDownLatch_2);
//			countDownLatch_2.await();
//			long et = System.currentTimeMillis();
//			System.out.println("第二步结束，总用时： "+(et-st)/1000+" s");
			//第三步
			System.out.println("第三步开始执行");
			long st = System.currentTimeMillis();
			ts.star_step_3();
			long et = System.currentTimeMillis();
			System.out.println("第三步结束，总用时： "+(et-st)/1000+" s");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

