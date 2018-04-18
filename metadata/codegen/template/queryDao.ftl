<#import "function.ftl" as func>
<#assign package=model.variables.package>
<#assign class=model.variables.class>
<#assign classVar=model.variables.classVar>
<#assign comment=model.tabComment>
<#assign system=vars.system>
<#assign domain=vars.domain>
<#assign sub=model.sub>
<#assign foreignKey=func.convertUnderLine(model.foreignKey)>
<#assign pkType=func.getPkType(model)>
<#assign fkType=func.getFkType(model)>

/**
 * 
 * <pre> 
 * 描述：${comment} DAO接口
 <#if vars.developer?exists>
 * 作者:${vars.developer}
 </#if>
 * 日期:${date?string("yyyy-MM-dd HH:mm:ss")}
 * 版权：${vars.company}
 * </pre>
 */
package ${domain}.${system}.${package}.dao;

import java.util.List;
import ${domain}.${system}.${package}.entity.${class};
import org.springframework.stereotype.Repository;
import ${domain}.core.dao.mybatis.BaseMybatisDao;

@Repository
public class ${class}QueryDao extends BaseMybatisDao<${class}> {

	@Override
	public String getNamespace() {
		return ${class}.class.getName();
	}
	
	<#if sub?exists && sub==true>
	<#assign foreignKeyVar=func.convertUnderLine(foreignKey)>
	public List<${class}> getByMain(String mainId){
		List<${class}> list=this.getBySqlKey("getByMain", mainId);
		return list;
	}
	
	public void delByMainId(String mainId){
		this.deleteBySqlKey("delByMainId", mainId);
	}
	</#if>

}

