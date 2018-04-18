package com.redxun.sys.org.entity;

import com.redxun.core.entity.BaseTreeNode;
/**
 * 
 * <pre> 
 * 描述：用户组树节点
 * 构建组：ent-base-web
 * 作者：csx
 * 日期:2014年5月21日-下午10:59:47
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 * </pre>
 */
public class GroupTreeNode extends BaseTreeNode{
	
	//所属用户组等级分类
	private String rankTypeId;
	//所属业务维度Key
	private String dimKey;
	//业务状态
	private String status;

	
	public String getRankTypeId() {
		return rankTypeId;
	}
	public void setRankTypeId(String rankTypeId) {
		this.rankTypeId = rankTypeId;
	}
	public String getDimKey() {
		return dimKey;
	}
	public void setDimKey(String dimKey) {
		this.dimKey = dimKey;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	
}
