package com.redxun.ui.grid.column.render;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.entity.GridHeader;
import com.redxun.sys.core.enums.MiniGridColumnType;

/**
 * 表格列表中多项值枚举列的展示
 * @author mansan
 *
 */
public class MiniGridColumnRenderDisplayItems implements MiniGridColumnRender{

	@Override
	public String getRenderType() {
		return MiniGridColumnType.DISPLAY_ITEMS.name();
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
			String name=rowObj.getString("name");
			String code=rowObj.getString("code");
			if(StringUtils.isEmpty(name) || StringUtils.isEmpty(code)){
				continue;
			}
			//找到该值
			if(code.equals(val.toString())){
				//若为导出，直接显示其名称即可。
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
		
		return val.toString();
	}
}
