
package com.redxun.oa.info.controller;

import java.util.List;

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
import com.redxun.oa.info.entity.InsMsgDef;
import com.redxun.oa.info.manager.InsMsgDefManager;

/**
 * INS_MSG_DEF控制器
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insMsgDef/")
public class InsMsgDefController extends BaseMybatisListController{
    @Resource
    InsMsgDefManager insMsgDefManager;
   
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
                insMsgDefManager.delete(id);
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
        InsMsgDef insMsgDef=null;
        if(StringUtils.isNotEmpty(pkId)){
           insMsgDef=insMsgDefManager.get(pkId);
        }else{
        	insMsgDef=new InsMsgDef();
        }
        return getPathView(request).addObject("insMsgDef",insMsgDef);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	InsMsgDef insMsgDef=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		insMsgDef=insMsgDefManager.get(pkId);
    		if("true".equals(forCopy)){
    			insMsgDef.setMsgId(null);
    		}
    	}else{
    		insMsgDef=new InsMsgDef();
    	}
    	
    	return getPathView(request).addObject("insMsgDef",insMsgDef);
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
        InsMsgDef insMsgDef = insMsgDefManager.getInsMsgDef(uId);
        String json = JSONObject.toJSONString(insMsgDef);
        return json;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return insMsgDefManager;
	}

}
