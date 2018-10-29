<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>


	<%-- 	<div>@copy right 4  ${pageScope.key1}></div> --%>
	<%-- 	<div>@copy right 4  ${sessionScope.key1}></div> --%>
	<%-- 	<div>@copy right 4  ${applicationScope.key1}></div> --%>
<%-- 	<div>@copy right 4 ${requestScope.key1}></div> --%>

	<div>@copy right 4 ${key1}></div>
	<div>@copy right 4 ${header.Cookie}></div>
	<div>@copy right 4 ${param.p1}></div>
	
	<%-- 	<div>@copy right 4  <%= request.getAttribute("key1") %>></div> --%>

</body>
</html>