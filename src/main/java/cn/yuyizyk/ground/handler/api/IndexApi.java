package cn.yuyizyk.ground.handler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yuyizyk.ground.services.UserService;

@Controller
@RequestMapping("/api/index")
public class IndexApi {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	@ResponseBody
	public String index() {
		userService.byAccount("123");
		return "My First Spring Mvc";
	}

}
