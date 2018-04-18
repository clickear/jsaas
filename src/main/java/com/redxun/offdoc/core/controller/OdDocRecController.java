package com.redxun.offdoc.core.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.BpmSolution;
import com.redxun.bpm.core.entity.ProcessStartCmd;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.bpm.core.manager.BpmSolFvManager;
import com.redxun.bpm.core.manager.BpmSolutionManager;
import com.redxun.bpm.form.manager.BpmFormViewManager;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.StringUtil;
import com.redxun.offdoc.core.entity.OdDocFlow;
import com.redxun.offdoc.core.entity.OdDocRec;
import com.redxun.offdoc.core.entity.OdDocument;
import com.redxun.offdoc.core.manager.OdDocFlowManager;
import com.redxun.offdoc.core.manager.OdDocRecManager;
import com.redxun.offdoc.core.manager.OdDocumentManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.bo.entity.SysBoDef;
import com.redxun.sys.bo.manager.SysBoDefManager;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * [OdDocRec]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/offdoc/core/odDocRec/")
public class OdDocRecController extends TenantListController{
    @Resource
    OdDocRecManager odDocRecManager;
    @Resource
    OsUserManager osUserManager;
    @Resource
    OsGroupManager osGroupManager;
    @Resource
    BpmInstManager bpmInstManager;
    @Resource
    OdDocumentManager odDocumentManager;
    @Resource
    BpmSolutionManager bpmSolutionManager;
    @Resource
    BpmSolFvManager bpmSolFvManager;
    @Resource
    BpmFormViewManager bpmFormViewManager;
    @Resource
    OdDocFlowManager odDocFlowManager;
    @Resource
    SysBoDefManager sysBoDefManager;
    @Resource
    SysFileManager sysFileManager; 
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter filter=super.getQueryFilter(request);
		OsGroup group=osGroupManager.getMainDeps(ContextUtil.getCurrentUserId());
		//默认为零表示无主部门
		String groupId=(group==null)?"0":group.getGroupId();
		filter.addFieldParam("recDepId",groupId);//
  		return filter;	
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                odDocRecManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    
    @RequestMapping("haveRead")
    @ResponseBody
    public JsonResult haveRead(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        OdDocRec odDocRec=odDocRecManager.get(pkId);
        odDocRec.setIsRead("YES");
        if(odDocRec.getReadTime()!=null){
        	odDocRec.setReadTime(new Date());
        }
        
        odDocRecManager.saveOrUpdate(odDocRec);
        return new JsonResult(true,"已读！");
    }
    
    @RequestMapping("haveIncoming")
    @ResponseBody
    public JsonResult haveIncoming(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	SysBoDef sysBoDef=sysBoDefManager.getByAlias("SWGZ");
        String pkId=request.getParameter("pkId");
        String userId=ContextUtil.getCurrentUserId();
        OdDocRec odDocRec=odDocRecManager.get(pkId);
        odDocRec.setSignStatus("SIGNING");//改变签收状态
        odDocRec.setSignUsrId(userId); 
        odDocRecManager.saveOrUpdate(odDocRec);
        OsGroup osGroup=osGroupManager.getMainDeps(userId);
        String docId=odDocRec.getDocId();
        OdDocument odDocument=odDocumentManager.get(docId);
        JSONObject jsonObject2=JSONObject.fromObject(odDocument);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonObject2.put("issuedDate", sdf.format(odDocument.getIssuedDate()));
        String mainDepId=jsonObject2.getString("mainDepId");
		String joinDepIds=jsonObject2.getString("joinDepIds");
		String ccDepId=jsonObject2.getString("ccDepId");
		String issueDepId=jsonObject2.getString("issueDepId");
		String issueUsrId=jsonObject2.getString("issueUsrId");
		
		jsonObject2.put("mainDepId_name", osGroupManager.getNameByGroupId(mainDepId));
		jsonObject2.put("joinDepIds_name", osGroupManager.getNameByGroupId(joinDepIds));
		jsonObject2.put("ccDepId_name", osGroupManager.getNameByGroupId(ccDepId));
		jsonObject2.put("issueDepId_name", osGroupManager.getNameByGroupId(issueDepId));
		jsonObject2.put("issueUsrId_name", osUserManager.get(issueUsrId).getFullname());
        
        
        String[] fileIdArray=odDocument.getBodyFilePath().split("/");
        String fileId=fileIdArray[fileIdArray.length-1].split(".doc")[0];
        SysFile fileObj=sysFileManager.get(fileId);
        JSONObject file=new JSONObject();
        file.put("fileId", fileId);
        file.put("fileName", fileObj.getFileName());
        file.put("bytes", fileObj.getTotalBytes());
        JSONArray fileArray=new JSONArray();
        fileArray.add(file);
        jsonObject2.put("fj", fileArray);
        
        JSONArray fileAttachArray=new JSONArray();
        String atFile=odDocument.getFileIds();
        if(StringUtils.isNotBlank(atFile)){
        	String[] fileIdAttachArray =atFile.split(",");
        	for (int i = 0; i < fileIdAttachArray.length; i++) {
    			String attachFileId=fileIdAttachArray[i];
    				SysFile attachFile=sysFileManager.get(attachFileId);
    				JSONObject fileAttach=new JSONObject();
    				fileAttach.put("fileId", attachFileId);
    				fileAttach.put("fileName",attachFile.getFileName());
    				fileAttach.put("bytes", attachFile.getTotalBytes());
    				fileAttachArray.add(fileAttachArray);
    		}
        	jsonObject2.put("ffileNames", fileArray);
        }
        
        ProcessStartCmd startCmd=new ProcessStartCmd();
        OdDocFlow odDocFlow=findOdDocFlow(osGroup.getGroupId());
        if(odDocFlow!=null){
        	startCmd.setSolId(odDocFlow.getRecSolId());
        }else{
        	return new JsonResult(false,"祖上未配置收文流程！");
        }
        
        JSONObject dataObject=new JSONObject();
        dataObject.put("data", jsonObject2);
        dataObject.put("boDefId", sysBoDef.getId());
        dataObject.put("formKey", "fwbd");
        dataObject.put("readOnly", false);
        JSONArray bosArray=new JSONArray();
        bosArray.add(dataObject);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("bos", bosArray);
        
		startCmd.setJsonData(jsonObject.toString());
		BpmInst inst= bpmInstManager.doStartProcess(startCmd);
        return new JsonResult(true,"正在签收！");
    }
    //根据groupId查找收发文,没找到就查找parentGroup的收发文直到找不到为止
    public OdDocFlow findOdDocFlow(String osGroupId){
    	OsGroup osGroup= osGroupManager.get(osGroupId);
    	if(odDocFlowManager.getByGroupId(osGroup.getGroupId())==null){//如果本group没设置收发文
    		String parentId=osGroup.getParentId();
    		
    		OsGroup parentGroup=osGroupManager.get(parentId);
    		if(parentGroup!=null){
    			return findOdDocFlow(parentId);
    			}else{
    			return null;
    		}
    		
    	}else{
    		return odDocFlowManager.getByGroupId(osGroup.getGroupId());
    	}
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
        OdDocRec odDocRec=null;
        if(StringUtils.isNotEmpty(pkId)){
           odDocRec=odDocRecManager.get(pkId);
        }else{
        	odDocRec=new OdDocRec();
        }
        return getPathView(request).addObject("odDocRec",odDocRec);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	OdDocRec odDocRec=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		odDocRec=odDocRecManager.get(pkId);
    		if("true".equals(forCopy)){
    			odDocRec.setRecId(null);
    		}
    	}else{
    		odDocRec=new OdDocRec();
    	}
    	return getPathView(request).addObject("odDocRec",odDocRec);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return odDocRecManager;
	}
	
	@RequestMapping("recDoc")
	@ResponseBody
	public JsonPageResult<OdDocRec> recDoc(HttpServletRequest request,HttpServletResponse response){
		String type=request.getParameter("type");//接收公文
		String userId=ContextUtil.getCurrentUserId();
		OsGroup osGroup=osGroupManager.getMainDeps(userId);
		String depId=(osGroup==null?"0":osGroup.getGroupId());
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("recDepId", depId);
		queryFilter.addFieldParam("recDtype", type);
		List<OdDocRec> odDocRecs=odDocRecManager.getAll(queryFilter);
		JsonPageResult<OdDocRec> jsonPageResult=new JsonPageResult<OdDocRec>(odDocRecs, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
		
	}
	
	

}
