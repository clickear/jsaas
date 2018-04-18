package com.redxun.ui.grid.column.render;

import java.util.Map;

import javax.annotation.Resource;

import com.redxun.core.entity.GridHeader;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.enums.MiniGridColumnType;
import com.redxun.sys.core.manager.SysInstManager;

public class MiniGridColumnRenderSysInst implements MiniGridColumnRender{
	@Resource
	SysInstManager sysInstManager;
	
	@Override
	public String getRenderType() {
		return MiniGridColumnType.SYSINST.name();
	}

	@Override
	public String render(GridHeader gridHeader,Map<String,Object> rowData, Object val, boolean isExport) {
		if(val==null) return "";
		String showLink=gridHeader.getRenderConfObj().getString("showLink");
		SysInst sysInst=sysInstManager.get(val.toString());
		if(sysInst==null) return"";
		if("true".equals(showLink) && !isExport){
			return "<a href='javascript:void(0);' class='label-info' onclick=\"_ShowTenantInfo('"+sysInst.getInstId()+"')\">"+sysInst.getNameCn()+"</a>";
		}else{
			return sysInst.getNameCn();
		}
	}
}
