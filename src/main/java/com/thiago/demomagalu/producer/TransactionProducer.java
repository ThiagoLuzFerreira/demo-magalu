package com.thiago.demomagalu.producer;

import com.thiago.demomagalu.model.Transaction;

public interface TransactionProducer {

    void publishTransactionMessage(Transaction transaction);
}
