package com.redxun.oa.pro.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.IJson;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.entity.ProVers;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.oa.pro.entity.Project;
import com.redxun.oa.pro.entity.ReqMgr;
import com.redxun.oa.pro.manager.PlanTaskManager;
import com.redxun.oa.pro.manager.ProAttendManager;
import com.redxun.oa.pro.manager.ProVersManager;
import com.redxun.oa.pro.manager.ProWorkAttManager;
import com.redxun.oa.pro.manager.ProWorkMatManager;
import com.redxun.oa.pro.manager.ProjectManager;
import com.redxun.oa.pro.manager.ReqMgrManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 需求管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/pro/reqMgr/")
public class ReqMgrController extends TenantListController{
    @Resource
    ReqMgrManager reqMgrManager;
    
    @Resource
    ProjectManager projectManager;
    
    @Resource
    ProVersManager proVersManager;
    
    @Resource
    OsUserManager osUserManager;
    @Resource
    ProAttendManager proAttendManager;
    @Resource
    ProWorkMatManager proWorkMatManager;
    @Resource
    PlanTaskManager planTaskManager;
    @Resource
    InfInnerMsgManager infInnerMsgManager;
    @Resource
    ProWorkAttManager proWorkAttManager;
    @Resource
    IJson iJson;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter filter=super.getQueryFilter(request);
  		//查找项目下的需求
  		String projectId=request.getParameter("projectId");
  		if(StringUtils.isNotEmpty(projectId)){
  		     Project project=projectManager.get(projectId);
  			if(project!=null){
  				filter.addFieldParam("project.projectId", project.getProjectId());
  			}
  		}
  		return filter;
	}
   /**
    * 删除需求
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
                proWorkMat.setTypePk(reqMgrManager.get(id).getProject().getProjectId());
                proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"删除了项目'"+projectManager.get(reqMgrManager.get(id).getProject().getProjectId()).getName()+"'的需求："+reqMgrManager.get(id).getSubject());
                String context=proWorkMat.getContent();
                List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
                for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
                	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
                		String userId=proWorkAtt.getUserId();
                		infInnerMsgManager.sendMessage(userId, null,context , "", true);
                	}
    			}
                proWorkMatManager.create(proWorkMat);
                reqMgrManager.delete(id);
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
        String checker="";
    	String rep="";
    	String exe="";
    	String tasknum="";
        ReqMgr reqMgr=null;
        if(StringUtils.isNotEmpty(pkId)){
        	reqMgr=reqMgrManager.get(pkId);
    		if(StringUtils.isNotBlank(reqMgr.getCheckerId()))//如果有checkerId,则回填他的名字到页面
    		checker=osUserManager.get(reqMgr.getCheckerId()).getFullname();
    		if(StringUtils.isNotBlank(reqMgr.getRepId()))//同上
    		rep=osUserManager.get(reqMgr.getRepId()).getFullname();	
    		if(StringUtils.isNotBlank(reqMgr.getExeId()))//同上
    		exe=osUserManager.get(reqMgr.getExeId()).getFullname();
    		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    		queryFilter.addFieldParam("reqMgr.reqId", pkId);
    		tasknum=planTaskManager.getAll(queryFilter).size()+"";
        }else{
        	reqMgr=new ReqMgr();
        }
        return getPathView(request).addObject("reqMgr",reqMgr).addObject("checker", checker).addObject("rep",rep).addObject("exe", exe).addObject("tasknum", tasknum);
    }
    
    /**
     * 编辑和创建
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String projectId = request.getParameter("projectId");
		String pkId = request.getParameter("pkId");
		String parentId = request.getParameter("parentId");
		String level = request.getParameter("level");
        String subnode=request.getParameter("subnode");
		String checker = "";//用来回填的字段:人
		String rep = "";
		String exe = "";
		ReqMgr reqMgr = null;
		if (StringUtils.isNotEmpty(pkId)) {
			reqMgr = reqMgrManager.get(pkId);
			if (StringUtils.isNotBlank(reqMgr.getCheckerId()))//如果有checkerId,则回填他的名字到页面
				checker = osUserManager.get(reqMgr.getCheckerId()).getFullname();
			if (StringUtils.isNotBlank(reqMgr.getRepId()))//同上
				rep = osUserManager.get(reqMgr.getRepId()).getFullname();
			if (StringUtils.isNotBlank(reqMgr.getExeId()))//同上
				exe = osUserManager.get(reqMgr.getExeId()).getFullname();
		} else {
			reqMgr = new ReqMgr();
			reqMgr.setProject(projectManager.get(projectId));
			
			if (StringUtils.isNotEmpty(parentId)) {
				reqMgr.setParentId(parentId);
				int i = Integer.parseInt(level);// 层次
				reqMgr.setLevel(i);
			}
			
		}
		List<ProVers> proVers = proVersManager.getByProjectId(projectId);
		String jsonResult = iJson.toJson(proVers);
		return getPathView(request).addObject("reqMgr", reqMgr).addObject("projectId", projectId).addObject("jsonResult", jsonResult).addObject("checker", checker).addObject("rep", rep).addObject("exe", exe);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return reqMgrManager;
	}
	/**
	 * 回填projectId给list页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String projectId=request.getParameter("projectId");
		List<ProVers> proVers=proVersManager.getByProjectId(projectId);
		int ListSize=proVers.size();
    	return getPathView(request).addObject("projectId", projectId).addObject("ListSize", ListSize);
	}
	/**
	 * 返回项目的需求
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("reqlist")
	@ResponseBody
	public JsonPageResult<ReqMgr> reqList(HttpServletRequest request,HttpServletResponse response){
		String projectId=request.getParameter("projectId");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("project.projectId", projectId);
		List<ReqMgr> reqMgrs=reqMgrManager.getAll(queryFilter);//
		JsonPageResult<ReqMgr> result=new JsonPageResult(reqMgrs,reqMgrs.size());
		return result;}
	
	
	
	/**
     * 获取项目下的需求
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("getreq")
    @ResponseBody
    public List<ReqMgr> getMyReq(HttpServletRequest request,HttpServletResponse response){
    	String projectId=request.getParameter("projectId");
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("project.projectId", projectId);
		List<ReqMgr> reqMgrs=reqMgrManager.getAll(queryFilter);
		return reqMgrs;}
	
	
    /**
     * 把项目的需求列举出来
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping("listMgr")
	@ResponseBody
	public List<ReqMgr> mgrs(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String projectId=request.getParameter("projectId");
		String mine=request.getParameter("mine");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("project.projectId", projectId);
		//如果为草稿状态就把数据拿出来
		if(StringUtils.isNotBlank(mine)){
			queryFilter.addFieldParam("status","DRAFT");
		}
		List<ReqMgr> reqMgrs=reqMgrManager.getAll(queryFilter);
		return reqMgrs;
	}
	
	/***
	 * 返回我负责的项目
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getUserMgr")
	@ResponseBody
	public List<ReqMgr> getUserMgr(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String thisUserId=ContextUtil.getCurrentUserId();
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("repId", thisUserId);
		queryFilter.addFieldParam("status","DRAFT");
		List<ReqMgr> mgrs=reqMgrManager.getAll(queryFilter);
		return mgrs;
		
	}
	
	
	
	

	
	
	
	 /**
     * 读取html文件到word
     * @param filepath html文件的路径
     * @return
     * @throws Exception
     */
    public boolean writeWordFile(String filepath) throws Exception {
           boolean flag = false;
           ByteArrayInputStream bais = null;
           FileOutputStream fos = null;
           String path = "C:/";  //根据实际情况写路径(输出路径)
           try {
                  if (!"".equals(path)) {
                         File fileDir = new File(path);
                         if (fileDir.exists()) {
                                String content = readFile(filepath);
                                byte b[] = content.getBytes();
                                bais = new ByteArrayInputStream(b);
                                POIFSFileSystem poifs = new POIFSFileSystem();
                                DirectoryEntry directory = poifs.getRoot();
                                DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
                                
                                fos = new FileOutputStream(path + "temp.xml");
                                poifs.writeFilesystem(fos);
                                bais.close();
                                fos.close();
                         }
                  }

           } catch (IOException e) {
                  e.printStackTrace();
           } finally {
                  if(fos != null) fos.close();
                  if(bais != null) bais.close();
           }
           return flag;
    }

    
 
    /**
     * 读取html文件到字符串
     * @param filename
     * @return
     * @throws Exception
     */
    public String readFile(String filename) throws Exception {
           StringBuffer buffer = new StringBuffer("");
           BufferedReader br = null;
           try {
                  br = new BufferedReader(new FileReader(filename));
                  buffer = new StringBuffer();
                  while (br.ready())
                         buffer.append((char) br.read());
           } catch (Exception e) {
                  e.printStackTrace();
           } finally {
                  if(br!=null) br.close();
           }
           return buffer.toString();
    }
    
    @RequestMapping("abc")
    public  void gogogo() throws Exception{
    	writeWordFile("D:/dev/jsaas-dev/jsaas_web/src/main/java/com/redxun/oa/pro/controller/template/NewFile2.html");
    }

    
    
	

}
