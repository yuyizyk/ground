package cn.yuyizyk.ground.core.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.yuyizyk.ground.services.safety.CustomCredentialsMatcher;
import cn.yuyizyk.ground.services.safety.CustomizeRealm;

/**
 * Shiro 框架配置文件
 * 
 * @author yuyi
 *
 */
@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class ShiroConfig {

	/**
	 * 和web.xml中的filtername同名
	 * 
	 * @return
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");// 没有认证返回地址
		shiroFilterFactoryBean.setUnauthorizedUrl("/login");// 没有授权返回地址
		Map<String, String> filterChainDefinitionMap = new HashMap<>();// http://www.cppblog.com/guojingjia2006/archive/2014/05/14/206956.html
		// filterChainDefinitionMap.put(key, value)
		filterChainDefinitionMap.put("/*", "anon");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public CustomizeRealm customizeRealm() {
		CustomizeRealm realm = new CustomizeRealm();
		realm.setCredentialsMatcher(new CustomCredentialsMatcher());
		return realm;
	}

	/**
	 * 安全管理
	 * 
	 * @return
	 */
	@Bean
	public DefaultWebSecurityManager securityManager(CustomizeRealm customizeRealm, EhCacheManager ehCacheManager) {

		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(customizeRealm);

		defaultWebSecurityManager.setCacheManager(ehCacheManager);
		return defaultWebSecurityManager;
	}

	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager cManager = new EhCacheManager();
		net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.newInstance();
		cManager.setCacheManager(cacheManager);
		return cManager;
	}

	/**
	 * 保证实现了Shiro内部lifecycle函数的bean执行
	 * 
	 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 生成代理，通过代理进行控制
	 * 
	 * @return
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	/**
	 * 安全管理器
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
}
