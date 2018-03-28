package cn.yuyizyk.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yuyizyk.ground.core.WebApplicationInitializer;
import cn.yuyizyk.ground.core.config.ApplicationDataConfig;
import cn.yuyizyk.ground.mapper.UserMapper;
import cn.yuyizyk.ground.model.pojo.UserInfo;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(classes = { WebApplicationInitializer.class, ApplicationDataConfig.class })
public class Demo {
	@Autowired
	private UserMapper userMapper;

	// @Test
	public void test1() {
		UserInfo ui = new UserInfo();
		System.out.println(userMapper.list(ui));

	}

	@Test
	public void test() {
		System.out.println("测试Spring整合Junit4进行单元测试");
		UserInfo ui = new UserInfo();
		// System.out.println(userMapper.total(ui));
		// ui.setUserid("2");
		// System.out.println(userMapper.list(ui));
		// System.out.println(userMapper.list(ui));
		ui.setAccount("123");
		System.out.println(userMapper.insert1(ui));

		// ui.setUserid(null);
		// UserInfo ui1 = new UserInfo();
		// ui1.setUserid("121112");
		// System.out.println(userMapper.insert(ui1));
		// System.out.println(userMapper.page(ui, 1, 10));
		// ui.setPassword("123");
		// System.out.println(userMapper.update(ui1, ui));
		// System.out.println(userMapper.list(UserInfo.class, ui.toSqlMap()));
	}
}
