package com.redxun.sys.log;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.context.HttpServletContext;
import com.redxun.core.jms.MessageProducer;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.log.entity.LogEntity;
import com.redxun.sys.log.entity.LogModule;
import com.redxun.sys.log.manager.LogModuleManager;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;
/**
 * 日志切片处理
 * @author chshx
 *
 */
public class LogAspect {
	
	public Object log(ProceedingJoinPoint  joinPoint) throws Throwable{
		
		Object obj= joinPoint.proceed();
		
		Method method= getMethod(joinPoint);
		if(method!=null ){
			LogEnt logEnt= method.getAnnotation(LogEnt.class);
			String module=logEnt.module();
			String subModule=logEnt.submodule();
			LogModuleManager logModuleManager=AppBeanUtil.getBean(LogModuleManager.class);
			OsGroupManager osGroupManager=AppBeanUtil.getBean(OsGroupManager.class);
			LogModule logModule=logModuleManager.getLogModuleByModuleAndSubModule(module, subModule);
			if(logModule!=null&&"FALSE".equals(logModule.getEnable())){
				return obj;
			}
			String action=logEnt.action();
			String tenantId=ContextUtil.getCurrentTenantId();
			String createBy=ContextUtil.getCurrentUserId();
			String[] paramNames=(logEnt.params()).split(",");
			HttpServletRequest request=HttpServletContext.getRequest();
			String userAgent=request.getHeader("user-agent");
			String userId=ContextUtil.getCurrentUserId();
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
			JSONArray jsonArray=new JSONArray();
			for (String param : paramNames) {
				String paramValue=request.getParameter(param);
				if(StringUtils.isNotBlank(paramValue)){
					JSONObject jsonObject=new JSONObject();
					jsonObject.put(param, paramValue);
					jsonArray.add(jsonObject);
				}
			}
			String ip=request.getRemoteAddr();
			LogEntity logEntity=new LogEntity();
			logEntity.setAction(action);
			logEntity.setId(IdUtil.getId());
			logEntity.setModule(module);
			logEntity.setSubModule(subModule);
			logEntity.setTenantId(tenantId);
			logEntity.setCreateBy(createBy);
			logEntity.setUserAgent(userAgent);
			logEntity.setIp(ip);
			logEntity.setMainGroupName(mainGroupName);
			logEntity.setTarget(jsonArray.toString());
			logEntity.setMainGroup(osGroup!=null?osGroup.getName():"");
			MessageProducer messageProducer=AppBeanUtil.getBean(MessageProducer.class);
			messageProducer.send("logMessageQueue", logEntity);
			
		}
		return obj;
		
	}
	
	private Method getMethod(ProceedingJoinPoint point){
 		String methodName=point.getSignature().getName();
		
		Object[] arguments = point.getArgs(); 
		
		Class<?> targetClass = point.getTarget().getClass();
		//方法
		Method[] methods = targetClass.getMethods();
		
		for(Method method:methods){
			if(!method.getName().equals(methodName)) continue;
			Class[] clazzs = method.getParameterTypes();
			if(clazzs.length!=arguments.length) continue;
			LogEnt logEnt= method.getAnnotation(LogEnt.class);
			if(logEnt!=null) return method;
		}
		return null;
	}
	
	
	public void afterThrowing(Exception e)  throws Throwable{  
        System.out.println("出异常了..."+e);
	}

}
