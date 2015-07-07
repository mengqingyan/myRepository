<%@ page language="java" deferredSyntaxAllowedAsLiteral="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String contextPath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath %>/resources/js/JSON-js-master/json2.js"></script>
<script type="text/javascript" src="<%=contextPath %>/resources/js/utils.js"></script>


<script type="text/javascript">
	var ctxPath = "<%=contextPath %>";
</script>







