package com.redxun.kms.core.controller;

import java.text.SimpleDateFormat;
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

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.kms.core.entity.KdDocHis;
import com.redxun.kms.core.entity.KdDocRead;
import com.redxun.kms.core.entity.KdReader;
import com.redxun.kms.core.manager.KdDocHisManager;

/**
 * [KdDocHis]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocHis/")
public class KdDocHisController extends BaseListController{
    @Resource
    KdDocHisManager kdDocHisManager;
   
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
                kdDocHisManager.delete(id);
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
        KdDocHis kdDocHis=null;
        if(StringUtils.isNotEmpty(pkId)){
           kdDocHis=kdDocHisManager.get(pkId);
        }else{
        	kdDocHis=new KdDocHis();
        }
        return getPathView(request).addObject("kdDocHis",kdDocHis);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	KdDocHis kdDocHis=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		kdDocHis=kdDocHisManager.get(pkId);
    		if("true".equals(forCopy)){
    			kdDocHis.setHisId(null);
    		}
    	}else{
    		kdDocHis=new KdDocHis();
    	}
    	return getPathView(request).addObject("kdDocHis",kdDocHis);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return kdDocHisManager;
	}

	/**
	 * 获得当前文档的历史版本
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getVersion")
	@ResponseBody
	public JsonPageResult<KdDocHis> getVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<KdDocHis> his = kdDocHisManager.getDocVersion(docId,queryFilter);		
		JsonPageResult<KdDocHis> result = new JsonPageResult<KdDocHis>(his, queryFilter.getPage().getTotalItems());
		return result;
	}
}
