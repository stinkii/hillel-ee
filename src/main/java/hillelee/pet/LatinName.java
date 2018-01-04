package hillelee.pet;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = LatinNameValidator.class)
public @interface LatinName {
    String message() default "Invalid latin name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
