package com.redxun.sys.core.enums;

/**
 * 
 * <pre> 
 * 描述：Ext了字段列表的中的数据类型
 * 
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014年9月16日-下午6:29:39
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public enum FieldDataType {
	AUTO("auto"),
	STRING("string"),
	INT("int"),
	NUMBER("number"),
	BOOLEAN("boolean"),
	DATE("date");
	
	private String fieldType;
	
	private FieldDataType(String fieldType){
		this.fieldType=fieldType;
	}
	
	@Override
	public String toString() {
		return fieldType;
	}
}
