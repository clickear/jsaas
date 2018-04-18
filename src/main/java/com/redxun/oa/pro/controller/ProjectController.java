package com.redxun.oa.pro.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.engine.FreemarkEngine;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.entity.ProAttend;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.oa.pro.entity.Project;
import com.redxun.oa.pro.entity.ReqMgr;
import com.redxun.oa.pro.manager.ProAttendManager;
import com.redxun.oa.pro.manager.ProVersManager;
import com.redxun.oa.pro.manager.ProWorkAttManager;
import com.redxun.oa.pro.manager.ProWorkMatManager;
import com.redxun.oa.pro.manager.ProjectManager;
import com.redxun.oa.pro.manager.ReqMgrManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsRelInstManager;
import com.redxun.sys.org.manager.OsRelTypeManager;
import com.redxun.sys.org.manager.OsUserManager;

import freemarker.template.TemplateException;
import net.sf.json.JSONObject;

/**
 * 项目管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/pro/project/")
public class ProjectController extends TenantListController{
    @Resource
    ProjectManager projectManager;
    
    @Resource
    SysTreeManager sysTreeManager;
    
    @Resource
    ProVersManager proVersManager;
    
    @Resource
    OsUserManager osUserManager;
    
    @Resource
    OsGroupManager groupManager;
    @Resource
    OsUserManager userManager;
    
    @Resource
    OsRelInstManager osRelInstManager;
    @Resource
    OsRelTypeManager osRelTypeManager;
    @Resource
    ProAttendManager proAttendManager;
    @Resource
    ProWorkMatManager proWorkMatManager;
    
    @Resource
    ReqMgrManager reqMgrManager;
    @Resource
    InfInnerMsgManager infInnerMsgManager;
    
    @Resource
    FreemarkEngine freemarkEngine;
    @Resource
    ProWorkAttManager proWorkAttManager;
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter filter=super.getQueryFilter(request);
  		//查找分类下的模型
  		String treeId=request.getParameter("treeId");
  		if(StringUtils.isNotEmpty(treeId)){
  			SysTree sysTree=sysTreeManager.get(treeId);
  			/*if(sysTree!=null){
  				filter.addLeftLikeFieldParam("sysTree.path", sysTree.getPath());
  			}*/
  			
  			filter.addFieldParam("sysTree.path",  sysTree.getPath());//("sysTree.path", sysTree.getPath());
  		}
  		return filter;
		/*return QueryFilterBuilder.createQueryFilter(request);*/
	}
   
	/**
	 * 根据projectId删除项目
	 * @param request
	 * @param response
	 * @return JsonResult
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
                proWorkMat.setTypePk(id);
                proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"删除了项目'"+projectManager.get(id).getName()+"'");
                proWorkMatManager.create(proWorkMat);//生成日志
                String context=proWorkMat.getContent();
                List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
                for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
                	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
                		String userId=proWorkAtt.getUserId();
       				 	infInnerMsgManager.sendMessage(userId, null,context , "", true);
                	}
    			}
            	
                projectManager.delete(id);
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
        Project project=null;
        int MatNum=proWorkMatManager.getByProjectId(pkId).size();
        StringBuffer AttPeople=new StringBuffer("");//参与人
        StringBuffer AttGroup=new StringBuffer("");//参与组
        String repor="";//负责人
        if(StringUtils.isNotEmpty(pkId)){
           project=projectManager.get(pkId);
           if(StringUtils.isNotBlank(project.getReporId()))
           repor=osUserManager.get(project.getReporId()).getFullname();
           List<ProAttend> proAttends=proAttendManager.getByProjectId(pkId);
           /**
            * 把人和组拼接出来再显示回填进去明细页面
            */
           for (ProAttend proAttend2 : proAttends) {
        	   if(StringUtils.isNotBlank(proAttend2.getGroupId())){
        		   String[] groupIds=proAttend2.getGroupId().split(",");
        		   for (String string : groupIds) {
        			   AttGroup.append(groupManager.get(string).getName());
           			   AttGroup.append("  ");
				}   
        	   }
			if(StringUtils.isNotBlank(proAttend2.getUserId())){
				String[] userIds=proAttend2.getUserId().split(",");
				for (String string : userIds) {
					AttPeople.append(userManager.get(string).getFullname());
					AttPeople.append("  ");
				}
			}
		   }
        }else{
        	project=new Project();
              }
        String currentuser=ContextUtil.getCurrentUserId();
        return getPathView(request).addObject("project",project).addObject("repor", repor).addObject("AttGroup", AttGroup).addObject("AttPeople", AttPeople).addObject("currenruser", currentuser).addObject("MatNum", MatNum);
    }
    
    /**
     * 编辑和创建
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String parentId=request.getParameter("parentId");
    	String isadd=request.getParameter("isadd");
    	
    	Project project=null;
    	String repor="";
    	if(StringUtils.isNotEmpty(pkId)){//编辑
    		project=projectManager.get(pkId);
    		repor=osUserManager.get(project.getReporId()).getFullname();//回填到页面的负责人
    	}else{//创建
    		project=new Project();
    		project.setSysTree(sysTreeManager.get(parentId));//设置项目的分类
    	}
    	return getPathView(request).addObject("project",project).addObject("repor", repor).addObject("isadd", isadd);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return projectManager;
	}
	
	/**
	 * 负责的项目
	 * @param request
	 * @param response
	 * @return result
	 * @throws IOException 
	 */
	@RequestMapping("listMyRes")
	@ResponseBody
	public JsonPageResult<Project> listMyRes(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String thisUser=ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("reporId", thisUser); //根据负责人Id查询
		List<Project> list2=projectManager.getAll(queryFilter);
		JsonPageResult<Project> result=new JsonPageResult(list2,queryFilter.getPage().getTotalItems());
		return result;}
	
	/**
	 * 当没有分类tree的时候右边加载一个空的grid防止报错
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("listBlank")
	@ResponseBody
	public List<Project> listBlank(HttpServletRequest request,HttpServletResponse response){
		return null;}
	

	/**
	 * 参与的项目,包括我的组也参与了
	 * @param request
	 * @param response
	 * @return result
	 */
	@RequestMapping("listMyAttend")
	@ResponseBody
	public JsonPageResult<Project> listMyAttend(HttpServletRequest request,HttpServletResponse response){
		String thisUser=ContextUtil.getCurrentUserId();
		String thisTenantId=ContextUtil.getCurrentTenantId();
		SqlQueryFilter sqlQueryFilter=QueryFilterBuilder.createSqlQueryFilter(request);
		
		List<Project> projects=projectManager.getMyAttendProject(thisUser, thisTenantId, sqlQueryFilter);
		
        JsonPageResult<Project> result=new JsonPageResult(projects,sqlQueryFilter.getPage().getTotalItems());
        
		return result;}
	
	/**
	 * 按模板生成word文件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("generateToWord")
	public void generateToWord(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("application/msword;charset=utf-8");
		String projectId=request.getParameter("projectId");
		Project project=projectManager.get(projectId);
		response.setHeader("Content-Disposition", "attachment;filename=" + project.getName()+".doc");
		OutputStream os = response.getOutputStream();
		//要填入模本的数据文件
		Map<String,Object> dataMap=new HashMap<String,Object>();
		List<ReqMgr> table1= reqMgrManager.getByProjectId(projectId);//All(queryFilter);//获取项目对应的reqMgr列表
		
		for (ReqMgr reqMgr : table1) {
			reqMgr.setMyDescp(reqMgr.getDescp().replaceAll("</?[^>]+>", ""));;
			if(StringUtils.isNotBlank(reqMgr.getExeId()))
			{reqMgr.setExe(osUserManager.get(reqMgr.getExeId()).getFullname());
			}else{
				reqMgr.setExe("");
			}//设置中文名或者""传入word
			if(StringUtils.isNotBlank(reqMgr.getRepId()))
			{reqMgr.setRep(osUserManager.get(reqMgr.getRepId()).getFullname());
			}else{
				reqMgr.setRep("");
			}
			if(StringUtils.isNotBlank(reqMgr.getCheckerId()))
			{reqMgr.setChecker(osUserManager.get(reqMgr.getCheckerId()).getFullname());
			}else{
				reqMgr.setChecker("");
			}
		}
		dataMap.put("table1", table1);
		OutputStreamWriter outWriter=new OutputStreamWriter(os); 
        try {
			freemarkEngine.processTemplate("rqt/wordTemplate.ftl", dataMap, outWriter);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			outWriter.close();
			os.close();
		}
	}
	
	@RequestMapping("ifExist")
	@ResponseBody
	public JSONObject ifExist(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject=new JSONObject();
		String projectId=request.getParameter("id");
		if(projectManager.get(projectId)==null){
			jsonObject.put("success", false);
		}else{
			jsonObject.put("success", true);
		}
		
		return jsonObject;
	}

}
