package com.redxun.oa.info.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.dao.InsColNewDao;
import com.redxun.oa.info.entity.InsColNew;
import com.redxun.oa.info.entity.InsNews;
/**
 * <pre> 
 * 描述：InsColNew业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class InsColNewManager extends BaseManager<InsColNew>{
	@Resource
	private InsColNewDao insColNewDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insColNewDao;
	}
    /**
     * 删除某栏目下信息关联列表
     * @param colId
     */
	public void delByColId(String colId){
		insColNewDao.delByColId(colId);
	}
	  /**
     * 删除某信息关联的栏目列表
     * @param newsId
     */
    public void delByNewId(String newId){
    	insColNewDao.delByNewId(newId);
    }
    
    /**
     * 查找某个栏目某个信息的关联关系
     * @param colId
     * @param newId
     * @return
     */
    public InsColNew getByColIdNewId(String colId,String newId){
    	return insColNewDao.getByColIdNewId(colId, newId);
    }
    /**
     * 删除某个栏目某个新闻的关联关系
     * @param colId
     * @param newId
     * @return
     */
    public void delByColIdNewId(String colId,String newId){
    	insColNewDao.delByColIdNewId(colId, newId);
    }
    
}