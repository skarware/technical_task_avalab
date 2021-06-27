package dev.skaringa.avalab.annotation.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Documented
public @interface IsAfter {
    String message() default "Provided date time is too old";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value();
}