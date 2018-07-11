import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;


/**
 * 资产资源匹配第二步：为资产打上索引
 * @author LJP
 *
 */
public class zc_index {
	
	public void do_zc_index(){
		Connection conn =null;
		Statement stmt = null;
		try {
			conn = DbUtil.getConn();
			stmt = conn.createStatement();
			ConnectSql_s1 cs1 = new ConnectSql_s1();
			ConnectSql_s2 cs2 = new ConnectSql_s2();
			List<Map<String,String>> location = cs1.getLocationCondition();
			long st=System.currentTimeMillis();
			int count=0;
			for(Map<String,String> m : location){
				Map<String,List<Map<String,String>>> level_map = cs2.zc_index(m.get("zhanzhi_name"));
				System.out.println("------------------------------开始为~~"+m.get("zhanzhi_name")+"~~站址打上索引");
				long startTime = System.currentTimeMillis();
				
				for(Map<String,String> m1 : level_map.get("1")){
					System.out.println(m1.get("sql"));
					stmt.execute(m1.get("sql"));
				}
				for(Map<String,String> m2 : level_map.get("2")){
					System.out.println(m2.get("sql"));
					stmt.execute(m2.get("sql"));
				}
				long endTime = System.currentTimeMillis();
				System.out.println("耗时："+(endTime-startTime)/1000+"s");
			}
			long ft = System.currentTimeMillis();
			System.out.println("匹配"+count+"个基站，总耗时："+(ft-st)/1000+"s");
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
		zc_index z = new zc_index();
		z.do_zc_index();
	}
}
