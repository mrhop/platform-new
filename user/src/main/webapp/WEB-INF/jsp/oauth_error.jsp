<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>异常 --- Hopever Oauth2 授权系统</title>
<link type="text/css" rel="stylesheet"
	href="../webjars/bootstrap/3.0.3/css/bootstrap.min.css" />
</head>
<body>
	<div class="container"  style="margin: 20px 0">
		<h1>Hopever Oauth2 授权异常</h1>
		<p>
			<c:out value="${message}" />
			(
			<c:out value="${error.summary}" />
			)
		</p>
		<div class="footer">
			请退回请求授权系统，进行重试，或者联系<a href="mailto:15309861499@163.com">15309861499@163.com</a>反馈问题。
		</div>
	</div>
</body>
</html>
