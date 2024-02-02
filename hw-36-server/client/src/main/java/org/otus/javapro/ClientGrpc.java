package org.otus.javapro;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.example.LoadBalancerGrpc;
import org.example.LoadBalancerRequest;
import org.example.LoadBalancerResponse;

import java.util.concurrent.CountDownLatch;

public class ClientGrpc {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext()
                .build();

        LoadBalancerGrpc.LoadBalancerStub stub = LoadBalancerGrpc.newStub(channel);

        LoadBalancerRequest request = LoadBalancerRequest.newBuilder()
                .setGreeting("Hello! Give me the answer")
                .build();

        CountDownLatch latch = new CountDownLatch(1);
        stub.sendRequestToBalancer(request, new StreamObserver<LoadBalancerResponse>() {
            @Override
            public void onNext(LoadBalancerResponse value) {
                System.out.println(value.getAnswer());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });
        latch.await();
    }
}