package cn.yuyizyk.ground.model.pojo;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;

import cn.yuyizyk.ground.model.annotations.Primarykey;
import cn.yuyizyk.ground.model.annotations.Table;

/**
 * 用户表
 * 
 * @author yuyi
 *
 */

@Table("user_info")
public class UserInfo extends SNFPOJO {

	@Primarykey
	private String userid;
	public String userid123;

	private Set<RoleInfo> roles;
	
	@Primarykey(priority = 1)
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<RoleInfo> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleInfo> roles) {
		this.roles = roles;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public static void main(String[] args) {
		System.out.println(JSONObject.toJSONString(SNFPOJO.getPrimaryKey(new UserInfo())));
	}
}
