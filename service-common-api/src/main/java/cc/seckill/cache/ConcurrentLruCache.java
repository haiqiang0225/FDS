package cc.seckill.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description: ConcurrentLruCache 线程安全的LRU缓存<br>
 * date: 2022/12/29 18:30 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public class ConcurrentLruCache<K, V> implements Cache<K, V> {

    private Map<Object, Object> cache;

    private final int capacity;

    private int size;


    private final long timeout;

    public ConcurrentLruCache() {
        this(1024);
    }

    public ConcurrentLruCache(int capacity) {
        this.capacity = capacity;
        cache = new ConcurrentHashMap<>(capacity);
        timeout = 1000L;
    }

    @Override
    public void put(K key, V value) {

    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void clearCache() {

    }
}
