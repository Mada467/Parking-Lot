<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Users">
    <h1>Users</h1>
    <div class="container text-center">
        <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
            <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddUser" role="button">Add User</a>
        </c:if>
    </div>

    <form method="POST" action="${pageContext.request.contextPath}/Users">
        <c:if test="${not empty users}">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">Username</th>
                        <th scope="col">Email</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>
                                <input type="checkbox" name="user_ids" value="${user.id}"/>
                            </td>
                            <td>${user.username}</td>
                            <td>${user.email}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <button type="submit" class="btn btn-primary">Invoice</button>
    </form>

    <c:if test="${not empty invoices}">
        <h3>Invoices</h3>
        <div class="list-group">
            <c:forEach var="username" items="${invoices}" varStatus="status">
                <div class="list-group-item">
                        ${status.index}. ${username}
                </div>
            </c:forEach>
        </div>
    </c:if>

</t:pageTemplate>