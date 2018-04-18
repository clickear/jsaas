import com.redxun.core.util.BeanUtil;

/**
 * Ognl工具类
 * <p>
 * 主要是为了在ognl表达式访问静态方法时可以减少长长的类名称编写 Ognl访问静态方法的表达式
 * </p>
 * 
 * @class@method(args) 示例使用:
 * 
 *                     <pre>
 * 	&lt;if test=&quot;@Ognl@isNotEmpty(userId)&quot;&gt;
 * 	and user_id = #{userId}
 * &lt;/if&gt;
 * </pre>
 * 
 * @author badqiu
 */
public class Ognl {
	/**
	 * 可以用于判断 Map,Collection,String,Array,Long是否为空
	 * 
	 * @param o
	 *            java.lang.Object.
	 * @return boolean.
	 */
	public static boolean isEmpty(Object o) throws IllegalArgumentException {
		return BeanUtil.isEmpty(o);
	}

	/**
	 * 可以用于判断 Map,Collection,String,Array是否不为空
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}

	/**
	 * 可以用于判断Long类型是否不为空
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isNotEmpty(Long o) {
		return !isEmpty(o);
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNumber(Object o) {
		return BeanUtil.isNumber(o);
	}

	/**
	 * 判断是否相等
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean equals(Object o1, Object o2) {
		return o1.equals(o2);
	}
	/**
	 * 判断是否相等
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean notEquals(Object o1, Object o2) {
		return !equals(o1, o2);
	}
	
	
}
