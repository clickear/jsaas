package com.redxun.oa.doc.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.FileUtil;
import com.redxun.oa.doc.entity.Doc;
import com.redxun.oa.doc.entity.DocAndFolder;
import com.redxun.oa.doc.entity.DocFolder;
import com.redxun.oa.doc.entity.DocRight;
import com.redxun.oa.doc.manager.DocFolderManager;
import com.redxun.oa.doc.manager.DocManager;
import com.redxun.oa.doc.manager.DocRightManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 文件管理
 * 
 * @author 陈茂昌 创建时间：2015.12.09
 */
@Controller
@RequestMapping("/oa/doc/doc/")
public class DocController extends TenantListController {

	@Resource
	DocFolderManager docFolderManager;

	@Resource
	DocManager docManager;

	@Resource
	OsUserManager osUserManager;
	@Resource
	OsGroupManager osGroupManager;
	@Resource
	DocRightManager docRightManager;

	/**
	 * 
	 * @param request从前台得到ids
	 *            ，然后删除对应的实体
	 * @param response
	 * @return jsonResult成功删除
	 * @throws Exception
	 */
	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {// 如果uid不为空则继续
			String[] ids = uId.split(",");
			for (String id : ids) {
				docManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 查看明细
	 * 
	 * @param request
	 * @param response
	 * @return 返回相应的get视图，到了docGet.jsp
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		String userId = ContextUtil.getCurrentUserId();
		Doc doc = null;
		boolean canEdit = false;
		boolean canDel = false;

		if (docRightManager.getByDocIdIdentityIdRight(pkId, DocRight.IDENTITYTYPE_USER, userId, DocRight.RIGHTS_EDIT)) {
			canEdit = true;
		}
		if (docRightManager.getByDocIdIdentityIdRight(pkId, DocRight.IDENTITYTYPE_USER, userId, DocRight.RIGHTS_DEL)) {
			canDel = true;
		}

		if (StringUtils.isNotEmpty(pkId)) {// 如果pkId不为空则获取对应实体
			doc = docManager.get(pkId);
		} else {
			doc = new Doc();// 否则新建实体
		}

		Map<String, String> map = new TreeMap<String, String>();// 构建map存放对应的附件
		for (SysFile file : doc.getInfDocFiles()) {
			map.put(file.getFileId(), file.getFileName());// 遍历每个doc实体的附件，放入map里
		}

		/*if (!Doc.DOC_HTML.equals(doc.getDocType())) {
			// 处理Office文档的配置
			// 设置PageOffice服务器组件
			PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
			poCtrl1.setServerPage(request.getContextPath() + "/poserver.zz"); // 此行必须
			// 设置打开的Word文档的主题
			// poCtrl.setTheme(ThemeType.Office2010);
			poCtrl1.setTheme(ThemeType.Office2010);
			poCtrl1.setTitlebar(false); // 隐藏标题栏
			poCtrl1.setMenubar(false); // 隐藏菜单栏
			poCtrl1.setOfficeToolbars(false);// 隐藏Office工具条
			poCtrl1.setCustomToolbar(false);// 隐藏自定义工具栏
			poCtrl1.setCaption(doc.getName());// 设置页面的显示标题
			OpenModeType modetype = null;
			if ("docx".equals(doc.getDocType()) || "doc".equals(doc.getDocType())) {
				modetype = OpenModeType.docReadOnly;
			} else if ("pptx".equals(doc.getDocType()) || "xls".equals(doc.getDocType())) {
				modetype = OpenModeType.xlsReadOnly;
			} else if ("pptx".equals(doc.getDocType()) || "ppt".equals(doc.getDocType())) {
				modetype = OpenModeType.pptReadOnly;
			}
			// 打开文件
			poCtrl1.webOpen(request.getContextPath() + "/oa/doc/doc/getDocFile.do?docPath=" + doc.getDocPath(), modetype, ContextUtil.getCurrentUser().getFullname());
			poCtrl1.setTagId("PageOfficeCtrl1"); // 此行必须
		}*/

		doc.getInfDocFiles().clear();
		doc.setInfDocFiles(null);
		doc.setUserId(ContextUtil.getCurrentUserId());
		String docPath=doc.getDocPath();
		String docType=doc.getDocType();
		return getPathView(request).addObject("doc", doc).addObject("file", map).addObject("docType", docType).addObject("docPath",docPath).addObject("canEdit", canEdit).addObject("canDel", canDel);
	}

	/**
	 * 利用listTree给子窗口传子文件以及文件夹
	 * 
	 * @param request
	 * @return 子文件夹里的docAndFolders
	 * @throws Exception
	 */
	@RequestMapping("listBox")
	@ResponseBody
	public List<DocAndFolder> getSubDocFolder(HttpServletRequest request) throws Exception {
		List<DocAndFolder> docAndFolders = listTree(request);
		// 暂时靠isShare是YES还是NO来判断是否共享文档，以后会变
		return docAndFolders;
	}

	/**
	 * 用key模糊匹配显示树中的节点们
	 * 
	 * @param request
	 * @return 匹配的docAndFolder的list集合
	 * @throws Exception
	 */
	@RequestMapping("getByKey")
	@ResponseBody
	public List<DocAndFolder> getByKey(HttpServletRequest request) throws Exception {
		String key = request.getParameter("key");//
		String folderId = request.getParameter("folderId");// 在
															// tomcat处理了编码为utf-8

		List<DocAndFolder> list = listTree(request);
		if (StringUtils.isNotEmpty(key)) {// 如果key存在则继续
			// 显示本级下的（子）文件以及文件夹
			int mylevel = docFolderManager.get(folderId).getDepth();
			for (int i = mylevel; i >= 0; i--) {// 同级的先按level从高到低排序
				for (int j = 0; j < list.size(); j++) {
					if (mylevel == list.get(j).getLevel()) {// 把同级的去掉
						list.remove(j);
						j--;
					}
				}
			}
			// 获取特定key的文件
			for (int i = 0; i < list.size(); i++) {
				if (!list.get(i).getName().contains(key)) {// 没有包含key
															// 的实体去除，只保留能匹配的
					list.remove(i);
					i--;
				}
			}
		}
		return list;
	}



	/**
	 * 获取
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getDocFile")
	public void getDocFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docPath = request.getParameter("docPath");
		// 创建file对象
		File file = new File(WebAppUtil.getAppAbsolutePath() + "/WEB-INF/docs/" + docPath);
		// 设置response的编码方式
		response.setContentType("application/x-msdownload");
		// 写明要下载的文件的大小
		// response.setContentLength((int) file.length());
		// response.setHeader("Content-Disposition", "attachment;filename=" +
		// sysFile.getFileName());
		FileUtil.downLoad(file, response);

		
	}

	/**
	 * 编辑页面，包括新建
	 * 
	 * @param request
	 * @param response
	 * @return返回edit.jsp
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String folderId = request.getParameter("folderId");
		String pkId = request.getParameter("pkId");
		String docType = request.getParameter("docType");
		ModelAndView mv = getPathView(request);
		StringBuffer readName = new StringBuffer();// 用于阅读权限的显示
		StringBuffer readId = new StringBuffer();// 用于阅读权限的Id
		StringBuffer editName = new StringBuffer();// 用于编辑权限的显示
		StringBuffer editId = new StringBuffer();// 用于编辑权限的Id
		StringBuffer delName = new StringBuffer();// 用于编辑权限的显示
		StringBuffer delId = new StringBuffer();// 用于编辑权限的Id

		Doc doc = null;
		if (StringUtils.isNotEmpty(pkId)) {// 如果传来的pkId不为空则按照Id获取doc实体
			doc = docManager.get(pkId);
			docType = doc.getDocType();
		} else {// 否则就新建Doc实体
			doc = new Doc();
			doc.setDocFolder(docFolderManager.get(folderId));
		}

		// 默认为Html文档
		if (StringUtils.isEmpty(docType)) {
			docType = Doc.DOC_HTML;
		}

		// 拼接字符串回显附件
		StringBuffer fileIdSb = new StringBuffer();
		StringBuffer fileNameSb = new StringBuffer();
		for (SysFile file : doc.getInfDocFiles()) {// 把每个doc附件都遍历并用逗号分隔拼接成ID和Name字符串用来显示
			fileIdSb.append(file.getFileId()).append(",");
			fileNameSb.append(file.getFileName()).append(",");
		}

		if (fileIdSb.length() > 0) {// 去除最后的逗号
			fileIdSb.deleteCharAt(fileIdSb.length() - 1);
			fileNameSb.deleteCharAt(fileNameSb.length() - 1);
		}
		doc.getInfDocFiles().clear();
		doc.setInfDocFiles(null);
		doc.setUserId(ContextUtil.getCurrentUserId());// 把当前USER设置进doc实体里
		if (StringUtils.isEmpty(doc.getIsShare())) {
			doc.setIsShare("NO");
		}

		if (!Doc.DOC_HTML.equals(docType)) {
			
		}

		// 拼接权限的回显
		// 取出该文档的所有个人阅读权限
		List<DocRight> readUserRights = docRightManager.getAllByDocIdRight(pkId, DocRight.IDENTITYTYPE_USER, DocRight.RIGHTS_READ);
		for (DocRight readUserRight : readUserRights) {
			readId.append(readUserRight.getIdentityId() + "_user");
			readId.append(",");
			readName.append(osUserManager.get(readUserRight.getIdentityId()).getFullname());
			readName.append(",");
		}
		// 取出该文档的所有组阅读权限
		List<DocRight> readGroupRights = docRightManager.getAllByDocIdRight(pkId, DocRight.IDENTITYTYPE_GROUP, DocRight.RIGHTS_READ);
		for (DocRight readGroupRight : readGroupRights) {
			readId.append(readGroupRight.getIdentityId() + "_group");
			readId.append(",");
			readName.append(osGroupManager.get(readGroupRight.getIdentityId()).getName());
			readName.append(",");
		}
		// 对阅读权限进行处理然后传去页面输出
		if (readId.length() > 0) {
			readName.deleteCharAt(readName.length() - 1);
			readId.deleteCharAt(readId.length() - 1);
		}
		mv.addObject("readName", readName.toString()).addObject("readId", readId.toString());
		
		// 取出该文档的所有个人编辑权限
		List<DocRight> editUserRights = docRightManager.getAllByDocIdRight(pkId, DocRight.IDENTITYTYPE_USER, DocRight.RIGHTS_EDIT);
		for (DocRight editUserRight : editUserRights) {
			editId.append(editUserRight.getIdentityId() + "_user");
			editId.append(",");
			editName.append(osUserManager.get(editUserRight.getIdentityId()).getFullname());
			editName.append(",");
		}
		// 取出该文档的所有组编辑权限
		List<DocRight> editGroupRights = docRightManager.getAllByDocIdRight(pkId, DocRight.IDENTITYTYPE_GROUP, DocRight.RIGHTS_EDIT);
		for (DocRight editGroupRight : editGroupRights) {
			editId.append(editGroupRight.getIdentityId() + "_group");
			editId.append(",");
			editName.append(osGroupManager.get(editGroupRight.getIdentityId()).getName());
			editName.append(",");
		}
		// 对编辑权限进行处理然后传去页面输出
		if (editId.length() > 0) {
			editName.deleteCharAt(editName.length() - 1);
			editId.deleteCharAt(editId.length() - 1);
		}
		mv.addObject("editName", editName.toString()).addObject("editId", editId.toString());
		
		// 取出该文档的所有个人删除权限
		List<DocRight> delUserRights = docRightManager.getAllByDocIdRight(pkId, DocRight.IDENTITYTYPE_USER, DocRight.RIGHTS_DEL);
		for (DocRight delUserRight : delUserRights) {
			delId.append(delUserRight.getIdentityId() + "_user");
			delId.append(",");
			delName.append(osUserManager.get(delUserRight.getIdentityId()).getFullname());
			delName.append(",");
		}
		// 取出该文档的所有组删除权限
		List<DocRight> delGroupRights = docRightManager.getAllByDocIdRight(pkId, DocRight.IDENTITYTYPE_GROUP, DocRight.RIGHTS_DEL);
		for (DocRight delGroupRight : delGroupRights) {
			delId.append(delGroupRight.getIdentityId() + "_group");
			delId.append(",");
			delName.append(osGroupManager.get(delGroupRight.getIdentityId()).getName());
			delName.append(",");
		}
		// 对删除权限进行处理然后传去页面输出
		if (delId.length() > 0) {
			delName.deleteCharAt(delName.length() - 1);
			delId.deleteCharAt(delId.length() - 1);
		}
		mv.addObject("delName", delName.toString()).addObject("delId", delId.toString());
		String docPath=doc.getDocPath();
		return mv.addObject("doc", doc).addObject("fileIds", fileIdSb.toString()).addObject("docPath",docPath).addObject("fileNames", fileNameSb.toString()).addObject("docType", docType);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return docManager;
	}

	/**
	 * 得到指定文件夹下的文件列表,前台jsp页面需要传入folderId multi(是否查找子集) type(公司还是个人)
	 * isShare(是否共享)
	 * 
	 * @param request
	 * @return 新构建的docAndFolders实体list
	 * @throws Exception
	 */
	@RequestMapping("listTree")
	@ResponseBody
	public List<DocAndFolder> listTree(HttpServletRequest request) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		String folderId = request.getParameter("folderId");
		String tenantId = ContextUtil.getCurrentTenantId();
		String multi = request.getParameter("multi");// 用来判断是否查找子集的字段0/1
		String type = request.getParameter("type");// 判断公司还是个人
		String isShare = request.getParameter("isShare");// 判断是否共享
		List<DocFolder> folderlist = new ArrayList<DocFolder>();
		List<Doc> list = new ArrayList<Doc>();
		if ("0".equals(multi)) {// 如果multi为0，则只查找文件夹下的东西，不继续遍历该节点以下的节点
			folderlist = docFolderManager.getSubFolder(folderId);
			list = docManager.getByFolderId(userId,DocRight.RIGHTS_READ, folderId, type);//(folderId,DocRight.RIGHTS_READ,type);
		} else {
			if (("undefined".equals(isShare)) || (isShare == null) || ("".equals(isShare))) {// 如果isShare是null或者undefine则查找
				folderlist = docFolderManager.getSpecialPathFolder(folderId, type);// 路径包含了folderId的文件夹,type选中公司或者个人
			} else {// 如果share字段有效的话则只查找共享文件夹
				folderlist = docFolderManager.getSpecialPathFolderWithShare(folderId, type, isShare, tenantId);// 添加包含Share字段的查询
			}
			// 获取应该展示的文件列表
			list = docManager.getAllByRgiht(userId, DocRight.RIGHTS_READ, folderId, type);
		}
		// 把自己除外，不显示自己
		for (int i = 0; i < folderlist.size(); i++) {
			if (folderId.equals(folderlist.get(i).getFolderId())) {
				folderlist.remove(i);
			}
		}

		List<DocAndFolder> docAndFolders = new ArrayList<DocAndFolder>();
		// 将文件夹添加进DocAndFoleder实体
		for (int i = 0; i < folderlist.size(); i++) {
			DocAndFolder docAndFolder = new DocAndFolder();
			docAndFolder.setDafId(folderlist.get(i).getFolderId());
			docAndFolder.setName(folderlist.get(i).getName());
			docAndFolder.setParent(folderlist.get(i).getParent());
			docAndFolder.setType("文件夹");
			docAndFolder.setCreateTime(folderlist.get(i).getCreateTime());
			docAndFolder.setCreateBy(folderlist.get(i).getCreateBy());
			docAndFolder.setCreateName(osUserManager.get(folderlist.get(i).getCreateBy()).getFullname());
			docAndFolder.setLevel(folderlist.get(i).getDepth());
			docAndFolders.add(docAndFolder);
		}
		// 将文件添加进DocAndFoleder实体
		for (int i = 0; i < list.size(); i++) {
			DocAndFolder docAndFolder2 = new DocAndFolder();
			docAndFolder2.setDafId(list.get(i).getDocId());
			docAndFolder2.setName(list.get(i).getName());
			docAndFolder2.setParent(list.get(i).getFolderId());
			docAndFolder2.setType(list.get(i).getDocType());
			docAndFolder2.setCreateTime(list.get(i).getCreateTime());
			docAndFolder2.setCreateBy(list.get(i).getCreateBy());
			docAndFolder2.setCreateName(osUserManager.get(list.get(i).getCreateBy()).getFullname());
			docAndFolder2.setLevel(list.get(i).getDocFolder().getDepth());
			docAndFolders.add(docAndFolder2);
		}

		return docAndFolders;
	}

	/**
	 * 移动文件到指定文件夹,前台jsp页面需要传入docId(要移动的文件Id) folderId(要移动到的目标文件夹)
	 * 
	 * @param request
	 * @param docFolder
	 * @param result
	 * @return
	 */
	@RequestMapping("move")
	@ResponseBody
	public JsonResult move(HttpServletRequest request) {
		String docId = request.getParameter("docId");
		String folderId = request.getParameter("folderId");
		Doc doc = docManager.get(docId);
		doc.setDocFolder(docFolderManager.get(folderId));
		docManager.update(doc);
		String msg = getMessage("doc.updated", new Object[] { doc.getName() }, "文件成功移动!");
		return new JsonResult(true, msg);

	}

	/**
	 * Portal门户中新闻Panel显示的List
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("portalList")
	@ResponseBody
	public JsonPageResult<Doc> portalList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		// queryFilter.setSelectJoinAtt("insNews");
		List dataList = docManager.getAll();
		return new JsonPageResult<Doc>(dataList, queryFilter.getPage().getTotalItems());
	}

}
