package com.redxun.kms.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.json.JsonResult;
import com.redxun.kms.core.entity.KdDoc;
import com.redxun.kms.core.entity.KdDocRight;
import com.redxun.kms.core.manager.KdDocManager;
import com.redxun.kms.core.manager.KdDocRightManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.org.manager.OsGroupManager;

/**
 * [KdDoc]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDoc/")
public class KdDocFormController extends BaseFormController {

	@Resource
	private KdDocManager kdDocManager;
	@Resource
	SysTreeManager sysTreeManager;
	@Resource
	OsGroupManager osGroupManager;
	@Resource
	KdDocRightManager kdDocRightManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("kdDoc")
	public KdDoc processForm(HttpServletRequest request) {
		String docId = request.getParameter("docId");
		KdDoc kdDoc = null;
		if (StringUtils.isNotEmpty(docId)) {
			kdDoc = kdDocManager.get(docId);
		} else {
			kdDoc = new KdDoc();
		}

		return kdDoc;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param kdDoc
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request, @ModelAttribute("kdDoc") @Valid KdDoc kdDoc, BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(kdDoc.getDocId())) {
			kdDoc.setDocId(idGenerator.getSID());
			kdDocManager.create(kdDoc);
			msg = getMessage("kdDoc.created", new Object[] { kdDoc.getIdentifyLabel() }, "[KdDoc]成功创建!");
		} else {
			kdDocManager.update(kdDoc);
			msg = getMessage("kdDoc.updated", new Object[] { kdDoc.getIdentifyLabel() }, "[KdDoc]成功更新!");
		}
		// saveOpMessage(request, msg);
		return new JsonResult(true, msg);
	}

	/**
	 * new1页面的下一步按钮的保存和更新
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveNew1")
	@ResponseBody
	public String saveNew1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		String docId = request.getParameter("docId");
		String jobId = request.getParameter("authorPos");
		String subject = request.getParameter("subject");
		String authorType = request.getParameter("authorType");
		String departmentId = request.getParameter("belongDepid");
		String treeId = request.getParameter("treeId");
		String author = request.getParameter("author");
		String docType = request.getParameter("docType");
		KdDoc kdDoc = null;

		if (StringUtils.isEmpty(docId)) {
			kdDoc = new KdDoc();
			kdDoc.setDocId(idGenerator.getSID());
			kdDoc.setAuthorType(authorType);
			kdDoc.setSubject(subject);
			kdDoc.setAuthor(author);
			kdDoc.setSysTree(sysTreeManager.get(treeId));
			kdDoc.setBelongDepid(departmentId);
			kdDoc.setStatus(KdDoc.STATUS_DRAFT);
			kdDoc.setAuthorPos(jobId);
			kdDoc.setViewTimes(0);
			kdDoc.setVersion(1);
			kdDoc.setDocType(docType);
			kdDoc.setIsEssence("NO");
			String noteString = "{\"notes\":[]}";
			kdDoc.setImgMaps(noteString);
			kdDocManager.create(kdDoc);
		} else {
			kdDoc = kdDocManager.get(docId);
			kdDoc.setAuthorType(authorType);
			kdDoc.setSubject(subject);
			kdDoc.setAuthor(author);
			kdDoc.setSysTree(sysTreeManager.get(treeId));
			kdDoc.setBelongDepid(departmentId);
			kdDoc.setStatus(KdDoc.STATUS_DRAFT);
			kdDoc.setAuthorPos(jobId);
			kdDoc.setVersion(1);
			kdDoc.setIsEssence("NO");
			kdDocManager.update(kdDoc);
		}
		// 设置本人的编辑权限
		KdDocRight kdRight = new KdDocRight();
		kdRight.setRightId(idGenerator.getSID());
		kdRight.setIdentityType(KdDocRight.IDENTITYTYPE_USER);
		kdRight.setKdDoc(kdDoc);
		kdRight.setIdentityId(userId);
		kdRight.setRight(KdDocRight.RIGHT_EDIT);
		kdDocRightManager.create(kdRight);
		return kdDoc.getDocId();
	}

	/**
	 * new2页面的下一步按钮的保存和更新
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveNew2")
	@ResponseBody
	public String saveNew2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String content = request.getParameter("content");
		String attFileids = request.getParameter("attFileids");
		KdDoc kdDoc = null;
		String imgId = request.getParameter("coverImgId");// 封面图
		if (StringUtils.isEmpty(docId)) {

		} else {
			kdDoc = kdDocManager.get(docId);
			kdDoc.setContent(content);
			if (StringUtils.isNotEmpty(imgId)) {
				kdDoc.setCoverImgId(imgId);
				/*String noteString = "{\"notes\":[]}";
				kdDoc.setImgMaps(noteString);*/
			}
			else{
				kdDoc.setImgMaps(null);
			}
			if (StringUtils.isNotBlank(attFileids)) {
				kdDoc.setAttFileids(attFileids);
			} else {
				kdDoc.setAttFileids("0");
			}

			kdDocManager.update(kdDoc);
		}
		return kdDoc.getDocId();
	}

	/**
	 * new3页面的下一步按钮的保存和更新
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveNew3")
	@ResponseBody
	public String saveNew3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String coverImgId = request.getParameter("coverImgId");
		String summary = request.getParameter("summary");
		String tags = request.getParameter("tags");
		int storePeroid = Integer.parseInt(request.getParameter("storePeroid"));
		KdDoc kdDoc = null;

		if (StringUtils.isEmpty(docId)) {

		} else {
			kdDoc = kdDocManager.get(docId);
			if (StringUtils.isNotEmpty(coverImgId)) {
				kdDoc.setCoverImgId(coverImgId);
			}
			kdDoc.setSummary(summary);
			kdDoc.setTags(tags);
			kdDoc.setStorePeroid(storePeroid);
			kdDocManager.update(kdDoc);
		}
		return kdDoc.getDocId();
	}

	/**
	 * new4页面的下一步按钮的保存和更新
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveNew4")
	@ResponseBody
	public String saveNew4(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userId = ContextUtil.getCurrentUserId();
		String[] readable = request.getParameter("readable").split(",");
		String[] editable = request.getParameter("editable").split(",");
		// String[] downloadPerson =
		// request.getParameter("downloadPerson").split(",");
		// String[] downloadGroup =
		// request.getParameter("downloadGroup").split(",");
		KdDoc kdDoc = null;

		if (StringUtils.isEmpty(docId)) {
		} else {
			kdDoc = kdDocManager.get(docId);
			kdDocRightManager.delByDocId(docId);
			// 设置本人的编辑权限
			KdDocRight kdRight = new KdDocRight();
			kdRight.setRightId(idGenerator.getSID());
			kdRight.setIdentityType(KdDocRight.IDENTITYTYPE_USER);
			kdRight.setKdDoc(kdDoc);
			kdRight.setIdentityId(userId);
			kdRight.setRight(KdDocRight.RIGHT_EDIT);
			kdDocRightManager.create(kdRight);

			// 保存阅读权限
			if (StringUtils.isNotBlank(readable[0])) {
				for (int i = 0; i < readable.length; i++) {
					if (readable[i].contains("user")) {
						String readPerson = readable[i].split("_")[0];
						if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_USER, readPerson, KdDocRight.RIGHT_READ)) {
							continue;
						}
						KdDocRight kdRight1 = new KdDocRight();
						kdRight1.setRightId(idGenerator.getSID());
						kdRight1.setDocId(docId);
						kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_USER);
						kdRight1.setKdDoc(kdDoc);
						kdRight1.setIdentityId(readPerson);
						kdRight1.setRight(KdDocRight.RIGHT_READ);
						kdDocRightManager.create(kdRight1);
					} else if (readable[i].contains("group")) {
						String readGroup = readable[i].split("_")[0];
						if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_GROUP, readGroup, KdDocRight.RIGHT_READ)) {
							continue;
						}
						KdDocRight kdRight1 = new KdDocRight();
						kdRight1.setRightId(idGenerator.getSID());
						kdRight1.setDocId(docId);
						kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_GROUP);
						kdRight1.setKdDoc(kdDoc);
						kdRight1.setIdentityId(readGroup);
						kdRight1.setRight(KdDocRight.RIGHT_READ);
						kdDocRightManager.create(kdRight1);
					}
				}
			} else {// 如果没有填写则保存为ALL的权限
				if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_ALL, KdDocRight.IDENTITYID_ALL, KdDocRight.RIGHT_READ)) {
				} else {
					KdDocRight kdRight1 = new KdDocRight();
					kdRight1.setRightId(idGenerator.getSID());
					kdRight1.setDocId(docId);
					kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_ALL);
					kdRight1.setKdDoc(kdDoc);
					kdRight1.setIdentityId(KdDocRight.IDENTITYID_ALL);
					kdRight1.setRight(KdDocRight.RIGHT_READ);
					kdDocRightManager.create(kdRight1);
				}
			}

			// 保存编辑权限
			if (StringUtils.isNotBlank(editable[0])) {
				for (int i = 0; i < editable.length; i++) {
					if (editable[i].contains("user")) {
						String editPerson = editable[i].split("_")[0];
						if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_USER, editPerson, KdDocRight.RIGHT_EDIT)) {
							continue;
						}
						KdDocRight kdRight1 = new KdDocRight();
						kdRight1.setRightId(idGenerator.getSID());
						kdRight1.setDocId(docId);
						kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_USER);
						kdRight1.setKdDoc(kdDoc);
						kdRight1.setIdentityId(editPerson);
						kdRight1.setRight(KdDocRight.RIGHT_EDIT);
						kdDocRightManager.create(kdRight1);
					} else if (editable[i].contains("group")) {
						String editGroup = editable[i].split("_")[0];
						if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_GROUP, editGroup, KdDocRight.RIGHT_EDIT)) {
							continue;
						}
						KdDocRight kdRight1 = new KdDocRight();
						kdRight1.setRightId(idGenerator.getSID());
						kdRight1.setDocId(docId);
						kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_GROUP);
						kdRight1.setKdDoc(kdDoc);
						kdRight1.setIdentityId(editGroup);
						kdRight1.setRight(KdDocRight.RIGHT_EDIT);
						kdDocRightManager.create(kdRight1);
					}
				}
			}
		}
		return kdDoc.getDocId();
	}

	@RequestMapping("saveEdit")
	@ResponseBody
	public String saveEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		String userId = ContextUtil.getCurrentUserId();
		// 第四步
		String[] readable = request.getParameter("readable").split(",");
		String[] editable = request.getParameter("editable").split(",");
		String[] downloadable = request.getParameter("downloadable").split(",");
		/*
		 * String[] downloadPerson =
		 * request.getParameter("downloadPerson").split(","); String[]
		 * downloadGroup = request.getParameter("downloadGroup").split(",");
		 */
		// 第三步
		String coverImgId = request.getParameter("coverImgId");
		String summary = request.getParameter("summary");
		String tags = request.getParameter("tags");
		int storePeroid = Integer.parseInt(request.getParameter("storePeroid"));
		// 第二步
		String content = request.getParameter("content");
		String attFileids = request.getParameter("attFileids");
		// 第一步
		String jobId = request.getParameter("authorPos");
		String subject = request.getParameter("subject");
		String authorType = request.getParameter("authorType");
		String departmentId = request.getParameter("belongDepid");
		String treeId = request.getParameter("treeId");
		String author = request.getParameter("author");

		KdDoc kdDoc = null;

		if (StringUtils.isEmpty(docId)) {

		} else {
			kdDoc = kdDocManager.get(docId);
			// 第一步
			kdDoc.setAuthorType(authorType);
			kdDoc.setSubject(subject);
			kdDoc.setAuthor(author);
			kdDoc.setSysTree(sysTreeManager.get(treeId));
			kdDoc.setBelongDepid(departmentId);
			kdDoc.setAuthorPos(jobId);
			// 第二步
			kdDoc.setContent(content);
			if (StringUtils.isNotBlank(attFileids)) {
				kdDoc.setAttFileids(attFileids);
			} else {
				kdDoc.setAttFileids("0");
			}
			// 第三步
			kdDoc = kdDocManager.get(docId);
			kdDoc.setCoverImgId(coverImgId);
			kdDoc.setSummary(summary);
			kdDoc.setTags(tags);
			kdDoc.setStorePeroid(storePeroid);
			kdDocManager.update(kdDoc);
			// 第四步
			kdDocRightManager.delByDocId(docId);
			// 设置本人的编辑权限
			KdDocRight kdRight = new KdDocRight();
			kdRight.setRightId(idGenerator.getSID());
			kdRight.setIdentityType(KdDocRight.IDENTITYTYPE_USER);
			kdRight.setKdDoc(kdDoc);
			kdRight.setIdentityId(userId);
			kdRight.setRight(KdDocRight.RIGHT_EDIT);
			kdDocRightManager.create(kdRight);
			// 保存阅读权限
			if (StringUtils.isNotBlank(readable[0])) {
				for (int i = 0; i < readable.length; i++) {
					if (readable[i].contains("user")) {
						String readPerson = readable[i].split("_")[0];
						if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_USER, readPerson, KdDocRight.RIGHT_READ)) {
							continue;
						}
						KdDocRight kdRight1 = new KdDocRight();
						kdRight1.setRightId(idGenerator.getSID());
						kdRight1.setDocId(docId);
						kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_USER);
						kdRight1.setKdDoc(kdDoc);
						kdRight1.setIdentityId(readPerson);
						kdRight1.setRight(KdDocRight.RIGHT_READ);
						kdDocRightManager.create(kdRight1);
					} else if (readable[i].contains("group")) {
						String readGroup = readable[i].split("_")[0];
						if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_GROUP, readGroup, KdDocRight.RIGHT_READ)) {
							continue;
						}
						KdDocRight kdRight1 = new KdDocRight();
						kdRight1.setRightId(idGenerator.getSID());
						kdRight1.setDocId(docId);
						kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_GROUP);
						kdRight1.setKdDoc(kdDoc);
						kdRight1.setIdentityId(readGroup);
						kdRight1.setRight(KdDocRight.RIGHT_READ);
						kdDocRightManager.create(kdRight1);
					}
				}
			} else {// 如果没有选择阅读权限，则默认所有人可以观看
				if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_ALL, KdDocRight.IDENTITYID_ALL, KdDocRight.RIGHT_READ)) {
				} else {
					KdDocRight kdRight1 = new KdDocRight();
					kdRight1.setRightId(idGenerator.getSID());
					kdRight1.setDocId(docId);
					kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_ALL);
					kdRight1.setKdDoc(kdDoc);
					kdRight1.setIdentityId(KdDocRight.IDENTITYID_ALL);
					kdRight1.setRight(KdDocRight.RIGHT_READ);
					kdDocRightManager.create(kdRight1);
				}
			}

			// 保存编辑权限
			if (StringUtils.isNotBlank(editable[0])) {
				for (int i = 0; i < editable.length; i++) {
					if (editable[i].contains("user")) {
						String editPerson = editable[i].split("_")[0];
						if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_USER, editPerson, KdDocRight.RIGHT_EDIT)) {
							continue;
						}
						KdDocRight kdRight1 = new KdDocRight();
						kdRight1.setRightId(idGenerator.getSID());
						kdRight1.setDocId(docId);
						kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_USER);
						kdRight1.setKdDoc(kdDoc);
						kdRight1.setIdentityId(editPerson);
						kdRight1.setRight(KdDocRight.RIGHT_EDIT);
						kdDocRightManager.create(kdRight1);
					} else if (editable[i].contains("group")) {
						String editGroup = editable[i].split("_")[0];
						if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_GROUP, editGroup, KdDocRight.RIGHT_EDIT)) {
							continue;
						}
						KdDocRight kdRight1 = new KdDocRight();
						kdRight1.setRightId(idGenerator.getSID());
						kdRight1.setDocId(docId);
						kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_GROUP);
						kdRight1.setKdDoc(kdDoc);
						kdRight1.setIdentityId(editGroup);
						kdRight1.setRight(KdDocRight.RIGHT_EDIT);
						kdDocRightManager.create(kdRight1);
					}
				}
			}
			// 保存删除权限
			if (StringUtils.isNotBlank(downloadable[0])) {
				for (int i = 0; i < downloadable.length; i++) {
					if (downloadable[i].contains("user")) {
						String downloadPerson = downloadable[i].split("_")[0];
						if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_USER, downloadPerson, KdDocRight.RIGHT_DOWNLOAD)) {
							continue;
						}
						KdDocRight kdRight1 = new KdDocRight();
						kdRight1.setRightId(idGenerator.getSID());
						kdRight1.setDocId(docId);
						kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_USER);
						kdRight1.setKdDoc(kdDoc);
						kdRight1.setIdentityId(downloadPerson);
						kdRight1.setRight(KdDocRight.RIGHT_DOWNLOAD);
						kdDocRightManager.create(kdRight1);
					} else if (downloadable[i].contains("group")) {
						String downloadGroup = downloadable[i].split("_")[0];
						if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_GROUP, downloadGroup, KdDocRight.RIGHT_DOWNLOAD)) {
							continue;
						}
						KdDocRight kdRight1 = new KdDocRight();
						kdRight1.setRightId(idGenerator.getSID());
						kdRight1.setDocId(docId);
						kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_GROUP);
						kdRight1.setKdDoc(kdDoc);
						kdRight1.setIdentityId(downloadGroup);
						kdRight1.setRight(KdDocRight.RIGHT_DOWNLOAD);
						kdDocRightManager.create(kdRight1);
					}
				}
			}else {// 如果没有选择下载权限，则默认所有人可以下载
				if (kdDocRightManager.getByDocIdIdentityIdRight(docId, KdDocRight.IDENTITYTYPE_ALL, KdDocRight.IDENTITYID_ALL, KdDocRight.RIGHT_DOWNLOAD)) {
				} else {
					KdDocRight kdRight1 = new KdDocRight();
					kdRight1.setRightId(idGenerator.getSID());
					kdRight1.setDocId(docId);
					kdRight1.setIdentityType(KdDocRight.IDENTITYTYPE_ALL);
					kdRight1.setKdDoc(kdDoc);
					kdRight1.setIdentityId(KdDocRight.IDENTITYID_ALL);
					kdRight1.setRight(KdDocRight.RIGHT_DOWNLOAD);
					kdDocRightManager.create(kdRight1);
				}
			}
			/* 附件保存 */
			String attach = request.getParameter("attFileids");
			if (StringUtils.isNotEmpty(attach)) {// attach字段不为空
				kdDoc.setAttFileids(attach);
			}else{
				kdDoc.setAttFileids("0");
			}
		}
		return kdDoc.getDocId();
	}
}
