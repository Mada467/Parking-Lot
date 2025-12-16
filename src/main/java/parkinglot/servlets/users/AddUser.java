package parkinglot.servlets.users;

import parkinglot.ejb.UsersBean;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AddUser", value = "/AddUser")
public class AddUser extends HttpServlet {

    @Inject
    private UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Trimitem grupurile intotdeauna, ca sa fie vizibile in JSP
        request.setAttribute("userGroups", new String[] {"READ_CARS", "WRITE_CARS", "READ_USERS", "WRITE_USERS"});

        request.getRequestDispatcher("/WEB-INF/pages/users/addUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Preluam rolurile exact cum au fost bifate in formular (userGroups poate fi null sau plin)
        String[] userGroupsArray = request.getParameterValues("user_groups");
        List<String> groupsToAssign = (userGroupsArray == null) ? new ArrayList<>() : Arrays.asList(userGroupsArray);

        usersBean.createUser(username, email, password, groupsToAssign);

        // Dupa inregistrare, utilizatorul merge la Login.
        response.sendRedirect(request.getContextPath() + "/Login");
    }
}