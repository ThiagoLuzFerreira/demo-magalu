package com.thiago.demomagalu.producer;

import com.thiago.demomagalu.model.Transaction;
import com.thiago.demomagalu.model.dto.TransactionResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducerImpl implements TransactionProducer{

    private final RabbitTemplate rabbitTemplate;

    @Value("${mq.queues.magalu-queue}")
    private String routingKey;
    public TransactionProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishTransactionMessage(Transaction transaction) {

        TransactionResponseDTO responseDTO = new TransactionResponseDTO();
        responseDTO.setStatus(transaction.getStatus());
        responseDTO.setOracleTransactionId(transaction.getOracleTransactionId());

        rabbitTemplate.convertAndSend(routingKey, responseDTO);
    }
}
