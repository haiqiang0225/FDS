package cc.seckill.util;

import cc.seckill.DeviceMainApp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.util
 * @Description : UUIDUtilTest
 * @Create on : 2023/5/25 18:46
 */
@SpringBootTest(classes = DeviceMainApp.class)
public class UUIDUtilTest {
    @Resource
    private UuidUtil uuidUtil;


    @BeforeAll
    public static void init() {
        Env.loadEnvs();
    }

    @Test
    public void testGetUUIDByRedis() throws InterruptedException {
        int nThreads = 100_0;
        CountDownLatch latch = new CountDownLatch(nThreads);
        HashSet<String> set = new HashSet<>();
        Thread[] threads = new Thread[nThreads];
        AtomicReference<String> maxUUID = new AtomicReference<>("");
        for (int i = 0; i < nThreads; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                latch.countDown();
                try {
                    latch.await();
                    String uuidByRedis = uuidUtil.getUUIDByRedis();
//                    System.out.println("Thread-" + finalI + " uuid : " + uuidByRedis);
                    synchronized (UUIDUtilTest.class) {
                        assert !set.contains(uuidByRedis);
                        if (maxUUID.get().compareToIgnoreCase(uuidByRedis) < 0) {
                            maxUUID.set(uuidByRedis);
                        }
                        set.add(uuidByRedis);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(maxUUID.get());
    }

    @Test
    public void testGetOneUUID() {
        String uuidByRedis = uuidUtil.getUUIDByRedis();
        System.out.println(">>>>>>>>>>> UUID : " + uuidByRedis);
        assert uuidByRedis != null && uuidByRedis.length() == 32 + 4;
    }
}
