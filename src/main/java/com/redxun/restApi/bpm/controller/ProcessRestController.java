package com.redxun.restApi.bpm.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.redxun.bpm.activiti.entity.ActNodeDef;
import com.redxun.bpm.activiti.service.ActRepService;
import com.redxun.bpm.activiti.util.ProcessHandleHelper;
import com.redxun.bpm.core.entity.BpmDestNode;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.BpmSolution;
import com.redxun.bpm.core.entity.BpmTask;
import com.redxun.bpm.core.entity.ProcessMessage;
import com.redxun.bpm.core.entity.ProcessNextCmd;
import com.redxun.bpm.core.entity.ProcessStartCmd;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.bpm.core.manager.BpmSolutionManager;
import com.redxun.bpm.core.manager.BpmTaskManager;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.DateUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.log.LogEnt;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/bpm/process/")
public class ProcessRestController {
	@Resource 
	BpmInstManager bpmInstManager;
	@Resource 
	BpmTaskManager bpmTaskManager;
	@Resource 
	ActRepService actRepService;
	@Resource
	UserService  userService;
	@Resource
	BpmSolutionManager bpmSolutionManager;
	
	
	/**
	 * {userAccount:'admin@redxun.cn',pageIndex:1,pageSize:20,Q_KEY__S_LK:'xxx',Q_NAME__S_LK:""}
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMySolutions")
	@ResponseBody
	public JsonPageResult getMySolutions(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonPageResult<BpmTask> result=new JsonPageResult<BpmTask>();
		
		String cmd=request.getParameter("cmd");
		
		if(StringUtils.isEmpty(cmd)){
			result.setSuccess(false);
			result.setMessage("没有正确提交cmd参数！cmd参数格式如：{userAccount:'abc@redxun.cn',pageIndex:1,pageSize:20,sortField:'createTime',sortOrder:'asc',Q_name_S_LK:'xxx'}");
			return result;
		}
		
		JSONObject cmdObj=JSONObject.fromObject(cmd);
		
		String userAccount=JSONUtil.getString(cmdObj,"userAccount");
		
		if(StringUtils.isEmpty(userAccount)){
			result.setSuccess(false);
			result.setMessage("没有正确提交cmd参数！cmd参数格式如：{userAccount:'abc@redxun.cn',pageIndex:1,pageSize:20,sortField:'createTime',sortOrder:'asc',Q_name_S_LK:'xxx'}");
			return result;
		}
		
		IUser sysUser=userService.getByUsername(userAccount);
    	
    	if(sysUser==null){
    		result.setSuccess(false);
			result.setMessage("用户账号"+userAccount+"不存在！");
			return result;
    	}
    	
    	//{userAccount:'admin@redxun.cn',pageIndex:0,pageSize:20,Q_KEY__S_LK:'demo',Q_NAME__S_LK:""}
    	QueryFilter filter=QueryFilterBuilder.createQueryFilter(cmdObj);
		
    	ContextUtil.setCurUser(sysUser);

		List<BpmSolution> bpmSolutions=bpmSolutionManager.getSolutions(filter,false);
		return new JsonPageResult<BpmSolution>(bpmSolutions,filter.getPage().getTotalItems());
	}
	
	/**
	 * 取得任务当前的跳出节点列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTaskOutNodes")
	@ResponseBody
	public JsonResult getTaskOutNodes(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String taskId=request.getParameter("taskId");
		BpmTask bpmTask=bpmTaskManager.get(taskId);
		ActNodeDef actNodeDef=actRepService.getActNodeDef(bpmTask.getProcDefId(), bpmTask.getTaskDefKey());
		return new JsonResult(true,"",actNodeDef.getOutcomeNodes());
	}
	
	/**
	 * 获得任务列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTasksByInstId")
	@ResponseBody
	public JsonResult getTasksByInstId(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String instId=request.getParameter("instId");
		BpmInst bpmInst=bpmInstManager.get(instId);
		List<BpmTask> tasks=bpmTaskManager.getByActInstId(bpmInst.getActInstId());
		return new JsonResult(true,"",tasks);
	}
	
	/**
     * 处理任务跳下一步
     * 传入参数，参数格式如下:
     * {
			taskId:'10000303',
			userAccount:'admin@redxun.cn',
			jsonData:{},
			jumpType:'AGREE',//REFUSE，BACK，BACK_TO_STARTOR
			opinion:'同意',
			destNodeId:'Task2',
			destNodeUsers:[
				{
				 nodeId:'UserTask1'
				 userIds:'1,2',
				 priority:50,
				 expiretime:'2016-03-04'
				},{
				 nodeId:'UserTask2'
				 userIds:'1,2',
				 priority:50,
				 expiretime:'2016-03-04'
				}
			]
	 }
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("doNext")
    @ResponseBody
    @LogEnt(action = "doNext", module = "流程", submodule = "流程接口")
    public JsonResult doNext(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	//加上处理的消息提示
    	ProcessMessage handleMessage=null;
    	String cmd=request.getParameter("cmd");
    	JSONObject cmdJson=JSONObject.fromObject(cmd);
    	ProcessHandleHelper.clearBackPath();
    	
    	String userAccount=JSONUtil.getString(cmdJson,"userAccount");
		
		if(StringUtils.isEmpty(userAccount)){
			return new JsonResult(false,"没有提交用户账号参数！");
		}
		
    	IUser user=userService.getByUsername(userAccount);
    	
    	if(user==null){
    		return new JsonResult(false,"用户账号"+userAccount+"不存在！");
    	}
    	JsonResult result=new JsonResult();
    	try{
    		ContextUtil.setCurUser(user);
	    	handleMessage=new ProcessMessage();
	    	ProcessHandleHelper.setProcessMessage(handleMessage);
	    	ProcessNextCmd processNextCmd=getNextCmdFromJson(cmdJson); 
	    	bpmTaskManager.doNext(processNextCmd);
    	}catch(Exception ex){
    		if(handleMessage.getErrorMsges().size()==0){
    			handleMessage.getErrorMsges().add(ex.getMessage());
    		}
    	}finally{
    		//在处理过程中，是否有错误的消息抛出
    		if(handleMessage.getErrorMsges().size()>0){
    			result.setSuccess(false);
    			result.setMessage(handleMessage.getErrors());
    		}else{
    			result.setSuccess(true);
    			result.setMessage("成功处理任务！");
    		}
    		ProcessHandleHelper.clearProcessMessage();
    		ContextUtil.clearCurLocal();
    	}
    	return result;
    }
    
    /**
     * 通过json参数获得执行下一步的处理对象
     * @param cmdJson，格式如下:
     * 
     * @return
     */
    private ProcessNextCmd getNextCmdFromJson(JSONObject cmdJson){
    	String taskId=JSONUtil.getString(cmdJson,"taskId");
    	String jsonData=JSONUtil.getString(cmdJson,"jsonData");
    	String jumpType=JSONUtil.getString(cmdJson,"jumpType");
    	String opinion=JSONUtil.getString(cmdJson,"opinion");
    	String destNodeId=JSONUtil.getString(cmdJson,"destNodeId");
    	String destNodeUsers=JSONUtil.getString(cmdJson,"destNodeUsers");
    	String nextJumpType=JSONUtil.getString(cmdJson,"nextJumpType");
    	
    	ProcessNextCmd cmd=new ProcessNextCmd();
    	cmd.setTaskId(taskId);
    	cmd.setJsonData(jsonData);
    	cmd.setJumpType(jumpType);
    	cmd.setOpinion(opinion);
    	
    	cmd.setNextJumpType(nextJumpType);
    	
    	if(StringUtils.isNotEmpty(destNodeId)){
    		cmd.setDestNodeId(destNodeId);
    	}
    	Calendar cal=Calendar.getInstance();
    	//默认为两天
    	cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+2);
    	if(StringUtils.isNotEmpty(destNodeUsers)){
    		JSONArray jsonArray=JSONArray.fromObject(destNodeUsers);
    		for(int i=0;i<jsonArray.size();i++){
    			JSONObject obj=jsonArray.getJSONObject(i);
    			String nodeId=obj.getString("nodeId");
    			String userIds=obj.getString("userIds");
    			Object priority=obj.get("priority");
    			Object expiretime=obj.get("expiretime");
    			int intPriority=50;
    			Date dateExpiretime=cal.getTime();
    			if(priority!=null){
    				intPriority=new Integer(priority.toString());
    			}
    			if(expiretime!=null){
    				dateExpiretime=DateUtil.parseDate(expiretime.toString());
    			}
    			
    			BpmDestNode dn=new BpmDestNode(nodeId,userIds,intPriority,dateExpiretime);
    			
    			cmd.getNodeUserMap().put(nodeId, dn);
    		}
    	}
    	return cmd;
    }
	
	/**
	 * 取得用户任务待办列表
	 * @param request  cmd 参数格式:{userAccount:'abc@redxun.cn',params:{pageIndex:1,pageSize:20,sortField:'createTime',sortOrder:'asc',Q_name_S_LK:'xxx'}}
	 * @param response
	 * @return
	 * @throws Excepion
	 */
	@RequestMapping("getTasksByUserAccount")
	@ResponseBody
	public JsonPageResult<BpmTask> getTasksByUserAccount(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		JsonPageResult<BpmTask> result=new JsonPageResult<BpmTask>();
		
		String cmd=request.getParameter("cmd");
		
		if(StringUtils.isEmpty(cmd)){
			result.setSuccess(false);
			result.setMessage("没有正确提交cmd参数！cmd参数格式如：{userAccount:'abc@redxun.cn',pageIndex:1,pageSize:20,sortField:'createTime',sortOrder:'asc',Q_name_S_LK:'xxx'}");
			return result;
		}
		
		JSONObject cmdObj=JSONObject.fromObject(cmd);
		
		String userAccount=JSONUtil.getString(cmdObj,"userAccount");
		
		if(StringUtils.isEmpty(userAccount)){
			result.setSuccess(false);
			result.setMessage("没有正确提交cmd参数！cmd参数格式如：{userAccount:'abc@redxun.cn',pageIndex:1,pageSize:20,sortField:'createTime',sortOrder:'asc',Q_name_S_LK:'xxx'}");
			return result;
		}
		
		IUser sysUser=userService.getByUsername(userAccount);
    	
    	if(sysUser==null){
    		result.setSuccess(false);
			result.setMessage("用户账号"+userAccount+"不存在！");
			return result;
    	}
    	String msg=null;
    	
    	
    	QueryFilter filter=QueryFilterBuilder.createQueryFilter(cmdObj);
    	
    	List<BpmTask> bpmTasks=null;
    	try{
    		ContextUtil.setCurUser(sysUser);
    		bpmTasks=bpmTaskManager.getByUserId(filter);
	    	msg="成功获得任务列表";
    	}catch(Exception ex){
    		msg=ex.getMessage();
    	}finally{
    		ContextUtil.clearCurLocal();
    	}
    	return new JsonPageResult(true,bpmTasks,filter.getPage().getTotalItems(),msg);
	}
	
	/**
	 * 启动流程的API,并以JSON格式返回,
	 * 提交参数格式如：{userAccount:'abc@xce.com',solId:'111',jsonData:'{}',vars:'[{name:\'a\',type:\'String\',value:\'abc\'}]'}
	 * 
	 * jsonData:
	 * 
	 * {
			boDefId:'2610000001170023',
			formKey:'exMaterInfo',
			data:{
				ajbh:'',
				ajmc:'',
				cl1:'',
				cl2:'',
				cl3:''
			}
		}
	 *
	 * 返回格式：{success:'true',data:[],errorMsg:'xxxxx'}
	 * @param request
	 * @param response
	 */
	@RequestMapping("startProcess")
	@ResponseBody
	@LogEnt(action = "startProcess", module = "流程", submodule = "流程接口")
	public JsonResult startProcess(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String cmd=request.getParameter("cmd");
		
		if(StringUtils.isEmpty(cmd)){
			return new JsonResult(false,"没有正确提交cmd参数！cmd参数格式如：{userAccount:'abc@redxun.cn',businessKey:'2222',jsonData:'{}',vars:'[{name:\'a\',type:\'String\',value:\'abc\'}]'");
		}
		
		JSONObject cmdObj=JSONObject.fromObject(cmd);
		
		String userAccount=JSONUtil.getString(cmdObj,"userAccount");
		
		if(StringUtils.isEmpty(userAccount)){
			return new JsonResult(false,"没有提交用户账号参数：{userAccount:'abc@redxun.cn',businessKey:'2222',jsonData:'{}',vars:'[{name:\'a\',type:\'String\',value:\'abc\'}]'");
		}
		
    	IUser user=userService.getByUsername(userAccount);
    	
    	if(user==null){
    		return new JsonResult(false,"用户账号"+userAccount+"不存在！");
    	}
    	String msg=null;
    	
    	try{
    		ContextUtil.setCurUser(user);
			ProcessStartCmd startCmd=new ProcessStartCmd();
			startCmd.setSolId(JSONUtil.getString(cmdObj,"solId"));
			startCmd.setJsonData(JSONUtil.getString(cmdObj, "jsonData"));
			startCmd.setBusinessKey(JSONUtil.getString(cmdObj, "businessKey"));
			String varArr=JSONUtil.getString(cmdObj, "vars");
			if(StringUtil.isNotEmpty(varArr)){
				Map<String,Object> vars=JSONUtil.jsonArr2Map(varArr);
				startCmd.setVars(vars);
			}
			BpmInst inst= bpmInstManager.doStartProcess(startCmd);
			
			return new JsonResult(true,"成功启动流程！",inst);
    	}catch(Exception ex){
    		msg=ex.getMessage();
    		ex.printStackTrace();
    	}finally{
    		ContextUtil.clearCurLocal();
    	}
		return new JsonResult(false,msg);
	}
	
	/**
	 * 撤销流程接口。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("recoverInst")
	@ResponseBody
	@LogEnt(action = "recoverInst", module = "流程", submodule = "流程接口")
	public JsonResult recoverInst(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonResult result=new JsonResult();
		String cmd=request.getParameter("cmd");
		
		if(StringUtils.isEmpty(cmd)){
			result.setSuccess(false);
			result.setMessage("没有正确提交cmd参数！cmd参数格式如：{userAccount:'abc@redxun.cn',instId:'1001'}");
			return result;
		}
		
		JSONObject cmdObj=JSONObject.fromObject(cmd);
		
		String userAccount=JSONUtil.getString(cmdObj,"userAccount");
		
		if(StringUtils.isEmpty(userAccount)){
			result.setSuccess(false);
			result.setMessage("没有正确提交cmd参数！cmd参数格式如：{userAccount:'abc@redxun.cn',instId:'1001'}");
			return result;
		}
		IUser sysUser=userService.getByUsername(userAccount);
    	
    	if(sysUser==null){
    		result.setSuccess(false);
			result.setMessage("用户账号"+userAccount+"不存在！");
			return result;
    	}
		String instId=cmdObj.getString("instId");
		
		ContextUtil.setCurUser(sysUser);
		
		result= bpmInstManager.recoverInst(instId);
		return result;
	}
	
	/**
	 * 获取代理给我的任务。
	 * {userAccount:'abc@redxun.cn',Q_createtime1_D_GE:'1001',Q_createtime2_D_LE:'',Q_name_S_LK:'',Q_description_S_LK:''}
	 * @param request
	 * @return
	 */
	@RequestMapping("getMyAgentTasks")
	@ResponseBody
	public JsonPageResult getMyAgentTasks(HttpServletRequest request){
		JsonPageResult result=new JsonPageResult();
		String cmd=request.getParameter("cmd");
		
		if(StringUtils.isEmpty(cmd)){
			result.setSuccess(false);
			result.setMessage("没有正确提交cmd参数！cmd参数格式如：{userAccount:'abc@redxun.cn',Q_createtime1_D_GE:'1001',Q_createtime2_D_LE:'',Q_name_S_LK:'',Q_description_S_LK:''}");
			return result;
		}
		
		JSONObject cmdObj=JSONObject.fromObject(cmd);
		
		String userAccount=JSONUtil.getString(cmdObj,"userAccount");
		
		if(StringUtils.isEmpty(userAccount)){
			result.setSuccess(false);
			result.setMessage("没有正确提交cmd参数！cmd参数格式如：{userAccount:'abc@redxun.cn',Q_createtime1_D_GE:'1001',Q_createtime2_D_LE:'',Q_name_S_LK:'',Q_description_S_LK:''}");
			return result;
		}
		IUser sysUser=userService.getByUsername(userAccount);
    	
    	if(sysUser==null){
    		result.setSuccess(false);
			result.setMessage("用户账号"+userAccount+"不存在！");
			return result;
    	}
		
		QueryFilter filter=  QueryFilterBuilder.createQueryFilter(request);
		filter.addFieldParam("userId", sysUser.getUserId());
		filter.addSortParam("CREATE_TIME_", "DESC");
		List<BpmTask> bpmTasks  = bpmTaskManager.getAgentToTasks(filter);
		return new JsonPageResult(bpmTasks, filter.getPage().getTotalItems());
	}
}
