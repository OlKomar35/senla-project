package org.senla.komar.spring.config;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Конфигурационный класс для настройки Liquibase в Spring-приложении.
 *
 * @author Olga Komar
 * @version 1.0
 */

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "org.senla.komar.spring")
public class LiquibaseConfig {

    /**
     * Путь к файлу с изменениями Liquibase, загруженный из свойства `liquibase.changelog-file` в конфигурации.
     */
    @Value("${liquibase.changelog-file}")
    private String path;

    /**
     * Источник данных (DataSource), используемый Liquibase для выполнения изменений базы данных.
     */
    public final DataSource dataSource;

    /**
     * Создает новый экземпляр LiquibaseConfig с указанным источником данных.
     *
     * @param dataSource Источник данных, используемый Liquibase.
     */
    public LiquibaseConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Создает и возвращает SpringLiquibase для выполнения миграций базы данных.
     *
     * @return SpringLiquibase.
     */
    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(path);
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
