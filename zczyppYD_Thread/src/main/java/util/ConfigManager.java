package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * <p>[读取配置文件]</p>
 * @author yushp
 *s
 */
public class ConfigManager {
	private static Document pathxmldoc = null;

	/**
	 * 
	 * <p>[读取文档]</p>
	 * @return
	 * @throws DocumentException 
	 */
	public static Document getPathXmlDoc() throws DocumentException{
		if (pathxmldoc!=null){
			return pathxmldoc;
		}else{
			SAXReader builder = new SAXReader();
			InputStream ins = ConfigManager.class
					.getResourceAsStream("/config.xml");
			pathxmldoc = builder.read(ins);
		}
		
		return pathxmldoc;
	}

	/**
	 * 
	 * <p>[根据配置文件标签路径获得节点值]</p>
	 * @param textlabel
	 * @return
	 * @throws DocumentException 
	 */
	public static String getItemValue(String textlabel) throws DocumentException{
		String value = "";
		Document doc = ConfigManager.getPathXmlDoc();
		Node node = null;
		node = doc.selectSingleNode(textlabel);  
		value = node.getStringValue().trim();   
		
		return value;
	}
	
	/**
	 * 
	 * <p>[根据配置文件标签路径获得子节点值]</p>
	 * @param textlabel
	 * @return
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getListValue(String textlabel) throws DocumentException{
		List<String> value = new ArrayList<String>();
		Document doc = ConfigManager.getPathXmlDoc();

		List<Node> node = null;
		node = doc.selectNodes(textlabel);  
		for(Node n: node){
			value.add(n.getStringValue().trim());
		}
		
		return value;
	}
}