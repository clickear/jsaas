package com.redxun.oa.info.controller;

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
import com.redxun.oa.info.entity.InsNews;
import com.redxun.oa.info.entity.InsNewsCtl;
import com.redxun.oa.info.manager.InsColNewDefManager;
import com.redxun.oa.info.manager.InsNewsCtlManager;
import com.redxun.oa.info.manager.InsNewsManager;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.RequestUtil;

/**
 * 新闻公告管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insNews/")
public class InsNewsFormController extends BaseFormController {

	@Resource
	private InsNewsManager insNewsManager;
	@Resource
	InsColNewDefManager insColNewDefManager;
	@Resource
	InsNewsCtlManager insNewsCtlManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("insNews")
	public InsNews processForm(HttpServletRequest request) {
		String newId = request.getParameter("newId");
		InsNews insNews = null;
		if (StringUtils.isNotEmpty(newId)) {
			insNews = insNewsManager.get(newId);
		} else {
			insNews = new InsNews();
		}

		return insNews;
	}

	@RequestMapping(value = "published")
	@ResponseBody
	public JsonResult published(HttpServletRequest request, HttpServletResponse response) {
		String msg = null;
		String issuedColIds = null;
		IUser user = ContextUtil.getCurrentUser();
		String newIds = request.getParameter("newId");
		String author = user.getFullname();
		String allowCmt = request.getParameter("allowCmt");
		String keywords = request.getParameter("keywords");
		String imgFileId = request.getParameter("imgFileId");
		String content = request.getParameter("content");
		String subject = request.getParameter("subject");
		String files = request.getParameter("files");
		String multi = RequestUtil.getString(request, "multi");
		String[] pkIds = newIds.split(",");		
		for (int i = 0; i < pkIds.length; i++) {
			InsNews insNews = insNewsManager.get(pkIds[i]);
			if(StringUtils.isEmpty(pkIds[0])){
				insNews= new InsNews();
				insNews.setNewId(idGenerator.getSID());
				insNews.setReadTimes(0);
				insNews.setKeywords(keywords);
				insNews.setAllowCmt(allowCmt);
				insNews.setContent(content);
				insNews.setSubject(subject);
				insNews.setImgFileId(imgFileId);
				insNews.setStatus("Issued");
				insNews.setAuthor(author);
				insNews.setFiles(files);
				insNewsManager.create(insNews);
				createNewsCtl(insNews);
			}else{
				if(!"yes".equals(multi)){
					insNews.setFiles(files);
					insNews.setAllowCmt(allowCmt);
					insNews.setContent(content);
					insNews.setSubject(subject);
					insNews.setImgFileId(imgFileId);
				}
				insNews.setStatus("Issued");
				insNewsManager.update(insNews);
			}			
			issuedColIds = request.getParameter("issuedColIds");
			insNewsManager.doPublish(insNews, issuedColIds);
		}
		return new JsonResult(true, msg);
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param insNews
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request, @ModelAttribute("insNews") @Valid InsNews insNews, BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String issuedColIds = RequestUtil.getString(request, "issuedColIds");
		String keywords = RequestUtil.getString(request, "keywords");
		String msg = null;
		if (StringUtils.isEmpty(insNews.getNewId())) {
			insNews.setNewId(idGenerator.getSID());
			insNews.setStatus("Draft");
			insNews.setAuthor(ContextUtil.getCurrentUser().getFullname());
			insNews.setKeywords(keywords);
			insNews.setReadTimes(0);
			insNewsManager.create(insNews);
			if(StringUtils.isNotEmpty(issuedColIds)){
				insColNewDefManager.delByNewId(insNews.getNewId());
				insNewsManager.doPublish(insNews, issuedColIds);
			}
			createNewsCtl(insNews);
			msg = getMessage("insNews.created", new Object[] { insNews.getSubject() }, "新闻公告成功创建!");
		} else {
			if(StringUtils.isNotEmpty(issuedColIds)){
				insNewsManager.doPublish(insNews, issuedColIds);
			}
			insNewsManager.update(insNews);
			msg = getMessage("insNews.updated", new Object[] { insNews.getSubject() }, "新闻公告成功更新!");
		}
		/*
		 * if ("Issued".equals(insNews.getStatus())) { issuedColIds =
		 * request.getParameter("issuedColIds"); String sStartTime =
		 * request.getParameter("startTime"); String sEndTime =
		 * request.getParameter("endTime"); if
		 * (StringUtils.isNotEmpty(sStartTime)) { startTime =
		 * DateUtil.parseDate(sStartTime); } if
		 * (StringUtils.isNotEmpty(sEndTime)) { endTime =
		 * DateUtil.parseDate(sEndTime); } }
		 */
		return new JsonResult(true, msg);
	}
	
	/**
	 * 生成新闻公告权限
	 * @param news
	 */
	private void createNewsCtl(InsNews news){
		InsNewsCtl checkctl = new InsNewsCtl();
		checkctl.setCtlId(IdUtil.getId());
		checkctl.setNewsId(news.getNewId());
		checkctl.setRight(InsNewsCtl.CTL_RIGHT_CHECK);
		checkctl.setType(InsNewsCtl.CTL_TYPE_ALL);
		insNewsCtlManager.create(checkctl);
		
		InsNewsCtl downctl = new InsNewsCtl();
		downctl.setCtlId(IdUtil.getId());
		downctl.setNewsId(news.getNewId());
		downctl.setRight(InsNewsCtl.CTL_RIGHT_DOWN);
		downctl.setType(InsNewsCtl.CTL_TYPE_ALL);
		insNewsCtlManager.create(downctl);
	}
}
