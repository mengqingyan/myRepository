<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/resources/inc/head.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<body>
	<center>
		<div>
			<h6>username, password</h6>
			<br>
			adminUser, admin123<br>
			generalUser, user123<br>
			managerUser, manager123<br>
			mqyTest, mqytest
			<br>
		</div>
		<br><br>
		
		<s:form action="user_doLogin" method="post">
			<s:textfield name="user.username" label="用户名"/>
			<s:password name="user.password" label="密 码"/>
			<s:textfield name="user.emp.username" label="职工名"/>
			<s:textfield name="user.address" label="地址"/>
			<sec:csrfInput/>
			<s:submit value="登录"></s:submit>
		</s:form>
	</center>
</body>
</html>