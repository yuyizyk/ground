package cn.yuyizyk.ground.model.pojo;

import cn.yuyizyk.ground.model.annotations.Table;
import cn.yuyizyk.ground.model.pojo.base.POJO;

/**
 * 用户与角色中间表
 * 
 * @author yuyi
 *
 */
@Table("user_to_role")
public class UserToRole extends POJO {
	private static transient final long serialVersionUID = 1L;

	private String uid;

	private String rid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

}
