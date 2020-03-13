package ru.otus.hw09;

import ru.otus.hw09.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

public class IoC {

  static MyClassInterface createMyClass() {
    InvocationHandler handler = new MyInvocationHandler(new MyClassImpl());
    return (MyClassInterface) Proxy.newProxyInstance(IoC.class.getClassLoader(),
        new Class<?>[]{MyClassInterface.class}, handler);
  }

  static class MyInvocationHandler implements InvocationHandler {
    private final MyClassInterface myClass;
    private Set<Method> methodsToPrint;

    MyInvocationHandler(MyClassInterface myClass) {
      this.myClass = myClass;
      this.methodsToPrint = new HashSet<>();

      for (Method method : myClass.getClass().getDeclaredMethods()) {
        if (method.isAnnotationPresent(Log.class)) {
          try {
            this.methodsToPrint.add(MyClassInterface.class.getDeclaredMethod(method.getName(), method.getParameterTypes()));
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
          }
        }
      }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      StringBuilder log = new StringBuilder();
      if (methodsToPrint.contains(method)) {
        log.append("executed method: ")
            .append(method.getName())
            .append("; params: ");
        for (Object arg : args) {
          log.append(arg.toString());
        }
        System.out.println(log.toString());
      }
      return method.invoke(myClass, args);
    }

    @Override
    public String toString() {
      return "MyInvocationHandler{" +
          "myClass=" + myClass +
          '}';
    }
  }
}
