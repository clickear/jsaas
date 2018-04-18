package com.redxun.oa.info.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.oa.info.dao.InsColumnDao;
import com.redxun.oa.info.entity.InsColumn;
/**
 * <pre> 
 * 描述：InsColumn业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class InsColumnManager extends BaseManager<InsColumn>{
	@Resource
	private InsColumnDao insColumnDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insColumnDao;
	}
	
    /**
     * 栏目名称的搜索
     * @param name 栏目名称
     * @param page
     * @return
     */
	public List<InsColumn> getByName(String colName, Page page) {
		return insColumnDao.getByName(colName ,page);
	}
	
	/**
     * 根据是否启用和允许关闭显示栏目
     * @return
     */
    public List<InsColumn> getByEnable(){
    	return insColumnDao.getByEnable();
    }
}