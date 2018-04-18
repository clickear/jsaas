package com.redxun.oa.pro.controller;

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
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.entity.ProAttend;
import com.redxun.oa.pro.entity.ProVers;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.oa.pro.entity.Project;
import com.redxun.oa.pro.manager.ProAttendManager;
import com.redxun.oa.pro.manager.ProWorkAttManager;
import com.redxun.oa.pro.manager.ProWorkMatManager;
import com.redxun.oa.pro.manager.ProjectManager;

/**
 * 人员管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/pro/proAttend/")
public class ProAttendController extends TenantListController{
    @Resource
    ProAttendManager proAttendManager;
    @Resource
    ProjectManager projectManager;
    @Resource
    ProAttendManager attendManager;
    @Resource
    OsUserManager osUserManager;
    @Resource
    OsGroupManager osGroupManager;
    @Resource
    ProWorkMatManager proWorkMatManager;
    @Resource
    InfInnerMsgManager infInnerMsgManager;
    @Resource
    ProWorkAttManager proWorkAttManager;
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter filter=super.getQueryFilter(request);
  		//查找分类下的模型
  		String projectId=request.getParameter("projectId");
  		if(StringUtils.isNotEmpty(projectId)){
  		     Project project=projectManager.get(projectId);
  			if(project!=null){
  				//filter.addLeftLikeFieldParam("sysTree.path", sysTree.getPath());
  				filter.addFieldParam("project.projectId", project.getProjectId());
  			}
  		}
  		return filter;
	}
	
	/**
	 * 把projectId回填到list页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String projectId=request.getParameter("projectId");
		return getPathView(request).addObject("projectId", projectId);
	}
	
	
	/**
	 * 返回对应projectId的参与人员，显示名字(这就是参与人员的页面)
	 * @param request
	 * @param response
	 * @return myList
	 */
	@RequestMapping("myList")
	@ResponseBody
	public JsonPageResult<ProAttend> myList(HttpServletRequest request,HttpServletResponse response){
		String projectId=request.getParameter("projectId");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("project.projectId", projectId);
		List<ProAttend> list=proAttendManager.getAll(queryFilter);
		List<ProAttend> mylist=new ArrayList<ProAttend>();
		for (ProAttend proAttend : list) {
			StringBuffer user=new StringBuffer("");
	    	StringBuffer group=new StringBuffer("");
	    	String[] userids=proAttend.getUserId().split(",");
	    	for (String string : userids) {//遍历用户,并把用户名加入到Stringbuffer里
	    		if(StringUtils.isNotBlank(string))
    			user.append(osUserManager.get(string).getFullname());
    			user.append(" ");
			}
	    	String username=user.toString();
	    	String[] groups=proAttend.getGroupId().split(",");
	    	for (String string : groups) {//遍历组,并把组名加入到Stringbuffer里
	    		if(StringUtils.isNotBlank(string))
    			group.append(osGroupManager.get(string).getName());
    			group.append(" ");
			}
	    	String groupname=group.toString();
	    	proAttend.setUsernames(username);
	    	proAttend.setGroupnames(groupname);
			mylist.add(proAttend);
		}
		 JsonPageResult<ProAttend> result=new JsonPageResult(list,list.size());
			return result;
		
	}
	
   /**
    * 删除参与人员
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
            	ProWorkMat proWorkMat=new ProWorkMat();
                proWorkMat.setActionId(idGenerator.getSID());
                proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
                proWorkMat.setTypePk(proAttendManager.get(id).getProject().getProjectId());
                proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"删除了项目'"+projectManager.get(proAttendManager.get(id).getProject().getProjectId()	).getName()+"'的人员");
                String context=proWorkMat.getContent();
                List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
                for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
                	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
                		String userId=proWorkAtt.getUserId();
       				 	infInnerMsgManager.sendMessage(userId, null,context , "", true);
                	}
    			}
                proWorkMatManager.create(proWorkMat);//创建日志记录
            	
                proAttendManager.delete(id);
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
        ProAttend proAttend=null;
        StringBuffer user=new StringBuffer("");
    	String users="";//显示人名
    	String groupss="";//显示组名
    	StringBuffer group=new StringBuffer("");
        if(StringUtils.isNotEmpty(pkId)){//如果有id则拼接出人名组名并回填
           proAttend=proAttendManager.get(pkId);
           String[] userids=proAttend.getUserId().split(",");
   		
   		for (String string : userids) {//遍历用户,并把用户名加入到Stringbuffer里
   			if(StringUtils.isNotBlank(string)){
   			user.append(osUserManager.get(string).getFullname());
   			user.append(",");}
			}
   		if(user.length()>1)
   		users=user.substring(0, user.length()-1);
   		String[] groups=proAttend.getGroupId().split(",");
   		for (String string : groups) {//遍历组,并把组名加入到Stringbuffer里
   			if(StringUtils.isNotBlank(string)){
   			group.append(osGroupManager.get(string).getName());
   			group.append(",");}
			}
   		if(group.length()>1)
   		groupss=group.substring(0, group.length()-1);
        }else{
        	proAttend=new ProAttend();
        }
        return getPathView(request).addObject("proAttend",proAttend).addObject("user", users).addObject("group", groupss);
    }
    
    /**
     * 编辑和创建页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String projectId=request.getParameter("parentId");//人员隶属那个项目
    	ProAttend proAttend=null;
    	StringBuffer user=new StringBuffer("");
    	String users="";
    	String groupss="";
    	StringBuffer group=new StringBuffer("");
    	if(StringUtils.isNotEmpty(pkId)){//更新
    		proAttend=proAttendManager.get(pkId);
    		String[] userids=proAttend.getUserId().split(",");
    		for (String string : userids) {//遍历用户,并把用户名加入到Stringbuffer里
    			if(StringUtils.isNotBlank(string)){
    			user.append(osUserManager.get(string).getFullname());
    			user.append(",");}
			}
    		if(user.length()>1)
    		users=user.substring(0, user.length()-1);
    		String[] groups=proAttend.getGroupId().split(",");
    		for (String string : groups) {//遍历组,并把组名加入到Stringbuffer里
    			if(StringUtils.isNotBlank(string)){
    			group.append(osGroupManager.get(string).getName());
    			group.append(",");}
			}
    		if(group.length()>1)
    		groupss=group.substring(0, group.length()-1);
    	}else{//创建
    		proAttend=new ProAttend();
    		proAttend.setProject(projectManager.get(projectId));//人员隶属那个项目
    	}
    	return getPathView(request).addObject("proAttend",proAttend).addObject("user", users).addObject("group", groupss);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return proAttendManager;
	}
	
	

}
