package com.redxun.org.api.context;

import com.redxun.org.api.model.ITenant;
import com.redxun.org.api.model.IUser;

/**
 * 当前登录的上下文件环境
 * @author csx
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public interface CurrentContext {

    /**
     * 获得当前用户
     *
     * @return IUser
     * @exception
     * @since 1.0.0
     */
    public IUser getCurrentUser();

    /**
     * 获得当前用户所属的租用ID
     *
     * @return String
     * @exception
     * @since 1.0.0
     */
    public String getCurrentTenantId();
    
    /**
     * 获得当前用户所属的租户对象
     * @return
     */
    public ITenant getCurrentTenant();

}
