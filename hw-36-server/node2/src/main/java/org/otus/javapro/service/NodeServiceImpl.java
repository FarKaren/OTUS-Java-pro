package org.otus.javapro.service;

import io.grpc.stub.StreamObserver;
import org.example.NodeAnswer;
import org.example.NodeGrpc;
import org.example.NodeRequest;

public class NodeServiceImpl extends NodeGrpc.NodeImplBase {

    @Override
    public void sendRequestToNode(NodeRequest request, StreamObserver<NodeAnswer> responseObserver) {
        NodeAnswer answer = NodeAnswer.newBuilder()
                .setAnswer("Hello from Node2")
                .build();
        responseObserver.onNext(answer);
        responseObserver.onCompleted();
    }
}
