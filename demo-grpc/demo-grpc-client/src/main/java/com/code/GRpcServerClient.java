package com.code;

import com.code.proto.UserProto;
import com.code.proto.UserServiceGrpc;
import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.google.protobuf.util.JsonFormat;

import java.util.List;

public class GRpcServerClient {
    public static final String host = "127.0.0.1";
    public static final Integer port = 9999;

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        try {
            UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub = UserServiceGrpc.newBlockingStub(channel);
            UserProto.UserRequest userRequest = UserProto.UserRequest.newBuilder().setDate("20220517").build();
            UserProto.UserResponse userResponse = userServiceBlockingStub.listUser(userRequest);
            List<UserProto.User> usersList = userResponse.getUsersList();
            System.out.println(userRequest.getDate());
            for (UserProto.User user : usersList) {
//                System.out.println(user.toString());
                System.out.println(JsonFormat.printer().includingDefaultValueFields().print(user));
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        } finally {
            channel.shutdown();
        }


    }
}
