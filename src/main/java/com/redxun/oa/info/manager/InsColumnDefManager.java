
package com.redxun.oa.info.manager;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.engine.FreemarkEngine;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.script.GroovyEngine;
import com.redxun.core.util.StringUtil;
import com.redxun.oa.info.dao.InsColumnDefDao;
import com.redxun.oa.info.dao.InsColumnDefQueryDao;
import com.redxun.oa.info.entity.InsColumnDef;
import com.redxun.oa.info.entity.InsColumnEntity;


/**
 * 
 * <pre> 
 * 描述：ins_column_def 处理接口
 * 作者:mansan
 * 日期:2017-08-16 11:39:47
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class InsColumnDefManager extends ExtBaseManager<InsColumnDef>{
	@Resource
	private InsColumnDefDao insColumnDefDao;
	@Resource
	private InsColumnDefQueryDao insColumnDefQueryDao;
	@Resource
	private GroovyEngine groovyEngine;
	@Resource
	private FreemarkEngine freemarkEngine;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insColumnDefDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return insColumnDefQueryDao;
	}
	
	public InsColumnDef getInsColumnDef(String uId){
		InsColumnDef insColumnDef = get(uId);
		return insColumnDef;
	}
	
	/**
	 * 根据栏目获取数据。
	 * @param colId		栏目id
	 * @param ctxPath	上下文路径
	 * @return
	 */
	public String getColHtml(String colId,String ctxPath){
		InsColumnDef insColumnDef = getInsColumnDef(colId);
		if(insColumnDef==null) return "";
        Map<String,Object> params = new HashMap<String, Object>();
        String function = insColumnDef.getFunction();
		params.put("colId", colId);
		params.put("ctxPath", ctxPath);
		InsColumnEntity entity = (InsColumnEntity) groovyEngine.executeScripts(function,params);		
    	//构造HTML
    	params.put("data", entity);
    	
		String html="";
    	if(StringUtil.isNotEmpty(insColumnDef.getTemplet())){
			try{
				html=freemarkEngine.parseByStringTemplate(params, insColumnDef.getTemplet());
			}catch(Exception e){
				logger.debug(e.getMessage());
			}
    	}
		return html;
	}
}
