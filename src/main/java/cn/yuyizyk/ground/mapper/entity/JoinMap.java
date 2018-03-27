package cn.yuyizyk.ground.mapper.entity;

import java.util.LinkedList;
import java.util.List;

import cn.yuyizyk.ground.model.entity.Entry;
import cn.yuyizyk.ground.model.pojo.base.POJO;

public class JoinMap {

	private Class<? extends POJO> cls;

	private List<Entry<String, Object>> filter = new LinkedList<Entry<String, Object>>();

	public JoinMap(Class<? extends POJO> cls) {
		this.cls = cls;
	}

	public Class<? extends POJO> getCls() {
		return cls;
	}

	public void setCls(Class<? extends POJO> cls) {
		this.cls = cls;
	}

	public List<Entry<String, Object>> getFilter() {
		return filter;
	}

	public void setFilter(List<Entry<String, Object>> filter) {
		this.filter = filter;
	}

	public JoinMap addfilter(String right, Object left) {
		filter.add(new Entry<String, Object>(right, left));
		return this;
	}

}
