package com.dmdev.http.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.dmdev.http.util.UrlPath.FIRST;

@WebServlet(FIRST)
public class FirstServlet extends HttpServlet
{
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        // Работа с параметрами запроса
        var paramValue = req.getParameter("param");
        var parameterMap = req.getParameterMap();

        // Работа с заголовками (headers) в doGet()
        // header позволяет узнать, откуда пришел запрос
        req.getHeader("user-agent");

        var headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            var header = headerNames.nextElement();
            System.out.println(req.getHeader(header));
        }

        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setHeader("token", "12345");
        // либо
        //resp.setContentType("text/html; charset=UTF-8");
        try (var writer = resp.getWriter())
        {
            writer.write("<h1>Привет from First Servlet</h1>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        // Работа с заголовками (headers)
        var parameterMap = req.getParameterMap();
        System.out.println(parameterMap);

        // Работа с телом (body)
        try (var reader = req.getReader();
             var lines = reader.lines())
        {
            lines.forEach(System.out::println);
        }
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }
}
