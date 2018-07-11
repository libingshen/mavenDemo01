import java.util.UUID;

/**
 * ID生成工具类
 * @author yushp
 *
 */
public class UUIDUtil {

	/**
	 * 获取唯一ID
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
}
