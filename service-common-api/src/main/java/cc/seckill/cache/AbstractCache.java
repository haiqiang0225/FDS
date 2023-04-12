package cc.seckill.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description: AbstractCache <br>
 * date: 2022/12/29 14:14 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public abstract class AbstractCache {
    private Map<Object, Object> cache;

    private final int capacity;

    private int size;

    public AbstractCache() {
        this(16);
    }

    public AbstractCache(int capacity) {
        this.capacity = capacity;
        cache = new ConcurrentHashMap<>(capacity);
    }

    public int getSize() {
        return this.size;
    }


}
