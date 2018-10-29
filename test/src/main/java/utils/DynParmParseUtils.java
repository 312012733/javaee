package utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynParmParseUtils
{
    public String build(String source, Object[] args)
    {
        if (null == args || args.length <= 0)
        {
            return source;
        }
        
        // 获取动态参数
        List<String> dynParamList = getDynParam(source);
        
        if (dynParamList.isEmpty())
        {
            return source;
        }
        
        // 把动态参数构造成map，map的key：动态参数 ， map的值：动态参数的值
        Map<String, Object> dynParamMap = buildDynParamMap(dynParamList, args);
        
        // 构造最终的结果，把动态参数，替换成对应的值
        String newLoagDesc = buildResult(source, dynParamMap);
        
        return newLoagDesc;
    }
    
    private Map<String, Object> buildDynParamMap(List<String> dynParamList, Object[] args)
    {
        Map<String, Object> dynParamMap = new HashMap<>();
        
        for (String param : dynParamList)
        {
            // 1.str 2.str1.str2.str3....
            String[] paramAry = param.split("\\.");
            
            int argsIndex = getArgsIndex(paramAry[0], args.length);
            
            Object value = args[argsIndex];
            
            if (paramAry.length > 1)// 说明是 str1.str2.str3....
            {
                
                for (int i = 1; i < paramAry.length; i++)
                {
                    try
                    {
                        Field field = value.getClass().getDeclaredField(paramAry[i]);// 为异常提示更清晰
                        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), value.getClass());
                        Method readMethod = pd.getReadMethod();
                        value = readMethod.invoke(value);
                    }
                    catch (Exception e)
                    {
                        throw new SecurityException("parse dynparam error. " + value.getClass().getName() + "."
                                + paramAry[i] + " is error.", e);
                    }
                }
                
            }
            
            dynParamMap.put(param, value);
        }
        
        return dynParamMap;
    }
    
    /**
     * 获取对应参数列表的下标
     * 
     * @param param
     *            动态参数中的arg0
     * @return 数列表的下标
     */
    private int getArgsIndex(String param, int length)
    {
        if (!param.matches("arg\\d"))
        {
            throw new SecurityException("param is error. format must match 'args\\d'. param:" + param);
        }
        
        int argsIndex = Integer.parseInt(param.substring("arg".length()));
        
        if (argsIndex < 0 || argsIndex >= length)
        {
            throw new SecurityException("param is error. argsIndex is " + argsIndex + " param:" + param);
        }
        
        return argsIndex;
    }
    
    private String buildResult(String source, Map<String, Object> dynParamMap)
    {
        for (Entry<String, Object> entry : dynParamMap.entrySet())
        {
            source = source.replace("#{" + entry.getKey() + "}", entry.getValue().toString());
        }
        
        return source;
    }
    
    private List<String> getDynParam(String logDesc)
    {
        List<String> dynParams = new ArrayList<>();
        
        Pattern p = Pattern.compile("#\\{[^\\{\\}]+\\}");
        Matcher m = p.matcher(logDesc);
        
        // 添加用户名为#{arg0.name}的用户，他的角色是#{arg0.role.name}.
        boolean flag = m.find();
        
        if (flag)
        {
            do
            {
                String temp = m.group(0);
                temp = temp.substring(temp.indexOf("{") + 1, temp.lastIndexOf("}"));
                dynParams.add(temp);
                
            } while (m.find());
        }
        
        return dynParams;
    }
    
}
