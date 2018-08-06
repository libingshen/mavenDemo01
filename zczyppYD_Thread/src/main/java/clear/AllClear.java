package clear;

public class AllClear {

	public static void main(String[] args) {
		new Thread(){
			public void run(){
				new ClearZcId().doClear();
			}
		}.start();
		new Thread(){
			public void run(){
				new ClearZcId().doClear();
			}
		}.start();
		new Thread(){
			public void run(){
				new ClearZcIndex().doClear();
			}
		}.start();
		new Thread(){
			public void run(){
				new ClearZcZhanzhi().doClear();
			}
		}.start();
		new Thread(){
			public void run(){
				new ClearZyId().doClear();
			}
		}.start();
		new Thread(){
			public void run(){
				new ClearZyIndex().doClear();
			}
		}.start();
		new Thread(){
			public void run(){
				new ClearZyPP().doClear();
			}
		}.start();
		new Thread(){
			public void run(){
				new ClearZyZhanzhi().doClear();
			}
		}.start();
		new Thread(){
			public void run(){
				new ClearZyIndex2nd().doClear();
			}
		}.start();
//		new ClearYD_Statistic().doClear();
	}
}
