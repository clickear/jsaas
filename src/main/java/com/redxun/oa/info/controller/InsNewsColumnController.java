
package com.redxun.oa.info.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.oa.info.entity.InsNewsColumn;
import com.redxun.oa.info.manager.InsNewsColumnManager;
import com.redxun.sys.log.LogEnt;

/**
 * 公告栏目管理控制器
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insNewsColumn/")
public class InsNewsColumnController extends BaseMybatisListController{
    @Resource
    InsNewsColumnManager insNewsColumnManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "oa", submodule = "公告栏目管理")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                insNewsColumnManager.delete(id);
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
        InsNewsColumn insNewsColumn=null;
        if(StringUtils.isNotEmpty(pkId)){
           insNewsColumn=insNewsColumnManager.get(pkId);
        }else{
        	insNewsColumn=new InsNewsColumn();
        }
        return getPathView(request).addObject("insNewsColumn",insNewsColumn);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	InsNewsColumn insNewsColumn=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		insNewsColumn=insNewsColumnManager.get(pkId);
    	}else{
    		insNewsColumn=new InsNewsColumn();
    	}
    	return getPathView(request).addObject("insNewsColumn",insNewsColumn);
    }
    
    /**
     * 有子表的情况下编辑明细的json
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("getJson")
    @ResponseBody
    public List<InsNewsColumn> getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String tenantId=ContextUtil.getCurrentTenantId();
        List<InsNewsColumn> newsColumns = insNewsColumnManager.getAllByTenantId(tenantId);
        return newsColumns;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return insNewsColumnManager;
	}

}
