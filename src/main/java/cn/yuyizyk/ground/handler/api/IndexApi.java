package cn.yuyizyk.ground.handler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yuyizyk.ground.services.UserService;

@Controller
@RequestMapping("/api/index")
public class IndexApi {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "" })
	@ResponseBody
	public String index() {
		userService.byAccount("123");
		return "My First Spring Mvc";
	}

}
