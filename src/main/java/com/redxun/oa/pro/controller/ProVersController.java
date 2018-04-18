package com.redxun.oa.pro.controller;

import java.util.Date;
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
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.entity.ProVers;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.oa.pro.entity.Project;
import com.redxun.oa.pro.manager.ProVersManager;
import com.redxun.oa.pro.manager.ProWorkAttManager;
import com.redxun.oa.pro.manager.ProWorkMatManager;
import com.redxun.oa.pro.manager.ProjectManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 版本管理
 * @author cmc
 */
@Controller
@RequestMapping("/oa/pro/proVers/")
public class ProVersController extends TenantListController{
    @Resource
    ProVersManager proVersManager;
    
    @Resource
    ProjectManager projectManager;
    @Resource
    OsUserManager osUserManager;
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
    * 删除版本
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
                proWorkMat.setTypePk(proVersManager.get(id).getProject().getProjectId());
                proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"删除了项目'"+projectManager.get(proVersManager.get(id).getProject().getProjectId()).getName()+"'的版本'"+proVersManager.get(id).getVersion()+"'");
                String context=proWorkMat.getContent();
                List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
                for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
                	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
                		String userId=proWorkAtt.getUserId();
       				 	infInnerMsgManager.sendMessage(userId, null,context , "", true);
                	}
    			}
                proWorkMatManager.create(proWorkMat);//生成日志记录
            	
                proVersManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    /**
     * 根据Id查看明细,返回.get页面
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        ProVers proVers=null;
        if(StringUtils.isNotEmpty(pkId)){
           proVers=proVersManager.get(pkId);
        }else{
        	proVers=new ProVers();
        }
        return getPathView(request).addObject("proVers",proVers);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String projectId=request.getParameter("parentId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	ProVers proVers=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		proVers=proVersManager.get(pkId);
    		if("true".equals(forCopy)){
    			proVers.setVersionId(null);
    		}
    	}else{
    		proVers=new ProVers();
    		proVers.setProject(projectManager.get(projectId));
    	}
    	return getPathView(request).addObject("proVers",proVers);
    }
    
    /**
     * list页面传递projectId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String projectId=request.getParameter("projectId");
    	return getPathView(request).addObject("projectId", projectId);}
    

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return proVersManager;
	}
	
	/**
	 * 发布版本
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping("publish")
	    @ResponseBody
	    public JsonResult publish(HttpServletRequest request,HttpServletResponse response) throws Exception{
	        String versionId=request.getParameter("versionId");
	        String projectId=request.getParameter("projectId");
	        if(StringUtils.isNotEmpty(versionId)){
	               ProVers proVers= proVersManager.get(versionId);
	               proVers.setStatus("DEPLOYED");//把版本设置成发布
	               Date date=new Date();
	               proVers.setEndTime(date);
	               proVersManager.saveOrUpdate(proVers);
	               Project project= projectManager.get(projectId);
		              project.setEndTime(date);
		              project.setStatus("DEPLOYED");
		              project.setVersion(proVers.getVersion());
		              projectManager.update(project);
		              ProWorkMat proWorkMat=new ProWorkMat();
		                proWorkMat.setActionId(idGenerator.getSID());
		                proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
		                proWorkMat.setTypePk(projectId);
		                proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"发布了项目'"+project.getName()+"'的版本'"+proVersManager.get(versionId).getVersion()+"'");
		                String context=proWorkMat.getContent();
		                List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
		                for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
		                	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
		                		String userId=proWorkAtt.getUserId();
		                		infInnerMsgManager.sendMessage(userId, null,context , "", true);
		                	}
		    			}
		                proWorkMatManager.create(proWorkMat);
	            
	        }
	        return new JsonResult(true,"成功发布！");
	    }
	 
	 
	 
	 /**
		 * 启动版本
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		 @RequestMapping("start")
		    @ResponseBody
		    public JsonResult start(HttpServletRequest request,HttpServletResponse response) throws Exception{
		        String versionId=request.getParameter("versionId");
		        String projectId=request.getParameter("projectId");
		        if(StringUtils.isNotEmpty(versionId)){
		               ProVers proVers= proVersManager.get(versionId);
		               proVers.setStatus("RUNNING");
		               Date startTime=new Date();
		               System.out.println(startTime.toString());
		               proVers.setStartTime(startTime);
		               proVersManager.saveOrUpdate(proVers);
		              Project project= projectManager.get(projectId);
		              project.setStartTime(startTime);
		              project.setStatus("RUNNING");
		              project.setVersion(proVers.getVersion());
		              projectManager.update(project);
		              ProWorkMat proWorkMat=new ProWorkMat();
		                proWorkMat.setActionId(idGenerator.getSID());
		                proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
		                proWorkMat.setTypePk(projectId);
		                proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"启动了项目'"+project.getName()+"'的版本'"+proVersManager.get(versionId).getVersion()+"'");
		                String context=proWorkMat.getContent();
		                List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
		                for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
		                	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
		                		String userId=proWorkAtt.getUserId();
		                		infInnerMsgManager.sendMessage(userId, null,context , "", true);
		                	}
		    			}
		                proWorkMatManager.create(proWorkMat);
		        }
		        return new JsonResult(true,"启动成功！");
		    }
	 
	 /**	
	  * 获取版本列表
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping("verList")
	 @ResponseBody
	 public JsonPageResult<ProVers> verList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 String projectId=request.getParameter("projectId");
		 
		 List<ProVers> list=proVersManager.getByProjectId(projectId);
		 JsonPageResult<ProVers> result=new JsonPageResult(list,list.size());
		return result;
	 }
	 
	

}
