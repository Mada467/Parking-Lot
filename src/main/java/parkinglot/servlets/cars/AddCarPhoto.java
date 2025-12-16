package parkinglot.servlets.cars;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import parkinglot.common.CarDto;
import parkinglot.ejb.CarsBean;

import jakarta.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "AddCarPhoto", value = "/AddCarPhoto")
@MultipartConfig
public class AddCarPhoto extends HttpServlet {

    @Inject
    private CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the car ID from the URL parameter
        Long carId = Long.parseLong(request.getParameter("id"));

        // Get car details from database
        CarDto car = carsBean.findById(carId);

        // Put car in request scope so JSP can access it
        request.setAttribute("car", car);

        // Forward to the JSP page (calea corectă!)
        request.getRequestDispatcher("/WEB-INF/pages/cars/addCarPhoto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the car ID from the form
        Long carId = Long.parseLong(request.getParameter("car_id"));

        // Get the uploaded file
        Part filePart = request.getPart("file");

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            String fileType = filePart.getContentType();

            // Read the file content
            InputStream fileContent = filePart.getInputStream();
            byte[] fileBytes = fileContent.readAllBytes();

            // Save the photo to the database
            carsBean.addPhotoToCar(carId, fileName, fileType, fileBytes);
        }

        // Redirect back to the Cars page
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}