
package com.redxun.sys.core.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.redxun.core.database.datasource.DataSourceUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysDataSource;
import com.redxun.sys.core.manager.SysDataSourceManager;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.validation.Valid;
import com.redxun.saweb.util.IdUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据源定义管理 管理
 * @author ray
 */
@Controller
@RequestMapping("/sys/core/sysDataSource/")
public class SysDataSourceFormController extends BaseFormController {

    @Resource
    private SysDataSourceManager sysDataSourceManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysDataSource")
    public SysDataSource processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysDataSource sysDataSource = null;
        if (StringUtils.isNotEmpty(id)) {
            sysDataSource = sysDataSourceManager.get(id);
        } else {
            sysDataSource = new SysDataSource();
        }

        return sysDataSource;
    }
    /**
     * 保存实体数据
     * @param request
     * @param orders
     * @param result
     * @return
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysDataSource") @Valid SysDataSource sysDataSource, BindingResult result) throws IllegalAccessException, NoSuchFieldException {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        
        if(sysDataSourceManager.isExist(sysDataSource)){
        	return new JsonResult(false, "数据源已经存在!");
        }
        JsonResult jsonResult= sysDataSourceManager.checkConnection(sysDataSource);
        if(!jsonResult.isSuccess()){
        	return jsonResult;
        }
        String msg = null;
        
        if (StringUtils.isEmpty(sysDataSource.getId())) {
            sysDataSource.setId(IdUtil.getId());
            sysDataSourceManager.create(sysDataSource);
            msg = getMessage("sysDataSource.created", new Object[]{sysDataSource.getIdentifyLabel()}, "[数据源定义管理]成功创建!");
        } else {
            sysDataSourceManager.update(sysDataSource);
            msg = getMessage("sysDataSource.updated", new Object[]{sysDataSource.getIdentifyLabel()}, "[数据源定义管理]成功更新!");
        }
        if ("yes".equals( sysDataSource.getEnabled())) {
        	//如果已存在则先关闭。
        	DruidDataSource ds= (DruidDataSource) DataSourceUtil.getDataSourceByAlias(sysDataSource.getAlias());
        	if(ds!=null){
        		ds.close();
        	}
        	//构建数据源。
			DataSource dataSource=	sysDataSourceManager.getDsBySysSource(sysDataSource);
			//添加到动态数据源。
			DataSourceUtil.addDataSource(sysDataSource.getAlias(), dataSource,true);
		}
        else{
        	//如果禁用删除并关闭数据源。
        	DruidDataSource ds= (DruidDataSource) DataSourceUtil.getDataSourceByAlias(sysDataSource.getAlias());
        	if(ds!=null){
        		DataSourceUtil.removeDataSource(sysDataSource.getAlias());
        		ds.close();
        	}
        }
        return new JsonResult(true, msg);
    }
}

