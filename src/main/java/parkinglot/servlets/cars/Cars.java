package parkinglot.servlets.cars;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.ServletSecurity; // NOU
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import parkinglot.common.CarDto;
import parkinglot.ejb.CarsBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Cars", value = "/Cars")
// REINTRODUCEM ServletSecurity, dar fără restricții specifice aici.
// Regulile vor veni din web.xml (care cere READ_CARS).
@ServletSecurity
public class Cars extends HttpServlet {

    @Inject
    private CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CarDto> cars = carsBean.findAllCars();
        request.setAttribute("cars", cars);

        int numberOfFreeParkingSpots = 10;
        request.setAttribute("numberOfFreeParkingSpots", numberOfFreeParkingSpots);

        // Păstrăm calea corectată, fără slash-ul inițial
        request.getRequestDispatcher("WEB-INF/pages/cars/cars.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] carIdsAsString = request.getParameterValues("car_ids");

        if (carIdsAsString != null) {
            List<Long> carIds = new ArrayList<>();
            for (String carIdStr : carIdsAsString) {
                carIds.add(Long.parseLong(carIdStr));
            }
            carsBean.deleteCarsByIds(carIds);
        }

        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}