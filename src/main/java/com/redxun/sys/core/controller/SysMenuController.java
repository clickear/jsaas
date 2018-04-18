package com.redxun.sys.core.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JsonResult;
import com.redxun.core.util.BeanUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseController;
import com.redxun.saweb.tag.ToolbarButtonType;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.entity.Subsystem;
import com.redxun.sys.core.entity.SysMenu;
import com.redxun.sys.core.entity.VirtualMenu;
import com.redxun.sys.core.manager.SubsystemManager;
import com.redxun.sys.core.manager.SysMenuManager;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.manager.OsRelInstManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 菜单控制器
 * @author csx
 *@Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysMenu")
public class SysMenuController extends BaseController{
	@Resource
	SysMenuManager sysMenuManager;
	@Resource
	SubsystemManager subsystemManager;
	@Resource
	OsRelInstManager osRelInstManager;
	@Resource(name="iJson")
	ObjectMapper objectMapper;

	@RequestMapping("getMenus")
	@ResponseBody
	public Collection<SysMenu> getMenus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String sysId=null;
		Cookie sysIdCookie=getCookie(request, "SYS_ID_");
		if(sysIdCookie!=null){
			sysId=sysIdCookie.getValue();
		}else{
			sysId=request.getParameter("sysId");
		}
		//基础子系统的id
		Subsystem baseSys=null;
		if(StringUtils.isEmpty(sysId)){
			baseSys=subsystemManager.getByKey("BASE_SYS");
			sysId=baseSys.getSysId();
		}
		
		//若为超级管理机构下的超级管理员，即显示全部菜单
		IUser curUser=ContextUtil.getCurrentUser();
		
		if(curUser.isSuperAdmin()){//Saas管理员

			List<SysMenu> menus= sysMenuManager.getBySysId(sysId);
			return menus;
		}
		else{//普通租户下的用户
			//先取得该用户授权下的用户组，再根据用户组获得其授权访问的菜单
			//应用系统进行菜单合并
			List<OsRelInst> groupInsts=osRelInstManager.getGrantGroupsByUserId(curUser.getUserId(), curUser.getTenant().getTenantId());
			//是否已经指定了系统，若没有，则获得授权访问的第一个子系统
			if(baseSys!=null){
				for(OsRelInst inst:groupInsts){
					String groupId=inst.getParty1();
					List<Subsystem> groupSyses=subsystemManager.getGrantSubsByGroupId(groupId);
					if(groupSyses!=null && groupSyses.size()>0){
						sysId=groupSyses.get(0).getSysId();
						break;
					}
				}
			}
			
			Set<SysMenu> grantMenus=new HashSet<SysMenu>();
			for(OsRelInst inst:groupInsts){
				String groupId=inst.getParty1();
				List<SysMenu> groupMenus=sysMenuManager.getGrantMenusBySysIdGroupId(sysId, groupId);
				grantMenus.addAll(groupMenus);
			}
			return grantMenus;
		}
	}
	
	/**
	 * 同步某个功能下的按钮至菜单资源表中以进行权限的控制
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("syncMenuBtns")
	@ResponseBody
	public JsonResult syncMenuBtns(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String menuId=request.getParameter("menuId");
		SysMenu parentMenu=sysMenuManager.get(menuId);
		String btns=request.getParameter("btns");
		com.alibaba.fastjson.JSONArray btnsArr=com.alibaba.fastjson.JSONArray.parseArray(btns);
		syncBtns(parentMenu,btnsArr);
		return new JsonResult(true,"成功同步菜单【"+parentMenu.getName()+"】下的按钮资源！");
	}
	
	private void syncBtns(SysMenu parentMenu,com.alibaba.fastjson.JSONArray btnsArr){
		for(int i=0;i<btnsArr.size();i++){
			com.alibaba.fastjson.JSONObject jsonObj=btnsArr.getJSONObject(i);
			String btnLabel=jsonObj.getString("btnLabel");
			String btnName=jsonObj.getString("btnName");
			String btnIconCls=jsonObj.getString("btnIconCls");
			String url=jsonObj.getString("url");
			if(StringUtils.isNotEmpty(url)){
				int index=url.indexOf("?");
				if(index!=-1){
					url=url.substring(0,index);
				}
			}
			if(StringUtils.isNotEmpty(url)&& StringUtils.isNotEmpty(btnName)){
				SysMenu btnMenu=sysMenuManager.getByParentIdMenuKey(parentMenu.getMenuId(), btnName);
				if(btnMenu==null){
					btnMenu=new SysMenu();
					btnMenu.setMenuId(IdUtil.getId());
					btnMenu.setName(btnLabel);
					btnMenu.setKey(btnName);
					btnMenu.setSubsystem(parentMenu.getSubsystem());
					btnMenu.setIsBtnMenu("YES");
					btnMenu.setUrl(url);
					btnMenu.setIconCls(btnIconCls);
					btnMenu.setShowType("URL");
					btnMenu.setSn(0);
					btnMenu.setParentId(parentMenu.getMenuId());
					btnMenu.setPath(parentMenu.getPath()+ btnMenu.getMenuId()+".");
					sysMenuManager.create(btnMenu);
				}else{
					btnMenu.setName(btnLabel);
					btnMenu.setKey(btnName);
					btnMenu.setUrl(url);
					btnMenu.setIconCls(btnIconCls);
					sysMenuManager.update(btnMenu);
				}
			}
			
			com.alibaba.fastjson.JSONArray childs=jsonObj.getJSONArray("children");
			
			if(childs==null) continue;
			
			syncBtns(parentMenu,childs);
		}
	}
	
	/**
	 * 菜单下的按钮管理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("buttonsMgr")
	public ModelAndView buttonsMgr(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String menuId=request.getParameter("menuId");
		SysMenu sysMenu=sysMenuManager.get(menuId);
		return getPathView(request).addObject("sysMenu",sysMenu);
	}
	/**
	 * 获得某菜单下的子菜单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByParentId")
	@ResponseBody
	public JsonResult getByParentId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String menuId=request.getParameter("menuId");
		List list=sysMenuManager.getByParentId(menuId);
		return new JsonResult(true,"",list);
	}
	/**
	 * 展示控件工具栏的按钮的列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("buttonTypes")
	@ResponseBody
	public ArrayNode buttonTypes(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ArrayNode arrayNode=objectMapper.createArrayNode();
		for(ToolbarButtonType type:ToolbarButtonType.values()){
			ObjectNode objectNode=objectMapper.createObjectNode();
			objectNode.put("name", type.name());
			objectNode.put("text", type.getText());
			arrayNode.add(objectNode);
		}
		return arrayNode;
	}
	
	/**
	 * 保存菜单下的功能按钮列表配置
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveButtons")
	@ResponseBody
	public JsonResult saveButtons(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String menuId=request.getParameter("menuId");
		String entityName=request.getParameter("entityName");
		SysMenu sysMenu=sysMenuManager.get(menuId);
		sysMenu.setEntityName(entityName);
		sysMenuManager.update(sysMenu);
		
		String buttonJson=request.getParameter("buttonJson");
		JSONArray jsonArr=JSONArray.fromObject(buttonJson);
		for(int i=0;i<jsonArr.size();i++){
			JSONObject jsonObj=jsonArr.getJSONObject(i);
			SysMenu tmpMenu=(SysMenu)JSONObject.toBean(jsonObj,SysMenu.class);
			if(StringUtils.isNotEmpty(tmpMenu.getMenuId())){
				tmpMenu.setEntityName(entityName);
				SysMenu orgMenu=sysMenuManager.get(tmpMenu.getMenuId());
				BeanUtil.copyNotNullProperties(orgMenu, tmpMenu);
				sysMenuManager.update(orgMenu);
			}else{
				tmpMenu.setIsBtnMenu(MBoolean.YES.name());
				tmpMenu.setParentId(sysMenu.getMenuId());
				tmpMenu.setEntityName(entityName);
				sysMenuManager.create(tmpMenu);
			}
		}
		
		return new JsonResult(true,"成功保存！");
	}
	

	/**
	 * 获取所有菜单的json串包括子系统
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("menuList")
	@ResponseBody
	public List<VirtualMenu> menuList(HttpServletRequest request,HttpServletResponse response){
		//String tenantId=ContextUtil.getCurrentTenantId();
		List<SysMenu> menus=sysMenuManager.getAll();
		List<Subsystem> subsystems=subsystemManager.getAll();
		List<VirtualMenu> virtualMenus=new ArrayList<VirtualMenu>();
		for (Subsystem subsystem : subsystems) {
        	VirtualMenu menu=new VirtualMenu();
			menu.setId(subsystem.getSysId());
			menu.setName(subsystem.getName());
			menu.setParent("0");
			//menu.setMenuIcon(subsystem.getLogo()+".png");
			menu.setIconCls(subsystem.getIconCls());
			menu.setMenuType("system");
			virtualMenus.add(menu);
		}
		for (SysMenu sysMenu : menus) {
			VirtualMenu menu=new VirtualMenu();
			menu.setId(sysMenu.getMenuId());
			menu.setName(sysMenu.getName());
			menu.setParent(sysMenu.getParentId());
			//menu.setMenuIcon(sysMenu.getImg());
			menu.setIconCls(sysMenu.getIconCls());
			menu.setMenuType("menu");
			virtualMenus.add(menu);
		}
        
		return virtualMenus;
	}
	
	/**
	 * 获取所有菜单的子系统及其子菜单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("newMenuList")
	@ResponseBody
	public List<VirtualMenu> newMenuList(HttpServletRequest request,HttpServletResponse response){
		//String tenantId=ContextUtil.getCurrentTenantId();
	
		List<SysMenu> menus= new ArrayList<SysMenu>();
		List<Subsystem> subsystems=subsystemManager.getAllOrderBySn();
		List<VirtualMenu> virtualMenus=new ArrayList<VirtualMenu>();
		for (Subsystem subsystem : subsystems) {			
        	VirtualMenu menu=new VirtualMenu();
			menu.setId(subsystem.getSysId());
			menu.setName(subsystem.getName());
			menu.setParent("0");
			//menu.setMenuIcon(subsystem.getLogo()+".png");
			menu.setIconCls(subsystem.getIconCls());
			menu.setMenuType("system");
			virtualMenus.add(menu);
			//查询这个sys下的menu
			menus = sysMenuManager.getByParentId(subsystem.getSysId());
			addSubMenu(virtualMenus,menus);
		}
		return virtualMenus;
	}
	
	/**
	 * 迭代增加子菜单
	 * @param virtualMenus
	 * @param menus
	 */
	private void addSubMenu(List<VirtualMenu> virtualMenus,List<SysMenu> menus){
		for(SysMenu menu:menus){
			VirtualMenu Vmenu=new VirtualMenu();
			Vmenu.setId(menu.getMenuId());
			Vmenu.setName(menu.getName());
			Vmenu.setParent(menu.getParentId());
			Vmenu.setIconCls(menu.getIconCls());
			Vmenu.setMenuType("menu");
			virtualMenus.add(Vmenu);
			List<SysMenu> Submenus = sysMenuManager.getByParentId(menu.getMenuId());
			addSubMenu(virtualMenus,Submenus);
		}
	}
	
	
	/**
	 * 进行移动菜单,级联修改路径等信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toMoveMenu")
	@ResponseBody
	public JsonResult toMoveMenu(HttpServletRequest request,HttpServletResponse response){
		String menuId =request.getParameter("menuId");
		String targetId=request.getParameter("targetId");
		String menuType=request.getParameter("menuType");
		String ckSubMenus=request.getParameter("ckSubMenus");

		//获得迁移的菜单
		SysMenu menu=sysMenuManager.get(menuId);
		//移动至系统下
		if("system".equals(menuType)){
			Subsystem subsystem=subsystemManager.get(targetId);
			//仅移动当前菜单下的菜单至子系统下
			if("true".equals(ckSubMenus)){
				List<SysMenu> subMenus=sysMenuManager.getByParentId(menuId);
				for(SysMenu m:subMenus){
					m.setSubsystem(subsystem);
					m.setParentId(subsystem.getSysId());
					m.setDepth(1);
					m.setPath(subsystem.getSysId()+"."+m.getMenuId()+".");
					sysMenuManager.update(m);
					//递归其下所有子菜单，并更新其路径
					cascadeUpdateSubMenu(m);
				}
			}else{//把该菜单移到子系统下
				menu.setSubsystem(subsystem);
				menu.setParentId(subsystem.getSysId());
				menu.setDepth(1);
				menu.setPath(subsystem.getSysId()+"."+menu.getMenuId()+".");
				sysMenuManager.update(menu);
				//递归其下所有子菜单，并更新其路径
				cascadeUpdateSubMenu(menu);
			}
		}else{//迁移到目标菜单下
			SysMenu parentMenu=sysMenuManager.get(targetId);
			//仅把其下的菜单进行迁移
			if("true".equals(ckSubMenus)){
				List<SysMenu> subMenus=sysMenuManager.getByParentId(menuId);
				for(SysMenu m:subMenus){
					m.setParentId(parentMenu.getMenuId());
					int pDepth=parentMenu.getDepth()==null?1:parentMenu.getDepth();
					m.setDepth(pDepth+1);
					m.setPath(parentMenu.getPath()+m.getMenuId()+".");
					sysMenuManager.update(m);
					//递归其下所有子菜单，并更新其路径
					cascadeUpdateSubMenu(m);
				}
				//更新当前迁移菜单的源与目标菜单的子菜单数
				int childs=parentMenu.getChilds()==null?0:parentMenu.getChilds();
				parentMenu.setChilds(childs+subMenus.size());
				menu.setChilds(0);
				sysMenuManager.update(parentMenu);
				sysMenuManager.update(menu);
			}else{//把当前菜单下其下的菜单一起迁移
				menu.setParentId(parentMenu.getMenuId());
				int pDepth=parentMenu.getDepth()==null?1:parentMenu.getDepth();
				menu.setDepth(pDepth+1);
				menu.setPath(parentMenu.getPath()+menu.getMenuId()+".");
				menu.setSubsystem(parentMenu.getSubsystem());
				sysMenuManager.update(menu);
				//递归其下所有子菜单，并更新其路径
				cascadeUpdateSubMenu(menu);
			}
		}
		
		return new JsonResult(true,"修改成功");
	}
	
	private void cascadeUpdateSubMenu(SysMenu parentMenu){
		List<SysMenu> subMenus=sysMenuManager.getByParentId(parentMenu.getMenuId());
		for(SysMenu m:subMenus){
			m.setParentId(parentMenu.getMenuId());
			int pDepth=parentMenu.getDepth()==null?1:parentMenu.getDepth();
			m.setDepth(pDepth+1);
			m.setPath(parentMenu.getPath()+m.getMenuId()+".");
			m.setSubsystem(parentMenu.getSubsystem());
			sysMenuManager.update(m);
			//递归其下所有子菜单，并更新其路径
			cascadeUpdateSubMenu(m);
		}
		parentMenu.setChilds(subMenus.size());
		sysMenuManager.update(parentMenu);
	}
	
	/**
	 * 查找上一级 ,并将自己的path修改成上级路径+menuId+"."
	 * 并级联修改depth
	 * @param sysMenu
	 */
	public void cascadeSaveMenu(SysMenu sysMenu,int distanse,String sysId){
		sysMenuManager.detach(sysMenu.getSubsystem());
		sysMenu.setSysId(sysId);
		sysMenu.setPath(sysMenuManager.get(sysMenu.getParentId()).getPath()+sysMenu.getMenuId()+".");
		sysMenu.setDepth(sysMenu.getDepth()-distanse);
		sysMenuManager.saveOrUpdate(sysMenu);
	}

	@RequestMapping("getByInstType")
	@ResponseBody
	public List<SysMenu>  getByInstType(HttpServletRequest request,HttpServletResponse response){
		String instType=RequestUtil.getString(request, "instType");
		List<SysMenu>  list= sysMenuManager.getByInstType(instType);
		return list;
	}

	@RequestMapping("getAllMenu")
	@ResponseBody
	public List<SysMenu> getAllMenu(HttpServletRequest request,HttpServletResponse response){
		return null;
	}

}
