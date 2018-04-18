package com.redxun.sys.core.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.constants.MStatus;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.core.dao.SysAccountDao;
import com.redxun.sys.core.entity.SysAccount;

/**
 * <pre>
 * 描述：SysAccount业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysAccountManager extends BaseManager<SysAccount> {

    @Resource
    private SysAccountDao sysAccountDao;

    @SuppressWarnings("rawtypes")
    @Override
    protected IDao getDao() {
        return sysAccountDao;
    }

    /**
     * 获得该租用下的用户账号信息
     *
     * @param name
     * @param tenantId
     * @return SysAccount
     * @since 1.0.0
     */
    public SysAccount getByNameTenantId(String name, String tenantId) {
    	List<SysAccount> accs= sysAccountDao.getByNameTenantId(name, tenantId);
    	if(accs.size()>0){
    		return accs.get(0);
    	}
    	return null;
    }

    public SysAccount getByName(String name) {
        return sysAccountDao.getByName(name);
    }
    
    /**
     *  根据账号名与域名查找账号
     * @param name
     * @param domain
     * @return
     */
    public SysAccount getByNameDomain(String name,String domain){
    	return sysAccountDao.getByNameDomain(name, domain);
    }

    /**
     * 检查账号是否已经存在
     *
     * @param name
     * @param tenantId
     * @return boolean
     * @since 1.0.0
     */
    public boolean isAccoutExist(String name, String tenantId) {
        List<SysAccount> accounts = sysAccountDao.getByNameTenantId(name, tenantId);
        return accounts.size()>0 ? true : false;
    }
    
    /**
     * 取得用户账号信息
     * @param userId
     * @return
     */
    public List<SysAccount> getByUserId(String userId){
    	return sysAccountDao.getByUserId(userId);
    }
    /**
     * 删除用户关联的账号
     * @param userId
     */
    public void delByUserId(String userId){
    	sysAccountDao.delByUserId(userId);
    }
    
    /**
     * 初始化账号
     * @param account
     * @param pwd
     * @param userId 绑定的用户id
     * @param fullname
     * @param instId
     * @return
     */
    public SysAccount initAccount(String account,String domain,String pwd,String userId,String fullname,String instId){
    	SysAccount sysAccount=new SysAccount();
    	sysAccount.setName(account);
    	sysAccount.setPwd(pwd);
    	sysAccount.setFullname(fullname);
    	sysAccount.setUserId(userId);
    	sysAccount.setTenantId(instId);
    	sysAccount.setStatus(MStatus.ENABLED.toString());
    	sysAccount.setDomain(domain);
    	sysAccountDao.create(sysAccount);
    	return sysAccount;
    }
}
