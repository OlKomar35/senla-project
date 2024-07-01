package org.senla.komar.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.senla.komar.spring.utils.Convertor;
import org.springframework.context.annotation.*;

/**
 * Конфигурационный класс для Spring-приложения.
 */

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "org.senla.komar.spring")
@PropertySource("classpath:application.properties")
public class ApplicationConfig  {

    /**
     * Создает и возвращает экземпляр ObjectMapper.
     *
     * @return Экземпляр ObjectMapper.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * Создает и возвращает экземпляр Convertor, используя ObjectMapper.
     *
     * @return Экземпляр Convertor.
     */
    @Bean
    public Convertor convertor() {
        return new Convertor(objectMapper());
    }
}
