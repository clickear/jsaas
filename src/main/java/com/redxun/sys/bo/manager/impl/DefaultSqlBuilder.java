package com.redxun.sys.bo.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.redxun.bpm.activiti.util.ProcessHandleHelper;
import com.redxun.bpm.core.entity.config.UserTaskConfig;
import com.redxun.core.entity.KeyValEnt;
import com.redxun.core.entity.SqlModel;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IGroup;
import com.redxun.org.api.service.GroupService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.api.ContextHandlerFactory;
import com.redxun.sys.bo.entity.SysBoEnt;
import com.redxun.sys.bo.manager.ISqlBuilder;

@Service
public class DefaultSqlBuilder implements ISqlBuilder {
	
	@Resource
	GroupService groupService;
	
	@Resource
	ContextHandlerFactory contextHandlerFactory;
	
	
	/**
	 * 子表权限
	 * {tableName1:{type:"user"},
	 * tableName2:{type:"group"},
	 * tableName3:{type:"all"}}
	 * tableName4:{type:"sql",sql:""}}
	 */

	@Override
	public SqlModel getByFk(SysBoEnt boEnt, String fk) {
		
		String sql="select * from " +  boEnt.getTableName()   +" where " + SysBoEnt.SQL_FK + "=#{fk}";
		
		
		SqlModel sqlModel=new SqlModel(sql);
		sqlModel.addParam("fk", fk);
		//构建子表SQL。
		bulidCondition(sqlModel,boEnt.getName());
		
		return sqlModel;
	}
	
	private void bulidCondition(SqlModel sqlModel,String tableName){
		Object config= ProcessHandleHelper.getObjectLocal();
		if(BeanUtil.isEmpty(config)) return;
		String right=(String)config;
		if(StringUtil.isEmpty(right)) return ;
		JSONObject json=JSONObject.parseObject(right);
		if(!json.containsKey(tableName)) return;
			
		JSONObject rightJson=json.getJSONObject(tableName);
		String type=rightJson.getString("type");
	
		if("user".equals(type)){
			handUser( sqlModel);
		}
		else if("group".equals(type)){
			handGroup(sqlModel);
		}
		else if("sql".equals(type)){
			String tmpSql=rightJson.getString("sql");
			handSql(sqlModel,tmpSql);
		}
	}
	
	private void handUser(SqlModel sqlModel){
		String userId=ContextUtil.getCurrentUserId();
		String sql= sqlModel.getSql() + " and " + SysBoEnt.FIELD_USER +"=#{"+SysBoEnt.FIELD_USER+"}";
		
		sqlModel.addParam(SysBoEnt.FIELD_USER, userId);
		sql +=" order by " + SysBoEnt.FIELD_CREATE_TIME + " desc ";
		
		sqlModel.setSql(sql);
	}

	private void handGroup(SqlModel sqlModel){
		String userId=ContextUtil.getCurrentUserId();
		IGroup group= groupService.getMainByUserId(userId);
		if(group==null) return;

		String sql= sqlModel.getSql() + " and " + SysBoEnt.FIELD_GROUP +"=#{"+SysBoEnt.FIELD_GROUP+"}";
		sqlModel.addParam(SysBoEnt.FIELD_GROUP, group.getIdentityId());
		sql +=" order by " + SysBoEnt.FIELD_CREATE_TIME + " desc ";
		sqlModel.setSql(sql);
	}
	
	private void handSql(SqlModel sqlModel,String sql){
		//替换常量 select * from where ref_id_=#{fk} and create_user_id_=[USERID]
		List<KeyValEnt> list= contextHandlerFactory.getHandlers();
		
		Map<String,Object> vars=new HashMap<String, Object>();
		
		for(KeyValEnt ent : list){
			String key=ent.getKey().replace("[", "").replace("]", "");
			if(sql.indexOf(ent.getKey())==-1) continue;
			sql=sql.replace(ent.getKey(), "#{" +key +"}" );
			Object val=contextHandlerFactory.getValByKey(ent.getKey(),vars);
			sqlModel.addParam(key, val);
		}
		sqlModel.setSql(sql);
	}
}
