package com.redxun.sys.ldap.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.db.core.return_code.ReturnCodeThreadLocal;
import com.redxun.core.db.ldap.LdapConfigEnum;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.ldap.entity.SysLdapConfig;
import com.redxun.sys.ldap.entity.SysLdapLog;
import com.redxun.sys.ldap.manager.SysLdapConfigManager;
/**
 * Ldap配置管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/sys/ldap/sysLdapConfig/")
public class SysLdapConfigController extends BaseListController {
	@Resource
	SysLdapConfigManager sysLdapConfigManager;

	/**
	 * 
	 * ldapConn
	 * 
	 * @Description:
	 * @Title: ldapConn
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception 参数说明
	 * @return JsonResult 返回类型
	 * @throws
	 */
	@RequestMapping("ldapConn")
	@ResponseBody
	public JsonResult ldapConn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysLdapLog sysLdapLog = new SysLdapLog();

		sysLdapLog.setLogName("手工连接LDAP");
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				this.sysLdapConfigManager.doConn(id, sysLdapLog);
				if (ReturnCodeThreadLocal.isSuccess()) {
					SysLdapConfig sysLdapConfig = this.sysLdapConfigManager
							.get(id);
					sysLdapConfig.setStatus(LdapConfigEnum.connLdap.name());
					sysLdapConfig.setStatusCn(LdapConfigEnum.connLdap
							.toString());
					this.sysLdapConfigManager.update(sysLdapConfig);
				} else {
					return new JsonResult(false, sysLdapLog.getLogName()
							+ "失败"
							+ ReturnCodeThreadLocal.findReturnCodeMain()
									.getContent());
				}
			}
		}
		return new JsonResult(true, sysLdapLog.getLogName() + "成功");
	}
	/**
	 * syncData
	 * 
	 * @Description:
	 * @Title: syncData
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception 参数说明
	 * @return JsonResult 返回类型
	 * @throws
	 */
	@RequestMapping("syncData")
	@ResponseBody
	public JsonResult syncData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysLdapLog sysLdapLog = new SysLdapLog();

		sysLdapLog.setLogName("手工同步AD数据");
		try {
			String uId = request.getParameter("ids");
			if (StringUtils.isNotEmpty(uId)) {
				String[] ids = uId.split(",");
				for (String id : ids) {
					this.sysLdapConfigManager.doSyncData(
							ContextUtil.getCurrentTenantId(), id, sysLdapLog);
					if (ReturnCodeThreadLocal.isSuccess()) {
					} else {
						StringBuffer sb = new StringBuffer(
								sysLdapLog.getLogName() + "错误");
						sb.append("原因为:").append(
								ReturnCodeThreadLocal.findReturnCodeMain()
										.getMsgCn());
						return new JsonResult(true, sb.toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer(sysLdapLog.getLogName() + "错误");
			sb.append("原因为:").append("");
			sb.append(e.getMessage()).append("");
			log.error(sb.toString(), e);
			return new JsonResult(true, sb.toString());
		}
		return new JsonResult(true, sysLdapLog.getLogName() + "成功");
	}
	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				sysLdapConfigManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
	}
	/**
	 * 查看明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		SysLdapConfig sysLdapConfig = null;
		if (StringUtils.isNotEmpty(pkId)) {
			sysLdapConfig = sysLdapConfigManager.get(pkId);
		} else {
			sysLdapConfig = new SysLdapConfig();
		}
		return getPathView(request).addObject("sysLdapConfig", sysLdapConfig);
	}
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		SysLdapConfig sysLdapConfig = null;
		if (StringUtils.isNotEmpty(pkId)) {
			sysLdapConfig = sysLdapConfigManager.get(pkId);
			if ("true".equals(forCopy)) {
				sysLdapConfig.setSysLdapConfigId(null);
			}
		} else {
			sysLdapConfig = new SysLdapConfig();
		}
		return getPathView(request).addObject("sysLdapConfig", sysLdapConfig);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return sysLdapConfigManager;
	}
}
