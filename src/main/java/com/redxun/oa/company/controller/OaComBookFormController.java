package com.redxun.oa.company.controller;

import java.util.HashSet;
import java.util.Set;

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
import com.redxun.kms.core.entity.KdDocRight;
import com.redxun.oa.company.entity.OaComBook;
import com.redxun.oa.company.entity.OaComRight;
import com.redxun.oa.company.manager.OaComBookManager;
import com.redxun.oa.company.manager.OaComRightManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * [OaComBook]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/company/oaComBook/")
public class OaComBookFormController extends BaseFormController {

	@Resource
	private OaComBookManager oaComBookManager;
	@Resource
	private OsGroupManager osGroupManager;
	@Resource
	private OsUserManager osUserManager;
	@Resource
	private SysTreeManager sysTreeManager;
	@Resource
	private OaComRightManager oaComRightManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("oaComBook")
	public OaComBook processForm(HttpServletRequest request) {
		String comId = request.getParameter("comId");
		OaComBook oaComBook = null;
		if (StringUtils.isNotEmpty(comId)) {
			oaComBook = oaComBookManager.get(comId);
		} else {
			oaComBook = new OaComBook();
		}

		return oaComBook;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param oaComBook
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request, @ModelAttribute("oaComBook") @Valid OaComBook oaComBook, BindingResult result) {
		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		String catId = request.getParameter("catId");
		String groupId = request.getParameter("groupId");
		if (StringUtils.isEmpty(oaComBook.getComId())) {
			oaComBook.setComId(idGenerator.getSID());
			String firstLetter = FirstLetterUtil.getFirstLetter(oaComBook.getName());
			oaComBook.setFirstLetter(firstLetter);
			oaComBook.setSysTree(sysTreeManager.get(catId));
			if (StringUtils.isNotBlank(groupId)) {
				oaComBook.setOsGroup(osGroupManager.get(groupId));
				oaComBook.setDepName(osGroupManager.get(groupId).getName());
			} else {
				oaComBook.setOsGroup(null);
			}
			if (oaComBook.getMobile2() != null && oaComBook.getMobile2().length() < 1) {
				oaComBook.setMobile2(null);
			}
			//创建权限
			OaComRight right = new OaComRight();
			Set<OaComRight> oaComRights = new HashSet<OaComRight>();
			right.setRightId(idGenerator.getSID());
			right.setIdentityType(OaComRight.IDENTITYTYPE_ALL);
			right.setOaComBook(oaComBook);
			right.setIdentityId(OaComRight.IDENTITYID_ALL);
			oaComRights.add(right);
			oaComBook.setOaComRights(oaComRights);
			
			oaComBookManager.create(oaComBook);
			msg = getMessage("oaComBook.created", new Object[] { oaComBook.getName() }, "通讯录成功创建!");
		} else {
			String firstLetter = FirstLetterUtil.getFirstLetter(oaComBook.getName());
			oaComBook.setFirstLetter(firstLetter);
			oaComBook.setComgroupId(catId);
			oaComBook.setSysTree(sysTreeManager.get(catId));
			if (StringUtils.isNotBlank(groupId)) {
				oaComBook.setOsGroup(osGroupManager.get(groupId));
				oaComBook.setMaindep(groupId);
				oaComBook.setDepName(osGroupManager.get(groupId).getName());
			} else {
				oaComBook.setOsGroup(null);
			}
			if (oaComBook.getMobile2() != null && oaComBook.getMobile2().length() < 1) {
				oaComBook.setMobile2(null);
			}
			oaComBookManager.update(oaComBook);
			msg = getMessage("oaComBook.updated", new Object[] { oaComBook.getName() }, "通讯录成功更新!");
		}
		// saveOpMessage(request, msg);
		return new JsonResult(true, msg);
	}

	@RequestMapping("multiSave")
	@ResponseBody
	public JsonResult multiSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userIds = request.getParameter("userIds");
		String catId = request.getParameter("catId");
		if (StringUtils.isNotBlank(userIds)) {
			String[] ids = userIds.split(",");
			for (String id : ids) {
				OsUser user = osUserManager.get(id);
				String mainDep = osGroupManager.getMainDeps(id).getName();
				OaComBook combook = new OaComBook();
				combook.setComId(idGenerator.getSID());
				combook.setDepName(mainDep);
				combook.setOsGroup(osGroupManager.getMainDeps(id));
				combook.setName(user.getFullname());
				combook.setMobile(user.getMobile());
				combook.setQq(user.getQq());
				combook.setEmail(user.getEmail());
				combook.setSysTree(sysTreeManager.get(catId));
				combook.setIsEmployee("YES");
				String firstLetter = FirstLetterUtil.getFirstLetter(user.getFullname());
				combook.setFirstLetter(firstLetter);
				//创建权限
				OaComRight right = new OaComRight();
				Set<OaComRight> oaComRights = new HashSet<OaComRight>();
				right.setRightId(idGenerator.getSID());
				right.setIdentityType(OaComRight.IDENTITYTYPE_ALL);
				right.setOaComBook(combook);
				right.setIdentityId(OaComRight.IDENTITYID_ALL);
				oaComRights.add(right);
				combook.setOaComRights(oaComRights);
				
				oaComBookManager.create(combook);
			}
		}

		return new JsonResult(true, "成功添加");
	}

	/**
	 * 保存权限
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveRights")
	@ResponseBody
	public JsonResult saveRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("comId");
		String userId = ContextUtil.getCurrentUserId();
		String[] readable = request.getParameter("readable").split(",");
		OaComBook combook = new OaComBook();

		if (StringUtils.isEmpty(pkId)) {
		} else {
			combook = oaComBookManager.get(pkId);
			oaComBookManager.delByUserId(pkId);

			// 保存阅读权限
			if (StringUtils.isNotBlank(readable[0])) {
				for (int i = 0; i < readable.length; i++) {
					if (readable[i].contains("user")) {
						String readPerson = readable[i].split("_")[0];
						/*
						 * if
						 * (kdDocRightManager.getByDocIdIdentityIdRight(docId,
						 * KdDocRight.IDENTITYTYPE_USER, readPerson,
						 * KdDocRight.RIGHT_READ)) { continue; }
						 */
						OaComRight right = new OaComRight();
						right.setRightId(idGenerator.getSID());
						right.setOaComBook(combook);
						right.setIdentityType(OaComRight.IDENTITYTYPE_USER);
						right.setCombookId(pkId);
						right.setIdentityId(readPerson);
						oaComRightManager.create(right);
					} else if (readable[i].contains("group")) {
						String readGroup = readable[i].split("_")[0];
						/*
						 * if
						 * (kdDocRightManager.getByDocIdIdentityIdRight(docId,
						 * KdDocRight.IDENTITYTYPE_GROUP, readGroup,
						 * KdDocRight.RIGHT_READ)) { continue; }
						 */
						OaComRight right = new OaComRight();
						right.setRightId(idGenerator.getSID());
						right.setOaComBook(combook);
						right.setIdentityType(OaComRight.IDENTITYTYPE_GROUP);
						right.setCombookId(pkId);
						right.setIdentityId(readGroup);
						oaComRightManager.create(right);
					}
				}
			} else {// 如果没有填写则保存为ALL的权限
				/*
				 * if (kdDocRightManager.getByDocIdIdentityIdRight(docId,
				 * KdDocRight.IDENTITYTYPE_ALL, KdDocRight.IDENTITYID_ALL,
				 * KdDocRight.RIGHT_READ)) { } else {
				 */
				OaComRight right = new OaComRight();
				right.setRightId(idGenerator.getSID());
				right.setCombookId(pkId);
				right.setIdentityType(OaComRight.IDENTITYTYPE_ALL);
				right.setOaComBook(combook);
				right.setIdentityId(OaComRight.IDENTITYID_ALL);
				oaComRightManager.create(right);
				// }
			}
		}
		return new JsonResult(true, "成功添加权限");
	}

}
