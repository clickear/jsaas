package com.redxun.oa.info.manager;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.info.dao.InsPortColDao;
import com.redxun.oa.info.entity.InsPortCol;
/**
 * <pre> 
 * 描述：InsPortCol业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class InsPortColManager extends BaseManager<InsPortCol>{
	@Resource
	private InsPortColDao insPortColDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insPortColDao;
	}
	
    /**
     * 查找门户栏目中间表中是否有某个门户某个栏目的关联关系
     * @param portId 门户Id
     * @param colId  栏目Id
     * @return
     */
	public InsPortCol getByPortCol(String portId, String colId){
		return insPortColDao.getByPortCol(portId, colId);
	}
    /**
     * 删除门户栏目中间表中某个门户某个栏目的关联关系
     * @param portId 门户Id
     * @param colId  栏目Id
     * @return
     */
	public void delByPortCol(String portId, String colId){
		insPortColDao.delByPortCol(portId, colId);
    }
    /**
     * 删除门户栏目中间表中所有门户Id为portId的数据
     * @param portId 门户Id
     * @return
     */
	public void delByPortal(String portId){
		insPortColDao.delByPortal(portId);
    }
	
	   /**
     * 得到门户栏目中间表中所有门户Id为portId的List，并按序号从小到大排序
     * @param portId 门户Id
     * @return 
     */
	public List<InsPortCol> getByPortId(String portId){
		return insPortColDao.getByPortId(portId);
	}
}