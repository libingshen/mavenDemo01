import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;



public class Two2Two {
	
	public int run_pp(){
		Connection conn = null;
		PreparedStatement stmt = null;
		readZC_2nd zc = new readZC_2nd();
		readZY_2nd zy = new readZY_2nd();
		ConnectSql_s1 css = new ConnectSql_s1();
		List<Map<String,String>> location = css.getLocationCondition();
		int location_count = 0;
		int count = 0;
		int co =0 ;
		try {
			conn = DbUtil.getConn();
			stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/insertSqlPP"));
			for(Map<String,String> m : location){
				List<Map<String,String>> list = new getIndex2nd().getIndexList(m.get("zc_id"),m.get("zhanzhi_name"));
				System.out.println("开始匹配：基站"+m.get("zhanzhi_name"));
				location_count++;
				co=0;
				for(Map<String,String> m1 : list){
					List<String> zc_index = zc.getData(m1.get("zc_index"),m1.get("zc_index_2nd"),m.get("zc_id"),m.get("zhanzhi_name"));
					List<String> zy_index = zy.getData(m1.get("zc_index"),m1.get("zc_index_2nd"),m.get("zc_id"),m.get("zhanzhi_name"));
					System.out.println("zc_indx:"+m1.get("zc_index")+"~~~~"+"zc_index_2nd:"+m1.get("zc_index_2nd")+"~~~~~~~~~~~~~~~~count:zc:"+zc_index.size()+"~~;  zy:"+zy_index.size()+"~~~~~~~~");
					for(int i = 0; i<zc_index.size() && i<zy_index.size() ;i++){
						//System.out.println("zc_id:"+zc_index.get(i)+"~~~~~~~zy_id:"+zy_index.get(i) );
						stmt.setString(1,zc_index.get(i));
				    	stmt.setString(2,zy_index.get(i));
				    	stmt.addBatch();
						count++;
						co++;
					}
					zc_index=null;
					zy_index=null;
				}
				System.out.println(m.get("zhanzhi_name")+"。该站址总共匹配："+co+"条");
			}
			stmt.executeBatch();
			conn.close();
			stmt.close();
			System.out.println(location_count+"个站址总共匹配："+count+"条");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	
	}
	public static void main(String[] args) {
		Two2Two tt= new Two2Two();
		tt.run_pp();
	}
}


