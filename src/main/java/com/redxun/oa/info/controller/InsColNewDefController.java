
package com.redxun.oa.info.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.oa.info.entity.InsColNewDef;
import com.redxun.oa.info.manager.InsColNewDefManager;

/**
 * ins_col_new_def控制器
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insColNewDef/")
public class InsColNewDefController extends BaseMybatisListController{
    @Resource
    InsColNewDefManager insColNewDefManager;
   
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
                insColNewDefManager.delete(id);
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
        InsColNewDef insColNewDef=null;
        if(StringUtils.isNotEmpty(pkId)){
           insColNewDef=insColNewDefManager.get(pkId);
        }else{
        	insColNewDef=new InsColNewDef();
        }
        return getPathView(request).addObject("insColNewDef",insColNewDef);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	InsColNewDef insColNewDef=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		insColNewDef=insColNewDefManager.get(pkId);
    		if("true".equals(forCopy)){
    			insColNewDef.setId(null);
    		}
    	}else{
    		insColNewDef=new InsColNewDef();
    	}
    	return getPathView(request).addObject("insColNewDef",insColNewDef);
    }
    
    /**
     * 有子表的情况下编辑明细的json
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("getJson")
    @ResponseBody
    public String getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String uId=RequestUtil.getString(request, "ids");    	
        InsColNewDef insColNewDef = insColNewDefManager.getInsColNewDef(uId);
        String json = JSONObject.toJSONString(insColNewDef);
        return json;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return insColNewDefManager;
	}

}
