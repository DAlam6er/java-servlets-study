package com.dmdev.http.servlet;

import com.dmdev.http.dto.ReadUserDto;
import com.dmdev.http.service.UserService;
import com.dmdev.http.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

import static com.dmdev.http.util.UrlPath.LOGIN;

@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet
{
    private final UserService userService = UserService.getInstance();

    // возвращает страницу login.jsp
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        req.getRequestDispatcher(JspHelper.getPath("login"))
            .forward(req, resp);
    }

    // обработка нажатия кнопки Login
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        // используем атрибуты name тега <input> для получения параметров
        userService.login(req.getParameter("email"), req.getParameter("password"))
            .ifPresentOrElse(
                user -> onLoginSuccess(user, req, resp),
                () -> onLoginFail(req, resp)
            );
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.sendRedirect("/login?error&email=" + req.getParameter("email"));
    }

    // отдельный метод, т.к. лямбда-выражение выбрасывает java.io.IOException
    @SneakyThrows
    private void onLoginSuccess(ReadUserDto user, HttpServletRequest req, HttpServletResponse resp)
    {
        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/flights");
    }
}
