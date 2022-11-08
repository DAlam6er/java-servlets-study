package com.dmdev.http.servlet;

import com.dmdev.http.dto.CreateUserDto;
import com.dmdev.http.entity.Gender;
import com.dmdev.http.entity.Role;
import com.dmdev.http.exception.ValidationException;
import com.dmdev.http.service.UserService;
import com.dmdev.http.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.dmdev.http.util.UrlPath.REGISTRATION;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet
{
    private final UserService userService = UserService.getInstance();

    // возвращаем jsp страницу
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        /*
        // Будет ошибка 500, пользователь увидит страницу 500.jsp,
        // прописанную в <error-page> </error-page> в файле web.xml
        if (true) {
            throw new RuntimeException();
        }
         */

        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());

        req.getRequestDispatcher(JspHelper.getPath("registration"))
            .forward(req, resp);
    }

    // принимаем форму и обрабатываем запросы
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        // Извлекаем все необходимые параметры из формы
        // name - имя параметра <input type="text" name="name" id="nameId">
        var userDto = CreateUserDto.builder()
            .name(req.getParameter("name"))
            .birthday(req.getParameter("birthday"))
            .email(req.getParameter("email"))
            .image(req.getPart("image"))
            .password(req.getParameter("password"))
            .role(req.getParameter("role"))
            .gender(req.getParameter("gender"))
            .build();

        try {
            userService.create(userDto);
            // После создания пользователя, делаем перенаправление на страницу входа
            resp.sendRedirect("/login");
        } catch (ValidationException ex) {
            req.setAttribute("errors", ex.getErrors());
            doGet(req, resp);
        }
    }
}
