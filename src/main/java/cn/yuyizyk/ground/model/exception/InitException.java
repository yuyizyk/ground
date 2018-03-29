package cn.yuyizyk.ground.model.exception;

public class InitException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InitException() {
		super();
	}

	public InitException(String msg) {
		super(msg);
	}
}
