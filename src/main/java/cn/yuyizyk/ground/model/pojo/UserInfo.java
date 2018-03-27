package cn.yuyizyk.ground.model.pojo;

import cn.yuyizyk.ground.model.annotations.Key;
import cn.yuyizyk.ground.model.annotations.Table;
import cn.yuyizyk.ground.model.pojo.base.POJO;
import cn.yuyizyk.ground.model.pojo.base.SNFPOJO;
import cn.yuyizyk.ground.util.data.SerializationUtil;

/**
 * 用户表
 * 
 * @author yuyi
 *
 */
@Table("user_info")
public class UserInfo extends SNFPOJO {
	private static transient final long serialVersionUID = 1L;

	@Key
	private String userid;

	private transient String password;

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

	public static void main(String[] args) {
		UserInfo ui = POJO.formJsonStr("{\"userid\":\"1123\"}", UserInfo.class);
		System.out.println(SerializationUtil.toJsonStr(ui.primaryKey()));
		System.out.println(ui);
	}
}
