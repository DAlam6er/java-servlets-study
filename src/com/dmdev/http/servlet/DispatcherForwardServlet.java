package com.dmdev.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.dmdev.http.util.UrlPath.DISPATCHER_FORWARD;

@WebServlet(DISPATCHER_FORWARD)
public class DispatcherForwardServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        /*
        var requestDispatcher = req.getRequestDispatcher("/flights");

        // В дальнейшем атрибут "1" будет использовать сервлет, обрабатывающий /flights
        req.setAttribute("1", "234");
        requestDispatcher.forward(req, resp);
         */

        // Получение объекта RequestDispatcher через ServletContext()
        //getServletContext().getRequestDispatcher();

        // более компактный вариант (неявное обращение к ServletContext)
        req.getRequestDispatcher("/flights").forward(req, resp);
        // В выходной поток ничего не будет записано!
        var writer = resp.getWriter();
        writer.write("Hello 2");
    }
}
