<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Вакансии</title>
	<style>

    <%@include file="/WEB-INF/css/bootstrap.min.css"%>
    	    body {
    	    background-color: #9ca0ba;
    	    }

    		table {
    		    border-collapse: collapse;
    		    width: 100%;
    		    background-color: #6ecc95;
    		}

    		td, th {
    		    border: 2px solid #9b3b3b;
    		    text-align: left;
    		    padding: 8px;
    		    width: auto;
    		    text-align: center;
    		}

    	</style>
</head>
<body>
	<form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input class="btn btn-primary" type="submit" value="ВЫЙТИ" />
    </form>

	<h1>Добро пожаловать, ${currentUser.firstName} ${currentUser.lastName}</h1>
	
	<c:if test="${!empty vacanciesInState}">
		<p>Ниже представлены различные вакансии:</p>
		<table>
			<tr>
				<th>Вакансия</th>
				<th>Работодатель/Компания</th>
				<th>Представитель компании</th>
				<th>Контакты</th>
				<th>Действие / Статус</th>
			</tr>
			<c:forEach items="${vacanciesInState}" var="vacancy">
				<tr>
					<td><a href="/vacancies/${vacancy.id}">${vacancy.name}</a></td>
					<td>${vacancy.company}</td>
					<td>${vacancy.host.firstName}</td>
					<td>${vacancy.host.username}, ${vacancy.host.phone}</td>
					<td>
						<c:choose>
							<c:when test="${vacancy.host == currentUser}">
								<a href="/vacancies/${vacancy.id}/edit">Изменить</a> |
								<a href="/vacancies/${vacancy.id}/delete">Удалить</a>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vacancy.attendees.contains(currentUser)}">
										Заявка на рассмотрении |
										<a href="/vacancies/${vacancy.id}/cancel">Отменить</a>
									</c:when>
									<c:otherwise>
										<a href="/vacancies/${vacancy.id}/join">Откликнуться</a>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<div class="col-md-6 spacer">
        <div class="panel panel-info">
            <div class="panel-heading">Создание вакансии</div>
            <div class="panel-body">
                <p><form:errors path="vacancy.*"/></p>
                <form:form method="POST" action="/vacancies" modelAttribute="vacancy">
                    <p>
                        <form:label class="control-label" path="name">Вакансия:</form:label>
                        <form:input class="form-control" placeholder="Не менее 10 символов" path="name"/>
                    </p>
                    <p>
                        <form:label class="control-label" path="company">Работодатель/Компания:</form:label>
                        <form:input class="form-control" placeholder="Не менее 2 символов" path="company"/>
                    </p>
                    <input class="btn btn-primary" type="submit" value="СОЗДАТЬ ВАКАНСИЮ"/>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>