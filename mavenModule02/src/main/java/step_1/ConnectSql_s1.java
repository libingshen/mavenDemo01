package step_1;

import util.ConfigManager;
import util.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectSql_s1 {

    public List<Map<String, String>> getLocationCondition() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            //获得数据库连接对象
            conn = DbUtil.getConn();
            stmt = conn.createStatement();
            //获得结果集对象
            rs = stmt.executeQuery(ConfigManager.getItemValue("/configuration/getLocationWhere"));
            while (rs.next()) {
                Map<String, String> m = new HashMap<String, String>();
                if (rs.getString("zhanzhi_name") != "" && rs.getString("zhanzhi_name") != null) {
                    m.put("zhanzhi_name", rs.getString("zhanzhi_name"));
                    m.put("ltext", rs.getString("ltext"));
                    m.put("anlhtxt", rs.getString("anlhtxt"));
                    m.put("zc_zz_key", rs.getString("zc_zz_key"));
                    m.put("zc_id", "光网OLT");
                    m.put("zy_id", "光网OLT");
                    list.add(m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    stmt.close();
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public List<Map<String, String>> zhanzhi_name_pp() {
        List<Map<String, String>> l = new ArrayList<Map<String, String>>();
        //ConnectSql_s1 cs = new ConnectSql_s1();
        List<Map<String, String>> list = this.getLocationCondition();
        for (Map<String, String> m : list) {
            Map<String, String> sqlMap = new HashMap<String, String>();
            String sql = "update (select * from zc_copy_test01 t ";
            sql = sql + " where t.ltext like '%" + m.get("ltext") + "%' and t.ANLHTXT like '%" + m.get("anlhtxt") + "%'";
            sql = sql + " and " + m.get("zc_zz_key");
            sql += ") k set k.zhanzhi_name = '" + m.get("zhanzhi_name") + "',k.zc_id='光网OLT'";
            System.out.println("----------------------------------" + m.get("zhanzhi_name"));
            System.out.println(sql);
            sqlMap.put("zhanzhi_name", m.get("zhanzhi_name"));
            sqlMap.put("sql", sql);
            l.add(sqlMap);
        }
        return l;
    }

    public static void main(String[] args) {
        ConnectSql_s1 css = new ConnectSql_s1();
        css.zhanzhi_name_pp();
    }
}
