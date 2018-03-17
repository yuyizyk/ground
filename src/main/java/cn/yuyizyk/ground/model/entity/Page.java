package cn.yuyizyk.ground.model.entity;

import java.util.ArrayList;

/**
 * 页
 * 
 * @author yuyi
 *
 */
public class Page<T> extends ArrayList<T> {
	private static final long serialVersionUID = -3183963314104279483L;

	/**
	 * 当前页
	 */
	private Integer page;
	/**
	 * 页长度
	 */
	private Integer size;
	/**
	 * 总数
	 */
	private Long totals;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Long getTotals() {
		return totals;
	}

	public void setTotals(Long totals) {
		this.totals = totals;
	}

	/**
	 * 转化为json字符串
	 * 
	 * @return
	 */
	public String toJSONString() {
		return null;
	}

	/**
	 * 转化为xml字符串
	 * 
	 * @return
	 */
	public String toXMLString() {
		return null;
	}
}
