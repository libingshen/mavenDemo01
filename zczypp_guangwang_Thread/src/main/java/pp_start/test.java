package pp_start;

public class test {
	private int times;

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	public void s(){
		System.out.println(this.times);
	}
	public static void main(String[] args) {
		test t = new test();
		t.setTimes(100);	
		t.s();
	}
}
