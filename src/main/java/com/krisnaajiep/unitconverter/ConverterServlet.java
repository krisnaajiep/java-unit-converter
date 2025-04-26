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
    private static final Map<String, String[]> VALIDATION_RULES = Map.of(
            "from", new String[]{"required"},
            "to", new String[]{"required"},
            "value", new String[]{"required", "number"}
    );

    @Override
    public void init(){}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String measurement = determineMeasurement(request);
        request.setAttribute("measurementUnits", conversionService.getUnits(measurement));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("convert") == null) {
            response.sendRedirect("");
            return;
        }

        String measurement = determineMeasurement(request);
        handleConversion(request, response, measurement);
    }

    private String determineMeasurement(HttpServletRequest request) {
        String measurement = request.getParameter("measurement");

        return switch (measurement != null ? measurement.toLowerCase() : "length") {
            case "weight" -> "weight";
            case "temperature" -> "temperature";
            default -> "length";
        };
    }

    private void handleConversion(HttpServletRequest request, HttpServletResponse response, String measurement)
            throws ServletException, IOException {
        ConversionInput input = getConversionInput(request);

        validateInput(input);

        if (!InputValidator.getErrors().isEmpty()) {
            setErrorAttributes(request, input, measurement);
        } else {
            Map<String, String> result = conversionService.convert(
                    measurement,
                    input.from(),
                    input.to(),
                    input.value()
            );
            request.setAttribute("result", result);
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private ConversionInput getConversionInput(HttpServletRequest request) {
        return new ConversionInput(
                request.getParameter("from"),
                request.getParameter("to"),
                request.getParameter("value")
        );
    }

    private void validateInput(ConversionInput input) {
        Map<String, Object> inputs = Map.of(
                "from", input.from(),
                "to", input.to(),
                "value", input.value()
        );

        InputValidator.setRules(inputs, VALIDATION_RULES);
    }

    private void setErrorAttributes(HttpServletRequest request, ConversionInput input, String measurement) {
        request.setAttribute("measurementUnits", conversionService.getUnits(measurement));
        request.setAttribute("from", input.from());
        request.setAttribute("to", input.to());
        request.setAttribute("value", input.value());
        request.setAttribute("errors", InputValidator.getErrors());
    }


    @Override
    public void destroy() {
        InputValidator.clearErrors();
    }

    private record ConversionInput(String from, String to, String value) {}
}
