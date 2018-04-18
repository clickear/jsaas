
package com.redxun.sys.demo.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.demo.entity.Demo;
import com.redxun.sys.demo.manager.DemoManager;
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

/**
 * Demo 管理
 * @author mansan
 */
@Controller
@RequestMapping("/sys/demo/demo/")
public class DemoFormController extends BaseFormController {

    @Resource
    private DemoManager demoManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("demo")
    public Demo processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        Demo demo = null;
        if (StringUtils.isNotEmpty(id)) {
            demo = demoManager.get(id);
        } else {
            demo = new Demo();
        }

        return demo;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("demo") @Valid Demo demo, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(demo.getId())) {
            demo.setId(IdUtil.getId());
            demoManager.create(demo);
            msg = getMessage("demo.created", new Object[]{demo.getIdentifyLabel()}, "[Demo]成功创建!");
        } else {
            demoManager.update(demo);
            msg = getMessage("demo.updated", new Object[]{demo.getIdentifyLabel()}, "[Demo]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

