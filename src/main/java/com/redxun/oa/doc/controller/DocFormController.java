package com.redxun.oa.doc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.json.JsonResult;
import com.redxun.kms.core.entity.KdDocRight;
import com.redxun.oa.doc.entity.Doc;
import com.redxun.oa.doc.entity.DocRight;
import com.redxun.oa.doc.manager.DocManager;
import com.redxun.oa.doc.manager.DocRightManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;

/**
 * 文档管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/doc/doc/")
public class DocFormController extends BaseFormController {

	@Resource
	private DocManager docManager;
	@Resource
	private SysFileManager sysFileManager;
	@Resource
	private DocRightManager docRightManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("doc")
	public Doc processForm(HttpServletRequest request) {
		String docId = request.getParameter("docId");
		Doc doc = null;
		if (StringUtils.isNotEmpty(docId)) {
			doc = docManager.get(docId);
		} else {
			doc = new Doc();
		}

		return doc;
	}

	/**
	 * 保存实体数据,并且把附件也保存进文档
	 * 
	 * @param request
	 * @param doc
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject save(HttpServletRequest request, @ModelAttribute("doc") @Valid Doc doc, BindingResult result) {
         JSONObject jsonObject=new JSONObject();
		String[] readable = request.getParameter("readable").split(",");
		String[] editable = request.getParameter("editable").split(",");
		String[] delable = request.getParameter("delable").split(",");
		String userId = ContextUtil.getCurrentUserId();
		String docPath=request.getParameter("docPath");

		if (result.hasFieldErrors()) {
			jsonObject.put("success", false);
			return jsonObject;
		}
		/* 清空缓存下面直接覆盖保存最新附件 */
		doc.getInfDocFiles().clear();
		/* 附件保存 */
		String attach = request.getParameter("attach");
		logger.debug("attch:" + attach);
		if (StringUtils.isNotEmpty(attach)) {// attach字段不为空则逗号切割它
			String[] fileIds = attach.split("[,]");
			for (String fileId : fileIds) {// 再把切出来的字段赋给fileId，通过它来查找出所有对应的sysFile
				SysFile sysFile = sysFileManager.get(fileId);
				if(sysFile!=null)
				doc.getInfDocFiles().add(sysFile);
			}
		}
		doc.setDocPath(docPath);

		// 保存Doc
		if (StringUtils.isEmpty(doc.getDocId())) {// 新建
			doc.setDocId(idGenerator.getSID());
			docManager.create(doc);
			docManager.flush();
			DocRight right = new DocRight();
			right.setRightId(idGenerator.getSID());
			right.setIdentityType(DocRight.IDENTITYTYPE_USER);
			right.setDoc(doc);
			right.setIdentityId(userId);
			right.setRights(DocRight.RIGHTS_EDIT);
			docRightManager.create(right);

			// 保存阅读权限
			if (StringUtils.isNotBlank(readable[0])) {
				for (int i = 0; i < readable.length; i++) {
					if (readable[i].contains("user")) {
						String readPerson = readable[i].split("_")[0];
						if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_USER, readPerson, DocRight.RIGHTS_READ)) {
							continue;
						}
						DocRight right1 = new DocRight();
						right1.setRightId(idGenerator.getSID());
						right1.setDocId(doc.getDocId());
						right1.setIdentityType(DocRight.IDENTITYTYPE_USER);
						right1.setDoc(doc);
						right1.setIdentityId(readPerson);
						right1.setRights(DocRight.RIGHTS_READ);
						docRightManager.create(right1);
					} else if (readable[i].contains("group")) {
						String readGroup = readable[i].split("_")[0];
						if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_GROUP, readGroup, DocRight.RIGHTS_READ)) {
							continue;
						}
						DocRight right1 = new DocRight();
						right1.setRightId(idGenerator.getSID());
						right1.setDocId(doc.getDocId());
						right1.setIdentityType(DocRight.IDENTITYTYPE_GROUP);
						right1.setDoc(doc);
						right1.setIdentityId(readGroup);
						right1.setRights(DocRight.RIGHTS_READ);
						docRightManager.create(right1);
					}
				}
			} else {// 如果没有填写则保存为ALL的权限
				if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_ALL, DocRight.IDENTITYID_ALL, DocRight.RIGHTS_READ)) {
				} else {
					DocRight right1 = new DocRight();
					right1.setRightId(idGenerator.getSID());
					right1.setDocId(doc.getDocId());
					right1.setIdentityType(DocRight.IDENTITYTYPE_ALL);
					right1.setDoc(doc);
					right1.setIdentityId(DocRight.IDENTITYID_ALL);
					right1.setRights(DocRight.RIGHTS_READ);
					docRightManager.create(right1);
				}
			}

			// 保存编辑权限
			if (StringUtils.isNotBlank(editable[0])) {
				for (int i = 0; i < editable.length; i++) {
					if (editable[i].contains("user")) {
						String editPerson = editable[i].split("_")[0];
						if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_USER, editPerson, DocRight.RIGHTS_EDIT)) {
							continue;
						}
						DocRight right1 = new DocRight();
						right1.setRightId(idGenerator.getSID());
						right1.setDocId(doc.getDocId());
						right1.setIdentityType(DocRight.IDENTITYTYPE_USER);
						right1.setDoc(doc);
						right1.setIdentityId(editPerson);
						right1.setRights(DocRight.RIGHTS_EDIT);
						docRightManager.create(right1);
					} else if (editable[i].contains("group")) {
						String editGroup = editable[i].split("_")[0];
						if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_GROUP, editGroup, DocRight.RIGHTS_EDIT)) {
							continue;
						}
						DocRight right1 = new DocRight();
						right1.setRightId(idGenerator.getSID());
						right1.setDocId(doc.getDocId());
						right1.setIdentityType(DocRight.IDENTITYTYPE_GROUP);
						right1.setDoc(doc);
						right1.setIdentityId(editGroup);
						right1.setRights(DocRight.RIGHTS_EDIT);
						docRightManager.create(right1);
					}
				}
			}

			// 保存删除权限
			if (StringUtils.isNotBlank(delable[0])) {
				for (int i = 0; i < delable.length; i++) {
					if (delable[i].contains("user")) {
						String delPerson = delable[i].split("_")[0];
						if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_USER, delPerson, DocRight.RIGHTS_DEL)) {
							continue;
						}
						DocRight right1 = new DocRight();
						right1.setRightId(idGenerator.getSID());
						right1.setDocId(doc.getDocId());
						right1.setIdentityType(DocRight.IDENTITYTYPE_USER);
						right1.setDoc(doc);
						right1.setIdentityId(delPerson);
						right1.setRights(DocRight.RIGHTS_DEL);
						docRightManager.create(right1);
					} else if (delable[i].contains("group")) {
						String delGroup = delable[i].split("_")[0];
						if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_GROUP, delGroup, DocRight.RIGHTS_DEL)) {
							continue;
						}
						DocRight right1 = new DocRight();
						right1.setRightId(idGenerator.getSID());
						right1.setDocId(doc.getDocId());
						right1.setIdentityType(DocRight.IDENTITYTYPE_GROUP);
						right1.setDoc(doc);
						right1.setIdentityId(delGroup);
						right1.setRights(DocRight.RIGHTS_DEL);
						docRightManager.create(right1);
					}
				}
			}

		} else {// 更新修改
			docRightManager.delByDocId(doc.getDocId());
			// 设置本人的编辑权限
			DocRight right = new DocRight();
			right.setRightId(idGenerator.getSID());
			right.setIdentityType(DocRight.IDENTITYTYPE_USER);
			right.setDoc(doc);
			right.setIdentityId(userId);
			right.setRights(DocRight.RIGHTS_EDIT);
			docRightManager.create(right);

			// 保存阅读权限
			if (StringUtils.isNotBlank(readable[0])) {
				for (int i = 0; i < readable.length; i++) {
					if (readable[i].contains("user")) {
						String readPerson = readable[i].split("_")[0];
						if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_USER, readPerson, DocRight.RIGHTS_READ)) {
							continue;
						}
						DocRight right1 = new DocRight();
						right1.setRightId(idGenerator.getSID());
						right1.setDocId(doc.getDocId());
						right1.setIdentityType(DocRight.IDENTITYTYPE_USER);
						right1.setDoc(doc);
						right1.setIdentityId(readPerson);
						right1.setRights(DocRight.RIGHTS_READ);
						docRightManager.create(right1);
					} else if (readable[i].contains("group")) {
						String readGroup = readable[i].split("_")[0];
						if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_GROUP, readGroup, DocRight.RIGHTS_READ)) {
							continue;
						}
						DocRight right1 = new DocRight();
						right1.setRightId(idGenerator.getSID());
						right1.setDocId(doc.getDocId());
						right1.setIdentityType(DocRight.IDENTITYTYPE_GROUP);
						right1.setDoc(doc);
						right1.setIdentityId(readGroup);
						right1.setRights(DocRight.RIGHTS_READ);
						docRightManager.create(right1);
					}
				}
			} else {// 如果没有填写则保存为ALL的权限
				if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_ALL, DocRight.IDENTITYID_ALL, DocRight.RIGHTS_READ)) {
				} else {
					DocRight right1 = new DocRight();
					right1.setRightId(idGenerator.getSID());
					right1.setDocId(doc.getDocId());
					right1.setIdentityType(DocRight.IDENTITYTYPE_ALL);
					right1.setDoc(doc);
					right1.setIdentityId(DocRight.IDENTITYID_ALL);
					right1.setRights(DocRight.RIGHTS_READ);
					docRightManager.create(right1);
				}
			}

			// 保存编辑权限
			if (StringUtils.isNotBlank(editable[0])) {
				for (int i = 0; i < editable.length; i++) {
					if (editable[i].contains("user")) {
						String editPerson = editable[i].split("_")[0];
						if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_USER, editPerson, DocRight.RIGHTS_EDIT)) {
							continue;
						}
						DocRight right1 = new DocRight();
						right1.setRightId(idGenerator.getSID());
						right1.setDocId(doc.getDocId());
						right1.setIdentityType(DocRight.IDENTITYTYPE_USER);
						right1.setDoc(doc);
						right1.setIdentityId(editPerson);
						right1.setRights(DocRight.RIGHTS_EDIT);
						docRightManager.create(right1);
					} else if (editable[i].contains("group")) {
						String editGroup = editable[i].split("_")[0];
						if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_GROUP, editGroup, DocRight.RIGHTS_EDIT)) {
							continue;
						}
						DocRight right1 = new DocRight();
						right1.setRightId(idGenerator.getSID());
						right1.setDocId(doc.getDocId());
						right1.setIdentityType(DocRight.IDENTITYTYPE_GROUP);
						right1.setDoc(doc);
						right1.setIdentityId(editGroup);
						right1.setRights(DocRight.RIGHTS_EDIT);
						docRightManager.create(right1);
					}
				}
			}

			// 保存删除权限
			if (StringUtils.isNotBlank(delable[0])) {
				for (int i = 0; i < delable.length; i++) {
					if (delable[i].contains("user")) {
						String delPerson = delable[i].split("_")[0];
						if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_USER, delPerson, DocRight.RIGHTS_DEL)) {
							continue;
						}
						DocRight right1 = new DocRight();
						right1.setRightId(idGenerator.getSID());
						right1.setDocId(doc.getDocId());
						right1.setIdentityType(DocRight.IDENTITYTYPE_USER);
						right1.setDoc(doc);
						right1.setIdentityId(delPerson);
						right1.setRights(DocRight.RIGHTS_DEL);
						docRightManager.create(right1);
					} else if (delable[i].contains("group")) {
						String delGroup = delable[i].split("_")[0];
						if (docRightManager.getByDocIdIdentityIdRight(doc.getDocId(), DocRight.IDENTITYTYPE_GROUP, delGroup, DocRight.RIGHTS_DEL)) {
							continue;
						}
						DocRight right1 = new DocRight();
						right1.setRightId(idGenerator.getSID());
						right1.setDocId(doc.getDocId());
						right1.setIdentityType(DocRight.IDENTITYTYPE_GROUP);
						right1.setDoc(doc);
						right1.setIdentityId(delGroup);
						right1.setRights(DocRight.RIGHTS_DEL);
						docRightManager.create(right1);
					}
				}
			}
		}

		String msg = null;
		if (StringUtils.isEmpty(doc.getDocId())) {
			doc.setDocId(idGenerator.getSID());
			docManager.create(doc);
			msg = getMessage("doc.created", new Object[] { doc.getName() }, "文件成功创建!");
		} else {

			docManager.update(doc);
			msg = getMessage("doc.updated", new Object[] { doc.getName() }, "文件成功更新!");
		}
		// saveOpMessage(request, msg);
		jsonObject.put("success", true);
		jsonObject.put("docId", doc.getDocId());
		return jsonObject;
	}

}
