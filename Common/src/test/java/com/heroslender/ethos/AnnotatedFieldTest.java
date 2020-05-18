package com.heroslender.ethos;

import com.heroslender.ethos.annotations.NotSerialized;
import com.heroslender.ethos.annotations.Optional;
import com.heroslender.ethos.annotations.SerializedName;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnnotatedFieldTest {

    @Test
    public void fieldsShouldHaveProperSerializedName() throws NoSuchFieldException {
        AnnotatedField field = AnnotatedField.load(TestConfig.class.getDeclaredField("name"));

        assertEquals("display-name", field.getSerializedName());

        field = AnnotatedField.load(TestConfig.class.getDeclaredField("lastName"));
        assertEquals("lastName", field.getSerializedName());

        field = AnnotatedField.load(TestConfig.class.getDeclaredField("age"));
        assertEquals("age", field.getSerializedName());
    }


    @Test
    public void lastNameShouldBeOptional() throws NoSuchFieldException {
        AnnotatedField field = AnnotatedField.load(TestConfig.class.getDeclaredField("name"));
        assertFalse(field.isOptional());

        field = AnnotatedField.load(TestConfig.class.getDeclaredField("lastName"));
        assertTrue(field.isOptional());

        field = AnnotatedField.load(TestConfig.class.getDeclaredField("age"));
        assertFalse(field.isOptional());
    }

    @Test
    public void fullNameShouldNotBeSerialized() throws NoSuchFieldException {
        AnnotatedField field = AnnotatedField.load(TestConfig.class.getDeclaredField("name"));
        assertFalse(field.isNotSerialized());

        field = AnnotatedField.load(TestConfig.class.getDeclaredField("lastName"));
        assertFalse(field.isNotSerialized());

        field = AnnotatedField.load(TestConfig.class.getDeclaredField("fullName"));
        assertTrue(field.isNotSerialized());
    }

    private static class TestConfig {
        @SerializedName("display-name")
        String name;

        @Optional
        String lastName;

        @NotSerialized
        String fullName;

        int age;
    }
}
