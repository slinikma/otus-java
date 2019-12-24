package ru.otus.hw17.jdbc;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.api.model.User;
import ru.otus.hw17.objectvisitor.TraverserImpl;

import java.io.ObjectStreamException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DbExecutor<T> {
  private static Logger logger = LoggerFactory.getLogger(DbExecutor.class);

  @Getter @Setter
  private Connection connection;

  public void create(T objectData) {
    try {
      String query = TraverserImpl.getInsertQuery(objectData);
      insertRecord(query);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

  public void update(T objectData) {

  }

  // TODO: почему в задании <T> generic метод + generic класс?
  // Использую generic тип класса
  public Optional<T> load(long id, Class<T> clazz) {
    try {
      String query = TraverserImpl.getSelectQuery(clazz);
//      T obj = clazz.getConstructor(T);
      // В параметры мы передаём классы, конструкторы которых тоже должны быть вызваны?
      return selectRecord(query, id, resultSet -> {
        try {
          if (resultSet.next()) {
            Constructor<T> constructor = clazz.getConstructor();
            T object = TraverserImpl.loadObjectFromResultSet(resultSet, constructor.newInstance());
            logger.info("Loaded object: " + object.toString());
            return object;
          }
        } catch (SQLException | NoSuchMethodException e) {
          logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        } catch (InstantiationException e) {
          e.printStackTrace();
        }
        return null;
      });
      // TODO: что делать с таким количеством кетчей? это жесть
      // пробрасывать ломбоком???
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  // TODO: optional
//  public void createOrUpdate(T objectData) {
//
//  }

  private long insertRecord(String sql) throws SQLException {
    Savepoint savePoint = this.connection.setSavepoint("savePointName");
//    sql = "INSERT INTO User(name, alias) VALUES ('DBSERVICEUSERRR', 'DBSERVICEUSERRR')";
    System.out.println(sql);
    try (Statement statement = this.connection.createStatement()) {
//      pst.setLong(1, 2);
//      pst.setString(1, "DBSERVICEUSERRR");
//      pst.setString(2, "DBSERVICEUSERRR2");
//      for (int idx = 0; idx < params.size(); idx++) {
//        pst.setString(idx + 1, params.get(idx));
//      }
      statement.executeUpdate(sql);
//      try (ResultSet rs = statement.getGeneratedKeys()) {
//        rs.next();
//        return rs.getInt(1);
//      }
      return 1;
    } catch (SQLException ex) {
      this.connection.rollback(savePoint);
      logger.error(ex.getMessage(), ex);
      throw ex;
    }
  }

  private Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
    try (PreparedStatement pst = this.connection.prepareStatement(sql)) {
      pst.setLong(1, id);
      try (ResultSet rs = pst.executeQuery()) {
        return Optional.ofNullable(rsHandler.apply(rs));
      }
    }
  }
}
