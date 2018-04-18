package com.redxun.oa.product.controller;

import java.util.ArrayList;
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
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.product.entity.OaAssets;
import com.redxun.oa.product.entity.OaKeyValue;
import com.redxun.oa.product.entity.OaProductDef;
import com.redxun.oa.product.entity.OaProductDefPara;
import com.redxun.oa.product.manager.OaAssParameterManager;
import com.redxun.oa.product.manager.OaAssetsManager;
import com.redxun.oa.product.manager.OaProductDefManager;
import com.redxun.oa.product.manager.OaProductDefParaManager;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 * [OaAssets]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/product/oaAssets/")
public class OaAssetsController extends BaseListController{
    @Resource
    OaAssetsManager oaAssetsManager;
    @Resource
    OaProductDefManager oaProductDefManager;
    @Resource
    OaProductDefParaManager oaProductDefParaManager;
    @Resource
    OaAssParameterManager oaAssParameterManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                oaAssetsManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    /**
     * 在新增和编辑资产信息时,显示所有的资产分类信息以便选择
     * @param request
     * @param response
     */
    @RequestMapping("getDefAll")
    @ResponseBody
    public List<OaProductDef> getDefAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
    		List<OaProductDef> oaMeetRooms = oaProductDefManager.getAll();
    		return oaMeetRooms;
    }
    
    /**
     * 选择产品分类的时候显示分类模块下的产品类型和产品属性
     * 
     * */
	@RequestMapping("getVKAll")
	@ResponseBody
	public JsonResult getVKAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String defId=request.getParameter("prodDefId");
		List<OaProductDefPara> oaProductDefParas=oaProductDefParaManager.getByDefId(defId);
		
		List<OaProductDefPara> listNew=new OaMyArrayList();
		List<OaKeyValue> oList=new ArrayList<OaKeyValue>();
		for(OaProductDefPara op:oaProductDefParas){
		listNew.add(op);
		}
		for(OaProductDefPara ocPara:listNew){
			OaKeyValue oaKeyValue=new OaKeyValue();
			oaKeyValue.setKeyId(ocPara.getOaProductDefParaKey().getKeyId());
			oaKeyValue.setName(ocPara.getOaProductDefParaKey().getName());
			oaKeyValue.setNumber(ocPara.getOaProductDefParaValue().getValueId());
			oaKeyValue.setNumbername(ocPara.getOaProductDefParaValue().getName());
			
			oList.add(oaKeyValue);
		}
		
		
		return new JsonResult(true,"信息返回成功",oList);
		
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
        OaAssets oaAssets=null;
        if(StringUtils.isNotEmpty(pkId)){
           oaAssets=oaAssetsManager.get(pkId);
        }else{
        	oaAssets=new OaAssets();
        }
        return getPathView(request).addObject("oaAssets",oaAssets);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String prodName=null;//产品分类名
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	OaAssets oaAssets=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		oaAssets=oaAssetsManager.get(pkId);
    		prodName=oaAssets.getOaProductDef().getName();
    		if("true".equals(forCopy)){
    			oaAssets.setAssId(null);
    		}
    	}else{
    		oaAssets=new OaAssets();
    	}
    	return getPathView(request).addObject("oaAssets",oaAssets).addObject("prodName", prodName);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return oaAssetsManager;
	}

}
