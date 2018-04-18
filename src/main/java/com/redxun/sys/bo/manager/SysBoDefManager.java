
package com.redxun.sys.bo.manager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.redxun.bpm.form.entity.BpmFormView;
import com.redxun.bpm.form.entity.OpinionDef;
import com.redxun.bpm.form.manager.BpmFormViewManager;
import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.database.api.ITableOperator;
import com.redxun.core.database.util.DbUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.bo.dao.SysBoAttrQueryDao;
import com.redxun.sys.bo.dao.SysBoDefDao;
import com.redxun.sys.bo.dao.SysBoDefQueryDao;
import com.redxun.sys.bo.dao.SysBoEntQueryDao;
import com.redxun.sys.bo.dao.SysBoRelationQueryDao;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.entity.SysBoDef;
import com.redxun.sys.bo.entity.SysBoEnt;
import com.redxun.sys.bo.entity.SysBoRelation;

/**
 * 
 * <pre> 
 * 描述：BO定义 处理接口
 * 作者:ray
 * 日期:2017-02-15 15:02:18
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class SysBoDefManager extends ExtBaseManager<SysBoDef>{
	@Resource
	private SysBoDefDao sysBoDefDao;
	@Resource
	private SysBoDefQueryDao sysBoDefQueryDao;
	@Resource
	private ITableOperator  tableOperator;
	@Resource
	private SysBoEntManager sysBoEntManager;
	@Resource
	private SysBoRelationQueryDao sysBoRelationQueryDao;
	@Resource
	private SysBoAttrQueryDao sysBoAttrQueryDao;
	@Resource
	private SysBoEntQueryDao sysBoEntQueryDao;
	@Resource
	private BpmFormViewManager bpmFormViewManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysBoDefDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return sysBoDefQueryDao;
	}
	
	
	/**
	 * 在保存BO定义之前先检查bo能否添加。
	 * <pre>
	 *  需要检查的点
	 *  1.BO定义系统中是否存在。
	 *  	存在则提示
	 *  2.实体系统中是否存在。
	 *  	存在则提示
	 *  3.物理表检查。
	 *  	如果bo定义需要生成表的情况。
	 *  	
	 *  	判断boDefId是否为空
	 *  	3.1如果为空表示第一次添加，需要判断物理表是否存在，如果存在则不能添加。
	 *  		存在提示。
	 *  	3.2如果不为空的情况，还需要判断 genTable 状态位判断
	 *  		如果原来状态为no，现在为yes。
	 *  			则表示需要添加物理表,可以走3.1逻辑。
	 *  		如果原来状态为yes。
	 *  
	 *  		则表示已经添加过物理表，还需要判断是否产生了新的物理表，还需要走判断。
	 *  		则需要检查对应的物理表是否已经存在数据。
	 *  		1.如果不存在则直接删除表，进行重建。
	 *  		2.如果存在数据则不对数据库进行操作，但是对字段进行标记是否生成，用户可以在界面进行操作。
	 *  
	 * </pre>
	 * @param boDefId
	 * @param ent
	 * @return
	 */
	public JsonResult canExeSave(String boDefId, SysBoEnt ent,String tenantId){
		JsonResult result=new JsonResult(true);
		List<SysBoEnt> ents= sysBoEntManager.getListByBoEnt(ent,false);
		//添加表
		SysBoEnt mainEnt=sysBoEntManager.getMain(ents);
		
		//1.判断定义是否存在
		result= checkBoDefExist( boDefId, mainEnt.getName(), tenantId);
		if(!result.isSuccess()) return result;
		//2.检查实体是否存在
		result= sysBoEntManager.isEntExist(ents);
		if(!result.isSuccess()) return result;
		
		//生成物理表的情况。
		if(SysBoDef.BO_YES.equals( ent.getGenTable())){
			//直接生成的情况
			if(StringUtil.isEmpty(boDefId)){
				result= sysBoEntManager.isTableExist(ents);
			}
			else{
				SysBoDef boDef=this.get(boDefId);
				if(boDef.getSupportDb().equals(SysBoDef.BO_NO)){
					result= sysBoEntManager.isTableExist(ents);
				}
				else{
					List<SysBoEnt> newEnts=new ArrayList<SysBoEnt>();
					for(SysBoEnt boent:ents){
						if(boent.getVersion().equals(SysBoDef.VERSION_NEW)){
							newEnts.add(boent);
						}
					}
					result= sysBoEntManager.isTableExist(newEnts);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 检查bo定义。
	 * @param boDefId
	 * @param mainEnt
	 * @param tenantId
	 * @return
	 */
	public JsonResult checkBoDefExist(String boDefId,String alias,String tenantId){
		JsonResult result=new JsonResult(true);
		
		boolean rtn= sysBoDefQueryDao.getCountByAlias(alias, tenantId, boDefId);
		if(rtn){
			result.setSuccess(false);
			result.setMessage("bo定义" + alias +"已经存在");
		}
		return result;
	}
	
	/**
	 * 根据别名获取bo定义。
	 * @param alias
	 * @return
	 */
	public SysBoDef getByAlias(String alias){
		String tenantId=ContextUtil.getCurrentTenantId();
		SysBoDef boDef= sysBoDefQueryDao.getByAlias(alias, tenantId);
		return boDef;
	}
	
	 
	
	
	/**
	 * 保存实体数据。
	 * <pre>
	 *  boDefId 
	 *  1.如果为空表示实体是新增的。
	 *  	如果为新增则需要检查是否系统中是否已存在实体定义。
	 *  	不存在才可以添加。
	 *  	
	 *  2.如果不为空表示实体需要更新。
	 *  	实体的处理可以保留BO定义数据，实体数据先删除掉，再重新构建bo数据。
	 *  	
	 * 关于物理表的处理
	 *  boDefId 为空的情况。
	 * 	1.还需要检测系统中物理表是否存在，如果存在则不能添加。
	 *  2.如果没有问题可以直接添加表。
	 *  
	 *  不为空的情况，表示bo已经保存过。
	 *  
	 *  	但是可能还没有生成物理表。
	 *  	可以参考boDefId为空的情况处理。
	 *  
	 *  	如果已经生成物理表。
	 *  	检测表中是否有数据
	 *  	
	 *  	1.如果没有数据，则删除表重建。
	 *  	2.如果有数据，则提示用户是否增加列。
	 *  	
	 *  
	 * 	
	 *  
	 * </pre>
	 * @param boDefId	bo定义ID
	 * @param ent		需要保存的实体。
	 * @return
	 * @throws SQLException 
	 */
	public String  saveBoEnt(String boDefId, SysBoEnt ent,List<SysBoAttr> sysBoAttrs,String tenantId,List<OpinionDef> opinionDefs) throws SQLException{
		//是否生成物理表。
		boolean genTable=ent.getGenTable().equals(SysBoDef.BO_YES);
		List<SysBoEnt> ents= sysBoEntManager.getListByBoEnt(ent,false);
		//添加表
		SysBoEnt mainEnt=sysBoEntManager.getMain(ents);
		List<SysBoEnt> subEnts=sysBoEntManager.getSub(ents);
		
		String jsonOpinion=JSONArray.toJSONString(opinionDefs);
		
		//表示新增。
		if(StringUtil.isEmpty(boDefId)){
			if(genTable){
				sysBoEntManager.createTable(ent);
			}
			//添加BODEF
			boDefId=IdUtil.getId();
			SysBoDef boDef=new SysBoDef();
			boDef.setId(boDefId);
			boDef.setAlais(ent.getName());
			boDef.setName(ent.getComment());
			boDef.setGenMode(SysBoDef.GEN_MODE_FORM);
			boDef.setTreeId(ent.getTreeId());
			boDef.setSupportDb(ent.getGenTable());
			boDef.setOpinionDef(jsonOpinion);
			
			sysBoDefQueryDao.create(boDef);
			
			resetEntVersion(ent);
			
			saveEnt(boDefId,mainEnt,subEnts,true);
		}
		else{
			handTable(boDefId,ent,sysBoAttrs);
			
			//根据bo定义删除
			removeByBoDefId(boDefId);
			
			saveEnt(boDefId,mainEnt,subEnts,false);
			//设置BO定义。
			SysBoDef boDef=sysBoDefQueryDao.get(boDefId);
			boDef.setName(ent.getComment());
			boDef.setTreeId(ent.getTreeId());
			boDef.setOpinionDef(jsonOpinion);
			boDef.setSupportDb(ent.getGenTable());
			sysBoDefQueryDao.update(boDef);
			
		}
		return boDefId;
		
	}
	
	private void handTable(String boDefId,SysBoEnt ent,List<SysBoAttr> sysBoAttrs) throws SQLException{
		boolean genTable=ent.getGenTable().equals(SysBoDef.BO_YES);
		if(!genTable) return;
		SysBoDef boDef=sysBoDefDao.get(boDefId);
		//未生成物理表。
		if(boDef.getSupportDb().equals(SysBoDef.BO_NO)){
			sysBoEntManager.createTable(ent);
			resetEntVersion(ent);
			return;
		}
		//已生成物理表。
		//判断物理表是否存在数据。
		String tablePre=DbUtil.getTablePre();
		String tableName=tablePre +ent.getName();
		boolean hasData=tableOperator.hasData(tableName);
		//主表没有数据的情况，直接删除表重建。
		if(!hasData){
			rebuidTable(ent);
			resetEntVersion(ent);
			return;
		}
		//存在数据
		List<SysBoEnt> list= sysBoEntManager.getListByBoEnt(ent,false);
		for(SysBoEnt boEnt:list){
			//创建的新表。
			if(SysBoDef.VERSION_NEW.equals( boEnt.getVersion())){
				sysBoEntManager.createSingleTable(boEnt);
				resetEntVersion(boEnt);
			}
			//表已存在,这个时候不对表进行操作，在列属性中记录变更情况。
			else{
				//处理新增的列
				List<SysBoAttr> attrs= boEnt.getSysBoAttrs();
				for(SysBoAttr attr:attrs){
					if(attr.getStatus().equals(SysBoDef.VERSION_NEW)){
						//新增的列。
						sysBoEntManager.createColumn(boEnt.getTableName(), attr);
						attr.setStatus(SysBoDef.VERSION_BASE);
					}else if((attr.getStatus().equals(SysBoDef.VERSION_DIFF))){//如果有区别
						//处理int和varchar的增大
						for (SysBoAttr sysBoAttr : sysBoAttrs) {
							if(attr.getFieldName().equals(sysBoAttr.getFieldName())){
								int disLength=attr.getLength()-sysBoAttr.getLength();//修改后的长度比原先的长度差
								if((attr.getDataType().equals(sysBoAttr.getDataType())||(attr.getDataType().equals("varchar")&&sysBoAttr.getDataType().equals("number")))&&disLength>=0){//如果数据类型一样才允许修改数据库
									sysBoEntManager.updateColumn(boEnt.getTableName(), attr);	
								}
							}
						}
						attr.setStatus(SysBoDef.VERSION_BASE);
					}
				}
			}
		}
	}
	
	
	
	/**
	 * 重置boEnt的状态。
	 * @param boEnt
	 */
	private void resetEntVersion(SysBoEnt boEnt){
		resetEntSingleVersion(boEnt);
		List<SysBoEnt> list=boEnt.getBoEntList();
		if(BeanUtil.isEmpty(list)) return;
		for(SysBoEnt ent:list){
			resetEntSingleVersion(ent);
		}
	}
	
	/**
	 * 重置单个实体的状态。
	 * @param boEnt
	 */
	private void resetEntSingleVersion(SysBoEnt boEnt){
		boEnt.setVersion(SysBoDef.VERSION_BASE);
		for(SysBoAttr attr:boEnt.getSysBoAttrs()){
			attr.setStatus(SysBoDef.VERSION_BASE);
		}
	}
	
	/**
	 * 重建表。
	 * <pre>
	 * 1.先删除所有的表。
	 * 2.在重新创建所有的表。
	 * </pre>
	 * @param ent
	 * @throws SQLException
	 */
	private void rebuidTable(SysBoEnt ent) throws SQLException{
		String tablePre= DbUtil.getTablePre();
		List<SysBoEnt> ents=new ArrayList<SysBoEnt>();
		ents.add(ent);
		ents.addAll(ent.getBoEntList());
		for(SysBoEnt boEnt:ents){
			//关联表不处理。
			if(boEnt.getIsRef()==1) continue;
			String tableName=tablePre +boEnt.getName();
			tableOperator.dropTable(tableName);
		}
		sysBoEntManager.createTable(ent);
		
	}
	
	/**
	 * 保存ent 源数据和映射关系。
	 * @param boDefId
	 * @param mainEnt
	 * @param subEnts
	 */
	public void saveEnt(String boDefId,SysBoEnt mainEnt,List<SysBoEnt> subEnts,boolean genId){
		String tenantId=ContextUtil.getCurrentTenantId();
		sysBoEntManager.createBoEnt(mainEnt,genId);
		createRel(boDefId, mainEnt);
		//添加子实体
		for(SysBoEnt subEnt:subEnts){
			//增加关联引用实体。
			if(subEnt.getIsRef()==1){
				BpmFormView bpmFormView= bpmFormViewManager.getLatestByKey(subEnt.getFormAlias(), tenantId);
				String refBoDefId=bpmFormView.getBoDefId();
				SysBoEnt boEnt= sysBoEntManager.getByBoDefId(refBoDefId);
				boEnt.setRelationType(subEnt.getRelationType());
				boEnt.setIsRef(1);
				boEnt.setFormAlias(subEnt.getFormAlias());
				createRel(boDefId, boEnt);
			}
			else{
				sysBoEntManager.createBoEnt(subEnt,genId);
				createRel(boDefId, subEnt);
			}
			
		}
	}
	
	/**
	 * 创建实体映射关系。
	 * @param boDefId
	 * @param ent
	 */
	private void createRel(String boDefId, SysBoEnt ent){
		SysBoRelation rel=new SysBoRelation();
		rel.setId(IdUtil.getId());
		rel.setBoDefid(boDefId);
		rel.setBoEntid(ent.getId());
		rel.setRelationType(ent.getRelationType());
		
		rel.setIsRef(ent.getIsRef());
		rel.setFormAlias(ent.getFormAlias());
		
		sysBoRelationQueryDao.create(rel);
	}
	
	/**
	 * 根据boDefId 删除数据。
	 * @param boDefId
	 */
	private void removeByBoDefId(String boDefId){
		List<String> entIds= getEntIdByBoDefId( boDefId);
		for(String entId:entIds){
			//根据entID删除
			sysBoAttrQueryDao.delByMainId(entId);
			//根据entID 删除实体ID
			sysBoEntQueryDao.delete(entId);
		}
		//删除关联关系
		sysBoRelationQueryDao.delByMainId(boDefId);
	}
	
	/**
	 * 获取关联的ENTID
	 * @param boDefId
	 * @return
	 */
	private List<String> getEntIdByBoDefId(String boDefId){
		List<SysBoRelation> list= sysBoRelationQueryDao.getByBoDefId(boDefId);
		List<String> ents=new ArrayList<String>();
		for(SysBoRelation ent :list){
			if(ent.getIsRef()==1) continue;
			ents.add(ent.getBoEntid());
		}
		return ents;
	}
	
	public SysBoRelation getEntByEntNameAndDefId(String boDefId,String entName){
		return sysBoRelationQueryDao.getByBoDefIdAndEntName(boDefId, entName);
	}
	
	/**
	 * 删除bo定义。
	 * @param boDefId
	 * @throws SQLException
	 */
	public void removeBoDef(String boDefId) throws SQLException{
		//判断是否有实体
		List<SysBoRelation> list= sysBoRelationQueryDao.getByBoDefId(boDefId);
		if(BeanUtil.isNotEmpty(list)) {
			//删除表。
			dropTable(boDefId);
			//删除bo定义。
			removeByBoDefId(boDefId);
		}
		//删除bo定义
		this.delete(boDefId);
		
	}
	
	/**
	 * 删除物理表。
	 * @param boDefId
	 * @throws SQLException
	 */
	private void dropTable(String boDefId) throws SQLException{
		//清除表
		SysBoEnt ent= sysBoEntManager.getByBoDefId(boDefId, false);
		if(!SysBoDef.BO_YES.equals(ent.getGenTable())) return;
		
		String tableName= ent.getTableName();
		tableOperator.dropTable(tableName);
		
		List<SysBoEnt> subEnts=ent.getBoEntList();
		for(SysBoEnt subEnt:subEnts){
			if(subEnt.getIsRef()==1) continue;
			tableOperator.dropTable(subEnt.getTableName());
		}
	}
	
}
