package com.redxun.sys.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.core.dao.SysButtonDao;
import com.redxun.sys.core.dao.SysFieldDao;
import com.redxun.sys.core.dao.SysModuleDao;
import com.redxun.sys.core.entity.SysButton;
import com.redxun.sys.core.entity.SysField;
import com.redxun.sys.core.entity.SysModule;
import com.redxun.sys.core.enums.ButtonType;
import com.redxun.sys.core.enums.SubButtonType;
/**
 * <pre> 
 * 描述：SysButton业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysButtonManager extends BaseManager<SysButton>{
	@Resource
	private SysButtonDao sysButtonDao;
	@Resource
	private SysModuleDao sysModuleDao;
	@Resource
	private SysFieldDao sysFieldDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysButtonDao;
	}
	/**
	 * 按模块ID及按钮类型获得按钮定义
	 * @param moduleId
	 * @param btnType
	 * @return 
	 * SysButton
	 * @exception 
	 * @since  1.0.0
	 */
	public SysButton getByModuleIdButtonType(String moduleId,String btnType){
		return sysButtonDao.getByModuleIdButtonType(moduleId, btnType);
	}
	/**
	 * 取到模块某位置的按钮列表
	 * @param moduleId 模块ID
	 * @param position 位置
	 * @return 
	 * List<SysButton>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysButton> getByModuleIdPos(String moduleId,String position){
		return sysButtonDao.getByModuleIdPos(moduleId, position);
	}
	/**
	 * 按模块ID取得所有按钮 
	 * @param moduleId
	 * @return 
	 * List<SysButton>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysButton> getByModuleId(String moduleId){
		return sysButtonDao.getByModuleId(moduleId);
	}
	 /**
     * 按模块ID及关联模块Id、按钮的类型获得按钮的定义
     * @param moduleId
     * @param linkModuleId
     * @param btnPos 按钮的位置
     * @return 
     * SysButton
     * @exception 
     * @since  1.0.0
     */
    public List<SysButton> getByRefModuleIdButtonType(String moduleId,String linkModuleId,String btnPos){
    	return sysButtonDao.getByRefModuleIdButtonPos(moduleId, linkModuleId, btnPos);
    }
	/**
	 * 初始化模块的所有缺省菜单
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void doInitDefaultAllButtons(String moduleId){
		SysModule sysModule=sysModuleDao.get(moduleId);
		ButtonType[] btnTypes=ButtonType.values();
		int i=0;
		for(ButtonType btn:btnTypes){
			if("CUSTOM".equals(btn.getBtnType())){
				continue;
			}
			SysButton button=sysButtonDao.getByModuleIdButtonType(moduleId, btn.getBtnType());
			if(button!=null) continue;
			button=new SysButton();
			button.setBtnType(btn.getBtnType());
			button.setName(btn.getBtnText());
			button.setPos(btn.getBtnPos());
			button.setSysModule(sysModule);
			button.setKey(btn.getBtnType());
			button.setSn(i++);
			sysButtonDao.create(button);
		}
		List<SysField> linkFields=sysFieldDao.getLinkFields(sysModule.getModuleId());
		SubButtonType[] subBtnTypes=SubButtonType.values();
		//每个子模块中的按钮
		for(SysField link:linkFields){
			int j=0;
			for(SubButtonType btn:subBtnTypes){
				SysButton button=sysButtonDao.getByRefModuleIdButtonType(moduleId,link.getLinkSysModule().getModuleId(), btn.getBtnType());
				if(button!=null) continue;
				button=new SysButton();
				button.setBtnType(btn.getBtnType());
				button.setName(btn.getBtnText());
				button.setPos(btn.getBtnPos());
				button.setSysModule(sysModule);
				button.setLinkSysModule(link.getLinkSysModule());
				button.setKey(btn.getBtnType());
				button.setSn(j++);
				sysButtonDao.create(button);
			}
		}
	}
}