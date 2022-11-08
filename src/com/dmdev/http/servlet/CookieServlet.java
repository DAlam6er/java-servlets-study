package com.dmdev.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static com.dmdev.http.util.UrlPath.COOKIES;

@WebServlet(COOKIES)
public class CookieServlet extends HttpServlet
{
    private static final String UNIQUE_ID = "userId";
    private final AtomicInteger counter = new AtomicInteger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        // Неудобно, т.к. придется парсить строку вида:
        // cookie: name1=value1; name2=value2;
        //var cookie = req.getHeader("cookie");

        // может прийти null !
        var cookies = req.getCookies();
        if (cookies == null ||
            Arrays.stream(cookies)
                .filter(cookie -> UNIQUE_ID.equals(cookie.getName()))
                .findFirst()
                .isEmpty())
        {
            // пользователь зашел на страницу впервые
            // значение неважно в данной задаче
            var cookie = new Cookie(UNIQUE_ID, "1");
            cookie.setPath("/cookies");
            // продолжительность жизни измеряется в секундах
            // cookie будет доступна до тех пор, пока браузер не закроется
            //cookie.setMaxAge(-1);
            cookie.setMaxAge(15 * 60);

            resp.addCookie(cookie);
            counter.incrementAndGet();
        }

        resp.setContentType("text/html; charset=UTF-8");
        try (var writer = resp.getWriter())
        {
            writer.write(counter.get());
        }
    }
}
