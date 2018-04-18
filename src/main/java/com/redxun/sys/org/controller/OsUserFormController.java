package com.redxun.sys.org.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.redxun.core.constants.MBoolean;
import com.redxun.core.constants.MStatus;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.util.EncryptUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.entity.SysAccount;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysAccountManager;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.entity.OsAttributeValue;
import com.redxun.sys.org.entity.OsCustomAttribute;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsAttributeValueManager;
import com.redxun.sys.org.manager.OsCustomAttributeManager;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsRelInstManager;
import com.redxun.sys.org.manager.OsRelTypeManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 用户管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/org/osUser/")
public class OsUserFormController extends BaseFormController {

    @Resource
    private OsUserManager osUserManager;
    @Resource
    private OsRelTypeManager osRelTypeManager;
    @Resource
    private OsRelInstManager osRelInstManager;
    @Resource
    private SysAccountManager sysAccountManager;
    @Resource
    private  SysInstManager sysInstManager;
    @Resource
    private OsGroupManager osGroupManager;
    @Resource
    private OsCustomAttributeManager osCustomAttributeManager;
    @Resource
    private OsAttributeValueManager osAttributeValueManager;
    
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("osUser")
    public OsUser processForm(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        OsUser osUser = null;
        if (StringUtils.isNotEmpty(userId)) {
            osUser = osUserManager.get(userId);
        } else {
            osUser = new OsUser();
            osUser.setFrom(OsUser.FROM_ADDED);
            
        }
        return osUser;
    }
    /**
     * 保存实体数据
     * @param request
     * @param osUser
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @LogEnt(action = "save", module = "组织结构", submodule = "系统用户")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("osUser") @Valid OsUser osUser, BindingResult result) {
    	//saveUserAttributes(request);
        String attachAccount=request.getParameter("isAttachAccount");
        SysInst sysInst=null;
        String password=request.getParameter("password");
    	String userName=request.getParameter("userName");
        if(StringUtils.isEmpty(osUser.getUserId()) 
        		&& StringUtils.isNotEmpty(attachAccount)){
        	String tenantId=osUser.getTenantId();
        	if(StringUtils.isEmpty(tenantId)){
        		tenantId=ContextUtil.getCurrentTenantId();
        	}
        	sysInst=sysInstManager.get(tenantId);
        	SysAccount instAccount=sysAccountManager.getByNameDomain(userName,sysInst.getDomain());
        	if(instAccount!=null){
        		result.reject("", "账号"+userName+"已经存在");
        	}
        }
        
        if (result.hasErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        
        String mainDepId=request.getParameter("mainDepId");
        String canDepIds=request.getParameter("canDepIds");	
        String canGroupIds=request.getParameter("canGroupIds");
        //主部门
        if(StringUtils.isNotEmpty(mainDepId)){
        	OsGroup osGroup=osGroupManager.get(mainDepId);
        	osUser.setMainDep(osGroup);
        }
        //从部门
        if(StringUtils.isNotEmpty(canDepIds)){
        	String[] canIds=canDepIds.split("[,]");
        	List<OsGroup> canDeps=new ArrayList<OsGroup>();
        	for(String cId:canIds){
        		OsGroup osGroup=osGroupManager.get(cId);
        		canDeps.add(osGroup);
        	}
        	osUser.setCanDeps(canDeps);
        }
        //其他用户组
        if(StringUtils.isNotEmpty(canGroupIds)){
        	String[] canIds=canGroupIds.split("[,]");
        	List<OsGroup> canGroups=new ArrayList<OsGroup>();
        	for(String cId:canIds){
        		OsGroup osGroup=osGroupManager.get(cId);
        		canGroups.add(osGroup);
        	}
        	osUser.setCanGroups(canGroups);
        }
        String msg = null;
        if (StringUtils.isEmpty(osUser.getUserId())) {
        	osUser.setUserId(idGenerator.getSID());
            osUserManager.create(osUser);
            //是否需要创建其对应的个人账号
         
            if("true".equals(attachAccount)){
            	
            	SysAccount account=new SysAccount();
            	account.setFullname(osUser.getFullname());
            	
            	account.setName(userName);
            	account.setDomain(sysInst.getDomain());
            	if(StringUtils.isEmpty(osUser.getEmail())){
            		osUser.setEmail(account.getName());
            		osUserManager.update(osUser);
                }
            	account.setUserId(osUser.getUserId());
            	String pwd=EncryptUtil.encryptSha256(password);
            	account.setPwd(pwd);
            	account.setTenantId(osUser.getTenantId());
            	account.setStatus(MStatus.ENABLED.toString());
            	sysAccountManager.create(account);
            }
            
            msg = getMessage("osUser.created", new Object[]{osUser.getIdentifyLabel()}, "用户成功创建!");
        } else {
        	osUser.setSyncWx(0);
            osUserManager.update(osUser);
            msg = getMessage("osUser.updated", new Object[]{osUser.getIdentifyLabel()}, "用户成功更新!");
        }

    	//添加用户的其他关系
    	String relInsts=request.getParameter("relInsts");
    	JSONArray relInstArr=JSONArray.fromObject(relInsts);
    	String isMain=MBoolean.NO.name();
    	for(int i=0;i<relInstArr.size();i++){
    		JSONObject rowObj=relInstArr.getJSONObject(i);
    		String party1=JSONUtil.getString(rowObj,"party1");
    		String osRelTypeId=rowObj.getString("osRelTypeId");
    		OsRelType osRelType=osRelTypeManager.get(osRelTypeId);
    		OsRelInst inst=new OsRelInst();
         	inst.setParty1(party1);
         	inst.setParty2(osUser.getUserId());
         	inst.setRelType(osRelType.getRelType());
         	inst.setRelTypeKey(osRelType.getKey());
         	inst.setDim1(osRelType.getDim1()==null?null:osRelType.getDim1().getDimId());
         	
         	inst.setIsMain(isMain);
         	inst.setOsRelType(osRelType);

         	inst.setStatus(MBoolean.ENABLED.toString());
         	inst.setInstId(IdUtil.getId());
         	inst.setTenantId(osUser.getTenantId());
         	osRelInstManager.create(inst);
    	}
        return new JsonResult(true, msg);
    }
   /* private void saveUserAttributes(HttpServletRequest request){
    	String currentUserId=ContextUtil.getCurrentUserId();
    	
    	String tenantId=ContextUtil.getCurrentTenantId();
    	String userId=RequestUtil.getString(request, "userId");
    	List<OsCustomAttribute> osCustomAttributes=osCustomAttributeManager.getUserTypeAttributeByTenantId(tenantId);
    	for (int i = 0; i < osCustomAttributes.size(); i++) {
			OsCustomAttribute osCustomAttribute=osCustomAttributes.get(i);
			String attributeId=osCustomAttribute.getID();
			String value=RequestUtil.getString(request, "widgetType_"+attributeId);
			OsAttributeValue osAttributeValue=osAttributeValueManager.getSpecialValueByUser(attributeId, userId);
			if(osAttributeValue!=null){
				osAttributeValue.setValue(value);
				osAttributeValueManager.update(osAttributeValue);
			}else{
				osAttributeValue=new OsAttributeValue();
				osAttributeValue.setId(IdUtil.getId());
				osAttributeValue.setAttributeId(attributeId);
				osAttributeValue.setTargetId(userId);
				osAttributeValue.setValue(value);
				osAttributeValue.setCreateBy(currentUserId);
				osAttributeValue.setCreateTime(new Date());
				osAttributeValueManager.create(osAttributeValue);
			}
		}
    	
    	
    }*/
}

