package parkinglot.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import parkinglot.common.UserDto;
import parkinglot.ejb.UsersBean;
import parkinglot.ejb.CarsBean;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddCars", value = "/AddCar")
public class AddCars extends HttpServlet {

    @Inject
    private UsersBean usersBean;

    @Inject
    private CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Găsește toți utilizatorii pentru dropdown-ul Owner
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);

        // Forward către pagina addCar.jsp
        request.getRequestDispatcher("/WEB-INF/pages/addCar.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Extrage parametrii din formular
        String licensePlate = request.getParameter("license_plate");
        String parkingSpot = request.getParameter("parking_spot");
        Long ownerId = Long.parseLong(request.getParameter("owner_id"));

        // Creează mașina folosind CarsBean
        carsBean.createCar(licensePlate, parkingSpot, ownerId);

        // Redirect înapoi la lista de mașini
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}