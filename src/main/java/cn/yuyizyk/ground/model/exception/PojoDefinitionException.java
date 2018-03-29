package cn.yuyizyk.ground.model.exception;

public class PojoDefinitionException extends InitException {
	private static final long serialVersionUID = 1L;

	public PojoDefinitionException(Class<?> cls, String msg) {
		super(String.format(" %s Definition Exception . %s", cls.getName(), msg));
	}
}
