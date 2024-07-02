package org.senla.komar.spring.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Конфигурационный класс для настройки Hibernate в Spring-приложении.
 *
 *  @author Olga Komar
 *  @version 1.0
 */

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "org.senla.komar.spring")
@PropertySource("classpath:application.yaml")
@RequiredArgsConstructor
public class HibernateConfig {

    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;
    @Value("${datasource.driver-class-name}")
    private String driver;
    @Value("${packages-to-scan}")
    private String packagesToScan;
    @Value("${hibernate.dialect}")
    private String dialect;

    /**
     * Создает и возвращает экземпляр EntityManager.
     *
     * @param entityManagerFactory Фабрика EntityManager.
     * @return Экземпляр EntityManager.
     */
    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Создает и возвращает DataSource для подключения к базе данных.
     *
     * @return DataSource.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    /**
     * Создает и возвращает PlatformTransactionManager для управления транзакциями.
     *
     * @return PlatformTransactionManager.
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    /**
     * Создает и возвращает LocalContainerEntityManagerFactoryBean для настройки EntityManagerFactory.
     *
     * @return LocalContainerEntityManagerFactoryBean.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan("org.senla.komar.spring.entity");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setJpaProperties(getHibernateProperties());
        return entityManager;
    }

    /**
     * Возвращает настройки Hibernate.
     *
     * @return Настройки Hibernate в виде объекта Properties.
     */
    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");

        return properties;
    }
}