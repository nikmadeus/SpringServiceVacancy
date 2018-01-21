<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${vacancy.name}</title>
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
	<p><a href="/vacancies">Все вакансии</a></p>
	<h1>${vacancy.name}</h1>
	<p>Представитель компании: ${vacancy.host.firstName} ${vacancy.host.lastName}</p>
	<p>Контакты: ${vacancy.host.username}, ${vacancy.host.phone}</p>
	<p>Работодатель/Компания: ${vacancy.company}</p>
	<p>Количество людей, откликнувшихся на вакансию: ${vacancy.attendees.size()}</p>
	
	<c:if test="${!empty vacancy.attendees}">
		<table>
			<tr>
				<th>Кандидат</th>
				<th>Эл.почта</th>
				<th>Телефон</th>
			</tr>
			<c:forEach items="${vacancy.attendees}" var="attendee">
				<tr>
					<td>${attendee.firstName} ${attendee.lastName}</td>
					<td>${attendee.username}</td>
					<td>${attendee.phone}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	
	<div class="col-md-6 spacer">
        <div class="panel panel-info">
            <div class="panel-heading">Комментарии</div>
            <div class="panel-body">
                <c:if test="${!empty comments}">
                    <c:forEach items="${comments}" var="comment" varStatus="loop">
                        <p>${comment.author.firstName} ${comment.author.lastName} написал: ${comment.text}</p>
                        <c:if test="{loop.index < comments.size()}">
                            <p>-----------------------------------------</p>
                        </c:if>
                    </c:forEach>
                </c:if>
                <p><form:errors path="comment.*"/></p>
                <form:form method="POST" action="/vacancies/${vacancy.id}/newcomment" modelAttribute="comment">
                    <p>
                        <form:label path="text">Добавить комментарий:</form:label>
                        <br>
                        <form:textarea placeholder="Не менее 5 символов" path="text"/>
                    </p>
                    <input class="btn btn-primary" type="submit" value="ОТПРАВИТЬ КОММЕНТАРИЙ"/>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>