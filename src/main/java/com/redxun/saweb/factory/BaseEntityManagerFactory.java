package com.redxun.saweb.factory;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.redxun.core.seq.IdGenerator;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.org.api.context.CurrentContext;
import com.redxun.saweb.dao.BaseEntityDao;
import com.redxun.saweb.handler.EntityPersistenceHandler;
import com.redxun.saweb.manager.BaseEntityManager;
/**
 * 基础实体业务管理工厂类，用于生成对应实体的实体Manager
 * @author csx
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Service
public class BaseEntityManagerFactory {

    private Log logger = LogFactory.getLog(BaseEntityManagerFactory.class);
    @Resource
    CurrentContext currentContext;

    @Resource
    protected IdGenerator idGenerator;

    @PersistenceContext
    protected EntityManager entityManager;
    /**
     * 缓存类名及类定义
     */
    private Map<String, Class<?>> classEntityMap = new HashMap<String, Class<?>>();

    /**
     * 缓存类名及类定义
     */
    private Map<String, Class<?>> classHandlerMap = new HashMap<String, Class<?>>();
    /**
     * 缓存类名及实体管理器
     */
    private Map<String, BaseEntityManager> classManagerMap = new HashMap<String, BaseEntityManager>();

    public Map<String, Class<?>> getClassEntityMap() {
        return classEntityMap;
    }

    /**
     * 通过实体类名获取类定义
     *
     * @param clasName
     * @return Class
     * @exception
     * @since 1.0.0
     */
    public Class getEntityClassFromName(String className) {
        Class cls = classEntityMap.get(className);
        if (cls != null) {
            return cls;
        }
        try {
            cls = Class.forName(className);
            classEntityMap.put(className, cls);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return cls;
    }

    /**
     * 通过实体处理器的类名获取类定义
     *
     * @param clasName
     * @return Class
     * @exception
     * @since 1.0.0
     */
    public Class getEntityHandlerClass(String className) {
        Class cls = classHandlerMap.get(className);
        if (cls != null) {
            return cls;
        }
        try {
            cls = Class.forName(className);
            classHandlerMap.put(className, cls);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return cls;
    }

    /**
     * 通过Class名称获取其实体管理服务类
     *
     * @param entityClass
     * @return BaseEntityManager
     * @exception
     * @since 1.0.0
     */
    public BaseEntityManager getBaseEntityManager(String entityClass) {
        BaseEntityManager baseEntityManager = classManagerMap.get(entityClass);
        if (baseEntityManager != null) {
            return baseEntityManager;
        }

        BaseEntityDao baseEntityDao = AppBeanUtil.getBean(BaseEntityDao.class);
        baseEntityDao.setEntityClass(getEntityClassFromName(entityClass));
        /*
         baseEntityDao.setEntityManager(entityManager);
         baseEntityDao.setIdGenerator(idGenerator);
         baseEntityDao.setCurrentContext(currentContext);
         */
        baseEntityManager = AppBeanUtil.getBean(BaseEntityManager.class);
        baseEntityManager.setBaseEntityDao(baseEntityDao);
        classManagerMap.put(entityClass, baseEntityManager);
        return baseEntityManager;
    }

    /**
     * 取得实体的前后置处理器
     *
     * @param entityName
     * @return EntityPersistenceHandler
     * @exception
     * @since 1.0.0
     */
    public EntityPersistenceHandler getHandler(String entityName) {
        Map<String, String> entityHandlerMap = (Map<String, String>) AppBeanUtil.getBean("entityPersistenceHandlers");
        String handlerClassName = entityHandlerMap.get(entityName);
        if (handlerClassName == null) {
            return null;
        }
        Class handlerClass = getEntityHandlerClass(handlerClassName);
        return (EntityPersistenceHandler) AppBeanUtil.getBean(handlerClass);
    }
}
