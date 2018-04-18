package com.redxun.sys.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.entity.GridHeader;
import com.redxun.core.util.StringUtil;

/**
 * <pre>
 * 
 * 描述：系统自定义业务管理列表实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.cn
 * 日期:2017-05-21 12:11:18
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_BO_LIST")
@TableDefine(title = "系统自定义业务管理列表")
public class SysBoList extends BaseTenantEntity {

	@FieldDefine(title = "ID")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "解决方案ID")
	@Column(name = "SOL_ID_")
	protected String solId;
	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	protected String name;
	@FieldDefine(title = "标识键")
	@Column(name = "KEY_")
	protected String key;
	
	@FieldDefine(title = "ID标识键")
	@Column(name = "ID_FIELD_")
	protected String idField="";
	
	@FieldDefine(title = "文本展示字段")
	@Column(name = "TEXT_FIELD_")
	protected String textField="";
	
	@FieldDefine(title = "父字段")
	@Column(name = "PARENT_FIELD_")
	protected String parentField="";
	
	@FieldDefine(title = "是树型对话框")
	@Column(name = "IS_TREE_DLG_")
	protected String isTreeDlg;
	
	@FieldDefine(title="仅允许选择子节点")
	@Column(name = "ONLY_SEL_LEAF_")
	protected String onlySelLeaf;
	
	
	@FieldDefine(title = "数据URL")
	@Column(name = "URL_")
	protected String url;
	@FieldDefine(title = "是否多选")
	@Column(name = "MULTI_SELECT_")
	protected String multiSelect="true";
	
	@FieldDefine(title = "描述")
	@Column(name = "DESCP_")
	protected String descp;
	@FieldDefine(title = "是否显示左树")
	@Column(name = "IS_LEFT_TREE_")
	protected String isLeftTree;
	@FieldDefine(title = "左树导航名称")
	@Column(name = "LEFT_NAV_")
	protected String leftNav;
	@FieldDefine(title = "左树字段映射")
	@Column(name = "LEFT_TREE_JSON_")
	protected String leftTreeJson;
	@FieldDefine(title = "SQL语句")
	@Column(name = "SQL_")
	protected String sql;
	@FieldDefine(title = "使用分支条件SQL")
	@Column(name = "USE_COND_SQL_")
	protected String useCondSql;
	@FieldDefine(title = "分支条件SQLS")
	@Column(name = "COND_SQLS_")
	protected String condSqls;
	
	
	@FieldDefine(title = "数据源别名")
	@Column(name = "DB_AS_")
	protected String dbAs;
	@FieldDefine(title = "展示列的JSONN")
	@Column(name = "COLS_JSON_")
	protected String colsJson;
	@FieldDefine(title = "字段列的JSON")
	@Column(name = "FIELDS_JSON_")
	protected String fieldsJson;
	@FieldDefine(title = "列表显示模板")
	@Column(name = "LIST_HTML_")
	protected String listHtml;
	@FieldDefine(title = "搜索条件JSON")
	@Column(name = "SEARCH_JSON_")
	protected String searchJson;
	@FieldDefine(title="JS脚本")
	@Column(name="BODY_SCRIPT_")
	protected String bodyScript="";
	
	@FieldDefine(title="宽度")
	@Column(name="WIDTH_")
	protected Integer width;
	@FieldDefine(title="高度")
	@Column(name="HEIGHT_")
	protected Integer height;
	
	@FieldDefine(title = "绑定流程方案")
	@Column(name = "BPM_SOL_ID_")
	protected String bpmSolId;
	@FieldDefine(title = "绑定表单方案")
	@Column(name = "FORM_ALIAS_")
	protected String formAlias="";
	@FieldDefine(title = "头部按钮配置")
	@Column(name = "TOP_BTNS_JSON_")
	protected String topBtnsJson;
	
	@FieldDefine(title = "数据权限")
	@Column(name = "DATA_RIGHT_JSON_")
	protected String dataRightJson;
	
	@FieldDefine(title = "是否对话框")
	@Column(name = "IS_DIALOG_")
	protected String isDialog;
	
	@FieldDefine(title = "是否生成HTML")
	@Column(name = "IS_GEN_")
	protected String isGen;

	@FieldDefine(title = "是否分页")
	@Column(name = "IS_PAGE_")
	protected String isPage;
	@FieldDefine(title = "是否允许导出")
	@Column(name = "IS_EXPORT_")
	protected String isExport;
	@FieldDefine(title = "是否分享表单")
	@Column(name = "IS_SHARE_")
	protected String isShare="NO";
	
	@FieldDefine(title = "树分类Id")
	@Column(name = "TREE_ID_")
	protected String treeId;
	
	@FieldDefine(title = "表格字段输出自定义")
	@Column(name = "DRAW_CELL_SCRIPT_")
	protected String drawCellScript="";
	
	@FieldDefine(title = "是否启用流程")
	@Column(name = "ENABLE_FLOW_")
	protected String enableFlow;
	
	@FieldDefine(title = "手机HTML")
	@Column(name = "MOBILE_HTML_")
	protected String mobileHtml;

	@FieldDefine(title = "数据风格")
	@Column(name = "DATA_STYLE_")
	protected String dataStyle="list";
	
	@FieldDefine(title = "行数据编辑")
	@Column(name = "ROW_EDIT_")
	protected String rowEdit="";
	
	@FieldDefine(title = "锁定开始列序号")
	@Column(name = "START_FRO_COL_")
	protected Integer startFroCol=0;
	
	@FieldDefine(title = "锁定结束列序号")
	@Column(name = "END_FRO_COL_")
	protected Integer endFroCol=0;
	
	@FieldDefine(title = "显示统计行")
	@Column(name = "SHOW_SUMMARY_ROW_")
	protected String showSummaryRow="NO";
	
	@Transient
	protected String formName;
	
	@Transient Map<String,SysBoTopButton> topButtonMap=new HashMap<String, SysBoTopButton>();
	
	@Transient Map<String,TreeConfig> leftTreeMap=new HashMap<String, TreeConfig>();
	
	@Transient Map<String,GridHeader> columnHeaderMap=new HashMap<String,GridHeader>();
	
	public SysBoList() {
	}

	public String getOnlySelLeaf() {
		return onlySelLeaf;
	}

	public void setOnlySelLeaf(String onlySelLeaf) {
		this.onlySelLeaf = onlySelLeaf;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDbAs() {
		return dbAs;
	}

	public void setDbAs(String dbAs) {
		this.dbAs = dbAs;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getMultiSelect() {
		if(StringUtil.isEmpty(this.multiSelect)){
			return "true";
		}
		return multiSelect;
	}

	public void setMultiSelect(String multiSelect) {
		this.multiSelect = multiSelect;
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

	public String getIsGen() {
		return isGen;
	}

	public void setIsGen(String isGen) {
		this.isGen = isGen;
	}

	public String getDrawCellScript() {
		return drawCellScript;
	}

	public void setDrawCellScript(String drawCellScript) {
		this.drawCellScript = drawCellScript;
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysBoList(String in_id) {
		this.setPkId(in_id);
	}

	@Override
	public String getIdentifyLabel() {
		return this.id;
	}

	@Override
	public Serializable getPkId() {
		return this.id;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.id = (String) pkId;
	}

	public String getId() {
		return this.id;
	}

	public String getEnableFlow() {
		return enableFlow;
	}

	public void setEnableFlow(String enableFlow) {
		this.enableFlow = enableFlow;
	}

	public void setId(String aValue) {
		this.id = aValue;
	}

	public void setSolId(String solId) {
		this.solId = solId;
	}

	/**
	 * 返回 解决方案ID
	 * 
	 * @return
	 */
	public String getSolId() {
		return this.solId;
	}

	

	public Map<String, TreeConfig> getLeftTreeMap() {
		return leftTreeMap;
	}

	public void setLeftTreeMap(Map<String, TreeConfig> leftTreeMap) {
		this.leftTreeMap = leftTreeMap;
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

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 返回 名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 返回 标识键
	 * 
	 * @return
	 */
	public String getKey() {
		return this.key;
	}

	public String getIsTreeDlg() {
		return isTreeDlg;
	}

	public void setIsTreeDlg(String isTreeDlg) {
		this.isTreeDlg = isTreeDlg;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	/**
	 * 返回 描述
	 * 
	 * @return
	 */
	public String getDescp() {
		return this.descp;
	}

	public void setIsLeftTree(String isLeftTree) {
		this.isLeftTree = isLeftTree;
	}

	/**
	 * 返回 是否显示左树
	 * 
	 * @return
	 */
	public String getIsLeftTree() {
		return this.isLeftTree;
	}

	public String getLeftNav() {
		return leftNav;
	}

	public void setLeftNav(String leftNav) {
		this.leftNav = leftNav;
	}

	public void setLeftTreeJson(String leftTreeJson) {
		this.leftTreeJson = leftTreeJson;
	}

	/**
	 * 返回 左树字段映射
	 * 
	 * @return
	 */
	public String getLeftTreeJson() {
		return this.leftTreeJson;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 返回 SQL语句
	 * 
	 * @return
	 */
	public String getSql() {
		return this.sql;
	}
	
	public String getRunSql(){
		if(StringUtils.isNotEmpty(this.sql)){
			try {
				return StringUtil.replaceVariableMap(this.sql, new HashMap<String, Object>());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.sql;
	}

	public String getBodyScript() {
		return bodyScript;
	}

	public void setBodyScript(String bodyScript) {
		this.bodyScript = bodyScript;
	}

	public void setColsJson(String colsJson) {
		this.colsJson = colsJson;
	}

	/**
	 * 返回 列的JSON
	 * 
	 * @return
	 */
	public String getColsJson() {
		if(StringUtil.isEmpty(this.colsJson)){
			return "[]";
		}
		return this.colsJson;
	}

	public void setListHtml(String listHtml) {
		this.listHtml = listHtml;
	}

	/**
	 * 返回 列表显示模板
	 * 
	 * @return
	 */
	public String getListHtml() {
		return this.listHtml;
	}

	public String getDataRightJson() {
		return dataRightJson;
	}

	public void setDataRightJson(String dataRightJson) {
		this.dataRightJson = dataRightJson;
	}

	public String getSearchJson() {
		if(StringUtil.isEmpty(this.searchJson)){
			return "[]";
		}
		return searchJson;
	}

	public void setSearchJson(String searchJson) {
		this.searchJson = searchJson;
	}

	public void setBpmSolId(String bpmSolId) {
		this.bpmSolId = bpmSolId;
	}

	/**
	 * 返回 绑定流程方案
	 * 
	 * @return
	 */
	public String getBpmSolId() {
		return this.bpmSolId;
	}

	public void setFormAlias(String formAlias) {
		this.formAlias = formAlias;
	}

	/**
	 * 返回 绑定表单方案
	 * 
	 * @return
	 */
	public String getFormAlias() {
		return this.formAlias;
	}

	public void setTopBtnsJson(String topBtnsJson) {
		this.topBtnsJson = topBtnsJson;
	}

	public Map<String, SysBoTopButton> getTopButtonMap() {
		return topButtonMap;
	}

	public void setTopButtonMap(Map<String, SysBoTopButton> topButtonMap) {
		this.topButtonMap = topButtonMap;
	}

	/**
	 * 返回 头部按钮配置
	 * 
	 * @return
	 */
	public String getTopBtnsJson() {
		return this.topBtnsJson;
	}

	public void setIsDialog(String isDialog) {
		this.isDialog = isDialog;
	}

	/**
	 * 返回 是否对话框
	 * 
	 * @return
	 */
	public String getIsDialog() {
		return this.isDialog;
	}

	public void setIsPage(String isPage) {
		this.isPage = isPage;
	}

	public String getFieldsJson() {
		if(StringUtil.isEmpty(this.fieldsJson)){
			return "[]";
		}
		return fieldsJson;
	}

	public void setFieldsJson(String fieldsJson) {
		this.fieldsJson = fieldsJson;
	}

	/**
	 * 返回 是否分页
	 * 
	 * @return
	 */
	public String getIsPage() {
		return this.isPage;
	}

	public void setIsExport(String isExport) {
		this.isExport = isExport;
	}
	

	public String getIsShare() {
		return isShare;
	}

	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}

	/**
	 * 返回 是否允许导出
	 * 
	 * @return
	 */
	public String getIsExport() {
		return this.isExport;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getMobileHtml() {
		return mobileHtml;
	}

	public void setMobileHtml(String mobileHtml) {
		this.mobileHtml = mobileHtml;
	}

	public String getDataStyle() {
		return dataStyle;
	}

	public void setDataStyle(String dataStyle) {
		this.dataStyle = dataStyle;
	}

	public String getRowEdit() {
		return rowEdit;
	}

	public void setRowEdit(String rowEdit) {
		this.rowEdit = rowEdit;
	}

	public Integer getStartFroCol() {
		return startFroCol;
	}

	public void setStartFroCol(Integer startFroCol) {
		this.startFroCol = startFroCol;
	}

	public Integer getEndFroCol() {
		return endFroCol;
	}

	public void setEndFroCol(Integer endFroCol) {
		this.endFroCol = endFroCol;
	}

	public String getShowSummaryRow() {
		return showSummaryRow;
	}

	public void setShowSummaryRow(String showSummaryRow) {
		this.showSummaryRow = showSummaryRow;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysBoList)) {
			return false;
		}
		SysBoList rhs = (SysBoList) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.solId, rhs.solId).append(this.name, rhs.name)
				.append(this.key, rhs.key).append(this.descp, rhs.descp)
				.append(this.isLeftTree, rhs.isLeftTree)
				.append(this.leftNav, rhs.leftNav)
				.append(this.leftTreeJson, rhs.leftTreeJson)
				.append(this.sql, rhs.sql)
				.append(this.dbAs, rhs.dbAs)
				.append(this.colsJson, rhs.colsJson)
				.append(this.listHtml, rhs.listHtml)
				.append(this.searchJson, rhs.searchJson)
				.append(this.bpmSolId, rhs.bpmSolId)
				.append(this.formAlias, rhs.formAlias)
				.append(this.topBtnsJson, rhs.topBtnsJson)
				.append(this.isDialog, rhs.isDialog)
				.append(this.isPage, rhs.isPage)
				.append(this.isExport, rhs.isExport).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.solId).append(this.name).append(this.key)
				.append(this.descp).append(this.isLeftTree)
				.append(this.leftNav).append(this.leftTreeJson)
				.append(this.sql).append(this.dbAs).append(this.colsJson)
				.append(this.listHtml).append(this.searchJson)
				.append(this.bpmSolId).append(this.formAlias)
				.append(this.isDialog)
				.append(this.isPage).append(this.isExport).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("solId", this.solId).append("name", this.name)
				.append("key", this.key).append("descp", this.descp)
				.append("isLeftTree", this.isLeftTree)
				.append("leftNav", this.leftNav)
				.append("leftTreeJson", this.leftTreeJson)
				.append("sql", this.sql).append("dbAs", this.dbAs)
				.append("colsJson", this.colsJson)
				.append("listHtml", this.listHtml)
				.append("searchJson", this.searchJson)
				.append("bpmSolId", this.bpmSolId)
				.append("formAlias", this.formAlias)
				.append("topBtnsJson", this.topBtnsJson)
				.append("isDialog", this.isDialog)
				.append("isPage", this.isPage)
				.append("isExport", this.isExport).toString();
	}

	public Map<String, GridHeader> getColumnHeaderMap() {
		return columnHeaderMap;
	}

	public void setColumnHeaderMap(Map<String, GridHeader> columnHeaderMap) {
		this.columnHeaderMap = columnHeaderMap;
	}

}
