package com.redxun.hr.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.hr.core.entity.HrDutySection;

/**
 * <pre>
 *  
 * 描述：HrDutySection数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class HrDutySectionDao extends BaseJpaDao<HrDutySection> {
	
	/**
	 * 根据是否组合班次获取实体
	 * @param isGroup
	 * @return
	 */
	public List<HrDutySection> getByIsGroup(String isGroup,String tenantId){
		String ql="from HrDutySection s where s.isGroup=? and s.tenantId=?";
		return this.getByJpql(ql, new Object[]{isGroup,tenantId});
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return HrDutySection.class;
	}

}
