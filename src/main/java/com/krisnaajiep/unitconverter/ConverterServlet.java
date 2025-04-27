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

/**
 * The {@code ConverterServlet} class is a servlet for handling unit conversion requests.
 * This servlet supports both GET and POST methods. The GET method retrieves available units
 * for a specific measurement type and forwards that data to a JSP page. The POST method handles
 * user-submitted conversion requests, validates the input, performs the conversion, and forwards
 * the result to the response page.
 */
@WebServlet(name = "converterServlet", value = "")
public class ConverterServlet extends HttpServlet {
    /**
     * A utility instance of {@link ConversionService} used for handling unit conversions.
     * This service provides methods for retrieving available units of a specific measurement type
     * and performing conversions between different units.
     */
    private final ConversionService conversionService = new ConversionService();

    /**
     * A static and immutable map defining the validation rules for input parameters.
     * Each key represents a parameter name, and the associated value is an array of validation
     * rules that should be applied to the respective parameter.
     */
    private static final Map<String, String[]> VALIDATION_RULES = Map.of(
            "from", new String[]{"required"},
            "to", new String[]{"required"},
            "value", new String[]{"required", "number"}
    );

    /**
     * Initializes the servlet and prepares it for handling requests. This method is
     * commonly used to perform one-time setup operations, such as loading resources
     * or initializing dependencies required by the servlet.
     */
    @Override
    public void init(){}

    /**
     * Handles HTTP GET requests by determining the measurement type,
     * retrieving corresponding units for the measurement, and forwarding
     * the request to the index.jsp page with the necessary data.
     *
     * @param request the HttpServletRequest object containing the client
     *                request and its parameters
     * @param response the HttpServletResponse object used to send the response
     *                 back to the client
     * @throws ServletException if a servlet-specific error occurs during request processing
     * @throws IOException if an I/O error occurs during request handling
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String measurement = determineMeasurement(request);
        request.setAttribute("measurementUnits", conversionService.getUnits(measurement));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * Processes HTTP POST requests by handling measurement conversions. If the
     * "convert" parameter is not present in the request, the method redirects
     * to an empty string path. Otherwise, it determines the type of measurement
     * to be converted and delegates the processing to the handleConversion method.
     *
     * @param request  the HttpServletRequest object containing the client request
     *                 and its parameters
     * @param response the HttpServletResponse object used to send the response
     *                 back to the client
     * @throws ServletException if a servlet-specific error occurs during request
     *                          processing
     * @throws IOException if an input or output error occurs during request handling
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("convert") == null) {
            response.sendRedirect("");
            return;
        }

        String measurement = determineMeasurement(request);
        handleConversion(request, response, measurement);
    }

    /**
     * Determines the type of measurement based on the "measurement" parameter
     * from the HTTP request. If the parameter is absent or invalid, it defaults
     * to "length".
     *
     * @param request the HttpServletRequest object containing the client request
     *                and its parameters
     * @return the determined measurement type; possible values are "length",
     *         "weight", or "temperature"
     */
    private String determineMeasurement(HttpServletRequest request) {
        String measurement = request.getParameter("measurement");

        return switch (measurement != null ? measurement.toLowerCase() : "length") {
            case "weight" -> "weight";
            case "temperature" -> "temperature";
            default -> "length";
        };
    }

    /**
     * Handles the conversion process based on the provided measurement type. This method retrieves
     * input values from the HTTP request, validates them, performs the conversion, and forwards
     * the result or error attributes to the JSP page.
     *
     * @param request the HttpServletRequest object containing the client request and its parameters
     * @param response the HttpServletResponse object for sending the response back to the client
     * @param measurement the type of measurement to be converted (e.g., "length", "weight", "temperature")
     * @throws ServletException if forwarding to the JSP page fails
     * @throws IOException if an input or output error occurs
     */
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

    /**
     * Extracts and constructs a ConversionInput object from the specified HTTP request.
     * The ConversionInput includes data for the source unit, target unit, and the value to
     * be converted, retrieved from the request parameters.
     *
     * @param request the HttpServletRequest object containing the client request,
     *                including parameters with keys "from", "to", and "value"
     * @return a ConversionInput object containing the extracted "from", "to", and "value" parameters
     */
    private ConversionInput getConversionInput(HttpServletRequest request) {
        return new ConversionInput(
                request.getParameter("from"),
                request.getParameter("to"),
                request.getParameter("value")
        );
    }

    /**
     * Validates the provided ConversionInput object. This method prepares a
     * map of inputs containing the source unit, target unit, and value to be
     * validated and applies validation rules using the InputValidator.
     *
     * @param input the ConversionInput object containing the "from", "to",
     *              and "value" parameters to be validated
     */
    private void validateInput(ConversionInput input) {
        Map<String, Object> inputs = Map.of(
                "from", input.from(),
                "to", input.to(),
                "value", input.value()
        );

        InputValidator.setRules(inputs, VALIDATION_RULES);
    }

    /**
     * Sets error attributes in the provided HttpServletRequest object. These attributes include
     * the available measurement units for the given measurement type, input parameters such as
     * "from", "to", and "value", and any validation errors obtained from the InputValidator.
     *
     * @param request the HttpServletRequest instance where the error attributes are to be set
     * @param input a ConversionInput object containing "from", "to", and "value" parameters
     *              representing the conversion input data
     * @param measurement a String representing the type of measurement, such as "length",
     *                    "weight", or "temperature", used to determine the applicable units
     */
    private void setErrorAttributes(HttpServletRequest request, ConversionInput input, String measurement) {
        request.setAttribute("measurementUnits", conversionService.getUnits(measurement));
        request.setAttribute("from", input.from());
        request.setAttribute("to", input.to());
        request.setAttribute("value", input.value());
        request.setAttribute("errors", InputValidator.getErrors());
    }


    /**
     * Cleans up any resources or data maintained by the servlet. This method is
     * called by the servlet container to indicate that the servlet is being removed
     * from service. It ensures that any thread-local validation errors managed
     * by the {@code InputValidator} class are cleared to release resources.
     */
    @Override
    public void destroy() {
        InputValidator.clearErrors();
    }

    /**
     * Represents input data required for a unit conversion operation. This record
     * encapsulates the source unit, target unit, and the value to be converted.
     *
     * @param from The source unit of the conversion (e.g., "meters").
     * @param to The target unit of the conversion (e.g., "kilometers").
     * @param value The value to be converted as a string.
     */
    private record ConversionInput(String from, String to, String value) {}
}
