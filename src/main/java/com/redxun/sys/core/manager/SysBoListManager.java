
package com.redxun.sys.core.manager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.dao.mybatis.CommonDao;
import com.redxun.core.dao.mybatis.domain.PageList;
import com.redxun.core.database.api.ITableMeta;
import com.redxun.core.database.api.model.Column;
import com.redxun.core.database.api.model.Table;
import com.redxun.core.database.datasource.DataSourceUtil;
import com.redxun.core.database.datasource.DbContextHolder;
import com.redxun.core.engine.FreemakerUtil;
import com.redxun.core.engine.FreemarkEngine;
import com.redxun.core.entity.GridHeader;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.script.GroovyEngine;
import com.redxun.core.util.Base64Util;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.bo.entity.SysBoEnt;
import com.redxun.sys.core.dao.SysBoListDao;
import com.redxun.sys.core.dao.SysBoListQueryDao;
import com.redxun.sys.core.entity.SysBoList;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.entity.TreeConfig;
import com.redxun.sys.core.enums.MiniGridColumnType;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.ui.grid.GridColEditParseConfig;
import com.redxun.ui.grid.GridColEditParseHandler;
import com.redxun.ui.query.QueryControlParseConfig;
import com.redxun.ui.query.QueryControlParseHandler;
import com.redxun.ui.view.model.UrlColumn;

import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import oracle.sql.TIMESTAMP;

/**
 * 
 * <pre> 
 * 描述：系统自定义业务管理列表 处理接口
 * 作者:mansan
 * 日期:2017-05-21 12:11:18
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class SysBoListManager extends ExtBaseManager<SysBoList>{
	@Resource
	private SysBoListDao sysBoListDao;
	@Resource
	private SysBoListQueryDao sysBoListQueryDao;
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	CommonDao commonDao;
	@Resource
	FreemarkEngine freemarkEngine;
	@Resource
	QueryControlParseConfig queryControlParseConfig;
	@Resource
	GridColEditParseConfig gridColEditParseConfig;
	@Resource
	ITableMeta iTableMeta;
	@Resource
	GroovyEngine groovyEngine;
	@Resource
	OsGroupManager osGroupManager;
	@Resource
	SysInstManager sysInstManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysBoListDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return sysBoListQueryDao;
	}
	

	/**
	 * 
	 * @param key
	 * @param tenantId
	 * @return
	 */
	public SysBoList getByKey(String key,String tenantId){
		SysBoList sysBoList=sysBoListDao.getByKey(key, tenantId);
		if(sysBoList==null){
			sysBoList=sysBoListDao.getByKey(key, SysInst.ADMIN_TENANT_ID);
		}
		return sysBoList;
	}
	
	/**
	 * 
	 * 判断Key是否存在
	 * @param key
	 * @param tenantId
	 * @param pkId
	 * @return
	 */
	public boolean isKeyExist(String key,String tenantId,String pkId){
		SysBoList sysBoList=getByKey(key,tenantId);
		if(StringUtils.isEmpty(pkId) && sysBoList!=null){
			return true;
		}else if(StringUtils.isNotEmpty(pkId) && sysBoList!=null && !(pkId.equals(sysBoList.getPkId()))){
			return true;
		}
		
		return false;
	}
	/**
	 * 获得栏目的HTML
	 * @param colJsons
	 * @return
	 */
	private String getColumnsHtml(String colJsons,List<UrlColumn> urlColumns,Map<String,Object> params){
		JSONArray columnJsons=JSONArray.parseArray(colJsons);
		Element el=new Element(Tag.valueOf("div"),"");
		el.attr("property","columns");
		
		Element indexCol=new Element(Tag.valueOf("div"),"");
		indexCol.attr("type","indexcolumn");
		el.appendChild(indexCol);
		
		Element checkCol=new Element(Tag.valueOf("div"),"");
		checkCol.attr("type","checkcolumn");
		el.appendChild(checkCol);
		
		genGridColumns(columnJsons,el,urlColumns,params);
		return el.toString();
	}
	
	private String getRapidSearchHtml(JSONArray searchJsonArr,Map<String,Object> params){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<searchJsonArr.size();i++){
			JSONObject config=searchJsonArr.getJSONObject(i);
			String fieldLabel=config.getString("fieldLabel");
			if(StringUtils.isEmpty(fieldLabel))continue;

			String fc=config.getString("fc");
			Element span=new Element(Tag.valueOf("span"),"");
			span.attr("class","rapid-class");
			QueryControlParseHandler handler=queryControlParseConfig.getControlParseHandler(fc);
			
			Element conEl=handler.parse(config,params);
			conEl.attr("onenter","onRapidSearch()");
			span.appendChild(conEl);
			sb.append(span.toString()).append("&nbsp;");
		}
		return sb.toString();
	}
	
	/**
	 * 获得搜索栏的Html
	 * @param searchJson
	 * @return
	 */
	private String getSearchHtml(String searchJson,Map<String,Object> params){
		if(StringUtils.isEmpty(searchJson)){
			return "";
		}
		Element divEl=new Element(Tag.valueOf("div"),"");
		Element ulEl=null;
		JSONArray jsonArray=JSONArray.parseArray(searchJson);
		JSONArray jsonAry=getByType(jsonArray,"query");
		for(int i=0;i<jsonAry.size();i++){
			if(i==0){
				ulEl=new Element(Tag.valueOf("ul"),"");
				divEl.appendChild(ulEl);
			}
			JSONObject config=jsonAry.getJSONObject(i);
			String fieldLabel=config.getString("fieldLabel");
			String fieldOp=config.getString("fieldOp");
			if(StringUtils.isEmpty(fieldLabel))continue;
			//分割符，则换行
			if("-".equals(fieldOp)){
				ulEl=new Element(Tag.valueOf("ul"),"");
				divEl.appendChild(ulEl);
				continue;
			}
			
			String fc=config.getString("fc");
			Element li=new Element(Tag.valueOf("li"),"");
			Element span=new Element(Tag.valueOf("span"),"");
			span.text(fieldLabel);
			li.appendChild(span);

			QueryControlParseHandler handler=queryControlParseConfig.getControlParseHandler(fc);
			
			Element conEl=handler.parse(config,params);
			li.appendChild(conEl);
			ulEl.appendChild(li);
			
			if(i==jsonAry.size()-1){
				String searchBtns="<li class=\"searchBtnBox\"><a class=\"mini-button _search\" onclick=\"onSearch()\" >搜索</a><a class=\"mini-button _reset\"";
				searchBtns+=" onclick=\"onClear()\">清空</a></li><li class=\"clearfix\"></li>";
				ulEl.append(searchBtns);
			}
			
			
		}
		return divEl.html();
	}
	
	public JSONArray getByType(JSONArray jsonArray,String type){
		JSONArray ary=new JSONArray();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject config=jsonArray.getJSONObject(i);
			if(config.containsKey("type")){
				if(config.getString("type") .equals(type)){
					ary.add(config);
				}
			}
			else {
				if("query".equals(type)){
					ary.add(config);
				}
			}
		}
		return ary;
	}
	
	/**
	 * 返回头部的按钮配置
	 * @param topButtonJson
	 * @return
	 */
	private JSONArray getTopButtonHtml(String topButtonJson){
		if(StringUtils.isEmpty(topButtonJson)){
			topButtonJson="[]";
		}
		JSONArray jsonArr=JSONArray.parseArray(topButtonJson);
		
		return jsonArr;
	}
	
	/**
	 * 产生对应的页面
	 * @param sysBoList
	 * @return
	 * @throws TemplateException 
	 * @throws IOException 
	 */
	public String[] genHtmlPage(SysBoList sysBoList,Map<String,Object> params) throws IOException, TemplateException{
		params.put("name", sysBoList.getName());
		params.put("leftNav", sysBoList.getLeftNav());
		params.put("isLeftTree", sysBoList.getIsLeftTree());
		params.put("enableFlow", "YES".equals(sysBoList.getEnableFlow())?"YES":"NO");
		params.put("isDialog",sysBoList.getIsDialog());
		params.put("isShare", sysBoList.getIsShare());
		
		//当值都大于0时，才会冻结某列
		if(sysBoList.getStartFroCol()!=null && 
				sysBoList.getEndFroCol()!=null && sysBoList.getEndFroCol()>0){
			params.put("showFroCol", "true");
		}else{
			params.put("showFroCol", "false");
		}
		//数据行控件绑定
		if("tree".equals(sysBoList.getDataStyle())){
			params.put("controlClass","mini-treegrid");
		}else{
			params.put("controlClass","mini-datagrid");
		}
		
		List<UrlColumn> urlColumns=new ArrayList<UrlColumn>();
		
		//产生表格的Html的Json
		String colsHtml=getColumnsHtml(sysBoList.getColsJson(),urlColumns,params);
		params.put("gridColumns", colsHtml);
		params.put("urlColumns",urlColumns);
		//产生搜索条件的Html
		String searchHtml=getSearchHtml(sysBoList.getSearchJson(),params);
		params.put("searchHtml",searchHtml);

		//返回json字段。
		JSONArray fieldRtn = getRtnJson(sysBoList);
		params.put("fieldsJson",fieldRtn);
		params.put("colsJson",JSONArray.parseArray(sysBoList.getColsJson()));
		
		JSONArray searchJson=  JSONArray.parseArray(sysBoList.getSearchJson());
		JSONArray querySearchJson=getSearchJson(searchJson,"query");
		JSONArray inSearchJson=getSearchJson(searchJson,"income");
		JSONArray rapidSerchJson=getSearchJson(searchJson,"rapid_query");
		
		params.put("querySearchJson",querySearchJson);
		params.put("inSearchJson",inSearchJson);
		params.put("rapidSerchJson",rapidSerchJson);
		
		//产生快速搜索条件的Html
		String rapidSearchHtml=getRapidSearchHtml(rapidSerchJson,params);
		params.put("rapidSearchHtml",rapidSearchHtml);
		
		//产生头部的功能按钮
		JSONArray topButtonJson=getTopButtonHtml(sysBoList.getTopBtnsJson());
		params.put("topButtonJson",topButtonJson);
		params.put("hasButton",topButtonJson.size()>0);
		
		params.put("sysBoList", sysBoList);
		params.put("drawCellScript", StringUtils.isNotEmpty(sysBoList.getDrawCellScript())?sysBoList.getDrawCellScript():"");
		List<TreeConfig> treeConfigs=JSONArray.parseArray(sysBoList.getLeftTreeJson(), TreeConfig.class);
		if(treeConfigs!=null){
			int i=1;
			for(TreeConfig config:treeConfigs){
				if(StringUtils.isNotEmpty(config.getOrgSql())){
					String sql=new String(Base64Util.encode(config.getOrgSql().getBytes("UTF-8")));
					config.setSuitableSql(sql);
				}
				if(StringUtils.isEmpty(config.getTreeId())){
					config.setTreeId("leftTree_"+i++);
				}
				if(StringUtils.isEmpty(config.getDs())){
					config.setDs("");
				}
			}
			params.put("treeConfigs", treeConfigs);
		}
		
		TemplateHashModel sysBoListModel=FreemakerUtil.getTemplateModel(this.getClass());
		params.put("SysBoListUtil", sysBoListModel);
		
		//处理手机端时，不能带有跨列的字段控制
		JSONArray orgColumns=(JSONArray)params.get("colsJson");
		JSONArray destColumns=new JSONArray();
		genColumns(orgColumns,destColumns);
		params.put("mobileCols", destColumns);
				
		String mobileHtml=freemarkEngine.mergeTemplateIntoString("list/mobileListTemplate.ftl", params);
		String html=freemarkEngine.mergeTemplateIntoString("list/pageListTemplate.ftl", params);
		String[] htmlAry=new String[2];
		htmlAry[0]=html;
		htmlAry[1]=mobileHtml;
		return htmlAry;
	}
	
	/**
	 * 产生新列头
	 * @param orgArr
	 * @param estArr
	 */
	private void genColumns(JSONArray orgArr,JSONArray destArr){
		if(orgArr==null){
			return;
		}
		for(int i=0;i<orgArr.size();i++){
			JSONObject field=orgArr.getJSONObject(i);
			JSONArray children=field.getJSONArray("children");
			if(children==null || children.size()==0){
				destArr.add(field);
			}
			genColumns(children,destArr);
		}
	}
	
	
	public static String removeByKeys(Object ary,String keys){
		JSONArray jsonAry=(JSONArray) JSONArray.toJSON(ary);
		String[] aryKey=keys.split(",");
		for(int i=0;i<jsonAry.size();i++){
			Object obj=jsonAry.get(i);
			JSONObject json=(JSONObject)obj;
			for(String key:aryKey){
				json.remove(key);
			}
		}
		return jsonAry.toJSONString();
	}
	
	private JSONArray getSearchJson(JSONArray searchJson,String type){
		JSONArray rtnAry=new JSONArray();
		for(Object obj : searchJson){
			JSONObject field=(JSONObject)obj;
			if(StringUtils.isEmpty(field.getString("type"))){
				continue;
			}
			if(field.getString("type").equals(type)){
				rtnAry.add(field);
			}
		}
		return rtnAry;
	}

	private JSONArray getRtnJson(SysBoList sysBoList) {
		JSONArray fieldAry=JSONArray.parseArray(sysBoList.getFieldsJson());
		JSONArray fieldRtn=new JSONArray();
		for(Object obj : fieldAry){
			JSONObject field=(JSONObject)obj;
			Object isRtn= field.get("isReturn");
			if(isRtn!=null && "YES".equals(isRtn.toString())){
				fieldRtn.add(field);
			}
		}
		return fieldRtn;
	}
	
	
	/**
	 * 产生对应的页面
	 * @param sysBoList
	 * @return
	 * @throws TemplateException 
	 * @throws IOException 
	 */
	public String genTreeDlgHtmlPage(SysBoList sysBoList,Map<String,Object> params) throws IOException, TemplateException{
		params.put("sysBoList", sysBoList);
		params.put("idField", sysBoList.getIdField());
		params.put("textField", sysBoList.getTextField());
		params.put("parentField", sysBoList.getParentField()==null?"":sysBoList.getParentField());
		params.put("onlySelLeaf", StringUtils.isEmpty(sysBoList.getOnlySelLeaf())?"NO":sysBoList.getOnlySelLeaf());
		params.put("allowCheck", StringUtils.isNotEmpty(sysBoList.getMultiSelect())? sysBoList.getMultiSelect():"false");
		String html=freemarkEngine.mergeTemplateIntoString("list/treeDlgTemplate.ftl", params);
		return html;
	}
	
	public Map<String,GridHeader> getGridHeaderMap(String colJsons){
		Map<String,GridHeader> headerMap=new HashMap<String,GridHeader>();
		JSONArray columnArr=JSONArray.parseArray(colJsons);
		genGridColumnHeaders(headerMap,columnArr);
		return headerMap;
	}
	
	/**
	 * 获得表格的列头
	 * @param headerMap
	 * @param columnArr
	 */
	private void genGridColumnHeaders(Map<String,GridHeader> headerMap,JSONArray columnArr){
		for(int i=0;i<columnArr.size();i++){
			JSONObject jsonObj=columnArr.getJSONObject(i);
			JSONArray children=jsonObj.getJSONArray("children");
			if(children!=null && children.size()>0){
				genGridColumnHeaders(headerMap,children);
				continue;
			}
			String header=jsonObj.getString("header");
			String field=jsonObj.getString("field");
			String renderType=jsonObj.getString("renderType");
			String renderConf=jsonObj.getString("renderConf");
			GridHeader gh=new GridHeader();
			gh.setFieldLabel(header);
			gh.setFieldName(field);
			gh.setRenderType(renderType);
			if(StringUtils.isNotEmpty(renderConf)){
				JSONObject conf=JSONObject.parseObject(renderConf);	
				gh.setRenderConfObj(conf);
			}
			headerMap.put(field,gh);
		}
	}
	
	/**
	 * 产生表格的miniui的grid列项
	 * @param columnArr
	 * @return
	 */
	private Element genGridColumns(JSONArray columnArr,Element el,List<UrlColumn> urlColumns,Map<String,Object> params){
		for(int i=0;i<columnArr.size();i++){
			JSONObject jsonObj=columnArr.getJSONObject(i);
			JSONArray children=jsonObj.getJSONArray("children");
			
			String header=jsonObj.getString("header");
			
			if(children!=null && children.size()>0){
				
				Element subEl=new Element(Tag.valueOf("div"),"");
				subEl.attr("header",header);
				String headerAlign=jsonObj.getString("headerAlign");
				if(StringUtils.isNotEmpty(headerAlign)){
					subEl.attr("headerAlign",headerAlign);
				}
				
				Element subColumns=new Element(Tag.valueOf("div"),"");
				subColumns.attr("property","columns");
				
				genGridColumns(children,subColumns,urlColumns,params);
				subEl.appendChild(subColumns);
				el.appendChild(subEl);
			}else{
				String field=jsonObj.getString("field");
				String allowSort=jsonObj.getString("allowSort");
				String width=jsonObj.getString("width");
				String headerAlign=jsonObj.getString("headerAlign");
				String format=jsonObj.getString("format");
				String datatype=jsonObj.getString("dataType");
				String url=jsonObj.getString("url");
				String urlType=jsonObj.getString("linkType");
				if(StringUtils.isEmpty(urlType)) {
					urlType="openWindow";
				}
				Element columnEl=new Element(Tag.valueOf("div"),"");
				
				if(format!=null && datatype!=null){
					if("date".equals(datatype)){
						columnEl.attr("dateFormat",format);
					}else if("float".equals(datatype) || "int".equals(datatype) || "currency".equals(datatype)){
						columnEl.attr("numberFormat",format);
					}
					columnEl.attr("dataType",datatype);
				}
				
				if(StringUtils.isNotEmpty(allowSort)){
					columnEl.attr("allowSort",allowSort);
				}
				if(StringUtils.isNotEmpty(header)){
					columnEl.attr("header",header);
				}
				if(StringUtils.isNotEmpty(width)){
					columnEl.attr("width",width);
				}
				
				if(StringUtils.isNotEmpty(field)){
					columnEl.attr("field",field);
					columnEl.attr("name",field);
					//加入连接字段
					if(StringUtils.isNotEmpty(url)) {
						UrlColumn urlColumn=new UrlColumn(field, url, urlType);
						urlColumns.add(urlColumn);
					}
				}
				
				if(StringUtils.isNotEmpty(headerAlign)){
					columnEl.attr("headerAlign",headerAlign);
				}
				
				el.appendChild(columnEl);
				//若配置了编辑器框，则生成带有编辑控件
				String controlConf=jsonObj.getString("controlConf");
				String control=jsonObj.getString("control");
				if(StringUtils.isNotEmpty(control) && StringUtils.isNotEmpty(controlConf)){
					JSONObject controlConfJson=JSONObject.parseObject(controlConf);
					GridColEditParseHandler handler=gridColEditParseConfig.getControlParseHandler(control);
					Element editor=handler.parse(columnEl,controlConfJson, params);
					if(editor!=null){
						columnEl.appendChild(editor);
					}
				}
			}
		}
		
		return el;
	}

	
	
	/**
	 * 通过外部参数获得业务实体的SQL
	 * @param sysBoList
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getValidSql(SysBoList sysBoList,Map<String,Object> params) throws Exception{

		String newSql=null;
		if("YES".equals(sysBoList.getUseCondSql())){
			String sql=freemarkEngine.parseByStringTemplate(params, sysBoList.getCondSqls());
			newSql=(String)groovyEngine.executeScripts(sql, params);
		}else{
			newSql=freemarkEngine.parseByStringTemplate(params, sysBoList.getSql());
		}
		//若不存在授权配置，则直接返回
		if(StringUtils.isEmpty(sysBoList.getDataRightJson())){
			return newSql;
		}
		
		JSONArray arr=JSONArray.parseArray(sysBoList.getDataRightJson());
		if(arr==null || arr.size()==0){
			return newSql;
		}
		//若需要进行授权，则在外面需要嵌多一层集合数据以进行数据权限的过滤
		StringBuffer sb=new StringBuffer("select * from (");
		//取得最后的order by 
		String orderBy=null;
		String alias=null;
		int lastOrderBy=newSql.toUpperCase().indexOf(" ORDER BY ");
		
		if(lastOrderBy!=-1){
			orderBy=newSql.substring(lastOrderBy);
			int dotIndex=orderBy.indexOf(".");
			if(dotIndex!=-1){
				alias=orderBy.substring(10,dotIndex);
			}
			newSql=newSql.substring(0,lastOrderBy);
		}
		
		if(orderBy==null){
			orderBy="";
		}
		if(alias==null){
			alias="_tmp";
		}
		sb.append(newSql).append(") ").append(alias).append(" where 1=1 ");
		
		if(arr.size()>0){
			sb.append(" and (");
		}
		IUser curUser=ContextUtil.getCurrentUser();
		StringBuffer conSb=new StringBuffer();
		for(int i=0;i<arr.size();i++){
			JSONObject obj=arr.getJSONObject(i);
			String field=obj.getString("field");
			String scope=obj.getString("scope");
			
			
			if("CREATE_USER_ID_".equals(field)){//按创建用户查询
				if("SELF".equals(scope)){
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias)
					.append(".CREATE_USER_ID_='").append(curUser.getUserId()).append("'");
					continue;
				}
				String upLowPath=curUser.getUserUpLowPath();
				//若路径为空，则只能看到自己本身的数据
				if(StringUtils.isEmpty(upLowPath)){
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias)
					.append(".CREATE_USER_ID_='").append(curUser.getUserId()).append("'");
					continue;
				}
				
				if("DUP_USERS".equals(scope)){
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias)
					.append(".CREATE_USER_ID_ in ").append("(select PARTY1_ from OS_REL_INST oi where oi.PATH_='"+upLowPath+"' and oi.REL_TYPE_ID_='3')");
				}else if("UP_USERS".equals(scope)){
					String parentPath=StringUtil.getParentPath(upLowPath);
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias)
					.append(".CREATE_USER_ID_ in ").append("(").append(StringUtil.getArrCharString(parentPath)).append(")");
				}else if("DDOWN_USERS".equals(scope)){
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias)
					.append(".CREATE_USER_ID_ in ").append("(select PARTY2_ from OS_REL_INST oi where oi.PARTY1_='"+curUser.getUserId()+"' and oi.REL_TYPE_ID_='3')");
				}else if("DOWN_USERS".equals(scope)){
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias)
					.append(".CREATE_USER_ID_ in (select PARTY2_ from OS_REL_INST oi where oi.PATH_ like '"+curUser.getUserUpLowPath()+"%')");
				}
			}else if("CREATE_GROUP_ID_".equals(field)){//按用户组查找
				String curDepId=curUser.getMainGroupId();
				if(curDepId==null){
					curDepId="0";
					continue;
				}
				OsGroup mainGroup=((OsUser)curUser).getMainDep();
				
				if("SELF_DEP".equals(scope)){
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias)
					.append(".CREATE_GROUP_ID_='").append(curDepId).append("'");
					continue;
				}else if("DUP_DEPS".equals(scope)){
					//取得上级部门
					OsGroup upGroup=osGroupManager.get(mainGroup.getParentId());
					String parentGpId=(upGroup!=null)?upGroup.getGroupId():"0";
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias).append(".CREATE_GROUP_ID_='").append(parentGpId).append("'");
				}else if("UP_DEPS".equals(scope)){//所有上级部门
					String groupPath=StringUtils.isEmpty(mainGroup.getPath())?"-1":mainGroup.getPath();//若当前的目录路径为空,则让他找不到数据
					String parentPath=StringUtil.getParentPath(groupPath);
					String idArr=StringUtil.getArrCharString(parentPath);
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias).append(".CREATE_GROUP_ID_ in ("+idArr+")");
				}else if("DDOWN_DEPS".equals(scope)){
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias).append(".CREATE_GROUP_ID_ in (select group_id_ from os_group p where p.parent_id_='"+mainGroup.getGroupId()+"')");
				}else if("DOWN_DEPS".equals(scope)){
					String groupPath=StringUtils.isEmpty(mainGroup.getPath())?"-1":mainGroup.getPath();//若当前的目录路径为空,则让他找不到数据
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias).append(".CREATE_GROUP_ID_ in (select group_id_ from os_group p where p.path_ like '"+groupPath+"%')");
				}
			}else if ("TENANT_ID_".equals(field)){//按机构查询
				String curTenantId=curUser.getTenant().getTenantId();
				if(curTenantId==null){
					curTenantId="0";
					continue;
				}
				SysInst itenant=(SysInst)curUser.getTenant();
				
				if("SELF_TENANT".equals(scope)){
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias)
					.append(".TENANT_ID_='").append(curTenantId).append("'");
					continue;
				}else if("DUP_TENANTS".equals(scope)){
					//取得上级机构 
					
					SysInst pInst=sysInstManager.get(itenant.getParentId());
					String pInstId=(pInst!=null)?pInst.getInstId():"0";
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias).append(".TENANT_ID_='").append(pInstId).append("'");
				}else if("UP_TENANTS".equals(scope)){//所有上级部门
					//取得上级机构 
					SysInst pInst=sysInstManager.get(itenant.getParentId());
					String pPath=StringUtils.isEmpty(pInst.getPath())?"-1":pInst.getPath();//若当前的目录路径为空,则让他找不到数据
					String parentPath=StringUtil.getParentPath(pPath);
					String idArr=StringUtil.getArrCharString(parentPath);
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias).append(".TENANT_ID_ in ("+idArr+")");
				}else if("DDOWN_TENANTPS".equals(scope)){
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias).append(".TENANT_ID_ in (select inst_id_ from sys_inst p where p.parent_id_='"+curTenantId+"')");
				}else if("DOWN_TENANTS".equals(scope)){
					String pPath=StringUtils.isEmpty(itenant.getPath())?"-1":itenant.getPath();//若当前的目录路径为空,则让他找不到数据
					if(conSb.length()>0){
						conSb.append(" or ");
					}
					conSb.append(alias).append(".TENANT_ID_ in ((select inst_id_ from sys_inst p where p.path_ like '"+pPath+"%')");
				}
			}
		}
		
		sb.append(conSb.toString());
		
		if(arr.size()>0){
			sb.append(")");
		}
		
		sb.append(orderBy);
		System.out.println("sql=================================================>"+sb.toString());
		return sb.toString();
	}
	
	
	/**
	 * 返回sql的查询结果
	 * @param sql
	 * @param page
	 * @return
	 */
	public PageList<Map<String,Object>> getPageDataBySql(String sql,Page page){
		//获得SQL数据结果并分页
		PageList<Map<String,Object>> results=commonDao.query(sql, null, page);
		return results;
	}
	
	public PageList<Map<String,Object>> getPageDataBySql(String sql,QueryFilter filter){
		return commonDao.queryDynamicList(sql, filter,null);
	}
	
	public List getDataBySql(String sql) throws SQLException{
		List list= commonDao.query(sql);
		for(Object row:list){
			Map<String,Object> o=(Map<String,Object>)row;
			handRow( o);
		}
		return list;
	}
	
	private void handRow(Map<String,Object> row) throws SQLException{
		for (Map.Entry<String, Object> ent : row.entrySet()) {
			Object obj=ent .getValue();
			if (obj instanceof TIMESTAMP) {
				Date date= getOracleTimestamp((TIMESTAMP)obj);
				row.put(ent.getKey(), date);
			}
		}
	}
	
	private Date  getOracleTimestamp(TIMESTAMP value) throws SQLException { 
		return value.dateValue();
	}
	
	public SysBoList getByKey(String key){
		String tenantId=ContextUtil.getCurrentTenantId();
		return sysBoListDao.getByKey(key, tenantId);
	}
	
	public Long insertData(List<Map<String,Object>> dataList,SysBoEnt boent){
		String tableName = boent.getTableName();
		long l = 0 ;
		for(Map<String,Object> data:dataList){
			StringBuffer keysb = new StringBuffer();
			StringBuffer valuesb = new StringBuffer();
			Map<String,Object> params=new HashMap<String, Object>();
			keysb.append("ID_").append(",");
			valuesb.append("#{ID_},");
			params.put("ID_",IdUtil.getId());
			keysb.append("CREATE_USER_ID_").append(",");
			valuesb.append("#{CREATE_USER_ID_},");
			params.put("CREATE_USER_ID_",ContextUtil.getCurrentUserId());
			keysb.append("CREATE_TIME_").append(",");
			valuesb.append("#{CREATE_TIME_},");
			params.put("CREATE_TIME_",new Date());
			for(Map.Entry<String, Object> entry:data.entrySet()){
				keysb.append(entry.getKey()).append(",");
				valuesb.append("#{").append(entry.getKey()).append("},");
				params.put(entry.getKey(),entry.getValue());

			}
			if(keysb.length()>0){
				keysb.deleteCharAt(keysb.length()-1);
				valuesb.deleteCharAt(valuesb.length()-1);
			}
			String sql = "insert into "+tableName+" ("+keysb+") values ("+valuesb+")" ;
			commonDao.execute(sql, params);
			l++;
		}
		return l;
	}
}
