package com.redxun.sys.bo.manager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.database.api.ITableOperator;
import com.redxun.core.database.api.model.Column;
import com.redxun.core.database.api.model.Table;
import com.redxun.core.database.datasource.DataSourceUtil;
import com.redxun.core.database.model.DefaultColumn;
import com.redxun.core.database.model.DefaultTable;
import com.redxun.core.database.util.DbUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.bo.dao.SysBoAttrQueryDao;
import com.redxun.sys.bo.dao.SysBoEntDao;
import com.redxun.sys.bo.dao.SysBoEntQueryDao;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.entity.SysBoDef;
import com.redxun.sys.bo.entity.SysBoEnt;
import com.redxun.sys.bo.manager.parse.BoAttrParseFactory;
import com.redxun.sys.bo.manager.parse.IBoAttrParse;
import com.redxun.sys.bo.manager.parse.impl.AttrParseUtil;

/**
 * 
 * <pre> 
 * 描述：表单实体对象 处理接口
 * 作者:ray
 * 日期:2017-02-15 15:02:18
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class SysBoEntManager extends ExtBaseManager<SysBoEnt>{
	@Resource
	private SysBoEntDao sysBoEntDao;
	@Resource
	private SysBoEntQueryDao sysBoEntQueryDao;
	@Resource
	private SysBoAttrQueryDao sysBoAttrQueryDao;
	@Resource
	private ITableOperator  tableOperator;
	@Resource
	private BoAttrParseFactory boAttrParseFactory;
	@Resource
	JdbcTemplate jdbcTemplate;
	
	@Override
	protected IDao getDao() {
		return sysBoEntDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		//add,update,base
		return sysBoEntQueryDao;
	}
	
	
	
	/**
	 * 解析html
	 * @param html
	 * @return
	 */
	public  SysBoEnt parseHtml(String html){
		SysBoEnt boEnt=new SysBoEnt();
		
		Document doc=Jsoup.parseBodyFragment(html);
		//先解析子表
		Elements  tables= doc.select("[plugins=\"rx-grid\"]");
		Iterator<Element> tableIt=tables.iterator();
		while(tableIt.hasNext()){
			Element tableEl=tableIt.next();
			
			Element tbEl=tableEl;
			
			SysBoEnt childEnt=new SysBoEnt();
			//处理子表数据
			parseEnt(childEnt,tableEl);
			
			tableEl.removeAttr("plugins");
			//解析明细表单。edittype="openwindow"
			String editType=tableEl.attr("edittype");
			//弹出表单单独处理不需要解析其中的字段。
			if(StringUtil.isNotEmpty(editType) && "editform".equals(editType)){
				String formname=tableEl.attr("formkey");
				//设置关联表单别名。
				childEnt.setFormAlias(formname);
				//为引用实体。
				childEnt.setIsRef(1);
				boEnt.addBoEnt(childEnt);
				//删除子表
				tableEl.remove();
				continue;
			}
			
			if(StringUtil.isNotEmpty(editType) && "openwindow".equals(editType)){
				Elements  detailWindow=tableEl.select(".mini-window");
				if(detailWindow.size()>0){
					tableEl=detailWindow.first();
				}
			}
			
			Elements fields = tableEl.select("[plugins]");
			
			//处理子表字段,取行的控件
			if(BeanUtil.isNotEmpty(fields)){
				handFields(fields, childEnt);
			}
			else{
				//<th class="header" width="200" header="pk_elem_name"
				Elements  els= tableEl.select("th[header]");
				handHeaderFields(els,childEnt);
			}
			
			boEnt.addBoEnt(childEnt);
			//删除子表
			tbEl.remove();
		}
		
		//再解析主表字段
		Elements  elements= doc.select("[plugins]");
		handFields(  elements, boEnt);
		
		return boEnt;
	}
	
	/**
	 * 处理子表数据。
	 * @param boEnt
	 * @param tableEl
	 */
	private static void parseEnt(SysBoEnt boEnt, Element tableEl){
		String label=tableEl.attr("label");
		String name=tableEl.attr("name");
		//是否树形
		String treegrid=tableEl.attr("treegrid");
		String requireformfield=tableEl.attr("requireformfield");
		boEnt.setName(name);
		boEnt.setComment(label);
		
		JSONObject json=new JSONObject();
		// 子表必填。
		if(StringUtil.isNotEmpty( requireformfield) && "true".equals(requireformfield)) {
			json.put("required", true);
		}
		boEnt.setExtJson(json.toJSONString());
		if(StringUtil.isEmpty(treegrid)){
			treegrid="NO";
		}
		else{
			treegrid="true".equals(treegrid)?"YES":"NO";
		}
		boEnt.setTree(treegrid);
		
	}
	
	/**
	 * 解析字段。
	 * @param elements
	 * @param ent
	 */
	private static void handFields(Elements  elements,SysBoEnt ent){
		BoAttrParseFactory factory=(BoAttrParseFactory)AppBeanUtil.getBean("boAttrParseFactory");
		for(Element el:elements){
			String pluginName=el.attr("plugins");
					
			IBoAttrParse parse=factory.getByPluginName(pluginName);
			if(parse==null) continue;
			SysBoAttr  formField=parse.parse(pluginName, el);
			
			ent.addBoAttr(formField);
		}
	}
	
	private static void handHeaderFields(Elements  elements,SysBoEnt ent){
		for(Element el:elements){
			SysBoAttr  boAttr=new SysBoAttr();
			boAttr.setControl("mini-textbox");
			boAttr.setName(el.attr("header").trim());
			boAttr.setComment(el.text().trim());
			ent.addBoAttr(boAttr);
		}
	}
	
	public SysBoEnt getByBoDefId(String boDefId){
		return this.getByBoDefId(boDefId, true);
	}
	
	/**
	 * 获取主BOENT对象。
	 * @param boDefId
	 * @return
	 */
	public SysBoEnt getMainByBoDefId(String boDefId){
		List<SysBoEnt> list= sysBoEntQueryDao.getByBoDefId(boDefId);
		SysBoEnt mainEnt= getMain( list);
		List<SysBoAttr> mainAttrs=  sysBoAttrQueryDao.getAttrsByEntId(mainEnt.getId());
		mainEnt.setSysBoAttrs(mainAttrs);
		return mainEnt;
	}
	
	/**
	 * 根据boDefId获取BOENT对象。
	 * @param boDefId
	 * @return
	 */
	public SysBoEnt getByBoDefId(String boDefId,boolean needAttr){
		if(StringUtils.isEmpty(boDefId)){
			return null;
		}
		List<SysBoEnt> list= sysBoEntQueryDao.getByBoDefId(boDefId);
		
		SysBoEnt mainEnt= getMain( list);
		if(mainEnt==null){
			return new SysBoEnt();
		}
		if(needAttr){
			List<SysBoAttr> mainAttrs=  sysBoAttrQueryDao.getAttrsByEntId(mainEnt.getId());
			mainEnt.setSysBoAttrs(mainAttrs);
		}
		
		List<SysBoEnt> subList= getSub( list);
		
		if(needAttr){
			if(BeanUtil.isNotEmpty(subList)){
				for(SysBoEnt subEnt:subList){
					List<SysBoAttr> subAttrs=  sysBoAttrQueryDao.getAttrsByEntId(subEnt.getId());
					subEnt.setSysBoAttrs(subAttrs);
				}
			}
		}
		
		mainEnt.setBoEntList(subList);
		
		return mainEnt;
	}
	
	public List<SysBoEnt> getListByBoDefId(String boDefId,boolean needAttr){
		List<SysBoEnt> list= sysBoEntQueryDao.getByBoDefId(boDefId);
		if(needAttr){
			for(SysBoEnt ent:list){
				List<SysBoAttr> mainAttrs=  sysBoAttrQueryDao.getAttrsByEntId(ent.getId());
				ent.setSysBoAttrs(mainAttrs);
			}
		}
		return list;
	}
	
	public SysBoEnt getMain(List<SysBoEnt> list){
		for(SysBoEnt ent:list){
			if(SysBoEnt.RELATION_TYPE_MAIN.equals(ent.getRelationType())){
				return ent;
			}
		}
		return null;
	}
	
	public List<SysBoEnt> getSub(List<SysBoEnt> list){
		List<SysBoEnt> rtnList=new ArrayList<SysBoEnt>();
		for(SysBoEnt ent:list){
			if(SysBoEnt.RELATION_TYPE_SUB .equals(ent.getRelationType())){
				rtnList.add(ent);
			}
		}
		return rtnList;
	}
	
	/**
	 * 将属性的BO ENT返回为列表，方便在浏览器端显示。
	 * @param boEnt
	 * @return
	 */
	public List<SysBoEnt> getListByBoEnt(SysBoEnt boEnt,boolean clearSubEnt){
		List<SysBoEnt> list=new ArrayList<SysBoEnt>();
		list.add(boEnt);
		for(SysBoEnt ent:boEnt.getBoEntList()){
			list.add(ent);
		}
		//是否清除子表。
		if(clearSubEnt){
			boEnt.clearSubBoEnt();
		}
		//
		return list;
	}
	
	/**
	 * 两个SysBoEnt进行合并。
	 * @param baseEnt
	 * @param curEnt
	 */
	public void merageBoEnt(SysBoEnt baseEnt,SysBoEnt curEnt){
		//合并属性
		List<SysBoAttr> baseAttrs=baseEnt.getSysBoAttrs();
		//当前属性
		List<SysBoAttr> curAttrs=curEnt.getSysBoAttrs();
		//合并属性。
		diffBoAttrs(baseEnt,baseAttrs,curAttrs);
		
		baseEnt.setVersion(SysBoDef.VERSION_BASE);
		
		baseEnt.setExtJson(curEnt.getExtJson());
		//合并子表。
		List<SysBoEnt>  baseEnts=baseEnt.getBoEntList();
		List<SysBoEnt> curEnts=curEnt.getBoEntList();
		
		Map<String,SysBoEnt> baseMaps= convertToEntMap(baseEnts);
		for(SysBoEnt ent:curEnts){
			String name=ent.getName().toLowerCase();
			//不存在的情况
			if(!baseMaps.containsKey(name)){
				baseEnts.add(ent);
			}
			//已存在的子表合并子表属性
			else{
				SysBoEnt baseBoEnt=baseMaps.get(name);
				baseBoEnt.setFormAlias(ent.getFormAlias());
				baseBoEnt.setIsRef(ent.getIsRef());
				baseBoEnt.setExtJson(ent.getExtJson());
				baseBoEnt.setTree(ent.getTree());
				if(1!=ent.getIsRef()){
					List<SysBoAttr> baseSubAttrs=baseBoEnt.getSysBoAttrs();
					List<SysBoAttr> curSubAttrs=ent.getSysBoAttrs();
					//对象合并。
					diffBoAttrs(baseBoEnt,baseSubAttrs,curSubAttrs);	
				}
			}
		}
	}
	
	/**
	 * 将ENT列表转成map对象。
	 * @param ents
	 * @return
	 */
	private Map<String,SysBoEnt> convertToEntMap(List<SysBoEnt>  ents){
		Map<String,SysBoEnt> maps=new HashMap<String, SysBoEnt>();
		for(SysBoEnt ent:ents){
			ent.setVersion(SysBoDef.VERSION_BASE);
			maps.put(ent.getName().toLowerCase(), ent);
		}
		return maps;
	}
	
	
	
	/**
	 * 将列表转成map对象。
	 * @param attrs
	 * @return
	 */
	private Map<String, SysBoAttr> convertToMap(List<SysBoAttr>  attrs){
		Map<String,SysBoAttr> maps=new HashMap<String, SysBoAttr>();
		for(SysBoAttr attr:attrs){
			maps.put(attr.getName().toLowerCase(), attr);
		}
		return maps;
	}
	
	private void diffBoAttrs(SysBoEnt boEnt, List<SysBoAttr>  baseAttrs,List<SysBoAttr>  curBoAttrs){
		//基础的属性版本。
		for(SysBoAttr attr:baseAttrs){
			attr.setStatus(SysBoDef.VERSION_BASE);
		}
		Map<String, SysBoAttr>  baseMap= convertToMap(baseAttrs);
		for(SysBoAttr attr:curBoAttrs){
			if(attr.isContain()){
				baseAttrs.add(attr);
				boEnt.setHasConflict(true);
				continue;
			}
			String name=attr.getName().toLowerCase();
			
			if(baseMap.containsKey(name)){
				SysBoAttr baseAttr=baseMap.get(name);
				//用回新的字段名称主要因为字段有大小写的问题。
				baseAttr.setName(attr.getName());
				
				baseAttr.setIsSingle(attr.getIsSingle());
				//变换属性版本
				if(!attr.equals(baseAttr)){
					baseAttr.setStatus(SysBoDef.VERSION_DIFF);
					String diffContent= getDiffContent( baseAttr,attr);
					baseAttr.setDiffConent(diffContent);
				}
				//复制新对象属性。
				baseAttr.setControl(attr.getControl());
				baseAttr.setDataType(attr.getDataType());
				baseAttr.setComment(attr.getComment());
				baseAttr.setLength(attr.getLength());
				baseAttr.setDecimalLength(attr.getDecimalLength());
				baseAttr.setExtJson(attr.getExtJson());
			}
			else{
				baseAttrs.add(attr);
			}
		}
	}
	
	private String getDataType(SysBoAttr attr){
		String dataType=attr.getDataType();
		if(attr.getDataType().equals(Column.COLUMN_TYPE_VARCHAR)){
			dataType=attr.getDataType() +"("+ attr.getLength() +")";
		}
		if(attr.getDataType().equals(Column.COLUMN_TYPE_NUMBER)){
			dataType=attr.getDataType() +"("+ attr.getLength() +"," + attr.getDecimalLength() +")";
		}
		return dataType;
	}
	
	/**
	 * 获取内容。
	 * @param baseAttr
	 * @param curAttr
	 * @return
	 */
	private String getDiffContent(SysBoAttr baseAttr,SysBoAttr curAttr){
		JSONObject json=new JSONObject();
		
		JSONObject ctlJson=new JSONObject();
		
		ctlJson.put("base", baseAttr.getControl());
		ctlJson.put("new", curAttr.getControl());
		
		json.put("control", ctlJson);
		
		JSONObject dateTypeJson=new JSONObject();
		
		String baseType=getDataType(baseAttr);
		String newType=getDataType(curAttr);;
		
		dateTypeJson.put("base", baseType);
		dateTypeJson.put("new", newType);
		
		json.put("dataType", dateTypeJson);
		
		return json.toJSONString();
			
	}
	
	/**
	 * 检测实体是否存在。
	 * @param list
	 * @return
	 */
	public JsonResult isEntExist(List<SysBoEnt> list){
		JsonResult result=new JsonResult(true);
		String msg="下列实体已存在:";
		for(SysBoEnt ent:list){
			Integer rtn= sysBoEntQueryDao.getCountByAlias(ent.getName(), 
					ent.getTenantId(), DataSourceUtil.LOCAL, ent.getId());
			
			if(rtn>0){
				result.setSuccess(false);
				msg+=" " +ent.getName();
			}
		}
		if(!result.isSuccess()){
			result.setMessage(msg);
		}
		return result;
	}
	
	/**
	 * 检查表是否存在。
	 * @param ents
	 * @return
	 */
	public JsonResult  isTableExist(List<SysBoEnt> ents){
		JsonResult result=new JsonResult(true);
		if(BeanUtil.isEmpty(ents)) return result;
		String msg="下列物理表已存在:";
		for(SysBoEnt ent:ents){
			//为实体引用的情况。
			if(ent.getIsRef()==1) continue;
			
			String tableName=ent.getTableName();
			boolean isExist=tableOperator.isTableExist(tableName);
			if(isExist){
				result.setSuccess(false);
				msg+=" " +ent.getName();
			}
		}
		if(!result.isSuccess()){
			result.setMessage(msg);
		}
		return result;
	}
	
	/**
	 * 使用BOENT创建列。
	 * @param boEnt
	 * @throws SQLException
	 */
	public void createTable(SysBoEnt boEnt) throws SQLException{
		List<Table> tables= getTablesByBoEnt( boEnt);
		for(Table table:tables){
			tableOperator.createTable(table);
			//创建索引
			if(!table.isMain()){
				tableOperator.createIndex(table.getEntName() +"_ref", table.getTableName(), "ref_id_");
			}
			
		}
	}
	
	
	/**
	 * 创建单个表。
	 * @param boEnt
	 * @throws SQLException
	 */
	public void createSingleTable(SysBoEnt boEnt) throws SQLException{
		Table table= getTableByEnt( boEnt);
		tableOperator.createTable(table);
	}
	
	/**
	 * 根据层次结构的boent获取表对象。
	 * @param boEnt
	 * @return
	 */
	public List<Table> getTablesByBoEnt(SysBoEnt boEnt){
		List<Table> list=new ArrayList<Table>();
		
		Table table= getTableByEnt( boEnt);
		table.setMain(true);
		list.add(table);
		if(BeanUtil.isNotEmpty( boEnt.getBoEntList())){
			List<SysBoEnt> boEnts=boEnt.getBoEntList();
			for(SysBoEnt ent:boEnts){
				//如果是引用的BO。
				if(ent.getIsRef()==1) continue;
				
				Table subTable= getTableByEnt( ent);
				list.add(subTable);
			}
		}
		return list;
	}
	
	/**
	 * 获得bo对应的主物理表
	 * @param boDefId
	 * @return
	 */
	public Table getMainTableByBodDefId(String boDefId){
		SysBoEnt sysBoEnt=getMainByBoDefId(boDefId);
		if(sysBoEnt==null){
			return null;
		}
		return getTableByEnt(sysBoEnt);
	}
	
	/**
	 * 根据boent 对象获取表对象。
	 * @param boEnt
	 * @return
	 */
	private Table getTableByEnt(SysBoEnt boEnt){
		String tablePre= DbUtil.getTablePre();
		String columnPre= DbUtil.getColumnPre();
		Table table=new DefaultTable();
		
		table.setComment(boEnt.getComment());
		table.setTableName(tablePre + boEnt.getName()); 
		table.setEntName(boEnt.getName());
		
		List<Column> cols=new ArrayList<Column>();
		List<SysBoAttr> boAttrs=  boEnt.getSysBoAttrs();
		
		for(Iterator<SysBoAttr> it=boAttrs.iterator();it.hasNext();){
			SysBoAttr attr=it.next();
			List<Column> col= getColsByAttr(attr,columnPre);
			cols.addAll(col);
		}
		
		List<Column> columns= getCommonCols(cols,boEnt);
		
		
		table.setColumnList(columns);
		return table;
	}
	
	/**
	 * 添加列。
	 * @param tableName
	 * @param attr
	 * @throws SQLException
	 */
	public void createColumn(String tableName, SysBoAttr attr) throws SQLException{
		String columnPre= DbUtil.getColumnPre();
		List<Column> columns= getColsByAttr( attr,  columnPre);
		for(Column col:columns){
			tableOperator.addColumn(tableName, col);
		}
	}
	
	/**
	 * 修改列字段
	 * @param tableName
	 * @param attr
	 * @throws SQLException
	 */
	public void updateColumn(String tableName,SysBoAttr attr)throws SQLException{
		String columnPre= DbUtil.getColumnPre();
		List<Column> columns= getColsByAttr( attr,  columnPre);
		for(Column col:columns){
			tableOperator.updateColumn(tableName, col.getFieldName(), col);
		}
		
	}

	/**
	 * 根据属性列获取列对象，属性有可能为复合属性，这样会生成两个列。
	 * @param attr
	 * @param columnPre
	 * @return
	 */
	private List<Column> getColsByAttr(SysBoAttr attr,String columnPre){
		List<Column> list=new ArrayList<Column>();
		Column col= getColumnByAttr( attr, columnPre,false);
		list.add(col);
		if(!attr.single()){
			 col= getColumnByAttr(attr, columnPre,true);
			 list.add(col);
		}
		return list;
	}
	
	/**
	 * 根据属性获取列对象。
	 * @param attr
	 * @param columnPre
	 * @param isSingle
	 * @return
	 */
	private Column getColumnByAttr(SysBoAttr attr,String columnPre,boolean isComplex){
		String dateType=attr.getDataType();
		Column col=new DefaultColumn();
		
		String name=isComplex ?SysBoEnt.COMPLEX_NAME:"";
		col.setFieldName(columnPre + attr.getName().toUpperCase() + name  );
		col.setColumnType(attr.getDataType());
		String comment=attr.getComment();
		
		if(!attr.single() && !isComplex){
			comment+="ID";
		}
		
		col.setComment(comment);
		
		if(Column.COLUMN_TYPE_VARCHAR.equals(dateType)){
			col.setCharLen(attr.getLength());
		}
		else if(Column.COLUMN_TYPE_NUMBER.equals(dateType)){
			col.setIntLen(attr.getLength());
			col.setDecimalLen(attr.getDecimalLength());
		}
		return col;
	}
	
	/**
	 *  批量保存行
	 * @param boDefId
	 * @param rowsJson
	 */
	public void batchRows(String boDefId,String rowsJson){
	
		Table mainTable=getMainTableByBodDefId(boDefId) ;
		String pkField=null;
		Map<String,Column> colsMap=new HashMap<String,Column>();
		for(Column col:mainTable.getColumnList()){
			colsMap.put(col.getFieldName(), col);
			if(col.getIsPk()){
				pkField=col.getFieldName();
			}
		}
		//String pkField=mainTable.getColumnList()
		boolean isContainRefId=false;
    	JSONArray rowArr=JSONArray.parseArray(rowsJson);
    	IUser curUser=ContextUtil.getCurrentUser();
    	Map<String,String> idMap=new HashMap<String, String>();
    	for(int i=0;i<rowArr.size();i++){
    		JSONObject row=rowArr.getJSONObject(i);
    		String id=row.getString("_id");
    		
    		Iterator<String> fieldIt=row.keySet().iterator();
    		StringBuffer sqlBuffer=new StringBuffer("");
    		StringBuffer valBuffer=new StringBuffer("");
    		boolean isInsert=false;
    		String pkId=row.getString(pkField);
    		
    		if(pkId==null){
    			isInsert=true;
    			sqlBuffer.append("insert into " + mainTable.getTableName()).append("(");
    		}else{
    			sqlBuffer.append("update " + mainTable.getTableName());
    		}
    		List<Object> params=new ArrayList<Object>();
    		
    		int cn=0;
    		while(fieldIt.hasNext()){
    			String field=fieldIt.next();
    			if("REF_ID_".equals(field)){
    				isContainRefId=true;
    			}
    			if(StringUtils.isNotEmpty(field) && !field.startsWith("_")){
    				Column fieldCol=colsMap.get(field);
    				if(fieldCol==null){
    					continue;
    				}
    				Object val = null; 
    				if("number".equals(fieldCol.getColumnType())){
    					val=row.getDouble(field);
    				}else if("date".equals(fieldCol.getColumnType())){
    					val=row.getDate(field);
    				}else{
    					val=row.getString(field);
    				}
    				
        			if(isInsert){
        				if(cn>0){
            				sqlBuffer.append(",");
            				valBuffer.append(",");
            			}
        				sqlBuffer.append(field);
        				valBuffer.append("?");
        				params.add(val);
        				cn++;
        			}else if(!field.equals(pkField)){
        				if(cn>0){
            				sqlBuffer.append(",");
            			}else{
            				sqlBuffer.append(" set ");
            			}
        				sqlBuffer.append(field).append("=?");
        				params.add(val);
        				cn++;
        			}
    			}
    		}
    		
    		if(!isInsert){
    			if(cn>0){
    				sqlBuffer.append(" , ");
    			}else{
    				sqlBuffer.append(" set ");
    			}
    			sqlBuffer.append(SysBoEnt.FIELD_UPDTIME_TIME).append("=?");
    			params.add(new Date());
    			
    			sqlBuffer.append(" where ").append(pkField).append("=?");
    			params.add(pkId);
    			logger.info("sql:"+ sqlBuffer.toString());
    			jdbcTemplate.update(sqlBuffer.toString(), params.toArray());
    			
    		}else{
    			if(cn>0){
    				sqlBuffer.append(",");
    				valBuffer.append(",");
    			}
    			sqlBuffer.append(pkField);
    			//1. 插入主键ID
    			valBuffer.append("?");
    			String nPkId=IdUtil.getId();
    			params.add(nPkId);
    			idMap.put(id, nPkId);
    			if(!isContainRefId){
	    			//2 . 插入外键
	    			sqlBuffer.append(",REF_ID_");
	    			valBuffer.append(",?");
	    			
	    			String parentId=row.getString("_pid");
	    			if("-1".equals(parentId)){
	    				params.add("0");
	    			}else{
	    				String pId=idMap.get(parentId);
	    				params.add(pId);
	    			}
	    			
    			}
    			
    			//3.插入用户ID
    			sqlBuffer.append(",").append(SysBoEnt.FIELD_USER);
    			valBuffer.append(",?");
    			params.add(curUser.getUserId());
    			//4.组ID
    			sqlBuffer.append(",").append(SysBoEnt.FIELD_GROUP);
    			valBuffer.append(",?");
    			params.add(curUser.getMainGroupId());
    			//5.创建时间
    			sqlBuffer.append(",").append(SysBoEnt.FIELD_CREATE_TIME);
    			valBuffer.append(",?");
    			params.add(new Date());
    			//6.租户ID
    			sqlBuffer.append(",").append(SysBoEnt.FIELD_TENANT);
    			valBuffer.append(",?");
    			params.add(curUser.getTenant().getTenantId());
    			
    			sqlBuffer.append(") values(").append(valBuffer.toString()).append(")");
    			logger.info("sql:"+ sqlBuffer.toString());
    			jdbcTemplate.update(sqlBuffer.toString(), params.toArray());
    		}
    	}
		
	}
	
	private List<Column> getCommonCols(List<Column> cols,SysBoEnt ent){
		List<Column> list=new ArrayList<Column>();
		Column colPk=new DefaultColumn();
		colPk.setColumnType(Column.COLUMN_TYPE_VARCHAR);
		colPk.setCharLen(64);
		colPk.setIsPk(true);
		colPk.setComment("主键");
		colPk.setFieldName("ID_");
		
		Column colFk=new DefaultColumn();
		colFk.setColumnType(Column.COLUMN_TYPE_VARCHAR);
		colFk.setCharLen(64);
		colFk.setComment("外键");
		colFk.setFieldName("REF_ID_");
		
		
		
		
		Column colUser=new DefaultColumn();
		colUser.setColumnType(Column.COLUMN_TYPE_VARCHAR);
		colUser.setCharLen(64);
		colUser.setComment("用户ID");
		colUser.setFieldName(SysBoEnt.FIELD_USER);
		
		Column colGroup=new DefaultColumn();
		colGroup.setColumnType(Column.COLUMN_TYPE_VARCHAR);
		colGroup.setCharLen(64);
		colGroup.setComment("组ID");
		colGroup.setFieldName(SysBoEnt.FIELD_GROUP);
		
		Column colCreateTime=new DefaultColumn();
		colCreateTime.setColumnType(Column.COLUMN_TYPE_DATE);
		colCreateTime.setComment("创建时间");
		colCreateTime.setFieldName(SysBoEnt.FIELD_CREATE_TIME);
		
		Column colUpdTime=new DefaultColumn();
		colUpdTime.setColumnType(Column.COLUMN_TYPE_DATE);
		colUpdTime.setComment("更新时间");
		colUpdTime.setFieldName(SysBoEnt.FIELD_UPDTIME_TIME);
		//租户ID
		Column colTenantId=new DefaultColumn();
		colTenantId.setColumnType(Column.COLUMN_TYPE_VARCHAR);
		colTenantId.setCharLen(64);
		colTenantId.setComment("租户ID");
		colTenantId.setFieldName(SysBoEnt.FIELD_TENANT);
		//实例ID
		Column instId=new DefaultColumn();
		instId.setColumnType(Column.COLUMN_TYPE_VARCHAR);
		instId.setCharLen(64);
		instId.setComment("流程实例ID");
		instId.setFieldName(SysBoEnt.FIELD_INST);
		
		//draft(草稿),runing(运行),complete(完成)
		Column status=new DefaultColumn();
		status.setColumnType(Column.COLUMN_TYPE_VARCHAR);
		status.setCharLen(20);
		status.setComment("状态");
		status.setFieldName(SysBoEnt.FIELD_INST_STATUS_);
		
		list.add(colPk);
		list.add(colFk);
		//增加父ID字段
		
		Column colParent=new DefaultColumn();
		colParent.setColumnType(Column.COLUMN_TYPE_VARCHAR);
		colParent.setCharLen(64);
		colParent.setComment("树形表");
		colParent.setFieldName(SysBoEnt.FIELD_PARENTID);
		list.add(colParent);
		
		
		list.addAll(cols);
		
		list.add(instId);
		list.add(status);
		
		list.add(colUser);
		list.add(colGroup);
		
		list.add(colTenantId);
		
		list.add(colCreateTime);
		list.add(colUpdTime);
		
		
		return list;
	}
	
	/**
	 * 添加bo实体。
	 * @param boEnt
	 * @return
	 */
	public SysBoEnt createBoEnt(SysBoEnt boEnt,boolean genId){
		if(genId || StringUtil.isEmpty(boEnt.getId())){
			String id=IdUtil.getId();
			boEnt.setId(id);
		}
		String entId=boEnt.getId();
		
		//添加实体
		sysBoEntQueryDao.create(boEnt);
		//添加属性
		List<SysBoAttr> attrs= boEnt.getSysBoAttrs();
		
		for(SysBoAttr attr:attrs){
			attr.setId(IdUtil.getId());
			attr.setEntId(entId);
			sysBoAttrQueryDao.create(attr);
		}
		
		return boEnt;
	}
	
	/**
	 * 通过entId获取该entity下的所有属性
	 * @param entId
	 * @return
	 */
	public List<SysBoAttr> getByEntId(String entId){
		List<SysBoAttr> attrs=  sysBoAttrQueryDao.getAttrsByEntId(entId);
		return attrs;
	}
	
	
	/**
	 * 根据boent 获取初始化数据。
	 * @param boEnt
	 * @return
	 */
	public JSONObject getInitData(SysBoEnt boEnt) {
		JSONObject rtnJson=getJsonByEnt(boEnt);
		List<SysBoEnt> subList=boEnt.getBoEntList();
		if(BeanUtil.isEmpty(subList)) return rtnJson;
		
		JSONObject initJson=new JSONObject();
		
		for(SysBoEnt subEnt:subList){
			JSONObject subJson=getJsonByEnt(subEnt);
			
			initJson.put(subEnt.getName(), subJson);
			
			JSONArray subAry=new JSONArray();
			rtnJson.put(SysBoEnt.SUB_PRE +subEnt.getName(), subAry);
		}
		
		rtnJson.put("initData", initJson);
		
		return rtnJson;
	}
	
	private JSONObject getJsonByEnt(SysBoEnt ent){
		JSONObject json=new JSONObject();
		List<SysBoAttr>  list=ent.getSysBoAttrs();
		for(SysBoAttr attr:list){
			String plugin=attr.getControl() ;
			IBoAttrParse parse= boAttrParseFactory.getByPluginName(plugin);
			
			JSONObject obj= parse.getInitData(attr);
			
			String key= AttrParseUtil.getKey(obj);
			String name= AttrParseUtil.getName(obj);
			
			json.put(attr.getName() , key);
			
			if(!attr.single()){
				json.put(attr.getName() + SysBoEnt.COMPLEX_NAME.toLowerCase(), name);
			}
		}
		
		return json;
	}
	
	/**
	 * 删除属性。
	 * @param attrId
	 */
	public void removeAttr(String attrId){
		SysBoAttr attr= sysBoAttrQueryDao.get(attrId);
    	SysBoEnt boEnt= sysBoEntQueryDao.get( attr.getEntId());
    	//删除列
    	if(boEnt.getGenTable()==SysBoDef.BO_YES){
    		tableOperator.dropColumn(boEnt.getTableName(), attr.getFieldName());
    	}
    	sysBoAttrQueryDao.delete(attrId);
	}

}
