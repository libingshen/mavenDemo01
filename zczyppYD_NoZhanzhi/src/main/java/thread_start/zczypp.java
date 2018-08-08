package thread_start;

import index.zc_index;
import index.zy_index;
import pp_third_step.Step_3rd;
import util.DbUtil;
import yidongwang_pp.Zhanzhi_pp_zc;
import yidongwang_pp.Zhanzhi_pp_zy;

import java.util.concurrent.CountDownLatch;

public class zczypp {
	
	public void do_pp(){
		try {
			/**
			 * 第一步打上站址
			 */
			System.out.println("第一步开始");
			Zhanzhi_pp_zc zzppzc = new Zhanzhi_pp_zc(DbUtil.getConn());
			zzppzc.do_zhanzhi_zcpp();
			Zhanzhi_pp_zy zzppzy = new Zhanzhi_pp_zy(DbUtil.getConn());
			zzppzy.do_zhanzhi_zypp();
			/**
			 * 第二步  打上索引
			 */
			System.out.println("第二步开始");
			CountDownLatch countDownLatch_2 = new CountDownLatch(2);
			new Thread(new zc_index(DbUtil.getConn(),countDownLatch_2)).start();
    		new Thread(new zy_index(DbUtil.getConn(),countDownLatch_2)).start();
    		countDownLatch_2.await();	
    		/**
    		 * 第三步 进行统计
    		 */
			System.out.println("第三步开始");
    		CountDownLatch countDownLatch_3 = new CountDownLatch(2);
    		new Thread(new Step_3rd(DbUtil.getConn())).start();
    		countDownLatch_3.await();	
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		zczypp z = new zczypp();
		z.do_pp();
	}
}
