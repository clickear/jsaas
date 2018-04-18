package com.redxun.sys.bo.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.druid.proxy.jdbc.ClobProxyImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.dao.mybatis.CommonDao;
import com.redxun.core.database.api.model.Column;
import com.redxun.core.database.util.DbUtil;
import com.redxun.core.entity.SqlModel;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.DateUtil;
import com.redxun.core.util.FileUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IGroup;
import com.redxun.org.api.service.GroupService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.bo.entity.BoResult;
import com.redxun.sys.bo.entity.BoResult.ACTION_TYPE;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.entity.SysBoDef;
import com.redxun.sys.bo.entity.SysBoEnt;

@Service
public class SysBoDataHandler {
	
	@Resource
	CommonDao commonDao;
	@Resource 
	ISqlBuilder iSqlBuilder;
	@Resource
	GroupService groupService;
	
	
	
	/**
	 * JSON的结构如下：
	 * {
	 * 	name:"ray",
	 * 	age:"29",
	 * 	SUB_school:
	 * 	[
	 * 		{grade:"小学",year:"2000"},
	 * 		{grade:"中学",year:"2006"},
	 * 	]
	 * 
	 * }
	 * 
	 * @param ent
	 * @param jsonData
	 * @return
	 */
	public BoResult handleData(SysBoEnt ent,JSONObject jsonData){
		String userId=ContextUtil.getCurrentUserId();
		IGroup group= groupService.getMainByUserId(userId);
		String groupId="";
		if(group!=null){
			groupId=group.getIdentityId();
		}
		
		List<BoResult> list=null;
		//判断数据是增加还是删除。
		BoResult result= isAdd( jsonData);
		result.setBoEnt(ent);
		//添加数据
		if(ACTION_TYPE.ADD.equals(result.getAction())){
			String parentId=getParentId(jsonData);
			CommonField field=new CommonField(result.getPk(), parentId,"0", userId, groupId);
			list=handAdd(field, ent, jsonData);
		}else{//更新数据
			CommonField field=new CommonField(result.getPk(), result.getPk(),"0", userId, groupId);
			list=handUpd(field,ent, jsonData);
		}
		
		return result;
		
	}
	
	/**
	 * 处理数据添加。
	 * @param pk			主键
	 * @param parentId		外键数据
	 * @param ent			实体数据
	 * @param jsonData		实例数据
	 * @return
	 */
	private List<BoResult> handAdd(CommonField field, SysBoEnt ent,JSONObject jsonData){
		List<BoResult> resultList=new ArrayList<BoResult>();
		
		BoResult mainResult=handInsert(field,"0",ent, jsonData,resultList);
		mainResult.setMain(true);
		resultList.add(mainResult);
		//没有子表数据直接返回。
		if(BeanUtil.isEmpty(ent.getBoEntList())) return resultList;
		
		String pk=field.getPk();

		//处理子表的数据。
		for(SysBoEnt subEnt:ent.getBoEntList()){
			String name=subEnt.getName();
			String key=SysBoEnt.SUB_PRE + name;
			//没有子表数据时跳过。
			if(!jsonData.containsKey(key)) continue;
			
			JSONArray jsonAry=jsonData.getJSONArray(key);
			//批量插入子表数据。
			field.setRefId(pk);
			
			handInsertList(field,"0", subEnt, jsonAry,resultList);
		}
		return resultList;
	}
	
	private void addCommonFields(List<String> fieldNameList,List<String> valNameList,
			Map<String,Object> params,CommonField field ,JSONObject jsonObj,SysBoEnt ent
			){
		fieldNameList.add(SysBoEnt.SQL_PK);
		valNameList.add(getParamsName(SysBoEnt.SQL_PK,Column.COLUMN_TYPE_VARCHAR));
		params.put(SysBoEnt.SQL_PK, field.getPk());
		//外键
		fieldNameList.add(SysBoEnt.SQL_FK);
		valNameList.add(getParamsName(SysBoEnt.SQL_FK,Column.COLUMN_TYPE_VARCHAR));
		params.put(SysBoEnt.SQL_FK, field.getRefId());
		
		if("YES".equals(ent.getTree())){
			fieldNameList.add(SysBoEnt.FIELD_PARENTID);
			valNameList.add(getParamsName(SysBoEnt.FIELD_PARENTID,Column.COLUMN_TYPE_VARCHAR));
			params.put(SysBoEnt.FIELD_PARENTID, field.getParentId());
		}
		
		
		//user
		fieldNameList.add(SysBoEnt.FIELD_USER);
		valNameList.add(getParamsName(SysBoEnt.FIELD_USER,Column.COLUMN_TYPE_VARCHAR));
		params.put(SysBoEnt.FIELD_USER, field.getUserId());
		//用户组
		fieldNameList.add(SysBoEnt.FIELD_GROUP);
		valNameList.add(getParamsName(SysBoEnt.FIELD_GROUP,Column.COLUMN_TYPE_VARCHAR));
		params.put(SysBoEnt.FIELD_GROUP, field.getGroupId());
		//创建时间
		fieldNameList.add(SysBoEnt.FIELD_CREATE_TIME);
		valNameList.add(getParamsName(SysBoEnt.FIELD_CREATE_TIME,Column.COLUMN_TYPE_DATE));
		params.put(SysBoEnt.FIELD_CREATE_TIME, new Date());
		//更新时间
		fieldNameList.add(SysBoEnt.FIELD_UPDTIME_TIME);
		valNameList.add(getParamsName(SysBoEnt.FIELD_UPDTIME_TIME,Column.COLUMN_TYPE_DATE));
		params.put(SysBoEnt.FIELD_UPDTIME_TIME,  new Date());
		//租户ID
		String tenantId=ContextUtil.getCurrentTenantId();
		fieldNameList.add(SysBoEnt.FIELD_TENANT);
		valNameList.add(getParamsName(SysBoEnt.FIELD_TENANT,Column.COLUMN_TYPE_VARCHAR));
		params.put(SysBoEnt.FIELD_TENANT,  tenantId);
		
		//插入实例ID
		if(jsonObj.containsKey(SysBoEnt.FIELD_INST)){
			String instId=jsonObj.getString(SysBoEnt.FIELD_INST);
			fieldNameList.add(SysBoEnt.FIELD_INST);
			valNameList.add(getParamsName(SysBoEnt.FIELD_INST,Column.COLUMN_TYPE_VARCHAR));
			params.put(SysBoEnt.FIELD_INST,  instId);
		}
		//插入实例状态
		if(jsonObj.containsKey(SysBoEnt.FIELD_INST_STATUS_)){
			String status=jsonObj.getString(SysBoEnt.FIELD_INST_STATUS_);
			fieldNameList.add(SysBoEnt.FIELD_INST_STATUS_);
			valNameList.add(getParamsName(SysBoEnt.FIELD_INST_STATUS_,Column.COLUMN_TYPE_VARCHAR));
			params.put(SysBoEnt.FIELD_INST_STATUS_,  status);
		}
		
	}
	
	/**
	 * 处理插入一行记录。
	 * @param pk			主键
	 * @param parentId		外键
	 * @param ent			对应的实体
	 * @param jsonData		对应的一行数据
	 * @return
	 */
	private BoResult handInsert(CommonField field,String parentId, SysBoEnt ent,JSONObject jsonData,List<BoResult> list){
		field.setParentId(parentId);
		//字段列表  name,amount
		List<String> fieldNameList=new ArrayList<String>();
		//值列表 #{name},#{amount}
		List<String> valNameList=new ArrayList<String>();
		// 值和数据。
		Map<String,Object> params=new HashMap<String,Object>();
		
		//添加基础字段。
		addCommonFields(fieldNameList,valNameList,params,field,jsonData,ent);
		
		List<SysBoAttr> attrs= ent.getSysBoAttrs();
		for(SysBoAttr attr:attrs){
			//这种字段表示数据库不存在。
			if(!SysBoDef.VERSION_BASE.equals( attr.getStatus() )) continue;
			
			String name=attr.getName() ;
			//判断数据是否存在
			if(!jsonData.containsKey(name)) continue;
			
			boolean isSingle=attr.single();
			
			handField(fieldNameList,valNameList,params,attr,true,jsonData);
			//处理复合字段。
			if(!isSingle){
				handField(fieldNameList,valNameList,params,attr,false,jsonData);
			}
		}
		
		String sql="insert into " + ent.getTableName() + "(" +
				StringUtil.join(fieldNameList, ",") +") values (" + 
				StringUtil.join(valNameList, ",") + ")";
		commonDao.execute(sql, params);
		
		if("YES".equals(ent.getTree()) && jsonData.containsKey("children")){
			JSONArray jsonAry=jsonData.getJSONArray("children");
			if(jsonAry.size()>0){
				handInsertList(field,field.getPk(), ent, jsonAry, list);
			}
		}
		
		BoResult result=new BoResult(field.getPk(),ACTION_TYPE.ADD,ent);
		
		return result;
	}
	
	private void handField(List<String> fieldNameList,
			List<String> valNameList,Map<String,Object> params,SysBoAttr attr,
			boolean isSingle,JSONObject jsonData){
		String name=attr.getName();
		String fieldName=attr.getFieldName();
		String tmpName=isSingle?name : name + SysBoEnt.COMPLEX_NAME;
		String tmpFieldName=isSingle?fieldName : fieldName + SysBoEnt.COMPLEX_NAME;
		
		fieldNameList.add(tmpFieldName);
		valNameList.add(getParamsName(tmpName,attr.getDataType()));
		
		//计算传入的值，对数据进行转换操作。
		Object val=getVal( attr, jsonData,isSingle);
		params.put(tmpName, val);
		
	}
	
	/**
	 * 根据属性配置对数据值进行类型转换。
	 * @param attr
	 * @param jsonData
	 * @return
	 */
	private Object getVal(SysBoAttr attr,JSONObject jsonData,boolean isSingle){
		String name=isSingle?attr.getName() : attr.getName() +SysBoEnt.COMPLEX_NAME.toLowerCase() ;
		String dataType=attr.getDataType();
		String val=jsonData.getString(name);
		
		if("mini-time".equals(attr.getControl())){
			return handTime(attr,val);
		}
		
		if("mini-month".equals(attr.getControl())){
			return handMonth(attr,val);
		}
		
		if(Column.COLUMN_TYPE_VARCHAR.equals(dataType) || Column.COLUMN_TYPE_CLOB.equals(dataType)){
			return val;
		}
		else if(Column.COLUMN_TYPE_NUMBER.equals(dataType)){
			return handNumber(attr,val);
		}
		else if(Column.COLUMN_TYPE_DATE.equals(dataType)){
			return handDate(attr,val);
		}
		return val;
	}
	
	/**
	 * 时间处理
	 * @param attr
	 * @param val
	 * @return
	 */
	private  String handTime(SysBoAttr attr,String val){
		if(StringUtil.isEmpty(val)) return val;
		val=val.replace("T"," ");
		if(val.length()==8) return val;
		
		Date date=DateUtil.parseDate(val,DateUtil.DATE_FORMAT_FULL);
		SimpleDateFormat format=new SimpleDateFormat(attr.getPropByName("format"));
		String rtn= format.format(date);
		return rtn;
		
	}
	
	/**
	 * 月份处理
	 * @param attr
	 * @param val
	 * @return
	 */
	private  String handMonth(SysBoAttr attr,String val){
		if(StringUtil.isEmpty(val)) return val;
		if(val.indexOf("T")==-1 && val.length()==7){
			return val;
		}
		val=val.replace("T"," ");
		
		Date date=DateUtil.parseDate(val);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
		return format.format(date);
	}
	
	/**
	 * 对数字类型的数据进行转换。
	 * @param attr
	 * @param val
	 * @return
	 */
	private Object handNumber(SysBoAttr attr,String val){
		if(StringUtil.isEmpty(val)) return null;
		val=val.trim();
		int length=attr.getLength();
		int decimalLen=attr.getDecimalLength();
		if(decimalLen==0){
			if(length<10){
				return Integer.parseInt(val);
			}
			else{
				return Long.parseLong(val);
			}
		}
		else{
			return Float.parseFloat(val);
		}
	}
	
	/**
	 * 处理日期，将数据转成日期类型。
	 * @param attr
	 * @param val
	 * @return
	 */
	private Object handDate(SysBoAttr attr,String val){
		if(StringUtil.isEmpty(val)) return null;
		val=val.replace("T", " ");
		//没有格式数据。
		if(StringUtil.isEmpty(attr.getExtJson())){
			return DateUtil.parseDate(val);
		}
		//有格式数据。
		else{
			String format="";
			String json=attr.getExtJson();
			if(StringUtil.isNotEmpty(json)){
				JSONObject jsonObj=JSONObject.parseObject(attr.getExtJson());
				if(jsonObj!=null && jsonObj.containsKey("format")){
					format=jsonObj.getString("format");
				}
			}
			if(val.length()==16){
				val=val+":00";
			}
			Object rtn=DateUtil.parseDate(val, format);
			return rtn;
		}
	}
	
	/**
	 *  这里是将 数据转成mybaits 的显示形式。
	 *  #{name}
	 * @param name
	 * @return
	 */
	private String getParamsName(String name,String type){

		String dataType="jdbcType=VARCHAR";
		if(Column.COLUMN_TYPE_VARCHAR.equals(type)){
			dataType="jdbcType=VARCHAR";
		}
		else if(Column.COLUMN_TYPE_DATE.equals(type)){
			dataType="jdbcType=TIMESTAMP";
		}
		else if(Column.COLUMN_TYPE_NUMBER.equals(type)){
			dataType="jdbcType=NUMERIC";
		}
		else if (Column.COLUMN_TYPE_CLOB.equals(type)){
			dataType="jdbcType=CLOB";
		}
		
		return "#{" + name +","+dataType+"}";
	}
	
	
	
	/**
	 * 对提交json对数据 进行更新。
	 * @param pk
	 * @param ent
	 * @param jsonData
	 * @return
	 */
	private List<BoResult> handUpd(CommonField field, SysBoEnt ent,JSONObject jsonData){
		List<BoResult> list=new ArrayList<BoResult>();
		//1.对主表数据进行更新。
		handUpdRow(field,jsonData, ent,list);
		
		if(BeanUtil.isEmpty(ent.getBoEntList())) return null;
		//获取原来数据，对数据进行更新。
		JSONObject originJson=getDataByPk(field.getPk(), ent);
		
		List<SysBoEnt> subList=ent.getBoEntList();
		
		List<BoResult> resultList=new ArrayList<BoResult>();
		//2.对子表数据进行处理。
		for(SysBoEnt subEnt:subList){
			String name=subEnt.getName();
			String key=SysBoEnt. SUB_PRE + name;
			//判断提交的子表数据是否存在，如果不存在则不处理。
			if(!jsonData.containsKey(key)) continue;
			
			//获取提交的列表数据
			JSONArray subJsonList=jsonData.getJSONArray(key);
			//获取之前保存的列表数据。
			JSONArray originSubJsonList=originJson.getJSONArray(key);
			//对列表数据进行维护，主要是增查改删。
			
			handUpd( field, subJsonList, originSubJsonList, resultList,subEnt);
		}
		
		return resultList;
	}
	
	/**
	 * 对数据进行增删改处理。
	 * @param pk
	 * @param subJsonList
	 * @param originSubJsonList
	 * @param resultList
	 * @param ent
	 */
	private void handUpd(CommonField field,JSONArray subJsonList,JSONArray originSubJsonList,List<BoResult> resultList,SysBoEnt ent){
		//1.处理删除的数据
		handDel(subJsonList, originSubJsonList, resultList, ent);
		
		//2.处理更新的数据。
		for(int i=0;i<subJsonList.size();i++){
			JSONObject jsonObj=subJsonList.getJSONObject(i);
			BoResult boResult= isAdd(jsonObj);
			if(BoResult.ACTION_TYPE.ADD.equals(boResult.getAction())){
				field.setPk(IdUtil.getId());
				handInsert(field,"0", ent, jsonObj, resultList);
			}
			else{
				handUpdRow(field, jsonObj, ent, resultList);
			}
			
		}
	}
	
	/**
	 * 处理删除的数据,提交的数据中没有，保存有的数据中有，就是需要删除的数据。
	 * 需要遍历原来的数据，看在新的数据中是否存在，不存在则删除。
	 * 先对新的数据建立map，再对原有数据进行遍历，看数据能新的数据中是否存在，不存在则表示需要删除。
	 * @param subJsonList
	 * @param originSubJsonList
	 * @param resultList
	 * @param ent
	 */
	private void handDel(JSONArray subJsonList,JSONArray originSubJsonList,List<BoResult> resultList,SysBoEnt ent){
		if(BeanUtil.isEmpty(originSubJsonList)) return;
		
		Set<String> pkSet=getPkSet(subJsonList);
		for(int i=0;i<originSubJsonList.size();i++){
			JSONObject json=originSubJsonList.getJSONObject(i);
			String pk=getPk(json);
			if(!pkSet.contains(pk)){
				BoResult result=delByPK(pk,ent);
				resultList.add(result);
			}
		}
	}
	
	/**
	 * 获取主键数据。
	 * @param json
	 * @return
	 */
	private String getPk(JSONObject json){
		return json.getString(SysBoEnt.SQL_PK);
	}

	/**
	 * 从上下文查找父ID。
	 * @param json
	 * @return
	 */
	private String getParentId(JSONObject json){
		if(json.containsKey(SysBoEnt.SQL_FK)){
			return json.getString(SysBoEnt.SQL_FK);
		}
		return "0";
	}
	
	/**
	 * 删除一行数据。
	 * @param pk	主键
	 * @param ent	实体数据
	 * @return
	 */
	private BoResult delByPK(String pk,SysBoEnt ent){
		
		String sql="delete from " + ent.getTableName() + " where " + SysBoEnt.SQL_PK + "=#{"+SysBoEnt.SQL_PK+"}";
		Map<String,Object> params=new HashMap<String, Object>();
		params.put(SysBoEnt.SQL_PK, pk);
		commonDao.execute(sql,params);
		
		BoResult result=new BoResult(pk,ACTION_TYPE.DELETE,ent);
		return result;
		
	}
	
	
	
	/**
	 * 获取主键数据set。
	 * @param subJsonList
	 * @return
	 */
	private Set<String> getPkSet(JSONArray subJsonList){
		Set<String> set=new HashSet<String>();
		for(int i=0;i<subJsonList.size();i++){
			JSONObject json=subJsonList.getJSONObject(i);
			getPkByJson(json,set);
		}
		return set;
	}
	
	private void getPkByJson(JSONObject json,Set<String> set){
		String pk=getPk(json);
		set.add(pk);
		
		JSONArray ary= json.getJSONArray("children");
		
		if(ary==null || ary.size()==0) return;
		for(int i=0;i<ary.size();i++){
			JSONObject o=ary.getJSONObject(i);
			getPkByJson(o,set);
		}
		
	}
	
	/**
	 * 处理添加的列表。
	 * @param pk
	 * @param boEnt
	 * @param jsonAry
	 * @param list
	 */
	private void handInsertList(CommonField field,String parentId,SysBoEnt boEnt, JSONArray jsonAry,List<BoResult> list){
		if(BeanUtil.isEmpty(jsonAry)) return ;
		
		for(Object obj:jsonAry){
			JSONObject subRow=(JSONObject) obj;
			String id=IdUtil.getId();
			field.setPk(id);
			BoResult  result= handInsert(field,parentId, boEnt,subRow,list);
			result.setMain(false);
			list.add(result);
		}
	}
	
	
	
	/**
	 * 处理一行数据更新。
	 * @param curJson
	 * @param ent
	 * @return
	 */
	private BoResult handUpdRow(CommonField field, JSONObject curJson,SysBoEnt ent,List<BoResult> resultList){
		String pk=getPk(curJson);
		
		List<String> updList=new ArrayList<String>();
		Map<String,Object> params=new HashMap<String,Object>();
		
		//添加更新时间字段
		updList.add(SysBoEnt.FIELD_UPDTIME_TIME +"=" + getParamsName(SysBoEnt.FIELD_UPDTIME_TIME,Column.COLUMN_TYPE_DATE));
		params.put(SysBoEnt.FIELD_UPDTIME_TIME , new Date());
		
		List<SysBoAttr> attrs=ent.getSysBoAttrs();
		for(SysBoAttr attr:attrs){
			if(!SysBoDef.VERSION_BASE.equals( attr.getStatus())) continue;
			
			String name=attr.getName() ;
			if(!curJson.containsKey(name)) continue;
			
			boolean isSingle=attr.single();
			
			updList.add(attr.getFieldName() +"=" + getParamsName(name,attr.getDataType()));
			Object val=getVal(attr,curJson,true);
			params.put(name, val);
			
			if(!isSingle){
				updList.add(attr.getFieldName() +SysBoEnt.COMPLEX_NAME +"=" + getParamsName(name +SysBoEnt.COMPLEX_NAME,attr.getDataType()));
				Object valName=getVal(attr,curJson,false);
				params.put(name +SysBoEnt.COMPLEX_NAME, valName);
			}
		}
		//这里表示没有需要更新的字段。
		if(BeanUtil.isEmpty(updList)) return null;
		
		
		if("YES".equals(ent.getTree()) && curJson.containsKey("children")){
			JSONArray jsonAry=curJson.getJSONArray("children");
			JSONArray newAry=new JSONArray();
			for(Object o:jsonAry){
				JSONObject jsonObj=(JSONObject)o;
				BoResult boResult= isAdd(jsonObj);
				if(BoResult.ACTION_TYPE.ADD.equals(boResult.getAction())){
					newAry.add(jsonObj);
				}
				else{
					handUpdRow(field,jsonObj, ent, resultList);
				}
			}
			if(newAry.size()>0){
				handInsertList(field,pk, ent, newAry, resultList);
			}
		}
		
		String sql="update " + ent.getTableName() +" set " + StringUtil.join(updList, ",") +
				" where " + SysBoEnt.SQL_PK +"=#{"+SysBoEnt.SQL_PK +"}";
		
		params.put(SysBoEnt.SQL_PK, pk);
		commonDao.execute(sql, params);
		
		BoResult result=new BoResult(pk,ACTION_TYPE.UPDATE,ent);
		
		resultList.add(result);
		
		return result;
	}
	
	/**
	 * 判断当前是添加还是更新。
	 * @param jsonData
	 * @return
	 */
	public BoResult isAdd(JSONObject jsonData){
		BoResult result=new BoResult();
		String pkName=SysBoEnt.SQL_PK;
		String pk="";
		result.setAction(ACTION_TYPE.ADD);
		if(jsonData.containsKey(pkName)){
			pk=jsonData.getString(pkName);
			if(StringUtil.isNotEmpty(pk)){
				result.setAction(ACTION_TYPE.UPDATE);
				result.setPk(pk);
			}
			else{
				result.setPk(IdUtil.getId());
			}
		}
		else{
			result.setPk(IdUtil.getId());
		}
		
		return result;
	}
	

	
	/**
	 * 根据主键获取存储在数据库表中的数据。
	 * @param pk
	 * @param boEnt
	 * @return
	 */
	public JSONObject getDataByPk(String pk,SysBoEnt boEnt){
		//获取主表json数据
		JSONObject jsonObj=getByPk( pk, boEnt);
		
		List<SysBoEnt> list= boEnt.getBoEntList();
		
		if(BeanUtil.isEmpty(list)) return jsonObj;
		
		for(SysBoEnt subEnt:list){
			//根据外键获取数据。
			JSONArray jsonAry= getByFk( pk, subEnt);
			jsonObj.put(SysBoEnt.SUB_PRE +  subEnt.getName(), jsonAry);
		}
		return jsonObj;
		
	}
	
	
	/**
	 * 根据外键获取表数据。
	 * @param fk
	 * @param boEnt
	 * @return
	 */
	private JSONArray getByFk(String fk,SysBoEnt boEnt){
		String columnPre=DbUtil.getColumnPre().toLowerCase();
		SqlModel sqlModel=iSqlBuilder.getByFk(boEnt, fk);
		List list= commonDao.query(sqlModel);
		JSONArray jsonAry=new JSONArray();
		Map<String,String> fieldMap=getAttrMap(boEnt);
		Map<String, SysBoAttr> attrMap=getAttrObjMap(boEnt);
		
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			JSONObject json=getJsonByRow(fieldMap,attrMap, map, columnPre);
			jsonAry.add(json);
		}
		
		return jsonAry;
	}
	
	/**
	 * 根据主键获取一行数据。
	 * @param pk
	 * @param boEnt
	 * @return
	 */
	public JSONObject getByPk(String pk,SysBoEnt boEnt){
		String columnPre= DbUtil.getColumnPre().toLowerCase();
		String sql="select * from " + boEnt.getTableName() + " where " + SysBoEnt.SQL_PK + "=#{"+SysBoEnt.SQL_PK+"}" ;
		SqlModel sqlModel=new SqlModel(sql);
		sqlModel.addParam(SysBoEnt.SQL_PK, pk);
		Map<String,Object> row=(Map<String,Object>) commonDao.queryForMap(sqlModel);
		
		Map<String,String> fieldMap=getAttrMap(boEnt);
		
		Map<String, SysBoAttr> attrMap=getAttrObjMap(boEnt);
		
		JSONObject json=getJsonByRow(fieldMap,attrMap, row, columnPre);
		
		return  json;
		
	}
	
	/**
	 * 读取一个表的数据。
	 * @param boEnt
	 * @param parentId	父ID。
	 * @return
	 */
	public List<JSONObject> getData(SysBoEnt boEnt,String parentId){
		String columnPre= DbUtil.getColumnPre().toLowerCase();
		
		String sql="";
		String tableName=boEnt.getTableName();
		if(StringUtil.isEmpty(parentId)){
			sql="select * from " + tableName ;
		}
		else{
			sql="SELECT a.*,(select count(*) from "+ tableName +" b where b."+SysBoEnt.SQL_FK+"=a."+
					SysBoEnt.SQL_PK+" ) CHILDS_AMOUNT_  from "+tableName+" a  where a." + SysBoEnt.SQL_FK+"=#{"+SysBoEnt.SQL_FK+"}" ;
		}
		
		SqlModel sqlModel=new SqlModel(sql);
		
		if(StringUtil.isNotEmpty(parentId)){
			sqlModel.addParam(SysBoEnt.SQL_FK, parentId);
		}
		
		Map<String,String> fieldMap=getAttrMap(boEnt);
		
		Map<String, SysBoAttr> attrMap=getAttrObjMap(boEnt);
		List<Map<String,Object>> list=commonDao.query(sqlModel);
		List<JSONObject> rtnList=new ArrayList<JSONObject>();
		for(Map map:list){
			
			JSONObject json=getJsonByRow(fieldMap,attrMap, map, columnPre);
			if(map.containsKey("CHILDS_AMOUNT_")){
				Integer amount=Integer.parseInt(map.get("CHILDS_AMOUNT_").toString());
				boolean isLeaf=amount==0;
				json.put("isLeaf",isLeaf);
				json.put("expanded",false);
			}
			rtnList.add(json);
		}
		return  rtnList;
	}
	
	
	
	/**
	 * 获取数据。
	 * @param fieldMap		字段映射
	 * @param row			数据map
	 * @param columnPre		列前缀
	 * @return
	 */
	private JSONObject getJsonByRow(Map<String,String> fieldMap,Map<String, SysBoAttr> attrMap,Map<String,Object> row,String columnPre){
		
		JSONObject json=new JSONObject();
		Iterator<Map.Entry<String, Object>> it = row.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> ent = it.next();
			String name=StringUtil.trimPrefix(ent.getKey().toLowerCase(),columnPre);
			String attrName=fieldMap.get(name);
			if(fieldMap.containsKey(name)){
				Object val=getValue(name, attrMap, ent.getValue());
				json.put(attrName, val);
			}
			else{
				json.put(ent.getKey(), ent.getValue());
			}
			
		}
		return json;
	}
	
	private Object getValue(String attrName,Map<String, SysBoAttr> attrMap,Object val){
		if(BeanUtil.isEmpty(val)) return "";
		
		if(!attrMap.containsKey(attrName)) return val;
		
		if(val instanceof ClobProxyImpl ){
			ClobProxyImpl clob=(ClobProxyImpl)val;
			return FileUtil.clobToString(clob);
		}
		
		SysBoAttr attr=attrMap.get(attrName);
		
		if(Column.COLUMN_TYPE_DATE.equals(attr.getDataType())){
			return convertDate(attr,(Date)val);
		}

		return val;
	}
	
	
	
	private String convertDate(SysBoAttr attr,Date val){
		if(StringUtil.isEmpty(attr.getExtJson())){
			return DateUtil.formatDate(val);
		}
		//有格式数据。
		else{
			String format="";
			String json=attr.getExtJson();
			if(StringUtil.isNotEmpty(json)){
				JSONObject jsonObj=JSONObject.parseObject(attr.getExtJson());
				if(jsonObj!=null && jsonObj.containsKey("format")){
					format=jsonObj.getString("format");
				}
			}
			return DateUtil.formatDate(val, format);
		}
	}
	
	/**
	 * 将 bo的name 变成小写 。
	 * 数据如下 name --> Name
	 * @param boEnt
	 * @return
	 */
	private Map<String,String> getAttrMap(SysBoEnt boEnt){
		Map<String,String> mapField=new HashMap<String, String>();
		List<SysBoAttr> list=boEnt.getSysBoAttrs();
		mapField.put(SysBoEnt.SQL_PK.toLowerCase(), SysBoEnt.SQL_PK);
		mapField.put(SysBoEnt.SQL_FK.toLowerCase(), SysBoEnt.SQL_FK);
		
		
		mapField.put(SysBoEnt.FIELD_USER.toLowerCase(),SysBoEnt.FIELD_USER);
		mapField.put(SysBoEnt.FIELD_GROUP.toLowerCase(),SysBoEnt.FIELD_GROUP);
		mapField.put(SysBoEnt.FIELD_CREATE_TIME .toLowerCase(),SysBoEnt.FIELD_CREATE_TIME);
		mapField.put(SysBoEnt.FIELD_UPDTIME_TIME .toLowerCase(),SysBoEnt.FIELD_UPDTIME_TIME);
		
		
		for(SysBoAttr attr:list){
			if(!attr.single()){
				mapField.put(attr.getName().toLowerCase() +SysBoEnt.COMPLEX_NAME.toLowerCase(), attr.getName() +SysBoEnt.COMPLEX_NAME.toLowerCase());
			}
			mapField.put(attr.getName().toLowerCase(), attr.getName());
		}
		return mapField;
	}
	
	private Map<String,SysBoAttr> getAttrObjMap(SysBoEnt boEnt){
		Map<String,SysBoAttr> mapField=new HashMap<String, SysBoAttr>();
		List<SysBoAttr> list=boEnt.getSysBoAttrs();
		
		for(SysBoAttr attr:list){
			if(!attr.single()){
				mapField.put(attr.getName().toLowerCase() +SysBoEnt.COMPLEX_NAME.toLowerCase(), attr);
			}
			mapField.put(attr.getName().toLowerCase(), attr);
		}
		return mapField;
	}
	


}
