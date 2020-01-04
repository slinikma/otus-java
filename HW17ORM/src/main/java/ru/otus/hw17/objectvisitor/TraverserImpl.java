package ru.otus.hw17.objectvisitor;

import ru.otus.hw17.api.model.myorm.Account;
import ru.otus.hw17.api.model.myorm.User;
import ru.otus.hw17.objectvisitor.visitable.types.ArrayField;
import ru.otus.hw17.objectvisitor.visitable.types.ObjectField;
import ru.otus.hw17.objectvisitor.visitable.types.PrimitiveField;
import ru.otus.hw17.objectvisitor.visitable.types.StringField;
import ru.otus.hw17.objectvisitor.visitors.InsertQueryBuilder;
import ru.otus.hw17.objectvisitor.visitors.SelectByIdQueryBuilder;
import ru.otus.hw17.objectvisitor.visitors.ResultSetObjectLoader;
import ru.otus.hw17.objectvisitor.visitors.UpdateQueryBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;

public class TraverserImpl {

  public static void main(String[] args) {
    try {
      // TODO: tests???
      System.out.println(TraverserImpl.getInsertQuery(new User(1, "Nikita")));
      System.out.println(TraverserImpl.getInsertQuery(new Account(1, "myAdminAcc", 123123)));

      System.out.println(TraverserImpl.getSelectQuery(User.class));
      System.out.println(TraverserImpl.getSelectQuery(Account.class));

      System.out.println(TraverserImpl.getUpdateQuery(new User(1, "Nikita")));
      System.out.println(TraverserImpl.getUpdateQuery(new Account(1, "myAdminAcc", 123123)));
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  public static <T> T loadObjectFromResultSet(ResultSet resultSet, T object) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
    var resultSetObjectLoader = new ResultSetObjectLoader(resultSet);
    traverse(object, resultSetObjectLoader, null);

    return object;
  }

  public static String getInsertQuery(Object object) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
    var insertQueryBuilderService = new InsertQueryBuilder();
    traverse(object, insertQueryBuilderService, null);

    return insertQueryBuilderService.getQuery();
  }

  public static <T> String getSelectQuery(Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException {
    var selectByIdQueryBuilderService = new SelectByIdQueryBuilder();
    traverse(clazz.getDeclaredConstructor().newInstance(), selectByIdQueryBuilderService, null);

    return selectByIdQueryBuilderService.getQuery();
  }

  public static <T> String getUpdateQuery(Object object) throws IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException {
    var updateQueryBuilderService = new UpdateQueryBuilder();
    traverse(object, updateQueryBuilderService, null);

    return updateQueryBuilderService.getQuery();
  }

  private static void traverse(Object object, Visitor service, Field rootField) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
    // Обрабатываем сам объект и его поле, если указали
    if (rootField != null) {
      if (object.getClass().isArray()) {
        new ArrayField(rootField, object).accept(service);
      } else {
        new ObjectField(rootField, object).accept(service);
      }
    }

    // Обрабатываем поля объекта
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }
      if (field.getType().isPrimitive()) {
        new PrimitiveField(field, object).accept(service);
      } else if (field.getType().isArray()) {
        new ArrayField(field, object).accept(service);
      } else if (field.getType().isAssignableFrom(String.class)) {
        new StringField(field, object).accept(service);
      } else {
        traverse(field.get(object), service, field);
      }
    }
  }
}
