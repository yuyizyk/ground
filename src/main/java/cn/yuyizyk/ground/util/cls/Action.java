package cn.yuyizyk.ground.util.cls;

@FunctionalInterface
public interface Action<T> {
	void apply(T t);
}
