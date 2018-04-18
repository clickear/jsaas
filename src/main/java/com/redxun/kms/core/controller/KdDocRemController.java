package com.redxun.kms.core.controller;

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
import com.redxun.core.util.StringUtil;
import com.redxun.kms.core.entity.KdDoc;
import com.redxun.kms.core.entity.KdDocRem;
import com.redxun.kms.core.manager.KdDocManager;
import com.redxun.kms.core.manager.KdDocRemManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.manager.OsGroupManager;

/**
 * [KdDocRem]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocRem/")
public class KdDocRemController extends BaseListController {
	@Resource
	KdDocRemManager kdDocRemManager;
	@Resource
	KdDocManager kdDocManager;
	@Resource
	OsGroupManager osGroupManager;
	@Resource
	InfInnerMsgManager infInnerMsgManager;

	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				kdDocRemManager.delete(id);
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
		KdDocRem kdDocRem = null;
		if (StringUtils.isNotEmpty(pkId)) {
			kdDocRem = kdDocRemManager.get(pkId);
		} else {
			kdDocRem = new KdDocRem();
		}
		return getPathView(request).addObject("kdDocRem", kdDocRem);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		KdDocRem kdDocRem = null;
		if (StringUtils.isNotEmpty(pkId)) {
			kdDocRem = kdDocRemManager.get(pkId);
			if ("true".equals(forCopy)) {
				kdDocRem.setRemId(null);
			}
		} else {
			kdDocRem = new KdDocRem();
		}
		return getPathView(request).addObject("kdDocRem", kdDocRem);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return kdDocRemManager;
	}

	/**
	 * 推荐个人
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("submitRem")
	@ResponseBody
	public JsonResult submitRem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");	
		String[] receiveId = request.getParameter("userId").split(",");
		String content = request.getParameter("content");
		int score = Integer.parseInt(request.getParameter("score"));
		
		KdDoc kdDoc = kdDocManager.get(docId);
		String msg="您收到了一个新的知识文档推荐【" + kdDoc.getSubject() + "】！";
		
		String linkMsg=KdUtil.getLink(request, kdDoc);
		
		String userIds="";
		String groupIds="";
		
		if (StringUtils.isNotBlank(receiveId[0])) {
			for (int i = 0; i < receiveId.length; i++) {
				if (receiveId[i].contains("user")) {
					String userId = receiveId[i].split("_")[0];
					KdDocRem docRem = new KdDocRem();
					docRem.setUserId(userId);
					docRem.setLevel(score);
					docRem.setMemo(content);
					docRem.setRemId(idGenerator.getSID());
					docRem.setKdDoc(kdDoc);
					kdDocRemManager.create(docRem);
					
					userIds+=userId +",";
				}else if(receiveId[i].contains("group")){
					String groupId = receiveId[i].split("_")[0];
					KdDocRem docRem = new KdDocRem();
					docRem.setDepId(groupId);
					docRem.setLevel(score);
					docRem.setMemo(content);
					docRem.setRemId(idGenerator.getSID());
					docRem.setKdDoc(kdDoc);
					kdDocRemManager.create(docRem);
					
					groupIds+=groupId +",";
				}
			}
			
			userIds=StringUtil.trimSuffix(userIds, ",");
			groupIds=StringUtil.trimSuffix(groupIds, ",");
			infInnerMsgManager.sendMessage(userIds, groupIds, msg, linkMsg,true);
			
			return new JsonResult(true, "成功推荐！");
		} 
		return new JsonResult(true, "推荐失败！");
	}

	/**
	 * 推荐到首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("homeRem")
	@ResponseBody
	public JsonResult homeRem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");

		if (kdDocRemManager.getByIsRem(docId, "root")) {
			return new JsonResult(true, "已经推荐过了！");
		} else {
			KdDoc kdDoc = kdDocManager.get(docId);
			KdDocRem docRem = new KdDocRem();
			docRem.setRemId(idGenerator.getSID());
			docRem.setKdDoc(kdDoc);
			docRem.setRecTreeId("root");
			kdDocRemManager.create(docRem);
			
			String msg="您创建的文档【" + kdDoc.getSubject() + "】已被推荐到首页！";
			String link=KdUtil.getLink(request, kdDoc);
			infInnerMsgManager.sendMessage(kdDoc.getCreateBy(), "", msg, link,true);
			
		}
		return new JsonResult(true, "成功推荐！");
	}
	
	
	
	/**
	 * 取消推荐到首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("notRem")
	@ResponseBody
	public JsonResult notRem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		
		kdDocRemManager.delByDocId(docId);		
		return new JsonResult(true, "已取消推荐！");
	}

}
