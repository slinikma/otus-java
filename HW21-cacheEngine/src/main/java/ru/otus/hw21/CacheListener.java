package ru.otus.hw21;

public interface CacheListener<K, V> {
  void notify(K key, V value, String action);
}
