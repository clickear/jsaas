
package com.redxun.sys.customform.manager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.bpm.api.IFlowService;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.manager.BpmInstDataManager;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.dao.mybatis.CommonDao;
import com.redxun.core.database.util.DbUtil;
import com.redxun.core.entity.SqlModel;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.script.GroovyEngine;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.ITenant;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.bo.dao.SysBoEntQueryDao;
import com.redxun.sys.bo.entity.BoResult;
import com.redxun.sys.bo.entity.SysBoEnt;
import com.redxun.sys.bo.manager.SysBoDataHandler;
import com.redxun.sys.bo.manager.SysBoEntManager;
import com.redxun.sys.customform.dao.SysCustomFormSettingDao;
import com.redxun.sys.customform.dao.SysCustomFormSettingQueryDao;
import com.redxun.sys.customform.entity.SysCustomFormSetting;

/**
 * 
 * <pre> 
 * 描述：自定义表单配置设定 处理接口
 * 作者:mansan
 * 日期:2017-05-16 10:25:38
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class SysCustomFormSettingManager extends ExtBaseManager<SysCustomFormSetting>{
	@Resource
	private SysCustomFormSettingDao sysCustomFormSettingDao;
	@Resource
	private SysCustomFormSettingQueryDao sysCustomFormSettingQueryDao;
	@Resource
	SysBoEntManager sysBoEntManager;
	@Resource
	SysBoDataHandler sysBoDataHandler;
	@Resource
	CommonDao commonDao;
	@Resource
	GroovyEngine groovyEngine;
	@Resource
    IFlowService flowService;
	@Resource
	SysBoEntQueryDao sysBoEntQueryDao;
	@Resource
	BpmInstDataManager bpmInstDataManager;
	
	@Resource
	BpmInstManager bpmInstManager;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysCustomFormSettingDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return sysCustomFormSettingQueryDao;
	}
	
	public boolean isAliasExist(SysCustomFormSetting formSetting){
		return sysCustomFormSettingQueryDao.isAliasExist(formSetting);
	} 
	
	/**
	 * 根据别名获取
	 * @param alias
	 * @return
	 */
	public SysCustomFormSetting getByAlias(String alias){
		String tenantId=ContextUtil.getCurrentTenantId();
		SysCustomFormSetting cus=sysCustomFormSettingQueryDao.getByAlias(alias, tenantId);
		if(cus==null){
			cus=sysCustomFormSettingQueryDao.getByAlias(alias, ITenant.ADMIN_TENANT_ID);
		}
		return cus;
	} 
	
	/**
	 * 根据别名获取树形数据。
	 * @param alias
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> getTreeData(String alias,String parentId) throws Exception{
		SysCustomFormSetting setting=getByAlias(alias);
		String field= setting.getDisplayFields();
		String boDefId=setting.getBodefId();
		SysBoEnt ent=sysBoEntManager.getByBoDefId(boDefId) ;
		
		//如果加载模式为懒加载且没有输入父ID设置父ID为0
		if(setting.getLoadMode().intValue()==1 && StringUtil.isEmpty(parentId)  ){
			parentId="0";
		}
		
		List<JSONObject> list=sysBoDataHandler.getData(ent, parentId);
		for(JSONObject json:list){
			String val=json.getString(field);
			json.put("text_", val);
		}
		//添加根节点。
		if("".equals(parentId) ||  parentId.equals("0")){
			JSONObject root=new JSONObject();
			root.put(SysBoEnt.SQL_PK, "0");
			root.put(SysBoEnt.SQL_FK, "-1");
			root.put("text_", setting.getName());
			list.add(0, root);
		}
		
		
		return list;
    }
	
	/**
	 * 根据别名和id删除主键。
	 * @param alias
	 * @param id
	 */
	public void removeTreeById(String alias,String id){
		SysCustomFormSetting setting=getByAlias(alias);
		if(StringUtil.isNotEmpty(setting.getDataHandler())){
			ICustomFormDataHandler handler=(ICustomFormDataHandler) AppBeanUtil.getBean(setting.getDataHandler());
			handler.delById(id);
		}else{
			String boDefId=setting.getBodefId();
			SysBoEnt ent=sysBoEntManager.getByBoDefId(boDefId) ;
			
			List<Map<String,Object>> list= getData(ent.getTableName(),id);
			for(Map<String,Object> row:list){
				String id_=(String)row.get(SysBoEnt.SQL_PK);
//				delById( ent, id_);
//				delSubById( ent, id);
				delByPkId(ent,id_);
			}
		}
		
	}
	
	private void delByPkId(SysBoEnt ent,String pkId){
		String sql="select * from " + ent.getTableName() + " where " + SysBoEnt.SQL_FK + "=#{" +SysBoEnt.SQL_FK +"}";
		SqlModel model=new SqlModel(sql);
		model.addParam(SysBoEnt.SQL_FK , pkId);
		//查出子记录
		List list=commonDao.query(model);
		if(list.size()==0){
			delById( ent, pkId);
			delSubById( ent, pkId);
		}else{
			//删除子记录
			for(int i=0;i<list.size();i++){
				Map<String,Object> obj=(Map<String,Object>)list.get(i);
				String pId=(String)obj.get(SysBoEnt.SQL_PK);
				delByPkId(ent,pId);
			}
			//删除主记录
			delById( ent, pkId);
		}
	}
	
	/**
	 * 删除子表记录。
	 * @param ent
	 * @param id
	 */
	private void delSubById(SysBoEnt ent,String id){
		if(BeanUtil.isEmpty( ent.getBoEntList())) return;
		for(SysBoEnt subEnt:ent.getBoEntList()){
			String sql=" delete from " + subEnt.getTableName() + " where " + SysBoEnt.SQL_FK + "=#{" +SysBoEnt.SQL_FK +"}";
			SqlModel model=new SqlModel(sql);
			model.addParam(SysBoEnt.SQL_FK , id);
			commonDao.execute(model);
		}
	}
	
	private void delById(SysBoEnt ent,String id){
		String sql=" delete from " + ent.getTableName() + " where " + SysBoEnt.SQL_PK + "=#{" +SysBoEnt.SQL_PK +"}";
		SqlModel model=new SqlModel(sql);
		model.addParam(SysBoEnt.SQL_PK , id);
		commonDao.execute(model);
	}
	
	private List<Map<String,Object>> getData(String tableName,String id){
		List<Map<String,Object>> rtnList=new ArrayList<Map<String,Object>>();
		Map<String,Object> row= getDataById(tableName,id);
		rtnList.add(row);
		//递归访问
		getData(tableName,id,rtnList);
		
		return rtnList;
	}
	
	private void getData(String tableName,String id,List<Map<String,Object>> rtnList){
		List<Map<String,Object>> list= getListByPid( tableName,id);
		rtnList.addAll(list);
		for(Map<String,Object> row:list){
			String pid=(String) row.get(SysBoEnt.SQL_PK);
			getData(tableName,pid,rtnList);
		}
	}
	
	private Map<String,Object> getDataById(String tableName,String id){
		String sql="select * from " + tableName + " where "+SysBoEnt.SQL_PK+"=#{"+SysBoEnt.SQL_PK+"}";
		SqlModel model=new SqlModel(sql);
		model.addParam(SysBoEnt.SQL_PK, id);
		Map<String,Object> row= (Map<String, Object>) commonDao.queryForMap(model);
		Map<String,Object> rtnRow= convertMapKey(row);
		return rtnRow;
	} 
	
	private List<Map<String,Object>> getListByPid(String tableName,String pid){
		String sql="select * from " + tableName + " where "+SysBoEnt.SQL_FK+"=#{"+SysBoEnt.SQL_PK+"}";
		SqlModel model=new SqlModel(sql);
		model.addParam(SysBoEnt.SQL_PK, pid);
		List<Map<String,Object>> list= (List<Map<String,Object>>) commonDao.query(model);
		List<Map<String,Object>> rtnList=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> row: list){
			Map<String,Object> rtnRow= convertMapKey(row);
			rtnList.add(rtnRow);
		}
		return rtnList;
	} 
	
	private Map<String,Object> convertMapKey(Map<String,Object> row) {
		Map<String,Object> rtnMap=new HashMap<String, Object>();
		for (Map.Entry<String, Object> ent : row.entrySet()) {  
			rtnMap.put(ent.getKey().toUpperCase(), ent.getValue());
		}  
		return rtnMap;
		
	}
	
	/**
	 * 保存JSON数据。
	 * @param alias
	 * @param jsonData
	 * @return
	 */
	public BoResult saveData(SysCustomFormSetting formSetting,JSONObject jsonData){
		JSONObject formData=jsonData.getJSONObject("formData");
		
		//1.执行前置脚本
		String preScript=formSetting.getPreJavaScript();
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("data", formData);
		if(StringUtil.isNotEmpty(preScript)){
			groovyEngine.executeScripts(preScript, params);
		}
		
		String boDefId=formSetting.getBodefId();
		//2.执行数据保存。
		BoResult result=null;
		String formDataHandler=formSetting.getDataHandler();
		if(StringUtil.isNotEmpty(formDataHandler)){
			ICustomFormDataHandler dataHandler=(ICustomFormDataHandler) AppBeanUtil.getBean(formDataHandler);
			result=dataHandler.save(boDefId,formData);
		}
		else{
			SysBoEnt ent=sysBoEntManager.getByBoDefId(boDefId) ;
			result= sysBoDataHandler.handleData(ent, formData);
		}
		
		if(StringUtil.isNotEmpty(formSetting.getSolId())){
			//3.启动流程。
			startFlow(formSetting, jsonData, result);
		}
		//4.执行后置脚本
		String afterScript=formSetting.getAfterJavaScript();
		if(StringUtil.isNotEmpty(afterScript)){
			params.put("result_", result);
			
			groovyEngine.executeScripts(afterScript, params);
		}
		return result;
	}
	
	/**
	 * 启动流程。
	 * @param formSetting
	 * @param jsonData
	 * @param result
	 */
	private void startFlow(SysCustomFormSetting formSetting,JSONObject jsonData,BoResult result){
		
		
		
		String solId=formSetting.getSolId();
		if(StringUtil.isEmpty(solId)) return;
		
		String formDataHandler=formSetting.getDataHandler();
		JSONObject setting=jsonData.getJSONObject("setting");
		//启动流程
		String action=setting.getString("action");
		boolean start=action.equals("startFlow");
		//取得流程实例，如果为不启动流程并且实例存在则返回。
		BpmInst bpmInst=bpmInstManager.getByBusKey(result.getPk());
		if(!start && bpmInst!=null){
			return;
		}
		
		String boDefId=formSetting.getBodefId();
		JSONObject cmdJsonData=new JSONObject();
		
		JSONObject boJson=new JSONObject();
		boJson.put("boDefId",formSetting.getBodefId());
		boJson.put("formKey", formSetting.getFormAlias());
		boJson.put("readonly", "false");
		boJson.put("data", jsonData.getJSONObject("formData"));
		JSONArray bos=new JSONArray();
		bos.add(boJson);
		cmdJsonData.put("bos", bos);
		
		String userId=ContextUtil.getCurrentUserId();
		JsonResult<BpmInst> jsonResult= flowService.startProcess(userId,solId,result.getPk(),cmdJsonData.toJSONString(),"",start,"customForm");
		if(StringUtil.isEmpty(formDataHandler)){
			bpmInst=jsonResult.getData();
			//更新表单数据。
			updFormData( bpmInst, result);
			if(result.getAction().equals(BoResult.ACTION_TYPE.ADD)) {
				//增加
				bpmInstDataManager.addBpmInstData(boDefId, result.getPk(), bpmInst.getInstId());
			}
		}
			
	}
	
	/**
	 * 更新表单数据。
	 * @param bpmInst
	 * @param result
	 */
	private void updFormData(BpmInst bpmInst,BoResult result){
		SysBoEnt boEnt=result.getBoEnt();
		String pk=result.getPk();
		
		String sql="update " + boEnt.getTableName() +" set " 
				+SysBoEnt.FIELD_INST +"=#{" + SysBoEnt.FIELD_INST +"},"
				+SysBoEnt.FIELD_INST_STATUS_ +"=#{" + SysBoEnt.FIELD_INST_STATUS_ +"}"
				+" where " + SysBoEnt.SQL_PK + "=#{" + SysBoEnt.SQL_PK +"}";
		//draft(草稿),runing(运行),complete(完成)
		String status=BpmInst.STATUS_DRAFTED;
		if(StringUtil.isNotEmpty(bpmInst.getActInstId())){
			status=BpmInst.STATUS_RUNNING;
		}
				
		SqlModel model=new SqlModel(sql);
		model.addParam(SysBoEnt.SQL_PK, pk);
		model.addParam(SysBoEnt.FIELD_INST, bpmInst.getInstId());
		model.addParam(SysBoEnt.FIELD_INST_STATUS_, status);
		
		commonDao.execute(model);
		
	}
	
	/**
	 * 判断字段唯一性。
	 * @param tableName
	 * @param fieldName
	 * @param value
	 * @param pk
	 * @return
	 */
	public boolean isFieldUnique(String tableName,String fieldName,String value,String pk){
		String sql="select count(*) from "+ DbUtil.getTablePre()  +tableName+" where "+ DbUtil.getColumnPre() +fieldName  +"=#{"+ fieldName  +"}" ;
		if(StringUtil.isNotEmpty(pk)){
			sql+=" and " + SysBoEnt.SQL_PK +"!=#{"+ SysBoEnt.SQL_PK +"}";
		}
		SqlModel model=new SqlModel(sql);
		model.addParam(fieldName, value);
		if(StringUtil.isNotEmpty(pk)){
			model.addParam(SysBoEnt.SQL_PK, pk);
		}
		String comrtn=commonDao.queryOne(model).toString();
		Integer rtn=Integer.parseInt(comrtn);
		return rtn>0;
		
	}
	
	public String getTableRightJson(SysCustomFormSetting setting){
		String rightJson=setting.getTableRightJson();
    	String boDefId = setting.getBodefId();
    	SysBoEnt boEnt= sysBoEntManager.getByBoDefId(boDefId, false);
    
    	JSONObject rtnJson=new JSONObject();
    	if(boEnt==null){
    		return rtnJson.toJSONString();
    	}
    	List<SysBoEnt> subList=boEnt.getBoEntList();
    	if(BeanUtil.isEmpty(subList)) return "{}";    	
    	
    	if(StringUtil.isEmpty(rightJson)){
    		for(SysBoEnt ent:subList){
        		String name=ent.getName();        	
        		JSONObject typeJson=new JSONObject();
        		typeJson.put("type", "all");
        		typeJson.put("comment", ent.getComment());
        		rtnJson.put(name, typeJson);
    		}
    		return rtnJson.toJSONString();
    	}
    	JSONObject orginJson=JSONObject.parseObject(rightJson);
    	 
    	for(SysBoEnt ent:subList){
    		String name=ent.getName();
    		if(!orginJson.containsKey(name)){
    			JSONObject typeJson=new JSONObject();
    			typeJson.put("type", "all");
    			typeJson.put("comment", ent.getComment());
    			rtnJson.put(name, typeJson);
    		}
    		else{
    			JSONObject json=orginJson.getJSONObject(name);
    			json.put("comment", ent.getComment());		
    			rtnJson.put(name, json);
    		}
    	}
		return rtnJson.toJSONString();
	}
	
	public String getTableRightJsonByBoDefId(String boDefId){
		JSONObject rtnJson=new JSONObject();
		SysBoEnt boEnt= sysBoEntManager.getByBoDefId(boDefId, false);
		List<SysBoEnt> subList=boEnt.getBoEntList();
    	if(BeanUtil.isEmpty(subList)) return "{}";   
    	for(SysBoEnt ent:subList){
    		String name=ent.getName();        	
    		JSONObject typeJson=new JSONObject();
    		typeJson.put("type", "all");
    		typeJson.put("comment", ent.getComment());
    		rtnJson.put(name, typeJson);
		}
		return rtnJson.toJSONString();
	}
	
}
