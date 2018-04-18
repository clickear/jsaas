package com.redxun.oa.doc.controller;

import java.util.ArrayList;
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
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.doc.entity.DocFolder;
import com.redxun.oa.doc.manager.DocFolderManager;
import com.redxun.oa.doc.manager.DocManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 文件夹管理
 * 
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/doc/docFolder/")
public class DocFolderController extends TenantListController {
	@Resource
	DocFolderManager docFolderManager;
	@Resource
	DocManager docManager;
	@Resource
	OsUserManager osUserManager;

	/**
	 * 按照ids删除对应的实体
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				docFolderManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 按照folderId删除对应的实体以及所包含的文件夹和文件还有文件里的附件,前台jsp需要传入folderId type(文档类型)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delContain")
	@ResponseBody
	public JsonResult delContain(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String folderId = request.getParameter("folderId");
		String type = request.getParameter("type");
		if (StringUtils.isNotEmpty(folderId)) {
			List<DocFolder> docFolders = docFolderManager.getSpecialPathFolder(
					folderId, type);
			for (DocFolder docFolder : docFolders) {// 遍历文件夹并且删除
				docFolderManager.deleteObject(docFolder);
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 查看文件夹明细,需要传入主键pkId
	 * 
	 * @param request
	 * @param response
	 * @return 返回get.jsp
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		DocFolder docFolder = null;
		if (StringUtils.isNotEmpty(pkId)) {// 如果pkId不为空就查找docFolder，为空则新建实体
			docFolder = docFolderManager.get(pkId);
		} else {
			docFolder = new DocFolder();
		}
		return getPathView(request).addObject("docFolder", docFolder);
	}

	/**
	 * 个人，公司点击左边的树列表后右边显示包含的文档,前台传入folderId multi type
	 * 这三个参数回填到index页面,构成参数,以便页面查找并且显示list
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String folderId = request.getParameter("folderId");
		String multi = request.getParameter("multi");
		String type = request.getParameter("type");
		return getPathView(request).addObject("folderId", folderId)
				.addObject("multi", multi).addObject("type", type);
	}

	/**
	 * 个人，公司点击左边的树列表后右边显示包含的文档,前台传入folderId multi type
	 * 这三个参数回填到index页面,构成参数,以便页面查找并且显示list
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showIndex")
	public ModelAndView showIndex(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String folderId = request.getParameter("folderId");
		String multi = request.getParameter("multi");
		String type = request.getParameter("type");
		return getPathView(request).addObject("folderId", folderId)
				.addObject("multi", multi).addObject("type", type);
	}

	/**
	 * 共享文文档，点击左边的树列表后右边显示包含的文档
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("shareIndex")
	public ModelAndView shareIndex(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String folderId = request.getParameter("folderId");
		String multi = request.getParameter("multi");
		String type = request.getParameter("type");
		String isShare = request.getParameter("isShare");// 此处是共享文件夹的字段
		return getPathView(request).addObject("folderId", folderId)
				.addObject("multi", multi).addObject("type", type)
				.addObject("isShare", isShare);
	}

	/**
	 * 文档管理器显示方式,以图表的方式显示文件夹和文件,前台传入folderId isShare type
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listBoxWindow")
	public ModelAndView listBoxWindow(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String folderId = request.getParameter("folderId");
		String type = request.getParameter("type");
		String isShare = request.getParameter("isShare");
		String firstOpenId = request.getParameter("firstOpenId");// 最初打开的文件夹,用来控制"后退"按钮
		return getPathView(request).addObject("firstOpenId", firstOpenId)
				.addObject("folderId", folderId).addObject("isShare", isShare)
				.addObject("type", type);
	}

	/**
	 * 修改docfolder文件夹
	 * 
	 * @param request
	 * @param response
	 * @return edit页面返回实体
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String pkId = request.getParameter("pkId");
		String parent = request.getParameter("parent");
		String userId = request.getParameter("path");
		String share = request.getParameter("share");
		String type = request.getParameter("type");

		DocFolder docFolder = null;
		if (StringUtils.isNotEmpty(pkId)) {// 如果pkId不为空则按pkId获取docFolder实体，否则新建
			docFolder = docFolderManager.get(pkId);

		} else {
			docFolder = new DocFolder();
			docFolder.setParent(parent);
			docFolder.setType(type);
			docFolder.setShare(share);

			if ("COMPANY".equals(docFolder.getType())) {
				System.out.println("$$$");
				docFolder.setUserId("0");
			}// 设置userid判断是公司还是个人,0是公司
			else {
				System.out.println("!!");
				docFolder.setUserId(ContextUtil.getCurrentUserId());
			}// 是个人则把userId设置成当前登录账户的Id
		}
		return getPathView(request).addObject("docFolder", docFolder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return docFolderManager;
	}

	/**
	 * 获取共享字段为YES的文件夹
	 * 
	 * @return docFolder列表list
	 * @throws Exception
	 */
	@RequestMapping("listShare")
	@ResponseBody
	public List<DocFolder> listShare() throws Exception {
		String tenantId = ContextUtil.getCurrentTenantId();
		List<DocFolder> list = docFolderManager.getShareFolder(tenantId);
		return list;

	}

	/**
	 * 个人文档目录
	 * 
	 * @return 个人的文件夹list
	 * @throws Exception
	 */
	@RequestMapping("listPersonal")
	@ResponseBody
	public List<DocFolder> listPersonal() throws Exception {
		String tenantId = ContextUtil.getCurrentTenantId();
		String userId = ContextUtil.getCurrentUserId();
		String type = "PERSONAL";
		List<DocFolder> list = docFolderManager.getByUserId(userId, tenantId,
				type);// userId是个人文档
		return list;
	}

	/**
	 * 公司文档目录
	 * 
	 * @return list
	 * @throws Exception
	 */
	@RequestMapping("listCompany")
	@ResponseBody
	public List<DocFolder> listCompany(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tenantId = ContextUtil.getCurrentTenantId();
		List<DocFolder> list = docFolderManager.getCompanyFolder("COMPANY",
				tenantId);
		return list;
	}

	/**
	 * 跳入选择界面,并传入是个人还是公司
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("select")
	public ModelAndView select(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		return getPathView(request).addObject("type", type);
	}

	/**
	 * 通过folderId(文件夹Id)获取一个DocFolder
	 * 
	 * @param request
	 * @return docFolder实体
	 * @throws Exception
	 */
	@RequestMapping("getOne")
	@ResponseBody
	public DocFolder getOne(HttpServletRequest request) throws Exception {
		String folderId = request.getParameter("folderId");
		DocFolder docFolder = docFolderManager.get(folderId);
		return docFolder;
	}

	/**
	 * 根据folderId(文件夹Id)获取它的parentId(父Id)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getParentFolderId")
	@ResponseBody
	public String getParentFolderId(HttpServletRequest request)
			throws Exception {
		String folderId = request.getParameter("folderId");
		String thisparentId = docFolderManager.get(folderId).getParent();
		// String parentId=docFolderManager.get(thisparentId).getParent();
		return thisparentId;

	}

}
