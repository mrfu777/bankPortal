package com.synpulse.portal.message.impl;


import com.synpulse.portal.message.BankPortalMessageListener;
import com.synpulse.portal.service.TransactionRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@Slf4j
public class BankPortalMessageListenerImpl implements BankPortalMessageListener {

    @Autowired
    private TransactionRecordsService transactionRecordsService;

    /**
     *  TransactionTopic kafka tpic
     *  groupId comsume group
     * @param record kafka msg
     * @param item ack controller
     */
    @KafkaListener(topics = "transactionTopic",groupId = "transactionGroup")
    public void onMessage(ConsumerRecord<String,String> record, Acknowledgment item){
        log.info("receiver message key:{} value:{}",record.key(),record.value());
        transactionRecordsService.handlerMsg(record.value());
        item.acknowledge();
        log.info("message handler success");
    }
}
