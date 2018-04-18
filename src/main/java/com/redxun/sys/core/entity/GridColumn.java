package com.redxun.sys.core.entity;

import java.util.List;

/**
 * 
 * <pre> 
 * 描述：表格列
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱: chshxuan@163.com
 * 日期:2014年8月20日-下午5:31:21
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public class GridColumn {
	private String text;
	private Integer width;
	private Boolean sortable=true;
	private String dataIndex;
	private String renderer;
	private String formatter;
	private Boolean hidden=false;
	List<GridColumn> columns=null;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Boolean getSortable() {
		return sortable;
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public List<GridColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<GridColumn> columns) {
		this.columns = columns;
	}

	public String getRenderer() {
		return renderer;
	}

	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	} 
	
	
}
