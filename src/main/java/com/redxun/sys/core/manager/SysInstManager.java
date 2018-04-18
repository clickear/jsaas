package com.redxun.sys.core.manager;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.redxun.core.cache.CacheUtil;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.constants.MStatus;
import com.redxun.core.dao.IDao;
import com.redxun.core.jms.IMessageProducer;
import com.redxun.core.mail.model.Mail;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.EncryptUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.dao.SysInstDao;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.util.SysPropertiesUtil;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsDimensionManager;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsRelInstManager;
import com.redxun.sys.org.manager.OsRelTypeManager;
import com.redxun.sys.org.manager.OsUserManager;
/**
 * <pre> 
 * 描述：SysInst业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysInstManager extends BaseManager<SysInst>{
	@Resource
	private SysInstDao sysInstDao;
	@Resource
	private OsDimensionManager osDimensionManager;
	@Resource
	private OsRelTypeManager osRelTypeManager;
	@Resource
	private OsUserManager osUserManager;
	@Resource
	private OsGroupManager osGroupManager;
	@Resource
	private OsRelInstManager osRelInstManager;
	@Resource
	private SysAccountManager sysAccountManager;
	@Resource
	IMessageProducer messageProducer;
	
	
	private static String TENANTS="tenants_";
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysInstDao;
	}
	
	public SysInst getByDomain(String domain){
		return sysInstDao.getByDomain(domain);
	}
	
	@Override
	public void delete(String id) {
		
		CacheUtil.delCache(TENANTS);
		
		initTenants();
		//先删除其对应的关联数据
		sysAccountManager.deleteByTenantId(id);
		osUserManager.deleteByTenantId(id);
		osRelTypeManager.deleteByTenantId(id);
		osDimensionManager.deleteByTenantId(id);
		super.delete(id);
	}
	
	
	
	
	
	@Override
	public void create(SysInst entity) {
		initTenants();
		super.create(entity);
	}

	@Override
	public void update(SysInst entity) {
		initTenants();
		super.update(entity);
	}

	/**
	 * 获得有效的机构列表
	 * @return
	 */
	public List<SysInst> getValidSysInsts(){
		return sysInstDao.getByStatus(MStatus.ENABLED.name());
	}
	
	/**
	 * 从缓存中获取租户信息。
	 * @return
	 */
	public static List<SysInst> getTenants(){
		SysInstManager manager=AppBeanUtil.getBean(SysInstManager.class);
		List<SysInst> list= (List<SysInst>) CacheUtil.getCache(TENANTS);
		if(BeanUtil.isEmpty(list)){
			list=manager.initTenants();
		}
		return list;
	}
	
	/**
	 * 初始化租户。
	 * @return
	 */
	public List<SysInst> initTenants(){
		List<SysInst> rtnList=new ArrayList<SysInst>();
		List<SysInst> list= getValidSysInsts();
		for(SysInst origin:list){
			SysInst inst=new SysInst();
			inst.setInstId(origin.getInstId());
			inst.setNameCn(origin.getNameCn());
			inst.setDomain(origin.getDomain());
			inst.setDsName(origin.getDsName());
			inst.setDsAlias(origin.getDsAlias());
			inst.setStatus(origin.getStatus());
			inst.setInstType(origin.getInstType());
			rtnList.add(inst);
		}
		CacheUtil.addCache(TENANTS, rtnList);
		
		return rtnList;
	}
	
	/**
	 * 注册企业机构
	 * @param sysInst
	 * @return
	 */
	public boolean doRegister(SysInst sysInst){
		String account=SysPropertiesUtil.getTenantAdminAccount();
		
		//先初始化状态，激活后才审批通过
		sysInst.setStatus(MStatus.INIT.toString());
		String instId=IdUtil.getId();
		sysInst.setInstId(instId);
		if(StringUtils.isNotEmpty(sysInst.getParentId())){
			SysInst pInst=get(sysInst.getParentId());
			if(pInst!=null){
				sysInst.setPath(pInst.getPath()+ instId+".");
			}else{
				sysInst.setPath("0." + instId+".");
			}
		}else{
			sysInst.setPath("0." + instId+".");
		}
		sysInstDao.create(sysInst);
		OsGroup mainGroup=osGroupManager.addInitPersonalGroup(sysInst);
		//创建化用户
		OsUser adminUser=osUserManager.initAdminUser(sysInst.getInstId(), "管理员", sysInst.getEmail(), sysInst.getPhone(), account);
		//初始一个行政用户组
		osRelInstManager.addBelongRelInst(mainGroup.getGroupId(), adminUser.getUserId(), MBoolean.YES.name());
		
		String pwd=new Integer(new Double(1000000*Math.random()).intValue()).toString();
		String enPwd=EncryptUtil.encryptSha256(pwd);
		//产生该企业的超级管理员
		sysAccountManager.initAccount(account,sysInst.getDomain(), enPwd, adminUser.getUserId(), "管理员", sysInst.getInstId());
		//发送邮件，通知管理该用户收到该邮件以进行激活
		sendMail(sysInst,pwd);
		
		return true;
	}
	

	public SysInst getByNameCn(String nameCn){
		return sysInstDao.getByNameCn(nameCn);
	}
	
	public SysInst getByShortNameCn(String shortNameCn){
		return sysInstDao.getByShortNameCn(shortNameCn);
	}

	private void sendMail(SysInst sysInst,String pwd){
		Mail model=new Mail();
				
		model.setSubject("云端企业注册激活通知--"+ sysInst.getNameCn());
		model.setSenderAddress(WebAppUtil.getMailFrom());
		
		model.setReceiverAddresses(sysInst.getEmail());
		model.setTemplate("mail/sysInstActive.ftl");
		//TODO 产生临时的激活码,并且需要在一定的时间内激活,过期则不允许处理
		model.addVar("activeUrl", WebAppUtil.getInstallHost()+"/activeInst.do?instId="+sysInst.getInstId());
		model.addVar("loginUrl", WebAppUtil.getInstallHost()+"/login.jsp");
		String account=SysPropertiesUtil.getAdminAccount();
		model.addVar("adminAccount", account+ "@"+sysInst.getDomain());
		model.addVar("password", pwd);
		model.addVar("appName", WebAppUtil.getAppName());
		//把邮件放置邮件的消息队列中
		messageProducer.send(model);
	}
	
}