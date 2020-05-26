package com.heroslender.ethos.annotations.validation.validators;

import com.heroslender.ethos.annotations.validation.ConstraintValidator;
import com.heroslender.ethos.annotations.validation.constraints.Pattern;

public class PatternValidator implements ConstraintValidator<Pattern, String> {
    private java.util.regex.Pattern pattern;

    @Override
    public void init(Pattern annotation) {
        this.pattern = java.util.regex.Pattern.compile(annotation.value());
    }

    @Override
    public boolean isValid(String value) {
        return pattern.matcher(value).matches();
    }
}
