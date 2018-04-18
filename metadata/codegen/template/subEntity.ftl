<#import "function.ftl" as func>
<#assign package=model.variables.package>
<#assign class=model.variables.class>
<#assign classVar=model.variables.classVar>
<#assign comment=model.tabComment>
<#assign subtables=model.subTableList>
<#assign pk=func.getPk(model) >
<#assign pkModel=model.pkModel >
<#assign pkVar=func.convertUnderLine(pk) >
<#assign pkType=func.getPkType(model)>
<#assign fkType=func.getFkType(model)>
<#assign system=vars.system>
<#assign domain=vars.domain>
<#assign tableName=model.tableName>
<#assign colList=model.columnList>
<#assign commonList=model.commonList>
<#assign foreignField="">
<#if (model.sub)>
	<#assign foreignField=model.foreignKey?lower_case>
</#if>




package ${domain}.${system}.${package}.entity;

import java.util.Date;
import ${domain}.core.entity.BaseTenantEntity;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import ${domain}.core.constants.MBoolean;
import ${domain}.core.annotion.table.FieldDefine;
import ${domain}.core.annotion.table.TableDefine;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import ${domain}.core.annotion.table.FieldDefine;
import ${domain}.core.xstream.convert.DateConverter;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * <pre>
 *  
 * 描述：${comment}实体类定义
 * 作者：${vars.developer}
 * 邮箱: ${vars.email}
 * 日期:${date?string("yyyy-MM-dd HH:mm:ss")}
 * 版权：${vars.company}
 * </pre>
 */
<#if (model.sub)>
public class ${class} implements Serializable {
<#else>
public class ${class} extends BaseTenantEntity {
</#if>
	
	@FieldDefine(title = "${pkModel.comment}")
	@Id
	@Column(name = "${pkModel.columnName}")
	protected ${pkModel.colType} ${func.convertUnderLine(pkModel.columnName)};

	<#list commonList as col>
	<#assign colName=func.convertUnderLine(col.columnName)>
	<#if (model.sub)>
	<#if colName!="createTime" && colName!="updateTime" >
	@FieldDefine(title = "${col.comment?trim}")
	@Column(name = "${col.columnName}")
	protected ${col.colType} ${colName};
	<#else>
	@FieldDefine(title = "${col.comment?trim}")
	@Column(name = "${col.columnName}")
	@XStreamConverter(DateConverter.class)
	protected Date ${colName};
	</#if>	
	<#else>
	<#if func.isExcludeField( colName) >
	@FieldDefine(title = "${col.comment?trim}")
	@Column(name = "${col.columnName}")
	protected ${col.colType} ${colName};
	</#if>
	</#if>
	</#list>
	
	<#if subtables?exists && subtables?size!=0>
	<#list subtables as subTable>
	<#assign subClass=subTable.variables.class>
	<#assign subClassVar=subTable.variables.classVar>
	protected java.util.List<${subClass}> ${subClassVar}s = new java.util.ArrayList<${subClass}>();
	</#list>
	</#if>
	
	
	public ${class}() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	 
	 
	<#if !(model.sub)>
	public ${class}(${pkModel.colType} in_id) {
		this.setPkId(in_id);
	}
	@Override
	public String getIdentifyLabel() {
		return this.${func.convertUnderLine(pkModel.columnName)};
	}

	@Override
	public Serializable getPkId() {
		return this.${func.convertUnderLine(pkModel.columnName)};
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.${func.convertUnderLine(pkModel.columnName)} = (String) pkId;
	}
	</#if>
	
	public ${pkModel.colType} get${func.convertUnderLine(pkModel.columnName)?cap_first}() {
		return this.${func.convertUnderLine(pkModel.columnName)};
	}

	
	public void set${func.convertUnderLine(pkModel.columnName)?cap_first}(${pkModel.colType} aValue) {
		this.${func.convertUnderLine(pkModel.columnName)} = aValue;
	}
	
	<#list commonList as col>
	<#assign colName=func.convertUnderLine(col.columnName)>
	<#if func.isExcludeField( colName) >
	public void set${colName?cap_first}(${col.colType} ${colName}) {
		this.${colName} = ${colName};
	}
	
	/**
	 * 返回 ${col.comment}
	 * @return
	 */
	public ${col.colType} get${colName?cap_first}() {
		return this.${colName};
	}
	</#if>
	</#list>
	
	<#if subtables?exists && subtables?size!=0>
	<#list subtables as subTable>
	<#assign subClass=subTable.variables.class>
	<#assign subClassVar=subTable.variables.classVar>
	public java.util.List<${subClass}> get${subClass}s() {
		return ${subClassVar}s;
	}

	public void set${subClass}s(java.util.List<${subClass}> in_${subClassVar}) {
		this.${subClassVar}s = in_${subClassVar};
	}
	</#list>
	</#if>
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ${class})) {
			return false;
		}
		${class} rhs = (${class}) object;
		return new EqualsBuilder()
		<#list model.columnList as col>
		<#assign colName=func.convertUnderLine(col.columnName)>
		<#if func.isExcludeField( colName) >
		<#if foreignField!=col.columnName?lower_case>
		.append(this.${colName}, rhs.${colName}) 
		</#if>
		</#if>
		</#list>
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		<#list model.columnList as col>
		<#if foreignField!=col.columnName?lower_case>
		<#assign colName=func.convertUnderLine(col.columnName)>
		<#if func.isExcludeField( colName) >
		.append(this.${colName}) 
		</#if>
		</#if>
		</#list>
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		<#list model.columnList as col>
		<#if foreignField!=col.columnName?lower_case>
		<#assign colName=func.convertUnderLine(col.columnName)>
		<#if func.isExcludeField( colName) >
		.append("${colName}", this.${colName}) 
		</#if>
		</#if>
		</#list>.toString();
	}

}



