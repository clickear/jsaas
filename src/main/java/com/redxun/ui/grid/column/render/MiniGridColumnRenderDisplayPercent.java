package com.redxun.ui.grid.column.render;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.redxun.core.entity.GridHeader;
import com.redxun.core.util.StringUtil;
import com.redxun.sys.core.enums.MiniGridColumnType;

/**
 * 表格列表中数值百分比显示
 * @author mansan
 *
 */
public class MiniGridColumnRenderDisplayPercent implements MiniGridColumnRender{

	@Override
	public String getRenderType() {
		return MiniGridColumnType.DISPLAY_PERCENT.name();
	}

	@Override
	public String render(GridHeader gridHeader, Map<String,Object> rowData,Object val, boolean isExport) {
		if(val==null) return "";
		if(!isExport){
			//是否为百分值
			Double numVal=new Double(0);
			//若为非数值
			if(!StringUtil.isNumeric(val.toString())){
				return val.toString();
			}
			
			String bgcolor=gridHeader.getRenderConfObj().getString("bgcolor");
			String fgcolor=gridHeader.getRenderConfObj().getString("fgcolor");
			String percent= gridHeader.getRenderConfObj().getString("isPercent");
			if("true".equals(percent)){
				numVal=100 * new Double(val.toString());
			}else{
				numVal=new Double(val.toString());
			}
			if(StringUtils.isEmpty(bgcolor)){
				bgcolor="green";
			}
			if(StringUtils.isEmpty(fgcolor)){
				fgcolor="white";
			}
			StringBuffer sb=new StringBuffer();
			sb.append("<div class=\"percent-bar\">").append("<div style=\"width:").append(numVal).append("%;background-color:")
			.append(bgcolor).append("\">")
			.append("<span style='color:").append(fgcolor).append("'>").append(numVal).append("%</span></div></div>");
			return sb.toString();
		}
		return val.toString();
	}
}
