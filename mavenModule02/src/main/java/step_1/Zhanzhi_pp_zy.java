import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;



public class Zhanzhi_pp_zy {
	
	public void do_zhanzhi_zypp() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ConnectSql_s1 css = new ConnectSql_s1();
		
	    int count = 0;
		try {
			conn = DbUtil.getConn();
			stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/zhanzhi_pp_zy"));
			List<Map<String,String>> location = css.getLocationCondition();
			for(Map<String,String> m : location){
				stmt.setString(1, m.get("zhanzhi_name"));
				stmt.setString(2, "%"+m.get("zhanzhi_name")+"%");
				stmt.execute();
				count++;
				System.out.println(count+"插入："+m.get("zhanzhi_name"));
			}
			System.out.println("资源总共匹配"+count+"个基站");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();
				stmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		Zhanzhi_pp_zy zzppzy = new Zhanzhi_pp_zy();
		zzppzy.do_zhanzhi_zypp();
	}

}
