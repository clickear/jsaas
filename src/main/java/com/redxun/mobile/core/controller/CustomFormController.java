package com.redxun.mobile.core.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.bpm.activiti.util.ProcessHandleHelper;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.manager.BpmFormRightManager;
import com.redxun.bpm.form.entity.BpmMobileForm;
import com.redxun.bpm.form.entity.FormModel;
import com.redxun.bpm.form.entity.FormViewRight;
import com.redxun.bpm.form.impl.formhandler.FormUtil;
import com.redxun.bpm.form.manager.BpmMobileFormManager;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.context.ProfileUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.bo.entity.SysBoEnt;
import com.redxun.sys.core.dao.SysMenuQueryDao;
import com.redxun.sys.core.entity.SysMenu;
import com.redxun.sys.customform.entity.SysCustomFormSetting;
import com.redxun.sys.customform.manager.SysCustomFormSettingManager;

@Controller
@RequestMapping("/mobile/custom/")
public class CustomFormController {


	@Resource
	SysMenuQueryDao sysMenuQueryDao ; 
	
	@Resource
	SysCustomFormSettingManager sysCustomFormSettingManager;
	@Resource
	BpmMobileFormManager bpmMobileFormManager;
	@Resource
	BpmFormRightManager bpmFormRightManager;
	
	
	/**
	 * 获取表单过程。
	 * 1.根据别名获取SysCustomFormSetting对象。
	 * 2.获取手机表单名称。
	 * 3.获取手机表单的html。
	 * 4.获取表单权限。
	 * 5.获取表单数据。 
	 *
	 * @param alias
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"form/{alias}/{id}"})
	public JSONObject getFormModel( @PathVariable(value="alias")String alias,
				@PathVariable(value="id")String id){
		JSONObject json=new JSONObject();
		
		
		FormModel formModel=new FormModel();
		formModel.setResult(false);
		String tenantId=ContextUtil.getCurrentTenantId();
		SysCustomFormSetting setting=sysCustomFormSettingManager.getByAlias(alias);
		if(setting==null){
			json.put("msg", "没有找到表单设定!");
			json.put("result", false);
			return json;
		}
		
		String mobileAlias=setting.getMobileFormAlias();
		BpmMobileForm mobileForm= bpmMobileFormManager.getByAlias(mobileAlias);
		if(mobileForm==null){
			json.put("msg", "没有设置手机表单!");
			json.put("result", false);
			return json;
		}
		
		//设置子表权限。
		ProcessHandleHelper.clearObjectLocal();
		ProcessHandleHelper.setObjectLocal(setting.getTableRightJson());
		
		JSONObject jsonData= FormUtil.getData(setting.getBodefId(),id);
		
		
		formModel.setBoDefId(setting.getBodefId());
		formModel.setContent(mobileForm.getFormHtml());
		formModel.setJsonData(jsonData);
		JSONObject permission= getPermission(setting.getFormAlias(),tenantId,false);
		formModel.setPermission(JSONObject.toJSONString(permission));
		
		formModel.setResult(true);
		
		boolean canStartFlow=canStartFlow(setting,jsonData);
		
		json.put("canStartFlow",canStartFlow);
		json.put("formModel",formModel);
		json.put("result",true);
		
		return json;
	}
	
	/**
	 * 是否能启动流程。
	 * @param setting
	 * @param jsonData
	 * @return
	 */
	private boolean canStartFlow(SysCustomFormSetting setting,JSONObject jsonData){
		if(StringUtil.isEmpty(setting.getSolId())){
			return false;
		}
		String status=jsonData.getString(SysBoEnt.FIELD_INST_STATUS_);
		if(StringUtil.isEmpty(status)) return true;
		
		if(BpmInst.STATUS_DRAFTED.equals(status)) return true;
		
		return false;
		
	}
	
	private JSONObject getPermission(String formKey,String tenantId,boolean readOnly){
		Map<String, Set<String>> profileMap=ProfileUtil.getCurrentProfile();
		JSONObject rightSetting=FormUtil.getRightByForm(formKey);
		JSONObject permisson= bpmFormRightManager.calcRights(rightSetting, profileMap, readOnly);
		return permisson;
	}
	
	/**
	 * 返回有权限的自定义列表。
	 * 自定义表格式：
	 * [{
	 * 		name:"",
	 * 		key:"",
	 * 		childs:[
	 * 			{MENU_ID_:"",PARENT_ID_:"",NAME_:"",BO_LIST_ID_:"",KEY_:""},
	 * 			{MENU_ID_:"",PARENT_ID_:"",NAME_:"",BO_LIST_ID_:"",KEY_:""}
	 * 		]
	 * 	}]
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"getMyMobileList"})
	public JSONArray getMyMobileList(HttpServletRequest request){
		String userId=ContextUtil.getCurrentUserId();
		String tenantId=ContextUtil.getCurrentTenantId();
		
		List<Map<String,Object>> menus= sysMenuQueryDao.getBoMenuByUserId(tenantId, userId);
		JSONObject menuObj=new JSONObject();
		for(Map<String,Object> menu:menus){
			//m.MENU_ID_ MENUID,m.PARENT_ID_ ,m.NAME_,m.BO_LIST_ID_,boList.KEY_
			String parentId=(String)menu.get("PARENT_ID_");
			if(menuObj.containsKey(parentId)){
				List<Map<String,Object>> list= menuObj.getObject(parentId, List.class);
				list.add(menu);
			}
			else{
				List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
				list.add(menu);
				menuObj.put(parentId, list);
			}
		}
		
		JSONArray jsonAry=new JSONArray();
		Set<String> set= menuObj.keySet();
		for(Iterator<String> it=set.iterator();it.hasNext();){
			String menuId=it.next();
			SysMenu menu=sysMenuQueryDao.get(menuId);
			if(menu==null) continue;
			JSONObject level1=new JSONObject();
			level1.put("name", menu.getName());
			level1.put("key", menu.getKey());
			level1.put("childs", menuObj.getObject(menuId, List.class));
			jsonAry.add(level1);
		}
		
		return jsonAry;
		
	}
	
}
