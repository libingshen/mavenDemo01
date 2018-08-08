package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.dom4j.DocumentException;

public class DbUtil {
	public static Connection getConn() throws ClassNotFoundException, SQLException, DocumentException{
		Class.forName(ConfigManager.getItemValue("/configuration/srcdatabase/driver")); 
        Connection srcConn = DriverManager.getConnection(
        		ConfigManager.getItemValue("/configuration/srcdatabase/url"), 
        		ConfigManager.getItemValue("/configuration/srcdatabase/username"), 
        		ConfigManager.getItemValue("/configuration/srcdatabase/password"));  
        return srcConn;
	}
}
