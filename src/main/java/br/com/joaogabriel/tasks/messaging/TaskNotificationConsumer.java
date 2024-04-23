package br.com.joaogabriel.tasks.messaging;

import br.com.joaogabriel.tasks.model.Task;
import br.com.joaogabriel.tasks.service.TaskService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TaskNotificationConsumer {

    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    private final TaskService taskService;

    public TaskNotificationConsumer(TaskService taskService) {
        this.taskService = taskService;
    }

    @KafkaListener(topics = "${kafka.task.notification.output}", groupId = "${kafka.task.notification.group.id}")
    public void receiveAndFinishTask(Task task) {
        Mono.just(task)
                .doOnNext(it -> logger.log(Level.INFO, "Receiving Task with Id: {0}", task.getId()))
                .flatMap(taskService::done)
                .block();
    }

}
