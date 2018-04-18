package com.redxun.sys.core.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jfree.base.modules.SubSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.Subsystem;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SubsystemManager;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.manager.OsGroupSysManager;
import com.redxun.sys.org.manager.OsRelInstManager;

/**
 * [Subsystem]管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/subsystem/")
public class SubsystemController extends BaseListController{
    @Resource
    SubsystemManager subsystemManager;
    @Resource
    OsGroupSysManager osGroupSysManager;
    @Resource
    OsRelInstManager osRelInstManager;
    @Resource
    SysInstManager  sysInstManager;
    
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "系统内核", submodule = "子系统")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                subsystemManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除!");
    }
    
    /**
     * 查看明细
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        Subsystem subsystem=null;
        if(StringUtils.isNotBlank(pkId)){
           subsystem=subsystemManager.get(pkId);
        }
        String domain=WebAppUtil.getOrgMgrDomain();
    	String tenantId=ContextUtil.getCurrentTenantId();
    	SysInst sysInst=sysInstManager.get(tenantId);
    	String currentDomain=sysInst.getDomain();
    	String isMainTenant="NO";
    	if(domain.equals(currentDomain)){
    		isMainTenant="YES";
    	}
        return getPathView(request).addObject("subsystem",subsystem).addObject("isMainTenant", isMainTenant);
    }
    
    /**
     * list页面返回用户所在租户是否根租户
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String domain=WebAppUtil.getOrgMgrDomain();
    	String tenantId=ContextUtil.getCurrentTenantId();
    	SysInst sysInst=sysInstManager.get(tenantId);
    	String currentDomain=sysInst.getDomain();
    	String isMainTenant="NO";
    	if(domain.equals(currentDomain)){
    		isMainTenant="YES";
    	}
    	return getPathView(request).addObject("isMainTenant", isMainTenant);
    }
    /**
     * 编辑子系统
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	// 复制添加
    	String forCopy = request.getParameter("forCopy");   	
        Subsystem subsystem=null;
        if(StringUtils.isNotBlank(pkId)){
           subsystem=subsystemManager.get(pkId);
        }
        if ("true".equals(forCopy)) {
        	subsystem.setSysId(null);
		}
        return getPathView(request).addObject("subsystem",subsystem);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return subsystemManager;
	}
	
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
	
	@RequestMapping("jsonAll")
	@ResponseBody
	public List<Subsystem> jsonAll(HttpServletRequest request) throws Exception{
		List<Subsystem> list=subsystemManager.getByValidSystem();
		return list;
	}
	
	/**
	 * 获得授权访问的子系统
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrantSyses")
	@ResponseBody
	public Collection<Subsystem> getGrantSyses(HttpServletRequest request) throws Exception{
		
		IUser curUser=ContextUtil.getCurrentUser();
		String mgrDomain=WebAppUtil.getOrgMgrDomain();
		
		if(mgrDomain.equals(curUser.getTenant().getDomain()) 
				&& curUser.isSuperAdmin()){
			return subsystemManager.getByValidSystem();
		}else{
			//先取得该用户授权下的用户组，再根据用户组获得其授权访问的菜单
			//应用系统进行菜单合并
			Set<Subsystem> grantSyses=new HashSet<Subsystem>();
			List<OsRelInst> groupInsts=osRelInstManager.getGrantGroupsByUserId(curUser.getUserId(), curUser.getTenant().getTenantId());
			for(OsRelInst inst:groupInsts){
				String groupId=inst.getParty1();
				List<Subsystem> groupSyses=subsystemManager.getGrantSubsByGroupId(groupId);
				grantSyses.addAll(groupSyses);
			}
			return grantSyses;
		}
	}
	
	/**
	 * 获取所有子系统以json串传出
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllSubSystem")
	@ResponseBody
	public List<Subsystem> getAllSubSystem(HttpServletRequest request) throws Exception{
		List<Subsystem> subsystems=subsystemManager.getAll();
		return subsystems;
	}
	
	/**
	 * 将需要转移的菜单的sysId传入到jsp中
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("allMenu")
	public ModelAndView allMenu(HttpServletRequest request,HttpServletResponse response){
		String menuId=request.getParameter("menuId");
		return getPathView(request).addObject("menuId",menuId);
	}
	
}
