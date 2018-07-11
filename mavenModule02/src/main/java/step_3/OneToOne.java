package step_3;

import step_1.ConnectSql_s1;
import util.ConfigManager;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;



public class OneToOne {
	
	public int run_o2o() {
		Connection conn = null;
		PreparedStatement stmt = null;
		readZC_1st zc = new readZC_1st();
		readZY_1st zy = new readZY_1st();
		
		ConnectSql_s1 cs1 = new ConnectSql_s1();
		List<Map<String,String>> location = cs1.getLocationCondition();
		int location_count=0;
		int count = 0;
		int co =0 ;
		try {
			conn = DbUtil.getConn();
			stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/insertSqlPP"));
			for(Map<String,String> m : location){
				location_count++;
				co=0;
				//获取一级索引
				List<String> list = new getIndex1st().getIndexList(m.get("zc_id"),m.get("zhanzhi_name"));
				System.out.println("开始匹配：基站"+m.get("zhanzhi_name"));
				for(String s : list){
					List<String> zc_index = zc.getData(s,m.get("zc_id"),m.get("zhanzhi_name"));
					List<String> zy_index = zy.getData(s,m.get("zc_id"),m.get("zhanzhi_name"));
					System.out.println("~~~~~~~zc_indx:"+s+"~~~~~~~~~~~~~~~~~~~~count:zc:"+zc_index.size()+"~~;  zy:"+zy_index.size()+"~~~~~~~~");
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
		OneToOne o =new OneToOne();
		o.run_o2o();
	}
}


