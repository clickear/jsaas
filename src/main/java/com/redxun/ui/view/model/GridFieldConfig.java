package com.redxun.ui.view.model;
/**
 * 表格字段控件配置
 * @author mansan
 *
 */
public class GridFieldConfig {
	//标签
	private String tag="input";
	
	private String name;
	private String key;
	private String displayfield;
	private String editcontrol="mini-textbox";
	private Integer width;
	private String datatype;
	private String format;
	private String cellStyle;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDisplayfield() {
		return displayfield;
	}
	public void setDisplayfield(String displayfield) {
		this.displayfield = displayfield;
	}
	public String getEditcontrol() {
		return editcontrol;
	}
	public void setEditcontrol(String editcontrol) {
		this.editcontrol = editcontrol;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getCellStyle() {
		return cellStyle;
	}
	public void setCellStyle(String cellStyle) {
		this.cellStyle = cellStyle;
	}
	public String getTag() {
		
		if("mini-textarea".equals(editcontrol)){
			return "textarea";
		}
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
