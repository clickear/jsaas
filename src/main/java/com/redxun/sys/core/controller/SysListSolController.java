
package com.redxun.sys.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysListSol;
import com.redxun.sys.core.manager.SysListSolManager;

/**
 * 系统列表方案控制器
 * @author mansan
 */
@Controller
@RequestMapping("/sys/core/sysListSol/")
public class SysListSolController extends BaseMybatisListController{
    @Resource
    SysListSolManager sysListSolManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                sysListSolManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除!");
    }
    
    /**
     * 查看明细
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=RequestUtil.getString(request, "pkId");
        SysListSol sysListSol=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysListSol=sysListSolManager.get(pkId);
        }else{
        	sysListSol=new SysListSol();
        }
        return getPathView(request).addObject("sysListSol",sysListSol);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysListSol sysListSol=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysListSol=sysListSolManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysListSol.setSolId(null);
    		}
    	}else{
    		sysListSol=new SysListSol();
    	}
    	return getPathView(request).addObject("sysListSol",sysListSol);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return sysListSolManager;
	}

}
