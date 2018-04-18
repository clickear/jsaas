package com.redxun.sys.log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import com.redxun.core.util.BeanUtil;

/**
 * 扫描有logent注解的类，读取相关的元数据。
 * @author ray
 *
 */
public class LogEntScanner implements InitializingBean {
	
	private Resource[] basePackage;
	
	private List<LogEntMetadata> list;
	
	
	public void setBasePackage(Resource[] tmp) {
		this.basePackage = tmp;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		list=findLogEnt(basePackage);
	}
	
	public List<LogEntMetadata> getLogMedata(){
		return list;
	}
	
	
	private static List<LogEntMetadata> findLogEnt(Resource[] basePackage)
			throws IOException, ClassNotFoundException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
		List<LogEntMetadata> logEntMetadatas = new ArrayList<LogEntMetadata>();
		if (basePackage == null) return logEntMetadatas;
		for (Resource resource : basePackage) {
			
			if (!resource.isReadable()) continue;
			MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
			LogEntMetadata metadata= getMetadata(metadataReader);
			if (getMetadata(metadataReader)!=null){
				logEntMetadatas.add(metadata);
			}
				
		}
		return logEntMetadatas;
	}

	/**
	 * 是否为需要的实体表类型。
	 * 
	 * @param metadataReader
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static LogEntMetadata getMetadata(MetadataReader metadataReader)
			throws ClassNotFoundException {
		Class c = Class.forName(metadataReader.getClassMetadata().getClassName());
		Method[] methods= c.getMethods();
		
		List<MethodMetadata> metadatas=new ArrayList<MethodMetadata>();
		
		for(Method method:methods){
			if(method.getAnnotation(LogEnt.class) != null){
				MethodMetadata metadata=new MethodMetadata();
				metadata.setMethodName(method.getName());
				metadata.setEnt(method.getAnnotation(LogEnt.class));
				metadatas.add(metadata);
			}
		}
		
		if(BeanUtil.isEmpty(metadatas)) return null;

		LogEntMetadata metadata=new LogEntMetadata();
		metadata.setClassName(c.getSimpleName());
		metadata.setFullName(c.getName());
		metadata.setMetadatas(metadatas);
		
		return metadata;
	}

}
