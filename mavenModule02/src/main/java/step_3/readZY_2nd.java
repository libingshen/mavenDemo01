import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class readZY_2nd {
	
	
	public List<String> getData(String index,String index_2nd,String zy_id,String zhanzhi_name){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			 conn = DbUtil.getConn();
			 stmt = conn.prepareStatement(ConfigManager.getItemValue("/configuration/selectSqlZY2nd"));
			 stmt.setString(1, index);
			 stmt.setString(2, index_2nd);
			 stmt.setString(3, zy_id);
			 stmt.setString(4, zhanzhi_name);
			 rs = stmt.executeQuery();
			 String queryZy = "id";
	         while(rs.next()){
	         	String str = rs.getString(queryZy);
	        	list.add(str);
	         }
	         stmt.close();
	         conn.close();
		} catch (Exception e) {
			e.printStackTrace();  
		} finally{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		readZY_2nd readzy = new readZY_2nd();
		List<String> list = readzy.getData("AN5516","OLT10","光网OLT","海口白坡");
		for(int i =0 ; i<list.size();i++){
			System.out.println(list.get(i));
		}
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间："+(endTime-startTime)/1000/60+"m");
	}
}
