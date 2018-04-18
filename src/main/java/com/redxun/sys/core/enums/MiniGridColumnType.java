package com.redxun.sys.core.enums;

/**
 * mini Grid的列类型
 * @author mansan
 *
 */
public enum MiniGridColumnType {
	COMMON("普通"),
	USER("用户"),
	GROUP("部门与组"),
	SYSINST("机构"),
	DATE("日期"),
	NUMBER("数字"),
	URL("URL链接"),
	DISPLAY_LABEL("单色标签"),
	DISPLAY_ITEMS("多色标签"),
	DISPLAY_PERCENT("百分比标签"),
	DISPLAY_RANGE("值区域标签"),
	lINK_URL("关联链接"),
	LINK_FLOW("关联流程"),
	FLOW_STATUS("流程状态"),
	SCRIPT("脚本计算");
	
	MiniGridColumnType(String typeName){
		this.typeName=typeName;
	}
	/**
	 * 类型名称
	 */
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
