<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Add User">
    <h1>Add User</h1>
    <form method="POST" action="${pageContext.request.contextPath}/AddUser">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <div class="mb-3">
            <label class="form-label">User Groups</label>
            <c:forEach var="userGroup" items="${userGroups}">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="user_groups" value="${userGroup}" id="${userGroup}">
                    <label class="form-check-label" for="${userGroup}">
                            ${userGroup}
                    </label>
                </div>
            </c:forEach>
        </div>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</t:pageTemplate>