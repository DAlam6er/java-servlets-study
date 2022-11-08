package com.dmdev.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.dmdev.http.util.UrlPath.DOWNLOAD;

@WebServlet(DOWNLOAD)
public class DownloadServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        // вместо отображения в браузере информации, будет скачан файл filename.txt
        resp.setHeader("Content-Disposition", "attachment; filename=\"filename.txt\"");
        //resp.setContentType("text/plain; charset=UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        /*
        try (var writer = resp.getWriter())
        {
            // передача hardcoded информации в filename.txt
            writer.write("Data from servlet!");
        }
         */

        /*
        так работать не будет, т.к. сервлет часть приложения TomCat
        где relative path будет от директории TomCat,
        а не от директории данного проекта в IDEA
        */
        //Files.readAllBytes(Path.of("resources", "first.json"));

        try(var outputStream = resp.getOutputStream();
        var inputStream = DownloadServlet.class.getClassLoader()
            .getResourceAsStream("first.json"))
        {
            outputStream.write(inputStream.readAllBytes());
        }
    }
}
