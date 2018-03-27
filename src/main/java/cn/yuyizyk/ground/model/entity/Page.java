package cn.yuyizyk.ground.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 页数据
 * 
 * @author yuyi
 *
 */
public class Page<T> implements Serializable {
	private static final transient long serialVersionUID = 1l;

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

	private List<T> data;

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

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
