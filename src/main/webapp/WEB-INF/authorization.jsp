<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Вход в систему</title>
	<style>

    <%@include file="/WEB-INF/css/bootstrap.min.css"%>
        body {
        background-color: #9ca0ba;
        }

    </style>
</head>
<body>
	<c:if test="${logoutMessage != null}">
        <c:out value="${logoutMessage}"></c:out>
    </c:if>
	<div class="col-md-6 spacer">
            <div class="panel panel-info">
                <div class="panel-heading">Регистрация</div>
                <div class="panel-body">
                    <p><form:errors path="user.*"/></p>
                    <form:form method="POST" action="/registration" modelAttribute="user">
                        <p>
                            <form:label class="control-label" path="firstName">Ваше имя:</form:label>
                            <form:input class="form-control" placeholder="Не менее 2 символов" path="firstName"/>
                        </p>
                        <p>
                            <form:label class="control-label" path="lastName">Ваша фамилия:</form:label>
                            <form:input class="form-control" placeholder="Не менее 2 символов" path="lastName"/>
                        </p>
                        <p>
                            <form:label class="control-label" path="username">Эл.почта:</form:label>
                            <form:input class="form-control" placeholder="example@mail.ru" path="username"/>
                        </p>
                        <p>
                            <form:label class="control-label" path="phone">Телефон:</form:label>
                            <form:input class="form-control" placeholder="Не менее 6 цифр" path="phone"/>
                        </p>
                        <p>
                            <form:label class="control-label" path="password">Пароль:</form:label>
                            <form:password class="form-control" placeholder="Не менее 8 символов" path="password"/>
                        </p>

                        <input class="btn btn-primary" type="submit" value="СОЗДАТЬ УЧЕТНУЮ ЗАПИСЬ"/>
                    </form:form>
            </div>
        </div>
    </div>

    <div class="col-md-6 spacer">
        <div class="panel panel-info">
            <div class="panel-heading">Вход в систему</div>
            <div class="panel-body">
                <c:if test="${errorMessage != null}">
                    <c:out value="${errorMessage}"></c:out>
                </c:if>
                <form method="POST" action="/">
                    <p>
                        <label class="control-label" for="username">Эл.почта:</label>
                        <input class="form-control" type="text" placeholder="@" id="username" name="username"/>
                    </p>
                    <p>
                        <label class="control-label" for="password">Пароль:</label>
                        <input class="form-control" type="password" placeholder="Не менее 8 символов" id="password" name="password"/>
                    </p>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input class="btn btn-primary" type="submit" value="ВОЙТИ"/>
                </form>
            </div>
        </div>
    </div>
</body>
</html>