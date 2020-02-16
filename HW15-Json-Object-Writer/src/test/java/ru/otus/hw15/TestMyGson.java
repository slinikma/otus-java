package ru.otus.hw15;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMyGson {

  Gson gson;
  MyGson myGson;

  @AllArgsConstructor
  @EqualsAndHashCode
  static class BagOfPrimitives {
    int value1;
    String value2;
    int value3;
    ArrayList<String> array;
    int[] intField;
  }

  @BeforeEach
  public void getMyGsonObject() {
    gson = new Gson();
    myGson = new MyGson();
  }

  @Test
  @DisplayName("Serialize object to json")
  public void SerializeObjectToJson() throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
    var arr = new ArrayList<String>();
    arr.add("1");
    arr.add("2");
    var bagOfPrimitives = new BagOfPrimitives(22, "test", 10, arr, new int[]{1,3,4,5});
//    var bagOfPrimitives = new BagOfPrimitives(new int[]{1,3,4,5});
    var bagOfPrimitivesJSON = "{\"value1\":22,\"value2\":\"test\",\"value3\":10}";

    System.out.println("GSON: " + gson.toJson(bagOfPrimitives));
    System.out.println("myGSON: " + myGson.toJson(bagOfPrimitives));

    assertEquals(gson.toJson(bagOfPrimitives), gson.toJson(bagOfPrimitives));
  }

  @Test
  @DisplayName("Serialize json to object")
  public void SerializeJsonToObject() {
    var arr = new ArrayList<String>();
    arr.add("1");
    arr.add("2");
    var bagOfPrimitives = new BagOfPrimitives(22, "test", 10, arr, new int[]{1,3,4,5});
//    var bagOfPrimitives = new BagOfPrimitives(new int[]{1,3,4,5});
    var bagOfPrimitivesJSON = "{\"value1\":22,\"value2\":\"test\",\"value3\":10}";

    assertEquals(gson.fromJson(bagOfPrimitivesJSON, BagOfPrimitives.class) ,bagOfPrimitives);
  }
}
