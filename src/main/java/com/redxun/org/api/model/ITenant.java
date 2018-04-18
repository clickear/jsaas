package com.redxun.org.api.model;
/**
 * 
 * <pre> 
 * 描述：承租者,在一个集团公司内使用时，承租者则为子公司
 * 构建组：ent-base-org-api
 * 作者：csx
 * 邮箱:keith@mitom.cn
 * 日期:2014年5月2日-下午2:43:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
public interface ITenant {
	/**
	 * 所有租用机构共用的租用ID，在业务数据中的实体表中
	 */
	public final static String PUBLIC_TENANT_ID = "0";

	/**
	 * 管理租用ID=1
	 */
	public final static String ADMIN_TENANT_ID = "1";

	
	/**
	 * 承租者Id
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getTenantId();
	/**
	 * 承租者名称
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getTenantName();
	/**
	 * 租户简称
	 * @return
	 */
	public String getShortName();
	/**
	 * 机构域名，唯一
	 * @return
	 */
	public String getDomain();
	/**
	 * 获得租用机构的状态
	 * @return
	 */
	public String getStatus();
	
	/**
	 * 组织机构类型
	 */
	public String getInstType();
}
