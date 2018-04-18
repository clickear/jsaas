
package com.redxun.oa.info.controller;

import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.oa.info.entity.InsMsgboxBoxDef;
import com.redxun.oa.info.manager.InsMsgboxBoxDefManager;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.RequestUtil;

/**
 * INS_MSGBOX_BOX_DEF 管理
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insMsgboxBoxDef/")
public class InsMsgboxBoxDefFormController extends BaseFormController {

    @Resource
    private InsMsgboxBoxDefManager insMsgboxBoxDefManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insMsgboxBoxDef")
    public InsMsgboxBoxDef processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        InsMsgboxBoxDef insMsgboxBoxDef = null;
        if (StringUtils.isNotEmpty(id)) {
            insMsgboxBoxDef = insMsgboxBoxDefManager.get(id);
        } else {
            insMsgboxBoxDef = new InsMsgboxBoxDef();
        }

        return insMsgboxBoxDef;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insMsgboxBoxDef") @Valid InsMsgboxBoxDef insMsgboxBoxDef, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(insMsgboxBoxDef.getId())) {
            insMsgboxBoxDef.setId(IdUtil.getId());
            insMsgboxBoxDefManager.create(insMsgboxBoxDef);
            msg = getMessage("insMsgboxBoxDef.created", new Object[]{insMsgboxBoxDef.getIdentifyLabel()}, "[INS_MSGBOX_BOX_DEF]成功创建!");
        } else {
            insMsgboxBoxDefManager.update(insMsgboxBoxDef);
            msg = getMessage("insMsgboxBoxDef.updated", new Object[]{insMsgboxBoxDef.getIdentifyLabel()}, "[INS_MSGBOX_BOX_DEF]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }

    @RequestMapping("saveMsgEntity")
    @ResponseBody
    public JsonResult saveMsgEntity(HttpServletRequest request,HttpServletResponse response){
    	String data = RequestUtil.getString(request, "data");
    	String boxId = RequestUtil.getString(request, "boxId");
    	JSONArray arr = JSONArray.parseArray(data);
    	Iterator<Object> it = arr.iterator();
    	InsMsgboxBoxDef def = new InsMsgboxBoxDef();
    	insMsgboxBoxDefManager.delByBoxId(boxId);
    	while(it.hasNext()){
    		JSONObject ob = (JSONObject) it.next();
    		String sn = ob.getString("sn");
    		String msgId = ob.getString("msgId");
    		if(StringUtils.isNotEmpty(sn)&&StringUtils.isNotEmpty(msgId)){    			
    			def.setSn(sn);
    			def.setMsgId(msgId);
    			def.setBoxId(boxId);
    			def.setId(IdUtil.getId());
    			insMsgboxBoxDefManager.create(def);
    	    }
    	}
    	return new JsonResult(true);
    }
}

