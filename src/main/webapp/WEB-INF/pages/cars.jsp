<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Cars">
    <h1>Cars</h1>
    <a href="${pageContext.request.contextPath}/AddCar" class="btn btn-primary btn-lg">Add Car</a>

    <c:if test="${not empty cars}">
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">License Plate</th>
                    <th scope="col">Parking Spot</th>
                    <th scope="col">Owner</th>
                    <th scope="col">Actions</th> <!-- COLOANĂ NOUĂ -->
                </tr>
                </thead>
                <tbody>
                <c:forEach var="car" items="${cars}">
                    <tr>
                        <td>${car.licensePlate}</td>
                        <td>${car.parkingSpot}</td>
                        <td>${car.ownerName}</td>
                        <td>
                            <!-- BUTON EDIT -->
                            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditCar?id=${car.id}">Edit Car</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <h5>Free parking spots: ${numberOfFreeParkingSpots}</h5>
</t:pageTemplate>