package efs.task.todoapp.web;

import com.sun.net.httpserver.HttpServer;
import efs.task.todoapp.init.commons.http.MyHttpHandler;
import efs.task.todoapp.init.HandicappedDependencyInjectionReader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class WebServerFactory {

    public static HttpServer createServer() throws IOException {

        System.out.println("Reading context and dependency injection....");

        HandicappedDependencyInjectionReader.run();
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        System.out.println("Thread pool: 10");
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        server.createContext("/", new MyHttpHandler());
        server.setExecutor(threadPoolExecutor);

        return server;
    }
}
