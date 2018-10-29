package utils;

import java.util.HashMap;
import java.util.Map;

//@Component
public class ThreadLocalUtil
{
    private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();
    
    public static void put(String key, Object value)
    {
        Map<String, Object> map = getMap();
        
        map.put(key, value);
    }
    
    public static Object get(String key)
    {
        Map<String, Object> map = getMap();
        
        return map.get(key);
    }
    
    public static Map<String, Object> getMap()
    {
        Map<String, Object> map = threadLocal.get();
        
        if (null == map)
        {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        
        return map;
    }
    
}
