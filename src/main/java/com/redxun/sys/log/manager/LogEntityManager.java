
package com.redxun.sys.log.manager;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.log.dao.LogEntityDao;
import com.redxun.sys.log.dao.LogEntityQueryDao;
import com.redxun.sys.log.entity.LogEntity;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：日志实体 处理接口
 * 作者:陈茂昌
 * 日期:2017-09-21 14:43:53
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class LogEntityManager extends ExtBaseManager<LogEntity>{
	@Resource
	private LogEntityDao logEntityDao;
	@Resource
	private LogEntityQueryDao logEntityQueryDao;
	@Resource
	private OsGroupManager osGroupManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return logEntityQueryDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return logEntityQueryDao;
	}
	
	public LogEntity getLogEntity(String uId){
		LogEntity logEntity = get(uId);
		return logEntity;
	}
	
	/**
	 * 记录登录日志
	 * @param request
	 */
	public void recordTheLog(HttpServletRequest request,HttpServletResponse response){
		String userAgent=request.getHeader("user-agent");
		String ip=request.getRemoteAddr();
		String userId=ContextUtil.getCurrentUserId();
		String tenantId=ContextUtil.getCurrentTenantId();
		OsGroup osGroup=osGroupManager.getMainDeps(userId);
		String mainGroupName;
		if(osGroup!=null){
			String path=osGroup.getPath();
			String[] groupIds=path.split("[.]");
			StringBuilder groupName=new StringBuilder("");
			for (int i = 0; i < groupIds.length; i++) {
				OsGroup group=osGroupManager.get(groupIds[i]);
				groupName.append(group!=null?group.getName()+"-":"");
			}
			if(groupName.length()>0){
				groupName.deleteCharAt(groupName.length()-1);
			}
			mainGroupName=groupName.toString();
		}else{
			mainGroupName="无主部门";
		}
		request.getCookies();
		Date date=new Date();
		String id=IdUtil.getId();
		Cookie[]  cookies=request.getCookies();
		/*清除cookie*/
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie=cookies[i];
			if("logInOutId".equals(cookie.getName())){
				cookie.setValue(null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				break;
			}
		}
		Cookie cookie = new Cookie("logInOutId",id);
		response.addCookie(cookie);
		
		
		LogEntity logEntity=new LogEntity();
		logEntity.setId(id);
		logEntity.setAction("登陆");
		logEntity.setCreateBy(userId);
		logEntity.setMainGroupName(mainGroupName);
		logEntity.setMainGroup(osGroup==null?"":osGroup.getGroupId());
		logEntity.setIp(ip);
		logEntity.setUserAgent(userAgent);
		logEntity.setCreateTime(date);
		logEntity.setTenantId(tenantId);
		this.create(logEntity);
	}
	
	public void recordTheLogOut(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies=request.getCookies();
		Date date=new Date();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie=cookies[i];
			if("logInOutId".equals(cookie.getName())){
				String logInOutId=cookie.getValue();
				LogEntity logEntity=this.get(logInOutId);
				if(logEntity!=null){
					logEntity.setUpdateTime(date);
					Date loginTime=logEntity.getCreateTime();
					logEntity.setDuration(date.getTime()-loginTime.getTime());
					this.update(logEntity);
				}
				cookie.setValue(null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				break;
			}
		}
	}
}
