package com.redxun.oa.doc.entity;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.json.JsonDateSerializer;
/**创建人：陈茂昌
 * 创建时间：2015.12.09
 * 描述：由folder和doc构造出来的一个实体，用来展示在docfolderindex2.jsp页面的treegrid
 * @author Administrator
 *
 */
public class DocAndFolder {
	/*文件和文件夹合并后的ID，docAndFolder每个实体唯一标识符*/
	protected String dafId;
	/* 名字*/
	protected String name;
	/* 父节点的ID，在这里与dafId对应*/
	protected String parent;
	/* 实体的类型，在后台会构造成“文件”，“文件夹”*/
	protected String type;
	/* 创建人ID */
	protected String createBy;
	/* 创建人名字，由创建人ID构造*/
	protected String createName;
	/*创建时间*/
	protected Date createTime;
	/* 排序等级，决定在同级树种的顺序*/
	protected int level;
	/* @return创建时间*/
	public String getCreateName() {
		return createName;
	}
	/*设置创建姓名*/
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	/* @return排序等级*/
	public int getLevel() {
		return level;
	}
	/* 设置排序等级*/
	public void setLevel(int level) {
		this.level = level;
	}

	/* @return创建人ID*/
	public String getCreateBy() {
		return createBy;
	}

	
	/* 设置创建人ID*/
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	/* @return创建时间*/
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	/* 设置创建时间*/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/* @return实体类型*/
	public String getType() {
		return type;
	}
	/*设置实体类型*/
	public void setType(String type) {
		this.type = type;
	}
	/* @return创建时间 */
	public String getDafId() {
		return dafId;
	}
	/*@return创建时间*/
	public void setDafId(String dafId) {
		this.dafId = dafId;
	}
	/* @return实体名*/
	public String getName() {
		return name;
	}
	/*设置实体名*/
	public void setName(String name) {
		this.name = name;
	}
	/* @return父节点ID*/
	public String getParent() {
		return parent;
	}
	/*设置父节点*/
	public void setParent(String parent) {
		this.parent = parent;
	}
	/*自动生成的toString方法，测试用*/
	@Override
	public String toString() {
		return "DocAndFolder [dafId=" + dafId + ", name=" + name + ", parent="
				+ parent + ", type=" + type + ", createBy=" + createBy
				+ ", createName=" + createName + ", createTime=" + createTime
				+ ", level=" + level + "]";
	}

}
