package ru.otus;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunTest {

  private static final Logger logger = LoggerFactory.getLogger(RunTest.class);

  @Test
  void addGetRemoveCache() {
    Cache<Integer, Long> cache = new MyCache<>();
    CacheListener<Integer, Long> listener1 =
        (key, value, action) -> logger.info("key:{}, value:{}, action: {}",  key, value, action);

    cache.addListener(listener1);
    cache.put(1, 123L);
    cache.get(1);
    cache.remove(1);
    cache.removeListener(listener1);
  }
}
