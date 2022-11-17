package com.code.example.transaction;

import com.code.data.PeopleEntity;
import com.code.data.UserTestEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author ccy
 * @description
 * @time 2022/11/16 19:19
 */
@Getter
@Setter
public class AfterTransactionEvent extends ApplicationEvent {

    private UserTestEntity peopleEntity;

    public AfterTransactionEvent(UserTestEntity peopleEntity) {
        super(peopleEntity);
        this.peopleEntity = peopleEntity;
    }
}
