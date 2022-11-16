package com.code.example.transaction;

import com.code.data.UserTestDao;
import com.code.data.UserTestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ccy
 * @description
 * @time 2022/11/16 11:41
 */
@RestController
public class MockTxController {
    @Autowired
    private MockTxManager mockTxManager;

    @GetMapping("/mock/tx/start")
    public String txStart() {
        mockTxManager.txStart();
        return "success";
    }
}
