package cn.yuyizyk.ground.model.pojo;

import java.util.List;

import org.apache.ibatis.mapping.FetchType;

import cn.yuyizyk.ground.model.annotations.AutoMap;
import cn.yuyizyk.ground.model.annotations.Generated;
import cn.yuyizyk.ground.model.annotations.PrimaryKey;
import cn.yuyizyk.ground.model.annotations.Table;
import cn.yuyizyk.ground.model.pojo.base.POJO;
import cn.yuyizyk.ground.model.pojo.base.SNFPOJO;
import cn.yuyizyk.ground.util.data.SerializationUtil;
import org.apache.ibatis.annotations.Many;

/**
 * 用户表
 * 
 * @author yuyi
 *
 */
@Table("user_info")
public class UserInfo extends SNFPOJO {
	private static transient final long serialVersionUID = 1L;

	@PrimaryKey
	@Generated
	private String userid;

	private transient String password;

	private String account;
	@AutoMap(fieldName = "userid", many = @Many(select = "cn.yuyizyk.ground.mapper.RoleMapper.listByUserid", fetchType = FetchType.LAZY))
	private List<RoleInfo> roles;

	public List<RoleInfo> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleInfo> roles) {
		this.roles = roles;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public static void main(String[] args) {
		UserInfo ui = POJO.formJsonStr("{\"userid\":\"1123\"}", UserInfo.class);
		System.out.println(SerializationUtil.toJsonStr(ui.primaryKey()));
		System.out.println(ui);
	}
}
