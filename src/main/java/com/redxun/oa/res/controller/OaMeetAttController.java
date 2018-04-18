package com.redxun.oa.res.controller;

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
import com.redxun.oa.res.entity.OaMeetAtt;
import com.redxun.oa.res.manager.OaMeetAttManager;
import com.redxun.oa.res.manager.OaMeetingManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 会议人员管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/res/oaMeetAtt/")
public class OaMeetAttController extends BaseListController{
    @Resource
    OaMeetAttManager oaMeetAttManager;
	@Resource
	OsUserManager osUserManager;
	@Resource
	OaMeetingManager oaMeetingManager;

   
	/**
	 * 查找我的会议
	 * 
	 * @return 个人的会议list
	 * @throws Exception
	 */
	@RequestMapping("listOameet")
	@ResponseBody
	public JsonPageResult<OaMeetAtt> listOameet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		String tenantId=ContextUtil.getCurrentTenantId();
		QueryFilter filter = QueryFilterBuilder.createQueryFilter(request);
		filter.addFieldParam("userId", userId);
		filter.addFieldParam("tenantId", tenantId);
		filter.addSortParam("createTime", "DESC");
		List<OaMeetAtt> list = oaMeetAttManager.getAll(filter);
		return new JsonPageResult<OaMeetAtt>(list, filter.getPage()
				.getTotalItems());
	}
	

	
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                oaMeetAttManager.delete(id);
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
        OaMeetAtt oaMeetAtt=null;
        if(StringUtils.isNotEmpty(pkId)){
           oaMeetAtt=oaMeetAttManager.get(pkId);
        }else{
        	oaMeetAtt=new OaMeetAtt();
        }
        return getPathView(request).addObject("oaMeetAtt",oaMeetAtt);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	OaMeetAtt oaMeetAtt=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		oaMeetAtt=oaMeetAttManager.get(pkId);
    		if("true".equals(forCopy)){
    			oaMeetAtt.setAttId(null);
    		}
    	}else{
    		oaMeetAtt=new OaMeetAtt();
    	}
    	return getPathView(request).addObject("oaMeetAtt",oaMeetAtt);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return oaMeetAttManager;
	}

}
