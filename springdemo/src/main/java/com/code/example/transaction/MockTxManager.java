package com.code.example.transaction;

import cn.hutool.core.util.RandomUtil;
import com.code.data.UserTestDao;
import com.code.data.UserTestEntity;
import com.code.util.JacksonUtil;
import com.code.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author ccy
 * @description
 * @time 2022/11/16 11:45
 */
@Service
@Slf4j
public class MockTxManager {

    @Autowired
    private UserTestDao userTestDao;

    @Transactional
    public void txStart() {
        UserTestEntity peopleEntity = new UserTestEntity();
//        peopleEntity.setId(RandomUtil.randomInt(0,9999));
        peopleEntity.setUsername(RandomUtil.randomString(8));
        userTestDao.save(peopleEntity);
        Optional<UserTestEntity> byId = userTestDao.findById(peopleEntity.getId());
        log.info("query1={}",JacksonUtil.obj2str(byId.get()));

        peopleEntity.setPassword(RandomUtil.randomNumbers(16));
        userTestDao.save(peopleEntity);
        byId = userTestDao.findById(peopleEntity.getId());
        log.info("query2={}",JacksonUtil.obj2str(byId.get()));
        SpringUtil.publishEvent(new AfterTransactionEvent(peopleEntity));
        log.info("txStart method finish");
    }
}
