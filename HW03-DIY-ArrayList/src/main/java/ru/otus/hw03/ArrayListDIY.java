package ru.otus.hw03;

import java.lang.reflect.Array;
import java.util.*;

public class ArrayListDIY<T> implements List<T> {
    //implements List<T>, RandomAccess {
  /**
   * Размер массива, если не задан явно в конструкторе
   * */
  private final static int DEFAULT_CAPACITY = 10;
  private final static Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
  private static final Object[] EMPTY_ELEMENTDATA = {};
  // Т.к. некоторые VM резервируют память под свои заголовки для массивов, то - 8 элементов
  private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

  /**
   * Переменная modCount нужна, чтобы отслеживать любые структурные изменения массива для выброса
   * итератором эксепшена ConcurrentModificationException. Т.к. ArrayList не синхронизированный.
   * */
  protected transient int modCount = 0;

  /**
   * Кол-во элементов массива
   * */
  private int size;
  /**
   *   transient - делает переменную "невидимой" для сериализации
   */
  transient Object[] internalArray;

  public ArrayListDIY() {
    this.internalArray = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
  }

  public ArrayListDIY(int initialCapacity) {
    if (initialCapacity > 0) {
      this.internalArray = new Object[initialCapacity];
    } else if (initialCapacity == 0) {
      this.internalArray = EMPTY_ELEMENTDATA;
    } else {
      throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
    }
  }

  public ArrayListDIY(Collection<? extends T> c) {
    // Да-да, есть бага, которую тут нужно обойти
    // TODO: посмотри если будет время
    this.internalArray = c.toArray();
    this.size = internalArray.length;
  }

  @Override
  public int size() {
    return this.size;
  }

  private Object[] grow() {
    return grow(this.size + 1);
  }

  /**
   * Запрашиваем увеличение массива хотябы до minCapacity
   *
   * Внутри Arrays.copyOf происходит вызов System.arraycopy
   * */
  private Object[] grow(int minCapacity) {
    return this.internalArray = Arrays.copyOf(internalArray, newCapacity(minCapacity));
  }

  private int newCapacity(int minCapacity) {
    int oldCapacity = internalArray.length;
    // Let's get schwifty, Morty!
    // Битовый сдвиг вправо - уменьшение числа в 2 раза.
    // Т.е. новый размер массива - 150% от старого.
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    // Проверяем случай, если увеличение со сдвигом не сработало
    if (newCapacity - oldCapacity <= 0) {
      // Если из-за того, что массив был пустой, то возвращаем DEFAULT_CAPACITY (10)
      // или minCapacity
      if (internalArray == DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
        return Math.max(DEFAULT_CAPACITY, minCapacity);
      // нельзя расти вниз
      //
      // Почему нельзя сразу проверять на неотрицательность minCapacity (до иф-а)?
      // Сравнения с DEFAULT_CAPACITY и MAX_ARRAY_SIZE уже покрывают этот кейс
      if (minCapacity < 0)
        throw new OutOfMemoryError();
      return minCapacity;
    }
    // Если швифтанулись и не выпали за инт, тогда возвращаем 150% от исходного
    return (newCapacity - MAX_ARRAY_SIZE <= 0)
        ? newCapacity
        : hugeCapacity(minCapacity);
  }

  private static int hugeCapacity(int minCapacity) {
    if (minCapacity < 0) // Whoops
      throw new OutOfMemoryError();
    // TODO: что за чёрная магия?
    // А тут мы проверяем, дествительно ли VM зарезервировала память под заголовки
    // Если повезло, то вернём Integer.MAX_VALUE, иначе Integer.MAX_VALUE - 8
    return (minCapacity > MAX_ARRAY_SIZE)
        ? Integer.MAX_VALUE
        : MAX_ARRAY_SIZE;
  }

  /**
   * Добавляет элемент в конец массива
   * */
  @Override
  public boolean add(T t) {
    modCount++;
    add(t, internalArray, size);
    return true;
  }

  /**
   * Вспомогательный метод. Отделён от add(T t) для поддержки
   * размера байткода метода меньше 35 байт (значение по умолчанию для -XX:MaxInlineSize).
   * Помогает при вызовах add(T t) в циклах скомпилированных C1.
   * TODO: что такое С1? сишный компилятор?
   * */
  private void add(T t, Object[] internalArray, int s) {
    if (s == internalArray.length)
      internalArray = grow();
    internalArray[s] = t;
    this.size = s + 1;
  }

  @Override
  public boolean isEmpty() {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public boolean contains(Object o) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public Iterator<T> iterator() {
    return new Itr();
  }

  @Override
  public Object[] toArray() {
    // Внутри вызов System.arraycopy
    // Возвращает новый массив. Привет сильная типизация?
    return Arrays.copyOf(internalArray, size);
  }

  @Override
  public <T1> T1[] toArray(T1[] a) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @SuppressWarnings("unchecked")
  T internalArray(int index) {
    return (T) internalArray[index];
  }

  @Override
  public T get(int index) {
    // Внутри страшная BiFunction с редьюсом результата и выпросом
    // outOfBoundsCheckIndex runtime exception
    Objects.checkIndex(index, this.size);
    return internalArray(index);
  }

  @Override
  public T set(int index, T element) {
    Objects.checkIndex(index, this.size);
    // Не нужно кастить благодаря ф-ии internalArray.
    T oldValue = internalArray(index);
    internalArray[index] = element;
    return oldValue;
  }

  @Override
  public void add(int index, T element) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public T remove(int index) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public int indexOf(Object o) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public int lastIndexOf(Object o) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  @Override
  public ListIterator<T> listIterator() {
    return new ListItr(0);
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    rangeCheckForAdd(index);
    return new ListItr(index);
  }

  private void rangeCheckForAdd(int index) {
    if (index > size || index < 0)
      throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
  }

  private String outOfBoundsMsg(int index) {
    return "Index: "+index+", Size: "+size;
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException("Not yet implemented...");
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof List)) {
      return false;
    }

    final int expectedModCount = this.modCount;

    boolean equal = (o.getClass() == ArrayListDIY.class)
        ? equalsArrayListDIY((ArrayListDIY<?>) o)
        : equalsRange((List<?>) o, 0, size);

    checkForComodification(expectedModCount);
    return equal;
  }

  // Сравнение ArrayListDIY и не ArrayListDIY (другую реализацию энтерфейса List)
  boolean equalsRange(List<?> other, int from, int to) {
    final Object[] ia = internalArray;
    if (to > ia.length) {
      throw new ConcurrentModificationException();
    }
    var oit = other.iterator();
    for (; from < to; from++) {
      if (!oit.hasNext() || !Objects.equals(ia[from], oit.next())) {
        return false;
      }
    }
    return !oit.hasNext();
  }

  // Сравнение двух ArrayListDIY
  private boolean equalsArrayListDIY(ArrayListDIY<?> other) {
    final int otherModCount = other.modCount;
    // Приравниваем, чтобы зафиксировать размер массива, который был на момент вызова ф-ии?
    final int s = size;
    boolean equal;
    if (equal = (s == other.size)) {
      // Если рамеры массивов совпадают, тогда сравниваем элементы
      final Object[] otherIa = other.internalArray;
      final Object[] ia = internalArray;
      // Немного странная проверка
      if (s > ia.length || s > otherIa.length) {
        throw new ConcurrentModificationException();
      }
      // Сравниваем поэлементно до первого несовпадения или конца массива
      for (int i = 0; i < s; i++) {
        if (!Objects.equals(ia[i], otherIa[i])) {
          equal = false;
          break;
        }
      }
    }
    // Проверяем, что массив не был изменён во время сравнения
    other.checkForComodification(otherModCount);
    return equal;
  }

  private void checkForComodification(final int expectedModCount) {
    if (modCount != expectedModCount) {
      throw new ConcurrentModificationException();
    }
  }

  public int hashCode() {
    int expectedModCount = modCount;
    int hash = hashCodeRange(0, size);
    checkForComodification(expectedModCount);
    return hash;
  }

  int hashCodeRange(int from, int to) {
    final Object[] es = internalArray;
    if (to > es.length) {
      throw new ConcurrentModificationException();
    }
    int hashCode = 1;
    for (int i = from; i < to; i++) {
      Object e = es[i];
      hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
    }
    return hashCode;
  }

  public void sort(Comparator<? super  T> c) {
    final int expectedModCount = modCount;
    // Вызывает ComparableTimSort.sort метод
    Arrays.sort((T[]) internalArray, 0, size, c);
    if (modCount != expectedModCount)
      throw new ConcurrentModificationException();
    modCount++;
  }

  private class Itr implements Iterator<T> {
    // Указывает на текущий элемент
    int cursor;
    // Указывает на последний возвращённый элемент
    int lastRet = -1;
    // айди структурных изменений массива
    // позволяет понять, поменялся ли мир, пока мы "думали"
    int expectedModCount = modCount;

    Itr() {}

    @Override
    public boolean hasNext() {
      // Если не конец массива, то следующий элемент существует
      return cursor != size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T next() {
      // Пачка проверок на то, "не ушла ли земля из под ног"
      // Сверяем, что с момента создания итератора не было структурных изменинй массива
      checkForComodification();
      // Записываем крусор во временную переменную, чтобы перезаписать на то, что могло быть
      // перезаписано, пока
      int i = cursor;
      // встроенный hasNext
      if (i >= size)
        throw new NoSuchElementException();
      // аналогично с int i, но почему после проверки а-ля hasNext?
      // т.к. копирование дорогая операция???
      Object[] internalArray = ArrayListDIY.this.internalArray;
      if (i >= internalArray.length)
        throw new ConcurrentModificationException();
      // собственно инкремент курсора
      cursor = i + 1;
      // и возврат нового эелемента, на который мы перешли
      return (T) internalArray[lastRet = i];
    }

    final void checkForComodification() {
      if (modCount != expectedModCount)
        throw new ConcurrentModificationException();
    }
  }

  /**
   * Апгрейд итератора до двунаправленного итератора
   * */
  private class ListItr extends Itr implements ListIterator<T> {

    ListItr(int index) {
      super();
      cursor = index;
    }

    @Override
    public boolean hasPrevious() {
      return cursor != 0;
    }

    @Override
    public int nextIndex() {
      return cursor;
    }

    @Override
    public int previousIndex() {
      // Хм, странно, почему нет проверки (hasPrevious)
      return cursor - 1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T previous() {
      checkForComodification();
      int i = cursor - 1;
      if (i < 0)
        throw new NoSuchElementException();
      Object[] internalArray = ArrayListDIY.this.internalArray;
      if (i >= internalArray.length)
        throw new ConcurrentModificationException();
      cursor = i;
      return (T) internalArray[lastRet = i];
    }

    /**
     * Изменяет последний элемент, возвращённый методами next() или previous()
     * */
    @Override
    public void set(T t) {
     if (lastRet < 0)
       throw new IllegalStateException();
     checkForComodification();

     try {
       ArrayListDIY.this.set(lastRet, t);
     } catch (IndexOutOfBoundsException ex) {
       throw new ConcurrentModificationException();
     }
    }

    @Override
    public void add(T t) {
      throw new UnsupportedOperationException("Not yet implemented...");
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Not yet implemented...");
    }
  }

}
