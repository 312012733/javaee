package com.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource(value = "classpath:jdbc.properties")
public class DataSourceConfig
{
    @Value("${jdbc.driverClassName}")
    private String driverClass;
    
    @Value("${jdbc.url}")
    private String jdbcUrl;
    
    @Value("${jdbc.username}")
    private String user;
    
    @Value("${jdbc.password}")
    private String password;
    
    @Value("${jdbc.c3p0.testConnectionOnCheckout}")
    private Boolean testConnectionOnCheckout;
    
    @Value("${jdbc.c3p0.testConnectionOnCheckin}")
    private boolean testConnectionOnCheckin;
    
    @Value("${jdbc.c3p0.idleConnectionTestPeriod}")
    private Integer idleConnectionTestPeriod;
    
    @Value("${jdbc.c3p0.initialPoolSize}")
    private Integer initialPoolSize;
    
    @Value("${jdbc.c3p0.minPoolSize}")
    private Integer minPoolSize;
    
    @Value("${jdbc.c3p0.maxPoolSize}")
    private Integer maxPoolSize;
    
    @Value("${jdbc.c3p0.maxIdleTime}")
    private Integer maxIdleTime;
    
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource)
    {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        
        return namedParameterJdbcTemplate;
    }
    
    @Bean
    public DataSource dataSource() throws PropertyVetoException
    {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setTestConnectionOnCheckout(testConnectionOnCheckout);
        dataSource.setTestConnectionOnCheckin(testConnectionOnCheckin);
        dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
        dataSource.setInitialPoolSize(initialPoolSize);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setMaxIdleTime(maxIdleTime);
        
        return dataSource;
    }
    
}
