package ru.otus.hw21;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements Cache<K, V> {

  private static final Logger logger = LoggerFactory.getLogger(MyCache.class);

  private final Map<K, V> cache = new WeakHashMap<>();
  private final Collection<WeakReference<CacheListener>> listeners = new HashSet<>();
  //создаем очередь ReferenceQueue
  private static ReferenceQueue<CacheListener> refQueue = new ReferenceQueue<>();

  @Override
  public void put(K key, V value) {
    cleanupUnusedListenerRefs();
    cache.put(key, value);
    for (var listener : listeners) {
      CacheListener<K,V> lst = listener.get();
      if (lst != null) {
        lst.notify(key, value, "put");
      }
    }
  }

  @Override
  public void remove(K key) {
    cleanupUnusedListenerRefs();
    if (cache.containsKey(key)) {
      for (var listener : listeners) {
        CacheListener<K,V> lst = listener.get();
        if (lst != null) {
          lst.notify(key, cache.get(key), "remove");
        }
      }
    }
    cache.remove(key);
  }

  @Override
  public V get(K key) {
    cleanupUnusedListenerRefs();
    if (cache.containsKey(key)) {
        for (var listener : listeners) {
          CacheListener<K,V> lst = listener.get();
          if (lst != null) {
            lst.notify(key, cache.get(key), "get");
          }
        }
        return cache.get(key);
    }
    return null;
  }

  @Override
  public void addListener(CacheListener listener) {
    cleanupUnusedListenerRefs();
    this.listeners.add(WeakElement.create(listener));
  }

  @Override
  public void removeListener(CacheListener listener) {
    this.listeners.remove(WeakElement.create(listener));
  }

  public int size() {
    return cache.size();
  }

  private void cleanupUnusedListenerRefs() {
    Reference<? extends CacheListener> removedListenerRef;
    while ((removedListenerRef = refQueue.poll()) != null) {
      logger.info("Ref queue poll: {}", removedListenerRef);
      this.removeListener(removedListenerRef.get());
    }
  }


  // Понял, что удаление из моего HashSet не работает,
  // т.к. сильная ссылка на объект != слабой ссылке
  // WeakElement сохраняет hash исходной сильной ссылки (объекта?) и переопределяет equals
  // Данный кусочек кода подсмотрел
  static public class WeakElement extends WeakReference {
    private int hash; /* Hashcode of key, stored here since the key
                               may be tossed by the GC */


    private WeakElement(CacheListener o) {
      super(o);
      hash = o.hashCode();
    }

    public static WeakElement create(CacheListener o) {

      //создаем Phantom Reference на объект типа CacheListener и "подвязываем" ее на переменную o
      PhantomReference<CacheListener> phantom = new PhantomReference<>(o, refQueue);

      return (o == null) ? null : new WeakElement(o);
    }

    /* A WeakElement is equal to another WeakElement iff they both refer to objects
           that are, in turn, equal according to their own equals methods */
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (!(o instanceof WeakElement))
        return false;
      Object t = this.get();
      Object u = ((WeakElement) o).get();
      if (t == u)
        return true;
      if ((t == null) || (u == null))
        return false;
      return t.equals(u);
    }

    public int hashCode() {
      return hash;
    }
  }
}
