package com.redxun.sys.ldap.manager;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.naming.NamingException;

import org.springframework.stereotype.Service;

import com.redxun.bpm.activiti.util.ProcessHandleHelper;
import com.redxun.bpm.core.entity.ProcessMessage;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.constants.MStatus;
import com.redxun.core.dao.IDao;
import com.redxun.core.db.core.return_code.ReturnCode;
import com.redxun.core.db.core.return_code.ReturnCodeThreadLocal;
import com.redxun.core.db.ldap.HttpReturnCodeEnum;
import com.redxun.core.db.ldap.LdapConfig;
import com.redxun.core.db.ldap.LdapConfigEnum;
import com.redxun.core.db.ldap.LdapGroup;
import com.redxun.core.db.ldap.LdapStatusEnum;
import com.redxun.core.db.ldap.LdapUser;
import com.redxun.core.db.ldap.LdapUtil;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.seq.IdGenerator;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.constants.EncryptType;
import com.redxun.sys.core.dao.SysAccountDao;
import com.redxun.sys.core.entity.SysAccount;
import com.redxun.sys.ldap.dao.SysLdapCnDao;
import com.redxun.sys.ldap.dao.SysLdapConfigDao;
import com.redxun.sys.ldap.dao.SysLdapLogDao;
import com.redxun.sys.ldap.dao.SysLdapOuDao;
import com.redxun.sys.ldap.entity.SysLdapCn;
import com.redxun.sys.ldap.entity.SysLdapConfig;
import com.redxun.sys.ldap.entity.SysLdapLog;
import com.redxun.sys.ldap.entity.SysLdapOu;
import com.redxun.sys.org.dao.OsDimensionDao;
import com.redxun.sys.org.dao.OsGroupDao;
import com.redxun.sys.org.dao.OsRelInstDao;
import com.redxun.sys.org.dao.OsRelTypeDao;
import com.redxun.sys.org.dao.OsUserDao;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.entity.OsUser;
/**
 * <pre>
 * 描述：SysLdapConfig业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 使用范围：授权给敏捷集团使用
 * </pre>
 */
@Service
public class SysLdapConfigManager extends BaseManager<SysLdapConfig> {
	@Resource
	private SysLdapLogDao sysLdapLogDao;
	@Resource
	protected IdGenerator idGenerator;
	@Resource
	private SysLdapOuDao sysLdapOuDao;
	@Resource
	private SysLdapCnDao sysLdapCnDao;
	@Resource
	private OsRelInstDao osRelInstDao;
	@Resource
	private OsRelTypeDao osRelTypeDao;
	@Resource
	private SysAccountDao sysAccountDao;
	@Resource
	private OsGroupDao osGroupDao;
	@Resource
	private OsUserDao osUserDao;
	@Resource
	private OsDimensionDao osDimensionDao;
	@Resource
	private SysLdapConfigDao sysLdapConfigDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysLdapConfigDao;
	}
	/**
	 * 
	 * LDAP连接测试
	 * 
	 * @Description:
	 * @Title: doConn
	 * @param @param id
	 * @param @param sysLdapLog
	 * @param @return
	 * @param @throws Exception 参数说明
	 * @return LdapConnBean 返回类型
	 * @throws
	 */
	public boolean doConn(String id, SysLdapLog sysLdapLog) throws Exception {
		LdapUtil ldapUtil = new LdapUtil();
		String logName = sysLdapLog.getLogName();

		try {
			LdapConfig config = this.findLdapConfig(id);
			ldapUtil.findDirContext(config);
			return ReturnCodeThreadLocal.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getStackTrace(), e);
			sysLdapLog.setContent(e.getMessage());
			StringBuffer sb = new StringBuffer(logName + "错误：");
			sb.append("\n");
			sb.append("原因为:").append(e.getMessage());
			ProcessMessage processMessage = ProcessHandleHelper
					.getProcessMessage();
			if (processMessage != null) {
				processMessage.getErrorMsges().add(sb.toString());
			}
			throw e;
		} finally {
			sysLdapLogDao.create(sysLdapLog);
		}

	}
	/**
	 * 同步数据
	 * 
	 * @Description:
	 * @Title: doSyncData
	 * @param @param tenantId
	 * @param @param configId
	 * @param @param sysLdapLog
	 * @param @return
	 * @param @throws Exception 参数说明
	 * @return LdapConnBean 返回类型
	 * @throws
	 */
	public boolean doSyncData(String tenantId, String configId,
			SysLdapLog sysLdapLog) throws Exception {
		ReturnCode returnCode = new ReturnCode();
		String logName = sysLdapLog.getLogName();

		try {
			if (StringUtil.isEmpty(configId)) {

				returnCode.setContent("配置id不能为空");
				returnCode.setSuccess(false);
				returnCode.setCode(HttpReturnCodeEnum.code404.name());
				returnCode.setMsgCn(HttpReturnCodeEnum.code404.getMsgCn());
				ReturnCodeThreadLocal.setReturnCode(returnCode);
				sysLdapLog
						.setContent(logName + "失败," + returnCode.getContent());

				return false;
			}
			LdapGroup ldapGroup = this.doSyncData2DB(tenantId, configId);
			if (ReturnCodeThreadLocal.isSuccess()) {
				sysLdapLog.setContent(logName + "成功");
				return this.doSyncData2org(tenantId, configId);
				// return true;
			} else {

				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getStackTrace(), e);
			sysLdapLog.setContent(e.getMessage());
			StringBuffer sb = new StringBuffer(logName + "错误：");
			sb.append("\n");
			sb.append("原因为:").append(e.getMessage());
			ProcessMessage processMessage = ProcessHandleHelper
					.getProcessMessage();
			if (processMessage != null) {
				processMessage.getErrorMsges().add(sb.toString());
			}
			throw e;
		} finally {
			sysLdapLogDao.create(sysLdapLog);
		}

	}
	/**
	 * 同步数据到中间表
	 * 
	 * @Description:
	 * @Title: doSyncData2DB
	 * @param @param tenantId
	 * @param @param id
	 * @param @return
	 * @param @throws NamingException 参数说明
	 * @return Boolean 返回类型
	 * @throws
	 */
	public LdapGroup doSyncData2DB(String tenantId, String configId)
			throws NamingException {
		ReturnCode returnCode = new ReturnCode();
		// 非删除状态的，所有都禁用
		this.sysLdapOuDao.updateStatus(tenantId,
				LdapStatusEnum.disable.toString());
		this.sysLdapCnDao.updateStatus(tenantId,
				LdapStatusEnum.disable.toString());
		LdapUtil ldapUtil = new LdapUtil();
		LdapConfig config = this.findLdapConfig(configId);
		String account = config.getAccount();
		if (StringUtil.isEmpty(account)) {

			returnCode.setContent("认证失败,账号不能为空");
			returnCode.setSuccess(false);
			returnCode.setCode(HttpReturnCodeEnum.code404.name());
			returnCode.setMsgCn(HttpReturnCodeEnum.code404.getMsgCn());
			ReturnCodeThreadLocal.setReturnCode(returnCode);
			return null;
		}
		String password = config.getPassword();
		if (StringUtil.isEmpty(password)) {

			returnCode.setContent("认证失败,密码不能为空");
			returnCode.setSuccess(false);
			returnCode.setCode(HttpReturnCodeEnum.code404.name());
			returnCode.setMsgCn(HttpReturnCodeEnum.code404.getMsgCn());
			ReturnCodeThreadLocal.setReturnCode(returnCode);
			return null;
		}
		// 得到AD数据
		LdapGroup ldapGroupRoot = ldapUtil.syncData(config);

		SysLdapOu sysLdapOu = new SysLdapOu();
		sysLdapOu.setTenantId(tenantId);
		sysLdapOu.setSysLdapOuId("0");
		sysLdapOu.setPath("0.");
		this.syncData2DB(tenantId, ldapGroupRoot, sysLdapOu);

		// 更新配置状态
		SysLdapConfig sysLdapConfig = this.sysLdapConfigDao.get(configId);
		sysLdapConfig.setStatus(LdapConfigEnum.syncLdap.name());
		sysLdapConfig.setStatusCn(LdapConfigEnum.syncLdap.toString());
		this.sysLdapConfigDao.update(sysLdapConfig);

		return ldapGroupRoot;
	}
	/**
	 * 找出 LdapConfig
	 * 
	 * @Description:
	 * @Title: findLdapConfig
	 * @param @param id
	 * @param @return 参数说明
	 * @return LdapConfig 返回类型
	 * @throws
	 */
	public LdapConfig findLdapConfig(String id) {
		SysLdapConfig sysLdapConfig = this.sysLdapConfigDao.get(id);
		LdapConfig config = new LdapConfig();
		config.setFilter(sysLdapConfig.getUserFilter());
		config.setDnRoot(sysLdapConfig.getDnBase());
		config.setAccount(sysLdapConfig.getAccount());
		config.setPassword(sysLdapConfig.getPassword());
		config.setDnDatum(sysLdapConfig.getDnDatum());
		config.setAttUserNo(sysLdapConfig.getAttUserNo());
		config.setAttUserName(sysLdapConfig.getAttUserName());
		config.setAttUserAcc(sysLdapConfig.getAttUserAcc());
		config.setAttUserPwd(sysLdapConfig.getAttUserPwd());
		config.setAttUserTel(sysLdapConfig.getAttUserTel());
		config.setAttUserMail(sysLdapConfig.getAttUserMail());
		config.setAttUserDescription("description");
		config.setAttDeptName(sysLdapConfig.getAttDeptName());
		config.setAttDeptDescription("description");
		String url = sysLdapConfig.getUrl();
		String[] urlArray = url.split(",");
		List urlList = Arrays.asList(urlArray);
		config.setUrlList(urlList);
		return config;
	}
	/**
	 * 同步数据到中间表,编历数据
	 * 
	 * @Description:
	 * @Title: syncData2DB
	 * @param @param tenantId
	 * @param @param ldapGroupRoot
	 * @param @param parentGroup
	 * @param @throws NamingException 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void syncData2DB(String tenantId, LdapGroup ldapGroupRoot,
			SysLdapOu parentGroup) throws NamingException {
		// 是否增加部门
		boolean isAddDept = false;
		// 是否更新部门
		boolean isUpdateDept = false;
		SysLdapOu sysLdapOu = sysLdapOuDao.getUniqueByUSNCreated(tenantId,
				ldapGroupRoot.getUsnCreated());
		if (sysLdapOu == null) {
			isAddDept = true;
			sysLdapOu = new SysLdapOu();
		} else {
			// 数据库存在的数据
			long usnCreatedDB = Long.parseLong(sysLdapOu.getUsnCreated());
			long usnChangedDB = Long.parseLong(sysLdapOu.getUsnChanged());
			long usnCreated = Long.parseLong(ldapGroupRoot.getUsnCreated());
			long usnChanged = Long.parseLong(ldapGroupRoot.getUsnChanged());
			if (usnCreated != usnCreatedDB) {
				isAddDept = true;
				this.sysLdapOuDao.updateStatus(tenantId,
						LdapStatusEnum.del.toString(),
						sysLdapOu.getUsnCreated());
			} else {
				if (usnChanged != usnChangedDB) {
					isUpdateDept = true;
				}
			}
		}
		if (isAddDept) {
			sysLdapOu = new SysLdapOu();
		}
		// 保存部门
		if (StringUtil.isNotEmpty(ldapGroupRoot.getName())) {
			sysLdapOu.setName(ldapGroupRoot.getName());
		} else {
			sysLdapOu.setName("用户组名称为空");
		}
		sysLdapOu.setWhenCreated(ldapGroupRoot.getWhenCreated());
		sysLdapOu.setWhenChanged(ldapGroupRoot.getWhenChanged());
		sysLdapOu.setUsnCreated(ldapGroupRoot.getUsnCreated());
		sysLdapOu.setUsnChanged(ldapGroupRoot.getUsnChanged());
		sysLdapOu.setDn(ldapGroupRoot.getDistinguishedName());
		if (isAddDept) {
			// 保存部门
			String groupId = idGenerator.getSID();
			sysLdapOu.setTenantId(tenantId);
			sysLdapOu.setSysLdapOuId(groupId);
			sysLdapOu.setParentId(parentGroup.getSysLdapOuId());
			sysLdapOu.setPath(parentGroup.getPath() + groupId + ".");
			sysLdapOu.setStatus(LdapStatusEnum.add.toString());
			this.sysLdapOuDao.create(sysLdapOu);
		} else {
			if (isUpdateDept) {
				sysLdapOu.setStatus(LdapStatusEnum.update.toString());
				this.sysLdapOuDao.update(sysLdapOu);
			} else {
				sysLdapOu.setStatus(LdapStatusEnum.enable.toString());
				this.sysLdapOuDao.update(sysLdapOu);
			}
		}
		// 找出所有的子部门
		List<LdapGroup> groupList = ldapGroupRoot.getGroupList();
		for (LdapGroup ldapGroup : groupList) {
			this.syncData2DB(tenantId, ldapGroup, sysLdapOu);
		}
		// 找出所有用户
		List<LdapUser> ldapUserList = ldapGroupRoot.getUserList();
		for (LdapUser ldapUser : ldapUserList) {
			boolean isAddUser = false;
			boolean isUpdateUser = false;
			SysLdapCn sysLdapCn = this.sysLdapCnDao.getUniqueByUSNCreated(
					tenantId, ldapUser.getUsnCreated());
			if (sysLdapCn == null) {
				isAddUser = true;
				sysLdapCn = new SysLdapCn();
			} else {
				// 数据库存在的数据
				long usnCreatedDB = Long.parseLong(sysLdapCn.getUsnCreated());
				long usnChangedDB = Long.parseLong(sysLdapCn.getUsnChanged());
				long usnCreated = Long.parseLong(ldapUser.getUsnCreated());
				long usnChanged = Long.parseLong(ldapUser.getUsnChanged());
				if (usnCreated != usnCreatedDB) {
					isAddUser = true;
					this.sysLdapCnDao.updateStatus(tenantId,
							LdapStatusEnum.del.toString(),
							sysLdapCn.getUsnCreated());
				} else {
					if (usnChanged != usnChangedDB) {
						isUpdateUser = true;
					}
				}
			}
			if (isAddUser) {
				sysLdapCn = new SysLdapCn();
			}
			// 保存用户CN
			sysLdapCn.setUserCode(ldapUser.getAccount());
			if (StringUtil.isNotEmpty(ldapUser.getName())) {
				sysLdapCn.setName(ldapUser.getName());
			} else {
				sysLdapCn.setName("用户名称为空");
			}
			if (StringUtil.isNotEmpty(ldapUser.getAccount())) {
				sysLdapCn.setUserAccount(ldapUser.getAccount());
			} else {
				sysLdapCn.setUserAccount("用户账户为空");
			}
			sysLdapCn.setMail(ldapUser.getMail());
			sysLdapCn.setTel(ldapUser.getTel());
			sysLdapCn.setWhenCreated(ldapUser.getWhenCreated());
			sysLdapCn.setWhenChanged(ldapUser.getWhenChanged());
			sysLdapCn.setUsnCreated(ldapUser.getUsnCreated());
			sysLdapCn.setUsnChanged(ldapUser.getUsnChanged());
			sysLdapCn.setDn(ldapUser.getDistinguishedName());
			if (isAddUser) {
				// 保存用户
				String userId = idGenerator.getSID();
				// 设置部门id
				sysLdapCn.setSysLdapOuId(sysLdapOu.getSysLdapOuId());
				sysLdapCn.setSysLdapUserId(userId);
				// sysLdapCn.setUserId(userId);
				sysLdapCn.setTenantId(tenantId);
				if (StringUtil.isNotEmpty(ldapUser.getCode())) {
					sysLdapCn.setUserCode(ldapUser.getCode());
				} else {
					sysLdapCn.setUserCode("no" + userId);
				}
				sysLdapCn.setStatus(LdapStatusEnum.add.toString());
				this.sysLdapCnDao.create(sysLdapCn);
			} else {
				if (isUpdateUser) {
					sysLdapCn.setStatus(LdapStatusEnum.update.toString());
					this.sysLdapCnDao.update(sysLdapCn);
				} else {
					sysLdapCn.setStatus(LdapStatusEnum.enable.toString());
					this.sysLdapCnDao.update(sysLdapCn);
				}
			}
		}
	}
	/**
	 * 同步数据到组织架构
	 * 
	 * @Description
	 * @Title doSyncData2org
	 * @param @param tenantId
	 * @param @param configId
	 * @param @return
	 * @param @throws NamingException 参数说明
	 * @return Boolean 返回类型
	 * @throws
	 */
	public Boolean doSyncData2org(String tenantId, String configId)
			throws NamingException {
		SysLdapConfig sysLdapConfig = this.sysLdapConfigDao.get(configId);
		// 是否第一次同步到中间表
		if (StringUtil.isEmpty(sysLdapConfig.getStatus())) {
			// 更新配置状态
			sysLdapConfig.setStatus(LdapConfigEnum.syncLdap.name());
			sysLdapConfig.setStatusCn(LdapConfigEnum.syncLdap.toString());
			this.sysLdapConfigDao.update(sysLdapConfig);
		}
		// 是否第一次同步
		if (LdapConfigEnum.syncOrg.name().equals(sysLdapConfig.getStatus())) {
			// 同步到组织架构
			this.syncData2org(tenantId);
		} else {
			// 第一次同步
			// 同步到组织架构
			this.doSyncData2orgFirst(tenantId);
			// 更新配置状态
			sysLdapConfig.setStatus(LdapConfigEnum.syncOrg.name());
			sysLdapConfig.setStatusCn(LdapConfigEnum.syncOrg.toString());
			this.sysLdapConfigDao.update(sysLdapConfig);
		}
		// 禁用状态的，所有都删除
		this.sysLdapOuDao.delLogic(tenantId);
		this.sysLdapCnDao.delLogic(tenantId);
		// 更新所有上一级id和path
		List<SysLdapOu> sysLdapOuList = this.sysLdapOuDao.getByParentId(
				tenantId, "0");
		this.updatePathAll(tenantId, null, sysLdapOuList);
		return true;
	}
	/**
	 * 同步数据到组织架构,第一次同步
	 * 
	 * @Description:
	 * @Title: doSyncData2orgFirst
	 * @param @param tenantId
	 * @param @throws NamingException 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void doSyncData2orgFirst(String tenantId) throws NamingException {
		// 同步到组织架构
		OsDimension osDimension = osDimensionDao.getByDimKeyTenantId(
				OsDimension.DIM_ADMIN, tenantId);
		OsRelType osRelType = osRelTypeDao
				.getByRelTypeKey(OsRelType.REL_CAT_GROUP_USER_BELONG);
		// 新增部门，更新部门，禁用部门
		// 新增部门
		List<SysLdapOu> sysLdapOuAddList = this.sysLdapOuDao
				.getByStatusNotDel(tenantId);
		for (SysLdapOu sysLdapOu : sysLdapOuAddList) {
			String groupId = sysLdapOu.getGroupId();
			if (StringUtil.isNotEmpty(groupId)) {
				// 更新部门
				OsGroup osGroup = this.osGroupDao.get(groupId);
				if (StringUtil.isNotEmpty(sysLdapOu.getName())) {
					osGroup.setName(sysLdapOu.getName());
				} else {
					osGroup.setName("用户组名称为空");
				}
				osGroup.setStatus(MStatus.ENABLED.toString());
				osGroup.setKey(sysLdapOu.getUsnCreated());
				this.osGroupDao.update(osGroup);
			} else {
				// 保存部门
				if (osGroupDao.isLDAPExist(sysLdapOu.getUsnCreated())) {
					OsGroup group = osGroupDao.getByKey(sysLdapOu
							.getUsnCreated());
					sysLdapOu.setGroupId(group.getGroupId());
					this.sysLdapOuDao.update(sysLdapOu);

				} else {
					OsGroup osGroup = new OsGroup();
					groupId = this.idGenerator.getSID();
					osGroup.setGroupId(groupId);
					osGroup.setForm(LdapConfig.LDAP);
					osGroup.setTenantId(tenantId);
					osGroup.setOsDimension(osDimension);
					osGroup.setSn(100);
					if (StringUtil.isNotEmpty(sysLdapOu.getName())) {
						osGroup.setName(sysLdapOu.getName());
					} else {
						osGroup.setName("用户组名称为空");
					}
					osGroup.setStatus(MStatus.ENABLED.toString());
					osGroup.setKey(sysLdapOu.getUsnCreated());
					this.osGroupDao.create(osGroup);
					// 更新组id
					sysLdapOu.setGroupId(groupId);
					this.sysLdapOuDao.update(sysLdapOu);

				}

			}
		}
		// 新增用户，更新用户，禁用用户
		// 新增用户
		List<SysLdapCn> sysLdapCnAddList = this.sysLdapCnDao
				.getByStatusNotDel(tenantId);
		if (sysLdapCnAddList != null && sysLdapCnAddList.size() > 0) {
			for (SysLdapCn sysLdapCn : sysLdapCnAddList) {
				String userId = sysLdapCn.getUserId();
				if (StringUtil.isNotEmpty(userId)) {
					// 更新用户
					OsUser osUser = this.osUserDao.get(userId);
					if (StringUtil.isNotEmpty(sysLdapCn.getName())) {
						osUser.setFullname(sysLdapCn.getName());
					} else {
						osUser.setFullname("用户名称为空");
					}
					osUser.setStatus(OsUser.STATUS_IN_JOB);
					osUser.setEmail(sysLdapCn.getMail());
					osUser.setMobile(sysLdapCn.getTel());
					osUser.setUserNo(sysLdapCn.getUsnCreated());
					this.osUserDao.update(osUser);
				} else {
					if (osUserDao.isLDAPExist(sysLdapCn.getUsnCreated())) {
						OsUser user = osUserDao.getByUserNo(sysLdapCn
								.getUsnCreated());
						user.setStatus(OsUser.STATUS_IN_JOB);
						osUserDao.update(user);
						SysAccount sysAccount = sysAccountDao.getByUserId(
								user.getUserId()).get(0);
						sysAccount.setStatus("ENABLE");
						sysAccountDao.update(sysAccount);
						sysLdapCn.setUserId(user.getUserId());
						this.sysLdapCnDao.update(sysLdapCn);
					} else {
						// 保存用户
						OsUser osUser = new OsUser();
						userId = this.idGenerator.getSID();
						osUser.setSex("Male");
						osUser.setUserId(userId);
						osUser.setFrom(LdapConfig.LDAP);
						osUser.setTenantId(tenantId);
						if (StringUtil.isNotEmpty(sysLdapCn.getName())) {
							osUser.setFullname(sysLdapCn.getName());
						} else {
							osUser.setFullname("用户名称为空");
						}
						osUser.setStatus(OsUser.STATUS_IN_JOB);
						osUser.setEmail(sysLdapCn.getMail());
						osUser.setMobile(sysLdapCn.getTel());
						osUser.setUserNo(sysLdapCn.getUsnCreated());
						this.osUserDao.create(osUser);
						// 更新用户id
						sysLdapCn.setUserId(userId);
						this.sysLdapCnDao.update(sysLdapCn);
						// 保存账户
						SysAccount sysAccount = new SysAccount();
						sysAccount.setAccountId(this.idGenerator.getSID());
						sysAccount.setTenantId(tenantId);
						sysAccount.setUserId(userId);
						sysAccount.setEncType(EncryptType.NONE.toString());
						if (StringUtil.isNotEmpty(sysLdapCn.getName())) {
							sysAccount.setFullname(sysLdapCn.getName());
						} else {
							sysAccount.setFullname("用户名称为空");
						}
						if (StringUtil.isNotEmpty(sysLdapCn.getUserAccount())) {
							sysAccount.setName(sysLdapCn.getUserAccount());
						} else {
							sysAccount.setName("用户账户为空"
									+ sysAccount.getAccountId());
						}
						sysAccount.setPwd("1");
						sysAccount.setStatus(MStatus.ENABLED.toString());
						sysAccountDao.create(sysAccount);
						// 关联部门与用户
						OsRelInst osRelInst = new OsRelInst();
						osRelInst.setTenantId(tenantId);
						SysLdapOu sysLdapOuTemp = this.sysLdapOuDao
								.get(sysLdapCn.getSysLdapOuId());
						osRelInst.setParty1(sysLdapOuTemp.getGroupId());
						osRelInst.setParty2(userId);
						osRelInst.setRelTypeId(osRelType.getId());
						osRelInst
								.setRelTypeKey(OsRelType.REL_CAT_GROUP_USER_BELONG);
						osRelInst.setRelType(osRelType.getRelType());
						osRelInst.setStatus(MStatus.ENABLED.toString());
						// 设置主部门
						osRelInst.setDim1(osDimension.getDimId());
						osRelInst.setIsMain(MBoolean.YES.name());
						this.osRelInstDao.create(osRelInst);
					}
				}
			}
		}
	}
	/**
	 * 同步数据到组织架构
	 * 
	 * @Description:
	 * @Title: syncData2org
	 * @param @param tenantId
	 * @param @throws NamingException 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void syncData2org(String tenantId) throws NamingException {
		// 同步到组织架构
		OsDimension osDimension = osDimensionDao.getByDimKeyTenantId(
				OsDimension.DIM_ADMIN, tenantId);
		OsRelType osRelType = osRelTypeDao
				.getByRelTypeKey(OsRelType.REL_CAT_GROUP_USER_BELONG);
		// 新增，更新，禁用
		// 新增
		List<SysLdapOu> sysLdapOuAddList = this.sysLdapOuDao.getByStatus(
				tenantId, LdapStatusEnum.add.toString());
		for (SysLdapOu sysLdapOu : sysLdapOuAddList) {
			// 保存部门
			if (osGroupDao.isLDAPExist(sysLdapOu.getUsnCreated())) {
				OsGroup group = osGroupDao.getByKey(sysLdapOu.getUsnCreated());
				sysLdapOu.setGroupId(group.getGroupId());
				this.sysLdapOuDao.update(sysLdapOu);

			} else {

				OsGroup osGroup = new OsGroup();
				String groupId = this.idGenerator.getSID();
				osGroup.setGroupId(groupId);
				osGroup.setTenantId(tenantId);
				osGroup.setOsDimension(osDimension);
				osGroup.setSn(100);
				if (StringUtil.isNotEmpty(sysLdapOu.getName())) {
					osGroup.setName(sysLdapOu.getName());
				} else {
					osGroup.setName("用户组名称为空");
				}
				osGroup.setStatus(MStatus.ENABLED.toString());
				osGroup.setKey(sysLdapOu.getUsnCreated());
				osGroup.setForm(LdapConfig.LDAP);
				this.osGroupDao.create(osGroup);
				// 更新组id
				sysLdapOu.setGroupId(groupId);
				this.sysLdapOuDao.update(sysLdapOu);
			}

		}
		// 更新部门
		List<SysLdapOu> sysLdapOuUpdateList = this.sysLdapOuDao.getByStatus(
				tenantId, LdapStatusEnum.update.toString());
		for (SysLdapOu sysLdapOu : sysLdapOuUpdateList) {
			if (StringUtil.isNotEmpty(sysLdapOu.getGroupId())) {
				// 更新部门
				OsGroup osGroup = this.osGroupDao.get(sysLdapOu.getGroupId());
				if (osGroup != null) {
					if (StringUtil.isNotEmpty(sysLdapOu.getName())) {
						osGroup.setName(sysLdapOu.getName());
					} else {
						osGroup.setName("用户组名称为空");
					}
					osGroup.setStatus(MStatus.ENABLED.toString());
					osGroup.setForm(LdapConfig.LDAP);
					this.osGroupDao.update(osGroup);
				}
			}
		}
		// 禁用部门
		List<SysLdapOu> sysLdapOuDisableList = this.sysLdapOuDao.getByStatus(
				tenantId, LdapStatusEnum.disable.toString());
		for (SysLdapOu sysLdapOu : sysLdapOuDisableList) {
			if (StringUtil.isNotEmpty(sysLdapOu.getGroupId())) {
				// 禁用部门
				OsGroup osGroup = this.osGroupDao.get(sysLdapOu.getGroupId());
				if (osGroup != null) {
					osGroup.setStatus(MStatus.DISABLED.toString());
					this.osGroupDao.update(osGroup);
				}
			}
		}
		// 禁用部门
		sysLdapOuDisableList = this.sysLdapOuDao.getByStatus(tenantId,
				LdapStatusEnum.del.toString());
		for (SysLdapOu sysLdapOu : sysLdapOuDisableList) {
			if (StringUtil.isNotEmpty(sysLdapOu.getGroupId())) {
				// 禁用部门
				OsGroup osGroup = this.osGroupDao.get(sysLdapOu.getGroupId());
				if (osGroup != null) {
					osGroup.setStatus(MStatus.DISABLED.toString());
					this.osGroupDao.update(osGroup);
				}
			}
		}
		// 新增用户，更新用户，禁用用户
		// 新增用户
		List<SysLdapCn> sysLdapCnAddList = this.sysLdapCnDao.getByStatus(
				tenantId, LdapStatusEnum.add.toString());
		for (SysLdapCn sysLdapCn : sysLdapCnAddList) {
			// 保存用户
			if (osUserDao.isLDAPExist(sysLdapCn.getUsnCreated())) {
				OsUser user = osUserDao.getByUserNo(sysLdapCn.getUsnCreated());
				user.setStatus(OsUser.STATUS_IN_JOB);
				osUserDao.update(user);
				SysAccount sysAccount = sysAccountDao.getByUserId(
						user.getUserId()).get(0);
				sysAccount.setStatus("ENABLE");
				sysAccountDao.update(sysAccount);
				sysLdapCn.setUserId(user.getUserId());
				this.sysLdapCnDao.update(sysLdapCn);
			} else {
				OsUser osUser = new OsUser();
				String userId = this.idGenerator.getSID();
				osUser.setSex("Male");
				osUser.setUserId(userId);
				osUser.setTenantId(tenantId);
				osUser.setUserId(userId);
				if (StringUtil.isNotEmpty(sysLdapCn.getName())) {
					osUser.setFullname(sysLdapCn.getName());
				} else {
					osUser.setFullname("用户名称为空");
				}
				osUser.setStatus(OsUser.STATUS_IN_JOB);
				osUser.setEmail(sysLdapCn.getMail());
				osUser.setMobile(sysLdapCn.getTel());
				osUser.setUserNo(sysLdapCn.getUsnCreated());
				osUser.setFrom(LdapConfig.LDAP);
				this.osUserDao.create(osUser);
				// 更新用户id
				sysLdapCn.setUserId(userId);
				this.sysLdapCnDao.update(sysLdapCn);
				// 保存账户
				SysAccount sysAccount = new SysAccount();
				sysAccount.setAccountId(this.idGenerator.getSID());
				sysAccount.setTenantId(tenantId);
				sysAccount.setUserId(userId);
				sysAccount.setEncType(EncryptType.NONE.toString());
				if (StringUtil.isNotEmpty(sysLdapCn.getName())) {
					sysAccount.setFullname(sysLdapCn.getName());
				} else {
					sysAccount.setFullname("用户名称为空");
				}
				if (StringUtil.isNotEmpty(sysLdapCn.getUserAccount())) {
					sysAccount.setName(sysLdapCn.getUserAccount());
				} else {
					sysAccount.setName("用户账户为空" + sysAccount.getAccountId());
				}
				sysAccount.setPwd("1");
				sysAccount.setStatus(MStatus.ENABLED.toString());
				sysAccountDao.create(sysAccount);
				// 关联部门与用户
				OsRelInst osRelInst = new OsRelInst();
				osRelInst.setTenantId(tenantId);
				osRelInst.setParty1(this.sysLdapOuDao.get(
						sysLdapCn.getSysLdapOuId()).getGroupId());
				osRelInst.setParty2(userId);
				osRelInst.setRelTypeId(osRelType.getId());
				osRelInst.setRelTypeKey(OsRelType.REL_CAT_GROUP_USER_BELONG);
				osRelInst.setRelType(osRelType.getRelType());
				osRelInst.setStatus(MStatus.ENABLED.toString());
				// 设置主部门
				osRelInst.setDim1(osDimension.getDimId());
				osRelInst.setIsMain(MBoolean.YES.name());
				this.osRelInstDao.create(osRelInst);
			}

		}
		// 更新用户
		List<SysLdapCn> sysLdapCnUpdateList = this.sysLdapCnDao.getByStatus(
				tenantId, LdapStatusEnum.update.toString());
		for (SysLdapCn sysLdapCn : sysLdapCnUpdateList) {
			if (StringUtil.isNotEmpty(sysLdapCn.getUserId())) {
				// 更新用户
				OsUser osUser = this.osUserDao.get(sysLdapCn.getUserId());
				String userId = sysLdapCn.getSysLdapUserId();
				if (StringUtil.isNotEmpty(sysLdapCn.getName())) {
					osUser.setFullname(sysLdapCn.getName());
				} else {
					osUser.setFullname("用户名称为空");
				}
				osUser.setStatus(OsUser.STATUS_IN_JOB);
				osUser.setEmail(sysLdapCn.getMail());
				osUser.setMobile(sysLdapCn.getTel());
				if (StringUtil.isNotEmpty(sysLdapCn.getUsnCreated())) {
					osUser.setUserNo(sysLdapCn.getUsnCreated());
				} else {
					osUser.setUserNo("no" + userId);
				}
				osUser.setFrom(LdapConfig.LDAP);
				this.osUserDao.update(osUser);
				// 更新账户
				List<SysAccount> accList = this.sysAccountDao
						.getByUserId(sysLdapCn.getUserId());
				for (SysAccount sysAccount : accList) {
					if (StringUtil.isNotEmpty(sysLdapCn.getName())) {
						sysAccount.setFullname(sysLdapCn.getName());
					} else {
						sysAccount.setFullname("用户名称为空");
					}
					if (StringUtil.isNotEmpty(sysLdapCn.getUserAccount())) {
						sysAccount.setName(sysLdapCn.getUserAccount());
					} else {
						sysAccount
								.setName("用户账户为空" + sysAccount.getAccountId());
					}
					sysAccount.setStatus(MStatus.ENABLED.toString());
					sysAccountDao.update(sysAccount);
				}
			}
		}
		// 禁用 用户
		List<SysLdapCn> sysLdapCnDisableList = this.sysLdapCnDao.getByStatus(
				tenantId, LdapStatusEnum.disable.toString());
		for (SysLdapCn sysLdapCn : sysLdapCnDisableList) {
			if (StringUtil.isNotEmpty(sysLdapCn.getUserId())) {
				// 禁用用户
				OsUser osUser = this.osUserDao.get(sysLdapCn.getUserId());
				if (osUser != null) {
					osUser.setStatus(OsUser.STATUS_DEL_JOB);
					this.osUserDao.update(osUser);
				}
				// 禁用账户
				List<SysAccount> accList = this.sysAccountDao
						.getByUserId(sysLdapCn.getUserId());
				for (SysAccount sysAccount : accList) {
					sysAccount.setStatus(MStatus.DISABLED.toString());
					sysAccountDao.update(sysAccount);
				}
			}
		}
		// 禁用 用户
		sysLdapCnDisableList = this.sysLdapCnDao.getByStatus(tenantId,
				LdapStatusEnum.del.toString());
		for (SysLdapCn sysLdapCn : sysLdapCnDisableList) {
			if (StringUtil.isNotEmpty(sysLdapCn.getUserId())) {
				// 禁用用户
				OsUser osUser = this.osUserDao.get(sysLdapCn.getUserId());
				if (osUser != null) {
					osUser.setStatus(OsUser.STATUS_DEL_JOB);
					this.osUserDao.update(osUser);
				}
				// 禁用账户
				List<SysAccount> accList = this.sysAccountDao
						.getByUserId(sysLdapCn.getUserId());
				for (SysAccount sysAccount : accList) {
					sysAccount.setStatus(MStatus.DISABLED.toString());
					sysAccountDao.update(sysAccount);
				}
			}
		}
	}
	/**
	 * 更新所有上一级id和path
	 * 
	 * @Description:
	 * @Title: updatePathAll
	 * @param @param tenantId
	 * @param @param osGroupParent
	 * @param @param sysLdapOuList 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void updatePathAll(String tenantId, OsGroup osGroupParent,
			List<SysLdapOu> sysLdapOuList) {
		for (SysLdapOu sysLdapOu : sysLdapOuList) {
			if (StringUtil.isNotEmpty(sysLdapOu.getGroupId())) {
				List<SysLdapOu> sysLdapOuChildList = this.sysLdapOuDao
						.getByParentId(tenantId, sysLdapOu.getSysLdapOuId());
				OsGroup osGroup = this.osGroupDao.get(sysLdapOu.getGroupId());
				if (osGroup != null) {
					if (osGroupParent == null) {
						osGroup.setParentId("0");
						osGroup.setPath("0." + osGroup.getGroupId() + ".");
						osGroup.setChilds(sysLdapOuChildList.size());
						this.osGroupDao.update(osGroup);
					} else {
						osGroup.setParentId(osGroupParent.getGroupId());
						osGroup.setPath(osGroupParent.getPath()
								+ osGroup.getGroupId() + ".");
						osGroup.setChilds(sysLdapOuChildList.size());
						this.osGroupDao.update(osGroup);
					}
					this.updatePathAll(tenantId, osGroup, sysLdapOuChildList);
				}
			}
		}
	}
}