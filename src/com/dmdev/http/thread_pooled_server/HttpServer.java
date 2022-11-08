package com.dmdev.http.thread_pooled_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpServer
{
    private final ExecutorService pool;
    private final int port;
    private boolean stopped;

    public HttpServer(int port, int poolSize)
    {
        this.port = port;
        this.pool = Executors.newFixedThreadPool(poolSize);
    }

    public void run()
    {
        try {
            var server = new ServerSocket(port);
            while (!stopped) {
                var socket = server.accept();
                System.out.println("Socket accepted");
                // передаем в pool обработку сокета
                pool.submit(() -> processSocket(socket));
            }
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
            TimeUnit.SECONDS.sleep(10);
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
        } catch (IOException | InterruptedException e) {
            //TODO: log error message
            e.printStackTrace();
        }
    }

    public void setStopped(boolean stopped)
    {
        this.stopped = stopped;
    }
}
