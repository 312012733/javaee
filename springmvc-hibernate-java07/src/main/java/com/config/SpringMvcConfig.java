package com.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com")
@EnableWebMvc
public class SpringMvcConfig
        // extends WebMvcConfigurerAdapter
        implements WebMvcConfigurer
{
    @Autowired
    private OpenSessionInViewInterceptor interceptor;
    
    @Bean
    public OpenSessionInViewInterceptor openSessionInViewInterceptor(SessionFactory sessionFactory)
    {
        
        OpenSessionInViewInterceptor openSessionInterceptor = new OpenSessionInViewInterceptor();
        
        openSessionInterceptor.setSessionFactory(sessionFactory);
        
        return openSessionInterceptor;
    }
    
    @Bean
    public CommonsMultipartResolver multipartResolver()
    {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        
        return multipartResolver;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addWebRequestInterceptor(interceptor);
    }
    
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer)
    {
        configurer.setUseSuffixPatternMatch(false);
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("*").addResourceLocations("classpath:/static/", "/");
        // registry.addResourceHandler("login*","error*").addResourceLocations("classpath:/static/","/");
    }
    
}
