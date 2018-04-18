package com.redxun.oa.info.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.info.entity.InsNews;
import com.redxun.oa.info.entity.InsPortCol;
/**
 * <pre> 
 * 描述：InsNews数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class InsNewsDao extends BaseJpaDao<InsNews> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return InsNews.class;
    }
    
/*    public List<String> getSubject(){
		String ql="select subject from InsNews ";
    	return this.getByJpql(ql,new Object[]{});
	}*/
}
