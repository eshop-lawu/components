package com.lawu.eshop.compensating.transaction;

import java.io.Serializable;

/**
 * @author Leach
 * @date 2017/4/12
 */
public class Reply implements Serializable {
    private Long transactionId;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
