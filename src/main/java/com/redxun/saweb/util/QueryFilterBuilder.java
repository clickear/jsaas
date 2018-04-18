package com.redxun.saweb.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redxun.core.json.JSONUtil;
import com.redxun.core.query.FieldLogic;
import com.redxun.core.query.IWhereClause;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.core.query.SortParam;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.core.util.DateUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.sys.core.entity.SearchItemNode;
import com.redxun.sys.core.manager.SysSearchItemManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**import org.displaytag.util.ParamEncoder;*/

/**
 * QueryFilter的构造器
 * @author csx
 */
public class QueryFilterBuilder {
	
	private static Log logger=LogFactory.getLog(QueryFilterBuilder.class);
	
    public final static String ORDER_ASC="1";
    
	public final static String ORDER_DESC="2"; 
	
	private static Pattern regex3 = Pattern.compile("Q_(.+)_(.+)_(.+)", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL | Pattern.MULTILINE);
	
	private static Pattern regex2 = Pattern.compile("Q_(.+)_(.+)", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL | Pattern.MULTILINE);
	
	private static Pattern regex1 = Pattern.compile("Q_(.+)", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL | Pattern.MULTILINE);
	
	
	
	
	public static SqlQueryFilter createSqlQueryFilter(HttpServletRequest request){
		SqlQueryFilter filter=new SqlQueryFilter();
		Page page=new Page();
    	//设置分页信息
        int pageIndex = RequestUtil.getInt(request, "pageIndex", 0);
        int pageSize = RequestUtil.getInt(request, "pageSize", Page.DEFAULT_PAGE_SIZE);
        //顺序不能调整
        page.setPageSize(pageSize);
        page.setPageIndex(pageIndex);
        
        filter.setPage(page);
        
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        
        if(StringUtils.isNotEmpty(sortField)&& StringUtils.isNotEmpty(sortOrder)){
            SortParam sortParam = new SortParam(sortField, sortOrder);
            filter.setSortParam(sortParam);
        }
        
        Map<String,Object> params=getSqlQueryParams(request);
        
        filter.setParams(params);
        
		return filter;
	}
	
	public static SqlQueryFilter createSqlQueryFilter(JSONObject cmdObj){
		
		SqlQueryFilter filter=new SqlQueryFilter();
		Page page=new Page();
    	//设置分页信息
        int pageIndex = JSONUtil.getInt(cmdObj,"pageIndex",0);
        int pageSize = JSONUtil.getInt(cmdObj, "pageSize", Page.DEFAULT_PAGE_SIZE);
        //顺序不能调整
        page.setPageSize(pageSize);
        page.setPageIndex(pageIndex);
        
        filter.setPage(page);
        
        String sortField = JSONUtil.getString(cmdObj,"sortField");
        String sortOrder = JSONUtil.getString(cmdObj,"sortOrder");
        
        if(StringUtils.isNotEmpty(sortField)&& StringUtils.isNotEmpty(sortOrder)){
            SortParam sortParam = new SortParam(sortField, sortOrder);
            filter.setSortParam(sortParam);
        }
        
        Map<String,Object> params=getSqlQueryParams(cmdObj);
        filter.setParams(params);
        return filter;
	}
	
	public static SqlQueryFilter createSqlQueryFilter(String jsonParams){
		JSONObject cmdObj=JSONObject.fromObject(jsonParams);
		return createSqlQueryFilter(cmdObj);
	}
	
	/**
	 *  创建Page
	 * @param request
	 * @return
	 */
    public static Page createPage(HttpServletRequest request){
    	
    	Page page=new Page();
    	//设置分页信息
        int pageIndex = RequestUtil.getInt(request, "pageIndex", 0);
        int pageSize = RequestUtil.getInt(request, "pageSize", Page.DEFAULT_PAGE_SIZE);
        //顺序不能调整
        page.setPageSize(pageSize);
        page.setPageIndex(pageIndex);
        
        return page;
    }
    
    public static QueryFilter createQueryFilter(JSONObject cmdObj){
		
    	QueryFilter filter=new QueryFilter();
		Page page=new Page();
    	//设置分页信息
        int pageIndex = JSONUtil.getInt(cmdObj,"pageIndex",0);
        int pageSize = JSONUtil.getInt(cmdObj, "pageSize", Page.DEFAULT_PAGE_SIZE);
        //顺序不能调整
        page.setPageSize(pageSize);
        page.setPageIndex(pageIndex);
        
        filter.setPage(page);
        
        String sortField = JSONUtil.getString(cmdObj,"sortField");
        String sortOrder = JSONUtil.getString(cmdObj,"sortOrder");
        
        if(StringUtils.isNotEmpty(sortField)&& StringUtils.isNotEmpty(sortOrder)){
            SortParam sortParam = new SortParam(sortField, sortOrder);
            filter.getOrderByList().add(sortParam);
        }
        
        List<IWhereClause> params=getQueryParams(cmdObj);
        filter.getFieldLogic().setCommands(params);
        return filter;
	}
	
    public static QueryFilter createQueryFilter(HttpServletRequest request){
    	QueryFilter filter = new QueryFilter();
    	String preAttName=request.getParameter("joinAttName");
    	if(StringUtils.isEmpty(preAttName)){
    		preAttName="";
    	}else{
    		filter.setSelectJoinAtt(preAttName);
    		preAttName+=".";
    	}
        
        //设置分页信息
        int pageIndex = RequestUtil.getInt(request, "pageIndex", 0);
        int pageSize = RequestUtil.getInt(request, "pageSize", Page.DEFAULT_PAGE_SIZE);
        //顺序不能调整
        Page page=(Page)filter.getPage();
        page.setPageSize(pageSize);
        page.setPageIndex(pageIndex);
        
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        if(StringUtils.isNotEmpty(sortField)&& StringUtils.isNotEmpty(sortOrder)){
            SortParam sortParam = new SortParam(preAttName+sortField, sortOrder);
            filter.getOrderByList().add(sortParam);
        }
        
        //高级搜索条件组合
        String searchId=request.getParameter("_searchId");
        if(StringUtils.isNotEmpty(searchId)){
        	SysSearchItemManager  itemManager=AppBeanUtil.getBean(SysSearchItemManager.class);
        	List<SearchItemNode> searchNodes=itemManager.getSearchItemTreeNodes(searchId);
        	for(SearchItemNode node:searchNodes){
        		IWhereClause iWhereClause=constructClause(node);
        		 filter.getFieldLogic().getCommands().add(iWhereClause);
        	}
        }else{
        	String filters=request.getParameter("filter");
        	List<IWhereClause> paramsList=null;
        	if(StringUtils.isNotEmpty(filters)){
        		paramsList=constructParams(request,preAttName);
        	}else{
        		paramsList=getQueryParams(request);
        	}
        	
        	

	        //设置查询参数
	        filter.getFieldLogic().setCommands(paramsList);
        }
        return filter;
    }
    
    public static List<IWhereClause> constructParams(HttpServletRequest request,String preAttName){
    	String filters=request.getParameter("filter");
    	List<IWhereClause> paramList=new ArrayList<IWhereClause>();
    	if(StringUtils.isEmpty(filters)) return paramList;
    	 JSONArray array = JSONArray.fromObject(filters);
        for(int i=0;i<array.size();i++){
           JSONObject obj = array.getJSONObject(i);
           String property = (String) obj.get("name");
           if (!property.startsWith("Q_")) continue;
           String value = obj.get("value").toString();
           if(StringUtils.isNotEmpty(value)){
        	   paramList.add(getQueryParam(preAttName,property,new String[]{value}));
           }
        }
    	return paramList;
    }
    
    /**
     * 通过Request构建SqlQueryParams
     * @param request
     * @return
     */
    public static Map<String,Object> getSqlQueryParams(HttpServletRequest request){
    	
    	Map<String,Object> qParams=new HashMap<String, Object>();
    	
    	Enumeration<?> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] values = request.getParameterValues(key);
			if(!key.startsWith("Q_")) continue;
			if (values.length > 0 && StringUtils.isNotEmpty(values[0])) {
				String[]keys=key.split("[_]");
				Object val=null;
				if(keys.length==2){
					val=getObjValue(QueryParam.OP_EQUAL,QueryParam.FIELD_TYPE_STRING,values[0]);
				}else if(keys.length==3){
					val=getObjValue(QueryParam.OP_EQUAL,keys[2],values[0]);
				}else if(keys.length==4){
					val=getObjValue(keys[3],keys[2],values[0]);
				}
				if(val!=null){
					qParams.put(keys[1],val);
				}
			}
		}
		return qParams;
    }
    
    
    
    private static Map<String,Object> getSqlQueryParams(JSONObject jsonObj){
    	Map<String,Object> params=new HashMap<String, Object>();
    	Iterator<String> keyIt=jsonObj.keys();
    			
    	while(keyIt.hasNext()){
    		String key=keyIt.next();
    		String val=JSONUtil.getString(jsonObj, key);
    		if(StringUtils.isEmpty(val) || !key.startsWith("Q_")){
    			continue;
    		}
    		Matcher regexMatcher = regex3.matcher(key);
    		Object pVal=null;
    		String name="";
    		if(regexMatcher.find()){
    			name=regexMatcher.group(1);
    			String type=regexMatcher.group(2);
    			String opType=regexMatcher.group(3);
    			pVal=getObjValue(opType,type,val);
    		}
    		else {
    			regexMatcher = regex2.matcher(key);
    			if(regexMatcher.find()){
    				name=regexMatcher.group(1);
    				String opType=regexMatcher.group(2);
    				pVal=getObjValue(opType,QueryParam.FIELD_TYPE_STRING,val);
    			}
    			else{
    				regexMatcher = regex1.matcher(key);
    				if(regexMatcher.find()){
    					name=regexMatcher.group(1);
    					pVal=getObjValue(QueryParam.OP_EQUAL,QueryParam.FIELD_TYPE_STRING,val);
    				}
    			}
    		}
			if(StringUtil.isNotEmpty(name) && pVal!=null){
				params.put(name,pVal);
			}
    	}
    	return params;
    }
    
   
    
    /**
     * 返回查询参数
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static List<IWhereClause> getQueryParams(HttpServletRequest request){
    	List<IWhereClause> paramList=new ArrayList<IWhereClause>();
    	Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] values = request.getParameterValues(key);
			
			if (values.length > 0 && StringUtils.isNotEmpty(values[0])) {
				if (key.startsWith("Q_")) {
					paramList.add(getQueryParam(key,values));
				}
			}
		}
    	return paramList;
    }
    
    public static List<IWhereClause> getQueryParams(JSONObject json){
    	List<IWhereClause> paramList=new ArrayList<IWhereClause>();
    	Iterator<String> keyIt=json.keys();
		
    	while(keyIt.hasNext()){
		
			String key = keyIt.next();
			String value = json.getString(key);
			
			if (StringUtil.isNotEmpty(key) && StringUtil.isNotEmpty(value)) {
				if (key.startsWith("Q_")) {
					String[] values=new String[1];
					values[0]=value;
					paramList.add(getQueryParam(key,values));
				}
			}
		}
    	return paramList;
    }
    
    private static QueryParam getQueryParam(String key,String[] values){
    	return getQueryParam(null,key,values);
    }

    /**
     * 返回查询参数的构造类型
     * Key 值格式如：Q_orderNo_S_EQ，Q_orderNo_NULL，Q_orderNo
     * @param preAttr 属性前缀
     * @param key
     * @param values
     * @return
     */
	private static QueryParam getQueryParam(String preAttr,String key,String[]values){
		QueryParam param=new QueryParam();
		
		if(preAttr==null) preAttr="";
		
		Matcher regexMatcher = regex3.matcher(key);
		
		if(regexMatcher.find()){
			String name=regexMatcher.group(1);
			String type=regexMatcher.group(2);
			String opType=regexMatcher.group(3);
			param.setFieldName(preAttr+name);
			param.setFieldType(type);
			param.setOpType(opType);
			Object value=getObjValue(param.getOpType(),param.getFieldType(),values[0]);
			if(value!=null){
				param.setValue(value);
			}
		}
		else {
			regexMatcher = regex2.matcher(key);
			if(regexMatcher.find()){
				String name=regexMatcher.group(1);
				String opType=regexMatcher.group(2);
				param.setFieldName(name);
				param.setOpType(opType);
				param.setValue(values[0]);
			}
			else{
				regexMatcher = regex1.matcher(key);
				if(regexMatcher.find()){
					String name=regexMatcher.group(1);
					param.setFieldName(name);
					param.setOpType(QueryParam.OP_EQUAL);
					param.setValue(values[0]);
				}
			}
		}
		
		return param;
	}

	/**
	 * 根据传入的类型获得真正值的格式
	 * 
	 * </p>
	 * 
	 * @param type
	 * @param paramValue
	 * @return
	 */
	private static Object getObjValue(String opType,String fieldType, String paramValue) {
		Object value = null;
		try{
			// 字符串 精准匹配
			if (QueryParam.FIELD_TYPE_STRING.equals(fieldType)) {
				if(QueryParam.OP_LIKE.equals(opType)){
					value="%"+paramValue+"%";
				}else if(QueryParam.OP_LEFT_LIKE.equals(opType)){
					value=paramValue+"%";
				}else if(QueryParam.OP_RIGHT_LIKE.equals(opType)){
					value="%"+paramValue;
				}else{
					value = paramValue;
				}
			} else if ("L".equals(fieldType)) {// 长整型
				value = new Long(paramValue);
			} else if ("I".equals(fieldType)) {// 整型
				value = new Integer(paramValue);
			} else if ("DB".equals(fieldType)) {//Double类型
				value = new Double(paramValue);
			}else if ("BD".equals(fieldType)) {// decimal
				value = new BigDecimal(paramValue);
			}else if ("FT".equals(fieldType)) {// FLOAT
				value = new Float(paramValue);
			}else if ("SN".equals(fieldType)) {// short
				value = new Short(paramValue);
			}else if ("D".equals(fieldType)) {
				paramValue=paramValue.replace("T", " ");
				value = DateUtil.parseDate(paramValue); 
				if(QueryParam.OP_LESS_EQUAL.equals(opType)){
					value=DateUtil.setEndDay((Date)value);
				}else if(QueryParam.OP_GREAT_EQUAL.equals(opType)){
					value=DateUtil.setStartDay((Date)value);
				}
			}else {
				value = paramValue;
			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return value;
	}
	
	public static IWhereClause constructClause(SearchItemNode node){
		IWhereClause clause=null;
		//构建条件
		if("Field".equalsIgnoreCase(node.getNodeType())){
			QueryParam param=new QueryParam();
			param.setFieldName(node.getFieldName());
			param.setFieldType(getDataQueryType(node.getFieldType()));
			param.setOpType(getDataOpType(node.getFieldOp()));
			Object val=getObjValue(param.getOpType(),param.getFieldType(),node.getFieldVal());
			if(val!=null){
				param.setValue(val);
			}
			clause=param;
		}else if(!"Field".equalsIgnoreCase(node.getNodeType()) && node.getChildren().size()>0){
			FieldLogic fieldLogic=new FieldLogic(node.getNodeType());
			for(SearchItemNode subNode:node.getChildren()){
				IWhereClause tempClause=constructClause(subNode);
				if(tempClause!=null){
					fieldLogic.getCommands().add(tempClause);
				}
			}
			clause=fieldLogic;
		}
		return clause;
	}
	/**
	 * 获得查询的数据类型
	 * @param javaType
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getDataQueryType(String javaType){
		if("java.lang.String".equals(javaType)) return QueryParam.FIELD_TYPE_STRING;
		if("java.util.Date".equals(javaType)) return QueryParam.FIELD_TYPE_DATE;
		if("java.lang.Short".equals(javaType)) return QueryParam.FIELD_TYPE_SHORT;
		if("java.lang.Integer".equals(javaType)) return QueryParam.FIELD_TYPE_INTEGER;
		if("java.lang.Long".equals(javaType)) return QueryParam.FIELD_TYPE_LONG;
		if("java.math.BigDecimal".equals(javaType)) return QueryParam.FIELD_TYPE_BIGDECIMAL;
		if("java.lang.Double".equals(javaType)) return QueryParam.FIELD_TYPE_DOUBLE;
		if("java.lang.Float".equals(javaType)) return QueryParam.FIELD_TYPE_FLOAT;
		return QueryParam.FIELD_TYPE_STRING;
	}
	/**
	 * 获取数据比较类型
	 * @param opType
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getDataOpType(String opType){
		if("<".equals(opType)) return QueryParam.OP_LESS;
		if(">".equals(opType)) return QueryParam.OP_GREAT;
		if("<=".equals(opType)) return QueryParam.OP_LESS_EQUAL;
		if(">=".equals(opType)) return QueryParam.OP_GREAT_EQUAL;
		if("=".equals(opType)) return QueryParam.OP_EQUAL;
		if("LIKE".equals(opType)) return QueryParam.OP_LIKE;
		if("EQUAL".equals(opType)) return QueryParam.OP_EQUAL;
		if("LEFT-LIKE".equals(opType)) return QueryParam.OP_LEFT_LIKE;
		if("RIGHT-LIKE".equals(opType)) return QueryParam.OP_RIGHT_LIKE;
		return QueryParam.OP_EQUAL;
	}
	
}
