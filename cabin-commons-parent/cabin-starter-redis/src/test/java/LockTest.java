import com.cabin.commons.redis.Lock.RedisDistributedLock;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.CountDownLatch;

/**
 * @author 伍六七
 * @date 2023/5/23 10:14
 * 需要在yaml配置redis才能连接，否则空指针报错RedisTemplate
 */
public class LockTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedisDistributedLock redisDistributedLock;

    private static final String LOCK_KEY = "product:p001";

    @BeforeEach
    public void setUp() {
        // 初始化 Redis 缓存数据
        redisTemplate.opsForValue().set("p001", 100);
    }

    @Test
    public void testLock() throws InterruptedException {
        // 创建 10 个线程，并发执行加锁和解锁方法
        int threadNum = 10;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadNum);

        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();
                    boolean lockResult = redisDistributedLock.tryLock(LOCK_KEY, Thread.currentThread().getName(), 5);
                    if (lockResult) {
                        Integer stock = (Integer) redisTemplate.opsForValue().get("p001");
                        if (stock > 0) {
                            redisTemplate.opsForValue().decrement("p001");
                            System.out.println("减少库存成功，当前库存为：" + redisTemplate.opsForValue().get("p001"));
                        } else {
                            System.out.println("库存不足，无法减少库存");
                        }
                    } else {
                        System.out.println("获取分布式锁失败，无法减少库存");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    redisDistributedLock.releaseLock(LOCK_KEY, Thread.currentThread().getName());
                    endLatch.countDown();
                }
            }).start();
        }

        // 启动多个线程
        startLatch.countDown();

        // 等待所有线程执行完成
        endLatch.await();
    }
}
