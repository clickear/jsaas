package com.redxun.oa.mail.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.mail.entity.MailConfig;
import com.redxun.oa.mail.entity.MailFolder;
import com.redxun.oa.mail.manager.MailConfigManager;
import com.redxun.oa.mail.manager.MailFolderManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;

/**
 * 邮件文件夹Controller
 * 
 * @author zwj 
 * 用途：处理对邮件文件夹相关操作的请求映射
 */
@Controller
@RequestMapping("/oa/mail/mailFolder/")
public class MailFolderController extends TenantListController {
	@Resource
	MailConfigManager mailConfigManager;

	@Resource
	MailFolderManager mailFolderManager;

	/**
	 * 根据主键删除邮箱文件夹
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				mailFolderManager.delete(id); // 删除实体
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 查看邮件文件夹的明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		MailFolder mailFolder = null;
		if (StringUtils.isNotEmpty(pkId)) {
			mailFolder = mailFolderManager.get(pkId);
		} else {
			mailFolder = new MailFolder();
		}
		return getPathView(request).addObject("mailFolder", mailFolder);
	}

	/**
	 * 根据外部邮箱账号配置Id获取对应配置的邮件目录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getFolderByGonfigId")
	@ResponseBody
	public JsonResult getFolderByGonfigId(HttpServletRequest request) throws Exception {
		String configId = request.getParameter("configId");
		List<MailFolder> mailfolders = mailFolderManager.getOutMailFolderByConfigId(configId); // 获取配置configId下的文件夹目录
		return new JsonResult(true, "成功", mailfolders);
	}

	/**
	 * 根据配置ID获取邮件目录,去掉根目录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getFolderByGonfigIdOutRoot")
	@ResponseBody
	public List<MailFolder> getFolderByGonfigIdOutRoot(HttpServletRequest request) throws Exception {
		String configId = request.getParameter("configId");
		List<MailFolder> mailfolders=mailFolderManager.getOutMailFolderOutOfRootByConfigId(configId);// 获取配置configId下的文件夹目录,去根目录
/*		List<MailFolder> mailfolders = mailFolderManager.getOutMailFolderByConfigId(configId); // 获取配置configId下的文件夹目录
*//*		for (int i = 0; i < mailfolders.size(); i++) { // 去根目录
			if ("0".equals(mailfolders.get(i).getParentId())) {
				mailfolders.remove(i);
			}
		}*/
		return mailfolders;
	}

	/**
	 * 获取当前用户所有邮件目录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllFolder")
	@ResponseBody
	public List<MailFolder> getAllFolder(HttpServletRequest request) throws Exception {
		List<MailFolder> mailfolders = mailFolderManager.getAllOutMailFolderByUserId(ContextUtil.getCurrentUserId());// 获取这个用户的所有外部邮件目录
		for (int i = 0; i < mailfolders.size(); i++) {
			if ("0".equals(mailfolders.get(i).getParentId())) {
				mailfolders.get(i).setName(mailConfigManager.get(mailfolders.get(i).getConfigId()).getAccount()); // 设置outlook-tree账号别名
			}
			mailfolders.get(i).setAlias(mailConfigManager.get(mailfolders.get(i).getConfigId()).getAccount()); // 设置别名
			if (MailFolder.TYPE_RECEIVE_FOLDER.equals(mailfolders.get(i).getType())) { // 如果文件夹是收件箱
				Long count = mailFolderManager.getUnreadMailByConfigId(mailfolders.get(i).getMailConfig().getConfigId()); // 获取未读邮件的总数
				mailfolders.get(i).setName(mailfolders.get(i).getName() + "(" + count + ")");
			}
		}
		return mailfolders;
	}

	/**
	 * 根据mailFolderId获取该文件夹的未读邮件数量（外部邮件）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getUnreadSum")
	@ResponseBody
	public JsonResult getUnreadSum(HttpServletRequest request) throws Exception {
		String mailFolderId = request.getParameter("mailFolderId");
		String configId = request.getParameter("configId");
		Long count = 0L;
		if (StringUtils.isNotEmpty(configId))
			count = mailFolderManager.getUnreadMailByConfigId(configId);
		else
			count = mailFolderManager.getUnreadMailByConfigId(mailFolderManager.get(mailFolderId).getMailConfig().getConfigId());
		return new JsonResult(true,"", count);
	}

	/**
	 * 编辑邮箱文件夹
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		String parentId = request.getParameter("parentId");
		String configId = request.getParameter("configId");
		String depth = request.getParameter("depth");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		MailFolder mailFolder = null;
		if (StringUtils.isNotEmpty(pkId)) {
			mailFolder = mailFolderManager.get(pkId);
			if ("true".equals(forCopy)) {
				mailFolder.setFolderId(null);
			}
		} else {
			if (StringUtils.isEmpty(configId)) { // configId为空的话，则为内部邮件文件夹
				mailFolder = new MailFolder();
				mailFolder.setParentId(parentId);
				mailFolder.setUserId(ContextUtil.getCurrentUserId());
				mailFolder.setType(MailFolder.TYPE_OTHER_FOLDER);
				mailFolder.setDepth(Integer.parseInt(depth));
				mailFolder.setInOut(MailFolder.FOLDER_FLAG_IN);
			} else { // 外部文件夹
				MailConfig mailConfig = mailConfigManager.get(configId);
				mailFolder = new MailFolder();
				mailFolder.setParentId(parentId);
				mailFolder.setMailConfig(mailConfig);
				mailFolder.setUserId(ContextUtil.getCurrentUserId());
				mailFolder.setType(MailFolder.TYPE_OTHER_FOLDER);
				mailFolder.setDepth(Integer.parseInt(depth));
				mailFolder.setInOut(MailFolder.FOLDER_FLAG_OUT);
			}
		}
		return getPathView(request).addObject("mailFolder", mailFolder);
	}

	/**
	 * 获取当前用户所有内部邮件文件夹目录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInnerMailFolder")
	@ResponseBody
	public List<MailFolder> getInnerMailFolder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<MailFolder> innerMailFolders = mailFolderManager.getInnerMailFolder(ContextUtil.getCurrentUserId());
		if (innerMailFolders.size() < 5) { // 如果数量小于5
			String folderId = idGenerator.getSID();
			mailFolderManager.createInnerMailFolder(folderId, ContextUtil.getCurrentUserId()); // 生成该用户的5个基本邮件目录：根目录，收件箱，发件箱，草稿箱，垃圾箱
		}
		innerMailFolders = mailFolderManager.getInnerMailFolder(ContextUtil.getCurrentUserId());
		for (int i = 0; i < innerMailFolders.size(); i++) { // 获取收件箱未读邮件数量
			if (MailFolder.TYPE_RECEIVE_FOLDER.equals(innerMailFolders.get(i).getType())) {
				Long count = mailFolderManager.getInnerUnreadMailByFolderId(innerMailFolders.get(i).getFolderId()); // 获取未读邮件的总数
				innerMailFolders.get(i).setName(innerMailFolders.get(i).getName() + "(" + count + ")");
			}
		}
		return innerMailFolders;
	}

	/**
	 * 获取内部邮件文件夹目录，去除根目录和发件箱
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInnerMailFolderOutOfRoot")
	@ResponseBody
	public List<MailFolder> getInnerMailFolderOutOfRoot(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<MailFolder> innerMailFolders = mailFolderManager.getInnerMailFolderOutOfRootByUserId(ContextUtil.getCurrentUserId()); // 获取内部邮件文件夹目录，去除根目录
		return innerMailFolders;
	}
	
	@RequestMapping("getInnerSenderFolder")
	@ResponseBody
	public List<MailFolder> getInnerSenderFolder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<MailFolder> innerSenderFolders = mailFolderManager.getInnerSenderFolder(ContextUtil.getCurrentUserId()); // 获取内部邮件文件夹目录，去除根目录
		return innerSenderFolders;
	}
	
	/*@RequestMapping("getByConfigId")
	@ResponseBody
	public JsonResult getByConfigId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String configId=request.getParameter("configId");
		List<MailFolder> mailFolders=mailFolderManager.getOutMailFolderByConfigId(configId);
		return new JsonResult(true,mailFolders);
	}*/

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return mailFolderManager;
	}

}
