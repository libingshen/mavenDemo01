package yidongwang_pp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import util.ConfigManager;
import util.DbUtil;



public class GetLocation {

	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	public GetLocation(Connection conn){
		this.conn=conn;
	}
	
	public List<Map<String,String>> getZcLocation(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(ConfigManager.getItemValue("/configuration/zc_zhanzhi_all"));
			while(rs.next()){
				HashMap<String,String> m = new HashMap<String,String>();
				m.put("ltext", rs.getString("ltext"));
				if(rs.getString("ltext").equals("信息网络分公司")
						|| rs.getString("ltext").equals("系统集成分公司")){
					m.put("zhanzhi_name", rs.getString("ltext"));
					list.add(m);
					
				}else if(rs.getString("ltext").equals("省公司本部")){
					m.put("zhanzhi_name", "海南省");
					list.add(m);
				}else{
					String ltext = rs.getString("ltext").substring(0,2);
					m.put("zhanzhi_name", ltext);
					list.add(m);
				}
			}
		} catch (Exception e) {
			System.out.println("资产打站址标识、异常");
			e.printStackTrace();
		} finally{
			try {
				stmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	public List<Map<String,String>> getZyLocation(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(ConfigManager.getItemValue("/configuration/zy_zhanzhi_all"));
			while(rs.next()){
				HashMap<String,String> m = new HashMap<String,String>();
				m.put("area_name", rs.getString("area_name"));
				if(rs.getString("area_name").equals("海南本地网")
						|| rs.getString("area_name").equals("海南省")){
					m.put("zhanzhi_name", "海南省");
					list.add(m);
					
				}else{
					String area_name = rs.getString("area_name").substring(0,2);
					m.put("zhanzhi_name", area_name);
					list.add(m);
				}
				
			}
		} catch (Exception e) {
			System.out.println("资产打站址标识、异常");
			e.printStackTrace();
		} finally{
			try {
				stmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	public static void main(String[] args) throws Exception {
		GetLocation g = new GetLocation(DbUtil.getConn());
		List<Map<String,String>> list = g.getZyLocation();
		System.out.println(list.get(1));
	}
}

	
