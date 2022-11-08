package com.dmdev.http.single_thread_server;

public class ServerRunner
{
    public static void main(String[] args)
    {
        new HttpServer(9000).run();
    }
}
