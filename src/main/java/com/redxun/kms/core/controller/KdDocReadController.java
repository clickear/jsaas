package com.redxun.kms.core.controller;

import java.text.SimpleDateFormat;
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

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.kms.core.entity.KdDoc;
import com.redxun.kms.core.entity.KdDocRead;
import com.redxun.kms.core.entity.KdDocRight;
import com.redxun.kms.core.entity.KdReader;
import com.redxun.kms.core.manager.KdDocReadManager;

/**
 * [KdDocRead]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocRead/")
public class KdDocReadController extends BaseListController {
	@Resource
	KdDocReadManager kdDocReadManager;
	@Resource
	OsUserManager osUserManager;
	@Resource
	OsGroupManager osGroupManager;

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
				kdDocReadManager.delete(id);
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
		KdDocRead kdDocRead = null;
		if (StringUtils.isNotEmpty(pkId)) {
			kdDocRead = kdDocReadManager.get(pkId);
		} else {
			kdDocRead = new KdDocRead();
		}
		return getPathView(request).addObject("kdDocRead", kdDocRead);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		KdDocRead kdDocRead = null;
		if (StringUtils.isNotEmpty(pkId)) {
			kdDocRead = kdDocReadManager.get(pkId);
			if ("true".equals(forCopy)) {
				kdDocRead.setReadId(null);
			}
		} else {
			kdDocRead = new KdDocRead();
		}
		return getPathView(request).addObject("kdDocRead", kdDocRead);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return kdDocReadManager;
	}

	/**
	 * 获得当前的阅读记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getReader")
	@ResponseBody
	public JsonPageResult<KdReader> getReader(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = request.getParameter("docId");
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<KdDocRead> reads = kdDocReadManager.getReader(docId, queryFilter);
		List<KdReader> readers = new ArrayList<KdReader>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (KdDocRead read : reads) {
			KdReader reader = new KdReader();
			reader.setId(idGenerator.getSID());
			if (osUserManager.get(read.getCreateBy()) != null) {
				reader.setName(osUserManager.get(read.getCreateBy()).getFullname());
			}
			reader.setReadTime(formatter.format(read.getCreateTime()));
			reader.setStatus(read.getDocStatus());
			if (osGroupManager.getMainDeps(read.getCreateBy()) != null) {
				reader.setDepName(osGroupManager.getMainDeps(read.getCreateBy()).getName());
			}
			readers.add(reader);
		}
		JsonPageResult<KdReader> result = new JsonPageResult<KdReader>(readers, queryFilter.getPage().getTotalItems());
		return result;
	}
}
