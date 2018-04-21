package cn.yuyizyk.ground.exception;

/**
 * 主键并非唯一
 * 
 * @author yuyi
 *
 */
public class NotUniquePrimaryKeyException extends RuntimeException {

	public NotUniquePrimaryKeyException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;

}
