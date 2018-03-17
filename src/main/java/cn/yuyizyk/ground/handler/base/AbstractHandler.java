package cn.yuyizyk.ground.handler.base;

import org.springframework.web.servlet.ModelAndView;

import cn.yuyizyk.ground.core.cache.HttpSession;

/**
 * 抽象handler
 * 
 * @author yuyi
 *
 */
public abstract class AbstractHandler implements Handler {

	protected AbstractHandler() {

	}

	protected HttpSession session;

	protected ModelAndView modelAndView;

	protected AbstractHandler put(String attributeName, Object attributeValue) {
		//modelAndView.addObject(attributeName, attributeValue);
		return this;
	}

}
