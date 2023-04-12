package cc.seckill.cache;

/**
 * description: Cache <br>
 * date: 2022/12/29 13:47 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public interface Cache<K, V> {

    void put(K key, V value);

    V get(K key);

    V remove(K key);

    int getSize();

    void clearCache();
}
