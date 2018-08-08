package thread_start;

import java.util.concurrent.CountDownLatch;

public class test {

	/**
	 *
	 * CountDownLatch:
	 * http://mlxnle.iteye.com/blog/2367055
	 *		一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
	 *		用给定的计数初始化 CountDownLatch。
	 *		由于调用了countDown()方法，所以在当前计数到达零之前，await 方法会一直受阻塞。
	 *		到达0之后，会释放所有等待的线程，await 的所有后续调用都将立即返回。
	 * step.1
	 * 创建CountDownLatch 实例预定计数次数:100
	 *
	 * step.2
	 * 递减锁存器的计数，如果计数到达零，则释放所有等待的线程。
	 * 如果当前计数大于零，则将计数减少 1
	 *
	 * step.3
	 * 使当前线程在锁存器倒计数至零之前一直等待，除非线程被中断
	 * 如果当前的计数为零，则此方法立即返回
	 * @param args
	 */
	public static void main(String[] args) {
		CountDownLatch countDownLatch_1 = new CountDownLatch(100);
		Runnable r1 = new MyRunnable_1(countDownLatch_1);
		CountDownLatch countDownLatch_2 = new CountDownLatch(100);
		Runnable r2 = new MyRunnable_2(countDownLatch_2);
		CountDownLatch countDownLatch_3 = new CountDownLatch(100);
		Runnable r3 = new MyRunnable_3(countDownLatch_3);

		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		Thread t3 = new Thread(r3);
		
		
		try {
			t1.start();
			countDownLatch_1.await();
			t2.start();
			countDownLatch_2.await();
			t3.start();
			countDownLatch_3.await();
			System.out.println("it is ok");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		
	}
}

class MyRunnable_1 implements Runnable{
	
	private CountDownLatch countDownLatch;
	
	MyRunnable_1(CountDownLatch countDownLatch){
		this.countDownLatch=countDownLatch;
	}
	
	public void run() {
		for(int i=0;i<100;i++){
			System.out.println("你好！第"+i+"次");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			countDownLatch.countDown();
		}
	}
}
class MyRunnable_2 implements Runnable{

	private CountDownLatch countDownLatch;
	
	MyRunnable_2(CountDownLatch countDownLatch){
		this.countDownLatch=countDownLatch;
	}
	public void run() {
		for(int i=0;i<100;i++){
			System.out.println("未来更好!第"+i+"次");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			countDownLatch.countDown();
		}
	}
}
class MyRunnable_3 implements Runnable{

	private CountDownLatch countDownLatch;
	
	MyRunnable_3(CountDownLatch countDownLatch){
		this.countDownLatch=countDownLatch;
	}
	public void run() {
		for(int i=0;i<100;i++){
			System.out.println("执行成功!第"+i+"次");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			countDownLatch.countDown();
		}
	}
}