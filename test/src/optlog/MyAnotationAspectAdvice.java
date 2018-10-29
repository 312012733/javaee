//package optlog;
//
//import java.beans.IntrospectionException;
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import utils.ThreadLocalUtil;
//
///**
// * 通知类：实现操作日志切面的逻辑
// */
//@Component
//@Aspect
//public class MyAnotationAspectAdvice
//{
//    
//    @Around(value = "execution(* com.my.service..*(..)) && @annotation(systemLogger)")
//    public Object saveOptlog(ProceedingJoinPoint jp, SystemLogger systemLogger)
//    {
//        Object result = null;
//        
//        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
//        String[] parameterNames = methodSignature.getParameterNames();
//        
//        try
//        {
//            
//            // before
//            
//            // method invoke
//            result = jp.proceed();
//            
//            saveOptlog(systemLogger, parameterNames, jp.getArgs(), true);
//            
//            // after
//            
//        }
//        catch (Throwable e)
//        {
//            saveOptlog(systemLogger, parameterNames, jp.getArgs(), false);
//            System.out.println("Throwable");
//        }
//        
//        return result;
//    }
//    
//    public static void saveOptlog(SystemLogger systemLogger, String[] parameterNames, Object[] args, boolean success)
//    {
//        // 保存日志
//        String logDesc = parseLogDesc(systemLogger.desc(), parameterNames, args);
//        
//        String loggerType = systemLogger.type().getValue();
//        String loggerOptType = systemLogger.operation().getValue();
//        long time = System.currentTimeMillis();
//        boolean result = success;
//        
//        HttpServletRequest req = (HttpServletRequest) ThreadLocalUtil.get("request");
//        
//        String ip = req.getRemoteHost();
//        
//        // String userName = req.getSession(false).getAttribute("user");
//        
//    }
//    
//    private static String parseLogDesc(String logDesc, String[] parameterNames, Object[] args)
//    {
//        // 1:获取动态参数 .
//        // 例如：添加用户名为{user.name}的用户，他的角色是{user.role.name} 中的 user.name 和 user.role.name
//        List<String> dynParams = getDynParam(logDesc);
//        
//        if (dynParams.isEmpty())
//        {
//            return logDesc;
//        }
//        
//        // 2: 以动态参数为key（user.name 和 user.role.name），  以 方法的参数或方法参数下面的属性 为值 ，构建一个map
//        Map<String, String> map = getDynParamMap(parameterNames, args, dynParams);
//        
//        // 3:把 日志描述中的动态参数 依次替换成对应的值
//        for (Entry<String, String> entry : map.entrySet())
//        {
//            logDesc = logDesc.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue());
//        }
//        
//        return logDesc;
//    }
//    
//    private static Map<String, String> getDynParamMap(String[] parameterNames, Object[] args, List<String> dynParams)
//    {
//        Map<String, String> map = new HashMap<>();
//        
//        for (String dynParam : dynParams)
//        {
//            int index = -1;// 记录动态参数 在方法参数中的位子
//            
//            // user.role.name
//            // id
//            String[] dynParamAttrs = dynParam.split("\\.");
//            
//            for (int i = 0; i < parameterNames.length; i++)
//            {
//                if (dynParamAttrs[0].equals(parameterNames[i]))
//                {
//                    index = i;
//                    break;
//                }
//            }
//            
//            if (index < 0)
//            {
//                System.out.println("【error】动态参数错误. dynParam:" + dynParam);
//            }
//            else
//            {
//                Object value = args[index];
//                
//                if (dynParamAttrs.length > 1)// user.role.name
//                {
//                    
//                    for (int i = 1; i < dynParamAttrs.length; i++)
//                    {
//                        try
//                        {
//                            PropertyDescriptor pd = new PropertyDescriptor(dynParamAttrs[i], value.getClass());
//                            Method m = pd.getReadMethod();
//                            value = m.invoke(value);
//                        }
//                        catch (IntrospectionException e)
//                        {
//                            System.out.println("【error】动态参数[" + dynParam + "]属性错误. dynParamAttr:" + dynParamAttrs[i]);
//                        }
//                        catch (Exception e)
//                        {
//                            System.out.println("【error】动态参数[" + dynParam + "]获取" + dynParamAttrs[i] + "值失败. 参数"
//                                    + dynParamAttrs[i - 1] + ":" + value);
//                        }
//                        
//                    }
//                }
//                
//                map.put(dynParam, value == null ? "null" : value.toString());
//            }
//            
//        }
//        return map;
//    }
//    
//    private static List<String> getDynParam(String logDesc)
//    {
//        List<String> dynParams = new ArrayList<>();
//        
//        Pattern p = Pattern.compile("\\{[^\\{\\}]+\\}");
//        Matcher m = p.matcher(logDesc);
//
////        添加用户名为{user.name}的用户，他的角色是{user.role.name}.
//        boolean flag = m.find();
//        
//        if (flag)
//        {
//            do
//            {
//                String temp = m.group().substring(1);
//                dynParams.add(temp.substring(0, temp.length() - 1));
//                
//            } while (m.find());
//        }
//        
//        return dynParams;
//    }
//    
//}
