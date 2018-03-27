package cn.yuyizyk.ground.mapper.entity;

public class JoinField {

	String tablename;

	String field;

	public JoinField(String tablename, String field) {
		this.field = field;
		this.tablename = tablename;
	}

	@Override
	public String toString() {
		return new StringBuffer(tablename).append(".").append(field).toString();
	}
}
