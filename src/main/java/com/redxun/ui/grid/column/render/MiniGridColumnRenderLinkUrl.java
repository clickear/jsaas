package com.redxun.ui.grid.column.render;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.context.HttpServletContext;
import com.redxun.core.entity.GridHeader;
import com.redxun.core.script.GroovyEngine;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.sys.core.enums.MiniGridColumnType;
import com.redxun.sys.db.manager.SysSqlCustomQueryManager;

public class MiniGridColumnRenderLinkUrl implements MiniGridColumnRender{
	private Log logger=LogFactory.getLog(MiniGridColumnRenderLinkUrl.class);
	@Override
	public String getRenderType() {
		return MiniGridColumnType.lINK_URL.name();
	}

	@Override
	public String render(GridHeader gridHeader, Map<String,Object> rowData,Object value, boolean isExport) {
		if(value==null) return "";
		JSONObject config=gridHeader.getRenderConfObj();
		//url: {ctxPath}/sys/customform/sysCustomFormSetting/detail/liveCheckRecordForm/{ref.ID_}.do,
		String url=config.getString("url");
		//自定义查询
		String cusQuery=config.getString("cusQuery");
		//newWindow,openWindow
		String linkType=config.getString("linkType");
		//F_CHECKTASKID_NAME
		String replaceRule=config.getString("replaceRule");
		HttpServletRequest request=HttpServletContext.getRequest();
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("ctxPath", request.getContextPath());
		Iterator<String> keyIt=rowData.keySet().iterator();
		while(keyIt.hasNext()){
			String key=keyIt.next();
			params.put("self."+key,rowData.get(key));
		}
		StringBuffer sb=new StringBuffer();
		//需要从关联查询中获得值
		if(StringUtil.isNotEmpty(cusQuery)){
			SysSqlCustomQueryManager queryManager=(SysSqlCustomQueryManager)AppBeanUtil.getBean(SysSqlCustomQueryManager.class);
			replaceRule=replaceRule.trim();
			Map<String,Object> inputParams=new HashMap<String,Object>();
			//获得输入参数
			JSONArray paramArr=config.getJSONArray("inputFields");
			
			GroovyEngine groovyEngine=(GroovyEngine)AppBeanUtil.getBean(GroovyEngine.class);
			for(int i=0;i<paramArr.size();i++){
				JSONObject param=paramArr.getJSONObject(i);
				String mode=param.getString("mode");
				String fieldName=param.getString("fieldName");
				String bindVal=param.getString("bindVal");
				//通过脚本计算输入值
				if("script".equals(mode)){
					Object val=groovyEngine.executeScripts(bindVal, rowData);
					inputParams.put(fieldName, val);
				}else{//通过输入参数计算输入值
					Object val=rowData.get(bindVal);
					inputParams.put(fieldName, val);
				}
			}
			try{
				//返回关联查询列表
				List<?> results=queryManager.getQueryData(cusQuery, inputParams);
				
				for(int i=0;i<results.size();i++){
					Map<String,Object> rows=(Map<String,Object>)results.get(i);
					Iterator<String> it=rows.keySet().iterator();
					while(it.hasNext()){
						String key=it.next();
						params.put("ref."+key,rows.get(key));
					}
					String rule=StringUtil.replaceVariableMap(replaceRule, params);
					url=StringUtil.replaceVariableMap(url,params);
					if(i>0){
						sb.append(",");
					}
					if(!isExport){
						//url=URLEncoder.encode(url, "UTF-8");
						sb.append("<a href=\"javascript:void(0)\" onclick=\"_ShowCusLink('"+rule.trim()+"','"+linkType+"','"+url+"')\">");
					}
					sb.append(rule);
					if(!isExport){
						sb.append("</a>");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
			}		
		}else{//没有关联查询
			try{
				String rule=StringUtil.replaceVariableMap(replaceRule, params);
				url=StringUtil.replaceVariableMap(url,params);
				if(!isExport){
					sb.append("<a href=\"javascript:void(0);\" onclick=\"_ShowCusLink('"+rule+"','"+linkType+"','"+url+"')\">");
				}
				sb.append(rule);
				if(!isExport){
					sb.append("</a>");
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		return sb.toString();
	}

}
