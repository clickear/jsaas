
package com.redxun.sys.org.controller;

import java.util.Date;

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
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsDimensionRight;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsDimensionManager;
import com.redxun.sys.org.manager.OsDimensionRightManager;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 维度授权控制器
 * @author ray
 */
@Controller
@RequestMapping("/sys/org/osDimensionRight/")
public class OsDimensionRightController extends BaseMybatisListController{
    @Resource
    OsDimensionRightManager osDimensionRightManager;
    @Resource
    OsUserManager osUserManager;
    @Resource
    OsGroupManager osGroupManager;
    
    @Resource
    OsDimensionManager osDimensionManager;
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "组织架构", submodule = "组织维度授权")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                osDimensionRightManager.delete(id);
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
        OsDimensionRight osDimensionRight=null;
        if(StringUtils.isNotEmpty(pkId)){
           osDimensionRight=osDimensionRightManager.get(pkId);
        }else{
        	osDimensionRight=new OsDimensionRight();
        }
        return getPathView(request).addObject("osDimensionRight",osDimensionRight);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String dimId=RequestUtil.getString(request, "dimId");
    	OsDimensionRight osDimensionRight=null;
    	osDimensionRight=osDimensionRightManager.getDimRightByDimId(dimId);
    	if(osDimensionRight==null){
    		osDimensionRight=new OsDimensionRight();
    		osDimensionRight.setCreateBy(ContextUtil.getCurrentUserId());
    		osDimensionRight.setCreateTime(new Date());
    		osDimensionRight.setTenantId(ContextUtil.getCurrentTenantId());
    		osDimensionRight.setRightId(idGenerator.getSID());
    		osDimensionRight.setDimId(dimId);
    		osDimensionRightManager.create(osDimensionRight);
    	}
    	OsDimension osDimension=osDimensionManager.get(dimId);
    	return getPathView(request).addObject("osDimensionRight",osDimensionRight).addObject("osDimension", osDimension);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return osDimensionRightManager;
	}
	
	@RequestMapping("getNames")
	@ResponseBody
	public JSONObject getNames(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject=new JSONObject();
		String userIds=RequestUtil.getString(request, "userIds");
		String groupIds=RequestUtil.getString(request, "groupIds");
		StringBuffer userBuffer=new StringBuffer("");
		StringBuffer groupBuffer=new StringBuffer("");
		String userNames;
		String groupNames;
		String[] userIdArray=userIds.split(",");
		for (int i = 0; i < userIdArray.length; i++) {
			OsUser osUser=osUserManager.get(userIdArray[i]);
			if(osUser!=null){
				userBuffer.append(osUser.getFullname());
				userBuffer.append(",");	
			}
		}
		
		String[] groupIdArray=groupIds.split(",");
		for (int i = 0; i < groupIdArray.length; i++) {
			OsGroup osGroup=osGroupManager.get(groupIdArray[i]);
			if(osGroup!=null){
				groupBuffer.append(osGroup.getName());
				groupBuffer.append(",");
			}
		}
		
		userNames= userBuffer.toString();
		groupNames=groupBuffer.toString();
		if(userNames.length()>=1){
			userNames=userNames.substring(0, userNames.length()-1);
		}
		if(groupNames.length()>=1){
			groupNames=groupNames.substring(0, groupNames.length()-1);
		}
		jsonObject.put("userNames", userNames);
		jsonObject.put("groupNames", groupNames);
		return jsonObject;
	}

}
