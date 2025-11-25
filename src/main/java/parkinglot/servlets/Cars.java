package parkinglot.servlets;

import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
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
@DeclareRoles({"READ_CARS", "WRITE_CARS"})
@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"READ_CARS"}),
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "POST", rolesAllowed = {"WRITE_CARS"})
        }
)
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

        request.getRequestDispatcher("/WEB-INF/pages/cars.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Extrage ID-urile mașinilor selectate
        String[] carIdsAsString = request.getParameterValues("car_ids");

        // Verifică dacă s-au selectat mașini
        if (carIdsAsString != null) {
            // Convertește String[] în List<Long>
            List<Long> carIds = new ArrayList<>();
            for (String carIdStr : carIdsAsString) {
                carIds.add(Long.parseLong(carIdStr));
            }

            // Șterge mașinile
            carsBean.deleteCarsByIds(carIds);
        }

        // Redirect înapoi la pagina Cars
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}