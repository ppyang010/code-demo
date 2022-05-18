package com.code.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.code.proto.UserProto;
import com.code.proto.UserServiceGrpc;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ccy
 * @description
 * @time 2022/5/18 6:36 PM
 */
@RestController
public class UserController {
    /**
     * 请求方的实例名
     */
    @GrpcClient("grpc-could-eureka-server")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @GetMapping("/user/list")
    public String listUsers(String date) throws InvalidProtocolBufferException {
        if (StrUtil.isBlank(date)) {
            date = DateUtil.today();
        }
        UserProto.UserRequest userRequest = UserProto.UserRequest.newBuilder().setDate(date).build();
        UserProto.UserResponse userResponse = userServiceBlockingStub.listUser(userRequest);
        return JsonFormat.printer().includingDefaultValueFields().print(userResponse);
    }
}
