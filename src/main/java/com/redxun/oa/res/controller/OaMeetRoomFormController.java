package com.redxun.oa.res.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.json.JsonResult;
import com.redxun.oa.res.entity.OaMeetRoom;
import com.redxun.oa.res.manager.OaMeetRoomManager;
import com.redxun.saweb.controller.BaseFormController;

/**
 * 会议室管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/res/oaMeetRoom/")
public class OaMeetRoomFormController extends BaseFormController {

    @Resource
    private OaMeetRoomManager oaMeetRoomManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaMeetRoom")
    public OaMeetRoom processForm(HttpServletRequest request) {
        String roomId = request.getParameter("roomId");
        OaMeetRoom oaMeetRoom = null;
        if (StringUtils.isNotEmpty(roomId)) {
            oaMeetRoom = oaMeetRoomManager.get(roomId);
        } else {
            oaMeetRoom = new OaMeetRoom();
        }

        return oaMeetRoom;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaMeetRoom
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaMeetRoom") @Valid OaMeetRoom oaMeetRoom, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(oaMeetRoom.getRoomId())) {
            oaMeetRoom.setRoomId(idGenerator.getSID());
            oaMeetRoomManager.create(oaMeetRoom);
            msg = getMessage("oaMeetRoom.created", new Object[]{oaMeetRoom.getName()}, "会议室成功创建!");
        } else {
            oaMeetRoomManager.update(oaMeetRoom);
            msg = getMessage("oaMeetRoom.updated", new Object[]{oaMeetRoom.getName()}, "会议室成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
    
    /**
     * 保存实体数据
     * @param request
     * @param oaMeetRoom
     * @param result
     * @return
     */
    @RequestMapping(value = "saveMenu", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveMenu(HttpServletRequest request,@ModelAttribute("oaMeetRoom") @Valid OaMeetRoom oaMeetRoom, BindingResult result) {
		String data = request.getParameter("data");
			JSONArray arrs=JSONArray.fromObject(data);
			for(int i=0;i<arrs.size();i++){
			JSONObject ftObject=arrs.getJSONObject(i);
			oaMeetRoom=(OaMeetRoom) JSONObject.toBean(ftObject, OaMeetRoom.class);
			 if (StringUtils.isEmpty(oaMeetRoom.getRoomId())) {
		            oaMeetRoom.setRoomId(idGenerator.getSID());
		            oaMeetRoomManager.create(oaMeetRoom);
				} else {
		            oaMeetRoomManager.update(oaMeetRoom);
		         }
			}
   
		return new JsonResult(true,"成功保存！",oaMeetRoom);
    }
}

