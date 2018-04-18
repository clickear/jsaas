package com.redxun.sys.db.entity;

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
import com.redxun.core.util.StringUtil;

/**
 * <pre>
 * 
 * 描述：自定义查询实体类定义
 * 作者：cjx
 * 邮箱: ray@redxun.com
 * 日期:2017-02-21 15:32:09
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_CUSTOM_QUERY")
@TableDefine(title = "自定义查询")
public class SysSqlCustomQuery extends BaseTenantEntity {

	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "名字")
	@Column(name = "NAME_")
	protected String name;
	
	@FieldDefine(title = "标识或别名")
	@Column(name = "KEY_")
	protected String key;
	
	@FieldDefine(title = "对象名称，如果是表就是表名，视图则视图名")
	@Column(name = "TABLE_NAME_")
	protected String tableName;
	
	@FieldDefine(title = "是否分页")
	@Column(name = "IS_PAGE_")
	protected Integer isPage;
	
	@FieldDefine(title = "分页大小")
	@Column(name = "PAGE_SIZE_")
	protected String pageSize;
	
	@FieldDefine(title = "条件字段的json")
	@Column(name = "WHERE_FIELD_")
	protected String whereField;
	
	@FieldDefine(title = "返回字段json")
	@Column(name = "RESULT_FIELD_")
	protected String resultField;
	
	@FieldDefine(title = "排序字段")
	@Column(name = "ORDER_FIELD_")
	protected String orderField;
	
	@FieldDefine(title = "数据源的别名")
	@Column(name = "DS_ALIAS_")
	protected String dsAlias;
	
	@FieldDefine(title = "是否数据库表0:视图,1:数据库表")
	@Column(name = "TABLE_")
	protected Integer table;
	
	@FieldDefine(title = "自定义SQL")
	@Column(name = "SQL_DIY_")
	protected String sqlDiy;
	
	@FieldDefine(title = "SQL")
	@Column(name = "SQL_")
	protected String sql="";
	
	
	@FieldDefine(title = "SQL构建类型")
	@Column(name = "SQL_BUILD_TYPE_")
	protected String sqlBuildType;

	public SysSqlCustomQuery() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysSqlCustomQuery(String in_id) {
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

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 名字
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
	 * 返回 标识或别名
	 * 
	 * @return
	 */
	public String getKey() {
		return this.key;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 返回 对象名称，如果是表就是表名，视图则视图名
	 * 
	 * @return
	 */
	public String getTableName() {
		return this.tableName;
	}
	public void setIsPage(Integer page) {
		this.isPage = page;
	}

	/**
	 * 返回 是否分页
	 * 
	 * @return
	 */
	public Integer getIsPage() {
		return this.isPage;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 返回 分页大小
	 * 
	 * @return
	 */
	public String getPageSize() {
		return this.pageSize;
	}

	public void setWhereField(String whereField) {
		this.whereField = whereField;
	}

	/**
	 * 返回 条件字段的json
	 * 
	 * @return
	 */
	public String getWhereField() {
		return this.whereField;
	}
	public void setResultField(String resultField) {
		this.resultField = resultField;
	}

	/**
	 * 返回 返回字段json
	 * 
	 * @return
	 */
	public String getResultField() {
		return this.resultField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	/**
	 * 返回 排序字段
	 * 
	 * @return
	 */
	public String getOrderField() {
		return this.orderField;
	}
	public void setDsAlias(String dsAlias) {
		this.dsAlias = dsAlias;
	}

	/**
	 * 返回 数据源的别名
	 * 
	 * @return
	 */
	public String getDsAlias() {
		return this.dsAlias;
	}
	public void setTable(Integer table) {
		this.table = table;
	}

	/**
	 * 返回 是否数据库表0:视图,1:数据库表
	 * 
	 * @return
	 */
	public Integer getTable() {
		return this.table;
	}
	public void setSqlDiy(String sqlDiy) {
		this.sqlDiy = sqlDiy;
	}

	/**
	 * 返回 自定义SQL
	 * 
	 * @return
	 */
	public String getSqlDiy() {
		if(StringUtil.isEmpty(this.sqlDiy)){
			return "";
		}
		return this.sqlDiy.trim();
	}
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setSqlBuildType(String sqlBuildType) {
		this.sqlBuildType = sqlBuildType;
	}

	/**
	 * 返回 SQL构建类型
	 * 
	 * @return
	 */
	public String getSqlBuildType() {
		return this.sqlBuildType;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysSqlCustomQuery)) {
			return false;
		}
		SysSqlCustomQuery rhs = (SysSqlCustomQuery) object;
		return new EqualsBuilder().append(this.id, rhs.id).append(this.name, rhs.name).append(this.key, rhs.key).append(this.tableName, rhs.tableName).append(this.isPage, rhs.isPage)
				.append(this.pageSize, rhs.pageSize).append(this.whereField, rhs.whereField).append(this.resultField, rhs.resultField)
				.append(this.orderField, rhs.orderField).append(this.dsAlias, rhs.dsAlias).append(this.table, rhs.table).append(this.sqlDiy, rhs.sqlDiy)
				.append(this.sqlBuildType, rhs.sqlBuildType).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.name).append(this.key).append(this.tableName).append(this.isPage).append(this.pageSize)
				.append(this.whereField).append(this.resultField).append(this.orderField).append(this.dsAlias).append(this.table).append(this.sqlDiy)
				.append(this.sqlBuildType).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("key", this.key).append("tableName", this.tableName).append("page", this.isPage)
				.append("pageSize", this.pageSize).append("whereField", this.whereField).append("resultField", this.resultField)
				.append("orderField", this.orderField).append("dsAlias", this.dsAlias).append("table", this.table).append("sqlDiy", this.sqlDiy)
				.append("sqlBuildType", this.sqlBuildType).toString();
	}

}
