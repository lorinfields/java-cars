package com.lambdaschool.cars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogConsumer
{
    @RabbitListener(queues = CarsApplication.QUEUE_NAME)
    public void consumeMessage(final Message cm)
    {
        log.info("Recieved Message: {}", cm.toString());
    }
}
