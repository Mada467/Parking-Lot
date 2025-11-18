<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Cars">
    <h1>Cars</h1>
    <a href="${pageContext.request.contextPath}/AddCar" class="btn btn-primary btn-lg">Add Car</a>

    <c:if test="${not empty cars}">
        <!-- FORMULAR CARE ÎNCONJOARĂ TABELUL -->
        <form method="POST" action="${pageContext.request.contextPath}/Cars">
            <!-- BUTON DELETE -->
            <button type="submit" class="btn btn-danger mt-3">Delete Selected</button>

            <div class="table-responsive mt-3">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th scope="col">Select</th> <!-- COLOANĂ NOUĂ -->
                        <th scope="col">License Plate</th>
                        <th scope="col">Parking Spot</th>
                        <th scope="col">Owner</th>
                        <th scope="col">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="car" items="${cars}">
                        <tr>
                            <td>
                                <!-- CHECKBOX PENTRU FIECARE MAȘINĂ -->
                                <input type="checkbox" name="car_ids" value="${car.id}">
                            </td>
                            <td>${car.licensePlate}</td>
                            <td>${car.parkingSpot}</td>
                            <td>${car.ownerName}</td>
                            <td>
                                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditCar?id=${car.id}">Edit Car</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </form>
    </c:if>

    <h5>Free parking spots: ${numberOfFreeParkingSpots}</h5>
</t:pageTemplate>