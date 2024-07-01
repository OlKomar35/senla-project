package org.senla.komar.spring.annotation;

import org.senla.komar.spring.util.MockTestData;
import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = MockTestData.USERNAME,
        password = MockTestData.USER_PASSWORD, roles = MockTestData.USER_ROLE)
public @interface WithMockDefaultUser {
}
