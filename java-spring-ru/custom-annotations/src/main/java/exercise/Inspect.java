package exercise;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

// BEGIN
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Inspect {
    String level() default "debug";
}
// END
