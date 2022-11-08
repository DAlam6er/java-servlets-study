package com.dmdev.http.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class SocketServerRunner
{
    public static void main(String[] args) throws IOException
    {
        //socketServerTest1();

        try (var serverSocket = new ServerSocket(7777);
             var socket = serverSocket.accept();
             var outputStream = new DataOutputStream(socket.getOutputStream());
             var inputStream = new DataInputStream(socket.getInputStream());
             var scanner = new Scanner(System.in))
        {
            var request = inputStream.readUTF();
            while (!"stop".equals(request)) {
                System.out.println("Client request: " + request);

                var response = scanner.nextLine();
                outputStream.writeUTF(response);

                request = inputStream.readUTF();
            }
        }
    }

    private static void socketServerTest1() throws IOException
    {
        // tcp порт
        // accept() - блокирующий вызов
        try (var serverSocket = new ServerSocket(7777);
             var socket = serverSocket.accept();
             var outputStream = new DataOutputStream(socket.getOutputStream());
             var inputStream = new DataInputStream(socket.getInputStream()))
        {
            // readUTF() - блокирующий вызов
            var request = inputStream.readUTF();
            System.out.println("Client request: " + request);
            outputStream.writeUTF("Hello from server!");
        }
    }
}
