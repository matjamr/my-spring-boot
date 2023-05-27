package efs.task.todoapp.web;

import com.sun.net.httpserver.HttpServer;
import efs.task.todoapp.init.HandicappedDependencyInjectionReader;
import efs.task.todoapp.init.http.MyHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServerFactory {

    public static HttpServer createServer() throws IOException {

        System.out.println("Reading context and dependency injection....");

        HandicappedDependencyInjectionReader.run();
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        server.createContext("/", new MyHttpHandler());

        return server;
    }
}
