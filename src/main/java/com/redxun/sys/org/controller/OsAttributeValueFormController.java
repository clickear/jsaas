
package com.redxun.sys.org.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.org.entity.OsAttributeValue;
import com.redxun.sys.org.manager.OsAttributeValueManager;
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
 * 人员属性值 管理
 * @author mansan
 */
@Controller
@RequestMapping("/sys/org/osAttributeValue/")
public class OsAttributeValueFormController extends BaseFormController {

    @Resource
    private OsAttributeValueManager osAttributeValueManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("osAttributeValue")
    public OsAttributeValue processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        OsAttributeValue osAttributeValue = null;
        if (StringUtils.isNotEmpty(id)) {
            osAttributeValue = osAttributeValueManager.get(id);
        } else {
            osAttributeValue = new OsAttributeValue();
        }

        return osAttributeValue;
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
    @LogEnt(action = "save", module = "sys", submodule = "人员属性值")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("osAttributeValue") @Valid OsAttributeValue osAttributeValue, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(osAttributeValue.getId())) {
            osAttributeValue.setId(IdUtil.getId());
            osAttributeValueManager.create(osAttributeValue);
            msg = getMessage("osAttributeValue.created", new Object[]{osAttributeValue.getIdentifyLabel()}, "[人员属性值]成功创建!");
        } else {
            osAttributeValueManager.update(osAttributeValue);
            msg = getMessage("osAttributeValue.updated", new Object[]{osAttributeValue.getIdentifyLabel()}, "[人员属性值]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

