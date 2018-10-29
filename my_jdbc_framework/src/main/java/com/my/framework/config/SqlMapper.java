package com.my.framework.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("mapper")
public class SqlMapper
{
    @XStreamAlias("namespace")
    @XStreamAsAttribute
    private String namespace;
    
    @XStreamImplicit(itemFieldName = "select")
    private List<Select> selectList;
    
    @XStreamImplicit(itemFieldName = "insert")
    private List<Insert> insertList;
    
    @XStreamImplicit(itemFieldName = "delete")
    private List<Delete> deleteList;
    
    @XStreamImplicit(itemFieldName = "update")
    private List<Update> updateList;
    
    @XStreamImplicit(itemFieldName = "resultMap")
    private List<ResultMap> resultMapList;
    
    @XStreamAlias("resultMap")
    static class ResultMap
    {
        @XStreamAsAttribute
        @XStreamAlias("id")
        private String id;
        
        @XStreamAlias("type")
        @XStreamAsAttribute
        private String type;
        
        // @XStreamAlias("id")
        private Id pkID;
        
        @XStreamImplicit(itemFieldName = "result")
        private List<Result> resultList;
        
        @XStreamAlias("result")
        static class Result
        {
            @XStreamAlias("column")
            @XStreamAsAttribute
            String column;
            
            @XStreamAlias("property")
            @XStreamAsAttribute
            String property;
            
            @XStreamAlias("javaType")
            @XStreamAsAttribute
            String javaType;
            
            public String getColumn()
            {
                return column;
            }
            
            public void setColumn(String column)
            {
                this.column = column;
            }
            
            public String getProperty()
            {
                return property;
            }
            
            public void setProperty(String property)
            {
                this.property = property;
            }
            
            public String getJavaType()
            {
                return javaType;
            }
            
            public void setJavaType(String javaType)
            {
                this.javaType = javaType;
            }
        }
        
        @XStreamAlias("id")
        static class Id
        {
            @XStreamAlias("column")
            @XStreamAsAttribute
            String column;
            
            @XStreamAlias("property")
            @XStreamAsAttribute
            String property;
            
            public String getColumn()
            {
                return column;
            }
            
            public void setColumn(String column)
            {
                this.column = column;
            }
            
            public String getProperty()
            {
                return property;
            }
            
            public void setProperty(String property)
            {
                this.property = property;
            }
            
        }
        
        public String getId()
        {
            return id;
        }
        
        public void setId(String id)
        {
            this.id = id;
        }
        
        public String getType()
        {
            return type;
        }
        
        public void setType(String type)
        {
            this.type = type;
        }
        
        public Id getPkID()
        {
            return pkID;
        }
        
        public void setPkID(Id pkID)
        {
            this.pkID = pkID;
        }
        
        public List<Result> getResultList()
        {
            return resultList;
        }
        
        public void setResultList(List<Result> resultList)
        {
            this.resultList = resultList;
        }
        
    }
    
    @XStreamAlias("select")
    @XStreamConverter(value = ToAttributedValueConverter.class, strings =
    { "sql" })
    static class Select
    {
        @XStreamAlias("id")
        @XStreamAsAttribute
        private String id;
        
        private String sql;
        
        @XStreamAlias("resultType")
        @XStreamAsAttribute
        private String resultType;
        
        @XStreamAlias("resultMap")
        @XStreamAsAttribute
        private String resultMap;
        
        public String getId()
        {
            return id;
        }
        
        public void setId(String id)
        {
            this.id = id;
        }
        
        public String getSql()
        {
            return sql;
        }
        
        public void setSql(String sql)
        {
            this.sql = sql;
        }
        
        public String getResultType()
        {
            return resultType;
        }
        
        public void setResultType(String resultType)
        {
            this.resultType = resultType;
        }
        
        public String getResultMap()
        {
            return resultMap;
        }
        
        public void setResultMap(String resultMap)
        {
            this.resultMap = resultMap;
        }
        
    }
    
    @XStreamAlias("insert")
    @XStreamConverter(value = ToAttributedValueConverter.class, strings =
    { "sql" })
    static class Insert
    {
        @XStreamAlias("id")
        @XStreamAsAttribute
        private String id;
        
        private String sql;
        
        public String getId()
        {
            return id;
        }
        
        public void setId(String id)
        {
            this.id = id;
        }
        
        public String getSql()
        {
            return sql;
        }
        
        public void setSql(String sql)
        {
            this.sql = sql;
        }
    }
    
    @XStreamAlias("delete")
    @XStreamConverter(value = ToAttributedValueConverter.class, strings =
    { "sql" })
    static class Delete
    {
        @XStreamAlias("id")
        @XStreamAsAttribute
        private String id;
        
        private String sql;
        
        public String getId()
        {
            return id;
        }
        
        public void setId(String id)
        {
            this.id = id;
        }
        
        public String getSql()
        {
            return sql;
        }
        
        public void setSql(String sql)
        {
            this.sql = sql;
        }
        
    }
    
    @XStreamAlias("update")
    @XStreamConverter(value = ToAttributedValueConverter.class, strings =
    { "sql" })
    static class Update
    {
        @XStreamAlias("id")
        @XStreamAsAttribute
        private String id;
        
        private String sql;
        
        public String getId()
        {
            return id;
        }
        
        public void setId(String id)
        {
            this.id = id;
        }
        
        public String getSql()
        {
            return sql;
        }
        
        public void setSql(String sql)
        {
            this.sql = sql;
        }
    }
    
    public String getNamespace()
    {
        return namespace;
    }
    
    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }
    
    public List<Select> getSelectList()
    {
        return selectList;
    }
    
    public void setSelectList(List<Select> selectList)
    {
        this.selectList = selectList;
    }
    
    public List<Insert> getInsertList()
    {
        return insertList;
    }
    
    public void setInsertList(List<Insert> insertList)
    {
        this.insertList = insertList;
    }
    
    public List<Delete> getDeleteList()
    {
        return deleteList;
    }
    
    public void setDeleteList(List<Delete> deleteList)
    {
        this.deleteList = deleteList;
    }
    
    public List<Update> getUpdateList()
    {
        return updateList;
    }
    
    public void setUpdateList(List<Update> updateList)
    {
        this.updateList = updateList;
    }
    
    public List<ResultMap> getResultMapList()
    {
        return resultMapList;
    }
    
    public void setResultMapList(List<ResultMap> resultMapList)
    {
        this.resultMapList = resultMapList;
    }
    
}
