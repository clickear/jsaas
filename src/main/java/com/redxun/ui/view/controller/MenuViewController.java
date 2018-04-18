package com.redxun.ui.view.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysMenu;
import com.redxun.sys.core.manager.SysMenuManager;
import com.redxun.sys.core.util.SysPropertiesUtil;
import com.redxun.sys.org.manager.OsGroupMenuManager;

@Controller
@RequestMapping("/ui/view/menuView/")
public class MenuViewController {
	
	@Resource SysMenuManager sysMenuManager;
	
	@Resource
	OsGroupMenuManager osGroupMenuManager;
	
	/**
	 * 单页功能集
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("funPage")
	public ModelAndView funPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String menuId=request.getParameter("menuId");
		//按权限过滤 TODO
		List<SysMenu> menus=sysMenuManager.getByParentId(menuId);
		return new ModelAndView("/ui/view/menuViewFunPage.jsp").addObject("menus",menus);
	}
	/**
	 * 多页功能集
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("funsPage")
	public ModelAndView funsPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String menuId=request.getParameter("menuId");
		Map<SysMenu,List<SysMenu>> tabMenuMap=new LinkedHashMap<SysMenu, List<SysMenu>>();
		//按权限过滤 TODO
		List<SysMenu> tabs=sysMenuManager.getByParentId(menuId);
		for(SysMenu tab:tabs){
			//按权限过滤 TODO
			List<SysMenu> menus=sysMenuManager.getByParentId(tab.getMenuId());
			tabMenuMap.put(tab,menus);
		}
		return new ModelAndView("/ui/view/menuViewFunsPage.jsp").addObject("tabMenuMap",tabMenuMap );
	}
	
	/**
	 * 功能面板
	 * @author Stephen
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("menuPanel")
	public ModelAndView menuPanel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String key=request.getParameter("key");
		String menuId=request.getParameter("menuId");
		SysMenu sysMenu = new SysMenu();	
		if(StringUtils.isNotEmpty(menuId)){
			sysMenu=sysMenuManager.get(menuId);
		}else if(StringUtil.isNotEmpty(key)){//根据key获取菜单
			sysMenu = sysMenuManager.getByKey(key);
		}
		IUser curUser = ContextUtil.getCurrentUser();
		
		//TODO 如果是租户管理员的话
		
		
		//判断用户的该菜单下，是否有更多菜单		
		List<SysMenu> menus = null;
		
		//顶级管理员
		if(WebAppUtil.isSaasMgrUser() && curUser.isSuperAdmin()){			
			menus = sysMenuManager.getMenusByParentId(sysMenu.getMenuId());
			//租户管理员
		}else if(curUser.isSuperAdmin()){
			menus = sysMenuManager.getMenusByTenantUser(sysMenu.getMenuId(),curUser.getUserId());
		}else{
			menus = sysMenuManager.getMenusByParentId(sysMenu.getMenuId());
		}
		
		//非超管
		if(!curUser.isSuperAdmin()){
			if(menus!=null&&menus.size()!=0){
				Iterator<SysMenu> it = menus.iterator();
				while(it.hasNext()){
					SysMenu subMenu = it.next();
					SysMenu userMenu = sysMenuManager.getByUserMenuId(curUser.getUserId(),subMenu.getMenuId());
					if(userMenu==null){
						it.remove();
						continue;
					}	
					List<SysMenu> subMenuList = sysMenuManager.getMenusByParentId(userMenu.getMenuId());
					//二级
					if(subMenuList!=null&&subMenuList.size()!=0){
						Iterator<SysMenu> its = subMenuList.iterator();
						while(its.hasNext()){
							SysMenu thirdMenu = its.next();
							SysMenu thirdMenuEntity = sysMenuManager.getByUserMenuId(curUser.getUserId(),thirdMenu.getMenuId());
							if(thirdMenuEntity==null){
								its.remove();
							}			   
						}
						subMenu.setChildList(subMenuList);	
					}	
					
				}
			}		
		}else{
			
			for(SysMenu subMenu: menus){
				List<SysMenu> subMenuList = new ArrayList<SysMenu>();
				if(WebAppUtil.isSaasMgrUser() && curUser.isSuperAdmin()){
					subMenuList = sysMenuManager.getMenusByParentId(subMenu.getMenuId());
				}else if(curUser.isSuperAdmin()){
					 subMenuList =  sysMenuManager.getMenusByTenantUser(subMenu.getMenuId(),curUser.getUserId());
				}
				
				if(subMenuList!=null&&subMenuList.size()!=0){
					subMenu.setChildList(subMenuList);				
				}
			}
		}			
		
		//功能面板集显示方式
		String menuDisplay = SysPropertiesUtil.getGlobalProperty("menuDisplay");
		if(StringUtil.isEmpty(menuDisplay)){
			menuDisplay = "block";
		}	
		
		if("FUN".equals(sysMenu.getShowType())){
			return new ModelAndView("/ui/view/menuViewFunTemplete.jsp").addObject("menus",menus).addObject("curMenu", sysMenu);
		}
					
		if("FUNS".equals(sysMenu.getShowType())){
			return new ModelAndView("/ui/view/menuViewFunTabTemplete.jsp").addObject("menus",menus).addObject("curMenu", sysMenu).addObject("menuDisplay", menuDisplay);
		}		
		return new ModelAndView("/ui/view/menuViewFunTemplete.jsp").addObject("menus",menus).addObject("curMenu", sysMenu);	
			
	}
	
	
	
	
	
	
	
}
