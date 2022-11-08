package com.dmdev.http.servlet;

import com.dmdev.http.service.TicketService;
import com.dmdev.http.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.dmdev.http.util.UrlPath.TICKETS;

@WebServlet(TICKETS)
public class TicketServlet extends HttpServlet
{
    private final TicketService ticketService = TicketService.getInstance();

    // Вариант с переадресацией запроса на JSP страницу
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        var flightId = Long.valueOf(req.getParameter("flightId"));
        req.setAttribute("tickets", ticketService.findAllByFlightId(flightId));

        req.getRequestDispatcher(JspHelper.getPath("tickets"))
            .forward(req, resp);
    }

    // Вариант с генерацией динамической составляющей
    /*
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        var flightId = Long.valueOf(req.getParameter("flightId"));

        resp.setContentType("text/html; charset=UTF-8");

        try (var printWriter = resp.getWriter())
        {
            printWriter.write("<h1>Купленные билеты:</h1>");
            printWriter.write("<ul>");
            ticketService.findAllByFlightId(flightId).forEach(ticketDto -> {
                printWriter.write("""
                    <li>
                        %s
                    </li>
                    """.formatted(ticketDto.getSeatNo()));
            });
            printWriter.write("</ul>");
        }
    }
     */
}
