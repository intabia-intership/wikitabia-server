package com.intabia.wikitabia.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intabia.wikitabia.config.rabbitmq.RabbitConstant;
import com.intabia.wikitabia.dao.UsersDao;
import com.intabia.wikitabia.dto.ResourceDto;
import com.intabia.wikitabia.exceptions.CustomException;
import com.intabia.wikitabia.services.interfaces.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
@Slf4j
@RequiredArgsConstructor
public class ResourceListener {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitConstant.queueName)
    public void createResourceByTelegram(String resource) {
        try {
            ResourceDto dto = objectMapper.readValue(resource, ResourceDto.class);
            resourceService.createResourceFromTelegram(dto);
            log.info("Ресурс '" + dto.getName() + "' добавлена");
        } catch (JsonProcessingException e) {
            throw new CustomException("Ресурс не может быть обработан");
        }
    }
}
