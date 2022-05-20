package com.springSecurity.service.Mail.Impl;

import com.springSecurity.config.RabbitConfiguration;
import com.springSecurity.entity.MailBean;
import com.springSecurity.service.Mail.MailService;
import com.springSecurity.utils.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service

public class MailServiceImpl implements MailService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailUtil mailUtil;

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);



    @Override
    public String sendMsgToMq(String msg) {
        //消息生产
        rabbitTemplate.convertAndSend(RabbitConfiguration.EXCHANGE_A, RabbitConfiguration.ROUTINGKEY_B, msg);
        logger.info("rabbitmq消息已经发送到交换机, 等待交换机接收..." + msg);





        return "success";
    }


    @RabbitHandler
    @RabbitListener(queues = RabbitConfiguration.QUEUE_B)
    public void sendEMail(String msg){
        logger.info("接收到发送邮件任务:{}", msg);
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    MailBean mailBean = new MailBean();
                    mailBean.setSubject("测试");
                    mailBean.setText(msg);
                    //发送邮件
                    mailUtil.sendMail(mailBean);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });







    }
}
