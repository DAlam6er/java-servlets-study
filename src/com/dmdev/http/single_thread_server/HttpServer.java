package com.dmdev.http.single_thread_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer
{
    private final int port;

    public HttpServer(int port)
    {
        this.port = port;
    }

    public void run()
    {
        try {
            var server = new ServerSocket(port);
            // Недостаток: сервер будет ждать, пока предыдущий запрос не будет выполнен
            var socket = server.accept();
            processSocket(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // метод обработки запроса
    private void processSocket(Socket socket)
    {
        // для считывания информации из Request
        try (socket;
            var inputStream = new DataInputStream(socket.getInputStream());
            var outputStream = new DataOutputStream(socket.getOutputStream()))
        {
            // 1. Обработка Request
            System.out.println("Request: " + new String(inputStream.readNBytes(400)));
            // 2. Создание Response
            var body = Files.readAllBytes(Path.of("resources", "example.html"));
            var headers = """
                HTTP/1.1 200 OK
                content-type: text/html
                content-length: %s
                """.formatted(body.length);

            outputStream.write(headers.getBytes());
            outputStream.write(System.lineSeparator().getBytes());
            outputStream.write(body);
        } catch (IOException e) {
            //TODO: log error message
            e.printStackTrace();
        }
    }
}
