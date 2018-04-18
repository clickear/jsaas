package com.redxun.saweb.util;

import com.redxun.core.query.Page;
import com.redxun.core.query.SortParam;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

/**
 * 分页工具排序类
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class PageSortUtil {
    public static Page constructPageFromRequest(HttpServletRequest request){
        Page page=new Page();
        //设置分页信息
        int pageIndex = RequestUtil.getInt(request, "pageIndex", 0);
        int pageSize = RequestUtil.getInt(request, "pageSize", Page.DEFAULT_PAGE_SIZE);
        //顺序不能调整
        page.setPageSize(pageSize);
        page.setPageIndex(pageIndex);
        
        return page;
    }
    /**
     * 获得单一的排序属性
     * @param request
     * @return 
     */
    public static SortParam constructSortParamFromRequest(HttpServletRequest request){
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        if(StringUtils.isNotEmpty(sortField)&& StringUtils.isNotEmpty(sortOrder)){
            SortParam sortParam = new SortParam(sortField, sortOrder);
            return sortParam;
        }
        return null;
    }
}
