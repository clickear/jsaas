
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
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.oa.info.entity.InsNewsCtl;
import com.redxun.oa.info.manager.InsNewsCtlManager;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 新闻公告权限表控制器
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insNewsCtl/")
public class InsNewsCtlController extends BaseMybatisListController{
    @Resource
    InsNewsCtlManager insNewsCtlManager;
    @Resource
    OsGroupManager osGroupManager;
    @Resource
    OsUserManager osUserManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "oa", submodule = "新闻公告权限表")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                insNewsCtlManager.delete(id);
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
        InsNewsCtl insNewsCtl=null;
        if(StringUtils.isNotEmpty(pkId)){
           insNewsCtl=insNewsCtlManager.get(pkId);
        }else{
        	insNewsCtl=new InsNewsCtl();
        }
        return getPathView(request).addObject("insNewsCtl",insNewsCtl);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String newsId = RequestUtil.getString(request, "newsId");
    	InsNewsCtl checkCtl = insNewsCtlManager.getByRightNewsId(InsNewsCtl.CTL_RIGHT_CHECK,newsId);
    	InsNewsCtl downCtl = insNewsCtlManager.getByRightNewsId(InsNewsCtl.CTL_RIGHT_DOWN,newsId);
    	String checkUserIds = "";
    	String checkGroupIds = "";
    	StringBuffer checkGroupNames = new StringBuffer();
    	StringBuffer checkUserNames = new StringBuffer();
    	if(checkCtl != null){
	    	checkUserIds = checkCtl.getUserId();
	    	checkGroupIds = checkCtl.getGroupId();
	    	if(StringUtil.isNotEmpty(checkGroupIds)){
	    		String[] arr = checkGroupIds.split(",");
	    		for(int i=0;i<arr.length;i++){
	    			String groupName = osGroupManager.get(arr[i]).getName();
	    			checkGroupNames.append(groupName).append(",");
	    		}
	    		checkGroupNames.deleteCharAt(checkGroupNames.length()-1);
	    	}
	    	if(StringUtil.isNotEmpty(checkUserIds)){
	    		String[] arr = checkUserIds.split(",");
	    		for(int i=0;i<arr.length;i++){
	    			String userName = osUserManager.get(arr[i]).getFullname();
	    			checkUserNames.append(userName).append(",");
	    		}
	    		checkUserNames.deleteCharAt(checkUserNames.length()-1);
	    	}
    	}
    	
    	String downUserIds = "";
    	String downGroupIds = "";
    	StringBuffer downGroupNames = new StringBuffer();
    	StringBuffer downUserNames = new StringBuffer();
    	if(downCtl != null){
	    	downUserIds = downCtl.getUserId();
	    	downGroupIds = downCtl.getGroupId();
	    	if(StringUtil.isNotEmpty(downGroupIds)){
	    		String[] arr = downGroupIds.split(",");
	    		for(int i=0;i<arr.length;i++){
	    			String groupName = osGroupManager.get(arr[i]).getName();
	    			downGroupNames.append(groupName).append(",");
	    		}
	    		downGroupNames.deleteCharAt(downGroupNames.length()-1);
	    	}
	    	if(StringUtil.isNotEmpty(downUserIds)){
	    		String[] arr = downUserIds.split(",");
	    		for(int i=0;i<arr.length;i++){
	    			String userName = osUserManager.get(arr[i]).getFullname();
	    			downUserNames.append(userName).append(",");
	    		}
	    		downUserNames.deleteCharAt(downUserNames.length()-1);
	    	}
    	}
    	
    	return getPathView(request).addObject("checkUserIds",checkUserIds).addObject("checkUserNames", checkUserNames)
    			.addObject("checkGroupIds",checkGroupIds).addObject("checkGroupNames", checkGroupNames)
    			.addObject("downUserIds",downUserIds).addObject("downUserNames", downUserNames)
    			.addObject("downGroupIds",downGroupIds).addObject("downGroupNames", downGroupNames)
    			.addObject("newsId", newsId);
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
        InsNewsCtl insNewsCtl = insNewsCtlManager.getInsNewsCtl(uId);
        String json = JSONObject.toJSONString(insNewsCtl);
        return json;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return insNewsCtlManager;
	}

}
