package com.redxun.oa.product.controller;

import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.util.BeanUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.oa.product.entity.OaProductDefParaKey;
import com.redxun.oa.product.entity.OaProductDefParaValue;
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
 * [OaProductDefParaKey]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/product/oaProductDefParaKey/")
public class OaProductDefParaKeyFormController extends BaseFormController {
	@Resource
	SysTreeManager sysTreeManager;
    @Resource
    private OaProductDefParaKeyManager oaProductDefParaKeyManager;
    @Resource
    OaProductDefParaValueManager oaProductDefParaValueManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaProductDefParaKey")
    public OaProductDefParaKey processForm(HttpServletRequest request) {
        String keyId = request.getParameter("keyId");
        OaProductDefParaKey oaProductDefParaKey = null;
        if (StringUtils.isNotEmpty(keyId)) {
            oaProductDefParaKey = oaProductDefParaKeyManager.get(keyId);
        } else {
            oaProductDefParaKey = new OaProductDefParaKey();
        }

        return oaProductDefParaKey;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaProductDefParaKey
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaProductDefParaKey") @Valid OaProductDefParaKey oaProductDefParaKey, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        
        //清除其原来的VALUE属性
        oaProductDefParaKey.getOaProductDefParaValues().clear();
        
        //更新属性
        String atts=request.getParameter("atts");
        if(StringUtils.isNotEmpty(atts)){
        	JSONArray arrs=JSONArray.fromObject(atts);
        	for(int i=0;i<arrs.size();i++){
        		JSONObject ftObject=arrs.getJSONObject(i);
        		OaProductDefParaValue oaValue=(OaProductDefParaValue)JSONUtil.json2Bean(ftObject.toString(), OaProductDefParaValue.class);
        		if(StringUtils.isEmpty(oaValue.getValueId())){
        			oaValue.setValueId(idGenerator.getSID());
        			oaValue.setOaProductDefParaKey(oaProductDefParaKey);
        			oaProductDefParaKey.getOaProductDefParaValues().add(oaValue);
        		}else{
        			OaProductDefParaValue para=oaProductDefParaValueManager.get(oaValue.getValueId());
        			BeanUtil.copyProperties(para, oaValue);
        			oaProductDefParaKey.getOaProductDefParaValues().add(para);
        		}
        	}
        }
        
        //设置其分类
        String treeId=request.getParameter("treeId");
        if(StringUtils.isNotEmpty(treeId)){
        	SysTree sysTree=sysTreeManager.get(treeId);
        	oaProductDefParaKey.setSysTree(sysTree);
        }
        
        String msg = null;
        if (StringUtils.isEmpty(oaProductDefParaKey.getKeyId())) {
            oaProductDefParaKey.setKeyId(idGenerator.getSID());
            oaProductDefParaKeyManager.create(oaProductDefParaKey);
            msg = getMessage("oaProductDefParaKey.created", new Object[]{oaProductDefParaKey.getName()}, "产品类型成功创建!");
        } else {
            oaProductDefParaKeyManager.update(oaProductDefParaKey);
            msg = getMessage("oaProductDefParaKey.updated", new Object[]{oaProductDefParaKey.getName()}, "产品类型成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

