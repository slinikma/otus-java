package ru.otus.hw15.visitor;

import javax.json.JsonObject;
import javax.json.JsonValue;

public interface TraversedType {
  // Метод необходим для реализации двойной диспетчеризации или double dispatch
  // Позволяет заставить компилятор использовать позднее динамическое связывание
  // вместо раннего статического
  // Т.е. смысл в том, чтобы в каждом конкретном элементе вызывать visitor и
  // передавать в него указатель на реализацию элемента, а не интерфейс элемента, чтобы потом
  // в рантайме определять что за элемент (джава не определит правильно)
  //
  // Получается что я статически связал метод визитора с конкретным типом поля, но какой вызовется визитор - это
  // уже динамическое связывание ???
  JsonValue accept(Visitor visitor) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException;
}
