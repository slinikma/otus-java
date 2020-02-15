package ru.otus;

public interface CacheListener<K, V> {
  void notify(K key, V value, String action);
}
