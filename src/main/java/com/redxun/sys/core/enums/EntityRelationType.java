package com.redxun.sys.core.enums;
/**
 * 
 * <pre> 
 * 描述：实体关系类型
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014年9月25日-上午11:37:32
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public enum EntityRelationType {
	/**
	 * 一对一
	 */
	OneToOne,
	/**
	 * 一对多
	 */
	OneToMany,
	/**
	 * 多对一
	 */
	ManyToOne,
	/**
	 * 多对多
	 */
	ManyToMany
}
