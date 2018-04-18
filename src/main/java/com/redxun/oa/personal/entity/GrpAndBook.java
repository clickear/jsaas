package com.redxun.oa.personal.entity;

/**
 * <pre>
 * 描述：联系人节点实体类定义
 * 构建联系人分组和分组下的联系人的树
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
public class GrpAndBook {
	/*Id*/
	protected String gabId;
	/*名字*/
	protected String name;
	/*父Id*/
	protected String parentId;
	/**
	 * 
	 * @return Id
	 */
	public String getGabId() {
		return this.gabId;
	}

	/**
	 * 设置Id
	 * @param gabId
	 */
	public void setGabId(String gabId) {
		this.gabId = gabId;
	}

	/**
	 * 名字
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名字
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 父Id
	 * @return
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 设置父Id
	 * @param parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}
