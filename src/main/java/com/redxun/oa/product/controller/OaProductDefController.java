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

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.oa.product.entity.OaKeyValue;
import com.redxun.oa.product.entity.OaProductDef;
import com.redxun.oa.product.entity.OaProductDefPara;
import com.redxun.oa.product.entity.OaProductDefParaKey;
import com.redxun.oa.product.entity.OaProductDefParaValue;
import com.redxun.oa.product.manager.OaProductDefManager;
import com.redxun.oa.product.manager.OaProductDefParaKeyManager;
import com.redxun.oa.product.manager.OaProductDefParaManager;
import com.redxun.oa.product.manager.OaProductDefParaValueManager;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeManager;

/**
 * [OaProductDef]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/product/oaProductDef/")
public class OaProductDefController extends TenantListController{
    @Resource
    OaProductDefManager oaProductDefManager;
	@Resource
	SysTreeManager sysTreeManager;
    @Resource
    OaProductDefParaKeyManager oaProductDefParaKeyManager;
    @Resource
    OaProductDefParaValueManager oaProductDefParaValueManager;
    @Resource
    OaProductDefParaManager oaProductDefParaManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter filter=super.getQueryFilter(request);
  		//查找分类下的模型
  		String treeId=request.getParameter("treeId");
  		if(StringUtils.isNotEmpty(treeId)){
  			SysTree sysTree=sysTreeManager.get(treeId);
  			
  			filter.addFieldParam("sysTree.path",  sysTree.getPath());
  		}
  		return filter;
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                oaProductDefManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    
    /**
     * 根据ValueId删除中间表下的数据
     * */
    @RequestMapping("delValueId")
    @ResponseBody
    public JsonResult delValueId(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String valueId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(valueId)){
            String[] ids=valueId.split(",");
            for(String id:ids){
            	oaProductDefParaManager.delByValueId(id);
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
        String treeName="";//分类名称
        OaProductDef oaProductDef=null;
        if(StringUtils.isNotEmpty(pkId)){
           oaProductDef=oaProductDefManager.get(pkId);
           if(oaProductDef.getTreeId()==null){
        	   treeName="所属类已经删除!";
           }else{
           treeName=oaProductDef.getSysTree().getName();
           }
        }else{
        	oaProductDef=new OaProductDef();
        }
        return getPathView(request).addObject("oaProductDef",oaProductDef).addObject("treeName",treeName);
    }
    
    /**
     * 根据分类ID获取字段
     * 
     * 
     * */
	@RequestMapping("getByTreeId")
	@ResponseBody
	public JsonPageResult<OaProductDefParaKey> getByTreeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		String treeId=request.getParameter("treeId");
		List<OaProductDefParaKey> list = new ArrayList<OaProductDefParaKey>();
		list = oaProductDefParaKeyManager.getByTreeId(treeId);

		return new JsonPageResult<OaProductDefParaKey>(list, queryFilter.getPage().getTotalItems());

	}
	
    /**
     * 获取到产品类型表的数据
     * 
     * 
     * */
	@RequestMapping("oaTreeKeyList")
	@ResponseBody
	public JsonPageResult<OaProductDefParaKey> oaTreeKeyList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		QueryFilter queryFilter=super.getQueryFilter(request);
		//查找分类下的模型
		String treeId=request.getParameter("treeId");
		if(StringUtils.isNotEmpty(treeId)){
			SysTree sysTree=sysTreeManager.get(treeId);
			if(sysTree!=null){
				queryFilter.getFieldLogic().getCommands().add(new QueryParam("sysTree.path", QueryParam.OP_LEFT_LIKE, sysTree.getPath()));
			}
		}
		queryFilter.addSortParam("createTime", "desc");
		List<OaProductDefParaKey> list=oaProductDefParaKeyManager.getAll(queryFilter);
		return new JsonPageResult<OaProductDefParaKey>(list,queryFilter.getPage().getTotalItems());
	}
	
    /**
     * 根据keyId获取字段
     * 
     * 
     * */
	@RequestMapping("getByIsKeyId")
	@ResponseBody
	public JsonPageResult<OaProductDefParaValue> getByIsKeyId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		String keyId=request.getParameter("keyId");
		List<OaProductDefParaValue> list = new ArrayList<OaProductDefParaValue>();
		list = oaProductDefParaValueManager.getByKeyId(keyId);

		return new JsonPageResult<OaProductDefParaValue>(list, queryFilter.getPage().getTotalItems());

	}
	
	
	
	
	
	@RequestMapping("getValueKeyIds")
	@ResponseBody
	public JsonResult getValueKeyIds(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String defId=request.getParameter("pkId");
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
	

    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	OaProductDef oaProductDef=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		oaProductDef=oaProductDefManager.get(pkId);
    		
    		if("true".equals(forCopy)){
    			oaProductDef.setProdDefId(null);
    		}
    	}else{
    		oaProductDef=new OaProductDef();
    	}
    	return getPathView(request).addObject("oaProductDef",oaProductDef);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return oaProductDefManager;
	}

}
