<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Cars">
    <h1>Cars</h1>

    <!-- BUTONUL ADD CAR - doar dacă ai WRITE_CARS -->
    <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
        <a href="${pageContext.request.contextPath}/AddCar" class="btn btn-primary btn-lg">Add Car</a>
    </c:if>

    <c:if test="${not empty cars}">
        <!-- FORMULAR CARE ÎNCONJOARĂ TABELUL -->
        <form method="POST" action="${pageContext.request.contextPath}/Cars">
            <!-- BUTON DELETE - doar dacă ai WRITE_CARS -->
            <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                <button type="submit" class="btn btn-danger mt-3">Delete Selected</button>
            </c:if>

            <div class="table-responsive mt-3">
                <table class="table table-striped">
                    <thead >
                    <tr>
                        <!-- COLOANA SELECT - doar dacă ai WRITE_CARS -->
                        <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                            <th scope ="col">Select</th>
                        </c:if>
                        <th scope="col">License Plate</th>
                        <th scope="col">Parking Spot</th>
                        <th scope="col">Owner</th>
                        <!-- COLOANA ACTIONS - doar dacă ai WRITE_CARS -->
                        <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                            <th scope="col">Actions</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="car" items="${cars}">
                        <tr>
                            <!-- CHECKBOX - doar dacă ai WRITE_CARS -->
                            <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                                <td>
                                    <input type="checkbox" name="car_ids" value="${car.id}">
                                </td>
                            </c:if>
                            <td>${car.licensePlate}</td>
                            <td>${car.parkingSpot}</td>
                            <td>${car.ownerName}</td>
                            <!-- BUTONUL EDIT - doar dacă ai WRITE_CARS -->
                            <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                                <td>
                                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditCar?id=${car.id}">Edit Car</a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </form>
    </c:if>

    <h5>Free parking spots: ${numberOfFreeParkingSpots}</h5>
</t:pageTemplate>