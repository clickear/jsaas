package com.redxun.oa.calendar.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.calendar.entity.WorkCalendar;
import com.redxun.oa.calendar.manager.WorkCalendarManager;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * [WorkCalendar]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/calendar/workCalendar/")
public class WorkCalendarFormController extends BaseFormController {

    @Resource
    private WorkCalendarManager workCalendarManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("workCalendar")
    public WorkCalendar processForm(HttpServletRequest request) {
        String calenderId = request.getParameter("calenderId");
        WorkCalendar workCalendar = null;
        if (StringUtils.isNotEmpty(calenderId)) {
            workCalendar = workCalendarManager.get(calenderId);
        } else {
            workCalendar = new WorkCalendar();
        }

        return workCalendar;
    }
    /**
     * 保存实体数据
     * @param request
     * @param workCalendar
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("workCalendar") @Valid WorkCalendar workCalendar, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(workCalendar.getCalenderId())) {
            workCalendar.setCalenderId(idGenerator.getSID());
            workCalendarManager.create(workCalendar);
            msg = getMessage("workCalendar.created", new Object[]{workCalendar.getIdentifyLabel()}, "[WorkCalendar]成功创建!");
        } else {
            workCalendarManager.update(workCalendar);
            msg = getMessage("workCalendar.updated", new Object[]{workCalendar.getIdentifyLabel()}, "[WorkCalendar]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

