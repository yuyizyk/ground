package cn.yuyizyk.ground.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import cn.yuyizyk.ground.mapper.base.SNFMapper;
import cn.yuyizyk.ground.model.pojo.RoleInfo;

public interface RoleMapper extends SNFMapper {

	/**
	 * 依据用户id查找对应的角色列表
	 * 
	 * @param username
	 * @return
	 */
	@Select("SELECT * FROM role_info WHERE roleid in (select rid from user_to_role where  uid=#{userid} ) ")
	List<RoleInfo> listByUserid(String userid);

}
