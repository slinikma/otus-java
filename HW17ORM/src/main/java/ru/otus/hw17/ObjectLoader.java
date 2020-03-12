package ru.otus.hw17;

import ru.otus.hw17.objectvisitor.ObjectTraverseException;
import ru.otus.hw17.objectvisitor.Traverser;
import ru.otus.hw17.objectvisitor.visitors.ResultSetObjectLoader;

import java.sql.ResultSet;

public class ObjectLoader {
  public static <T> T loadFromResultSet(ResultSet resultSet, T object) throws ObjectTraverseException {
    var resultSetObjectLoader = new ResultSetObjectLoader(resultSet);
    Traverser.traverse(object, resultSetObjectLoader, null);

    return object;
  }
}
