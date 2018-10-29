package com.my.framework.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("configuration")
public class Configuration
{
    @XStreamAlias("properties")
    private Properties properties;
    
    @XStreamAlias("dataSource")
    private DataSource dataSource;
    
    @XStreamAlias("mappers")
    private Mappers mappers;
    
    private List<SqlMapper> sqlMapperList;
    
    /**
     * dataSource 下面 property 里面的 变量 的 key 。 比如：${jdbc.driverClassName} ---》
     * jdbc.driverClassName
     */
    private String buildPropertyKey(String var)
    {
        return var.substring(2, var.length() - 1);
    }
    
    public SessionFactory buildSessionFactory() throws IOException
    {
        // 加载 数据库 实体的 映射配置文件
        sqlMapperList = new ArrayList<>();
        
        List<Mapper> mappers = this.getMappers().getMappers();
        
        for (Mapper mapper : mappers)
        {
            XmlUtils xmlUtil = new XmlUtils(mapper.getResource());
            SqlMapper sqlMapper = xmlUtil.parse(SqlMapper.class);
            
            sqlMapperList.add(sqlMapper);
        }
        
        // 加载jdbc的 属性文件
        java.util.Properties jdbcProp = new java.util.Properties();
        jdbcProp.load(Configuration.class.getClassLoader().getResourceAsStream(properties.getResource()));
        
        // 构建 session工厂
        SessionFactory sessionFactory = new SessionFactory(sqlMapperList);
        
        Map<String, String> propertiesMap = dataSource.propertiesToMap();
        
        String driver = jdbcProp.getProperty(buildPropertyKey(propertiesMap.get(SessionFactory.JDBC_DRIVER)));
        String url = jdbcProp.getProperty(buildPropertyKey(propertiesMap.get(SessionFactory.JDBC_URL)));
        String password = jdbcProp.getProperty(buildPropertyKey(propertiesMap.get(SessionFactory.JDBC_PASSWORD)));
        String username = jdbcProp.getProperty(buildPropertyKey(propertiesMap.get(SessionFactory.JDBC_USER_NAME)));
        
        sessionFactory.setDiver(driver);
        sessionFactory.setUrl(url);
        sessionFactory.setPassword(password);
        sessionFactory.setUsername(username);
        
        return sessionFactory;
    }
    
    @XStreamAlias("dataSource")
    static class DataSource
    {
        @XStreamAlias("type")
        @XStreamAsAttribute
        private String type;
        
        @XStreamImplicit(itemFieldName = "property")
        private List<Property> properties;
        
        Map<String, String> propertiesToMap()
        {
            Map<String, String> map = new HashMap<>();
            
            for (Property property : properties)
            {
                map.put(property.getName(), property.getValue());
            }
            
            return map;
        }
        
        String getType()
        {
            return type;
        }
        
        void setType(String type)
        {
            this.type = type;
        }
        
        List<Property> getProperties()
        {
            return properties;
        }
        
        void setProperties(List<Property> properties)
        {
            this.properties = properties;
        }
        
    }
    
    @XStreamAlias("mapper")
    static class Mapper
    {
        @XStreamAlias("resource")
        @XStreamAsAttribute
        private String resource;
        
        String getResource()
        {
            return resource;
        }
        
        void setResource(String resource)
        {
            this.resource = resource;
        }
        
    }
    
    @XStreamAlias("mappers")
    static class Mappers
    {
        @XStreamImplicit(itemFieldName = "mapper")
        List<Mapper> mappers;
        
        List<Mapper> getMappers()
        {
            return mappers;
        }
        
        void setMappers(List<Mapper> mappers)
        {
            this.mappers = mappers;
        }
        
    }
    
    @XStreamAlias("properties")
    static class Properties
    {
        @XStreamAlias("resource")
        @XStreamAsAttribute
        private String resource;
        
        String getResource()
        {
            return resource;
        }
        
        void setResource(String resource)
        {
            this.resource = resource;
        }
        
    }
    
    @XStreamAlias("property")
    static class Property
    {
        @XStreamAlias("name")
        @XStreamAsAttribute
        private String name;
        
        @XStreamAlias("value")
        @XStreamAsAttribute
        private String value;
        
        String getName()
        {
            return name;
        }
        
        void setName(String name)
        {
            this.name = name;
        }
        
        String getValue()
        {
            return value;
        }
        
        void setValue(String value)
        {
            this.value = value;
        }
        
    }
    
    Properties getProperties()
    {
        return properties;
    }
    
    void setProperties(Properties properties)
    {
        this.properties = properties;
    }
    
    DataSource getDataSource()
    {
        return dataSource;
    }
    
    void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }
    
    Mappers getMappers()
    {
        return mappers;
    }
    
    void setMappers(Mappers mappers)
    {
        this.mappers = mappers;
    }
    
}
