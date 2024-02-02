package org.otus.javapro;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.otus.javapro.service.NodeServiceImpl;

public class Node1 {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder
                .forPort(8082)
                .addService(new NodeServiceImpl())
                .build();
        server.start();
        server.awaitTermination();
    }
}