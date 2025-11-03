package com.example.parkinglot;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Cars", value = "/Cars")
public class Cars extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("numberOfFreeParkingSpots", 10);
        request.getRequestDispatcher("/WEB-INF/pages/cars.jsp").forward(request,response);
}

}