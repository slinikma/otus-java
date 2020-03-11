package ru.otus.hw1719.objectvisitor;

public class ObjectTraverseException extends Exception {
  public ObjectTraverseException(String msg) {
    super(msg);
  }

  public ObjectTraverseException(Exception ex) {
    super(ex);
  }
}
