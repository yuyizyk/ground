package cn.yuyizyk.ground.handler.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面首页
 * 
 * @author yuyi
 *
 */
@Controller
@RequestMapping("/page/login")
public class LoginPage {

	@RequestMapping(value = { "/", "" })
	public ModelAndView Login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login.ftl");
		mv.addObject("title", "My First Spring Mvc");
		return mv;
	}

}
