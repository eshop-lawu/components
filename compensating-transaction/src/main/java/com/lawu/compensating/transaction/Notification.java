package com.lawu.compensating.transaction;

import java.io.Serializable;

/**
 * @author Leach
 * @date 2017/3/29
 */
public abstract class Notification implements Serializable {
    
    private static final long serialVersionUID = 6363486881966391317L;
    
    private Long transactionId;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
