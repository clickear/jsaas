package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysSeqId;
import com.redxun.sys.core.manager.SysSeqIdManager;

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
 * 系统流水号管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysSeqId/")
public class SysSeqIdFormController extends BaseFormController {

    @Resource
    private SysSeqIdManager sysSeqIdManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysSeqId")
    public SysSeqId processForm(HttpServletRequest request) {
        String seqId = request.getParameter("seqId");
        SysSeqId sysSeqId = null;
        if (StringUtils.isNotEmpty(seqId)) {
            sysSeqId = sysSeqIdManager.get(seqId);
        } else {
            sysSeqId = new SysSeqId();
        }

        return sysSeqId;
    }
    /**
     * 保存实体数据
     * @param request
     * @param sysSeqId
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysSeqId") @Valid SysSeqId sysSeqId, BindingResult result) {
    	if(StringUtils.isEmpty(sysSeqId.getSeqId())){
    		boolean isAliasExist=sysSeqIdManager.isAliasExsist(sysSeqId.getAlias(), ContextUtil.getCurrentTenantId());
    		if(isAliasExist){
    			result.rejectValue("alias", "sysSeqId.alias", "该别名"+sysSeqId.getAlias()+"已经存在！");
    		}
    	}
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysSeqId.getSeqId())) {
            sysSeqId.setSeqId(idGenerator.getSID());
            sysSeqIdManager.create(sysSeqId);
            msg = getMessage("sysSeqId.created", new Object[]{sysSeqId.getIdentifyLabel()}, "系统流水号成功创建!");
        } else {
            sysSeqIdManager.update(sysSeqId);
            msg = getMessage("sysSeqId.updated", new Object[]{sysSeqId.getIdentifyLabel()}, "系统流水号成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

