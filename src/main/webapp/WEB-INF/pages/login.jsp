<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Login">
    <c:if test="${not empty message}">
        <div class="alert alert-danger" role="alert">
                ${message}
        </div>
    </c:if>

    <form method="POST" action="j_security_check">
        <div class="mb-3">
            <label for="j_username" class="form-label">Username</label>
            <input type="text" class="form-control" id="j_username" name="j_username" required>
        </div>
        <div class="mb-3">
            <label for="j_password" class="form-label">Password</label>
            <input type="password" class="form-control" id="j_password" name="j_password" required>
        </div>
        <button type="submit" class="btn btn-primary">Sign In</button>
    </form>
</t:pageTemplate>