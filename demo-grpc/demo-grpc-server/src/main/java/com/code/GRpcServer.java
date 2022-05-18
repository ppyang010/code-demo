package com.code;

import cn.hutool.core.util.StrUtil;
import com.code.service.UserService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GRpcServer {
    public static final int port = 9999;

    public static void main(String[] args) throws InterruptedException, IOException {
        Server server = ServerBuilder.forPort(port).addService(new UserService()).build().start();
        System.out.println(StrUtil.format("grpc server start success port={}", port));
        server.awaitTermination();
    }
}
