package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：SysSearchItem实体类定义
 * 搜索条件项
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@XmlRootElement
@Table(name = "SYS_SEARCH_ITEM")
@TableDefine(title = "搜索条件项")
public class SysSearchItem extends BaseTenantEntity {

    /**
     * 用于获取树节点ID
     */
    @Transient
    private String id;

    @Id
    @Column(name = "ITEM_ID_")
    protected String itemId;
    /* 条件类型 */
    @FieldDefine(title = "条件类型", group = "基本信息")
    @Column(name = "NODE_TYPE_")
    @Size(max = 20)
    @NotEmpty
    protected String nodeType;

    @FieldDefine(title = "条件类型标签", group = "基本信息")
    @Column(name = "NODE_TYPE_LABEL_")
    @Size(max = 20)
    @NotEmpty
    protected String nodeTypeLabel;

    /* 父ID */
    @FieldDefine(title = "父ID", group = "基本信息")
    @Column(name = "PARENT_ID_")
    @Size(max = 64)
    @NotEmpty
    protected String parentId;
    /* 路径 */
    @FieldDefine(title = "路径", group = "基本信息")
    @Column(name = "PATH_")
    @Size(max = 256)
    protected String path;
    /* 字段类型 */
    @FieldDefine(title = "字段类型", group = "基本信息")
    @Column(name = "FIELD_TYPE_")
    @Size(max = 20)
    protected String fieldType;
    /* 序号 */
    @Column(name = "SN_")
    protected Integer sn;
    /* 条件标签 */
    @FieldDefine(title = "条件标签", group = "基本信息")
    @Column(name = "LABEL_")
    @Size(max = 100)
    @NotEmpty
    protected String label;
    /**/
    @FieldDefine(title = "字段比较符", group = "基本信息")
    @Column(name = "FIELD_OP_")
    @Size(max = 20)
    protected String fieldOp;

    @FieldDefine(title = "字段比较符标签", group = "基本信息")
    @Column(name = "FIELD_OP_LABEL_")
    @Size(max = 20)
    protected String fieldOpLabel;

    /* 字段标签 */
    @FieldDefine(title = "字段标签", group = "基本信息")
    @Column(name = "FIELD_TITLE_")
    @Size(max = 50)
    protected String fieldTitle;
    /* 字段名称 */
    @FieldDefine(title = "字段名称", group = "基本信息")
    @Column(name = "FIELD_NAME_")
    @Size(max = 64)
    protected String fieldName;
    /* 字段值 */
    @FieldDefine(title = "字段值", group = "基本信息")
    @Column(name = "FIELD_VAL_")
    @Size(max = 80)
    protected String fieldVal;
    /* 控件类型 */
    @FieldDefine(title = "控件类型", group = "基本信息")
    @Column(name = "CTL_TYPE_")
    @Size(max = 50)
    protected String ctlType;
    /* 值格式 */
    @FieldDefine(title = "值格式", group = "基本信息")
    @Column(name = "FORMAT_")
    @Size(max = 50)
    protected String format;
    /* 预处理 */
    @FieldDefine(title = "预处理", group = "基本信息")
    @Column(name = "PRE_HANDLE_")
    @Size(max = 65535)
    protected String preHandle;

    @FieldDefine(title = "搜索字段", group = "基本信息")
    @ManyToOne
    @JoinColumn(name = "FIELD_ID_")
    protected com.redxun.sys.core.entity.SysField sysField;
    @FieldDefine(title = "所属搜索", group = "基本信息")
    @ManyToOne
    @JoinColumn(name = "SEARCH_ID_")
    protected com.redxun.sys.core.entity.SysSearch sysSearch;

	//用于构建树的子节点
//	@Transient
//	protected List<SysSearchItem> children;
    /**
     * Default Empty Constructor for class SysSearchItem
     */
    public SysSearchItem() {
        super();
    }

    /**
     * Default Key Fields Constructor for class SysSearchItem
     */
    public SysSearchItem(String in_itemId) {
        this.setItemId(in_itemId);
    }

    @JsonBackReference
    public com.redxun.sys.core.entity.SysField getSysField() {
        return sysField;
    }

    public void setSysField(com.redxun.sys.core.entity.SysField in_sysField) {
        this.sysField = in_sysField;
    }

    @JsonBackReference
    public com.redxun.sys.core.entity.SysSearch getSysSearch() {
        return sysSearch;
    }

    public void setSysSearch(com.redxun.sys.core.entity.SysSearch in_sysSearch) {
        this.sysSearch = in_sysSearch;
    }

    public String getNodeTypeLabel() {
        return nodeTypeLabel;
    }

    public void setNodeTypeLabel(String nodeTypeLabel) {
        this.nodeTypeLabel = nodeTypeLabel;
    }

    public String getFieldOpLabel() {
        return fieldOpLabel;
    }

    public void setFieldOpLabel(String fieldOpLabel) {
        this.fieldOpLabel = fieldOpLabel;
    }

    /**
     * * @return String
     */
    public String getItemId() {
        return this.itemId;
    }

    /**
     * 设置
     */
    public void setItemId(String aValue) {
        this.itemId = aValue;
    }

    /**
     * * @return String
     */
    public String getSearchId() {
        return this.getSysSearch() == null ? null : this.getSysSearch()
                .getSearchId();
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    /**
     * 设置
     */
    public void setSearchId(String aValue) {
        if (aValue == null) {
            sysSearch = null;
        } else if (sysSearch == null) {
            sysSearch = new com.redxun.sys.core.entity.SysSearch(aValue);
			// sysSearch.setVersion(new Integer(0));//set a version to cheat
            // hibernate only
        } else {
            //
            sysSearch.setSearchId(aValue);
        }
    }

    /**
     * 条件类型 * @return String
     */
    public String getNodeType() {
        return this.nodeType;
    }

    /**
     * 设置 条件类型
     */
    public void setNodeType(String aValue) {
        this.nodeType = aValue;
    }

    /**
     * 父ID * @return String
     */
    public String getParentId() {
        return this.parentId;
    }

    /**
     * 设置 父ID
     */
    public void setParentId(String aValue) {
        this.parentId = aValue;
    }

    /**
     * 路径 * @return String
     */
    public String getPath() {
        return this.path;
    }

    /**
     * 设置 路径
     */
    public void setPath(String aValue) {
        this.path = aValue;
    }

    /**
     * 字段类型 * @return String
     */
    public String getFieldType() {
        return this.fieldType;
    }

    /**
     * 设置 字段类型
     */
    public void setFieldType(String aValue) {
        this.fieldType = aValue;
    }

    /**
     * 条件标签 * @return String
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * 设置 条件标签
     */
    public void setLabel(String aValue) {
        this.label = aValue;
    }

    /**
     * * @return String
     */
    public String getFieldOp() {
        return this.fieldOp;
    }

    /**
     * 设置
     */
    public void setFieldOp(String aValue) {
        this.fieldOp = aValue;
    }

    /**
     * 字段标签 * @return String
     */
    public String getFieldTitle() {
        return this.fieldTitle;
    }

    /**
     * 设置 字段标签
     */
    public void setFieldTitle(String aValue) {
        this.fieldTitle = aValue;
    }

    /**
     * 字段ID * @return String
     */
    public String getFieldId() {
        return this.getSysField() == null ? null : this.getSysField()
                .getFieldId();
    }

    /**
     * 设置 字段ID
     */
    public void setFieldId(String aValue) {
        if (aValue == null) {
            sysField = null;
        } else if (sysField == null) {
            sysField = new com.redxun.sys.core.entity.SysField(aValue);
			// sysField.setVersion(new Integer(0));//set a version to cheat
            // hibernate only
        } else {
            //
            sysField.setFieldId(aValue);
        }
    }

    /**
     * 字段名称 * @return String
     */
    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * 设置 字段名称
     */
    public void setFieldName(String aValue) {
        this.fieldName = aValue;
    }

    /**
     * 字段值 * @return String
     */
    public String getFieldVal() {
        return this.fieldVal;
    }

    /**
     * 设置 字段值
     */
    public void setFieldVal(String aValue) {
        this.fieldVal = aValue;
    }

    /**
     * 控件类型 * @return String
     */
    public String getCtlType() {
        return this.ctlType;
    }

    /**
     * 设置 控件类型
     */
    public void setCtlType(String aValue) {
        this.ctlType = aValue;
    }

    /**
     * 值格式 * @return String
     */
    public String getFormat() {
        return this.format;
    }

    /**
     * 设置 值格式
     */
    public void setFormat(String aValue) {
        this.format = aValue;
    }

    /**
     * 预处理 * @return String
     */
    public String getPreHandle() {
        return this.preHandle;
    }

    /**
     * 设置 预处理
     */
    public void setPreHandle(String aValue) {
        this.preHandle = aValue;
    }

    @Override
    public String getIdentifyLabel() {
        return this.itemId;
    }

    @Override
    public Serializable getPkId() {
        return this.itemId;
    }

    @Override
    public void setPkId(Serializable pkId) {
        this.itemId = (String) pkId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//	public List<SysSearchItem> getChildren() {
//		return children;
//	}
//
//	public void setChildren(List<SysSearchItem> children) {
//		this.children = children;
//	}
    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof SysSearchItem)) {
            return false;
        }
        SysSearchItem rhs = (SysSearchItem) object;
        return new EqualsBuilder().append(this.itemId, rhs.itemId)
                .append(this.nodeType, rhs.nodeType)
                .append(this.parentId, rhs.parentId)
                .append(this.path, rhs.path)
                .append(this.fieldType, rhs.fieldType)
                .append(this.label, rhs.label)
                .append(this.fieldOp, rhs.fieldOp)
                .append(this.fieldTitle, rhs.fieldTitle)
                .append(this.fieldName, rhs.fieldName)
                .append(this.fieldVal, rhs.fieldVal)
                .append(this.ctlType, rhs.ctlType)
                .append(this.format, rhs.format)
                .append(this.preHandle, rhs.preHandle)
                .append(this.tenantId, rhs.tenantId)
                .append(this.createBy, rhs.createBy)
                .append(this.createTime, rhs.createTime)
                .append(this.updateBy, rhs.updateBy)
                .append(this.updateTime, rhs.updateTime).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-82280557, -700257973).append(this.itemId)
                .append(this.nodeType).append(this.parentId).append(this.path)
                .append(this.fieldType).append(this.label).append(this.fieldOp)
                .append(this.fieldTitle).append(this.fieldName)
                .append(this.fieldVal).append(this.ctlType).append(this.format)
                .append(this.preHandle).append(this.tenantId)
                .append(this.createBy).append(this.createTime)
                .append(this.updateBy).append(this.updateTime).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("itemId", this.itemId)
                .append("nodeType", this.nodeType)
                .append("parentId", this.parentId).append("path", this.path)
                .append("fieldType", this.fieldType)
                .append("label", this.label).append("fieldOp", this.fieldOp)
                .append("fieldTitle", this.fieldTitle)
                .append("fieldName", this.fieldName)
                .append("fieldVal", this.fieldVal)
                .append("ctlType", this.ctlType).append("format", this.format)
                .append("preHandle", this.preHandle)
                .append("tenantId", this.tenantId)
                .append("createBy", this.createBy)
                .append("createTime", this.createTime)
                .append("updateBy", this.updateBy)
                .append("updateTime", this.updateTime).toString();
    }

}
