package thread_start;

import java.util.concurrent.CountDownLatch;

public class test {

	
	
	
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