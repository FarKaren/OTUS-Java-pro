package org.otus.javapro.service;

import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.example.*;

import java.util.Map;
import java.util.stream.Collectors;

public class LoadBalancerServiceImpl extends LoadBalancerGrpc.LoadBalancerImplBase {
    private static final String HOST = "localhost";
    private final Map<Integer, String> nodeList = Map.of(
            8082, HOST,
            8083, HOST,
            8084, HOST
    );

    @Override
    public void sendRequestToBalancer(LoadBalancerRequest request, StreamObserver<LoadBalancerResponse> responseObserver) {
        var nodes = getAvailableNodeList();
        for (Map.Entry<Integer, String> node : nodes.entrySet()) {
            LoadBalancerResponse response = LoadBalancerResponse.newBuilder()
                    .setAnswer(getAnswer(node))
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    private String getAnswer(Map.Entry<Integer, String> node) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(node.getValue(), node.getKey())
                .usePlaintext()
                .build();
        NodeGrpc.NodeBlockingStub stub = NodeGrpc.newBlockingStub(channel);

        NodeRequest request = NodeRequest.newBuilder()
                .setGreeting("Hello! Give me the answer")
                .build();
        return stub.sendRequestToNode(request).getAnswer();
    }

    private Map<Integer, String> getAvailableNodeList() {
        return nodeList.entrySet().stream()
                .filter(node -> {
                    ManagedChannel channel = ManagedChannelBuilder.forAddress(node.getValue(), node.getKey())
                            .usePlaintext()
                            .build();
                    try {
                        ConnectivityState state = channel.getState(true);
                        return state == ConnectivityState.READY || state == ConnectivityState.IDLE;
                    } catch (Exception e) {
                        return false;
                    } finally {
                        channel.shutdownNow();
                    }
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
