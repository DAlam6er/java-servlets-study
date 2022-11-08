package com.dmdev.http.client;

import java.io.IOException;
import java.net.URL;

public class UrlExample
{
    public static void main(String[] args) throws IOException
    {
        //checkGoogle();

        var url = new URL("file:///G:\\My Drive\\prog\\Java\\web\\" +
            "servlets\\dmdev-http-servlets-starter\\src\\com\\dmdev\\" +
            "http\\socket\\DatagramRunner.java");
        var urlConnection = url.openConnection();
        System.out.println(new String(urlConnection.getInputStream().readAllBytes()));
    }

    private static void checkGoogle() throws IOException
    {
        var url = new URL("https://www.google.com");
        var urlConnection = url.openConnection();

        // по умолчанию работает с GET, для того, чтобы работал с POST:
        urlConnection.setDoOutput(true);
        try (var outputStream = urlConnection.getOutputStream())
        {
            byte[] buffer = "Body sample content".getBytes();
            outputStream.write(buffer);
        }

        System.out.println();
    }
}
