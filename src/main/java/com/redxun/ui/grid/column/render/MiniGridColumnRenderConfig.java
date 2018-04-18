package com.redxun.ui.grid.column.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

/**
 * MiniGrid列头渲染的配置
 * @author mansan
 *
 */
public class MiniGridColumnRenderConfig implements InitializingBean{
	/**
	 * 列值渲染器映射
	 */
	private Map<String, MiniGridColumnRender> columnRenderMap = new HashMap<String, MiniGridColumnRender>();
	/**
	 * 列值渲染器列表
	 */
	private List<MiniGridColumnRender> columnRenders = new ArrayList<MiniGridColumnRender>();

	/**
	 * 默认列值渲染器
	 */
	private MiniGridColumnRender defaulColumnRender = new MiniGridColumnRenderCommon();

	@Override
	public void afterPropertiesSet() throws Exception {
		for(MiniGridColumnRender render:columnRenders){
			columnRenderMap.put(render.getRenderType(), render);
		}
	}

	public Map<String, MiniGridColumnRender> getColumnRenderMap() {
		return columnRenderMap;
	}

	public void setColumnRenderMap(Map<String, MiniGridColumnRender> columnRenderMap) {
		this.columnRenderMap = columnRenderMap;
	}

	public List<MiniGridColumnRender> getColumnRenders() {
		return columnRenders;
	}

	public void setColumnRenders(List<MiniGridColumnRender> columnRenders) {
		this.columnRenders = columnRenders;
	}

	public MiniGridColumnRender getDefaulColumnRender() {
		return defaulColumnRender;
	}

	public void setDefaulColumnRender(MiniGridColumnRender defaulColumnRender) {
		this.defaulColumnRender = defaulColumnRender;
	}

}
