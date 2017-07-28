<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>授权 --- Hopever Oauth2 授权系统</title>
    <link type="text/css" rel="stylesheet"
          href="../webjars/bootstrap/3.0.3/css/bootstrap.min.css"/>
</head>

<body>

<div class="container">
    <h1>Hopever Oauth2 授权系统</h1>

    <h2>请确认</h2>

    <p>
        您即将授权 "
        <c:out value="${client.clientName}"/>
        " 访问您如下的受保护资源.
    </p>

    <form id="confirmationForm" name="confirmationForm"
          action="<%=request.getContextPath()%>/oauth/authorize" method="post">
        <input name="user_oauth_approval" value="true" type="hidden"/>
        <ul class="list-unstyled">
            <c:forEach items="${scopes}" var="scope">
                <c:set var="approved">
                    <c:if test="${scope.value}"> checked</c:if>
                </c:set>
                <c:set var="denied">
                    <c:if test="${!scope.value}"> checked</c:if>
                </c:set>
                <li>
                    <div class="form-group">
                            ${scope.key}: <input type="radio" name="${scope.key}"
                                                 value="true" ${approved}>允许访问</input> <input type="radio"
                                                                                              name="${scope.key}"
                                                                                              value="false" ${denied}>禁止访问</input>
                    </div>
                </li>
            </c:forEach>
        </ul>
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">确定</button>
    </form>
    <div class="footer" style="margin: 20px 0">
        请谨慎确认并进行授权，有问题请联系<a href="mailto:15309861499@163.com">15309861499@163.com</a>。
    </div>
</div>

</body>
</html>
