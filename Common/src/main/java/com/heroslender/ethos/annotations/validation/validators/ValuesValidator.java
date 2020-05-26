package com.heroslender.ethos.annotations.validation.validators;

import com.heroslender.ethos.annotations.validation.ConstraintValidator;
import com.heroslender.ethos.annotations.validation.constraints.Values;

public class ValuesValidator implements ConstraintValidator<Values, String> {
    private Values annotation;

    @Override
    public void init(Values annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value) {
        boolean matchCase = annotation.matchCase();

        for (String val : annotation.value()) {
            if (matchCase ? value.equals(val) : value.equalsIgnoreCase(val)) {
                return true;
            }
        }

        return false;
    }
}
