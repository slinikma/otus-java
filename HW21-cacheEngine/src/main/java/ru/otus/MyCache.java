package ru.otus;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements Cache {

  private final Map cache = new WeakHashMap();
  private final Collection<WeakReference<CacheListener>> listeners = new HashSet<>();

  @Override
  public void put(Object key, Object value) {
    if (!cache.containsKey(key)) {
      cache.put(key, value);
      for (var listener : listeners) {
        listener.get().notify(key, value, "put");
      }
    }
  }

  @Override
  public void remove(Object key) {
    if (cache.containsKey(key)) {
      for (var listener : listeners) {
        listener.get().notify(key, cache.get(key), "remove");
      }
    }
    cache.remove(key);
  }

  @Override
  public Object get(Object key) {
    if (cache.containsKey(key)) {
        for (var listener : listeners) {
          listener.get().notify(key, cache.get(key), "get");
        }
        return cache.get(key);
    }
    return null;
  }

  @Override
  public void addListener(CacheListener listener) {
    this.listeners.add(new WeakReference<>(listener));
  }

  @Override
  public void removeListener(CacheListener listener) {
    this.listeners.remove(listener);
  }
}