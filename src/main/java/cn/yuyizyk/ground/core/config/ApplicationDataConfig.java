package cn.yuyizyk.ground.core.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import cn.yuyizyk.ground.core.bean.ApplicationInfo;
import cn.yuyizyk.ground.util.cls.LoaderUtil;

/**
 * 
 * @author yuyi
 *
 */

@EnableAspectJAutoProxy
@EnableTransactionManagement
@MapperScan(basePackages = { "cn.yuyizyk.ground.mapper" })
public class ApplicationDataConfig {
	private final static Logger log = LoggerFactory.getLogger(ApplicationDataConfig.class);

	/*
	 * @Bean public MapperScannerConfigurer mapperScannerConfigurer() {
	 * MapperScannerConfigurer mapperScannerConfigurer = new
	 * MapperScannerConfigurer(); // 跟@MapperScan(basePackages = {
	 * "cn.yuyizyk.ground.mapper" }) 等同 // 如果通过web.xml 加载servlet的话，可能找不到映射对象 建议用注解
	 * mapperScannerConfigurer.setBasePackage("cn.yuyizyk.ground.mapper");
	 * mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
	 * return mapperScannerConfigurer; }
	 */

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		DataSource druidDataSource = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = loader.getResourceAsStream("properties/jdbc.properties");
			Properties p = new Properties();
			p.load(inputStream);
			druidDataSource = DruidDataSourceFactory.createDataSource(p);
		} catch (Exception e) {
			log.error("获得数据库链接发生异常", e);
			System.exit(0);
		}
		return druidDataSource;
	}

	@Bean(name = "transactionManager")
	public DataSourceTransactionManager dataSourceTransactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(this.getDataSource());
		return dataSourceTransactionManager;
	}

	@Bean
	public Interceptor[] interceptors() {
		Set<Class<?>> set = LoaderUtil.getClzFromPkg("cn.yuyizyk.ground.mapper.interceptor");
		List<Interceptor> li = new ArrayList<>();
		set.forEach(a -> {
			if (Interceptor.class.isAssignableFrom(a)) {
				try {
					li.add((Interceptor) a.newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					log.error("-------------初始化拦截器异常-------------", e);
					System.exit(0);
				}
			}
		});
		return li.toArray(new Interceptor[] {});
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, Interceptor[] interceptors) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPlugins(interceptors);
		return sessionFactory.getObject();
	}

	@Bean
	public ApplicationInfo applicationInfo() {
		return ApplicationInfo.newInstance();
	}

	/**
	 * 多表映射
	 * 
	 * @return
	 */
	@Bean
	public Map<String, Object> mymapper() {
		return new HashMap<String, Object>();
	}

}
