package com.dmdev.http.servlet;

import com.dmdev.http.dto.ReadUserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.dmdev.http.util.UrlPath.SESSIONS;

@WebServlet(SESSIONS)
public class SessionServlet extends HttpServlet
{
    private static final String USER = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        // getSession() вызывает перегруженный метод getSession(true)
        // определяющий, создавать сессию, если она не была найдена в Map
        // т.е. по умолчанию она создается
        var session = req.getSession();
        //System.out.println(session.isNew());

        // Если у сессии есть атрибут "user", значит он пришел не впервые
        // и это уже как минимум его второй запрос
        var user = (ReadUserDto) session.getAttribute(USER);
        if (user == null) {
            // это первый запрос от пользователя, т.о. добавляем его в сессию
            // обычно пользователь устанавливается один раз на странице login
            // приложения по username и password
            user = ReadUserDto.builder()
                .id(25)
                .email("test@gmail.com")
                .build();
            session.setAttribute(USER, user);
        }
    }
}
