<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!-- 引入jstl的标签库 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>

	<!-- 我是html的注释    -->
	<%--  我是jsp的注释    --%>

	<%--  我是 jsp 声明     <%!   %>  声明里面定义的变量是 类成员变量，方法是 类的方法   --%>
	<%--  我是 jsp 表达式  <%=   %>   --%>
	<%--  我是 jsp 脚本     <%    %>  --%>
	<%--  我是 jsp 指令     <%@   %>   --%>
	<%--  我是 jsp 动作    <jsp:   >   --%>

	<%--
	1	request	这是与请求相关联的HttpServletRequest对象。
	2	response	这是与客户端的响应关联的HttpServletResponse对象。
	3	out	这是用于将输出发送到客户端的PrintWriter对象。
	4	session	这是与请求相关联的HttpSession对象。
	5	application	这是与应用程序上下文相关联的ServletContext对象。
	6	config	这是与该页面相关联的ServletConfig对象。
	
	7	pageContext	这封装了使用服务器特定的功能，如更高性能的JspWriter。
	8	page	这只是一个同义词，用于调用由翻译的servlet类定义的方法。(this)
	9	Exception	Exception对象允许指定的JSP访问异常数据。
	--%>

	<%!public static int temp = 9527;
    
    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ms");
    
    public String formatTime(long time)
    {
        return sf.format(time);
    }%>

	<%
	    int i = 0;
	   
	    for (; i < 10; i++)
	    {
	%>

	<div>
		时间<%=i%>：<%=formatTime(System.currentTimeMillis())%></div>

	<%
	    if (i > 8)
	        {
	         //   throw new SecurityException("参数错误");
	        }
	        
	    }
	%>

	<%@ include file="test2.jsp"%><!-- jsp指令的包含，是静态包含，先包含在编译 -->

	<%-- 	<%@ include file="test3.jsp" %> --%>
	<jsp:include page="test3.jsp"></jsp:include><!-- jsp动作的包含，是动态包含（通过调用java api 来包含），分别编译，再包含 -->


	<%
		request.setAttribute("key1","aaa");
// 		pageContext.setAttribute("key1","aaa");
	%>
	
	<div><%= request.getAttribute("key1") %></div>
	
<%-- 	<jsp:forward page="test4.jsp"></jsp:forward> --%>

</body>
</html>