package cn.yuyizyk.ground.application.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import cn.yuyizyk.ground.mapper.parser.PojoDecoratorFactory;
import cn.yuyizyk.ground.util.cls.LoaderUtil;

/**
 * 
 * @author yuyi
 *
 */

@EnableAspectJAutoProxy
@EnableTransactionManagement
@MapperScan(basePackages = { "cn.yuyizyk.ground.mapper" })
@PropertySource("classpath:properties/init.properties")
public class ApplicationDataConfig {
	private final static Logger log = LoggerFactory.getLogger(ApplicationDataConfig.class);

	@Value("${interceptors_basePackages}")
	private String interceptorsBasePackages;

	@Value("${info_properties_path}")
	private String infoPropertiesPath;

	@Value("${jdbc_properties_path}")
	private String jdbcPropertiesPath;

	@Bean(name = "initProperties")
	public Properties initProperties() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties p = new Properties();
		try {
			p.load(new BufferedReader(new InputStreamReader(loader.getResourceAsStream("properties/init.properties"))));
		} catch (IOException e) {
			log.error("获得配置发生异常", e);
			System.exit(0);
		}
		return p;
	}

	@Bean(name = "jdbcProperties")
	public Properties jdbcProperties() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties p = new Properties();
		try {
			p.load(new BufferedReader(new InputStreamReader(loader.getResourceAsStream(jdbcPropertiesPath))));
		} catch (IOException e) {
			log.error("获得配置发生异常", e);
			System.exit(0);
		}
		return p;
	}

	@Bean(name = "infoProperties")
	public Properties infoProperties() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties p = new Properties();
		try {
			p.load(new BufferedReader(new InputStreamReader(loader.getResourceAsStream(infoPropertiesPath))));
		} catch (IOException e) {
			log.error("获得配置发生异常", e);
			System.exit(0);
		}
		return p;
	}

	@Bean(name = "dataSource")
	public DataSource dataSource(Properties jdbcProperties) {
		DataSource druidDataSource = null;
		try {
			druidDataSource = DruidDataSourceFactory.createDataSource(jdbcProperties);
		} catch (Exception e) {
			log.error("获得数据库链接发生异常", e);
			System.exit(0);
		}
		return druidDataSource;
	}

	@Bean(name = "transactionManager")
	public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource);
		return dataSourceTransactionManager;
	}

	@Bean
	public Interceptor[] interceptors() {
		List<Interceptor> li = new ArrayList<>();
		LoaderUtil.scanning(interceptorsBasePackages, a -> {
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

	/**
	 * 注册一个pojo操作
	 * 
	 * @return
	 */
	@Bean
	private PojoDecoratorFactory pojoFactory() {
		return new PojoDecoratorFactory() {
		};
	}

}
