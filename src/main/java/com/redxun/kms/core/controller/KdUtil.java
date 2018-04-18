package com.redxun.kms.core.controller;

import javax.servlet.http.HttpServletRequest;

import com.redxun.kms.core.entity.KdDoc;

public class KdUtil {

	/**
	 * 获取doc连接。
	 * @param request
	 * @param doc
	 * @return
	 */
	public static String getLink(HttpServletRequest  request, KdDoc doc){
		String link= "<ul><li><a class=\"subject\" href=\"javascript:void(0);\" isDoc=\"yes\"  href1=\""+request.getContextPath()+"/kms/core/kdDoc/show.do?docId=" + doc.getDocId() + "\">" + doc.getSubject() + "</a></li></ul>";
		return link;
	}
}
