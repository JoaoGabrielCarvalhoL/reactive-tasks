package br.com.joaogabriel.tasks.messaging;

import br.com.joaogabriel.tasks.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TaskNotificationProducer {

    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    @Value("${kafka.task.notification.output}")
    private String topic;

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public TaskNotificationProducer(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<Task> sendNotification(Task task) {
        return Mono.just(task)
                .map(it -> this.kafkaTemplate.send(topic, task))
                .map(it -> task)
                .doOnNext(it -> logger.log(Level.INFO, "Task Notification send successfully!"));
    }
}
