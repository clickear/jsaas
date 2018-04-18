
package com.redxun.sys.bo.controller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.bpm.core.entity.config.ProcessConfig;
import com.redxun.bpm.core.manager.BoDataUtil;
import com.redxun.bpm.core.manager.IFormDataHandler;
import com.redxun.bpm.form.entity.BpmFormView;
import com.redxun.bpm.form.entity.BpmMobileForm;
import com.redxun.bpm.form.manager.BpmFormViewManager;
import com.redxun.bpm.form.manager.BpmMobileFormManager;
import com.redxun.core.database.api.ITableOperator;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.ExceptionUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.bo.dao.SysBoAttrQueryDao;
import com.redxun.sys.bo.dao.SysBoRelationQueryDao;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.entity.SysBoDef;
import com.redxun.sys.bo.entity.SysBoEnt;
import com.redxun.sys.bo.entity.SysBoRelation;
import com.redxun.sys.bo.manager.SysBoDefManager;
import com.redxun.sys.bo.manager.SysBoEntManager;
import com.redxun.sys.bo.manager.parse.BoAttrParseFactory;

/**
 * BO定义控制器
 * @author ray
 */
@Controller
@RequestMapping("/sys/bo/sysBoDef/")
public class SysBoDefController extends BaseMybatisListController{
    @Resource
    SysBoDefManager sysBoDefManager;
    @Resource
    BpmFormViewManager bpmFormViewManager;
    @Resource
    BpmMobileFormManager bpmMobileFormManager;
    @Resource
    SysBoEntManager sysBoEntManager;
    @Resource
    BoAttrParseFactory boAttrParseFactory;
    @Resource
    SysBoAttrQueryDao sysBoAttrQueryDao;
    @Resource
    SysBoRelationQueryDao sysBoRelationQueryDao;
    @Resource
	private ITableOperator  tableOperator;
    
    
   

   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                sysBoDefManager.delete(id);
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
        SysBoDef sysBoDef=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysBoDef=sysBoDefManager.get(pkId);
        }else{
        	sysBoDef=new SysBoDef();
        }
        return getPathView(request).addObject("sysBoDef",sysBoDef);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysBoDef sysBoDef=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysBoDef=sysBoDefManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysBoDef.setId(null);
    		}
    	}else{
    		sysBoDef=new SysBoDef();
    	}
    	return getPathView(request).addObject("sysBoDef",sysBoDef);
    }
    
    
    @RequestMapping("getJson")
    @ResponseBody
    public  JSONObject  getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	IFormDataHandler handler=BoDataUtil.getDataHandler(ProcessConfig.DATA_SAVE_MODE_JSON);
		if(handler!=null){
			return handler.getInitData(pkId);
		}
		return null;
    	
    	
    }
    
 
    
    @RequestMapping("getRelForm")
    @ResponseBody
    public  JSONObject  getRelForm(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//pc表单
    	QueryFilter filter=  new QueryFilter();
		filter.addFieldParam("boDefId",pkId);
		List<BpmFormView> formViews = bpmFormViewManager.getByFilter(filter);
		//手机表单
		List<BpmMobileForm> mobileforms = bpmMobileFormManager.getByBoDefId(pkId);
		//pc表单
		JSONArray formAry=new JSONArray();
		for(BpmFormView view:formViews){
			JSONObject obj=new JSONObject();
			obj.put("id", view.getViewId());
			obj.put("name", view.getName());
			obj.put("alias", view.getKey());
			formAry.add(obj);
		}
		//手机表单	
		JSONArray mobileAry=new JSONArray();
		for(BpmMobileForm view:mobileforms){
			JSONObject obj=new JSONObject();
			obj.put("id", view.getId());
			obj.put("name", view.getName());
			obj.put("alias", view.getAlias());
			mobileAry.add(obj);
		}
		
		JSONObject rtnObj=new JSONObject();
		
		rtnObj.put("pc",formAry);
		rtnObj.put("mobile",mobileAry);
    	
		return rtnObj;
    	
    	
    }
    
    @RequestMapping("getBoJson")
    @ResponseBody
    public  JSONObject  getBoJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String boDefId=RequestUtil.getString(request, "boDefId");
    	List<SysBoEnt> list=sysBoEntManager.getListByBoDefId(boDefId,true);
    	SysBoDef boDef=sysBoDefManager.get(boDefId);
    	
    	JSONObject json=new JSONObject();
    	
    	json.put("list", list);
    	json.put("hasGen", boDef.getSupportDb());
    	json.put("boDefId", boDefId);
    	
		return json;
    	
    	
    }
    
    @RequestMapping("manage")
    @ResponseBody
    public  ModelAndView  manage(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String boDefId=RequestUtil.getString(request, "boDefId");
    	com.alibaba.fastjson.JSONObject json= boAttrParseFactory.getPluginDesc();
		ModelAndView mv = getPathView(request);
		mv.addObject("json", json);
		mv.addObject("boDefId", boDefId);
		return mv;
    }
    
    @RequestMapping("removeAttr")
    @ResponseBody
    public  JsonResult<Void>  removeAttr(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String attrId=RequestUtil.getString(request, "attrId");
    	JsonResult<Void> rtn=new JsonResult<Void>(true, "删除列成功!");
    	try{
    		sysBoEntManager.removeAttr(attrId);
    	}
    	catch (Exception e) {
    		rtn=new JsonResult<Void>(false, "删除列失败!");
		}
		
		return rtn;
    }
    
    @RequestMapping("removeEnt")
    @ResponseBody
    public JsonResult<Void> removeEnt(HttpServletRequest request,HttpServletResponse response) {
    	String boDefId=RequestUtil.getString(request, "boDefId");
    	String entId=RequestUtil.getString(request, "entId");
    	JsonResult<Void> json=new JsonResult<Void>(true,"删除BO实体成功!");
    	SysBoDef boDef=sysBoDefManager.get(boDefId);
    	SysBoEnt boEnt=sysBoEntManager.get(entId);
    	boolean genTable=SysBoDef.BO_YES.equals(boDef.getSupportDb());
    	SysBoRelation boRelation=sysBoRelationQueryDao.getByDefEntId(boDefId, entId);
    	try{
    		//删除表
        	if(genTable && boRelation.getIsRef()!=1){
        		//删除物理表
        		tableOperator.dropTable(boEnt.getTableName());
        	}
        	
        	//删除关系和实体定义。
        	if(boRelation.getIsRef()!=1){
        		sysBoEntManager.delete(entId);
        		sysBoAttrQueryDao.delByMainId(entId);
        	}
        	sysBoRelationQueryDao.delete(boRelation.getId());
    	}
    	catch (Exception e) {
			json=new JsonResult<Void>(false, ExceptionUtil.getExceptionMessage(e));
		}
    	
    	
    	
		return json;
    	
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return sysBoDefManager;
	}

}
