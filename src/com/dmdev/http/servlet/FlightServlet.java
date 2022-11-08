package com.dmdev.http.servlet;

import com.dmdev.http.service.FlightService;
import com.dmdev.http.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.dmdev.http.util.UrlPath.FLIGHTS;

@WebServlet(FLIGHTS)
public class FlightServlet extends HttpServlet
{
    private final FlightService flightService = FlightService.getInstance();

    // Вариант с генерацией динамической составляющей,
    // закрытием Writer и незамедлительной отправкой в клиент Response
    /*
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (var printWriter = resp.getWriter())
        {
            printWriter.write("<h1>Список перелётов:</h1>");
            printWriter.write("<ul>");
            flightService.findAll().forEach(flightDto -> {
                printWriter.write("""
                    <li>
                        <a href="/tickets?flightId=%d">%s</a>
                    </li>
                    """.formatted(flightDto.getId(), flightDto.getDescription()));
            });
            printWriter.write("</ul>");
        }
    }
    */

    // Вариант с генерацией динамической составляющей,
    // Writer не закрывается, в этом случае клиенту не будет возвращён Response
    /*
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        var printWriter = resp.getWriter();
        printWriter.write("<h1>Список перелётов:</h1>");
        printWriter.write("<ul>");
        flightService.findAll().forEach(flightDto -> {
            printWriter.write("""
                    <li>
                        <a href="/tickets?flightId=%d">%s</a>
                    </li>
                    """.formatted(flightDto.getId(), flightDto.getDescription()));
        });
        printWriter.write("</ul>");
    }
     */

    // Вариант с переадресацией запроса на JSP страницу
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        req.setAttribute("flights", flightService.findAll());

        req.getRequestDispatcher(JspHelper.getPath("flights"))
            .forward(req, resp);
    }
}
