<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="/resources/inc/head.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>success</title>
</head>
<body>
	<center>
		<div>
			<h2>access test success!</h2>
			地址：<span>${fn:escapeXml(user.address)}</span>
		</div>
		<a href="user_logout.php">退出</a>
	</center>
</body>
</html>