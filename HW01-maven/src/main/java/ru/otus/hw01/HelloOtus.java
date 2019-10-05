package ru.otus.hw01;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * To run JAR execute: java --enable-preview -jar target/HW01-maven-2019-06-SNAPSHOT-jar-with-dependencies.jar
 */

public class HelloOtus {

  public static void main(String[] args) {

    Multimap<Integer, Set<String>> hashMultMap = HashMultimap.create();
    Set numbersSet = new HashSet<String>();
    numbersSet.add("One");
    numbersSet.add("Two");
    numbersSet.add("Three");

    Set namesSet = new HashSet<String>();
    namesSet.add("Sam");
    namesSet.add("John");

    hashMultMap.put(1, numbersSet);
    hashMultMap.put(2, namesSet);

    System.out.println("Hash map size: " + hashMultMap.size());

    for (Map.Entry e : hashMultMap.entries()) {
      System.out.println("Hash multimap key: " + e.getKey());
      System.out.println("Hash multimap value: " + e.getValue());
    }
  }
}
