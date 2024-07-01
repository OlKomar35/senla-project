package org.senla.komar.spring.config;

import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Конфигурационный класс, отвечающий за настройку веб-компонентов приложения.
 */

@EnableWebMvc
@ComponentScan("org.senla.komar.spring")
@Configuration
public class WebConfig {

    /**
     * Создает и настраивает объект валидатора, использующий Hibernate Validator.
     *
     * @return Настроенный экземпляр LocalValidatorFactoryBean.
     */
    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setProviderClass(HibernateValidator.class);
        return validator;
    }

    /**
     * Создает и настраивает объект MethodValidationPostProcessor,
     * который применяет валидацию аргументов методов в контроллерах.
     *
     * @return Настроенный экземпляр MethodValidationPostProcessor.
     */
    @Bean
    public  MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(validator());
        return postProcessor;
    }
}

