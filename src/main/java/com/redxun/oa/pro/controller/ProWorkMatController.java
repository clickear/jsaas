package com.redxun.oa.pro.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.oa.pro.entity.Project;
import com.redxun.oa.pro.entity.ReqMgr;
import com.redxun.oa.pro.manager.ProAttendManager;
import com.redxun.oa.pro.manager.ProWorkAttManager;
import com.redxun.oa.pro.manager.ProWorkMatManager;
import com.redxun.oa.pro.manager.ProjectManager;
import com.redxun.oa.pro.manager.ReqMgrManager;

/**
 * 动态管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/pro/proWorkMat/")
public class ProWorkMatController extends BaseListController{
    @Resource
    ProWorkMatManager proWorkMatManager;
    @Resource
    ProjectManager projectManager;
    @Resource
    ReqMgrManager reqMgrManager;
    @Resource
    OsUserManager osUserManager;
    @Resource
    ProAttendManager proAttendManager;
    @Resource
    SysFileManager sysFileManager;
    @Resource
    InfInnerMsgManager infInnerMsgManager;
    @Resource
    ProWorkAttManager proWorkAttManager;
    
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
	/**
	 * 删除动态
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
               
                if("REQ".equals(proWorkMatManager.get(id).getType())){//如果是需求，则把该条动态的typeId改成需求对应的项目的Id，否则直接写项目的Id
                	 proWorkMat.setTypePk(reqMgrManager.get(proWorkMatManager.get(id).getTypePk()).getProject().getProjectId());
                }else{
                	 proWorkMat.setTypePk(proWorkMatManager.get(id).getTypePk());
                }
                proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"删除了'"+proWorkMatManager.get(id).getType()+"'的评论'"+proWorkMatManager.get(id).getContent()+"'");
                String context=proWorkMat.getContent();
                List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
                for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
                	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
                		String userId=proWorkAtt.getUserId();
       				 	infInnerMsgManager.sendMessage(userId, null,context , "", true);
                	}
    			}
                proWorkMatManager.create(proWorkMat);
                proWorkMatManager.delete(id);
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
        ProWorkMat proWorkMat=null;
        Map<String,String> map = new TreeMap<String, String>();//构建map存放对应的附件
        if(StringUtils.isNotEmpty(pkId)){
           proWorkMat=proWorkMatManager.get(pkId);
           List<SysFile> list=sysFileManager.getByModelNameRecordId("com.redxun.oa.pro.entity.ProWorkMat",pkId);
         
           for(SysFile file:list){
       		map.put(file.getFileId(), file.getFileName());//遍历每个实体的附件，放入map里
       	}
        }else{
        	proWorkMat=new ProWorkMat();
        }
        return getPathView(request).addObject("proWorkMat",proWorkMat).addObject("file", map);
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
    	String type=request.getParameter("type");
    	String projectId=request.getParameter("projectId");
    	ProWorkMat proWorkMat=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		proWorkMat=proWorkMatManager.get(pkId);
    		proWorkMat.setType(type);
    		proWorkMat.setTypePk(projectId);
    	}else{
    		proWorkMat=new ProWorkMat();
    		proWorkMat.setType(type);
    		proWorkMat.setTypePk(projectId);
    	}
    	String mytype="";//回填"类型"
    	if("PROJECT".equals(type)){
    		mytype=projectManager.get(projectId).getName();
    	}else{
    		mytype=reqMgrManager.get(projectId).getSubject();
    	}
    	return getPathView(request).addObject("proWorkMat",proWorkMat).addObject("mytype", mytype);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return proWorkMatManager;
	}
	
	
	/**
	 * 返回list的jsp页面，并把project这个实体传过去
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String projectId=request.getParameter("projectId");
		String reqId=request.getParameter("reqId");
		String type=request.getParameter("type");
		String noact=request.getParameter("noact");//让"动态"栏目禁止一些操作(list页面的toolbar)
		String thisprojectId="";
		ReqMgr reqMgr=null;
        Project project=null;
        if("MYACTION".equals(type)){//判断是否是动态,list页面的操作
        	thisprojectId=request.getParameter("projectId");
        }
        if(StringUtils.isNotBlank(projectId)){
        	project=projectManager.get(projectId);
        	if("ACTION".equals(type)){
            	project=null;
            }
        }
        if(StringUtils.isNotBlank(reqId))
        {
        	reqMgr=reqMgrManager.get(reqId);
        }
		return getPathView(request).addObject("project",project).addObject("reqMgr", reqMgr).addObject("type",type).addObject("thisprojectId", thisprojectId).addObject("noact", noact);
		}
	
	
	/**
	 * 返回list的jsp页面，并把project这个实体传过去
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listforact")
	public ModelAndView listForAct(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String projectId=request.getParameter("projectId");
		String reqId=request.getParameter("reqId");
		String type=request.getParameter("type");
		String noact=request.getParameter("noact");//让"动态"栏目禁止一些操作
		String thisprojectId="";
		ReqMgr reqMgr=null;
        Project project=null;
        if("ACTION".equals(type)){
        	thisprojectId=request.getParameter("projectId");
        }
        if(StringUtils.isNotBlank(projectId)){
        	project=projectManager.get(projectId);
        	if("ACTION".equals(type)){
            	project=null;
            }
        }
        if(StringUtils.isNotBlank(reqId))
        {
        	reqMgr=reqMgrManager.get(reqId);
        }
		return getPathView(request).addObject("project",project).addObject("reqMgr", reqMgr).addObject("type",type).addObject("thisprojectId", thisprojectId).addObject("noact", noact);
		}
	
	
	/**
	 * 取出该项目的评论
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("listByProject")
	@ResponseBody
	public JsonPageResult<ProWorkMat> listByProject(HttpServletRequest request,HttpServletResponse response){
		String projectId=request.getParameter("projectId");
		String reqId=request.getParameter("reqId");
		String type=request.getParameter("type");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		
		if("REQ".equals(type)){//如果是需求
			queryFilter.addFieldParam("typePk", reqId);
			queryFilter.addFieldParam("type", "REQ");
		}else{//否则是项目
			queryFilter.addFieldParam("typePk", projectId);
			queryFilter.addFieldParam("type", "PROJECT");}
		
		List<ProWorkMat> list=proWorkMatManager.getAll(queryFilter);
		
		JsonPageResult<ProWorkMat> result=new JsonPageResult(list,list.size());
		return result;}
	
	
	
/*	@RequestMapping("listForKey")
	@ResponseBody
	public JsonPageResult<ProWorkMat> listForKey(HttpServletRequest request,HttpServletResponse response){
		String projectId=request.getParameter("projectId");
		String key=request.getParameter("key");
		List<ProWorkMat> list=proWorkMatManager.getByProjectId(projectId);
		for (int i = 0; i < list.size(); i++) {
			if(!list.get(i).getContent().contains(key)){
				list.remove(i);
				i--;
			}
		}
		JsonPageResult<ProWorkMat> result=new JsonPageResult<ProWorkMat>(list,list.size());
		return result;}*/
	
	
	/**
	 * 评论里按字段查询(可含时间段)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("searchTheMat")
	@ResponseBody
	public JsonPageResult<ProWorkMat> searchTheMat(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String projectId=request.getParameter("projectId");
		String reqId=request.getParameter("reqId");
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		if(StringUtils.isNotBlank(reqId)){
			queryFilter.addFieldParam("typePk", reqId);
			queryFilter.addFieldParam("type", "REQ");
		}else{
		queryFilter.addFieldParam("typePk", projectId);
		queryFilter.addFieldParam("type", "PROJECT");}
		List<ProWorkMat> list=proWorkMatManager.getAll(queryFilter);
		JsonPageResult<ProWorkMat> result=new JsonPageResult<ProWorkMat>(list,list.size());
		return result;}
	
	/**
	 * 根据projectId拿出该项目动态
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("listAct")
	@ResponseBody
	public JsonPageResult<ProWorkMat> listAct(HttpServletRequest request,HttpServletResponse response){
		String projectId=request.getParameter("projectId");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("type", "MYACTION");
		queryFilter.addFieldParam("typePk", projectId);
		List<ProWorkMat> list=proWorkMatManager.getAll(queryFilter);//getActByProjectId(projectId);
		JsonPageResult<ProWorkMat> result=new JsonPageResult(list,list.size());
		return result;}

}
