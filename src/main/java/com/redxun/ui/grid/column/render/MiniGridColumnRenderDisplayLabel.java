package com.redxun.ui.grid.column.render;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.redxun.core.entity.GridHeader;
import com.redxun.sys.core.enums.MiniGridColumnType;

/**
 * 表格列表中显示标签列的值展示
 * @author mansan
 *
 */
public class MiniGridColumnRenderDisplayLabel implements MiniGridColumnRender{

	@Override
	public String getRenderType() {
		return MiniGridColumnType.DISPLAY_LABEL.name();
	}

	@Override
	public String render(GridHeader gridHeader, Map<String,Object> rowData,Object val, boolean isExport) {
		if(val==null) return "";

		if(!isExport){
			String isfull=gridHeader.getRenderConfObj().getString("isfull");
			if(StringUtils.isEmpty(isfull)){
				isfull="";
			}
			String bgcolor=gridHeader.getRenderConfObj().getString("bgcolor");
			String fgcolor=gridHeader.getRenderConfObj().getString("fgcolor");
			if(StringUtils.isEmpty(bgcolor)){
				bgcolor="green";
			}
			if(StringUtils.isEmpty(fgcolor)){
				fgcolor="white";
			}
			String splitChar=gridHeader.getRenderConfObj().getString("splitChar");
			if(StringUtils.isEmpty(splitChar)){
				splitChar=" ";
			}
			StringBuffer sb=new StringBuffer();
			String[]vals=val.toString().split(splitChar);
			for(String v:vals){
				sb.append("<span class='display-label-"+isfull+"' style='background-color:").append(bgcolor)
				.append(";color:").append(fgcolor).append("'>"+v+"</span>&nbsp;");
			}
			return sb.toString();
		}
		return val.toString();
	}
}
