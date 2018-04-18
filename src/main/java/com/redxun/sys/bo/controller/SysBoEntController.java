
package com.redxun.sys.bo.controller;

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
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.entity.SysBoDef;
import com.redxun.sys.bo.entity.SysBoEnt;
import com.redxun.sys.bo.entity.SysBoRelation;
import com.redxun.sys.bo.manager.SysBoDefManager;
import com.redxun.sys.bo.manager.SysBoEntManager;

/**
 * 表单实体对象控制器
 * @author ray
 */
@Controller
@RequestMapping("/sys/bo/sysBoEnt/")
public class SysBoEntController extends BaseMybatisListController{
    @Resource
    SysBoEntManager sysBoEntManager;
    @Resource
    SysBoDefManager sysBoDefManager;
    
   
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
                sysBoEntManager.delete(id);
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
        SysBoEnt sysBoEnt=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysBoEnt=sysBoEntManager.get(pkId);
        }else{
        	sysBoEnt=new SysBoEnt();
        }
        return getPathView(request).addObject("sysBoEnt",sysBoEnt);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysBoEnt sysBoEnt=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysBoEnt=sysBoEntManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysBoEnt.setId(null);
    		}
    	}else{
    		sysBoEnt=new SysBoEnt();
    	}
    	return getPathView(request).addObject("sysBoEnt",sysBoEnt);
    }
    
    @RequestMapping("getTreeByBoDefId")
    @ResponseBody
    public JSONArray getTreeByBoDefId(HttpServletRequest request ){
    	String bodefId=RequestUtil.getString(request, "boDefId");
    	int needSub=RequestUtil.getInt(request, "needSub", 1);
    	
    	
    	JSONArray jsonAry=new JSONArray();
    	if(StringUtils.isEmpty(bodefId)){
    		return jsonAry;
    	}
    	if(bodefId.indexOf(",")!=-1){
    		bodefId=bodefId.substring(0, bodefId.indexOf(","));
    	}
    	SysBoEnt ent= sysBoEntManager.getByBoDefId(bodefId);
    	for(SysBoAttr attr:ent.getSysBoAttrs()){
    		JSONObject jsonField=new JSONObject();
    		jsonField.put("id", attr.getName());
    		jsonField.put("text", attr.getComment());
    		jsonField.put("pid", "");
    		jsonField.put("type", "field");
    		jsonAry.add(jsonField);
    	}
    	if(needSub==0) return jsonAry;
    	
    	for(SysBoEnt subEnt : ent.getBoEntList()){
    		JSONObject tb=new JSONObject();
    		tb.put("id", subEnt.getName());
    		tb.put("text", subEnt.getComment());
    		tb.put("pid", "");
    		tb.put("type", "table");
    		jsonAry.add(tb);
    		
    		for(SysBoAttr attr:subEnt.getSysBoAttrs()){
        		JSONObject jsonField=new JSONObject();
        		jsonField.put("id", attr.getName());
        		jsonField.put("text", attr.getComment());
        		jsonField.put("pid", subEnt.getName());
        		jsonField.put("type", "field");
        		jsonAry.add(jsonField);
        	}
    	}
    	return jsonAry;
    	
    }
    
    @RequestMapping("getBoEntByBoDefId")
    @ResponseBody
    public SysBoEnt getBoEntByBoDefId(HttpServletRequest request ){
    	String bodefId=RequestUtil.getString(request, "boDefId");
    	SysBoEnt sysBoEnt= sysBoEntManager.getByBoDefId(bodefId);
    	return sysBoEnt;
    }
    
    @RequestMapping("getFieldByBoDefId")
    @ResponseBody
    public List<SysBoAttr> getFieldBy(HttpServletRequest  request,HttpServletResponse response){
    	String boDefId=RequestUtil.getString(request, "boDefId");
    	String entName=RequestUtil.getString(request, "tableName");
    	SysBoRelation sysBoRelation=sysBoDefManager.getEntByEntNameAndDefId(boDefId, entName);
    	String entId=sysBoRelation.getBoEntid();
    	List<SysBoAttr> sysBoAttrs=sysBoEntManager.getByEntId(entId);
    	return sysBoAttrs;
    }
    
    @RequestMapping("getCommonField")
    @ResponseBody
    public JSONArray getCommonField(HttpServletRequest request ){
    	
    	JSONArray jsonAry=new JSONArray();
    	
    	addCommonField(jsonAry,"");
    	
    	return jsonAry;
    	
    }
    
    private void addCommonField(JSONArray ary,String parentId){
    	JSONObject pk=new JSONObject();
		pk.put("id", SysBoEnt.SQL_PK);
		pk.put("text", "主键");
		pk.put("pid", parentId);
		pk.put("type", "common");
		
		JSONObject fk=new JSONObject();
		fk.put("id", SysBoEnt.SQL_FK);
		fk.put("text", "外键");
		fk.put("pid", parentId);
		fk.put("type", "common");
		
		JSONObject uid=new JSONObject();
		uid.put("id", SysBoEnt.FIELD_USER);
		uid.put("text", "用户ID");
		uid.put("pid", parentId);
		uid.put("type", "common");
		
		JSONObject gid=new JSONObject();
		gid.put("id", SysBoEnt.FIELD_GROUP);
		gid.put("text", "组ID");
		gid.put("pid", parentId);
		gid.put("type", "common");
		
		JSONObject ctime=new JSONObject();
		ctime.put("id", SysBoEnt.FIELD_CREATE_TIME);
		ctime.put("text", "创建时间");
		ctime.put("pid", parentId);
		ctime.put("type", "common");
		
		JSONObject utime=new JSONObject();
		utime.put("id", SysBoEnt.FIELD_UPDTIME_TIME);
		utime.put("text", "更新时间");
		utime.put("pid", parentId);
		utime.put("type", "common");
		
		ary.add(pk);
		ary.add(fk);
		ary.add(uid);
		ary.add(gid);
		ary.add(ctime);
		ary.add(utime);
		
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return sysBoEntManager;
	}

}
