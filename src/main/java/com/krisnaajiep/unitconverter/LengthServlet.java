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

import com.krisnaajiep.unitconverter.util.ConversionMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "lengthServlet", value = "/length")
public class LengthServlet extends HttpServlet {
    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("lengthUnits", ConversionMap.getLengthUnits().keySet().toArray());
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("convert") != null) {
            String value = request.getParameter("value");
            request.setAttribute("value", value);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            response.sendRedirect("");
        }
    }

    public void destroy() {}
}
