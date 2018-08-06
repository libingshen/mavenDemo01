package yidongwang_pp;

import util.ConfigManager;
import util.DbUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
/**
 * 资产资源匹配第一步：给资产表打上  基站
 * @author LJP
 *
 */
public class Zhanzhi_pp_zc {
	/**
	 * 资产表  插入zhanzhi_name字段数据 
	 * @param
	 */
	public void do_zhanzhi_zcpp() {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String logFileName = "资产打上站址";
		String logPath = "C:"+File.separator+"YDlog"+File.separator+"移动网站址"+File.separator+logFileName+".txt";
		PrintWriter logPrint;
		try {
			logPrint = new PrintWriter(new FileWriter(logPath, true), true);
		} catch (IOException e1) {
			System.out.println(logFileName+",异常");
			return;
		}
		
		
		/**
		 * 原版获得地址规则
		 */
		GetLocation gl = new GetLocation();
		
	    int count = 0;
		try {
			conn = DbUtil.getConn();
			//预编译SQL
			stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/zhanzhi_pp"));
			//获得SQL参数
			List<Map<String,String>> location = gl.getLocation_zcpp(conn);
			for(Map<String,String> m : location){
				stmt.setString(1, m.get("zhanzhi_name"));
				stmt.setString(2, "%"+m.get("city")+"%");
				stmt.setString(3, "%"+m.get("zhanzhi_suoyin")+"%");
				stmt.setString(4, "%"+m.get("city")+"%");
				stmt.setString(5, "%"+m.get("zhanzhi_suoyin")+"%");
				stmt.execute();
				count++;
				logPrint.println("资产插入第 "+count+" 个站址；站址名： "+m.get("zhanzhi_name"));
				//System.out.println("资产插入第 "+count+" 个站址；站址名： "+m.get("zhanzhi_name"));
			}
			logPrint.println("资产总共匹配"+count+"个基站");
		} catch (Exception e) {
			System.out.println("资产打站址标识、异常");
			e.printStackTrace();
		} finally{
			try {
				conn.close();
				stmt.close();
				logPrint.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new Thread(){
			public void run(){
				new Zhanzhi_pp_zc().do_zhanzhi_zcpp();
			}
		}.start();
		
		new Thread(){
			public void run(){
				new Zhanzhi_pp_zy().do_zhanzhi_zypp();
			}
		}.start();
		
	}
}
