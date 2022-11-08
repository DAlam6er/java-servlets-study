package com.dmdev.http.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Scanner;

public class SocketRunner
{
    public static void main(String[] args) throws IOException
    {
        //socketTest1();

        var inetAddress = Inet4Address.getByName("localhost");
        try (var socket = new Socket(inetAddress, 7777);
             var outputStream = new DataOutputStream(socket.getOutputStream());
             var inputStream = new DataInputStream(socket.getInputStream());
             var scanner = new Scanner(System.in))
        {
            //outputStream.writeUTF("Hello world!");
            while (scanner.hasNextLine()) {
                var request = scanner.nextLine();
                outputStream.writeUTF(request);

                // readUTF() - блокирующий вызов
                // в случае закрытия соединения сервером (получение "stop")
                // ответа от сервера не будет, и т.о. будет выброшено
                // java.io.EOFException
                var response = inputStream.readUTF();
                System.out.println("Response from server: " + response);
            }
        }

    }

    private static void socketTest1() throws IOException
    {
        // http - порт 80
        // https - порт 443
        // tcp
        // клиент
        // inetAddress используется для того, чтобы не хардкодить хост при создании сокета
        var inetAddress = Inet4Address.getByName("google.com");
        try (var socket = new Socket(inetAddress, 80);
             var outputStream = new DataOutputStream(socket.getOutputStream());
             var inputStream = new DataInputStream(socket.getInputStream()))
        {
            outputStream.writeUTF("Hello world!");
            // клиент (он же сокет) будет ожидать до тех пор, пока не вернем ответ
            var response = inputStream.readAllBytes();
            System.out.println(response.length);
        }
    }
}
