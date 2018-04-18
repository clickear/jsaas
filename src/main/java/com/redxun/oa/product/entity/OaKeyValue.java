package com.redxun.oa.product.entity;

import java.io.Serializable;

import com.redxun.core.entity.BaseTenantEntity;

public class OaKeyValue extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 产品定义ID
	protected String defId;
	//产品类型ID
	protected String keyId;
	// 产品类型名
	protected String name;
	// 产品属性ID
	protected String number;
	//产品属性名
	protected String numbername;
	//自定义产品类型
	protected String customKeyName;
	//自定义产品属性
	protected String customValueName;
	
	
	//构造OaKeyValue
	
	public OaKeyValue(){
		
	}
	
	//带参数构造
	public OaKeyValue(String keyId,String defId,String name,String numer,String numbername){
		this.keyId=keyId;
		this.defId=defId;
		this.name=name;
		this.number=numer;
		this.numbername=numbername;
	}
	
	

	// 产品定义ID
	public String getDefId() {
		return defId;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}
	
	
	//产品类型ID
	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	// 产品类型名
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 产品属性ID
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	
	//产品属性名
	public String getNumbername() {
		return numbername;
	}

	public void setNumbername(String numbername) {
		this.numbername = numbername;
	}

	
	//自定义产品类型
	public String getCustomKeyName() {
		return customKeyName;
	}

	public void setCustomKeyName(String customKeyName) {
		this.customKeyName = customKeyName;
	}

	//自定义产品属性
	public String getCustomValueName() {
		return customValueName;
	}

	public void setCustomValueName(String customValueName) {
		this.customValueName = customValueName;
	}

	@Override
	public String getIdentifyLabel() {
		return null;
	}

	@Override
	public Serializable getPkId() {
		return null;
	}

	@Override
	public void setPkId(Serializable pkId) {

	}

}
