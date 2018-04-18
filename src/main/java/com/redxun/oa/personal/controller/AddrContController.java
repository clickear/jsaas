package com.redxun.oa.personal.controller;

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
import com.redxun.oa.personal.entity.AddrCont;
import com.redxun.oa.personal.manager.AddrContManager;
import com.redxun.saweb.controller.TenantListController;

/**
 *通讯录联系人信息Controller
 * 
 * @author zwj
 *  用途：处理对联系人信息相关操作的请求映射
 */

@Controller
@RequestMapping("/oa/personal/addrCont/")
public class AddrContController extends TenantListController{
    @Resource
    AddrContManager addrContManager;
  
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                addrContManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
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
        String pkId=request.getParameter("pkId");
        AddrCont addrCont=null;
        if(StringUtils.isNotEmpty(pkId)){
           addrCont=addrContManager.get(pkId);
        }else{
        	addrCont=new AddrCont();
        }
        return getPathView(request).addObject("addrCont",addrCont);
    }
    
    /**
     * 编辑通讯录联系人信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	AddrCont addrCont=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		addrCont=addrContManager.get(pkId);
    		if("true".equals(forCopy)){
    			addrCont.setContId(null);
    		}
    	}else{
    		addrCont=new AddrCont();
    	}
    	return getPathView(request).addObject("addrCont",addrCont);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return addrContManager;
	}

}
