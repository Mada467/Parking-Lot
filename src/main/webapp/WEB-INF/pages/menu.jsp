<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header data-bs-theme="blue">
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">Parking Lot</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse"
                    aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav me-auto mb-2 mb-md-0">
                    <c:if test="${pageContext.request.isUserInRole('READ_CARS')}">
                        <li class="nav-item">
                            <a class="nav-link
                           ${pageContext.request.requestURI.substring(pageContext.request.requestURI.lastIndexOf("/"))
        eq '/cars.jsp' ? ' active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/Cars">Cars</a>
                        </li>
                    </c:if>
                    <c:if test="${pageContext.request.isUserInRole('READ_USERS')}">
                        <li class="nav-item">
                            <a class="nav-link
                           ${pageContext.request.requestURI.substring(pageContext.request.requestURI.lastIndexOf("/"))
        eq '/users.jsp' ? ' active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/Users">Users</a>
                        </li>
                    </c:if>
                </ul>
                <ul class="navbar-nav">
                    <c:choose>
                        <c:when test="${pageContext.request.getRemoteUser() == null}">

                            <%-- Secțiune pentru înregistrare (Add User) și Login --%>
                            <li class="nav-item">
                                <a class="btn btn-outline-light me-2" href="${pageContext.request.contextPath}/AddUser">Add User</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/Login">Login</a>
                            </li>
                        </c:when>
                        <c:otherwise>

                            <%-- Secțiune pentru Logout --%>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/Logout">Logout (${pageContext.request.getRemoteUser()})</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
</header>