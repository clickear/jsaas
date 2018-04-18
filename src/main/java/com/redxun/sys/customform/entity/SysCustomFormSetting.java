



package com.redxun.sys.customform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 *  
 * 描述：自定义表单配置设定实体类定义
 * 作者：mansan
 * 邮箱: ray@redxun.com
 * 日期:2017-05-16 10:25:38
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_CUSTOMFORM_SETTING")
@TableDefine(title = "自定义表单配置设定")
public class SysCustomFormSetting extends BaseTenantEntity {

	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;
	@FieldDefine(title = "分类Id")
	@Column(name = "TREE_ID_")
	protected String treeId; 
	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "别名")
	@Column(name = "ALIAS_")
	protected String alias; 
	@FieldDefine(title = "前置JS脚本")
	@Column(name = "PRE_JS_SCRIPT_")
	protected String preJsScript; 
	@FieldDefine(title = "后置JS脚本")
	@Column(name = "AFTER_JS_SCRIPT_")
	protected String afterJsScript; 
	@FieldDefine(title = "前置JAVA脚本")
	@Column(name = "PRE_JAVA_SCRIPT_")
	protected String preJavaScript; 
	@FieldDefine(title = "后置JAVA脚本")
	@Column(name = "AFTER_JAVA_SCRIPT_")
	protected String afterJavaScript; 
	@FieldDefine(title = "方案名称")
	@Column(name = "SOL_NAME_")
	protected String solName; 
	@FieldDefine(title = "方案ID")
	@Column(name = "SOL_ID_")
	protected String solId; 
	@FieldDefine(title = "表单名")
	@Column(name = "FORM_NAME_")
	protected String formName; 
	
	@FieldDefine(title = "表单别名")
	@Column(name = "FORM_ALIAS_")
	protected String formAlias;

	@FieldDefine(title = "业务模型ID")
	@Column(name = "BODEF_ID_")
	protected String bodefId; 
	@FieldDefine(title = "业务模型")
	@Column(name = "BODEF_NAME_")
	protected String bodefName; 
	@FieldDefine(title = "树形表单(0,普通表单,1,树形表单)")
	@Column(name = "IS_TREE_")
	protected Integer isTree; 
	
	@FieldDefine(title = "树形加载方式0,一次性加载,1,懒加载")
	@Column(name = "LOAD_MODE_")
	protected Integer loadMode=0; 
	
	@FieldDefine(title = "加载级别")
	@Column(name = "EXPAND_LEVEL_")
	protected Integer expandLevel=1; 
	
	@FieldDefine(title = "树显示字段")
	@Column(name = "DISPLAY_FIELDS_")
	protected String displayFields=""; 
	
	@FieldDefine(title = "按钮定义")
	@Column(name = "BUTTON_DEF_")
	protected String buttonDef="";
	
	@FieldDefine(title = "数据处理器")
	@Column(name = "DATA_HANDLER_")
	protected String dataHandler="";
	
	
	@FieldDefine(title = "子表权限")
	@Column(name = "TABLE_RIGHT_JSON_")
	protected String tableRightJson="";
	
	@FieldDefine(title = "手机表单")
	@Column(name = "MOBILE_FORM_ALIAS_")
	protected String mobileFormAlias="";
	
	@FieldDefine(title = "手机表单")
	@Column(name = "MOBILE_FORM_NAME_")
	protected String mobileFormName="";
	
	
	
	
	
	
	
	public SysCustomFormSetting() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysCustomFormSetting(String in_id) {
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

	
	public void setId(String aValue) {
		this.id = aValue;
	}
	
	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getFormAlias() {
		return formAlias;
	}

	public void setFormAlias(String formAlias) {
		this.formAlias = formAlias;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	/**
	 * 返回 别名
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}
	public void setPreJsScript(String preJsScript) {
		this.preJsScript = preJsScript;
	}
	
	/**
	 * 返回 前置JS脚本
	 * @return
	 */
	public String getPreJsScript() {
		return this.preJsScript;
	}
	public void setAfterJsScript(String afterJsScript) {
		this.afterJsScript = afterJsScript;
	}
	
	/**
	 * 返回 后置JS脚本
	 * @return
	 */
	public String getAfterJsScript() {
		return this.afterJsScript;
	}
	public void setPreJavaScript(String preJavaScript) {
		this.preJavaScript = preJavaScript;
	}
	
	/**
	 * 返回 前置JAVA脚本
	 * @return
	 */
	public String getPreJavaScript() {
		return this.preJavaScript;
	}
	public void setAfterJavaScript(String afterJavaScript) {
		this.afterJavaScript = afterJavaScript;
	}
	
	/**
	 * 返回 后置JAVA脚本
	 * @return
	 */
	public String getAfterJavaScript() {
		return this.afterJavaScript;
	}
	public void setSolName(String solName) {
		this.solName = solName;
	}
	
	/**
	 * 返回 方案名称
	 * @return
	 */
	public String getSolName() {
		return this.solName;
	}
	public void setSolId(String solId) {
		this.solId = solId;
	}
	
	/**
	 * 返回 方案ID
	 * @return
	 */
	public String getSolId() {
		return this.solId;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	/**
	 * 返回 表单名
	 * @return
	 */
	public String getFormName() {
		return this.formName;
	}
	
	public void setBodefId(String bodefId) {
		this.bodefId = bodefId;
	}
	
	/**
	 * 返回 业务模型ID
	 * @return
	 */
	public String getBodefId() {
		return this.bodefId;
	}
	public void setIsTree(Integer isTree) {
		this.isTree = isTree;
	}
	
	/**
	 * 返回 树形表单(0,普通表单,1,树形表单)
	 * @return
	 */
	public Integer getIsTree() {
		return this.isTree;
	}
	
	
	
		

	public String getBodefName() {
		return bodefName;
	}

	public void setBodefName(String bodefName) {
		this.bodefName = bodefName;
	}

	public Integer getLoadMode() {
		return loadMode;
	}

	public void setLoadMode(Integer loadMode) {
		this.loadMode = loadMode;
	}

	public Integer getExpandLevel() {
		return expandLevel;
	}

	public void setExpandLevel(Integer expandLevel) {
		this.expandLevel = expandLevel;
	}

	public String getDisplayFields() {
		return displayFields;
	}

	public void setDisplayFields(String displayFields) {
		this.displayFields = displayFields;
	}

	public String getButtonDef() {
		return buttonDef;
	}

	public void setButtonDef(String buttonDef) {
		this.buttonDef = buttonDef;
	}

	public String getDataHandler() {
		return dataHandler;
	}

	public void setDataHandler(String dataHandler) {
		this.dataHandler = dataHandler;
	}

	public String getTableRightJson() {
		return tableRightJson;
	}

	public void setTableRightJson(String tableRightJson) {
		this.tableRightJson = tableRightJson;
	}

	public String getMobileFormAlias() {
		return mobileFormAlias;
	}

	public String getMobileFormName() {
		return mobileFormName;
	}

	public void setMobileFormAlias(String mobileFormAlias) {
		this.mobileFormAlias = mobileFormAlias;
	}

	public void setMobileFormName(String mobileFormName) {
		this.mobileFormName = mobileFormName;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysCustomFormSetting)) {
			return false;
		}
		SysCustomFormSetting rhs = (SysCustomFormSetting) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.name, rhs.name) 
		.append(this.alias, rhs.alias) 
		.append(this.solName, rhs.solName) 
		.append(this.solId, rhs.solId) 
		.append(this.formName, rhs.formName) 
		.append(this.bodefId, rhs.bodefId) 
		.append(this.isTree, rhs.isTree) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.alias) 
		.append(this.solName) 
		.append(this.solId) 
		.append(this.formName) 
		.append(this.bodefId) 
		.append(this.isTree) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("name", this.name) 
				.append("alias", this.alias) 
				.append("solName", this.solName) 
				.append("solId", this.solId) 
				.append("formName", this.formName) 
				.append("bodefId", this.bodefId) 
				.append("isTree", this.isTree).toString();
	}

}



