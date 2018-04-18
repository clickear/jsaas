package com.redxun.offdoc.core.manager;

import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.redxun.bpm.activiti.handler.ProcessEndHandler;
import com.redxun.bpm.activiti.handler.ProcessStartAfterHandler;
import com.redxun.bpm.activiti.handler.TaskAfterHandler;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.IExecutionCmd;
import com.redxun.bpm.core.entity.ProcessStartCmd;
import com.redxun.bpm.core.entity.config.ProcessConfig;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.seq.IdGenerator;
import com.redxun.core.util.StringUtil;
import com.redxun.offdoc.core.dao.OdDocumentDao;
import com.redxun.offdoc.core.entity.OdDocRec;
import com.redxun.offdoc.core.entity.OdDocument;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;
@Service
public class OdDocumentSolutionManager   implements ProcessStartAfterHandler,TaskAfterHandler,ProcessEndHandler{
	@Resource
	private OdDocumentDao odDocumentDao;
	@Resource
	private IdGenerator idGenerator;
	@Resource
	private OdDocRecManager odDocRecManager;
	@Resource
	private OdDocumentManager odDocumentManager;
	@Resource
	private SysFileManager sysFileManager;

	@Override
	public void endHandle(BpmInst bpmInst) {
		String docId=bpmInst.getBusKey();
		String userId=ContextUtil.getCurrentUserId();
		OdDocument odDocument=odDocumentManager.get(docId);
		if(!docId.contains("!")){
			OdDocRec odDocRec=odDocRecManager.getRecByDocId(docId,"INCOMING");
			odDocRec.setSignStatus("SIGNED");
			odDocRec.setSignTime(new Date());
			odDocRecManager.saveOrUpdate(odDocRec);
			OdDocument odDocumentRec=new OdDocument();
			odDocumentRec.setDocId(idGenerator.getSID());
			odDocumentRec.setArchType((short) 1);
			odDocumentRec.setAssDepId(odDocument.getAssDepId());
			odDocumentRec.setBodyFilePath(odDocument.getBodyFilePath());
			odDocumentRec.setBpmInstId(bpmInst.getActInstId());
			odDocumentRec.setBpmSolId(odDocument.getBpmSolId());
			odDocumentRec.setCcDepId(odDocument.getCcDepId());
			odDocumentRec.setCNType(odDocument.getCNType());
			odDocumentRec.setCreateBy(userId);
			odDocumentRec.setCreateTime(new Date());
			odDocumentRec.setDocType(odDocument.getDocType());
			odDocumentRec.setFileIds(odDocument.getFileIds());
			odDocumentRec.setFileNames(odDocument.getFileNames());
			odDocumentRec.setIsJoinIssue(odDocument.getIsJoinIssue());
			odDocumentRec.setIssuedDate(odDocument.getIssuedDate());
			odDocumentRec.setIssueDepId(odDocument.getIssueDepId());
			odDocumentRec.setIssueNo(odDocument.getIssueNo());
			odDocumentRec.setIssueUsrId(odDocument.getIssueNo());
			odDocumentRec.setJoinDepIds(odDocument.getJoinDepIds());
			odDocumentRec.setKeywords(odDocument.getKeywords());
			odDocumentRec.setMainDepId(odDocument.getMainDepId());
			odDocumentRec.setOrgArchId(odDocument.getDocId());
			odDocumentRec.setPrintCount(odDocument.getPrintCount());
			odDocumentRec.setPrivacyLevel(odDocument.getPrivacyLevel());
			odDocumentRec.setSecrecyTerm(odDocument.getSecrecyTerm());
			odDocumentRec.setSelfNo(idGenerator.getSID());
			odDocumentRec.setStatus("2");
			odDocumentRec.setSubject(odDocument.getSubject());
			odDocumentRec.setSummary(odDocument.getSummary());
			odDocumentRec.setTakeDepId(odDocument.getTakeDepId());
			odDocumentRec.setUrgentLevel(odDocument.getUrgentLevel());
			odDocumentManager.create(odDocumentRec);
		}else{//有!则是直接启动的流程
			String documentId=docId.split("!")[0];
			
		}
		
		
	}

	@Override
	public void taskAfterHandle(IExecutionCmd cmd, String nodeId, String busKey) {
		
		
	}

//	@Override
	public String processStartAfterHandle(String json, String actInstId) {
		
		JSONObject jsonObj=JSONObject.fromObject(json);
		JSONArray bos=jsonObj.getJSONArray("bos");
		JSONObject dataObj =bos.getJSONObject(0);
		JSONObject jsonObject=dataObj.getJSONObject("data");
		String docId=(String) jsonObject.get("docId");
		if(StringUtils.isBlank(docId)){
			String solId=(String) jsonObject.get("solId");
			String contextFileIds=(String) jsonObject.get("contextFileIds");
			String ccDepId=(String) jsonObject.get("ccDepId");
			String cNType = (String) jsonObject.get("cNType");
			String docType = (String) jsonObject.get("docType");
			String fileIds=(String) jsonObject.get("fileIds");
			String fileNames=(String) jsonObject.get("fileNames");
			String issueNo=(String) jsonObject.get("issueNo");
			String keywords=(String) jsonObject.get("keywords");
			String mainDepId=(String) jsonObject.get("mainDepId");
			Integer printCount=  Integer.parseInt((String) jsonObject.get("printCount"));
			String privacyLevel=(String) jsonObject.get("privacyLevel");
			Integer secrecyTerm= Integer.parseInt((String) jsonObject.get("secrecyTerm"));
			String subject=(String) jsonObject.get("subject");
			String summary=(String) jsonObject.get("summary");
			String urgentLevel=(String) jsonObject.get("urgentLevel");
			
			String userId=ContextUtil.getCurrentUserId();
			OdDocument odDocument=new OdDocument();
			odDocument.setArchType((short) 1);
			SysFile sysFile=sysFileManager.get(contextFileIds);
			odDocument.setBodyFilePath(sysFile.getPath());
			odDocument.setBpmInstId(actInstId);
			odDocument.setBpmSolId(solId);
			odDocument.setCcDepId(ccDepId);
			odDocument.setCNType(cNType);
			odDocument.setCreateBy(userId);
			odDocument.setDocType(docType);
			odDocument.setFileIds(fileIds);
			odDocument.setFileNames(fileNames);
			odDocument.setIssuedDate(new Date());
			odDocument.setIssueNo(issueNo);
			odDocument.setIssueUsrId(userId);
			odDocument.setKeywords(keywords);
			odDocument.setMainDepId(mainDepId);
			odDocument.setPrintCount(printCount);
			odDocument.setPrivacyLevel(privacyLevel);
			odDocument.setSecrecyTerm(secrecyTerm);
			odDocument.setSelfNo(idGenerator.getSID());
			odDocument.setStatus("1");//发文状态
			odDocument.setSubject(subject);
			odDocument.setSummary(summary);
			odDocument.setTenantId(ContextUtil.getCurrentTenantId());
			odDocument.setUrgentLevel(urgentLevel);
			odDocument.setDocId(idGenerator.getSID());
			odDocumentManager.create(odDocument);
			
			
			OdDocRec odDocRec=new OdDocRec();
			odDocRec.setCreateBy(userId);
			odDocRec.setCreateTime(new Date());
			odDocRec.setIsRead("NO");
			odDocRec.setOdDocument(odDocument);
			odDocRec.setRecDepId(odDocument.getMainDepId());
			odDocRec.setRecId(idGenerator.getSID());
			odDocRec.setSignStatus("SIGNING");
			odDocRecManager.create(odDocRec);
			return odDocument.getDocId();//+"!";
		}else{
			return docId;	
		}
		
	}
	
	public OdDocument getByActInstId(String actInstId){
		return odDocumentDao.getByActInstId(actInstId);
	}

	public String processStartAfterHandle(ProcessConfig processConfig, ProcessStartCmd cmd, BpmInst bpmInst) {
		// TODO Auto-generated method stub
		return null;
	}

}
