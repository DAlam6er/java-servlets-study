package com.dmdev.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dmdev.http.util.UrlPath.LOCALE;
import static com.dmdev.http.util.UrlPath.LOGIN;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@WebServlet(LOCALE)
public class LocaleServet extends HttpServlet
{
    private static final String LANG = "lang";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        // параметр - имя кнопки из страницы header.jsp
        var language = req.getParameter(LANG);
        // установка языка в сессию
        req.getSession().setAttribute(LANG, language);

        var prevPage = req.getHeader("referer");
        var page = prevPage != null ? prevPage : LOGIN;

        var uri = URI.create(page);
        var uriQuery = uri.getQuery();
        if (uriQuery != null) {
            var parameters =
                Arrays.stream(uriQuery.split("&"))
                .map(this::splitQueryParameter)
                .collect(Collectors.groupingBy(
                    SimpleImmutableEntry::getKey,
                    LinkedHashMap::new,
                    mapping(Map.Entry::getValue, toList())));

            parameters.put(LANG, List.of(language));
            //TODO переписать в Streams API
            StringBuilder newQuery = new StringBuilder("?");
            for (Map.Entry<String, List<String>> entry : parameters.entrySet()) {
                for (String value : entry.getValue()) {
                    newQuery.append(entry.getKey()).append("=").append(value).append("&");
                }
            }
            newQuery.deleteCharAt(newQuery.length()-1);

            resp.sendRedirect(uri.getPath() + newQuery);
        } else {
            resp.sendRedirect(uri.getPath() + "?lang=" + language);
        }
    }

    private SimpleImmutableEntry<String, String> splitQueryParameter(String it)
    {
        final int idx = it.indexOf("=");
        final String key = idx > 0 ? it.substring(0, idx) : it;
        final String value = idx > 0 && it.length() > idx + 1
            ? it.substring(idx + 1) : null;
        return new SimpleImmutableEntry<>(
            URLDecoder.decode(key, StandardCharsets.UTF_8),
            URLDecoder.decode(value, StandardCharsets.UTF_8)
        );
    }
}
