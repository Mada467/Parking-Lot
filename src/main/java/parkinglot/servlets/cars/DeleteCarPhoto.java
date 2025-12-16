package parkinglot.servlets.cars;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import parkinglot.common.CarPhotoDto;
import parkinglot.ejb.CarsBean;

import jakarta.inject.Inject;
import java.io.IOException;

@WebServlet(name = "DeleteCarPhoto", value = "/DeleteCarPhoto")
public class DeleteCarPhoto extends HttpServlet {

    @Inject
    private CarsBean carsBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the car ID from the request
        Long carId = Long.parseLong(request.getParameter("id"));

        // Get the current photo for this car
        CarPhotoDto photo = carsBean.findPhotoByCarId(carId);

        if (photo != null) {
            // Delete the photo
            carsBean.deleteCarPhoto(carId);
        }

        // Redirect back to the Cars page
        response.sendRedirect(request.getContextPath() + "/Cars");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}