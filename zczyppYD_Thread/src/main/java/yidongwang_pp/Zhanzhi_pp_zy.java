package yidongwang_pp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import util.ConfigManager;
import util.DbUtil;
/**
 * 资产资源第一步：为资源表打上基站
 * @author LJP
 *
 */
public class Zhanzhi_pp_zy {
	/**
	 * 资源表  插入zhanzhi_name数据
	 * @param args
	 */
	public void do_zhanzhi_zypp() {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String logFileName = "资源打上站址";
		String logPath = "F:"+File.separator+"YDlog"+File.separator+"移动网站址"+File.separator+logFileName+".txt";
		PrintWriter logPrint;
		try {
			logPrint = new PrintWriter(new FileWriter(logPath, true), true);
		} catch (IOException e1) {
			System.out.println(logFileName+",异常");
			return;
		}
		
		GetLocation gl = new GetLocation();
		
	    int count = 0;
		try {
			conn = DbUtil.getConn();
			stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/zhanzhi_pp_zy"));
			List<String> location = gl.getLocation(conn);
			for(String s : location){
				stmt.setString(1, s);
				stmt.setString(2, "%"+s+"%");
				stmt.execute();
				count++;
				logPrint.println(count+"插入："+s);
			}
			logPrint.println("资源总共匹配"+count+"个基站");
			System.out.println("资源总共匹配"+count+"个基站");
		} catch (Exception e) {
			System.out.println("资源打站址标识、异常");
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
	
}
