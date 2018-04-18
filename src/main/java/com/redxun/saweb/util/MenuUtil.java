package com.redxun.saweb.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redxun.sys.core.entity.MenuNode;
/**
 * <pre> 
 * 描述：菜单工具类、获取
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014年8月1日-下午3:01:22
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public class MenuUtil {
	
	//@Resource
	//private  ICache<Object> icache;
	
	private static Map<String,List<MenuNode>> menuGroupsMap=new HashMap<String, List<MenuNode>>();

	public static List<MenuNode> getMenuGroups(String sysId) {
		return menuGroupsMap.get(sysId);
	}

	public static void setMenuGroups(String sysId,List<MenuNode> menuGroups) {
		menuGroupsMap.put(sysId, menuGroups);
	}
	
	public static void clearMenuCache(){
		menuGroupsMap.clear();
	}

}
