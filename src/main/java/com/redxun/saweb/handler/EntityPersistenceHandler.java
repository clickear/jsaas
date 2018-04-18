package com.redxun.saweb.handler;

import com.redxun.core.entity.BaseEntity;
import com.redxun.saweb.constants.HandlerResult;

/**
 * 
 * <pre> 
 * 描述：调用通用实体保存及更新时，触发该接口的调用
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chshxuan@163.com
 * 日期:2014年12月26日-上午11:58:14
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public interface EntityPersistenceHandler {
	/**
	 * 
	 * 前置处理数据
	 * @param entity
	 * @param isCreate 是否在进行实体创建，为false表示为更新实体时调用
	 * @return 
	 * HandlerResult
	 * @exception 
	 * @since  1.0.0
	 */
	public HandlerResult preExecute(BaseEntity entity,boolean isCreated);
	/**
	 * 后置处理器
	 * @param entity
	 * @param isCreate 是否在进行实体创建，为false表示为更新实体时调用
	 * @return 
	 * HandlerResult
	 * @exception 
	 * @since  1.0.0
	 */
	public HandlerResult afterExecute(BaseEntity entity,boolean isCreated);
}
