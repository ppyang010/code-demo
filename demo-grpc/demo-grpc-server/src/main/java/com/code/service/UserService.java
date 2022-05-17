package com.code.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.code.proto.UserProto;
import com.code.proto.UserServiceGrpc;
import io.grpc.stub.StreamObserver;


public class UserService extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void listUser(UserProto.UserRequest request, StreamObserver<UserProto.UserResponse> responseObserver) {
        String date = request.getDate();
        UserProto.UserResponse res = null;
        UserProto.UserResponse.Builder resBuilder = UserProto.UserResponse.newBuilder();
        resBuilder.setData(date);

        try {
            for (int i = 0; i < 10; i++) {
                UserProto.User build = UserProto.User.newBuilder()
                        .setId(RandomUtil.randomInt(1000, 9999))
                        .setUsername(RandomUtil.randomString(9))
                        .setAge(RandomUtil.randomInt(0, 99))
                        .setCreateTime(DateUtil.currentSeconds())
                        .setValid(i % 2 == 0).build();
                resBuilder.addUsers(build);
            }
            res = resBuilder.build();
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onNext(res);
        }


    }
}
