package ru.otus.hw06.annotations;

import java.lang.annotation.*;

@Documented
@MyCustomAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface AfterEach {
}
