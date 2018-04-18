<#import "function.ftl" as func>
<#assign package=model.variables.package>
<#assign class=model.variables.class>
<#assign classVar=model.variables.classVar>
<#assign comment=model.tabComment>
<#assign pk=func.getPk(model) >
<#assign pkModel=model.pkModel >
<#assign pkVar=func.convertUnderLine(pk) >
<#assign pkType=func.getPkType(model)>
<#assign fkType=func.getFkType(model)>
<#assign system=vars.system>
<#assign domain=vars.domain>
<#assign tableName=model.tableName>
<#assign colList=model.columnList>
<#assign commonList=model.commonList>

package ${domain}.${system}.${package}.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import ${domain}.core.json.JsonResult;
import ${domain}.core.manager.ExtBaseManager;
import ${domain}.core.query.QueryFilter;
import ${domain}.saweb.util.RequestUtil;
import ${domain}.saweb.controller.BaseMybatisListController;
import ${domain}.saweb.util.QueryFilterBuilder;
import ${domain}.${system}.${package}.entity.${class};
import ${domain}.${system}.${package}.manager.${class}Manager;
import ${domain}.sys.log.LogEnt;

/**
 * ${comment}控制器
 * @author ${vars.developer}
 */
@Controller
@RequestMapping("/${system}/${package}/${classVar}/")
public class ${class}Controller extends BaseMybatisListController{
    @Resource
    ${class}Manager ${classVar}Manager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "${system}", submodule = "${comment}")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                ${classVar}Manager.delete(id);
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
        ${class} ${classVar}=null;
        if(StringUtils.isNotEmpty(pkId)){
           ${classVar}=${classVar}Manager.get(pkId);
        }else{
        	${classVar}=new ${class}();
        }
        return getPathView(request).addObject("${classVar}",${classVar});
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	${class} ${classVar}=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		${classVar}=${classVar}Manager.get(pkId);
    	}else{
    		${classVar}=new ${class}();
    	}
    	return getPathView(request).addObject("${classVar}",${classVar});
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
    public ${class} getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String uId=RequestUtil.getString(request, "ids");    	
        ${class} ${classVar} = ${classVar}Manager.get${class}(uId);
        return ${classVar};
    }
    
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @LogEnt(action = "save", module = "${system}", submodule = "${comment}")
    public JsonResult save(HttpServletRequest request, @RequestBody ${class} ${classVar}, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(${classVar}.getId())) {
            ${classVar}Manager.create(projectInfo);
            msg = getMessage("${classVar}.created", new Object[]{${classVar}.getIdentifyLabel()}, "[${comment}]成功创建!");
        } else {
            projectInfoManager.update(${classVar});
            msg = getMessage("${classVar}.updated", new Object[]{${classVar}.getIdentifyLabel()}, "[${comment}]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return ${classVar}Manager;
	}

}
