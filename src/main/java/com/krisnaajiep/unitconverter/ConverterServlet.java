package com.krisnaajiep.unitconverter;

/*
IntelliJ IDEA 2025.1 (Ultimate Edition)
Build #IU-251.23774.435, built on April 14, 2025
@Author krisna a.k.a. Krisna Ajie
Java Developer
Created on 26/04/25 00.44
@Last Modified 26/04/25 00.44
Version 1.0
*/

import com.krisnaajiep.unitconverter.service.ConversionService;
import com.krisnaajiep.unitconverter.util.validator.InputValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "converterServlet", value = "")
public class ConverterServlet extends HttpServlet {
    private final ConversionService conversionService = new ConversionService();
    private String measurement;

    public void init(){}

    private void setUp(HttpServletRequest request) {
        String parameter = request.getParameter("measurement") == null
                ? "length"
                : request.getParameter("measurement");

        switch (parameter.toLowerCase()) {
            case "weight" -> measurement = "weight";
            case "temperature" -> measurement = "temperature";
            default -> measurement = "length";
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        setUp(request);

        request.setAttribute("measurementUnits", conversionService.getUnits(measurement));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        setUp(request);

        if (request.getParameter("convert") != null) {
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String value = request.getParameter("value");

            Map<String, Object> inputs = Map.of("from", from, "to", to, "value", value);
            Map<String, String[]> rules = Map.of(
                    "from", new String[]{"required"},
                    "to", new String[]{"required"},
                    "value", new String[]{"required", "number"}
            );

            InputValidator.setRules(inputs, rules);
            request.setAttribute("errors", InputValidator.getErrors());

            if (!InputValidator.getErrors().isEmpty()) {
                request.setAttribute("measurementUnits", conversionService.getUnits(measurement));
                request.setAttribute("from", from);
                request.setAttribute("to", to);
                request.setAttribute("value", value);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                Map<String, String> result = conversionService.convert(measurement, from, to, value);

                request.setAttribute("result", result);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("");
        }
    }

    public void destroy() {
        InputValidator.clearErrors();
    }
}
