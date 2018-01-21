<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Редактирование вакансии</title>
    <style>

    <%@include file="/WEB-INF/css/bootstrap.min.css"%>
	    body {
	    background-color: #9ca0ba;
	    }

	</style>
</head>
<body>
	<p><a href="/vacancies">Все вакансии</a></p>
	<h1>${vacancy.name}</h1>

	<div class="col-md-6 spacer">
        <div class="panel panel-info">
            <div class="panel-heading">Изменение данных о вакансии</div>
            <div class="panel-body">
                <p><form:errors path="vacancy.*"/></p>
                <form:form method="POST" action="/vacancies/${id}/edit" modelAttribute="vacancy">
                    <p>
                        <form:label class="control-label" path="name">Вакансия:</form:label>
                        <form:input class="form-control" placeholder="Не менее 10 символов" path="name"/>
                    </p>
                    <p>
                        <form:label class="control-label" path="company">Работодатель/Компания:</form:label>
                        <form:input class="form-control" placeholder="Не менее 2 символов" path="company"/>
                    </p>
                    <input class="btn btn-primary" type="submit" value="Обновить данные"/>
                </form:form>
                <p><a href="/vacancies/${vacancy.id}/delete">Удалить вакансию</a></p>
            </div>
        </div>
    </div>
</body>
</html>