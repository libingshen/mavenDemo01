package sqlUtil;

public class test {
public static void main(String[] args) {
	String s = "update (select * from zy_copy_test01 t "
			+	"where"+" {zy_guize}"
			+	" and t.zhanzhi_name = "+" '{zhanzhi_name}' "
			+	") k set k.zy_index = "+ " '{zy_index}' ";
	String sql = s;
	System.out.println("sql before==========>"+sql);
	sql=sql.replace("{zhanzhi_name}", "海南");
	sql=sql.replace("{zy_index}", "海南");
	System.out.println("sql after============>"+sql);
}
}
