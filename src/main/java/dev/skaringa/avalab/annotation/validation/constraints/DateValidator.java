package dev.skaringa.avalab.annotation.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateValidator implements ConstraintValidator<IsAfter, LocalDateTime> {
    String validDate;

    @Override
    public void initialize(IsAfter constraintAnnotation) {
        validDate = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDateTime date, ConstraintValidatorContext constraintValidatorContext) {
        String[] splitDate = validDate.split("-");
        return date.isAfter(
                LocalDate.of(
                        Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[2]))
                        .atStartOfDay());
    }
}
