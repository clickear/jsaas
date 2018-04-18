package com.redxun.offdoc.core.manager;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.redxun.bpm.activiti.handler.ProcessEndHandler;
import com.redxun.bpm.activiti.handler.ProcessStartAfterHandler;
import com.redxun.bpm.activiti.handler.TaskAfterHandler;
import com.redxun.bpm.activiti.util.ProcessHandleHelper;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.IExecutionCmd;
import com.redxun.bpm.core.entity.ProcessStartCmd;
import com.redxun.bpm.core.entity.config.ProcessConfig;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.core.dao.IDao;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.seq.IdGenerator;
import com.redxun.offdoc.core.dao.OdDocumentDao;
import com.redxun.offdoc.core.entity.OdDocRec;
import com.redxun.offdoc.core.entity.OdDocument;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.org.manager.OsGroupManager;
/**
 * <pre> 
 * 描述：OdDocument业务服务类
 * 作者：陈茂昌
 * 日期:2016-3-8-上午16:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class OdDocumentManager extends BaseManager<OdDocument> implements ProcessStartAfterHandler,TaskAfterHandler,ProcessEndHandler{
	@Resource
	private OdDocumentDao odDocumentDao;
	@Resource
	private BpmInstManager bpmInstManager;
	@Resource
	private OsGroupManager osGroupManager;
	@Resource
	private SysFileManager sysFileManager;
	@Resource
	private SysTreeManager sysTreeManager;
	@Resource
	private OdDocRecManager odDocRecManager;
	@Resource
	private IdGenerator idGenerator;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return odDocumentDao;
	}
	@Override
	public void endHandle(BpmInst bpmInst) {
		String actInstId=bpmInst.getActInstId();
		OdDocument odDocument=getByActInstId(actInstId);
		String mainDepId=odDocument.getMainDepId();
		String[] mainDepIds=mainDepId.split(",");
		for (String string : mainDepIds) {
			OdDocRec odDocRec=new OdDocRec();
			odDocRec.setOdDocument(odDocument);
			odDocRec.setIsRead("NO");
			odDocRec.setRecDepId(string);
			odDocRec.setRecDtype("INCOMING");
			odDocRec.setSignStatus("NOTSIGN");
			odDocRec.setRecId(idGenerator.getSID());
			odDocRecManager.create(odDocRec);//主送部门
		}
		String ccDepId=odDocument.getCcDepId();
		if(StringUtils.isNotBlank(ccDepId)){//如果有抄送部门
			String[] depIds=ccDepId.split(",");
			for (String string : depIds) {
				OdDocRec ccOdDoc=new OdDocRec();
				ccOdDoc.setIsRead("NO");
				ccOdDoc.setOdDocument(odDocument);
				ccOdDoc.setRecDepId(string);
				ccOdDoc.setRecDtype("COPYTO");
				ccOdDoc.setSignStatus("NOTSIGN");
				ccOdDoc.setRecId(idGenerator.getSID());
				odDocRecManager.create(ccOdDoc);
			}
		}
	}
	@Override
	public void taskAfterHandle(IExecutionCmd cmd, String nodeId, String busKey) {
		System.out.println(cmd);
	}
//	@Override
	public String processStartAfterHandle(String json, String actInstId) {
		ProcessStartCmd cmd= (ProcessStartCmd) ProcessHandleHelper.getProcessCmd();
		String solId=cmd.getSolId();
		
		JSONObject jsonObject=JSONObject.fromObject(json);
		JSONArray bos=jsonObject.getJSONArray("bos");
		JSONObject jsonObj=bos.getJSONObject(0).getJSONObject("data");
		OdDocument odDocument=new OdDocument();
		String fromHtml=(jsonObject.get("fromHtml")==null?"":jsonObject.get("fromHtml").toString());
		if("YES".equals(fromHtml)){
			odDocument=get((String) jsonObject.get("docId"));
		}else{
			
			
			String treeId=jsonObj.getString("sysTree");
			SysTree sysTree=sysTreeManager.get(treeId);
			odDocument.setSysTree(sysTree);
			odDocument.setDocId(idGenerator.getSID());
			
			
			
			
			/*String treeId=(String) jsonObject.get("sysTree");
			SysTree sysTree=sysTreeManager.get(treeId);
			odDocument.setSysTree(sysTree);
			odDocument.setDocId(idGenerator.getSID());*/
			}
		
			
		
		
		
		
			odDocument.setArchType((short) 0);
			odDocument.setIssueNo(jsonObj.getString("issueNo"));
			odDocument.setIssuedDate(new Date());
			odDocument.setBpmSolId(solId);
			odDocument.setIssueDepId(osGroupManager.getMainDeps(ContextUtil.getCurrentUserId()).getGroupId());
			odDocument.setJoinDepIds(jsonObj.getString("joinDepIds"));
			if(StringUtils.isNotBlank(odDocument.getJoinDepIds())){
				odDocument.setIsJoinIssue("YES");
				}else{
				odDocument.setIsJoinIssue("NO");	
				}
			odDocument.setMainDepId(jsonObj.getString("mainDepId"));
			odDocument.setCcDepId(jsonObj.getString("ccDepId"));
			odDocument.setSubject(jsonObj.getString("subject"));
			odDocument.setPrivacyLevel(jsonObj.getString("privacyLevel"));
			odDocument.setSecrecyTerm(JSONUtil.getInt(jsonObj, "secrecyTerm", 0));
			odDocument.setPrintCount(JSONUtil.getInt(jsonObj, "printCount", 0));
			odDocument.setKeywords(jsonObj.getString("keywords"));
			odDocument.setUrgentLevel(jsonObj.getString("urgentLevel"));
			odDocument.setSummary(jsonObj.getString("summary"));
			Object object=jsonObj.get("fileIds");
			
			
			
			if(!"".equals(object)){
				JSONArray fileArray=jsonObj.getJSONArray("fileIds");//.getJSONObject(0);
				StringBuilder fileId=new StringBuilder("");
				StringBuilder fileName=new StringBuilder("");
				for(int i=0;i<fileArray.size();i++){
					JSONObject file=fileArray.getJSONObject(i);
					fileId.append(file.getString("fileId"));
					fileId.append(",");
					fileName.append(file.getString("fileName"));
					fileName.append(",");
				}
				if(fileId.length()>0){
					fileId.deleteCharAt(fileId.length()-1);
					fileName.deleteCharAt(fileName.length()-1);
				}
				odDocument.setFileIds(fileId.toString());
				odDocument.setFileNames(fileId.toString());
			}
			
			
			odDocument.setBpmInstId(actInstId);
			if(StringUtils.isNotBlank(jsonObj.getString("contextFileIds"))){
				JSONObject contextFileIds=jsonObj.getJSONArray("contextFileIds").getJSONObject(0);
			SysFile sysFile=sysFileManager.get(contextFileIds.getString("fileId"));
			odDocument.setBodyFilePath(sysFile.getPath());
			}
			odDocument.setIssueUsrId(ContextUtil.getCurrentUserId());
			odDocument.setDocType(jsonObj.getString("docType"));
			odDocument.setStatus("1");
			odDocument.setIssuedDate(new Date());
			saveOrUpdate(odDocument);
		
		
		return odDocument.getDocId();
	}
	
	public OdDocument getByActInstId(String actInstId){
		return odDocumentDao.getByActInstId(actInstId);
	}
	public String processStartAfterHandle(ProcessConfig processConfig, ProcessStartCmd cmd, BpmInst bpmInst) {
		// TODO Auto-generated method stub
		return null;
	}
}