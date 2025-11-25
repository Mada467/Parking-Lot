package parkinglot.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import parkinglot.common.CarDto;
import parkinglot.common.UserDto;
import parkinglot.ejb.CarsBean;
import parkinglot.ejb.UsersBean;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditCar", value = "/EditCar")
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_CARS"}))
public class EditCar extends HttpServlet {

    @Inject
    private CarsBean carsBean;

    @Inject
    private UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Extrage ID-ul mașinii din URL
        Long carId = Long.parseLong(request.getParameter("id"));

        // Găsește mașina pe care o editezi
        CarDto car = carsBean.findById(carId);
        request.setAttribute("car", car);

        // Găsește toți utilizatorii pentru dropdown
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);

        // Forward către pagina de editare
        request.getRequestDispatcher("/WEB-INF/pages/editCar.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Extrage parametrii din formular
        Long carId = Long.parseLong(request.getParameter("car_id"));
        String licensePlate = request.getParameter("license_plate");
        String parkingSpot = request.getParameter("parking_spot");
        Long ownerId = Long.parseLong(request.getParameter("owner_id"));

        // Actualizează mașina
        carsBean.updateCar(carId, licensePlate, parkingSpot, ownerId);

        // Redirect înapoi la lista de mașini
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}