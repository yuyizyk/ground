package cn.yuyizyk.ground.services.safety;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.yuyizyk.ground.core.config.ApplicationConfig;
import cn.yuyizyk.ground.model.pojo.RoleInfo;
import cn.yuyizyk.ground.model.pojo.UserInfo;
import cn.yuyizyk.ground.services.UserService;

/**
 * 自定义授权 验证
 * 
 * @author yuyi
 *
 */
public class CustomizeRealm extends AuthorizingRealm {
	private final static Logger log = LoggerFactory.getLogger(CustomizeRealm.class);

	@Autowired
	private UserService userService;

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取当前用户
		UserInfo user = (UserInfo) principals.fromRealm(getName()).iterator().next();
		// 得到权限字符串
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		Set<RoleInfo> roles = user.getRoles();
		List<String> list = new ArrayList<>();
		for (RoleInfo role : roles) {
			/*
			 * Set<Module> modules = role.getModules(); for (Module m : modules) { if
			 * (m.getCtype() == 0) { // 说明是主菜单 list.add(m.getCpermission()); } }
			 */
		}

		info.addStringPermissions(list);
		return info;
	}

	/**
	 * 认证 登录
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		UserInfo user = userService.byAccount(upToken.getUsername());
		if (user == null) {
			return null;
		} else {
			AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
			return info;
		}

	}

}
