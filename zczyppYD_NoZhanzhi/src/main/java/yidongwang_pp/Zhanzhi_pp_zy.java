package yidongwang_pp;

import util.ConfigManager;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
/**
 * 资产资源第一步：为资源表打上基站
 * @author LJP
 *
 */
public class Zhanzhi_pp_zy {
	private Connection conn;
	private PreparedStatement ps;
	
	public Zhanzhi_pp_zy(Connection conn){
		this.conn=conn;
	}
	/**
	 * 资产表  插入zhanzhi_name字段数据 
	 * @param
	 */
	public void do_zhanzhi_zypp() {
		int count =0;
		try {
			List<Map<String,String>> list = new GetLocation(conn).getZyLocation(); 
			ps = conn.prepareStatement(ConfigManager.getItemValue("/configuration/zy_zhanzhi_update"));
			for(Map<String,String> m : list){
				System.out.println(m.get("zhanzhi_name")+":"+m.get("area_name"));
				ps.setString(1,m.get("zhanzhi_name"));
				ps.setString(2,"%"+m.get("area_name")+"%");
				ps.addBatch();
				count++;
				if(count%3000==0){
					ps.executeBatch();
				}
			}
			ps.executeBatch();
		} catch (Exception e) {
			System.out.println("资源打站址标识、异常");
			e.printStackTrace();
		} finally{
			try {
				conn.close();
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws Exception {
		new Zhanzhi_pp_zy(DbUtil.getConn()).do_zhanzhi_zypp();
	}
}
