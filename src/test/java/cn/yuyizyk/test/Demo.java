package cn.yuyizyk.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yuyizyk.ground.WebApplicationInitializer;
import cn.yuyizyk.ground.core.config.ApplicationDataConfig;
import cn.yuyizyk.ground.mapper.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(classes = { WebApplicationInitializer.class, ApplicationDataConfig.class })
public class Demo {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	@Test
	public void test() {
		System.out.println("测试Spring整合Junit4进行单元测试");
		System.out.println(userMapper);
	}
}
