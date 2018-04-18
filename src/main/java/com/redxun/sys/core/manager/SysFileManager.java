package com.redxun.sys.core.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.sys.core.dao.SysFileDao;
import com.redxun.sys.core.entity.SysFile;
import java.util.List;

/**
 * <pre>
 * 描述：SysFile业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysFileManager extends BaseManager<SysFile> {

    @Resource
    private SysFileDao sysFileDao;
    

    @SuppressWarnings("rawtypes")
    @Override
    protected IDao getDao() {
        return sysFileDao;
    }

    /**
     * 返回某模块下某记录的所有附件
     *
     * @param entityName
     * @param recordId
     * @param fileName
     * @param ext
     * @param page
     * @return
     */
    public List<SysFile> getRecordFiles(String entityName, String recordId, String fileName, String ext, Page page) {
        return sysFileDao.getByEntityNameRecordIdFileNameExt(entityName, recordId, fileName, ext, page);
    }

    /**
     * 取得某模块某记录下的所有文件列表
     *
     * @param moduleName 模块名称
     * @param recordId 记录Id
     * @return
     */
    public List<SysFile> getByModelNameRecordId(String moduleName, String recordId) {
        return sysFileDao.getByModelNameRecordId(moduleName, recordId);
    }
    /**
     * 取得应用的图片文件列表
     * @return
     */
    public List<SysFile> getAppImages(){
    	return sysFileDao.getByFromMineType(SysFile.FROM_APP, SysFile.MINE_TYPE_IMAGE);
    }
    /**
     * 通过来源获得个人的图片或应用级下的图片
     * @param from
     * @param userId 图片归属的用户Id
     * @return
     */
    public List<SysFile> getImagesByFrom(String from,String userId){
    	return sysFileDao.getByFromMineType(from,SysFile.MINE_TYPE_IMAGE,userId);
    }
    /**
     * 取得应用的图标的列表
     * @return
     */
    public List<SysFile> getAppIcons(){
    	return sysFileDao.getByFromMineType(SysFile.FROM_APP, SysFile.MINE_TYPE_ICON);
    }
    /**
     * 取得应用的图标的列表
     * @param page 分页返回
     * @return
     */
    public List<SysFile> getAppIcons(Page page){
    	return sysFileDao.getByFromMineType(SysFile.FROM_APP, SysFile.MINE_TYPE_ICON ,page);
    }
    
    
}
