package cn.yuyizyk.ground.application.cache;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yuyizyk.ground.application.cache.imp.EhCacheEngine;

public interface CacheEngine {

	final static Logger log = LoggerFactory.getLogger(CacheEngine.class);
	
	
	

	public static CacheEngine getInstance() {
		try {
			Configuration confing = null;
			Parameters params = new Parameters();
			FileBasedConfigurationBuilder<PropertiesConfiguration> builder = new FileBasedConfigurationBuilder<PropertiesConfiguration>(
					PropertiesConfiguration.class)
							.configure(params.properties().setFileName("properties/info.properties")); // 那么这个文件会自动在下面几个地方被搜寻：当前目录
			// 、用户主目录
			// 、classpath
			confing = builder.getConfiguration();

			return (CacheEngine) Class
					.forName(CacheEngine.class.getPackage().getName() + "." + confing.getString("cache")).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ConfigurationException e) {
			log.error("异常:", e);
		}
		return new EhCacheEngine();
	}
}
