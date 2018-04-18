package com.redxun.sys.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysModule;
import com.redxun.sys.core.manager.SysModuleManager;

/**
 * [SysModule]管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysModule/")
public class SysModuleController extends BaseListController{
    @Resource
    SysModuleManager sysModuleManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
            	SysModule module=sysModuleManager.get(id);
            	if(module==null || MBoolean.YES.name().equals(module)){
            		continue;
            	}
                sysModuleManager.removeEntityCascade(id);
            }
        }
        return new JsonResult(true,"成功删除！");
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
        SysModule sysModule=null;
        if(StringUtils.isNotBlank(pkId)){
           sysModule=sysModuleManager.get(pkId);
        }else{
        	sysModule=new SysModule();
        }
        return getPathView(request).addObject("sysModule",sysModule);
    }
    
    /**
     * 获得单条记录
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("getRecord")
    @ResponseBody
    public SysModule getRecord(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        SysModule sysModule=null;
        if(StringUtils.isNotBlank(pkId)){
           sysModule=sysModuleManager.get(pkId);
        }
        return sysModule;
    }
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	SysModule sysModule=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysModule=sysModuleManager.get(pkId);
    	}else{
    		sysModule=new SysModule();
    	}
    	return getPathView(request).addObject("sysModule",sysModule);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return sysModuleManager;
	}
	
	/**
	 * 注册业务模块
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("regModule")
	@ResponseBody
	public JsonResult regModule(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String entityName=request.getParameter("entityName");
		try{
			Class entityClass=Class.forName(entityName);
			sysModuleManager.createOrUpdateFromEntityClass(entityClass);
		}catch(Exception ex){
			ex.printStackTrace();
			return new JsonResult(false,ex.getCause().toString());
		}
		return new JsonResult(true,"成功注册!");
	}
	
	@RequestMapping("getAllModule")
	@ResponseBody
	public List<SysModule> getAllModule(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<SysModule> sysModules=sysModuleManager.getAll();
		return sysModules;
	}

}
