<#import "function.ftl" as func>
<#assign package=model.variables.package>
<#assign comment=model.tabComment>
<#assign class=model.variables.class>
<#assign classVar=model.variables.classVar>
<#assign system=vars.system>
<#assign domain=vars.domain>
<#assign pkType=func.getPkType(model)>
<#assign subtables=model.subTableList>
<#assign sub=model.sub>

package ${domain}.${system}.${package}.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ${domain}.core.dao.IDao;
import ${domain}.${system}.${package}.dao.${class}Dao;
import ${domain}.${system}.${package}.dao.${class}QueryDao;
import ${domain}.${system}.${package}.entity.${class};

<#list subtables as subTable>
<#assign subClass=subTable.variables.class>
<#assign subPackage=subTable.variables.package>
import ${domain}.${system}.${subPackage}.entity.${subClass};
import ${domain}.${system}.${subPackage}.dao.${subClass}QueryDao;
</#list>

import ${domain}.core.dao.mybatis.BaseMybatisDao;
import ${domain}.core.manager.ExtBaseManager;
import java.util.List;
import com.redxun.saweb.util.IdUtil;

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
	private ${class}Dao ${classVar}Dao;
	@Resource
	private ${class}QueryDao ${classVar}QueryDao;
	<#list subtables as subTable>
	<#assign subClass=subTable.variables.class>
	<#assign subClassVar=subTable.variables.classVar>
	@Resource
	private ${subClass}QueryDao ${subClassVar}QueryDao;
	</#list>
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return ${classVar}Dao;
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
		List<${subClass}> ${subClassVar}s= ${subClassVar}QueryDao.getByMain(uId);
		${classVar}.set${subClass}s(${subClassVar}s);
		</#list>
		return ${classVar};
	}
	

	
	<#if sub?? >
	
	@Override
	public void create(${class} entity) {
		entity.setId(IdUtil.getId());
		super.create(entity);
		
		<#list subtables as subTable>
		<#assign subClass=subTable.variables.class>
		<#assign subClassVar=subTable.variables.classVar>
		<#assign foreignKey=func.convertUnderLine(subTable.foreignKey)?cap_first>
		
		List<${subClass}> ${subClassVar}s=entity.get${subClass}s();
		for(${subClass} ${subClassVar}:${subClassVar}s){
			${subClassVar}.setId(IdUtil.getId());
			${subClassVar}.set${foreignKey}(entity.getId());
			${subClassVar}QueryDao.create(${subClassVar});
		}
		</#list>
	}

	@Override
	public void update(ProjectInfo entity) {
		super.update(entity);
		
		
		<#list subtables as subTable>
		<#assign subClass=subTable.variables.class>
		<#assign subClassVar=subTable.variables.classVar>
		
		${subClassVar}QueryDao.delByMainId(entity.getId());
		
		<#assign foreignKey=func.convertUnderLine(subTable.foreignKey)?cap_first>
		List<${subClass}> ${subClassVar}s=entity.get${subClass}s();
		for(${subClass} ${subClassVar}:${subClassVar}s){
			${subClassVar}.setId(IdUtil.getId());
			${subClassVar}.set${foreignKey}(entity.getId());
			${subClassVar}QueryDao.create(${subClassVar});
		}
		</#list>
		
		
	}
	
	</#if>
	
}
