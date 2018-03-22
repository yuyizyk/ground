package cn.yuyizyk.ground.model.pojo;

import cn.yuyizyk.ground.model.annotations.Table;

@Table("role_info")
public class RoleInfo extends SNFPOJO {
	private static transient final long serialVersionUID = 1L;

	private String rolename;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
}
