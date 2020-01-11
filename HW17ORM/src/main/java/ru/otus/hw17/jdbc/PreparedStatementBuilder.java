package ru.otus.hw17.jdbc;

import ru.otus.hw17.objectvisitor.Traverser;
import ru.otus.hw17.objectvisitor.visitors.InsertQueryBuilder;
import ru.otus.hw17.objectvisitor.visitors.SelectByIdQueryBuilder;
import ru.otus.hw17.objectvisitor.visitors.UpdateQueryBuilder;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementBuilder {

  private Connection connection;

  public PreparedStatementBuilder(Connection connection) {
    this.connection = connection;
  }

  public PreparedStatement getInsertPreparedStatement(Object object) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SQLException {
    // TODO: встроить кэширование из ДЗ про cache engine
    var insertQueryBuilderService = new InsertQueryBuilder();
    Traverser.traverse(object, insertQueryBuilderService, null);

    try (PreparedStatement pst = this.connection.prepareStatement(insertQueryBuilderService.getQueryString())) {
      // Цикл для замены '?' на параметры через PreparedStatement
      // Тем самым защищаемся от SQL инъекций
      var params = insertQueryBuilderService.getParams();
      for (int i = 0; i < params.size(); i++) {
        pst.setObject(i, params.get(i));
      }

      return pst;
    }
  }

  public <T> PreparedStatement getSelectPreparedStatement(Class<T> clazz, long id) throws IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, SQLException {
    // TODO: встроить кэширование из ДЗ про cache engine
    var selectByIdQueryBuilderService = new SelectByIdQueryBuilder();
    Traverser.traverse(clazz.getDeclaredConstructor().newInstance(), selectByIdQueryBuilderService, null);

    try (PreparedStatement pst = this.connection.prepareStatement(selectByIdQueryBuilderService.getQueryString())) {
      pst.setLong(0, id);

      return pst;
    }
  }

  public <T> PreparedStatement getUpdatePreparedStatement(Object object) throws IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, SQLException {
    // TODO: встроить кэширование из ДЗ про cache engine
    var updateQueryBuilderService = new UpdateQueryBuilder();
    Traverser.traverse(object, updateQueryBuilderService, null);

    try (PreparedStatement pst = this.connection.prepareStatement(updateQueryBuilderService.getQueryString())) {
      // Заменяем '?' на ID PreparedStatement
      // Тем самым защищаемся от SQL инъекций
      pst.setObject(0, updateQueryBuilderService.getIdFieldValue());

      return pst;
    }
  }
}
