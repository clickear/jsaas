package com.redxun.offdoc.core.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.ProcessStartCmd;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.bo.entity.SysBoDef;
import com.redxun.sys.bo.manager.SysBoDefManager;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.offdoc.core.entity.OdDocFlow;
import com.redxun.offdoc.core.entity.OdDocRec;
import com.redxun.offdoc.core.entity.OdDocument;
import com.redxun.offdoc.core.manager.OdDocFlowManager;
import com.redxun.offdoc.core.manager.OdDocRecManager;
import com.redxun.offdoc.core.manager.OdDocumentManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * [OdDocument]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/offdoc/core/odDocument/")
public class OdDocumentFormController extends BaseFormController {

    @Resource
    private OdDocumentManager odDocumentManager;
    @Resource
    private SysFileManager sysFileManager;
    @Resource
    private OdDocRecManager odDocRecManager;
    @Resource
    private OsGroupManager osGroupManager;
    @Resource
    private OdDocFlowManager odDocFlowManager;
    @Resource
    private BpmInstManager bpmInstManager;
    @Resource
    private SysBoDefManager sysBoDefManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("odDocument")
    public OdDocument processForm(HttpServletRequest request) {
        String docId = request.getParameter("docId");
        OdDocument odDocument = null;
        if (StringUtils.isNotEmpty(docId)) {
            odDocument = odDocumentManager.get(docId);
        } else {
            odDocument = new OdDocument();
        }

        return odDocument;
    }
    /**
     * 保存实体数据
     * @param request
     * @param odDocument
     * @param result
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("odDocument") @Valid OdDocument odDocument, BindingResult result) throws Exception {
		String attach=request.getParameter("ffileNames");
		String draft=request.getParameter("draft");
		String docPath=request.getParameter("docPath");
		String  odDocFlowType=request.getParameter("odDocFlow");//收发文
		SysBoDef sysBoDef=sysBoDefManager.getByAlias("fwbd");
		
		StringBuffer sbName=new StringBuffer("");
		StringBuffer sbId=new StringBuffer("");
		JSONArray attachArray=new JSONArray();
		if(StringUtils.isNotBlank(attach)){
			attachArray=JSONArray.fromObject(attach);
		}
		for(int i=0;i<attachArray.size();i++){
			JSONObject attachObj=attachArray.getJSONObject(i);
			sbId.append(attachObj.getString("fileId")+",");
			sbName.append(attachObj.getString("fileName")+",");
		}
		if(sbName.length()>0){
			sbName.deleteCharAt(sbName.length()-1);
			sbId.deleteCharAt(sbId.length()-1);
		}
		/*
		if(StringUtils.isNotEmpty(attach)){//attach字段不为空则逗号切割它
			   String[] fileIds=attach.split("[,]");
			   for(String fileId:fileIds){//再把切出来的字段赋给fileId，通过它来查找出所有对应的sysFile	
				   SysFile sysFile=sysFileManager.get(fileId);
				   sbName.append(sysFile.getFileName());
				   sbName.append(",");
			   }
			   sbName.deleteCharAt(sbName.length()-1);
		 }
		String attachName=sbName.toString();//附件名
*/		
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(odDocument.getDocId())) {
            odDocument.setDocId(idGenerator.getSID());
            odDocument.setFileIds(sbId.toString());
            odDocument.setFileNames(sbName.toString());
            if("YES".equals(draft)){
            	odDocument.setStatus("0");
            }else{
            	odDocument.setStatus("1");
            }
            
            OsGroup mainGroup=osGroupManager.getMainDeps(ContextUtil.getCurrentUserId());
            if(mainGroup!=null){
            	odDocument.setIssueDepId(mainGroup.getGroupId());//发文主部门
            }
            odDocument.setIssuedDate(new Date());
    		odDocument.setIssueUsrId(ContextUtil.getCurrentUserId());
    		if(StringUtils.isBlank(odDocument.getBodyFilePath())){
    			odDocument.setBodyFilePath(docPath);
    		}
    		odDocumentManager.create(odDocument);
    		if("SEND".equals(odDocFlowType)){
    		        String userId=ContextUtil.getCurrentUserId();
    		        OsGroup osGroup=osGroupManager.getMainDeps(userId);
    		        JSONObject jsonObject2=JSONObject.fromObject(odDocument);
    		        jsonObject2.put("bo_Def_Id_", sysBoDef.getId());
    		        
    		        jsonObject2.put("fileIds", attachArray);
    		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		        jsonObject2.put("issuedDate", sdf.format(new Date()));
    		        jsonObject2.put("sysTree", odDocument.getSysTree().getTreeId());
    		        jsonObject2.put("issueDepId", osGroup.getName());
    		        jsonObject2.put("fromHtml", "YES");//区别是从流程直接启动的还是从发文稿纸启动的流程
    				String mainDepId=jsonObject2.getString("mainDepId");
    				String joinDepIds=jsonObject2.getString("joinDepIds");
    				String ccDepId=jsonObject2.getString("ccDepId");
    				
    				jsonObject2.put("mainDepId_name", osGroupManager.getNameByGroupId(mainDepId));
    				jsonObject2.put("joinDepIds_name", osGroupManager.getNameByGroupId(joinDepIds));
    				jsonObject2.put("ccDepId_name", osGroupManager.getNameByGroupId(ccDepId));
    		        String[] fileIdArray=odDocument.getBodyFilePath().split("/");
    		        String fileId=fileIdArray[fileIdArray.length-1].split(".doc")[0];
    		        SysFile fileObj=sysFileManager.get(fileId);
    		        JSONObject file=new JSONObject();
    		        file.put("fileId", fileId);
    		        file.put("fileName", fileObj.getFileName());
    		        file.put("bytes", fileObj.getTotalBytes());
    		        JSONArray fileArray=new JSONArray();
    		        fileArray.add(file);
    		        jsonObject2.put("contextFileIds", fileArray);
    		        ProcessStartCmd startCmd=new ProcessStartCmd();
    		        OdDocFlow odDocFlow=findOdDocFlow(osGroup.getGroupId());
    		        if(odDocFlow!=null){
    		        	startCmd.setSolId(odDocFlow.getSendSolId());
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
    				odDocument.setBpmInstId(inst.getActInstId());
    				odDocumentManager.update(odDocument);
    		}
            
            msg = getMessage("odDocument.created", new Object[]{odDocument.getSubject()}, "公文成功创建!");
        } else {
        	odDocument.setFileIds(sbId.toString());
            odDocument.setFileNames(sbName.toString());
            if("YES".equals(draft)){
            	odDocument.setStatus("0");
            }else{
            	odDocument.setStatus("1");
            	
            }
            odDocument.setBodyFilePath(docPath);
            odDocumentManager.update(odDocument);
            if("SEND".equals(odDocFlowType)){
		        String userId=ContextUtil.getCurrentUserId();
		        OsGroup osGroup=osGroupManager.getMainDeps(userId);
		        JSONObject jsonObject2=JSONObject.fromObject(odDocument);
		        jsonObject2.put("fileIds", attachArray);
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        jsonObject2.put("issuedDate", sdf.format(new Date()));
		        jsonObject2.put("sysTree", odDocument.getSysTree().getTreeId());
		        jsonObject2.put("issueDepId", osGroup.getName());
		        String[] fileIdArray=odDocument.getBodyFilePath().split("/");
		        String fileId=fileIdArray[fileIdArray.length-1].split(".doc")[0];
		        SysFile fileObj=sysFileManager.get(fileId);
		        JSONObject file=new JSONObject();
		        file.put("fileId", fileId);
		        file.put("fileName", fileObj.getFileName());
		        file.put("bytes", fileObj.getTotalBytes());
		        JSONArray fileArray=new JSONArray();
		        fileArray.add(file);
		        jsonObject2.put("contextFileIds", fileArray);
		        String mainDepId=jsonObject2.getString("mainDepId");
				String joinDepIds=jsonObject2.getString("joinDepIds");
				String ccDepId=jsonObject2.getString("ccDepId");
				
				jsonObject2.put("mainDepId_name", osGroupManager.getNameByGroupId(mainDepId));
				jsonObject2.put("joinDepIds_name", osGroupManager.getNameByGroupId(joinDepIds));
				jsonObject2.put("ccDepId_name", osGroupManager.getNameByGroupId(ccDepId));
		        ProcessStartCmd startCmd=new ProcessStartCmd();
		        OdDocFlow odDocFlow=findOdDocFlow(osGroup.getGroupId());
		        if(odDocFlow!=null){
		        	startCmd.setSolId(odDocFlow.getSendSolId());
		        }else{
		        	return new JsonResult(false,"祖上未配置收文流程！");
		        }
		        jsonObject2.put("fromHtml", "YES");//区别是从流程直接启动的还是从发文稿纸启动的流程
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
		}
           
            msg = getMessage("odDocument.updated", new Object[]{odDocument.getSubject()}, "公文成功更新!");
        }
        return new JsonResult(true, msg);
    }
    
    
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
    
    
}

