package com.redxun.oa.res.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.res.entity.OaMeetRoom;
import com.redxun.oa.res.manager.OaMeetRoomManager;
import com.redxun.saweb.controller.TenantListController;

/**
 * 会议室管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/res/oaMeetRoom/")
public class OaMeetRoomController extends TenantListController{
    @Resource
    OaMeetRoomManager oaMeetRoomManager;

    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                oaMeetRoomManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    /**
     * 查看明细
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        OaMeetRoom oaMeetRoom=null;
        if(StringUtils.isNotEmpty(pkId)){
           oaMeetRoom=oaMeetRoomManager.get(pkId);
        }else{
        	oaMeetRoom=new OaMeetRoom();
        }
        return getPathView(request).addObject("oaMeetRoom",oaMeetRoom);
    }
    
    
    /**
     * 在新增Oameeting和编辑Oameeting时,显示所有的会议室以便选择
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("getAll")
    @ResponseBody
    public List<OaMeetRoom> getAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
    		List<OaMeetRoom> oaMeetRooms = oaMeetRoomManager.getAll();
    		return oaMeetRooms;
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	OaMeetRoom oaMeetRoom=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		oaMeetRoom=oaMeetRoomManager.get(pkId);
    		if("true".equals(forCopy)){
    			oaMeetRoom.setRoomId(null);
    		}
    	}else{
    		oaMeetRoom=new OaMeetRoom();
    	}
    	return getPathView(request).addObject("oaMeetRoom",oaMeetRoom);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return oaMeetRoomManager;
	}

}
