package com.heroslender.ethos.annotations.validation;

import java.lang.annotation.Annotation;

public interface ConstraintValidator<A extends Annotation, T> {
    void init(A annotation);

    boolean isValid(T value);
}
