package com.redxun.oa.info.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.redxun.core.query.SortParam;
import com.redxun.core.util.StringUtil;
import com.redxun.kms.core.entity.KdCoverImage;
import com.redxun.kms.core.entity.KdDoc;
import com.redxun.kms.core.manager.KdDocManager;
import com.redxun.oa.info.dao.InsNewsColumnQueryDao;
import com.redxun.oa.info.dao.InsNewsQueryDao;
import com.redxun.oa.info.entity.Comment;
import com.redxun.oa.info.entity.InsColNew;
import com.redxun.oa.info.entity.InsColNewDef;
import com.redxun.oa.info.entity.InsColumn;
import com.redxun.oa.info.entity.InsNews;
import com.redxun.oa.info.entity.InsNewsCm;
import com.redxun.oa.info.entity.InsNewsColumn;
import com.redxun.oa.info.entity.InsNewsCtl;
import com.redxun.oa.info.entity.ReplyComment;
import com.redxun.oa.info.entity.SortReply;
import com.redxun.oa.info.manager.InsColNewDefManager;
import com.redxun.oa.info.manager.InsColNewManager;
import com.redxun.oa.info.manager.InsColumnDefManager;
import com.redxun.oa.info.manager.InsColumnManager;
import com.redxun.oa.info.manager.InsNewsCmManager;
import com.redxun.oa.info.manager.InsNewsCtlManager;
import com.redxun.oa.info.manager.InsNewsManager;
import com.redxun.oa.mail.entity.MailFolder;
import com.redxun.oa.mail.manager.MailFolderManager;
import com.redxun.org.api.context.ProfileUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 * 新闻信息管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insNews/")
public class InsNewsController extends TenantListController {
	@Resource
	InsNewsManager insNewsManager;
	@Resource
	InsColNewManager insColNewManager;
	@Resource
	InsColumnManager insColumnManager;
	@Resource
	InsNewsCmManager insNewsCmManager;
	@Resource
	MailFolderManager mailFolderManager;
	@Resource
	KdDocManager kdDocManager;
	@Resource
	InsColNewDefManager insColNewDefManager;
	@Resource
	InsColumnDefManager insColumnDefManager;
	@Resource
	InsNewsCtlManager insNewsCtlManager;
	@Resource
	InsNewsQueryDao insNewsQueryDao;
	@Resource
	InsNewsColumnQueryDao insNewsColumnQueryDao;

	/**
	 * 新闻发布页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("publish")
	public ModelAndView publish(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String newIds = request.getParameter("pkId");
		String[] pkIds = newIds.split(",");
		StringBuffer newId = new StringBuffer();
		StringBuffer newTitle = new StringBuffer();
		for (int i = 0; i < pkIds.length; i++) {
			InsNews insNews = insNewsManager.get(pkIds[i]);
			if ("Draft".equals(insNews.getStatus())) {
				newId.append(insNews.getNewId()).append(",");
				newTitle.append(insNews.getSubject()).append(",");
			}
		}
		if (newId.length() > 0) {
			newId.deleteCharAt(newId.length() - 1);
			newTitle.deleteCharAt(newTitle.length() - 1);
		}
		return getPathView(request).addObject("newId", newId.toString()).addObject("newTitle", newTitle.toString());
	}

	/**
	 * 按栏目ID获得栏目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("byColId")
	public ModelAndView byColId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String colId = request.getParameter("colId");
		InsNewsColumn column= insNewsColumnQueryDao.get(colId);
		return getPathView(request).addObject("colId", colId).addObject("column", column);
	}

	@RequestMapping("inMail")
	public ModelAndView inMail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		MailFolder mailFolder = mailFolderManager.getInnerMailFolderByUserIdAndType(userId, MailFolder.TYPE_RECEIVE_FOLDER);

		ModelAndView view = getPathView(request);
		if (mailFolder != null) {
			view.addObject("folderId", mailFolder.getFolderId());
		}

		return view;
	}

	/**
	 * 首页的知识文档栏目显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("kdDoc")
	public ModelAndView kdDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

		ModelAndView view = getPathView(request);
		return view.addObject("remDoc", remDoc);
	}

	/**
	 * Portal门户中新闻Panel显示的List
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByColId")
	@ResponseBody
	public JsonPageResult<InsNews> listByColId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String colId = request.getParameter("colId");
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("COLUMN_ID_", colId);
		List<InsNews> insNews = insNewsQueryDao.getByColId(queryFilter);
		return new JsonPageResult<InsNews>(insNews, queryFilter.getPage().getTotalItems());
	}

	/**
	 * 在栏目中加入多个消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("joinColumn")
	@ResponseBody
	public JsonResult joinColumn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String colId = request.getParameter("colId");
		InsColumn column = insColumnManager.get(colId);
		String newsIds = request.getParameter("newsIds");
		String[] nIds = newsIds.split("[,]");
		int i = 1;
		for (String nId : nIds) {

			InsColNew insColNew = insColNewManager.getByColIdNewId(colId, nId);
			if (insColNew != null) {
				continue;
			}

			InsNews news = insNewsManager.get(nId);
			InsColNew cn = new InsColNew();
			cn.setStartTime(new Date());
			Calendar cal = Calendar.getInstance();
			// 默认为有效期两年
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 2);
			cn.setEndTime(cal.getTime());
			cn.setInsNews(news);
			cn.setInsColumn(column);
			cn.setSn(i++);
			insColNewManager.create(cn);
		}
		return new JsonResult(true, "成功加入!");
	}

	/*@RequestMapping("addReadTimes")
	@ResponseBody
	public JsonResult addReadTimes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		InsNews insNew = insNewsManager.get(pkId);
		int readTime = insNew.getReadTimes();
		readTime += 1;
		insNew.setReadTimes(readTime);
		insNewsManager.update(insNew);
		return new JsonResult(true, "成功加入!");
	}*/

	/**
	 * 获得正在发布中的信息供用户进行选择
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getValids")
	@ResponseBody
	public JsonPageResult<InsNews> getValids(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		queryFilter.addFieldParam("status", InsNews.STATUS_ISSUED);
		List<InsNews> newsList = insNewsManager.getAll(queryFilter);
		return new JsonPageResult<InsNews>(newsList, queryFilter.getPage().getTotalItems());
	}

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				insColNewDefManager.delByNewId(id);
				insNewsManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 获得这条评论下面的所有子评论
	 * 
	 * @param rplId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InsNewsCm> findAllReply(String rplId) {
		List<InsNewsCm> rplCms = new ArrayList<InsNewsCm>();
		if (insNewsCmManager.getByReplyId(rplId).size() != 0) {// 判断这条评论下是否有子评论
			for (InsNewsCm rplcm : insNewsCmManager.getByReplyId(rplId)) {// 遍历所有子评论
				rplCms.add(rplcm);// 将子评论加入所有子评论的List中
				rplCms.addAll(findAllReply(rplcm.getCommId()));// 再次判断这条子评论是否有子评论,如果有就加入
			}
		}
		SortReply sort = new SortReply();
		Collections.sort(rplCms, sort);// 按照时间排序
		return rplCms;
	}

	/**
	 * 查看明细,从portal点击和从管理页面进入时不一样的
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String permit = request.getParameter("permit");// 获得权限,是否从编辑页面进入,具有删除权限的页面需设置权限为yes
		String pkId = request.getParameter("pkId");// 新闻Id
		String user = ContextUtil.getCurrentUser().getFullname();// 当前用户名

		// 增加阅读次数
		InsNews insNew = insNewsManager.get(pkId);
		int readTime = insNew.getReadTimes();
		readTime += 1;
		insNew.setReadTimes(readTime);
		insNewsManager.update(insNew);
		insNewsManager.flush();

		List<InsNewsCm> replyCms = null;// 一个用于装子评论的List
		List<InsNewsCm> insNewsCms = insNewsCmManager.getByNewId(pkId);// 该新闻的所有评论
		List<Comment> comments = new ArrayList<Comment>();// 回复评论类
		for (InsNewsCm insNewsCm : insNewsCms) {// 遍历该新闻的所有评论
			// 判断是否是子回复
			if ("no".equals(insNewsCm.getIsReply())) {// 不是子评论
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format(insNewsCm.getCreateTime());// 设置时间格式
				String content = insNewsCm.getContent().replace("\r\n", "<br>");// 将换行的代码换成页面格式
				replyCms = findAllReply(insNewsCm.getCommId());// 获得这条不是子评论的所有的子评论
				List<ReplyComment> replyComments = new ArrayList<ReplyComment>();// 子评论的类
				for (InsNewsCm replyCm : replyCms) {// 遍历当前评论的所有子评论
					String replyData = formatter.format(replyCm.getCreateTime());// 设置时间格式
					String replycontent = replyCm.getContent().replace("\r\n", "<br>");// 换行
					// 新建子评论类,然后放入当前评论的所有子评论的List中
					ReplyComment rplcom = new ReplyComment(replyCm.getCommId(), replyCm.getFullName(), replyData, replycontent, insNewsCmManager.get(replyCm.getRepId()).getFullName(), replyCm.getRepId());
					replyComments.add(rplcom);
				}
				// 新建评论类,将子评论装入当前评论中
				Comment comment = new Comment(insNewsCm.getCommId(), insNewsCm.getFullName(), dateString, content, replyComments);
				comments.add(comment);
			}
		}
		
		boolean isDown = getDownCtl(pkId);
		boolean isCheck = getCheckCtl(pkId);
		
		ModelAndView mv = getPathView(request);
		InsNews insNews = null;
		if (StringUtils.isNotBlank(pkId)) {
			insNews = insNewsManager.get(pkId);
		} else {
			insNews = new InsNews();
		}
		mv.addObject("permit", permit).addObject("isDown", isDown).addObject("isCheck", isCheck);
		mv.addObject("user", user);
		mv.addObject("comments", iJson.toJson(comments));
		return mv.addObject("insNews", insNews);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		ModelAndView mv = getPathView(request);
		StringBuffer colIds = new StringBuffer();
		StringBuffer colNames = new StringBuffer();
		InsNews insNews = null;
		if (StringUtils.isNotEmpty(pkId)) {
			insNews = insNewsManager.get(pkId);
			QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
			queryFilter.addSortParam("createTime", SortParam.SORT_DESC);
			queryFilter.addParam("newId", pkId);
			List<InsColNewDef> defs = insColNewDefManager.getByNewId(queryFilter);
			for(InsColNewDef def:defs){
				colIds.append(def.getColId()).append(",");		
			}
			if(colIds.length()>0){
				colIds.deleteCharAt(colIds.length()-1);
			}
			mv.addObject("colIds", colIds);
		} else {
			insNews = new InsNews();
		}
		// InsColNew insColNew = insColNewManager.getByColIdNewId(pkId, pkId);
		return mv.addObject("insNews", insNews);
	}

	@RequestMapping("getAll")
	@ResponseBody
	public List<InsNews> getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<InsNews> insNews = insNewsManager.getAll();
		return insNews;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return insNewsManager;
	}
	
	private boolean getDownCtl(String newsId){
		boolean isDown = false;
		String userId = ContextUtil.getCurrentUserId();
		Map<String,Set<String>> profiles = ProfileUtil.getCurrentProfile();
		Set<String> groupIds = profiles.get("group");
		Set<String> subGroupIds = profiles.get("subGroup");
		InsNewsCtl downCtl = insNewsCtlManager.getByRightNewsId(InsNewsCtl.CTL_RIGHT_DOWN, newsId);
		if(downCtl ==null || InsNewsCtl.CTL_TYPE_ALL.equals(downCtl.getType())) return true;
		String ctlUserIds = downCtl.getUserId();
		String ctlGroupIds = downCtl.getGroupId();
		if(StringUtil.isNotEmpty(ctlUserIds)){
			String[] userIdArr = ctlUserIds.split(",");
			for(int i=0;i<userIdArr.length;i++){
				if(userId.equals(userIdArr[i])){
					isDown = true;
					return isDown;
				}
			}
		}
		
		if(StringUtil.isNotEmpty(ctlGroupIds)){
			String[] groupIdArr = ctlGroupIds.split(",");
			for(int i=0;i<groupIdArr.length;i++){
				for(String groupId:groupIds){
					if(groupId.equals(groupIdArr[i])){
						isDown = true;
						return isDown;
					}
				}
				for(String subGroupId:subGroupIds){
					if(subGroupId.equals(groupIdArr[i])){
						isDown = true;
						return isDown;
					}
				}
			}
		}
		
		return isDown;
	}
	
	private boolean getCheckCtl(String newsId){
		boolean isPrint = false;
		String userId = ContextUtil.getCurrentUserId();
		Map<String,Set<String>> profiles = ProfileUtil.getCurrentProfile();
		Set<String> groupIds = profiles.get("group");
		Set<String> subGroupIds = profiles.get("subGroup");
		InsNewsCtl checkCtl = insNewsCtlManager.getByRightNewsId(InsNewsCtl.CTL_RIGHT_CHECK, newsId);
		if(checkCtl==null || InsNewsCtl.CTL_TYPE_ALL.equals(checkCtl.getType())) return true;
		String ctlUserIds = checkCtl.getUserId();
		String ctlGroupIds = checkCtl.getGroupId();
		if(StringUtil.isNotEmpty(ctlUserIds)){
			String[] userIdArr = ctlUserIds.split(",");
			for(int i=0;i<userIdArr.length;i++){
				if(userId.equals(userIdArr[i])){
					isPrint = true;
					return isPrint;
				}
			}
		}
		
		if(StringUtil.isNotEmpty(ctlGroupIds)){
			String[] groupIdArr = ctlGroupIds.split(",");
			for(int i=0;i<groupIdArr.length;i++){
				for(String groupId:groupIds){
					if(groupId.equals(groupIdArr[i])){
						isPrint = true;
						return isPrint;
					}
				}
				for(String subGroupId:subGroupIds){
					if(subGroupId.equals(groupIdArr[i])){
						isPrint = true;
						return isPrint;
					}
				}
			}
		}
		
		return isPrint;
	}

}
