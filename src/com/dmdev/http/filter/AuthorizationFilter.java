package com.dmdev.http.filter;

import com.dmdev.http.dto.ReadUserDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

import static com.dmdev.http.util.UrlPath.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter
{
    private static final Set<String> PUBLIC_PATH =
        Set.of(LOGIN, REGISTRATION, IMAGES, LOCALE);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException
    {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();
        // servletRequest содержит информацию о том, залогинен ли пользователь в системе или нет
        if (isPublicPath(uri) || isUserLoggedIn(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // header говорит о том, с какой страницы пришел пользователь на страницу /login
            // prevPage может быть null, если переход на недоступную страницу был осуществлен
            // путем ввода адреса в адресной строке
            var prevPage = ((HttpServletRequest) servletRequest).getHeader("referer");
            ((HttpServletResponse) servletResponse).sendRedirect(
                prevPage != null ? prevPage : LOGIN
            );
        }
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest)
    {
        var user = (ReadUserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        //return user != null && user.getRole() == Role.ADMIN;
        return user != null;
    }

    private boolean isPublicPath(String uri)
    {
        // uri может быть вида /images/users/image.png,
        // поэтому используем параметр вида path -> uri.startsWith(path)
        return PUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }
}
