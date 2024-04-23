package br.com.joaogabriel.tasks.mapper;

import br.com.joaogabriel.tasks.controller.payload.TaskRequest;
import br.com.joaogabriel.tasks.controller.payload.TaskResponse;
import br.com.joaogabriel.tasks.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getPriority(),
                task.getState(), task.getAddress());
    }

    public TaskRequest toTaskRequest(Task task) {
        return new TaskRequest(task.getTitle(), task.getDescription(), task.getPriority());
    }

    public Task toTask(TaskRequest taskRequest) {
        return new Task(taskRequest.title(), taskRequest.description(), taskRequest.priority(), null);
    }
}
