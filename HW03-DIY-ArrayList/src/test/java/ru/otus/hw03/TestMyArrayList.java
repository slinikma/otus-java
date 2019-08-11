package ru.otus.hw03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.google.common.collect.Ordering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMyArrayList {
  private final static int LIST_SIZE = 30;
  private List<Integer> myListOfInts;
  private List<Integer> listOfInts;

  /**
   * Create new array list
   * */
  @BeforeEach
  public void createList() {
    myListOfInts = new ArrayListDIY<>(LIST_SIZE);
    listOfInts = new ArrayList<>(LIST_SIZE);

    getRandomIntStream(0,10).limit(LIST_SIZE)
        .forEach(elem -> {
          listOfInts.add(elem);
          myListOfInts.add(elem);
        });
  }

  @Test
  @DisplayName("Collections.addAll calls next List methods: add(E e)")
  public void collectionsAddAll () {
    Collections.addAll(myListOfInts, 1,2,3,4,5);
    Collections.addAll(listOfInts, 1,2,3,4,5);

    assertEquals(listOfInts, myListOfInts);
  }

  @Test
  @DisplayName("Collections.copy calls List methods: size(), get(), set(), listIterator()")
  // TODO: Так-же проверяет instanceof RandomAccess. Посмотреть
  public void collectionsCopy () {
    List<Integer> myListOfInts_copy = new ArrayListDIY<>(30);
    List<Integer> listOfInts_copy = new ArrayList<>(30);

    IntStream.range(40, 70).forEach(elem -> {
      listOfInts_copy.add(elem);
      myListOfInts_copy.add(elem);
    });

    Collections.copy(myListOfInts_copy, myListOfInts);
    Collections.copy(listOfInts_copy, listOfInts);

    assertEquals(myListOfInts, myListOfInts_copy);
    assertEquals(listOfInts, listOfInts_copy);
  }

  @Test
  @DisplayName("Collections.sort calls next List methods: sort()")
  public void collectionsSort() {
    // Вызывает sort метод у переданной коллекции.
    // Comparator задан как null
    Collections.sort(listOfInts);
    Collections.sort(myListOfInts);

    assertTrue(Ordering.natural().isOrdered(listOfInts));
    assertTrue(Ordering.natural().isOrdered(myListOfInts));
  }

  private static IntStream getRandomIntStream(int min, int max) {
    Random r = new Random();
    return r.ints(min, (max + 1));
  }
}
