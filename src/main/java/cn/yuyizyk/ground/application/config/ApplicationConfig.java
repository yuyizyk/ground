package cn.yuyizyk.ground.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cn.yuyizyk.ground.application.filter.BaseFilter;

/**
 * 配置类
 * 
 * @author yuyi
 *
 */

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = { "cn.yuyizyk.ground" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
@Import(ApplicationDataConfig.class)
// @PropertySource({"classpath:jdbc.properties"})
// @ImportSource("XXXX.xml")
// @Configuration，用于表示这个类是一个配置类，用于配置Spring的相关信息
// @EnableAspectJAutoProxy，启用切面自动代理，用于AOP
// @EnableTransactionManagement，启用注解事务，即可以使用@Transactional注解来控制事务
// @ComponentScan，组件扫描，在basePackages指定的目录下扫描被@Controller、@Service、@Component等注解注册的组件
// @Import，引入指定的配置类，我们引入了Spring容器配置类和数据源事务配置类
// @PropertySource，加载指定的配置文件，配置文件内容会加载入Environment中等待调用*/
public class ApplicationConfig {
	private final static Logger log = LoggerFactory.getLogger(ApplicationConfig.class);
}
