package com.redxun.sys.core.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.redxun.core.entity.BaseEntity;
import com.redxun.core.util.EncryptUtil;
import com.redxun.saweb.constants.EncryptType;
import com.redxun.saweb.constants.HandlerResult;
import com.redxun.saweb.handler.EntityPersistenceHandler;
import com.redxun.sys.core.entity.SysAccount;
/**
 * 系统账号的预处理类
 * @author csx
 *@Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Service
public class SysAccountHandler implements EntityPersistenceHandler{
	
	private Log logger=LogFactory.getLog(SysAccountHandler.class);
	
	@Override
	public HandlerResult preExecute(BaseEntity entity,boolean isCreated) {
		SysAccount account=(SysAccount)entity;
		if(isCreated){
			if(EncryptType.MD5.toString().equals(account.getEncType())){
				String encPwd=EncryptUtil.encryptMd5(account.getEncType());
				account.setPwd(encPwd);
			}else if(EncryptType.SHA256.toString().equals(account.getEncType())){
				String encPwd=EncryptUtil.encryptSha256(account.getEncType());
				account.setPwd(encPwd);
			}
		}
		return HandlerResult.SCCUESS;
	}

	@Override
	public HandlerResult afterExecute(BaseEntity entity,boolean isCreated) {
		return HandlerResult.SCCUESS;
	}

}
