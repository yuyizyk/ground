package cn.yuyizyk.ground.model.pojo;

import cn.yuyizyk.ground.annotations.PrimaryKey;
import cn.yuyizyk.ground.annotations.Table;
import cn.yuyizyk.ground.model.pojo.base.SNFPOJO;

@Table("role_info")
public class RoleInfo extends SNFPOJO {
	private static transient final long serialVersionUID = 1L;

	@PrimaryKey
	private String roleid;
	
	private String rolename;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
