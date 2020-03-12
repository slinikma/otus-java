package ru.otus.hw17.jdbc;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.objectvisitor.ObjectTraverseException;
import ru.otus.hw17.objectvisitor.Traverser;
import ru.otus.hw17.objectvisitor.visitors.InsertQueryBuilder;
import ru.otus.hw17.objectvisitor.visitors.SelectByIdQueryBuilder;
import ru.otus.hw17.objectvisitor.visitors.UpdateQueryBuilder;

import java.sql.*;
import java.util.Optional;
import java.util.function.Function;

public class DbExecutor<T> {
  private static Logger logger = LoggerFactory.getLogger(DbExecutor.class);

  @Getter
  @Setter
  private Connection connection;

  public long create(T objectData) throws ObjectTraverseException, SQLException {

    var insertQueryBuilderService = new InsertQueryBuilder();
    Traverser.traverse(objectData, insertQueryBuilderService, null);

    Savepoint savePoint = connection.setSavepoint("savePointName");
    try (var prepareStatement = this.connection.prepareStatement(insertQueryBuilderService.getQueryString(), Statement.RETURN_GENERATED_KEYS)) {
      var params = insertQueryBuilderService.getParams();
      for (int i = 0; i < params.size(); i++) {
        prepareStatement.setObject(i + 1, params.get(i));
      }

      prepareStatement.executeUpdate();

      try (var resultSet = prepareStatement.getGeneratedKeys()) {
        resultSet.next();
        return resultSet.getLong(insertQueryBuilderService.getIdFieldName());
      }
    } catch (SQLException ex) {
      connection.rollback(savePoint);
      logger.error(ex.getMessage(), ex);
      throw ex;
    }
  }

  public long update(T objectData) throws SQLException, ObjectTraverseException {

    var updateQueryBuilderService = new UpdateQueryBuilder();
    Traverser.traverse(objectData, updateQueryBuilderService, null);

    Savepoint savePoint = connection.setSavepoint("savePointName");
    try (var prepareStatement = this.connection.prepareStatement(updateQueryBuilderService.getQueryString())) {
      var params = updateQueryBuilderService.getParams();
      for (int i = 0; i < params.size(); i++) {
        prepareStatement.setObject(i + 1, params.get(i));
      }
      prepareStatement.setObject(params.size() + 1, updateQueryBuilderService.getIdFieldValue());

      prepareStatement.executeUpdate();

      return (Long) updateQueryBuilderService.getIdFieldValue();
    } catch (SQLException ex) {
      connection.rollback(savePoint);
      logger.error(ex.getMessage(), ex);
      throw ex;
    }
  }

  public Optional<T> load(long id, Class<? extends T> clazz, Function<ResultSet, T> rsHandler) throws SQLException, DbExecutorException {

    var selectByIdQueryBuilderService = new SelectByIdQueryBuilder();
    try {
      Traverser.traverse(clazz.getDeclaredConstructor().newInstance(), selectByIdQueryBuilderService, null);
    } catch (Exception e) {
      throw new DbExecutorException(e);
    }

    try (var prepareStatement = this.connection.prepareStatement(selectByIdQueryBuilderService.getQueryString())) {
      prepareStatement.setLong(1, id);
      try (var resultSet = prepareStatement.executeQuery()) {
        return Optional.ofNullable(rsHandler.apply(resultSet));
      }
    }
  }

  // TODO: optional
//  public void createOrUpdate(T objectData) {
//
//  }
}
