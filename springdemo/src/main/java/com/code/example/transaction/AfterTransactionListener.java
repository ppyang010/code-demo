package com.code.example.transaction;

import com.code.data.PeopleEntity;
import com.code.data.UserTestEntity;
import com.code.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author ccy
 * @description
 * @time 2022/11/16 19:21
 */
@Slf4j
@Component
public class AfterTransactionListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    private void onAfterCommitEvent(AfterTransactionEvent event) {
        UserTestEntity peopleEntity = event.getPeopleEntity();

        log.info("onAfterCommitEvent entity={}", JacksonUtil.obj2str(peopleEntity));
    }
}
