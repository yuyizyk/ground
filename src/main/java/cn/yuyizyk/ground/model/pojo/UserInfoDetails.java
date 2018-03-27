package cn.yuyizyk.ground.model.pojo;

import java.util.List;

import cn.yuyizyk.ground.mapper.Mapper;
import cn.yuyizyk.ground.mapper.SelfMapper;
import cn.yuyizyk.ground.mapper.entity.JoinField;
import cn.yuyizyk.ground.mapper.entity.JoinMap;
import cn.yuyizyk.ground.model.annotations.Key;
import cn.yuyizyk.ground.model.annotations.Table;
import cn.yuyizyk.ground.model.pojo.base.SNFPOJO;
import cn.yuyizyk.ground.model.pojo.base.SelfMapping;

@Table("user_info")
public class UserInfoDetails extends SNFPOJO implements SelfMapping {
	private static final long serialVersionUID = 1L;
	@Key
	private String userid;

	private transient String password;

	private List<RoleInfo> role;

	@Override
	public void autoMapping(SelfMapper mapper) {
		role = mapper.manyToMany(RoleInfo.class, new JoinMap(UserToRole.class).addfilter("rid",
				new JoinField(RoleInfo.tableName(RoleInfo.class), "roleid")));
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<RoleInfo> getRole() {
		return role;
	}

	public void setRole(List<RoleInfo> role) {
		this.role = role;
	}

}
