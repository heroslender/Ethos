package com.heroslender.ethos.annotations.validation.validators;

import com.heroslender.ethos.annotations.validation.ConstraintValidator;
import com.heroslender.ethos.annotations.validation.constraints.Min;

public class MinValidator implements ConstraintValidator<Min, Long> {
    private Min annotation;

    @Override
    public void init(Min annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(Long value) {
        return value > annotation.value();
    }
}
