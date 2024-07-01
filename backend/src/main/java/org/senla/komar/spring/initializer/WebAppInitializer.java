package org.senla.komar.spring.initializer;

import org.senla.komar.spring.config.ApplicationConfig;
import org.senla.komar.spring.config.HibernateConfig;
import org.senla.komar.spring.config.LiquibaseConfig;
import org.senla.komar.spring.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ApplicationConfig.class, HibernateConfig.class, LiquibaseConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
