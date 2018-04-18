package com.redxun.ui.grid.column.render;

import java.util.Map;

import com.redxun.core.entity.GridHeader;

/**
 * MiniUI表列数值渲染
 * @author mansan
 *
 */
public interface MiniGridColumnRender {
	/**
	 * 获得渲染类型
	 * @return
	 */
	String getRenderType();
	/**
	 * 根据传入的值进行返回值的渲染
	 * @param gridHeader
	 * @param val
	 * @param isExport
	 * @return
	 */
	String render(GridHeader gridHeader,Map<String,Object> rowData,Object val,boolean isExport);
}
