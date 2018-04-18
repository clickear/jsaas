
package com.redxun.sys.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.pool.DruidDataSource;
import com.redxun.core.database.datasource.DataSourceUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.FileUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.entity.SysDataSource;
import com.redxun.sys.core.manager.SysDataSourceManager;

/**
 * 数据源定义管理控制器
 * @author ray
 */
@Controller
@RequestMapping("/sys/core/sysDataSource/")
public class SysDataSourceController extends BaseMybatisListController{
    @Resource
    SysDataSourceManager sysDataSourceManager;
   
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
            	SysDataSource datasource=sysDataSourceManager.get(id);
            	String alias=datasource.getAlias();
            	DruidDataSource ds= (DruidDataSource) DataSourceUtil.getDataSourceByAlias(alias);
            	
            	//删除时关闭数据源。
            	if(ds!=null){
            		DataSourceUtil.removeDataSource(alias);
            		ds.close();
            	}
                sysDataSourceManager.delete(id);
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
        SysDataSource sysDataSource=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysDataSource=sysDataSourceManager.get(pkId);
        }else{
        	sysDataSource=new SysDataSource();
        }
        return getPathView(request).addObject("sysDataSource",sysDataSource);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysDataSource sysDataSource=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysDataSource=sysDataSourceManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysDataSource.setId(null);
    		}
    	}else{
    		sysDataSource=new SysDataSource();
    		String json=readJson();
    		sysDataSource.setInitOnStart("yes");
    		sysDataSource.setEnabled("yes");
    		sysDataSource.setDbType("mysql");
    		sysDataSource.setSetting(json);
    		
    	}
    	return getPathView(request).addObject("sysDataSource",sysDataSource);
    }
    
    private String readJson(){
    	String path=FileUtil.getClassesPath() + "/config/dataSource.json";
		String json=FileUtil.readFile(path);
		return json;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return sysDataSourceManager;
	}
	
	@RequestMapping("testConnect")
	@ResponseBody
	public JsonResult testConnect(HttpServletRequest request, @ModelAttribute("sysDataSource") @Valid SysDataSource sysDataSource) {

        if(sysDataSourceManager.isExist(sysDataSource)){
        	return new JsonResult(false, "数据源已经存在!");
        }
        JsonResult rtn= sysDataSourceManager.checkConnection(sysDataSource);
        return rtn;
	}

}
