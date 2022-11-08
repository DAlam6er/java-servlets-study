package com.dmdev.http.socket;

import java.io.IOException;
import java.net.*;

public class DatagramRunner
{
    public static void main(String[] args) throws IOException
    {
        var inetAddress = InetAddress.getByName("localhost");
        try (var datagramSocket = new DatagramSocket())
        {
            // ----> [buffer] <------
            // размер буфера должен быть регламентирован с принимающей стороной
            //var bytes = new byte[512];
            var bytes = "Hello from UDP client".getBytes();
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, inetAddress, 7777);
            // отправляем запрос на сервер (без подтверждения о доставке)
            datagramSocket.send(packet);
        }
    }
}
