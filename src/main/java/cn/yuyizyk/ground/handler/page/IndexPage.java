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
@RequestMapping("/")
public class IndexPage {

	@RequestMapping(value = { "/", "/index", "/home", "/index.html" })
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
//		mv.setViewName("index/home.ftl");
		mv.setViewName("login.ftl");
		mv.addObject("title", "My First Spring Mvc");
		return mv;
	}
	
}
