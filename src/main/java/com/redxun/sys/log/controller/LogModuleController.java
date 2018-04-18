
package com.redxun.sys.log.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.entity.KeyValEnt;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.log.entity.LogModule;
import com.redxun.sys.log.manager.LogModuleManager;

/**
 * 日志模块控制器
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/sys/log/logModule/")
public class LogModuleController extends BaseMybatisListController{
    @Resource
    LogModuleManager logModuleManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                logModuleManager.delete(id);
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
        String pkId=RequestUtil.getString(request, "pkId");
        LogModule logModule=null;
        if(StringUtils.isNotEmpty(pkId)){
           logModule=logModuleManager.get(pkId);
        }else{
        	logModule=new LogModule();
        }
        return getPathView(request).addObject("logModule",logModule);
    }
    
	@RequestMapping("getModuleList")
	@ResponseBody
	public List<KeyValEnt> getModuleList(HttpServletRequest request,HttpServletResponse response){
		String tenantId=ContextUtil.getCurrentTenantId();
		List<KeyValEnt> keyValEnts=new ArrayList<KeyValEnt>();
		Map<String, Object> params=new HashMap<String, Object>();
		List<String> logModules=logModuleManager.getModuleList();
		for (String string : logModules) {
			KeyValEnt keyValEnt=new KeyValEnt(string, string);
			keyValEnts.add(keyValEnt);
		}
		return keyValEnts;
	}
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	LogModule logModule=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		logModule=logModuleManager.get(pkId);
    		if("true".equals(forCopy)){
    			logModule.setId(null);
    		}
    	}else{
    		logModule=new LogModule();
    	}
    	return getPathView(request).addObject("logModule",logModule);
    }
    
    @RequestMapping("disableModule")
    @ResponseBody
    public JSONObject disableModule(HttpServletRequest request,HttpServletResponse response){
    	String id=RequestUtil.getString(request, "id");
    	LogModule logModule=logModuleManager.get(id);
    	logModule.setEnable("FALSE");
    	logModuleManager.update(logModule);
    	JSONObject jsonObject=new JSONObject();
    	jsonObject.put("success", true);
    	return jsonObject;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return logModuleManager;
	}

}
