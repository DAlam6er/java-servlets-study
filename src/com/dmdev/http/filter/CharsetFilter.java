package com.dmdev.http.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(value = "/*", dispatcherTypes = DispatcherType.REQUEST)
public class CharsetFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException
    {
        // запрос проходит через данный фильтр, следующие строки будут выполнены:
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // filterChain знает про всю цепочку вызовов
        filterChain.doFilter(request, response);
        // ответ возвращается через данный фильтр, следующая строка будет выполнена
        System.out.println();
    }
}