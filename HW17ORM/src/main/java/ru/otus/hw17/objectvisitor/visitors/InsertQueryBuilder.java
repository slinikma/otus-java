package ru.otus.hw17.objectvisitor.visitors;

import ru.otus.hw17.objectvisitor.Visitor;
import ru.otus.hw17.objectvisitor.visitable.types.ArrayField;
import ru.otus.hw17.objectvisitor.visitable.types.ObjectField;
import ru.otus.hw17.objectvisitor.visitable.types.PrimitiveField;
import ru.otus.hw17.objectvisitor.visitable.types.StringField;

import java.util.ArrayList;
import java.util.List;

public class InsertQueryBuilder implements Visitor {
  private StringBuilder query;
  private List<String> params;
  private boolean isTableAppended = false;
  private boolean isCommaNeeded = false;

  public InsertQueryBuilder() {
    query = new StringBuilder();
    params = new ArrayList<>();
    query.append("insert into ");
  }

  @Override
  public void visit(ArrayField field) {
    if (!this.isTableAppended) {
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append("(");
      this.isTableAppended = true;
    }

    if (isCommaNeeded) {
      query.append(",");
    }

    // TODO: проверить
    query.append(field.getName());
    params.add(field.getArray().toString());
  }

  @Override
  public void visit(PrimitiveField field) {
    if (!this.isTableAppended) {
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append("(");
      this.isTableAppended = true;
    }

    if (isCommaNeeded) {
      query.append(",");
    } else {
      isCommaNeeded = true;
    }

    query.append(field.getName());
    params.add(field.getBoxedPrimitive().toString());
  }

  @Override
  public void visit(ObjectField field) {
    // Сложное не примитивное поле, которое должно быть ссылкой на другую таблицу.
    // Выходит за рамки ДЗ
    throw new UnsupportedOperationException("Object fields unsupported!");
  }

  @Override
  public void visit(StringField field) {
    if (!this.isTableAppended) {
      query.append(field.getFieldOfObject().getClass().getSimpleName())
          .append("(");
      this.isTableAppended = true;
    }

    if (isCommaNeeded) {
      query.append(",");
    }

    query.append(field.getName());
    params.add("'" + field.getValue() + "'");
  }

  // Смотрел sqlQueryBuilder библиотеку, но решил её не использовать
  // Возвращать PreparedStatement тоже такая себе идея, т.к. нужно передавать connection
  // Так что через StringBuilder
  public String getQuery() {
    boolean isFirstParam = true;

    query.append(") values (");

    for (var param: params) {
      if (!isFirstParam) {
        query.append(",");
      } else {
        isFirstParam = false;
      }

      query.append(param);
    }
    query.append(")");

    return query.toString();
  }
}
