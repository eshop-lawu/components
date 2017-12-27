package com.lawu.compensating.transaction;

import java.io.Serializable;

/**
 * @author Leach
 * @date 2017/4/12
 */
public class Reply implements Serializable {
    
    private static final long serialVersionUID = -2679454453968450127L;
    
    private Long transactionId;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
