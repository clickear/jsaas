package com.redxun.kms.core.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.kms.core.entity.KdCoverImage;
import com.redxun.kms.core.entity.KdDoc;
import com.redxun.kms.core.entity.KdDocCmmt;
import com.redxun.kms.core.entity.KdDocCmmtShow;
import com.redxun.kms.core.entity.KdDocFav;
import com.redxun.kms.core.entity.KdDocHis;
import com.redxun.kms.core.entity.KdDocRead;
import com.redxun.kms.core.entity.KdDocRem;
import com.redxun.kms.core.entity.KdDocRemShow;
import com.redxun.kms.core.entity.KdDocRight;
import com.redxun.kms.core.entity.KdMapNote;
import com.redxun.kms.core.entity.KdMenu;
import com.redxun.kms.core.entity.KdQuestion;
import com.redxun.kms.core.entity.KdUser;
import com.redxun.kms.core.manager.KdDocCmmtManager;
import com.redxun.kms.core.manager.KdDocFavManager;
import com.redxun.kms.core.manager.KdDocHisManager;
import com.redxun.kms.core.manager.KdDocManager;
import com.redxun.kms.core.manager.KdDocReadManager;
import com.redxun.kms.core.manager.KdDocRemManager;
import com.redxun.kms.core.manager.KdDocRightManager;
import com.redxun.kms.core.manager.KdQuestionManager;
import com.redxun.kms.core.manager.KdUserManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysReport;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 文档控制器管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDoc/")
public class KdDocController extends TenantListController {
	@Resource
	KdDocManager kdDocManager;
	@Resource
	SysTreeManager sysTreeManager;
	@Resource
	OsGroupManager osGroupManager;
	@Resource
	OsUserManager osUserManager;
	@Resource
	KdDocCmmtManager kdDocCmmtManager;
	@Resource
	KdDocRightManager kdDocRightManager;
	@Resource
	KdDocRemManager kdDocRemManager;
	@Resource
	KdDocReadManager kdDocReadManager;
	@Resource
	InfInnerMsgManager infInnerMsgManager;
	@Resource
	KdDocFavManager kdDocFavManager;
	@Resource
	KdDocHisManager kdDocHisManager;
	@Resource
	KdQuestionManager kdQuestionManager;
	@Resource
	KdUserManager kdUserManager;
	@Resource
	SysFileManager sysFileManager;
	

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				kdDocManager.delete(id);
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
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		KdDoc kdDoc = null;
		if (StringUtils.isNotEmpty(pkId)) {
			kdDoc = kdDocManager.get(pkId);
		} else {
			kdDoc = new KdDoc();
		}
		return getPathView(request).addObject("kdDoc", kdDoc);
	}

	/**
	 * 编辑页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		String userName = ContextUtil.getCurrentUser().getFullname();
		ModelAndView mv = getPathView(request);
		KdDoc kdDoc = null;
		String jobName = null;// 职位名
		String depName = null;// 部门名
		StringBuffer readName = new StringBuffer();// 用于阅读权限的显示
		StringBuffer readId = new StringBuffer();// 用于阅读权限的Id
		StringBuffer editName = new StringBuffer();// 用于编辑权限的显示
		StringBuffer editId = new StringBuffer();// 用于编辑权限的Id
		StringBuffer downName = new StringBuffer();// 用于编辑权限的显示
		StringBuffer downId = new StringBuffer();// 用于编辑权限的Id

		if (StringUtils.isNotEmpty(pkId)) {
			kdDoc = kdDocManager.get(pkId);
			if ("0".equals(kdDoc.getAttFileids())) {// 如果没有附件，则设为空值
				kdDoc.setAttFileids("");
			}
			if (StringUtils.isNotBlank(kdDoc.getAuthorPos())) {// 如果是外部就没有岗位职位
				jobName = osGroupManager.get(kdDoc.getAuthorPos()).getName();
				depName = osGroupManager.get(kdDoc.getBelongDepid()).getName();
			}
			// 取出该文档的所有个人阅读权限
			List<KdDocRight> readUserRights = kdDocRightManager.getAllByDocIdRight(pkId, KdDocRight.IDENTITYTYPE_USER, KdDocRight.RIGHT_READ);
			for (KdDocRight readUserRight : readUserRights) {
				readId.append(readUserRight.getIdentityId() + "_user");
				readId.append(",");
				readName.append(osUserManager.get(readUserRight.getIdentityId()).getFullname());
				readName.append(",");
			}
			// 取出该文档的所有组阅读权限
			List<KdDocRight> readGroupRights = kdDocRightManager.getAllByDocIdRight(pkId, KdDocRight.IDENTITYTYPE_GROUP, KdDocRight.RIGHT_READ);
			for (KdDocRight readGroupRight : readGroupRights) {
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
			List<KdDocRight> editUserRights = kdDocRightManager.getAllByDocIdRight(pkId, KdDocRight.IDENTITYTYPE_USER, KdDocRight.RIGHT_EDIT);
			for (KdDocRight editUserRight : editUserRights) {
				editId.append(editUserRight.getIdentityId() + "_user");
				editId.append(",");
				editName.append(osUserManager.get(editUserRight.getIdentityId()).getFullname());
				editName.append(",");
			}
			// 取出该文档的所有组编辑权限
			List<KdDocRight> editGroupRights = kdDocRightManager.getAllByDocIdRight(pkId, KdDocRight.IDENTITYTYPE_GROUP, KdDocRight.RIGHT_EDIT);
			for (KdDocRight editGroupRight : editGroupRights) {
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

			// 取出该文档的所有个人下载权限
			List<KdDocRight> downUserRights = kdDocRightManager.getAllByDocIdRight(pkId, KdDocRight.IDENTITYTYPE_USER, KdDocRight.RIGHT_EDIT);
			for (KdDocRight downUserRight : downUserRights) {
				downId.append(downUserRight.getIdentityId() + "_user");
				downId.append(",");
				downName.append(osUserManager.get(downUserRight.getIdentityId()).getFullname());
				downName.append(",");
			}
			// 取出该文档的所有组下载权限
			List<KdDocRight> downGroupRights = kdDocRightManager.getAllByDocIdRight(pkId, KdDocRight.IDENTITYTYPE_GROUP, KdDocRight.RIGHT_EDIT);
			for (KdDocRight downGroupRight : downGroupRights) {
				downId.append(downGroupRight.getIdentityId() + "_group");
				downId.append(",");
				downName.append(osGroupManager.get(downGroupRight.getIdentityId()).getName());
				downName.append(",");
			}
			// 对下载权限进行处理然后传去页面输出
			if (downId.length() > 0) {
				downName.deleteCharAt(downName.length() - 1);
				downId.deleteCharAt(downId.length() - 1);
			}
			mv.addObject("downName", downName.toString()).addObject("downId", downId.toString());

		} else {
			kdDoc = new KdDoc();
			return new ModelAndView("/kms/core/kdDocNew1.jsp").addObject("kdDoc", kdDoc);
		}
		return mv.addObject("kdDoc", kdDoc).addObject("jobName", jobName).addObject("depName", depName).addObject("userName", userName);
	}

	/**
	 * 知识仓库首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		List<KdMenu> leftMenu = getLeftMenu("CAT_KMS_KDDOC");
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);

		// 最新Doc
		List<KdCoverImage> newestDoc = new ArrayList<KdCoverImage>();
		List<KdDoc> newdocs = kdDocManager.getByNewest(userId, "KD_DOC", queryFilter);
		for (KdDoc doc : newdocs) {
			KdCoverImage image = new KdCoverImage();
			image.setDocId(doc.getDocId());
			image.setTitle(doc.getSubject());
			image.setPicUrl("/sys/core/file/imageView.do?fileId=" + doc.getCoverImgId());
			image.setJumpUrl("/kms/core/kdDoc/show.do?docId=" + doc.getDocId());
			image.setSummary(doc.getSummary());
			newestDoc.add(image);
		}

		// 最热Doc
		List<KdCoverImage> hotestDoc = new ArrayList<KdCoverImage>();
		List<KdDoc> hotdocs = kdDocManager.getByHotest(userId, "KD_DOC", queryFilter);
		for (KdDoc doc : hotdocs) {
			KdCoverImage image = new KdCoverImage();
			image.setDocId(doc.getDocId());
			image.setTitle(doc.getSubject());
			image.setPicUrl("/sys/core/file/imageView.do?fileId=" + doc.getCoverImgId());
			image.setJumpUrl("/kms/core/kdDoc/show.do?docId=" + doc.getDocId());
			image.setSummary(doc.getSummary());
			hotestDoc.add(image);
		}

		// 精华Doc
		List<KdCoverImage> essenceDoc = new ArrayList<KdCoverImage>();
		List<KdDoc> essenceDocs = kdDocManager.getByEssence(userId);
		for (KdDoc doc : essenceDocs) {
			KdCoverImage image = new KdCoverImage();
			image.setDocId(doc.getDocId());
			image.setTitle(doc.getSubject());
			image.setPicUrl("/sys/core/file/imageView.do?fileId=" + doc.getCoverImgId());
			image.setJumpUrl("/kms/core/kdDoc/show.do?docId=" + doc.getDocId());
			image.setSummary(doc.getSummary());
			essenceDoc.add(image);
		}
		return getPathView(request).addObject("leftMenu", iJson.toJson(leftMenu)).addObject("newestDoc", newestDoc).addObject("hotestDoc", hotestDoc).addObject("essenceDoc", essenceDoc);
	}

	/**
	 * 知识仓库搜索
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("search")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catId = request.getParameter("catId");
		String name = "";
		if (StringUtils.isNotEmpty(catId)) {
			SysTree sysTree = sysTreeManager.get(catId);
			String[] paths = {};
			if (StringUtils.isNotEmpty(sysTree.getPath())) {
				paths = sysTree.getPath().split("\\.");
			}

			for (int i = 0; i < paths.length; i++) {
				if (i == 0) {
					name = "<a class='cat' id='" + sysTreeManager.get(paths[i]).getTreeId() + "'>" + sysTreeManager.get(paths[i]).getName() + "</a>";
					continue;
				}
				name = name + ">" + "<a class='cat' id='" + sysTreeManager.get(paths[i]).getTreeId() + "'>" + sysTreeManager.get(paths[i]).getName() + "</a>";
			}
		}

		List<KdMenu> leftMenu = getLeftMenu("CAT_KMS_KDDOC");
		return this.getPathView(request).addObject("leftMenu", iJson.toJson(leftMenu)).addObject("name", name).addObject("catId", catId);
	}

	/**
	 * 选择分类的选择框页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchType")
	@ResponseBody
	public List<SysTree> searchType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docTypeId = request.getParameter("docTypeId");
		String path = null;
		String tenantId = getCurTenantId(request);
		// 优先使用页面参数中指定显示的维度下的用户组
		if (StringUtils.isNotEmpty(docTypeId)) {// 当点击维度树时显示的用户组
			path = sysTreeManager.get(docTypeId).getPath();
		} else {
			path = "";
		}
		String name = request.getParameter("name");
		String key = request.getParameter("key");
		List<SysTree> docs = sysTreeManager.getByTreeIdNameKey(tenantId, path, name, key);
		return docs;
	}

	/**
	 * 左边目录的构造
	 * 
	 * @return
	 */
	public List<KdMenu> getLeftMenu(String key) {
		String tenantId = ContextUtil.getCurrentTenantId();
		// StringBuffer sb = new StringBuffer();
		// sb.append("<div class='sidebar'><div id='allSearch' class='sidebar_top sidebar_top_tc'>分类导航</div><div class='sidebar_con'><dl class='sidebar_item'>");
		// sb.append("<div class='sidebar'><div id='allSearch' class='sidebar_top sidebar_top_tc'>知识导航</div><div class='sidebar_con'><dl class='sidebar_item'>");

		List<SysTree> trees = sysTreeManager.getMainMenu(key, tenantId);
		List<KdMenu> menus = new ArrayList<KdMenu>();
		for (SysTree tree : trees) {
			// sb.append("<dd><h3 class=''><a class='cat' id='" +
			// tree.getTreeId() + "' href='#'>" + tree.getName() +
			// "</a></h3><s></s><div class='sidebar_popup dis3' style='display: none;'><div class='sidebar_popup_class clearfix'>");
			KdMenu menu = new KdMenu();
			List<KdMenu> secondMenus = new ArrayList<KdMenu>();
			menu.setId(tree.getTreeId());
			menu.setName(tree.getName());
			List<SysTree> secondTrees = sysTreeManager.getByParentIdCatKey(tree.getTreeId(), key, tenantId);
			for (SysTree secondTree : secondTrees) {
				// sb.append("<div class='sidebar_popup_item'> <strong><a class='cat' id='"
				// + secondTree.getTreeId() + "' href='#'>" +
				// secondTree.getName() + "</a></strong><p>");
				KdMenu secondMenu = new KdMenu();
				List<KdMenu> thirdMenus = new ArrayList<KdMenu>();
				secondMenu.setId(secondTree.getTreeId());
				secondMenu.setName(secondTree.getName());
				List<SysTree> thirdTrees = sysTreeManager.getByParentIdCatKey(secondTree.getTreeId(), key, tenantId);
				for (SysTree thirdTree : thirdTrees) {
					KdMenu thirdMenu = new KdMenu();
					thirdMenu.setId(thirdTree.getTreeId());
					thirdMenu.setName(thirdTree.getName());
					thirdMenus.add(thirdMenu);
					// sb.append("<span class='linesbg'><a class='cat' id='" +
					// thirdTree.getTreeId() + "' href='#'>" +
					// thirdTree.getName() + "</a></span>");
				}
				secondMenu.setLowerMenu(thirdMenus);
				secondMenus.add(secondMenu);
				// sb.append("</p></div>");
			}
			menu.setLowerMenu(secondMenus);
			menus.add(menu);
			// sb.append("</div><div class='sidebar_popup_all'><a href='#'><span class='linesbg'>查看全部"
			// + tree.getName() + "</span></a></div></div></dd>");
		}
		// sb.append("</dl></div><div class='sidebar_bottom'></div></div>");
		return menus;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return kdDocManager;
	}

	/**
	 * 当没有分类tree的时候右边加载一个空的grid防止报错
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("listBlank")
	@ResponseBody
	public List<SysReport> listBlank(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	/**
	 * 第一步new1页面的显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("new1")
	public ModelAndView new1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userName = ContextUtil.getCurrentUser().getFullname();
		Date nowTime = new Date();
		String tenantId = ContextUtil.getCurrentTenantId();
		KdDoc kdDoc = null;
		String jobName = null;
		String depName = null;
		String treeType = null;
		if (StringUtils.isNotEmpty(docId)) {
			kdDoc = kdDocManager.get(docId);
			if (StringUtils.isNotBlank(kdDoc.getAuthorPos())) {// 如果是外部就没有岗位职位
				jobName = osGroupManager.get(kdDoc.getAuthorPos()).getName();
				depName = osGroupManager.get(kdDoc.getBelongDepid()).getName();
			}
			treeType = kdDoc.getSysTree().getName();
		} else {
			kdDoc = new KdDoc();
		}
		return getPathView(request).addObject("kdDoc", kdDoc).addObject("jobName", jobName).addObject("depName", depName).addObject("treeType", treeType).addObject("userName", userName).addObject("nowTime", nowTime).addObject("tenantId", tenantId);
	}

	/**
	 * 第二步new2页面的显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("new2")
	public ModelAndView new2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userName = ContextUtil.getCurrentUser().getFullname();
		KdDoc kdDoc = null;
		StringBuffer fileNames = new StringBuffer();
		if (StringUtils.isNotEmpty(docId)) {
			kdDoc = kdDocManager.get(docId);
			if (StringUtils.isNotEmpty(kdDoc.getAttFileids())) {
				String[] fileNameArray = kdDoc.getAttFileids().split(",");
				for (int i = 0; i < fileNameArray.length; i++) {
					if ("0".equals(fileNameArray[i])) {
						break;
					}
					fileNames.append(sysFileManager.get(fileNameArray[i]).getFileName() + ",");
				}
				if (fileNames.length() > 0) {
					fileNames.deleteCharAt(fileNames.length() - 1);
				}
			}
		} else {
			kdDoc = new KdDoc();
			return new ModelAndView("/kms/core/kdDocNew1.jsp").addObject("kdDoc", kdDoc);
		}
		return getPathView(request).addObject("kdDoc", kdDoc).addObject("userName", userName).addObject("fileNames", fileNames.toString());
	}

	/**
	 * 第三步new3页面的显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("new3")
	public ModelAndView new3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userName = ContextUtil.getCurrentUser().getFullname();
		KdDoc kdDoc = null;
		if (StringUtils.isNotEmpty(docId)) {
			kdDoc = kdDocManager.get(docId);

		} else {
			kdDoc = new KdDoc();
			return new ModelAndView("/kms/core/kdDocNew1.jsp").addObject("kdDoc", kdDoc);
		}
		if (kdDoc.getStorePeroid() == null) {
			kdDoc.setStorePeroid(1);
		}
		return getPathView(request).addObject("kdDoc", kdDoc).addObject("userName", userName);
	}

	/**
	 * 第四步new4页面的显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("new4")
	public ModelAndView new4(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userName = ContextUtil.getCurrentUser().getFullname();
		KdDoc kdDoc = null;
		if (StringUtils.isNotEmpty(docId)) {
			kdDoc = kdDocManager.get(docId);
		} else {
			kdDoc = new KdDoc();
			return new ModelAndView("/kms/core/kdDocNew1.jsp").addObject("kdDoc", kdDoc).addObject("userName", userName);
		}
		return getPathView(request).addObject("kdDoc", kdDoc).addObject("userName", userName);
	}

	/**
	 * 知识页面的显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("show")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userId = ContextUtil.getCurrentUserId();
		// 判断是否有这个权限
		if (!showRight(userId, docId)) {
			return new ModelAndView("/kms/core/kdDocError.jsp");
		}

		boolean isCmmt = false;
		String createName = null;
		String updateName = null;
		StringBuffer treeType = new StringBuffer();
		KdDoc kdDoc = null;
		if (StringUtils.isNotEmpty(docId)) {
			if (kdDocManager.getIsAlive(docId, "KD_DOC")) {// 判断是否文档还在，如果不在跳转去错误页面
				kdDoc = kdDocManager.get(docId);
				createName = osUserManager.get(kdDoc.getCreateBy()).getFullname();
				if (StringUtils.isNotBlank(kdDoc.getUpdateBy())) {
					updateName = osUserManager.get(kdDoc.getUpdateBy()).getFullname();
				}
				String[] trees = kdDoc.getSysTree().getPath().split("\\.");
				// treeType.append("首页 > 知识仓库");
				for (int i = 0; i < trees.length; i++) {
					if (i == trees.length - 1) {
						treeType.append(sysTreeManager.get(trees[i]).getName());
					} else {
						treeType.append(sysTreeManager.get(trees[i]).getName() + " > ");
					}
				}
				if (kdDocCmmtManager.getIsByDocId(docId, userId)) {// 判断是否已经评论了
					isCmmt = true;
				}
			} else {
				return new ModelAndView("/kms/core/kdDocError.jsp");
			}
		} else {
			kdDoc = new KdDoc();
		}

		// 构造点评
		List<KdDocCmmt> cmmts = kdDocCmmtManager.getByDocId(docId);
		List<KdDocCmmtShow> cmmtShows = new ArrayList<KdDocCmmtShow>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (KdDocCmmt cmmt : cmmts) {
			KdDocCmmtShow cmmtShow = new KdDocCmmtShow();
			cmmtShow.setCmmtName(osUserManager.get(cmmt.getCreateBy()).getFullname());
			cmmtShow.setContent(cmmt.getContent());
			cmmtShow.setKdDoc(cmmt.getKdDoc());
			cmmtShow.setLevel(cmmt.getLevel());
			cmmtShow.setScore(cmmt.getScore());
			cmmtShow.setCmmtTime(formatter.format(cmmt.getCreateTime()));
			cmmtShows.add(cmmtShow);
		}

		// 构造推荐
		List<KdDocRem> rems = kdDocRemManager.getByDocId(docId);
		List<KdDocRemShow> remShows = new ArrayList<KdDocRemShow>();
		for (KdDocRem rem : rems) {
			KdDocRemShow remShow = new KdDocRemShow();
			remShow.setRemName(osUserManager.get(rem.getCreateBy()).getFullname());
			remShow.setContent(rem.getMemo());
			remShow.setKdDoc(rem.getKdDoc());
			remShow.setScore(rem.getLevel());
			remShow.setRemTime(formatter.format(rem.getCreateTime()));
			if (StringUtils.isNotBlank(rem.getDepId()) && osGroupManager.get(rem.getDepId()) != null) {// 组不为空
				remShow.setRemTarget(osGroupManager.get(rem.getDepId()).getName());
			} else if (StringUtils.isNotBlank(rem.getUserId()) && osUserManager.get(rem.getUserId()) != null) {// 人不为空
				remShow.setRemTarget(osUserManager.get(rem.getUserId()).getFullname());
			}
			remShows.add(remShow);
		}
		// 判断是否可以编辑
		boolean isEdit = false;
		if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_USER, userId, KdDocRight.RIGHT_EDIT)) {
			isEdit = true;
		} else if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_ALL, KdDocRight.IDENTITYID_ALL, KdDocRight.RIGHT_EDIT)) {
			isEdit = true;
		}
		// 平均分
		float score = getAvgScore(docId);
		// 增加阅读次数
		int readTimes = kdDoc.getViewTimes();
		readTimes = readTimes + 1;
		kdDoc.setViewTimes(readTimes);
		kdDocManager.updateSkipUpdateTime(kdDoc);
		// 添加阅读名单
		KdDocRead kdRead = new KdDocRead();
		kdRead.setKdDoc(kdDoc);
		kdRead.setDocStatus(kdDoc.getStatus());
		kdRead.setReadId(idGenerator.getSID());
		kdDocReadManager.create(kdRead);
		// 构造附件
		Map<String, String> map = new TreeMap<String, String>();// 构建map存放对应的附件
		if (!"0".equals(kdDoc.getAttFileids())) {
			String[] files = kdDoc.getAttFileids().split(",");
			for (String file : files) {
				map.put(sysFileManager.get(file).getFileId(), sysFileManager.get(file).getFileName());// 遍历每个doc实体的附件，放入map里
			}
		}

		return getPathView(request).addObject("kdDoc", kdDoc).addObject("createName", createName).addObject("updateName", updateName).addObject("treeType", treeType.toString()).addObject("isCmmt", isCmmt).addObject("score", score).addObject("cmmts", iJson.toJson(cmmtShows)).addObject("rems", iJson.toJson(remShows)).addObject("isEdit", isEdit).addObject("file", map);
	}

	/**
	 * 知识页面的显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("hisShow")
	public ModelAndView hisShow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String hisId = request.getParameter("hisId");
		String userId = ContextUtil.getCurrentUserId();

		KdDocHis his = kdDocHisManager.get(hisId);
		KdDoc kdDoc = his.getKdDoc();
		kdDoc.setSubject(his.getSubject());
		kdDoc.setContent(his.getContent());

		// 判断是否有这个权限
		if (!showRight(userId, kdDoc.getDocId())) {
			return new ModelAndView("/kms/core/kdDocError.jsp");
		}

		boolean isHis = true;
		boolean isCmmt = false;
		String createName = null;
		String updateName = null;
		StringBuffer treeType = new StringBuffer();
		if (StringUtils.isNotEmpty(hisId)) {
			if (kdDocManager.getIsAlive(kdDoc.getDocId(), "KD_DOC")) {// 判断是否文档还在，如果不在跳转去错误页面
				createName = osUserManager.get(kdDoc.getCreateBy()).getFullname();
				updateName = osUserManager.get(kdDoc.getUpdateBy()).getFullname();
				String[] trees = kdDoc.getSysTree().getPath().split("\\.");
				// treeType.append("首页 > 知识仓库");
				for (int i = 0; i < trees.length; i++) {
					if (i == trees.length - 1) {
						treeType.append(sysTreeManager.get(trees[i]).getName());
					} else {
						treeType.append(sysTreeManager.get(trees[i]).getName() + " > ");
					}
				}
				if (kdDocCmmtManager.getIsByDocId(kdDoc.getDocId(), userId)) {// 判断是否已经评论了
					isCmmt = true;
				}
			} else {
				return new ModelAndView("/kms/core/kdDocError.jsp");
			}
		} else {
			kdDoc = new KdDoc();
		}

		// 构造点评
		List<KdDocCmmt> cmmts = kdDocCmmtManager.getByDocId(kdDoc.getDocId());
		List<KdDocCmmtShow> cmmtShows = new ArrayList<KdDocCmmtShow>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (KdDocCmmt cmmt : cmmts) {
			KdDocCmmtShow cmmtShow = new KdDocCmmtShow();
			cmmtShow.setCmmtName(osUserManager.get(cmmt.getCreateBy()).getFullname());
			cmmtShow.setContent(cmmt.getContent());
			cmmtShow.setKdDoc(cmmt.getKdDoc());
			cmmtShow.setLevel(cmmt.getLevel());
			cmmtShow.setScore(cmmt.getScore());
			cmmtShow.setCmmtTime(formatter.format(cmmt.getCreateTime()));
			cmmtShows.add(cmmtShow);
		}

		// 构造推荐
		List<KdDocRem> rems = kdDocRemManager.getByDocId(kdDoc.getDocId());
		List<KdDocRemShow> remShows = new ArrayList<KdDocRemShow>();
		for (KdDocRem rem : rems) {
			KdDocRemShow remShow = new KdDocRemShow();
			remShow.setRemName(osUserManager.get(rem.getCreateBy()).getFullname());
			remShow.setContent(rem.getMemo());
			remShow.setKdDoc(rem.getKdDoc());
			remShow.setScore(rem.getLevel());
			remShow.setRemTime(formatter.format(rem.getCreateTime()));
			if (StringUtils.isNotBlank(rem.getDepId())) {// 组Id不为空
				remShow.setRemTarget(osGroupManager.get(rem.getDepId()).getName());
			} else if (StringUtils.isNotBlank(rem.getUserId())) {// 人Id不为空
				remShow.setRemTarget(osUserManager.get(rem.getUserId()).getFullname());
			}
			remShows.add(remShow);
		}
		// 判断是否可以编辑
		boolean isEdit = true;
		if (kdDocRightManager.getByDocIdIdentityIdRight(kdDoc.getDocId(), KdDocRight.IDENTITYTYPE_USER, userId, KdDocRight.RIGHT_EDIT)) {
			isEdit = false;
		} else if (kdDocRightManager.getByDocIdIdentityIdRight(kdDoc.getDocId(), KdDocRight.IDENTITYTYPE_ALL, KdDocRight.IDENTITYID_ALL, KdDocRight.RIGHT_EDIT)) {
			isEdit = false;
		}
		// 平均分
		float score = getAvgScore(kdDoc.getDocId());
		// 增加阅读次数
		int readTimes = kdDoc.getViewTimes();
		readTimes = readTimes + 1;
		kdDoc.setViewTimes(readTimes);
		kdDocManager.update(kdDoc);
		// 添加阅读名单
		KdDocRead kdRead = new KdDocRead();
		kdRead.setKdDoc(kdDoc);
		kdRead.setDocStatus(kdDoc.getStatus());
		kdRead.setReadId(idGenerator.getSID());
		kdDocReadManager.create(kdRead);
		return new ModelAndView("/kms/core/kdDocShow.jsp").addObject("kdDoc", kdDoc).addObject("createName", createName).addObject("updateName", updateName).addObject("treeType", treeType.toString()).addObject("isCmmt", isCmmt).addObject("isHis", isHis).addObject("score", score).addObject("cmmts", iJson.toJson(cmmtShows)).addObject("rems", iJson.toJson(remShows)).addObject("isEdit", isEdit);
	}

	/**
	 * 根据分类搜索个人能看的doc
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("getByPath")
	@ResponseBody
	public JsonPageResult<KdDoc> getByPath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catId = request.getParameter("catId");
		if (StringUtils.isNotEmpty(catId)) {
			catId = sysTreeManager.get(catId).getPath();
		}
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<KdDoc> docs = kdDocManager.getAllByRgiht(userId, "KD_DOC", KdDocRight.RIGHT_READ, catId, queryFilter);
		// List<KdDoc> docs = kdDocManager.getAll(queryFilter);
		JsonPageResult<KdDoc> docList = new JsonPageResult<KdDoc>(docs, queryFilter.getPage().getTotalItems());
		return docList;
	}

	/**
	 * 后台根据分类搜索所有doc
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("getAllByPath")
	@ResponseBody
	public JsonPageResult<KdDoc> getAllByPath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catId = request.getParameter("catId");
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<KdDoc> docs = kdDocManager.getByPath(sysTreeManager.get(catId).getPath(), "KD_DOC", queryFilter);
		for (KdDoc doc : docs) {
			if (kdDocRemManager.getByIsRem(doc.getDocId(), "root")) {
				doc.setIsPortalPic("YES");
			} else {
				doc.setIsPortalPic("NO");
			}
		}
		JsonPageResult<KdDoc> docList = new JsonPageResult<KdDoc>(docs, queryFilter.getPage().getTotalItems());
		return docList;
	}

	/**
	 * 个人中心搜索doc
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("personalSearchDoc")
	@ResponseBody
	public JsonPageResult<KdDoc> personalSearchDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		Map<String, QueryParam> queryParams = queryFilter.getQueryParams();
		List<KdDoc> docs = new ArrayList<KdDoc>();
		// 是否有查询状态
		if (queryParams.get("Type") != null) {
			if ("myDoc".equals(queryParams.get("Type").getValue())) {
				docs = kdDocManager.getMyDoc(userId, "KD_DOC", queryFilter);
			} else if ("myCmmt".equals(queryParams.get("Type").getValue())) {
				docs = kdDocManager.getDocByCmmt(userId, "KD_DOC", queryFilter);
			} else if ("myEdit".equals(queryParams.get("Type").getValue())) {
				docs = kdDocManager.getAllPersonalDoc(userId, "KD_DOC", KdDocRight.RIGHT_EDIT, queryFilter);
			} else if ("myFav".equals(queryParams.get("Type").getValue())) {
				docs = kdDocManager.getMyFav(userId, "KD_DOC", queryFilter);
			}
		} else {
			docs = kdDocManager.getAllPersonalDoc(userId, "KD_DOC", KdDocRight.RIGHT_READ, queryFilter);
		}
		JsonPageResult<KdDoc> docList = new JsonPageResult<KdDoc>(docs, queryFilter.getPage().getTotalItems());
		return docList;
	}

	/**
	 * 根据条件搜索doc
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByAddition")
	@ResponseBody
	public JsonPageResult<KdDoc> getByAddition(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catId = request.getParameter("catId");
		if (StringUtils.isNotEmpty(catId)) {
			catId = sysTreeManager.get(catId).getPath();
		}
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<KdDoc> docs = kdDocManager.getAllByRgiht(userId, "KD_DOC", KdDocRight.RIGHT_READ, catId, queryFilter);
		JsonPageResult<KdDoc> result = new JsonPageResult<KdDoc>(docs, queryFilter.getPage().getTotalItems());
		return result;
	}

	/**
	 * 搜索所有符合权限的doc
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllDoc")
	@ResponseBody
	public JsonPageResult<KdDoc> getAllDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catId = request.getParameter("catId");
		if (StringUtils.isNotEmpty(catId)) {
			catId = sysTreeManager.get(catId).getPath();
		}
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		String userId = ContextUtil.getCurrentUserId();
		List<KdDoc> docs = kdDocManager.getAllByRgiht(userId, "KD_DOC", KdDocRight.RIGHT_READ, catId, queryFilter);
		JsonPageResult<KdDoc> result = new JsonPageResult<KdDoc>(docs, queryFilter.getPage().getTotalItems());
		return result;
	}

	/**
	 * 设置点评
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("sendCommt")
	@ResponseBody
	public JsonResult getSendCommt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String content = request.getParameter("content");
		int score = Integer.parseInt(request.getParameter("score"));
		String level = request.getParameter("level");
		String isNotice = request.getParameter("isNotice");
		String ctxPath=request.getContextPath();

		KdDoc kdDoc = kdDocManager.get(docId);
		KdDocCmmt docCmmt = new KdDocCmmt();
		docCmmt.setCommentId(idGenerator.getSID());
		docCmmt.setContent(content);
		docCmmt.setLevel(level);
		docCmmt.setScore(score);
		docCmmt.setKdDoc(kdDoc);
		kdDocCmmtManager.create(docCmmt);
		kdDoc.setCompScore(getAvgScore(docId));
		kdDocManager.update(kdDoc);

		if ("true".equals(isNotice)) {
			String msg="您创建的文档【" + kdDoc.getSubject() + "】有新的点评，请查看！";
			String linkMsg=KdUtil.getLink(request, kdDoc);
			infInnerMsgManager.sendMessage(kdDoc.getCreateBy(), "", msg, linkMsg,true);
		}
		return new JsonResult(true, "成功点评！");
	}

	/**
	 * 发布，只有编辑状态才有
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("publish")
	@ResponseBody
	public JsonResult publish(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		// SimpleDateFormat formatter = new SimpleDateFormat
		// ("yyyy-MM-dd HH:mm");
		KdDoc kdDoc = kdDocManager.get(docId);
		kdDoc.setStatus(KdDoc.STATUS_ISSUED);
		kdDoc.setIssuedTime(new Date());
		kdDocManager.update(kdDoc);
		return new JsonResult(true, "成功发布！");
	}

	/**
	 * 收藏
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("favorite")
	@ResponseBody
	public JsonResult favorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userId = ContextUtil.getCurrentUserId();

		if (kdDocFavManager.getIsFav(userId, docId)) {
			return new JsonResult(true, "您已经收藏过了!");
		}
		KdDoc kdDoc = kdDocManager.get(docId);
		KdDocFav docFav = new KdDocFav();
		docFav.setKdDoc(kdDoc);
		docFav.setFavId(idGenerator.getSID());
		kdDocFavManager.create(docFav);
		return new JsonResult(true, "成功收藏！");
	}

	/**
	 * 新版本
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("newVersion")
	@ResponseBody
	public void newVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		KdDoc kdDoc = kdDocManager.get(docId);
		KdDocHis his = new KdDocHis();
		int version = kdDoc.getVersion();
		his.setHisId(idGenerator.getSID());
		his.setKdDoc(kdDoc);
		his.setAuthor(kdDoc.getAuthor());
		his.setAttachIds(kdDoc.getAttFileids());
		his.setContent(kdDoc.getContent());
		his.setCoverFileId(kdDoc.getCoverImgId());
		his.setVersion(kdDoc.getVersion());
		his.setSubject(kdDoc.getSubject());
		kdDocHisManager.create(his);

		kdDoc.setVersion(version + 1);
		kdDoc.setStatus(KdDoc.STATUS_DRAFT);
		kdDocManager.update(kdDoc);
	}

	/**
	 * 设为精华
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("essence")
	@ResponseBody
	public JsonResult essence(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String essence = request.getParameter("essence");
		KdDoc kdDoc = kdDocManager.get(docId);
		kdDoc.setIsEssence(essence);
		kdDocManager.update(kdDoc);
		if ("YES".equals(essence)) {
			return new JsonResult(true, "已设为精华");
		} else {
			return new JsonResult(true, "已取消精华");
		}

	}

	/**
	 * 获得点评平均分
	 * 
	 * @param docId
	 * @return
	 */
	public float getAvgScore(String docId) {
		List<KdDocCmmt> cmmts = kdDocCmmtManager.getByDocId(docId);
		float i = 0;
		for (KdDocCmmt cmmt : cmmts) {
			i = i + cmmt.getScore();
		}
		i = i / cmmts.size();
		return i;
	}

	/**
	 * 地图页面的显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mapShow")
	public ModelAndView mapShow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userId = ContextUtil.getCurrentUserId();
		// 判断是否有这个权限
		if (!showRight(userId, docId)) {
			return new ModelAndView("/kms/core/kdDocError.jsp");
		}

		boolean isCmmt = false;
		String createName = null;
		String updateName = null;
		StringBuffer treeType = new StringBuffer();
		KdDoc kdDoc = null;
		if (StringUtils.isNotEmpty(docId)) {
			if (kdDocManager.getIsAlive(docId, "KD_MAP")) {// 判断是否文档还在，如果不在跳转去错误页面
				kdDoc = kdDocManager.get(docId);
				createName = osUserManager.get(kdDoc.getCreateBy()).getFullname();
				updateName = osUserManager.get(kdDoc.getUpdateBy()).getFullname();
				String[] trees = kdDoc.getSysTree().getPath().split("\\.");
				// treeType.append("首页 > 知识仓库");
				for (int i = 0; i < trees.length; i++) {
					if (i == trees.length - 1) {
						treeType.append(sysTreeManager.get(trees[i]).getName());
					} else {
						treeType.append(sysTreeManager.get(trees[i]).getName() + " > ");
					}
				}
				if (kdDocCmmtManager.getIsByDocId(docId, userId)) {// 判断是否已经评论了
					isCmmt = true;
				}
			} else {
				return new ModelAndView("/kms/core/kdDocError.jsp");
			}
		} else {
			kdDoc = new KdDoc();
		}

		// 构造点评
		List<KdDocCmmt> cmmts = kdDocCmmtManager.getByDocId(docId);
		List<KdDocCmmtShow> cmmtShows = new ArrayList<KdDocCmmtShow>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (KdDocCmmt cmmt : cmmts) {
			KdDocCmmtShow cmmtShow = new KdDocCmmtShow();
			cmmtShow.setCmmtName(osUserManager.get(cmmt.getCreateBy()).getFullname());
			cmmtShow.setContent(cmmt.getContent());
			cmmtShow.setKdDoc(cmmt.getKdDoc());
			cmmtShow.setLevel(cmmt.getLevel());
			cmmtShow.setScore(cmmt.getScore());
			cmmtShow.setCmmtTime(formatter.format(cmmt.getCreateTime()));
			cmmtShows.add(cmmtShow);
		}

		// 构造推荐
		List<KdDocRem> rems = kdDocRemManager.getByDocId(docId);
		List<KdDocRemShow> remShows = new ArrayList<KdDocRemShow>();
		for (KdDocRem rem : rems) {
			KdDocRemShow remShow = new KdDocRemShow();
			remShow.setRemName(osUserManager.get(rem.getCreateBy()).getFullname());
			remShow.setContent(rem.getMemo());
			remShow.setKdDoc(rem.getKdDoc());
			remShow.setScore(rem.getLevel());
			remShow.setRemTime(formatter.format(rem.getCreateTime()));
			if (StringUtils.isNotBlank(rem.getDepId())) {// 组Id不为空
				remShow.setRemTarget(osGroupManager.get(rem.getDepId()).getName());
			} else if (StringUtils.isNotBlank(rem.getUserId())) {// 人Id不为空
				remShow.setRemTarget(osUserManager.get(rem.getUserId()).getFullname());
			}
			remShows.add(remShow);
		}
		// 判断是否可以编辑
		boolean isEdit = false;
		if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_USER, userId, KdDocRight.RIGHT_EDIT)) {
			isEdit = true;
		} else if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_ALL, KdDocRight.IDENTITYID_ALL, KdDocRight.RIGHT_EDIT)) {
			isEdit = true;
		}
		// 平均分
		float score = getAvgScore(docId);
		// 增加阅读次数
		int readTimes = kdDoc.getViewTimes();
		readTimes = readTimes + 1;
		kdDoc.setViewTimes(readTimes);
		kdDocManager.update(kdDoc);
		// 添加阅读名单
		KdDocRead kdRead = new KdDocRead();
		kdRead.setKdDoc(kdDoc);
		kdRead.setDocStatus(kdDoc.getStatus());
		kdRead.setReadId(idGenerator.getSID());
		kdDocReadManager.create(kdRead);
		return getPathView(request).addObject("kdDoc", kdDoc).addObject("createName", createName).addObject("updateName", updateName).addObject("treeType", treeType.toString()).addObject("isCmmt", isCmmt).addObject("score", score).addObject("cmmts", iJson.toJson(cmmtShows)).addObject("rems", iJson.toJson(remShows)).addObject("isEdit", isEdit);
	}

	@RequestMapping("listKdMap")
	@ResponseBody
	public JsonPageResult<KdDoc> listKdMap(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<KdDoc> maps = kdDocManager.getKdMapList(queryFilter);
		JsonPageResult<KdDoc> result = new JsonPageResult<KdDoc>(maps, queryFilter.getPage().getTotalItems());
		return result;
	}

	@RequestMapping("mapMgrEdit")
	public ModelAndView mapEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		String userName = ContextUtil.getCurrentUser().getFullname();
		ModelAndView mv = getPathView(request);
		KdDoc kdDoc = null;
		String jobName = null;
		String depName = null;
		String treeType = null;
		StringBuffer readName = new StringBuffer();
		StringBuffer readId = new StringBuffer();
		StringBuffer userReadId = new StringBuffer();
		if (StringUtils.isNotEmpty(pkId)) {
			kdDoc = kdDocManager.get(pkId);
			if ("0".equals(kdDoc.getAttFileids())) {// 如果没有附件，则设为空值
				kdDoc.setAttFileids("");
			}
			if (StringUtils.isNotBlank(kdDoc.getAuthorPos())) {// 如果是外部就没有岗位职位
				jobName = osGroupManager.get(kdDoc.getAuthorPos()).getName();
				depName = osGroupManager.get(kdDoc.getBelongDepid()).getName();
			}
			List<KdDocRight> readUserRights = kdDocRightManager.getAllByDocIdRight(pkId, KdDocRight.IDENTITYTYPE_USER, KdDocRight.RIGHT_READ);
			for (KdDocRight readUserRight : readUserRights) {
				userReadId.append(readUserRight.getIdentityId());
				userReadId.append(",");
				readId.append(readUserRight.getIdentityId());
				readId.append(",");
				readName.append(osUserManager.get(readUserRight.getIdentityId()).getFullname());
				readName.append(",");
			}
			if (userReadId.length() > 0) {
				userReadId.deleteCharAt(userReadId.length() - 1);
			}
			if (readId.length() > 0) {
				readName.deleteCharAt(readName.length() - 1);
				readId.deleteCharAt(readId.length() - 1);
			}
			mv.addObject("userReadId", userReadId.toString()).addObject("readName", readName.toString()).addObject("readId", readId);
		} else {
			kdDoc = new KdDoc();
		}
		return mv.addObject("kdDoc", kdDoc).addObject("jobName", jobName).addObject("depName", depName).addObject("treeType", treeType).addObject("userName", userName);
	}

	@RequestMapping("getPoint")
	@ResponseBody
	public JsonResult getPoint(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String operation = request.getParameter("operation");

		List<KdMapNote> notes = new ArrayList<KdMapNote>();

		if ("get".equals(operation)) {
			String docId = request.getParameter("docId");
			KdDoc kdMap = kdDocManager.get(docId);
			List<KdMapNote> kdMapNotes = new ArrayList<KdMapNote>();
			String mapNoteString = kdMap.getImgMaps();
			if(StringUtils.isNotEmpty(mapNoteString)){
				JSONObject notesJsonObj = JSONObject.fromObject(mapNoteString);
				JSONArray jsonArray = (JSONArray) notesJsonObj.get("notes");
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject note = (JSONObject) jsonArray.get(i);
					KdMapNote kdMapNote = (KdMapNote) JSONUtil.json2Bean(note.toString(), KdMapNote.class);
					kdMapNotes.add(kdMapNote);
				}
			}
			return new JsonResult(true, "", kdMapNotes);
		}
		if ("add".equals(operation)) {
			String docId = request.getParameter("docId");
			KdDoc kdMap = kdDocManager.get(docId);
			String newNote = request.getParameter("note");
			String noteId = "\"id\":\"" + idGenerator.getSID() + "\"";
			String newNoteJsonString = "{" + noteId + "," + newNote.substring(1);
			String notesJson = kdMap.getImgMaps();
			JSONObject jsonObj = JSONObject.fromObject(notesJson);
			JSONArray jsonArray = (JSONArray) jsonObj.get("notes");
			JSONObject newNoteJson = JSONObject.fromObject(newNoteJsonString);
			jsonArray.add(newNoteJson);

			JSONObject newJson = JSONObject.fromObject("{\"notes\":" + jsonArray.toString() + "}");
			kdMap.setImgMaps(newJson.toString());
			kdDocManager.update(kdMap);
		}
		if ("edit".equals(operation)) {
			String docId = request.getParameter("docId");
			KdDoc kdMap = kdDocManager.get(docId);
			String newNote = request.getParameter("note"); // 编辑热点信息
			String noteId = (String) JSONObject.fromObject(newNote).get("id"); // 编辑热点的ID

			// String newNoteJsonString="{"+noteId+","+newNote.substring(1);
			String notesJson = kdMap.getImgMaps();
			JSONObject jsonObj = JSONObject.fromObject(notesJson);
			JSONArray jsonArray = (JSONArray) jsonObj.get("notes");
			List<KdMapNote> kdMapNotes = new ArrayList<KdMapNote>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject note = (JSONObject) jsonArray.get(i);
				KdMapNote kdMapNote = (KdMapNote) JSONUtil.json2Bean(note.toString(), KdMapNote.class);
				if (((String) note.get("id")).equals(noteId)) {
					KdMapNote newMapNote = (KdMapNote) JSONUtil.json2Bean(newNote.toString(), KdMapNote.class);
					kdMapNotes.add(newMapNote);
					continue;
				}
				kdMapNotes.add(kdMapNote);
			}
			JSONArray newJsonArray = new JSONArray();

			for (int i = 0; i < kdMapNotes.size(); i++) {
				String json = JSONUtil.toJson(kdMapNotes.get(i));
				JSONObject jsonObject = JSONObject.fromObject(json);
				newJsonArray.add(jsonObject);
			}
			// JSONObject newNoteJson=JSONObject.fromObject(newNoteJsonString);
			// jsonArray.add(newNoteJson);

			JSONObject newJson = JSONObject.fromObject("{\"notes\":" + newJsonArray.toString() + "}");
			kdMap.setImgMaps(newJson.toString());
			kdDocManager.update(kdMap);
		}
		if ("delete".equals(operation)) {
			String docId = request.getParameter("docId");
			String noteId = request.getParameter("noteId");
			KdDoc kdMap = kdDocManager.get(docId);
			JSONObject jsonObj = JSONObject.fromObject(kdMap.getImgMaps());
			JSONArray jsonArray = (JSONArray) jsonObj.get("notes");
			List<KdMapNote> kdMapNotes = new ArrayList<KdMapNote>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject note = (JSONObject) jsonArray.get(i);
				KdMapNote kdMapNote = (KdMapNote) JSONUtil.json2Bean(note.toString(), KdMapNote.class);
				if (kdMapNote.getId().equals(noteId))
					continue;
				kdMapNotes.add(kdMapNote);
			}
			JSONArray newJsonArray = new JSONArray();

			for (int i = 0; i < kdMapNotes.size(); i++) {
				String json = JSONUtil.toJson(kdMapNotes.get(i));
				JSONObject jsonObject = JSONObject.fromObject(json);
				newJsonArray.add(jsonObject);
			}
			JSONObject newJson = JSONObject.fromObject("{\"notes\":" + newJsonArray.toString() + "}");
			kdMap.setImgMaps(newJson.toString());
			kdDocManager.update(kdMap);
		}
		return new JsonResult(true, "", notes);

	}



	@RequestMapping("getByKey")
	@ResponseBody
	public JsonPageResult<KdDoc> getByKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		String catKey = request.getParameter("key");
		List<KdDoc> kdDocList = kdDocManager.getByDocType(catKey, (Page) queryFilter.getPage());
		JsonPageResult<KdDoc> kdDocLists = new JsonPageResult<KdDoc>(kdDocList, queryFilter.getPage().getTotalItems());
		return kdDocLists;
	}

	@RequestMapping("getByDocIds")
	@ResponseBody
	public List<KdDoc> getByDocIds(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// QueryFilter
		// queryFilter=QueryFilterBuilder.createQueryFilter(request);
		String docIds = request.getParameter("docIds");

		List<KdDoc> kdDocs = new ArrayList<KdDoc>();
		if (StringUtils.isNotEmpty(docIds)) {
			String[] docIdsArray = docIds.split(",");
			for (int i = 0; i < docIdsArray.length; i++) {
				kdDocs.add(kdDocManager.get(docIdsArray[i]));
			}
		}
		return kdDocs;
	}

	@RequestMapping("mapNew1")
	public ModelAndView mapNew1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userName = ContextUtil.getCurrentUser().getFullname();
		Date nowTime = new Date();
		String tenantId = ContextUtil.getCurrentTenantId();
		KdDoc kdDoc = null;
		String jobName = null;
		String depName = null;
		String treeType = null;
		if (StringUtils.isNotEmpty(docId)) {
			kdDoc = kdDocManager.get(docId);
			jobName = osGroupManager.get(kdDoc.getAuthorPos()).getName();
			depName = osGroupManager.get(kdDoc.getBelongDepid()).getName();
			treeType = kdDoc.getSysTree().getName();
		} else {
			kdDoc = new KdDoc();
		}
		return getPathView(request).addObject("kdDoc", kdDoc).addObject("jobName", jobName).addObject("depName", depName).addObject("treeType", treeType).addObject("userName", userName).addObject("nowTime", nowTime).addObject("tenantId", tenantId);
	}

	/**
	 * 第二步new2页面的显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mapNew2")
	public ModelAndView mapNew2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userName = ContextUtil.getCurrentUser().getFullname();
		KdDoc kdDoc = null;
		if (StringUtils.isNotEmpty(docId)) {
			kdDoc = kdDocManager.get(docId);
		} else {
			kdDoc = new KdDoc();
			return new ModelAndView("/kms/core/kdDocNew1.jsp").addObject("kdDoc", kdDoc);
		}
		return getPathView(request).addObject("kdDoc", kdDoc).addObject("userName", userName);
	}

	/**
	 * 第三步new3页面的显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mapNew3")
	public ModelAndView mapNew3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userName = ContextUtil.getCurrentUser().getFullname();
		KdDoc kdDoc = null;
		if (StringUtils.isNotEmpty(docId)) {
			kdDoc = kdDocManager.get(docId);

		} else {
			kdDoc = new KdDoc();
			return new ModelAndView("/kms/core/kdDocNew1.jsp").addObject("kdDoc", kdDoc);
		}
		if (kdDoc.getStorePeroid() == null) {
			kdDoc.setStorePeroid(1);
		}
		return getPathView(request).addObject("kdDoc", kdDoc).addObject("userName", userName);
	}

	/**
	 * 第四步new4页面的显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mapNew4")
	public ModelAndView mapNew4(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userName = ContextUtil.getCurrentUser().getFullname();
		KdDoc kdDoc = null;
		if (StringUtils.isNotEmpty(docId)) {
			kdDoc = kdDocManager.get(docId);
		} else {
			kdDoc = new KdDoc();
			return new ModelAndView("/kms/core/kdDocMapNew1.jsp").addObject("kdDoc", kdDoc).addObject("userName", userName);
		}
		return getPathView(request).addObject("kdDoc", kdDoc).addObject("userName", userName);
	}

	@RequestMapping("mapSearch")
	public ModelAndView mapSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catId = request.getParameter("catId");
		String name = "";
		if (StringUtils.isNotEmpty(catId)) {
			SysTree sysTree = sysTreeManager.get(catId);
			String[] paths = {};
			if (StringUtils.isNotEmpty(sysTree.getPath())) {
				paths = sysTree.getPath().split("\\.");
			}

			for (int i = 0; i < paths.length; i++) {
				if (i == 0) {
					name = "<a class='cat' id='" + sysTreeManager.get(paths[i]).getTreeId() + "'>" + sysTreeManager.get(paths[i]).getName() + "</a>";
					continue;
				}
				name = name + ">" + "<a class='cat' id='" + sysTreeManager.get(paths[i]).getTreeId() + "'>" + sysTreeManager.get(paths[i]).getName() + "</a>";
			}
		}

		List<KdMenu> leftMenu = getLeftMenu("CAT_KMS_KDMAP");
		return this.getPathView(request).addObject("leftMenu", iJson.toJson(leftMenu)).addObject("name", name);
	}

	@RequestMapping("getMapByPath")
	@ResponseBody
	public JsonPageResult<KdDoc> getMapByPath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catId = request.getParameter("treeId");
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);

		if (StringUtils.isNotEmpty(catId))
			queryFilter.addFieldParam("sysTree.path", QueryParam.OP_LEFT_LIKE, sysTreeManager.get(catId).getPath());
		queryFilter.addFieldParam("docType", "KD_MAP");
		queryFilter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
		List<KdDoc> docs = kdDocManager.getAll(queryFilter);
		JsonPageResult<KdDoc> docList = new JsonPageResult<KdDoc>(docs, queryFilter.getPage().getTotalItems());
		return docList;
	}

	/**
	 * 搜索所有符合权限的doc
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMapByRightAndPath")
	@ResponseBody
	public JsonPageResult<KdDoc> getMapByRightAndPath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catId = request.getParameter("treeId");
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		String userId = ContextUtil.getCurrentUserId();
		List<KdDoc> docs = new ArrayList<KdDoc>();
		if (StringUtils.isNotEmpty(catId))
			docs = kdDocManager.getAllByRgiht(userId, "KD_MAP", KdDocRight.RIGHT_READ, sysTreeManager.get(catId).getPath(), queryFilter);
		else
			docs = kdDocManager.getAllByRgiht(userId, "KD_MAP", KdDocRight.RIGHT_READ, "", queryFilter);

		JsonPageResult<KdDoc> result = new JsonPageResult<KdDoc>(docs, queryFilter.getPage().getTotalItems());
		return result;
	}

	@RequestMapping("getMapByAddition")
	@ResponseBody
	public JsonPageResult<KdDoc> getMapByAddition(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catId = request.getParameter("treeId");
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<KdDoc> docs = new ArrayList<KdDoc>();
		if (StringUtils.isEmpty(catId))
			docs = kdDocManager.getAllByRgiht(userId, "KD_MAP", KdDocRight.RIGHT_READ, "", queryFilter);
		else
			docs = kdDocManager.getAllByRgiht(userId, "KD_MAP", KdDocRight.RIGHT_READ, sysTreeManager.get(catId).getPath(), queryFilter);
		JsonPageResult<KdDoc> result = new JsonPageResult<KdDoc>(docs, queryFilter.getPage().getTotalItems());
		return result;
	}

	@RequestMapping("mapPersonalSearchDoc")
	@ResponseBody
	public JsonPageResult<KdDoc> mapPersonalSearchDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		Map<String, QueryParam> queryParams = queryFilter.getQueryParams();
		List<KdDoc> docs = new ArrayList<KdDoc>();
		// 是否有查询状态
		if (queryParams.get("Type") != null) {
			if ("myMap".equals(queryParams.get("Type").getValue())) {
				docs = kdDocManager.getMyDoc(userId, "KD_MAP", queryFilter);
			} else if ("myCmmt".equals(queryParams.get("Type").getValue())) {
				docs = kdDocManager.getDocByCmmt(userId, "KD_MAP", queryFilter);
			} else if ("myEdit".equals(queryParams.get("Type").getValue())) {
				docs = kdDocManager.getAllPersonalDoc(userId, "KD_MAP", KdDocRight.RIGHT_EDIT, queryFilter);
			} else if ("myFav".equals(queryParams.get("Type").getValue())) {
				docs = kdDocManager.getMyFav(userId, "KD_MAP", queryFilter);
			}
		} else {
			docs = kdDocManager.getAllPersonalDoc(userId, "KD_MAP", KdDocRight.RIGHT_READ, queryFilter);
		}
		JsonPageResult<KdDoc> docList = new JsonPageResult<KdDoc>(docs, queryFilter.getPage().getTotalItems());
		return docList;
	}

	/**
	 * 知识仓库首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mapIndex")
	public ModelAndView mapIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		List<KdMenu> leftMenu = getLeftMenu("CAT_KMS_KDMAP");
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);

		// 最新Doc
		List<KdCoverImage> newestDoc = new ArrayList<KdCoverImage>();
		List<KdDoc> newdocs = kdDocManager.getByNewest(userId, "KD_MAP", queryFilter);
		for (KdDoc doc : newdocs) {
			KdCoverImage image = new KdCoverImage();
			image.setDocId(doc.getDocId());
			image.setTitle(doc.getSubject());
			image.setPicUrl("/sys/core/file/imageView.do?fileId=" + doc.getCoverImgId());
			image.setJumpUrl("/kms/core/kdDoc/show.do?docId=" + doc.getDocId());
			image.setSummary(doc.getSummary());
			newestDoc.add(image);
		}

		// 最热Doc
		List<KdCoverImage> hotestDoc = new ArrayList<KdCoverImage>();
		List<KdDoc> hotdocs = kdDocManager.getByHotest(userId, "KD_MAP", queryFilter);
		for (KdDoc doc : hotdocs) {
			KdCoverImage image = new KdCoverImage();
			image.setDocId(doc.getDocId());
			image.setTitle(doc.getSubject());
			image.setPicUrl("/sys/core/file/imageView.do?fileId=" + doc.getCoverImgId());
			image.setJumpUrl("/kms/core/kdDoc/show.do?docId=" + doc.getDocId());
			image.setSummary(doc.getSummary());
			hotestDoc.add(image);
		}

		return getPathView(request).addObject("leftMenu", iJson.toJson(leftMenu)).addObject("newestDoc", newestDoc).addObject("hotestDoc", hotestDoc);
	}

	/**
	 * 知识仓库首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("home")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		// 判断当前用户是否已经关联了知识仓库
		if (!kdUserManager.getIsByUserId(userId)) {
			OsUser user = osUserManager.get(userId);
			return new ModelAndView("/kms/core/kdUserConnect.jsp").addObject("user", user);
		}

		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		// 左边目录
		List<KdMenu> leftMenu = getLeftMenu("CAT_KMS_KDDOC");

		// 循环显示的带图片的推荐Doc
		List<KdCoverImage> remDoc = new ArrayList<KdCoverImage>();
		List<KdDoc> remdocs = kdDocManager.getByRem();
		for (KdDoc doc : remdocs) {
			KdCoverImage image = new KdCoverImage();
			image.setDocId(doc.getDocId());
			image.setTitle(doc.getSubject());
			image.setPicUrl("/sys/core/file/imageView.do?fileId=" + doc.getCoverImgId());
			image.setJumpUrl("/kms/core/kdDoc/show.do?docId=" + doc.getDocId());
			image.setSummary(doc.getSummary());
			remDoc.add(image);
		}

		// 最新Doc
		List<KdDoc> newdocs = kdDocManager.getByNewest(userId, "KD_DOC", queryFilter);
		// 最热Doc
		List<KdDoc> hotdocs = kdDocManager.getByHotest(userId, "KD_DOC", queryFilter);
		// 最新回答
		List<KdQuestion> latestQuestions = kdQuestionManager.getLatestQuestion(ContextUtil.getCurrentTenantId());
		// 最热回答
		List<KdQuestion> hostestQuestions = kdQuestionManager.getHostestQuestion(ContextUtil.getCurrentTenantId());
		// 未解决回答
		List<KdQuestion> unsolvedQuestions = kdQuestionManager.getByStatus(KdQuestion.STATUS_UNSOLVED,ContextUtil.getCurrentTenantId());
		// 最佳回答
		List<KdQuestion> bestQuestions = kdQuestionManager.getBestQuestion(ContextUtil.getCurrentTenantId());
		// 总积分榜
		Page page = new Page();
		page.setPageIndex(0);
		page.setPageSize(5);
		List<KdUser> totalScore = kdUserManager.getScoreUser(page,ContextUtil.getCurrentTenantId());
		// 总财富榜
		List<KdUser> totalPoint = kdUserManager.getPointUser(page,ContextUtil.getCurrentTenantId());

		return new ModelAndView("/kms/core/kdDocHome.jsp").addObject("leftMenu", iJson.toJson(leftMenu)).addObject("newdocs", newdocs).addObject("hotdocs", hotdocs).addObject("latestQuestions", latestQuestions).addObject("hostestQuestions", hostestQuestions).addObject("unsolvedQuestions", unsolvedQuestions).addObject("bestQuestions", bestQuestions).addObject("totalScore", totalScore).addObject("totalPoint", totalPoint).addObject("remDoc", remDoc);
	}

	/**
	 * 判断打开一个文档的时候是否具有权限
	 * 
	 * @param userId
	 *            用户id
	 * @param docId
	 *            文档Id
	 * @return
	 */
	public boolean showRight(String userId, String docId) {
		boolean right = false;
		KdDoc doc = kdDocManager.get(docId);
		if (userId.equals(doc.getCreateBy())) {
			right = true;
			return right;
		}
		if (kdDocRightManager.getIsAll(docId)) {
			right = true;
			return right;
		}
		if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_USER, userId, KdDocRight.RIGHT_READ)) {
			right = true;
			return right;
		}
		List<OsGroup> groups = osGroupManager.getBelongDeps(userId);
		for (OsGroup group : groups) {
			String groupId = group.getGroupId();
			if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_GROUP, groupId, KdDocRight.RIGHT_READ)) {
				right = true;
				return right;
			}
		}
		return right;
	}

	/**
	 * 个人中心页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("personal")
	public ModelAndView personal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		return getPathView(request).addObject("userId", userId);
	}

	/**
	 * 个人中心页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mapPersonal")
	public ModelAndView mapPersonal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		return getPathView(request).addObject("userId", userId);
	}
	
	@RequestMapping("removeAllNote")
	@ResponseBody
	public JsonResult removeAllNode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId=request.getParameter("docId");
		String noteString = "{\"notes\":[]}";
		KdDoc map= kdDocManager.get(docId);
		map.setImgMaps(noteString);
		kdDocManager.update(map);
		return new JsonResult(true,"");
	}

}
