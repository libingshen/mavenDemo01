public class GuangWangPP {
	public static void main(String[] args) {
		OneToOne one = new OneToOne();
		Two2Two two = new Two2Two();
		int o = one.run_o2o();
		int t = two.run_pp();
		System.out.println("总共匹配："+ (o+t));
	}
}
