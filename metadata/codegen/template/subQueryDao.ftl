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

import ${domain}.${system}.${package}.entity.${class};
import org.springframework.stereotype.Repository;
import ${domain}.core.dao.mybatis.BaseMybatisDao;
import ${domain}.core.dao.mybatis.MyBatisDao;
import ${domain}.core.query.QueryFilter;
import java.util.List;

@Repository
<#if (model.sub)>
public class ${class}QueryDao extends MyBatisDao<${class},String> {
<#else>
public class ${class}QueryDao extends BaseMybatisDao<${class}> {
</#if>

	@Override
	public String getNamespace() {
		return ${class}.class.getName();
	}
	
	<#if (model.sub)>
	@Override
	public void deleteObject(${class} arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Long getTotalItems(QueryFilter arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void saveOrUpdate(${class} arg0) {
		// TODO Auto-generated method stub
		
	}
	</#if>
	/**
	 * 查询外键ID为refId的子表List
	 * @param refId
	 * @return
	 */
	public List<${class}> getByMain(String refId){
		return this.getBySqlKey("get${class}List", refId);
	}
	
		/**
	 * 查询外键ID为refId的子表List
	 * @param refId
	 * @return
	 */
	public List<${class}> delByMainId(String refId){
		return this.getBySqlKey("delByMainId",refId);
	}

}

