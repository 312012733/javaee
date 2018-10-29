package com.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.context.annotation.Configuration
// @Import(
// { HibernateUtils.class, DataSourceConfig.class })
@EnableTransactionManagement
public class HibernateConfig
{
    @Value("classpath:hibernate.properties")
    private Resource resource;
    
    // private Properties hibernateProperties;
    
    
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) throws HibernateException, IOException
    {
        Properties hibernateProperties = new Properties();
        hibernateProperties.load(resource.getInputStream());
        
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.bean");
        sessionFactory.setHibernateProperties(hibernateProperties);
        
        return sessionFactory;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory, DataSource dataSource)
            throws HibernateException, IOException
    {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        transactionManager.setDataSource(dataSource);
        
        return transactionManager;
    }
    
    // @Bean
    // public SessionFactory sessionFactory() throws HibernateException,
    // IOException
    // {
    // Resource resource =
    // resourceLoader.getResource("classpath:hibernate.cfg.xml");
    //
    // Configuration config = new Configuration().configure(resource.getFile());
    //
    // SessionFactory sessionFactory = config.buildSessionFactory();
    //
    // return sessionFactory;
    // }
    
}
