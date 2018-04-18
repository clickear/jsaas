package com.redxun.ui.grid.column.render;

import java.util.Map;

import com.redxun.bpm.enums.ProcessStatus;
import com.redxun.core.entity.GridHeader;
import com.redxun.sys.core.enums.MiniGridColumnType;

/**
 * 表格列表中流程实例的列的展示
 * @author mansan
 *
 */
public class MiniGridColumnRenderFlowStatus implements MiniGridColumnRender{

	@Override
	public String getRenderType() {
		return MiniGridColumnType.FLOW_STATUS.name();
	}

	@Override
	public String render(GridHeader gridHeader, Map<String,Object> rowData,Object val, boolean isExport) {
		if(val==null) return "";
		String label=val.toString();
		//若找到该状态
		ProcessStatus status=ProcessStatus.valueOf(label);
		if(status!=null){
			label= status.getStatusLabel();
		}
		
		if(!isExport){
			return "<span class='display-label- flow_status_"+val.toString().toLowerCase()+"'>"+label+"</span>";
		}
		return label;
	}
}
