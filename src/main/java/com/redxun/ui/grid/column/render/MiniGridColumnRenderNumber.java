package com.redxun.ui.grid.column.render;

import java.text.DecimalFormat;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import com.redxun.core.entity.GridHeader;
import com.redxun.sys.core.enums.MiniGridColumnType;

public class MiniGridColumnRenderNumber implements MiniGridColumnRender{
	private Log logger=LogFactory.getLog(MiniGridColumnRenderNumber.class);
	@Override
	public String getRenderType() {
		return MiniGridColumnType.NUMBER.name();
	}

	@Override
	public String render(GridHeader gridHeader, Map<String,Object> rowData,Object val, boolean isExport) {
		if(val==null) return "";
		String format=gridHeader.getRenderConfObj().getString("format");
		if(StringUtils.isEmpty(format)) return val.toString();
		DecimalFormat df=new DecimalFormat(format);
		try{
			return df.format(new Double(val.toString()));
		}catch(Exception e){
			logger.error(e.getMessage());
			return val.toString();
		}
	}

}
