package com.redxun.mobile.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RuntimeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.bpm.bm.manager.BpmFormInstManager;
import com.redxun.bpm.core.dao.BpmNodeJumpQueryDao;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.BpmInstCc;
import com.redxun.bpm.core.entity.BpmMessageBoard;
import com.redxun.bpm.core.entity.BpmNodeJump;
import com.redxun.bpm.core.entity.BpmSolution;
import com.redxun.bpm.core.entity.BpmTask;
import com.redxun.bpm.core.entity.config.DestNodeUsers;
import com.redxun.bpm.core.entity.config.UserTaskConfig;
import com.redxun.bpm.core.manager.BpmFormRightManager;
import com.redxun.bpm.core.manager.BpmInstCcManager;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.bpm.core.manager.BpmMessageBoardManager;
import com.redxun.bpm.core.manager.BpmNodeJumpManager;
import com.redxun.bpm.core.manager.BpmNodeSetManager;
import com.redxun.bpm.core.manager.BpmSolFvManager;
import com.redxun.bpm.core.manager.BpmSolutionManager;
import com.redxun.bpm.core.manager.BpmTaskManager;
import com.redxun.bpm.core.manager.IDataSettingHandler;
import com.redxun.bpm.form.dao.BpmMobileFormDao;
import com.redxun.bpm.form.entity.BpmFormView;
import com.redxun.bpm.form.entity.BpmMobileForm;
import com.redxun.bpm.form.entity.FormModel;
import com.redxun.bpm.form.entity.FormViewRight;
import com.redxun.bpm.form.impl.formhandler.FormUtil;
import com.redxun.bpm.form.manager.BpmFormViewManager;
import com.redxun.bpm.form.manager.BpmMobileFormManager;
import com.redxun.bpm.form.manager.FormViewRightManager;
import com.redxun.core.json.JsonResult;
import com.redxun.core.query.IPage;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.SortParam;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.core.seq.IdGenerator;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.DateUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.mobile.model.ListModel;
import com.redxun.mobile.util.CommonUtil;
import com.redxun.oa.info.entity.InfInnerMsg;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.org.api.context.ProfileUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeManager;

@Controller
@RequestMapping("/mobile/bpm/")
public class BpmController {
	
	@Resource
	private BpmTaskManager bpmTaskManager;
	@Resource
	private BpmSolFvManager bpmSolFvManager;
	@Resource
	private BpmMobileFormDao bpmMobileFormDao;
	@Resource
	private BpmInstManager bpmInstManager;
	@Resource
	private BpmFormInstManager bpmFormInstManager;
	@Resource
	private BpmNodeJumpQueryDao bpmNodeJumpQueryDao;
	@Resource
	private BpmNodeJumpManager bpmNodeJumpManager;
	
	@Resource
	private BpmMobileFormManager bpmMobileFormManager;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private FormViewRightManager formViewRightManager;
	@Resource
	private BpmSolutionManager bpmSolutionManager;
	
	@Resource
	InfInnerMsgManager infInnerMsgManager;
	@Resource
	BpmNodeSetManager bpmNodeSetManager;	
	@Resource
	BpmInstCcManager bpmInstCcManager;
	@Resource
	BpmMessageBoardManager bpmMessageBoardManager;
	
	@Resource
	SysTreeManager sysTreeManager;
	@Resource
	BpmFormRightManager bpmFormRightManager;
	
	/**
	 * 获取我的待办任务。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("myTasks")
	@ResponseBody
	public ListModel myTasks(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = QueryFilterBuilder.createQueryFilter(request);
		filter.addFieldParam("enableMobile", 1);
		List<BpmTask> bpmTasks = bpmTaskManager.getByUserId(filter);
		return CommonUtil.getListModel(bpmTasks, filter.getPage().getTotalItems());
	}
	
	@RequestMapping("getCounts")
	@ResponseBody
	public Map<String,Integer> getCounts(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Integer> map=new HashMap<String, Integer>();
		
		Integer task= getTaskCount( request);
		
		map.put("task", task);
		
		Integer attend= getAttendCount( request);
		
		map.put("attend", attend);
		
		map.put("email", 0);
		
		Integer message= getMessageCount( request);
		map.put("message", message);
				
		return map;
	}
	
	private Integer getTaskCount(HttpServletRequest request){
		QueryFilter filter = QueryFilterBuilder.createQueryFilter(request);
		filter.addFieldParam("enableMobile", 1);
		List<BpmTask> bpmTasks = bpmTaskManager.getByUserId(filter);
		return  filter.getPage().getTotalItems();
	}
	
	private Integer getAttendCount(HttpServletRequest request) throws Exception{
		ListModel list= myAttendsData( request);
		return list.getTotal();
	}
	
	private Integer getMessageCount(HttpServletRequest request) throws Exception{
		QueryFilter filter = QueryFilterBuilder.createQueryFilter(request);
		List<InfInnerMsg> list= infInnerMsgManager.getUnreadMsgList(ContextUtil.getCurrentUserId(), filter);
		return list.size();
	}
	
	
	
	
	
	@RequestMapping("myAttendsData")
	@ResponseBody
	public ListModel myAttendsData(HttpServletRequest request) throws Exception{
		String userId=ContextUtil.getCurrentUserId();
		SqlQueryFilter filter=QueryFilterBuilder.createSqlQueryFilter(request);
		
		String subject=request.getParameter("subject");
		String createtime1=request.getParameter("createtime1");
		String createtime2=request.getParameter("createtime2");
		String createtime3=request.getParameter("createtime3");
		String createtime4=request.getParameter("createtime4");
		
		if(StringUtils.isNotBlank(subject)){
			filter.addFieldParam("subject","%"+subject+"%");
		}
		if(StringUtils.isNotBlank(createtime1)){
			filter.addFieldParam("createtime1",DateUtil.parseDate(createtime1));
		}
		if(StringUtils.isNotBlank(createtime2)){
			filter.addFieldParam("createtime2",DateUtil.parseDate(createtime2));
		}
		if(StringUtils.isNotBlank(createtime3)){
			filter.addFieldParam("createtime3",DateUtil.parseDate(createtime3));
		}
		if(StringUtils.isNotBlank(createtime4)){
			filter.addFieldParam("createtime4",DateUtil.parseDate(createtime4));
		}
		filter.addFieldParam("userId", userId);
		filter.addFieldParam("enableMobile", 1);
		
		List<BpmNodeJump> bpmInstList=bpmNodeJumpQueryDao.getMyCheckInst(filter);	
		
		return CommonUtil.getListModel(bpmInstList, filter.getPage().getTotalItems());
	}
	
	/**
	 * 获取表单数据和模版。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getTaskInfo")
	@ResponseBody
	public JSONObject getTaskInfo(HttpServletRequest request, HttpServletResponse response){
		JSONObject rtnJson=new JSONObject();
		
		String taskId = RequestUtil.getString(request, "taskId");
		String versionJson=RequestUtil.getString(request, "versionJson","");
		String tenantId=ContextUtil.getCurrentTenantId();
		
		// 获得任务实例
		BpmTask bpmTask = bpmTaskManager.get(taskId);
		if(bpmTask==null){
			rtnJson.put("result", "taskEnd");
			return rtnJson;
		}
		rtnJson.put("bpmTask", bpmTask);
		BpmInst bpmInst = bpmInstManager.getByActInstId(bpmTask.getProcInstId());
		
		Map<String,Object> vars=runtimeService.getVariables(bpmTask.getProcInstId());
		
		/**
		 * 获取表单。
		 */
		List<FormModel> formModels=getMobileForms(bpmTask.getTaskDefKey(), bpmInst,vars);
		
		if(BeanUtil.isEmpty(formModels)){
			rtnJson.put("result", "noForm");
			return rtnJson;
		}
		/**
		 * 表单权限计算。
		 */
		Map<String, String> rightMap =  getTaskPermission( bpmInst.getSolId(), bpmInst.getActDefId(), bpmTask.getTaskDefKey(), formModels);
		handFormModel(formModels, rightMap, versionJson);
		
		UserTaskConfig taskConfig = bpmNodeSetManager.getTaskConfig(bpmInst.getSolId(), bpmTask.getProcDefId(),bpmTask.getTaskDefKey());

		// 获得任务的下一步的人员映射列表
		List<DestNodeUsers> destNodeUserList = bpmTaskManager.getDestnationUsers(taskId);
		// 是否到达结束事件节点
		boolean isReachEndEvent = bpmTaskManager.isReachEndEvent(destNodeUserList);
		//目标用户列表。
		rtnJson.put("destNodeUserList", destNodeUserList);
		//是否到达结束节点。
		rtnJson.put("isReachEndEvent", isReachEndEvent);
		rtnJson.put("formModels", formModels);
		rtnJson.put("taskConfig",taskConfig);
		rtnJson.put("result", true);
		return rtnJson;
	}

	private void handFormModel(List<FormModel> formModels, Map<String, String> rightMap,
			String versionJson) {
		Map<String,String> versionMap=convertVersionMap(versionJson);
		
		for(FormModel formModel:formModels){
			String boDefId=formModel.getBoDefId();
			String permission= rightMap.get(boDefId);
			if(StringUtil.isEmpty(permission)){
				permission="{}";
			}
			formModel.setPermission(permission);
			//判断是否应该往浏览器端输出表单。
			if(versionMap.containsKey(formModel.getViewId()) ){
				String stamp= versionMap.get(formModel.getViewId());
				if(stamp.equals(formModel.getTimeStamp())){
					formModel.setContent("");
				}
			}
		}
	}
	
	
	
	
	
	private List<FormModel> getStartMobileForms(String solId,String instId){
		BpmSolution bpmSolution=bpmSolutionManager.get(solId);
		IDataSettingHandler settingHanler=AppBeanUtil.getBean(IDataSettingHandler.class);
		List<BpmMobileForm> mobileList=bpmMobileFormManager.getStartFormView(solId, bpmSolution.getActDefId(), instId);
		
		Map<String,Object> vars=new HashMap<String,Object>();
		
		List<FormModel> list=new ArrayList<FormModel>();
		
		for(BpmMobileForm mobileForm:mobileList){
			FormModel formModel=new FormModel();
			formModel.setViewId(mobileForm.getId());
			formModel.setBoDefId(mobileForm.getBoDefId());
			formModel.setContent(mobileForm.getFormHtml());
			formModel.setDescription(mobileForm.getName());
			formModel.setFormKey(mobileForm.getAlias());
			
			JSONObject dataJson= FormUtil.getData(bpmSolution.getDataSaveMode(), mobileForm.getBoDefId(), instId);
			
			String initSetting=mobileForm.getBpmSolFv().getDataConfs();
			if(StringUtil.isNotEmpty(initSetting)){
				//处理数据
				JSONObject settingJson=JSONObject.parseObject(initSetting);
				settingHanler.handSetting(dataJson, mobileForm.getBoDefId(), settingJson, false,vars);
			}
			formModel.setJsonData(dataJson);
			formModel.setTimeStamp( String.valueOf(mobileForm.getUpdateTime().getTime()));
			list.add(formModel);
		}
		
		return list;
		
	} 
	
	
	private List<FormModel> getMobileForms(String nodeId,BpmInst bpmInst,Map<String,Object > vars){
		IDataSettingHandler settingHanler=AppBeanUtil.getBean(IDataSettingHandler.class);
		List<BpmMobileForm> mobileList=bpmMobileFormManager.getFormView(bpmInst.getSolId(), 
				bpmInst.getActDefId(), nodeId, bpmInst.getInstId());
		
		List<FormModel> list=new ArrayList<FormModel>();
		
		for(BpmMobileForm mobileForm:mobileList){
			FormModel formModel=new FormModel();
			formModel.setViewId(mobileForm.getId());
			formModel.setBoDefId(mobileForm.getBoDefId());
			formModel.setContent(mobileForm.getFormHtml());
			formModel.setDescription(mobileForm.getName());
			formModel.setFormKey(mobileForm.getAlias());
			
			JSONObject dataJson= FormUtil.getData(bpmInst,mobileForm.getBoDefId());
			
			String initSetting=mobileForm.getBpmSolFv().getDataConfs();
			if(StringUtil.isNotEmpty(initSetting)){
				//处理数据
				JSONObject settingJson=JSONObject.parseObject(initSetting);
				settingHanler.handSetting(dataJson, mobileForm.getBoDefId(), settingJson, false,vars);
			}
			formModel.setJsonData(dataJson);
			//添加时间戳
			formModel.setTimeStamp( String.valueOf(mobileForm.getUpdateTime().getTime()));
			list.add(formModel);
		}
		
		return list;
		
	} 
	
	/**
	 * 转换表单版本字符串。
	 * @param versionJson
	 * @return
	 */
	private Map<String,String> convertVersionMap(String versionJson){
		Map<String,String> map=new HashMap<String,String>();
		if(StringUtil.isEmpty(versionJson)) return map;
		JSONArray aryJson=JSONArray.parseArray(versionJson);
		//[{id:"",t:""}]
		for(Object obj:aryJson){
			JSONObject json=(JSONObject)obj;
			map.put(json.getString("id"), json.getString("t"));
		}
		return map;
	}
	
	
	@RequestMapping("getInstInfo")
	@ResponseBody
	public JSONObject getInstInfo(HttpServletRequest request, HttpServletResponse response){
		JSONObject rtnJson=new JSONObject();
		String instId = RequestUtil.getString(request, "instId");
		String tenantId=ContextUtil.getCurrentTenantId();
		//[{id:"",t:""}]
		String versionJson=RequestUtil.getString(request, "versionJson");
		
		
		BpmInst bpmInst =null;
		if(StringUtil.isEmpty(instId)){
			String actInstId = RequestUtil.getString(request, "actInstId");
			bpmInst = bpmInstManager.getByActInstId(actInstId);
		}
		else{
			bpmInst = bpmInstManager.get(instId);
		}
		
		
		Map<String,Object> vars=new HashMap<String, Object>();
		if(BpmInst.STATUS_RUNNING.equals(bpmInst.getStatus())){
			vars=runtimeService.getVariables(bpmInst.getActInstId());
		}
		
		List<FormModel> formModels=getMobileForms(BpmFormView.SCOPE_DETAIL, bpmInst,vars);
		
		Map<String, String> rightMap =  getDetailPermission( bpmInst.getSolId(),bpmInst.getActDefId(), formModels);
		
		handFormModel(formModels, rightMap, versionJson);
		
		if(BeanUtil.isEmpty(formModels)){
			rtnJson.put("result", "noForm");
			return rtnJson;
		}
		
		rtnJson.put("formModels", formModels);
		
		rtnJson.put("bpmInst", bpmInst);
		rtnJson.put("result", true);
		
		return rtnJson;
	}
	
	
	@RequestMapping("mySolutions")
	@ResponseBody
	public ListModel mySolutions(HttpServletRequest request,HttpServletResponse response) throws Exception{
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		
		queryFilter.addFieldParam("SUPPORT_MOBILE_", 1);

		List<BpmSolution> bpmSolutions=bpmSolutionManager.getSolutions(queryFilter,false);
		return CommonUtil.getListModel(bpmSolutions, queryFilter.getPage().getTotalItems());
	}
	
	@RequestMapping("startForm")
	@ResponseBody
	public JSONObject startForm(HttpServletRequest request, HttpServletResponse response){
		JSONObject rtnJson=new JSONObject();
		
		String solId = RequestUtil.getString(request, "solId");
		String instId = RequestUtil.getString(request, "instId");
		String versionJson=RequestUtil.getString(request, "versionJson");
		
		BpmSolution bpmSolution = bpmSolutionManager.get(solId);
		
		/**
		 * 获取表单。
		 */
		List<FormModel> formModels=getStartMobileForms(solId, instId);
		
		if(BeanUtil.isEmpty(formModels)){
			rtnJson.put("result", "noForm");
			return rtnJson;
		}
		
		//JSONObject getByStart(String formAlias, String actDefId,String solId)
		
		/**
		 * 表单权限计算。
		 */
		Map<String,String> rightMap =  getStartPermission(solId,bpmSolution.getActDefId(),formModels);
		handFormModel(formModels, rightMap, versionJson);
		
		
		rtnJson.put("formModels", formModels);
		rtnJson.put("bpmSolution", bpmSolution);
		rtnJson.put("result", true);
		return rtnJson;
	}
	
	private Map<String,String> getStartPermission(String solId,String actDefId,List<FormModel> formModels){
		Map<String, Set<String>> profileMap=ProfileUtil.getCurrentProfile();
		Map<String,String> map=new HashMap<String, String>();
		for(FormModel formModel:formModels){
			
			JSONObject rightSetting=FormUtil. getByBoStart( formModel.getBoDefId(),  actDefId, solId);
			JSONObject rightJson=bpmFormRightManager.calcRights(rightSetting, profileMap, false);
			map.put(formModel.getBoDefId(), rightJson.toJSONString());
		}
		return map;
		
	}
	
	private Map<String,String> getTaskPermission(String solId,String actDefId,String nodeId, List<FormModel> formModels){
		Map<String, Set<String>> profileMap=ProfileUtil.getCurrentProfile();
		Map<String,String> map=new HashMap<String, String>();
		for(FormModel formModel:formModels){
			JSONObject rightSetting=FormUtil.getByBoNodeId(formModel.getBoDefId(), nodeId, actDefId, solId);
			JSONObject rightJson=bpmFormRightManager.calcRights(rightSetting, profileMap, false);
			map.put(formModel.getBoDefId(), rightJson.toJSONString());
		}
		return map;
		
	}
	
	private Map<String,String> getDetailPermission(String solId,String actDefId,List<FormModel> formModels){
		Map<String, Set<String>> profileMap=ProfileUtil.getCurrentProfile();
		Map<String,String> map=new HashMap<String, String>();
		for(FormModel formModel:formModels){
			JSONObject rightSetting=FormUtil.getByBoDetail(formModel.getFormKey(),  actDefId, solId);
			JSONObject rightJson=bpmFormRightManager.calcRights(rightSetting, profileMap, false);
			map.put(formModel.getBoDefId(), rightJson.toJSONString());
		}
		return map;
		
	}
	
	@RequestMapping("getMyDrafts")
	@ResponseBody
	public ListModel getMyDrafts(HttpServletRequest request){
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		//加上我的草稿条件的过滤条件
		queryFilter.addFieldParam("createBy",ContextUtil.getCurrentUserId());
		queryFilter.addFieldParam("status", BpmInst.STATUS_DRAFTED);
		queryFilter.addFieldParam("supportMobile", 1);
		queryFilter.getOrderByList().add(new SortParam("createTime", SortParam.SORT_DESC));
		
		List<BpmInst> instList=bpmInstManager.getAll(queryFilter);
		
		return CommonUtil.getListModel(instList, queryFilter.getPage().getTotalItems());
	}
	
	@RequestMapping("copyToMe")
	@ResponseBody
	public ListModel copyToMe(HttpServletRequest request){
		QueryFilter sqlFilter=QueryFilterBuilder.createQueryFilter(request);
		sqlFilter.addFieldParam("userId", ContextUtil.getCurrentUserId());
		sqlFilter.addFieldParam("supportMobile", 1);
		List<BpmInstCc> lists= bpmInstCcManager.getToMeInsts(sqlFilter);
		return CommonUtil.getListModel(lists, sqlFilter.getPage().getTotalItems());
	}
	
	@RequestMapping("getMyApply")
	@ResponseBody
	public ListModel getMyApply(HttpServletRequest request){
		QueryFilter filter=QueryFilterBuilder.createQueryFilter(request);
		filter.addFieldParam("CREATE_BY_", ContextUtil.getCurrentUserId());
		filter.addFieldParam("supportMobile", 1);
		List<BpmInst> lists= bpmInstManager.getMyInst(filter);
		for(BpmInst bi:lists){
			String actInstId=bi.getActInstId();
			List<BpmTask> bpmTasks=bpmTaskManager.getByActInstId(actInstId);
			StringBuffer curTask=new StringBuffer();
			for(BpmTask bpmTask:bpmTasks){
				curTask.append(bpmTask.getName()+"、");
			}
			bi.setTaskNodes(curTask.toString());
		}
		return CommonUtil.getListModel(lists, filter.getPage().getTotalItems());
	}
	
	@RequestMapping("getSolutionType")
	@ResponseBody
	public JsonResult getSolutionType(HttpServletRequest request){
		List<BpmSolution> mySolution = bpmSolutionManager.getAll();
		return new JsonResult(true,"成功返回",mySolution);
	}
	
	//获取留言	
	@RequestMapping("getMessageBoard")
	@ResponseBody
	public ListModel getMessageBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String procInstId = RequestUtil.getString(request, "procInstId");
		BpmInst bpmInst = bpmInstManager.getByActInstId(procInstId);
		List<BpmMessageBoard> bpmMessageBoards = null;
		QueryFilter filter = QueryFilterBuilder.createQueryFilter(request);
		if(bpmInst==null){
			bpmMessageBoards = new ArrayList<BpmMessageBoard>(0);
			return CommonUtil.getListModel(bpmMessageBoards, 0);
		}
		bpmMessageBoards = bpmMessageBoardManager.getMessageBoardByInstId(bpmInst.getInstId(),filter);
			
		return CommonUtil.getListModel(bpmMessageBoards, bpmMessageBoards.size());
	}
	
	//留言
    @RequestMapping("leaveMessage")
 	@ResponseBody
    public JsonResult leaveMessage(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String procInstId = RequestUtil.getString(request, "procInstId");
    	String messageContent = RequestUtil.getString(request, "messageContent");
    	BpmInst bpmInst = bpmInstManager.getByActInstId(procInstId);  
    	if(StringUtil.isEmpty(bpmInst.getInstId())){
    		return new JsonResult(false,"该留言没有绑定到流程，请检查数据");
    	}   
    	BpmMessageBoard bpmMessageBoard = new BpmMessageBoard();
    	bpmMessageBoard.setId(IdUtil.getId());
    	bpmMessageBoard.setInstId(bpmInst.getInstId());
    	bpmMessageBoard.setMessageContent(messageContent);
    	bpmMessageBoard.setMessageAuthor(ContextUtil.getCurrentUser().getFullname());
    	bpmMessageBoard.setMessageAuthorId(ContextUtil.getCurrentUserId());
    	bpmMessageBoardManager.create(bpmMessageBoard);
    	return new JsonResult(true,"留言成功");
    }
    
    @RequestMapping("getCatTree")
	@ResponseBody
	public ListModel getCatTree(HttpServletRequest request,HttpServletResponse response) throws Exception{
		boolean isAdmin=RequestUtil.getBoolean(request, "isAdmin",false);
		IUser user=ContextUtil.getCurrentUser();
		List<SysTree> treeList=null;
		if(user.isSuperAdmin()){
			treeList=sysTreeManager.getByCatKeyTenantId(SysTree.CAT_BPM_SOLUTION, ContextUtil.getCurrentTenantId());			
		}
		else{
			treeList=bpmSolutionManager.getCategoryTree(isAdmin);
		}
		List<SysTree> list = BeanUtil.listToTree(treeList);
		
		return CommonUtil.getListModel(list, list.size());
	}
    
    @RequestMapping("solutionList")
	@ResponseBody
	public JsonResult solutionList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		String treeId=RequestUtil.getString(request, "treeId");
		
		IPage page = queryFilter.getPage();
		Page pa = (Page)page;
		pa.setPageSize(10000);
		
		if(StringUtil.isNotEmpty(treeId)){
			SysTree sysTree= sysTreeManager.get(treeId);
			if(sysTree!=null){
				String path=sysTree.getPath();
				queryFilter.addLeftLikeFieldParam("TREE_PATH_", path);
			}
		}
		List<BpmSolution> bpmSolutions=bpmSolutionManager.getSolutions(queryFilter,true);
		return new JsonResult(true,"获取成功",bpmSolutions);
	}
     
	

}
