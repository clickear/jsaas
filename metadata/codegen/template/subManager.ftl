<#import "function.ftl" as func>
<#assign package=model.variables.package>
<#assign comment=model.tabComment>
<#assign subtables=model.subTableList>
<#assign class=model.variables.class>
<#assign classVar=model.variables.classVar>
<#assign system=vars.system>
<#assign domain=vars.domain>
<#assign pkType=func.getPkType(model)>

package ${domain}.${system}.${package}.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import ${domain}.core.dao.IDao;
import ${domain}.saweb.util.IdUtil;
import ${domain}.${system}.${package}.entity.${class};
import ${domain}.${system}.${package}.dao.${class}QueryDao;
<#if subtables?exists && subtables?size!=0>
<#list subtables as subTable>
<#assign subClass=subTable.variables.class>
<#assign subClassVar=subTable.variables.classVar>
import ${domain}.${system}.${package}.entity.${subClass};
import ${domain}.${system}.${package}.dao.${subClass}QueryDao;
</#list>
</#if>

import ${domain}.core.dao.mybatis.BaseMybatisDao;
import ${domain}.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：${comment} 处理接口
 <#if vars.developer?exists>
 * 作者:${vars.developer}
 </#if>
 * 日期:${date?string("yyyy-MM-dd HH:mm:ss")}
 * 版权：${vars.company}
 * </pre>
 */
@Service
public class ${class}Manager extends ExtBaseManager<${class}>{
	@Resource
	private ${class}QueryDao ${classVar}QueryDao;
	<#if subtables?exists && subtables?size!=0>
	<#list subtables as subTable>
	<#assign subClass=subTable.variables.class>
	<#assign subClassVar=subTable.variables.classVar>
	@Resource
	private ${subClass}QueryDao ${subClassVar}QueryDao;
	</#list>
	</#if>
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return ${classVar}QueryDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return ${classVar}QueryDao;
	}
	

	public ${class} get${class}(String uId){
		${class} ${classVar} = get(uId);
		<#list subtables as subTable>
		<#assign subClass=subTable.variables.class>
		<#assign subClassVar=subTable.variables.classVar>
		List<${subClass}> ${subClassVar}= ${subClassVar}QueryDao.get${subClass}List(uId);
		${classVar}.set${subClass}s(new ArrayList<${subClass}>(${subClassVar}));
		</#list>
		return ${classVar};
	}

	
	/**
	 * 更新主表
	 * @param ${classVar}
	 */
	public void subUpdate(${class} ${classVar}){
		update(${classVar});
		<#if subtables?exists && subtables?size!=0>
		<#list subtables as subTable>
		<#assign subClass=subTable.variables.class>
		<#assign subClassVar=subTable.variables.classVar>
		List<${subClass}> ${subClassVar}Set = ${classVar}.get${subClass}s();
		${subClassVar}QueryDao.delByMainId(${classVar}.getId());
		for(${subClass} grid:${subClassVar}Set){
        	grid.setId(IdUtil.getId());
        	grid.setRefId(${classVar}.getId());
        	${subClassVar}QueryDao.create(grid);        
        }
        </#list>  
		</#if>     
	}
	
	/**
	 * 保存新的主表
	 * @param ${classVar}
	 */
	public void subSave(${class} ${classVar}){
		create(${classVar});
		<#if subtables?exists && subtables?size!=0>
		<#list subtables as subTable>
		<#assign subClass=subTable.variables.class>
		<#assign subClassVar=subTable.variables.classVar>
		List<${subClass}> ${subClassVar}Set = ${classVar}.get${subClass}s();
		for(${subClass} grid:${subClassVar}Set){
        	grid.setId(IdUtil.getId());
        	grid.setRefId(${classVar}.getId());
        	${subClassVar}QueryDao.create(grid);        
        }
        </#list>  
		</#if>        
	}
	
}
