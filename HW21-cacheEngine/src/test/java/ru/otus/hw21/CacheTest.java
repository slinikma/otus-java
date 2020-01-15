package ru.otus.hw21;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheTest {

  private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);

  @Test
  void addGetRemoveCache() {
    Cache<Integer, Long> cache = new MyCache<>();
    CacheListener<Integer, Long> listener1 =
        (key, value, action) -> logger.info("key:{}, value:{}, action: {}",  key, value, action);

    cache.addListener(listener1);
    cache.put(new Integer(1), 123L);
    cache.get(1);
    cache.remove(1);
    cache.removeListener(listener1);
  }

  @Test
  void gcRemovesElems() throws InterruptedException {
    Cache<Integer, Long> cache = new MyCache<>();
    CacheListener<Integer, Long> listener1 =
        (key, value, action) -> logger.info("key:{}, value:{}, action: {}",  key, value, action);

    cache.addListener(listener1);

    int limit = 100;
    for (int idx = 0; idx < limit; idx++) {
      cache.put(new Integer(idx), 123L);
    }

    logger.info("\n\n------------------------------");
    logger.info("before gc: {}", cache.size());
    for (int idx = 0; idx < cache.size(); idx++) {
      logger.info("key:{}, value:{}", idx, cache.get(idx));
    }

    System.gc();
    Thread.sleep(5000);

    logger.info("\n\n------------------------------");
    logger.info("after gc: {}", cache.size());
    for (int idx = 0; idx < cache.size(); idx++) {
      logger.info("key:{}, value:{}", idx, cache.get(idx));
    }
  }
}
