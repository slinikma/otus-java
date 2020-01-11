package ru.otus.hw17.objectvisitor;

public class ObjectTraverseException extends Exception {
  public ObjectTraverseException(String msg) {
    super(msg);
  }

  public ObjectTraverseException(Exception ex) {
    super(ex);
  }
}
