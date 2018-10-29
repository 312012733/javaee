package com.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class SpirngMvcInitializer implements WebApplicationInitializer
{
    
    @Override
    public void onStartup(ServletContext container) throws ServletException
    {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringMvcConfig.class);
        
        // XmlWebApplicationContext
        
        Dynamic dispatcherServlet = container.addServlet("dispatcherServlet", new DispatcherServlet(context));
        dispatcherServlet.addMapping("/");
        dispatcherServlet.setLoadOnStartup(1);
        
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("utf-8");
        
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST);
        boolean isMatchAfter = false;
        String urlPatterns = "/*";
        container.addFilter("characterEncodingFilter", characterEncodingFilter)
                .addMappingForUrlPatterns(dispatcherTypes, isMatchAfter, urlPatterns);
        ;
    }
    
}
