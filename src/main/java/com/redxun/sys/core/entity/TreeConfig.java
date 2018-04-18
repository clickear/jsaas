package com.redxun.sys.core.entity;
/**
 * 用于Miniui的树的生成配置
 * @author mansan
 *
 */
public class TreeConfig {
	//树控件Id
	private String treeId;
	//左导航tab中的标签名
	private String tabName="";
	//URL参数名
	private String paramName="";
	
	private String idField;
	
	private String textField;
	
	private String parentField="";
	
	private String sql;
	//useCondSql
	private String useCondSql="NO";
	//
	private String condSqls="";
	//数据源
	private String ds;
	//点击
	private String onnodeclick="";
	//Icon图标
	private String iconCls="";
	//默认是否展开
	private String expandOnLoad="true";
	
	private String suitableSql="";

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getTabName() {
		return tabName;
	}
	
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	
	public String getIdField() {
		return idField;
	}
	
	public void setIdField(String idField) {
		this.idField = idField;
	}
	
	public String getTextField() {
		return textField;
	}
	
	public void setTextField(String textField) {
		this.textField = textField;
	}
	
	public String getParentField() {
		return parentField;
	}
	
	public void setParentField(String parentField) {
		this.parentField = parentField;
	}
	
	public String getDs() {
		return ds;
	}
	
	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getOnnodeclick() {
		return onnodeclick;
	}

	public void setOnnodeclick(String onnodeclick) {
		this.onnodeclick = onnodeclick;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getExpandOnLoad() {
		return expandOnLoad;
	}

	public void setExpandOnLoad(String expandOnLoad) {
		this.expandOnLoad = expandOnLoad;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getUseCondSql() {
		return useCondSql;
	}

	public void setUseCondSql(String useCondSql) {
		this.useCondSql = useCondSql;
	}

	public String getCondSqls() {
		return condSqls;
	}

	public void setCondSqls(String condSqls) {
		this.condSqls = condSqls;
	}

	public String getSuitableSql() {
		return suitableSql;
	}

	public void setSuitableSql(String suitableSql) {
		this.suitableSql = suitableSql;
	}
	
	//获得原始SQL
	public String getOrgSql(){
		if("YES".equals(useCondSql)){
			return condSqls;
		}
		return sql;
	}
}
