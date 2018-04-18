package com.redxun.oa.product.controller;

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
import com.redxun.oa.product.entity.OaProductDefParaKey;
import com.redxun.oa.product.manager.OaProductDefParaKeyManager;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeManager;

/**
 * [OaProductDefParaKey]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/product/oaProductDefParaKey/")
public class OaProductDefParaKeyController extends TenantListController{
    @Resource
    OaProductDefParaKeyManager oaProductDefParaKeyManager;
	@Resource
	SysTreeManager sysTreeManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter filter=super.getQueryFilter(request);
  		//查找分类下的模型
  		String treeId=request.getParameter("treeId");
  		if(StringUtils.isNotEmpty(treeId)){
  			SysTree sysTree=sysTreeManager.get(treeId);
  			
  			filter.addFieldParam("sysTree.path",  sysTree.getPath());
  		}
  		return filter;
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                oaProductDefParaKeyManager.delete(id);
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
        String treeName="";//分类名称
        OaProductDefParaKey oaProductDefParaKey=null;
        if(StringUtils.isNotEmpty(pkId)){
           oaProductDefParaKey=oaProductDefParaKeyManager.get(pkId);
           if(oaProductDefParaKey.getTreeId()==null){
        	   treeName="所属类已经删除!";
           }else{
           treeName=oaProductDefParaKey.getSysTree().getName();
           }
        }else{
        	oaProductDefParaKey=new OaProductDefParaKey();
        }
        return getPathView(request).addObject("oaProductDefParaKey",oaProductDefParaKey).addObject("treeName",treeName);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	OaProductDefParaKey oaProductDefParaKey=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		oaProductDefParaKey=oaProductDefParaKeyManager.get(pkId);
    		if("true".equals(forCopy)){
    			oaProductDefParaKey.setKeyId(null);
    		}
    	}else{
    		oaProductDefParaKey=new OaProductDefParaKey();
    	}
    	return getPathView(request).addObject("oaProductDefParaKey",oaProductDefParaKey);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return oaProductDefParaKeyManager;
	}

}
