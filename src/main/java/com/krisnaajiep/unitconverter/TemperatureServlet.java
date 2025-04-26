package com.krisnaajiep.unitconverter;

/*
IntelliJ IDEA 2025.1 (Ultimate Edition)
Build #IU-251.23774.435, built on April 14, 2025
@Author krisna a.k.a. Krisna Ajie
Java Developer
Created on 26/04/25 11.29
@Last Modified 26/04/25 11.29
Version 1.0
*/

import com.krisnaajiep.unitconverter.service.ConversionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "temperatureServlet", value = "/temperature")
public class TemperatureServlet extends HttpServlet {
    private final ConversionService conversionService = new ConversionService();

    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("temperatureUnits", conversionService.getUnits("temperature"));
        request.getRequestDispatcher("/temperature.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("convert") != null) {
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String value = request.getParameter("value");
            Map<String, String> result = conversionService.convertTemperature(from, to, value);

            request.setAttribute("result", result);
            request.getRequestDispatcher("/temperature.jsp").forward(request, response);
        } else {
            response.sendRedirect("");
        }
    }

    public void destroy() {}
}
