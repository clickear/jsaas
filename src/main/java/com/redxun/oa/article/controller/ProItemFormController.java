
package com.redxun.oa.article.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.article.entity.ProItem;
import com.redxun.oa.article.manager.ProItemManager;
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
 * 项目 管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/article/proItem/")
public class ProItemFormController extends BaseFormController {

    @Resource
    private ProItemManager proItemManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("proItem")
    public ProItem processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        ProItem proItem = null;
        if (StringUtils.isNotEmpty(id)) {
            proItem = proItemManager.get(id);
        } else {
            proItem = new ProItem();
        }

        return proItem;
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
    @LogEnt(action = "save", module = "oa", submodule = "项目")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("proItem") @Valid ProItem proItem, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(proItem.getID())) {
            proItem.setID(IdUtil.getId());
            proItemManager.create(proItem);
            msg = getMessage("proItem.created", new Object[]{proItem.getIdentifyLabel()}, "[项目]成功创建!");
        } else {
            proItemManager.update(proItem);
            msg = getMessage("proItem.updated", new Object[]{proItem.getIdentifyLabel()}, "[项目]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

