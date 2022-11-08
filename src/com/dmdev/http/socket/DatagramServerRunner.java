package com.dmdev.http.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DatagramServerRunner
{
    public static void main(String[] args) throws IOException
    {
        try (var datagramServer = new DatagramSocket(7777))
        {
            // в этот buffer будут передаваться данные из клиента
            byte[] buffer = new byte[512];
            var packet = new DatagramPacket(buffer, buffer.length);
            // блокирующий метод
            datagramServer.receive(packet);

            //packet.getData();
            System.out.println(new String(buffer));
        }
    }
}
