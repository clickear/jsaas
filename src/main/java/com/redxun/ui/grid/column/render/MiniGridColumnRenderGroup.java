package com.redxun.ui.grid.column.render;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.redxun.core.entity.GridHeader;
import com.redxun.sys.core.enums.MiniGridColumnType;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;

public class MiniGridColumnRenderGroup implements MiniGridColumnRender{

	@Resource
	OsGroupManager osGroupManager;
	
	@Override
	public String getRenderType() {
		return MiniGridColumnType.GROUP.name();
	}

	@Override
	public String render(GridHeader gridHeader,Map<String,Object> rowData,Object val,boolean isExport) {
		if(val==null) return "";
		OsGroup osGroup=osGroupManager.get(val.toString());
		if(osGroup==null){
			return "";
		}
		String groupNames="";
		String showPath=gridHeader.getRenderConfObj().getString("showPath");
		if(!isExport && "true".equals(showPath) && StringUtils.isNotEmpty(osGroup.getPath())){
			groupNames=osGroupManager.getGroupFullPathNames(val.toString());
		}else{
			groupNames=osGroup.getName();
		}
		String sb="";
		String showLink=gridHeader.getRenderConfObj().getString("showLink");
		if(!isExport && "true".equals(showLink)){
			sb="<a class=\"label-info\" href=\"javascript:void(0);\" onclick=\"_ShowGroupInfo('"+osGroup.getGroupId()+"')\">";
			sb+=groupNames+"</a>";
			return sb;
		}else{
			return groupNames;
		}
	}

}
