package com.redxun.ui.grid.column.render;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.entity.GridHeader;
import com.redxun.core.script.GroovyEngine;
import com.redxun.sys.core.enums.MiniGridColumnType;

/**
 * 表格列表中数值范围
 * @author mansan
 *
 */
public class MiniGridColumnRenderDisplayRange implements MiniGridColumnRender{
	@Resource
	GroovyEngine groovyEngine;
	@Override
	public String getRenderType() {
		return MiniGridColumnType.DISPLAY_RANGE.name();
	}

	@Override
	public String render(GridHeader gridHeader, Map<String,Object> rowData,Object val, boolean isExport) {
		if(val==null) return "";
		JSONArray colorConfigs=gridHeader.getRenderConfObj().getJSONArray("colorConfigs");
		String isfull=gridHeader.getRenderConfObj().getString("isfull");
		if(StringUtils.isEmpty(isfull)){
			isfull="";
		}
		for(int i=0;i<colorConfigs.size();i++){
			JSONObject rowObj=colorConfigs.getJSONObject(i);
			String express=rowObj.getString("express");
			String name=rowObj.getString("name");
			if(StringUtils.isEmpty(express) || StringUtils.isEmpty(name)){
				continue;
			}
			try{
				Map<String,Object> params=new HashMap<String,Object>();
				params.put("value", val);
				Object result=groovyEngine.executeScripts(express, params);
				//检查该条件是否满足
				if(result instanceof Boolean){
					Boolean rs=(Boolean)result;
					if(rs==true){
						
						if(isExport){
							return name;
						}
						String bgcolor=rowObj.getString("bgcolor");
						String fgcolor=rowObj.getString("fgcolor");
						if(StringUtils.isEmpty(bgcolor)){
							bgcolor="green";
						}
						if(StringUtils.isEmpty(fgcolor)){
							fgcolor="white";
						}
						
						return "<span class='display-label-"+isfull+"' style='background-color:"+bgcolor
								+ ";color:" + fgcolor+ "'>"+name+"</span>";
						
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		return val.toString();
	}
}
