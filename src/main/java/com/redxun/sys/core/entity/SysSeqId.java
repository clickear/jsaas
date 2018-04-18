package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：SysSeqId实体类定义
 * 系统流水号
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "SYS_SEQ_ID")
@TableDefine(title = "系统流水号")
public class SysSeqId extends BaseTenantEntity {
	/**
	 * 流程实例流程号主键=1，其记录的数据由系统初始化 TODO,如何解决集群下的ID不重复
	 */
	public final static String BPM_INST_SEQ_ID="1";
	
	/**
	 * 按天生成=DAY
	 */
	public final static String GEN_TYPE_DAY="DAY";
	/**
	 * 按周生成=WEEK
	 */
	public final static String GEN_TYPE_WEEK="WEEK";
	/**
	 * 按月生成=MONTH
	 */
	public final static String GEN_TYPE_MONTH="MONTH";
	/**
	 * 按年生成=YEAR
	 */
	public final static String GEN_TYPE_YEAR="YEAR";
	/**
	 * 自动生成=AUTO
	 */
	public final static String GEN_TYPE_AUTO="AUTO";
	
	/**
	 * 流程实例的产生的序号
	 */
	public final static String ALIAS_BPM_INST_BILL_NO="BPM_INST_BILL_NO";
	
	
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "SEQ_ID_")
	protected String seqId;
	/* 名称 */
	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	@Size(max = 80)
	@NotEmpty
	protected String name;
	/* 别名 */
	@FieldDefine(title = "别名")
	@Column(name = "ALIAS_")
	@Size(max = 50)
	protected String alias;
	/* 当前日期 */
	@FieldDefine(title = "当前日期")
	@Column(name = "CUR_DATE_")
	protected java.util.Date curDate;
	/* 规则 */
	@FieldDefine(title = "规则")
	@Column(name = "RULE_")
	@Size(max = 100)
	protected String rule;
	
	/* 规则配置 */
	@FieldDefine(title = "规则配置")
	@Column(name = "RULE_CONF_")
	@Size(max = 512)
	protected String ruleConf;
	
	/* 初始值 */
	@FieldDefine(title = "初始值")
	@Column(name = "INIT_VAL_")
	protected Integer initVal=1;
	/*
	 * 生成方式
	 */
	@FieldDefine(title = "生成方式")
	@Column(name = "GEN_TYPE_")
	@Size(max = 20)
	protected String genType;
	/* 流水号长度 */
	@FieldDefine(title = "流水号长度")
	@Column(name = "LEN_")
	protected Integer len;
	/* 当前值 */
	@FieldDefine(title = "当前值")
	@Column(name = "CUR_VAL")
	protected Integer curVal=0;
	/* 步长 */
	@FieldDefine(title = "步长")
	@Column(name = "STEP_")
	protected Short step;
	/* 备注 */
	@FieldDefine(title = "备注")
	@Column(name = "MEMO_")
	@Size(max = 512)
	protected String memo;
	
	/* 是否缺省 */
	@FieldDefine(title = "系统默认")
	@Column(name = "IS_DEFAULT_")
	@Size(max = 20)
	protected String isDefault;

	@Transient
	protected Integer newVal=0;
	
	
	
	
	/**
	 * Default Empty Constructor for class SysSeqId
	 */
	public SysSeqId() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysSeqId
	 */
	public SysSeqId(String in_seqId) {
		this.setSeqId(in_seqId);
	}

	/**
	 * 流水号ID * @return String
	 */
	public String getSeqId() {
		return this.seqId;
	}

	/**
	 * 设置 流水号ID
	 */
	public void setSeqId(String aValue) {
		this.seqId = aValue;
	}

	/**
	 * 名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 别名 * @return String
	 */
	public String getAlias() {
		return this.alias;
	}

	/**
	 * 设置 别名
	 */
	public void setAlias(String aValue) {
		this.alias = aValue;
	}

	/**
	 * 当前日期 * @return java.util.Date
	 */
	public java.util.Date getCurDate() {
		return this.curDate;
	}

	/**
	 * 设置 当前日期
	 */
	public void setCurDate(java.util.Date aValue) {
		this.curDate = aValue;
	}

	/**
	 * 规则 * @return String
	 */
	public String getRule() {
		return this.rule;
	}

	/**
	 * 设置 规则
	 */
	public void setRule(String aValue) {
		this.rule = aValue;
	}

	/**
	 * 初始值 * @return Integer
	 */
	public Integer getInitVal() {
		return this.initVal;
	}

	/**
	 * 设置 初始值
	 */
	public void setInitVal(Integer aValue) {
		this.initVal = aValue;
	}

	/**
	 * 生成方式
	 * 
	 * @return Short
	 */
	public String getGenType() {
		return this.genType;
	}

	/**
	 * 设置 生成方式
	 */
	public void setGenType(String aValue) {
		this.genType = aValue;
	}

	/**
	 * 流水号长度 * @return Integer
	 */
	public Integer getLen() {
		return this.len;
	}

	/**
	 * 设置 流水号长度
	 */
	public void setLen(Integer aValue) {
		this.len = aValue;
	}

	/**
	 * 当前值 * @return Integer
	 */
	public Integer getCurVal() {
		return this.curVal;
	}

	/**
	 * 设置 当前值
	 */
	public void setCurVal(Integer aValue) {
		this.curVal = aValue;
	}

	/**
	 * 步长 * @return Short
	 */
	public Short getStep() {
		return this.step;
	}

	/**
	 * 设置 步长
	 */
	public void setStep(Short aValue) {
		this.step = aValue;
	}

	/**
	 * 备注 * @return String
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * 设置 备注
	 */
	public void setMemo(String aValue) {
		this.memo = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.name;
	}

	@Override
	public Serializable getPkId() {
		return this.seqId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.seqId = (String) pkId;
	}

	public String getRuleConf() {
		return ruleConf;
	}

	public void setRuleConf(String ruleConf) {
		this.ruleConf = ruleConf;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getNewVal() {
		return newVal;
	}

	public void setNewVal(Integer newVal) {
		this.newVal = newVal;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysSeqId)) {
			return false;
		}
		SysSeqId rhs = (SysSeqId) object;
		return new EqualsBuilder().append(this.seqId, rhs.seqId)
				.append(this.name, rhs.name).append(this.alias, rhs.alias)
				.append(this.curDate, rhs.curDate).append(this.rule, rhs.rule)
				.append(this.initVal, rhs.initVal)
				.append(this.genType, rhs.genType).append(this.len, rhs.len)
				.append(this.curVal, rhs.curVal).append(this.step, rhs.step)
				.append(this.memo, rhs.memo)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.seqId)
				.append(this.name).append(this.alias).append(this.curDate)
				.append(this.rule).append(this.initVal).append(this.genType)
				.append(this.len).append(this.curVal).append(this.step)
				.append(this.memo).append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("seqId", this.seqId)
				.append("name", this.name).append("alias", this.alias)
				.append("curDate", this.curDate).append("rule", this.rule)
				.append("initVal", this.initVal)
				.append("genType", this.genType).append("len", this.len)
				.append("curVal", this.curVal).append("step", this.step)
				.append("memo", this.memo).append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
