
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.entity.InsPortalPermission;
import com.redxun.oa.info.manager.InsPortalPermissionManager;
import com.redxun.org.api.context.ProfileUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 布局权限设置控制器
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insPortalPermission/")
public class InsPortalPermissionController extends BaseMybatisListController{
    @Resource
    InsPortalPermissionManager insPortalPermissionManager;
    @Resource
    OsUserManager osUserManager;
    @Resource
    OsGroupManager osGroupManager;
   
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
                insPortalPermissionManager.delete(id);
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
        InsPortalPermission insPortalPermission=null;
        if(StringUtils.isNotEmpty(pkId)){
           insPortalPermission=insPortalPermissionManager.get(pkId);
        }else{
        	insPortalPermission=new InsPortalPermission();
        }
        return getPathView(request).addObject("insPortalPermission",insPortalPermission);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String layoutId = RequestUtil.getString(request, "layoutId");
    	JSONObject typeJson=ProfileUtil.getProfileTypeJson();
    	List<InsPortalPermission> lists = insPortalPermissionManager.getByLayoutId(layoutId);
    	for(InsPortalPermission list:lists){
    		String type = list.getType();
    		if(InsPortalPermission.TYPE_USER.equals(type)){
    			list.setOwnerName(osUserManager.get(list.getOwnerId()).getFullname());
    		}else if(InsPortalPermission.TYPE_GROUP.equals(type)||InsPortalPermission.TYPE_SUBGROUP.equals(type)){
    			list.setOwnerName(osGroupManager.get(list.getOwnerId()).getName());
    		}
    	}
    	net.sf.json.JSONArray arr = net.sf.json.JSONArray.fromObject(lists);
    	return getPathView(request).addObject("arr",arr).addObject("layoutId", layoutId).addObject("typeJson", typeJson);
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
        InsPortalPermission insPortalPermission = insPortalPermissionManager.getInsPortalPermission(uId);
        String json = JSONObject.toJSONString(insPortalPermission);
        return json;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return insPortalPermissionManager;
	}

}
