import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;



public class Zhanzhi_pp_zc {
	
	public void do_zhanzhi_pp(){
		Connection conn =null;
		Statement stmt = null;
		try {
			conn = DbUtil.getConn();
			stmt = conn.createStatement();
			ConnectSql_s1 cs = new ConnectSql_s1();
			List<Map<String,String>> location = cs.zhanzhi_name_pp();
			long st=System.currentTimeMillis();
			int count =0;
			for(Map<String,String> m : location){
				System.out.println("------------------------------开始为~~"+m.get("zhanzhi_name")+"~~站址打上zhanzhi_name");
				long startTime = System.currentTimeMillis();
				System.out.println(m.get("sql"));
				stmt.executeUpdate(m.get("sql"));
				long endTime = System.currentTimeMillis();
				System.out.println("匹配一个站址耗时："+(endTime-startTime)/1000+"s");
				count++;
			}
			long et = System.currentTimeMillis();
			System.out.println("总耗时"+(et-st)/1000+"s"+",总共打上"+count+"个站址ַ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(conn != null){
				try {
					conn.close();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		Zhanzhi_pp_zc zzpp = new Zhanzhi_pp_zc();
		zzpp.do_zhanzhi_pp();
	}
}
