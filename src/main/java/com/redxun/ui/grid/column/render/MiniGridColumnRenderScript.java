package com.redxun.ui.grid.column.render;

import java.util.Map;

import javax.annotation.Resource;

import com.redxun.core.entity.GridHeader;
import com.redxun.core.script.GroovyEngine;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.core.enums.MiniGridColumnType;

public class MiniGridColumnRenderScript implements MiniGridColumnRender{
	@Resource
	GroovyEngine groovyEngine;
	@Override
	public String getRenderType() {
		return MiniGridColumnType.SCRIPT.name();
	}

	@Override
	public String render(GridHeader gridHeader, Map<String,Object> rowData,Object val, boolean isExport) {
		if(val==null) return "";
		IUser curUser=ContextUtil.getCurrentUser();
		rowData.put("curUserId", curUser.getUserId());
		rowData.put("curDepId",curUser.getMainGroupId());
		rowData.put("curTenantId", curUser.getTenant().getTenantId());
		rowData.put("value", val);
		rowData.put("isExport", isExport);
		String script=gridHeader.getRenderConfObj().getString("script");
		
		if(StringUtil.isNotEmpty(script)){
			Object obj=groovyEngine.executeScripts(script, rowData);
			if(obj!=null){
				return obj.toString();
			}
		}
		return val.toString();
	}

}
