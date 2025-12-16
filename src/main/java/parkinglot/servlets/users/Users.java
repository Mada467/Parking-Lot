package parkinglot.servlets.users;

import parkinglot.ejb.InvoiceBean;
import parkinglot.ejb.UsersBean;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "Users", value = "/Users")
@DeclareRoles({"READ_USERS", "WRITE_USERS"})
@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"READ_USERS"}),
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "POST", rolesAllowed = {"WRITE_USERS"})
        }
)
public class Users extends HttpServlet {

    @Inject
    private UsersBean usersBean;

    @Inject
    private InvoiceBean invoiceBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("users", usersBean.findAllUsers());

        // Obține usernames pentru userii selectați pentru facturare
        if (invoiceBean.getUserIds() != null && !invoiceBean.getUserIds().isEmpty()) {
            List<String> invoices = usersBean.findUsernamesByUserIds(invoiceBean.getUserIds());
            request.setAttribute("invoices", invoices);
        }

        request.setAttribute("activePage", "Users");
        request.getRequestDispatcher("/WEB-INF/pages/users/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Preia valorile checkboxurilor
        String[] userIdsArray = request.getParameterValues("user_ids");

        if (userIdsArray != null) {
            // Convertește String[] în List<Long>
            List<Long> userIds = Arrays.stream(userIdsArray)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            // Salvează în InvoiceBean
            invoiceBean.getUserIds().clear();
            invoiceBean.getUserIds().addAll(userIds);
        } else {
            // Dacă nu e nimic selectat, golește lista
            invoiceBean.getUserIds().clear();
        }

        // Redirectează înapoi la GET
        response.sendRedirect(request.getContextPath() + "/Users");
    }
}