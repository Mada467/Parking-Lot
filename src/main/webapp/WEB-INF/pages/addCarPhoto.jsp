<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Add Car Photo">
    <h1>Add Photo</h1>

    <form method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/AddCarPhoto">
        <div class="mb-3">
            <label for="license_plate" class="form-label">License Plate</label>
            <input type="text" class="form-control" id="license_plate" name="license_plate" value="${car.licensePlate}" readonly>
        </div>

        <div class="mb-3">
            <label for="file" class="form-label">Photo</label>
            <input type="file" class="form-control" id="file" name="file" required>
        </div>

        <input type="hidden" name="car_id" value="${car.id}">

        <button type="submit" class="btn btn-primary">Upload Photo</button>
    </form>
</t:pageTemplate>