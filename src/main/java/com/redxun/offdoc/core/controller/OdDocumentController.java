package com.redxun.offdoc.core.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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

import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.offdoc.core.entity.OdDocument;
import com.redxun.offdoc.core.manager.OdDocumentManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysDicManager;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsDimensionManager;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * [OdDocument]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/offdoc/core/odDocument/")
public class OdDocumentController extends TenantListController{
    @Resource
    OdDocumentManager odDocumentManager;
    @Resource
    SysTreeManager sysTreeManager;
    @Resource
    OsGroupManager osGroupManager;
    @Resource
    OsUserManager osUserManager;
    @Resource
    OsDimensionManager osDimensionManager;
    @Resource
    SysDicManager sysDicManager;
    @Resource
    BpmInstManager bpmInstManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter filter=super.getQueryFilter(request);
  		//查找分类下的模型
  		String treeId=request.getParameter("treeId");
  		String archType=request.getParameter("archType");
  		
  		if(StringUtils.isNotEmpty(treeId)){
  			SysTree sysTree=sysTreeManager.get(treeId);
  			filter.addFieldParam("sysTree.path",  sysTree.getPath());//("sysTree.path", sysTree.getPath());
  		}
  		if(StringUtils.isNotEmpty(archType)){
  			filter.addFieldParam("archType",Short.parseShort(archType));//("sysTree.path", sysTree.getPath());
  		}
  		
  		return filter;	
	
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                odDocumentManager.delete(id);
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
        OdDocument odDocument=null;
        String mainDep="";
        String ccDep="";
        String issueDep="";
        String takeDep="";
        String joinDeps="";
        String assDep="";
        String status="";
        String urgenLevel="";
        String privacyLevel="";
        String archType="";
        if(StringUtils.isNotEmpty(pkId)){
           odDocument=odDocumentManager.get(pkId);
        }else{
        	odDocument=new OdDocument();
        }
     
    	if(StringUtils.isNotBlank(odDocument.getDocType())){
    		odDocument.setCNType(sysDicManager.get(odDocument.getDocType()).getName());
    	}
    	if(StringUtils.isNotBlank(odDocument.getMainDepId())){
			String[] mainDepIds=odDocument.getMainDepId().split(",");
			for (String string : mainDepIds) {
				mainDep=osGroupManager.get(string).getName()+","+mainDep;
			}
				mainDep=mainDep.substring(0, mainDep.length()-1);
		}
		if(StringUtils.isNotBlank(odDocument.getCcDepId())){
			String[] ccDepIds=odDocument.getCcDepId().split(",");
			for (String string : ccDepIds) {
				ccDep=osGroupManager.get(string).getName()+","+ccDep;
			}
				ccDep=ccDep.substring(0, ccDep.length()-1);
		}
    	if(StringUtils.isNotBlank(odDocument.getIssueDepId())){
    		 issueDep=osGroupManager.get(odDocument.getIssueDepId()).getName();//发文机关
    	}
    	
    	if(StringUtils.isNotBlank(odDocument.getTakeDepId())){
    		takeDep=osGroupManager.get(odDocument.getTakeDepId()).getName();//承办机关
    	}
    	if(StringUtils.isNotBlank(odDocument.getAssDepId())){
    		assDep=osGroupManager.get(odDocument.getAssDepId()).getName();//协办机关
    	}
    	if(StringUtils.isNotBlank(odDocument.getJoinDepIds())){
    		StringBuffer sb=new StringBuffer("");
    		String[] ids=odDocument.getJoinDepIds().split(",");
    		for (String depId : ids) {
				sb.append(osGroupManager.get(depId).getName());
				sb.append(",");
			}
    		sb.deleteCharAt(sb.length()-1);
    		joinDeps=sb.toString();//联合发文部门
    	}
    	if("0".equals(odDocument.getStatus())){
    		status="拟稿";
    	}else if("1".equals(odDocument.getStatus())){
    		status="发文状态";
      }else if("2".equals(odDocument.getStatus())){
    	  status="归档状态";
      }
    	
    	if("COMMON".equals(odDocument.getUrgentLevel())){
    		urgenLevel="普通";
    	}else if("URGENT".equals(odDocument.getUrgentLevel())){
    		urgenLevel="紧急";
    	}else if("URGENTEST".equals(odDocument.getUrgentLevel())){
    		urgenLevel="特急";
    	}else if("MORE_URGENT".equals(odDocument.getUrgentLevel())){
    		urgenLevel="特提";
    	}
    	
    	
    	if("COMMON".equals(odDocument.getPrivacyLevel())){
    		privacyLevel="普通";
    	}else if("SECURITY	".equals(odDocument.getPrivacyLevel())){
    		privacyLevel="秘密";
    	}else if("MIDDLE-SECURITY".equals(odDocument.getPrivacyLevel())){
    		privacyLevel="机密";
    	}else if("TOP SECURITY".equals(odDocument.getPrivacyLevel())){
    		privacyLevel="绝密";
    	}
    	
    	if("0".equals(String.valueOf(odDocument.getArchType()))){
    		archType="发文";
    	}else if("1".equals(String.valueOf(odDocument.getArchType()))){
    		archType="收文";
    	}
    	String docPath=odDocument.getBodyFilePath();
    	String docType="doc";
    	BpmInst bpmInst=bpmInstManager.getByActInstId(odDocument.getBpmInstId());
    	String bpmInstId="";
    	if( bpmInst!=null){
    		bpmInstId= bpmInst.getInstId();
    	}
        return getPathView(request).addObject("odDocument",odDocument).addObject("bpmInstId", bpmInstId).addObject("mainDep", mainDep).addObject("docType", docType).addObject("docPath", docPath).addObject("ccDep", ccDep).addObject("issueDep", issueDep).addObject("takeDep", takeDep).addObject("assDep", assDep).addObject("joinDeps", joinDeps).addObject("status",status ).addObject("archType", archType).addObject("privacyLevel",privacyLevel ).addObject("urgenLevel",urgenLevel );
    }
    
    
 
    /**
     * 获取
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getDocFile")
    public void getDocFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String docPath=request.getParameter("docPath");
    	// 创建file对象
		File file = new File(WebAppUtil.getAppAbsolutePath()+"/WEB-INF/docs/"+docPath);
		// 设置response的编码方式
		response.setContentType("application/x-msdownload");
		// 写明要下载的文件的大小

		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff =null;
		OutputStream out=null;
		
		try{
			buff=new BufferedInputStream(fis);
			out = response.getOutputStream();
			byte[] bs = new byte[1024];
			int n = 0;
			while ((n = buff.read(bs)) != -1) {
				out.write(bs, 0, n);
			}
		}finally{
			fis.close();
			buff.close();
			out.close();
		}
    }
    
    /**
     * 收文
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("incomingEdit")
    public ModelAndView incomingEdit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String recId=request.getParameter("recId");
    	String fileIds="";
    	String fileNames="";
    	String mainDep="";
    	String ccDep="";
    	String joinDeps="";
    	String issueDep="";//发文部门中文名
    	StringBuffer joinDep=new StringBuffer("");
    	OdDocument odDocument=odDocumentManager.get(pkId);
    	if(StringUtils.isNotBlank(odDocument.getMainDepId())){
			String[] mainDepIds=odDocument.getMainDepId().split(",");
			for (String string : mainDepIds) {
				mainDep=osGroupManager.get(string).getName()+","+mainDep;
			}
				mainDep=mainDep.substring(0, mainDep.length()-1);
		}
		if(StringUtils.isNotBlank(odDocument.getCcDepId())){
			String[] ccDepIds=odDocument.getCcDepId().split(",");
			for (String string : ccDepIds) {
				ccDep=osGroupManager.get(string).getName()+","+ccDep;
			}
				ccDep=ccDep.substring(0, ccDep.length()-1);
		}
    		if(StringUtils.isNotBlank(odDocument.getJoinDepIds())){
    			String[] ids=odDocument.getJoinDepIds().split(",");
        		for (String depId : ids) {
    				joinDep.append(osGroupManager.get(depId).getName());
    				joinDep.append(",");
    			}
        		joinDep.deleteCharAt(joinDep.length()-1);
    		}
    		fileIds=odDocument.getFileIds();
    		fileNames=odDocument.getFileNames();
    		joinDeps=joinDep.toString();
    		issueDep=osGroupManager.get(odDocument.getIssueDepId()).getName();
    		String assDepId=odDocument.getAssDepId();
    		String takeDepId=odDocument.getTakeDepId();
    		String assDepName="";
    		String takeDepName="";
    		if(StringUtils.isNotBlank(assDepId)){
    			assDepName=osGroupManager.get(assDepId).getName();
    		}
    		if(StringUtils.isNotBlank(takeDepId)){
    			takeDepName=osGroupManager.get(takeDepId).getName();
    		}
    		String privacy="";
    		String pl=odDocument.getPrivacyLevel();
    		if("COMMON".equals(pl)){
    			privacy="普通";
    		}else if("SECURITY".equals(pl)){
    			privacy="秘密";
    		}else if("MIDDLE-SECURITY".equals(pl)){
    			privacy="机密";
    		}else if("TOP-SECURITY".equals(pl)){
    			privacy="绝密";
    		}
    		
    		String urgent="";
    		String ul=odDocument.getUrgentLevel();
    		if("COMMON".equals(ul)){
    			urgent="普通";
    		}else if("URGENT".equals(ul)){
    			urgent="紧急";
    		}else if("URGENTEST".equals(ul)){
    			urgent="特急";
    		}else if("MORE_URGENT".equals(ul)){
    			urgent="特提";
    		}
    		
    	return getPathView(request).addObject("odDocument",odDocument).addObject("urgent", urgent).addObject("privacy",privacy).addObject("assDepName", assDepName).addObject("takeDepName", takeDepName).addObject("incoming", "incoming").addObject("fileIds",fileIds).addObject("fileNames",fileNames).addObject("ccDep", ccDep).addObject("mainDep", mainDep).addObject("joinDeps", joinDeps).addObject("issueDep",issueDep).addObject("isDispatchDoc", "YES").addObject("recId", recId);
    }
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String treeId=request.getParameter("treeId");
    	String fileIds="";
    	String fileNames="";
    	String mainDep="";
    	String ccDep="";
    	String joinDeps="";
    	String issueDep="";//发文部门中文名
    	StringBuffer joinDep=new StringBuffer("");
    	OdDocument odDocument=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		odDocument=odDocumentManager.get(pkId);
    		if(StringUtils.isNotBlank(odDocument.getMainDepId())){
    			String[] mainDepIds=odDocument.getMainDepId().split(",");
    			for (String string : mainDepIds) {
    				mainDep=osGroupManager.get(string).getName()+","+mainDep;
				}
    				mainDep=mainDep.substring(0, mainDep.length()-1);
    		}
    		if(StringUtils.isNotBlank(odDocument.getCcDepId())){
    			String[] ccDepIds=odDocument.getCcDepId().split(",");
    			for (String string : ccDepIds) {
    				ccDep=osGroupManager.get(string).getName()+","+ccDep;
				}
    				ccDep=ccDep.substring(0, ccDep.length()-1);
    		}
    		if(StringUtils.isNotBlank(odDocument.getJoinDepIds())){
    			String[] ids=odDocument.getJoinDepIds().split(",");
        		for (String depId : ids) {
    				joinDep.append(osGroupManager.get(depId).getName());
    				joinDep.append(",");
    			}
        		joinDep.deleteCharAt(joinDep.length()-1);
    		}
    		
    		
    		fileIds=odDocument.getFileIds();
    		fileNames=odDocument.getFileNames();
    		joinDeps=joinDep.toString();
    		issueDep=osGroupManager.get(odDocument.getIssueDepId()).getName();
    		
    	}else{
    		odDocument=new OdDocument();
    		odDocument.setTreeId(treeId);
    		OsGroup mainGroup=osGroupManager.getMainDeps(ContextUtil.getCurrentUserId());
    		if(mainGroup!=null){
    			issueDep=mainGroup.getName();
    		}
    		
    	}
    	String docPath=odDocument.getBodyFilePath();
    	String docType="doc";
    	return getPathView(request).addObject("odDocument",odDocument).addObject("fileIds",fileIds).addObject("docType", docType).addObject("docPath", docPath).addObject("fileNames",fileNames).addObject("ccDep", ccDep).addObject("mainDep", mainDep).addObject("joinDeps", joinDeps).addObject("issueDep",issueDep);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return odDocumentManager;
	}
	
	/**
	 * 收文
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("listOdDoc")
	@ResponseBody
	public JsonPageResult<OdDocument> listOdDoc(HttpServletRequest request,HttpServletResponse response){
		String tenantId=ContextUtil.getCurrentTenantId();
		
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("tenantId", tenantId);
		 OsGroup group=osGroupManager.getMainDeps(ContextUtil.getCurrentUserId());
		String groupId=(group==null)?"0":group.getGroupId();
		queryFilter.addFieldParam("mainDepId", groupId);
		
		 queryFilter.addFieldParam("archType", 1);
		 List<OdDocument> list=odDocumentManager.getAll(queryFilter);
		 for (OdDocument odDocument : list) {
			 if(StringUtils.isNotBlank(odDocument.getDocType())){
				 odDocument.setCNType(sysDicManager.get(odDocument.getDocType()).getName());
			 }
			
		}
		 JsonPageResult<OdDocument> jsonPageResult=new JsonPageResult(list,queryFilter.getPage().getTotalItems());
		return jsonPageResult;
		
	}
	
	@RequestMapping("selectGroup")
	@ResponseBody
	public List<OsGroup> selectGroup(HttpServletResponse response,HttpServletRequest request){
		String dimId=osDimensionManager.getByDimKeyTenantId("_ADMIN", ContextUtil.getCurrentTenantId()).getDimId();
		List<OsGroup> list=osGroupManager.getByDimIdTenantId(dimId, ContextUtil.getCurrentTenantId());
		
		return list;
		
	}

}
