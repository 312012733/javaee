package com.my.xstream;

import java.io.InputStream;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;

@XStreamAlias("project")
// @XStreamConverter(value = ToAttributedValueConverter.class, strings ={
// "innerHtml" })
public class Pom
{
    @XStreamAsAttribute
    @XStreamAlias("xmlns")
    private String xmlns;
    
    @XStreamAsAttribute
    @XStreamAlias("xsi:schemaLocation")
    private String xsiSchemaLocation;
    
    @XStreamAlias("modelVersion")
    private String modelVersion;
    
    @XStreamAlias("groupId")
    private String groupId;
    
    @XStreamAlias("artifactId")
    private String artifactId;
    
    @XStreamAlias("version")
    private String version;
    
    @XStreamAlias("dependencies")
    private Dependencies dependencies;
    
    public static class Dependencies
    {
        @XStreamImplicit(itemFieldName = "dependency")
        private List<Dependency> dependencyList;
        
        public List<Dependency> getDependencyList()
        {
            return dependencyList;
        }
        
        public void setDependencyList(List<Dependency> dependencyList)
        {
            this.dependencyList = dependencyList;
        }
        
        @Override
        public String toString()
        {
            return "Dependencies [dependencyList=" + dependencyList + "]";
        }
        
    }
    
    public static class Dependency
    {
        @XStreamAlias("groupId")
        private String groupId;
        
        @XStreamAlias("artifactId")
        private String artifactId;
        
        @XStreamAlias("version")
        private String version;
        
        public String getGroupId()
        {
            return groupId;
        }
        
        public void setGroupId(String groupId)
        {
            this.groupId = groupId;
        }
        
        public String getArtifactId()
        {
            return artifactId;
        }
        
        public void setArtifactId(String artifactId)
        {
            this.artifactId = artifactId;
        }
        
        public String getVersion()
        {
            return version;
        }
        
        public void setVersion(String version)
        {
            this.version = version;
        }
        
        @Override
        public String toString()
        {
            return "Dependency [groupId=" + groupId + ", artifactId=" + artifactId + ", version=" + version + "]";
        }
        
    }
    
    public String getXmlns()
    {
        return xmlns;
    }
    
    public void setXmlns(String xmlns)
    {
        this.xmlns = xmlns;
    }
    
    public String getXsiSchemaLocation()
    {
        return xsiSchemaLocation;
    }
    
    public void setXsiSchemaLocation(String xsiSchemaLocation)
    {
        this.xsiSchemaLocation = xsiSchemaLocation;
    }
    
    public String getModelVersion()
    {
        return modelVersion;
    }
    
    public void setModelVersion(String modelVersion)
    {
        this.modelVersion = modelVersion;
    }
    
    public String getGroupId()
    {
        return groupId;
    }
    
    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }
    
    public String getArtifactId()
    {
        return artifactId;
    }
    
    public void setArtifactId(String artifactId)
    {
        this.artifactId = artifactId;
    }
    
    public String getVersion()
    {
        return version;
    }
    
    public void setVersion(String version)
    {
        this.version = version;
    }
    
    public Dependencies getDependencies()
    {
        return dependencies;
    }
    
    public void setDependencies(Dependencies dependencies)
    {
        this.dependencies = dependencies;
    }
    
    @Override
    public String toString()
    {
        return "Pom [xmlns=" + xmlns + ", xsiSchemaLocation=" + xsiSchemaLocation + ", modelVersion=" + modelVersion
                + ", groupId=" + groupId + ", artifactId=" + artifactId + ", version=" + version + ", dependencies="
                + dependencies + "]";
    }
    
    public static void main(String[] args)
    {
        InputStream in = TestMain.class.getClassLoader().getResourceAsStream("pom.xml");
        
        Configruation<Pom> config = new Configruation<>(in, new DomDriver());
        
        Pom pom = config.parse(Pom.class);
        
        System.out.println(JSONObject.toJSON(pom));
    }
    
}
