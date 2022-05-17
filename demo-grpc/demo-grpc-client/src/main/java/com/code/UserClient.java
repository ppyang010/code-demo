package com.code;

import cn.hutool.json.JSONUtil;
import com.code.proto.UserProto;
import com.code.proto.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;

import java.util.List;

public class UserClient {
    public static final String host = "localhost";
    public static final Integer port = 9800;

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
                System.out.println(JSONUtil.toJsonStr(user));
            }
        }finally {
            channel.shutdown();
        }


    }
}
