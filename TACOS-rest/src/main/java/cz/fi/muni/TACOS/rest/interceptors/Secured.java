package cz.fi.muni.TACOS.rest.interceptors;

import cz.fi.muni.TACOS.enums.UserRole;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {
    @Nonbinding UserRole[] roles() default {UserRole.SUPERADMIN};
}
