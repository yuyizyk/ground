package cn.yuyizyk.ground.model.entity;

/**
 * 
 * @author yuyi
 * 
 * @param <K>
 * @param <V>
 */
public class Entry<K, V> implements java.util.Map.Entry<K, V> {

	final private K key;
	final private V value;

	public Entry(K k, V v) {
		this.key = k;
		this.value = v;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public Object setValue(Object value) {
		return null;
	}

}
