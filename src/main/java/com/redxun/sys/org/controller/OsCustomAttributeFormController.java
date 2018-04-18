
package com.redxun.sys.org.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.org.entity.OsCustomAttribute;
import com.redxun.sys.org.manager.OsCustomAttributeManager;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.redxun.saweb.util.IdUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.redxun.sys.log.LogEnt;

/**
 * 自定义属性 管理
 * @author mansan
 */
@Controller
@RequestMapping("/sys/org/osCustomAttribute/")
public class OsCustomAttributeFormController extends BaseFormController {

    @Resource
    private OsCustomAttributeManager osCustomAttributeManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("osCustomAttribute")
    public OsCustomAttribute processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        OsCustomAttribute osCustomAttribute = null;
        if (StringUtils.isNotEmpty(id)) {
            osCustomAttribute = osCustomAttributeManager.get(id);
        } else {
            osCustomAttribute = new OsCustomAttribute();
        }

        return osCustomAttribute;
    }
    /**
     * 保存实体数据
     * @param request
     * @param orders
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @LogEnt(action = "save", module = "sys", submodule = "自定义属性")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("osCustomAttribute") @Valid OsCustomAttribute osCustomAttribute, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(osCustomAttribute.getID())) {
            osCustomAttribute.setID(IdUtil.getId());
            osCustomAttributeManager.create(osCustomAttribute);
            msg = getMessage("osCustomAttribute.created", new Object[]{osCustomAttribute.getIdentifyLabel()}, "[自定义属性]成功创建!");
        } else {
            osCustomAttributeManager.update(osCustomAttribute);
            msg = getMessage("osCustomAttribute.updated", new Object[]{osCustomAttribute.getIdentifyLabel()}, "[自定义属性]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

