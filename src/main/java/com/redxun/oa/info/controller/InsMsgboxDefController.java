
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
import com.redxun.oa.info.entity.InsMsgboxDef;
import com.redxun.oa.info.manager.InsMsgboxDefManager;

/**
 * 栏目消息盒子表控制器
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insMsgboxDef/")
public class InsMsgboxDefController extends BaseMybatisListController{
    @Resource
    InsMsgboxDefManager insMsgboxDefManager;
   
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
                insMsgboxDefManager.delete(id);
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
        InsMsgboxDef insMsgboxDef=null;
        if(StringUtils.isNotEmpty(pkId)){
           insMsgboxDef=insMsgboxDefManager.get(pkId);
        }else{
        	insMsgboxDef=new InsMsgboxDef();
        }
        return getPathView(request).addObject("insMsgboxDef",insMsgboxDef);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	InsMsgboxDef insMsgboxDef=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		insMsgboxDef=insMsgboxDefManager.get(pkId);
    		if("true".equals(forCopy)){
    			insMsgboxDef.setBoxId(null);
    		}
    	}else{
    		insMsgboxDef=new InsMsgboxDef();
    	}
    	return getPathView(request).addObject("insMsgboxDef",insMsgboxDef);
    }
    
    @RequestMapping("editMsg")
    public ModelAndView editMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String boxId=RequestUtil.getString(request, "boxId");
    	InsMsgboxDef insMsgboxDef=null;
    	if(StringUtils.isNotEmpty(boxId)){
    		insMsgboxDef=insMsgboxDefManager.get(boxId);
    	}else{
    		insMsgboxDef=new InsMsgboxDef();
    	}
    	return getPathView(request).addObject("insMsgboxDef",insMsgboxDef).addObject("boxId", boxId);
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
        InsMsgboxDef insMsgboxDef = insMsgboxDefManager.getInsMsgboxDef(uId);
        String json = JSONObject.toJSONString(insMsgboxDef);
        return json;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return insMsgboxDefManager;
	}

}
