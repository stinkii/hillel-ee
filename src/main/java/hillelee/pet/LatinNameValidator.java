package hillelee.pet;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatinNameValidator implements ConstraintValidator<LatinName, String> {
    @Override
    public void initialize(LatinName constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) return true;
        return value.contains("um") || value.contains("us");
    }
}
