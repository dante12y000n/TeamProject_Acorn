<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/insert.jsp</title>
<jsp:include page="../include/resource.jsp"/>
</head>
<body>
<div class="container">
	<script>
		alert("<strong>${dto.userid }</strong> 회원님 가입 되었습니다.");
		location.href="${pageContext.request.contextPath }/home.go";
	</script>
</div>
</body>
</html>
