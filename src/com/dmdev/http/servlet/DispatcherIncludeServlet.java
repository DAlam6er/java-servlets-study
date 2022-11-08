package com.dmdev.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.dmdev.http.util.UrlPath.DISPATCHER_INCLUDE;

@WebServlet(DISPATCHER_INCLUDE)
public class DispatcherIncludeServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        req.getRequestDispatcher("/flights").include(req, resp);
        // без этой строки будут крокозябры, т.к. headers сервлета FlightServlet не работают
        resp.setContentType("text/html; charset=UTF-8");

        // В выходной поток будет записано "Hello 2"
        var writer = resp.getWriter();
        writer.write("Hello 2");
    }
}
