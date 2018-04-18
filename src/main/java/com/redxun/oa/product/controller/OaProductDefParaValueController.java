package com.redxun.oa.product.controller;

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

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.oa.product.entity.OaProductDefParaValue;
import com.redxun.oa.product.manager.OaProductDefParaValueManager;

/**
 * [OaProductDefParaValue]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/product/oaProductDefParaValue/")
public class OaProductDefParaValueController extends BaseListController{
    @Resource
    OaProductDefParaValueManager oaProductDefParaValueManager;
   
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
                oaProductDefParaValueManager.delete(id);
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
        OaProductDefParaValue oaProductDefParaValue=null;
        if(StringUtils.isNotEmpty(pkId)){
           oaProductDefParaValue=oaProductDefParaValueManager.get(pkId);
        }else{
        	oaProductDefParaValue=new OaProductDefParaValue();
        }
        return getPathView(request).addObject("oaProductDefParaValue",oaProductDefParaValue);
    }
    
    /**
     * 查找某个KEY表下的字段列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("getByKeyId")
    @ResponseBody
    public List<OaProductDefParaValue> getByKeyId(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	List<OaProductDefParaValue> oaValues=new ArrayList<OaProductDefParaValue>();
    	String keyId=request.getParameter("keyId");
    	if(StringUtils.isEmpty(keyId)){
    		return oaValues;
    	}
    	return oaProductDefParaValueManager.getByKeyId(keyId);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	OaProductDefParaValue oaProductDefParaValue=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		oaProductDefParaValue=oaProductDefParaValueManager.get(pkId);
    		if("true".equals(forCopy)){
    			oaProductDefParaValue.setValueId(null);
    		}
    	}else{
    		oaProductDefParaValue=new OaProductDefParaValue();
    	}
    	return getPathView(request).addObject("oaProductDefParaValue",oaProductDefParaValue);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return oaProductDefParaValueManager;
	}

}
