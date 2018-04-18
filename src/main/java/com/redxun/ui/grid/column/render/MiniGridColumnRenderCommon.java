package com.redxun.ui.grid.column.render;

import java.util.Map;

import com.redxun.core.entity.GridHeader;
import com.redxun.sys.core.enums.MiniGridColumnType;

public class MiniGridColumnRenderCommon implements MiniGridColumnRender{

	@Override
	public String getRenderType() {
		return MiniGridColumnType.COMMON.name();
	}

	@Override
	public String render(GridHeader gridHeader, Map<String,Object> rowData,Object val, boolean isExport) {
		if(val==null) return "";
		return val.toString();
	}

}
