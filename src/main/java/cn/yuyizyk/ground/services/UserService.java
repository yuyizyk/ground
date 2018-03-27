package cn.yuyizyk.ground.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yuyizyk.ground.mapper.UserMapper;
import cn.yuyizyk.ground.model.pojo.UserInfo;
import cn.yuyizyk.ground.services.base.AbstractService;

@Service
public class UserService extends AbstractService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 账号
	 * 
	 * @param username
	 * @return
	 */
	public UserInfo byAccount(String username) {
		return userMapper.byAccount(username);
	}

}
