package com.redxun.oa.script;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redxun.bpm.bm.manager.BpmFormInstManager;
import com.redxun.bpm.core.entity.BpmSolution;
import com.redxun.bpm.core.entity.BpmTask;
import com.redxun.bpm.core.manager.BpmInstDataManager;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.bpm.core.manager.BpmInstTmpManager;
import com.redxun.bpm.core.manager.BpmSolutionManager;
import com.redxun.bpm.core.manager.BpmTaskManager;
import com.redxun.core.annotion.cls.ClassDefine;
import com.redxun.core.annotion.cls.MethodDefine;
import com.redxun.core.annotion.cls.ParamDefine;
import com.redxun.core.cache.ICache;
import com.redxun.core.dao.mybatis.CommonDao;
import com.redxun.core.entity.SqlModel;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.script.GroovyEngine;
import com.redxun.core.script.GroovyScript;
import com.redxun.oa.info.dao.InsNewsColumnQueryDao;
import com.redxun.oa.info.entity.InfInnerMsg;
import com.redxun.oa.info.entity.InsColumnDef;
import com.redxun.oa.info.entity.InsColumnEntity;
import com.redxun.oa.info.entity.InsMsgBoxEntity;
import com.redxun.oa.info.entity.InsMsgDef;
import com.redxun.oa.info.entity.InsMsgboxDef;
import com.redxun.oa.info.entity.InsNews;
import com.redxun.oa.info.entity.InsNewsColumn;
import com.redxun.oa.info.entity.InsPortalParams;
import com.redxun.oa.info.manager.InfInboxManager;
import com.redxun.oa.info.manager.InsColumnDefManager;
import com.redxun.oa.info.manager.InsMsgDefManager;
import com.redxun.oa.info.manager.InsMsgboxDefManager;
import com.redxun.oa.info.manager.InsNewsManager;
import com.redxun.oa.mail.entity.InnerMail;
import com.redxun.oa.mail.entity.OutMail;
import com.redxun.oa.mail.manager.InnerMailManager;
import com.redxun.oa.mail.manager.OutMailManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.core.entity.SysBoList;
import com.redxun.sys.core.manager.SysBoListManager;
import com.redxun.sys.db.entity.SysSqlCustomQuery;
import com.redxun.sys.db.manager.SysSqlCustomQueryManager;
import com.redxun.sys.org.dao.OsGroupQueryDao;
import com.redxun.sys.org.dao.OsUserQueryDao;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsRelInstManager;
import com.redxun.sys.org.manager.OsRelTypeManager;
import com.redxun.sys.org.manager.OsUserManager;
/**
 * 流程脚本处理类，放置于脚本运行的环境, 配置@ClassDefine及@MethodDefine目的是
 * 为了可以把系统中自带的API显示出来给配置人员查看及选择使用
 * 
 * @author mansan
 * @Email: chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 *            本源代码受软件著作法保护，请在授权允许范围内使用。
 */
@ClassDefine(title = "流程脚本服务类")
public class PortalScript implements GroovyScript {
	private Log log = LogFactory.getLog(PortalScript.class);
	@Resource
	RuntimeService runtimeService;
	@Resource
	TaskService taskService;
	@Resource
	BpmInstManager bpmInstManager;
	@Resource
	BpmFormInstManager bpmFormInstManager;
	@Resource
	OsGroupManager osGroupManager;
	@Resource
	OsUserManager osUserManager;
	@Resource
	OsRelTypeManager osRelTypeManager;
	@Resource
	BpmInstTmpManager bpmInstTmpManager;
	@Resource
	OsRelInstManager osRelInstManager;
	@Resource
	OsGroupQueryDao groupDao;
	@Resource
	OsUserQueryDao userQueryDao;
	@Resource
	BpmInstDataManager bpmInstDataManager ;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	InsNewsManager insNewsManager;
	@Resource
	InfInboxManager infInboxManager;
	@Resource
	OutMailManager outMailManager;
	@Resource
	InnerMailManager innerMailManager;
	@Resource
	InsMsgboxDefManager insMsgboxDefManager;
	@Resource
	InsMsgDefManager insMsgDefManager;
	@Resource
	GroovyEngine groovyEngine;
	@Resource
	CommonDao commonDao;
	@Resource
	InsColumnDefManager insColumnDefManager;
	@Resource
	BpmSolutionManager bpmSolutionManager;
	@Resource
	SysBoListManager sysBoListManager;
	@Resource
	ICache<SysBoList> iCache;
	@Resource
	SysSqlCustomQueryManager sysSqlCustomQueryManager;
	@Resource
	InsNewsColumnQueryDao insNewsColumnQueryDao;


	@MethodDefine(title = "自定义列表", params = {@ParamDefine(title = "栏目主键", varName = "colId"),@ParamDefine(title = "自定义表单Key", varName = "boKey")})
	public InsColumnEntity getSysBoList(String colId, String bokey){
		InsColumnDef col = insColumnDefManager.get(colId);
		SysSqlCustomQuery sqlCustomQuery=sysSqlCustomQueryManager.getByKey(bokey);
		Page page=new Page(0,Integer.parseInt(sqlCustomQuery.getPageSize()));
		Map<String,Object> params=new HashMap<String, Object>();
		List result=new ArrayList();
		try {
			result = sysSqlCustomQueryManager.getData(sqlCustomQuery, params,page);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String url = col.getDataUrl();
		InsColumnEntity<HashMap<String,String>> entity = new InsColumnEntity<HashMap<String,String>>(col.getName(), url, result);
		return entity;
	}
	
	/**
	 * 门户待办栏目数据
	 * @return
	 */
	@MethodDefine(title = "我的待办栏目", params = {@ParamDefine(title = "栏目主键", varName = "colId")})
	public InsColumnEntity getPortalBpmTask(String colId) {
		String userId = ContextUtil.getCurrentUserId();
		String tenantId = ContextUtil.getCurrentTenantId();
		InsPortalParams params =new InsPortalParams();
		InsColumnDef col = insColumnDefManager.get(colId);
		params.setUserId(userId);
		params.setTenantId(tenantId);
		ArrayList<BpmTask> bpmtasks = (ArrayList<BpmTask>) bpmTaskManager.getPortalMyTasks(params);
		InsColumnEntity<BpmTask> entity = new InsColumnEntity<BpmTask>(col.getName(), "/bpm/core/bpmTask/myList.do", bpmtasks);
		return entity;
	}
	
	/**
	 * 我的流程草稿栏目数据
	 * @return
	 */
	@MethodDefine(title = "我的流程草稿", params = {@ParamDefine(title = "栏目主键", varName = "colId")})
	public InsColumnEntity getPortalMyDrafts(String colId) {
		String userId = ContextUtil.getCurrentUserId();
		String tenantId = ContextUtil.getCurrentTenantId();
		InsPortalParams params =new InsPortalParams();
		InsColumnDef col = insColumnDefManager.get(colId);
		params.setUserId(userId);
		params.setTenantId(tenantId);
		QueryFilter queryFilter= new QueryFilter();
		ArrayList<BpmSolution> bpmSolutions=(ArrayList<BpmSolution>)bpmSolutionManager.getSolutions(queryFilter,false);
		InsColumnEntity<BpmSolution> entity = new InsColumnEntity<BpmSolution>(col.getName(), "/oa/personal/bpmSolApply/myList.do", bpmSolutions);
		return entity;
	}
	
	/**
	 * 我的流程方案栏目数据
	 * @return
	 */
	@MethodDefine(title = "我的流程方案", params = {@ParamDefine(title = "栏目主键", varName = "colId")})
	public InsColumnEntity getPortalMySolList(String colId) {
		String userId = ContextUtil.getCurrentUserId();
		String tenantId = ContextUtil.getCurrentTenantId();
		InsPortalParams params =new InsPortalParams();
		InsColumnDef col = insColumnDefManager.get(colId);
		params.setUserId(userId);
		params.setTenantId(tenantId);
		QueryFilter queryFilter= new QueryFilter();
		ArrayList<BpmSolution> bpmSolutions=(ArrayList<BpmSolution>)bpmSolutionManager.getSolutions(queryFilter,false);
		InsColumnEntity<BpmSolution> entity = new InsColumnEntity<BpmSolution>(col.getName(), "/oa/personal/bpmSolApply/myList.do", bpmSolutions);
		return entity;
	}
	
	/**
	 * 门户新闻公告栏目数据
	 * @return
	 */
	@MethodDefine(title = "新闻公告栏目", params = {@ParamDefine(title = "栏目主键", varName = "colId")})
	public InsColumnEntity getPortalNews(String colId) {
		QueryFilter queryFilter = new QueryFilter();
		queryFilter.addFieldParam("COLUMN_ID_", colId);
	
		Page page=new Page(0,5);
		queryFilter.setPage(page);
		InsNewsColumn col= insNewsColumnQueryDao.get(colId);
		ArrayList<InsNews> insNews = (ArrayList<InsNews>) insNewsManager.getPortalNews(queryFilter);
		InsColumnEntity<InsNews> entity = new InsColumnEntity<InsNews>(col.getName(), "/oa/info/insNews/byColId.do?colId="+colId+"&portal=YES", insNews);
		return entity;
	}
	
	/**
	 * 门户我的消息数据
	 * @return
	 */
	@MethodDefine(title = "我的消息", params = {@ParamDefine(title = "栏目主键", varName = "colId")})
	public InsColumnEntity getPortalMsg(String colId) {
		String userId = ContextUtil.getCurrentUserId();
		String tenantId = ContextUtil.getCurrentTenantId();
		InsColumnDef col = insColumnDefManager.get(colId);
		InsPortalParams params =new InsPortalParams();
		params.setUserId(userId);
		params.setTenantId(tenantId);
		ArrayList<InfInnerMsg> insMsg = (ArrayList<InfInnerMsg>) infInboxManager.getPortalMsg(params);
		InsColumnEntity<InfInnerMsg> entity = new InsColumnEntity<InfInnerMsg>(col.getName(), "/oa/info/infInbox/receive.do", insMsg);
		return entity;
	}
	
	/**
	 * 门户外部邮件数据
	 * @return
	 */
	@MethodDefine(title = "外部邮件", params = {@ParamDefine(title = "栏目主键", varName = "colId")})
	public InsColumnEntity getPortalOutEmail(String colId) {
		String userId = ContextUtil.getCurrentUserId();
		String tenantId = ContextUtil.getCurrentTenantId();
		InsColumnDef col = insColumnDefManager.get(colId);
		InsPortalParams params =new InsPortalParams();
		params.setUserId(userId);
		params.setTenantId(tenantId);
		ArrayList<OutMail> outEmail = (ArrayList<OutMail>)outMailManager.getPortalOutMail(params);
		InsColumnEntity<OutMail> entity = new InsColumnEntity<OutMail>(col.getName(), "/oa/mail/mailConfig/getAllConfig.do", outEmail);
		return entity;
	}
	
	/**
	 * 门户内部邮件数据
	 * @return
	 */
	public InsColumnEntity getPortalInEmail(String colId) {
		String userId = ContextUtil.getCurrentUserId();
		String tenantId = ContextUtil.getCurrentTenantId();
		InsColumnDef col = insColumnDefManager.get(colId);
		InsPortalParams params =new InsPortalParams();
		params.setUserId(userId);
		params.setTenantId(tenantId);
		ArrayList<InnerMail> inMail = (ArrayList<InnerMail>)innerMailManager.getPortalInMail(params);
		InsColumnEntity<InnerMail> entity = new InsColumnEntity<InnerMail>(col.getName(), "/oa/mail/mailBox/list.do", inMail);
		return entity;
	}
	
	@MethodDefine(title = "消息盒子", params = {@ParamDefine(title = "消息盒子Key（请修改为具体消息盒子的Key）", varName = "boxKey")})
	public InsColumnEntity getPortalMsgBox(String boxKey){
		ArrayList<InsMsgBoxEntity> boxEntity = new ArrayList<InsMsgBoxEntity>();
		InsMsgboxDef boxDef = insMsgboxDefManager.getByKey(boxKey);
		if(boxDef==null){
			return new InsColumnEntity();
		}
		List<InsMsgDef> msgDef = insMsgDefManager.getByMsgBoxId(boxDef.getBoxId());
		
		for(InsMsgDef msg:msgDef){
			InsMsgBoxEntity e = new InsMsgBoxEntity();
			if("sql".equals(msg.getType())){
				int count = getCountByType(msg.getMsgId(),"sql");
				e.setCount(count);
			}else if("func".equals(msg.getType())){
				int count = getCountByType(msg.getMsgId(),"func");
				e.setCount(count);
			}
			
			e.setIcon(msg.getIcon());
			e.setTitle(msg.getContent());
			e.setUrl(msg.getUrl());
			boxEntity.add(e);
		}
		InsColumnEntity<InsMsgBoxEntity> entity = new InsColumnEntity<InsMsgBoxEntity>("消息盒子", "", boxEntity);
		return entity;
	}
	
	/**
	 * 执行消息的sql脚本
	 * @param msgId
	 * @return
	 */
    public int getCountByType(String msgId,String type) {
    	InsMsgDef msgBox = insMsgDefManager.get(msgId);
    	if(type.equals("sql")){	    	
			String sql=(String)groovyEngine.executeScripts(msgBox.getSqlFunc(), new HashMap<String, Object>());
			SqlModel model=new SqlModel(sql);//获取SQL
			Integer i  =Integer.parseInt(commonDao.queryOne(model).toString());
			return i;
    	}else if(type.equals("func")){
    		int i = (Integer) groovyEngine.executeScripts(msgBox.getSqlFunc(),new HashMap<String, Object>());
    		return i;
    	}
    	
    	return 0;
    }
    
	/**
	 * 我的流程方案栏目数据
	 * @return
	 */
	@MethodDefine(title = "我的流程方案", params = {@ParamDefine(title = "栏目主键", varName = "colId")})
	public int getCountMySolList() {
		String userId = ContextUtil.getCurrentUserId();
		String tenantId = ContextUtil.getCurrentTenantId();
		QueryFilter queryFilter= new QueryFilter();
		ArrayList<BpmSolution> bpmSolutions=(ArrayList<BpmSolution>)bpmSolutionManager.getSolutions(queryFilter,false);
		return bpmSolutions.size();
	}
	
	private SysBoList getBoList(String boKey){
		SysBoList sysBoList=(SysBoList)iCache.getByKey(ICache.SYS_BO_LIST_CACHE+boKey +"_"+ ContextUtil.getTenant().getDomain());
		if(sysBoList==null){
			sysBoList =sysBoListManager.getByKey(boKey, ContextUtil.getCurrentTenantId());
			//放置于缓存
			iCache.add(ICache.SYS_BO_LIST_CACHE+boKey +"_"+ ContextUtil.getTenant().getDomain(), sysBoList);
		}
		return sysBoList;
	}


}
