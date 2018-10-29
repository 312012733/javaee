package com.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.dao")
@Import(DataSourceConfig.class)
public class MybatisConfig implements ResourceLoaderAware
{
    private ResourceLoader resourceLoader;
    
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws IOException
    {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        
        sqlSessionFactory.setConfigLocation(resourceLoader.getResource("classpath:/mybatis.xml"));
        
        sqlSessionFactory.setDataSource(dataSource);
        
        // sqlSessionFactory.setPlugins(new Interceptor[]
        // { new PageInterceptor() });
        
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] mapperLocations = resourcePatternResolver.getResources("classpath:/mapper/*_mapper.xml");
        
        sqlSessionFactory.setMapperLocations(mapperLocations);
        
        return sqlSessionFactory;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) throws IOException
    {
        PlatformTransactionManager tm = new DataSourceTransactionManager(dataSource);
        
        return tm;
    }
    
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;
        
    }
    
}
