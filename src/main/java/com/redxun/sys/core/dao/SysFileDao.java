package com.redxun.sys.core.dao;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.Page;
import com.redxun.sys.core.entity.SysFile;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * 描述：SysFile数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysFileDao extends BaseJpaDao<SysFile> {

    @SuppressWarnings("rawtypes")
    @Override
    protected Class getEntityClass() {
        return SysFile.class;
    }

    /**
     * 返回实体记录名称
     * @param entityName
     * @param recordId
     * @param fileName
     * @param ext
     * @param page
     * @return
     */
    public List<SysFile> getByEntityNameRecordIdFileNameExt(String entityName, String recordId, String fileName, String ext, Page page) {
        List<Object> params=new ArrayList<Object>();
        String ql="from SysFile sf where sf.sysModule.entityName=? and sf.recordId=?";
        params.add(entityName);
        params.add(recordId);
        if(StringUtils.isNotEmpty(fileName)){
            ql+=" and sf.fileName like ?";
            params.add("%"+fileName+"%");
        }
        if(StringUtils.isNotEmpty(ext)){
            ql+=" and sf.ext=?";
            params.add(ext);
        }
        return this.getByJpql(ql, params, page);
    }
    
    /**
     * 取得某模块某记录下的所有文件列表
     * @param moduleName 模块名称
     * @param recordId 记录Id
     * @return 
     */
    public List<SysFile> getByModelNameRecordId(String moduleName,String recordId){
        String ql="from SysFile sf where sf.sysModule.entityName=? and sf.recordId=? ";
        return this.getByJpql(ql,new Object[]{moduleName,recordId});
    }
    
    /**
     * 按来源查找附件
     * @param from
     * @return
     */
    public List<SysFile> getByFrom(String from){
    	String ql="from SysFile sf where sf.from=?";
    	return this.getByJpql(ql, new Object[]{from});
    }
    /**
     * 按来源查找附件
     * @param from
     * @param page
     * @return
     */
    public List<SysFile> getByFrom(String from,Page page){
    	String ql="from SysFile sf where sf.from=?";
    	return this.getByJpql(ql, new Object[]{from},page);
    }
    
    
    /**
     * 按来源及类型查找附件
     * @param from
     * @return
     */
    public List<SysFile> getByFromMineType(String from,String mineType){
    	String ql="from SysFile sf where sf.from=? and sf.mineType=?";
    	return this.getByJpql(ql, new Object[]{from,mineType});
    }
    
    /**
     * 按来源及类型查找附件,并且分页返回
     * @param from
     * @return
     */
    public List<SysFile> getByFromMineType(String from,String mineType,Page page){
    	String ql="from SysFile sf where sf.from=? and sf.mineType=?";
    	return this.getByJpql(ql, new Object[]{from,mineType},page);
    }
    /**
     * 按来源及媒体类型及用户来源获得图片类型
     * @param from
     * @param mineType
     * @param userId
     * @return
     */
    public List<SysFile> getByFromMineType(String from,String mineType,String userId){
    	List<Object> params=new ArrayList<Object>();
    	String ql="from SysFile sf where sf.from=? and sf.mineType=?";
    	params.add(from);
    	params.add(mineType);
    	if(SysFile.FROM_SELF.equals(from)){
    		ql+=" and sf.createBy=?";
    		params.add(userId);
    	}
    	return this.getByJpql(ql, params.toArray());
    }
    
    /**
     * 按来源及类型查找附件
     * @param from
     * @param page
     * @return
     */
    public List<SysFile> getByFrom(String from,String mineType,Page page){
    	String ql="from SysFile sf where sf.from=? and sf.mineType=?";
    	return this.getByJpql(ql, new Object[]{from,mineType},page);
    }
    
}
