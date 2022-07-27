package com.o2s.conf;

import java.util.Properties;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.o2s.db.repo", entityManagerFactoryRef="entityManagerFactory")
@EnableTransactionManagement
public class JPAConfiguration {

    @Value("${spring.datasource.url}")
    private String dSUrl;
    @Value("${spring.datasource.username}")
    private String dSUserName;
    @Value("${spring.datasource.password}")
    private String dSPassword;
    @Value("${spring.datasource.driver-class-name}")
    private String dSDriverClassName;
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;
    @Value("${spring.jpa.properties.hibernate.show_sql}")
    private String hibernateShowSql;
    @Value("${spring.jpa.properties.hibernate.format_sql}")
    private String hibernateFormatSql;
    @Value("${spring.jpa.properties.hibernate.ddl-auto}")
    private String hibernateDDLAuto;

    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.o2s.db.model" });

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dSDriverClassName);
        dataSource.setUrl(dSUrl);
        dataSource.setUsername(dSUserName);
        dataSource.setPassword(dSPassword);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hibernateDDLAuto);
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("show_sql", hibernateShowSql);
        properties.setProperty("format_sql", hibernateFormatSql);
        return properties;
    }
    
}
