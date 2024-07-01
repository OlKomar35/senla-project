package org.senla.komar.spring.annotation;

import org.senla.komar.spring.util.MockTestData;
import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = MockTestData.ADMIN_USERNAME,
        password = MockTestData.ADMIN_PASSWORD, roles = MockTestData.ADMIN_ROLE)
public @interface WithMockAdmin {
}
