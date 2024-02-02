package org.otus.javapro;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.otus.javapro.service.LoadBalancerServiceImpl;

public class LoadBalancer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder
                .forPort(8081)
                .addService(new LoadBalancerServiceImpl())
                .build();
        server.start();
        server.awaitTermination();
    }
}