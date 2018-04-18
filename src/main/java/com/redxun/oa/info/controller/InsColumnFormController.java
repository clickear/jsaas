package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsColType;
import com.redxun.oa.info.entity.InsColumn;
import com.redxun.oa.info.entity.InsPortCol;
import com.redxun.oa.info.entity.InsPortal;
import com.redxun.oa.info.manager.InsColTypeManager;
import com.redxun.oa.info.manager.InsColumnManager;
import com.redxun.oa.info.manager.InsPortalManager;

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
 * [InsColumn]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insColumn/")
public class InsColumnFormController extends BaseFormController {

    @Resource
    private InsColumnManager insColumnManager;
    @Resource
    private InsColTypeManager insColTypeManager;
    @Resource
    private InsPortalManager insPortalManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insColumn")
    public InsColumn processForm(HttpServletRequest request) {
        String colId = request.getParameter("colId");
        InsColumn insColumn = null;
        if (StringUtils.isNotEmpty(colId)) {
            insColumn = insColumnManager.get(colId);
        } else {
            insColumn = new InsColumn();
        }

        return insColumn;
    }
    /**
     * 保存实体数据
     * @param request
     * @param insColumn
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insColumn") @Valid InsColumn insColumn, BindingResult result) {
    	String colTypeId = request.getParameter("colType");
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(insColumn.getColId())) {
        	InsColType insColType = insColTypeManager.get(colTypeId);
            insColumn.setColId(idGenerator.getSID());
            insColumn.setInsColType(insColType);
            insColumn.setColType(insColType.getName());
            insColumnManager.create(insColumn);
            msg = getMessage("insColumn.created", new Object[]{insColumn.getName()}, "栏目成功创建!");
        } else {
        	InsColType insColType = insColTypeManager.get(colTypeId);
        	insColumn.setInsColType(insColType);
        	insColumn.setColType(insColType.getName());
            insColumnManager.update(insColumn);
            msg = getMessage("insColumn.updated", new Object[]{insColumn.getName()}, "栏目成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
    
    /**
     * 新增栏目的保存。在ShowEdit和PersonShowEdit页面点击新增栏目insColumnNewCol的保存
     * @param request
     * @param insColumn
     * @param result
     * @return
     */
    @RequestMapping(value = "saveByPort", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveByPort(HttpServletRequest request, @ModelAttribute("insColumn") @Valid InsColumn insColumn, BindingResult result) {
    	String colTypeId = request.getParameter("colType");//得到栏目类型Id
    	String portId = request.getParameter("portId");//门户Id
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        InsPortal insPortal = insPortalManager.get(portId);
        if (StringUtils.isEmpty(insColumn.getColId())) {//新建一个栏目
        	InsColType insColType = insColTypeManager.get(colTypeId);
            insColumn.setColId(idGenerator.getSID());
            insColumn.setInsColType(insColType);
            insColumn.setColType(insColType.getName());
            insColumnManager.create(insColumn);
            msg = getMessage("insColumn.created", new Object[]{insColumn.getName()}, "栏目成功创建!");
        } 
        //更新栏目，暂时不需要
        /*else {
        	InsColType insColType = insColTypeManager.get(colTypeId);
        	insColumn.setInsColType(insColType);
        	insColumn.setColType(insColType.getName());
            insColumnManager.update(insColumn);
            msg = getMessage("insColumn.updated", new Object[]{insColumn.getIdentifyLabel()}, "[InsColumn]成功更新!");
        }*/
        
        //建立新的中间表，将新建的栏目与当前门户联系起来
        InsPortCol portcol = new InsPortCol();
		portcol.setConfId(idGenerator.getSID());
		portcol.setPortId(portId);
		portcol.setColId(insColumn.getColId());
		portcol.setHeight(300);
		portcol.setHeightUnit("px");
		portcol.setSn(99);
		portcol.setColNum(0);
		insPortal.getInsPortCols().add(portcol);
		insPortalManager.update(insPortal);
        
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

