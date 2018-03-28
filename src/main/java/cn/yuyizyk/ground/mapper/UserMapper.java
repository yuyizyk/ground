package cn.yuyizyk.ground.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.yuyizyk.ground.model.pojo.UserInfo;

public interface UserMapper extends SNFMapper {

	/**
	 * 
	 * @param username
	 * @return
	 */
	@Select("SELECT * FROM user_info WHERE userid=#{userid} ")
	UserInfo byAccount(@Param("userid") String userid);

	
}
