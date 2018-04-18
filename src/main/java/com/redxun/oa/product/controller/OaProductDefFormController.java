package com.redxun.oa.product.controller;

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

import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.oa.product.entity.OaProductDef;
import com.redxun.oa.product.entity.OaProductDefPara;
import com.redxun.oa.product.entity.OaProductDefParaKey;
import com.redxun.oa.product.entity.OaProductDefParaValue;
import com.redxun.oa.product.manager.OaProductDefManager;
import com.redxun.oa.product.manager.OaProductDefParaKeyManager;
import com.redxun.oa.product.manager.OaProductDefParaManager;
import com.redxun.oa.product.manager.OaProductDefParaValueManager;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeManager;

/**
 * [OaProductDef]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/product/oaProductDef/")
public class OaProductDefFormController extends BaseFormController {

    @Resource
    private OaProductDefManager oaProductDefManager;
	@Resource
	SysTreeManager sysTreeManager;
    @Resource
    OaProductDefParaKeyManager oaProductDefParaKeyManager;
    @Resource
    OaProductDefParaValueManager oaProductDefParaValueManager;
    @Resource
    OaProductDefParaManager oaProductDefParaManager;
    
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaProductDef")
    public OaProductDef processForm(HttpServletRequest request) {
        String prodDefId = request.getParameter("prodDefId");
        OaProductDef oaProductDef = null;
        if (StringUtils.isNotEmpty(prodDefId)) {
            oaProductDef = oaProductDefManager.get(prodDefId);
        } else {
            oaProductDef = new OaProductDef();
        }

        return oaProductDef;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaProductDef
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaProductDef") @Valid OaProductDef oaProductDef, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        
        oaProductDef.getOaProductDefParas().clear();
        //更新属性
        String atts=request.getParameter("atts");
        OaProductDefPara oPara=null;
        OaProductDefParaKey oKey=null;
        OaProductDefParaValue oValue=null;
        if(StringUtils.isNotEmpty(atts)){
        	JSONArray arrs=JSONArray.fromObject(atts);
        	for(int i=0;i<arrs.size();i++){
        		JSONObject ftObject=arrs.getJSONObject(i);
        		String keyId =JSONUtil.getString(ftObject, "keyId");
        		String[] valueId=JSONUtil.getString(ftObject, "number").split(",");
        		oKey=oaProductDefParaKeyManager.get(keyId);

        			if(valueId.length > 0){
        				for(int j = 0; j < valueId.length; j++){
        					if (StringUtils.isNotEmpty(oaProductDef.getProdDefId())) {
        						oPara=oaProductDefParaManager.getDefByKeyOrValueId(oaProductDef.getProdDefId(), keyId, valueId[j]);
        						if (oPara != null) {
        							continue;
        						}
        					}

        					oPara=new OaProductDefPara();
        					oValue=oaProductDefParaValueManager.get(valueId[j]);
                			oPara.setId(idGenerator.getSID());
                			oPara.setOaProductDef(oaProductDef);
                			
                			oPara.setOaProductDefParaKey(oKey);
                			oPara.setOaProductDefParaValue(oValue);
                			oaProductDef.getOaProductDefParas().add(oPara);
        				}
        			}
        	}
        }
        
        //设置其分类
        String treeId=request.getParameter("treeId");
        if(StringUtils.isNotEmpty(treeId)){
        	SysTree sysTree=sysTreeManager.get(treeId);
        	oaProductDef.setSysTree(sysTree);
        }
        
        String msg = null;
        if (StringUtils.isEmpty(oaProductDef.getProdDefId())) {
            oaProductDef.setProdDefId(idGenerator.getSID());
            oaProductDefManager.create(oaProductDef);
            msg = getMessage("oaProductDef.created", new Object[]{oaProductDef.getName()}, "资产分类成功创建!");
        } else {
            oaProductDefManager.update(oaProductDef);
            msg = getMessage("oaProductDef.updated", new Object[]{oaProductDef.getName()}, "资产分类成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

