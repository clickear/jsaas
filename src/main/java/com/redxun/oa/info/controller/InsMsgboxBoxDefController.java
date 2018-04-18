
package com.redxun.oa.info.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.entity.InsMsgDef;
import com.redxun.oa.info.entity.InsMsgboxBoxDef;
import com.redxun.oa.info.manager.InsMsgDefManager;
import com.redxun.oa.info.manager.InsMsgboxBoxDefManager;
import com.redxun.oa.info.manager.InsMsgboxDefManager;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;

/**
 * INS_MSGBOX_BOX_DEF控制器
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insMsgboxBoxDef/")
public class InsMsgboxBoxDefController extends BaseMybatisListController{
    @Resource
    InsMsgboxBoxDefManager insMsgboxBoxDefManager;
    @Resource
    InsMsgboxDefManager insMsgboxDefManager;
    @Resource
    InsMsgDefManager insMsgManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                insMsgboxBoxDefManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除!");
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
        String pkId=RequestUtil.getString(request, "pkId");
        InsMsgboxBoxDef insMsgboxBoxDef=null;
        if(StringUtils.isNotEmpty(pkId)){
           insMsgboxBoxDef=insMsgboxBoxDefManager.get(pkId);
        }else{
        	insMsgboxBoxDef=new InsMsgboxBoxDef();
        }
        return getPathView(request).addObject("insMsgboxBoxDef",insMsgboxBoxDef);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	InsMsgboxBoxDef insMsgboxBoxDef=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		insMsgboxBoxDef=insMsgboxBoxDefManager.get(pkId);
    		if("true".equals(forCopy)){
    			insMsgboxBoxDef.setId(null);
    		}
    	}else{
    		insMsgboxBoxDef=new InsMsgboxBoxDef();
    	}
    	return getPathView(request).addObject("insMsgboxBoxDef",insMsgboxBoxDef);
    }
    
    /**
     * 有子表的情况下编辑明细的json
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("getJson")
    @ResponseBody
    public String getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String uId=RequestUtil.getString(request, "ids");    	
        InsMsgboxBoxDef insMsgboxBoxDef = insMsgboxBoxDefManager.getInsMsgboxBoxDef(uId);
        String json = JSONObject.toJSONString(insMsgboxBoxDef);
        return json;
    }
    
    @RequestMapping("getByBoxId")
    @ResponseBody
    public List<InsMsgboxBoxDef> getByBoxId(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String boxId = RequestUtil.getString(request, "boxId");
    	List<InsMsgboxBoxDef> boxs = insMsgboxBoxDefManager.getByBoxId(boxId);
    	for(InsMsgboxBoxDef box:boxs){
    		InsMsgDef msg = insMsgManager.get(box.getMsgId());
    		if(msg!=null){
    			box.setContent(msg.getContent());
    		}    		
    	}
    	return boxs;
    }
    

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return insMsgboxBoxDefManager;
	}

}
