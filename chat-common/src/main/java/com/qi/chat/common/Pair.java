package com.qi.chat.common;

public class Pair<K, V> {

	private K k;
	private V v;

	public Pair(K k, V v) {
		this.k = k;
		this.v = v;
	}

	public void setPair(K k, V v) {
		this.k = k;
		this.v = v;
	}

	public K first() {
		return k;
	}

	public V second() {
		return v;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Pair))
			return false;
		return this.k.equals(((Pair<?, ?>) obj).k);
	}
}
