package com.test;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class ApplePayVO implements Serializable {

    private String transactionId;

    private String productId;

    public String getTransactionId() {
        return transactionId;
    }

    public ApplePayVO setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public ApplePayVO setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public ApplePayVO(String transactionId, String productId) {
        this.transactionId = transactionId;
        this.productId = productId;
    }

    public static void main(String[] args) throws Exception {
        Set<ApplePayVO> applePayVOSet = new HashSet<>();

        applePayVOSet.add(new ApplePayVO("transactionId", "productId"));
        applePayVOSet.add(new ApplePayVO("transactionId", "productId"));
        for (int i = 0; i < 100; i++) {
            System.out.println("i==" + i);
            System.out.println(applePayVOSet.size());
        }


    }
}
