package com.redxun.sys.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.bpmclient.model.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.ITenant;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.entity.SysSeqId;
import com.redxun.sys.core.manager.SysSeqIdManager;

/**
 * [SysSeqId]管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysSeqId/")
public class SysSeqIdController extends BaseListController{
    @Resource
    SysSeqIdManager sysSeqIdManager;
   
    

   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                sysSeqIdManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
    	String tenantId=ITenant.ADMIN_TENANT_ID;
    	return this.getPathView(request).addObject("tenantId", tenantId);
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
        SysSeqId sysSeqId=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysSeqId=sysSeqIdManager.get(pkId);
        }else{
        	sysSeqId=new SysSeqId();
        	//默认为长度为
        	sysSeqId.setLen(12);
        	sysSeqId.setInitVal(1);
        	sysSeqId.setGenType(SysSeqId.GEN_TYPE_AUTO);
        }
        return getPathView(request).addObject("sysSeqId",sysSeqId);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysSeqId sysSeqId=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysSeqId=sysSeqIdManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysSeqId.setSeqId(null);
    		}
    	}else{
    		sysSeqId=new SysSeqId();
    	}
    	return getPathView(request).addObject("sysSeqId",sysSeqId);
    }
    
    @RequestMapping("getInstAllSeq")
    @ResponseBody
    public JsonPageResult<SysSeqId> getInstAllSeq(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	QueryFilter queryFilter =QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("tenantId", ITenant.ADMIN_TENANT_ID);
    	List<SysSeqId> sIds=new ArrayList<SysSeqId>();
    	String key=request.getParameter("key");
    	if(StringUtils.isEmpty(key))
    		sIds=sysSeqIdManager.getAll(queryFilter);
    	else{
    		queryFilter.addLeftLikeFieldParam("alias",key);
    		sIds=sysSeqIdManager.getAll(queryFilter);
    	}
    	return new JsonPageResult<SysSeqId>(true, sIds, queryFilter.getPage().getTotalItems(), "成功查询");
    }
    
    @RequestMapping("getNameById")
    @ResponseBody
    public String getNameById(HttpServletRequest request,HttpServletResponse response){
    	String id=RequestUtil.getString(request, "id");
    	SysSeqId sysSeqId=sysSeqIdManager.get(id);
    	String name=sysSeqId.getName();
    	return name;
    }
    

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return sysSeqIdManager;
	}

}
