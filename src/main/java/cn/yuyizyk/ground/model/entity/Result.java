package cn.yuyizyk.ground.model.entity;

/**
 * 结果集
 * 
 * @author yuyi
 * 
 * @param <T>
 */
public class Result<T> {
	/**
	 * 自定义code值
	 */
	public int code;
	/**
	 * 自定义code值说明
	 */
	public String message;
	/**
	 * 数据主体
	 */
	public T data;
}
