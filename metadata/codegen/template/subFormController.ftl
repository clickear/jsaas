<#import "function.ftl" as func>
<#assign package=model.variables.package>
<#assign class=model.variables.class>
<#assign classVar=model.variables.classVar>
<#assign comment=model.tabComment>
<#assign subtables=model.subTableList>
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

import ${domain}.core.json.JsonResult;
import ${domain}.saweb.controller.BaseFormController;
import ${domain}.${system}.${package}.entity.${class};
import ${domain}.${system}.${package}.manager.${class}Manager;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import ${domain}.saweb.util.IdUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * ${comment} 管理
 * @author ${vars.developer}
 */
@Controller
@RequestMapping("/${system}/${package}/${classVar}/")
public class ${class}FormController extends BaseFormController {

    @Resource
    private ${class}Manager ${classVar}Manager;


    /**
     * 保存实体数据
     * @param request
     * @param orders
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @RequestBody  ${class} ${classVar}, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(${classVar}.getId())) {
            ${classVar}.set${pkVar?cap_first}(IdUtil.getId());
            ${classVar}Manager.create(${classVar});
            msg = getMessage("${classVar}.created", new Object[]{${classVar}.getIdentifyLabel()}, "[${comment}]成功创建!");
        } else {
            ${classVar}Manager.update(${classVar});
            msg = getMessage("${classVar}.updated", new Object[]{${classVar}.getIdentifyLabel()}, "[${comment}]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

