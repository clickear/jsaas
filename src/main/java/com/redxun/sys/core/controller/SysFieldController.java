package com.redxun.sys.core.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.BeanUtil;
import com.redxun.saweb.controller.BaseController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysField;
import com.redxun.sys.core.entity.SysFieldNode;
import com.redxun.sys.core.entity.SysFormField;
import com.redxun.sys.core.entity.SysFormGroup;
import com.redxun.sys.core.entity.SysFormSchema;
import com.redxun.sys.core.entity.SysModule;
import com.redxun.sys.core.enums.EntityRelationType;
import com.redxun.sys.core.manager.SysFieldManager;
import com.redxun.sys.core.manager.SysFormSchemaManager;
import com.redxun.sys.core.manager.SysModuleManager;
/**
 * <pre> 
 * 描述：SysField控制层处理类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@mitom.cn
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Controller
@RequestMapping("/sys/core/sysField/")
public class SysFieldController extends BaseController {
	
    @Resource
    private SysFieldManager sysFieldManager;
    @Resource
    private SysModuleManager sysModuleManager;
    @Resource
    private SysFormSchemaManager sysFormSchemaManager;
    
    
    /**
     * 获取SysField信息列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
        java.util.List<SysField> sysFieldList=sysFieldManager.getAll(queryFilter);
        return getPathView(request).addObject("sysFieldList",sysFieldList);
    }
    
    /**
     * 取得模块下的所有字段列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("getByModuleId")
    @ResponseBody
    public JsonPageResult<SysField> getByModuleId(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String moduleId=request.getParameter("moduleId");
    	List<SysField> fields= sysFieldManager.getByModuleId(moduleId);
    	return new JsonPageResult<SysField>(fields, fields.size());
    }
    /**
     * 编辑单个SysField的明细信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request) throws Exception {
    	String fieldId=request.getParameter("pk");
    	SysField sysField=null;
    	if(StringUtils.isNotEmpty(fieldId)){
    		sysField=sysFieldManager.get(fieldId);
    	}else{
    		sysField=new SysField();
    	}
        return getPathView(request).addObject("sysField", sysField);
    }
    
    /**
     * 删除SysField
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
    	String fieldId=request.getParameter("pk");
    	sysFieldManager.delete(fieldId);
    	String msg=getMessage("sysField.deleted","成功删除[SysField]！");
    	saveOpMessage(request, msg);
    	return new JsonResult(true,msg);
    }
    
    /**
     * 删除多个所选SysField
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("mulDel")
    @ResponseBody
    public JsonResult mulDel(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String msg="";
    	String pks=request.getParameter("pks");
    	if(StringUtils.isNotEmpty(pks)){
    		String[]ids=pks.split("[,]");
    		sysFieldManager.deleteByPks(ids);
    		msg=getMessage("sysField.mul.deleted","成功删除所选择[SysField]！");
    		saveOpMessage(request,msg);
    	}
    	return new JsonResult(true,msg);
    }
   
    /**
     * 获取单个SysField的明细信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request) throws Exception {
    	String fieldId=request.getParameter("pkId");
    	SysField sysField=sysFieldManager.get(fieldId);
    	return getPathView(request).addObject("sysField",sysField);
    }


}
