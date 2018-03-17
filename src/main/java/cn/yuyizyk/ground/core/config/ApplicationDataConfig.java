package cn.yuyizyk.ground.core.config;

import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

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

/**
 * 
 * @author yuyi
 *
 */

@EnableAspectJAutoProxy
@EnableTransactionManagement
@MapperScan(basePackages = { "cn.yuyi.ground.mapper" })
public class ApplicationDataConfig {
	private final static Logger log = LoggerFactory.getLogger(ApplicationDataConfig.class);

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
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		return sessionFactory.getObject();
	}
}
