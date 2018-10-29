<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	isErrorPage="true" 
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>

	
	<h2>错误显示页面...</h2>
    <p>Sorry, an error occurred.</p>
    <p>Here is the exception stack trace:</p>
    <pre>
        <%
       		out.write("================="+exception.getMessage());
            exception.printStackTrace(response.getWriter());
        %>
    </pre>

</body>
</html>