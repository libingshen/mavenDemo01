package thread_start;

import index.zc_index;
import index.zy_index;
import pp_third_step.Step_3rd;
import util.DbUtil;
import util.DeleteDirectory;
import yidongwang_pp.GetLocation;
import yidongwang_pp.Zhanzhi_pp_zc;
import yidongwang_pp.Zhanzhi_pp_zy;

import java.io.File;
import java.util.concurrent.CountDownLatch;

public class zczypp {
	
	public void do_pp(){
		//创建 文件夹 F:/YDlog
		String logDirPath = "C:"+File.separator+"YDlog";
		File logDir = new File(logDirPath);
    	if(logDir.exists()){
    		DeleteDirectory.deleteDir(logDir);
    	}
    	logDir.mkdirs();
		//创建文件夹 F:/YDlog/移动网站址
		String logZhanzhiStr = "C:"+File.separator+"YDlog"+File.separator+"移动网站址";
		File logZhanzhi = new File(logZhanzhiStr);
    	if(logZhanzhi.exists()){
    		DeleteDirectory.deleteDir(logZhanzhi);
    	}
    	logZhanzhi.mkdirs();
    	//创建文件夹 F:/YDlog/移动网索引
		String logIndexStr = "C:"+File.separator+"YDlog"+File.separator+"移动网索引";
		File logIndex = new File(logIndexStr);
    	if(logIndex.exists()){
    		DeleteDirectory.deleteDir(logIndex);
    	}
    	logIndex.mkdirs();
    	//创建文件夹 F:/YDlog/移动网匹配
		String logPPStr = "C:"+File.separator+"YDlog"+File.separator+"移动网匹配";
		File logPP = new File(logPPStr);
    	if(logPP.exists()){
    		DeleteDirectory.deleteDir(logPP);
    	}
    	logPP.mkdirs();
		
    	
		/**
		 * 第一步：资产 打上站址标识
		 * 		资源 打上站址标识
		 */
    	System.out.println("第一步：给资产、资源打上站址标识");
    	long st_1 = System.currentTimeMillis();
		Zhanzhi_pp_zc zz_zc = new Zhanzhi_pp_zc();
		zz_zc.do_zhanzhi_zcpp();
		Zhanzhi_pp_zy zz_zy = new Zhanzhi_pp_zy();
		zz_zy.do_zhanzhi_zypp();
		long et_1= System.currentTimeMillis();
		System.out.println("第一步耗时： "+(et_1-st_1)/1000+" s");
		/**
		 * 第二步： 以站址为单位
		 * 			为资产  打上  索引标识
		 * 			为资源  打上  索引标识
		 */
		System.out.println("第二步：给资产、资源打上索引标识");
		long st_2 = System.currentTimeMillis();
		int zhanzhi_count = new GetLocation().getZhanzhi_count();
		int times ;
    	int i1 = zhanzhi_count/100;
		int i2 = zhanzhi_count%100;
		if(i2 > 0){
			times = i1+1;						//次数，线程个数
		}else{
			times = i1;
		}
		System.out.println("打索引阶段需要启动线程个数为："+times);
		try {
			CountDownLatch countDownLatch = new CountDownLatch(times*2);
			int count_begin = 0;
			int count_end = 0;
			for (int i=0; i < times; i++) {
	    		count_begin = i*100;
	    		count_end = count_begin+99;
	    		new Thread(new zc_index(DbUtil.getConn(),count_begin,count_end,countDownLatch)).start();
	    		new Thread(new zy_index(DbUtil.getConn(),count_begin,count_end,countDownLatch)).start();
			}
			countDownLatch.await();		//主线程等待
			long et_2= System.currentTimeMillis();
			System.out.println("第二步耗时： "+(et_2-st_2)/1000+" s");
			
			/**
			 * 第三步： 以站址为单位
			 * 			根据  索引
			 * 				进行资产  资源匹配
			 */
			System.out.println("第三步:资产、资源匹配");
			long st_3= System.currentTimeMillis();
			System.out.println("匹配阶段需要启动线程个数为："+times);
			CountDownLatch countDownLatch_1 = new CountDownLatch(times);
			count_begin = 0;
    		count_end = 0;
    		for (int i=0;i<times;i++){
    			count_begin = i*100+1;
	    		count_end = count_begin+100-1;
	    		new Thread(new Step_3rd(DbUtil.getConn(),count_begin,count_end,countDownLatch_1)).start();
    		}
    		countDownLatch_1.await();
    		long et_3= System.currentTimeMillis();
    		System.out.println("第三步耗时： "+(et_3-st_3)/1000+" s");
    		System.out.println("开始数据统计");
    		new Make_YDStatistic().make_statistic();
    		System.out.println("完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
