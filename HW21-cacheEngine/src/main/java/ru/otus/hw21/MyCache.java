package ru.otus.hw21;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements Cache<K, V> {

  private final Map<K, V> cache = new WeakHashMap();
  private final Collection<WeakReference<CacheListener>> listeners = new HashSet<>();

  @Override
  public void put(K key, V value) {
    cache.put(key, value);
    for (var listener : listeners) {
      if (listener != null) {
        listener.get().notify(key, value, "put");
      }
    }
  }

  @Override
  public void remove(K key) {
    if (cache.containsKey(key)) {
      for (var listener : listeners) {
        if (listener != null) {
          listener.get().notify(key, cache.get(key), "remove");
        }
      }
    }
    cache.remove(key);
  }

  @Override
  public V get(K key) {
    if (cache.containsKey(key)) {
        for (var listener : listeners) {
          if (listener != null) {
            listener.get().notify(key, cache.get(key), "get");
          }
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

  public int size() {
    return cache.size();
  }
}
