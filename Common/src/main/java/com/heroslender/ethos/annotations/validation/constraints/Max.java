package com.heroslender.ethos.annotations.validation.constraints;

import com.heroslender.ethos.annotations.validation.Constraint;
import com.heroslender.ethos.annotations.validation.validators.MaxValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MaxValidator.class)
public @interface Max {
    long value();
}
