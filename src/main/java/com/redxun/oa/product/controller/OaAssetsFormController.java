package com.redxun.oa.product.controller;

import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.product.entity.OaAssParameter;
import com.redxun.oa.product.entity.OaAssets;
import com.redxun.oa.product.entity.OaProductDefParaKey;
import com.redxun.oa.product.entity.OaProductDefParaValue;
import com.redxun.oa.product.manager.OaAssetsManager;
import com.redxun.oa.product.manager.OaProductDefParaKeyManager;
import com.redxun.oa.product.manager.OaProductDefParaValueManager;

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

/**
 * 资信息管理
 * @author csx
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司 (www.redxun.cn)
 * 本源代码受软件著法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/oa/product/oaAssets/")
public class OaAssetsFormController extends BaseFormController {

    @Resource
    private OaAssetsManager oaAssetsManager;
    @Resource
    OaProductDefParaKeyManager oaProductDefParaKeyManager;
    @Resource
    OaProductDefParaValueManager oaProductDefParaValueManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaAssets")
    public OaAssets processForm(HttpServletRequest request) {
        String assId = request.getParameter("assId");
        OaAssets oaAssets = null;
        if (StringUtils.isNotEmpty(assId)) {
            oaAssets = oaAssetsManager.get(assId);
        } else {
            oaAssets = new OaAssets();
        }

        return oaAssets;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaAssets
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaAssets") @Valid OaAssets oaAssets, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        
        oaAssets.getOaAssParameters().clear();
        String oldjson=request.getParameter("oldjson");
        String newjson=request.getParameter("newjson");
        JSONArray jArray=null;
        OaAssParameter oaAssParameter=null;
        OaProductDefParaKey oKey=null;
        OaProductDefParaValue oValue=null;
        if(StringUtils.isNotEmpty(newjson)){
        	jArray=JSONArray.fromObject(newjson);
        	for(int j=0;j<jArray.size();j++){
        		JSONObject jObject=jArray.getJSONObject(j);
        		String keyName=JSONUtil.getString(jObject, "customKeyName");
        		String[] valueName=JSONUtil.getString(jObject, "customValueName").split(",");
        		if(valueName.length>0){
        			for(int i=0;i<valueName.length;i++){
    					oaAssParameter=new OaAssParameter();
    					oaAssParameter.setParaId(idGenerator.getSID());
    					oaAssParameter.setCustomKeyName(keyName);
    					oaAssParameter.setCustomValueName(valueName[i]);
    					oaAssParameter.setOaAssets(oaAssets);
    					oaAssets.getOaAssParameters().add(oaAssParameter);
        			}
        		}
        	}
        }
        if(StringUtils.isNotEmpty(oldjson)){
        	JSONArray arrs=JSONArray.fromObject(oldjson);
        	for(int i=0;i<arrs.size();i++){
        		JSONObject ftObject=arrs.getJSONObject(i);
        		String keyId =JSONUtil.getString(ftObject, "keyId");
        		String[] valueId=JSONUtil.getString(ftObject, "number").split(",");

        		oKey=oaProductDefParaKeyManager.get(keyId);
        		
    			if(valueId.length > 0){
    				
    				for(int j = 0; j < valueId.length; j++){
    					
    					oaAssParameter=new OaAssParameter();
    					oValue=oaProductDefParaValueManager.get(valueId[j]);
    					oaAssParameter.setParaId(idGenerator.getSID());
    					oaAssParameter.setOaProductDefParaKey(oKey);
    					oaAssParameter.setOaProductDefParaValue(oValue);
    					oaAssParameter.setOaAssets(oaAssets);
    					oaAssets.getOaAssParameters().add(oaAssParameter);
    					
    				}
    			}
    			
        	}
        }
        
        String msg = null;
        if (StringUtils.isEmpty(oaAssets.getAssId())) {
            oaAssets.setAssId(idGenerator.getSID());
//            oaAssets.setJson(datajson);
            oaAssetsManager.create(oaAssets);
            msg = getMessage("oaAssets.created", new Object[]{oaAssets.getName()}, "资产信息成功创建!");
        } else {
            oaAssetsManager.update(oaAssets);
            msg = getMessage("oaAssets.updated", new Object[]{oaAssets.getName()}, "资产信息成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

