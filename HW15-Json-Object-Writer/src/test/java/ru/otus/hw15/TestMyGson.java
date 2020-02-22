package ru.otus.hw15;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMyGson {

  @AllArgsConstructor
  @EqualsAndHashCode
  static class NestedBagOfPrimitives {
    int intVal;
    String strVal;
    int[] intArr;
    List<Object> objList;
    Set<Object> objSet;
    Map<Object, Object> objMap;
  }

  @AllArgsConstructor
  @EqualsAndHashCode
  static class BagOfPrimitives {
    int intVal;
    String strVal;
    int[] intArr;
    List<Object> objList;
    Set<Object> objSet;
    Map<Object, Object> objMap;
    NestedBagOfPrimitives object;
  }

  Gson gson;
  MyGson myGson;

  BagOfPrimitives bagOfPrimitives;
  NestedBagOfPrimitives nestedBagOfPrimitives;

  @BeforeEach
  public void getGson() {
    gson = new Gson();
    myGson = new MyGson();
  }

  @BeforeEach
  public void createObject() {
    var arrayList = new ArrayList<Object>();
    arrayList.add("string");
    arrayList.add(2);
    arrayList.add(3L);
    arrayList.add(true);
    arrayList.add(3.14);

    var hashSet = new HashSet<Object>();
    hashSet.add("string");
    hashSet.add(2);
    hashSet.add(3L);
    hashSet.add(true);
    hashSet.add(3.14);
    hashSet.add(arrayList);

    var hashMap = new HashMap<Object, Object>();
    hashMap.put(1, "string");
    hashMap.put(2, 2);
    hashMap.put(3, 3L);
    hashMap.put(4, true);
    hashMap.put(5, 3.14);
    hashMap.put(6, arrayList);

    nestedBagOfPrimitives = new NestedBagOfPrimitives(1,
        "string",
        new int[]{1, 2, 3},
        arrayList,
        hashSet,
        hashMap);

    bagOfPrimitives = new BagOfPrimitives(1,
        "string",
        new int[]{1, 2, 3},
        arrayList,
        hashSet,
        hashMap,
        nestedBagOfPrimitives);
  }

  @Test
  @DisplayName("Serialize object to json")
  public void SerializeObjectToJson() throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException {

    System.out.println("GSON: " + gson.toJson(bagOfPrimitives));
    System.out.println("myGSON: " + myGson.toJson(bagOfPrimitives));

    assertEquals(gson.toJson(bagOfPrimitives), myGson.toJson(bagOfPrimitives));
  }

//  @Test
//  @DisplayName("Serialize json to object")
//  public void SerializeJsonToObject() {
//    var arr = new ArrayList<String>();
//    arr.add("1");
//    arr.add("2");
////    var bagOfPrimitives = new BagOfPrimitives(22, "test", 10, arr, new int[]{1,3,4,5});
////    var bagOfPrimitives = new BagOfPrimitives(new int[]{1,3,4,5});
//    var bagOfPrimitivesJSON = "{\"value1\":22,\"value2\":\"test\",\"value3\":10}";
//
////    assertEquals(gson.fromJson(bagOfPrimitivesJSON, BagOfPrimitives.class) ,bagOfPrimitives);
//  }
}
