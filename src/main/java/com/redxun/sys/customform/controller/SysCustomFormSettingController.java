
package com.redxun.sys.customform.controller;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.bpm.activiti.util.ProcessHandleHelper;
import com.redxun.bpm.api.IFlowService;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.manager.BpmFormRightManager;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.bpm.core.manager.BpmSolutionManager;
import com.redxun.bpm.form.api.FormHandlerFactory;
import com.redxun.bpm.form.entity.BpmFormView;
import com.redxun.bpm.form.entity.FormModel;
import com.redxun.bpm.form.impl.formhandler.FormUtil;
import com.redxun.bpm.form.manager.BpmFormViewManager;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.core.util.ExcelUtil;
import com.redxun.core.util.PdfUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.context.ProfileUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.api.ContextHandlerFactory;
import com.redxun.sys.bo.entity.BoResult;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.entity.SysBoDef;
import com.redxun.sys.bo.entity.SysBoEnt;
import com.redxun.sys.bo.manager.SysBoDataHandler;
import com.redxun.sys.bo.manager.SysBoDefManager;
import com.redxun.sys.bo.manager.SysBoEntManager;
import com.redxun.sys.core.entity.SysBoList;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysBoListManager;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.customform.entity.SysCustomFormSetting;
import com.redxun.sys.customform.manager.ICustomFormDataHandler;
import com.redxun.sys.customform.manager.SysCustomFormSettingManager;

/**
 * 自定义表单配置设定控制器
 * @author mansan
 */
@Controller
@RequestMapping("/sys/customform/sysCustomFormSetting/")
public class SysCustomFormSettingController extends BaseMybatisListController{
    @Resource
    SysCustomFormSettingManager sysCustomFormSettingManager;
    @Resource
    FormHandlerFactory formHandlerFactory;
    @Resource
    SysBoEntManager sysBoEntManager;
    
    @Resource
    SysBoListManager sysBoListManager;
    
    @Resource
    IFlowService flowService;
    @Resource
    BpmFormViewManager bpmFormViewManager;
    @Resource
    SysBoDefManager sysBoDefManager;
    @Resource
    BpmInstManager bpmInstManager;
    @Resource
    SysBoDataHandler sysBoDataHandler;
    @Resource
    ContextHandlerFactory contextHandlerFactory;
    @Resource
    JdbcTemplate jdbcTemplate;
    @Resource
    SysTreeManager sysTreeManager;
    @Resource
    BpmSolutionManager bpmSolutionManager;
    @Resource
    BpmFormRightManager bpmFormRightManager;
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                sysCustomFormSettingManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除!");
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
        String pkId=RequestUtil.getString(request, "pkId");
        SysCustomFormSetting sysCustomFormSetting=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysCustomFormSetting=sysCustomFormSettingManager.get(pkId);
        }else{
        	sysCustomFormSetting=new SysCustomFormSetting();
        }
        return getPathView(request).addObject("sysCustomFormSetting",sysCustomFormSetting);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	ModelAndView mv = getPathView(request);
    	SysCustomFormSetting sysCustomFormSetting=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysCustomFormSetting=sysCustomFormSettingManager.get(pkId);
    		String treeId=sysCustomFormSetting.getTreeId();
    		if(StringUtils.isNotBlank(treeId)){
    			SysTree sysTree=sysTreeManager.get(treeId);
    			mv.addObject("sysTree", sysTree);
    		}
    		String tableRightJson=sysCustomFormSettingManager.getTableRightJson(sysCustomFormSetting);
			//设置子表权限。
			mv.addObject("tableRightJson", tableRightJson);
    	}else{
    		sysCustomFormSetting=new SysCustomFormSetting();
    	}
    	JSONArray contextVarAry=getContextVars();
		mv.addObject("contextVars", contextVarAry.toJSONString());
    	return mv.addObject("sysCustomFormSetting",sysCustomFormSetting);
    }
    
    /**
     * 多行保存
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{alias}/rowsSave")
    @ResponseBody
    public JsonResult rowsSave(HttpServletRequest request,@PathVariable(value="alias")String alias){
    	String rows=request.getParameter("rows");
    	
    	SysCustomFormSetting setting=sysCustomFormSettingManager.getByAlias(alias);

    	if(setting==null){
    		return new JsonResult(false,"表单方案映射没有配置！请联系管理员。");
    	}
    	
    	sysBoEntManager.batchRows(setting.getBodefId(),rows);
    	
    	return new JsonResult(true,"成功保存行数据！");
    }

    @RequestMapping("getContextVarsAndTableJson")
    @ResponseBody
    public JSONObject getContextVarsAndTableJson(HttpServletRequest request,HttpServletResponse response){
    	String boDefId=RequestUtil.getString(request, "boDefId");
    	
    	JSONObject rtnObj=new JSONObject();
    	
    	String tableRightJson=sysCustomFormSettingManager.getTableRightJsonByBoDefId(boDefId);//getTableRightJson(sysCustomFormSettingManager.get(pkId));
		//设置子表权限。
		rtnObj.put("tableRightJson", tableRightJson);
    	JSONArray contextVarAry=getContextVars();
    	rtnObj.put("contextVars", contextVarAry.toJSONString());
    	
    	return rtnObj;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return sysCustomFormSettingManager;
	}
	
	@RequestMapping(value={"treeMgr/{alias}"})
    public ModelAndView treeMgr(HttpServletRequest request,@PathVariable(value="alias")String alias) throws Exception{
		ModelAndView mv=new ModelAndView();
		mv.addObject("alias", alias);
		mv.setViewName("/sys/customform/sysCustomFormSettingTreeMgr.jsp");
		return mv;
    }
	
	@RequestMapping(value={"tree/{alias}"})
	@ResponseBody
	public List<JSONObject> treeData(HttpServletRequest request,@PathVariable(value="alias")String alias) throws Exception{
		String parentId=RequestUtil.getString(request, SysBoEnt.SQL_PK, "");
		List<JSONObject> list= sysCustomFormSettingManager.getTreeData(alias, parentId);
		return list;
    }
	
	/**
	 * 新版本的导入excel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("{boKey}/importExcel")
    @ResponseBody
	public Long importExcel(@PathVariable("boKey")String boKey,MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {

		SysBoList sysBoList=sysBoListManager.getByKey(boKey, ContextUtil.getCurrentTenantId());
		if(sysBoList ==null) return null;
		//获取该表所有字段
		String sysFormAlias = sysBoList.getFormAlias();
		SysCustomFormSetting scfs = sysCustomFormSettingManager.getByAlias(sysFormAlias);
		BpmFormView fv = bpmFormViewManager.getLatestByKey(scfs.getFormAlias(), ContextUtil.getCurrentTenantId());
		String boDefId = fv.getBoDefId();
		SysBoDef bodef = sysBoDefManager.get(boDefId);
		SysBoEnt boent = sysBoEntManager.getByBoDefId(bodef.getId());
		List<SysBoAttr> attrs = sysBoEntManager.getByEntId(boent.getId());
		
		Map<String, MultipartFile> files = request.getFileMap();
		Iterator<MultipartFile> it = files.values().iterator();
		long l = 0;
		
		while(it.hasNext()){
			MultipartFile f = it.next();
			InputStream is = f.getInputStream();
			WorkbookFactory.create(is);
			List<Map<String,Object>> dataList = ExcelUtil.importExcel(attrs, f);
			l += sysBoListManager.insertData(dataList,boent);			
		}
		return l;
	}
	
	/**
	 * 替换form方法实现新的表单的展示
	 * @param request
	 * @param alias
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"{alias}/add"})
	public ModelAndView add(HttpServletRequest request,@PathVariable(value="alias")String alias) throws Exception{
		return getAddForm(request,alias);
	}
	
	@RequestMapping(value={"form/{alias}"})
    public ModelAndView form(HttpServletRequest request,@PathVariable(value="alias")String alias) throws Exception{
		return getAddForm(request,alias);
    }
	
	private ModelAndView getAddForm(HttpServletRequest request,String alias) throws Exception{
		ModelAndView mv=new ModelAndView();
		String pid=RequestUtil.getString(request, "pid");
		String tenantId=ContextUtil.getCurrentTenantId();
		SysCustomFormSetting setting=sysCustomFormSettingManager.getByAlias(alias);
		
		Map<String,Object> params=RequestUtil.getParameterValueMap(request, false);
		
		String dataHandler=setting.getDataHandler();
		FormModel formModel=null;
		//增加数据处理器。
		if(StringUtil.isEmpty(dataHandler)){
			formModel=FormUtil.getFormByFormAlias(setting.getId(),setting.getFormAlias(), "",false,false, params);
		}else{
			BpmFormView bpmFormView=bpmFormViewManager.getLatestByKey(setting.getFormAlias(), tenantId);
			ICustomFormDataHandler handler=(ICustomFormDataHandler) AppBeanUtil.getBean(dataHandler);
			JSONObject jsonData= handler.getInitData(bpmFormView.getBoDefId());
			FormUtil.setContextData(jsonData, params);
			formModel=FormUtil.getByFormView(setting.getId(),bpmFormView, jsonData, false, false);
		}
		
		String parentHtml="";
		if(StringUtil.isNotEmpty(pid) && setting.getIsTree()==1){
			parentHtml="<input class='mini-hidden' name='"+SysBoEnt.SQL_FK+"' value='"+pid+"'>";
			formModel.setContent( formModel.getContent() +parentHtml);
		}
		boolean canStartFlow=StringUtil.isNotEmpty(setting.getSolId());
		boolean hasAfterJs=StringUtil.isNotEmpty(setting.getAfterJsScript());
		
		String viewId=formModel.getViewId();
		mv.addObject("viewId", viewId);
		mv.addObject("formModel", formModel);
		mv.addObject("setting", setting);
		mv.addObject("canStartFlow", canStartFlow);
		mv.addObject("hasAfterJs", hasAfterJs);
		
		mv.setViewName("/sys/customform/sysCustomFormSettingForm.jsp");
		return mv;
	}
	
	/**
	 * 跳至PDF表单打印页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportPDF")
	@ResponseBody
	public void exportPDF(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String alias=RequestUtil.getString(request, "alias");
		String id=RequestUtil.getString(request, "id");
		String tenantId=ContextUtil.getCurrentTenantId();
		
		SysCustomFormSetting setting=sysCustomFormSettingManager.getByAlias(alias);
		BpmFormView bpmFormView=bpmFormViewManager.getLatestByKey(setting.getFormAlias(), tenantId);
		
		JSONObject rightSetting=FormUtil.getRightByForm(bpmFormView.getKey());
		Map<String, Set<String>> profileMap=ProfileUtil.getCurrentProfile();
		JSONObject rightJson=bpmFormRightManager.calcRights(rightSetting, profileMap, true) ;
		JSONObject jsonData= FormUtil.getData(bpmFormView.getBoDefId(),id);
		
		JSONObject opinionData=new JSONObject();
		
		String tempHtml = bpmFormViewManager.getPDFHtml(rightJson, bpmFormView.getPdfTemp(), jsonData,opinionData,"");
		
		String html= bpmFormViewManager. getHtml(tempHtml);
		String downloadFileName = URLEncoder.encode(setting.getName(),"UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" +downloadFileName);
		PdfUtil.convertHtmlToPdf(html, response.getOutputStream());
	}
	
	/**
	 * 表单编辑数据。
	 * @param alias
	 * @param id
	 * @return
	 * @throws Exception
	 * @{@link Deprecated} 使用formEdit
	 */
	@RequestMapping(value={"form/{alias}/{id}"})
    public ModelAndView formEdit(HttpServletRequest request, @PathVariable(value="alias")String alias,@PathVariable(value="id")String id) throws Exception{
		ModelAndView mv=getFormEdit(request,alias,id);
		return mv;
    }
	/**
	 * 表单编辑数据。
	 * @param alias
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"{alias}/edit"})
    public ModelAndView formEdit(HttpServletRequest request, @PathVariable(value="alias")String alias) throws Exception{
		String id=request.getParameter("pk");
		ModelAndView mv=getFormEdit(request,alias,id);
		return mv;
    }
	
	private ModelAndView getFormEdit(HttpServletRequest request,String alias,String id) throws Exception{
		ModelAndView mv=new ModelAndView();
		String tenantId=ContextUtil.getCurrentTenantId();
		SysCustomFormSetting setting=sysCustomFormSettingManager.getByAlias(alias);
		String dataHandler=setting.getDataHandler();
		
		Map<String,Object> params=RequestUtil.getParameterValueMap(request, false);
		
		//设置子表权限。
		ProcessHandleHelper.clearObjectLocal();
		ProcessHandleHelper.setObjectLocal(setting.getTableRightJson());
		
		
		FormModel formModel=null;
		if(StringUtil.isEmpty(dataHandler)){
			formModel =FormUtil.getFormByFormAlias(setting.getId(),setting.getFormAlias(), id,false,false, params);
		}else{
			BpmFormView bpmFormView=bpmFormViewManager.getLatestByKey(setting.getFormAlias(), tenantId);
			ICustomFormDataHandler handler=(ICustomFormDataHandler) AppBeanUtil.getBean(dataHandler);
			JSONObject jsonData= handler.getByPk(id,bpmFormView.getBoDefId());
			FormUtil.setContextData(jsonData, params);
			formModel=FormUtil.getByFormView(setting.getId(), bpmFormView, jsonData, false, false);
		}
		
		mv.addObject("formModel", formModel);
		
		boolean canStartFlow= canStartFlow(formModel,setting.getSolId());
		boolean hasAfterJs=StringUtil.isNotEmpty(setting.getAfterJsScript());
		
		mv.addObject("setting", setting);
		mv.addObject("hasAfterJs", hasAfterJs);
		
		mv.addObject("canStartFlow", canStartFlow);
		mv.setViewName("/sys/customform/sysCustomFormSettingForm.jsp");
		return mv;
	}
	
	/**
	 * 判断否启动流程。
	 * <pre>
	 * 	1.是否存在解决方案。
	 * 	2.是否存在流程实例。
	 * </pre>
	 * @param formModel
	 * @param solId
	 * @return
	 */
	private boolean canStartFlow(FormModel formModel,String solId){
		if(StringUtil.isEmpty(solId)) return false;
		JSONObject data=formModel.getJsonData();
	
		String status=data.getString(SysBoEnt.FIELD_INST_STATUS_);
		if(BpmInst.STATUS_DRAFTED.equals(status)) return true;
		
		return false;
	}
	
	/**
	 * 查看明细
	 * @param alias
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/detail/{alias}/{id}"})
    public ModelAndView detail(@PathVariable(value="alias")String alias,@PathVariable(value="id")String id) throws Exception{
		return getDetailForm(alias,id);
    }
	
	/**
	 * 查看明细
	 * @param alias
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/{alias}/detail"})
    public ModelAndView detail(HttpServletRequest request,@PathVariable(value="alias")String alias) throws Exception{
		String id=request.getParameter("pk");
		return getDetailForm(alias,id);
    }

	private ModelAndView getDetailForm(String alias,String id) throws Exception{
		ModelAndView mv=new ModelAndView();
		String tenantId=ContextUtil.getCurrentTenantId();
		
		SysCustomFormSetting setting=sysCustomFormSettingManager.getByAlias(alias);
		
		//设置子表权限。
		ProcessHandleHelper.clearObjectLocal();
		ProcessHandleHelper.setObjectLocal(setting.getTableRightJson());
		
		String dataHandler=setting.getDataHandler();
		FormModel formModel=null;
		if(StringUtil.isEmpty(dataHandler)){
			formModel= FormUtil.getFormByFormAlias(setting.getId(),setting.getFormAlias(), id,true,false, new HashMap<String, Object>());
		}else{
			BpmFormView bpmFormView=bpmFormViewManager.getLatestByKey(setting.getFormAlias(), tenantId);
			ICustomFormDataHandler handler=(ICustomFormDataHandler) AppBeanUtil.getBean(dataHandler);
			JSONObject jsonData= handler.getByPk(id,bpmFormView.getBoDefId());
			formModel=FormUtil.getByFormView(setting.getId(), bpmFormView, jsonData, true, false);
		}
		
		mv.addObject("formModel", formModel);
		
		JSONObject jsonObj=formModel.getJsonData();
		
		boolean hasInst=jsonObj.containsKey(SysBoEnt.FIELD_INST);
		if(hasInst){
			String instId=jsonObj.getString(SysBoEnt.FIELD_INST);
			mv.addObject("instId", instId);
		}
		mv.addObject("hasInst", hasInst);
		
		mv.addObject("setting", setting);
		mv.setViewName("/sys/customform/sysCustomFormSettingDetail.jsp");
		return mv;
	}
	
	@RequestMapping(value={"saveData"},method=RequestMethod.POST)
	@ResponseBody
	public JsonResult<JSONObject> save(HttpServletRequest request,@RequestBody String json ) throws Exception{
		JsonResult<JSONObject> rtn=null;
		try{
			JSONObject jsonObj=JSONObject.parseObject(json);
			JSONObject setting=jsonObj.getJSONObject("setting");
			
			String alias=setting.getString("alias");
			SysCustomFormSetting formSetting= sysCustomFormSettingManager.getByAlias(alias);
			BoResult result= sysCustomFormSettingManager.saveData(formSetting, jsonObj);
			
			return handTreeResult(result,formSetting,jsonObj);
 		}
		catch(Exception ex){
			ex.printStackTrace();
			rtn= new JsonResult<JSONObject>(false, "保存表单数据失败!");
		}
		return rtn;
    }
	
	/**
	 * 获得表单方案的对话框
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listForDialog")
	@ResponseBody
	public JsonPageResult<SysCustomFormSetting> listForDialog(HttpServletRequest request,HttpServletResponse response) throws Exception{
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		
		String tenantId=ContextUtil.getCurrentTenantId();
		queryFilter.addFieldParam("tenantId", tenantId);
		queryFilter.addSortParam("createTime", "desc");
		List<SysCustomFormSetting> list=sysCustomFormSettingManager.getAll(queryFilter);
		return new JsonPageResult<SysCustomFormSetting>(list, queryFilter.getPage().getTotalItems());
	}
	
	private JsonResult<JSONObject> handTreeResult(BoResult result,SysCustomFormSetting setting,JSONObject jsonObj){
		JSONObject settingJson=jsonObj.getJSONObject("setting");
		JSONObject formData=jsonObj.getJSONObject("formData");
		JsonResult<JSONObject> rtn=new JsonResult<JSONObject>(true);
		String msg="";
		if(BoResult.ACTION_TYPE.ADD.equals(result.getAction())){
			msg="添加数据成功!";
		}
		else{
			msg="编辑数据成功!";
		}
		String action=settingJson.getString("action");
		if("startFlow".equals(action)){
			msg="启动流程成功!";
		}
		
		rtn.setMessage(msg);
		//1.不是属性返回。
		JSONObject json=new JSONObject();
		json.put("pk", result.getPk());
		json.put("action", result.getAction());
		//是否为树形。
		json.put("isTree", setting.getIsTree());
		
		rtn.setData(json);
		
		if(0== setting.getIsTree().intValue()) return rtn;
		//2.删除子表。
		Set<String> removeKeys=new HashSet<String>();
		Set<String> set= formData.keySet();
		for(String key :set){
			if(key.startsWith(SysBoEnt.SUB_PRE)){
				removeKeys.add(key);
			}
		}
		for(String key :removeKeys){
			formData.remove(key);
		}
		//3.设置主键
		formData.put(SysBoEnt.SQL_PK, result.getPk());
		//4.设置显示字段。
		String field=setting.getDisplayFields();
		
		if(StringUtil.isNotEmpty(field)){
			String val=formData.getString(field);
			formData.put("text_", val);
		}
		json.put("row", formData);
		rtn.setData(json);
		return rtn;
	}
	
	@RequestMapping(value={"remove/{alias}"},method=RequestMethod.POST)
	@ResponseBody
	public JsonResult<Void> remove(HttpServletRequest request,@PathVariable(value="alias")String alias ) throws Exception{
		String id=RequestUtil.getString(request, "id");
		String[] deleteIds = id.split(",");
		try{
			for (int i = 0; i < deleteIds.length; i++) {
				sysCustomFormSettingManager.removeTreeById(alias, deleteIds[i]);
			}
			return new JsonResult<Void>(true, "删除数据成功!");
		}
		catch(Exception ex){
			return new JsonResult<Void>(false, "删除数据失败!");
		}
    }
	
	@RequestMapping(value={"{alias}/removeById"},method=RequestMethod.POST)
	@ResponseBody
	public JsonResult<Void> removeById(HttpServletRequest request,@PathVariable(value="alias")String alias ) throws Exception{
		String id=RequestUtil.getString(request, "id");
		String[] deleteIds = id.split(",");
		try{
			for (int i = 0; i < deleteIds.length; i++) {
				sysCustomFormSettingManager.removeTreeById(alias, deleteIds[i]);
			}
			return new JsonResult<Void>(true, "删除数据成功!");
		}
		catch(Exception ex){
			return new JsonResult<Void>(false, "删除数据失败!");
		}
    }
	
	@RequestMapping("validateFieldUnique")
	@ResponseBody
	public JSONObject validateFieldUnique(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject=new JSONObject();
		String boDefId=RequestUtil.getString(request, "boDefId");
		String fieldName=RequestUtil.getString(request, "fieldName");
		String value=RequestUtil.getString(request, "value");
		String pkId=RequestUtil.getString(request, "pkId");
		
		SysBoEnt sysBoEnt=sysBoEntManager.getMainByBoDefId(boDefId);
		String tableName=sysBoEnt.getName();
		boolean isExist=sysCustomFormSettingManager.isFieldUnique(tableName, fieldName, value, pkId);
		if(isExist){
			jsonObject.put("success", false);
		}
		else{
			jsonObject.put("success", true);
		}
		return jsonObject;
	}
	
	@RequestMapping("getTreeByCat")
	@ResponseBody
	public List<SysTree> getTreeByCat (HttpServletRequest request,HttpServletResponse response){
		String catKey=RequestUtil.getString(request, "catKey");
		String tenantId=ContextUtil.getCurrentTenantId();
		List<SysTree> treeList=sysTreeManager.getByCatKeyTenantId(catKey, tenantId);
		return treeList;
	}
	
	
	@RequestMapping("getCustomFormSetting")
	@ResponseBody
	public  JsonPageResult<SysCustomFormSetting> getCustomFormSetting(HttpServletRequest request,HttpServletResponse response){
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		String treeId=RequestUtil.getString(request, "treeId");
		queryFilter.addFieldParam("treeId", treeId);
		List<SysCustomFormSetting> sysCustomFormSettings=sysCustomFormSettingManager.getAll(queryFilter);
		JsonPageResult<SysCustomFormSetting> jsonPageResult=new JsonPageResult<SysCustomFormSetting>(sysCustomFormSettings, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
	}
	
    /**
     * 获取常量。
     * @return
     */
    private JSONArray getContextVars(){
    	JSONArray contextVarAry= contextHandlerFactory.getJsonHandlers();
		//添加常量
		addVars(contextVarAry);
		//常量
		return contextVarAry;
    }
    
    private void addVars(JSONArray ary){
    	JSONObject ref=new JSONObject();
    	ref.put("key",SysBoEnt.SQL_FK);
    	ref.put("val","外键字段");
    			
    	
    	JSONObject refState=new JSONObject();
    	
    	refState.put("key",SysBoEnt.SQL_FK_STATEMENT);
    	refState.put("val","外键占位符");
    	
    	ary.add(ref);
    	ary.add(refState);
    	
    }


}
