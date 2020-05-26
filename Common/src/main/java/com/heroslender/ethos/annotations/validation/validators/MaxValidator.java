package com.heroslender.ethos.annotations.validation.validators;

import com.heroslender.ethos.annotations.validation.ConstraintValidator;
import com.heroslender.ethos.annotations.validation.constraints.Max;

public class MaxValidator implements ConstraintValidator<Max, Long> {
    private Max annotation;

    @Override
    public void init(Max annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(Long value) {
        return value < annotation.value();
    }
}
