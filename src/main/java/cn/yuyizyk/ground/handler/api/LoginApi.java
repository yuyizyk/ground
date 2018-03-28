package cn.yuyizyk.ground.handler.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.yuyizyk.ground.core.bean.SysConstant;
import cn.yuyizyk.ground.handler.base.AbstractApi;
import cn.yuyizyk.ground.model.pojo.RoleInfo;
import cn.yuyizyk.ground.model.pojo.UserInfo;
import cn.yuyizyk.ground.services.UserService;

@RestController
public class LoginApi extends AbstractApi {
	@Autowired
	private UserService userService;

	@RequestMapping("/api/login")
	public String login(HttpServletRequest request, Model model) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		/**
		 * shiro登录方式：根据用户名获取密码，密码为null非法用户；有密码检查是否用户填写的密码
		 * 登录成功后无需往httpsession中存放当前用户，这样就跟web容器绑定，关联太紧密；它自己创建
		 * subject对象，实现自己的session。这个跟web容器脱离，实现松耦合。
		 */
		// 调用shiro判断当前用户是否是系统用户
		Subject subject = SecurityUtils.getSubject(); // 得到当前用户
		// shiro是将用户录入的登录名和密码（未加密）封装到token对象中
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

		try {
			subject.login(token); // 自动调用AuthRealm.doGetAuthenticationInfo

			// 写seesion，保存当前user对象
			UserInfo curUser = (UserInfo) subject.getPrincipal(); // 从shiro中获取当前用户
			// System.out.println(curUser.getDept().getDeptName()); // 让懒加载变成立即加载
			List<RoleInfo> roles = curUser.getRoles();
			/*
			 * for (Role role : roles) { Set<Module> moduless = role.getModules(); for
			 * (Module m : moduless) System.out.println(m.getName()); }
			 */
			session.put(SysConstant.CURRENT_USER_INFO, curUser); // Principal 当前用户对象
		} catch (AuthenticationException ex) {
			super.put("errorInfo", "用户名密码错误，请重新填写!");
			ex.printStackTrace();

			return "login";
		}
		return SUCCESS;
	}
}
